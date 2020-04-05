package com.onway.web.controller.withdraw;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.onway.common.lang.CollectionUtils;
import com.onway.common.lang.StringUtils;
import com.onway.core.service.Des3;
import com.onway.fyapp.common.dal.dataobject.AccountDO;
import com.onway.fyapp.common.dal.dataobject.AccountLogDO;
import com.onway.fyapp.common.dal.dataobject.UserAliDO;
import com.onway.fyapp.common.dal.dataobject.WithdrawDO;
import com.onway.model.enums.AccountLogFlgEnum;
import com.onway.model.enums.AccountLogTypeEnum;
import com.onway.model.enums.DelFlgEnum;
import com.onway.model.enums.PayTypeEnum;
import com.onway.model.enums.WithDrawFromEnum;
import com.onway.model.enums.WithdrawStatusEnum;
import com.onway.platform.common.base.BaseResult;
import com.onway.platform.common.exception.BaseRuntimeException;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.result.JsonResult;
import com.onway.utils.DateUtil;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;

/**
 * 提现控制类
 */
@Controller
public class WithdrawalController extends AbstractController {

    /**
     * 查询个人用户提现记录
     * 
     * @param cell
     * @param nickName
     * @param status
     * @param withDrawType
     * @param startDate
     * @param endDate
     * @param offset
     * @return
     */
    @RequestMapping("/queryAllUserWithdrawList.do")
    @ResponseBody
    public JSONObject selectwithdrawal(String cell, String nickName, String status,
                                       String withDrawType, String startDate, String endDate,
                                       String realName, Integer offset, Integer limit) {

        JSONObject jsonObject = new JSONObject();
        Date startTime = DateUtil.stringToDate(startDate, DateUtil.webFormat);
        Date endTime = DateUtil.stringToDate(endDate, DateUtil.webFormat);

        if (null != endTime)
            endTime = DateUtil.addDays(endTime, 1);

        List<Map<String, Object>> queryAllUserWithdrawList = withdrawDAO.queryAllUserWithdrawList(
            cell, nickName, status, withDrawType, startTime, endTime, realName, offset, limit);
        for (Map<String, Object> map : queryAllUserWithdrawList) {

            BigDecimal amount = (BigDecimal) map.get("AMOUNT");
            if (null != amount)
                map.put("AMOUNT", amount.divide(new BigDecimal(100)));

            BigDecimal huAmount = (BigDecimal) map.get("HU_AMOUNT");
            if (null != huAmount)
                map.put("HU_AMOUNT", huAmount.divide(new BigDecimal(100)));

            String withStatus = (String) map.get("STATUS");
            map.put("WITHDRAW_STATUS", withStatus);
            WithdrawStatusEnum withStatusCode = WithdrawStatusEnum.getByCode(withStatus);
            if (null != withStatusCode)
                map.put("STATUS_STR", withStatusCode.message());

            String withType = (String) map.get("WITHDRAW_TYPE");
            PayTypeEnum withTypeCode = PayTypeEnum.getByCode(withType);
            if (null != withTypeCode)
                map.put("WITHDRAW_TYPE_STR", withTypeCode.message());

            String withFrom = (String) map.get("WITHDRAW_FROM");
            WithDrawFromEnum withFromCode = WithDrawFromEnum.getByCode(withFrom);
            if (null != withFromCode)
                map.put("WITHDRAW_FROM", withFromCode.message());

            String al_account = MapUtils.getString(map, "al_account");
            if (StringUtils.isNotBlank(al_account)) {
                map.put("al_account", Des3.decode(al_account));
            }

            String VOUCEHR_IMG = MapUtils.getString(map, "VOUCEHR_IMG");
            List<String> voucherImg = JSONArray.parseArray(VOUCEHR_IMG, String.class);
            if (CollectionUtils.isNotEmpty(voucherImg))
                map.put("voucherImg", voucherImg);
            else
                map.put("voucherImg", null);
        }

        jsonObject.put("rows", queryAllUserWithdrawList);
        jsonObject.put("total", withdrawDAO.queryAllUserWithdrawListCount(cell, nickName, status,
            withDrawType, startTime, endTime, realName));

        return jsonObject;
    }

