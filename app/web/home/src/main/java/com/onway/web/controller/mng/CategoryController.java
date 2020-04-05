package com.onway.web.controller.mng;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.fyapp.common.dal.dataobject.CategoryDO;
import com.onway.result.JsonResult;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;

/**
 * 商品分类
 */
@Controller
public class CategoryController extends AbstractController {

    /**
     * 一级分类
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/selectFirstCate.do")
    @ResponseBody
    public JSONObject selectFirstCate(HttpServletRequest request, Integer offset, Integer limit) {

        JSONObject data = new JSONObject();
        try {
            List<Map<String, Object>> selectFirstCate = new ArrayList<Map<String, Object>>();

            List<CategoryDO> firstCate = categoryDAO.selectByTopId(0, offset, limit);

            for (CategoryDO categoryFirst : firstCate) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("ID", categoryFirst.getId());
                map.put("TOP_ID", categoryFirst.getTopId());
                map.put("TOP_NAME", categoryFirst.getCategoryName());
                map.put("CATEGORY_NAME", categoryFirst.getCategoryName());
                selectFirstCate.add(map);
            }

            data.put("rows", selectFirstCate);
            data.put("total", categoryDAO.selectByTopIdCount(0));

        } catch (Exception e) {
            logger.error(MessageFormat.format("二级分类 列表异常", new Object[] {}));
        }
        return data;
    }

    /**
     * 二级分类 列表
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    //    @RequestMapping("/selectSecondCate.do")
    //    @ResponseBody
    //    public JSONObject selectSecondCate(HttpServletRequest request, Integer offset, Integer limit) {
    //
    //        JSONObject data = new JSONObject();
    //        try {
    //            List<Map<String, Object>> selectSecondCate = new ArrayList<Map<String, Object>>();
    //
    //            List<CategoryDO> firstCate = categoryDAO.selectByTopId(0);
    //
    //            for (CategoryDO categoryFirst : firstCate) {
    //                Map<String, Object> map;
    //                List<CategoryDO> secondCate = categoryDAO.selectByTopId(categoryFirst.getId());
    //                for (CategoryDO categorySecont : secondCate) {
    //                    map = new HashMap<String, Object>();
    //                    map.put("ID", categorySecont.getId());
    //                    map.put("TOP_ID", categoryFirst.getId());
    //                    map.put("TOP_NAME", categoryFirst.getCategoryName());
    //                    map.put("CATEGORY_NAME", categorySecont.getCategoryName());
    //                    selectSecondCate.add(map);
    //                }
    //            }
    //            data.put("rows", selectSecondCate);
    //            data.put("total", selectSecondCate.size());
    //
    //        } catch (Exception e) {
    //            logger.error(MessageFormat.format("二级分类 列表异常", new Object[] {}));
    //        }
    //        return data;
    //    }

    /**
     * 根据topId查询分类
     * 
     * @param request
     * @param offset
     * @param limit
     * @param topId
     * @return
     */
    @RequestMapping("/queryCateByTopId.do")
    @ResponseBody
    public JSONObject queryCateByTopId(HttpServletRequest request, Integer offset, Integer limit) {

        String topId = request.getParameter("topId");
        JSONObject data = new JSONObject();
        try {
            List<Map<String, Object>> selectThirdCate = new ArrayList<Map<String, Object>>();

            List<CategoryDO> thirdCate = categoryDAO.selectByTopId(Integer.valueOf(topId), offset,
                limit);

            CategoryDO selectById = categoryDAO.selectById(Integer.valueOf(topId));
            for (CategoryDO categoryThird : thirdCate) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("ID", categoryThird.getId());
                map.put("TOP_ID", topId);
                map.put("CATEGORY_NAME", categoryThird.getCategoryName());
                if (null != selectById)
                    map.put("TOP_NAME", selectById.getCategoryName());
                selectThirdCate.add(map);
            }
            data.put("rows", selectThirdCate);
            data.put("total", categoryDAO.selectByTopIdCount(Integer.valueOf(topId)));

        } catch (Exception e) {
            logger.error(MessageFormat.format("分类 列表异常", new Object[] {}));
        }
        return data;
    }

    /**
     * 更新属性
     * 
     * @param request
     * @param goodsAttr
     * @return
     */
    @RequestMapping("/updateCate.do")
    @ResponseBody
    public Object updateGoodsAttr(final HttpServletRequest request, final CategoryDO categoryDO) {

        final JsonResult data = new JsonResult(true);
        controllerTemplate.execute(data, new ControllerCallBack() {

            @Override
            public void executeService() {
                if (categoryDO.getId() == 0) {
                    //                    if(categoryDAO.selectByTopIdCount(0) >= 2){
                    //                        throw new BaseRuntimeException("目前只能存在两个一级分类！");
                    //                    }
                    //新增
                    categoryDAO.newCate(categoryDO);
                } else {
                    //修改	
                    categoryDAO.updateCate(categoryDO);
                }
            }

            @Override
            public void check() {
            }
        });
        return data;
    }

    /**
     * 删除属性
     * 
     * @param request
     * @param goodsAttr
     * @return
     */
    @RequestMapping("/deleteCate.do")
    @ResponseBody
    public Object deleteGoodsAttr(final HttpServletRequest request, final int id) {

        final JsonResult data = new JsonResult(true);
        controllerTemplate.execute(data, new ControllerCallBack() {

            @Override
            public void executeService() {
                categoryDAO.deleteCate(id);
            }

            @Override
            public void check() {
            }
        });
        return data;
    }

}
