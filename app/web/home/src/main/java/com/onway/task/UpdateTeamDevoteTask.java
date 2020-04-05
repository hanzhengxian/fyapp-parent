package com.onway.task;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.onway.common.lang.Money;
import com.onway.common.lang.StringUtils;
import com.onway.core.service.code.CodeGenerateComponent;
import com.onway.core.service.localcache.manager.SysConfigCacheManager;
import com.onway.fyapp.common.dal.daointerface.AccountDAO;
import com.onway.fyapp.common.dal.daointerface.AccountLogDAO;
import com.onway.fyapp.common.dal.daointerface.SysLevelDAO;
import com.onway.fyapp.common.dal.daointerface.TeamDAO;
import com.onway.fyapp.common.dal.dataobject.AccountDO;
import com.onway.fyapp.common.dal.dataobject.AccountLogDO;
import com.onway.fyapp.common.dal.dataobject.SysLevelDO;
import com.onway.fyapp.common.dal.dataobject.TeamDO;
import com.onway.model.enums.AccountLogFlgEnum;
import com.onway.model.enums.AccountLogTypeEnum;
import com.onway.model.enums.AccountTypeEnum;
import com.onway.model.enums.BizTypeEnum;
import com.onway.model.enums.DelFlgEnum;
import com.onway.model.enums.SysConfigCacheKeyEnum;
import com.onway.model.enums.UserLevelEnum;
import com.onway.utils.DateUtil;

/**
 * 更新用户等级  每月一号
 * 
 * 会员晋升分V1、V2、V3、V4、V5五个等级，根据会员类别(顾客、门店、代理商)

分别为五个等级设置不同的健康值范围，顾客为前12个月(或24月)的健康值作为

当前等级评定标准，门店和代理商将前3个月的健康值作为当前等级评定标准，均

采取月移动式评定规则，每月(或每季度)1号更新会员等级。举例说明：假设今天

为2018-06-01，那么顾客的健康值取2017-05-01~2018-04-30的健康值减去2017-

05-01~2017-05-31的健康值加上2018-05-01~2018-05-31的健康值作为当前等级的

评定标准。
 */
public class UpdateTeamDevoteTask extends AbstractTask {

    private TeamDAO               teamDAO;

    private AccountDAO            accountDAO;

    private AccountLogDAO         accountLogDAO;

    private SysLevelDAO           sysLevelDAO;

    private CodeGenerateComponent codeGenerateComponent;

    private SysConfigCacheManager sysConfigCacheManager;

