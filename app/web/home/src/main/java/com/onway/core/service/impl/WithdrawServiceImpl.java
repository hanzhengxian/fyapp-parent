/**
 * 
 */
package com.onway.core.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.onway.common.lang.StringUtils;
import com.onway.core.service.WithdrawService;
import com.onway.fyapp.common.dal.daointerface.AccountDAO;
import com.onway.fyapp.common.dal.daointerface.AccountLogDAO;
import com.onway.fyapp.common.dal.daointerface.TeamUserDAO;
import com.onway.fyapp.common.dal.daointerface.UserDAO;
import com.onway.fyapp.common.dal.daointerface.WithdrawDAO;
import com.onway.fyapp.common.dal.dataobject.AccountDO;
import com.onway.fyapp.common.dal.dataobject.AccountLogDO;
import com.onway.fyapp.common.dal.dataobject.TeamUserDO;
import com.onway.fyapp.common.dal.dataobject.UserDO;
import com.onway.fyapp.common.dal.dataobject.WithdrawDO;
import com.onway.model.enums.AccountLogFlgEnum;
import com.onway.model.enums.AccountLogTypeEnum;
import com.onway.model.enums.DelFlgEnum;
import com.onway.model.enums.PayTypeEnum;
import com.onway.model.enums.UserRoleEnum;
import com.onway.model.enums.WithdrawStatusEnum;
import com.onway.platform.common.base.BaseResult;
import com.onway.platform.common.service.template.ServiceCheckCallback;
import com.onway.platform.common.service.template.ServiceTemplate;
import com.onway.platform.common.service.util.AssertUtil;

/**
 * 提现 （转账到支付宝账户 转账到微信账户）
 */
public class WithdrawServiceImpl implements WithdrawService {

    private static final Logger logger = Logger.getLogger(WithdrawServiceImpl.class);

    private WithdrawDAO         withdrawDAO;

    private ServiceTemplate     serviceTemplate;

    private AccountDAO          accountDAO;

    private AccountLogDAO       accountLogDAO;

    private UserDAO             userDAO;

    private TeamUserDAO         teamUserDAO;

    private WechatComponent     wechatComponent;

    private AlipayComponent     alipayComponent;

    public void setWithdrawDAO(WithdrawDAO withdrawDAO) {
        this.withdrawDAO = withdrawDAO;
    }

