package com.onway.web.controller.sys;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.common.lang.Money;
import com.onway.fyapp.common.dal.dataobject.SysLevelDO;
import com.onway.model.enums.ErrorCode;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.result.JsonResult;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;

/**
 * 等级管理
 * 
 * @author Administrator
 *
 */
@Controller
public class LevelController extends AbstractController {

    /**
     * 查看等级信息
     * 
     * @param offset
     * @param limit
     * @param name
     * @return
     */
    @RequestMapping("selectlevel.do")
    @ResponseBody
    public JSONObject selectrole(HttpServletRequest request, Integer offset, Integer limit) {
        String queryLevel = request.getParameter("queryLevel");
        String queryType = request.getParameter("queryType");
        JSONObject result = new JSONObject();
        result.put("rows", sysLevelDAO.searchAllLevel(queryLevel, queryType, offset, limit));
        result.put("total", sysLevelDAO.searchAllLevelCount(queryLevel, queryType, offset, limit));
        return result;
    }

    /**
     * 添加等级信息
     * 
     * @return
     */
    @RequestMapping("/addlevel.do")
    @ResponseBody
    public Object addlevel(final SysLevelDO sysLevelDO, final HttpServletRequest re) {
        final String type = re.getParameter("type");
        final String levelType = re.getParameter("levelType");
        final JsonResult result = new JsonResult(true);
        controllerTemplate.execute(result, new ControllerCallBack() {
            @Override
            public void executeService() {
                SysLevelDO lo = sysLevelDAO.just(type, levelType);
                if (lo == null) {
                    sysLevelDO.setDicountRate(sysLevelDO.getDicountRate()/100);
                    int newLevelInfo = sysLevelDAO.newLevelInfo(sysLevelDO);
                    result.setSuccess(newLevelInfo > 0 ? true : false);
                    result.setMessage(newLevelInfo > 0 ? "添加成功" : "添加失败");
                } else {
                    result.setSuccess(false);
                    result.setMessage("已有该类型的等级！！！");

                }
            }

            @Override
            public void check() {

            }
        });
        return result;
    }

    @RequestMapping("/checkArg.do")
    @ResponseBody
    public Object checkArg(final HttpServletRequest request) {
        final String type = request.getParameter("type");
        final String levelType = request.getParameter("levelType");
        final String minAmount = request.getParameter("type");
        final String maxAmount = request.getParameter("levelType");
        final JsonResult result = new JsonResult(true);
        controllerTemplate.execute(result, new ControllerCallBack() {
            @Override
            public void executeService() {

            }

            @Override
            public void check() {
                AssertUtil.notBlank(type, "类型不能为空");
                AssertUtil.notBlank(levelType, "等级不能为空");
                AssertUtil.notBlank(minAmount, "最小值不能为空");
                AssertUtil.notBlank(maxAmount, "最大值不能为空");
            }
        });
        return result;
    }

    /**
     * 修改等级信息
     * 
     * @return
     */
    @RequestMapping("/updatelevel.do")
    @ResponseBody
    public Object updaterole(final HttpServletRequest re) {
        final String type = re.getParameter("etype");
        final String levelType = re.getParameter("elevelType");
        final String id = re.getParameter("editId");
        final String minAmount = re.getParameter("eminAmount");
        final String maxAmount = re.getParameter("emaxAmount");
        final String edicountRate = re.getParameter("edicountRate");
        final JsonResult result = new JsonResult(true);
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {
                SysLevelDO sysLevelDO = new SysLevelDO();
                sysLevelDO.setId(Integer.parseInt(id));
                sysLevelDO.setType(type);
                sysLevelDO.setLevelType(levelType);
                sysLevelDO.setDicountRate(Double.parseDouble(edicountRate) / 100);
                sysLevelDO.setMinAmount(new Money(minAmount));
                sysLevelDO.setMaxAmount(new Money(maxAmount));
                int updateLevelInfo = sysLevelDAO.updateLevelInfo(sysLevelDO);
                result.setSuccess(updateLevelInfo > 0 ? true : false);
                result.setMessage(updateLevelInfo > 0 ? "修改成功" : "修改失败");
            }

            @Override
            public void check() {
                AssertUtil.notBlank(id, "id不能为空");
                AssertUtil.notBlank(type, "商家类型不能为空");
                AssertUtil.notBlank(levelType, "等级不能为空");
            }
        });
        return result;
    }

    /**
     * 删除角色
     * 
     * @param roleId
     * @param request
     * @return
     */
    @RequestMapping("/deleteLevel.do")
    @ResponseBody
    public Object deleteRole(final int id, final HttpServletRequest request) {

        final JsonResult jsonResult = new JsonResult(true);
        controllerTemplate.execute(jsonResult, new ControllerCallBack() {

            @Override
            public void executeService() {
                int deleteLevelInfo = sysLevelDAO.deleteLevelInfo(id);
                jsonResult.setSuccess(deleteLevelInfo > 0 ? true : false);
                jsonResult.setMessage(deleteLevelInfo > 0 ? ErrorCode.SUCCESS.message()
                    : ErrorCode.SYSTEM_ERROR.message());
            }

            @Override
            public void check() {

            }
        });
        return jsonResult;
    }
}
