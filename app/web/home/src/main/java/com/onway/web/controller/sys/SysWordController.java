package com.onway.web.controller.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.fyapp.common.dal.dataobject.WordDO;
import com.onway.result.JsonResult;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;

/**
 * 等级管理
 * 
 * @author Administrator
 *
 */
@Controller
public class SysWordController extends AbstractController {

	/**
	 * 查询文档
	 * @param offset
	 * @param limit
	 * @return
	 */
	@RequestMapping("selectWord.do")
	@ResponseBody
	public JSONObject selectWord( String wordTypeS, Integer offset, Integer limit) {
		JSONObject result = new JSONObject();
		List<WordDO> searchAllWord = wordDAO.searchAllWord(wordTypeS, offset, limit);
		result.put("rows", searchAllWord);
		result.put("total", wordDAO.searchAllWordCount(wordTypeS));
		return result;
	}

	/**
	 * 添加文档信息
	 * 
	 * @return
	 */
	@RequestMapping("/addWord.do")
	@ResponseBody
	public Object addWord(final WordDO wordDO,
			final HttpServletRequest re) {
		final JsonResult result = new JsonResult(true);
		controllerTemplate.execute(result, new ControllerCallBack() {
			@Override
			public void executeService() {
				int newWord = wordDAO.newWord(wordDO);
				result.setSuccess(newWord > 0 ? true : false);
				result.setMessage(newWord > 0 ? "添加成功" : "添加失败");
			}

			@Override
			public void check() {

			}
		});
		return result;
	}

	/**
	 * 修改等级信息
	 * 
	 * @return
	 */
	@RequestMapping("/updateWord.do")
	@ResponseBody
	public Object updaterole(final WordDO wordDO,
			final HttpServletRequest re) {
		final JsonResult result = new JsonResult(true);
		controllerTemplate.execute(result, new ControllerCallBack() {

			@Override
			public void executeService() {
				int updateWordInfo = wordDAO.updateWordInfo(wordDO.getWordContent(), wordDO.getId());
				result.setSuccess(updateWordInfo > 0 ? true : false);
				result.setMessage(updateWordInfo > 0 ? "修改成功" : "修改失败");
			}

			@Override
			public void check() {

			}
		});
		return result;
	}

}
