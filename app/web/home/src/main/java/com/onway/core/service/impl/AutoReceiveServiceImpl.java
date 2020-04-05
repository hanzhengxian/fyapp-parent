/**
 * 
 */
package com.onway.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.onway.common.lang.Money;
import com.onway.common.lang.StringUtils;
import com.onway.core.service.AutoReceiveService;
import com.onway.fyapp.common.dal.daointerface.AccountDAO;
import com.onway.fyapp.common.dal.daointerface.AccountLogDAO;
import com.onway.fyapp.common.dal.daointerface.OrderRebateDAO;
import com.onway.fyapp.common.dal.daointerface.PlanReturnDAO;
import com.onway.fyapp.common.dal.daointerface.SysLevelDAO;
import com.onway.fyapp.common.dal.daointerface.UserDAO;
import com.onway.fyapp.common.dal.dataobject.AccountDO;
import com.onway.fyapp.common.dal.dataobject.AccountLogDO;
import com.onway.fyapp.common.dal.dataobject.OrderRebateDO;
import com.onway.fyapp.common.dal.dataobject.PlanReturnDO;
import com.onway.fyapp.common.dal.dataobject.SysLevelDO;
import com.onway.model.enums.AccountLogFlgEnum;
import com.onway.model.enums.AccountLogTypeEnum;
import com.onway.model.enums.AccountTypeEnum;
import com.onway.model.enums.DelFlgEnum;
import com.onway.model.enums.UserLevelEnum;
import com.onway.platform.common.base.BaseResult;
import com.onway.platform.common.base.QueryResult;
import com.onway.platform.common.service.template.AbstractServiceImpl;
import com.onway.platform.common.service.template.ServiceCheckCallback;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.utils.DateUtil;

public class AutoReceiveServiceImpl extends AbstractServiceImpl implements AutoReceiveService {

    private AccountDAO     accountDAO;

    private AccountLogDAO  accountLogDAO;

    private UserDAO        userDAO;

    private OrderRebateDAO orderRebateDAO;

    private PlanReturnDAO  planReturnDAO;

