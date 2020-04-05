package com.onway.web.controller.mng.data;

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
import com.onway.common.lang.StringUtils;
import com.onway.model.enums.OrderReturnStatusEnum;
import com.onway.model.enums.StatusEnum;
import com.onway.utils.DateUtil;
import com.onway.utils.StringToListUtil;
import com.onway.utils.excel.ExportExcel;
import com.onway.web.controller.AbstractController;

/**
 * 投诉记录  质量问题
 * 
 * @author yuhang.ni
 * @version $Id: ComplainController.java, v 0.1 2019年7月15日 上午10:23:22 Administrator Exp $
 */
@Controller
@RequestMapping("/complain")
public class ComplainController extends AbstractController {

    /**
     * 顾客投诉记录
     * 
     * @param request
     * @return
     */
    @RequestMapping("/orderComplainRecord.do")
    @ResponseBody
    public JSONObject orderComplainRecord(HttpServletRequest request, int offset, int limit) {

        String[] teamIds = request.getParameterValues("teamIds[]");

        String operMsg = request.getParameter("operMsg");
        String userMsg = request.getParameter("userMsg");
        String proMsg = request.getParameter("proMsg");

        //ING   END
        String conStatus = request.getParameter("conStatus");

        String startTime = request.getParameter("startDate");
        String endTime = request.getParameter("endDate");

        Date startDate = DateUtil.stringToDate(startTime, DateUtil.webFormat);
        Date endDate = DateUtil.stringToDate(endTime, DateUtil.webFormat);
        if (null != endDate)
            endDate = DateUtil.addDays(endDate, 1);

        JSONObject data = new JSONObject();
        try {
            List<String> teamList = StringToListUtil.arrToList(teamIds);
            String reasonMain = "质量";

            List<String> conStatusList = new ArrayList<String>();
            if (StringUtils.equals(conStatus, "ING")) {
                conStatusList.add(StatusEnum.INIT.getCode());
            } else if (StringUtils.equals(conStatus, "END")) {
                conStatusList.add(StatusEnum.ENABLED.getCode());
                conStatusList.add(StatusEnum.UNABLED.getCode());
            } else {
                conStatusList = null;
            }

            List<Map<String, Object>> orderComplainRecord = orderReturnDAO.getOrderComplainRecord(
                reasonMain, teamList, operMsg, userMsg, proMsg, conStatusList, startDate, endDate,
                offset, limit);
            for (Map<String, Object> map : orderComplainRecord) {
                String status = MapUtils.getString(map, "status");
                OrderReturnStatusEnum statusEnum = OrderReturnStatusEnum.getByCode(status);
                if (null != statusEnum)
                    map.put("statusStr", statusEnum.message());

                String returnImg = MapUtils.getString(map, "returnImg");
                List<String> returnImgs = StringToListUtil.toListArr(returnImg);
                map.put("returnImgs", returnImgs);

                String returnVoucherImg = MapUtils.getString(map, "returnVoucherImg");
                List<String> returnVoucherImgs = StringToListUtil.toListArr(returnVoucherImg);
                map.put("returnVoucherImgs", returnVoucherImgs);

                String quartuImg = MapUtils.getString(map, "quartyImg");
                List<String> quartuImgs = StringToListUtil.toListArr(quartuImg);
                map.put("quartuImgs", quartuImgs);
            }

            int count = orderReturnDAO.getOrderComplainRecordCount(reasonMain, teamList, operMsg,
                userMsg, proMsg, conStatusList, startDate, endDate);

            data.put("rows", orderComplainRecord);
            data.put("total", count);

        } catch (Exception e) {
            logger.error("顾客投诉记录查询异常:" + e.getMessage());
        }
        return data;
    }

