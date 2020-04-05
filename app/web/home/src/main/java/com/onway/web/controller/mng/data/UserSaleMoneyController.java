package com.onway.web.controller.mng.data;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.common.lang.CollectionUtils;
import com.onway.common.lang.Money;
import com.onway.model.enums.OrderStatusEnum;
import com.onway.utils.BigdecimalUtil;
import com.onway.utils.DateUtil;
import com.onway.utils.StringToListUtil;
import com.onway.utils.excel.ExportExcel;
import com.onway.web.controller.AbstractController;

/**
 * 会员消费统计控制类
 * @author zhoumx
 *
 */
@Controller
@RequestMapping("/userSale")
public class UserSaleMoneyController extends AbstractController {

    /**
     * 列表数据查询
     * 条件为 门店 推荐人 时间段以及只查询销售成功的记录
     * @param request
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "/userSaleMoney.do")
    @ResponseBody
    public JSONObject userSaleMoney(HttpServletRequest request, Integer limit, Integer offset) {
        //查询条件
        String[] teamIds = request.getParameterValues("teamIds[]");

        String userMsg = request.getParameter("userMsg");
        String reMsg = request.getParameter("reMsg");

        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        //返回结果集
        JSONObject data = new JSONObject();

        List<String> teamList = StringToListUtil.arrToList(teamIds);
        List<String> status = OrderStatusEnum.getSuccCode();

        Date startDate = DateUtil.stringToDate(startTime, DateUtil.webFormat);
        Date endDate = DateUtil.stringToDate(endTime, DateUtil.webFormat);
        if (null != endDate)
            endDate = DateUtil.addDays(endDate, 1);

        BigDecimal sumAllMoney = orderDAO.sumAllMoney(teamList, startDate, endDate, status);

        List<Map<String, Object>> userSale = orderDAO.userSaleMoney(teamList, userMsg, reMsg,
            startDate, endDate, status, offset, limit);

        int count = orderDAO.userSaleMoneyCount(teamList, userMsg, reMsg, startDate, endDate,
            status);

        Money realOrderAmount_page = new Money(0);
        for (Map<String, Object> map : userSale) {
            BigDecimal realOrderAmount = (BigDecimal) map.get("realOrderAmount");
            Money realOrderAmount_ = BigdecimalUtil.toMoney(realOrderAmount);
            realOrderAmount_page = realOrderAmount_page.add(realOrderAmount_);
            map.put("realOrderAmount", realOrderAmount_.toSimpleString());
            BigDecimal rate = realOrderAmount.divide(sumAllMoney, 4, BigDecimal.ROUND_HALF_UP);
            map.put("rate", rate.multiply(new BigDecimal(100)).doubleValue());
        }
        data.put("rows", userSale);
        data.put("total", count);

        data.put("realOrderAmount_page", realOrderAmount_page.toSimpleString());

        BigDecimal realOrderAmountSum = orderDAO.userSaleMoneySum(teamList, userMsg, reMsg,
            startDate, endDate, status);
        Money realOrderAmountSum_ = BigdecimalUtil.toMoney(realOrderAmountSum);
        data.put("realOrderAmountSum", realOrderAmountSum_.toSimpleString());

        return data;
    }

    @RequestMapping(value = "/exportUserSaleMoney.do")
    @ResponseBody
    public void exportUserSaleMoney(HttpServletRequest request, HttpServletResponse response) {
        //查询条件
        String[] teamIds = request.getParameterValues("teamIds[]");

        String userMsg = request.getParameter("userMsg");
        String reMsg = request.getParameter("reMsg");

        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");

        List<String> teamList = StringToListUtil.arrToList(teamIds);
        List<String> status = OrderStatusEnum.getSuccCode();

        Date startDate = DateUtil.stringToDate(startTime, DateUtil.webFormat);
        Date endDate = DateUtil.stringToDate(endTime, DateUtil.webFormat);
        if (null != endDate)
            endDate = DateUtil.addDays(endDate, 1);

        BigDecimal sumAllMoney = orderDAO.sumAllMoney(teamList, startDate, endDate, status);

        List<Map<String, Object>> userSale = orderDAO.userSaleMoney(teamList, userMsg, reMsg,
            startDate, endDate, status, null, null);

        for (Map<String, Object> map : userSale) {
            BigDecimal realOrderAmount = (BigDecimal) map.get("realOrderAmount");
            map.put("realOrderAmount", BigdecimalUtil.toMoney(realOrderAmount).toSimpleString());
            BigDecimal rate = realOrderAmount.divide(sumAllMoney, 4, BigDecimal.ROUND_HALF_UP);
            map.put("rate", rate.multiply(new BigDecimal(100)).doubleValue());
        }

        try {
            if (CollectionUtils.isEmpty(userSale)) {
                return;
            } else {
                // 表头
                String[] headers = { "会员姓名", "消费总额", "会员消费占比（%）", "联系方式" };
                // 数据键名或者MODEL类字段名
                String[] Col = { "userName", "realOrderAmount", "rate", "userCell" };

                //                ExportExcel<Map<String, Object>> ex = new ExportExcel<Map<String, Object>>();
                //                List<Map<String, Object>> excelList = userSale;
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
                HSSFWorkbook book = ExportExcel.exportExcel(fileName, headers, Col, userSale, null);
                //写入数据
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + fileName);
                book.write(response.getOutputStream());

                response.getOutputStream().flush();
                response.getOutputStream().close();
            }
        } catch (Exception e) {
            logger.error("exportUserSaleMoney：导出异常");
        }
    }

}
