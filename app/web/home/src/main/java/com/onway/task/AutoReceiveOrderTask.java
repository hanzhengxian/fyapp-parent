package com.onway.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gexin.fastjson.JSONArray;
import com.onway.common.lang.CollectionUtils;
import com.onway.common.lang.Money;
import com.onway.common.lang.StringUtils;
import com.onway.core.service.AutoReceiveService;
import com.onway.core.service.MsgService;
import com.onway.core.service.UserQueryService;
import com.onway.core.service.localcache.manager.SysConfigCacheManager;
import com.onway.fyapp.common.dal.daointerface.OrderDAO;
import com.onway.fyapp.common.dal.daointerface.PlanReturnDAO;
import com.onway.fyapp.common.dal.daointerface.TeamDAO;
import com.onway.fyapp.common.dal.daointerface.UserDAO;
import com.onway.fyapp.common.dal.dataobject.OrderDO;
import com.onway.fyapp.common.dal.dataobject.OrderRebateDO;
import com.onway.fyapp.common.dal.dataobject.PlanReturnDO;
import com.onway.fyapp.common.dal.dataobject.TeamDO;
import com.onway.fyapp.common.dal.dataobject.UserDO;
import com.onway.model.enums.AccountTypeEnum;
import com.onway.model.enums.DelFlgEnum;
import com.onway.model.enums.MsgTypeEnum;
import com.onway.model.enums.OrderStatusEnum;
import com.onway.model.enums.PlanCategoryEnum;
import com.onway.model.enums.PlanReturnTypeEnum;
import com.onway.model.enums.StockReturnTypeEnum;
import com.onway.model.enums.SysConfigCacheKeyEnum;
import com.onway.platform.common.base.QueryResult;
import com.onway.result.LinkProStock;
import com.onway.utils.DateUtil;

/**
 * 自动确认收货
 */
public class AutoReceiveOrderTask extends AbstractTask {

    private UserDAO               userDAO;

    private OrderDAO              orderDAO;

    private TeamDAO               teamDAO;

    private PlanReturnDAO         planReturnDAO;

    private SysConfigCacheManager sysConfigCacheManager;

    private MsgService            msgService;

    private AutoReceiveService    autoReceiveService;

    private UserQueryService      userQueryService;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public void setTeamDAO(TeamDAO teamDAO) {
        this.teamDAO = teamDAO;
    }

    public void setPlanReturnDAO(PlanReturnDAO planReturnDAO) {
        this.planReturnDAO = planReturnDAO;
    }

    public void setSysConfigCacheManager(SysConfigCacheManager sysConfigCacheManager) {
        this.sysConfigCacheManager = sysConfigCacheManager;
    }

    public void setMsgService(MsgService msgService) {
        this.msgService = msgService;
    }

    public void setAutoReceiveService(AutoReceiveService autoReceiveService) {
        this.autoReceiveService = autoReceiveService;
    }