    public void setServiceTemplate(ServiceTemplate serviceTemplate) {
        this.serviceTemplate = serviceTemplate;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public void setAccountLogDAO(AccountLogDAO accountLogDAO) {
        this.accountLogDAO = accountLogDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setTeamUserDAO(TeamUserDAO teamUserDAO) {
        this.teamUserDAO = teamUserDAO;
    }

    public void setWechatComponent(WechatComponent wechatComponent) {
        this.wechatComponent = wechatComponent;
    }

    public void setAlipayComponent(AlipayComponent alipayComponent) {
        this.alipayComponent = alipayComponent;
    }

    /**
     * 提现审核
     * 
     * @return
     */
    @Override
    public BaseResult withDrawCheck(final Integer id, final String withdrawNo,
                                    final String checkType, final String failReason,
                                    final HttpServletRequest request) {
        final BaseResult result = new BaseResult(true);
        serviceTemplate.execute(result, new ServiceCheckCallback() {

            @Override
            public void check() {
                AssertUtil.notBlank(withdrawNo, "提现操作号为空");
                AssertUtil.notBlank(checkType, "审核状态为空");
            }

            @Override
            public void executeService() {
                if (StringUtils.equals(checkType, WithdrawStatusEnum.REFUSE.getCode())
                    && StringUtils.isBlank(failReason)) {
                    result.setSuccess(false);
                    result.setMessage("拒绝审核请填写原因");
                    return;
                }

                WithdrawDO withdrawDO = withdrawDAO.selectWithdrawByIdAndNo(id, withdrawNo);
                if (null == withdrawDO) {
                    result.setSuccess(false);
                    result.setMessage("提现信息不存在");
                    return;
                }

                if (StringUtils.equals(checkType, WithdrawStatusEnum.REFUSE.getCode())) {
                    // 直接拒绝
                    // 1.给用户退还胡币 2. 修改审核状态 REFUSE ， 审核拒绝原因 failReason
                    transAcc(withdrawDO);

                    withdrawDO.setStatus(WithdrawStatusEnum.REFUSE.getCode());
                    withdrawDO.setFailReason(failReason);
                    withdrawDAO.checkWithdraw(withdrawDO);
                } else if (StringUtils.equals(checkType, WithdrawStatusEnum.OFF_LINE.getCode())) {
                    // 线下打款
                    // 直接修改成线下打款状态
                    withdrawDO.setStatus(WithdrawStatusEnum.OFF_LINE.getCode());
                    withdrawDAO.checkWithdraw(withdrawDO);
                    withdrawDO.setStatus(checkType);
                    withdrawDO.setFailReason(failReason);
                    withdrawDO.setModifier(request.getSession().getAttribute("user_id").toString());
                    if (StringUtils.hasLength(request.getParameter("listFile"))) {
                        withdrawDO.setVoucehrImg(request.getParameter("listFile"));
                    }
                    withdrawDAO.shenheOnline(withdrawDO);

                } else if (StringUtils.equals(checkType, WithdrawStatusEnum.FINISH.getCode())) {

                    AccountDO accountDO = accountDAO.selectByAccNo(withdrawDO.getAccountNo());
                    //用户账户
                    String openId = "";
                    String realName = "";
                    if (StringUtils.equals(accountDO.getType(), "1")) {
                        UserDO userDO = userDAO.selectByUserId(accountDO.getLinkId());
                        openId = userDO.getOpenId();

                    } else {
                        //团队账户,查找团队leader
                        TeamUserDO teamUserDO = teamUserDAO.selectLeaderByTeamId(
                            accountDO.getLinkId(), UserRoleEnum.LEADER_ROLE.getCode());
                        if (null == teamUserDO) {
                            result.setSuccess(false);
                            result.setMessage("没有团队领导信息");
                            return;
                        }
                        UserDO userDO = userDAO.selectByUserId(teamUserDO.getTeamUserId());
                        openId = userDO.getOpenId();
                    }

                    // 审核通过
                    // 支付宝 或者 微信 根据
                    String withdrawType = withdrawDO.getWithdrawType();
                    if (StringUtils.equals(withdrawType, PayTypeEnum.ALIPAY.getCode())) {
                        // 转账到支付宝账户
                        AlipayFundTransToaccountTransferResponse response = null;
                        try {
                            response = alipayComponent.pay(withdrawDO.getWithdrawNo() + "TX",
                                withdrawDO.getAmount().toString(), "account", realName);
                        } catch (AlipayApiException e) {
                            logger.error("支付宝转账调用异常", e);
                            throw new RuntimeException("支付宝转账调用异常");
                        }

                        //10000:成功    20000：异常
                        if (StringUtils.equals(response.getCode(), "20000")) {
                            //                            transAcc(withdrawDO);
                            //                            withdrawDO.setStatus(WithdrawStatusEnum.FAILED.getCode());
                            //                            withdrawDO.setFailReason(response.getSubMsg());
                            //                            withdrawDAO.checkWithdraw(withdrawDO);
                            //                            throw new RuntimeException(response.getSubMsg());
                            result.setSuccess(false);
                            result.setMessage(response.getSubMsg());
                            return;
                        }

                        withdrawDO.setStatus(WithdrawStatusEnum.FINISH.getCode());
                        withdrawDAO.checkWithdraw(withdrawDO);

                    } else if (StringUtils.equals(withdrawType, PayTypeEnum.WEIXIN.getCode())) {
                        // 转账到微信用户
                        BaseResult baseResult = wechatComponent.withdrawByWechat(
                            withdrawDO.getWithdrawNo() + "TX", withdrawDO.getAmount(), openId,
                            request);
                        if (!baseResult.isSuccess()) {
                            //                            transAcc(withdrawDO);
                            //                            withdrawDO.setStatus(WithdrawStatusEnum.FAILED.getCode());
                            //                            withdrawDO.setFailReason(baseResult.getMessage());
                            //                            withdrawDAO.checkWithdraw(withdrawDO);
                            //                            throw new RuntimeException(baseResult.getMessage());
                            result.setSuccess(false);
                            result.setMessage(baseResult.getMessage());
                            return;
                        }
                        withdrawDO.setStatus(WithdrawStatusEnum.FINISH.getCode());
                        withdrawDAO.checkWithdraw(withdrawDO);
                    }

                }
            }
        });
        return result;
    }

    //提现退回
    public boolean transAcc(WithdrawDO withdrawDO) {
        boolean result = false;
        AccountDO lockAccountDO = accountDAO.selectByAccNoForUpdate(withdrawDO.getAccountNo());
        if (null == lockAccountDO) {
            throw new RuntimeException("账户不存在");
        }

        //积分回退
        //        if (0 < accountDAO.updateHuPoint(withdrawDO.getHuAmount().add(lockAccountDO.getHuPoint()),
        //            lockAccountDO.getAccountNo())) {
        //            AccountLogDO accountLog = new AccountLogDO();
        //            accountLog.setAccountNo(lockAccountDO.getAccountNo());
        //            accountLog.setType("3");
        //            accountLog.setFlg("7");
        //            accountLog.setAmount(withdrawDO.getHuAmount());
        //            accountLog.setBeforeBalance(lockAccountDO.getHuPoint());
        //            accountLog.setAfterBalance(lockAccountDO.getHuPoint().add(withdrawDO.getHuAmount()));
        //            accountLog.setDelFlg("0");
        //            if (0 < accountLogDAO.insert(accountLog)) {
        //                result = true;
        //            }
        //        }

        //积分回退
        if (0 < accountDAO.updateHuPointRe(
            withdrawDO.getHuAmount().add(lockAccountDO.getRePoint()), lockAccountDO.getAccountNo())) {
            AccountLogDO accountLog = new AccountLogDO();
            accountLog.setAccountNo(lockAccountDO.getAccountNo());
            accountLog.setType(AccountLogTypeEnum.RE_POINT.getCode());
            accountLog.setFlg(AccountLogFlgEnum.RETURNPUTFORWARD.getCode());
            accountLog.setAmount(withdrawDO.getHuAmount());
            accountLog.setBeforeBalance(lockAccountDO.getRePoint());
            accountLog.setAfterBalance(lockAccountDO.getRePoint().add(withdrawDO.getHuAmount()));
            accountLog.setDelFlg(DelFlgEnum.NOT_DEL.getCode());
            if (0 < accountLogDAO.insert(accountLog)) {
                result = true;
            }
        }

        return result;

    }
}
