package com.onway.core.service.impl;

import java.math.BigDecimal;
import java.security.SecureRandom;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.onway.common.lang.Money;
import com.onway.common.lang.StringUtils;
import com.onway.core.service.AccountTransComponent;
import com.onway.core.service.code.CodeGenerateComponent;
import com.onway.fyapp.common.dal.daointerface.AccountDAO;
import com.onway.fyapp.common.dal.daointerface.AccountLogDAO;
import com.onway.fyapp.common.dal.daointerface.OrderDAO;
import com.onway.fyapp.common.dal.daointerface.UserDAO;
import com.onway.fyapp.common.dal.dataobject.AccountDO;
import com.onway.fyapp.common.dal.dataobject.AccountLogDO;
import com.onway.fyapp.common.dal.dataobject.OrderDO;
import com.onway.fyapp.common.dal.dataobject.OrderReturnDO;
import com.onway.fyapp.common.dal.dataobject.UserDO;
import com.onway.model.enums.BizTypeEnum;
import com.onway.platform.common.base.BaseResult;
import com.onway.platform.common.service.template.ServiceCheckCallback;
import com.onway.platform.common.service.template.ServiceTemplate;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.utils.BigdecimalUtil;

/**
 * 
 * 退款相关
 * 
 * @author weina.chen
 * @version $Id: AccountTransComponentImpl.java, v 0.1 2018年7月27日 下午2:47:49 ROG
 *          Exp $
 */
public class AccountTransComponentImpl implements AccountTransComponent {

    private static final Logger logger = Logger.getLogger(AccountTransComponentImpl.class);

    private AccountDAO          accountDAO;

    private AccountLogDAO       accountLogDAO;

    private OrderDAO            orderDAO;

    private UserDAO             userDAO;

    private ServiceTemplate     serviceTemplate;

    private WechatComponent     wechatComponent;

    private AlipayComponent     alipayComponent;
    
    private CodeGenerateComponent codeGenerateComponent;

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public void setAccountLogDAO(AccountLogDAO accountLogDAO) {
        this.accountLogDAO = accountLogDAO;
    }

    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setServiceTemplate(ServiceTemplate serviceTemplate) {
        this.serviceTemplate = serviceTemplate;
    }

    public void setWechatComponent(WechatComponent wechatComponent) {
        this.wechatComponent = wechatComponent;
    }

    public void setAlipayComponent(AlipayComponent alipayComponent) {
        this.alipayComponent = alipayComponent;
    }

    public void setCodeGenerateComponent(CodeGenerateComponent codeGenerateComponent) {
        this.codeGenerateComponent = codeGenerateComponent;
    }

    @Override
    public BaseResult tuiKuanForAccount(final String userId, final Money amt, final String type,
                                        final String childOrderId, final String user_id) {

        final BaseResult result = new BaseResult(true);
        serviceTemplate.execute(result, new ServiceCheckCallback() {

            @Override
            public void executeService() {
                if (amt.lessEqualThan(new Money(0))) {
                    throw new RuntimeException("不合法的金额数目");
                }

                if (!StringUtils.equalsAny(type, new String[] { "2", "3" })) {
                    throw new RuntimeException("不合法的类型");
                }

                AccountDO lockAccountDO = accountDAO.selectByLinkIdForUpdate(userId);
                if (null == lockAccountDO) {
                    throw new RuntimeException("账户不存在");
                }
                // (2 胡币 3 积分)
                if (StringUtils.equals(type, "2")) {
                    if (0 >= accountDAO.updateHuBal(lockAccountDO.getHuBalance().add(amt),
                        lockAccountDO.getAccountNo())) {
                        throw new RuntimeException("转账异常");
                    }
                } else if (StringUtils.equals(type, "3")) {
                    if (0 >= accountDAO.updateHuPoint(lockAccountDO.getHuPoint().add(amt),
                        lockAccountDO.getAccountNo())) {
                        throw new RuntimeException("转账异常");
                    }
                }

                AccountLogDO accountLog = new AccountLogDO();
                accountLog.setAccountNo(lockAccountDO.getAccountNo());
                accountLog.setType(type);
                accountLog.setFlg("5");
                accountLog.setAmount(amt);
                if (StringUtils.equals(type, "2")) {
                    accountLog.setBeforeBalance(lockAccountDO.getHuBalance());
                    accountLog.setAfterBalance(lockAccountDO.getHuBalance().add(amt));
                } else if (StringUtils.equals(type, "3")) {
                    accountLog.setBeforeBalance(lockAccountDO.getHuPoint().add(
                        lockAccountDO.getRePoint()));
                    accountLog.setAfterBalance(lockAccountDO.getHuPoint()
                        .add(lockAccountDO.getRePoint()).add(amt));
                }
                accountLog.setDelFlg("0");
                if (0 >= accountLogDAO.insert(accountLog)) {
                    throw new RuntimeException("添加转账流水异常");
                }

                //                if (0 >= orderDAO.updateOrderStatByChildId("8", user_id, childOrderId)) {
                //                    throw new RuntimeException("订单状态更新异常");
                //                }
            }

            @Override
            public void check() {
                AssertUtil.notBlank(userId, "用户编号不能为空");
                AssertUtil.notNull(amt, "操作金额不能为空");
                AssertUtil.notBlank(type, "类型不能为空");
                AssertUtil.notBlank(childOrderId, "订单编号不能为空");
            }
        });
        return result;
    }

