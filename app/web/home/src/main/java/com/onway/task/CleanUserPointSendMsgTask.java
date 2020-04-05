package com.onway.task;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.onway.common.lang.Money;
import com.onway.core.service.MsgService;
import com.onway.fyapp.common.dal.daointerface.AccountDAO;
import com.onway.fyapp.common.dal.daointerface.AccountLogDAO;
import com.onway.fyapp.common.dal.daointerface.UserDAO;
import com.onway.fyapp.common.dal.dataobject.AccountDO;
import com.onway.fyapp.common.dal.dataobject.UserDO;
import com.onway.model.enums.AccountLogFlgEnum;
import com.onway.model.enums.AccountLogTypeEnum;
import com.onway.model.enums.AccountTypeEnum;
import com.onway.model.enums.DelFlgEnum;
import com.onway.model.enums.MsgTypeEnum;
import com.onway.utils.BigdecimalUtil;
import com.onway.utils.DateUtil;

/**
 * 每年5月一号下午一点触发  清理积分通知
 * 查询  前一年 7月1号0点到 今年1月1号零点的积分总流水
 * @author yuhang.ni
 * @version $Id: CleanUserPointTask.java, v 0.1 2019年1月10日 下午2:19:19 Administrator Exp $
 */
public class CleanUserPointSendMsgTask extends AbstractTask {

    private AccountDAO    accountDAO;

    private AccountLogDAO accountLogDAO;

    private UserDAO       userDAO;

    private MsgService    msgService;

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public void setAccountLogDAO(AccountLogDAO accountLogDAO) {
        this.accountLogDAO = accountLogDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setMsgService(MsgService msgService) {
        this.msgService = msgService;
    }

    @Override
    protected boolean canProcess() {
        return true;
    }

    @Override
    protected void process() {
        try {
            Date firstDayByYear = DateUtil.getFirstDayByYear();
            Date middleDayByYear = DateUtil.getMiddleDayByYear();

            int pageNum = 1;
            int pageSize = 50;

            String linkId = null;
            int allCount = accountDAO.queryAllAccountCount(linkId, AccountTypeEnum.USER.getCode(),
                DelFlgEnum.NOT_DEL.getCode());

            String accountLogType = AccountLogTypeEnum.POINT.getCode();

            //积分全部flg
            List<String> accountLogFlgAll = new ArrayList<String>();
            accountLogFlgAll.add(AccountLogFlgEnum.COST.getCode());
            accountLogFlgAll.add(AccountLogFlgEnum.COST_RETURN.getCode());
            accountLogFlgAll.add(AccountLogFlgEnum.REBATE.getCode());
            accountLogFlgAll.add(AccountLogFlgEnum.REBATE_RETURN.getCode());
            accountLogFlgAll.add(AccountLogFlgEnum.SHOPPING.getCode());
            accountLogFlgAll.add(AccountLogFlgEnum.RECEIVE.getCode());
            accountLogFlgAll.add(AccountLogFlgEnum.RETURN.getCode());
            accountLogFlgAll.add(AccountLogFlgEnum.EXCHANGE.getCode());
            accountLogFlgAll.add(AccountLogFlgEnum.GETRECEIVE.getCode());
            accountLogFlgAll.add(AccountLogFlgEnum.SHARE.getCode());

            //积分扣减flg
            List<String> accountLogFlgOut = new ArrayList<String>();
            accountLogFlgOut.add(AccountLogFlgEnum.COST.getCode());
            accountLogFlgOut.add(AccountLogFlgEnum.REBATE_RETURN.getCode());

            for (int i = 0; i < (allCount / pageSize) + 1; i++) {
                Integer startRow = (pageNum - 1) * pageSize;

                List<AccountDO> queryAllAccount = accountDAO.queryAllAccount(linkId,
                    AccountTypeEnum.USER.getCode(), DelFlgEnum.NOT_DEL.getCode(), startRow,
                    pageSize);
                for (AccountDO accountDO : queryAllAccount) {
                    String accountNo = accountDO.getAccountNo();

                    //当前剩余积分
                    Money nowHasPoint = accountDO.getHuPoint();//300   0
                    //1-6月总积分流水
                    BigDecimal allPoint = accountLogDAO.queryAllPointLimitDate(accountNo,
                        accountLogType, accountLogFlgAll, firstDayByYear, middleDayByYear);

                    Money allPointMoney = BigdecimalUtil.toMoney(allPoint);//500

                    if (allPointMoney.lessEqualThan(new Money(0)))
                        continue;

                    //1-6月总扣减流水
                    BigDecimal outPoint = accountLogDAO.queryAllPointLimitDate(accountNo,
                        accountLogType, accountLogFlgOut, firstDayByYear, middleDayByYear);

                    Money outPointMoney = BigdecimalUtil.toMoney(outPoint);//100      400

                    //1-6月总积分流水   - 1-6月总支出流水    
                    Money cleanAfterPoint = allPointMoney.subtract(outPointMoney);//400     100

                    //取  nowHasPoint  与  cleanAfterPoint 的最小值
                    Money realAfterPoint = new Money(0);
                    if (nowHasPoint.lessEqualThan(cleanAfterPoint)) {
                        realAfterPoint = nowHasPoint;
                    } else {
                        realAfterPoint = cleanAfterPoint;
                    }

                    if (realAfterPoint.lessThan(new Money(0))) {
                        realAfterPoint = new Money(0);
                    }

                    Money realOutPoint = nowHasPoint.subtract(realAfterPoint);
                    if (realOutPoint.lessEqualThan(new Money(0))) {
                        continue;
                    }

                    String userId = accountDO.getLinkId();
                    UserDO orderDO = userDAO.selectByUserId(userId);
                    if (null != orderDO) {
                        //微信通知
                        String msgContent = "您有" + realOutPoint.toSimpleString() + "积分值，将于7月1日零点清零";
                        msgService.newMsg(orderDO.getUserId(), MsgTypeEnum.SYS_NOTICE.getCode(),
                            "积分清零通知", msgContent);
                        msgService.sendWechatMsg(orderDO.getOpenId(), msgContent);
                    }
                }

                pageNum++;
            }

        } catch (Exception e) {
            logger.error("", e);
        }
    }

}
