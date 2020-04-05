package com.onway.web.controller.mng;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.common.lang.Money;
import com.onway.fyapp.common.dal.dataobject.OrderRebateDO;
import com.onway.fyapp.common.dal.dataobject.OrderVoucherDO;
import com.onway.model.enums.OrderOweStatusEnum;
import com.onway.model.enums.StatusEnum;
import com.onway.utils.BigdecimalUtil;
import com.onway.utils.StringToListUtil;
import com.onway.web.controller.AbstractController;

/**
 * 订单记录
 * 
 * @author yuhang.ni
 * @version $Id: OrderRecordController.java, v 0.1 2019年1月24日 上午11:50:17 Administrator Exp $
 */
@Controller
public class OrderRecordController extends AbstractController {

    /**
     * 借条记录
     * 
     * @param orderId
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/getOrderOweRecord.do")
    @ResponseBody
    public JSONObject getOrderOweRecord(String orderId, Integer offset, Integer limit) {
        JSONObject jo = new JSONObject();
        List<Map<String, Object>> getOrderOweRecord = orderOweDAO.getOrderOweRecord(orderId,
            offset, limit);
        for (Map<String, Object> map : getOrderOweRecord) {
            String status = MapUtils.getString(map, "status");
            OrderOweStatusEnum orderOweStatusEnum = OrderOweStatusEnum.getByCode(status);
            if (null != orderOweStatusEnum)
                map.put("statusStr", orderOweStatusEnum.message());
        }
        jo.put("rows", getOrderOweRecord);
        jo.put("total", orderOweDAO.getOrderOweRecordCount(orderId));
        return jo;
    }

    /**
     * 返利记录
     * 
     * @param childOrderId
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/getOrderRebateRecord.do")
    @ResponseBody
    public JSONObject getOrderRebateRecord(String childOrderId, Integer offset, Integer limit) {
        JSONObject jo = new JSONObject();
        List<OrderRebateDO> orderRebateRecord = orderRebateDAO.getOrderRebateRecord(childOrderId,
            offset, limit);
        jo.put("rows", orderRebateRecord);
        jo.put("total", orderRebateDAO.getOrderRebateRecordCount(childOrderId));
        return jo;
    }

    /**
     * 退款退货记录
     * 
     * @param childOrderId
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/getOrderReturnRecord.do")
    @ResponseBody
    public JSONObject getOrderReturnRecord(String childOrderId, Integer offset, Integer limit) {
        JSONObject jo = new JSONObject();

        List<Map<String, Object>> orderReturnRecord = orderReturnDAO.getOrderReturnRecord(
            childOrderId, offset, limit);
        for (Map<String, Object> map : orderReturnRecord) {
            String status = MapUtils.getString(map, "status");
            StatusEnum statusEnum = StatusEnum.getByCode(status);
            if (null != statusEnum)
                map.put("statusStr", statusEnum.message());

            Money shouldReturnMoney = BigdecimalUtil.toMoney((BigDecimal) map
                .get("shouldReturnMoney"));
            map.put("shouldReturnMoney", shouldReturnMoney);

            Money shouldReturnPoint = BigdecimalUtil.toMoney((BigDecimal) map
                .get("shouldReturnPoint"));
            map.put("shouldReturnMoney", shouldReturnPoint);

            Money shouldReturnVoucher = BigdecimalUtil.toMoney((BigDecimal) map
                .get("shouldReturnVoucher"));
            map.put("shouldReturnVoucher", shouldReturnVoucher);

            Money shouldReturnDevote = BigdecimalUtil.toMoney((BigDecimal) map
                .get("shouldReturnDevote"));
            map.put("shouldReturnDevote", shouldReturnDevote);

            Money shouldReturnRebateUser = BigdecimalUtil.toMoney((BigDecimal) map
                .get("shouldReturnRebateUser"));
            map.put("shouldReturnRebateUser", shouldReturnRebateUser);

            Money shouldReturnRebateReco = BigdecimalUtil.toMoney((BigDecimal) map
                .get("shouldReturnRebateReco"));
            map.put("shouldReturnRebateReco", shouldReturnRebateReco);

            Money shouldReturnRebateRecoUser = BigdecimalUtil.toMoney((BigDecimal) map
                .get("shouldReturnRebateRecoUser"));
            map.put("shouldReturnRebateRecoUser", shouldReturnRebateRecoUser);

            String returnImg = MapUtils.getString(map, "returnImg");
            List<String> returnImgs = StringToListUtil.toListArr(returnImg);
            map.put("returnImgs", returnImgs);

            String returnVoucherImg = MapUtils.getString(map, "returnVoucherImg");
            List<String> returnVoucherImgs = StringToListUtil.toListArr(returnVoucherImg);
            map.put("returnVoucherImgs", returnVoucherImgs);

            String quartuImg = MapUtils.getString(map, "quartuImg");
            List<String> quartuImgs = StringToListUtil.toListArr(quartuImg);
            map.put("quartuImgs", quartuImgs);
        }
        jo.put("rows", orderReturnRecord);
        jo.put("total", orderReturnDAO.getOrderReturnRecordCount(childOrderId));
        return jo;
    }

    /**
     * 凭证记录
     * 
     * @param childOrderId
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/getOrderVoucherRecord.do")
    @ResponseBody
    public JSONObject getOrderVoucherRecord(String childOrderId, Integer offset, Integer limit) {
        JSONObject jo = new JSONObject();
        List<OrderVoucherDO> orderVoucherRecord = orderVoucherDAO.getOrderVoucherRecord(
            childOrderId, offset, limit);
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        for (OrderVoucherDO orderVoucherDO : orderVoucherRecord) {
            String voucherImg = orderVoucherDO.getVoucherImg();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("voucher", orderVoucherDO.getVoucher());
            map.put("voucherImg", StringToListUtil.toList(voucherImg));
            map.put("gmtCreate", orderVoucherDO.getGmtCreate());
            list.add(map);
        }
        jo.put("rows", list);
        jo.put("total", orderVoucherDAO.getOrderVoucherRecordCount(childOrderId));
        return jo;
    }

}
