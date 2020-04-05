package com.onway.web.controller.select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.common.lang.StringUtils;
import com.onway.model.EnumItem;
import com.onway.model.enums.AccountLogFlgEnum;
import com.onway.model.enums.AccountLogTypeEnum;
import com.onway.model.enums.AgeGroupEnum;
import com.onway.model.enums.SysConfigCacheKeyEnum;
import com.onway.model.enums.UserCheckRoleEnum;
import com.onway.model.enums.WorkCheckTypeEnum;
import com.onway.result.JsonQueryResult;
import com.onway.web.controller.AbstractController;

/**
 * 枚举 集合控制类
 */
@Controller
public class SelectController extends AbstractController {

    /**
     * 账户流水 类型枚举
     * @param request
     * @return
     */
    @RequestMapping("/accountLogType.do")
    @ResponseBody
    public Object accountLogType(HttpServletRequest request) {
        JsonQueryResult<List<EnumItem>> result = new JsonQueryResult<List<EnumItem>>(true);
        List<EnumItem> accountLogType = AccountLogTypeEnum.list;
        result.setObj(accountLogType);
        return result;
    }

    /**
     * 账户流水标识枚举
     * @param request
     * @return
     */
    @RequestMapping("/accountLogFlg.do")
    @ResponseBody
    public Object accountLogFlg(HttpServletRequest request) {
        JsonQueryResult<List<EnumItem>> result = new JsonQueryResult<List<EnumItem>>(true);
        List<EnumItem> accountLogFlg = AccountLogFlgEnum.list;
        result.setObj(accountLogFlg);
        return result;
    }

    /**
     * 系统经营类别
     * 
     * @param request
     * @return
     */
    @RequestMapping("/businessCategory.do")
    @ResponseBody
    public Object businessCategory(HttpServletRequest request) {
        JsonQueryResult<List<String>> result = new JsonQueryResult<List<String>>(true);
        String businessCategoryS = sysConfigCacheManager
            .getConfigValue(SysConfigCacheKeyEnum.BUSINESS_CATEGORY);
        List<String> asList = new ArrayList<String>();
        if (StringUtils.isNotBlank(businessCategoryS)) {
            //中文分号
            String[] strings = businessCategoryS.split("；");
            asList = Arrays.asList(strings);
        }
        result.setObj(asList);
        return result;
    }
    
    @RequestMapping("/workCheckTypeEnum.do")
    @ResponseBody
    public Object workCheckTypeEnum(HttpServletRequest request) {
        JsonQueryResult<List<EnumItem>> result = new JsonQueryResult<List<EnumItem>>(true);
        List<EnumItem> workCheckTypeEnum = WorkCheckTypeEnum.list;
        result.setObj(workCheckTypeEnum);
        return result;
    }
    
    /**
     * 团队审核角色
     * 
     * @param request
     * @return
     */
    @RequestMapping("/userCheckRoleEnum.do")
    @ResponseBody
    public Object userCheckRoleEnum(HttpServletRequest request) {
        JsonQueryResult<List<EnumItem>> result = new JsonQueryResult<List<EnumItem>>(true);
        List<EnumItem> userCheckRole = UserCheckRoleEnum.list;
        result.setObj(userCheckRole);
        return result;
    }
    
    @RequestMapping("/ageGroupEnum.do")
    @ResponseBody
    public Object ageGroupEnum(HttpServletRequest request) {
        JsonQueryResult<List<EnumItem>> result = new JsonQueryResult<List<EnumItem>>(true);
        List<EnumItem> accountLogType = AgeGroupEnum.list;
        result.setObj(accountLogType);
        return result;
    }

}
