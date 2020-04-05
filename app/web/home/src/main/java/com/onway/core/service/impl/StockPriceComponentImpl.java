/**
 * 
 */
package com.onway.core.service.impl;

import java.util.List;
import java.util.Map;

import com.onway.common.lang.CollectionUtils;
import com.onway.common.lang.Money;
import com.onway.common.lang.StringUtils;
import com.onway.core.service.MsgService;
import com.onway.core.service.StockPriceComponent;
import com.onway.core.service.localcache.manager.SysConfigCacheManager;
import com.onway.fyapp.common.dal.daointerface.GoodsAttrDAO;
import com.onway.fyapp.common.dal.daointerface.GoodsAttrValueDAO;
import com.onway.fyapp.common.dal.daointerface.OrderDAO;
import com.onway.fyapp.common.dal.daointerface.ProductCategoryDAO;
import com.onway.fyapp.common.dal.daointerface.ProductDAO;
import com.onway.fyapp.common.dal.daointerface.ProductTeamDAO;
import com.onway.fyapp.common.dal.daointerface.StockPriceDAO;
import com.onway.fyapp.common.dal.daointerface.StockPriceTeamDAO;
import com.onway.fyapp.common.dal.daointerface.TeamDAO;
import com.onway.fyapp.common.dal.daointerface.UserDAO;
import com.onway.fyapp.common.dal.dataobject.GoodsAttrDO;
import com.onway.fyapp.common.dal.dataobject.GoodsAttrValueDO;
import com.onway.fyapp.common.dal.dataobject.OrderDO;
import com.onway.fyapp.common.dal.dataobject.ProductCategoryDO;
import com.onway.fyapp.common.dal.dataobject.ProductDO;
import com.onway.fyapp.common.dal.dataobject.ProductTeamDO;
import com.onway.fyapp.common.dal.dataobject.StockPriceDO;
import com.onway.fyapp.common.dal.dataobject.StockPriceTeamDO;
import com.onway.fyapp.common.dal.dataobject.TeamDO;
import com.onway.fyapp.common.dal.dataobject.UserDO;
import com.onway.model.enums.DelFlgEnum;
import com.onway.model.enums.MsgTypeEnum;
import com.onway.model.enums.OrderStatusEnum;
import com.onway.model.enums.StockReturnTypeEnum;
import com.onway.model.enums.SysConfigCacheKeyEnum;
import com.onway.model.excel.ImportOrderSend;
import com.onway.platform.common.base.BaseResult;
import com.onway.platform.common.exception.BaseRuntimeException;
import com.onway.platform.common.service.template.AbstractServiceImpl;
import com.onway.platform.common.service.template.ServiceCheckCallback;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.utils.DateUtil;
import com.onway.utils.StringToListUtil;

public class StockPriceComponentImpl extends AbstractServiceImpl implements StockPriceComponent {

    private StockPriceDAO         stockPriceDAO;

    private GoodsAttrDAO          goodsAttrDAO;

    private GoodsAttrValueDAO     goodsAttrValueDAO;

    private ProductDAO            productDAO;

    private UserDAO               userDAO;

    private MsgService            msgService;

    private SysConfigCacheManager sysConfigCacheManager;

    public void setStockPriceDAO(StockPriceDAO stockPriceDAO) {
        this.stockPriceDAO = stockPriceDAO;
    }

    public void setGoodsAttrDAO(GoodsAttrDAO goodsAttrDAO) {
        this.goodsAttrDAO = goodsAttrDAO;
    }

    public void setGoodsAttrValueDAO(GoodsAttrValueDAO goodsAttrValueDAO) {
        this.goodsAttrValueDAO = goodsAttrValueDAO;
    }

    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setMsgService(MsgService msgService) {
        this.msgService = msgService;
    }

    public void setSysConfigCacheManager(SysConfigCacheManager sysConfigCacheManager) {
        this.sysConfigCacheManager = sysConfigCacheManager;
    }

