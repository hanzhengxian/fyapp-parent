package com.onway.web.controller.mng;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.onway.common.lang.CollectionUtils;
import com.onway.common.lang.Money;
import com.onway.common.lang.StringUtils;
import com.onway.fyapp.common.dal.dataobject.PlanReturnDO;
import com.onway.model.enums.BizTypeEnum;
import com.onway.model.enums.DelFlgEnum;
import com.onway.model.enums.PlanReturnTypeEnum;
import com.onway.model.enums.StockReturnTypeEnum;
import com.onway.platform.common.exception.BaseRuntimeException;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.result.JsonResult;
import com.onway.result.LinkProStock;
import com.onway.utils.BigdecimalUtil;
import com.onway.utils.DateUtil;
import com.onway.utils.ListToMap;
import com.onway.utils.NumUtils;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;

/**
 * 方案管理-返利方案控制器
 * 
 * @author liaoshengzhe
 * @version $Id: DiscountController.java, v 0.1 2018年8月21日 下午6:20:09 liaoshengzhe Exp $
 */
@Controller
public class PlanReturnController extends AbstractController {

    /**
     * 分页查询返利方案
     * 
     * @param request
     * @return
     */
    @RequestMapping("selectPlanReturn.do")
    @ResponseBody
    public Object selectPlanReturn(HttpServletRequest request, Integer offset, Integer limit) {
        String returnId = request.getParameter("returnId");
        String returnName = request.getParameter("returnName");
        String returnType = request.getParameter("returnType");
        String recommendLevel = request.getParameter("recommendLevel");
        String teamId = request.getParameter("teamId");

        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");

        String status = request.getParameter("status");//0正在进行  1已结束

        Date now = new Date();

        JSONObject data = new JSONObject();
        try {
            Date startDate = DateUtil.stringToDate(startTime, DateUtil.webFormat);
            Date endDate = DateUtil.stringToDate(endTime, DateUtil.webFormat);
            if (null != endDate)
                endDate = DateUtil.addDays(endDate, 1);

            List<Map<String, Object>> queryList = planReturnDAO.queryList(returnId, returnName,
                returnType, recommendLevel, teamId, startDate, endDate, status, now, offset, limit);
            int count = planReturnDAO.queryListCount(returnId, returnName, returnType,
                recommendLevel, teamId, startDate, endDate, status, now);
            data.put("success", true);
            data.put("rows", queryList);
            data.put("total", count);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error(MessageFormat.format("查询返利方案异常", new Object[] {}));
        }
        return data;
    }