    private SysLevelDAO    sysLevelDAO;

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public void setAccountLogDAO(AccountLogDAO accountLogDAO) {
        this.accountLogDAO = accountLogDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setOrderRebateDAO(OrderRebateDAO orderRebateDAO) {
        this.orderRebateDAO = orderRebateDAO;
    }

    public void setPlanReturnDAO(PlanReturnDAO planReturnDAO) {
        this.planReturnDAO = planReturnDAO;
    }

    public void setSysLevelDAO(SysLevelDAO sysLevelDAO) {
        this.sysLevelDAO = sysLevelDAO;
    }

    /**
     * 支付成功 返健康值（购买用户）
     */
    @Override
    public BaseResult payOrderSuccForDevote(final String userId, final String userType,
                                            final Money addDevote, final String childOrderId) {
        final BaseResult result = new BaseResult(false);
        serviceTemplate.execute(result, new ServiceCheckCallback() {

            @Override
            public void check() {
                AssertUtil.notBlank(userId, "用户编号为空");
                AssertUtil.notBlank(userType, "用户类型为空");
            }

            @Override
            public void executeService() {
                if (addDevote.lessEqualThan(new Money()))
                    return;

                AccountDO accountDO = accountDAO.queryByLinkId(userId, userType,
                    DelFlgEnum.NOT_DEL.getCode());

                updateUserDevoteForLevel(accountDO, addDevote, "0", childOrderId);

                result.setSuccess(true);
            }

        });
        return result;
    }

    /*
     * 支付成功 返健康值（推荐团队）
     */
    @Override
    public BaseResult payOrderSuccForDevoteForTeam(final String teamId, final Money addDevote,
                                                   final String childOrderId) {
        final BaseResult result = new BaseResult(false);
        serviceTemplate.execute(result, new ServiceCheckCallback() {

            @Override
            public void check() {
                AssertUtil.notBlank(teamId, "门店编号为空");
            }

            @Override
            public void executeService() {
                if (addDevote.lessEqualThan(new Money()))
                    return;

                //给团队增加健康值
                AccountDO accountTeam = accountDAO.queryByLinkId(teamId,
                    AccountTypeEnum.TEAM.getCode(), DelFlgEnum.NOT_DEL.getCode());
                if (null == accountTeam) {
                    result.setMessage("未查询到团队账户信息");
                    return;
                }

                // 给团队增加健康值
                accountDAO.payOrderSuccForDevote(accountTeam.getDevoteAmount().add(addDevote),
                    accountTeam.getAccountNo());

                // 增加流水
                AccountLogDO accountLogT = new AccountLogDO();
                accountLogT.setAccountNo(accountTeam.getAccountNo());
                accountLogT.setType(AccountLogTypeEnum.CONTRIBUTE.getCode());
                accountLogT.setFlg(AccountLogFlgEnum.REBATE.getCode());
                accountLogT.setAmount(addDevote);
                accountLogT.setBeforeBalance(accountTeam.getDevoteAmount());
                accountLogT.setAfterBalance(accountTeam.getDevoteAmount().add(addDevote));
                accountLogT.setLinkNo(childOrderId);
                accountLogDAO.creatNewLog(accountLogT);
            }

        });
        return result;
    }

    /**
     * 返利 流水（推荐人 团队）
     */
    @Override
    public BaseResult payOrderSuccForReturnTeam(final Money returnHu, final String returnTeamId,
                                                final String returnTeamType, final String linkNo) {
        final BaseResult result = new BaseResult(false);
        serviceTemplate.execute(result, new ServiceCheckCallback() {

            @Override
            public void check() {
                AssertUtil.notBlank(returnTeamId, "团队编号为空");
                AssertUtil.notBlank(returnTeamType, "团队类型为空");
            }

            @Override
            public void executeService() {

                AccountDO accountDO = accountDAO.queryByLinkId(returnTeamId, returnTeamType,
                    DelFlgEnum.NOT_DEL.getCode());
                if (null == accountDO) {
                    result.setMessage("未查询到账户信息");
                    return;
                }

                String accountNo = accountDO.getAccountNo();

                Money hu = returnHu;

                if (hu.greaterThan(new Money())) {
//                    accountDAO.updatePoint(accountDO.getHuPoint().add(hu), accountNo);
                    accountDAO.updateHuPointRe(accountDO.getRePoint().add(hu), accountNo);
                    // 增加流水
                    AccountLogDO accountLog = new AccountLogDO();
                    accountLog.setAccountNo(accountDO.getAccountNo());
                    accountLog.setType(AccountLogTypeEnum.RE_POINT.getCode());
                    accountLog.setFlg(AccountLogFlgEnum.REBATE.getCode());
                    accountLog.setAmount(hu);
                    accountLog.setBeforeBalance(accountDO.getRePoint());
                    accountLog.setAfterBalance(accountDO.getRePoint().add(hu));
                    accountLog.setLinkNo(linkNo);
                    accountLogDAO.creatNewLog(accountLog);
                }
            }

        });
        return result;
    }

    @Override
    public BaseResult payOrderSuccForReturnStock(final String returnUserId,
                                                 final String returnUserType,
                                                 final Money returnPoint, final String linkNo) {

        final BaseResult result = new BaseResult(false);
        serviceTemplate.execute(result, new ServiceCheckCallback() {

            @Override
            public void check() {
                AssertUtil.notBlank(returnUserId, "用户编号为空");
                AssertUtil.notBlank(returnUserType, "用户类型为空");
            }

            @Override
            public void executeService() {

                AccountDO accountDO = accountDAO.queryByLinkId(returnUserId, returnUserType,
                    DelFlgEnum.NOT_DEL.getCode());
                if (null == accountDO) {
                    result.setMessage("未查询到账户信息");
                    return;
                }

                String accountNo = accountDO.getAccountNo();

                if (returnPoint.greaterThan(new Money())) {
                    accountDAO.updatePoint(accountDO.getHuPoint().add(returnPoint), accountNo);
                    // 增加流水
                    AccountLogDO accountLog = new AccountLogDO();
                    accountLog.setAccountNo(accountDO.getAccountNo());
                    accountLog.setType(AccountLogTypeEnum.POINT.getCode());
                    accountLog.setFlg(AccountLogFlgEnum.REBATE.getCode());
                    accountLog.setAmount(returnPoint);
                    accountLog.setBeforeBalance(accountDO.getHuPoint().add(accountDO.getRePoint()));
                    accountLog.setAfterBalance(accountDO.getHuPoint().add(accountDO.getRePoint())
                        .add(returnPoint));
                    accountLog.setLinkNo(linkNo);
                    accountLogDAO.creatNewLog(accountLog);
                }

            }

        });
        return result;
    }

    @Override
    public BaseResult newOrderRebate(final OrderRebateDO orderRebateDO) {
        final BaseResult result = new BaseResult(true);
        serviceTemplate.execute(result, new ServiceCheckCallback() {

            @Override
            public void check() {
            }

            @Override
            public void executeService() {
                orderRebateDAO.newOrderRebate(orderRebateDO);
            }
        });
        return result;
    }

    /**
     * 获取返利方案（推荐人）
     */
    @Override
    public QueryResult<PlanReturnDO> getPlanReturn(final String returnType,
                                                   final int recommendLevel, final String teamId) {
        final QueryResult<PlanReturnDO> result = new QueryResult<PlanReturnDO>(false);
        serviceTemplate.execute(result, new ServiceCheckCallback() {

            @Override
            public void check() {

            }

            @Override
            public void executeService() {
                PlanReturnDO query = planReturnDAO.query(returnType, recommendLevel, teamId, DelFlgEnum.NOT_DEL.getCode());
                if (null != query) {
                    result.setSuccess(true);
                    result.setResultObject(query);
                }
            }
        });
        return result;
    }

    //***********************************************************************************//

    /**
     * 健康值更新用户等级  实时更新
     * type  0 +  1 -
     */
    public BaseResult updateUserDevoteForLevel(final AccountDO accountDO, final Money thisDevote,
                                               final String thisType, final String orderid) {
        final BaseResult result = new BaseResult(false);
        serviceTemplate.execute(result, new ServiceCheckCallback() {

            @Override
            public void check() {

            }

            @Override
            public void executeService() {

                if (null == accountDO) {
                    return;
                }

                String userId = accountDO.getLinkId();
                String accountNo = accountDO.getAccountNo();

                Date lastYeatStartDay = DateUtil.getTimeByMounthForFirstDay(-12);
                Date thisEndDay = null;

                String type = "4";
                SysLevelDO levelOne = sysLevelDAO.query(UserLevelEnum.LEVEL_ONE.getCode(), type);
                Money levelOneDevote = new Money();
                if (null != levelOne)
                    levelOneDevote = levelOne.getMinAmount();
                SysLevelDO levelTwo = sysLevelDAO.query(UserLevelEnum.LEVEL_TWO.getCode(), type);
                Money levelTwoDevote = new Money();
                if (null != levelTwo)
                    levelTwoDevote = levelTwo.getMinAmount();
                SysLevelDO levelThree = sysLevelDAO.query(UserLevelEnum.LEVEL_THREE.getCode(), type);
                Money levelThreeDevote = new Money();
                if (null != levelThree)
                    levelThreeDevote = levelThree.getMinAmount();
                SysLevelDO levelFour = sysLevelDAO.query(UserLevelEnum.LEVEL_FOUR.getCode(), type);
                Money levelFourDevote = new Money();
                if (null != levelFour)
                    levelFourDevote = levelFour.getMinAmount();
                SysLevelDO levelFive = sysLevelDAO.query(UserLevelEnum.LEVEL_FIVE.getCode(), type);
                Money levelFiveDevote = new Money();
                if (null != levelFive)
                    levelFiveDevote = levelFive.getMinAmount();

                //过去一年度   增加的贡献值流水
                List<String> accountLogFlgAdd = new ArrayList<String>();
                accountLogFlgAdd.add(AccountLogFlgEnum.REBATE.getCode());
                BigDecimal allDevoteLimitDateAdd = accountLogDAO.queryAllDevoteLimitDateAdd(userId,
                    AccountLogTypeEnum.CONTRIBUTE.getCode(), accountLogFlgAdd, lastYeatStartDay,
                    thisEndDay);

                //过去一年度   减少的贡献值流水
                List<String> accountLogFlgSub = new ArrayList<String>();
                accountLogFlgSub.add(AccountLogFlgEnum.REBATE_RETURN.getCode());
                accountLogFlgSub.add(AccountLogFlgEnum.NOLONGER.getCode());
                BigDecimal allDevoteLimitDateSub = accountLogDAO.queryAllDevoteLimitDateSub(userId,
                    AccountLogTypeEnum.CONTRIBUTE.getCode(), accountLogFlgSub, lastYeatStartDay,
                    thisEndDay);

                //过去一年度   真实的贡献值流水
                BigDecimal allDevoteLimitDate = allDevoteLimitDateAdd
                    .subtract(allDevoteLimitDateSub);

                // 过去一年度健康值总额
                Money devoteMoney = new Money(allDevoteLimitDate.divide(new BigDecimal(100)));
                Money devoteAmount = accountDO.getDevoteAmount();//现有健康值
                if (StringUtils.equals(thisType, "0")) {
                    devoteMoney = devoteMoney.add(thisDevote);
                    devoteAmount = devoteAmount.add(thisDevote);
                } else {
                    devoteMoney = devoteMoney.subtract(thisDevote);
                    devoteAmount = devoteAmount.subtract(thisDevote);
                }
                if (devoteMoney.greaterEqualThan(levelFiveDevote)) {
                    userDAO.updateUserLevel(UserLevelEnum.LEVEL_FIVE.getValue(), userId);
                } else if (devoteMoney.greaterEqualThan(levelFourDevote)) {
                    userDAO.updateUserLevel(UserLevelEnum.LEVEL_FOUR.getValue(), userId);
                } else if (devoteMoney.greaterEqualThan(levelThreeDevote)) {
                    userDAO.updateUserLevel(UserLevelEnum.LEVEL_THREE.getValue(), userId);
                } else if (devoteMoney.greaterEqualThan(levelTwoDevote)) {
                    userDAO.updateUserLevel(UserLevelEnum.LEVEL_TWO.getValue(), userId);
                } else if (devoteMoney.greaterEqualThan(levelOneDevote)) {
                    userDAO.updateUserLevel(UserLevelEnum.LEVEL_ONE.getValue(), userId);
                }

                if (StringUtils.equals(thisType, "0")) {
                    AccountLogDO accountLog = new AccountLogDO();
                    accountLog.setAccountNo(accountDO.getAccountNo());
                    accountLog.setType(AccountLogTypeEnum.CONTRIBUTE.getCode());
                    accountLog.setFlg(AccountLogFlgEnum.REBATE.getCode());
                    accountLog.setAmount(thisDevote);
                    accountLog.setBeforeBalance(accountDO.getDevoteAmount());
                    accountLog.setAfterBalance(accountDO.getDevoteAmount().add(thisDevote));
                    accountLog.setLinkNo(orderid);
                    accountLogDAO.creatNewLog(accountLog);
                } else {
                    AccountLogDO accountLog = new AccountLogDO();
                    accountLog.setAccountNo(accountDO.getAccountNo());
                    accountLog.setType(AccountLogTypeEnum.CONTRIBUTE.getCode());
                    accountLog.setFlg(AccountLogFlgEnum.REBATE_RETURN.getCode());
                    accountLog.setAmount(thisDevote);
                    accountLog.setBeforeBalance(accountDO.getDevoteAmount());
                    accountLog.setAfterBalance(accountDO.getDevoteAmount().subtract(thisDevote));
                    accountLog.setLinkNo(orderid);
                    accountLogDAO.creatNewLog(accountLog);
                }

                //是否有失效流水
                if (devoteMoney.lessThan(devoteAmount)) {
                    AccountLogDO accountLog = new AccountLogDO();
                    accountLog.setAccountNo(accountNo);
                    accountLog.setType(AccountLogTypeEnum.CONTRIBUTE.getCode());
                    accountLog.setFlg(AccountLogFlgEnum.NOLONGER.getCode());
                    accountLog.setAmount(devoteAmount.subtract(devoteMoney));
                    accountLog.setBeforeBalance(devoteAmount);
                    accountLog.setAfterBalance(devoteMoney);
                    accountLogDAO.creatNewLog(accountLog);
                }
                
                accountDAO.updateDevote(devoteMoney, accountNo);
            }

        });
        return result;
    }

}
