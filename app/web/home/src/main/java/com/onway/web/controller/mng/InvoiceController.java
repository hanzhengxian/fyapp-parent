package com.onway.web.controller.mng;

import java.text.MessageFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.common.lang.StringUtils;
import com.onway.fyapp.common.dal.dataobject.rst.InvoiceResult;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.result.JsonResult;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;

@Controller
public class InvoiceController extends AbstractController {

    /**
     * 获取发票记录
     * 
     * @param request
     * @param offset
     * @param limit
     * @return 
     */
    @RequestMapping("/selectinvoice.do")
    @ResponseBody
    public JSONObject selectUserAccountList(HttpServletRequest request, Integer offset,
                                            Integer limit) {
        String nickName = request.getParameter("nickName");
        String cell = request.getParameter("cell");
        String invoiceid = request.getParameter("invoiceid");
        String orderid = request.getParameter("orderid");
        String printStatus = request.getParameter("printStatus");
        JSONObject data = new JSONObject();
        try {

            List<InvoiceResult> queryList = invoiceDAO.selectinvoice(cell, nickName, invoiceid,
                printStatus, orderid, offset, limit);
            int count = invoiceDAO.selectinvoice(cell, nickName, invoiceid, printStatus, orderid,
                null, null).size();
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
     * 修改出票状态 
     * 
     * @param request
     * @return
     */
    @RequestMapping("printInvoice.do")
    @ResponseBody
    public Object printInvoice(final HttpServletRequest request) {
        final JsonResult result = new JsonResult(true);
        final String id = request.getParameter("id");
        //        final String printStatus = request.getParameter("printStatus");
        final String user_id = request.getSession().getAttribute("user_id").toString();

        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {

                if (StringUtils.hasLength(id) && Integer.valueOf(id) > 0) {
                    result.setSuccess(0 < invoiceDAO.updatePrintStatus("1", user_id,
                        Integer.valueOf(id)));
                } else {
                    result.setSuccess(false);
                    result.setMessage("操作失败");
                }

            }

            @Override
            public void check() {
                AssertUtil.notBlank(id, "编号不能为空");
                //                AssertUtil.notBlank(printStatus, "发票状态不能为空");

            }
        });
        return result;
    }
}
