package com.onway.web.controller.mng;

import java.text.MessageFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.fyapp.common.dal.dataobject.AddressDO;
import com.onway.model.enums.DelFlgEnum;
import com.onway.web.controller.AbstractController;

/**
 * 用户地址
 */
@Controller
public class AddressController extends AbstractController {

    /**
     * 用户收货地址
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/selectUserAddress.do")
    @ResponseBody
    public JSONObject selectUserAddress(HttpServletRequest request, Integer offset, Integer limit) {

        String userId = request.getParameter("userId");

        JSONObject data = new JSONObject();
        try {
            List<AddressDO> queryList = addressDAO.queryAddressList(userId,
                DelFlgEnum.NOT_DEL.getCode(), offset, limit);
            data.put("rows", queryList);
            data.put("total",
                addressDAO.queryAddressListCount(userId, DelFlgEnum.NOT_DEL.getCode()));
        } catch (Exception e) {
            logger.error(MessageFormat.format("查询用户收货地址异常", new Object[] {}));

        }
        return data;
    }

}
