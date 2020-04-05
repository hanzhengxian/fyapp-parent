package com.onway.web.controller.mng.data;

import java.math.BigDecimal;
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
import com.onway.fyapp.common.dal.dataobject.UserDO;
import com.onway.model.enums.OrderStatusEnum;
import com.onway.model.enums.SysConfigCacheKeyEnum;
import com.onway.model.enums.UserLevelEnum;
import com.onway.utils.BigdecimalUtil;
import com.onway.utils.DateUtil;
import com.onway.utils.StringToListUtil;
import com.onway.utils.excel.ExportExcel;
import com.onway.web.controller.AbstractController;

/**
 * 推荐销售额度报表
 * @author zhoumx
 *
 */
@Controller
@RequestMapping("/recommend")
public class RecommendSaleMoneyController extends AbstractController {

    /**
     * 列表数据
     * 按门店 营业员 月份 统计报表
     */
    @RequestMapping(value = "/saleMoney.do")
    @ResponseBody
    public JSONObject userSaleMoney(HttpServletRequest request, Integer limit, Integer offset) {
        //查询条件
        String[] teamIds = request.getParameterValues("teamIds[]");
        String reMsg = request.getParameter("reMsg");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");

        //汇总方式(按团队  2 /按推荐人  1)
        String type = request.getParameter("type");
        //返回结果集
        JSONObject data = new JSONObject();
        List<String> teamList = StringToListUtil.arrToList(teamIds);
        List<String> status = OrderStatusEnum.getSuccCodeNotReturn();

        Date startDate = DateUtil.stringToDate(startTime, DateUtil.webFormat);
        Date endDate = DateUtil.stringToDate(endTime, DateUtil.webFormat);
        if (null != endDate)
            endDate = DateUtil.addDays(endDate, 1);

        List<Map<String, Object>> saleMoney = new ArrayList<Map<String, Object>>();
        int count = 0;

        Double re_user_count_page = 0D;
        Double re_buy_count_page = 0D;
        Money re_buy_amount_page = new Money(0);
        Money u_buy_amount_page = new Money(0);
        Money all_team_amount_page = new Money(0);
        Double all_team_count_page = 0D;

        Double re_user_count_sum = 0D;
        Double re_buy_count_sum = 0D;
        Money re_buy_amount_sum = new Money(0);
        Money u_buy_amount_sum = new Money(0);
        Money all_team_amount_sum = new Money(0);
        Double all_team_count_sum = 0D;

        if (StringUtils.equals(type, "1")) {
            saleMoney = orderDAO.recommendSaleMoneyForRecommendUser(teamList, reMsg, status,
                startDate, endDate, offset, limit);
            for (Map<String, Object> map : saleMoney) {
                Integer re_level = MapUtils.getInteger(map, "re_level");
                UserLevelEnum userLevelEnum = UserLevelEnum.getByValue(re_level);
                if (null != userLevelEnum)
                    map.put("re_level", userLevelEnum.getMessage());

                Double re_buy_count = MapUtils.getDouble(map, "re_buy_count");
                re_buy_count_page += re_buy_count;

                Double re_user_count = MapUtils.getDouble(map, "re_user_count");
                re_user_count_page += re_user_count;

                Money re_buy_amount = BigdecimalUtil.toMoney((BigDecimal) map.get("re_buy_amount"));
                re_buy_amount_page = re_buy_amount_page.add(re_buy_amount);
                map.put("re_buy_amount", re_buy_amount.toSimpleString());

                //                BigDecimal u_buy_amount = (BigDecimal) map.get("u_buy_amount");
                //                Money u_buy_amount_ = BigdecimalUtil.toMoney(u_buy_amount);
                //                u_buy_amount_page = u_buy_amount_page.add(u_buy_amount_);
                //                map.put("u_buy_amount", u_buy_amount_.toSimpleString());
                map.put("u_buy_amount", null);

                //                BigDecimal rate = u_buy_amount.divide(sumAllMoney, 4, BigDecimal.ROUND_HALF_UP);
                //                map.put("rate", rate.multiply(new BigDecimal(100)).doubleValue());
            }
            count = orderDAO.recommendSaleMoneyForRecommendUserCount(teamList, reMsg);

            List<String> userOList = orderDAO.recommendSaleMoneyForRecommendUserOList(teamList,
                reMsg);
            if (CollectionUtils.isNotEmpty(userOList)) {
                Map<String, Object> userReSum = orderDAO.recommendSaleMoneyForRecommendUserReSum(
                    userOList, status, startDate, endDate);
                if (null != userReSum) {
                    Double re_buy_count_sum_ = MapUtils.getDouble(userReSum, "re_buy_count_sum");
                    re_buy_count_sum += re_buy_count_sum_;

                    Money re_buy_amount_sum_ = BigdecimalUtil.toMoney((BigDecimal) userReSum
                        .get("re_buy_amount_sum"));
                    re_buy_amount_sum = re_buy_amount_sum.add(re_buy_amount_sum_);
                }
                re_user_count_sum = (double) userOList.size();
            }

            //            List<String> userRList = orderDAO.recommendSaleMoneyForRecommendUserRList(teamList,
            //                reMsg);
            //            if (CollectionUtils.isNotEmpty(userRList)) {
            //                BigDecimal u_buy_amount_sum_ = orderDAO.recommendSaleMoneyForRecommendUserOuSum(
            //                    userRList, status, startDate, endDate);
            //                u_buy_amount_sum = u_buy_amount_sum.add(BigdecimalUtil.toMoney(u_buy_amount_sum_));
            //            }
        }

        if (StringUtils.equals(type, "2")) {
            List<String> unifyUserIds = null;
            String cell = sysConfigCacheManager
                .getConfigValue(SysConfigCacheKeyEnum.UNIFY_CUSTOMER_CELL);
            if (StringUtils.isNotBlank(cell)) {
                UserDO userDO = userDAO.selectByUserCell(cell);
                if (null != userDO) {
                    unifyUserIds = new ArrayList<String>();
                    unifyUserIds.add(userDO.getUserId());
                }
            }
            saleMoney = orderDAO.recommendSaleMoneyForRecommendTeam(teamList, reMsg, status,
                startDate, endDate, unifyUserIds, offset, limit);
            for (Map<String, Object> map : saleMoney) {
                Double re_buy_count = MapUtils.getDouble(map, "re_buy_count");
                re_buy_count_page += re_buy_count;

                Double re_user_count = MapUtils.getDouble(map, "re_user_count");
                re_user_count_page += re_user_count;

                Double all_team_count = MapUtils.getDouble(map, "all_team_count");
                all_team_count_page += all_team_count;

                Money re_buy_amount = BigdecimalUtil.toMoney((BigDecimal) map.get("re_buy_amount"));
                re_buy_amount_page = re_buy_amount_page.add(re_buy_amount);
                map.put("re_buy_amount", re_buy_amount.toSimpleString());

                BigDecimal u_buy_amount = (BigDecimal) map.get("u_buy_amount");
                Money u_buy_amount_ = BigdecimalUtil.toMoney(u_buy_amount);
                map.put("u_buy_amount", 0);
                map.put("rate", 0);

                BigDecimal all_team_amount = (BigDecimal) map.get("all_team_amount");
                Money all_team_amount_ = BigdecimalUtil.toMoney(all_team_amount);
                map.put("all_team_amount", all_team_amount_.toSimpleString());
                all_team_amount_page = all_team_amount_page.add(all_team_amount_);

                u_buy_amount_ = BigdecimalUtil.toMoney(u_buy_amount);
                u_buy_amount_page = u_buy_amount_page.add(u_buy_amount_);
                map.put("u_buy_amount", u_buy_amount_.toSimpleString());

                if(all_team_amount_.greaterThan(new Money(0))){
                    BigDecimal rate = u_buy_amount.divide(all_team_amount, 4, BigDecimal.ROUND_HALF_UP);
                    map.put("rate", rate.multiply(new BigDecimal(100)).doubleValue());
                }
            }
            count = orderDAO.recommendSaleMoneyForRecommendTeamCount(teamList, reMsg);

            Map<String, Object> teamSum = orderDAO.recommendSaleMoneyForRecommendTeamSum(teamList,
                reMsg, status, startDate, endDate, unifyUserIds);
            if (null != teamSum) {
                Double re_user_count_sum_ = MapUtils.getDouble(teamSum, "re_user_count_sum");
                re_user_count_sum += re_user_count_sum_;

                Double re_buy_count_sum_ = MapUtils.getDouble(teamSum, "re_buy_count_sum");
                re_buy_count_sum += re_buy_count_sum_;

                Double all_team_count_sum_ = MapUtils.getDouble(teamSum, "all_team_count_sum");
                all_team_count_sum += all_team_count_sum_;

                Money re_buy_amount_sum_ = BigdecimalUtil.toMoney((BigDecimal) teamSum
                    .get("re_buy_amount_sum"));
                re_buy_amount_sum = re_buy_amount_sum.add(re_buy_amount_sum_);

                Money u_buy_amount_sum_ = BigdecimalUtil.toMoney((BigDecimal) teamSum
                    .get("u_buy_amount_sum"));
                u_buy_amount_sum = u_buy_amount_sum.add(u_buy_amount_sum_);

                Money all_team_amount_sum_ = BigdecimalUtil.toMoney((BigDecimal) teamSum
                    .get("all_team_amount_sum"));
                all_team_amount_sum = all_team_amount_sum.add(all_team_amount_sum_);
            }
        }

        data.put("rows", saleMoney);
        data.put("total", count);

        data.put("re_user_count_page", re_user_count_page);
        data.put("re_buy_count_page", re_buy_count_page);
        data.put("re_buy_amount_page", re_buy_amount_page.toSimpleString());
        data.put("u_buy_amount_page", u_buy_amount_page.toSimpleString());
        data.put("all_team_amount_page", all_team_amount_page.toSimpleString());
        data.put("all_team_count_page", all_team_count_page);

        //        BigDecimal recommendSaleMoneySum = orderDAO.recommendSaleMoneySum(teamList, reMsg,
        //            startDate, endDate, status);
        //        Money recommendSaleMoneySum_ = BigdecimalUtil.toMoney(recommendSaleMoneySum);
        //        data.put("recommendSaleMoneySum", recommendSaleMoneySum_.toSimpleString());

        data.put("re_user_count_sum", re_user_count_sum);
        data.put("re_buy_count_sum", re_buy_count_sum);
        data.put("re_buy_amount_sum", re_buy_amount_sum.toSimpleString());
        data.put("u_buy_amount_sum", u_buy_amount_sum.toSimpleString());
        data.put("all_team_amount_sum", all_team_amount_sum.toSimpleString());
        data.put("all_team_count_sum", all_team_count_sum);

        return data;
    }