    /**
     * 添加推荐人返利方案
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("addPlanReturn.do")
    @ResponseBody
    public Object addPlanReturn(HttpServletRequest request, Integer offset, Integer limit) {
        String addReturnName = request.getParameter("addReturnName");
        String addReturnType = request.getParameter("addReturnType");
        String addRecommendLevel = request.getParameter("addRecommendLevel");
        String addReturnRate = request.getParameter("addReturnRate");
        String addTeamId = request.getParameter("addTeamId");
        String user_id = request.getSession().getAttribute("user_id").toString();

        JSONObject data = new JSONObject();
        if (!NumUtils.checkeRate(addReturnRate)) {
            data.put("success", false);
            data.put("message", "请填写正确的百分比值");
            return data;
        }

        int queryCount = planReturnDAO.queryCount(addReturnType, addRecommendLevel, addTeamId);
        if (queryCount > 0) {
            data.put("success", true);
            data.put("message", "添加失败，已经有相同类型相同等级的方案，请勿重复添加");
        } else {
            PlanReturnDO planReturnDO = new PlanReturnDO();
            planReturnDO.setReturnId(codeGenerateComponent.nextCodeByType(BizTypeEnum.RETURN_ID));
            planReturnDO.setReturnName(addReturnName);
            planReturnDO.setReturnType(addReturnType);
            planReturnDO.setRecommendLevel(Integer.parseInt(addRecommendLevel));
            planReturnDO.setReturnRate(Double.parseDouble(addReturnRate) / 100);
            planReturnDO.setReturnSubType("2");
            planReturnDO.setTeamId(addTeamId);
            planReturnDO.setDelFlg("0");
            planReturnDO.setModifier(user_id);
            int result = planReturnDAO.insert(planReturnDO);
            data.put("success", result > 0 ? true : false);
            data.put("message", result > 0 ? "添加成功" : "添加失败");
        }
        return data;
    }

    /**
     * 修改推荐人返利方案
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("updatePlanReturn.do")
    @ResponseBody
    public Object updatePlanReturn(HttpServletRequest request) {
        final String editReturnId = request.getParameter("editReturnId");
        final String editReturnName = request.getParameter("editReturnName");
        final String editReturnRate = request.getParameter("editReturnRate");

        final String user_id = request.getSession().getAttribute("user_id").toString();
        final JsonResult data = new JsonResult(false);
        controllerTemplate.execute(data, new ControllerCallBack() {

            @Override
            public void executeService() {
                int result = planReturnDAO.update(editReturnName,
                    Double.parseDouble(editReturnRate) / 100, user_id, editReturnId);
                data.setSuccess(result > 0 ? true : false);
                data.setMessage(result > 0 ? "操作成功" : "操作失败");
            }

            @Override
            public void check() {
                AssertUtil.notBlank(editReturnId, "方案编号不能为空");
                AssertUtil.notBlank(editReturnRate, "比例不能为空");
                AssertUtil.state(NumUtils.checkeRate(editReturnRate), "请填写正确的百分比值");
            }
        });
        return data;
    }

    /**
     * 删除返利方案
     * 
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/deletePlanReturn.do")
    @ResponseBody
    public Object deletePlanReturn(HttpServletRequest request, final String returnId) {
        final JsonResult data = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();
        controllerTemplate.execute(data, new ControllerCallBack() {

            @Override
            public void executeService() {
                int result = planReturnDAO.delete(user_id, returnId);
                data.setSuccess(result > 0 ? true : false);
                data.setMessage(result > 0 ? "删除成功" : "删除失败");
            }

            @Override
            public void check() {
                AssertUtil.notBlank(returnId, "id不能为空");
            }

        });
        return data;
    }

    /**
     * 添加个人返利方案
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("addPlanReturnForSelf.do")
    @ResponseBody
    public Object addPlanReturnForSelf(final HttpServletRequest request) {

        final String returnName = request.getParameter("addReturnName");
        final String returnSubType = request.getParameter("addReturnSubType");
        final String returnValue = request.getParameter("addReturnValue");
        final String endTime = request.getParameter("addEndTime");
        final String startTime = request.getParameter("addStartTime");
        final String user_id = request.getSession().getAttribute("user_id").toString();

        final JsonResult retult = new JsonResult(false);

        controllerTemplate.execute(retult, new ControllerCallBack() {

            @Override
            public void executeService() {

                StockReturnTypeEnum stockReturnTypeEnum = StockReturnTypeEnum
                    .getByCode(returnSubType);
                if (null == stockReturnTypeEnum)
                    throw new BaseRuntimeException("返利类型枚举错误");

                if (stockReturnTypeEnum == StockReturnTypeEnum.VALUE
                    || stockReturnTypeEnum == StockReturnTypeEnum.RETE) {
                    if (StringUtils.isBlank(returnValue))
                        throw new BaseRuntimeException("请填写返利  积分值  或者  百分比");
                }

                PlanReturnDO planReturnDO = new PlanReturnDO();
                planReturnDO.setReturnId(codeGenerateComponent
                    .nextCodeByType(BizTypeEnum.RETURN_ID));
                planReturnDO.setReturnName(returnName);
                planReturnDO.setReturnType(PlanReturnTypeEnum.SELF.getCode());
                planReturnDO.setReturnSubType(returnSubType);

                if (StringUtils.equals(returnSubType, StockReturnTypeEnum.RETE.getCode())) {
                    String thisValue = String.valueOf(Double.parseDouble(returnValue) / 100);
                    planReturnDO.setReturnValue(thisValue);
                } else {
                    planReturnDO.setReturnValue(returnValue);
                }

                planReturnDO.setEndTime(DateUtil.stringToDate(endTime, DateUtil.webFormat));
                planReturnDO.setStartTime(DateUtil.stringToDate(startTime, DateUtil.webFormat));
                planReturnDO.setDelFlg(DelFlgEnum.NOT_DEL.getCode());
                planReturnDO.setCreater(user_id);
                int insert = planReturnDAO.insert(planReturnDO);
                if (insert > 0) {
                    retult.setSuccess(true);
                }
            }

            @Override
            public void check() {
                AssertUtil.notBlank(returnName, "请填写返利名称");
                AssertUtil.notBlank(returnSubType, "返利类型不能为空");
                AssertUtil.notBlank(endTime, "请选择结束日期");
                if (StringUtils.equals(returnSubType, StockReturnTypeEnum.RETE.getCode())) {
                    AssertUtil.state(NumUtils.checkeRate(returnValue), "请填写正确的百分比值");
                }
            }

        });
        return retult;
    }

    /**
     * 修改个人返利方案
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("editPlanReturnForSelf.do")
    @ResponseBody
    public Object editPlanReturnForSelf(final HttpServletRequest request) {

        final String returnName = request.getParameter("editReturnName");

        final String returnSubType = request.getParameter("editReturnSubType");
        final String returnValue = request.getParameter("editReturnValue");
        final String endTime = request.getParameter("editEndTime");
        final String startTime = request.getParameter("editStartTime");
        final String returnId = request.getParameter("editReturnId");

        final String user_id = request.getSession().getAttribute("user_id").toString();

        final JsonResult retult = new JsonResult(false);

        controllerTemplate.execute(retult, new ControllerCallBack() {

            @Override
            public void executeService() {

                PlanReturnDO planReturnDO = planReturnDAO.queryByReturnId(returnId);
                if (null == planReturnDO)
                    throw new BaseRuntimeException("返利信息查询失败");

                StockReturnTypeEnum stockReturnTypeEnum = StockReturnTypeEnum
                    .getByCode(returnSubType);
                if (null == stockReturnTypeEnum)
                    throw new BaseRuntimeException("返利类型枚举错误");

                if (stockReturnTypeEnum == StockReturnTypeEnum.VALUE
                    || stockReturnTypeEnum == StockReturnTypeEnum.RETE) {
                    if (StringUtils.isBlank(returnValue))
                        throw new BaseRuntimeException("请填写返利  积分值  或者  百分比");
                }

                if (stockReturnTypeEnum == StockReturnTypeEnum.NOTHING) {
                    planReturnDO.setReturnValue("0");
                }

                if (StringUtils.equals(returnSubType, StockReturnTypeEnum.RETE.getCode())) {
                    String thisValue = String.valueOf(Double.parseDouble(returnValue) / 100);
                    planReturnDO.setReturnValue(thisValue);
                } else {
                    planReturnDO.setReturnValue(returnValue);
                }

                planReturnDO.setReturnName(returnName);
                planReturnDO.setReturnSubType(returnSubType);
                planReturnDO.setEndTime(DateUtil.stringToDate(endTime, DateUtil.webFormat));
                planReturnDO.setStartTime(DateUtil.stringToDate(startTime, DateUtil.webFormat));
                planReturnDO.setModifier(user_id);

                int updateReturnSelf = planReturnDAO.updateReturnSelf(planReturnDO);
                if (updateReturnSelf > 0) {
                    retult.setSuccess(true);
                }
            }

            @Override
            public void check() {
                AssertUtil.notBlank(returnId, "返利信息为空");
                AssertUtil.notBlank(returnName, "请填写返利名称");
                AssertUtil.notBlank(returnSubType, "返利类型不能为空");
                AssertUtil.notBlank(endTime, "请选择结束日期");
                if (StringUtils.equals(returnSubType, StockReturnTypeEnum.RETE.getCode())) {
                    AssertUtil.state(NumUtils.checkeRate(returnValue), "请填写正确的百分比值");
                }
            }

        });
        return retult;
    }

    /**
     * 选择关联商品
     * 
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/returnPlanChooseGood.do")
    @ResponseBody
    public Object returnPlanChooseGood(final HttpServletRequest request, final String returnId,
                                       final String productId) {

        final JsonResult json = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();

        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                PlanReturnDO planReturnDO = planReturnDAO.queryByReturnId(returnId);
                if (null == planReturnDO)
                    throw new BaseRuntimeException("返利信息查询失败");

                String linkPro = planReturnDO.getLinkPro();

                if (StringUtils.contains(linkPro, productId))
                    return;

                List<String> parseArray = JSONArray.parseArray(linkPro, String.class);
                if (CollectionUtils.isEmpty(parseArray)) {
                    parseArray = new ArrayList<String>();
                }
                parseArray.add(productId);

                JSONArray array = JSONArray.parseArray(JSON.toJSONString(parseArray));

                int modifyLinkPro = planReturnDAO.updateReturnSelfLinkPro(array.toString(),
                    user_id, returnId);
                if (modifyLinkPro > 0)
                    json.setSuccess(true);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(returnId, "返利信息为空");
            }

        });
        return json;
    }

    /**
     * 取消选择
     * 
     * @param request
     * @param bannerId
     * @param productId
     * @return
     */
    @RequestMapping("/returnPlanUnChooseGood.do")
    @ResponseBody
    public Object returnPlanUnChooseGood(final HttpServletRequest request, final String returnId,
                                         final String productId) {

        final JsonResult json = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();

        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                PlanReturnDO planReturnDO = planReturnDAO.queryByReturnId(returnId);
                if (null == planReturnDO)
                    throw new BaseRuntimeException("返利信息查询失败");

                String linkPro = planReturnDO.getLinkPro();

                if (!StringUtils.contains(linkPro, productId))
                    return;

                List<String> parseArray = JSONArray.parseArray(linkPro, String.class);
                parseArray.remove(productId);

                JSONArray array = JSONArray.parseArray(JSON.toJSONString(parseArray));

                int modifyLinkPro = planReturnDAO.updateReturnSelfLinkPro(array.toString(),
                    user_id, returnId);
                if (modifyLinkPro > 0)
                    json.setSuccess(true);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(returnId, "返利信息为空");
            }

        });
        return json;
    }

    @RequestMapping("returnPlanChooseGoodAll.do")
    @ResponseBody
    public Object returnPlanChooseGoodAll(final HttpServletRequest request, final String returnId,
                                          final String[] productIdS) {

        final JsonResult json = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();

        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                PlanReturnDO planReturnDO = planReturnDAO.queryByReturnId(returnId);
                if (null == planReturnDO)
                    throw new BaseRuntimeException("返利信息查询失败");

                String linkPro = planReturnDO.getLinkPro();
                List<String> parseArray = JSONArray.parseArray(linkPro, String.class);
                if (CollectionUtils.isEmpty(parseArray)) {
                    parseArray = new ArrayList<String>();
                }

                for (String productId : productIdS) {
                    if (StringUtils.contains(linkPro, productId))
                        continue;
                    parseArray.add(productId);
                }

                JSONArray array = JSONArray.parseArray(JSON.toJSONString(parseArray));

                int modifyLinkPro = planReturnDAO.updateReturnSelfLinkPro(array.toString(),
                    user_id, returnId);
                if (modifyLinkPro > 0)
                    json.setSuccess(true);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(returnId, "返利信息为空");
            }

        });
        return json;
    }

    @RequestMapping("/returnPlanUnChooseGoodAll.do")
    @ResponseBody
    public Object returnPlanUnChooseGoodAll(final HttpServletRequest request,
                                            final String returnId, final String[] productIdS) {

        final JsonResult json = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();

        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                PlanReturnDO planReturnDO = planReturnDAO.queryByReturnId(returnId);
                if (null == planReturnDO)
                    throw new BaseRuntimeException("返利信息查询失败");

                String linkPro = planReturnDO.getLinkPro();
                List<String> parseArray = JSONArray.parseArray(linkPro, String.class);
                if (CollectionUtils.isEmpty(parseArray)) {
                    parseArray = new ArrayList<String>();
                }

                for (String productId : productIdS) {
                    if (!StringUtils.contains(linkPro, productId))
                        continue;
                    parseArray.remove(productId);
                }

                JSONArray array = JSONArray.parseArray(JSON.toJSONString(parseArray));

                int modifyLinkPro = planReturnDAO.updateReturnSelfLinkPro(array.toString(),
                    user_id, returnId);
                if (modifyLinkPro > 0)
                    json.setSuccess(true);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(returnId, "返利信息为空");
            }

        });
        return json;
    }

    @RequestMapping("/selectGoodsForReturnSelf.do")
    @ResponseBody
    public JSONObject selectuser(HttpServletRequest request, Integer offset, Integer limit) {
        String productId = request.getParameter("productId");
        String proType = request.getParameter("proType");
        String teamId = request.getParameter("linkTeam");
        String teamName = request.getParameter("teamName");
        String productName = request.getParameter("productName");
        String onSale = request.getParameter("onSale");
        String canBuy = request.getParameter("canBuy");
        String licenseNo = request.getParameter("licenseNo");
        String isDelete = request.getParameter("isDelete");
        String goodErpNo = request.getParameter("goodErpNo");
        String barcode = request.getParameter("goodbarCode");//条形码
        String hasStockPrice = request.getParameter("hasStockPrice");//是否有属性  0无 1有

        JSONObject data = new JSONObject();

        String returnId = request.getParameter("returnId");

        PlanReturnDO planReturnDO = null;
        if (StringUtils.isNotBlank(returnId))
            planReturnDO = planReturnDAO.queryByReturnId(returnId);

        List<Map<String, Object>> goodsList = productDAO.queryGoods(productId, proType, teamId,
            teamName, productName, onSale, canBuy, licenseNo, isDelete, goodErpNo, null, null,
            null, barcode, hasStockPrice, offset, limit);
        for (Map<String, Object> map : goodsList) {
            String thisProductId = (String) map.get("productId");
            if (null != planReturnDO
                && StringUtils.contains(planReturnDO.getLinkPro(), thisProductId))
                map.put("hasChoose", true);
        }

        int total = productDAO.selectProductPageCount(productId, proType, teamId, teamName,
            productName, onSale, canBuy, licenseNo, isDelete, goodErpNo, null, null, null, barcode,
            hasStockPrice);
        data.put("rows", goodsList);
        data.put("total", total);
        return data;
    }

    @RequestMapping("/selectGoodsAttrForReturnSelf.do")
    @ResponseBody
    public JSONObject selectGoodsAttrForReturnSelf(HttpServletRequest request, Integer offset,
                                                   Integer limit) {

        String productId = request.getParameter("productId");

        JSONObject data = new JSONObject();

        String returnId = request.getParameter("returnId");

        PlanReturnDO planReturnDO = null;
        if (StringUtils.isNotBlank(returnId))
            planReturnDO = planReturnDAO.queryByReturnId(returnId);

        List<Map<String, Object>> queryProStockPrice = stockPriceDAO.queryProStockPrice(productId,
            DelFlgEnum.NOT_DEL.getCode(), "2", offset, limit);

        for (Map<String, Object> map : queryProStockPrice) {

            Money price = BigdecimalUtil.toMoney((BigDecimal) map.get("price"));
            map.put("price", price);
            Money goodAmount = BigdecimalUtil.toMoney((BigDecimal) map.get("goodAmount"));
            map.put("goodAmount", goodAmount);
            Money point = BigdecimalUtil.toMoney((BigDecimal) map.get("point"));
            map.put("point", point);

            String thisProductId = MapUtils.getString(map, "goodsNo");
            int thisStockId = MapUtils.getInteger(map, ("id"));
            map.put("stockId", thisStockId);

            map.put("hasChoose", false);
            if (null != planReturnDO) {
                if (StringUtils.contains(planReturnDO.getLinkPro(), thisProductId)) {
                    String linkProStock = planReturnDO.getLinkProStock();
                    if (StringUtils.isNotBlank(linkProStock)) {
                        List<LinkProStock> linkProStockS = JSONArray.parseArray(linkProStock,
                            LinkProStock.class);
                        for (LinkProStock linkProStockObj : linkProStockS) {
                            if (StringUtils
                                .notEquals(linkProStockObj.getProductId(), thisProductId))
                                continue;

                            List<Integer> stockId = linkProStockObj.getStockId();
                            for (Integer thisId : stockId) {
                                if (thisStockId == thisId) {
                                    map.put("hasChoose", true);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        int total = stockPriceDAO.queryProStockPriceCount(productId, DelFlgEnum.NOT_DEL.getCode(),
            "2");
        data.put("rows", queryProStockPrice);
        data.put("total", total);
        return data;
    }

    /**
     * 选择关联商品属性
     * 
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/returnPlanChooseGoodAttr.do")
    @ResponseBody
    public Object returnPlanChooseGoodAttr(final HttpServletRequest request, final String returnId,
                                           final String productId, final int chooseId) {

        final JsonResult json = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();

        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                PlanReturnDO planReturnDO = planReturnDAO.queryByReturnId(returnId);
                if (null == planReturnDO)
                    throw new BaseRuntimeException("返利信息查询失败");

                String linkPro = planReturnDO.getLinkPro();
                String linkProStock = planReturnDO.getLinkProStock();

                //【1】判断是否加入过商品关联
                List<String> linkProIdS = JSONArray.parseArray(linkPro, String.class);
                if (CollectionUtils.isEmpty(linkProIdS)) {
                    linkProIdS = new ArrayList<String>();
                }
                if (!linkProIdS.contains(productId))
                    linkProIdS.add(productId);
                //商品
                JSONArray linkProIdSArray = JSONArray.parseArray(JSON.toJSONString(linkProIdS));

                List<LinkProStock> linkProAttrS = JSONArray.parseArray(linkProStock,
                    LinkProStock.class);
                if (CollectionUtils.isEmpty(linkProAttrS)) {
                    linkProAttrS = new ArrayList<LinkProStock>();
                    LinkProStock proStock = new LinkProStock();
                    proStock.setProductId(productId);
                    List<Integer> sIds = new ArrayList<Integer>();
                    sIds.add(chooseId);
                    proStock.setStockId(sIds);
                    linkProAttrS.add(proStock);
                } else {
                    //【2】判断是否已经加入过该属性
                    Map<String, LinkProStock> linkProStocktoMapObj = ListToMap
                        .linkProStocktoMapObj(linkProAttrS);
                    LinkProStock checkLinkProStock = linkProStocktoMapObj.get(productId);
                    if (null == checkLinkProStock) {
                        LinkProStock proStock = new LinkProStock();
                        proStock.setProductId(productId);
                        List<Integer> sIds = new ArrayList<Integer>();
                        sIds.add(chooseId);
                        proStock.setStockId(sIds);
                        linkProAttrS.add(proStock);
                    } else {
                        //先判断是否已经有sId
                        List<Integer> sIds = checkLinkProStock.getStockId();
                        if (sIds.contains(chooseId))
                            return;

                        linkProAttrS.remove(checkLinkProStock);
                        LinkProStock proStock = new LinkProStock();
                        proStock.setProductId(productId);
                        sIds.add(chooseId);
                        proStock.setStockId(sIds);
                        linkProAttrS.add(proStock);
                    }
                }

                //商品 属性
                JSONArray linkProAttrSArray = JSONArray.parseArray(JSON.toJSONString(linkProAttrS));
                int updateReturnSelfLinkProAttr = planReturnDAO.updateReturnSelfLinkProAttr(
                    linkProIdSArray.toString(), linkProAttrSArray.toString(), user_id, returnId);

                if (updateReturnSelfLinkProAttr > 0)
                    json.setSuccess(true);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(returnId, "返利信息为空");
            }

        });
        return json;
    }

    /**
     * 取消选择
     * 
     * @param request
     * @param bannerId
     * @param productId
     * @return
     */
    @RequestMapping("/returnPlanUnChooseGoodAttr.do")
    @ResponseBody
    public Object returnPlanUnChooseGoodAttr(final HttpServletRequest request,
                                             final String returnId, final String productId,
                                             final int chooseId) {

        final JsonResult json = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();

        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                PlanReturnDO planReturnDO = planReturnDAO.queryByReturnId(returnId);
                if (null == planReturnDO)
                    throw new BaseRuntimeException("返利信息查询失败");

                String linkPro = planReturnDO.getLinkPro();
                String linkProStock = planReturnDO.getLinkProStock();

                //【1】判断是否加入过商品关联
                List<String> linkProIdS = JSONArray.parseArray(linkPro, String.class);
                if (CollectionUtils.isEmpty(linkProIdS)) {
                    linkProIdS = new ArrayList<String>();
                }

                List<LinkProStock> linkProAttrS = JSONArray.parseArray(linkProStock,
                    LinkProStock.class);
                if (CollectionUtils.isEmpty(linkProAttrS)) {
                    return;
                } else {
                    //【2】判断是否已经加入过该属性
                    Map<String, LinkProStock> linkProStocktoMapObj = ListToMap
                        .linkProStocktoMapObj(linkProAttrS);
                    LinkProStock checkLinkProStock = linkProStocktoMapObj.get(productId);
                    if (null == checkLinkProStock) {
                        LinkProStock proStock = new LinkProStock();
                        proStock.setProductId(productId);
                        List<Integer> sIds = new ArrayList<Integer>();
                        sIds.add(chooseId);
                        proStock.setStockId(sIds);
                        linkProAttrS.add(proStock);
                    } else {
                        //先判断是否已经有sId
                        List<Integer> sIds = checkLinkProStock.getStockId();
                        if (!sIds.contains(chooseId))
                            return;

                        sIds.remove(sIds.indexOf(chooseId));

                        linkProAttrS.remove(checkLinkProStock);
                        LinkProStock proStock = new LinkProStock();
                        proStock.setProductId(productId);
                        proStock.setStockId(sIds);
                        if (CollectionUtils.isNotEmpty(sIds))
                            linkProAttrS.add(proStock);
                    }
                }

                //商品
                JSONArray linkProIdSArray = JSONArray.parseArray(JSON.toJSONString(linkProIdS));

                //商品 属性
                JSONArray linkProAttrSArray = JSONArray.parseArray(JSON.toJSONString(linkProAttrS));
                int updateReturnSelfLinkProAttr = planReturnDAO.updateReturnSelfLinkProAttr(
                    linkProIdSArray.toString(), linkProAttrSArray.toString(), user_id, returnId);

                if (updateReturnSelfLinkProAttr > 0)
                    json.setSuccess(true);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(returnId, "返利信息为空");
            }

        });
        return json;
    }

    //    @RequestMapping("returnPlanChooseGoodAttrAll.do")
    //    @ResponseBody
    //    public Object returnPlanChooseGoodAttrAll(final HttpServletRequest request, final String returnId,
    //                                          final String[] productIdS) {
    //
    //        final JsonResult json = new JsonResult(false);
    //        final String user_id = request.getSession().getAttribute("user_id").toString();
    //
    //        controllerTemplate.execute(json, new ControllerCallBack() {
    //
    //            @Override
    //            public void executeService() {
    //                
    //            }
    //
    //            @Override
    //            public void check() {
    //                AssertUtil.notBlank(returnId, "返利信息为空");
    //            }
    //
    //        });
    //        return json;
    //    }
    //
    //    @RequestMapping("/returnPlanUnChooseGoodAttrAll.do")
    //    @ResponseBody
    //    public Object returnPlanUnChooseGoodAttrAll(final HttpServletRequest request,
    //                                            final String returnId, final String[] productIdS) {
    //
    //        final JsonResult json = new JsonResult(false);
    //        final String user_id = request.getSession().getAttribute("user_id").toString();
    //
    //        controllerTemplate.execute(json, new ControllerCallBack() {
    //
    //            @Override
    //            public void executeService() {
    //                
    //            }
    //
    //            @Override
    //            public void check() {
    //                AssertUtil.notBlank(returnId, "返利信息为空");
    //            }
    //
    //        });
    //        return json;
    //    }

}