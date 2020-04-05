package com.onway.task;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.onway.common.lang.Money;
import com.onway.common.lang.StringUtils;
import com.onway.core.service.localcache.manager.SysConfigCacheManager;
import com.onway.fyapp.common.dal.daointerface.AccountDAO;
import com.onway.fyapp.common.dal.daointerface.AccountLogDAO;
import com.onway.fyapp.common.dal.daointerface.SysLevelDAO;
import com.onway.fyapp.common.dal.daointerface.UserDAO;
import com.onway.fyapp.common.dal.dataobject.AccountDO;
import com.onway.fyapp.common.dal.dataobject.SysLevelDO;
import com.onway.fyapp.common.dal.dataobject.UserDO;
import com.onway.model.enums.AccountLogFlgEnum;
import com.onway.model.enums.AccountLogTypeEnum;
import com.onway.model.enums.SysConfigCacheKeyEnum;
import com.onway.model.enums.UserLevelEnum;
import com.onway.utils.DateUtil;

/**
 * 更新用户等级 每月一号
 * 
 * 会员晋升分V1、V2、V3、V4、V5五个等级，根据会员类别(顾客、门店、代理商)
 * 
 * 分别为五个等级设置不同的健康值范围，顾客为前12个月(或24月)的健康值作为
 * 
 * 当前等级评定标准，门店和代理商将前3个月的健康值作为当前等级评定标准，均
 * 
 * 采取月移动式评定规则，每月(或每季度)1号更新会员等级。举例说明：假设今天
 * 
 * 为2018-06-01，那么顾客的健康值取2017-05-01~2018-04-30的健康值减去2017-
 * 
 * 05-01~2017-05-31的健康值加上2018-05-01~2018-05-31的健康值作为当前等级的
 * 
 * 评定标准。
 */
public class UpdateUserDevoteTask extends AbstractTask {

    private UserDAO               userDAO;

    private AccountDAO            accountDAO;

    private AccountLogDAO         accountLogDAO;

    private SysLevelDAO           sysLevelDAO;

    private SysConfigCacheManager sysConfigCacheManager;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public void setAccountLogDAO(AccountLogDAO accountLogDAO) {
        this.accountLogDAO = accountLogDAO;
    }

    public void setSysLevelDAO(SysLevelDAO sysLevelDAO) {
        this.sysLevelDAO = sysLevelDAO;
    }

    public void setSysConfigCacheManager(SysConfigCacheManager sysConfigCacheManager) {
        this.sysConfigCacheManager = sysConfigCacheManager;
    }

    @Override
    protected boolean canProcess() {
        return false;
    }

    @Override
    protected void process() {
        try {
            // 假设今天为2018-06-01，那么顾客的健康值取2017-05-01~2018-04-30的健康值减去2017-05-01~2017-05-31的健康值加上2018-05-01~2018-05-31的健康值作为当前等级的评定标准。
            // 2017-06-01 ~~~ 2018-05-31
            String LEVEL_USER_DEVOTE_TIME = sysConfigCacheManager
                .getConfigValue(SysConfigCacheKeyEnum.LEVEL_USER_DEVOTE_TIME);
            if (StringUtils.isNotBlank(LEVEL_USER_DEVOTE_TIME)) {

                Date lastYeatStartDay = DateUtil.getTimeByMounthForFirstDay(-Integer
                    .valueOf(LEVEL_USER_DEVOTE_TIME));
                Date thisEndDay = DateUtil.getTimeByMounthForFirstDay(0);

                int pageNum = 1;
                int pageSize = 50;

                int allCount = userDAO.queryAllUserCount();

                String type = "4";
                SysLevelDO levelOne = sysLevelDAO.query(UserLevelEnum.LEVEL_ONE.getCode(), type);
                Money levelOneDevote = new Money();
                if (null != levelOne)
                    levelOneDevote = levelOne.getMinAmount();
                SysLevelDO levelTwo = sysLevelDAO.query(UserLevelEnum.LEVEL_TWO.getCode(), type);
                Money levelTwoDevote = new Money();
                if (null != levelTwo)
                    levelTwoDevote = levelTwo.getMinAmount();
                SysLevelDO levelThree = sysLevelDAO
                    .query(UserLevelEnum.LEVEL_THREE.getCode(), type);
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

                for (int i = 0; i < (allCount / pageSize) + 1; i++) {
                    Integer startRow = (pageNum - 1) * pageSize;
                    List<UserDO> queryAllUser = userDAO.queryAllUser(startRow, pageSize);
                    for (UserDO userDO : queryAllUser) {
                        String userId = userDO.getUserId();
                        //1 用户 2 团队
                        BigDecimal allDevoteLimitDate = accountLogDAO.queryAllDevoteLimitDate(
                            userId, AccountLogTypeEnum.CONTRIBUTE.getCode(),
                            AccountLogFlgEnum.REBATE.getCode(), lastYeatStartDay, thisEndDay);
                        // 过去一年度健康值总额
                        Money devoteMoney = new Money(
                            allDevoteLimitDate.divide(new BigDecimal(100)));
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

                        AccountDO accountDO = accountDAO.selectByLinkIdForUpdate(userId);
                        if (null != accountDO)
                            accountDAO.updateDevote(devoteMoney, accountDO.getAccountNo());
                    }
                    pageNum++;
                }

            }

        } catch (Exception e) {
            logger.error("", e);
        }
    }

}