    @Override
    public BaseResult importStockPriceForGoods(final String goodNo, final String userId,
                                               final List<StockPriceDO> importStockPriceDO) {
        final BaseResult result = new BaseResult(false);
        serviceTemplate.execute(result, new ServiceCheckCallback() {

            @Override
            public void check() {
                AssertUtil.notBlank(goodNo, "商品信息为空");
            }

            @Override
            public void executeService() {
                ProductDO productDO = productDAO.selectByProdId(goodNo);
                if (null == productDO)
                    throw new BaseRuntimeException("商品信息异常");

                List<Map<String, Object>> queryProStockPrice = stockPriceDAO.queryProStockPrice(
                    goodNo, DelFlgEnum.NOT_DEL.getCode(), null, null, null);
                if (CollectionUtils.isNotEmpty(queryProStockPrice)) {
                    String type = (String) queryProStockPrice.get(0).get("type");
                    if (StringUtils.equals(type, "1")) {
                        throw new BaseRuntimeException("商品已有无属性记录，无法导入");
                    }
                }

                StringBuilder errMsg = new StringBuilder();

                for (StockPriceDO stockPriceDO : importStockPriceDO) {

                    int index = importStockPriceDO.indexOf(stockPriceDO) + 2;

                    String attrNames = stockPriceDO.getAttrNames();
                    String valuees = stockPriceDO.getValuees();

                    //查询库中是否存在   属性值   和  属性名 
                    String[] attrNamesSplit = attrNames.split(",");
                    String[] valueesSplit = valuees.split(",");

                    int lengthName = attrNamesSplit.length;
                    int lengthValue = valueesSplit.length;

                    if (lengthName != lengthValue) {
                        errMsg.append("第" + index + "行数据格式不正确；");
                        continue;
                    }

                    String attrIds = "";
                    String valueIds = "";

                    for (int i = 0; i < lengthName; i++) {
                        //属性名
                        String attrName = attrNamesSplit[i];
                        GoodsAttrDO checkHasAttr = goodsAttrDAO.selectByAttrNameObj(attrName);
                        int attrId = 0;
                        if (null == checkHasAttr) {
                            //新增
                            checkHasAttr = new GoodsAttrDO();
                            checkHasAttr.setAttrName(attrName);
                            checkHasAttr.setCreater(userId);
                            attrId = goodsAttrDAO.insert(checkHasAttr);
                        } else {
                            attrId = checkHasAttr.getId();
                        }
                        attrIds = attrIds + "," + attrId;

                        //属性值
                        String attrValue = valueesSplit[i];
                        GoodsAttrValueDO checkHasValue = goodsAttrValueDAO.selectAttrByValueObj(
                            attrValue, String.valueOf(attrId));
                        int valueId = 0;
                        if (null == checkHasValue) {
                            //新增
                            checkHasValue = new GoodsAttrValueDO();
                            checkHasValue.setAttrId(String.valueOf(attrId));
                            checkHasValue.setValue(attrValue);
                            checkHasValue.setCreater(userId);
                            valueId = goodsAttrValueDAO.insert(checkHasValue);
                        } else {
                            valueId = checkHasValue.getId();
                        }
                        valueIds = valueIds + "," + valueId;
                    }

                    String thisAttrIds = attrIds.substring(1, attrIds.length());

                    String thisValueIds = valueIds.substring(1, valueIds.length());

                    stockPriceDO.setAttrIds(thisAttrIds);
                    stockPriceDO.setValueIds(thisValueIds);

                    // 判断该商品是否已经存在相同库存属性记录
                    int checkProStockPrice = stockPriceDAO.checkProStockPrice(null, goodNo,
                        thisAttrIds, thisValueIds, DelFlgEnum.NOT_DEL.getCode());
                    if (checkProStockPrice > 0) {
                        errMsg.append("第" + index + "行重复提交属性；");
                        continue;
                    }

                    String payType = stockPriceDO.getPayType();

                    if (StringUtils.equals(payType, "1")) {
                        // 纯现金
                        stockPriceDO.setPoint(new Money(0));
                        stockPriceDO.setPointRate(0);
                    } else if (StringUtils.equals(payType, "2")) {
                        // 现金加积分比例方式
                        stockPriceDO.setPoint(new Money(0));
                    } else if (StringUtils.equals(payType, "3")) {
                        // 现金加积分
                        stockPriceDO.setPointRate(0);
                    } else if (StringUtils.equals(payType, "4")) {
                        // 纯积分
                        stockPriceDO.setGoodAmount(new Money(0));
                        stockPriceDO.setPointRate(0);
                    }
                    stockPriceDO.setGoodsNo(goodNo);
                    stockPriceDO.setType("2");
                    stockPriceDO.setDelFlg(DelFlgEnum.NOT_DEL.getCode());
                    stockPriceDO.setCreater(userId);
                    stockPriceDO.setReturnType(StockReturnTypeEnum.NOTHING.getCode());
                    int insertId = stockPriceDAO.insert(stockPriceDO);
                    stockPriceDO.setId(insertId);
                    linkStockPriceTeam(stockPriceDO);
                }

                if (StringUtils.isNotBlank(errMsg.toString())) {
                    result.setMessage(errMsg.append("其他导入成功").toString());
                } else {
                    result.setSuccess(true);
                }
            }
        });
        return result;
    }

