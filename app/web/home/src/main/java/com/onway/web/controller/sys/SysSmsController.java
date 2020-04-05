package com.onway.web.controller.sys;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.utils.DateUtil;
import com.onway.web.controller.AbstractController;

/**
 * 系统 短信记录控制类
 * 
 * @author Administrator
 *
 */
@Controller
public class SysSmsController extends AbstractController {

	/**
	 * 短信记录
	 * 
	 * @param offset
	 * @param limit
	 * @param word
	 * @param selectstatus
	 * @return
	 */
	@RequestMapping("/selectsyssms.do")
	@ResponseBody
	public JSONObject selectsyssms(Integer offset, Integer limit,
			String selectcell, String starpdate, String enddate) {
		JSONObject jo = new JSONObject();
		Date startTime = DateUtil.stringToDate(starpdate, DateUtil.webFormat);
		Date endTime = DateUtil.stringToDate(enddate, DateUtil.webFormat);
		if(null != endTime)
		    endTime = DateUtil.addDays(endTime, 1);
		List<Map<String, Object>> selectallSms = smDAO.selectallSms(selectcell,
		    startTime, endTime, offset, limit);
		jo.put("rows", selectallSms);
		jo.put("total", smDAO.selectallSmscount(selectcell, startTime, endTime));
		return jo;
	}
}
