package com.onway.web.controller.mng.data;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.common.lang.CollectionUtils;
import com.onway.common.lang.Money;
import com.onway.common.lang.StringUtils;
import com.onway.fyapp.common.dal.dataobject.VoucherRecordDO;
import com.onway.model.VoucherRecordDomain;
import com.onway.model.conver.VoucherConver;
import com.onway.model.enums.CheckStatusEnum;
import com.onway.model.enums.VoucherFromWayEnum;
import com.onway.model.enums.VoucherRecordTypeEnum;
import com.onway.utils.BigdecimalUtil;
import com.onway.utils.DateUtil;
import com.onway.utils.excel.ExportExcel;
import com.onway.web.controller.AbstractController;

/**
 * 庆余卡销售记录
 * 
 * @author yuhang.ni
 * @version $Id: VoucherController.java, v 0.1 2019年3月13日 下午4:28:18 Administrator Exp $
 */
@Controller
public class VoucherController extends AbstractController {

    /**
     * 庆余卡销售记录
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/queryAllVoucherList.do")
    @ResponseBody
    public JSONObject queryAllVoucherList(HttpServletRequest request, Integer offset, Integer limit) {

        String fromWay = request.getParameter("fromWay");
        String checkStatus = request.getParameter("checkStatus");
        String checkStatusAcc = request.getParameter("checkStatusAcc");
        String userMsg = request.getParameter("userMsg");
        String teamMsg = request.getParameter("teamMsg");
        String acerMsg = request.getParameter("acerMsg");
        String cwerMsg = request.getParameter("cwerMsg");
        String createrMsg = request.getParameter("createrMsg");

        String startTime = request.getParameter("startDate");
        String endTime = request.getParameter("endDate");

        JSONObject data = new JSONObject();
        try {

            Date startDate = DateUtil.stringToDate(startTime, DateUtil.webFormat);
            Date endDate = DateUtil.stringToDate(endTime, DateUtil.webFormat);
            if (null != endDate)
                endDate = DateUtil.addDays(endDate, 1);

            List<Map<String, Object>> queryAllVoucherList = voucherDAO.queryAllVoucherList(fromWay,
                checkStatus, checkStatusAcc, userMsg, teamMsg, acerMsg, cwerMsg, createrMsg,
                startDate, endDate, offset, limit);

            Money amount_page = new Money(0);
            Money leftAmount_page = new Money(0);
            Money realAmount_page = new Money(0);

            for (Map<String, Object> map : queryAllVoucherList) {
                String fromWayThis = MapUtils.getString(map, "fromWay");
                VoucherFromWayEnum voucherFromWayEnum = VoucherFromWayEnum.getByCode(fromWayThis);
                if (null != voucherFromWayEnum)
                    map.put("fromWayStr", voucherFromWayEnum.message());

                if (voucherFromWayEnum != VoucherFromWayEnum.CHONG_ZHI) {
                    map.put("amount", new BigDecimal(0));
                    map.put("realAmount", new BigDecimal(0));
                }

                String checkStatusThis = MapUtils.getString(map, "checkStatus");
                String checkStatusAccThis = MapUtils.getString(map, "checkStatusAcc");
                if (StringUtils.notEquals(checkStatusThis, CheckStatusEnum.ENABLED.getCode())
                    && StringUtils.notEquals(checkStatusAccThis, CheckStatusEnum.ENABLED.getCode()))
                    map.put("leftAmount", new BigDecimal(0));

                BigDecimal amount = (BigDecimal) map.get("amount");
                Money amount_ = BigdecimalUtil.toMoney(amount);
                amount_page = amount_page.add(amount_);
                map.put("amount", amount_.toSimpleString());
                BigDecimal leftAmount = (BigDecimal) map.get("leftAmount");
                Money leftAmount_ = BigdecimalUtil.toMoney(leftAmount);
                leftAmount_page = leftAmount_page.add(leftAmount_);
                map.put("leftAmount", leftAmount_.toSimpleString());
                BigDecimal realAmount = (BigDecimal) map.get("realAmount");
                Money realAmount_ = BigdecimalUtil.toMoney(realAmount);
                realAmount_page = realAmount_page.add(realAmount_);
                map.put("realAmount", realAmount_.toSimpleString());

                CheckStatusEnum checkStatusEnum = CheckStatusEnum.getByCode(checkStatusThis);
                if (null != checkStatusEnum)
                    map.put("checkStatusStr", checkStatusEnum.message());

                CheckStatusEnum checkStatusAccEnum = CheckStatusEnum.getByCode(checkStatusAccThis);
                if (null != checkStatusAccEnum)
                    map.put("checkStatusAccStr", checkStatusAccEnum.message());

                String hu_userId = MapUtils.getString(map, "hu_userId");
                String ht_teamId = MapUtils.getString(map, "ht_teamId");
                BigDecimal queryMyVoucherSumAmount = voucherDAO
                    .queryMyVoucherSumAmount(hu_userId, ht_teamId,
                        CheckStatusEnum.ENABLED.getCode(), CheckStatusEnum.ENABLED.getCode());
                map.put("voucherLeftTeam", BigdecimalUtil.toMoney(queryMyVoucherSumAmount)
                    .toSimpleString());
            }

            data.put("rows", queryAllVoucherList);
            data.put("total", voucherDAO.queryAllVoucherListCount(fromWay, checkStatus,
                checkStatusAcc, userMsg, teamMsg, acerMsg, cwerMsg, createrMsg, startDate, endDate));

            data.put("amount_page", amount_page.toSimpleString());
            data.put("leftAmount_page", leftAmount_page.toSimpleString());
            data.put("realAmount_page", realAmount_page.toSimpleString());

            Map<String, Object> queryAllVoucherSum = voucherDAO.queryAllVoucherSum(fromWay,
                checkStatus, checkStatusAcc, userMsg, teamMsg, acerMsg, cwerMsg, createrMsg,
                startDate, endDate);
            data.put("amountSum", 0);
            data.put("leftAmountSum", 0);
            data.put("realAmountSum", 0);
            if (null != queryAllVoucherSum) {
                BigDecimal amountSum = (BigDecimal) queryAllVoucherSum.get("amountSum");
                data.put("amountSum", BigdecimalUtil.toMoney(amountSum).toSimpleString());

                BigDecimal leftAmountSum = (BigDecimal) queryAllVoucherSum.get("leftAmountSum");
                data.put("leftAmountSum", BigdecimalUtil.toMoney(leftAmountSum).toSimpleString());

                BigDecimal realAmountSum = (BigDecimal) queryAllVoucherSum.get("realAmountSum");
                data.put("realAmountSum", BigdecimalUtil.toMoney(realAmountSum).toSimpleString());
            }

        } catch (Exception e) {
            logger.error(MessageFormat.format("查询庆余卡异常", new Object[] {}));

        }
        return data;
    }

    @RequestMapping("/exportAllVoucherList.do")
    public void exportAllVoucherList(String fromWay, String checkStatus, String checkStatusAcc,
                                     String userMsg, String teamMsg, String acerMsg,
                                     String cwerMsg, String createrMsg, String startDate,
                                     String endDate, HttpServletResponse response,
                                     HttpServletRequest request) {

        try {
            Date endTime = DateUtil.stringToDate(endDate, DateUtil.newFormat);
            if (null != endTime)
                endTime = DateUtil.addDays(endTime, 1);
            //            List<Map<String, Object>> queryAllVoucherList = voucherDAO.queryAllVoucherList(fromWay,
            //                checkStatus, checkStatusAcc, userMsg, teamMsg, acerMsg, cwerMsg, createrMsg,
            //                DateUtil.stringToDate(startDate, DateUtil.newFormat), endTime, null, null);
            List<Map<String, Object>> queryAllVoucherList = voucherDAO.queryAllVoucherRecordList(
                fromWay, checkStatus, checkStatusAcc, userMsg, teamMsg, acerMsg, cwerMsg,
                createrMsg, DateUtil.stringToDate(startDate, DateUtil.newFormat), endTime, null,
                null);
            if (CollectionUtils.isEmpty(queryAllVoucherList)) {
                throw new RuntimeException("未找到相关数据");
            } else {

                for (Map<String, Object> map : queryAllVoucherList) {
                    String fromWayThis = MapUtils.getString(map, "fromWay");
                    VoucherFromWayEnum voucherFromWayEnum = VoucherFromWayEnum
                        .getByCode(fromWayThis);
                    if (null != voucherFromWayEnum)
                        map.put("fromWayStr", voucherFromWayEnum.message());

                    String recordTypeThis = MapUtils.getString(map, "recordType");
                    VoucherRecordTypeEnum voucherRecordTypeEnum = VoucherRecordTypeEnum
                        .getByCode(recordTypeThis);
                    if (null != voucherRecordTypeEnum)
                        map.put("recordTypeStr", voucherRecordTypeEnum.message());

                    if (voucherFromWayEnum != VoucherFromWayEnum.CHONG_ZHI) {
                        map.put("amount", new BigDecimal(0));
                        map.put("realAmount", new BigDecimal(0));
                    }

                    BigDecimal amount = (BigDecimal) map.get("amount");
                    map.put("amount", BigdecimalUtil.toMoney(amount).toSimpleString());
                    BigDecimal leftAmount = (BigDecimal) map.get("leftAmount");
                    map.put("leftAmount", BigdecimalUtil.toMoney(leftAmount).toSimpleString());
                    BigDecimal realAmount = (BigDecimal) map.get("realAmount");
                    map.put("realAmount", BigdecimalUtil.toMoney(realAmount).toSimpleString());

                    String checkStatusThis = MapUtils.getString(map, "checkStatus");
                    CheckStatusEnum checkStatusEnum = CheckStatusEnum.getByCode(checkStatusThis);
                    if (null != checkStatusEnum)
                        map.put("checkStatusStr", checkStatusEnum.message());

                    String checkStatusAccThis = MapUtils.getString(map, "checkStatusAcc");
                    CheckStatusEnum checkStatusAccEnum = CheckStatusEnum
                        .getByCode(checkStatusAccThis);
                    if (null != checkStatusAccEnum)
                        map.put("checkStatusAccStr", checkStatusAccEnum.message());

                    if (StringUtils.notEquals(checkStatusThis, CheckStatusEnum.ENABLED.getCode())
                        && StringUtils.notEquals(checkStatusAccThis,
                            CheckStatusEnum.ENABLED.getCode()))
                        map.put("leftAmount", "0");

                    Date gmtCreate = (Date) map.get("gmtCreate");
                    Date gmtModified = (Date) map.get("gmtModified");
                    map.put("gmtCreate", DateUtil.dateToString(gmtCreate, DateUtil.newFormat));
                    map.put("gmtModified", DateUtil.dateToString(gmtModified, DateUtil.newFormat));

                    if (null != map.get("checkDate")) {
                        Date checkDate = (Date) map.get("checkDate");
                        map.put("checkDate", DateUtil.dateToString(checkDate, DateUtil.newFormat));
                    }
                    if (null != map.get("checkDateAcc")) {
                        Date checkDateAcc = (Date) map.get("checkDateAcc");
                        map.put("checkDateAcc",
                            DateUtil.dateToString(checkDateAcc, DateUtil.newFormat));
                    }

                    String hu_userId = MapUtils.getString(map, "hu_userId");
                    String ht_teamId = MapUtils.getString(map, "ht_teamId");
                    BigDecimal queryMyVoucherSumAmount = voucherDAO.queryMyVoucherSumAmount(
                        hu_userId, ht_teamId, CheckStatusEnum.ENABLED.getCode(),
                        CheckStatusEnum.ENABLED.getCode());
                    map.put("voucherLeftTeam", BigdecimalUtil.toMoney(queryMyVoucherSumAmount)
                        .toSimpleString());

                    String hucc_name = MapUtils.getString(map, "hucc_name");
                    String hucc_cell = MapUtils.getString(map, "hucc_cell");
                    map.put("hucc_Msg", checkEmpty(hucc_name) + "/" + checkEmpty(hucc_cell));

                    String hucca_name = MapUtils.getString(map, "hucca_name");
                    String hucca_cell = MapUtils.getString(map, "hucca_cell");
                    map.put("hucca_Msg", checkEmpty(hucca_name) + "/" + checkEmpty(hucca_cell));

                    String hu_name = MapUtils.getString(map, "hu_name");
                    String hu_cell = MapUtils.getString(map, "hu_cell");
                    map.put("hu_Msg", checkEmpty(hu_name) + "/" + checkEmpty(hu_cell));

                    String ht_teamName = MapUtils.getString(map, "ht_teamName");
                    String ht_teamCell = MapUtils.getString(map, "ht_teamCell");
                    String ht_teamErpNo = MapUtils.getString(map, "ht_teamErpNo");
                    map.put("teamMsg", checkEmpty(ht_teamId) + "/" + checkEmpty(ht_teamName) + "/"
                                       + checkEmpty(ht_teamCell) + "/" + checkEmpty(ht_teamErpNo));

                    String ht_chargeUser = MapUtils.getString(map, "ht_chargeUser");
                    String ht_chargeCell = MapUtils.getString(map, "ht_chargeCell");
                    map.put("ht_Msg", checkEmpty(ht_chargeUser) + "/" + checkEmpty(ht_chargeCell));

                    String huc_name = MapUtils.getString(map, "huc_name");
                    String huc_cell = MapUtils.getString(map, "huc_cell");
                    map.put("huc_Msg", checkEmpty(huc_name) + "/" + checkEmpty(huc_cell));

                    BigDecimal recordAmount = (BigDecimal) map.get("recordAmount");
                    String recordType = MapUtils.getString(map, "recordType");

                    Money recordAmount_ = BigdecimalUtil.toMoney(recordAmount);
                    map.put("recordAmount", recordAmount_.toSimpleString());
                    if (StringUtils.equals(recordType, VoucherRecordTypeEnum.GIFT_IN.getCode())
                        || StringUtils.equals(recordType, VoucherRecordTypeEnum.ORDER_IN.getCode())
                        || StringUtils.equals(recordType,
                            VoucherRecordTypeEnum.ORDER_OUT_RETURN.getCode())) {
                        if (recordAmount_.lessThan(new Money(0))) {
                            map.put("recordAmount", recordAmount_.toSimpleString());
                        } else {
                            map.put("recordAmount", "+" + recordAmount_.toSimpleString());
                        }
                    }

                    if (StringUtils.equals(recordType, VoucherRecordTypeEnum.GIFT_OUT.getCode())
                        || StringUtils
                            .equals(recordType, VoucherRecordTypeEnum.ORDER_OUT.getCode())) {
                        if (recordAmount_.lessEqualThan(new Money(0))) {
                            map.put("recordAmount", recordAmount_.toSimpleString());
                        } else {
                            map.put("recordAmount", "-" + recordAmount_.toSimpleString());
                        }
                    }
                }

                // 表头
                String[] headers = { "来源", "庆余卡编号", "操作金额", "关联编号", "所属用户", "所属门店", "充值金额",
                        "剩余可用金额", "实付金额", "余额（所属门店）", "备注", "凭证号", "审核状态（业务员）", "审核（业务员）",
                        "审核备注（业务员）", "审核时间（业务员）", "审核状态（财务）", "审核（财务）", "审核备注（财务）", "审核时间（财务）",
                        "所属门店负责人", "创建者", "创建时间", "修改时间" };
                // 数据键名或者MODEL类字段名
                String[] Col = { "recordTypeStr", "voucherNo", "recordAmount", "recordLink",
                        "hu_Msg", "teamMsg", "amount", "leftAmount", "realAmount",
                        "voucherLeftTeam", "memo", "cerNo", "checkStatusStr", "hucc_Msg",
                        "checkMemo", "checkDate", "checkStatusAccStr", "hucca_Msg", "checkMemoAcc",
                        "checkDateAcc", "ht_Msg", "huc_Msg", "gmtCreate", "gmtModified" };

                //                ExportExcel<Map<String, Object>> ex = new ExportExcel<Map<String, Object>>();
                //                List<Map<String, Object>> excelList = queryAllVoucherList;
                //                // 生成Excel
                //                HSSFWorkbook workbook = ex.exportExcel("sheet1", headers, Col, excelList, null);
                //                // 下载
                //                response.setContentType("application/vnd.ms-excel");
                //                response.setHeader("Content-disposition", "attachment;filename=" + "table.xls");
                //                OutputStream ouputStream = response.getOutputStream();
                //                workbook.write(ouputStream);
                //                ouputStream.flush();
                //                ouputStream.close();

                String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())
                    .toString().concat(".xls");
                HSSFWorkbook book = ExportExcel.exportExcel(fileName, headers, Col,
                    queryAllVoucherList, null);
                //写入数据
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + fileName);
                book.write(response.getOutputStream());

                response.getOutputStream().flush();
                response.getOutputStream().close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 庆余卡使用详细
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/queryVoucherDetailList.do")
    @ResponseBody
    public JSONObject queryVoucherDetailList(HttpServletRequest request, Integer offset,
                                             Integer limit) {

        String voucherNo = request.getParameter("voucherNo");

        JSONObject data = new JSONObject();
        try {

            List<VoucherRecordDomain> list = new ArrayList<VoucherRecordDomain>();

            List<VoucherRecordDO> queryVoucherRecord = voucherRecordDAO.queryVoucherRecord(
                voucherNo, offset, limit);

            for (VoucherRecordDO voucherRecordDO : queryVoucherRecord) {
                list.add(VoucherConver.buildReduceListResult(voucherRecordDO));
            }

            data.put("rows", list);
            data.put("total", voucherRecordDAO.queryVoucherRecordCount(voucherNo));

        } catch (Exception e) {
            logger.error(MessageFormat.format("查询庆余卡使用详细异常", new Object[] {}));
        }
        return data;
    }

    @RequestMapping("/queryAllVoucherRecordList.do")
    @ResponseBody
    public JSONObject queryAllVoucherRecordList(HttpServletRequest request, Integer offset,
                                                Integer limit) {

        String fromWay = request.getParameter("fromWay");
        String checkStatus = request.getParameter("checkStatus");
        String checkStatusAcc = request.getParameter("checkStatusAcc");
        String userMsg = request.getParameter("userMsg");
        String teamMsg = request.getParameter("teamMsg");
        String acerMsg = request.getParameter("acerMsg");
        String cwerMsg = request.getParameter("cwerMsg");
        String createrMsg = request.getParameter("createrMsg");

        String startTime = request.getParameter("startDate");
        String endTime = request.getParameter("endDate");

        JSONObject data = new JSONObject();
        try {

            Date startDate = DateUtil.stringToDate(startTime, DateUtil.webFormat);
            Date endDate = DateUtil.stringToDate(endTime, DateUtil.webFormat);
            if (null != endDate)
                endDate = DateUtil.addDays(endDate, 1);

            List<Map<String, Object>> queryAllVoucherList = voucherDAO.queryAllVoucherRecordList(
                fromWay, checkStatus, checkStatusAcc, userMsg, teamMsg, acerMsg, cwerMsg,
                createrMsg, startDate, endDate, offset, limit);

            Money amount_page = new Money(0);
            Money leftAmount_page = new Money(0);
            Money realAmount_page = new Money(0);

            List<String> voucherNos = new ArrayList<String>();
            for (Map<String, Object> map : queryAllVoucherList) {
                String voucherNo = MapUtils.getString(map, "voucherNo");
                boolean con = false;
                if (!voucherNos.contains(voucherNo)) {
                    con = true;
                    voucherNos.add(voucherNo);
                }

                String fromWayThis = MapUtils.getString(map, "fromWay");
                VoucherFromWayEnum voucherFromWayEnum = VoucherFromWayEnum.getByCode(fromWayThis);
                if (null != voucherFromWayEnum)
                    map.put("fromWayStr", voucherFromWayEnum.message());

                String recordTypeThis = MapUtils.getString(map, "recordType");
                VoucherRecordTypeEnum voucherRecordTypeEnum = VoucherRecordTypeEnum
                    .getByCode(recordTypeThis);
                if (null != voucherRecordTypeEnum)
                    map.put("recordTypeStr", voucherRecordTypeEnum.message());

                if (voucherFromWayEnum != VoucherFromWayEnum.CHONG_ZHI) {
                    map.put("amount", new BigDecimal(0));
                    map.put("realAmount", new BigDecimal(0));
                }

                String checkStatusThis = MapUtils.getString(map, "checkStatus");
                String checkStatusAccThis = MapUtils.getString(map, "checkStatusAcc");
                if (StringUtils.notEquals(checkStatusThis, CheckStatusEnum.ENABLED.getCode())
                    && StringUtils.notEquals(checkStatusAccThis, CheckStatusEnum.ENABLED.getCode()))
                    map.put("leftAmount", new BigDecimal(0));

                BigDecimal amount = (BigDecimal) map.get("amount");
                Money amount_ = BigdecimalUtil.toMoney(amount);
                if (con) {
                    amount_page = amount_page.add(amount_);
                }
                map.put("amount", amount_.toSimpleString());
                BigDecimal leftAmount = (BigDecimal) map.get("leftAmount");
                Money leftAmount_ = BigdecimalUtil.toMoney(leftAmount);
                if (con) {
                    leftAmount_page = leftAmount_page.add(leftAmount_);
                }
                map.put("leftAmount", leftAmount_.toSimpleString());
                BigDecimal realAmount = (BigDecimal) map.get("realAmount");
                Money realAmount_ = BigdecimalUtil.toMoney(realAmount);
                if (con) {
                    realAmount_page = realAmount_page.add(realAmount_);
                }
                map.put("realAmount", realAmount_.toSimpleString());

                CheckStatusEnum checkStatusEnum = CheckStatusEnum.getByCode(checkStatusThis);
                if (null != checkStatusEnum)
                    map.put("checkStatusStr", checkStatusEnum.message());

                CheckStatusEnum checkStatusAccEnum = CheckStatusEnum.getByCode(checkStatusAccThis);
                if (null != checkStatusAccEnum)
                    map.put("checkStatusAccStr", checkStatusAccEnum.message());

                String hu_userId = MapUtils.getString(map, "hu_userId");
                String ht_teamId = MapUtils.getString(map, "ht_teamId");
                BigDecimal queryMyVoucherSumAmount = voucherDAO
                    .queryMyVoucherSumAmount(hu_userId, ht_teamId,
                        CheckStatusEnum.ENABLED.getCode(), CheckStatusEnum.ENABLED.getCode());
                map.put("voucherLeftTeam", BigdecimalUtil.toMoney(queryMyVoucherSumAmount)
                    .toSimpleString());

                BigDecimal recordAmount = (BigDecimal) map.get("recordAmount");
                String recordType = MapUtils.getString(map, "recordType");

                Money recordAmount_ = BigdecimalUtil.toMoney(recordAmount);
                map.put("recordAmount", recordAmount_.toSimpleString());
                if (StringUtils.equals(recordType, VoucherRecordTypeEnum.GIFT_IN.getCode())
                    || StringUtils.equals(recordType, VoucherRecordTypeEnum.ORDER_IN.getCode())
                    || StringUtils.equals(recordType,
                        VoucherRecordTypeEnum.ORDER_OUT_RETURN.getCode())) {
                    if (recordAmount_.lessThan(new Money(0))) {
                        map.put("recordAmount", recordAmount_.toSimpleString());
                    } else {
                        map.put("recordAmount", "+" + recordAmount_.toSimpleString());
                    }
                }

                if (StringUtils.equals(recordType, VoucherRecordTypeEnum.GIFT_OUT.getCode())
                    || StringUtils.equals(recordType, VoucherRecordTypeEnum.ORDER_OUT.getCode())) {
                    if (recordAmount_.lessEqualThan(new Money(0))) {
                        map.put("recordAmount", recordAmount_.toSimpleString());
                    } else {
                        map.put("recordAmount", "-" + recordAmount_.toSimpleString());
                    }
                }
            }

            data.put("rows", queryAllVoucherList);
            data.put("total", voucherDAO.queryAllVoucherRecordListCount(fromWay, checkStatus,
                checkStatusAcc, userMsg, teamMsg, acerMsg, cwerMsg, createrMsg, startDate, endDate));

            data.put("amount_page", amount_page.toSimpleString());
            data.put("leftAmount_page", leftAmount_page.toSimpleString());
            data.put("realAmount_page", realAmount_page.toSimpleString());

            List<String> voucehrNoList = voucherDAO.queryAllVoucherRecordVoucehrNoList(fromWay,
                checkStatus, checkStatusAcc, userMsg, teamMsg, acerMsg, cwerMsg, createrMsg,
                startDate, endDate, null, null);
            data.put("amountSum", 0);
            data.put("leftAmountSum", 0);
            data.put("realAmountSum", 0);

            if (CollectionUtils.isNotEmpty(voucehrNoList)) {
                Map<String, Object> queryAllVoucherSum = voucherDAO
                    .queryAllVoucherRecordSum(voucehrNoList);
                if (null != queryAllVoucherSum) {
                    BigDecimal amountSum = (BigDecimal) queryAllVoucherSum.get("amountSum");
                    data.put("amountSum", BigdecimalUtil.toMoney(amountSum).toSimpleString());

                    BigDecimal leftAmountSum = (BigDecimal) queryAllVoucherSum.get("leftAmountSum");
                    data.put("leftAmountSum", BigdecimalUtil.toMoney(leftAmountSum)
                        .toSimpleString());

                    BigDecimal realAmountSum = (BigDecimal) queryAllVoucherSum.get("realAmountSum");
                    data.put("realAmountSum", BigdecimalUtil.toMoney(realAmountSum)
                        .toSimpleString());
                }
            }

        } catch (Exception e) {
            logger.error(MessageFormat.format("查询庆余卡异常", new Object[] {}));

        }
        return data;
    }
}
