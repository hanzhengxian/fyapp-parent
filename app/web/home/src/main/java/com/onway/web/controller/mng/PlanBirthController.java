package com.onway.web.controller.mng;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.common.lang.DateUtils;
import com.onway.common.lang.Money;
import com.onway.common.lang.StringUtils;
import com.onway.utils.BigdecimalUtil;
import com.onway.web.controller.AbstractController;

/**
 * 方案管理-注册生日当月方案控制器
 * 
 * @author liaoshengzhe
 * @version $Id: PlanBirthController.java, v 0.1 2018年8月21日 下午6:20:09 liaoshengzhe Exp $
 */
@Controller
public class PlanBirthController extends AbstractController {

    /**
     * 查询方案列表
     * 
     * @param request
     * @return
     */
	@RequestMapping("selectPlanBirth.do")
	@ResponseBody
	public Object selectPlanBirth(HttpServletRequest request) {
		
		JSONObject data = new JSONObject();
		try {
			List<Map<String, Object>> queryList = planBirthDAO.queryList();
			for (Map<String, Object> map : queryList) {
				Money money = BigdecimalUtil.toMoney((BigDecimal)map.get("amount"));
				map.put("amount", money);
			}
			data.put("success", true);
			data.put("rows", queryList);
			data.put("total", planBirthDAO.queryListCount());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(MessageFormat.format("查询方案异常", new Object[] {}));
		}
		return data;
	}
	
	/**
	 * 修改方案
	 * 
	 * @param request
	 * @param offset
	 * @param limit
	 * @return
	 */
	@RequestMapping("updatePlanBirth.do")
	@ResponseBody
	public Object updatePlanBirth(HttpServletRequest request) {
		String user_id = request.getSession().getAttribute("user_id").toString();
		
        String birthId = request.getParameter("birthId");
        String planType = request.getParameter("planType");
        String amountType = request.getParameter("amountType");
        String amount = request.getParameter("amount");
        String rateTmp = request.getParameter("rate");
        String status = request.getParameter("status");
        String endDateTmp = request.getParameter("endDate");
		JSONObject data = new JSONObject();
		Money moneyPrice = new Money(0);
		if(StringUtils.isNotBlank(amount)){
		    moneyPrice = new Money(amount);
        }
		Double rate = new Double(0);
		if (StringUtils.isNotBlank(rateTmp)) {
            rate = Double.parseDouble(rateTmp);
        }
		Date endDate = null;
		if (StringUtils.isNotBlank(endDateTmp)) {
		    try {
	            endDate = DateUtils.parseDate(endDateTmp, "yyyy-MM-dd HH:mm:ss");
	        } catch (ParseException e) {
	            logger.error(MessageFormat.format("时间转换异常", new Object[]{}));
	        }
        }
		int result = planBirthDAO.update(planType, amountType, moneyPrice, rate, endDate, status, user_id, birthId);
		data.put("success", result > 0 ? true : false);
		data.put("message", result > 0 ? "操作成功" : "操作失败");
		return data;
	}

}