    /**
     * 
     */
    @RequestMapping("/exportOrderComplainRecord.do")
    public void exportOrderComplainRecord(HttpServletResponse response, HttpServletRequest request) {

        String[] teamIds = request.getParameterValues("teamIds[]");

        String operMsg = request.getParameter("operMsg");
        String userMsg = request.getParameter("userMsg");
        String proMsg = request.getParameter("proMsg");

        //ING   END
        String conStatus = request.getParameter("conStatus");

        String startTime = request.getParameter("startDate");
        String endTime = request.getParameter("endDate");

        Date startDate = DateUtil.stringToDate(startTime, DateUtil.webFormat);
        Date endDate = DateUtil.stringToDate(endTime, DateUtil.webFormat);
        if (null != endDate)
            endDate = DateUtil.addDays(endDate, 1);

        try {

            List<String> teamList = StringToListUtil.arrToList(teamIds);
            String reasonMain = "质量";

            List<String> conStatusList = new ArrayList<String>();
            if (StringUtils.equals(conStatus, "ING")) {
                conStatusList.add(StatusEnum.INIT.getCode());
            } else if (StringUtils.equals(conStatus, "END")) {
                conStatusList.add(StatusEnum.ENABLED.getCode());
                conStatusList.add(StatusEnum.UNABLED.getCode());
            } else {
                conStatusList = null;
            }

            List<Map<String, Object>> orderComplainRecord = orderReturnDAO.getOrderComplainRecord(
                reasonMain, teamList, operMsg, userMsg, proMsg, conStatusList, startDate, endDate,
                null, null);

            if (CollectionUtils.isEmpty(orderComplainRecord)) {
                throw new RuntimeException("未找到相关数据");
            } else {

                for (Map<String, Object> map : orderComplainRecord) {
                    String status = MapUtils.getString(map, "status");
                    OrderReturnStatusEnum statusEnum = OrderReturnStatusEnum.getByCode(status);
                    if (null != statusEnum)
                        map.put("statusStr", statusEnum.message());

                    String teamId = MapUtils.getString(map, "teamId");
                    String teamName = MapUtils.getString(map, "teamName");
                    String teamErpNo = MapUtils.getString(map, "teamErpNo");
                    map.put("teamMsg", checkEmpty(teamId) + "/" + checkEmpty(teamName) + "/"
                                       + checkEmpty(teamErpNo));

                    String productId = MapUtils.getString(map, "productId");
                    String productName = MapUtils.getString(map, "productName");
                    String productErpNo = MapUtils.getString(map, "productErpNo");
                    String specification = MapUtils.getString(map, "specification");
                    String unit = MapUtils.getString(map, "unit");
                    map.put("proMsg", checkEmpty(productId) + "/" + checkEmpty(productName) + "/"
                                      + checkEmpty(productErpNo) + "/" + checkEmpty(specification)
                                      + "/" + checkEmpty(unit));

                    String cln_user_name = MapUtils.getString(map, "cln_user_name");
                    String cln_user_cell = MapUtils.getString(map, "cln_user_cell");
                    map.put("cln_Msg", checkEmpty(cln_user_name) + "/" + checkEmpty(cln_user_cell));

                    String linkUser = MapUtils.getString(map, "linkUser");
                    String linkCell = MapUtils.getString(map, "linkCell");
                    map.put("link_Msg", checkEmpty(linkUser) + "/" + checkEmpty(linkCell));

                    String assUserName = MapUtils.getString(map, "assUserName");
                    String assUserCell = MapUtils.getString(map, "assUserCell");
                    map.put("ass_Msg", checkEmpty(assUserName) + "/" + checkEmpty(assUserCell));

                    String acc_user_name = MapUtils.getString(map, "acc_user_name");
                    String acc_user_cell = MapUtils.getString(map, "acc_user_cell");
                    map.put("acc_Msg", checkEmpty(acc_user_name) + "/" + checkEmpty(acc_user_cell));

                    String cwnq_user_name = MapUtils.getString(map, "cwnq_user_name");
                    String cwnq_user_cell = MapUtils.getString(map, "cwnq_user_cell");
                    map.put("cwnq_Msg", checkEmpty(cwnq_user_name) + "/"
                                        + checkEmpty(cwnq_user_cell));
                }

                // 表头
                String[] headers = { "状态", "退货原因", "门店", "商品信息", "订单数量", "退货数量", "顾客", "投诉时间",
                        "投诉联系人信息", "顾客备注", "顾客描述图片", "营业员信息", "营业员审核备注", "营业员质量问题描述", "营业员描述图片",
                        "质管信息", "质管处理方式", "质管审核备注", "营业员退款凭证", "营业员退款凭证图片", "财务内勤信息", "财务内勤审核理由",
                        "订单号", "子订单号" };
                // 数据键名或者MODEL类字段名
                String[] Col = { "statusStr", "reason", "teamMsg", "proMsg", "orderCount",
                        "returnCount", "cln_Msg", "cln_time", "link_Msg", "reasonOther",
                        "returnImg", "ass_Msg", "failReason", "quartyDesc", "quartyImg", "acc_Msg",
                        "dealWay", "acc_user_check_memo", "returnVoucherMemo", "returnVoucherImg",
                        "cwnq_Msg", "cwnq_user_check_memo", "orderNo", "orderId" };

                String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())
                    .toString().concat(".xls");
                HSSFWorkbook book = ExportExcel.exportExcel(fileName, headers, Col,
                    orderComplainRecord, null);
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
