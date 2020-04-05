package com.onway.web.controller.sys;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.onway.fyapp.common.dal.dataobject.SysRoleDO;
import com.onway.model.enums.ErrorCode;
import com.onway.platform.common.enums.PlatformCodeEnum;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.result.JsonResult;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;

/**
 * 角色管理
 * 
 * @author ASUS
 *
 */
@Controller
public class RoleController extends AbstractController {
	/**
	 * 
	 * 查看信息
	 * 
	 * @param offset
	 * @param limit
	 * @param name
	 * @return
	 */
	@RequestMapping("selectrole.do")
	@ResponseBody
	public JSONObject selectrole(Integer offset, Integer limit,
			String selectname) {
		JSONObject result = new JSONObject();
		result.put("rows", sysRoleDAO.selectallrole(selectname, offset, limit));
		result.put("total", sysRoleDAO.selectallrolecount(selectname));
		return result;
	}

	/**
	 * 添加角色信息
	 * 
	 * @param role
	 * @param re
	 * @return
	 */
	@RequestMapping("/addrole.do")
	@ResponseBody
	public Object addrole(final SysRoleDO role, final HttpServletRequest request) {
		final JsonResult result = new JsonResult(true);
		final String user_id = request.getSession().getAttribute("user_id")
				.toString();
		controllerTemplate.execute(result, new ControllerCallBack() {

			@Override
			public void executeService() {
				role.setRoleId(codeGenerateComponent
						.nextCodeByType(PlatformCodeEnum.WEBSITE_PLATFORM));
				role.setCreater(user_id);
				int insertrole = sysRoleDAO.insertrole(role);
				result.setSuccess(insertrole > 0 ? true : false);
				result.setMessage(insertrole > 0 ? "添加成功" : "添加失败");
			}

			@Override
			public void check() {
				AssertUtil.notBlank(role.getName(), "名称不能为空！");
			}
		});
		return result;
	}

	/**
	 * 修改角色名称 信息
	 * 
	 * @param name
	 * @param re
	 * @param id
	 * @return
	 */
	@RequestMapping("/updaterole.do")
	@ResponseBody
	public Object updaterole(final String name,
			final HttpServletRequest request, final Integer id) {
		final JsonResult result = new JsonResult(true);
		final String user_id = request.getSession().getAttribute("user_id")
				.toString();
		controllerTemplate.execute(result, new ControllerCallBack() {

			@Override
			public void executeService() {
				int updaterole = sysRoleDAO.updaterole(name, user_id, id);
				result.setSuccess(updaterole > 0 ? true : false);
				result.setMessage(updaterole > 0 ? "修改成功" : "修改失败");
			}

			@Override
			public void check() {
				AssertUtil.notBlank(name, "名称不能为空！");
			}
		});
		return result;
	}

	/**
	 * 查看权限信息
	 * 
	 * @param roldid
	 * @return
	 */
	@RequestMapping("/selectjutisdiction.do")
	@ResponseBody
	public JSONObject selectjutisdiction(Integer roldid) {
		JSONObject result = new JSONObject();
		JSONArray ja = JSONArray.parseArray(sysRoleDAO
				.selectjurisidiction(roldid));
		result.put("menu", JSONArray.toJSON(sysMenuDAO.selectallmenu()));
		result.put("role", ja);
		return result;
	}

	/**
	 * 修改角色权限
	 * 
	 * @param value
	 * @param re
	 * @param id
	 * @return
	 */
	@RequestMapping("/updatejurisidiction.do")
	@ResponseBody
	public Object updaterole(final int id, final String value,
			final HttpServletRequest request) {
		final JsonResult result = new JsonResult(true);
		final String user_id = request.getSession().getAttribute("user_id")
				.toString();
		controllerTemplate.execute(result, new ControllerCallBack() {
			@Override
			public void executeService() {
				int updatejurisidiction = sysRoleDAO.updatejurisidiction(value,
						user_id, id);
				result.setSuccess(updatejurisidiction > 0 ? true : false);
				result.setMessage(updatejurisidiction > 0 ? "修改成功" : "修改失败");
			}

			@Override
			public void check() {
				AssertUtil.notBlank(value, "菜单权限为空！");
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
	@RequestMapping("/deleteRole.do")
	@ResponseBody
	public Object deleteRole(final int id, final HttpServletRequest request) {

		final JsonResult jsonResult = new JsonResult(true);
		controllerTemplate.execute(jsonResult, new ControllerCallBack() {

			@Override
			public void executeService() {
				int deleteRole = sysRoleDAO.deleteRole(id);
				jsonResult.setSuccess(deleteRole > 0 ? true : false);
				jsonResult.setMessage(deleteRole > 0 ? ErrorCode.SUCCESS
						.message() : ErrorCode.SYSTEM_ERROR.message());
			}

			@Override
			public void check() {

			}
		});
		return jsonResult;
	}
}
