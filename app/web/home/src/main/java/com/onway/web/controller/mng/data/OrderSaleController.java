package com.onway.web.controller.mng.data;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
import com.onway.fyapp.common.dal.dataobject.TeamDO;
import com.onway.fyapp.common.dal.dataobject.UserDO;
import com.onway.model.enums.AccountTypeEnum;
import com.onway.model.enums.CalTypeEnum;
import com.onway.model.enums.CheckStatusEnum;
import com.onway.model.enums.OrderStatusEnum;
import com.onway.model.enums.OrderSubStatusEnum;
import com.onway.model.enums.SexEnum;
import com.onway.model.enums.StatusEnum;
import com.onway.model.enums.SysConfigCacheKeyEnum;
import com.onway.model.enums.TeamLevelEnum;
import com.onway.model.enums.UserLevelEnum;
import com.onway.result.DateCal;
import com.onway.result.OrderDiscountRate;
import com.onway.result.OrderSalePrice;
import com.onway.utils.BigdecimalUtil;
import com.onway.utils.DateUtil;
import com.onway.utils.ExportExcel;
import com.onway.utils.ReduceAmountUtil;
import com.onway.utils.StringToListUtil;
import com.onway.web.controller.AbstractController;

/**
 * 销售统计
 * 
 * @author yuhang.ni
 * @version $Id: OrderSaleController.java, v 0.1 2019年4月9日 下午3:16:40 Administrator Exp $
 */
@Controller
@RequestMapping("/data")
public class OrderSaleController extends AbstractController {

    /**
     * 门店销售额环比/同比
     * 
     * @param request
     * @return
     */
    @RequestMapping("/calculateStoreSalesMoney.do")
    @ResponseBody
    public JSONObject calculateStoreSalesMoney(HttpServletRequest request) {

        String[] teamIds = request.getParameterValues("teamIds[]");
        String calType = request.getParameter("calType");//TB   HB

        String startTime = request.getParameter("startDate");
        String endTime = request.getParameter("endDate");

        //YEAR   YEAR_HALF   MOUNTH     DAYS      HOURS
        String timeType = request.getParameter("timeType");

        JSONObject jo = new JSONObject();

        List<String> teamList = StringToListUtil.arrToList(teamIds);
        List<String> orderStatusList = OrderStatusEnum.getSuccCode();

        List<OrderSalePrice> saleTb = new ArrayList<OrderSalePrice>();
        //同比
        if (StringUtils.equals(calType, CalTypeEnum.TB.getCode())) {
            if (StringUtils.equals(timeType, "YEAR")) {
                List<DateCal> dateList = DateUtil.getYearsTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoney(teamList, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "YEAR_HALF")) {
                List<DateCal> dateList = DateUtil.getHalfYearsTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoney(teamList, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "MOUNTH")) {
                List<DateCal> dateList = DateUtil.getMounthsTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoney(teamList, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "DAYS")) {
                List<DateCal> dateList = DateUtil.getDaysTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoney(teamList, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "HOURS")) {
                List<DateCal> dateList = DateUtil.getHoursByDayTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoney(teamList, orderStatusList, dateCal, saleTb);
                }
            }
        }

