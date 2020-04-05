package com.onway.web.controller.mng;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.fyapp.common.dal.dataobject.GoodsAttrDO;
import com.onway.fyapp.common.dal.dataobject.GoodsAttrValueDO;
import com.onway.result.JsonResult;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;

/**
 * 商品属性
 */
@Controller
public class GoodsAttrController extends AbstractController {

    /**
     * 商品属性列表
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/selectAllAttr.do")
    @ResponseBody
    public JSONObject selectAllAttr(HttpServletRequest request, Integer offset, Integer limit) {
        String attrName = request.getParameter("attrName");
        JSONObject data = new JSONObject();
        try {

            List<Map<String, Object>> selectPage = goodsAttrDAO.selectPage(attrName, offset, limit);

            data.put("rows", selectPage);
            data.put("total", goodsAttrDAO.selectPageCount(attrName));

        } catch (Exception e) {
            logger.error(MessageFormat.format("查询商品属性列表异常", new Object[] {}));
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
    @RequestMapping("/updateGoodsAttr.do")
    @ResponseBody
    public Object updateGoodsAttr(final HttpServletRequest request, final GoodsAttrDO goodsAttr) {

        final JsonResult data = new JsonResult(true);

        final String user_id = request.getSession().getAttribute("user_id").toString();
        controllerTemplate.execute(data, new ControllerCallBack() {

            @Override
            public void executeService() {
                if (0 == goodsAttr.getId()) {
                    goodsAttr.setNormal(true);
                    goodsAttr.setCreater(user_id);
                    goodsAttrDAO.insert(goodsAttr);
                } else {
                    goodsAttrDAO.update(goodsAttr, user_id);
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
    @RequestMapping("/deleteGoodsAttr.do")
    @ResponseBody
    public Object deleteGoodsAttr(final HttpServletRequest request, final int attrId) {

        final JsonResult data = new JsonResult(true);
        controllerTemplate.execute(data, new ControllerCallBack() {

            @Override
            public void executeService() {
                goodsAttrDAO.deleteByAttrNo(attrId);
            }

            @Override
            public void check() {
            }
        });
        return data;
    }

    /**
     * 商品属性值列表
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/selectAllAttrValue.do")
    @ResponseBody
    public JSONObject selectAllAttrValue(HttpServletRequest request, Integer offset, Integer limit) {
        String attrId = request.getParameter("attrId");
        JSONObject data = new JSONObject();
        try {

            List<Map<String, Object>> selectPage = goodsAttrValueDAO.selectPage(attrId, offset,
                limit);

            data.put("rows", selectPage);
            data.put("total", goodsAttrValueDAO.selectPageCount(attrId));

        } catch (Exception e) {
            logger.error(MessageFormat.format("查询商品属性值列表异常", new Object[] {}));
        }
        return data;
    }

    /**
     * 更新属性值
     * 
     * @param request
     * @param goodsAttr
     * @return
     */
    @RequestMapping("/updateGoodsAttrValue.do")
    @ResponseBody
    public Object updateGoodsAttrValue(final HttpServletRequest request,
                                       final GoodsAttrValueDO goodsAttrValue) {

        final JsonResult data = new JsonResult(true);
        final String user_id = request.getSession().getAttribute("user_id").toString();
        controllerTemplate.execute(data, new ControllerCallBack() {

            @Override
            public void executeService() {
                if (0 == goodsAttrValue.getId()) {
                    goodsAttrValue.setCreater(user_id);
                    goodsAttrValueDAO.insert(goodsAttrValue);
                } else {
                    goodsAttrValueDAO.update(goodsAttrValue, user_id);
                }
            }

            @Override
            public void check() {
            }
        });
        return data;
    }

    /**
     * 删除属性值
     * 
     * @param request
     * @param goodsAttr
     * @return
     */
    @RequestMapping("/deleteGoodsAttrValue.do")
    @ResponseBody
    public Object deleteGoodsAttrValue(final HttpServletRequest request, final int attrId) {

        final JsonResult data = new JsonResult(true);
        controllerTemplate.execute(data, new ControllerCallBack() {

            @Override
            public void executeService() {
                goodsAttrValueDAO.deleteById(attrId);
            }

            @Override
            public void check() {
            }
        });
        return data;
    }

}
