package com.onway.web.controller.mng;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.onway.common.lang.CollectionUtils;
import com.onway.common.lang.Money;
import com.onway.common.lang.StringUtils;
import com.onway.core.service.AccountTransComponent;
import com.onway.core.service.ExpressComponent;
import com.onway.fyapp.common.dal.dataobject.MsgDO;
import com.onway.fyapp.common.dal.dataobject.OrderDO;
import com.onway.fyapp.common.dal.dataobject.OrderPickDO;
import com.onway.fyapp.common.dal.dataobject.OrderRebateDO;
import com.onway.fyapp.common.dal.dataobject.OrderReturnDO;
import com.onway.fyapp.common.dal.dataobject.SysExpressDO;
import com.onway.fyapp.common.dal.dataobject.UserDO;
import com.onway.model.enums.MsgTypeEnum;
import com.onway.model.enums.OrderPayWayEnum;
import com.onway.model.enums.OrderStatusEnum;
import com.onway.model.enums.OrderSubStatusEnum;
import com.onway.model.enums.PayStatusEnum;
import com.onway.model.enums.ProTypeEnum;
import com.onway.model.enums.SysConfigCacheKeyEnum;
import com.onway.model.enums.SysMsgEnum;
import com.onway.platform.common.base.BaseResult;
import com.onway.platform.common.base.QueryResult;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.result.JsonQueryResult;
import com.onway.result.JsonResult;
import com.onway.result.express.ExpressDataPojo;
import com.onway.result.express.ExpressQueryPojo;
import com.onway.shunfeng.service.ShunfengService;
import com.onway.utils.BigdecimalUtil;
import com.onway.utils.DateUtil;
import com.onway.utils.ReduceAmountUtil;
import com.onway.utils.StringToListUtil;
import com.onway.utils.excel.ExportExcel;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;

/**
 * 
 * 订单
 * 
 * @author weina.chen
 * @version $Id: OrderController.java, v 0.1 2018年7月27日 下午5:18:56 ROG Exp $
 */
@Controller
public class OrderController extends AbstractController {

    @Resource
    private ExpressComponent      expressComponent;

    @Resource
    private AccountTransComponent accountTransComponent;

    @Resource
    private ShunfengService       shunfengService;