    /**
     * 图形数据查询
     * x坐标显示月份以及推荐人
     * y坐标为推荐销售额
     * @param request
     * @return
     */
    @RequestMapping(value = "/saleMoneyEchart.do")
    @ResponseBody
    public JSONObject saleMoneyEchart(HttpServletRequest request) {
        //查询条件
        String[] teamIds = request.getParameterValues("teamIds[]");
        String reMsg = request.getParameter("reMsg");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String type = request.getParameter("type");

        Date startDate = DateUtil.stringToDate(startTime, DateUtil.webFormat);
        Date endDate = DateUtil.stringToDate(endTime, DateUtil.webFormat);
        if (null != endDate)
            endDate = DateUtil.addDays(endDate, 1);

        //返回结果集
        JSONObject data = new JSONObject();
        List<String> teamList = StringToListUtil.arrToList(teamIds);
        List<String> status = OrderStatusEnum.getSuccCodeNotReturn();
        List<String> yDataName = new ArrayList<String>();
        List<String> yDataValue = new ArrayList<String>();
        List<String> xData = new ArrayList<String>();

        List<Map<String, Object>> saleMoney = new ArrayList<Map<String, Object>>();

        if (StringUtils.equals(type, "1")) {
            saleMoney = orderDAO.recommendSaleMoneyForRecommendUser(teamList, reMsg, status,
                startDate, endDate, null, null);
        }

        if (StringUtils.equals(type, "2")) {
            List<String> unifyUserIds = null;
            String cell = sysConfigCacheManager
                .getConfigValue(SysConfigCacheKeyEnum.UNIFY_CUSTOMER_CELL);
            if (StringUtils.isNotBlank(cell)) {
                UserDO userDO = userDAO.selectByUserCell(cell);
                if (null != userDO) {
                    unifyUserIds = new ArrayList<String>();
                    unifyUserIds.add(userDO.getUserId());
                }
            }
            saleMoney = orderDAO.recommendSaleMoneyForRecommendTeam(teamList, reMsg, status,
                startDate, endDate, unifyUserIds, null, null);
        }

        for (Map<String, Object> map : saleMoney) {
            BigDecimal re_buy_amount = (BigDecimal) map.get("re_buy_amount");

            if (StringUtils.equals(type, "1")) {
                yDataName.add(MapUtils.getString(map, "re_name"));
                xData.add(MapUtils.getString(map, "re_name"));
            }
            if (StringUtils.equals(type, "2")) {
                yDataName.add(MapUtils.getString(map, "teamName"));
                xData.add(MapUtils.getString(map, "teamName"));
            }
            yDataValue.add(BigdecimalUtil.toMoney(re_buy_amount).toSimpleString());
        }
        data.put("xData", xData);
        //		data.put("yDataName",yDataName);
        data.put("yDataValue", yDataValue);
        return data;
    }

