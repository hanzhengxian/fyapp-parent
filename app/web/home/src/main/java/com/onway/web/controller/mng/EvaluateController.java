package com.onway.web.controller.mng;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.common.lang.DateUtils;
import com.onway.common.lang.StringUtils;
import com.onway.fyapp.common.dal.dataobject.EvaluateDO;
import com.onway.result.JsonResult;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;

/**
 * 查询评价相关类
 * @author Administrator
 *
 */
@Controller
public class EvaluateController extends AbstractController {

    /**
     * 查询信息列表
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/selectek.do")
    @ResponseBody
    public JSONObject selectek(HttpServletRequest request, Integer offset, Integer limit) {
        String linkid = request.getParameter("linkid");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        JSONObject data = new JSONObject();
        try {
            Date startTime = null;
            Date endTime = null;

            if (StringUtils.isNotBlank(startDate)) {
                startTime = DateUtils.parseDate(startDate, DateUtils.webFormat);
            }

            if (StringUtils.isNotBlank(endDate)) {
                endTime = DateUtils.parseDate(endDate, DateUtils.webFormat);
                endTime = DateUtils.addDays(endTime, 1);
            }
            List<EvaluateDO> queryList = evaluateDAO.selectevaluate(linkid, startTime, endTime,
                offset, limit);
            int count = evaluateDAO.selectevaluate(linkid, startTime, endTime, null, null).size();
            data.put("rows", queryList);
            data.put("total", count);
        } catch (Exception e) {
            logger.error(MessageFormat.format("查询用户异常", new Object[] {}));
            // throw new BaseRuntimeException(ErrorCode.QUERY_EEOR,
            // ErrorCode.QUERY_EEOR.getDesc());
        }
        return data;
    }

    @RequestMapping("/selectep.do")
    @ResponseBody
    public JSONObject selectep(HttpServletRequest request, Integer offset, Integer limit) {
        String linkid = request.getParameter("linkid");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        JSONObject data = new JSONObject();
        try {
            Date startTime = null;
            Date endTime = null;

            if (StringUtils.hasLength(startDate)) {
                startTime = DateUtils.parseDate(startDate, DateUtils.webFormat);
            }

            if (StringUtils.hasLength(endDate)) {
                endTime = DateUtils.parseDate(endDate, DateUtils.webFormat);
                endTime = DateUtils.addDays(endTime, 1);
            }
            List<EvaluateDO> queryList = evaluateDAO.selectep(linkid, startTime, endTime, offset,
                limit);
            int count = evaluateDAO.selectep(linkid, startTime, endTime, null, null).size();

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
    @RequestMapping("/deletepl.do")
    @ResponseBody
    public Object deletepl(HttpServletRequest request, final int id) {
        final JsonResult json = new JsonResult(true);
        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                int result = evaluateDAO.deletepl(id);
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

    @RequestMapping("/upodatepl.do")
    @ResponseBody
    public Object upodatepl(HttpServletRequest request, final int id, final String isBest) {
        final JsonResult json = new JsonResult(true);
        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                int upodatepl = evaluateDAO.upodatepl(isBest, id);
                if (upodatepl > 0) {
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