package com.onway.web.controller.mng;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.result.JsonResult;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;

/**
 * 意见反馈
 * @author Administrator
 *
 */
@Controller
public class OptionController extends AbstractController {

    /**
     * 查询信息列表
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/selectoption.do")
    @ResponseBody
    public JSONObject selectuser(HttpServletRequest request) {
        String cell = request.getParameter("cell");
        String nickName = request.getParameter("nickName");
        String age = request.getParameter("age");
        String sex = request.getParameter("sex");
        String offset = request.getParameter("offset");
        String limit = request.getParameter("limit");
        JSONObject data = new JSONObject();
        try {
            List<Map<String, Object>> queryList = optionDAO.selectoption(cell, nickName, age, sex,
                Integer.parseInt(offset), Integer.parseInt(limit));
            int count = optionDAO.selectoption(cell, nickName, age, sex, null, null).size();
            data.put("rows", queryList);
            data.put("total", count);
        } catch (Exception e) {
            logger.error(MessageFormat.format("查询用户异常", new Object[] {}));
            // throw new BaseRuntimeException(ErrorCode.QUERY_EEOR,
            // ErrorCode.QUERY_EEOR.getDesc());
        }
        return data;
    }

    /**
     * 删除
     */
    @RequestMapping("/deleteoption.do")
    @ResponseBody
    public Object deletemsg(HttpServletRequest request, final int id) {
        final JsonResult json = new JsonResult(true);
        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                int result = optionDAO.deleteoption(id);
                if (result > 0) {
                    json.setSuccess(true);
                    json.setMessage("操作成功");
                } else {
                    json.setSuccess(false);
                    json.setMessage("操作失败");
                }
            }

            @Override
            public void check() {
            }

        });
        return json;
    }
}
