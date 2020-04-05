package com.onway.web.controller.mng;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.core.service.ExpressComponent;
import com.onway.platform.common.base.QueryResult;
import com.onway.result.express.ExpressQueryPojo;
import com.onway.web.controller.AbstractController;

@Controller
public class ExpressController extends AbstractController {

    @Resource
    private ExpressComponent expressComponent;

    /**
     * 查询物流 测试
     * @param request
     * @return
     */
    @RequestMapping("/getExpressText.do")
    @ResponseBody
    public Object getExpressText(HttpServletRequest request) {

        String com = request.getParameter("com");
        String num = request.getParameter("num");
        QueryResult<ExpressQueryPojo> kuaiDiInfo4Firm = expressComponent.getKuaiDiInfo4Firm(
            com, num);
        return kuaiDiInfo4Firm;
    }

}