    private ProductTeamDAO    productTeamDAO;

    private TeamDAO           teamDAO;

    private StockPriceTeamDAO stockPriceTeamDAO;

    public void setProductTeamDAO(ProductTeamDAO productTeamDAO) {
        this.productTeamDAO = productTeamDAO;
    }

    public void setTeamDAO(TeamDAO teamDAO) {
        this.teamDAO = teamDAO;
    }

    public void setStockPriceTeamDAO(StockPriceTeamDAO stockPriceTeamDAO) {
        this.stockPriceTeamDAO = stockPriceTeamDAO;
    }

    public BaseResult linkStockPriceTeam(final StockPriceDO stockPriceDO) {
        final BaseResult result = new BaseResult(false);
        serviceTemplate.execute(result, new ServiceCheckCallback() {

            @Override
            public void check() {

            }

            @Override
            public void executeService() {
                int stockPriceId = stockPriceDO.getId();
                // 查找所有关联店铺
                if (stockPriceId != 0) {
                    ProductTeamDO checkByProdId = productTeamDAO.checkByProdId(stockPriceDO
                        .getGoodsNo());
                    if (null != checkByProdId && StringUtils.isNotBlank(checkByProdId.getTeamId())) {
                        String teamId = checkByProdId.getTeamId();
                        List<TeamDO> queryAllTeamForTop = teamDAO.queryAllTeamForTop(teamId);
                        for (TeamDO teamDO : queryAllTeamForTop) {
                            String thisTeamId = teamDO.getTeamId();
                            StockPriceTeamDO stockPriceTeamDO = stockPriceTeamDAO
                                .queryByIdAndTeamId(stockPriceId, thisTeamId);
                            if (null == stockPriceTeamDO) {
                                stockPriceTeamDO = new StockPriceTeamDO();
                                stockPriceTeamDO.setTeamId(thisTeamId);
                                stockPriceTeamDO.setStockId(stockPriceId);
                                stockPriceTeamDO.setStock(stockPriceDO.getStock());
                                stockPriceTeamDAO.insert(stockPriceTeamDO);
                            }
                        }
                    }
                }
            }
        });
        return result;
    }

