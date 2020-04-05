/**
 * onway.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.onway.web.controller;

import java.util.Map;
import com.onway.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.onway.common.lang.StringUtils;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.result.JsonResult;
import com.onway.utils.Md5Encrypt;
import com.onway.web.template.ControllerCallBack;

@Controller
public class IndexController extends AbstractController {

    @RequestMapping("/test")
    @ResponseBody
    public Object test(HttpServletRequest request) {
        return new JsonResult(true, "code", request.getParameter("name"));
    }

    /**
     * 跳转到登陆页面
     * 
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("/index")
    public String index(HttpServletRequest request, ModelMap modelMap) {
        return "html/login";
    }

    @RequestMapping(value = "/updateUsernameOrPassword.action")
    public String updateUsernameOrPassword(Integer type, HttpServletRequest request) {

        return "/html/updateUsernameOrPassword.html";
    }

    /**
     * 更改密码
     * 
     * @param password
     * @param newpassword
     * @param newpasswords
     * @param request
     * @return
     */
    @RequestMapping("/updatemypassword.do")
    @ResponseBody
    public Object updatemypassword(final String password, final String newpassword,
                                   final String newpasswords, final HttpServletRequest request) {
        final JsonResult result = new JsonResult(true);
        final String user_id = request.getSession().getAttribute("user_id").toString();
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {
                // TODO Auto-generated method stub

                result.setMessage(sysRoleUserDAO.updatepassword(Md5Encrypt.toMD5High(newpassword), user_id, user_id) > 0 ? "操作成功" : "操作失败");
            }

            @Override
            public void check() {
                // TODO Auto-generated method stub
                AssertUtil.notBlank(password, "密码不能为空");
                AssertUtil.notBlank(newpassword, "新密码不能为空");
                AssertUtil.notBlank(newpasswords, "新密码不能为空");
                AssertUtil.state(newpassword.equals(newpasswords), "两次密码不一致");
                AssertUtil.state(
                    Md5Encrypt.toMD5High(password).equals(
                        sysRoleUserDAO.selectpassword(request.getSession().getAttribute("user_id")
                                                      + "")), "原密码输入有误");
            }
        });
        return result;
    }

    /**
     * 
     * @param session
     * @param request
     * @return
     */
    @RequestMapping("/home.html")
    public String indexhtml(HttpSession session, HttpServletRequest request) {
        request.setAttribute("USER_NAME", session.getAttribute("username"));
        request.setAttribute("userid", session.getAttribute("user_id"));
        request.setAttribute("REAL_NAME", session.getAttribute("realName"));
        request.setAttribute("firstmenu", JSONArray.parseArray(session.getAttribute("role") + ""));
        return "html/index";
    }

    @RequestMapping("/welcome.html")
    public String welcome(ModelMap map) {

        return "html/welcome";
    }

    /**
     * 
     * @param re
     * @return
     */
    @RequestMapping("/removesession.do")
    @ResponseBody
    public String removesession(HttpServletRequest request) {
        request.getSession().removeAttribute("username");
        request.getSession().removeAttribute("user_id");
        return "login";
    }

    @RequestMapping("/login.do")
    @ResponseBody
    public JSONObject login(HttpServletRequest request) {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        JSONObject result = new JSONObject();
        result.put("flag", false);

        Map<String, Object> user = sysRoleUserDAO.login(userName, Md5Encrypt.toMD5High(password));
        if (null == user) {
            result.put("data", "用户名或密码有误");
            return result;
        } else {
            request.getSession().setAttribute("user_id", user.get("user_id"));
            request.getSession().setAttribute("username", user.get("username"));
            request.getSession().setAttribute("realName", user.get("realName"));
            request.getSession().setAttribute("role", user.get("JURISDICTION"));
            result.put("data", "登陆成功");
            result.put("flag", true);
        }
        return result;
    }

    /**
     * 跳转界面
     * 
     * @param url
     * @return
     */
    @RequestMapping("/tourl.do")
    public String tourl(String url, String userId, HttpSession session, HttpServletRequest re) {
        String menu = (String) session.getAttribute("role");

        if ("html/updatepassword".equals(url)) {
            return url;
        }

        if (StringUtils.isNotBlank(menu)) {
            if (!menu.contains(url)) {
                re.getSession().removeAttribute("username");
                re.getSession().removeAttribute("user_id");
                re.getSession().removeAttribute("realName");
                return "html/login";
            }
        }
        if (StringUtils.isNotBlank(userId))
            return url + "?userId=" + userId;
        return url;
    }

}