    @Override
    public BaseResult tuiKuanForZFB(final OrderDO orderDO, final OrderReturnDO orderReturnDO) {
        final BaseResult result = new BaseResult(false);
        serviceTemplate.execute(result, new ServiceCheckCallback() {

            @Override
            public void executeService() {

                if (orderReturnDO.getShouldReturnMoney().lessEqualThan(new Money(0))) {
                    result.setSuccess(false);
                    result.setMessage("不合法的金额数目");
                    return;
                }

                AlipayTradeRefundResponse response = null;
                try {
                    response = alipayComponent.orderRefund(orderDO, orderReturnDO);
                } catch (AlipayApiException e) {
                    logger.error("支付宝退款调用异常", e);
                    result.setSuccess(false);
                    result.setMessage("支付宝退款调用异常");
                    return;
                }

                // 10000:成功 20000：异常
                if (StringUtils.equals(response.getCode(), "10000")) {
                    result.setSuccess(true);
                    result.setMessage(response.getMsg());
                } else {
                    result.setSuccess(false);
                    result.setMessage(response.getSubMsg());
                }
            }

            @Override
            public void check() {
                AssertUtil.notNull(orderDO, "订单信息不能为空");
                AssertUtil.notNull(orderReturnDO, "退款订单信息不能为空");
            }
        });
        return result;
    }

    @Override
    public BaseResult tuiKuanForWeChat(final String userId, final Money amt,
                                       final String childOrderId, final HttpServletRequest request,
                                       final String user_id) {
        final BaseResult result = new BaseResult(true);
        serviceTemplate.execute(result, new ServiceCheckCallback() {

            @Override
            public void executeService() {

                if (amt.lessEqualThan(new Money(0))) {
                    throw new RuntimeException("不合法的金额数目");
                }

                if (0 >= orderDAO.updateOrderStatByChildId("8", user_id, childOrderId)) {
                    throw new RuntimeException("订单状态更新异常");
                }

                UserDO userDO = userDAO.selectByUserId(userId);

                String orderNo = childOrderId + "TH" + getSafeCode(4);

                BaseResult baseResult = wechatComponent.withdrawByWechat(orderNo, amt,
                    userDO.getOpenId(), request);
                if (!baseResult.isSuccess()) {
                    result.setMessage(baseResult.getMessage());
                    result.setSuccess(false);
                }

            }

            @Override
            public void check() {
                AssertUtil.notBlank(userId, "用户编号不能为空");
                AssertUtil.notNull(amt, "操作金额不能为空");
                AssertUtil.notBlank(childOrderId, "订单编号不能为空");
            }
        });
        return result;
    }

    @Override
    public BaseResult tuiKuanForWeChatS(final OrderDO orderDO, final Money refundMoney,
                                        final int linkReturnId) {
        final BaseResult result = new BaseResult(true);
        serviceTemplate.execute(result, new ServiceCheckCallback() {

            @Override
            public void executeService() {

                if (refundMoney.lessEqualThan(new Money(0))) {
                    result.setSuccess(false);
                    result.setMessage("不合法的金额数目");
                    return;
                }

                BigDecimal sumOrderAmount = orderDAO.sumOrderAmount(orderDO.getOrderId());
                Money money = BigdecimalUtil.toMoney(sumOrderAmount);

                String refundNo = codeGenerateComponent.nextCodeByType(BizTypeEnum.REFUND_NO);

                BaseResult refundByWechat = wechatComponent.refundByWechat(orderDO.getOrderId(),
                    orderDO.getChildOrderId(), refundNo, money, refundMoney, linkReturnId);

                result.setMessage(refundByWechat.getMessage());
                result.setSuccess(refundByWechat.isSuccess());
            }

            @Override
            public void check() {

            }
        });
        return result;
    }

    public static String getSafeCode(int length) {
        if (length < 1 || length > 10) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= length; i++) {
            int random = new SecureRandom().nextInt(10);
            sb.append(random);
        }
        return sb.toString();
    }
}