    @RequestMapping(value = "/exportSaleMoney.do")
    public void exportSaleMoney(HttpServletRequest request, HttpServletResponse response) {
        //查询条件
        String[] teamIds = request.getParameterValues("teamIds[]");
        String reMsg = request.getParameter("reMsg");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");

        String type = request.getParameter("type");
        //返回结果集
        List<String> teamList = StringToListUtil.arrToList(teamIds);
        List<String> status = OrderStatusEnum.getSuccCodeNotReturn();

        Date startDate = DateUtil.stringToDate(startTime, DateUtil.webFormat);
        Date endDate = DateUtil.stringToDate(endTime, DateUtil.webFormat);
        if (null != endDate)
            endDate = DateUtil.addDays(endDate, 1);

        List<Map<String, Object>> saleMoney = new ArrayList<Map<String, Object>>();

        if (StringUtils.equals(type, "1")) {
            saleMoney = orderDAO.recommendSaleMoneyForRecommendUser(teamList, reMsg, status,
                startDate, endDate, null, null);
            for (Map<String, Object> map : saleMoney) {
                Integer re_level = MapUtils.getInteger(map, "re_level");
                UserLevelEnum userLevelEnum = UserLevelEnum.getByValue(re_level);
                if (null != userLevelEnum)
                    map.put("re_level", userLevelEnum.getMessage());

                Money re_buy_amount = BigdecimalUtil.toMoney((BigDecimal) map.get("re_buy_amount"));
                map.put("re_buy_amount", re_buy_amount.toSimpleString());
                map.put("u_buy_amount", null);
            }
        }

        if (StringUtils.equals(type, "2")) {
            List<String> unifyUserIds = null;
            String cell = sysConfigCacheManager
                .getConfigValue(SysConfigCacheKeyEnum.UNIFY_CUSTOMER_CELL);
            if (StringUtils.isNotBlank(cell)) {
                UserDO userDO = userDAO.selectByUserCell(cell);
                if (null != userDO) {
                    unifyUserIds = new ArrayList<String>();
                    unifyUserIds.add(userDO.getUserId());
                }
            }
            saleMoney = orderDAO.recommendSaleMoneyForRecommendTeam(teamList, reMsg, status,
                startDate, endDate, unifyUserIds, null, null);
            for (Map<String, Object> map : saleMoney) {
                Money re_buy_amount = BigdecimalUtil.toMoney((BigDecimal) map.get("re_buy_amount"));
                map.put("re_buy_amount", re_buy_amount.toSimpleString());

                BigDecimal u_buy_amount = (BigDecimal) map.get("u_buy_amount");
                Money u_buy_amount_ = BigdecimalUtil.toMoney(u_buy_amount);
                map.put("u_buy_amount", 0);
                map.put("rate", 0);

                BigDecimal all_team_amount = (BigDecimal) map.get("all_team_amount");
                Money all_team_amount_ = BigdecimalUtil.toMoney(all_team_amount);
                map.put("all_team_amount", all_team_amount_.toSimpleString());

                u_buy_amount_ = BigdecimalUtil.toMoney(u_buy_amount);
                map.put("u_buy_amount", u_buy_amount_.toSimpleString());

                if(all_team_amount_.greaterThan(new Money(0))){
                    BigDecimal rate = u_buy_amount.divide(all_team_amount, 4, BigDecimal.ROUND_HALF_UP);
                    map.put("rate", rate.multiply(new BigDecimal(100)).doubleValue());
                }
            }
        }

        try {
            if (CollectionUtils.isEmpty(saleMoney)) {
                return;
            } else {
                // 表头
                String[] headers = { "团队编号", "团队ERP编号", "团队名称", "推荐人编号", "推荐人手机号码", "推荐人真实姓名",
                        "推荐人等级", "推荐人ERP编号", "推荐人数", "推荐消费人数", "推荐销售金额", "会员销售金额", "总销售金额", "消费人数",
                        "会员金额占比" };
                // 数据键名或者MODEL类字段名
                String[] Col = { "teamId", "teamErpNo", "teamName", "re_userId", "re_cell",
                        "re_name", "re_level", "re_erpNo", "re_user_count", "re_buy_count",
                        "re_buy_amount", "u_buy_amount", "all_team_amount", "all_team_count",
                        "rate" };

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
                HSSFWorkbook book = ExportExcel
                    .exportExcel(fileName, headers, Col, saleMoney, null);
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