    public void setTeamDAO(TeamDAO teamDAO) {
        this.teamDAO = teamDAO;
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

    public void setCodeGenerateComponent(CodeGenerateComponent codeGenerateComponent) {
        this.codeGenerateComponent = codeGenerateComponent;
    }

    public void setSysConfigCacheManager(SysConfigCacheManager sysConfigCacheManager) {
        this.sysConfigCacheManager = sysConfigCacheManager;
    }

    @Override
    protected boolean canProcess() {
        return true;
    }

    @Override
    protected void process() {
        try {
            //假设今天为2018-06-01，那么顾客的健康值取2017-05-01~2018-04-30的健康值减去2017-05-01~2017-05-31的健康值加上2018-05-01~2018-05-31的健康值作为当前等级的评定标准。
            //2017-06-01  ~~~   2018-05-31
            Date thisEndDay = DateUtil.getTimeByMounthForFirstDay(0);

            //1 门店 2 服务商 3代理商
            String LEVEL_SHOP_DEVOTE_TIME = sysConfigCacheManager
                .getConfigValue(SysConfigCacheKeyEnum.LEVEL_SHOP_DEVOTE_TIME);
            if (StringUtils.isNotBlank(LEVEL_SHOP_DEVOTE_TIME)) {
                Date lastYeatStartDay = DateUtil.getTimeByMounthForFirstDay(-Integer
                    .valueOf(LEVEL_SHOP_DEVOTE_TIME));
                updateTeamDevote("1", lastYeatStartDay, thisEndDay);
            }

            String LEVEL_SERVICE_DEVOTE_TIME = sysConfigCacheManager
                .getConfigValue(SysConfigCacheKeyEnum.LEVEL_SERVICE_DEVOTE_TIME);
            if (StringUtils.isNotBlank(LEVEL_SERVICE_DEVOTE_TIME)) {
                Date lastYeatStartDay = DateUtil.getTimeByMounthForFirstDay(-Integer
                    .valueOf(LEVEL_SERVICE_DEVOTE_TIME));
                updateTeamDevote("2", lastYeatStartDay, thisEndDay);
            }

            String LEVEL_AGENT_DEVOTE_TIME = sysConfigCacheManager
                .getConfigValue(SysConfigCacheKeyEnum.LEVEL_AGENT_DEVOTE_TIME);
            if (StringUtils.isNotBlank(LEVEL_AGENT_DEVOTE_TIME)) {
                Date lastYeatStartDay = DateUtil.getTimeByMounthForFirstDay(-Integer
                    .valueOf(LEVEL_AGENT_DEVOTE_TIME));
                updateTeamDevote("3", lastYeatStartDay, thisEndDay);
            }

        } catch (Exception e) {
            logger.error("", e);
        }
    }

    private void updateTeamDevote(String teamType, Date lastYeatStartDay, Date thisEndDay) {
        int pageNum = 1;
        int pageSize = 50;

        int allCount = teamDAO.queryAllTeamCount(teamType);

        SysLevelDO levelOne = sysLevelDAO.query(UserLevelEnum.LEVEL_ONE.getCode(), teamType);
        Money levelOneDevote = new Money();
        if (null != levelOne)
            levelOneDevote = levelOne.getMinAmount();
        SysLevelDO levelTwo = sysLevelDAO.query(UserLevelEnum.LEVEL_TWO.getCode(), teamType);
        Money levelTwoDevote = new Money();
        if (null != levelTwo)
            levelTwoDevote = levelTwo.getMinAmount();
        SysLevelDO levelThree = sysLevelDAO.query(UserLevelEnum.LEVEL_THREE.getCode(), teamType);
        Money levelThreeDevote = new Money();
        if (null != levelThree)
            levelThreeDevote = levelThree.getMinAmount();
        SysLevelDO levelFour = sysLevelDAO.query(UserLevelEnum.LEVEL_FOUR.getCode(), teamType);
        Money levelFourDevote = new Money();
        if (null != levelFour)
            levelFourDevote = levelFour.getMinAmount();
        SysLevelDO levelFive = sysLevelDAO.query(UserLevelEnum.LEVEL_FIVE.getCode(), teamType);
        Money levelFiveDevote = new Money();
        if (null != levelFive)
            levelFiveDevote = levelFive.getMinAmount();

        for (int i = 0; i < (allCount / pageSize) + 1; i++) {
            Integer startRow = (pageNum - 1) * pageSize;
            List<TeamDO> queryAllTeam = teamDAO.queryAllTeam(teamType, startRow, pageSize);
            for (TeamDO teamDO : queryAllTeam) {
                String teamId = teamDO.getTeamId();
                //1 用户 2 团队
                BigDecimal allDevoteLimitDate = accountLogDAO.queryAllDevoteLimitDate(teamId,
                    AccountLogTypeEnum.CONTRIBUTE.getCode(), AccountLogFlgEnum.REBATE.getCode(),
                    lastYeatStartDay, thisEndDay);
                //过去一年度健康值总额
                Money devoteMoney = new Money(allDevoteLimitDate.divide(new BigDecimal(100)));
                if (devoteMoney.greaterEqualThan(levelFiveDevote)) {
                    teamDAO.updateTeamLevel(UserLevelEnum.LEVEL_FIVE.getValue(), teamId);
                } else if (devoteMoney.greaterEqualThan(levelFourDevote)) {
                    teamDAO.updateTeamLevel(UserLevelEnum.LEVEL_FOUR.getValue(), teamId);
                } else if (devoteMoney.greaterEqualThan(levelThreeDevote)) {
                    teamDAO.updateTeamLevel(UserLevelEnum.LEVEL_THREE.getValue(), teamId);
                } else if (devoteMoney.greaterEqualThan(levelTwoDevote)) {
                    teamDAO.updateTeamLevel(UserLevelEnum.LEVEL_TWO.getValue(), teamId);
                } else if (devoteMoney.greaterEqualThan(levelOneDevote)) {
                    teamDAO.updateTeamLevel(UserLevelEnum.LEVEL_ONE.getValue(), teamId);
                }
                AccountDO accountDO = accountDAO.queryByLinkId(teamId,
                    AccountTypeEnum.TEAM.getCode(), DelFlgEnum.NOT_DEL.getCode());
                if (null != accountDO) {
                    Money devoteAmount = accountDO.getDevoteAmount();
                    if (devoteMoney.lessThan(devoteAmount)) {
                        AccountLogDO accountLog = new AccountLogDO();
                        accountLog.setAccountNo(accountDO.getAccountNo());
                        accountLog.setType(AccountLogTypeEnum.CONTRIBUTE.getCode());
                        accountLog.setFlg(AccountLogFlgEnum.NOLONGER.getCode());
                        accountLog.setAmount(devoteAmount.subtract(devoteMoney));
                        accountLog.setBeforeBalance(devoteAmount);
                        accountLog.setAfterBalance(devoteMoney);
                        accountLogDAO.insert(accountLog);
                    }
                    accountDAO.updateDevote(devoteMoney, accountDO.getAccountNo());
                } else {
                    AccountDO account = new AccountDO();
                    account.setAccountNo(codeGenerateComponent.nextCodeByType(BizTypeEnum.TEAM_NO));
                    account.setLinkId(teamId);
                    account.setType(AccountTypeEnum.TEAM.getCode());
                    account.setDelFlg(DelFlgEnum.NOT_DEL.getCode());
                    account.setDevoteAmount(devoteMoney);
                    accountDAO.creat(account);
                }
            }
            pageNum++;
        }
    }

}
