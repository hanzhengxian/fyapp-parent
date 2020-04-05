package com.onway.web.controller.sys;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.fyapp.common.dal.dataobject.SysReturnReasonDO;
import com.onway.model.enums.ErrorCode;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.result.JsonResult;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;
/**
 * 退货理由
 */
@Controller
public class ReturnReasonController extends AbstractController {


	@RequestMapping("selectAllReason.do")
	@ResponseBody
	public JSONObject selectAllReason(HttpServletRequest request, Integer offset, Integer limit) {
		JSONObject result = new JSONObject();
		result.put("rows", sysReturnReasonDAO.selectAllReason(offset, limit));
		result.put("total", sysReturnReasonDAO.selectAllReasonCount());
		return result;
	}
	
	@RequestMapping("/insertReason.do")
	@ResponseBody
	public Object insertReason(final SysReturnReasonDO sysReturnReasonDO, final HttpServletRequest request) {
		final JsonResult result = new JsonResult(true);
		controllerTemplate.execute(result, new ControllerCallBack() {
			@Override
			public void executeService() {
				int insertReason = sysReturnReasonDAO.insertReason(sysReturnReasonDO);
				result.setSuccess(insertReason > 0 ? true : false);
				result.setMessage(insertReason > 0 ? "添加成功" : "添加失败");
			}
			@Override
			public void check() {

			}
		});
		return result;
	}

	@RequestMapping("/updateReason.do")
	@ResponseBody
	public Object updateReason(final HttpServletRequest request) {
		final String id = request.getParameter("id");
		final String reason = request.getParameter("reason");
		final String rank = request.getParameter("rank");
		final JsonResult result = new JsonResult(true);
		controllerTemplate.execute(result, new ControllerCallBack() {

			@Override
			public void executeService() {
				int updateReason = sysReturnReasonDAO.updateReason(reason, Integer.parseInt(rank), Integer.parseInt(id));
				result.setSuccess(updateReason > 0 ? true : false);
				result.setMessage(updateReason > 0 ? "修改成功" : "修改失败");
    			}

			@Override
			public void check() {
			       AssertUtil.notBlank(id, "id不能为空");
			       AssertUtil.notBlank(reason, "内容不能为空");
			       AssertUtil.notBlank(rank, "排序不能为空");
			}
		});
		return result;
	}
	
	@RequestMapping("/delReason.do")
	@ResponseBody
	public Object deleteRole(final int id, final HttpServletRequest request) {

		final JsonResult jsonResult = new JsonResult(true);
		controllerTemplate.execute(jsonResult, new ControllerCallBack() {

			@Override
			public void executeService() {
				int delReason = sysReturnReasonDAO.delReason(id);
				jsonResult.setSuccess(delReason > 0 ? true : false);
				jsonResult.setMessage(delReason > 0 ? ErrorCode.SUCCESS
						.message() : ErrorCode.SYSTEM_ERROR.message());
			}

			@Override
			public void check() {

			}
		});
		return jsonResult;
	}
}