    /**
     * 分页查询
     * 
     * @param userId
     * @param orderId
     * @param productId
     * @param orderState
     * @param transferType
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/selectAllOrder.do")
    @ResponseBody
    public JSONObject selectallsysuser(String userId, String orderId, String childOrderId,
                                       String productId, String productName, String productErpNo,
                                       Integer orderState, String transferType, String teamId,
                                       String teamName, String teamErpNo, String userCell,
                                       String orderCell, String payWay, String startDate,
                                       String endDate, String rankType, String errFlg,
                                       Integer subOrderStatus, Integer offset, Integer limit,
                                       String isCashier, String cashierUser, String voucher,
                                       String chooseChildrenIdS, String barCode) {
        JSONObject jo = new JSONObject();

        List<String> orderStatusList = new ArrayList<String>();
        getOrderStatusList(orderState, orderStatusList);
        List<String> subOrderStatusList = new ArrayList<String>();
        getSubOrderStatusList(subOrderStatus, subOrderStatusList, orderStatusList);

        List<String> childOrderIdList = new ArrayList<String>();
        if (StringUtils.isNotBlank(chooseChildrenIdS)) {
            chooseChildrenIdS = chooseChildrenIdS.replace("[", "");
            chooseChildrenIdS = chooseChildrenIdS.replace("]", "");
            chooseChildrenIdS = chooseChildrenIdS.replace("\"", "");
            String[] split = chooseChildrenIdS.split(",");
            for (String childrenOrderId : split) {
                if (StringUtils.isNotBlank(childrenOrderId)
                    && StringUtils.notEquals(childrenOrderId, "null"))
                    childOrderIdList.add(childrenOrderId);
            }
        }
        if (CollectionUtils.isEmpty(childOrderIdList)) {
            childOrderIdList = null;
        }

        String CAN_APPLY_DAY = sysConfigCacheManager
            .getConfigValue(SysConfigCacheKeyEnum.CAN_APPLY_DAY);

        //isCashier 是否营业员折扣   0否  1是
        //cashierUser   收银操作员  （手机号  姓名  编号）

        Date endTime = DateUtil.stringToDate(endDate, DateUtil.webFormat);
        if (null != endTime)
            endTime = DateUtil.addDays(endTime, 1);

        List<Map<String, Object>> selectByQuery = orderDAO.selectByQuery(userId, orderId,
            childOrderId, productId, productName, productErpNo, orderStatusList, transferType,
            teamId, teamName, teamErpNo, userCell, orderCell, payWay,
            DateUtil.stringToDate(startDate, DateUtil.webFormat), endTime, childOrderIdList,
            rankType, errFlg, subOrderStatusList, isCashier, cashierUser, voucher, barCode, offset,
            limit);
        for (Map<String, Object> map : selectByQuery) {
            Money realOrderPrice = BigdecimalUtil.toMoney((BigDecimal) map.get("realOrderPrice"));
            map.put("realOrderPrice", realOrderPrice);//现金
            Money orderPrice = BigdecimalUtil.toMoney((BigDecimal) map.get("orderPrice"));
            map.put("orderPrice", orderPrice);//
            Money childOrderPrice = BigdecimalUtil.toMoney((BigDecimal) map.get("childOrderPrice"));
            map.put("childOrderPrice", childOrderPrice);//订单金额
            Money realPointPrice = BigdecimalUtil.toMoney((BigDecimal) map.get("realPointPrice"));
            map.put("realPointPrice", realPointPrice);//支付积分
            Money pointPrice = BigdecimalUtil.toMoney((BigDecimal) map.get("pointPrice"));
            map.put("pointPrice", pointPrice);
            Money productPrice = BigdecimalUtil.toMoney((BigDecimal) map.get("productPrice"));
            map.put("productPrice", productPrice);
            Money luggage = BigdecimalUtil.toMoney((BigDecimal) map.get("luggage"));
            map.put("luggage", luggage);//运费
            Money reduceAmount = BigdecimalUtil.toMoney((BigDecimal) map.get("reduceAmount"));
            map.put("reduceAmount", reduceAmount);//已优惠金额
            Money reduceAmountPlan = BigdecimalUtil.toMoney((BigDecimal) map
                .get("reduceAmountPlan"));
            map.put("reduceAmountPlan", reduceAmountPlan);
            Money reduceAmountTemp = BigdecimalUtil.toMoney((BigDecimal) map
                .get("reduceAmountTemp"));
            map.put("reduceAmountTemp", reduceAmountTemp);
            Money reduceAmountVoucher = BigdecimalUtil.toMoney((BigDecimal) map
                .get("reduceAmountVoucher"));
            map.put("reduceAmountVoucher", reduceAmountVoucher);//庆余卡

            Money productOldPrice = BigdecimalUtil.toMoney((BigDecimal) map.get("productOldPrice"));
            Double orderCount = MapUtils.getDouble(map, "orderCount");
            map.put("oldAllAmount", productOldPrice.multiply(orderCount));//订单原价

            Money discountMoney = BigdecimalUtil.toMoney((BigDecimal) map.get("discountMoney"));
            map.put("discountMoney", discountMoney);//折扣金额

            // 积分抵扣现金
            Money reducePoint = BigdecimalUtil.toMoney((BigDecimal) map.get("reducePoint"));
            map.put("reducePoint", reducePoint);
            Money reducePointMoney = BigdecimalUtil.toMoney((BigDecimal) map
                .get("reducePointMoney"));
            map.put("reducePointMoney", reducePointMoney);

            map.put("amountAndVoucher", realOrderPrice.add(reduceAmountVoucher));//已支付金额

            String reducePro = MapUtils.getString(map, "reducePro");
            String reducePlat = MapUtils.getString(map, "reducePlat");
            String reduceTeam = MapUtils.getString(map, "reduceTeam");

            Money reduceAmountDiscount = ReduceAmountUtil.getChildOrderReduceAmountS(reducePro,
                reducePlat, reduceTeam);
            map.put("reduceAmountDiscount", reduceAmountDiscount);//优惠券

            String orderStateEnum = MapUtils.getString(map, "orderState");
            OrderStatusEnum orderStatusEnum = OrderStatusEnum.getByCode(orderStateEnum);
            if (null != orderStatusEnum)
                map.put("orderStateStr", orderStatusEnum.message());

            String subOrderStatusEnum = MapUtils.getString(map, "subOrderStatus");
            OrderSubStatusEnum orderSubStatusEnum = OrderSubStatusEnum
                .getByCode(subOrderStatusEnum);
            if (null != orderSubStatusEnum)
                map.put("subOrderStatusStr", orderSubStatusEnum.message());

            if (orderSubStatusEnum == OrderSubStatusEnum.CHECK_DAQU_MA
                && orderStatusEnum != OrderStatusEnum.WAIT_CHECK) {
                map.put("subOrderStatusStr", null);
            }

            //延长标识
            map.put("canDelaty", false);
            String delayFlg = MapUtils.getString(map, "delayFlg");
            Date completeDate = (Date) map.get("completeDate");
            if (StringUtils.isNotBlank(delayFlg) && null != completeDate) {
                if (StringUtils.isNotBlank(CAN_APPLY_DAY)) {
                    int day = Integer.valueOf(CAN_APPLY_DAY);
                    Date beforeDate = DateUtil.getBeforeDate(day);
                    if (!completeDate.after(beforeDate) && StringUtils.equals(delayFlg, "0")) {
                        map.put("canDelaty", true);
                    }
                }
            }

            String orderIdThis = MapUtils.getString(map, "orderId");

            OrderPickDO orderPickDO = orderPickDAO.queryByOrderId(orderIdThis);
            if (null != orderPickDO) {
                map.put("pickMsg", orderPickDO.getPickDay() + "，" + orderPickDO.getStartTime()
                                   + ":" + orderPickDO.getEndTime());
            }

            Double serviceTimes = MapUtils.getDouble(map, "serviceTimes");
            map.put("hasConfirmNum", orderCount - serviceTimes);
            map.put("notConfirmNum", serviceTimes);

            String voucherImg = MapUtils.getString(map, "voucherImg");
            map.put("voucherImg", StringToListUtil.toList(voucherImg));
        }
        jo.put("rows", selectByQuery);
        jo.put("total", orderDAO.selectCountByQuery(userId, orderId, childOrderId, productId,
            productName, productErpNo, orderStatusList, transferType, teamId, teamName, teamErpNo,
            userCell, orderCell, payWay, DateUtil.stringToDate(startDate, DateUtil.webFormat),
            endTime, childOrderIdList, rankType, errFlg, subOrderStatusList, isCashier,
            cashierUser, voucher, barCode));
        return jo;
    }

    /**
     * 按条件导出所有订单列表
     * 
     * @param userId
     * @param orderId
     * @param productId
     * @param orderState
     * @param transferType
     * @param teamId
     * @param teamName
     * @param teamErpNo
     * @param userCell
     * @param orderCell
     * @param payWay
     * @param startDate
     * @param endDate
     * @param response
     */
    @RequestMapping("/exportAllPage.do")
    public void exportAllPage(String userId, String orderId, String childOrderId, String productId,
                              String productName, Integer orderState, String transferType,
                              String teamId, String teamName, String teamErpNo, String userCell,
                              String orderCell, String payWay, String startDate, String endDate,
                              int offset, int limit, String type, String errFlg,
                              Integer subOrderStatus, String isCashier, String cashierUser,
                              String productErpNo, String voucher, String barCode,
                              HttpServletResponse response) {
        try {
            List<String> orderStatusList = new ArrayList<String>();
            getOrderStatusList(orderState, orderStatusList);
            List<String> subOrderStatusList = new ArrayList<String>();
            getSubOrderStatusList(subOrderStatus, subOrderStatusList, orderStatusList);

            Date endTime = DateUtil.stringToDate(endDate, DateUtil.webFormat);
            if (null != endTime)
                endTime = DateUtil.addDays(endTime, 1);

            List<Map<String, Object>> selectByQuery = new LinkedList<Map<String, Object>>();
            if (StringUtils.equals(type, "thisPage")) {
                selectByQuery = orderDAO.selectByQuery(userId, orderId, childOrderId, productId,
                    productName, productErpNo, orderStatusList, transferType, teamId, teamName,
                    teamErpNo, userCell, orderCell, payWay,
                    DateUtil.stringToDate(startDate, DateUtil.webFormat), endTime, null, "0",
                    errFlg, subOrderStatusList, isCashier, cashierUser, voucher, barCode, offset,
                    limit);
            }
            if (StringUtils.equals(type, "all")) {
                selectByQuery = orderDAO.selectByQuery(userId, orderId, childOrderId, productId,
                    productName, productErpNo, orderStatusList, transferType, teamId, teamName,
                    teamErpNo, userCell, orderCell, payWay,
                    DateUtil.stringToDate(startDate, DateUtil.webFormat), endTime, null, "0",
                    errFlg, subOrderStatusList, isCashier, cashierUser, voucher, barCode, null,
                    null);
            }
            if (selectByQuery.isEmpty()) {
                throw new RuntimeException("未找到相关数据");
            } else {
                for (Map<String, Object> map : selectByQuery) {
                    String thisOrderState = MapUtils.getString(map, "orderState");
                    OrderStatusEnum orderStatusEnum = OrderStatusEnum.getByCode(thisOrderState);
                    if (orderStatusEnum != null) {
                        map.put("orderState", orderStatusEnum.message());
                    } else {
                        map.put("orderState", "---");
                    }

                    String payStateTmp = MapUtils.getString(map, "payState");
                    PayStatusEnum payStatusEnum = PayStatusEnum.getByCode(payStateTmp);
                    if (payStatusEnum != null) {
                        map.put("payState", payStatusEnum.message());
                    } else {
                        map.put("payState", "---");
                    }

                    String payWayTmp = MapUtils.getString(map, "payWay");
                    OrderPayWayEnum orderPayWayEnum = OrderPayWayEnum.getByCode(payWayTmp);
                    if (orderPayWayEnum != null) {
                        map.put("payWay", orderPayWayEnum.message());
                    } else {
                        map.put("payWay", "---");
                    }

                    String productTypeTmp = MapUtils.getString(map, "productType");
                    ProTypeEnum proTypeEnum = ProTypeEnum.getByCode(productTypeTmp);
                    if (proTypeEnum != null) {
                        map.put("productType", proTypeEnum.message());
                    } else {
                        map.put("productType", "---");
                    }

                    String transferTypeTmp = MapUtils.getString(map, "transferType");
                    if (transferTypeTmp.equals("0")) {
                        map.put("transferType", "物流配送");
                        String province = MapUtils.getString(map, "province");
                        String city = MapUtils.getString(map, "city");
                        String area = MapUtils.getString(map, "area");
                        String reAddr = MapUtils.getString(map, "reAddr");
                        map.put("receiveAddress", province + city + area + reAddr);
                    } else if (transferTypeTmp.equals("1")) {
                        map.put("transferType", "到店自取");
                    }

                    Money realOrderPrice = BigdecimalUtil.toMoney((BigDecimal) map
                        .get("realOrderPrice"));
                    map.put("realOrderPrice", realOrderPrice.toSimpleString());//现金
                    Money orderPrice = BigdecimalUtil.toMoney((BigDecimal) map.get("orderPrice"));
                    map.put("orderPrice", orderPrice.toSimpleString());
                    Money childOrderPrice = BigdecimalUtil.toMoney((BigDecimal) map
                        .get("childOrderPrice"));//订单金额
                    map.put("childOrderPrice", childOrderPrice.toSimpleString());
                    Money realPointPrice = BigdecimalUtil.toMoney((BigDecimal) map
                        .get("realPointPrice"));
                    map.put("realPointPrice", realPointPrice.toSimpleString());//支付积分
                    Money pointPrice = BigdecimalUtil.toMoney((BigDecimal) map.get("pointPrice"));
                    map.put("pointPrice", pointPrice.toSimpleString());
                    Money productPrice = BigdecimalUtil.toMoney((BigDecimal) map
                        .get("productPrice"));
                    map.put("productPrice", productPrice.toSimpleString());
                    Money luggage = BigdecimalUtil.toMoney((BigDecimal) map.get("luggage"));
                    map.put("luggage", luggage.toSimpleString());//运费
                    Money reduceAmount = BigdecimalUtil.toMoney((BigDecimal) map
                        .get("reduceAmount"));//已优惠金额
                    map.put("reduceAmount", reduceAmount.toSimpleString());
                    Money reduceAmountPlan = BigdecimalUtil.toMoney((BigDecimal) map
                        .get("reduceAmountPlan"));
                    map.put("reduceAmountPlan", reduceAmountPlan.toSimpleString());
                    Money reduceAmountTemp = BigdecimalUtil.toMoney((BigDecimal) map
                        .get("reduceAmountTemp"));
                    map.put("reduceAmountTemp", reduceAmountTemp.toSimpleString());
                    Money reduceAmountVoucher = BigdecimalUtil.toMoney((BigDecimal) map
                        .get("reduceAmountVoucher"));
                    map.put("reduceAmountVoucher", reduceAmountVoucher.toSimpleString());//庆余卡

                    Money productOldPrice = BigdecimalUtil.toMoney((BigDecimal) map
                        .get("productOldPrice"));
                    Double orderCount = MapUtils.getDouble(map, "orderCount");
                    map.put("oldAllAmount", productOldPrice.multiply(orderCount).toSimpleString());//订单原价

                    Money discountMoney = BigdecimalUtil.toMoney((BigDecimal) map
                        .get("discountMoney"));
                    map.put("discountMoney", discountMoney.toSimpleString());//折扣金额

                    // 积分抵扣现金
                    Money reducePoint = BigdecimalUtil.toMoney((BigDecimal) map.get("reducePoint"));
                    Money reducePointMoney = BigdecimalUtil.toMoney((BigDecimal) map
                        .get("reducePointMoney"));
                    map.put("reducePoint",
                        reducePoint.toSimpleString() + "积分抵扣" + reducePointMoney.toSimpleString()
                                + "元现金");

                    map.put("amountAndVoucher", realOrderPrice.add(reduceAmountVoucher));//已支付金额

                    String reducePro = MapUtils.getString(map, "reducePro");
                    String reducePlat = MapUtils.getString(map, "reducePlat");
                    String reduceTeam = MapUtils.getString(map, "reduceTeam");

                    Money reduceAmountDiscount = ReduceAmountUtil.getChildOrderReduceAmountS(
                        reducePro, reducePlat, reduceTeam);
                    map.put("reduceAmountDiscount", reduceAmountDiscount.toSimpleString());//优惠券

                    Date gmtCreate = (Date) map.get("gmtCreate");
                    Date gmtModified = (Date) map.get("gmtModified");
                    map.put("gmtCreate", DateUtil.dateToString(gmtCreate, DateUtil.newFormat));
                    map.put("gmtModified", DateUtil.dateToString(gmtModified, DateUtil.newFormat));

                    String errFlgS = MapUtils.getString(map, "errFlg");
                    if (StringUtils.equals(errFlgS, "0")) {
                        map.put("errFlg", "否");
                    }
                    if (StringUtils.equals(errFlgS, "1")) {
                        map.put("errFlg", "是");
                    }

                    String subOrderStatusEnum = MapUtils.getString(map, "subOrderStatus");
                    OrderSubStatusEnum orderSubStatusEnum = OrderSubStatusEnum
                        .getByCode(subOrderStatusEnum);
                    if (null != orderSubStatusEnum)
                        map.put("subOrderStatusStr", orderSubStatusEnum.message());

                    String orderIdThis = MapUtils.getString(map, "orderId");

                    OrderPickDO orderPickDO = orderPickDAO.queryByOrderId(orderIdThis);
                    if (null != orderPickDO) {
                        map.put("pickMsg",
                            orderPickDO.getPickDay() + "，" + orderPickDO.getStartTime() + ":"
                                    + orderPickDO.getEndTime());
                    }

                    Double serviceTimes = MapUtils.getDouble(map, "serviceTimes");
                    map.put("hasConfirmNum", orderCount - serviceTimes);
                    map.put("notConfirmNum", serviceTimes);
                }
                // 表头
                String[] headers = { "订单状态", "是否异常", "退款状态", "支付状态", "支付方式", "创建日期", "订单编号",
                        "取货方式", "商品名称", "商品属性", "购买数量", "自提时间", "已核销数", "未核销数", "订单原价", "运费",
                        "已优惠金额", "订单金额", "优惠券", "折扣金额", "积分抵扣现金", "支付金额", "庆余卡", "现金", "支付积分",
                        "收货地址", "收货人", "联系电话", "商品类型", "修改者", "修改时间", "团队名称", "商超凭证号", "子订单编号",
                        "用户编号" };
                // 数据键名或者MODEL类字段名
                String[] Col = { "orderState", "errFlg", "subOrderStatusStr", "payState", "payWay",
                        "gmtCreate", "orderId", "transferType", "productName", "valuees",
                        "orderCount", "pickMsg", "hasConfirmNum", "notConfirmNum", "oldAllAmount",
                        "luggage", "reduceAmount", "childOrderPrice", "reduceAmountDiscount",
                        "discountMoney", "reducePoint", "amountAndVoucher", "reduceAmountVoucher",
                        "realOrderPrice", "realPointPrice", "receiveAddress", "reUserName", "cell",
                        "productType", "modifier", "gmtModified", "teamName", "voucher",
                        "childOrderId", "userId" };

                //                ExportExcel<Map<String, Object>> ex = new ExportExcel<Map<String, Object>>();
                //                List<Map<String, Object>> excelList = selectByQuery;
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
                HSSFWorkbook book = ExportExcel.exportExcel(fileName, headers, Col, selectByQuery,
                    null);
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
     * 导出待支付订单
     * 
     * @param userId
     * @param orderId
     * @param productId
     * @param orderState
     * @param transferType
     * @param teamId
     * @param teamName
     * @param teamErpNo
     * @param userCell
     * @param orderCell
     * @param payWay
     * @param startDate
     * @param endDate
     * @param response
     */
    @RequestMapping("/exportAllWaitPage.do")
    public void exportAllWaitPage(String userId, String orderId, String childOrderId,
                                  String productId, String productName, String orderState,
                                  String transferType, String teamId, String teamName,
                                  String teamErpNo, String userCell, String orderCell,
                                  String payWay, String startDate, String endDate,
                                  String[] chooseChildrenIdS, String errFlg, String subOrderStatus,
                                  String isCashier, String cashierUser, String productErpNo,
                                  String voucher, String barCode, HttpServletResponse response,
                                  HttpServletRequest request) {

        try {

            List<String> orderStatusList = new ArrayList<String>();
            orderStatusList.add(OrderStatusEnum.NOT_SEND.getCode());
            orderStatusList.add(OrderStatusEnum.NOT_SEND_ING.getCode());

            List<String> childOrderIdList = new ArrayList<String>();
            for (String childrenOrderId : chooseChildrenIdS) {
                if (StringUtils.isNotBlank(childrenOrderId))
                    childOrderIdList.add(childrenOrderId);
            }
            if (CollectionUtils.isEmpty(childOrderIdList)) {
                childOrderIdList = null;
            }

            Date endTime = DateUtil.stringToDate(endDate, DateUtil.webFormat);
            if (null != endTime)
                endTime = DateUtil.addDays(endTime, 1);

            List<Map<String, Object>> selectByQuery = orderDAO.selectByQuery(userId, orderId,
                childOrderId, productId, productName, productErpNo, orderStatusList, transferType,
                teamId, teamName, teamErpNo, userCell, orderCell, payWay,
                DateUtil.stringToDate(startDate, DateUtil.webFormat), endTime, childOrderIdList,
                "1", errFlg, null, isCashier, cashierUser, voucher, barCode, null, null);
            if (selectByQuery.isEmpty()) {
                throw new RuntimeException("未找到相关数据");
            } else {
                List<String> changeStatusChildOrderId = new ArrayList<String>();
                for (Map<String, Object> map : selectByQuery) {
                    String thisOrderState = MapUtils.getString(map, "orderState");
                    OrderStatusEnum orderStatusEnum = OrderStatusEnum.getByCode(thisOrderState);
                    if (orderStatusEnum != null) {
                        map.put("orderState", orderStatusEnum.message());
                    } else {
                        map.put("orderState", "---");
                    }

                    String payStateTmp = MapUtils.getString(map, "payState");
                    PayStatusEnum payStatusEnum = PayStatusEnum.getByCode(payStateTmp);
                    if (payStatusEnum != null) {
                        map.put("payState", payStatusEnum.message());
                    } else {
                        map.put("payState", "---");
                    }

                    String payWayTmp = MapUtils.getString(map, "payWay");
                    OrderPayWayEnum orderPayWayEnum = OrderPayWayEnum.getByCode(payWayTmp);
                    if (orderPayWayEnum != null) {
                        map.put("payWay", orderPayWayEnum.message());
                    } else {
                        map.put("payWay", "---");
                    }

                    String productTypeTmp = MapUtils.getString(map, "productType");
                    ProTypeEnum proTypeEnum = ProTypeEnum.getByCode(productTypeTmp);
                    if (proTypeEnum != null) {
                        map.put("productType", proTypeEnum.message());
                    } else {
                        map.put("productType", "---");
                    }

                    String transferTypeTmp = MapUtils.getString(map, "transferType");
                    if (transferTypeTmp.equals("0")) {
                        map.put("transferType", "物流配送");
                        String province = MapUtils.getString(map, "province");
                        String city = MapUtils.getString(map, "city");
                        String area = MapUtils.getString(map, "area");
                        String reAddr = MapUtils.getString(map, "reAddr");
                        map.put("receiveAddress", province + city + area + reAddr);
                    } else if (transferTypeTmp.equals("1")) {
                        map.put("transferType", "到店自取");
                    }

                    Money realOrderPrice = BigdecimalUtil.toMoney((BigDecimal) map
                        .get("realOrderPrice"));
                    map.put("realOrderPrice", realOrderPrice.toSimpleString());
                    Money orderPrice = BigdecimalUtil.toMoney((BigDecimal) map.get("orderPrice"));
                    map.put("orderPrice", orderPrice.toSimpleString());
                    Money childOrderPrice = BigdecimalUtil.toMoney((BigDecimal) map
                        .get("childOrderPrice"));
                    map.put("childOrderPrice", childOrderPrice.toSimpleString());
                    Money realPointPrice = BigdecimalUtil.toMoney((BigDecimal) map
                        .get("realPointPrice"));
                    map.put("realPointPrice", realPointPrice.toSimpleString());
                    Money pointPrice = BigdecimalUtil.toMoney((BigDecimal) map.get("pointPrice"));
                    map.put("pointPrice", pointPrice.toSimpleString());
                    Money productPrice = BigdecimalUtil.toMoney((BigDecimal) map
                        .get("productPrice"));
                    map.put("productPrice", productPrice.toSimpleString());
                    Money luggage = BigdecimalUtil.toMoney((BigDecimal) map.get("luggage"));
                    map.put("luggage", luggage.toSimpleString());
                    Money reduceAmount = BigdecimalUtil.toMoney((BigDecimal) map
                        .get("reduceAmount"));
                    map.put("reduceAmount", reduceAmount.toSimpleString());
                    Money reduceAmountPlan = BigdecimalUtil.toMoney((BigDecimal) map
                        .get("reduceAmountPlan"));
                    map.put("reduceAmountPlan", reduceAmountPlan.toSimpleString());
                    Money reduceAmountTemp = BigdecimalUtil.toMoney((BigDecimal) map
                        .get("reduceAmountTemp"));
                    map.put("reduceAmountTemp", reduceAmountTemp.toSimpleString());
                    Money reduceAmountVoucher = BigdecimalUtil.toMoney((BigDecimal) map
                        .get("reduceAmountVoucher"));
                    map.put("reduceAmountVoucher", reduceAmountVoucher.toSimpleString());

                    String reducePro = MapUtils.getString(map, "reducePro");
                    String reducePlat = MapUtils.getString(map, "reducePlat");
                    String reduceTeam = MapUtils.getString(map, "reduceTeam");

                    Money reduceAmountDiscount = ReduceAmountUtil.getChildOrderReduceAmountS(
                        reducePro, reducePlat, reduceTeam);
                    map.put("reduceAmountDiscount", reduceAmountDiscount.toSimpleString());

                    Date gmtCreate = (Date) map.get("gmtCreate");
                    Date gmtModified = (Date) map.get("gmtModified");
                    map.put("gmtCreate", DateUtil.dateToString(gmtCreate, DateUtil.newFormat));
                    map.put("gmtModified", DateUtil.dateToString(gmtModified, DateUtil.newFormat));

                    changeStatusChildOrderId.add((String) map.get("childOrderId"));
                }

                //订单改为等待发货
                orderDAO.updateOrderStatusToNotSendIngByChildIdList(
                    OrderStatusEnum.NOT_SEND_ING.getCode(), changeStatusChildOrderId);

                // 表头
                String[] headers = { "订单时间", "收货人", "收货电话", "收货地址", "订单状态", "取货方式", "实付金额", "实付积分",
                        "商品名称", "商品属性", "购买数量", "运费", "erp编号", "父订单编号", "子订单编号", "用户编号", "商品编号",
                        "物流公司（编号）", "物流单号" };
                // 数据键名或者MODEL类字段名
                String[] Col = { "gmtModified", "reUserName", "cell", "receiveAddress",
                        "orderState", "transferType", "realOrderPrice", "realPointPrice",
                        "productName", "valuees", "orderCount", "luggage", "erpNo", "orderId",
                        "childOrderId", "userId", "productId", "", "" };

                //                ExportExcel<Map<String, Object>> ex = new ExportExcel<Map<String, Object>>();
                //                List<Map<String, Object>> excelList = selectByQuery;
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
                HSSFWorkbook book = ExportExcel.exportExcel(fileName, headers, Col, selectByQuery,
                    null);
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
     * 查询所有的快递公司
     * 
     * @param request
     * @return
     */
    @RequestMapping("queryKuaidiComs.do")
    @ResponseBody
    public Object queryKuaidiComs(final HttpServletRequest request) {
        final JsonQueryResult<List<SysExpressDO>> result = new JsonQueryResult<List<SysExpressDO>>(
            true);
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {
                result.setObj(sysExpressDAO.selectAll());
            }

            @Override
            public void check() {
            }
        });
        return result;
    }

    /**
     * 添加物流信息
     * 
     * @param request
     * @return
     */
    @RequestMapping("addWuliuInfo.do")
    @ResponseBody
    public Object addWuliuInfo(final HttpServletRequest request) {
        final JsonResult result = new JsonResult(true);
        final String childOrderId = request.getParameter("childOrderId");
        final String expressNo = request.getParameter("companyNo");
        final String kuaidiNo = request.getParameter("kuaidiNo");

        final String user_id = request.getSession().getAttribute("user_id").toString();
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {
                OrderDO existDo = orderDAO.selectByChildOrderId(childOrderId);
                if (null == existDo) {
                    throw new RuntimeException("订单不存在");
                }

                result.setSuccess(0 < orderDAO.updateWuliuAndSta(expressNo, kuaidiNo, user_id,
                    childOrderId));
                if (result.isSuccess()) {
                    result.setMessage("操作成功");
                } else {
                    result.setMessage("操作失败");
                }

            }

            @Override
            public void check() {
                AssertUtil.notBlank(childOrderId, "订单编号不能为空");
                AssertUtil.notBlank(expressNo, "物流公司不能为空");
                AssertUtil.notBlank(kuaidiNo, "快递单号不能为空");
            }
        });
        return result;
    }

    /**
     * 查看物流信息
     * 
     * @param request
     * @return
     */
    @RequestMapping("showWuliuInfo.do")
    @ResponseBody
    public Object showWuliuInfo(final HttpServletRequest request) {
        final JsonQueryResult<ExpressQueryPojo> result = new JsonQueryResult<ExpressQueryPojo>(true);
        final String comNo = request.getParameter("companyNo");
        final String kuaidiNo = request.getParameter("kuaidiNo");
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {
                SysExpressDO expressDO = sysExpressDAO.selectByExpressNo(comNo);
                if (null == expressDO) {
                    throw new RuntimeException("记录不存在");
                }

                JsonQueryResult<ExpressQueryPojo> routeRequest = shunfengService.routeRequest(
                    expressDO.getExpressName(), kuaidiNo);
                if (routeRequest.isSuccess()) {
                    result.setObj(routeRequest.getObj());
                    result.setMessage(routeRequest.getMessage());
                    result.setSuccess(routeRequest.isSuccess());
                    return;
                }
                
                QueryResult<ExpressQueryPojo> queryResult = expressComponent.getKuaiDiInfo4Firm(
                    expressDO.getCom(), kuaidiNo);
                if (null != queryResult.getResultObject()) {
                    queryResult.getResultObject().setCompanyName(expressDO.getExpressName());
                    result.setObj(queryResult.getResultObject());
                } else {
                    ExpressQueryPojo resultObject = queryResult.getResultObject();
                    resultObject = new ExpressQueryPojo();
                    resultObject.setState("暂无物流信息");
                    resultObject.setCompanyName(expressDO.getExpressName());
                    resultObject.setNu(kuaidiNo);
                    resultObject.setData(new ArrayList<ExpressDataPojo>());
                    result.setObj(resultObject);
                }
                
            }

            @Override
            public void check() {
                AssertUtil.notBlank(comNo, "请选择物流公司");
                AssertUtil.notBlank(kuaidiNo, "快递单号不能为空");
            }
        });
        return result;
    }

