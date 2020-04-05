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
import com.onway.utils.DateUtil;
import com.onway.utils.excel.ExportExcel;
import com.onway.web.controller.AbstractController;

/**
 * 优惠券使用记录
 * 
 * @author yuhang.ni
 * @version $Id: DiscountDataController.java, v 0.1 2019年4月9日 上午9:45:01 Administrator Exp $
 */
@Controller
public class DiscountDataController extends AbstractController {

    /**
     * 优惠券使用记录
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/queryAllDiscountUseList.do")
    @ResponseBody
    public JSONObject queryAllDiscountUseList(HttpServletRequest request, Integer offset,
                                              Integer limit) {

        List<String> province = getProCitNameS(request.getParameterValues("linkProvince[]"));
        List<String> city = getProCitNameS(request.getParameterValues("linkCity[]"));
        String teamMsg = request.getParameter("teamMsg");
        String discountId = request.getParameter("discountId");
//        String discountName = request.getParameter("discountName");

        String startTime = request.getParameter("startDate");
        String endTime = request.getParameter("endDate");

        JSONObject data = new JSONObject();
        try {
            if (CollectionUtils.isNotEmpty(city)) {
                province = null;
            }

            Date startDate = DateUtil.stringToDate(startTime, DateUtil.webFormat);
            Date endDate = DateUtil.stringToDate(endTime, DateUtil.webFormat);
            if (null != endDate)
                endDate = DateUtil.addDays(endDate, 1);

            //            List<Map<String, Object>> queryAllDiscountUseList = orderDAO.queryAllDiscountUseList(
            //                province, city, teamMsg, discountName, discountId, startDate, endDate, offset,
            //                limit);

            List<Map<String, Object>> queryAllDiscountUseList = orderDAO.queryAllDiscountUseListS(
                province, city, teamMsg, discountId, startDate, endDate, offset, limit);

            if (CollectionUtils.isEmpty(queryAllDiscountUseList)) {
                data.put("rate_page", "");
                data.put("orderPrice_page", "");
                data.put("amount_page", "");
                data.put("orderPrice_all", "");
                data.put("amount_all", "");
                data.put("rate_all", "");
                data.put("rows", queryAllDiscountUseList);
                data.put("total", 0);
                return data;
            }

            Money orderPrice_page = new Money(0);
            BigDecimal rate_page = new BigDecimal(0);
            BigDecimal amount_page = new BigDecimal(0);
            for (Map<String, Object> map : queryAllDiscountUseList) {
                String orderPrice_str = MapUtils.getString(map, "orderPrice");
                Money orderPrice = new Money(orderPrice_str).divide(100);
                map.put("orderPrice", orderPrice.toSimpleString());

                String amount_str = MapUtils.getString(map, "amount");
                BigDecimal rate = new BigDecimal(amount_str).divide(orderPrice.getAmount(), 4,
                    BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
                map.put("discountRate", rate);

                orderPrice_page = orderPrice_page.add(orderPrice);
                rate_page = rate_page.add(new BigDecimal(amount_str));
                amount_page = amount_page.add(new BigDecimal(amount_str));
                
                String discountId_this = MapUtils.getString(map, "discountId");
                if(StringUtils.isNotBlank(discountId_this))
                    map.put("discountName", discountDAO.getDisCountName(discountId_this));
                
            }
            data.put("rate_page",
                rate_page.divide(orderPrice_page.getAmount(), 4, BigDecimal.ROUND_HALF_UP)
                    .multiply(new BigDecimal(100)));
            data.put("orderPrice_page", orderPrice_page.toSimpleString());
            data.put("amount_page", amount_page.doubleValue());

            data.put("rows", queryAllDiscountUseList);
            //            data.put("total", orderDAO.queryAllDiscountUseListCount(province, city, teamMsg,
            //                discountName, discountId, startDate, endDate));
            data.put("total", orderDAO.queryAllDiscountUseListCountS(province, city, teamMsg,
                discountId, startDate, endDate));

            try {
                //                Map<String, Object> sumAllDiscountUseList = orderDAO.sumAllDiscountUseList(
                //                    province, city, teamMsg, discountName, discountId, startDate, endDate);
                Map<String, Object> sumAllDiscountUseList = orderDAO.sumAllDiscountUseListS(
                    province, city, teamMsg, discountId, startDate, endDate);
                if (null != sumAllDiscountUseList) {
                    String orderPrice_str = MapUtils.getString(sumAllDiscountUseList, "orderPrice");
                    Money orderPrice = new Money(orderPrice_str).divide(100);
                    String amount_str = MapUtils.getString(sumAllDiscountUseList, "amount");
                    BigDecimal rate_all = new BigDecimal(amount_str).divide(orderPrice.getAmount(),
                        4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
                    data.put("orderPrice_all", orderPrice.toSimpleString());
                    data.put("amount_all",
                        new BigDecimal(amount_str).setScale(2, BigDecimal.ROUND_HALF_UP));
                    data.put("rate_all", rate_all);
                }
            } catch (Exception e) {

            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("查询优惠券使用记录异常", new Object[] {}));

        }
        return data;
    }

    @RequestMapping("/exportAllDiscountUseList.do")
    public void exportAllDiscountUseList(HttpServletResponse response, HttpServletRequest request) {

        List<String> province = getProCitNameS(request.getParameterValues("linkProvince[]"));
        List<String> city = getProCitNameS(request.getParameterValues("linkCity[]"));

        String teamMsg = request.getParameter("teamMsg");
        String discountId = request.getParameter("discountId");
//        String discountName = request.getParameter("discountName");

        String startTime = request.getParameter("startDate");
        String endTime = request.getParameter("endDate");

        if (CollectionUtils.isNotEmpty(city)) {
            province = null;
        }

        try {

            Date startDate = DateUtil.stringToDate(startTime, DateUtil.webFormat);
            Date endDate = DateUtil.stringToDate(endTime, DateUtil.webFormat);
            if (null != endDate)
                endDate = DateUtil.addDays(endDate, 1);

            //            List<Map<String, Object>> queryAllDiscountUseList = orderDAO.queryAllDiscountUseList(
            //                province, city, teamMsg, discountName, discountId, startDate, endDate, null, null);
            List<Map<String, Object>> queryAllDiscountUseList = orderDAO.queryAllDiscountUseListS(
                province, city, teamMsg, discountId, startDate, endDate, null, null);
            if (CollectionUtils.isEmpty(queryAllDiscountUseList)) {
                throw new RuntimeException("未找到相关数据");
            } else {

                for (Map<String, Object> map : queryAllDiscountUseList) {

                    if (null != map.get("gmtCreate")) {
                        Date gmtCreate = (Date) map.get("gmtCreate");
                        map.put("gmtCreate", DateUtil.dateToString(gmtCreate, DateUtil.newFormat));
                    }

                    String orderPrice_str = MapUtils.getString(map, "orderPrice");
                    Money orderPrice = new Money(orderPrice_str).divide(100);
                    map.put("orderPrice", orderPrice.toSimpleString());

                    String amount_str = MapUtils.getString(map, "amount");
                    BigDecimal rate = new BigDecimal(amount_str).divide(orderPrice.getAmount(), 4,
                        BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
                    map.put("discountRate", rate);

                    String teamId = MapUtils.getString(map, "teamId");
                    String teamErpNo = MapUtils.getString(map, "teamErpNo");
                    String teamName = MapUtils.getString(map, "teamName");

                    map.put("teamMsg", checkEmpty(teamId) + "/" + checkEmpty(teamErpNo) + "/"
                                       + checkEmpty(teamName));
                    
                    String discountId_this = MapUtils.getString(map, "discountId");
                    if(StringUtils.isNotBlank(discountId_this))
                        map.put("discountName", discountDAO.getDisCountName(discountId_this));
                }

                // 表头
                String[] headers = { "订单号", "子订单号", "会员手机号", "商品标题", "优惠券编号", "优惠券名称",
                        "门店编号/ERP编号/名称", "门店地址", "省/市", "订单原价", "优惠金额", "折扣比率（%）", "创建时间" };
                // 数据键名或者MODEL类字段名
                String[] Col = { "orderNo", "orderId", "userCell", "proName", "discountId",
                        "discountName", "teamMsg", "teamAddress", "teamCity", "orderPrice",
                        "amount", "discountRate", "gmtCreate" };

                //                ExportExcel<Map<String, Object>> ex = new ExportExcel<Map<String, Object>>();
                //                List<Map<String, Object>> excelList = queryAllDiscountUseList;
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
                    queryAllDiscountUseList, null);
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
