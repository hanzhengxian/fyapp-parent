package com.onway.web.controller.sys;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.common.lang.StringUtils;
import com.onway.fyapp.common.dal.dataobject.SysSearchDO;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.result.JsonResult;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;

@Controller
public class SysSearchController extends AbstractController {

    /**
     * 分页查询
     * 
     * @param keyWord
     * @param status
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("getSearchList.do")
    @ResponseBody
    public Object getSearchList(String keyWord, String status, Integer offset, Integer limit) {
        JSONObject jo = new JSONObject();
        jo.put("rows", sysSearchDAO.selectByQuery(keyWord, status, offset, limit));
        jo.put("total", sysSearchDAO.selectCountByQuery(keyWord, status));
        return jo;
    }

    /**
     * 新增或修改
     * 
     * @param request
     * @return
     */
    @RequestMapping("editSearch.do")
    @ResponseBody
    public Object editSearch(final HttpServletRequest request) {
        final JsonResult result = new JsonResult(true);
        final String id = request.getParameter("id");
        final String keyWord = request.getParameter("akeyWord");
        final String status = request.getParameter("astatus");
        final String times = request.getParameter("times");
        final String rank = request.getParameter("rank");
        final String memo = request.getParameter("memo");

        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {

                if (StringUtils.hasLength(id) && Integer.valueOf(id) > 0) {
                    result.setSuccess(0 < sysSearchDAO.updateById(keyWord, status,
                        Integer.valueOf(times), Integer.valueOf(rank), memo, Integer.valueOf(id)));
                } else {
                    SysSearchDO sysSearch = new SysSearchDO();
                    sysSearch.setKeyWord(keyWord);
                    sysSearch.setStatus(status);
                    if (StringUtils.hasLength(times)) {
                        sysSearch.setTimes(Integer.valueOf(times));
                    } else {
                        sysSearch.setTimes(0);
                    }
                    sysSearch.setRank(Integer.valueOf(rank));
                    sysSearch.setMemo(memo);
                    sysSearch.setDelFlg("0");
                    result.setSuccess(0 < sysSearchDAO.insert(sysSearch));
                }

            }

            @Override
            public void check() {
                AssertUtil.notBlank(keyWord, "关键词不能为空");
                AssertUtil.notBlank(status, "状态不能为空");
                AssertUtil.notBlank(keyWord, "关键词不能为空");
                AssertUtil.notBlank(rank, "排序不能为空");

            }
        });
        return result;
    }

    @RequestMapping("deletSearch.do")
    @ResponseBody
    public Object deletSearch(final HttpServletRequest request) {
        final JsonResult result = new JsonResult(true);
        final String id = request.getParameter("id");

        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {

                if (StringUtils.hasLength(id) && Integer.valueOf(id) > 0) {
                    result.setSuccess(0 < sysSearchDAO.delById("1", Integer.valueOf(id)));
                } else {
                    result.setSuccess(false);
                    result.setMessage("操作失败");
                }

            }

            @Override
            public void check() {
                AssertUtil.notBlank(id, "编号不能为空");

            }
        });
        return result;
    }
}
