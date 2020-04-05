package com.onway.web.controller.mng;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.common.lang.DateUtils;
import com.onway.common.lang.Money;
import com.onway.common.lang.StringUtils;
import com.onway.model.enums.AccountLogFlgEnum;
import com.onway.model.enums.AccountLogTypeEnum;
import com.onway.model.enums.TeamLevelEnum;
import com.onway.model.enums.UserLevelEnum;
import com.onway.utils.BigdecimalUtil;
import com.onway.utils.DateUtil;
import com.onway.web.controller.AbstractController;

@Controller
public class AccountController extends AbstractController {

    /**
     * 获取用户流水记录
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/selectUserAccountList.do")
    @ResponseBody
    public JSONObject selectUserAccountList(HttpServletRequest request, Integer offset,
                                            Integer limit) {

        String nickName = request.getParameter("nickName");
        String cell = request.getParameter("cell");
        String userId = request.getParameter("userId");
        String logType = request.getParameter("logType");
        String logFlg = request.getParameter("logFlg");
        String accountNo = request.getParameter("accountNo");
        String startTime = request.getParameter("startDate");
        String endTime = request.getParameter("endDate");
        JSONObject data = new JSONObject();
        List<Map<String, Object>> queryList = null;
        Date startDate = null;
        Date endDate = null;
        try {
            if (StringUtils.hasLength(startTime)) {
                startDate = DateUtils.parseDate(startTime, DateUtils.webFormat);
            }

            if (StringUtils.hasLength(endTime)) {
                endDate = DateUtils.parseDate(endTime, DateUtils.webFormat);
                endDate = DateUtil.addDays(endDate, 1);
            }
            queryList = accountLogDAO.selectUserAccountList(userId, cell, nickName, logType,
                logFlg, accountNo, startDate, endDate, offset, limit);
        } catch (Exception e) {
            logger.error(MessageFormat.format("获取用户流水记录异常", new Object[] {}));
        }
        for (Map<String, Object> map : queryList) {

            String type = (String) map.get("TYPE");
            map.put("TYPE_SHOW", AccountLogTypeEnum.getByCode(type).message());

            String flg = (String) map.get("FLG");
            map.put("FLG_SHOW", AccountLogFlgEnum.getByCode(flg).message());

            BigDecimal amount = (BigDecimal) map.get("AMOUNT");
            String IO = "+";
            if (StringUtils.equals(flg, AccountLogFlgEnum.COST.getCode())
                || StringUtils.equals(flg, AccountLogFlgEnum.REBATE_RETURN.getCode())
                || StringUtils.equals(flg, AccountLogFlgEnum.PUTFORWARD.getCode())
                || StringUtils.equals(flg, AccountLogFlgEnum.OUT_TIME.getCode())
                || StringUtils.equals(flg, AccountLogFlgEnum.NOLONGER.getCode())) {
                IO = "-";
            }
            map.put("AMOUNT", IO + new Money(amount).divide(100).toSimpleString());

            BigDecimal beAmount = (BigDecimal) map.get("BEFORE_BALANCE");
            map.put("BEFORE_BALANCE", new Money(beAmount).divide(100).toSimpleString());

            BigDecimal afAmount = (BigDecimal) map.get("AFTER_BALANCE");
            map.put("AFTER_BALANCE", new Money(afAmount).divide(100).toSimpleString());

            Integer userLevel = MapUtils.getInteger(map, "USER_LEVEL");
            UserLevelEnum userLevelEnum = UserLevelEnum.getByValue(userLevel);
            if (null != userLevelEnum)
                map.put("USER_LEVEL", userLevelEnum.message());

            BigDecimal devote = (BigDecimal) map.get("DEVOTE_AMOUNT");
            map.put("DEVOTE_AMOUNT", BigdecimalUtil.toMoney(devote).toSimpleString());

            BigDecimal hu_point = (BigDecimal) map.get("HU_POINT");
            BigDecimal re_point = (BigDecimal) map.get("RE_POINT");
            map.put("POINT", BigdecimalUtil.toMoney(hu_point).toSimpleString());
            map.put("RE_POINT", BigdecimalUtil.toMoney(re_point).toSimpleString());

        }
        int count = accountLogDAO.selectUserAccountListCount(userId, cell, nickName, logType,
            logFlg, accountNo, startDate, endDate);
        data.put("rows", queryList);
        data.put("total", count);
        return data;
    }

    /**
     * 获取团队流水记录
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/selectTeamAccountList.do")
    @ResponseBody
    public JSONObject selectTeamAccountList(HttpServletRequest request, Integer offset,
                                            Integer limit) {

        String teamName = request.getParameter("teamName");
        String cell = request.getParameter("cell");
        String teamId = request.getParameter("teamId");
        String logType = request.getParameter("logType");
        String logFlg = request.getParameter("logFlg");
        String erpNo = request.getParameter("erpNo");
        String startTime = request.getParameter("startDate");
        String endTime = request.getParameter("endDate");
        JSONObject data = new JSONObject();
        Date startDate = null;
        Date endDate = null;
        try {
            if (StringUtils.hasLength(startTime)) {
                startDate = DateUtils.parseDate(startTime, DateUtils.webFormat);
            }

            if (StringUtils.hasLength(endTime)) {
                endDate = DateUtils.parseDate(endTime, DateUtils.webFormat);
                endDate = DateUtil.addDays(endDate, 1);
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("获取团队流水记录异常", new Object[] {}));
        }

        List<Map<String, Object>> queryList = accountLogDAO.selectTeamAccountList(teamId, cell,
            teamName, logType, logFlg, erpNo, startDate, endDate, offset, limit);
        for (Map<String, Object> map : queryList) {

            String type = (String) map.get("TYPE");
            map.put("TYPE_SHOW", AccountLogTypeEnum.getByCode(type).message());

            String flg = (String) map.get("FLG");
            map.put("FLG_SHOW", AccountLogFlgEnum.getByCode(flg).message());

            BigDecimal amount = (BigDecimal) map.get("AMOUNT");
            String IO = "+";
            if (StringUtils.equals(flg, AccountLogFlgEnum.COST.getCode())
                || StringUtils.equals(flg, AccountLogFlgEnum.REBATE_RETURN.getCode())
                || StringUtils.equals(flg, AccountLogFlgEnum.PUTFORWARD.getCode())
                || StringUtils.equals(flg, AccountLogFlgEnum.OUT_TIME.getCode())
                || StringUtils.equals(flg, AccountLogFlgEnum.NOLONGER.getCode())) {
                IO = "-";
            }
            map.put("AMOUNT", IO + new Money(amount).divide(100).toSimpleString());

            BigDecimal beAmount = (BigDecimal) map.get("BEFORE_BALANCE");
            map.put("BEFORE_BALANCE", new Money(beAmount).divide(100).toSimpleString());

            BigDecimal afAmount = (BigDecimal) map.get("AFTER_BALANCE");
            map.put("AFTER_BALANCE", new Money(afAmount).divide(100).toSimpleString());

            Integer teamLevel = MapUtils.getInteger(map, "TEAM_LEVEL");
            TeamLevelEnum teamLevelEnum = TeamLevelEnum.getByValue(teamLevel);
            if (null != teamLevelEnum)
                map.put("TEAM_LEVEL", teamLevelEnum.message());

            BigDecimal devote = (BigDecimal) map.get("DEVOTE_AMOUNT");
            map.put("DEVOTE_AMOUNT", BigdecimalUtil.toMoney(devote).toSimpleString());

            BigDecimal hu_point = (BigDecimal) map.get("HU_POINT");
            BigDecimal re_point = (BigDecimal) map.get("RE_POINT");
            map.put("POINT", BigdecimalUtil.toMoney(hu_point).toSimpleString());
            map.put("RE_POINT", BigdecimalUtil.toMoney(re_point).toSimpleString());
        }

        data.put("rows", queryList);
        data.put("total", accountLogDAO.selectTeamAccountListCount(teamId, cell, teamName, logType,
            logFlg, erpNo, startDate, endDate));
        return data;
    }
}