    private OrderDAO orderDAO;

    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    /**
     * excel发货
     */
    @Override
    public BaseResult excelForOrderSend(final List<ImportOrderSend> importOrderSendDO,
                                        final String userId) {
        final BaseResult result = new BaseResult(false);
        serviceTemplate.execute(result, new ServiceCheckCallback() {

            @Override
            public void check() {

            }

            @Override
            public void executeService() {
                if(CollectionUtils.isEmpty(importOrderSendDO)){
                    result.setMessage("没有运单信息");
                    return;
                }
                

                StringBuilder errMsg = new StringBuilder();

                for (ImportOrderSend importOrderSend : importOrderSendDO) {
                    String childOrderId = importOrderSend.getChildOrderId();
                    OrderDO orderDO = orderDAO.selectByChildOrderId(childOrderId);
                    if (null == orderDO) {
                        errMsg.append("子单号" + childOrderId + "信息异常");
                        continue;
                    }

                    if (StringUtils.notEquals(orderDO.getOrderState(),
                        OrderStatusEnum.NOT_SEND.getCode())
                        && StringUtils.notEquals(orderDO.getOrderState(),
                            OrderStatusEnum.NOT_SEND_ING.getCode())) {
                        errMsg.append("子单号" + childOrderId + "不是待发货订单");
                        continue;
                    }
                    
                    if(StringUtils.isBlank(importOrderSend.getExpressNo()))
                        importOrderSend.setExpressNo("100000083");

                    orderDAO.updateWuliuAndSta(importOrderSend.getExpressNo(),
                        importOrderSend.getKuaidiNo(), userId, childOrderId);

                    //发货推送
                    UserDO userInfo = userDAO.finduserbyid(orderDO.getUserId());

                    if (null != userInfo) {
                        //发货通知
                        String msgContent = "您购买的商品" + orderDO.getProductName() + "已于"
                                            + DateUtil.getNowString() + "发货，运单号："
                                            + importOrderSend.getKuaidiNo();
                        msgService.newMsg(userInfo.getUserId(), MsgTypeEnum.ORDER_MSG.getCode(),
                            "订单发货通知", msgContent);

                        String linkUrl = sysConfigCacheManager
                            .getConfigValue(SysConfigCacheKeyEnum.USER_ORDER_DETAILS_API)
                                         + "?id="
                                         + orderDO.getChildOrderId();

                        msgService.sendWechatCustomerMsg(userInfo.getOpenId(), linkUrl,
                            orderDO.getImgSrc(), "订单发货通知", msgContent);
                    }
                }

                if (StringUtils.isNotBlank(errMsg.toString())) {
                    result.setMessage(errMsg.append("其他导入成功").toString());
                } else {
                    result.setSuccess(true);
                }

            }
        });
        return result;
    }

    private ProductCategoryDAO productCategoryDAO;

    public void setProductCategoryDAO(ProductCategoryDAO productCategoryDAO) {
        this.productCategoryDAO = productCategoryDAO;
    }

    @Override
    public BaseResult newProCategory(final String productId, final String[] addFirstCate,
                                     final String[] addSecondCate, final String[] addThirdCate) {
        final BaseResult result = new BaseResult(false);
        serviceTemplate.execute(result, new ServiceCheckCallback() {

            @Override
            public void check() {
                AssertUtil.notBlank(productId, "商品信息为空");
            }

            @Override
            public void executeService() {
                if (null == addFirstCate || null == addSecondCate || null == addThirdCate) {
                    return;
                }

                if ((addFirstCate.length != addSecondCate.length)
                    || (addFirstCate.length != addThirdCate.length)) {
                    return;
                }

                //【清理旧数据】
                productCategoryDAO.deleteProCate(productId);

                List<String> firstCate = StringToListUtil.arrToList(addFirstCate);
                List<String> secondCate = StringToListUtil.arrToList(addSecondCate);
                List<String> thirdCate = StringToListUtil.arrToList(addThirdCate);

                int index = 0;
                for (String categoryId : firstCate) {
                    if (StringUtils.notEquals(categoryId, "defaultnull")
                        && StringUtils.isNotBlank(categoryId)) {
                        ProductCategoryDO categoryDO = new ProductCategoryDO();
                        categoryDO.setProductId(productId);
                        categoryDO.setCategoryId(categoryId);
                        categoryDO.setGroupId(index + 1);
                        productCategoryDAO.newProCate(categoryDO);
                    }

                    String secondCateId = secondCate.get(index);
                    if (StringUtils.notEquals(secondCateId, "defaultnull")
                        && StringUtils.isNotBlank(secondCateId)) {
                        ProductCategoryDO categoryDO = new ProductCategoryDO();
                        categoryDO.setProductId(productId);
                        categoryDO.setCategoryId(secondCateId);
                        categoryDO.setGroupId(index + 1);
                        productCategoryDAO.newProCate(categoryDO);
                    }

                    String thirdCateId = thirdCate.get(index);
                    if (StringUtils.notEquals(thirdCateId, "defaultnull")
                        && StringUtils.isNotBlank(thirdCateId)) {
                        ProductCategoryDO categoryDO = new ProductCategoryDO();
                        categoryDO.setProductId(productId);
                        categoryDO.setCategoryId(thirdCateId);
                        categoryDO.setGroupId(index + 1);
                        productCategoryDAO.newProCate(categoryDO);
                    }
                    index++;
                }

                result.setSuccess(true);
            }
        });
        return result;
    }

}
