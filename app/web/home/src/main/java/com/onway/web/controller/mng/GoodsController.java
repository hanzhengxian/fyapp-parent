package com.onway.web.controller.mng;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.onway.common.lang.CollectionUtils;
import com.onway.common.lang.Money;
import com.onway.common.lang.StringUtils;
import com.onway.fyapp.common.dal.dataobject.CategoryDO;
import com.onway.fyapp.common.dal.dataobject.GoodsAttrDO;
import com.onway.fyapp.common.dal.dataobject.ImgDO;
import com.onway.fyapp.common.dal.dataobject.ProductCategoryDO;
import com.onway.fyapp.common.dal.dataobject.ProductDO;
import com.onway.fyapp.common.dal.dataobject.ProductTeamDO;
import com.onway.fyapp.common.dal.dataobject.ProductTypeDO;
import com.onway.fyapp.common.dal.dataobject.StockPriceDO;
import com.onway.fyapp.common.dal.dataobject.StockPriceTeamDO;
import com.onway.fyapp.common.dal.dataobject.StockPriceTeamMiddleDO;
import com.onway.fyapp.common.dal.dataobject.TeamDO;
import com.onway.model.GoodsAttrAndValues;
import com.onway.model.enums.BizTypeEnum;
import com.onway.model.enums.DelFlgEnum;
import com.onway.model.enums.ImgTypeEnum;
import com.onway.model.enums.OnShellEnum;
import com.onway.model.enums.ProPayTypeEnum;
import com.onway.model.enums.StatusEnum;
import com.onway.platform.common.configration.ConfigrationFactory;
import com.onway.platform.common.exception.BaseRuntimeException;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.result.JsonQueryResult;
import com.onway.result.JsonResult;
import com.onway.utils.BigdecimalUtil;
import com.onway.utils.NumUtils;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;

@Controller
public class GoodsController extends AbstractController {

    private static final String IMAGE_FILE = ConfigrationFactory.getConfigration()
                                               .getPropertyValue("user_img_upload_realpath");

    private static final String IMAGE_PATH = ConfigrationFactory.getConfigration()
                                               .getPropertyValue("user_img_path");

    /**
     * 分页查询商品信息
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/selectGoods.do")
    @ResponseBody
    public JSONObject selectuser(HttpServletRequest request, Integer offset, Integer limit) {
        String productId = request.getParameter("productId");
        String proType = request.getParameter("proType");
        String teamId = request.getParameter("teamId");
        String teamName = request.getParameter("teamName");
        String productName = request.getParameter("productName");
        String onSale = request.getParameter("onSale");
        String canBuy = request.getParameter("canBuy");
        String licenseNo = request.getParameter("licenseNo");
        String isDelete = request.getParameter("isDelete");
        String goodErpNo = request.getParameter("goodErpNo");
        String freeLuggage = request.getParameter("freeLuggage");
        String barcode = request.getParameter("goodbarCode");//条形码
        String hasStockPrice = request.getParameter("hasStockPrice");//是否有属性  0无 1有

        JSONObject data = new JSONObject();

        int total = productDAO.selectProductPageCount(productId, proType, teamId, teamName,
            productName, onSale, canBuy, licenseNo, isDelete, goodErpNo, null, null, freeLuggage,
            barcode, hasStockPrice);

        List<Map<String, Object>> goodsList = productDAO.queryGoods(productId, proType, teamId,
            teamName, productName, onSale, canBuy, licenseNo, isDelete, goodErpNo, null, null,
            freeLuggage, barcode, hasStockPrice, offset, limit);

        data.put("rows", goodsList);
        data.put("total", total);
        return data;
    }

    /**
     * 查询商品 图片信息
     * 
     * @param request
     *            1 banner 2 详情 3 列表
     * @return
     */
    @RequestMapping("/selectGoodsImg.do")
    @ResponseBody
    public Object selectGoodsImg(HttpServletRequest request) {

        String productId = request.getParameter("productId");
        String imgFlg = request.getParameter("imgFlg");

        JsonQueryResult<List<String>> result = new JsonQueryResult<List<String>>(true);

        ImgDO imgDO = imgDAO.queryImgByProductIdAndType(productId, null, imgFlg,
            DelFlgEnum.NOT_DEL.getCode());

        List<String> list = new ArrayList<String>();
        if (null != imgDO && StringUtils.isNotBlank(imgDO.getImgUrl())) {
            if (!StringUtils.equals(imgFlg, ImgTypeEnum.DETAILS_IMG.getCode())) {
                JSONArray parseArray = JSONArray.parseArray(imgDO.getImgUrl());
                for (Object object : parseArray) {
                    list.add(object.toString());
                }
            } else {
                list.add(imgDO.getImgUrl());
            }
        }

        result.setObj(list);
        return result;
    }

    /**
     * 查询商品 属性库存 。。。
     * 
     * @param request
     * @return
     */
    @RequestMapping("/selectGoodsStockPrice.do")
    @ResponseBody
    public Object selectGoodsStockPrice(HttpServletRequest request, Integer offset, Integer limit) {

        String productId = request.getParameter("productId");

        JSONObject data = new JSONObject();

        List<Map<String, Object>> queryProStockPrice = stockPriceDAO.queryProStockPrice(productId,
            DelFlgEnum.NOT_DEL.getCode(), null, offset, limit);

        ProductDO productDO = productDAO.selectByProdId(productId);
        String onSale = productDO.getOnSale();

        for (Map<String, Object> map : queryProStockPrice) {
            Money price = BigdecimalUtil.toMoney((BigDecimal) map.get("price"));
            map.put("price", price);
            Money goodAmount = BigdecimalUtil.toMoney((BigDecimal) map.get("goodAmount"));
            map.put("goodAmount", goodAmount);
            Money point = BigdecimalUtil.toMoney((BigDecimal) map.get("point"));
            map.put("point", point);

            Double pointRate = MapUtils.getDouble(map, "pointRate");
            map.put("pointRate", pointRate * 100);

            String payType = MapUtils.getString(map, "payType");
            ProPayTypeEnum payTypeEnum = ProPayTypeEnum.getByCode(payType);
            if (null != payTypeEnum)
                map.put("payTypeStr", payTypeEnum.message());

            map.put("onSale", onSale);
            map.put("productName", productDO.getProductName());
            map.put("proName", productDO.getProName());
        }

        data.put("rows", queryProStockPrice);
        data.put("total",
            stockPriceDAO.queryProStockPriceCount(productId, DelFlgEnum.NOT_DEL.getCode(), null));
        return data;
    }

