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
import com.onway.fyapp.common.dal.dataobject.GoodsAttrDO;
import com.onway.fyapp.common.dal.dataobject.UserDO;
import com.onway.model.enums.AccountLogFlgEnum;
import com.onway.model.enums.AccountLogTypeEnum;
import com.onway.model.enums.UserLevelEnum;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.result.JsonResult;
import com.onway.utils.DateUtil;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;

/**
 * 用户相关
 * 
 * @author Administrator
 *
 */
@Controller
public class UserController extends AbstractController {

    /**
     * 分页查询用户信息列表
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/selectuser.do")
    @ResponseBody
    public JSONObject selectuser(HttpServletRequest request, Integer offset, Integer limit) {
        String userId = request.getParameter("userId");
        String nickName = request.getParameter("nickName");
        String realName = request.getParameter("realName");
        String cell = request.getParameter("selectcell");
        String sex = request.getParameter("selectsex");
        String userLevel = request.getParameter("userLevel");
        String delFlg = request.getParameter("delFlg");
        String startTime = request.getParameter("startDate");
        String endTime = request.getParameter("endDate");
        String recommendUserCell = request.getParameter("recommendUserCell");
        String recommendUserId = request.getParameter("recommendUserId");
        String recommendNickName = request.getParameter("recommendNickName");
        String recommendRealName = request.getParameter("recommendRealName");
        String erpNo = request.getParameter("erpNo");
        JSONObject data = new JSONObject();
        try {
            Date startDate = null;
            Date endDate = null;
            if (StringUtils.hasLength(startTime)) {
                startDate = DateUtils.parseDate(startTime, "yyyy-MM-dd");
            }
            if (StringUtils.hasLength(endTime)) {
                endDate = DateUtils.parseDate(endTime, "yyyy-MM-dd");
                endDate = DateUtil.addDays(endDate, 1);
            }
            List<Map<String, Object>> queryList = userDAO.selectUserList(nickName, realName, cell,
                sex, userLevel, delFlg, startDate, endDate, recommendUserCell, userId,
                recommendUserId, recommendNickName, recommendRealName, erpNo, offset, limit);
            for (Map<String, Object> map : queryList) {
                Integer user_Level = MapUtils.getInteger(map, "userLevel");
                UserLevelEnum userLevelEnum = UserLevelEnum.getByValue(user_Level);
                if (null != userLevelEnum)
                    map.put("userLevel_Str", userLevelEnum.message());

                Date birthday = (Date) map.get("birthday");
                map.put("birthday", DateUtil.dateToString(birthday, DateUtil.webFormat));
            }
            data.put("rows", queryList);
            data.put("total", userDAO.queryListCount(nickName, realName, cell, sex, userLevel,
                delFlg, startDate, endDate, recommendUserCell, userId, recommendUserId,
                recommendNickName, recommendRealName, erpNo));
        } catch (Exception e) {
            logger.error(MessageFormat.format("查询用户异常", new Object[] {}));
            // throw new BaseRuntimeException(ErrorCode.QUERY_EEOR,
            // ErrorCode.QUERY_EEOR.getDesc());
        }
        return data;
    }

    /**
     * 查询用户流水信息
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/selectAccountLog.do")
    @ResponseBody
    public JSONObject selectAccountLog(HttpServletRequest request, Integer offset, Integer limit) {
        String userId = request.getParameter("userId");
        String logType = request.getParameter("logType");
        String logFlg = request.getParameter("logFlg");
        String startTime = request.getParameter("startDate");
        String endTime = request.getParameter("endDate");
        JSONObject data = new JSONObject();
        try {
            Date startDate = null;
            Date endDate = null;

            if (StringUtils.hasLength(startTime)) {
                startDate = DateUtils.parseDate(startTime, DateUtils.webFormat);
            }

            if (StringUtils.hasLength(endTime)) {
                endDate = DateUtils.parseDate(endTime, DateUtils.webFormat);
                endDate = DateUtil.addDays(endDate, 1);
            }

            List<Map<String, Object>> queryList = accountLogDAO.selectUserAccountList(userId, null,
                null, logType, logFlg, null, startDate, endDate, offset, limit);
            for (Map<String, Object> map : queryList) {

                BigDecimal beAmount = (BigDecimal) map.get("BEFORE_BALANCE");
                map.put("BEFORE_BALANCE", new Money(beAmount).divide(100).toSimpleString());

                BigDecimal afAmount = (BigDecimal) map.get("AFTER_BALANCE");
                map.put("AFTER_BALANCE", new Money(afAmount).divide(100).toSimpleString());

                String type = (String) map.get("TYPE");
                map.put("TYPE_SHOW", AccountLogTypeEnum.getByCode(type).message());

                String flg = (String) map.get("FLG");
                map.put("FLG_SHOW", AccountLogFlgEnum.getByCode(flg).message());

                String IO = "+";
                if (StringUtils.equals(flg, AccountLogFlgEnum.COST.getCode())
                    || StringUtils.equals(flg, AccountLogFlgEnum.REBATE_RETURN.getCode())
                    || StringUtils.equals(flg, AccountLogFlgEnum.PUTFORWARD.getCode())
                    || StringUtils.equals(flg, AccountLogFlgEnum.OUT_TIME.getCode())
                    || StringUtils.equals(flg, AccountLogFlgEnum.NOLONGER.getCode())) {
                    IO = "-";
                }
                BigDecimal amount = (BigDecimal) map.get("AMOUNT");
                map.put("AMOUNT", IO + new Money(amount).divide(100).toSimpleString());
            }
            data.put("rows", queryList);
            data.put("total", accountLogDAO.selectUserAccountListCount(userId, null, null, logType,
                logFlg, null, startDate, endDate));
        } catch (Exception e) {
            logger.error(MessageFormat.format("获取用户流水记录异常", new Object[] {}));
        }
        return data;
    }

    @RequestMapping("/modifyUserStatus.do")
    @ResponseBody
    public Object modifyUserStatus(final HttpServletRequest request, final GoodsAttrDO goodsAttr) {

        final JsonResult data = new JsonResult(false);

        final String delFlg = request.getParameter("delFlg");
        final String userId = request.getParameter("userId");

        controllerTemplate.execute(data, new ControllerCallBack() {

            @Override
            public void executeService() {
                int modifyUserStatus = userDAO.modifyUserStatus(delFlg, userId);
                if (modifyUserStatus > 0) {
                    data.setSuccess(true);
                }
            }

            @Override
            public void check() {
            }
        });
        return data;
    }

    @RequestMapping("/updateUserRealName.do")
    @ResponseBody
    public Object updateUserRealName(final HttpServletRequest request) {

        final JsonResult data = new JsonResult(false);

        final String userId = request.getParameter("userId");
        final String realName = request.getParameter("realName");
        final String erpNo = request.getParameter("erpNo");

        controllerTemplate.execute(data, new ControllerCallBack() {

            @Override
            public void executeService() {

                UserDO userDO = userDAO.selectByUserId(userId);
                if (null == userDO) {
                    data.setMessage("用户信息查询异常");
                    return;
                }

                if (StringUtils.isNotBlank(erpNo)) {
                    userDO.setErpNo(erpNo);
                } else {
                    userDO.setErpNo(null);
                }
                userDO.setRealName(realName);

                int updateUserRealName = userDAO.updateUserRealName(userDO);
                if (updateUserRealName > 0) {
                    data.setSuccess(true);
                }
            }

            @Override
            public void check() {
                AssertUtil.notBlank(userId, "用户ID为空");
                AssertUtil.notBlank(realName, "用户姓名为空");
            }
        });
        return data;
    }
}