        //环比
        if (StringUtils.equals(calType, CalTypeEnum.HB.getCode())) {
            if (StringUtils.equals(timeType, "YEAR")) {
                List<DateCal> dateList = DateUtil.getYearsHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoney(teamList, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "YEAR_HALF")) {
                List<DateCal> dateList = DateUtil.getHalfYearsHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoney(teamList, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "MOUNTH")) {
                List<DateCal> dateList = DateUtil.getMounthsHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoney(teamList, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "DAYS")) {
                List<DateCal> dateList = DateUtil.getDaysHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoney(teamList, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "HOURS")) {
                List<DateCal> dateList = DateUtil.getHoursByDayHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoney(teamList, orderStatusList, dateCal, saleTb);
                }
            }
        }

        Collections.sort(saleTb, new Comparator<OrderSalePrice>() {
            @Override
            public int compare(OrderSalePrice o1, OrderSalePrice o2) {
                String dateA = o1.getDateA();
                String dateB = o2.getDateB();
                if (dateA.compareTo(dateB) < 0) {
                    return 1;
                } else if (dateA.compareTo(dateB) > 0) {
                    return -1;
                }
                return 0;
            }
        });

        jo.put("rows", saleTb);
        return jo;
    }

    @RequestMapping("/exportCalculateStoreSalesMoney.do")
    public void exportCalculateStoreSalesMoney(HttpServletRequest request,
                                               HttpServletResponse response) {

        String[] teamIds = request.getParameterValues("teamIds[]");
        String calType = request.getParameter("calType");//TB   HB

        String startTime = request.getParameter("startDate");
        String endTime = request.getParameter("endDate");

        //YEAR   YEAR_HALF   MOUNTH     DAYS      HOURS
        String timeType = request.getParameter("timeType");

        List<String> teamList = StringToListUtil.arrToList(teamIds);
        List<String> orderStatusList = OrderStatusEnum.getSuccCode();

        List<OrderSalePrice> saleTb = new ArrayList<OrderSalePrice>();
        //同比
        if (StringUtils.equals(calType, CalTypeEnum.TB.getCode())) {
            if (StringUtils.equals(timeType, "YEAR")) {
                List<DateCal> dateList = DateUtil.getYearsTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoney(teamList, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "YEAR_HALF")) {
                List<DateCal> dateList = DateUtil.getHalfYearsTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoney(teamList, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "MOUNTH")) {
                List<DateCal> dateList = DateUtil.getMounthsTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoney(teamList, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "DAYS")) {
                List<DateCal> dateList = DateUtil.getDaysTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoney(teamList, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "HOURS")) {
                List<DateCal> dateList = DateUtil.getHoursByDayTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoney(teamList, orderStatusList, dateCal, saleTb);
                }
            }
        }

        //环比
        if (StringUtils.equals(calType, CalTypeEnum.HB.getCode())) {
            if (StringUtils.equals(timeType, "YEAR")) {
                List<DateCal> dateList = DateUtil.getYearsHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoney(teamList, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "YEAR_HALF")) {
                List<DateCal> dateList = DateUtil.getHalfYearsHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoney(teamList, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "MOUNTH")) {
                List<DateCal> dateList = DateUtil.getMounthsHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoney(teamList, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "DAYS")) {
                List<DateCal> dateList = DateUtil.getDaysHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoney(teamList, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "HOURS")) {
                List<DateCal> dateList = DateUtil.getHoursByDayHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoney(teamList, orderStatusList, dateCal, saleTb);
                }
            }
        }

        Collections.sort(saleTb, new Comparator<OrderSalePrice>() {
            @Override
            public int compare(OrderSalePrice o1, OrderSalePrice o2) {
                String dateA = o1.getDateA();
                String dateB = o2.getDateB();
                if (dateA.compareTo(dateB) < 0) {
                    return 1;
                } else if (dateA.compareTo(dateB) > 0) {
                    return -1;
                }
                return 0;
            }
        });

        try {
            if (CollectionUtils.isEmpty(saleTb)) {
                return;
            } else {
                // 表头
                String[] headers = { "当前日期", "当前日期销售额", "对比日期", "对比日期销售额", "环比/同比率（%）" };
                // 数据键名或者MODEL类字段名
                String[] Col = { "dateA", "priceA", "dateB", "priceB", "link_ratio_money" };

                ExportExcel<OrderSalePrice> ex = new ExportExcel<OrderSalePrice>();
                List<OrderSalePrice> excelList = saleTb;
                // 生成Excel
                HSSFWorkbook workbook = ex.exportExcel("sheet1", headers, Col, excelList, null);
                // 下载
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + "table.xls");
                OutputStream ouputStream = response.getOutputStream();
                workbook.write(ouputStream);
                ouputStream.flush();
                ouputStream.close();
            }
        } catch (Exception e) {
            logger.error("exportCalculateStoreSalesMoney：导出异常");
        }
    }

    private void calculateStoreSalesMoney(List<String> teamList, List<String> orderStatusList,
                                          DateCal dateCal, List<OrderSalePrice> saleTb) {
        Map<String, Object> calculateSalesAmount = orderDAO.calculateSalesAmount(teamList,
            orderStatusList, dateCal.getStartDate(), dateCal.getEndDate(), dateCal.getStartDates(),
            dateCal.getEndDates());
        OrderSalePrice orderSalePrice = new OrderSalePrice();
        orderSalePrice.setDateA(dateCal.getDateFormat());
        orderSalePrice.setDateB(dateCal.getDateFormats());
        if (null == calculateSalesAmount) {
            orderSalePrice.setPriceA("0.00");
            orderSalePrice.setPriceB("0.00");
            orderSalePrice.setLink_ratio_money(0.00);
        } else {
            Money realPriceA = BigdecimalUtil.toMoney((BigDecimal) calculateSalesAmount
                .get("realPriceA"));
            Money realPriceB = BigdecimalUtil.toMoney((BigDecimal) calculateSalesAmount
                .get("realPriceB"));
            orderSalePrice.setPriceA(realPriceA.toSimpleString());
            orderSalePrice.setPriceB(realPriceB.toSimpleString());

            BigDecimal a = (BigDecimal) calculateSalesAmount.get("realPriceA");
            BigDecimal b = (BigDecimal) calculateSalesAmount.get("realPriceB");
            orderSalePrice.setLink_ratio_money(BigdecimalUtil.cal(a, b));
        }
        saleTb.add(orderSalePrice);
    }

    /**
     * 图表
     * 
     * @param request
     * @return
     */
    @RequestMapping("/calculateStoreSalesMoneyCharts.do")
    @ResponseBody
    public JSONObject calculateStoreSalesMoneyCharts(HttpServletRequest request) {

        String[] teamIds = request.getParameterValues("teamIds[]");
        String calType = request.getParameter("calType");//TB   HB

        String startTime = request.getParameter("startDate");
        String endTime = request.getParameter("endDate");

        //YEAR   YEAR_HALF   MOUNTH     DAYS      HOURS
        String timeType = request.getParameter("timeType");

        JSONObject jo = new JSONObject();

        List<String> teamList = StringToListUtil.arrToList(teamIds);
        List<String> orderStatusList = OrderStatusEnum.getSuccCode();

        //同比
        if (StringUtils.equals(calType, CalTypeEnum.TB.getCode())) {
            List<String> xAxisData = new LinkedList<String>();
            List<String> seriesDataA = new LinkedList<String>();
            List<String> seriesDataB = new LinkedList<String>();

            if (StringUtils.equals(timeType, "YEAR")) {
                List<DateCal> dateList = DateUtil.getYearsTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoneyCharts(teamList, orderStatusList, dateCal, xAxisData,
                        seriesDataA, seriesDataB);
                }
            } else if (StringUtils.equals(timeType, "YEAR_HALF")) {
                List<DateCal> dateList = DateUtil.getHalfYearsTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoneyCharts(teamList, orderStatusList, dateCal, xAxisData,
                        seriesDataA, seriesDataB);
                }
            } else if (StringUtils.equals(timeType, "MOUNTH")) {
                List<DateCal> dateList = DateUtil.getMounthsTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoneyCharts(teamList, orderStatusList, dateCal, xAxisData,
                        seriesDataA, seriesDataB);
                }
            } else if (StringUtils.equals(timeType, "DAYS")) {
                List<DateCal> dateList = DateUtil.getDaysTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoneyCharts(teamList, orderStatusList, dateCal, xAxisData,
                        seriesDataA, seriesDataB);
                }
            } else if (StringUtils.equals(timeType, "HOURS")) {
                List<DateCal> dateList = DateUtil.getHoursByDayTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoneyCharts(teamList, orderStatusList, dateCal, xAxisData,
                        seriesDataA, seriesDataB);
                }
            }
            jo.put("xAxisData", xAxisData);
            jo.put("seriesDataA", seriesDataA);
            jo.put("seriesDataB", seriesDataB);
            return jo;
        }

        //环比
        if (StringUtils.equals(calType, CalTypeEnum.HB.getCode())) {
            List<String> xAxisData = new LinkedList<String>();
            List<String> seriesDataA = new LinkedList<String>();
            List<String> seriesDataB = new LinkedList<String>();
            if (StringUtils.equals(timeType, "YEAR")) {
                List<DateCal> dateList = DateUtil.getYearsHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoneyCharts(teamList, orderStatusList, dateCal, xAxisData,
                        seriesDataA, seriesDataB);
                }
            } else if (StringUtils.equals(timeType, "YEAR_HALF")) {
                List<DateCal> dateList = DateUtil.getHalfYearsHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoneyCharts(teamList, orderStatusList, dateCal, xAxisData,
                        seriesDataA, seriesDataB);
                }
            } else if (StringUtils.equals(timeType, "MOUNTH")) {
                List<DateCal> dateList = DateUtil.getMounthsHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoneyCharts(teamList, orderStatusList, dateCal, xAxisData,
                        seriesDataA, seriesDataB);
                }
            } else if (StringUtils.equals(timeType, "DAYS")) {
                List<DateCal> dateList = DateUtil.getDaysHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoneyCharts(teamList, orderStatusList, dateCal, xAxisData,
                        seriesDataA, seriesDataB);
                }
            } else if (StringUtils.equals(timeType, "HOURS")) {
                List<DateCal> dateList = DateUtil.getHoursByDayHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateStoreSalesMoneyCharts(teamList, orderStatusList, dateCal, xAxisData,
                        seriesDataA, seriesDataB);
                }
            }
            jo.put("xAxisData", xAxisData);
            jo.put("seriesDataA", seriesDataA);
            jo.put("seriesDataB", seriesDataB);
            return jo;
        }
        return jo;
    }

    private void calculateStoreSalesMoneyCharts(List<String> teamList,
                                                List<String> orderStatusList, DateCal dateCal,
                                                List<String> xAxisData, List<String> seriesDataA,
                                                List<String> seriesDataB) {
        Map<String, Object> calculateSalesAmount = orderDAO.calculateSalesAmount(teamList,
            orderStatusList, dateCal.getStartDate(), dateCal.getEndDate(), dateCal.getStartDates(),
            dateCal.getEndDates());
        xAxisData.add(dateCal.getDateFormat());
        if (null == calculateSalesAmount) {
            seriesDataA.add("0.00");
            seriesDataB.add("0.00");
        } else {
            Money realPriceA = BigdecimalUtil.toMoney((BigDecimal) calculateSalesAmount
                .get("realPriceA"));
            seriesDataA.add(realPriceA.toSimpleString());
            Money realPriceB = BigdecimalUtil.toMoney((BigDecimal) calculateSalesAmount
                .get("realPriceB"));
            seriesDataB.add(realPriceB.toSimpleString());
        }
    }

    /**
     * 商品分类销售额环比/同比
     * 
     * @param request
     * @return
     */
    @RequestMapping("/calculateCategorySalesMoney.do")
    @ResponseBody
    public JSONObject calculateCategorySalesMoney(HttpServletRequest request) {

        String province = getProCitName(request.getParameter("linkProvince"));
        String city = getProCitName(request.getParameter("linkCity"));

        String[] teamIds = request.getParameterValues("teamIds[]");
        String[] teamLevels = request.getParameterValues("teamLevels[]");

        String calType = request.getParameter("calType");//TB   HB

        String timeType = request.getParameter("timeType");

        String startTime = request.getParameter("startDate");
        String endTime = request.getParameter("endDate");

        String proMsg = request.getParameter("proMsg");

        JSONObject jo = new JSONObject();

        List<String> childCategorysList = getClassList(request);

        List<String> teamId = StringToListUtil.arrToList(teamIds);
        List<String> teamLevel = StringToListUtil.arrToList(teamLevels);

        List<String> orderStatusList = OrderStatusEnum.getSuccCode();

        List<OrderSalePrice> saleTb = new ArrayList<OrderSalePrice>();
        //同比
        if (StringUtils.equals(calType, CalTypeEnum.TB.getCode())) {
            if (StringUtils.equals(timeType, "YEAR")) {
                List<DateCal> dateList = DateUtil.getYearsTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoney(proMsg, province, city, childCategorysList, teamId,
                        teamLevel, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "YEAR_HALF")) {
                List<DateCal> dateList = DateUtil.getHalfYearsTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoney(proMsg, province, city, childCategorysList, teamId,
                        teamLevel, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "MOUNTH")) {
                List<DateCal> dateList = DateUtil.getMounthsTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoney(proMsg, province, city, childCategorysList, teamId,
                        teamLevel, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "DAYS")) {
                List<DateCal> dateList = DateUtil.getDaysTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoney(proMsg, province, city, childCategorysList, teamId,
                        teamLevel, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "HOURS")) {
                List<DateCal> dateList = DateUtil.getHoursByDayTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoney(proMsg, province, city, childCategorysList, teamId,
                        teamLevel, orderStatusList, dateCal, saleTb);
                }
            }
        }

        //环比
        if (StringUtils.equals(calType, CalTypeEnum.HB.getCode())) {
            if (StringUtils.equals(timeType, "YEAR")) {
                List<DateCal> dateList = DateUtil.getYearsHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoney(proMsg, province, city, childCategorysList, teamId,
                        teamLevel, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "YEAR_HALF")) {
                List<DateCal> dateList = DateUtil.getHalfYearsHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoney(proMsg, province, city, childCategorysList, teamId,
                        teamLevel, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "MOUNTH")) {
                List<DateCal> dateList = DateUtil.getMounthsHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoney(proMsg, province, city, childCategorysList, teamId,
                        teamLevel, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "DAYS")) {
                List<DateCal> dateList = DateUtil.getDaysHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoney(proMsg, province, city, childCategorysList, teamId,
                        teamLevel, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "HOURS")) {
                List<DateCal> dateList = DateUtil.getHoursByDayHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoney(proMsg, province, city, childCategorysList, teamId,
                        teamLevel, orderStatusList, dateCal, saleTb);
                }
            }
        }

        Collections.sort(saleTb, new Comparator<OrderSalePrice>() {
            @Override
            public int compare(OrderSalePrice o1, OrderSalePrice o2) {
                String dateA = o1.getDateA();
                String dateB = o2.getDateB();
                if (dateA.compareTo(dateB) < 0) {
                    return 1;
                } else if (dateA.compareTo(dateB) > 0) {
                    return -1;
                }
                return 0;
            }
        });

        jo.put("rows", saleTb);
        return jo;
    }

    @RequestMapping("/exportCalculateCategorySalesMoney.do")
    public void exportCalculateCategorySalesMoney(HttpServletRequest request,
                                                  HttpServletResponse response) {

        String province = getProCitName(request.getParameter("linkProvince"));
        String city = getProCitName(request.getParameter("linkCity"));

        String[] teamIds = request.getParameterValues("teamIds[]");
        String[] teamLevels = request.getParameterValues("teamLevels[]");

        String calType = request.getParameter("calType");//TB   HB

        String timeType = request.getParameter("timeType");

        String startTime = request.getParameter("startDate");
        String endTime = request.getParameter("endDate");

        String proMsg = request.getParameter("proMsg");

        List<String> childCategorysList = getClassList(request);

        List<String> teamId = StringToListUtil.arrToList(teamIds);
        List<String> teamLevel = StringToListUtil.arrToList(teamLevels);

        List<String> orderStatusList = OrderStatusEnum.getSuccCode();

        List<OrderSalePrice> saleTb = new ArrayList<OrderSalePrice>();
        //同比
        if (StringUtils.equals(calType, CalTypeEnum.TB.getCode())) {
            if (StringUtils.equals(timeType, "YEAR")) {
                List<DateCal> dateList = DateUtil.getYearsTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoney(proMsg, province, city, childCategorysList, teamId,
                        teamLevel, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "YEAR_HALF")) {
                List<DateCal> dateList = DateUtil.getHalfYearsTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoney(proMsg, province, city, childCategorysList, teamId,
                        teamLevel, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "MOUNTH")) {
                List<DateCal> dateList = DateUtil.getMounthsTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoney(proMsg, province, city, childCategorysList, teamId,
                        teamLevel, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "DAYS")) {
                List<DateCal> dateList = DateUtil.getDaysTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoney(proMsg, province, city, childCategorysList, teamId,
                        teamLevel, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "HOURS")) {
                List<DateCal> dateList = DateUtil.getHoursByDayTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoney(proMsg, province, city, childCategorysList, teamId,
                        teamLevel, orderStatusList, dateCal, saleTb);
                }
            }
        }

        //环比
        if (StringUtils.equals(calType, CalTypeEnum.HB.getCode())) {
            if (StringUtils.equals(timeType, "YEAR")) {
                List<DateCal> dateList = DateUtil.getYearsHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoney(proMsg, province, city, childCategorysList, teamId,
                        teamLevel, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "YEAR_HALF")) {
                List<DateCal> dateList = DateUtil.getHalfYearsHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoney(proMsg, province, city, childCategorysList, teamId,
                        teamLevel, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "MOUNTH")) {
                List<DateCal> dateList = DateUtil.getMounthsHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoney(proMsg, province, city, childCategorysList, teamId,
                        teamLevel, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "DAYS")) {
                List<DateCal> dateList = DateUtil.getDaysHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoney(proMsg, province, city, childCategorysList, teamId,
                        teamLevel, orderStatusList, dateCal, saleTb);
                }
            } else if (StringUtils.equals(timeType, "HOURS")) {
                List<DateCal> dateList = DateUtil.getHoursByDayHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoney(proMsg, province, city, childCategorysList, teamId,
                        teamLevel, orderStatusList, dateCal, saleTb);
                }
            }
        }

        Collections.sort(saleTb, new Comparator<OrderSalePrice>() {
            @Override
            public int compare(OrderSalePrice o1, OrderSalePrice o2) {
                String dateA = o1.getDateA();
                String dateB = o2.getDateB();
                if (dateA.compareTo(dateB) < 0) {
                    return 1;
                } else if (dateA.compareTo(dateB) > 0) {
                    return -1;
                }
                return 0;
            }
        });

        try {
            if (CollectionUtils.isEmpty(saleTb)) {
                return;
            } else {

                // 表头
                String[] headers = { "当前日期", "当前日期销售额", "当前日期销量", "对比日期", "对比日期销售额", "对比日期销量",
                        "（销售额）环比/同比率（%）", "（销量）环比/同比率（%）" };
                // 数据键名或者MODEL类字段名
                String[] Col = { "dateA", "priceA", "numA", "dateB", "priceB", "numB", "link_ratio_money",
                        "link_ratio_num" };

                ExportExcel<OrderSalePrice> ex = new ExportExcel<OrderSalePrice>();
                List<OrderSalePrice> excelList = saleTb;
                // 生成Excel
                HSSFWorkbook workbook = ex.exportExcel("sheet1", headers, Col, excelList, null);
                // 下载
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + "table.xls");
                OutputStream ouputStream = response.getOutputStream();
                workbook.write(ouputStream);
                ouputStream.flush();
                ouputStream.close();
            }
        } catch (Exception e) {
            logger.error("exportCalculateCategorySalesMoney：导出异常");
        }
    }

    private void calculateCategorySalesMoney(String proMsg, String province, String city,
                                             List<String> childCategorysList, List<String> teamId,
                                             List<String> teamLevel, List<String> orderStatusList,
                                             DateCal dateCal, List<OrderSalePrice> saleTb) {
        if (StringUtils.isNotBlank(city)) {
            province = null;
        }
        Map<String, Object> calculateSalesAmount = orderDAO.calculateSalesAmountCategory(proMsg,
            province, city, childCategorysList, teamId, teamLevel, orderStatusList,
            dateCal.getStartDate(), dateCal.getEndDate(), dateCal.getStartDates(),
            dateCal.getEndDates());
        OrderSalePrice orderSalePrice = new OrderSalePrice();
        orderSalePrice.setDateA(dateCal.getDateFormat());
        orderSalePrice.setDateB(dateCal.getDateFormats());
        if (null == calculateSalesAmount) {
            orderSalePrice.setPriceA("0.00");
            orderSalePrice.setPriceB("0.00");
            orderSalePrice.setLink_ratio_money(0.00);
            orderSalePrice.setNumA(new BigDecimal(0));
            orderSalePrice.setNumB(new BigDecimal(0));
            orderSalePrice.setLink_ratio_num(0.00);
        } else {
            Money realPriceA = BigdecimalUtil.toMoney((BigDecimal) calculateSalesAmount
                .get("realPriceA"));
            Money realPriceB = BigdecimalUtil.toMoney((BigDecimal) calculateSalesAmount
                .get("realPriceB"));
            orderSalePrice.setPriceA(realPriceA.toSimpleString());
            orderSalePrice.setPriceB(realPriceB.toSimpleString());

            BigDecimal a = (BigDecimal) calculateSalesAmount.get("realPriceA");
            BigDecimal b = (BigDecimal) calculateSalesAmount.get("realPriceB");
            orderSalePrice.setLink_ratio_money(BigdecimalUtil.cal(a, b));

            BigDecimal saleNumA = new BigDecimal(MapUtils.getDouble(calculateSalesAmount,
                "saleNumA")).setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal saleNumB = new BigDecimal(MapUtils.getDouble(calculateSalesAmount,
                "saleNumB")).setScale(2, BigDecimal.ROUND_HALF_UP);

            orderSalePrice.setNumA(saleNumA);
            orderSalePrice.setNumB(saleNumB);
            orderSalePrice.setLink_ratio_num(BigdecimalUtil.cal(saleNumA, saleNumB));
        }
        saleTb.add(orderSalePrice);
    }

    /**
     * 商品分类销售额环比/同比   图表数据
     * 
     * @param request
     * @return
     */
    @RequestMapping("/calculateCategorySalesMoneyCharts.do")
    @ResponseBody
    public JSONObject calculateCategorySalesMoneyCharts(HttpServletRequest request) {

        String province = getProCitName(request.getParameter("linkProvince"));
        String city = getProCitName(request.getParameter("linkCity"));

        String[] teamIds = request.getParameterValues("teamIds[]");
        String[] teamLevels = request.getParameterValues("teamLevels[]");

        String calType = request.getParameter("calType");//TB   HB
        String proMsg = request.getParameter("proMsg");

        String timeType = request.getParameter("timeType");//MOUNTH   YEAR
        String startTime = request.getParameter("startDate");
        String endTime = request.getParameter("endDate");

        JSONObject jo = new JSONObject();

        List<String> childCategorysList = getClassList(request);

        List<String> teamId = StringToListUtil.arrToList(teamIds);
        List<String> teamLevel = StringToListUtil.arrToList(teamLevels);

        List<String> orderStatusList = OrderStatusEnum.getSuccCode();

        //同比
        if (StringUtils.equals(calType, CalTypeEnum.TB.getCode())) {
            List<String> xAxisData = new LinkedList<String>();
            List<String> seriesPriceA = new LinkedList<String>();
            List<String> seriesPriceB = new LinkedList<String>();
            List<Double> seriesNumA = new LinkedList<Double>();
            List<Double> seriesNumB = new LinkedList<Double>();

            if (StringUtils.equals(timeType, "YEAR")) {
                List<DateCal> dateList = DateUtil.getYearsTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoneyCharts(proMsg, province, city, childCategorysList,
                        teamId, teamLevel, orderStatusList, dateCal, xAxisData, seriesPriceA,
                        seriesPriceB, seriesNumA, seriesNumB);
                }
            } else if (StringUtils.equals(timeType, "YEAR_HALF")) {
                List<DateCal> dateList = DateUtil.getHalfYearsTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoneyCharts(proMsg, province, city, childCategorysList,
                        teamId, teamLevel, orderStatusList, dateCal, xAxisData, seriesPriceA,
                        seriesPriceB, seriesNumA, seriesNumB);
                }
            } else if (StringUtils.equals(timeType, "MOUNTH")) {
                List<DateCal> dateList = DateUtil.getMounthsTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoneyCharts(proMsg, province, city, childCategorysList,
                        teamId, teamLevel, orderStatusList, dateCal, xAxisData, seriesPriceA,
                        seriesPriceB, seriesNumA, seriesNumB);
                }
            } else if (StringUtils.equals(timeType, "DAYS")) {
                List<DateCal> dateList = DateUtil.getDaysTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoneyCharts(proMsg, province, city, childCategorysList,
                        teamId, teamLevel, orderStatusList, dateCal, xAxisData, seriesPriceA,
                        seriesPriceB, seriesNumA, seriesNumB);
                }
            } else if (StringUtils.equals(timeType, "HOURS")) {
                List<DateCal> dateList = DateUtil.getHoursByDayTB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoneyCharts(proMsg, province, city, childCategorysList,
                        teamId, teamLevel, orderStatusList, dateCal, xAxisData, seriesPriceA,
                        seriesPriceB, seriesNumA, seriesNumB);
                }
            }
            jo.put("xAxisData", xAxisData);
            jo.put("seriesPriceA", seriesPriceA);
            jo.put("seriesPriceB", seriesPriceB);
            jo.put("seriesNumA", seriesNumA);
            jo.put("seriesNumB", seriesNumB);
            return jo;
        }

        //环比
        if (StringUtils.equals(calType, CalTypeEnum.HB.getCode())) {
            List<String> xAxisData = new LinkedList<String>();
            List<String> seriesPriceA = new LinkedList<String>();
            List<String> seriesPriceB = new LinkedList<String>();
            List<Double> seriesNumA = new LinkedList<Double>();
            List<Double> seriesNumB = new LinkedList<Double>();
            if (StringUtils.equals(timeType, "YEAR")) {
                List<DateCal> dateList = DateUtil.getYearsHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoneyCharts(proMsg, province, city, childCategorysList,
                        teamId, teamLevel, orderStatusList, dateCal, xAxisData, seriesPriceA,
                        seriesPriceB, seriesNumA, seriesNumB);
                }
            } else if (StringUtils.equals(timeType, "YEAR_HALF")) {
                List<DateCal> dateList = DateUtil.getHalfYearsHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoneyCharts(proMsg, province, city, childCategorysList,
                        teamId, teamLevel, orderStatusList, dateCal, xAxisData, seriesPriceA,
                        seriesPriceB, seriesNumA, seriesNumB);
                }
            } else if (StringUtils.equals(timeType, "MOUNTH")) {
                List<DateCal> dateList = DateUtil.getMounthsHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoneyCharts(proMsg, province, city, childCategorysList,
                        teamId, teamLevel, orderStatusList, dateCal, xAxisData, seriesPriceA,
                        seriesPriceB, seriesNumA, seriesNumB);
                }
            } else if (StringUtils.equals(timeType, "DAYS")) {
                List<DateCal> dateList = DateUtil.getDaysHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoneyCharts(proMsg, province, city, childCategorysList,
                        teamId, teamLevel, orderStatusList, dateCal, xAxisData, seriesPriceA,
                        seriesPriceB, seriesNumA, seriesNumB);
                }
            } else if (StringUtils.equals(timeType, "HOURS")) {
                List<DateCal> dateList = DateUtil.getHoursByDayHB(startTime, endTime);
                for (DateCal dateCal : dateList) {
                    calculateCategorySalesMoneyCharts(proMsg, province, city, childCategorysList,
                        teamId, teamLevel, orderStatusList, dateCal, xAxisData, seriesPriceA,
                        seriesPriceB, seriesNumA, seriesNumB);
                }
            }
            jo.put("xAxisData", xAxisData);
            jo.put("seriesPriceA", seriesPriceA);
            jo.put("seriesPriceB", seriesPriceB);
            jo.put("seriesNumA", seriesNumA);
            jo.put("seriesNumB", seriesNumB);
            return jo;
        }
        return jo;
    }

    private void calculateCategorySalesMoneyCharts(String proMsg, String province, String city,
                                                   List<String> childCategorysList,
                                                   List<String> teamId, List<String> teamLevel,
                                                   List<String> orderStatusList, DateCal dateCal,
                                                   List<String> xAxisData,
                                                   List<String> seriesPriceA,
                                                   List<String> seriesPriceB,
                                                   List<Double> seriesNumA, List<Double> seriesNumB) {
        if (StringUtils.isNotBlank(city)) {
            province = null;
        }
        Map<String, Object> calculateSalesAmountCategory = orderDAO.calculateSalesAmountCategory(
            proMsg, province, city, childCategorysList, teamId, teamLevel, orderStatusList,
            dateCal.getStartDate(), dateCal.getEndDate(), dateCal.getStartDates(),
            dateCal.getEndDates());
        xAxisData.add(dateCal.getDateFormat());
        if (null == calculateSalesAmountCategory) {
            seriesPriceA.add("0.00");
            seriesPriceB.add("0.00");
            seriesNumA.add(0.00);
            seriesNumB.add(0.00);
        } else {
            Money realPriceA = BigdecimalUtil.toMoney((BigDecimal) calculateSalesAmountCategory
                .get("realPriceA"));
            seriesPriceA.add(realPriceA.toSimpleString());
            Money realPriceB = BigdecimalUtil.toMoney((BigDecimal) calculateSalesAmountCategory
                .get("realPriceB"));
            seriesPriceB.add(realPriceB.toSimpleString());

            Double saleNumA = MapUtils.getDouble(calculateSalesAmountCategory, "saleNumA");
            Double saleNumB = MapUtils.getDouble(calculateSalesAmountCategory, "saleNumB");
            seriesNumA.add(saleNumA);
            seriesNumB.add(saleNumB);
        }
    }

    /**
     * 门店   平均折扣
     * 门店折扣分析
     * @param request
     * @return
     */
    @RequestMapping("/calculateSalesDiscountRate.do")
    @ResponseBody
    public JSONObject calculateSalesDiscountRate(HttpServletRequest request) {

        String[] teamIds = request.getParameterValues("teamIds[]");

        String showReduce = request.getParameter("showReduce");// 是 1  否  0   包含优惠券

        //YEAR   YEAR_HALF   MOUNTH     DAYS      HOURS
        String timeType = request.getParameter("timeType");

        String startTime = request.getParameter("startDate");
        String endTime = request.getParameter("endDate");

        JSONObject jo = new JSONObject();

        List<String> teamList = StringToListUtil.arrToList(teamIds);
        List<String> orderStatusList = OrderStatusEnum.getSuccCode();

        List<OrderDiscountRate> rateList = new ArrayList<OrderDiscountRate>();
        if (StringUtils.equals(timeType, "YEAR")) {
            List<DateCal> dateList = DateUtil.getYears(startTime, endTime);
            for (DateCal dateCal : dateList) {
                OrderDiscountRate orderDiscountRate = new OrderDiscountRate();
                calculateSalesDiscountRate(teamList, orderStatusList, dateCal, showReduce,
                    orderDiscountRate);
                orderDiscountRate.setDate(dateCal.getDateFormat());
                rateList.add(orderDiscountRate);
            }

        } else if (StringUtils.equals(timeType, "YEAR_HALF")) {
            List<DateCal> dateList = DateUtil.getHalfYears(startTime, endTime);
            for (DateCal dateCal : dateList) {
                OrderDiscountRate orderDiscountRate = new OrderDiscountRate();
                calculateSalesDiscountRate(teamList, orderStatusList, dateCal, showReduce,
                    orderDiscountRate);
                orderDiscountRate.setDate(dateCal.getDateFormat());
                rateList.add(orderDiscountRate);
            }
        } else if (StringUtils.equals(timeType, "MOUNTH")) {
            List<DateCal> dateList = DateUtil.getMounths(startTime, endTime);
            for (DateCal dateCal : dateList) {
                OrderDiscountRate orderDiscountRate = new OrderDiscountRate();
                calculateSalesDiscountRate(teamList, orderStatusList, dateCal, showReduce,
                    orderDiscountRate);
                orderDiscountRate.setDate(dateCal.getDateFormat());
                rateList.add(orderDiscountRate);
            }
        } else if (StringUtils.equals(timeType, "DAYS")) {
            List<DateCal> dateList = DateUtil.getDays(startTime, endTime);
            for (DateCal dateCal : dateList) {
                OrderDiscountRate orderDiscountRate = new OrderDiscountRate();
                calculateSalesDiscountRate(teamList, orderStatusList, dateCal, showReduce,
                    orderDiscountRate);
                orderDiscountRate.setDate(dateCal.getDateFormat());
                rateList.add(orderDiscountRate);
            }
        } else if (StringUtils.equals(timeType, "HOURS")) {
            List<DateCal> dateList = DateUtil.getHoursByDay(startTime, endTime);
            for (DateCal dateCal : dateList) {
                OrderDiscountRate orderDiscountRate = new OrderDiscountRate();
                calculateSalesDiscountRate(teamList, orderStatusList, dateCal, showReduce,
                    orderDiscountRate);
                orderDiscountRate.setDate(dateCal.getDateFormat());
                rateList.add(orderDiscountRate);
            }
        }

        BigDecimal realPayPrice_page = new BigDecimal(0);
        BigDecimal oldPayPrice_page = new BigDecimal(0);
        double rate_page = 0D;
        for (OrderDiscountRate orderDiscountRate : rateList) {
            String realPayPrice = orderDiscountRate.getRealPayPrice();
            String oldPayPrice = orderDiscountRate.getOldPayPrice();
            realPayPrice_page = realPayPrice_page.add(new BigDecimal(realPayPrice));
            oldPayPrice_page = oldPayPrice_page.add(new BigDecimal(oldPayPrice));
        }
        rate_page = BigdecimalUtil.calRate(realPayPrice_page, oldPayPrice_page);
        jo.put("rate_page", rate_page);
        jo.put("realPayPrice_page", realPayPrice_page);
        jo.put("oldPayPrice_page", oldPayPrice_page);

        Collections.sort(rateList, new Comparator<OrderDiscountRate>() {
            @Override
            public int compare(OrderDiscountRate o1, OrderDiscountRate o2) {
                String dateA = o1.getDate();
                String dateB = o2.getDate();
                if (dateA.compareTo(dateB) < 0) {
                    return 1;
                } else if (dateA.compareTo(dateB) > 0) {
                    return -1;
                }
                return 0;
            }
        });

        jo.put("rows", rateList);
        return jo;
    }

    @RequestMapping("/exportCalculateSalesDiscountRate.do")
    public void exportCalculateSalesDiscountRate(HttpServletRequest request,
                                                 HttpServletResponse response) {

        String[] teamIds = request.getParameterValues("teamIds[]");

        String showReduce = request.getParameter("showReduce");// 是 1  否  0   包含优惠券

        //YEAR   YEAR_HALF   MOUNTH     DAYS      HOURS
        String timeType = request.getParameter("timeType");

        String startTime = request.getParameter("startDate");
        String endTime = request.getParameter("endDate");

        List<String> teamList = StringToListUtil.arrToList(teamIds);
        List<String> orderStatusList = OrderStatusEnum.getSuccCode();

        List<OrderDiscountRate> rateList = new ArrayList<OrderDiscountRate>();
        if (StringUtils.equals(timeType, "YEAR")) {
            List<DateCal> dateList = DateUtil.getYears(startTime, endTime);
            for (DateCal dateCal : dateList) {
                OrderDiscountRate orderDiscountRate = new OrderDiscountRate();
                calculateSalesDiscountRate(teamList, orderStatusList, dateCal, showReduce,
                    orderDiscountRate);
                orderDiscountRate.setDate(dateCal.getDateFormat());
                rateList.add(orderDiscountRate);
            }

        } else if (StringUtils.equals(timeType, "YEAR_HALF")) {
            List<DateCal> dateList = DateUtil.getHalfYears(startTime, endTime);
            for (DateCal dateCal : dateList) {
                OrderDiscountRate orderDiscountRate = new OrderDiscountRate();
                calculateSalesDiscountRate(teamList, orderStatusList, dateCal, showReduce,
                    orderDiscountRate);
                orderDiscountRate.setDate(dateCal.getDateFormat());
                rateList.add(orderDiscountRate);
            }
        } else if (StringUtils.equals(timeType, "MOUNTH")) {
            List<DateCal> dateList = DateUtil.getMounths(startTime, endTime);
            for (DateCal dateCal : dateList) {
                OrderDiscountRate orderDiscountRate = new OrderDiscountRate();
                calculateSalesDiscountRate(teamList, orderStatusList, dateCal, showReduce,
                    orderDiscountRate);
                orderDiscountRate.setDate(dateCal.getDateFormat());
                rateList.add(orderDiscountRate);
            }
        } else if (StringUtils.equals(timeType, "DAYS")) {
            List<DateCal> dateList = DateUtil.getDays(startTime, endTime);
            for (DateCal dateCal : dateList) {
                OrderDiscountRate orderDiscountRate = new OrderDiscountRate();
                calculateSalesDiscountRate(teamList, orderStatusList, dateCal, showReduce,
                    orderDiscountRate);
                orderDiscountRate.setDate(dateCal.getDateFormat());
                rateList.add(orderDiscountRate);
            }
        } else if (StringUtils.equals(timeType, "HOURS")) {
            List<DateCal> dateList = DateUtil.getHoursByDay(startTime, endTime);
            for (DateCal dateCal : dateList) {
                OrderDiscountRate orderDiscountRate = new OrderDiscountRate();
                calculateSalesDiscountRate(teamList, orderStatusList, dateCal, showReduce,
                    orderDiscountRate);
                orderDiscountRate.setDate(dateCal.getDateFormat());
                rateList.add(orderDiscountRate);
            }
        }

        Collections.sort(rateList, new Comparator<OrderDiscountRate>() {
            @Override
            public int compare(OrderDiscountRate o1, OrderDiscountRate o2) {
                String dateA = o1.getDate();
                String dateB = o2.getDate();
                if (dateA.compareTo(dateB) < 0) {
                    return 1;
                } else if (dateA.compareTo(dateB) > 0) {
                    return -1;
                }
                return 0;
            }
        });

        try {
            if (CollectionUtils.isEmpty(rateList)) {
                return;
            } else {
                // 表头
                String[] headers = { "日期", "订单实付", "订单原价", "折扣比率（%）" };
                // 数据键名或者MODEL类字段名
                String[] Col = { "date", "realPayPrice", "oldPayPrice", "rate" };

                ExportExcel<OrderDiscountRate> ex = new ExportExcel<OrderDiscountRate>();
                List<OrderDiscountRate> excelList = rateList;
                // 生成Excel
                HSSFWorkbook workbook = ex.exportExcel("sheet1", headers, Col, excelList, null);
                // 下载
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + "table.xls");
                OutputStream ouputStream = response.getOutputStream();
                workbook.write(ouputStream);
                ouputStream.flush();
                ouputStream.close();
            }
        } catch (Exception e) {
            logger.error("exportAllCashierOrderList：导出异常");

        }
    }

    private void calculateSalesDiscountRate(List<String> teamList, List<String> orderStatusList,
                                            DateCal dateCal, String showReduce,
                                            OrderDiscountRate orderDiscountRate) {
        Map<String, Object> calculateSalesDiscountRate = orderDAO.calculateSalesStoreDiscountRate(
            teamList, orderStatusList, dateCal.getStartDate(), dateCal.getEndDate());
        //优惠券信息
        Money reduceMoney = new Money(0);
        if (StringUtils.equals(showReduce, "0")) {
            List<Map<String, Object>> calculateSalesStoreDiscountRateForOrderId = orderDAO
                .calculateSalesStoreDiscountRateForOrderId(teamList, orderStatusList,
                    dateCal.getStartDate(), dateCal.getEndDate());
            for (Map<String, Object> map : calculateSalesStoreDiscountRateForOrderId) {
                MapUtils.getString(map, "childOrderId");
                String reducePro = MapUtils.getString(map, "reducePro");
                String reduceTeam = MapUtils.getString(map, "reduceTeam");
                String reducePlat = MapUtils.getString(map, "reducePlat");
                Money reduceAmountDiscount = ReduceAmountUtil.getChildOrderReduceAmountS(reducePro,
                    reducePlat, reduceTeam);
                reduceMoney = reduceMoney.add(reduceAmountDiscount);
            }
        }
        if (null == calculateSalesDiscountRate) {
            orderDiscountRate.setRate(0.00);
        } else {
            Money realPayPrice = BigdecimalUtil.toMoney(
                (BigDecimal) calculateSalesDiscountRate.get("realPayPrice")).add(reduceMoney);
            Money oldPayPrice = BigdecimalUtil.toMoney((BigDecimal) calculateSalesDiscountRate
                .get("oldPayPrice"));
            orderDiscountRate.setRealPayPrice(realPayPrice.toSimpleString());
            orderDiscountRate.setOldPayPrice(oldPayPrice.toSimpleString());

            BigDecimal a = (BigDecimal) calculateSalesDiscountRate.get("realPayPrice");
            BigDecimal b = (BigDecimal) calculateSalesDiscountRate.get("oldPayPrice");
            orderDiscountRate.setRate(BigdecimalUtil.calRate(a, b));
        }
    }

    /**
     * 单品折扣比率
     * 
     * @param request
     * @return
     */
    @RequestMapping("/calculateSalesCategoryDiscountRate.do")
    @ResponseBody
    public JSONObject calculateSalesCategoryDiscountRate(HttpServletRequest request) {

        String[] teamIds = request.getParameterValues("teamIds[]");

        //YEAR   YEAR_HALF   MOUNTH     DAYS      HOURS
        String timeType = request.getParameter("timeType");

        String proMsg = request.getParameter("proMsg");

        String startTime = request.getParameter("startDate");
        String endTime = request.getParameter("endDate");

        String showReduce = request.getParameter("showReduce");// 是 1  否  0   包含优惠券

        JSONObject jo = new JSONObject();

        List<String> childCategorysList = getClassList(request);

        List<String> teamId = StringToListUtil.arrToList(teamIds);

        List<String> orderStatusList = OrderStatusEnum.getSuccCode();

        List<OrderDiscountRate> rateList = new ArrayList<OrderDiscountRate>();

        if (StringUtils.equals(timeType, "YEAR")) {
            List<DateCal> dateList = DateUtil.getYears(startTime, endTime);
            for (DateCal dateCal : dateList) {
                calculateSalesCategoryDiscountRate(proMsg, teamId, orderStatusList,
                    childCategorysList, dateCal, showReduce, rateList);
            }
        } else if (StringUtils.equals(timeType, "YEAR_HALF")) {
            List<DateCal> dateList = DateUtil.getHalfYears(startTime, endTime);
            for (DateCal dateCal : dateList) {
                calculateSalesCategoryDiscountRate(proMsg, teamId, orderStatusList,
                    childCategorysList, dateCal, showReduce, rateList);
            }
        } else if (StringUtils.equals(timeType, "MOUNTH")) {
            List<DateCal> dateList = DateUtil.getMounths(startTime, endTime);
            for (DateCal dateCal : dateList) {
                calculateSalesCategoryDiscountRate(proMsg, teamId, orderStatusList,
                    childCategorysList, dateCal, showReduce, rateList);
            }
        } else if (StringUtils.equals(timeType, "DAYS")) {
            List<DateCal> dateList = DateUtil.getDays(startTime, endTime);
            for (DateCal dateCal : dateList) {
                calculateSalesCategoryDiscountRate(proMsg, teamId, orderStatusList,
                    childCategorysList, dateCal, showReduce, rateList);
            }
        } else if (StringUtils.equals(timeType, "HOURS")) {
            List<DateCal> dateList = DateUtil.getHoursByDay(startTime, endTime);
            for (DateCal dateCal : dateList) {
                calculateSalesCategoryDiscountRate(proMsg, teamId, orderStatusList,
                    childCategorysList, dateCal, showReduce, rateList);
            }
        }

        BigDecimal oldPayPrice_page = new BigDecimal(0);
        BigDecimal realPayPrice_page = new BigDecimal(0);
        BigDecimal discountPrice_page = new BigDecimal(0);
        for (OrderDiscountRate orderDiscountRate : rateList) {
            String oldPayPrice = orderDiscountRate.getOldPayPrice();
            String realPayPrice = orderDiscountRate.getRealPayPrice();
            String discountPrice = orderDiscountRate.getDicountPrice();
            oldPayPrice_page = oldPayPrice_page.add(new BigDecimal(oldPayPrice));
            realPayPrice_page = realPayPrice_page.add(new BigDecimal(realPayPrice));
            discountPrice_page = discountPrice_page.add(new BigDecimal(discountPrice));
        }
        double rate_page = BigdecimalUtil.calRate(realPayPrice_page, oldPayPrice_page);
        jo.put("oldPayPrice_page", oldPayPrice_page);
        jo.put("realPayPrice_page", realPayPrice_page);
        jo.put("discountPrice_page", discountPrice_page);
        jo.put("rate_page", rate_page);

        Collections.sort(rateList, new Comparator<OrderDiscountRate>() {
            @Override
            public int compare(OrderDiscountRate o1, OrderDiscountRate o2) {
                String dateA = o1.getDate();
                String dateB = o2.getDate();
                if (dateA.compareTo(dateB) < 0) {
                    return 1;
                } else if (dateA.compareTo(dateB) > 0) {
                    return -1;
                }
                return 0;
            }
        });

        jo.put("rows", rateList);

        return jo;
    }

    @RequestMapping("/exportCalculateSalesCategoryDiscountRate.do")
    public void exportCalculateSalesCategoryDiscountRate(HttpServletRequest request,
                                                         HttpServletResponse response) {

        String[] teamIds = request.getParameterValues("teamIds[]");

        //YEAR   YEAR_HALF   MOUNTH     DAYS      HOURS
        String timeType = request.getParameter("timeType");

        String proMsg = request.getParameter("proMsg");

        String startTime = request.getParameter("startDate");
        String endTime = request.getParameter("endDate");

        String showReduce = request.getParameter("showReduce");// 是 1  否  0   包含优惠券

        List<String> childCategorysList = getClassList(request);

        List<String> teamId = StringToListUtil.arrToList(teamIds);

        List<String> orderStatusList = OrderStatusEnum.getSuccCode();

        List<OrderDiscountRate> rateList = new ArrayList<OrderDiscountRate>();

        if (StringUtils.equals(timeType, "YEAR")) {
            List<DateCal> dateList = DateUtil.getYears(startTime, endTime);
            for (DateCal dateCal : dateList) {
                calculateSalesCategoryDiscountRate(proMsg, teamId, orderStatusList,
                    childCategorysList, dateCal, showReduce, rateList);
            }
        } else if (StringUtils.equals(timeType, "YEAR_HALF")) {
            List<DateCal> dateList = DateUtil.getHalfYears(startTime, endTime);
            for (DateCal dateCal : dateList) {
                calculateSalesCategoryDiscountRate(proMsg, teamId, orderStatusList,
                    childCategorysList, dateCal, showReduce, rateList);
            }
        } else if (StringUtils.equals(timeType, "MOUNTH")) {
            List<DateCal> dateList = DateUtil.getMounths(startTime, endTime);
            for (DateCal dateCal : dateList) {
                calculateSalesCategoryDiscountRate(proMsg, teamId, orderStatusList,
                    childCategorysList, dateCal, showReduce, rateList);
            }
        } else if (StringUtils.equals(timeType, "DAYS")) {
            List<DateCal> dateList = DateUtil.getDays(startTime, endTime);
            for (DateCal dateCal : dateList) {
                calculateSalesCategoryDiscountRate(proMsg, teamId, orderStatusList,
                    childCategorysList, dateCal, showReduce, rateList);
            }
        } else if (StringUtils.equals(timeType, "HOURS")) {
            List<DateCal> dateList = DateUtil.getHoursByDay(startTime, endTime);
            for (DateCal dateCal : dateList) {
                calculateSalesCategoryDiscountRate(proMsg, teamId, orderStatusList,
                    childCategorysList, dateCal, showReduce, rateList);
            }
        }

        Collections.sort(rateList, new Comparator<OrderDiscountRate>() {
            @Override
            public int compare(OrderDiscountRate o1, OrderDiscountRate o2) {
                String dateA = o1.getDate();
                String dateB = o2.getDate();
                if (dateA.compareTo(dateB) < 0) {
                    return 1;
                } else if (dateA.compareTo(dateB) > 0) {
                    return -1;
                }
                return 0;
            }
        });

        try {
            if (CollectionUtils.isEmpty(rateList)) {
                return;
            } else {

                // 表头
                String[] headers = { "日期", "订单原价", "订单实付", "折扣金额", "折扣比率（%）" };
                // 数据键名或者MODEL类字段名
                String[] Col = { "date", "oldPayPrice", "realPayPrice", "dicountPrice", "rate" };

                ExportExcel<OrderDiscountRate> ex = new ExportExcel<OrderDiscountRate>();
                List<OrderDiscountRate> excelList = rateList;
                // 生成Excel
                HSSFWorkbook workbook = ex.exportExcel("sheet1", headers, Col, excelList, null);
                // 下载
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + "table.xls");
                OutputStream ouputStream = response.getOutputStream();
                workbook.write(ouputStream);
                ouputStream.flush();
                ouputStream.close();
            }
        } catch (Exception e) {
            logger.error("exportCalculateSalesCategoryDiscountRate：导出异常");
        }
    }

    private void calculateSalesCategoryDiscountRate(String proMsg, List<String> teamId,
                                                    List<String> orderStatusList,
                                                    List<String> childCategorysList,
                                                    DateCal dateCal, String showReduce,
                                                    List<OrderDiscountRate> rateList) {
        Map<String, Object> calculateSalesCategoryDiscountRate = orderDAO
            .calculateSalesCategoryDiscountRate(proMsg, teamId, orderStatusList,
                childCategorysList, dateCal.getStartDate(), dateCal.getEndDate());
        OrderDiscountRate orderDiscountRate = new OrderDiscountRate();
        orderDiscountRate.setDate(dateCal.getDateFormat());

        //优惠券信息
        Money reduceMoney = new Money(0);
        if (StringUtils.equals(showReduce, "0")) {
            List<Map<String, Object>> calculateSalesStoreDiscountRateForOrderId = orderDAO
                .calculateSalesCategoryDiscountRateForOrderId(proMsg, teamId, orderStatusList,
                    childCategorysList, dateCal.getStartDate(), dateCal.getEndDate());
            for (Map<String, Object> map : calculateSalesStoreDiscountRateForOrderId) {
                MapUtils.getString(map, "childOrderId");
                String reducePro = MapUtils.getString(map, "reducePro");
                String reduceTeam = MapUtils.getString(map, "reduceTeam");
                String reducePlat = MapUtils.getString(map, "reducePlat");
                Money reduceAmountDiscount = ReduceAmountUtil.getChildOrderReduceAmountS(reducePro,
                    reducePlat, reduceTeam);
                reduceMoney = reduceMoney.add(reduceAmountDiscount);
            }
        }

        if (null == calculateSalesCategoryDiscountRate) {
            orderDiscountRate.setRate(0.00);
        } else {
            Money realPayPrice = BigdecimalUtil.toMoney(
                (BigDecimal) calculateSalesCategoryDiscountRate.get("realPayPrice")).add(
                reduceMoney);
            Money oldPayPrice = BigdecimalUtil
                .toMoney((BigDecimal) calculateSalesCategoryDiscountRate.get("oldPayPrice"));
            orderDiscountRate.setRealPayPrice(realPayPrice.toSimpleString());
            orderDiscountRate.setOldPayPrice(oldPayPrice.toSimpleString());

            orderDiscountRate.setDicountPrice(oldPayPrice.subtract(realPayPrice).toSimpleString());

            BigDecimal a = (BigDecimal) calculateSalesCategoryDiscountRate.get("realPayPrice");
            BigDecimal b = (BigDecimal) calculateSalesCategoryDiscountRate.get("oldPayPrice");
            orderDiscountRate.setRate(BigdecimalUtil.calRate(a, b));
        }
        rateList.add(orderDiscountRate);

    }

    /**
     * 按月、按年统计销售、品类
     * 销售情况综合分析
     * @param request
     * @return
     */
    @RequestMapping("/calculateSalesCategoryForUserOrAcc.do")
    @ResponseBody
    public JSONObject calculateSalesCategoryForUserOrAcc(HttpServletRequest request,
                                                         Integer offset, Integer limit) {

        List<String> province = getProCitNameS(request.getParameterValues("linkProvince[]"));
        List<String> city = getProCitNameS(request.getParameterValues("linkCity[]"));
        String userType = request.getParameter("userType");//0用户   1营业员
        String someMsg = request.getParameter("someMsg");
        String reerMsg = request.getParameter("reerMsg");
        String[] teamIds = request.getParameterValues("teamIds[]");
        String proMsg = request.getParameter("proMsg");
        List<String> childCategoryS = getClassList(request);
        List<String> orderStatus = OrderStatusEnum.getSuccCode();

        String startTime = request.getParameter("startDate");
        String endTime = request.getParameter("endDate");

        String isTeamUser = request.getParameter("isTeamUser");

        JSONObject jo = new JSONObject();
        try {
            if (CollectionUtils.isNotEmpty(city)) {
                province = null;
            }

            List<String> teamList = StringToListUtil.arrToList(teamIds);

            Date startDate = DateUtil.stringToDate(startTime, DateUtil.webFormat);
            Date endDate = DateUtil.stringToDate(endTime, DateUtil.webFormat);
            if (null != endDate)
                endDate = DateUtil.addDays(endDate, 1);

            List<Map<String, Object>> calculateSalesCategoryForUserOrAcc = orderDAO
                .calculateSalesCategoryForUserOrAcc(province, city, userType, someMsg, reerMsg,
                    teamList, proMsg, childCategoryS, orderStatus, startDate, endDate, isTeamUser,
                    offset, limit);
            Double orderCount_page = 0D;
            Money orderPrice_page = new Money(0);
            Money payPrice_page = new Money(0);
            Money realOrderPrice_page = new Money(0);

            for (Map<String, Object> map : calculateSalesCategoryForUserOrAcc) {
                orderCount_page += MapUtils.getDouble(map, "orderCount");
                //零售单价
                BigDecimal productOldPrice = (BigDecimal) map.get("productOldPrice");
                map.put("productOldPrice", BigdecimalUtil.toMoney(productOldPrice).toSimpleString());
                //订单原价
                BigDecimal orderPrice = (BigDecimal) map.get("orderPrice");
                Money orderPrice_ = BigdecimalUtil.toMoney(orderPrice);
                orderPrice_page = orderPrice_page.add(orderPrice_);
                map.put("orderPrice", orderPrice_.toSimpleString());
                //实付单价
                BigDecimal producePrice = (BigDecimal) map.get("producePrice");
                map.put("producePrice", BigdecimalUtil.toMoney(producePrice).toSimpleString());
                //实付金额
                BigDecimal payPrice = (BigDecimal) map.get("payPrice");
                Money payPrice_ = BigdecimalUtil.toMoney(payPrice);
                payPrice_page = payPrice_page.add(payPrice_);
                map.put("payPrice", payPrice_.toSimpleString());
                //消费金额
                BigDecimal realOrderPrice = (BigDecimal) map.get("realOrderPrice");
                Money realOrderPrice_ = BigdecimalUtil.toMoney(realOrderPrice);
                realOrderPrice_page = realOrderPrice_page.add(realOrderPrice_);
                map.put("realOrderPrice", realOrderPrice_.toSimpleString());

                Integer categoryId = MapUtils.getInteger(map, "categoryId");
                if (null != categoryId) {
                    //                    CategoryDO selectById = categoryDAO.selectById(categoryId);

                }
                String recommendType = MapUtils.getString(map, "recommendType");
                String recommendId = MapUtils.getString(map, "recommendId");
                String recommendUserId = MapUtils.getString(map, "recommendUserId");
                if (StringUtils.equals(recommendType, AccountTypeEnum.TEAM.getCode())) {
                    if (StringUtils.isNotBlank(recommendUserId)) {
                        UserDO userDO = userDAO.selectByUserId(recommendUserId);
                        if (null != userDO) {
                            map.put("re_userid", userDO.getUserId());
                            map.put("re_name",
                                StringUtils.isBlank(userDO.getRealName()) ? userDO.getNickName()
                                    : userDO.getRealName());
                            map.put("re_cell", userDO.getCell());
                            map.put("re_erpNo", userDO.getErpNo());
                        }
                    }

                    if (StringUtils.isNotBlank(recommendId)) {
                        TeamDO teamDO = teamDAO.selectByTeamId(recommendId);
                        if (null != teamDO) {
                            map.put("re_teamid", teamDO.getTeamId());
                            map.put("re_erp", teamDO.getErpNo());
                            map.put("re_reamName", teamDO.getTeamName());
                        }
                    }
                } else {
                    if (StringUtils.isNotBlank(recommendUserId)) {
                        UserDO userDO = userDAO.selectByUserId(recommendUserId);
                        if (null != userDO) {
                            map.put("re_userid", userDO.getUserId());
                            map.put("re_name",
                                StringUtils.isBlank(userDO.getRealName()) ? userDO.getNickName()
                                    : userDO.getRealName());
                            map.put("re_cell", userDO.getCell());
                            map.put("re_erpNo", userDO.getErpNo());
                        }
                    }
                }
            }
            jo.put("rows", calculateSalesCategoryForUserOrAcc);
            jo.put(
                "total",
                orderDAO.calculateSalesCategoryForUserOrAccCount(province, city, userType, someMsg,
                    reerMsg, teamList, proMsg, childCategoryS, orderStatus, startDate, endDate,
                    isTeamUser).size());

            //小计
            jo.put("orderCount_page", String.format("%.2f", orderCount_page));
            jo.put("orderPrice_page", orderPrice_page.toSimpleString());
            jo.put("payPrice_page", payPrice_page.toSimpleString());
            jo.put("realOrderPrice_page", realOrderPrice_page.toSimpleString());

            //合计
            Map<String, Object> userOrAccSum = orderDAO.calculateSalesCategoryForUserOrAccSum(
                province, city, userType, someMsg, reerMsg, teamList, proMsg, childCategoryS,
                orderStatus, startDate, endDate, isTeamUser);
            if (null != userOrAccSum) {
                Double orderCountSum = MapUtils.getDouble(userOrAccSum, "orderCountSum");
                jo.put("orderCountSum", String.format("%.2f", orderCountSum));

                BigDecimal orderPriceSum = (BigDecimal) userOrAccSum.get("orderPriceSum");
                jo.put("orderPriceSum", BigdecimalUtil.toMoney(orderPriceSum).toSimpleString());

                BigDecimal payPriceSum = (BigDecimal) userOrAccSum.get("payPriceSum");
                jo.put("payPriceSum", BigdecimalUtil.toMoney(payPriceSum).toSimpleString());

                BigDecimal realOrderPriceSum = (BigDecimal) userOrAccSum.get("realOrderPriceSum");
                jo.put("realOrderPriceSum", BigdecimalUtil.toMoney(realOrderPriceSum)
                    .toSimpleString());
            } else {
                jo.put("orderCountSum", 0);
                jo.put("orderPriceSum", 0);
                jo.put("payPriceSum", 0);
                jo.put("realOrderPriceSum", 0);
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("销售情况综合分析查询异常", new Object[] {}));
        }

        return jo;
    }

    @RequestMapping("/exportCalculateSalesCategoryForUserOrAcc.do")
    public void exportCalculateSalesCategoryForUserOrAcc(HttpServletRequest request,
                                                         HttpServletResponse response) {

        List<String> province = getProCitNameS(request.getParameterValues("linkProvince[]"));
        List<String> city = getProCitNameS(request.getParameterValues("linkCity[]"));
        String userType = request.getParameter("userType");//0用户   1营业员
        String someMsg = request.getParameter("someMsg");
        String reerMsg = request.getParameter("reerMsg");
        String[] teamIds = request.getParameterValues("teamIds[]");
        String proMsg = request.getParameter("proMsg");
        List<String> childCategoryS = getClassList(request);
        List<String> orderStatus = OrderStatusEnum.getSuccCode();

        String startTime = request.getParameter("startDate");
        String endTime = request.getParameter("endDate");

        String isTeamUser = request.getParameter("isTeamUser");
        try {
            if (CollectionUtils.isNotEmpty(city)) {
                province = null;
            }
            List<String> teamList = StringToListUtil.arrToList(teamIds);

            Date startDate = DateUtil.stringToDate(startTime, DateUtil.webFormat);
            Date endDate = DateUtil.stringToDate(endTime, DateUtil.webFormat);
            if (null != endDate)
                endDate = DateUtil.addDays(endDate, 1);

            List<Map<String, Object>> calculateSalesCategoryForUserOrAcc = orderDAO
                .calculateSalesCategoryForUserOrAcc(province, city, userType, someMsg, reerMsg,
                    teamList, proMsg, childCategoryS, orderStatus, startDate, endDate, isTeamUser,
                    null, null);
            for (Map<String, Object> map : calculateSalesCategoryForUserOrAcc) {
                //零售单价
                BigDecimal productOldPrice = (BigDecimal) map.get("productOldPrice");
                map.put("productOldPrice", BigdecimalUtil.toMoney(productOldPrice).toSimpleString());
                //订单原价
                BigDecimal orderPrice = (BigDecimal) map.get("orderPrice");
                map.put("orderPrice", BigdecimalUtil.toMoney(orderPrice).toSimpleString());
                //实付单价
                BigDecimal producePrice = (BigDecimal) map.get("producePrice");
                map.put("producePrice", BigdecimalUtil.toMoney(producePrice).toSimpleString());
                //实付金额
                BigDecimal payPrice = (BigDecimal) map.get("payPrice");
                map.put("payPrice", BigdecimalUtil.toMoney(payPrice).toSimpleString());
                //消费金额
                BigDecimal realOrderPrice = (BigDecimal) map.get("realOrderPrice");
                map.put("realOrderPrice", BigdecimalUtil.toMoney(realOrderPrice).toSimpleString());

                Integer categoryId = MapUtils.getInteger(map, "categoryId");
                if (null != categoryId) {
                    //                    CategoryDO selectById = categoryDAO.selectById(categoryId);

                }

                String recommendType = MapUtils.getString(map, "recommendType");
                String recommendId = MapUtils.getString(map, "recommendId");
                String recommendUserId = MapUtils.getString(map, "recommendUserId");
                if (StringUtils.equals(recommendType, AccountTypeEnum.TEAM.getCode())) {
                    if (StringUtils.isNotBlank(recommendUserId)) {
                        UserDO userDO = userDAO.selectByUserId(recommendUserId);
                        if (null != userDO) {
                            map.put("re_userid", userDO.getUserId());
                            map.put("re_name",
                                StringUtils.isBlank(userDO.getRealName()) ? userDO.getNickName()
                                    : userDO.getRealName());
                            map.put("re_cell", userDO.getCell());
                            map.put("re_erpNo", userDO.getErpNo());
                        }
                    }

                    if (StringUtils.isNotBlank(recommendId)) {
                        TeamDO teamDO = teamDAO.selectByTeamId(recommendId);
                        if (null != teamDO) {
                            map.put("re_teamid", teamDO.getTeamId());
                            map.put("re_erp", teamDO.getErpNo());
                            map.put("re_reamName", teamDO.getTeamName());
                        }
                    }
                } else {
                    if (StringUtils.isNotBlank(recommendUserId)) {
                        UserDO userDO = userDAO.selectByUserId(recommendUserId);
                        if (null != userDO) {
                            map.put("re_userid", userDO.getUserId());
                            map.put("re_name",
                                StringUtils.isBlank(userDO.getRealName()) ? userDO.getNickName()
                                    : userDO.getRealName());
                            map.put("re_cell", userDO.getCell());
                            map.put("re_erpNo", userDO.getErpNo());
                        }
                    }
                }
                String re_userid = MapUtils.getString(map, "re_userid");
                String re_name = MapUtils.getString(map, "re_name");
                String re_cell = MapUtils.getString(map, "re_cell");
                map.put("re_Msg", checkEmpty(re_userid) + "/" + checkEmpty(re_name) + "/"
                                  + checkEmpty(re_cell));

                String re_teamid = MapUtils.getString(map, "re_teamid");
                String re_erp = MapUtils.getString(map, "re_erp");
                String re_reamName = MapUtils.getString(map, "re_reamName");
                map.put("re_team_Msg", checkEmpty(re_teamid) + "/" + checkEmpty(re_erp) + "/"
                                       + checkEmpty(re_reamName));

                String op_userid = MapUtils.getString(map, "op_userid");
                String op_name = MapUtils.getString(map, "op_name");
                String op_cell = MapUtils.getString(map, "op_cell");
                map.put("op_Msg", checkEmpty(op_userid) + "/" + checkEmpty(op_name) + "/"
                                  + checkEmpty(op_cell));

                String us_userid = MapUtils.getString(map, "us_userid");
                String us_name = MapUtils.getString(map, "us_name");
                String us_cell = MapUtils.getString(map, "us_cell");
                map.put("us_Msg", checkEmpty(us_userid) + "/" + checkEmpty(us_name) + "/"
                                  + checkEmpty(us_cell));

                if (null != map.get("gmtCreate")) {
                    Date gmtCreate = (Date) map.get("gmtCreate");
                    map.put("gmtCreate", DateUtil.dateToString(gmtCreate, DateUtil.newFormat));
                }
            }

            if (CollectionUtils.isEmpty(calculateSalesCategoryForUserOrAcc)) {
                return;
            } else {
                // 表头
                String[] headers = { "创建日期", "推荐人ERP编号", "推荐人手机号码", "推荐人真实姓名", "订单号", "子订单号",
                        "门店编号", "门店ERP编号", "门店名称", "推荐团队编号", "推荐团队ERP编号", "推荐团队名称", "操作人",
                        "操作人手机号", "操作人ERP", "会员", "会员手机号", "会员ERP", "商品标题", "数量", "零售单价", "订单原价",
                        "实付单价", "实付金额", "消费金额", "所属类别" };
                // 数据键名或者MODEL类字段名
                String[] Col = { "gmtCreate", "re_erpNo", "re_cell", "re_name", "orderNo",
                        "orderId", "teamId", "teamErpNo", "teamName", "re_teamid", "re_erp",
                        "re_reamName", "op_name", "op_cell", "op_erpNo", "us_name", "us_cell",
                        "us_erpNo", "productName", "orderCount", "productOldPrice", "orderPrice",
                        "producePrice", "payPrice", "realOrderPrice", "cate_one" };

                //                ExportExcel<Map<String, Object>> ex = new ExportExcel<Map<String, Object>>();
                //                List<Map<String, Object>> excelList = calculateSalesCategoryForUserOrAcc;
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
                HSSFWorkbook book = com.onway.utils.excel.ExportExcel.exportExcel(fileName,
                    headers, Col, calculateSalesCategoryForUserOrAcc, null);
                //写入数据
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + fileName);
                book.write(response.getOutputStream());

                response.getOutputStream().flush();
                response.getOutputStream().close();
            }
        } catch (Exception e) {
            logger.error("exportCalculateSalesCategoryForUserOrAcc：导出异常");
        }
    }

    /**
     * 筛选时间段、按门店会员消费时段高峰、低谷
     * 
     * @param request
     * @return
     */
    @RequestMapping("/calculateSalesForUsersOrStores.do")
    @ResponseBody
    public JSONObject calculateSalesForUsersOrStores(HttpServletRequest request) {

        String startTime = request.getParameter("startDate");
        String endTime = request.getParameter("endDate");

        String userMsg = request.getParameter("userMsg");
        String proMsg = request.getParameter("proMsg");

        //YEAR   YEAR_HALF   MOUNTH     DAYS      HOURS
        String timeType = request.getParameter("timeType");

        String[] teamIds = request.getParameterValues("teamIds[]");

        List<String> teamList = StringToListUtil.arrToList(teamIds);

        JSONObject jo = new JSONObject();

        List<String> orderStatusList = OrderStatusEnum.getSuccCode();

        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        if (StringUtils.equals(timeType, "YEAR")) {
            List<DateCal> dateList = DateUtil.getYears(startTime, endTime);
            for (DateCal dateCal : dateList) {
                calculateSalesForUsersOrStores(userMsg, proMsg, teamList, orderStatusList, dateCal,
                    resultList);
            }
        } else if (StringUtils.equals(timeType, "YEAR_HALF")) {
            List<DateCal> dateList = DateUtil.getHalfYears(startTime, endTime);
            for (DateCal dateCal : dateList) {
                calculateSalesForUsersOrStores(userMsg, proMsg, teamList, orderStatusList, dateCal,
                    resultList);
            }
        } else if (StringUtils.equals(timeType, "MOUNTH")) {
            List<DateCal> dateList = DateUtil.getMounths(startTime, endTime);
            for (DateCal dateCal : dateList) {
                calculateSalesForUsersOrStores(userMsg, proMsg, teamList, orderStatusList, dateCal,
                    resultList);
            }
        } else if (StringUtils.equals(timeType, "DAYS")) {
            List<DateCal> dateList = DateUtil.getDays(startTime, endTime);
            for (DateCal dateCal : dateList) {
                calculateSalesForUsersOrStores(userMsg, proMsg, teamList, orderStatusList, dateCal,
                    resultList);
            }
        } else if (StringUtils.equals(timeType, "HOURS")) {
            List<DateCal> dateList = DateUtil.getHoursByDay(startTime, endTime);
            for (DateCal dateCal : dateList) {
                calculateSalesForUsersOrStores(userMsg, proMsg, teamList, orderStatusList, dateCal,
                    resultList);
            }
        }

        Collections.sort(resultList, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                String dateA = MapUtils.getString(o1, "date");
                String dateB = MapUtils.getString(o2, "date");
                if (dateA.compareTo(dateB) < 0) {
                    return 1;
                } else if (dateA.compareTo(dateB) > 0) {
                    return -1;
                }
                return 0;
            }
        });
        jo.put("rows", resultList);
        return jo;
    }

    @RequestMapping("/exportCalculateSalesForUsersOrStores.do")
    public void exportCalculateSalesForUsersOrStores(HttpServletRequest request,
                                                     HttpServletResponse response) {

        String startTime = request.getParameter("startDate");
        String endTime = request.getParameter("endDate");

        String userMsg = request.getParameter("userMsg");
        String proMsg = request.getParameter("proMsg");

        //YEAR   YEAR_HALF   MOUNTH     DAYS      HOURS
        String timeType = request.getParameter("timeType");

        String[] teamIds = request.getParameterValues("teamIds[]");

        List<String> teamList = StringToListUtil.arrToList(teamIds);

        List<String> orderStatusList = OrderStatusEnum.getSuccCode();

        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        if (StringUtils.equals(timeType, "YEAR")) {
            List<DateCal> dateList = DateUtil.getYears(startTime, endTime);
            for (DateCal dateCal : dateList) {
                calculateSalesForUsersOrStores(userMsg, proMsg, teamList, orderStatusList, dateCal,
                    resultList);
            }
        } else if (StringUtils.equals(timeType, "YEAR_HALF")) {
            List<DateCal> dateList = DateUtil.getHalfYears(startTime, endTime);
            for (DateCal dateCal : dateList) {
                calculateSalesForUsersOrStores(userMsg, proMsg, teamList, orderStatusList, dateCal,
                    resultList);
            }
        } else if (StringUtils.equals(timeType, "MOUNTH")) {
            List<DateCal> dateList = DateUtil.getMounths(startTime, endTime);
            for (DateCal dateCal : dateList) {
                calculateSalesForUsersOrStores(userMsg, proMsg, teamList, orderStatusList, dateCal,
                    resultList);
            }
        } else if (StringUtils.equals(timeType, "DAYS")) {
            List<DateCal> dateList = DateUtil.getDays(startTime, endTime);
            for (DateCal dateCal : dateList) {
                calculateSalesForUsersOrStores(userMsg, proMsg, teamList, orderStatusList, dateCal,
                    resultList);
            }
        } else if (StringUtils.equals(timeType, "HOURS")) {
            List<DateCal> dateList = DateUtil.getHoursByDay(startTime, endTime);
            for (DateCal dateCal : dateList) {
                calculateSalesForUsersOrStores(userMsg, proMsg, teamList, orderStatusList, dateCal,
                    resultList);
            }
        }

        Collections.sort(resultList, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                String dateA = MapUtils.getString(o1, "date");
                String dateB = MapUtils.getString(o2, "date");
                if (dateA.compareTo(dateB) < 0) {
                    return 1;
                } else if (dateA.compareTo(dateB) > 0) {
                    return -1;
                }
                return 0;
            }
        });

        try {

            if (CollectionUtils.isEmpty(resultList)) {
                return;
            } else {
                // 表头
                String[] headers = { "日期", "销售额", "销量" };
                // 数据键名或者MODEL类字段名
                String[] Col = { "date", "realPayPrice", "realSalesNum" };

                //                ExportExcel<Map<String, Object>> ex = new ExportExcel<Map<String, Object>>();
                //                List<Map<String, Object>> excelList = resultList;
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
                HSSFWorkbook book = com.onway.utils.excel.ExportExcel.exportExcel(fileName,
                    headers, Col, resultList, null);
                //写入数据
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + fileName);
                book.write(response.getOutputStream());

                response.getOutputStream().flush();
                response.getOutputStream().close();
            }
        } catch (Exception e) {
            logger.error("exportCalculateSalesForUsersOrStores：导出异常");

        }
    }

    private void calculateSalesForUsersOrStores(String userMsg, String proMsg,
                                                List<String> teamList,
                                                List<String> orderStatusList, DateCal dateCal,
                                                List<Map<String, Object>> resultList) {
        Map<String, Object> calculateSalesForUsersOrStores = orderDAO
            .calculateSalesForUsersOrStores(userMsg, proMsg, teamList, orderStatusList,
                dateCal.getStartDate(), dateCal.getEndDate());
        if (null == calculateSalesForUsersOrStores) {
            calculateSalesForUsersOrStores = new HashMap<String, Object>();
            calculateSalesForUsersOrStores.put("realPayPrice", "0.00");
            calculateSalesForUsersOrStores.put("realSalesNum", "");
        } else {
            Money realPayPrice = BigdecimalUtil.toMoney((BigDecimal) calculateSalesForUsersOrStores
                .get("realPayPrice"));
            calculateSalesForUsersOrStores.put("realPayPrice", realPayPrice.toSimpleString());
        }
        calculateSalesForUsersOrStores.put("date", dateCal.getDateFormat());
        resultList.add(calculateSalesForUsersOrStores);
    }

    /**
     * 筛选时间段、按门店会员消费时段高峰、低谷  
     * 图表
     * @param request
     * @return
     */
    @RequestMapping("/calculateSalesForUsersOrStoresCharts.do")
    @ResponseBody
    public JSONObject calculateSalesForUsersOrStoresCharts(HttpServletRequest request) {

        String startTime = request.getParameter("startDate");
        String endTime = request.getParameter("endDate");

        String userMsg = request.getParameter("userMsg");
        String proMsg = request.getParameter("proMsg");

        //YEAR   YEAR_HALF   MOUNTH     DAYS      HOURS
        String timeType = request.getParameter("timeType");

        String[] teamIds = request.getParameterValues("teamIds[]");

        List<String> teamList = StringToListUtil.arrToList(teamIds);

        JSONObject jo = new JSONObject();

        List<String> orderStatusList = OrderStatusEnum.getSuccCode();

        List<String> xAxisData = new LinkedList<String>();
        List<String> realPayPrice = new LinkedList<String>();
        List<Double> realSalesNum = new LinkedList<Double>();

        if (StringUtils.equals(timeType, "YEAR")) {
            List<DateCal> dateList = DateUtil.getYears(startTime, endTime);
            for (DateCal dateCal : dateList) {
                Map<String, Object> calculateSalesForUsersOrStores = orderDAO
                    .calculateSalesForUsersOrStores(userMsg, proMsg, teamList, orderStatusList,
                        dateCal.getStartDate(), dateCal.getEndDate());
                if (null == calculateSalesForUsersOrStores) {
                    realPayPrice.add("0.00");
                    realSalesNum.add(0.00);
                } else {
                    Money realPayPricethis = BigdecimalUtil
                        .toMoney((BigDecimal) calculateSalesForUsersOrStores.get("realPayPrice"));
                    realPayPrice.add(realPayPricethis.toSimpleString());

                    Double realSalesNumThis = MapUtils.getDouble(calculateSalesForUsersOrStores,
                        "realSalesNum");
                    realSalesNum.add(realSalesNumThis);
                }
                xAxisData.add(dateCal.getDateFormat());
            }

        } else if (StringUtils.equals(timeType, "YEAR_HALF")) {
            List<DateCal> dateList = DateUtil.getHalfYears(startTime, endTime);
            for (DateCal dateCal : dateList) {
                Map<String, Object> calculateSalesForUsersOrStores = orderDAO
                    .calculateSalesForUsersOrStores(userMsg, proMsg, teamList, orderStatusList,
                        dateCal.getStartDate(), dateCal.getEndDate());
                if (null == calculateSalesForUsersOrStores) {
                    realPayPrice.add("0.00");
                    realSalesNum.add(0.00);
                } else {
                    Money realPayPricethis = BigdecimalUtil
                        .toMoney((BigDecimal) calculateSalesForUsersOrStores.get("realPayPrice"));
                    realPayPrice.add(realPayPricethis.toSimpleString());

                    Double realSalesNumThis = MapUtils.getDouble(calculateSalesForUsersOrStores,
                        "realSalesNum");
                    realSalesNum.add(realSalesNumThis);
                }
                xAxisData.add(dateCal.getDateFormat());
            }
        } else if (StringUtils.equals(timeType, "MOUNTH")) {
            List<DateCal> dateList = DateUtil.getMounths(startTime, endTime);
            for (DateCal dateCal : dateList) {
                Map<String, Object> calculateSalesForUsersOrStores = orderDAO
                    .calculateSalesForUsersOrStores(userMsg, proMsg, teamList, orderStatusList,
                        dateCal.getStartDate(), dateCal.getEndDate());
                if (null == calculateSalesForUsersOrStores) {
                    realPayPrice.add("0.00");
                    realSalesNum.add(0.00);
                } else {
                    Money realPayPricethis = BigdecimalUtil
                        .toMoney((BigDecimal) calculateSalesForUsersOrStores.get("realPayPrice"));
                    realPayPrice.add(realPayPricethis.toSimpleString());

                    Double realSalesNumThis = MapUtils.getDouble(calculateSalesForUsersOrStores,
                        "realSalesNum");
                    realSalesNum.add(realSalesNumThis);
                }
                xAxisData.add(dateCal.getDateFormat());
            }
        } else if (StringUtils.equals(timeType, "DAYS")) {
            List<DateCal> dateList = DateUtil.getDays(startTime, endTime);
            for (DateCal dateCal : dateList) {
                Map<String, Object> calculateSalesForUsersOrStores = orderDAO
                    .calculateSalesForUsersOrStores(userMsg, proMsg, teamList, orderStatusList,
                        dateCal.getStartDate(), dateCal.getEndDate());
                if (null == calculateSalesForUsersOrStores) {
                    realPayPrice.add("0.00");
                    realSalesNum.add(0.00);
                } else {
                    Money realPayPricethis = BigdecimalUtil
                        .toMoney((BigDecimal) calculateSalesForUsersOrStores.get("realPayPrice"));
                    realPayPrice.add(realPayPricethis.toSimpleString());

                    Double realSalesNumThis = MapUtils.getDouble(calculateSalesForUsersOrStores,
                        "realSalesNum");
                    realSalesNum.add(realSalesNumThis);
                }
                xAxisData.add(dateCal.getDateFormat());
            }
        } else if (StringUtils.equals(timeType, "HOURS")) {
            List<DateCal> dateList = DateUtil.getHoursByDay(startTime, endTime);
            for (DateCal dateCal : dateList) {
                Map<String, Object> calculateSalesForUsersOrStores = orderDAO
                    .calculateSalesForUsersOrStores(userMsg, proMsg, teamList, orderStatusList,
                        dateCal.getStartDate(), dateCal.getEndDate());
                if (null == calculateSalesForUsersOrStores) {
                    realPayPrice.add("0.00");
                    realSalesNum.add(0.00);
                } else {
                    Money realPayPricethis = BigdecimalUtil
                        .toMoney((BigDecimal) calculateSalesForUsersOrStores.get("realPayPrice"));
                    realPayPrice.add(realPayPricethis.toSimpleString());

                    Double realSalesNumThis = MapUtils.getDouble(calculateSalesForUsersOrStores,
                        "realSalesNum");
                    realSalesNum.add(realSalesNumThis);
                }
                xAxisData.add(dateCal.getDateFormat());
            }
        }

        jo.put("xAxisData", xAxisData);
        jo.put("realPayPrice", realPayPrice);
        jo.put("realSalesNum", realSalesNum);
        return jo;
    }

    /**
     * 会员信息结构表
     * 
     * @param request
     * @return
     */
    @RequestMapping("/calculateSalesCategoryForUserMsg.do")
    @ResponseBody
    public JSONObject calculateSalesCategoryForUserMsg(HttpServletRequest request, Integer offset,
                                                       Integer limit) {

        String buyMsg = request.getParameter("buyMsg");
        String ageGroup = request.getParameter("ageGroup");
        String proMsg = request.getParameter("proMsg");
        String province = getProCitName(request.getParameter("linkProvince"));
        String city = getProCitName(request.getParameter("linkCity"));

        String startTime = request.getParameter("startDate");
        String endTime = request.getParameter("endDate");

        String specialyFlg = request.getParameter("specialyFlg");

        JSONObject jo = new JSONObject();

        List<String> childCategorysList = getClassList(request);

        List<String> orderStatusList = OrderStatusEnum.getSuccCodeNotReturn();

        if (StringUtils.isNotBlank(city)) {
            province = null;
        }

        Date startDate = DateUtil.stringToDate(startTime, DateUtil.webFormat);
        Date endDate = DateUtil.stringToDate(endTime, DateUtil.webFormat);
        if (null != endDate)
            endDate = DateUtil.addDays(endDate, 1);

        String specialyUid = null;
        if (StringUtils.equals(specialyFlg, "true") || StringUtils.equals(specialyFlg, "false")) {
            String cell = sysConfigCacheManager
                .getConfigValue(SysConfigCacheKeyEnum.UNIFY_CUSTOMER_CELL);
            if (StringUtils.isNotBlank(cell)) {
                UserDO userDO = userDAO.selectByUserCell(cell);
                if (null != userDO)
                    specialyUid = userDO.getUserId();
            }
        }

        List<Map<String, Object>> calculateSalesCategoryForUserOrAcc = orderDAO
            .calculateSalesCategoryForUserMsg(buyMsg, ageGroup, proMsg, province, city, startDate,
                endDate, childCategorysList, orderStatusList, specialyFlg, specialyUid, offset,
                limit);

        Money realPayPrice_page = new Money(0);
        Double orderCount_page = 0D;
        Money voucherSumAmount_page = new Money(0);
        for (Map<String, Object> map : calculateSalesCategoryForUserOrAcc) {
            Integer userLevel = MapUtils.getInteger(map, "userLevel");
            UserLevelEnum userLevelEnum = UserLevelEnum.getByValue(userLevel);
            if (null != userLevelEnum)
                map.put("userLevel", userLevelEnum.getMessage());

            Integer re_userLevel = MapUtils.getInteger(map, "re_userLevel");
            UserLevelEnum userLevelEnum_re = UserLevelEnum.getByValue(re_userLevel);
            if (null != userLevelEnum_re)
                map.put("re_userLevel", userLevelEnum_re.getMessage());

            String sex = MapUtils.getString(map, "sex");
            SexEnum sexEnum = SexEnum.getByCode(sex);
            if (null != sexEnum)
                map.put("sex", sexEnum.message());

            String userId = MapUtils.getString(map, "userId");

            List<Map<String, Object>> queryMyVoucherSumAmount = voucherDAO
                .queryMyVoucherSumAmountTeam(userId, null, CheckStatusEnum.ENABLED.getCode(),
                    CheckStatusEnum.ENABLED.getCode());
            Map<String, Object> voucherMsg = new HashMap<String, Object>();
            for (Map<String, Object> map2 : queryMyVoucherSumAmount) {
                Money voucherSumAmount_ = BigdecimalUtil.toMoney((BigDecimal) map2.get("amount"));
                String teamName = MapUtils.getString(map2, "teamName");
                if (voucherSumAmount_.greaterThan(new Money(0))) {
                    voucherMsg.put(teamName, voucherSumAmount_.toSimpleString());
                    voucherSumAmount_page = voucherSumAmount_page.add(voucherSumAmount_);
                }
            }
            StringBuffer teamName_v = new StringBuffer();
            StringBuffer voucherSumAmount = new StringBuffer();
            Set<Entry<String, Object>> entrySet = voucherMsg.entrySet();
            for (Entry<String, Object> entry : entrySet) {
                teamName_v.append(entry.getKey()).append("<br>");
                voucherSumAmount.append(entry.getValue()).append("<br>");
            }
            if (StringUtils.isNotBlank(teamName_v.toString())) {
                map.put("teamName_v",
                    teamName_v.toString().substring(0, teamName_v.toString().length() - 4));
            } else {
                map.put("teamName_v", teamName_v.toString());
            }
            if (StringUtils.isNotBlank(voucherSumAmount.toString())) {
                map.put(
                    "voucherSumAmount",
                    voucherSumAmount.toString().substring(0,
                        voucherSumAmount.toString().length() - 4));
            } else {
                map.put("voucherSumAmount", voucherSumAmount.toString());
            }

            BigDecimal queryOrderPriceSum = orderDAO.queryOrderPriceSum(userId, null,
                childCategorysList, orderStatusList);
            Double queryOrderCountSum = orderDAO.queryOrderCountSum(userId, null,
                childCategorysList);

            Money realPayPricethis = BigdecimalUtil.toMoney(queryOrderPriceSum);
            Double orderCount = queryOrderCountSum;
            realPayPrice_page = realPayPrice_page.add(realPayPricethis);
            map.put("realPayPrice", realPayPricethis.toSimpleString());

            map.put("orderCount", orderCount);
            orderCount_page += orderCount;
        }

        jo.put("rows", calculateSalesCategoryForUserOrAcc);
        jo.put("total", orderDAO.calculateSalesCategoryForUserMsgCount(buyMsg, ageGroup, proMsg,
            province, city, startDate, endDate, childCategorysList, orderStatusList, specialyFlg,
            specialyUid));

        jo.put("realPayPrice_page", realPayPrice_page.toSimpleString());
        jo.put("orderCount_page", orderCount_page);
        jo.put("voucherSumAmount_page", voucherSumAmount_page.toSimpleString());

        //        Map<String, Object> sumAll = orderDAO.calculateSalesCategoryForUserMsgSum(buyMsg, ageGroup,
        //            proMsg, province, city, startDate, endDate, childCategorysList, orderStatusList,
        //            specialyFlg, specialyUid);
        jo.put("realPayPrice_sum", 0);
        jo.put("orderCount_sum", 0);
        jo.put("voucherSumAmount_sum", 0);
        //        if (null != sumAll) {
        //            Money realPayPriceSum = BigdecimalUtil.toMoney((BigDecimal) sumAll
        //                .get("realPayPriceSum"));
        //            jo.put("realPayPrice_sum", realPayPriceSum.toSimpleString());
        //        }

        List<String> userIds = orderDAO.calculateSalesCategoryForUserMsgUserIds(buyMsg, ageGroup,
            proMsg, province, city, startDate, endDate, childCategorysList, orderStatusList,
            specialyFlg, specialyUid);
        if (CollectionUtils.isNotEmpty(userIds)) {
            BigDecimal queryMyVoucherSumAmountUserIds = voucherDAO
                .queryMyVoucherSumAmountUserIds(userIds, null, CheckStatusEnum.ENABLED.getCode(),
                    CheckStatusEnum.ENABLED.getCode());
            Money queryMyVoucherSumAmountUserIds_ = BigdecimalUtil
                .toMoney(queryMyVoucherSumAmountUserIds);
            jo.put("voucherSumAmount_sum", queryMyVoucherSumAmountUserIds_.toSimpleString());
            //            Integer orderCountSum = orderDAO.queryMyVoucherSumCount(userIds);
            //            jo.put("orderCount_sum", orderCountSum);

            BigDecimal queryOrderPriceSum = orderDAO.queryOrderPriceSum(null, userIds,
                childCategorysList, orderStatusList);
            Double queryOrderCountSum = orderDAO.queryOrderCountSum(null, userIds,
                childCategorysList);
            jo.put("realPayPrice_sum", BigdecimalUtil.toMoney(queryOrderPriceSum).toSimpleString());
            jo.put("orderCount_sum", queryOrderCountSum);
        }

        return jo;
    }

    @RequestMapping("/exportCalculateSalesCategoryForUserMsg.do")
    public void exportCalculateSalesCategoryForUserMsg(HttpServletRequest request,
                                                       HttpServletResponse response) {

        String buyMsg = request.getParameter("buyMsg");
        String ageGroup = request.getParameter("ageGroup");
        String proMsg = request.getParameter("proMsg");
        String province = getProCitName(request.getParameter("linkProvince"));
        String city = getProCitName(request.getParameter("linkCity"));

        String startTime = request.getParameter("startDate");
        String endTime = request.getParameter("endDate");

        String specialyFlg = request.getParameter("specialyFlg");

        List<String> childCategorysList = getClassList(request);

        List<String> orderStatusList = OrderStatusEnum.getSuccCodeNotReturn();

        if (StringUtils.isNotBlank(city)) {
            province = null;
        }

        Date startDate = DateUtil.stringToDate(startTime, DateUtil.webFormat);
        Date endDate = DateUtil.stringToDate(endTime, DateUtil.webFormat);
        if (null != endDate)
            endDate = DateUtil.addDays(endDate, 1);

        String specialyUid = null;
        if (StringUtils.equals(specialyFlg, "true") || StringUtils.equals(specialyFlg, "false")) {
            String cell = sysConfigCacheManager
                .getConfigValue(SysConfigCacheKeyEnum.UNIFY_CUSTOMER_CELL);
            if (StringUtils.isNotBlank(cell)) {
                UserDO userDO = userDAO.selectByUserCell(cell);
                if (null != userDO)
                    specialyUid = userDO.getUserId();
            }
        }

        List<Map<String, Object>> calculateSalesCategoryForUserOrAcc = orderDAO
            .calculateSalesCategoryForUserMsg(buyMsg, ageGroup, proMsg, province, city, startDate,
                endDate, childCategorysList, orderStatusList, specialyFlg, specialyUid, null, null);
        for (Map<String, Object> map : calculateSalesCategoryForUserOrAcc) {
            Integer userLevel = MapUtils.getInteger(map, "userLevel");
            UserLevelEnum userLevelEnum = UserLevelEnum.getByValue(userLevel);
            if (null != userLevelEnum)
                map.put("userLevel", userLevelEnum.getMessage());

            Integer re_userLevel = MapUtils.getInteger(map, "re_userLevel");
            UserLevelEnum userLevelEnum_re = UserLevelEnum.getByValue(re_userLevel);
            if (null != userLevelEnum_re)
                map.put("re_userLevel", userLevelEnum_re.getMessage());

            String sex = MapUtils.getString(map, "sex");
            SexEnum sexEnum = SexEnum.getByCode(sex);
            if (null != sexEnum)
                map.put("sex", sexEnum.message());

            String userId = MapUtils.getString(map, "userId");
            List<Map<String, Object>> queryMyVoucherSumAmount = voucherDAO
                .queryMyVoucherSumAmountTeam(userId, null, CheckStatusEnum.ENABLED.getCode(),
                    CheckStatusEnum.ENABLED.getCode());

            Map<String, Object> voucherMsg = new HashMap<String, Object>();
            for (Map<String, Object> map2 : queryMyVoucherSumAmount) {
                Money voucherSumAmount_ = BigdecimalUtil.toMoney((BigDecimal) map2.get("amount"));
                String teamName = MapUtils.getString(map, "teamName");
                if (voucherSumAmount_.greaterThan(new Money(0))) {
                    voucherMsg.put(teamName, voucherSumAmount_.toSimpleString());
                }
            }

            StringBuffer teamName_v = new StringBuffer();
            StringBuffer voucherSumAmount = new StringBuffer();
            Set<Entry<String, Object>> entrySet = voucherMsg.entrySet();
            for (Entry<String, Object> entry : entrySet) {
                teamName_v.append(entry.getKey()).append("/");
                voucherSumAmount.append(entry.getValue()).append("/");
            }
            if (StringUtils.isNotBlank(teamName_v.toString())) {
                map.put("teamName_v",
                    teamName_v.toString().substring(0, teamName_v.toString().length() - 1));
            } else {
                map.put("teamName_v", teamName_v.toString());
            }
            if (StringUtils.isNotBlank(voucherSumAmount.toString())) {
                map.put(
                    "voucherSumAmount",
                    voucherSumAmount.toString().substring(0,
                        voucherSumAmount.toString().length() - 1));
            } else {
                map.put("voucherSumAmount", voucherSumAmount.toString());
            }

            String province_ = MapUtils.getString(map, "province");
            String city_ = MapUtils.getString(map, "city");
            String area_ = MapUtils.getString(map, "area");
            String reAddr_ = MapUtils.getString(map, "reAddr");
            map.put("reAddress", checkEmpty(province_) + "/" + checkEmpty(city_) + "/"
                                 + checkEmpty(area_) + "/" + checkEmpty(reAddr_));

            if (null != map.get("registerTime")) {
                Date registerTime = (Date) map.get("registerTime");
                map.put("registerTime", DateUtil.dateToString(registerTime, DateUtil.newFormat));
            }

            String re_name = MapUtils.getString(map, "re_name");
            String re_userLevel_ = MapUtils.getString(map, "re_userLevel");
            map.put("re_Msg", checkEmpty(re_name) + "/" + checkEmpty(re_userLevel_));

            BigDecimal queryOrderPriceSum = orderDAO.queryOrderPriceSum(userId, null,
                childCategorysList, orderStatusList);
            Double queryOrderCountSum = orderDAO.queryOrderCountSum(userId, null,
                childCategorysList);
            Money realPayPricethis = BigdecimalUtil.toMoney(queryOrderPriceSum);
            Double orderCount = queryOrderCountSum;
            map.put("realPayPrice", realPayPricethis.toSimpleString());
            map.put("orderCount", orderCount);
        }

        try {
            if (CollectionUtils.isEmpty(calculateSalesCategoryForUserOrAcc)) {
                return;
            } else {
                // 表头
                String[] headers = { "会员编号", "会员手机号", "会员昵称", "会员等级", "真实姓名", "性别", "收货地址", "年龄段",
                        "消费金额", "下单次数", "庆余卡机构", "庆余卡余额", "注册时间", "推荐人编号", "推荐人手机号码", "推荐人真实姓名",
                        "推荐人等级" };
                // 数据键名或者MODEL类字段名
                String[] Col = { "userId", "cell", "nickName", "userLevel", "realName", "sex",
                        "reAddress", "ageGroup", "realPayPrice", "orderCount", "teamName_v",
                        "voucherSumAmount", "registerTime", "re_userId", "re_cell", "re_name",
                        "re_userLevel" };

                //                ExportExcel<Map<String, Object>> ex = new ExportExcel<Map<String, Object>>();
                //                List<Map<String, Object>> excelList = calculateSalesCategoryForUserOrAcc;
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
                HSSFWorkbook book = com.onway.utils.excel.ExportExcel.exportExcel(fileName,
                    headers, Col, calculateSalesCategoryForUserOrAcc, null);
                //写入数据
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + fileName);
                book.write(response.getOutputStream());

                response.getOutputStream().flush();
                response.getOutputStream().close();
            }
        } catch (Exception e) {
            logger.error("exportCalculateSalesCategoryForUserMsg：导出异常");

        }

    }

    /**
     * 获取类别查询条件集合
     * @param request
     * @return
     */
    private List<String> getClassList(HttpServletRequest request) {
        String[] childCategory = request.getParameterValues("childCategory[]");
        String[] childCategorys = request.getParameterValues("childCategorys[]");
        String[] fatherCategory = request.getParameterValues("fatherCategory[]");
        //第三类别
        if (childCategorys != null && childCategorys.length > 0) {
            //         	childCategory = null;
            return StringToListUtil.arrToList(childCategorys);
        }
        //第二类别
        if (childCategory != null && childCategory.length > 0) {
            //         	fatherCategory = null;
            return StringToListUtil.arrToList(childCategory);
        }

        //第一类别
        return StringToListUtil.arrToList(fatherCategory);
    }

    /**
     * 收银订单
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/queryAllCashierOrderList.do")
    @ResponseBody
    public JSONObject queryAllCashierOrderList(HttpServletRequest request, Integer offset,
                                               Integer limit) {

        String[] teamIds = request.getParameterValues("teamIds[]");
        String cashierUserMsg = request.getParameter("cashierUserMsg");
        String proMsg = request.getParameter("proMsg");
        String buyMsg = request.getParameter("buyMsg");

        String orderNo = request.getParameter("orderNo");
        String orderId = request.getParameter("orderId");

        String startTime = request.getParameter("startDate");
        String endTime = request.getParameter("endDate");

        String specialyFlg = request.getParameter("specialyFlg");

        JSONObject data = new JSONObject();
        try {
            List<String> teamList = StringToListUtil.arrToList(teamIds);

            String specialyUid = null;
            if (StringUtils.equals(specialyFlg, "true") || StringUtils.equals(specialyFlg, "false")) {
                String cell = sysConfigCacheManager
                    .getConfigValue(SysConfigCacheKeyEnum.UNIFY_CUSTOMER_CELL);
                if (StringUtils.isNotBlank(cell)) {
                    UserDO userDO = userDAO.selectByUserCell(cell);
                    if (null != userDO)
                        specialyUid = userDO.getUserId();
                }
            }

            Date startDate = DateUtil.stringToDate(startTime, DateUtil.webFormat);
            Date endDate = DateUtil.stringToDate(endTime, DateUtil.webFormat);
            if (null != endDate)
                endDate = DateUtil.addDays(endDate, 1);

            List<Map<String, Object>> queryAllCashierOrderList = orderDAO.queryAllCashierOrderList(
                teamList, cashierUserMsg, proMsg, buyMsg, orderNo, orderId, specialyFlg,
                specialyUid, startDate, endDate, offset, limit);

            Double orderCountPage = 0D;
            Money productPricePage = new Money(0);
            Money productOldPricePage = new Money(0);
            Money realOrderPricePage = new Money(0);
            Money amountVoucherPage = new Money(0);
            Money oldSalePricePage = new Money(0);
            Double cashierDiscountPage = 0D;
            Money returnAmountPage = new Money(0);

            for (Map<String, Object> map : queryAllCashierOrderList) {
                String orderStatus = MapUtils.getString(map, "orderStatus");
                OrderStatusEnum orderStatusEnum = OrderStatusEnum.getByCode(orderStatus);
                if (null != orderStatusEnum)
                    map.put("orderStatusStr", orderStatusEnum.message());

                if (orderStatusEnum == OrderStatusEnum.WAIT_CHECK) {
                    String orderSubStatus = MapUtils.getString(map, "orderSubStatus");
                    OrderSubStatusEnum orderSubStatusEnum = OrderSubStatusEnum
                        .getByCode(orderSubStatus);
                    if (null != orderSubStatusEnum)
                        map.put("orderSubStatusStr", orderSubStatusEnum.message());
                }

                orderCountPage += MapUtils.getDouble(map, "orderCount");

                BigDecimal productPrice = (BigDecimal) map.get("productPrice");
                Money productPrice_ = BigdecimalUtil.toMoney(productPrice);
                productPricePage = productPricePage.add(productPrice_);
                map.put("productPrice", productPrice_.toSimpleString());

                BigDecimal productOldPrice = (BigDecimal) map.get("productOldPrice");
                Money productOldPrice_ = BigdecimalUtil.toMoney(productOldPrice);
                productOldPricePage = productOldPricePage.add(productOldPrice_);
                map.put("productOldPrice", productOldPrice_.toSimpleString());

                BigDecimal realOrderPrice = (BigDecimal) map.get("realOrderPrice");
                Money realOrderPrice_ = BigdecimalUtil.toMoney(realOrderPrice);
                realOrderPricePage = realOrderPricePage.add(realOrderPrice_);
                map.put("realOrderPrice", realOrderPrice_.toSimpleString());

                BigDecimal amountVoucher = (BigDecimal) map.get("amountVoucher");
                Money amountVoucher_ = BigdecimalUtil.toMoney(amountVoucher);
                amountVoucherPage = amountVoucherPage.add(amountVoucher_);
                map.put("amountVoucher", amountVoucher_.toSimpleString());

                BigDecimal oldSalePrice = (BigDecimal) map.get("oldSalePrice");
                Money oldSalePrice_ = BigdecimalUtil.toMoney(oldSalePrice);
                oldSalePricePage = oldSalePricePage.add(oldSalePrice_);
                map.put("oldSalePrice", oldSalePrice_.toSimpleString());

                Double cashierDiscount = MapUtils.getDouble(map, "cashierDiscount");
                cashierDiscountPage += cashierDiscount;
                map.put(
                    "cashierDiscount",
                    new BigDecimal(cashierDiscount).multiply(new BigDecimal(100)).setScale(2,
                        BigDecimal.ROUND_HALF_UP));

                Integer buy_userLevel = MapUtils.getInteger(map, "buy_userLevel");
                UserLevelEnum userLevelEnum = UserLevelEnum.getByValue(buy_userLevel);
                if (null != userLevelEnum)
                    map.put("buy_userLevel", userLevelEnum.getMessage());

                Integer teamLevel = MapUtils.getInteger(map, "teamLevel");
                TeamLevelEnum teamLevelEnum = TeamLevelEnum.getByValue(teamLevel);
                if (null != teamLevelEnum)
                    map.put("teamLevel", teamLevelEnum.getMessage());

                //已经退款成功的金额
                BigDecimal returnAmount = orderReturnDAO.sumAmountOrderReturnSucc(
                    MapUtils.getString(map, "orderId"), StatusEnum.ENABLED.getCode());
                String IO = "";
                Money returnAmount_ = BigdecimalUtil.toMoney(returnAmount);
                if (returnAmount_.greaterThan(new Money(0))) {
                    IO = "-";
                }
                map.put("returnAmount", IO + returnAmount_.toSimpleString());
                returnAmountPage = returnAmountPage.add(returnAmount_);
            }

            Integer total = orderDAO.queryAllCashierOrderListCount(teamList, cashierUserMsg,
                proMsg, buyMsg, orderNo, orderId, specialyFlg, specialyUid, startDate, endDate);
            data.put("rows", queryAllCashierOrderList);
            data.put("total", total);

            data.put("orderCountPage", orderCountPage);
            data.put("productPricePage", productPricePage.toSimpleString());
            data.put("productOldPricePage", productOldPricePage.toSimpleString());
            data.put("realOrderPricePage", realOrderPricePage.toSimpleString());
            data.put("amountVoucherPage", amountVoucherPage.toSimpleString());
            data.put("oldSalePricePage", oldSalePricePage.toSimpleString());
            data.put(
                "cashierDiscountPage",
                new BigDecimal(cashierDiscountPage)
                    .divide(new BigDecimal(limit), 4, BigDecimal.ROUND_HALF_UP)
                    .multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
            if (returnAmountPage.greaterThan(new Money(0))) {
                data.put("returnAmountPage", "-" + returnAmountPage.toSimpleString());
            } else {
                data.put("returnAmountPage", returnAmountPage.toSimpleString());
            }

            //总计
            Map<String, Object> queryAllCashierOrderListSum = orderDAO.queryAllCashierOrderListSum(
                teamList, cashierUserMsg, proMsg, buyMsg, orderNo, orderId, specialyFlg,
                specialyUid, startDate, endDate);
            data.put("orderCountSum", queryAllCashierOrderListSum.get("orderCountSum"));
            BigDecimal productPriceSum = (BigDecimal) queryAllCashierOrderListSum
                .get("productPriceSum");
            data.put("productPriceSum", BigdecimalUtil.toMoney(productPriceSum).toSimpleString());
            BigDecimal productOldPriceSum = (BigDecimal) queryAllCashierOrderListSum
                .get("productOldPriceSum");
            data.put("productOldPriceSum", BigdecimalUtil.toMoney(productOldPriceSum)
                .toSimpleString());
            BigDecimal realOrderPriceSum = (BigDecimal) queryAllCashierOrderListSum
                .get("realOrderPriceSum");
            data.put("realOrderPriceSum", BigdecimalUtil.toMoney(realOrderPriceSum)
                .toSimpleString());
            BigDecimal amountVoucherSum = (BigDecimal) queryAllCashierOrderListSum
                .get("amountVoucherSum");
            data.put("amountVoucherSum", BigdecimalUtil.toMoney(amountVoucherSum).toSimpleString());
            BigDecimal oldSalePriceSum = (BigDecimal) queryAllCashierOrderListSum
                .get("oldSalePriceSum");
            data.put("oldSalePriceSum", BigdecimalUtil.toMoney(oldSalePriceSum).toSimpleString());
            Double cashierDiscountSum = MapUtils.getDouble(queryAllCashierOrderListSum,
                "cashierDiscountSum");
            if (CollectionUtils.isNotEmpty(queryAllCashierOrderList)) {
                data.put(
                    "cashierDiscountSum",
                    new BigDecimal(cashierDiscountSum)
                        .divide(new BigDecimal(total), 4, BigDecimal.ROUND_HALF_UP)
                        .multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
            } else {
                data.put("cashierDiscountSum", 0);
            }

            List<String> childOrderIds = orderDAO.queryAllCashierOrderListIds(teamList,
                cashierUserMsg, proMsg, buyMsg, orderNo, orderId, specialyFlg, specialyUid,
                startDate, endDate);
            data.put("returnAmountSum", 0);
            if (CollectionUtils.isNotEmpty(childOrderIds)) {
                BigDecimal sumAmountOrderReturnSuccIds = orderReturnDAO
                    .sumAmountOrderReturnSuccIds(childOrderIds, StatusEnum.ENABLED.getCode());
                String IO = "";
                if (BigdecimalUtil.toMoney(sumAmountOrderReturnSuccIds).greaterThan(new Money(0))) {
                    IO = "-";
                }
                data.put("returnAmountSum", IO
                                            + BigdecimalUtil.toMoney(sumAmountOrderReturnSuccIds)
                                                .toSimpleString());
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("查询收银订单异常", new Object[] {}));

        }
        return data;
    }

    @RequestMapping("/exportAllCashierOrderList.do")
    public void exportAllCashierOrderList(HttpServletRequest request, HttpServletResponse response) {

        String[] teamIds = request.getParameterValues("teamIds[]");
        String cashierUserMsg = request.getParameter("cashierUserMsg");
        String proMsg = request.getParameter("proMsg");
        String buyMsg = request.getParameter("buyMsg");

        String orderNo = request.getParameter("orderNo");
        String orderId = request.getParameter("orderId");

        String startTime = request.getParameter("startDate");
        String endTime = request.getParameter("endDate");

        String specialyFlg = request.getParameter("specialyFlg");

        try {
            List<String> teamList = StringToListUtil.arrToList(teamIds);

            String specialyUid = null;
            if (StringUtils.equals(specialyFlg, "true") || StringUtils.equals(specialyFlg, "false")) {
                String cell = sysConfigCacheManager
                    .getConfigValue(SysConfigCacheKeyEnum.UNIFY_CUSTOMER_CELL);
                if (StringUtils.isNotBlank(cell)) {
                    UserDO userDO = userDAO.selectByUserCell(cell);
                    if (null != userDO)
                        specialyUid = userDO.getUserId();
                }
            }

            Date startDate = DateUtil.stringToDate(startTime, DateUtil.webFormat);
            Date endDate = DateUtil.stringToDate(endTime, DateUtil.webFormat);
            if (null != endDate)
                endDate = DateUtil.addDays(endDate, 1);

            List<Map<String, Object>> queryAllCashierOrderList = orderDAO.queryAllCashierOrderList(
                teamList, cashierUserMsg, proMsg, buyMsg, orderNo, orderId, specialyFlg,
                specialyUid, startDate, endDate, null, null);

            for (Map<String, Object> map : queryAllCashierOrderList) {
                String orderStatus = MapUtils.getString(map, "orderStatus");
                OrderStatusEnum orderStatusEnum = OrderStatusEnum.getByCode(orderStatus);
                if (null != orderStatusEnum)
                    map.put("orderStatusStr", orderStatusEnum.message());

                if (orderStatusEnum == OrderStatusEnum.WAIT_CHECK) {
                    String orderSubStatus = MapUtils.getString(map, "orderSubStatus");
                    OrderSubStatusEnum orderSubStatusEnum = OrderSubStatusEnum
                        .getByCode(orderSubStatus);
                    if (null != orderSubStatusEnum)
                        map.put("orderSubStatusStr", orderSubStatusEnum.message());
                }

                BigDecimal productPrice = (BigDecimal) map.get("productPrice");
                map.put("productPrice", BigdecimalUtil.toMoney(productPrice).toSimpleString());

                BigDecimal productOldPrice = (BigDecimal) map.get("productOldPrice");
                map.put("productOldPrice", BigdecimalUtil.toMoney(productOldPrice).toSimpleString());

                BigDecimal realOrderPrice = (BigDecimal) map.get("realOrderPrice");
                map.put("realOrderPrice", BigdecimalUtil.toMoney(realOrderPrice).toSimpleString());

                BigDecimal amountVoucher = (BigDecimal) map.get("amountVoucher");
                map.put("amountVoucher", BigdecimalUtil.toMoney(amountVoucher).toSimpleString());

                BigDecimal oldSalePrice = (BigDecimal) map.get("oldSalePrice");
                map.put("oldSalePrice", BigdecimalUtil.toMoney(oldSalePrice).toSimpleString());

                Double cashierDiscount = MapUtils.getDouble(map, "cashierDiscount");
                map.put(
                    "cashierDiscount",
                    new BigDecimal(cashierDiscount).multiply(new BigDecimal(100)).setScale(2,
                        BigDecimal.ROUND_HALF_UP));

                Integer buy_userLevel = MapUtils.getInteger(map, "buy_userLevel");
                UserLevelEnum userLevelEnum = UserLevelEnum.getByValue(buy_userLevel);
                if (null != userLevelEnum)
                    map.put("buy_userLevel", userLevelEnum.getMessage());

                Integer teamLevel = MapUtils.getInteger(map, "teamLevel");
                TeamLevelEnum teamLevelEnum = TeamLevelEnum.getByValue(teamLevel);
                if (null != teamLevelEnum)
                    map.put("teamLevel", teamLevelEnum.getMessage());

                String teamLevel_ = MapUtils.getString(map, "teamLevel");
                String teamName = MapUtils.getString(map, "teamName");
                String teamErpNo = MapUtils.getString(map, "teamErpNo");
                map.put("teamMsg", checkEmpty(teamLevel_) + "/" + checkEmpty(teamName) + "/"
                                   + checkEmpty(teamErpNo));

                String productName = MapUtils.getString(map, "productName");
                String productErpNo = MapUtils.getString(map, "productErpNo");
                map.put("proMsg", checkEmpty(productName) + "/" + checkEmpty(productErpNo));

                String op_name = MapUtils.getString(map, "op_name");
                String op_cell = MapUtils.getString(map, "op_cell");
                String op_erpNo = MapUtils.getString(map, "op_erpNo");
                map.put("op_Msg", checkEmpty(op_name) + "/" + checkEmpty(op_cell) + "/"
                                  + checkEmpty(op_erpNo));

                String buy_userLevel_ = MapUtils.getString(map, "buy_userLevel");
                String buy_name = MapUtils.getString(map, "buy_name");
                String buy_cell = MapUtils.getString(map, "buy_cell");
                map.put("buy_Msg", checkEmpty(buy_userLevel_) + "/" + checkEmpty(buy_name) + "/"
                                   + checkEmpty(buy_cell));

                if (null != map.get("gmtCreate")) {
                    Date gmtCreate = (Date) map.get("gmtCreate");
                    map.put("gmtCreate", DateUtil.dateToString(gmtCreate, DateUtil.newFormat));
                }

                //已经退款成功的金额
                BigDecimal returnAmount = orderReturnDAO.sumAmountOrderReturnSucc(
                    MapUtils.getString(map, "orderId"), StatusEnum.ENABLED.getCode());
                String IO = "";
                if (BigdecimalUtil.toMoney(returnAmount).greaterThan(new Money(0))) {
                    IO = "-";
                }
                map.put("returnAmount", IO + BigdecimalUtil.toMoney(returnAmount).toSimpleString());
            }

            if (CollectionUtils.isEmpty(queryAllCashierOrderList)) {
                return;
            } else {
                // 表头
                String[] headers = { "销售门店", "商品", "销售金额", "销售金额（已退款）", "销售原金额", "销售数量", "收银员折扣",
                        "销售单价", "原价", "庆余卡使用", "订单状态", "订单审核状态", "收银员", "购买人", "创建时间" };
                // 数据键名或者MODEL类字段名
                String[] Col = { "teamMsg", "proMsg", "realOrderPrice", "returnAmount",
                        "oldSalePrice", "orderCount", "cashierDiscount", "productPrice",
                        "productOldPrice", "amountVoucher", "orderStatusStr", "orderSubStatusStr",
                        "op_Msg", "buy_Msg", "gmtCreate" };

                //                ExportExcel<Map<String, Object>> ex = new ExportExcel<Map<String, Object>>();
                //                List<Map<String, Object>> excelList = queryAllCashierOrderList;
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
                HSSFWorkbook book = com.onway.utils.excel.ExportExcel.exportExcel(fileName,
                    headers, Col, queryAllCashierOrderList, null);
                //写入数据
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + fileName);
                book.write(response.getOutputStream());

                response.getOutputStream().flush();
                response.getOutputStream().close();
            }
        } catch (Exception e) {
            logger.error("exportAllCashierOrderList：导出异常");

        }
    }

}
