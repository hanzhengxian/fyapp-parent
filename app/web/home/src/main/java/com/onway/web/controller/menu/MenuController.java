package com.onway.web.controller.menu;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.onway.common.lang.StringUtils;
import com.onway.fyapp.common.dal.dataobject.SysMenuDO;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.result.JsonResult;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;
/**
 * 菜单管理
 * @author ASUS
 *
 */
@Controller
public class MenuController extends AbstractController {
	
	/**
	 * 跳转页面查看信息
	 * @param map
	 * @return
	 */
	@RequestMapping("/menu.html")
	public String tomenu(ModelMap map,HttpSession session,HttpServletRequest request){
		String menu = (String)session.getAttribute("role");
    	if(StringUtils.isNotBlank(menu)){
			if(!menu.contains("menu.html")){
				request.getSession().removeAttribute("username");
				request.getSession().removeAttribute("user_id");
				return "html/login";
	    	}
    	}
		map.put("menu", JSONArray.toJSON(sysMenuDAO.selectallmenu()));
		map.put("maxid", sysMenuDAO.selectmaxid());
		return "html/menu/menu";
	}
	/**
	 * 修改菜单信息
	 * @param name
	 * @param url
	 * @param rank
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/updatemenu.do")
	@ResponseBody
	public Object updatemenu(final String name,final String url,final Integer rank,final Integer id,final HttpServletRequest request){
		final JsonResult result = new JsonResult(true);
		controllerTemplate.execute(result, new ControllerCallBack() {
			
			@Override
			public void executeService() {
				int updatemenu = sysMenuDAO.updatemenu(name, url, rank,request.getSession().getAttribute("username")+"",new Date(),id);
				result.setSuccess(updatemenu > 0 ? true:false);
				result.setMessage(updatemenu > 0 ? "修改成功":"修改失败");
			}
			
			@Override
			public void check() {
				AssertUtil.notBlank(name, "名称不能为空！");
			}
		});
		return result;
	}
	/**
	 * 添加信息
	 * @param name
	 * @param url
	 * @param rank
	 * @param id
	 * @param request
	 * @param pid
	 * @return
	 */
	@RequestMapping("/addmenu.do")
	@ResponseBody
	public Object addmenu(final String name,final String url,final Integer rank,final Integer id,final HttpServletRequest request,final Integer pid){
		final JsonResult result = new JsonResult(true);
		controllerTemplate.execute(result, new ControllerCallBack() {
			
			@Override
			public void executeService() {
				SysMenuDO menu = new SysMenuDO();
//				menu.setId(id);
				menu.setName(name);
				menu.setUrl(url);
				menu.setPid(pid);
				menu.setRank(rank);
				menu.setCreater(request.getSession().getAttribute("username")+"");
				menu.setGmtCreate(new Date());
				int addmenu = sysMenuDAO.addmenu(menu);
				result.setSuccess(addmenu > 0 ? true:false);
				result.setMessage(addmenu > 0 ? "添加成功":"添加失败");
			}
			
			@Override
			public void check() {
				AssertUtil.notBlank(name, "名称不能为空！");
				AssertUtil.notNull(id, "异常！");
				AssertUtil.notNull(pid, "异常！");
			}
		});
		return result;
	}

	/**
	 * 删除菜单
	 * @param id
	 * @param type
	 * @return
	 */
	@RequestMapping("/deletemenu.do")
	@ResponseBody
	public Object deletemenu(final Integer id,final String type){
		final JsonResult result = new JsonResult(true);
		controllerTemplate.execute(result, new ControllerCallBack() {
			
			@Override
			public void executeService() {
				//如果是1的话属于父节点
				if("1".equals(type)){
					sysMenuDAO.deletemenubyid(id);
					sysMenuDAO.deletemenubypid(id);
				}else{
					sysMenuDAO.deletemenubyid(id);
				}
			}
			
			@Override
			public void check() {
				
			}
		});
		return result;
	}
}
