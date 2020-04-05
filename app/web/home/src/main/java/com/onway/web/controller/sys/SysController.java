package com.onway.web.controller.sys;

import java.io.IOException;
import java.util.Date;








import java.util.List;

import org.apache.commons.httpclient.HttpException;
//import org.apache.commons.httpclient.HttpException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.common.lang.HttpUtils;
import com.onway.common.lang.StringUtils;
import com.onway.core.service.localcache.enums.LocalCacheNameEnum;
import com.onway.fyapp.common.dal.dataobject.SysConfigDO;
import com.onway.model.enums.SysConfigCacheKeyEnum;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.result.JsonResult;
import com.onway.utils.NumUtils;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;

/**
 * 系统配置管理
 * @author Administrator
 *	jar包有问题  以后解决
 */
@Controller
public class SysController extends AbstractController{
	
	/**
	 * 查看系统配置信息
	 * @param offset
	 * @param limit
	 * @param name
	 * @return
	 */
	@RequestMapping("/selectconfig.do")
	@ResponseBody
	public JSONObject selectconfig(Integer offset,Integer limit,String name){
		JSONObject jo = new JSONObject();
		List<SysConfigDO> selectallsysconfig = sysConfigDAO.selectallsysconfig(name, offset, limit);
		for (SysConfigDO sysConfigDO : selectallsysconfig) {
		    if(StringUtils.equals(sysConfigDO.getSysKey(), SysConfigCacheKeyEnum.COMMEN_RETURN_RATE.getCode())){
		        String sysValue = String.format("%.0f", Double.parseDouble(sysConfigDO.getSysValue())*100);
		        sysConfigDO.setSysValue(sysValue);
		    }
        }
		jo.put("rows",selectallsysconfig);
		jo.put("total", sysConfigDAO.selectallsysconfigcount(name));
		return jo;
	}
	/**
	 * 手动刷新缓存
	 */
	@RequestMapping("/reload.do")
	@ResponseBody
	public void reload(){
		try {
			HttpUtils.executeGetMethod(sysConfigCacheManager.getConfigValue("REFRESH_URL"));
			sysConfigCacheManager.reload();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加系统配置
	 * @param sysConfig
	 * @param re
	 * @return
	 */
	@RequestMapping("/insertsysconfig.do")
	@ResponseBody
	public Object insertsysconfig(final SysConfigDO sysConfig){
		final JsonResult jr =  new JsonResult(true);
		controllerTemplate.execute(jr, new ControllerCallBack() {
			
			@Override
			public void executeService() {
				sysConfig.setGmtCreate(new Date());
				jr.setMessage(sysConfigDAO.insertsysconfig(sysConfig)>0 ? "添加成功":"添加失败");
				cacheManager.refresh(LocalCacheNameEnum.SYS_CONFIG.getCode());
				//刷新本地缓存
//				sysConfigCacheManager.reload();
				//请求刷新app服务器缓存
//				try {
//					HttpUtils.executeGetMethod(sysConfigCacheManager.getConfigValue("REFRESH_URL"));
//				} catch (HttpException e) {
//					e.printStackTrace();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
			}
			
			@Override
			public void check() {
				AssertUtil.notBlank(sysConfig.getMemo(), "名称不能为空！");
				AssertUtil.notBlank(sysConfig.getSysKey(), "键名不能为空！");
				AssertUtil.notBlank(sysConfig.getSysValue(), "值不能为空！");
			}
		});
		return jr;
	}
	/**
	 * 修改配置信息
	 * @param memo
	 * @param key
	 * @param value
	 * @param status
	 * @param id
	 * @return
	 */
	@RequestMapping("/updatesysconfig.do")
	@ResponseBody
	public Object updatesysconfig(final String memo,final String sysKey ,final String sysValue,final String status,final String id){
		final JsonResult jr =  new JsonResult(true);
		controllerTemplate.execute(jr, new ControllerCallBack() {
			
			@Override
			public void executeService() {
			    String thisValue = sysValue;
			    if(StringUtils.equals(sysKey, SysConfigCacheKeyEnum.COMMEN_RETURN_RATE.getCode())){
			        Double rateDouble = Double.valueOf(sysValue);
			        thisValue =  String.format("%.2f", rateDouble/100);
			    }
				jr.setMessage(sysConfigDAO.updatesysconfig(new Date(), sysKey, thisValue, status, memo, id)>0 ? "修改成功":"修改失败");
				cacheManager.refresh(LocalCacheNameEnum.SYS_CONFIG.getCode());
				//刷新本地缓存
//				sysConfigCacheManager.reload();
				//请求刷新app服务器缓存
//				try {
//					HttpUtils.executeGetMethod(sysConfigCacheManager.getConfigValue("REFRESH_URL"));
//				} catch (HttpException e) {
//					e.printStackTrace();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
			}
			
			@Override
			public void check() {
				AssertUtil.notBlank(id, "异常！");
				if(StringUtils.equals(sysKey, SysConfigCacheKeyEnum.COMMEN_RETURN_RATE.getCode())){
				    AssertUtil.state(NumUtils.checkeRate(sysValue), "请填写正确的百分比0~100");
				}
			}
		});
		return jr;
	}
}
