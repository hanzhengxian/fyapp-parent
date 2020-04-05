package com.onway.web.controller.sys;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.fyapp.common.dal.dataobject.ProductTypeDO;
import com.onway.model.enums.DelFlgEnum;
import com.onway.platform.common.exception.BaseRuntimeException;
import com.onway.result.JsonResult;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;

/**
 * 自定义商品类型  管理
 * 
 * @author yuhang.ni
 * @version $Id: SysProTypeController.java, v 0.1 2019年4月15日 下午2:18:47 Administrator Exp $
 */
@Controller
public class SysProTypeController extends AbstractController {

	/**
	 * 查询类型
	 * 
	 * @param offset
	 * @param limit
	 * @return
	 */
	@RequestMapping("/queryProType.do")
	@ResponseBody
	public JSONObject queryProType(Integer offset, Integer limit) {
		JSONObject result = new JSONObject();
		List<Map<String, Object>> queryAllType = productTypeDAO.queryAllType(DelFlgEnum.NOT_DEL.getCode(), offset, limit);
		result.put("rows", queryAllType);
		result.put("total", productTypeDAO.queryAllTypeCount(DelFlgEnum.NOT_DEL.getCode()));
		return result;
	}

	/**
	 * 修改
	 * 
	 * @param productTypeDO
	 * @param request
	 * @return
	 */
	@RequestMapping("/modifyProType.do")
	@ResponseBody
	public Object modifyProType(final ProductTypeDO productTypeDO,
			final HttpServletRequest request) {
		final JsonResult result = new JsonResult(true);
		controllerTemplate.execute(result, new ControllerCallBack() {

			@Override
			public void executeService() {
			    ProductTypeDO queryByTypeId = productTypeDAO.queryByTypeId(productTypeDO.getTypeId());
			    if(null == queryByTypeId)
			        throw new BaseRuntimeException("信息查询异常");
			    
			    int modify = productTypeDAO.modify(productTypeDO);
			    
				result.setSuccess(modify > 0 ? true : false);
				result.setMessage(modify > 0 ? "修改成功" : "修改失败");
			}

			@Override
			public void check() {

			}
		});
		return result;
	}

}