    // 初始化商品添加页面
    @RequestMapping("initAddPdPage.do")
    @ResponseBody
    public Object initAddPdPage(final HttpServletRequest request) {

        final JsonQueryResult<JSONObject> result = new JsonQueryResult<JSONObject>(true);
        final String prodId = request.getParameter("prodId");
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {
                JSONObject data = new JSONObject();

                //topId为0 的数据
                List<CategoryDO> topCats = categoryDAO.selectByTopId(0, null, null);
                if (topCats.size() == 2) {
                    // 查询所有商品分类
                    List<CategoryDO> babaCateList = categoryDAO.selectByTopId(topCats.get(0)
                        .getId(), null, null);
                    // 查询所有功能分类
                    List<CategoryDO> babaEffectCateList = categoryDAO.selectByTopId(topCats.get(1)
                        .getId(), null, null);
                    data.put("babaCateList", babaCateList);
                    data.put("babaEffectCateList", babaEffectCateList);
                    data.put("babaCateName", topCats.get(0).getCategoryName());
                    data.put("babaEffectCateName", topCats.get(1).getCategoryName());
                }
                if (topCats.size() == 1) {
                    // 查询所有商品分类
                    List<CategoryDO> babaCateList = categoryDAO.selectByTopId(topCats.get(0)
                        .getId(), null, null);
                    // 查询所有功能分类
                    List<CategoryDO> babaEffectCateList = new ArrayList<CategoryDO>();
                    data.put("babaCateList", babaCateList);
                    data.put("babaEffectCateList", babaEffectCateList);
                    data.put("babaCateName", topCats.get(0).getCategoryName());
                    data.put("babaEffectCateName", null);
                }

                if (StringUtils.hasLength(prodId)) {
                    ProductDO productDO = productDAO.selectByProdId(prodId);
                    data.put("prodInfo", productDO);
                    ImgDO goodsImg = imgDAO.queryImgByProductIdAndType(prodId, null,
                        ImgTypeEnum.HEAD_IMG.getCode(), DelFlgEnum.NOT_DEL.getCode());

                    List<String> list = null;
                    if (null != goodsImg && StringUtils.isNotBlank(goodsImg.getImgUrl())) {
                        list = new ArrayList<String>();
                        JSONArray parseArray = JSONArray.parseArray(goodsImg.getImgUrl());
                        for (Object object : parseArray) {
                            list.add(object.toString());
                        }
                        data.put("goodsImg", list);
                    }

                    ImgDO listImg = imgDAO.queryImgByProductIdAndType(prodId, null,
                        ImgTypeEnum.LIST_IMG.getCode(), DelFlgEnum.NOT_DEL.getCode());

                    if (null != listImg && StringUtils.isNotBlank(listImg.getImgUrl())) {
                        list = new ArrayList<String>();
                        JSONArray parseArray = JSONArray.parseArray(listImg.getImgUrl());
                        for (Object object : parseArray) {
                            list.add(object.toString());
                        }
                        data.put("listImg", list);
                    }

                    ImgDO detail = imgDAO.queryImgByProductIdAndType(prodId, null,
                        ImgTypeEnum.DETAILS_IMG.getCode(), DelFlgEnum.NOT_DEL.getCode());
                    if (null != detail && StringUtils.isNotBlank(detail.getImgUrl())) {
                        data.put("detail", detail.getImgUrl());
                    }

                    List<String> groupId = productCategoryDAO.selectProCateGroupByGroupId(prodId);
                    if (CollectionUtils.isEmpty(groupId)) {
                        data.put("hasCats", false);
                    } else {
                        data.put("hasCats", true);
                        data.put("proCateSize", groupId.size());

                        Map<String, List<CategoryDO>> cateList = new HashMap<String, List<CategoryDO>>();
                        for (String id : groupId) {
                            List<ProductCategoryDO> proCate = productCategoryDAO.selectProCate(
                                prodId, id);
                            List<CategoryDO> categoryDOs = new ArrayList<CategoryDO>();
                            for (ProductCategoryDO productCategoryDO : proCate) {
                                String categoryId = productCategoryDO.getCategoryId();
                                categoryDOs.add(categoryDAO.selectById(Integer.valueOf(categoryId)));
                            }
                            cateList.put(id, categoryDOs);
                        }
                        data.put("cateList", cateList);
                    }
                }
                result.setObj(data);
            }

            @Override
            public void check() {
            }
        });

        return result;
    }

    @RequestMapping("selectCateByTopId.do")
    @ResponseBody
    public Object selectCateByTopId(final HttpServletRequest request) {

        final JsonQueryResult<JSONObject> result = new JsonQueryResult<JSONObject>(true);
        final String topId = request.getParameter("topId");
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {
                JSONObject data = new JSONObject();

                List<CategoryDO> childList = categoryDAO.selectByTopId(Integer.valueOf(topId),
                    null, null);

                data.put("childList", childList);
                result.setObj(data);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(topId, "属性id不能为空");
            }
        });

        return result;
    }

    /**
     * 编辑商品和新增
     * 
     * @param request
     * @return
     */
    @RequestMapping("editGoods.do")
    @ResponseBody
    public Object editGoods(final HttpServletRequest request) {
        final JsonResult result = new JsonResult(true);
        final String prodId = request.getParameter("prodId");
        final String productName = request.getParameter("productName");
        final String proName = request.getParameter("proName");
        final String showOld = request.getParameter("showOld");
        final String productArea = request.getParameter("productArea");
        final String brand = request.getParameter("brand");
        final String manufacturer = request.getParameter("manufacturer");
        final String licenseNo = request.getParameter("licenseNo");
        final String type = request.getParameter("type");
        final String fatherCate = request.getParameter("fatherCate");
        final String childCate = request.getParameter("childCate");
        final String fatherEfectCate = request.getParameter("fatherEfectCate");
        final String childEfectCate = request.getParameter("childEfectCate");
        final String delFlg = request.getParameter("delFlg");
        final String proType = request.getParameter("proType");
        final String canBuy = request.getParameter("canBuy");
        final String isNew = request.getParameter("isNew");
        final String userLevel = request.getParameter("userLevel");
        final String erpNo = request.getParameter("erpNo");
        final String html = request.getParameter("html");
        final String listFile = request.getParameter("listFile");
        final String goodsFile = request.getParameter("goodsFile");
        final String pickType = request.getParameter("pickType");
        final String maxDiscount = request.getParameter("maxDiscount");
        final String unit = request.getParameter("unit");
        final String resourceArea = request.getParameter("resourceArea");
        final String keepDate = request.getParameter("keepDate");
        final String keepWay = request.getParameter("keepWay");
        final String attentionMemo = request.getParameter("attentionMemo");
        final String specification = request.getParameter("specification");
        final String rankNum = request.getParameter("rankNum");

        final String businessCategory = request.getParameter("businessCategory");

        final String freeLuggage = request.getParameter("freeLuggage");

        final String[] addFirstCate = request.getParameterValues("addFirstCate");
        final String[] addSecondCate = request.getParameterValues("addSecondCate");
        final String[] addThirdCate = request.getParameterValues("addThirdCate");

        final String user_id = request.getSession().getAttribute("user_id").toString();
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {
                ProductDO product = new ProductDO();
                product.setProductName(productName);
                product.setProName(proName);
                //助记码
                String proZjm = zjmComponent.getZjm(productName);
                product.setProZjm(proZjm);
                product.setShowOld(showOld);
                product.setProductArea(productArea);
                product.setBrand(brand);
                product.setManufacturer(manufacturer);
                product.setLicenseNo(licenseNo);
                product.setType(type);
                product.setFatherCate(fatherCate);
                product.setChildCate(childCate);
                product.setFatherEfectCate(fatherEfectCate);
                product.setChildEfectCate(childEfectCate);
                product.setDelFlg(delFlg);
                product.setProType(proType);
                product.setCanBuy(canBuy);
                product.setErpNo(erpNo);
                product.setIsNew(isNew);
                product.setUnit(unit);
                product.setProductAreaSource(resourceArea);
                product.setKeepDate(keepDate);
                product.setKeepWay(keepWay);
                product.setAttenMemo(attentionMemo);
                product.setSpecification(specification);
                if (StringUtils.isBlank(maxDiscount)) {
                    product.setMaxDiscount(0.00);
                } else {
                    BigDecimal divide = new BigDecimal(maxDiscount).divide(new BigDecimal(100));
                    product.setMaxDiscount(divide.doubleValue());
                }
                product.setPickType(pickType);
                product.setUserLevel(Integer.parseInt(userLevel));
                product.setRankNum(rankNum);
                product.setFreeLuagger(freeLuggage);

                if (StringUtils.isNotBlank(businessCategory)) {
                    String[] split = businessCategory.split(",");
                    List<String> asList = Arrays.asList(split);
                    JSONArray array = JSONArray.parseArray(JSON.toJSONString(asList));
                    product.setBusinessCategory(array.toString());
                } else {
                    product.setBusinessCategory(null);
                }

                if (StringUtils.hasLength(prodId)) {
                    ProductDO existDO = productDAO.selectByProdId(prodId);
                    if (null == existDO) {
                        throw new BaseRuntimeException("记录不存在 ");
                    }
                    if (!StringUtils.equals(existDO.getOnSale(), OnShellEnum.OFF_SHELL.getCode())) {
                        throw new BaseRuntimeException("请先下架商品再修改信息！");
                    }
                    product.setProductId(prodId);
                    product.setModifier(user_id);
                    product.setOnSale(existDO.getOnSale());
                    product.setSales(existDO.getSales());
                    product.setMounthSales(existDO.getMounthSales());
                    productDAO.updateByProdId(product);
                } else {
                    product.setProductId(codeGenerateComponent
                        .nextCodeByType(BizTypeEnum.PRODUCT_NO));
                    product.setCreater(user_id);
                    product.setOnSale(OnShellEnum.OFF_SHELL.getCode());
                    productDAO.insert(product);
                }

                //商品分类处理
                stockPriceComponent.newProCategory(product.getProductId(), addFirstCate,
                    addSecondCate, addThirdCate);

                ImgDO img = null;

                if (StringUtils.hasLength(listFile)) {

                    ImgDO existImg = imgDAO.queryImgByProductIdAndType(product.getProductId(),
                        null, ImgTypeEnum.LIST_IMG.getCode(), null);
                    img = new ImgDO();
                    if (StringUtils.equals(proType, "2")) {
                        img.setImgType("2");
                    } else {
                        img.setImgType("1");
                    }
                    img.setImgId(product.getProductId());
                    img.setImgFlg(ImgTypeEnum.LIST_IMG.getCode());
                    img.setImgUrl(listFile);
                    img.setDelFlg("0");
                    if (null != existImg) {
                        img.setId(existImg.getId());
                        imgDAO.updateById(img);
                    } else {
                        imgDAO.insert(img);

                    }

                }

                if (StringUtils.hasLength(goodsFile)) {
                    ImgDO existImg = imgDAO.queryImgByProductIdAndType(product.getProductId(),
                        null, ImgTypeEnum.HEAD_IMG.getCode(), null);

                    img = new ImgDO();
                    if (StringUtils.equals(proType, "2")) {
                        img.setImgType("2");
                    } else {
                        img.setImgType("1");
                    }
                    img.setImgId(product.getProductId());
                    img.setImgFlg(ImgTypeEnum.HEAD_IMG.getCode());
                    img.setImgUrl(goodsFile);
                    img.setDelFlg("0");
                    if (null != existImg) {
                        img.setId(existImg.getId());
                        imgDAO.updateById(img);
                    } else {
                        imgDAO.insert(img);
                    }

                }

                if (StringUtils.hasLength(html)) {
                    ImgDO existImg = imgDAO.queryImgByProductIdAndType(product.getProductId(),
                        null, ImgTypeEnum.DETAILS_IMG.getCode(), null);

                    img = new ImgDO();
                    if (StringUtils.equals(proType, "2")) {
                        img.setImgType("2");
                    } else {
                        img.setImgType("1");
                    }
                    img.setImgId(product.getProductId());
                    img.setImgFlg(ImgTypeEnum.DETAILS_IMG.getCode());
                    img.setImgUrl(html);
                    img.setDelFlg("0");
                    if (null != existImg) {
                        img.setId(existImg.getId());
                        imgDAO.updateById(img);
                    } else {
                        imgDAO.insert(img);
                    }
                }

                result.setMessage("操作成功");
            }

            @Override
            public void check() {
                AssertUtil.notBlank(productName, "商品名称不能为空");
                AssertUtil.notBlank(proType, "商品类型不能为空");
                AssertUtil.notBlank(delFlg, "删除标志不能为空");
                AssertUtil.notBlank(canBuy, "可购买人群不能为空");
            }
        });

        return result;
    }

    /**
     * 商品属性
     * 
     * @param request
     * @return
     */
    @RequestMapping("selectAllGoodAttr.do")
    @ResponseBody
    public Object selectAllGoodAttr(final HttpServletRequest request) {
        final JsonQueryResult<JSONObject> result = new JsonQueryResult<JSONObject>(true);
        final String prodId = request.getParameter("prodId");
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {
                JSONObject jsonObject = new JSONObject();

                if (!StringUtils.hasLength(prodId)) {
                    List<GoodsAttrDO> attrList = goodsAttrDAO.selectAll();
                    jsonObject.put("attrList", attrList);
                } else {
                    List<StockPriceDO> stockList = stockPriceDAO.queryProStockPriceNotMap(prodId,
                        DelFlgEnum.NOT_DEL.getCode());
                    if (CollectionUtils.isNotEmpty(stockList)) {
                        jsonObject.put("stockPrice", stockList.get(0));

                    }

                }
                result.setObj(jsonObject);

            }

            @Override
            public void check() {
            }
        });
        return result;
    }

    /**
     * 查询属性值
     * 
     * @param request
     * @return
     */
    @RequestMapping("selectAttrValueByInfo.do")
    @ResponseBody
    public Object selectAttrValueByInfo(final HttpServletRequest request) {
        final JsonQueryResult<List<GoodsAttrAndValues>> result = new JsonQueryResult<List<GoodsAttrAndValues>>(
            true);
        final String attrIds = request.getParameter("attrIds");
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {
                JSONArray parseArray = JSONArray.parseArray(attrIds);
                List<GoodsAttrAndValues> attrList = new ArrayList<GoodsAttrAndValues>();
                GoodsAttrAndValues attrAndValues = null;
                for (Object object : parseArray) {
                    attrAndValues = new GoodsAttrAndValues();
                    GoodsAttrDO goodsAttrDO = goodsAttrDAO.selectById(Integer.valueOf(object
                        .toString()));
                    attrAndValues.setAttrId(goodsAttrDO.getId());
                    attrAndValues.setAttrName(goodsAttrDO.getAttrName());
                    attrAndValues.setValueList(goodsAttrValueDAO.selectAttrByAttrNoAndSta(
                        String.valueOf(goodsAttrDO.getId()), StatusEnum.ENABLED.getCode()));
                    attrList.add(attrAndValues);

                }
                result.setObj(attrList);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(attrIds, "属性编号不能为空");
            }
        });
        return result;
    }

    /**
     * 添加库存属性
     * 
     * @param request
     * @return
     */
    @RequestMapping("addStockPrice.do")
    @ResponseBody
    public Object addStockPrice(final HttpServletRequest request, final MultipartFile img) {
        final JsonResult result = new JsonResult(true);
        final String idStr = request.getParameter("id");
        final String goodsNo = request.getParameter("goodsNo");
        final String type = request.getParameter("type");
        final String attrIds = request.getParameter("attrIds");
        final String valueIds = request.getParameter("valueIds");
        final String price = request.getParameter("price");
        final String goodAmount = request.getParameter("goodAmount");
        final String point = request.getParameter("point");
        final String stock = request.getParameter("stock");
        final String pickLimit = request.getParameter("pickLimit");
        final String memo = request.getParameter("memo");
        final String payType = request.getParameter("payType");
        final String erpNo = request.getParameter("erpNo");
        final String barCode = request.getParameter("barCode");
        final String pointRate = request.getParameter("pointRate");
        final String controlStock = request.getParameter("controlStock");

        final String user_id = request.getSession().getAttribute("user_id").toString();
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {

                ProductDO productDO = productDAO.selectByProdId(goodsNo);
                if (null == productDO)
                    throw new BaseRuntimeException("商品信息查询失败！");

                if (!StringUtils.equals(productDO.getOnSale(), OnShellEnum.OFF_SHELL.getCode()))
                    throw new BaseRuntimeException("请先下架商品再修改信息！");

                StockPriceDO stockPrice = new StockPriceDO();
                stockPrice.setGoodsNo(goodsNo);
                if (StringUtils.equals(type, "2") && !StringUtils.hasLength(idStr)) {
                    List<StockPriceDO> stockList = stockPriceDAO.queryProStockPriceNotMap(goodsNo,
                        DelFlgEnum.NOT_DEL.getCode());
                    if (CollectionUtils.isNotEmpty(stockList)) {
                        stockPrice.setAttrIds(stockList.get(0).getAttrIds());
                        stockPrice.setAttrNames(stockList.get(0).getAttrNames());
                    } else {
                        AssertUtil.notBlank(attrIds, "属性不能为空");
                        AssertUtil.notBlank(valueIds, "属性值不能为空");
                        JSONArray attrArr = JSONArray.parseArray(attrIds);
                        String attrIdStr = "";
                        String attrNameStr = "";
                        String attrName = "";
                        for (int i = 0; i < attrArr.size(); i++) {
                            if (i == 0) {
                                attrIdStr += attrArr.getString(i);
                            } else if (i < attrArr.size()) {
                                attrIdStr += "," + attrArr.getString(i);
                            }
                            attrName = goodsAttrDAO.selectById(
                                Integer.valueOf(attrArr.getString(i))).getAttrName();
                            if (i == 0) {
                                attrNameStr += attrName;
                            } else if (i < attrArr.size()) {
                                attrNameStr += "," + attrName;
                            }
                        }
                        stockPrice.setAttrIds(attrIdStr);
                        stockPrice.setAttrNames(attrNameStr);
                    }

                    JSONArray valueArr = JSONArray.parseArray(valueIds);
                    String valueIdStr = "";
                    String valueNameStr = "";
                    String valueName = "";
                    for (int i = 0; i < valueArr.size(); i++) {
                        if (i == 0) {
                            valueIdStr += valueArr.getString(i);
                        } else if (i < valueArr.size()) {
                            valueIdStr += "," + valueArr.getString(i);
                        }
                        valueName = goodsAttrValueDAO.selectAttrById(
                            Integer.valueOf(valueArr.getString(i))).getValue();
                        if (i == 0) {
                            valueNameStr += valueName;
                        } else if (i < valueArr.size()) {
                            valueNameStr += "," + valueName;
                        }
                    }
                    stockPrice.setValueIds(valueIdStr);
                    stockPrice.setValuees(valueNameStr);
                }

                // 判断该商品是否已经存在相同库存属性记录
                if (StringUtils.isBlank(idStr)) {
                    int checkProStockPrice = stockPriceDAO.checkProStockPrice(idStr, goodsNo,
                        stockPrice.getAttrIds(), stockPrice.getValueIds(),
                        DelFlgEnum.NOT_DEL.getCode());
                    if (checkProStockPrice > 0) {
                        throw new BaseRuntimeException("请勿重复提交相同已存在属性");
                    }
                }

                // 如果没有这个路径,则创建
                if (null != img) {
                    String imgName = getRandomCode(3) + ".jpg";
                    String path = IMAGE_FILE + imgName;
                    String url = IMAGE_PATH + imgName;
                    try {
                        uploadFile(img, path);
                    } catch (Exception e) {
                        throw new BaseRuntimeException("图片上传异常");
                    }
                    stockPrice.setImgSrc(url);
                }

                stockPrice.setType(type);
                stockPrice.setMemo(memo);
                stockPrice.setPrice(new Money(price));
                stockPrice.setStock(Integer.valueOf(stock));
                stockPrice.setPayType(payType);
                stockPrice.setErpNo(erpNo);
                stockPrice.setBarCode(barCode);
                if (StringUtils.isBlank(pickLimit)) {
                    stockPrice.setPickLimit(0);
                } else {
                    stockPrice.setPickLimit(Double.parseDouble(pickLimit));
                }
                if (StringUtils.isBlank(controlStock)) {
                    stockPrice.setControlStock("2");
                } else {
                    stockPrice.setControlStock(controlStock);
                }
                if (StringUtils.equals(payType, "1")) {
                    // 纯现金
                    stockPrice.setGoodAmount(new Money(goodAmount));
                } else if (StringUtils.equals(payType, "2")) {
                    // 现金加积分比例方式
                    stockPrice.setGoodAmount(new Money(goodAmount));
                    stockPrice.setPointRate(Double.parseDouble(pointRate) / 100);
                } else if (StringUtils.equals(payType, "3")) {
                    // 现金加积分
                    stockPrice.setGoodAmount(new Money(goodAmount));
                    stockPrice.setPoint(new Money(point));
                } else if (StringUtils.equals(payType, "4")) {
                    // 纯积分
                    stockPrice.setPoint(new Money(point));
                }

                @SuppressWarnings("unused")
                int stockPriceId = 0;
                if (StringUtils.hasLength(idStr)) {
                    StockPriceDO existDo = stockPriceDAO.selectById(Integer.valueOf(idStr));
                    if (null == existDo) {
                        throw new RuntimeException("记录不存在");
                    }
                    if (null == img) {
                        stockPrice.setImgSrc(existDo.getImgSrc());
                    }
                    stockPriceId = Integer.valueOf(idStr);
                    stockPrice.setId(Integer.valueOf(idStr));
                    stockPrice.setModifier(user_id);
                    stockPriceDAO.update(stockPrice);
                } else {
                    stockPrice.setCreater(user_id);
                    stockPriceId = stockPriceDAO.insert(stockPrice);
                }

                // 查找所有关联店铺
                //                if (stockPriceId != 0) {
                //                    ProductTeamDO checkByProdId = productTeamDAO.checkByProdId(stockPrice
                //                        .getGoodsNo());
                //                    if (null != checkByProdId && StringUtils.isNotBlank(checkByProdId.getTeamId())) {
                //                        String teamId = checkByProdId.getTeamId();
                //                        List<TeamDO> queryAllTeamForTop = teamDAO.queryAllTeamForTop(teamId);
                //                        for (TeamDO teamDO : queryAllTeamForTop) {
                //                            String thisTeamId = teamDO.getTeamId();
                //                            StockPriceTeamDO stockPriceTeamDO = stockPriceTeamDAO
                //                                .queryByIdAndTeamId(stockPriceId, thisTeamId);
                //                            if (null == stockPriceTeamDO) {
                //                                stockPriceTeamDO = new StockPriceTeamDO();
                //                                stockPriceTeamDO.setTeamId(thisTeamId);
                //                                stockPriceTeamDO.setStockId(stockPriceId);
                //                                stockPriceTeamDO.setStock(stockPrice.getStock());
                //                                stockPriceTeamDAO.insert(stockPriceTeamDO);
                //                            }
                //                        }
                //                    }
                //                }

                result.setMessage("操作成功");
            }

            @Override
            public void check() {
                AssertUtil.notBlank(goodsNo, "商品编号不能为空");
                AssertUtil.notBlank(type, "是否有属性不能为空");
                AssertUtil.notBlank(price, "原价不能为空");
                if (StringUtils.equals(payType, "1")) {
                    // 纯现金
                    AssertUtil.notBlank(goodAmount, "购买价格不能为空");
                } else if (StringUtils.equals(payType, "2")) {
                    // 现金加积分比例方式
                    AssertUtil.notBlank(goodAmount, "购买价格不能为空");
                    AssertUtil.state(NumUtils.checkeRate(pointRate), "请填写正确的百分比值（积分比例）");
                } else if (StringUtils.equals(payType, "3")) {
                    // 现金加积分
                    AssertUtil.notBlank(goodAmount, "购买价格不能为空");
                    AssertUtil.notBlank(point, "积分不能为空");
                } else if (StringUtils.equals(payType, "4")) {
                    // 纯积分
                    AssertUtil.notBlank(point, "积分不能为空");
                }
                AssertUtil.notBlank(stock, "库存不能为空");
                if (StringUtils.hasLength(idStr)) {
                    if (!StringUtils.isNumeric(idStr)) {
                        throw new RuntimeException("编号只能为数字");
                    }
                }
            }
        });
        return result;
    }

    @RequestMapping("runStockTeam.do")
    @ResponseBody
    public Object runStockTeam(final HttpServletRequest request) {
        final JsonResult result = new JsonResult(true);
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {
                List<ProductDO> queryAllGoods = productDAO.queryAllGoods();
                for (ProductDO productDO : queryAllGoods) {
                    String productId = productDO.getProductId();
                    ProductTeamDO checkByProdId = productTeamDAO.checkByProdId(productId);
                    if (null != checkByProdId && StringUtils.isNotBlank(checkByProdId.getTeamId())) {
                        String teamId = checkByProdId.getTeamId();
                        List<Map<String, Object>> queryProStockPrice = stockPriceDAO
                            .queryProStockPrice(productId, DelFlgEnum.NOT_DEL.getCode(), null,
                                null, null);
                        for (Map<String, Object> map2 : queryProStockPrice) {
                            Integer stockPriceId = MapUtils.getInteger(map2, "id");
                            Double stock = MapUtils.getDouble(map2, "stock");
                            List<TeamDO> queryAllTeamForTop = teamDAO.queryAllTeamForTop(teamId);
                            for (TeamDO teamDO : queryAllTeamForTop) {
                                String thisTeamId = teamDO.getTeamId();
                                StockPriceTeamDO stockPriceTeamDO = stockPriceTeamDAO
                                    .queryByIdAndTeamId(stockPriceId, thisTeamId);
                                if (null == stockPriceTeamDO) {
                                    stockPriceTeamDO = new StockPriceTeamDO();
                                    stockPriceTeamDO.setTeamId(thisTeamId);
                                    stockPriceTeamDO.setStockId(stockPriceId);
                                    stockPriceTeamDO.setStock(stock);
                                    stockPriceTeamDAO.insert(stockPriceTeamDO);
                                }
                            }
                        }
                    }
                }

            }

            @Override
            public void check() {

            }
        });
        return result;
    }

    @RequestMapping("runProZjm.do")
    @ResponseBody
    public Object runProZjm(final HttpServletRequest request) {
        final JsonResult result = new JsonResult(true);
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {
                List<ProductDO> queryAllGoods = productDAO.queryAllGoods();
                for (ProductDO productDO : queryAllGoods) {
                    String productId = productDO.getProductId();
                    String productName = productDO.getProductName();
                    if (StringUtils.isNotBlank(productName)) {
                        String proZjm = zjmComponent.getZjm(productName);
                        productDAO.updateZjm(proZjm, productId);
                    }
                }
            }

            @Override
            public void check() {

            }
        });
        return result;
    }

    /**
     * 删除
     * 
     * @param request
     * @return
     */
    @RequestMapping("delStockPrice.do")
    @ResponseBody
    public Object delStockPrice(final HttpServletRequest request) {
        final JsonResult result = new JsonResult(true);
        final String idStr = request.getParameter("id");

        final String user_id = request.getSession().getAttribute("user_id").toString();
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {
                StockPriceDO stockPriceDO = stockPriceDAO.selectById(Integer.valueOf(idStr));
                if (null == stockPriceDO)
                    throw new BaseRuntimeException("商品信息查询失败");

                ProductDO existDO = productDAO.selectByProdId(stockPriceDO.getGoodsNo());
                if (null == existDO) {
                    throw new BaseRuntimeException("商品信息查询失败");
                }
                if (!StringUtils.equals(existDO.getOnSale(), OnShellEnum.OFF_SHELL.getCode())) {
                    throw new BaseRuntimeException("请先下架商品再修改信息！");
                }

                int delStockPrice = stockPriceDAO.delStockPrice(DelFlgEnum.HAS_DEL.getCode(),
                    user_id, Integer.valueOf(idStr));

                if (delStockPrice > 0)
                    result.setMessage("操作成功");
                else {
                    result.setSuccess(false);
                    result.setMessage("操作失败");
                }

            }

            @Override
            public void check() {
                AssertUtil.notBlank(idStr, "参数异常");
            }
        });
        return result;
    }

    /**
     * 查询门店列表
     * 
     * @param productId
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("queryPdTemaList.do")
    @ResponseBody
    public Object queryPdTemaList(HttpServletRequest request, String productId, Integer offset,
                                  Integer limit) {
        JSONObject data = new JSONObject();
        List<Map<String, Object>> teamList = productTeamDAO.selectByPage(productId, offset, limit);
        int total = productTeamDAO.selectCountByPage(productId);
        data.put("rows", teamList);
        data.put("total", total);
        return data;
    }

    @RequestMapping("addPdTeamList.do")
    @ResponseBody
    public Object addPdTeamList(final HttpServletRequest request) {
        final String prodId = request.getParameter("prodId");
        final String teamId = request.getParameter("teamId");
        final JsonResult result = new JsonResult(true);

        //        final String user_id = request.getSession().getAttribute("user_id").toString();
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {
                ProductDO productDO = productDAO.selectByProdId(prodId);
                if (null == productDO) {
                    throw new RuntimeException("商品不存在 ");
                }

                TeamDO teamDO = teamDAO.selectByTeamId(teamId);
                if (null == teamDO) {
                    throw new RuntimeException("门店不存在 ");
                }

                ProductTeamDO productTeamDO = productTeamDAO.selectByProdIdATeamId(prodId, teamId);
                if (null != productTeamDO) {
                    throw new RuntimeException("该门店已与该商品绑定关系");
                }
                ProductTeamDO pt = productTeamDAO.checkByProdId(prodId);
                if (pt == null) {
                    ProductTeamDO productTeam = new ProductTeamDO();
                    productTeam.setProductId(prodId);
                    productTeam.setTeamId(teamId);
                    int insert = productTeamDAO.insert(productTeam);
                    //                    productDAO.updateOnSale(user_id, prodId);
                    result.setSuccess(insert > 0);
                    result.setMessage(insert > 0 ? "操作成功" : "操作失败");
                } else {
                    result.setMessage("该商品已经和门店绑定关系");
                }
            }

            @Override
            public void check() {
                AssertUtil.notBlank(prodId, "商品编号不能为空");
                AssertUtil.notBlank(teamId, "门店编号不能为空");

            }
        });
        return result;
    }

    @RequestMapping("initAddTeam.do")
    @ResponseBody
    public Object dd(final HttpServletRequest request) {
        final JsonQueryResult<List<TeamDO>> result = new JsonQueryResult<List<TeamDO>>(true);
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {
                // 门店
                List<TeamDO> teamList = teamDAO.selectTopTeam();

                result.setObj(teamList);
            }

            @Override
            public void check() {
            }
        });
        return result;

    }

    @RequestMapping("delPdTeam.do")
    @ResponseBody
    public Object delPdTeam(final HttpServletRequest request) {
        final JsonResult result = new JsonResult(true);
        final String prodId = request.getParameter("prodId");
        final String teamId = request.getParameter("teamId");
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {

                ProductTeamDO productTeamDO = productTeamDAO.selectByProdIdATeamId(prodId, teamId);
                if (null == productTeamDO) {
                    return;
                }

                result.setSuccess(0 < productTeamDAO.deleteByTeamIdAndProdId(teamId, prodId));
            }

            @Override
            public void check() {
                AssertUtil.notBlank(prodId, "商品编号不能为空");
                AssertUtil.notBlank(teamId, "门店编号不能为空");

            }
        });
        return result;
    }

    /**
     * 商品删除
     * 
     * @param request
     * @return
     */
    @RequestMapping("deleteGoods.do")
    @ResponseBody
    public Object deleteGoods(final HttpServletRequest request) {
        final JsonResult result = new JsonResult(false);
        final String goodsId = request.getParameter("goodsId");
        final String user_id = request.getSession().getAttribute("user_id").toString();
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {
                ProductDO chekPro = productDAO.selectByProdId(goodsId);
                if (null == chekPro) {
                    throw new BaseRuntimeException("商品信息查询异常");
                }
                //删除商品
                int updateDelFlg = productDAO.updateDelFlg(user_id, chekPro.getId());
                //清理关联 hqyt_product_team
                if (updateDelFlg <= 0) {
                    throw new BaseRuntimeException("删除失败");
                }

                productTeamDAO.cleanALlProLink(chekPro.getProductId());

                result.setSuccess(true);
                result.setMessage("删除成功");
            }

            @Override
            public void check() {
                AssertUtil.notBlank(goodsId, "商品编号不能为空");
            }
        });
        return result;
    }

    /**
     * 删除商品恢复
     * 未删除  下架
     * @param request
     * @return
     */
    @RequestMapping("recoverGoods.do")
    @ResponseBody
    public Object recoverGoods(final HttpServletRequest request) {
        final JsonResult result = new JsonResult(false);
        final String goodsId = request.getParameter("goodsId");
        final String user_id = request.getSession().getAttribute("user_id").toString();
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {
                ProductDO chekPro = productDAO.selectByProdId(goodsId);
                if (null == chekPro) {
                    throw new BaseRuntimeException("商品信息查询异常");
                }
                chekPro.setDelFlg(DelFlgEnum.NOT_DEL.getCode());
                chekPro.setOnSale(OnShellEnum.OFF_SHELL.getCode());
                chekPro.setModifier(user_id);
                int updateDelFlg = productDAO.recoverGood(chekPro);
                if (updateDelFlg <= 0) {
                    throw new BaseRuntimeException("恢复失败");
                }
                result.setSuccess(true);
                result.setMessage("恢复成功");
            }

            @Override
            public void check() {
                AssertUtil.notBlank(goodsId, "商品编号不能为空");
            }
        });
        return result;
    }

    // 生成随机数的,防止图片重复
    public String getRandomCode(int length) {
        if (length < 1 || length > 10) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= length; i++) {
            int rand = new Random().nextInt(10);
            sb.append(rand);
        }
        return String.valueOf(System.currentTimeMillis()) + sb.toString();
    }

    /**
     * 上下架
     * 
     * @param request
     * @return
     */
    @RequestMapping("changeOnSale.do")
    @ResponseBody
    public Object changeOnSale(final HttpServletRequest request) {
        final JsonResult result = new JsonResult(false);
        final String goodsId = request.getParameter("goodsId");
        final String user_id = request.getSession().getAttribute("user_id").toString();
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {
                ProductDO productDO = productDAO.queryGoodsById(Integer.parseInt(goodsId));
                if (productDO.getOnSale().equals(OnShellEnum.ON_SHELL.getCode())) {
                    int autoOnSale = productDAO.autoOnSale(OnShellEnum.OFF_SHELL.getCode(),
                        user_id, Integer.parseInt(goodsId));
                    result.setSuccess(autoOnSale > 0);
                    result.setMessage(autoOnSale > 0 ? "下架成功" : "下架失败");
                } else if (productDO.getOnSale().equals(OnShellEnum.OFF_SHELL.getCode())) {
                    int autoOnSale = productDAO.autoOnSale(OnShellEnum.ON_SHELL.getCode(), user_id,
                        Integer.parseInt(goodsId));
                    result.setSuccess(autoOnSale > 0);
                    result.setMessage(autoOnSale > 0 ? "上架成功" : "上架失败");
                } else {
                    result.setSuccess(false);
                    result.setMessage("该商品状态异常，请联系管理员");
                }
            }

            @Override
            public void check() {
                AssertUtil.notBlank(goodsId, "商品编号不能为空");
            }
        });
        return result;
    }

    @RequestMapping("queryGoodsTypesList.do")
    @ResponseBody
    public Object queryGoodsTypesList() {
        List<ProductTypeDO> list = productTypeDAO.selectAll();
        return list;
    }

    /**
     * 
     * 
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/queryFatherCategory.do")
    @ResponseBody
    public JSONObject queryFatherCategory(HttpServletRequest request, int type) {
        JSONObject jo = new JSONObject();
        List<CategoryDO> topCate = categoryDAO.searchByTopId(0);
        if (CollectionUtils.isEmpty(topCate)) {
            jo.put("rows", new ArrayList<CategoryDO>());
        } else {
            //            int size = topCate.size();
            //            if (size <= 1) {
            //                if (type == 1) {
            //                    jo.put("rows", categoryDAO.searchByTopId(topCate.get(0).getId()));
            //                } else {
            //                    jo.put("rows", new ArrayList<CategoryDO>());
            //                }
            //            } else {
            //                if (type == 1) {
            //                    jo.put("rows", categoryDAO.searchByTopId(topCate.get(0).getId()));
            //                } else {
            //                    jo.put("rows", categoryDAO.searchByTopId(topCate.get(1).getId()));
            //                }
            //            }
            jo.put("rows", topCate);
        }
        return jo;
    }

    @RequestMapping("/queryChildCategory.do")
    @ResponseBody
    public JSONObject queryChildCategory(HttpServletRequest request) {
        JSONObject jo = new JSONObject();
        String[] topId = request.getParameterValues("topId");

        List<String> topIds = Arrays.asList(topId);
        if (CollectionUtils.isEmpty(topIds)
            || (StringUtils.isBlank(topIds.get(0)) && topIds.size() <= 1)) {
            topIds = null;
            jo.put("rows", new ArrayList<CategoryDO>());
            return jo;
        }

        List<CategoryDO> queryChildCategoryByTopIdS = categoryDAO
            .queryChildCategoryByTopIdS(topIds);

        jo.put("rows", queryChildCategoryByTopIdS);
        return jo;
    }

    /**
     * 手动同步库存信息
     * 
     * @param request
     * @return
     */
    @RequestMapping("handCleanStock.do")
    @ResponseBody
    public Object handCleanStock(final HttpServletRequest request) {

        final JsonResult result = new JsonResult(true);
        final String user_id = request.getSession().getAttribute("user_id").toString();
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {
                List<StockPriceTeamMiddleDO> listMiddle = stockPriceTeamMiddleDAO.queryAll();

                if (CollectionUtils.isNotEmpty(listMiddle)) {
                    //清理旧数据   是否为总店   0否  1是
                    stockPriceTeamDAO.deleteAllByIstop("1");
                }

                for (StockPriceTeamMiddleDO stockPriceTeamMiddleDO : listMiddle) {
                    //执行添加数据
                    List<StockPriceTeamDO> dataList = doExcute1(stockPriceTeamMiddleDO);

                    if (CollectionUtils.isNotEmpty(dataList)) {
                        for (StockPriceTeamDO data : dataList) {
                            //先判断是否有数据
                            StockPriceTeamDO stockPriceTeamDO = stockPriceTeamDAO
                                .queryByIdAndTeamId(data.getStockId(), data.getTeamId());
                            int a = 0;
                            if (null == stockPriceTeamDO) {
                                a = stockPriceTeamDAO.insert(data);

                            } else {
                                stockPriceTeamDO.setStock(stockPriceTeamMiddleDO.getStock());
                                a = stockPriceTeamDAO.update(stockPriceTeamDO);
                            }
                            if (a > 0) {
                                stockPriceTeamMiddleDAO.updateStatus(stockPriceTeamMiddleDO.getId());
                                doExcute2(data);
                            }
                        }
                    }
                }
                //将所有记录状态改为已操作
                stockPriceTeamMiddleDAO.updateStatusAll();

                result.setMessage("手动同步中间库存操作成功");
            }

            @Override
            public void check() {
                AssertUtil.notBlank(user_id, "未登录");
            }
        });

        return result;
    }

    private List<StockPriceTeamDO> doExcute1(StockPriceTeamMiddleDO stockPriceTeamMiddleDO) {
        TeamDO teamDo = teamDAO.selectTeamByErpNo(stockPriceTeamMiddleDO.getErpNoTeam());
        //可能重复
        List<StockPriceDO> stockPriceList = stockPriceDAO.queryInfoByErpNo(stockPriceTeamMiddleDO
            .getErpNoAttr());
        if (teamDo != null && CollectionUtils.isNotEmpty(stockPriceList)) {
            List<StockPriceTeamDO> stockPriceTeamDOs = new ArrayList<StockPriceTeamDO>();
            for (StockPriceDO stockPriceDO : stockPriceList) {
                StockPriceTeamDO stockPriceTeam = new StockPriceTeamDO();
                stockPriceTeam.setTeamId(teamDo.getTeamId());
                stockPriceTeam.setStockId(stockPriceDO.getId());
                stockPriceTeam.setStock(stockPriceTeamMiddleDO.getStock());
                stockPriceTeamDOs.add(stockPriceTeam);
            }
            return stockPriceTeamDOs;
        } else {
            return null;
        }
    }

    private boolean doExcute2(StockPriceTeamDO stockPriceTeamDo) {
        StockPriceDO selectById = stockPriceDAO.selectById(stockPriceTeamDo.getStockId());
        int count = productTeamDAO.queryCountByTeamIdAndProductId(stockPriceTeamDo.getTeamId(),
            selectById.getGoodsNo());
        if (count > 0) {
            stockPriceDAO.updateStockByErpNo(stockPriceTeamDo.getStock(),
                stockPriceTeamDo.getStockId());
        }
        return true;
    }

}
