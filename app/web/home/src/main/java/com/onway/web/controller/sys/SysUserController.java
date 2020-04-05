package com.onway.web.controller.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.common.lang.StringUtils;
import com.onway.fyapp.common.dal.dataobject.SysRoleUserDO;
import com.onway.platform.common.enums.PlatformCodeEnum;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.result.JsonResult;
import com.onway.utils.Md5Encrypt;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;
/**
 * 系统人员管理
 * @author ASUS
 *
 */
@Controller
public class SysUserController extends AbstractController{
	
	/**
	 * 跳转到页面
	 * @param map
	 * @return
	 */
	@RequestMapping("/tosysuser.html")
	public String tosysuser(ModelMap map,HttpSession session){
		String menu = (String)session.getAttribute("role");
    	if(StringUtils.isNotBlank(menu)){
			if(!menu.contains("tosysuser.html")){
				return "html/index";
	    	}
    	}
		map.put("role", sysRoleDAO.selectallroletouser());
		return "html/sys/sysuser";
	}
	/**
	 * 查看人员信息
	 * @param username
	 * @param offset
	 * @param limit
	 * @return
	 */
	@RequestMapping("/selectallsysuser.do")
	@ResponseBody
	public JSONObject selectallsysuser(String name,Integer offset,Integer limit){
		JSONObject jo = new JSONObject();
		jo.put("rows", sysRoleUserDAO.selectalladminuser(name, offset, limit));
		jo.put("total", sysRoleUserDAO.selectalladminusercount(name));
		return jo;
	}
	/**
	 * 修改信息
	 * @param username
	 * @param roleId
	 * @param re
	 * @param id
	 * @return
	 */
	@RequestMapping("/updatesysuser.do")
	@ResponseBody
	public Object updatesysuser(final String username,final String roleId,final HttpServletRequest request,final Integer id, final String realName){
		final JsonResult jr = new JsonResult(true);
		final String user_id = request.getSession().getAttribute("user_id").toString();
		controllerTemplate.execute(jr, new ControllerCallBack() {
			
			@Override
			public void executeService() {
				
				jr.setMessage(sysRoleUserDAO.updateadminuser(username, realName, roleId, user_id, id)>0 ? "修改成功":"修改失败");
			}
			
			@Override
			public void check() {
				AssertUtil.notBlank(username, "用户帐号不能为空！");
				AssertUtil.notBlank(roleId, "请选择角色信息！");
				AssertUtil.notBlank(realName, "用户真实姓名不能为空！");
			}
		});
		return jr;
	}
	/**
	 * 添加信息
	 * @param adminUser
	 * @param re
	 * @return
	 */
	@RequestMapping("/addsysuser.do")
	@ResponseBody
	public Object addsysuser(final SysRoleUserDO sysRoleUserDO,final HttpServletRequest request){
		final JsonResult jr = new JsonResult(true);
		final String user_id = request.getSession().getAttribute("user_id").toString();
		controllerTemplate.execute(jr, new ControllerCallBack() {
			
			@Override
			public void executeService() {
				// TODO Auto-generated method stub
				int count = sysRoleUserDAO.queryCountSysUserByUserName(sysRoleUserDO.getUsername());
				if (count > 0) {
					jr.setSuccess(false);
					jr.setMessage("该用户名已存在");
				} else {
					sysRoleUserDO.setUserId(codeGenerateComponent.nextCodeByType(PlatformCodeEnum.WEBSITE_PLATFORM));
					sysRoleUserDO.setPassword(Md5Encrypt.toMD5High(sysRoleUserDO.getPassword()));
					sysRoleUserDO.setCreater(user_id);
					jr.setMessage(sysRoleUserDAO.addadminuser(sysRoleUserDO)>0 ? "添加成功":"添加失败");
				}
			}
			
			@Override
			public void check() {
				AssertUtil.notBlank(sysRoleUserDO.getUsername(), "用户账号不能为空！");
				AssertUtil.notBlank(sysRoleUserDO.getPassword(), "密码不能为空！");
				AssertUtil.notBlank(sysRoleUserDO.getRoleId(), "请选择角色信息！");
				AssertUtil.notBlank(sysRoleUserDO.getRealName(), "用户真实姓名不能为空！");
			}
		});
		return jr;
	}
	
	@RequestMapping("/deleteSysUser.do")
    @ResponseBody
    public Object deleteSysUser(final HttpServletRequest request,final String id){
        final JsonResult jr = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();
        controllerTemplate.execute(jr, new ControllerCallBack() {
            
            @Override
            public void executeService() {
                // TODO Auto-generated method stub
                int a = sysRoleUserDAO.deleteSysUser(user_id, Integer.parseInt(id));
                jr.setSuccess(a > 0);
                jr.setMessage(a > 0 ? "操作成功" : "操作失败");
            }
            
            @Override
            public void check() {
                // TODO Auto-generated method stub
                AssertUtil.notBlank(id, "用户ID不能为空！");
            }
        });
        return jr;
    }
	
}
