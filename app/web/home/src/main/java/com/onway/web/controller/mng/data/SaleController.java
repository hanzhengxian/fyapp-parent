package com.onway.web.controller.mng.data;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
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
import com.onway.utils.BigdecimalUtil;
import com.onway.utils.DateUtil;
import com.onway.utils.ReduceAmountUtil;
import com.onway.utils.excel.ExportExcel;
import com.onway.web.controller.AbstractController;

/**
 * 销售折扣审核记录
 * 
 * @author yuhang.ni
 * @version $Id: SaleController.java, v 0.1 2019年4月8日 上午10:42:02 Administrator Exp $
 */
@Controller
public class SaleController extends AbstractController {

    /**
     * 销售折扣审核记录
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/queryAllSaleCheckList.do")
    @ResponseBody
    public JSONObject queryAllSaleCheckList(HttpServletRequest request, Integer offset,
                                            Integer limit) {

        String province = getProCitName(request.getParameter("linkProvince"));
        String city = getProCitName(request.getParameter("linkCity"));
        String teamMsg = request.getParameter("teamMsg");
        String operMsg = request.getParameter("operMsg");
        String ywerMsg = request.getParameter("ywerMsg");
        String dqerMsg = request.getParameter("dqerMsg");

        String proMsg = request.getParameter("proMsg");
        String userMsg = request.getParameter("userMsg");

        String startTimeYw = request.getParameter("startDateYw");
        String endTimeYw = request.getParameter("endDateYw");

        String ywCheckStatus = request.getParameter("ywCheckStatus");
        String dqCheckStatus = request.getParameter("dqCheckStatus");

        String startTimeDq = request.getParameter("startDateDq");
        String endTimeDq = request.getParameter("endDateDq");

        String startTimeOrder = request.getParameter("startDateOrder");
        String endTimeOrder = request.getParameter("endDateOrder");

        JSONObject data = new JSONObject();
        try {

            Date startDateYw = DateUtil.stringToDate(startTimeYw, DateUtil.webFormat);
            Date endDateYw = DateUtil.stringToDate(endTimeYw, DateUtil.webFormat);
            if (null != endDateYw)
                endDateYw = DateUtil.addDays(endDateYw, 1);

            Date startDateDq = DateUtil.stringToDate(startTimeDq, DateUtil.webFormat);
            Date endDateDq = DateUtil.stringToDate(endTimeDq, DateUtil.webFormat);
            if (null != endDateDq)
                endDateDq = DateUtil.addDays(endDateDq, 1);

            Date startDateOrder = DateUtil.stringToDate(startTimeOrder, DateUtil.webFormat);
            Date endDateOrder = DateUtil.stringToDate(endTimeOrder, DateUtil.webFormat);
            if (null != endDateOrder)
                endDateOrder = DateUtil.addDays(endDateOrder, 1);

            if (StringUtils.isNotBlank(city))
                province = null;

            List<Map<String, Object>> queryAllSaleCheckList = orderDAO.queryAllSaleCheckList(
                province, city, teamMsg, operMsg, ywerMsg, dqerMsg, startDateYw, endDateYw,
                startDateDq, endDateDq, startDateOrder, endDateOrder, proMsg, userMsg,
                ywCheckStatus, dqCheckStatus, offset, limit);

            Double orderCount_page = 0D;
            Money orderPrice_page = new Money(0);
            Money realOrderPrice_page = new Money(0);
            Money reduceAmountDiscount_page = new Money(0);

            for (Map<String, Object> map : queryAllSaleCheckList) {
                Double orderCount = MapUtils.getDouble(map, "orderCount");
                orderCount_page += orderCount;

                BigDecimal productPrice = (BigDecimal) map.get("productPrice");
                map.put("productPrice", BigdecimalUtil.toMoney(productPrice).toSimpleString());

                BigDecimal productOldPrice = (BigDecimal) map.get("productOldPrice");
                map.put("productOldPrice", BigdecimalUtil.toMoney(productOldPrice).toSimpleString());

                BigDecimal realOrderPrice = (BigDecimal) map.get("realOrderPrice");
                Money realOrderPrice_ = BigdecimalUtil.toMoney(realOrderPrice);
                realOrderPrice_page = realOrderPrice_page.add(realOrderPrice_);
                map.put("realOrderPrice", realOrderPrice_.toSimpleString());

                BigDecimal orderPrice = (BigDecimal) map.get("orderPrice");
                Money orderPrice_ = BigdecimalUtil.toMoney(orderPrice);
                orderPrice_page = orderPrice_page.add(orderPrice_);
                map.put("orderPrice", orderPrice_.toSimpleString());

                Double cashierDiscount = MapUtils.getDouble(map, "cashierDiscount");
                BigDecimal multiply = new BigDecimal(cashierDiscount).multiply(new BigDecimal(100));
                map.put("cashierDiscount", multiply.setScale(2, BigDecimal.ROUND_HALF_UP));

                String reducePro = MapUtils.getString(map, "reducePro");
                String reducePlat = MapUtils.getString(map, "reducePlat");
                String reduceTeam = MapUtils.getString(map, "reduceTeam");

                Money reduceAmountDiscount = ReduceAmountUtil.getChildOrderReduceAmountS(reducePro,
                    reducePlat, reduceTeam);
                reduceAmountDiscount_page = reduceAmountDiscount_page.add(reduceAmountDiscount);
                map.put("reduceAmountDiscount", reduceAmountDiscount.toSimpleString());
            }

            data.put("rows", queryAllSaleCheckList);
            data.put("total", orderDAO.queryAllSaleCheckListCount(province, city, teamMsg, operMsg,
                ywerMsg, dqerMsg, startDateYw, endDateYw, startDateDq, endDateDq, startDateOrder,
                endDateOrder, proMsg, userMsg, ywCheckStatus, dqCheckStatus));

            data.put("orderCount_page", String.format("%.2f", orderCount_page));
            data.put("orderPrice_page", orderPrice_page.toSimpleString());
            data.put("realOrderPrice_page", realOrderPrice_page.toSimpleString());
            data.put("reduceAmountDiscount_page", reduceAmountDiscount_page.toSimpleString());

            Map<String, Object> queryAllSaleCheckSum = orderDAO.queryAllSaleCheckSum(province,
                city, teamMsg, operMsg, ywerMsg, dqerMsg, startDateYw, endDateYw, startDateDq,
                endDateDq, startDateOrder, endDateOrder, proMsg, userMsg, ywCheckStatus,
                dqCheckStatus);
            data.put("orderCountSum", 0);
            data.put("orderPriceSum", 0);
            data.put("realOrderPriceSum", 0);
            if (null != queryAllSaleCheckSum) {
                Double orderCountSum = MapUtils.getDouble(queryAllSaleCheckSum, "orderCountSum");
                data.put("orderCountSum", String.format("%.2f", orderCountSum));

                BigDecimal orderPriceSum = (BigDecimal) queryAllSaleCheckSum.get("orderPriceSum");
                data.put("orderPriceSum", BigdecimalUtil.toMoney(orderPriceSum).toSimpleString());

                BigDecimal realOrderPriceSum = (BigDecimal) queryAllSaleCheckSum
                    .get("realOrderPriceSum");
                data.put("realOrderPriceSum", BigdecimalUtil.toMoney(realOrderPriceSum)
                    .toSimpleString());
            }

        } catch (Exception e) {
            logger.error(MessageFormat.format("查询销售折扣审核记录异常", new Object[] {}));

        }
        return data;
    }

    @RequestMapping("/exportAllSaleCheckList.do")
    public void exportAllSaleCheckList(String linkProvince, String linkCity, String teamMsg,
                                       String operMsg, String ywerMsg, String dqerMsg,
                                       String startDateYw, String endDateYw, String startDateDq,
                                       String endDateDq, String startDateOrder,
                                       String endDateOrder, String proMsg, String userMsg,
                                       String ywCheckStatus, String dqCheckStatus,
                                       HttpServletResponse response, HttpServletRequest request) {
        try {
            Date startTimeYw = DateUtil.stringToDate(startDateYw, DateUtil.webFormat);
            Date endTimeYw = DateUtil.stringToDate(endDateYw, DateUtil.webFormat);
            if (null != endTimeYw)
                endTimeYw = DateUtil.addDays(endTimeYw, 1);

            Date startTimeDq = DateUtil.stringToDate(startDateDq, DateUtil.webFormat);
            Date endTimeDq = DateUtil.stringToDate(endDateDq, DateUtil.webFormat);
            if (null != endTimeDq)
                endTimeDq = DateUtil.addDays(endTimeDq, 1);

            Date startTimeOrder = DateUtil.stringToDate(startDateOrder, DateUtil.webFormat);
            Date endTimeOrder = DateUtil.stringToDate(endDateOrder, DateUtil.webFormat);
            if (null != endTimeOrder)
                endTimeOrder = DateUtil.addDays(endTimeOrder, 1);

            String province = getProCitName(linkProvince);
            String city = getProCitName(linkCity);
            if (StringUtils.isNotBlank(city))
                province = null;

            List<Map<String, Object>> queryAllSaleCheckList = orderDAO.queryAllSaleCheckList(
                province, city, teamMsg, operMsg, ywerMsg, dqerMsg, startTimeYw, endTimeYw,
                startTimeDq, endTimeDq, startTimeOrder, endTimeOrder, proMsg, userMsg,
                ywCheckStatus, dqCheckStatus, null, null);
            if (CollectionUtils.isEmpty(queryAllSaleCheckList)) {
                throw new RuntimeException("未找到相关数据");
            } else {

                for (Map<String, Object> map : queryAllSaleCheckList) {
                    BigDecimal productPrice = (BigDecimal) map.get("productPrice");
                    map.put("productPrice", BigdecimalUtil.toMoney(productPrice).toSimpleString());

                    BigDecimal productOldPrice = (BigDecimal) map.get("productOldPrice");
                    map.put("productOldPrice", BigdecimalUtil.toMoney(productOldPrice)
                        .toSimpleString());

                    BigDecimal realOrderPrice = (BigDecimal) map.get("realOrderPrice");
                    map.put("realOrderPrice", BigdecimalUtil.toMoney(realOrderPrice)
                        .toSimpleString());

                    BigDecimal orderPrice = (BigDecimal) map.get("orderPrice");
                    map.put("orderPrice", BigdecimalUtil.toMoney(orderPrice).toSimpleString());

                    Double cashierDiscount = MapUtils.getDouble(map, "cashierDiscount");
                    BigDecimal multiply = new BigDecimal(cashierDiscount).multiply(new BigDecimal(
                        100));
                    map.put("cashierDiscount", multiply.setScale(2, BigDecimal.ROUND_HALF_UP));

                    String reducePro = MapUtils.getString(map, "reducePro");
                    String reducePlat = MapUtils.getString(map, "reducePlat");
                    String reduceTeam = MapUtils.getString(map, "reduceTeam");

                    Money reduceAmountDiscount = ReduceAmountUtil.getChildOrderReduceAmountS(
                        reducePro, reducePlat, reduceTeam);
                    map.put("reduceAmountDiscount", reduceAmountDiscount.toSimpleString());

                    if (null != map.get("yw_time")) {
                        Date yw_time = (Date) map.get("yw_time");
                        map.put("yw_time", DateUtil.dateToString(yw_time, DateUtil.newFormat));
                    }
                    if (null != map.get("dq_time")) {
                        Date dq_time = (Date) map.get("dq_time");
                        map.put("dq_time", DateUtil.dateToString(dq_time, DateUtil.newFormat));
                    }
                    if (null != map.get("gmtCreate")) {
                        Date gmtCreate = (Date) map.get("gmtCreate");
                        map.put("gmtCreate", DateUtil.dateToString(gmtCreate, DateUtil.newFormat));
                    }

                    String yw_name = MapUtils.getString(map, "yw_name");
                    String yw_cell = MapUtils.getString(map, "yw_cell");
                    map.put("yw_Msg", checkEmpty(yw_name) + "/" + checkEmpty(yw_cell));

                    String dq_name = MapUtils.getString(map, "dq_name");
                    String dq_cell = MapUtils.getString(map, "dq_cell");
                    map.put("dq_Msg", checkEmpty(dq_name) + "/" + checkEmpty(dq_cell));

                    String buy_name = MapUtils.getString(map, "buy_name");
                    String buy_cell = MapUtils.getString(map, "buy_cell");
                    map.put("buy_Msg", checkEmpty(buy_name) + "/" + checkEmpty(buy_cell));

                    String op_name = MapUtils.getString(map, "op_name");
                    String op_cell = MapUtils.getString(map, "op_cell");
                    map.put("op_Msg", checkEmpty(op_name) + "/" + checkEmpty(op_cell));
                }

                // 表头
                String[] headers = { "序号", "订单号", "子订单号", "商品名称", "业务经理", "审核理由（业务）", "审核时间（业务）",
                        "大区经理", "审核理由（大区）", "审核时间（大区）", "折扣（%）", "购买人", "数量", "零售金额", "实付金额",
                        "优惠券金额", "操作人", "订单创建时间" };
                // 数据键名或者MODEL类字段名
                String[] Col = { "proNum", "orderNo", "orderId", "productName", "yw_Msg",
                        "yw_reason", "yw_time", "dq_Msg", "dq_reason", "dq_time",
                        "cashierDiscount", "buy_Msg", "orderCount", "orderPrice", "realOrderPrice",
                        "reduceAmountDiscount", "op_Msg", "gmtCreate" };

                //                ExportExcel<Map<String, Object>> ex = new ExportExcel<Map<String, Object>>();
                //                List<Map<String, Object>> excelList = queryAllSaleCheckList;
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
                    queryAllSaleCheckList, null);
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
}
