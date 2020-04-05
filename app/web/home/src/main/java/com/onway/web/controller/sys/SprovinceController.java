package com.onway.web.controller.sys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

//import org.apache.commons.httpclient.HttpException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.common.lang.CollectionUtils;
import com.onway.common.lang.StringUtils;
import com.onway.fyapp.common.dal.dataobject.SProvinceDO;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.result.JsonResult;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;

/**
 * 城市管理
 */
@Controller
public class SprovinceController extends AbstractController {

    /**
     * 查看系统配置信息
     * @param offset
     * @param limit
     * @param name
     * @return
     */
    @RequestMapping("/selectAllPro.do")
    @ResponseBody
    public JSONObject selectconfig(Integer offset, Integer limit, String cityName, String isUse,
                                   String depth) {
        JSONObject jo = new JSONObject();
        List<SProvinceDO> queryAll = sProvinceDAO.queryAll(cityName, isUse, depth, offset, limit);
        jo.put("rows", queryAll);
        jo.put("total", sProvinceDAO.queryAllCount(cityName, isUse, depth));
        return jo;
    }

    /**
     * 设置是否可用
     * @param sProvinceDO
     * @return
     */
    @RequestMapping("/updateCanUse.do")
    @ResponseBody
    public Object updateCanUse(final HttpServletRequest request) {
        final JsonResult jr = new JsonResult(true);
        final String id = request.getParameter("id");
        final String isUse = request.getParameter("type");
        controllerTemplate.execute(jr, new ControllerCallBack() {

            @Override
            public void executeService() {
                SProvinceDO sProvinceDO = new SProvinceDO();
                sProvinceDO.setId(Integer.valueOf(id));
                sProvinceDO.setIsUse(Integer.valueOf(isUse));
                sProvinceDAO.updateCanUse(sProvinceDO);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(id);
                AssertUtil.notBlank(isUse);
            }
        });
        return jr;
    }

    /**
     * 设置地区邮费
     * @param sProvinceDO
     * @return
     */
    @RequestMapping("/updatePostage.do")
    @ResponseBody
    public Object updatePostage(final SProvinceDO sProvinceDO) {
        final JsonResult jr = new JsonResult(true);
        controllerTemplate.execute(jr, new ControllerCallBack() {

            @Override
            public void executeService() {
                sProvinceDAO.updatePostage(sProvinceDO);
            }

            @Override
            public void check() {

            }
        });
        return jr;
    }

    @RequestMapping("/getAllCity.do")
    @ResponseBody
    public JSONObject getAllCity(Integer offset, Integer limit, String chooseProvinceId,
                                 String isUse, String depth) {
        JSONObject jo = new JSONObject();

        if (StringUtils.isBlank(chooseProvinceId)) {
            jo.put("rows", new ArrayList<SProvinceDO>());
            return jo;
        }
        List<SProvinceDO> queryAll = sProvinceDAO.queryAllCityByParentId(chooseProvinceId, isUse,
            offset, limit);
        jo.put("rows", queryAll);
        return jo;
    }

    @RequestMapping("/getAllCityComplex.do")
    @ResponseBody
    public JSONObject getAllCityComplex(Integer offset, Integer limit, String isUse, String depth,
                                        HttpServletRequest request) {
        JSONObject jo = new JSONObject();

        String[] chooseProvinceId = request.getParameterValues("chooseProvinceId");

        List<String> parentIds = Arrays.asList(chooseProvinceId);
        if (CollectionUtils.isEmpty(parentIds)
            || (StringUtils.isBlank(parentIds.get(0)) && parentIds.size() <= 1)){
            jo.put("rows", new ArrayList<SProvinceDO>());
            return jo;
        }
        
        List<SProvinceDO> queryAll = sProvinceDAO.queryAllCityByParentIdS(parentIds, isUse, "2",
            null, null);

        jo.put("rows", queryAll);
        return jo;
    }
}
