package com.onway.web.controller.mng;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.fyapp.common.dal.dataobject.MsgDO;
import com.onway.fyapp.common.dal.dataobject.UserDO;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.result.JsonResult;
import com.onway.utils.DateUtil;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;

/**
 * 系统消息
 * @author Administrator
 *
 */
@Controller
public class MsgController extends AbstractController {

    /**
     * 查询信息列表
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/selectmsg.do")
    @ResponseBody
    public JSONObject selectuser(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String msgType = request.getParameter("msgType");
        String msgTitle = request.getParameter("title");
        String isRead = request.getParameter("isRead");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String offset = request.getParameter("offset");
        String limit = request.getParameter("limit");

        String userCell = request.getParameter("userCell");
        String userNickName = request.getParameter("userNickName");
        String userRealName = request.getParameter("userRealName");

        JSONObject data = new JSONObject();
        try {
            Date startTime = DateUtil.stringToDate(startDate, DateUtil.webFormat);
            Date endTime = DateUtil.stringToDate(endDate, DateUtil.webFormat);
            if (null != endTime)
                endTime = DateUtil.addDays(endTime, 1);

            List<Map<String, Object>> queryList = msgDAO.selectMsg(userId, msgType, msgTitle,
                isRead, startTime, endTime, userCell, userNickName, userRealName,
                Integer.parseInt(offset), Integer.parseInt(limit));

            int count = msgDAO.selectMsgCount(userId, msgType, msgTitle, isRead, startTime,
                endTime, userCell, userNickName, userRealName);

            data.put("rows", queryList);
            data.put("total", count);
        } catch (Exception e) {
            logger.error(MessageFormat.format(" 查询信息列表异常", new Object[] {}));

        }
        return data;
    }

    //添加
    @RequestMapping("/addmsg.do")
    @ResponseBody
    public Object addmsg(final HttpServletRequest request, final String auserCell,
                         final String amsgType, final String amsgTitle, final String amsgContent,
                         final String aisread) {

        final String user_id = request.getSession().getAttribute("user_id").toString();

        final JsonResult data = new JsonResult(true);
        controllerTemplate.execute(data, new ControllerCallBack() {

            @Override
            public void executeService() {
                UserDO userDO = userDAO.selectByUserCell(auserCell);
                if (null == userDO) {
                    data.setSuccess(false);
                    data.setMessage("请输入正确手机号");
                    return;
                }

                MsgDO m = new MsgDO();
                m.setGmtCreate(new Date());
                m.setUserId(userDO.getUserId());
                m.setMsgType(amsgType);
                m.setMsgTitle(amsgTitle);
                m.setMsgContent(amsgContent);
                m.setCreater(user_id);
                m.setIsRead("false");
                int result = msgDAO.insertMsg(m);
                if (result > 0) {
                    data.setSuccess(true);
                    data.setMessage("操作成功");
                } else {
                    data.setSuccess(false);
                    data.setMessage("操作失败");
                }
            }

            @Override
            public void check() {
                AssertUtil.notBlank(auserCell, "请填写手机号");
            }
        });
        return data;
    }

    /**
     * 修改
     */
    @RequestMapping("/updatemsg.do")
    @ResponseBody
    public Object updatemsg(final HttpServletRequest request) {
        final String eid = request.getParameter("eid");
        final String euserId = request.getParameter("euserId");
        final String emsgType = request.getParameter("emsgType");
        final String emsgTitle = request.getParameter("emsgTitle");
        final String emsgcontent = request.getParameter("emsgContent");
        final String user_id = request.getSession().getAttribute("user_id").toString();
        final JsonResult data = new JsonResult(true);
        controllerTemplate.execute(data, new ControllerCallBack() {
            @Override
            public void executeService() {
                int result = msgDAO.updateMsg(euserId, emsgType, emsgTitle, emsgcontent, user_id,
                    Integer.parseInt(eid));
                if (result > 0) {
                    data.setSuccess(true);
                    data.setMessage("操作成功");
                } else {
                    data.setSuccess(false);
                    data.setMessage("操作失败");
                }
            }

            @Override
            public void check() {
            }
        });
        return data;
    }

    /**
     * 删除
     */
    @RequestMapping("/deletemsg.do")
    @ResponseBody
    public Object deletemsg(HttpServletRequest request, final int id) {
        final JsonResult json = new JsonResult(true);
        final String user_id = request.getSession().getAttribute("user_id").toString();
        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                int result = msgDAO.deleteMsg(user_id, id);
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
