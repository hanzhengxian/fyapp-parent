package com.onway.web.controller.mng;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.platform.common.base.BaseResult;
import com.onway.platform.common.base.QueryResult;
import com.onway.result.JsonQueryResult;
import com.onway.result.JsonResult;
import com.onway.shunfeng.service.ShunfengService;
import com.onway.web.controller.AbstractController;

/**
 * 运单打印
 * 
 * @author yuhang.ni
 * @version $Id: SfPrintController.java, v 0.1 2019年3月14日 上午10:55:29 Administrator Exp $
 */
@Controller
public class SfPrintController extends AbstractController {

    @Resource
    private ShunfengService shunfengService;

    /**
     * 顺风下单
     * 
     * @param request
     * @return
     */
    @RequestMapping("sfGetMaino.do")
    @ResponseBody
    public Object sfGetMaino(final HttpServletRequest request) {

        JsonResult jsonResult = new JsonResult(true);
        String[] chooseChildrenIdS = request.getParameterValues("chooseChildrenIdS");
        String user_id = request.getSession().getAttribute("user_id").toString();

        BaseResult sfGetMaino = shunfengService.sfGetMaino(chooseChildrenIdS, user_id);
        jsonResult.setSuccess(sfGetMaino.isSuccess());
        jsonResult.setMessage(sfGetMaino.getMessage());

        return jsonResult;
    }

    /**
     * 运单图片打印
     * 
     * @param request
     * @return
     * @throws Exception 
     */
    @RequestMapping("sfPrint.do")
    @ResponseBody
    public Object sfPrint(final HttpServletRequest request) {

        JsonResult jsonResult = new JsonResult(true);
        String[] chooseChildrenIdS = request.getParameterValues("chooseChildrenIdS");
        String savePath = request.getParameter("savePath");
        String user_id = request.getSession().getAttribute("user_id").toString();

        BaseResult sfPrint = shunfengService.sfPrint(chooseChildrenIdS, user_id, savePath);
        jsonResult.setSuccess(sfPrint.isSuccess());
        jsonResult.setMessage(sfPrint.getMessage());

        return jsonResult;
    }

    @RequestMapping("sfGetMainoAndPrint.do")
    @ResponseBody
    public Object sfGetMainoAndPrint(final HttpServletRequest request) {

        JsonQueryResult<List<String>> jsonResult = new JsonQueryResult<List<String>>(false);
        String[] chooseChildrenIdS = request.getParameterValues("chooseChildrenIdS");
        String user_id = request.getSession().getAttribute("user_id").toString();

        QueryResult<List<String>> sfGetMaino = shunfengService.sfGetMainoAndPrint(
            chooseChildrenIdS, user_id);
        jsonResult.setSuccess(sfGetMaino.isSuccess());
        jsonResult.setMessage(sfGetMaino.getMessage());

        jsonResult.setObj(sfGetMaino.getResultObject());
        return jsonResult;
    }

}