    /**
     * 团队提现记录
     * @param teamName
     * @param teamType
     * @param status
     * @param withDrawType
     * @param startDate
     * @param endDate
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/queryAllTeamWithdrawList.do")
    @ResponseBody
    public JSONObject queryAllTeamWithdrawList(String teamName, String teamType, String status,
                                               String withDrawType, String startDate,
                                               String endDate, Integer offset, Integer limit) {

        JSONObject jsonObject = new JSONObject();
        Date startTime = DateUtil.stringToDate(startDate, DateUtil.webFormat);
        Date endTime = DateUtil.stringToDate(endDate, DateUtil.webFormat);

        if (null != endTime)
            endTime = DateUtil.addDays(endTime, 1);

        List<Map<String, Object>> queryAllUserWithdrawList = withdrawDAO.queryAllTeamWithdrawList(
            teamName, teamType, status, withDrawType, startTime, endTime, offset, limit);
        for (Map<String, Object> map : queryAllUserWithdrawList) {

            BigDecimal amount = (BigDecimal) map.get("AMOUNT");
            if (null != amount)
                map.put("AMOUNT", amount.divide(new BigDecimal(100)));

            BigDecimal huAmount = (BigDecimal) map.get("HU_AMOUNT");
            if (null != huAmount)
                map.put("HU_AMOUNT", huAmount.divide(new BigDecimal(100)));

            String withStatus = (String) map.get("STATUS");
            map.put("WITHDRAW_STATUS", withStatus);
            WithdrawStatusEnum withStatusCode = WithdrawStatusEnum.getByCode(withStatus);
            if (null != withStatusCode)
                map.put("STATUS_STR", withStatusCode.message());

            String withType = (String) map.get("WITHDRAW_TYPE");
            PayTypeEnum withTypeCode = PayTypeEnum.getByCode(withType);
            if (null != withTypeCode)
                map.put("WITHDRAW_TYPE_STR", withTypeCode.message());

            String withFrom = (String) map.get("WITHDRAW_FROM");
            WithDrawFromEnum withFromCode = WithDrawFromEnum.getByCode(withFrom);
            if (null != withFromCode)
                map.put("WITHDRAW_FROM", withFromCode.message());

            String al_account = MapUtils.getString(map, "al_account");
            if (StringUtils.isNotBlank(al_account)) {
                map.put("al_account", Des3.decode(al_account));
            }

            String VOUCEHR_IMG = MapUtils.getString(map, "VOUCEHR_IMG");
            List<String> voucherImg = JSONArray.parseArray(VOUCEHR_IMG, String.class);
            if (CollectionUtils.isNotEmpty(voucherImg))
                map.put("voucherImg", voucherImg);
            else
                map.put("voucherImg", null);
        }

        jsonObject.put("rows", queryAllUserWithdrawList);
        jsonObject.put("total", withdrawDAO.queryAllTeamWithdrawListCount(teamName, teamType,
            status, withDrawType, startTime, endTime));

        return jsonObject;
    }

    /**
     * 修改
     */
    @RequestMapping("/shenhe.do")
    @ResponseBody
    public Object updatemsg(final HttpServletRequest request) {
        final String eid = request.getParameter("eid");
        final String withdrawNo = request.getParameter("withdrawNo");
        final String checkType = request.getParameter("checkType");
        final String failReason = request.getParameter("failReason");

        //图片凭证
        final String listFile = request.getParameter("listFile");

        final String user_id = request.getSession().getAttribute("user_id").toString();

        final JsonResult data = new JsonResult(false);
        controllerTemplate.execute(data, new ControllerCallBack() {
            @Override
            public void executeService() {
                WithdrawDO withdrawDO = withdrawDAO.selectWithdrawByWithdrawNo(withdrawNo);
                if (null == withdrawDO)
                    throw new BaseRuntimeException("提现记录查询失败");

                AccountDO accountDO = accountDAO.searchByLinkIdOrAccountNo(null,
                    withdrawDO.getAccountNo(), null, null);
                if (null == accountDO)
                    throw new BaseRuntimeException("账户信息不存在");

                if (withdrawDO.getWithdrawType().equals("ALIPAY")) {
                    UserAliDO userAliDO = userAliDAO.queryInfoByUserId(accountDO.getLinkId());
                    if (null == userAliDO)
                        throw new BaseRuntimeException("没有绑定支付宝账户信息");

                    String aliAccount = Des3.decode(userAliDO.getAliAccount());
                    // 提现号 人民币金额 支付宝账号 真实姓名
                    try {
                        if (StringUtils.equals(checkType, WithdrawStatusEnum.REFUSE.getCode())) {
                            int result = withdrawDAO.shenhe(checkType, failReason, user_id,
                                Integer.parseInt(eid), withdrawNo);
                            transAcc(withdrawDO);
                            // 直接拒绝
                            // 1.给用户退还积分 2. 修改审核状态 REFUSE ， 审核拒绝原因 failReason
                            WithdrawDO withdrawDOtmp = new WithdrawDO();
                            withdrawDOtmp.setStatus(WithdrawStatusEnum.REFUSE.getCode());
                            withdrawDOtmp.setFailReason(failReason);
                            withdrawDAO.checkWithdraw(withdrawDOtmp);
                            if (result > 0) {
                                data.setSuccess(true);
                                data.setMessage("审核成功");
                            } else {
                                data.setSuccess(false);
                                data.setMessage("操作失败");
                            }
                        } else if (StringUtils.equals(checkType,
                            WithdrawStatusEnum.OFF_LINE.getCode())) {

                            //线下退款保存凭证
                            withdrawDO.setStatus(checkType);
                            withdrawDO.setFailReason(failReason);
                            withdrawDO.setModifier(user_id);
                            if (StringUtils.hasLength(listFile)) {
                                withdrawDO.setVoucehrImg(listFile);
                            }
                            withdrawDO.setStatus(WithdrawStatusEnum.OFF_LINE.getCode());
                            int result = withdrawDAO.shenheOnline(withdrawDO);

                            if (result > 0) {
                                data.setSuccess(true);
                                data.setMessage("审核成功");
                            } else {
                                data.setSuccess(false);
                                data.setMessage("操作失败");
                            }
                        } else if (StringUtils.equals(checkType,
                            WithdrawStatusEnum.FINISH.getCode())) {
                            AlipayFundTransToaccountTransferResponse payResponse = alipayComponent
                                .pay(withdrawDO.getWithdrawNo(), withdrawDO.getAmount().getAmount()
                                    .toString(), aliAccount, userAliDO.getRealName());
                            if (payResponse.isSuccess()) {
                                int result = withdrawDAO.shenhe(checkType, failReason, user_id,
                                    Integer.parseInt(eid), withdrawNo);
                                if (result > 0) {
                                    data.setSuccess(true);
                                    data.setMessage("审核成功");
                                }
                            } else {
                                data.setMessage(payResponse.getSubMsg());
                            }
                        }
                    } catch (AlipayApiException e) {
                        logger.error("金额转换出现异常", e);
                    }
                } else if (withdrawDO.getWithdrawType().equals("WEIXIN")) {
                    BaseResult withDrawCheck = withdrawService.withDrawCheck(Integer.parseInt(eid),
                        withdrawNo, checkType, failReason, request);
                    if (withDrawCheck.isSuccess()) {
                        data.setSuccess(true);
                        data.setMessage("审核成功");
                    } else {
                        data.setSuccess(false);
                        data.setMessage(withDrawCheck.getMessage());
                    }
                }
            }

            @Override
            public void check() {
                if (StringUtils.equals(checkType, WithdrawStatusEnum.OFF_LINE.getCode())) {
                    AssertUtil.notBlank(listFile, "请上传线上打款凭证");
                    List<String> list = JSONArray.parseArray(listFile, String.class);
                    AssertUtil.state(!CollectionUtils.isEmpty(list), "请上传线上打款凭证");
                    AssertUtil.notBlank(failReason, "请填写线下打款说明");
                }
            }
        });
        return data;
    }

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