    /**
     * 修改订单状态 type:ATH(同意退货) DTH(驳回退货) GTH(收到退货)
     * 
     * @param request
     * @return
     */
    @RequestMapping("editOrderSta.do")
    @ResponseBody
    public Object editOrderSta(final HttpServletRequest request) {
        final JsonResult result = new JsonResult(true);
        final String childOrderId = request.getParameter("childOrderId");
        final String type = request.getParameter("type");

        final String user_id = request.getSession().getAttribute("user_id").toString();
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {
                OrderDO orderExist = orderDAO.selectByChildOrderId(childOrderId);
                if (null == orderExist) {
                    throw new RuntimeException("订单不存在");
                }
                if (StringUtils.equals("ATH", type)) {
                    if (!StringUtils.equals("4", orderExist.getOrderState())) {
                        throw new RuntimeException("不能操作的订单");
                    }
                    // 订单状态由 申请退货-->7审核成功（待退货）
                    result.setSuccess(0 < orderDAO.updateOrderStatByChildId("7", user_id,
                        childOrderId));

                } else if (StringUtils.equals("DTH", type)) {
                    if (!StringUtils.equals("4", orderExist.getOrderState())) {
                        throw new RuntimeException("不能操作的订单");
                    }
                    // 订单状态由 申请退货-->9审核驳回（退货）
                    result.setSuccess(0 < orderDAO.updateOrderStatByChildId("9", user_id,
                        childOrderId));

                } else if (StringUtils.equals("GTH", type)) {
                    if (!StringUtils.equals("7", orderExist.getOrderState())) {
                        throw new RuntimeException("不能操作的订单");
                    }
                    // 订单状态由 7审核成功（待退货）-->5退货成功(待退款)
                    result.setSuccess(0 < orderDAO.updateOrderStatByChildId("5", user_id,
                        childOrderId));
                }
            }

            @Override
            public void check() {
                AssertUtil.notBlank(type, "类型不能为空");
                AssertUtil.notBlank(childOrderId, "订单编号不能为空");
            }
        });
        return result;

    }

    /**
     * 退款
     * 
     * @param request
     * @return
     */
    @RequestMapping("tuikuan.do")
    @ResponseBody
    public Object tuikuan(final HttpServletRequest request) {
        final JsonResult result = new JsonResult(true);
        final String childOrderId = request.getParameter("childOrderId");

        final String user_id = request.getSession().getAttribute("user_id").toString();
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {

                OrderDO orderExist = orderDAO.selectByChildOrderId(childOrderId);
                if (null == orderExist) {
                    throw new RuntimeException("订单不存在");
                }

                if (!StringUtils.equals(orderExist.getOrderState(), "5")) {
                    throw new RuntimeException("不可操作的订单");
                }

                // 积分商品只需要退回积分
                BaseResult baseResult = new BaseResult(false);

                if (StringUtils.equals(orderExist.getProductType(), "3")) {

                    baseResult = accountTransComponent.tuiKuanForAccount(orderExist.getUserId(),
                        orderExist.getRealOrderPrice(), "3", childOrderId, user_id);

                } else if (StringUtils.equalsAny(orderExist.getProductType(), new String[] { "1",
                        "2" })) {
                    // 判断支付类型 1 微信 2 支付宝 3余额(胡币) 4其他
                    if (StringUtils.equals(orderExist.getPayWay(), "1")) {
                        baseResult = accountTransComponent.tuiKuanForWeChatS(orderExist,
                            orderExist.getRealOrderPrice(), 0);
                    } else if (StringUtils.equals(orderExist.getPayWay(), "2")) {

                        baseResult = accountTransComponent.tuiKuanForZFB(orderExist, null);

                    } else if (StringUtils.equals(orderExist.getPayWay(), "3")) {
                        baseResult = accountTransComponent.tuiKuanForAccount(
                            orderExist.getUserId(), orderExist.getRealOrderPrice(), "2",
                            childOrderId, user_id);
                    }
                }
                // 发送通知消息
                if (baseResult.isSuccess()) {
                    // 扣除推荐人获得的返利
                    OrderRebateDO orderRebateDo = orderRebateDAO.selectByChildOrderId(childOrderId);
                    if (orderRebateDo != null) {
                        accountDAO.updateAmount(orderRebateDo.getHuAmount() + "",
                            orderRebateDo.getPointAmount() + "", orderExist.getRecommendId());
                        // accountTempDAO.updateBalanceAmount(orderRebateDo.getHuTempAmount()
                        // + "",
                        // orderExist.getRecommendId());
                        // accountTempDAO.updatePointAmount(orderRebateDo.getPointTempAmount()
                        // + "",
                        // orderExist.getRecommendId());
                    }
                    MsgDO msg = new MsgDO();
                    msg.setMsgTitle("退款成功");
                    msg.setUserId(orderExist.getUserId());
                    msg.setMsgType("2");
                    msg.setMsgContent("退款成功");
                    msg.setIsDel("0");
                    msg.setIsRead("false");
                    msgDAO.insertMsg(msg);
                }
                result.setSuccess(baseResult.isSuccess());
                result.setMessage(baseResult.getMessage());

            }

            @Override
            public void check() {
                AssertUtil.notBlank(childOrderId, "订单编号不能为空");

            }
        });
        return result;
    }

    /**
     * 查询退货信息
     * 
     * @param request
     * @return
     */
    @RequestMapping("queryReturnInfo.do")
    @ResponseBody
    public Object queryReturnInfo(final HttpServletRequest request) {
        final JsonQueryResult<JSONObject> result = new JsonQueryResult<JSONObject>(true);
        final String childOrderId = request.getParameter("childOrderId");
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {
                OrderReturnDO orderReturnDO = orderReturnDAO.selectByChildOrderId(childOrderId);
                if (null == orderReturnDO) {
                    return;
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("returnInfo", orderReturnDO);
                if (StringUtils.hasLength(orderReturnDO.getCarriNo())) {
                    SysExpressDO sysExpressDO = sysExpressDAO.selectByExpressNo(orderReturnDO
                        .getCarriCom());
                    
                    JsonQueryResult<ExpressQueryPojo> routeRequest = shunfengService.routeRequest(
                        orderReturnDO.getCarriNo(), sysExpressDO.getCom());
                    if (routeRequest.isSuccess()) {
                        routeRequest.getObj().setCompanyName(sysExpressDO.getExpressName());
                        jsonObject.put("wuliuInfo", routeRequest.getObj());
                        return;
                    }
                    
                    QueryResult<ExpressQueryPojo> queryResult = expressComponent.getKuaiDiInfo4Firm(
                        sysExpressDO.getCom(), orderReturnDO.getCarriNo());
                    if (null != queryResult.getResultObject()) {
                        queryResult.getResultObject().setCompanyName(sysExpressDO.getExpressName());

                        jsonObject.put("wuliuInfo", queryResult.getResultObject());
                    } else {
                        ExpressQueryPojo resultObject = queryResult.getResultObject();
                        resultObject = new ExpressQueryPojo();
                        resultObject.setState("暂无物流信息");
                        resultObject.setCompanyName(sysExpressDO.getExpressName());
                        resultObject.setNu(orderReturnDO.getCarriNo());
                        resultObject.setData(new ArrayList<ExpressDataPojo>());

                        jsonObject.put("wuliuInfo", queryResult.getResultObject());
                    }
                    
                    if (!queryResult.isSuccess()) {
                        result.setSuccess(false);
                        result.setMessage(queryResult.getMessage());
                    }
                }

                result.setObj(jsonObject);

            }

            @Override
            public void check() {
                AssertUtil.notBlank(childOrderId, "订单编号不存在");
            }
        });

        return result;

    }

    /**
     * 批量发货
     * 
     * @param request
     * @return
     */
    @RequestMapping("manyAddWuliu.do")
    @ResponseBody
    public Object childOrderIdArr(final HttpServletRequest request) {

        final JsonResult baseResult = new JsonResult(true);
        final String childOrderIdArr = request.getParameter("childOrderIdArr");
        final String expressNo = request.getParameter("companyNo");
        final String kuaidiNo = request.getParameter("kuaidiNo");

        final String user_id = request.getSession().getAttribute("user_id").toString();
        controllerTemplate.execute(baseResult, new ControllerCallBack() {

            @Override
            public void executeService() {
                JSONArray parseArray = JSONArray.parseArray(childOrderIdArr);
                for (Object object : parseArray) {
                    OrderDO existDo = orderDAO.selectByChildOrderId(object.toString());
                    if (null == existDo) {
                        throw new RuntimeException("订单信息异常");
                    }
                    if (!StringUtils.equals(existDo.getOrderState(),
                        OrderStatusEnum.NOT_SEND.getCode())
                        && !StringUtils.equals(existDo.getOrderState(),
                            OrderStatusEnum.NOT_SEND_ING.getCode())) {
                        throw new RuntimeException(existDo.getChildOrderId() + "非待发货订单");
                    }

                    if (0 >= orderDAO.updateWuliuAndSta(expressNo, kuaidiNo, user_id,
                        object.toString())) {
                        throw new RuntimeException("修改物流信息异常");
                    }

                    //发货推送
                    UserDO userInfo = userDAO.finduserbyid(existDo.getUserId());

                    if (null != userInfo) {
                        //发货通知
                        String msgContent = "您购买的商品" + existDo.getProductName() + "已于"
                                            + DateUtil.getNowString() + "发货，运单号：" + kuaidiNo;
                        msgService.newMsg(userInfo.getUserId(), MsgTypeEnum.ORDER_MSG.getCode(),
                            "订单发货通知", msgContent);

                        String linkUrl = sysConfigCacheManager
                            .getConfigValue(SysConfigCacheKeyEnum.USER_ORDER_DETAILS_API)
                                         + "?id="
                                         + existDo.getChildOrderId();

                        msgService.sendWechatCustomerMsg(userInfo.getOpenId(), linkUrl,
                            existDo.getImgSrc(), "订单发货通知", msgContent);
                    }
                }
            }

            @Override
            public void check() {
                AssertUtil.notBlank(childOrderIdArr, "订单编号不能为空");
                AssertUtil.notBlank(expressNo, "物流公司不能为空");
                AssertUtil.notBlank(kuaidiNo, "快递单号不能为空");
            }
        });

        return baseResult;

    }

    public String getPrettyNumber(String number) {
        return BigDecimal.valueOf(Double.parseDouble(number)).stripTrailingZeros().toPlainString();
    }

    /**
     * 退款订单 未回款成功
     * 
     * @param childOrderId
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/queryForOrderReturnNotSucc.do")
    @ResponseBody
    public JSONObject queryForOrderReturnNotSucc(String childOrderId, Integer offset, Integer limit) {

        JSONObject jo = new JSONObject();

        String moneyStatus = SysMsgEnum.FAIL.getCode();

        List<Map<String, Object>> selectByQuery = orderDAO.queryForOrderReturnNotSucc(childOrderId,
            moneyStatus, offset, limit);
        for (Map<String, Object> map : selectByQuery) {
            Money realOrderPrice = BigdecimalUtil.toMoney((BigDecimal) map.get("realOrderPrice"));
            map.put("realOrderPrice", realOrderPrice);
            Money orderPrice = BigdecimalUtil.toMoney((BigDecimal) map.get("orderPrice"));
            map.put("orderPrice", orderPrice);
            Money childOrderPrice = BigdecimalUtil.toMoney((BigDecimal) map.get("childOrderPrice"));
            map.put("childOrderPrice", childOrderPrice);
            Money realPointPrice = BigdecimalUtil.toMoney((BigDecimal) map.get("realPointPrice"));
            map.put("realPointPrice", realPointPrice);
            Money pointPrice = BigdecimalUtil.toMoney((BigDecimal) map.get("pointPrice"));
            map.put("pointPrice", pointPrice);
            Money productPrice = BigdecimalUtil.toMoney((BigDecimal) map.get("productPrice"));
            map.put("productPrice", productPrice);
            Money luggage = BigdecimalUtil.toMoney((BigDecimal) map.get("luggage"));
            map.put("luggage", luggage);
            Money reduceAmount = BigdecimalUtil.toMoney((BigDecimal) map.get("reduceAmount"));
            map.put("reduceAmount", reduceAmount);
            Money reduceAmountPlan = BigdecimalUtil.toMoney((BigDecimal) map
                .get("reduceAmountPlan"));
            map.put("reduceAmountPlan", reduceAmountPlan);
            Money reduceAmountTemp = BigdecimalUtil.toMoney((BigDecimal) map
                .get("reduceAmountTemp"));
            map.put("reduceAmountTemp", reduceAmountTemp);
            Money reduceAmountVoucher = BigdecimalUtil.toMoney((BigDecimal) map
                .get("reduceAmountVoucher"));
            map.put("reduceAmountVoucher", reduceAmountVoucher);

            String reducePro = MapUtils.getString(map, "reducePro");
            String reducePlat = MapUtils.getString(map, "reducePlat");
            String reduceTeam = MapUtils.getString(map, "reduceTeam");

            Money reduceAmountDiscount = ReduceAmountUtil.getChildOrderReduceAmountS(reducePro,
                reducePlat, reduceTeam);
            map.put("reduceAmountDiscount", reduceAmountDiscount);

            String orderStateEnum = MapUtils.getString(map, "orderState");
            OrderStatusEnum orderStatusEnum = OrderStatusEnum.getByCode(orderStateEnum);
            if (null != orderStatusEnum)
                map.put("orderStateStr", orderStatusEnum.message());

            String subOrderStatusEnum = MapUtils.getString(map, "subOrderStatus");
            OrderSubStatusEnum orderSubStatusEnum = OrderSubStatusEnum
                .getByCode(subOrderStatusEnum);
            if (null != orderSubStatusEnum)
                map.put("subOrderStatusStr", orderSubStatusEnum.message());

            String moneyStatusEnum = MapUtils.getString(map, "moneyStatus");
            SysMsgEnum sysMsgEnum = SysMsgEnum.getByCode(moneyStatusEnum);
            if (null != sysMsgEnum)
                map.put("moneyStatusStr", sysMsgEnum.message());

        }
        jo.put("rows", selectByQuery);
        jo.put("total", orderDAO.queryForOrderReturnNotSuccCount(childOrderId, moneyStatus));
        return jo;
    }

    /**
     * 延长订单退款退货
     * 
     * @param request
     * @return
     */
    @RequestMapping("modifyDelayFlg.do")
    @ResponseBody
    public Object modifyDelayFlg(final HttpServletRequest request) {
        final JsonResult result = new JsonResult(true);
        final String childOrderId = request.getParameter("childOrderId");

        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {
                OrderDO orderExist = orderDAO.selectByChildOrderId(childOrderId);
                if (null == orderExist) {
                    throw new RuntimeException("订单不存在");
                }

                orderDAO.modifyDelayFlg("1", childOrderId);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(childOrderId, "订单编号不能为空");
            }
        });
        return result;
    }

    @RequestMapping("/exportWaitExcel.do")
    public void exportWaitExcel(String[] chooseChildrenIdS, HttpServletRequest request,
                                HttpServletResponse response) {

        try {
            List<String> childOrderIdList = new ArrayList<String>();
            for (String childrenOrderId : chooseChildrenIdS) {
                if (StringUtils.isNotBlank(childrenOrderId))
                    childOrderIdList.add(childrenOrderId);
            }
            if (CollectionUtils.isEmpty(childOrderIdList)) {
                childOrderIdList = null;
            }

            List<Map<String, Object>> selectByQuery = orderDAO.selectByQuery(null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null, null,
                childOrderIdList, "1", null, null, null, null, null, null, null, null);
            if (selectByQuery.isEmpty()) {
                throw new RuntimeException("未找到相关数据");
            } else {
                List<String> changeStatusChildOrderId = new ArrayList<String>();
                for (Map<String, Object> map : selectByQuery) {
                    String thisOrderState = MapUtils.getString(map, "orderState");
                    OrderStatusEnum orderStatusEnum = OrderStatusEnum.getByCode(thisOrderState);
                    if (orderStatusEnum != null) {
                        map.put("orderState", orderStatusEnum.message());
                    } else {
                        map.put("orderState", "---");
                    }

                    String payStateTmp = MapUtils.getString(map, "payState");
                    PayStatusEnum payStatusEnum = PayStatusEnum.getByCode(payStateTmp);
                    if (payStatusEnum != null) {
                        map.put("payState", payStatusEnum.message());
                    } else {
                        map.put("payState", "---");
                    }

                    String payWayTmp = MapUtils.getString(map, "payWay");
                    OrderPayWayEnum orderPayWayEnum = OrderPayWayEnum.getByCode(payWayTmp);
                    if (orderPayWayEnum != null) {
                        map.put("payWay", orderPayWayEnum.message());
                    } else {
                        map.put("payWay", "---");
                    }

                    String productTypeTmp = MapUtils.getString(map, "productType");
                    ProTypeEnum proTypeEnum = ProTypeEnum.getByCode(productTypeTmp);
                    if (proTypeEnum != null) {
                        map.put("productType", proTypeEnum.message());
                    } else {
                        map.put("productType", "---");
                    }

                    String transferTypeTmp = MapUtils.getString(map, "transferType");
                    if (transferTypeTmp.equals("0")) {
                        map.put("transferType", "物流配送");
                        String province = MapUtils.getString(map, "province");
                        String city = MapUtils.getString(map, "city");
                        String area = MapUtils.getString(map, "area");
                        String reAddr = MapUtils.getString(map, "reAddr");
                        map.put("receiveAddress", province + city + area + reAddr);
                    } else if (transferTypeTmp.equals("1")) {
                        map.put("transferType", "到店自取");
                    }

                    Money realOrderPrice = BigdecimalUtil.toMoney((BigDecimal) map
                        .get("realOrderPrice"));
                    map.put("realOrderPrice", realOrderPrice.toSimpleString());
                    Money orderPrice = BigdecimalUtil.toMoney((BigDecimal) map.get("orderPrice"));
                    map.put("orderPrice", orderPrice.toSimpleString());
                    Money childOrderPrice = BigdecimalUtil.toMoney((BigDecimal) map
                        .get("childOrderPrice"));
                    map.put("childOrderPrice", childOrderPrice.toSimpleString());
                    Money realPointPrice = BigdecimalUtil.toMoney((BigDecimal) map
                        .get("realPointPrice"));
                    map.put("realPointPrice", realPointPrice.toSimpleString());
                    Money pointPrice = BigdecimalUtil.toMoney((BigDecimal) map.get("pointPrice"));
                    map.put("pointPrice", pointPrice.toSimpleString());
                    Money productPrice = BigdecimalUtil.toMoney((BigDecimal) map
                        .get("productPrice"));
                    map.put("productPrice", productPrice.toSimpleString());
                    Money luggage = BigdecimalUtil.toMoney((BigDecimal) map.get("luggage"));
                    map.put("luggage", luggage.toSimpleString());
                    Money reduceAmount = BigdecimalUtil.toMoney((BigDecimal) map
                        .get("reduceAmount"));
                    map.put("reduceAmount", reduceAmount.toSimpleString());
                    Money reduceAmountPlan = BigdecimalUtil.toMoney((BigDecimal) map
                        .get("reduceAmountPlan"));
                    map.put("reduceAmountPlan", reduceAmountPlan.toSimpleString());
                    Money reduceAmountTemp = BigdecimalUtil.toMoney((BigDecimal) map
                        .get("reduceAmountTemp"));
                    map.put("reduceAmountTemp", reduceAmountTemp.toSimpleString());
                    Money reduceAmountVoucher = BigdecimalUtil.toMoney((BigDecimal) map
                        .get("reduceAmountVoucher"));
                    map.put("reduceAmountVoucher", reduceAmountVoucher.toSimpleString());

                    String reducePro = MapUtils.getString(map, "reducePro");
                    String reducePlat = MapUtils.getString(map, "reducePlat");
                    String reduceTeam = MapUtils.getString(map, "reduceTeam");

                    Money reduceAmountDiscount = ReduceAmountUtil.getChildOrderReduceAmountS(
                        reducePro, reducePlat, reduceTeam);
                    map.put("reduceAmountDiscount", reduceAmountDiscount.toSimpleString());

                    Date gmtCreate = (Date) map.get("gmtCreate");
                    Date gmtModified = (Date) map.get("gmtModified");
                    map.put("gmtCreate", DateUtil.dateToString(gmtCreate, DateUtil.newFormat));
                    map.put("gmtModified", DateUtil.dateToString(gmtModified, DateUtil.newFormat));

                    changeStatusChildOrderId.add((String) map.get("childOrderId"));
                }

                // 表头
                String[] headers = { "订单时间", "收货人", "收货电话", "收货地址", "订单状态", "取货方式", "实付金额", "实付积分",
                        "商品名称", "商品属性", "购买数量", "运费", "erp编号", "父订单编号", "子订单编号", "用户编号", "商品编号",
                        "物流公司（编号）", "物流单号" };
                // 数据键名或者MODEL类字段名
                String[] Col = { "gmtModified", "reUserName", "cell", "receiveAddress",
                        "orderState", "transferType", "realOrderPrice", "realPointPrice",
                        "productName", "valuees", "orderCount", "luggage", "erpNo", "orderId",
                        "childOrderId", "userId", "productId", "carriCom", "carriNo" };

                //                ExportExcel<Map<String, Object>> ex = new ExportExcel<Map<String, Object>>();
                //                List<Map<String, Object>> excelList = selectByQuery;
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
                HSSFWorkbook book = ExportExcel.exportExcel(fileName, headers, Col, selectByQuery,
                    null);
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
     * 速发  标准模板   订单导出
     * 
     * @param request
     * @param response
     */
    @RequestMapping("/exportSfExcel.do")
    public void exportSfExcel(String[] chooseChildrenIdS, HttpServletRequest request,
                              HttpServletResponse response) {

        try {
            List<String> childOrderIdList = new ArrayList<String>();
            if (null != chooseChildrenIdS) {
                for (String childrenOrderId : chooseChildrenIdS) {
                    if (StringUtils.isNotBlank(childrenOrderId))
                        childOrderIdList.add(childrenOrderId);
                }
            }
            if (CollectionUtils.isEmpty(childOrderIdList)) {
                return;
            }

            List<Map<String, Object>> selectByQuery = orderDAO.selectByQuery(null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null, null,
                childOrderIdList, OrderStatusEnum.HAS_SEND.getCode(), null, null, null, null, null,
                null, null, null);
            if (selectByQuery.isEmpty()) {
                throw new RuntimeException("未找到相关数据");
            } else {

                String j_company = sysConfigCacheManager
                    .getConfigValue(SysConfigCacheKeyEnum.J_COMPANY);
                String j_contact = sysConfigCacheManager
                    .getConfigValue(SysConfigCacheKeyEnum.J_CONTACT);
                String j_tel = sysConfigCacheManager.getConfigValue(SysConfigCacheKeyEnum.J_TEL);
                String j_Address = sysConfigCacheManager
                    .getConfigValue(SysConfigCacheKeyEnum.J_PROVINCE)
                                   + sysConfigCacheManager
                                       .getConfigValue(SysConfigCacheKeyEnum.J_CITY)
                                   + sysConfigCacheManager
                                       .getConfigValue(SysConfigCacheKeyEnum.J_COUNTY)
                                   + sysConfigCacheManager
                                       .getConfigValue(SysConfigCacheKeyEnum.J_ADDRESS);

                String sf_custid = sysConfigCacheManager
                    .getConfigValue(SysConfigCacheKeyEnum.SF_CUSTID);

                int index = 1;
                for (Map<String, Object> map : selectByQuery) {
                    map.put("index", index);
                    index++;

                    String re_address = MapUtils.getString(map, "province")
                                        + MapUtils.getString(map, "city")
                                        + MapUtils.getString(map, "area")
                                        + MapUtils.getString(map, "reAddr");
                    map.put("re_address", re_address);

                    map.put("send_count", 1);

                    map.put("ywlx",
                        sysConfigCacheManager.getConfigValue(SysConfigCacheKeyEnum.SF_YWLX));
                    map.put("fkfs",
                        sysConfigCacheManager.getConfigValue(SysConfigCacheKeyEnum.SF_FKFS));

                    map.put("j_company", j_company);
                    map.put("j_contact", j_contact);
                    map.put("j_tel", j_tel);
                    map.put("j_Address", j_Address);
                    map.put("sf_custid", sf_custid);

                    Money productPrice = BigdecimalUtil.toMoney((BigDecimal) map
                        .get("productPrice"));
                    map.put("productPrice", productPrice.toSimpleString());

                    Double orderCount = MapUtils.getDouble(map, "orderCount");
                    map.put("orderCount", orderCount.intValue());

                    String valuees = MapUtils.getString(map, "valuees");
                    if (StringUtils.isNotBlank(valuees)) {
                        map.put("productName", MapUtils.getString(map, "productName") + "| "
                                               + valuees);
                    }
                }

                // 表头
                String[] headers = { "序号", "订单号", "运单号", "子单号", "签回单号", "寄方备注", "寄方公司", "寄方姓名",
                        "寄方联系方式", "寄方地址", "收方公司", "收方姓名", "收方联系方式", "收方地址", "商品名称", "商品编码", "商品数量",
                        "商品单价/元", "商品货号", "商品属性", "包裹件数", "业务类型", "付款方式", "月结卡号", "包裹重量/KG",
                        "代收金额", "代收卡号", "保价金额", "是否签回单", "派送日期", "派送时段", "是否自取", "是否保单配送",
                        "是否票据专送", "是否易碎宝", "易碎宝服务费/元", "是否口令签收", "标准化包装（元）", "个性化包装（元）", "其它费用（元）",
                        "超长超重服务费", "是否双人派送", "长(cm)", "宽(cm)", "高(cm)", "扩展字段1", "扩展字段2", "扩展字段3",
                        "扩展字段4", "扩展字段5", "温区", "签单返还范本", "保鲜服务", "WOW基础", "WOW尊享" };

                // 数据键名或者MODEL类字段名
                String[] Col = { "index", "childOrderId", "carriNo", "", "", "", "j_company",
                        "j_contact", "j_tel", "j_Address", "", "reUserName", "cell", "re_address",
                        "productName", "productId", "orderCount", "productPrice", "", "",
                        "send_count", "ywlx", "fkfs", "sf_custid", "", "", "", "", "", "", "", "",
                        "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                        "", "", "", "" };

                String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())
                    .toString().concat(".xls");
                HSSFWorkbook book = ExportExcel.exportExcelS(fileName, headers, Col, selectByQuery,
                    null);
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

    public void getOrderStatusList(Integer orderState, List<String> orderStatusList) {
        if (null == orderState) {
            orderStatusList = null;
            return;
        }
        switch (orderState) {
            case 0:
                orderStatusList = null;
                break;
            case 1:
                //待付款(含待审核)
                orderStatusList.add(OrderStatusEnum.WAIT_PAY.getCode());
                orderStatusList.add(OrderStatusEnum.WAIT_CHECK.getCode());
                break;
            case 2:
                //待发货(含待接单)
                orderStatusList.add(OrderStatusEnum.NOT_SEND.getCode());
                orderStatusList.add(OrderStatusEnum.NOT_SEND_ING.getCode());
                orderStatusList.add(OrderStatusEnum.WAIT_RECEIVE.getCode());
                break;
            case 3:
                //待收货
                orderStatusList.add(OrderStatusEnum.HAS_SEND.getCode());
                break;
            case 4:
                //订单完成(含未评价)
                orderStatusList.add(OrderStatusEnum.HAS_RECEIVE.getCode());
                orderStatusList.add(OrderStatusEnum.SUCCESS_ORDER.getCode());
                break;
            case 5:
                //订单失败(含审核不通过)
                orderStatusList.add(OrderStatusEnum.CANCLE.getCode());
                orderStatusList.add(OrderStatusEnum.FAIL_CHECK.getCode());
                break;
            case 6:
                //退款完成
                orderStatusList.add(OrderStatusEnum.SUCCESS_MONEY_RETURN.getCode());
                orderStatusList.add(OrderStatusEnum.SUCC_GOOD_RETURN.getCode());
                break;
            case 7:
                //退款失败(含审核不通过)
                orderStatusList.add(OrderStatusEnum.FAIL_MONEY_RETURN.getCode());
                orderStatusList.add(OrderStatusEnum.FAIL_GOOD_RETURN.getCode());
                orderStatusList.add(OrderStatusEnum.ERR_RETURN.getCode());
                break;
            case 8:
                //退款中(流程中)
                orderStatusList.add(OrderStatusEnum.APPLY_MONEY_RETURN.getCode());
                orderStatusList.add(OrderStatusEnum.APPLY_GOOD_RETURN.getCode());
                break;
            default:
                orderStatusList = null;
                break;
        }
    }

    public void getSubOrderStatusList(Integer subOrderStatus, List<String> subOrderStatusList,
                                      List<String> orderStatusList) {
        if (null == subOrderStatus) {
            subOrderStatusList = null;
            return;
        }
        switch (subOrderStatus) {
            case 0:
                subOrderStatusList = null;
                break;
            case 1:
                //待付款(待审核)
                subOrderStatusList.add(OrderSubStatusEnum.CHECK_BANSHI_MA.getCode());
                subOrderStatusList.add(OrderSubStatusEnum.CHECK_DAQU_MA.getCode());
                break;
            case 2:
                //待发货(待接单)
                if (null == orderStatusList) {
                    orderStatusList = new ArrayList<String>();
                    orderStatusList.add(OrderStatusEnum.WAIT_RECEIVE.getCode());
                }
                break;
            case 3:
                //订单完成(未评价)
                if (null == orderStatusList) {
                    orderStatusList = new ArrayList<String>();
                    orderStatusList.add(OrderStatusEnum.HAS_RECEIVE.getCode());
                }
                break;
            case 4:
                //订单失败(审核不通过)
                subOrderStatusList.add(OrderSubStatusEnum.CASHIER_FAIL.getCode());
                break;
            case 5:
                //退款完成(仅退款)
                subOrderStatusList.add(OrderSubStatusEnum.MONEY_RETURN_SUCC.getCode());
                break;
            case 6:
                //退款完成(退款退货)
                subOrderStatusList.add(OrderSubStatusEnum.GOOD_SECOND_RETURN_SUCC.getCode());
                break;
            case 7:
                //退款失败(审核不通过)
                subOrderStatusList.add(OrderSubStatusEnum.MONEY_RETURN_FAIL.getCode());
                subOrderStatusList.add(OrderSubStatusEnum.GOOD_FIRST_RETURN_FAIL.getCode());
                subOrderStatusList.add(OrderSubStatusEnum.GOOD__SECOND_RETURN_FAIL.getCode());
                break;
            case 8:
                //退款中(用户待发货)
                subOrderStatusList.add(OrderSubStatusEnum.GOOD_FIRST_RETURN_SUCC.getCode());
                break;
            case 9:
                //退款中(门店待收货)
                subOrderStatusList.add(OrderSubStatusEnum.GOOD_SECOND_RETURN_INIT.getCode());
                break;
            case 10:
                //收银审核通过
                subOrderStatusList.add(OrderSubStatusEnum.CASHIER_SUCC.getCode());
                break;
            case 11:
                //收银审核拒绝
                subOrderStatusList.add(OrderSubStatusEnum.CASHIER_FAIL.getCode());
                break;
            default:
                subOrderStatusList = null;
                break;
        }
    }

}
