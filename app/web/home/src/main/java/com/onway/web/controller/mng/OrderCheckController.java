package com.onway.web.controller.mng;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.model.enums.CheckStatusEnum;
import com.onway.model.enums.OrderCheckTypeEnum;
import com.onway.model.enums.UserRoleEnum;
import com.onway.web.controller.AbstractController;

/**
 * 订单审核记录
 * 
 * @author yuhang.ni
 * @version $Id: OrderCheckController.java, v 0.1 2018年12月28日 下午7:01:14 Administrator Exp $
 */
@Controller
public class OrderCheckController extends AbstractController {

    /**
     * 分页查询
     * 
     * @return
     */
    @RequestMapping("/queryOrderCheckList.do")
    @ResponseBody
    public JSONObject queryOrderCheckList(String childOrderId, Integer offset, Integer limit) {
        JSONObject jo = new JSONObject();

        List<Map<String, Object>> selectByQuery = orderCheckRecordDAO.queryOrderCheckList(
            childOrderId, offset, limit);
        for (Map<String, Object> map : selectByQuery) {
            String CHECK_TYPE = MapUtils.getString(map, "CHECK_TYPE");
            OrderCheckTypeEnum orderCheckTypeEnum = OrderCheckTypeEnum.getByCode(CHECK_TYPE);
            if(null != orderCheckTypeEnum)
                map.put("CHECK_TYPE_STR", orderCheckTypeEnum.message());

            String CHECK_ROLE = MapUtils.getString(map, "CHECK_ROLE");
            UserRoleEnum userRoleEnum = UserRoleEnum.getByCode(CHECK_ROLE);
            if(null != userRoleEnum)
                map.put("CHECK_ROLE_STR", userRoleEnum.message());

            String CHECK_STATUS = MapUtils.getString(map, "CHECK_STATUS");
            CheckStatusEnum checkStatusEnum = CheckStatusEnum.getByCode(CHECK_STATUS);
            if(null != checkStatusEnum)
                map.put("CHECK_STATUS_STR", checkStatusEnum.message());
        }
        jo.put("rows", selectByQuery);
        jo.put("total", orderCheckRecordDAO.queryOrderCheckListCount(childOrderId));
        return jo;
    }

}