    public void setUserQueryService(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @Override
    protected boolean canProcess() {
        return true;
    }

    @Override
    protected void process() {
        try {

            int day = 7;
            String autoDay = sysConfigCacheManager
                .getConfigValue(SysConfigCacheKeyEnum.AUTO_RECEIVE_ORDER_DAY);
            if (StringUtils.isNotBlank(autoDay))
                day = Integer.valueOf(autoDay);

            Date dayAgo = DateUtil.getTimeByDay(-day);

            List<OrderDO> queryAutoReceiveOrder = orderDAO.queryAutoReceiveOrder(dayAgo,
                OrderStatusEnum.HAS_SEND.getCode());
            for (OrderDO orderDO : queryAutoReceiveOrder) {

                String userId = orderDO.getUserId();

                if (orderDAO.updateOrderStatByChildId(OrderStatusEnum.HAS_RECEIVE.getCode(),
                    "AUTO", orderDO.getChildOrderId()) > 0) {
                    UserDO userDO = userDAO.finduserbyid(userId);

                    Money amount = orderDO.getRealOrderPrice()
                        .add(orderDO.getReduceAmountVoucher());
                    //增加健康值
                    String rate = sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.DEVOTE_MONEY_RATE);
                    
                    List<OrderDO> returnOrder = new ArrayList<OrderDO>();
                    returnOrder.add(orderDO);
                    
                    //【购买返利】
                    //购买人是团队成员   贡献值  积分 增加到  非会员统一账户
                    //购买人不是团队成员   贡献值  积分 增加到  个人账户  
                    QueryResult<TeamDO> buy_user_team = userQueryService.searchUserTeam(orderDO
                        .getUserId());
                    if (buy_user_team.isSuccess()) {
                        String unifyCell = sysConfigCacheManager
                            .getConfigValue(SysConfigCacheKeyEnum.UNIFY_CUSTOMER_CELL);
                        QueryResult<UserDO> unifyUserInfo = userQueryService.getUserInfo(unifyCell);
                        if (unifyUserInfo.isSuccess()) {
                            UserDO unifyUserDO = unifyUserInfo.getResultObject();
                            //健康值    非会员统一账户
                            autoReceiveService.payOrderSuccForDevote(unifyUserDO.getUserId(),
                                AccountTypeEnum.USER.getCode(),
                                amount.multiply(Double.parseDouble(rate)),
                                orderDO.getChildOrderId());
                            //记录健康值
                            orderDO.setDevoteAmount(amount.multiply(Double.parseDouble(rate)));
                            //0个人  1非会员统一账户
                            orderDO.setDevoteAmountTo("1");
                            orderDAO.modifyDevoteByChildOrderId(orderDO);
                            // 返非会员统一账户   积分
                            paySuccReturn(unifyUserDO, returnOrder);
                        }
                    } else {
                        //健康值   个人账户  
                        autoReceiveService.payOrderSuccForDevote(orderDO.getUserId(),
                            AccountTypeEnum.USER.getCode(),
                            amount.multiply(Double.parseDouble(rate)), orderDO.getChildOrderId());
                        //记录健康值
                        orderDO.setDevoteAmount(amount.multiply(Double.parseDouble(rate)));
                        //0个人  1非会员统一账户
                        orderDO.setDevoteAmountTo("0");
                        orderDAO.modifyDevoteByChildOrderId(orderDO);
                        // 返自己 积分    个人账户  
                        paySuccReturn(userDO, returnOrder);
                    }
                    
                    //【推荐返利】
                    //推荐人与购买人一致   不返利
                    //推荐人有团队               返利团队
                    //推荐人无团队               返利推荐人
                    String teamId = orderDO.getTeamId();
                    //订单所属门店
                    TeamDO orderTeam = teamDAO.queryTeamByTeamId(teamId);
                    //推荐人返利
                    String recommend_userid = orderDO.getRecommendId();
                    //推荐人是否为团队用户
                    QueryResult<TeamDO> recommend_user_team = userQueryService
                        .searchUserTeam(recommend_userid);
                    String thisId = recommend_userid;
                    String thisType = AccountTypeEnum.USER.getCode();
                    boolean changeRecommendType = true;
                    //没有推荐人记录
                    if (StringUtils.isBlank(recommend_userid)) {
                        if (null != orderTeam) {
                            //健康值
                            autoReceiveService.payOrderSuccForDevoteForTeam(teamId,
                                amount.multiply(Double.parseDouble(rate)),
                                orderDO.getChildOrderId());
                            //积分
                            paySuccReturnForTeam(orderTeam, returnOrder,
                                PlanCategoryEnum.PLAN_RECOMMEND.getCode());

                            thisId = teamId;
                            thisType = AccountTypeEnum.TEAM.getCode();
                        }
                    } else {
                        if (recommend_user_team.isSuccess()) {
                            //推荐门店用户  返贡献值  积分
                            TeamDO teamDO = recommend_user_team.getResultObject();
                            //健康值
                            autoReceiveService.payOrderSuccForDevoteForTeam(teamDO.getTeamId(),
                                amount.multiply(Double.parseDouble(rate)),
                                orderDO.getChildOrderId());
                            //积分
                            paySuccReturnForTeam(teamDO, returnOrder,
                                PlanCategoryEnum.PLAN_RECOMMEND.getCode());
                            thisId = teamDO.getTeamId();
                            thisType = AccountTypeEnum.TEAM.getCode();
                        } else {
                            //推荐人与购买人一致，返利给订单所属门店
                            if (StringUtils.equals(recommend_userid, orderDO.getUserId())) {
                                if (null != orderTeam) {
                                    //健康值
                                    autoReceiveService.payOrderSuccForDevoteForTeam(teamId,
                                        amount.multiply(Double.parseDouble(rate)),
                                        orderDO.getChildOrderId());
                                    //积分
                                    paySuccReturnForTeam(orderTeam, returnOrder,
                                        PlanCategoryEnum.PLAN_RECOMMEND.getCode());
                                    thisId = teamId;
                                    thisType = AccountTypeEnum.TEAM.getCode();
                                }
                            } else {
                                //返推荐用户积分
                                QueryResult<UserDO> reUserInfo = userQueryService
                                    .searchByUserIdOrOpenId(recommend_userid, null);
                                if (reUserInfo.isSuccess()) {
                                    //健康值
                                    autoReceiveService.payOrderSuccForDevote(recommend_userid,
                                        AccountTypeEnum.USER.getCode(),
                                        amount.multiply(Double.parseDouble(rate)),
                                        orderDO.getChildOrderId());
                                    //积分
                                    paySuccReturnForRecommendUser(reUserInfo.getResultObject(),
                                        returnOrder);
                                }
                            }
                        }
                    }

                    if (changeRecommendType) {
                        //记录推荐返利时候是用户还是团队
                        orderDO.setRecommendId(thisId);
                        orderDO.setRecommendType(thisType);
                        orderDO.setRecommendUserId(recommend_userid);
                        orderDAO.changeRecommendType(orderDO);
                    }
                    
                    // 发货通知
                    String msgContent = "您购买的商品" + orderDO.getProductName() + "已于"
                                        + DateUtil.getNowString() + "自动确认收货，订单号："
                                        + orderDO.getChildOrderId();
                    msgService.newMsg(orderDO.getUserId(), MsgTypeEnum.ORDER_MSG.getCode(),
                        "订单确认通知", msgContent);
                    String linkUrl = sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.USER_ORDER_DETAILS_API)
                                     + "?id="
                                     + orderDO.getChildOrderId();

                    msgService.sendWechatCustomerMsg(userDO.getOpenId(), linkUrl,
                        orderDO.getImgSrc(), "自动确认收货", msgContent);
                }
            }
        } catch (Exception e) {
            logger.error("自动确认收货异常", e);
        }
    }

    /**
     * 回调 正常返利 返个人
     * 
     * @param userDO
     * @param returnOrder
     * @param planCategory
     */
    public void paySuccReturn(UserDO userDO, List<OrderDO> returnOrder) {
        //购物返还的积分（应支付金额+庆余卡+邮费）*积分购物比例返利积分。
        //邮费已在应付金额里面了
        //订单分商品
        Map<String, List<OrderDO>> productMap = new HashMap<String, List<OrderDO>>();
        for (OrderDO orderDO : returnOrder) {
            String productId = orderDO.getProductId();
            if (productMap.containsKey(productId)) {
                List<OrderDO> list = productMap.get(productId);
                list.add(orderDO);
                productMap.put(productId, list);
            } else {
                List<OrderDO> list = new ArrayList<OrderDO>();
                list.add(orderDO);
                productMap.put(productId, list);
            }
        }

        Money allReturnPoint = new Money();
        for (Map.Entry<String, List<OrderDO>> entry : productMap.entrySet()) {
            String productId = entry.getKey();
            List<OrderDO> proOrderList = entry.getValue();

            Date today = DateUtil.getToday();
            //根据商品编号找出合适的返利方案
            PlanReturnDO planReturnDO = planReturnDAO.queryForReturnSelf(
                PlanReturnTypeEnum.SELF.getCode(), productId, today, DelFlgEnum.NOT_DEL.getCode());

            String thisReturnType = "";
            String thisReturnValue = "";
            List<LinkProStock> linkProStockIds = null;
            if (null == planReturnDO) {
                //找系统通用
                String commenReturnType = sysConfigCacheManager
                    .getConfigValue(SysConfigCacheKeyEnum.COMMEN_RETURN_TYPE);
                if (!StringUtils.equals(commenReturnType, StockReturnTypeEnum.VALUE.getCode())
                    && !StringUtils.equals(commenReturnType, StockReturnTypeEnum.RETE.getCode())) {
                    continue;
                }

                String commenReturnValue = sysConfigCacheManager
                    .getConfigValue(SysConfigCacheKeyEnum.COMMEN_RETURN_VALUE);
                String commenReturnRate = sysConfigCacheManager
                    .getConfigValue(SysConfigCacheKeyEnum.COMMEN_RETURN_RATE);

                if (StringUtils.equals(commenReturnType, StockReturnTypeEnum.VALUE.getCode())) {
                    thisReturnValue = commenReturnValue;
                }

                if (StringUtils.equals(commenReturnType, StockReturnTypeEnum.RETE.getCode())) {
                    thisReturnValue = commenReturnRate;
                }

                thisReturnType = commenReturnType;
            } else {
                //该商品方案是否配置了属性
                String linkProStock = planReturnDO.getLinkProStock();

                String returnSubType = planReturnDO.getReturnSubType();
                if (!StringUtils.equals(returnSubType, StockReturnTypeEnum.VALUE.getCode())
                    && !StringUtils.equals(returnSubType, StockReturnTypeEnum.RETE.getCode())) {
                    continue;
                }
                thisReturnValue = planReturnDO.getReturnValue();
                thisReturnType = returnSubType;

                if (StringUtils.isNotBlank(linkProStock)) {
                    linkProStockIds = JSONArray.parseArray(linkProStock, LinkProStock.class);
                }
            }

            if (StringUtils.isBlank(thisReturnValue))
                continue;

            if (CollectionUtils.isEmpty(linkProStockIds)) {
                //针对商品返利
                //计算该商品实付总金额
                Money realOrderPrice = new Money();
                Money voucherAmount = new Money();
                for (OrderDO orderDO : proOrderList) {
                    realOrderPrice = realOrderPrice.add(orderDO.getRealOrderPrice());
                    voucherAmount = voucherAmount.add(orderDO.getReduceAmountVoucher());
                }

                Money returnPoint = new Money();

                //按设置积分值
                if (StringUtils.equals(thisReturnType, StockReturnTypeEnum.VALUE.getCode())) {
                    returnPoint = new Money(thisReturnValue);
                }

                //按照实际支付金额比例
                if (StringUtils.equals(thisReturnType, StockReturnTypeEnum.RETE.getCode())) {
                    //（应支付金额+庆余卡）*积分购物比例返利积分
                    double rateValue = Double.parseDouble(thisReturnValue);
                    returnPoint = (realOrderPrice.add(voucherAmount)).multiply(rateValue);
                }

                if (returnPoint.lessEqualThan(new Money(0)))
                    continue;

                //返利积分分摊
                long[] ratiosFoPro = getRatiosForRealOrderPrice(proOrderList);
                Money[] allocaPro = returnPoint.allocate(ratiosFoPro);

                for (OrderDO orderDO : proOrderList) {
                    int indexOf = proOrderList.indexOf(orderDO);
                    Money thisReturnPoint = allocaPro[indexOf];

                    //新增订单返利记录
                    OrderRebateDO orderRebateDO = new OrderRebateDO();
                    orderRebateDO.setChildOrderId(orderDO.getChildOrderId());
                    orderRebateDO.setRebateType("1");

                    orderRebateDO.setPointAmount(thisReturnPoint);

                    autoReceiveService.newOrderRebate(orderRebateDO);
                }

                allReturnPoint = allReturnPoint.add(returnPoint);
            } else {
                //查看对应属性
                for (LinkProStock linkProStock : linkProStockIds) {
                    if (StringUtils.notEquals(linkProStock.getProductId(), productId))
                        continue;

                    List<Integer> stockIdS = linkProStock.getStockId();
                    //属性归类
                    Map<Integer, List<OrderDO>> mapForStock = new HashMap<Integer, List<OrderDO>>();
                    for (Integer stockId : stockIdS) {
                        for (OrderDO orderDO : proOrderList) {
                            int stockIdThis = orderDO.getStockId();
                            if (stockIdThis == stockId) {
                                if (mapForStock.containsKey(stockIdThis)) {
                                    List<OrderDO> list = mapForStock.get(stockIdThis);
                                    list.add(orderDO);
                                    mapForStock.put(stockIdThis, list);
                                } else {
                                    List<OrderDO> list = new ArrayList<OrderDO>();
                                    list.add(orderDO);
                                    mapForStock.put(stockIdThis, list);
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                    //商品分属性
                    for (Map.Entry<Integer, List<OrderDO>> entryStock : mapForStock.entrySet()) {

                        List<OrderDO> orderListForStock = entryStock.getValue();
                        Money realOrderPrice = new Money();
                        Money voucherAmount = new Money();
                        for (OrderDO orderDO : proOrderList) {
                            realOrderPrice = realOrderPrice.add(orderDO.getRealOrderPrice());
                            voucherAmount = voucherAmount.add(orderDO.getReduceAmountVoucher());
                        }

                        Money returnPoint = new Money();

                        //按设置积分值
                        if (StringUtils.equals(thisReturnType, StockReturnTypeEnum.VALUE.getCode())) {
                            returnPoint = new Money(thisReturnValue);
                        }

                        //按照实际支付金额比例
                        if (StringUtils.equals(thisReturnType, StockReturnTypeEnum.RETE.getCode())) {
                            double rateValue = Double.parseDouble(thisReturnValue);
                            returnPoint = (realOrderPrice.add(voucherAmount)).multiply(rateValue);
                        }

                        if (returnPoint.lessEqualThan(new Money(0)))
                            continue;

                        //返利积分分摊
                        long[] ratiosFoStock = getRatiosForRealOrderPrice(orderListForStock);
                        Money[] allocaProStock = returnPoint.allocate(ratiosFoStock);

                        for (OrderDO orderDO : orderListForStock) {
                            int indexOf = orderListForStock.indexOf(orderDO);
                            Money thisReturnPoint = allocaProStock[indexOf];

                            //新增订单返利记录
                            OrderRebateDO orderRebateDO = new OrderRebateDO();
                            orderRebateDO.setChildOrderId(orderDO.getChildOrderId());
                            orderRebateDO.setRebateType("1");

                            orderRebateDO.setPointAmount(thisReturnPoint);

                            autoReceiveService.newOrderRebate(orderRebateDO);
                        }
                        allReturnPoint = allReturnPoint.add(returnPoint);
                    }
                }
            }
        }
        autoReceiveService.payOrderSuccForReturnStock(userDO.getUserId(),
            AccountTypeEnum.USER.getCode(), allReturnPoint, returnOrder.get(0).getOrderId());
    }

    // 返利 团队
    public void paySuccReturnForTeam(TeamDO teamDO, List<OrderDO> teamOrder, String planCategory) {
        // 团队
        Money allTeamOrderAmount = new Money();
        List<Money> orderMoney = new ArrayList<Money>();
        for (OrderDO orderDO : teamOrder) {
            allTeamOrderAmount = allTeamOrderAmount.add(orderDO.getRealOrderPrice().add(
                orderDO.getReduceAmountVoucher()));
            orderMoney.add(orderDO.getRealOrderPrice().add(orderDO.getReduceAmountVoucher()));
        }
        String teamType = teamDO.getTeamType();
        int teamLevel = teamDO.getTeamLevel();
        String teamId = teamDO.getTeamId();
        // 团队
        // 先查找是否有针对该团队的返利方案
        QueryResult<PlanReturnDO> planReturn = autoReceiveService.getPlanReturn(teamType,
            teamLevel, teamId);
        if (planReturn.isSuccess()) {
            PlanReturnDO planReturnDO = planReturn.getResultObject();
            double returnRate = planReturnDO.getReturnRate();
            Money returnHu = allTeamOrderAmount.multiply(returnRate);

            autoReceiveService.payOrderSuccForReturnTeam(returnHu, teamDO.getTeamId(),
                AccountTypeEnum.TEAM.getCode(), teamOrder.get(0).getOrderId());
            // 新增返利记录
            long[] ratios = getRatios(orderMoney);
            Money[] allocate = returnHu.allocate(ratios);

            for (int i = 0; i < teamOrder.size(); i++) {
                // 分摊到每个子订单的返利积分值
                Money huAmount = allocate[i];
                OrderRebateDO orderRebateDO = new OrderRebateDO();
                orderRebateDO.setChildOrderId(teamOrder.get(i).getChildOrderId());
                orderRebateDO.setRebateType("2");
                orderRebateDO.setPointAmount(huAmount);
                autoReceiveService.newOrderRebate(orderRebateDO);
            }

        } else {
            planReturn = autoReceiveService.getPlanReturn(teamType, teamLevel, null);
            if (planReturn.isSuccess()) {
                PlanReturnDO planReturnDO = planReturn.getResultObject();
                double returnRate = planReturnDO.getReturnRate();
                Money returnHu = allTeamOrderAmount.multiply(returnRate);
                autoReceiveService.payOrderSuccForReturnTeam(returnHu, teamDO.getTeamId(),
                    AccountTypeEnum.TEAM.getCode(), teamOrder.get(0).getOrderId());

                // 新增返利记录
                long[] ratios = getRatios(orderMoney);
                Money[] allocate = returnHu.allocate(ratios);

                for (int i = 0; i < teamOrder.size(); i++) {
                    // 分摊到每个子订单的返利积分值
                    Money huAmount = allocate[i];
                    OrderRebateDO orderRebateDO = new OrderRebateDO();
                    orderRebateDO.setChildOrderId(teamOrder.get(i).getChildOrderId());
                    orderRebateDO.setRebateType("2");
                    orderRebateDO.setPointAmount(huAmount);
                    autoReceiveService.newOrderRebate(orderRebateDO);
                }
            }
        }
    }

    // 返利 推荐用户
    public void paySuccReturnForRecommendUser(UserDO recommendUser, List<OrderDO> allOrder) {
        // 团队
        Money allTeamOrderAmount = new Money();
        List<Money> orderMoney = new ArrayList<Money>();
        for (OrderDO orderDO : allOrder) {
            allTeamOrderAmount = allTeamOrderAmount.add(orderDO.getRealOrderPrice().add(
                orderDO.getReduceAmountVoucher()));
            orderMoney.add(orderDO.getRealOrderPrice().add(orderDO.getReduceAmountVoucher()));
        }
        String userType = "4";
        int userLevel = recommendUser.getUserLevel();
        String userId = recommendUser.getUserId();
        // 返推荐人
        // 先查找是否有针对该推荐人的返利方案
        QueryResult<PlanReturnDO> planReturn = autoReceiveService.getPlanReturn(userType,
            userLevel, userId);
        if (planReturn.isSuccess()) {
            PlanReturnDO planReturnDO = planReturn.getResultObject();
            double returnRate = planReturnDO.getReturnRate();
            Money returnHu = allTeamOrderAmount.multiply(returnRate);

            autoReceiveService.payOrderSuccForReturnTeam(returnHu, userId,
                AccountTypeEnum.USER.getCode(), allOrder.get(0).getOrderId());
            // 新增返利记录
            long[] ratios = getRatios(orderMoney);
            Money[] allocate = returnHu.allocate(ratios);

            for (int i = 0; i < allOrder.size(); i++) {
                // 分摊到每个子订单的返利积分值
                Money huAmount = allocate[i];
                OrderRebateDO orderRebateDO = new OrderRebateDO();
                orderRebateDO.setChildOrderId(allOrder.get(i).getChildOrderId());
                orderRebateDO.setRebateType("3");
                orderRebateDO.setPointAmount(huAmount);
                autoReceiveService.newOrderRebate(orderRebateDO);
            }

        } else {
            planReturn = autoReceiveService.getPlanReturn(userType, userLevel, null);
            if (planReturn.isSuccess()) {
                PlanReturnDO planReturnDO = planReturn.getResultObject();
                double returnRate = planReturnDO.getReturnRate();
                Money returnHu = allTeamOrderAmount.multiply(returnRate);
                autoReceiveService.payOrderSuccForReturnTeam(returnHu, userId,
                    AccountTypeEnum.TEAM.getCode(), allOrder.get(0).getOrderId());

                // 新增返利记录
                long[] ratios = getRatios(orderMoney);
                Money[] allocate = returnHu.allocate(ratios);

                for (int i = 0; i < allOrder.size(); i++) {
                    // 分摊到每个子订单的返利积分值
                    Money huAmount = allocate[i];
                    OrderRebateDO orderRebateDO = new OrderRebateDO();
                    orderRebateDO.setChildOrderId(allOrder.get(i).getChildOrderId());
                    orderRebateDO.setRebateType("3");
                    orderRebateDO.setPointAmount(huAmount);
                    autoReceiveService.newOrderRebate(orderRebateDO);
                }
            }
        }
    }

    public long[] getRatiosForRealOrderPrice(List<OrderDO> orderList) {
        long[] ratios = new long[orderList.size()];
        for (int i = 0; i < orderList.size(); i++) {
            // 去除可能存在的两位小数 multiply(100)
            ratios[i] = Long.valueOf((orderList.get(i).getRealOrderPrice().add(orderList.get(i)
                .getReduceAmountVoucher())).multiply(100).toSimpleString());
        }
        return ratios;
    }

    public long[] getRatios(List<Money> orderMoney) {
        long[] ratios = new long[orderMoney.size()];
        for (int i = 0; i < orderMoney.size(); i++) {
            // 去除可能存在的两位小数 multiply(100)
            ratios[i] = Long.valueOf(orderMoney.get(i).multiply(100).toSimpleString());
        }
        return ratios;
    }

}
