package com.onway.shunfeng.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.json.xml.XMLSerializer;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSONObject;
import com.onway.common.lang.CollectionUtils;
import com.onway.common.lang.DateUtils;
import com.onway.common.lang.StringUtils;
import com.onway.core.service.MsgService;
import com.onway.core.service.code.CodeGenerateComponent;
import com.onway.core.service.localcache.manager.SysConfigCacheManager;
import com.onway.fyapp.common.dal.daointerface.OrderDAO;
import com.onway.fyapp.common.dal.daointerface.SysExpressDAO;
import com.onway.fyapp.common.dal.daointerface.UserDAO;
import com.onway.fyapp.common.dal.dataobject.OrderDO;
import com.onway.fyapp.common.dal.dataobject.SysExpressDO;
import com.onway.fyapp.common.dal.dataobject.UserDO;
import com.onway.model.enums.BizTypeEnum;
import com.onway.model.enums.DeliveryTypeEnum;
import com.onway.model.enums.MsgTypeEnum;
import com.onway.model.enums.OrderStatusEnum;
import com.onway.model.enums.PrintFlgEnum;
import com.onway.model.enums.SysConfigCacheKeyEnum;
import com.onway.platform.common.base.BaseResult;
import com.onway.platform.common.base.QueryResult;
import com.onway.platform.common.configration.ConfigrationFactory;
import com.onway.platform.common.exception.BaseRuntimeException;
import com.onway.platform.common.service.template.AbstractServiceImpl;
import com.onway.platform.common.service.template.ServiceCheckCallback;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.result.JsonQueryResult;
import com.onway.result.express.ExpressDataPojo;
import com.onway.result.express.ExpressQueryPojo;
import com.onway.shunfeng.model.ConsignerMsg;
import com.onway.shunfeng.model.DeliverMsg;
import com.onway.shunfeng.model.OrderMailnoResult;
import com.onway.shunfeng.model.RouteRequest;
import com.onway.shunfeng.model.ShunFengResult;
import com.onway.shunfeng.model.conver.PrintConver;
import com.onway.shunfeng.model.request.AddedService;
import com.onway.shunfeng.model.request.Cargo;
import com.onway.shunfeng.model.request.Order;
import com.onway.shunfeng.print.PrintSfOrder;
import com.onway.shunfeng.util.ComplateRequest;
import com.onway.utils.DateUtil;
import com.sf.csim.express.service.CallExpressServiceTools;
import com.sf.dto.CargoInfoDto;
import com.sf.dto.WaybillDto;

/**
 * 丰桥打印
 * 
 * @author yuhang.ni
 * @version $Id: ShunfengServiceImpl.java, v 0.1 2019年3月13日 下午7:35:46 Administrator Exp $
 */
public class ShunfengServiceImpl extends AbstractServiceImpl implements ShunfengService {

    public static final Logger    logger = Logger.getLogger(ShunfengServiceImpl.class);

    private SysConfigCacheManager sysConfigCacheManager;

    private OrderDAO              orderDAO;

    private CodeGenerateComponent codeGenerateComponent;

    private UserDAO               userDAO;

    private MsgService            msgService;

    private SysExpressDAO         sysExpressDAO;

    public void setSysConfigCacheManager(SysConfigCacheManager sysConfigCacheManager) {
        this.sysConfigCacheManager = sysConfigCacheManager;
    }

    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public void setCodeGenerateComponent(CodeGenerateComponent codeGenerateComponent) {
        this.codeGenerateComponent = codeGenerateComponent;
    }

    public void setMsgService(MsgService msgService) {
        this.msgService = msgService;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setSysExpressDAO(SysExpressDAO sysExpressDAO) {
        this.sysExpressDAO = sysExpressDAO;
    }

    @SuppressWarnings({ "static-access" })
    @Override
    public JsonQueryResult<ShunFengResult> callExpressService(Order order, Cargo cargo,
                                                              AddedService addedService) {

        JsonQueryResult<ShunFengResult> queryResult = new JsonQueryResult<ShunFengResult>(false);

        //丰桥平台公共测试账号  
        //https://bsp-oisp.sf-express.com/bsp-oisp/sfexpressService
        //SLKJ2019
        //FBIqMkZjzxbsZgo7jTpeq7PD8CVzLT4Q
        String reqURL = sysConfigCacheManager.getConfigValue(SysConfigCacheKeyEnum.SF_REQ_URL);
        String clientCode = sysConfigCacheManager
            .getConfigValue(SysConfigCacheKeyEnum.SF_CLIENT_CODE);
        String checkword = sysConfigCacheManager
            .getConfigValue(SysConfigCacheKeyEnum.SF_CHECK_CODE);

        String requestXml = ComplateRequest.getRequest(order, cargo, addedService, clientCode);
        logger.info(MessageFormat.format("顺风下单请求报文，message:{0}", new Object[] { requestXml }));

        CallExpressServiceTools client = CallExpressServiceTools.getInstance();

        String respXml = client.callSfExpressServiceByCSIM(reqURL, requestXml, clientCode,
            checkword);
        if (StringUtils.isNotBlank(respXml)) {
            logger.info(MessageFormat.format("顺风下单返回报文，message:{0}", new Object[] { respXml }));
        } else {
            queryResult.setMessage("顺风下单数据返回异常!");
            return queryResult;
        }

        XMLSerializer xmlSerializer = new XMLSerializer();
        String xmlStr = xmlSerializer.read(respXml).toString();

        JSONObject resp = JSONObject.parseObject(xmlStr);
        String code = resp.getString("Head");
        // 接口请求成功
        if (code.equals("OK")) {
            JSONObject orderResponse = resp.getJSONObject("Body").getJSONObject("OrderResponse");
            // 下单返回结果  1：人工确认  2：可收派  3：不可以收派
            String filter_result = orderResponse.getString("@filter_result");
            // 顺丰运单号
            String mailno = orderResponse.getString("@mailno");
            logger.info(MessageFormat.format("----------顺丰请求成功-------，打印订单号：，mailno:{0}",
                new Object[] { mailno }));
            logger.info(MessageFormat.format(
                "----------顺丰请求成功-------，filter_result：，filter_result:{0}",
                new Object[] { filter_result }));
            ShunFengResult shunFengResult = new ShunFengResult();
            shunFengResult.setMailno(mailno);
            queryResult.setObj(shunFengResult);
        } else {
            String error = resp.getString("ERROR");
            logger.error(MessageFormat.format("----------顺丰请求失败-------：，ERROR:{0}",
                new Object[] { error }));
            queryResult.setMessage(error);
            return queryResult;
        }
        queryResult.setSuccess(true);
        return queryResult;
    }

    @Override
    public BaseResult sfGetMaino(final String[] chooseChildrenIdS, final String user_id) {
        final BaseResult result = new BaseResult(false);
        serviceTemplate.execute(result, new ServiceCheckCallback() {

            @Override
            public void executeService() {

                if (null == chooseChildrenIdS) {
                    result.setMessage("未选中订单");
                    return;
                }

                List<String> childOrderIdList = new ArrayList<String>();
                for (String childrenOrderId : chooseChildrenIdS) {
                    if (StringUtils.isNotBlank(childrenOrderId))
                        childOrderIdList.add(childrenOrderId);
                }
                if (CollectionUtils.isEmpty(childOrderIdList)) {
                    result.setMessage("未选中订单");
                    return;
                }

                //订单收货地址整理
                Map<String, OrderMailnoResult> map = new HashMap<String, OrderMailnoResult>();
                for (String childOrderId : childOrderIdList) {
                    OrderDO orderDO = orderDAO.selectByChildOrderId(childOrderId);
                    if (null == orderDO
                        || !StringUtils.equals(orderDO.getTransferType(),
                            DeliveryTypeEnum.EXPRESS.getCode()))
                        continue;

                    if (StringUtils.isNotBlank(orderDO.getCarriNo()))
                        continue;

                    String province = orderDO.getProvince();
                    String city = orderDO.getCity();
                    String area = orderDO.getArea();
                    String reAddr = orderDO.getReAddr();

                    String reUserName = orderDO.getReUserName();
                    String cell = orderDO.getCell();

                    String thisKey = province + city + area + reAddr + reUserName + cell;

                    if (map.containsKey(thisKey)) {
                        OrderMailnoResult mailnoResult = map.get(thisKey);
                        List<OrderDO> childOrderS = mailnoResult.getChildOrderS();
                        childOrderS.add(orderDO);
                        mailnoResult.setChildOrderS(childOrderS);
                        map.put(thisKey, mailnoResult);
                    } else {
                        OrderMailnoResult mailnoResult = new OrderMailnoResult();
                        mailnoResult.setProvince(province);
                        mailnoResult.setCity(city);
                        mailnoResult.setArea(area);
                        mailnoResult.setReAddr(reAddr);
                        mailnoResult.setReUserName(reUserName);
                        mailnoResult.setCell(cell);
                        List<OrderDO> childOrderS = new ArrayList<OrderDO>();
                        childOrderS.add(orderDO);
                        mailnoResult.setChildOrderS(childOrderS);
                        map.put(thisKey, mailnoResult);
                    }
                }

                if (map.isEmpty()) {
                    return;
                }

                String carriCom = sysConfigCacheManager
                    .getConfigValue(SysConfigCacheKeyEnum.SHUNFENG_CODE);

                //下单
                Set<Entry<String, OrderMailnoResult>> entrySet = map.entrySet();
                for (Entry<String, OrderMailnoResult> entry : entrySet) {
                    OrderMailnoResult orderMailnoResult = entry.getValue();

                    Order order = new Order();
                    if (ConfigrationFactory.getConfigration().isProd())
                        order.setOrderid(codeGenerateComponent
                            .nextCodeByType(BizTypeEnum.SF_ORDER_ID));
                    else
                        order.setOrderid(codeGenerateComponent
                            .nextCodeByType(BizTypeEnum.SF_ORDER_ID_DEV));
                    order.setExpress_type("1");
                    //寄件人信息
                    order.setJ_province(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_PROVINCE));
                    order.setJ_city(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_CITY));
                    order.setJ_county(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_COUNTY));
                    order.setJ_company(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_COMPANY));
                    order.setJ_contact(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_CONTACT));
                    order.setJ_tel(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_TEL));
                    order.setJ_address(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_ADDRESS));
                    //收件人信息
                    order.setD_province(orderMailnoResult.getProvince());
                    order.setD_city(orderMailnoResult.getCity());
                    order.setD_county(orderMailnoResult.getArea());
                    //                    order.setD_company("神罗科技");
                    order.setD_contact(orderMailnoResult.getReUserName());
                    order.setD_tel(orderMailnoResult.getCell());
                    order.setD_address(orderMailnoResult.getReAddr());

                    order.setParcel_quantity(1);//包裹数
                    order.setPay_method("1");
                    order.setCustid(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.SF_CUSTID));

                    List<OrderDO> childOrderS = orderMailnoResult.getChildOrderS();
                    StringBuffer buffer = new StringBuffer();
                    for (OrderDO orderDO2 : childOrderS) {
                        buffer.append(orderDO2.getProductName()).append("*");
                    }
                    Cargo cargo = new Cargo();
                    cargo.setName(buffer.toString());

                    JsonQueryResult<ShunFengResult> callExpressService = callExpressService(order,
                        cargo, null);
                    if (callExpressService.isSuccess()) {
                        ShunFengResult shunFengResult = callExpressService.getObj();
                        String mailno = shunFengResult.getMailno();
                        for (OrderDO orderDO : childOrderS) {
                            orderDAO.modifyMailno(carriCom, mailno, user_id,
                                orderDO.getChildOrderId());
                        }
                    } else {
                        throw new BaseRuntimeException(callExpressService.getMessage());
                    }
                }
                result.setSuccess(true);

            }

            @Override
            public void check() {
                AssertUtil.notBlank(user_id, "登录信息异常");
            }
        });
        return result;
    }

    @Override
    public BaseResult sfPrint(final String[] chooseChildrenIdS, final String user_id,
                              final String savePath) {
        final BaseResult result = new BaseResult(false);
        serviceTemplate.execute(result, new ServiceCheckCallback() {

            @Override
            public void executeService() {

                if (null == chooseChildrenIdS) {
                    result.setMessage("未选中订单");
                    return;
                }

                List<String> childOrderIdList = new ArrayList<String>();
                for (String childrenOrderId : chooseChildrenIdS) {
                    if (StringUtils.isNotBlank(childrenOrderId))
                        childOrderIdList.add(childrenOrderId);
                }
                if (CollectionUtils.isEmpty(childOrderIdList)) {
                    result.setMessage("未选中订单");
                    return;
                }
                //处理的已发货的物流点订单

                //订单收货地址整理     
                Map<String, OrderMailnoResult> map = new HashMap<String, OrderMailnoResult>();
                for (String childOrderId : childOrderIdList) {
                    OrderDO orderDO = orderDAO.selectByChildOrderId(childOrderId);
                    if (null == orderDO
                        || !StringUtils.equals(orderDO.getTransferType(),
                            DeliveryTypeEnum.EXPRESS.getCode()))
                        continue;

                    if (StringUtils.isBlank(orderDO.getCarriNo()))
                        continue;

                    String carriNo = orderDO.getCarriNo();
                    if (map.containsKey(carriNo)) {
                        OrderMailnoResult mailnoResult = map.get(carriNo);
                        List<OrderDO> childOrderS = mailnoResult.getChildOrderS();
                        childOrderS.add(orderDO);
                        mailnoResult.setChildOrderS(childOrderS);
                        map.put(carriNo, mailnoResult);
                    } else {
                        OrderMailnoResult mailnoResult = new OrderMailnoResult();
                        mailnoResult.setMailNo(carriNo);
                        List<OrderDO> childOrderS = new ArrayList<OrderDO>();
                        childOrderS.add(orderDO);
                        mailnoResult.setChildOrderS(childOrderS);
                        map.put(carriNo, mailnoResult);
                    }
                }

                if (map.isEmpty()) {
                    return;
                }

                String clientCode = sysConfigCacheManager
                    .getConfigValue(SysConfigCacheKeyEnum.SF_CLIENT_CODE);
                String checkWord = sysConfigCacheManager
                    .getConfigValue(SysConfigCacheKeyEnum.SF_CHECK_CODE);

                String savePath = sysConfigCacheManager
                    .getConfigValue(SysConfigCacheKeyEnum.SAVE_PATH);

                Map<String, List<OrderDO>> mapForSend = new HashMap<String, List<OrderDO>>();
                Set<Entry<String, OrderMailnoResult>> entrySet = map.entrySet();
                List<String> files = new ArrayList<String>();
                for (Entry<String, OrderMailnoResult> entry : entrySet) {
                    String mailNo = entry.getKey();

                    //查出所有和这个运单号相关的订单
                    List<OrderDO> queryByMailno = orderDAO.queryByMailno(mailNo);
                    if (CollectionUtils.isEmpty(queryByMailno))
                        continue;

                    List<CargoInfoDto> cargoInfoList = new ArrayList<CargoInfoDto>();
                    for (OrderDO orderDO : queryByMailno) {
                        CargoInfoDto cargo = new CargoInfoDto();
                        cargo.setCargo(orderDO.getProductName());
                        cargo.setCargoCount((int) orderDO.getOrderCount());
                        cargo.setCargoUnit("件");
                        //                        cargo.setSku("00015645");
                        cargo.setRemark(" ");
                        cargoInfoList.add(cargo);
                    }

                    ConsignerMsg consignerMsg = new ConsignerMsg();
                    consignerMsg.setConsignerProvince(queryByMailno.get(0).getProvince());
                    consignerMsg.setConsignerCity(queryByMailno.get(0).getCity());
                    consignerMsg.setConsignerCounty(queryByMailno.get(0).getArea());
                    consignerMsg.setConsignerAddress(queryByMailno.get(0).getReAddr());
                    consignerMsg.setConsignerMobile(queryByMailno.get(0).getCell());
                    consignerMsg.setConsignerName(queryByMailno.get(0).getReUserName());

                    DeliverMsg deliverMsg = new DeliverMsg();
                    deliverMsg.setDeliverProvince(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_PROVINCE));
                    deliverMsg.setDeliverCity(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_CITY));
                    deliverMsg.setDeliverCounty(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_COUNTY));
                    deliverMsg.setDeliverCompany(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_COMPANY));
                    deliverMsg.setDeliverAddress(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_ADDRESS));//详细地址建议最多30个字  字段过长影响打印效果
                    deliverMsg.setDeliverName(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_CONTACT));
                    deliverMsg.setDeliverMobile(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_MOBILE));
                    deliverMsg.setDeliverTel(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_TEL));
                    deliverMsg.setMonthAccount(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.SF_CUSTID));

                    List<WaybillDto> waybillDtoList = PrintConver.buildWaybillDtoList(clientCode,
                        checkWord, mailNo, cargoInfoList, consignerMsg, deliverMsg);

                    try {
                        PrintSfOrder.print(waybillDtoList, savePath, savePath, files);
                    } catch (Exception e) {
                        logger.error(MessageFormat.format("----------运单图片生成异常！-------：，e:{0}",
                            new Object[] { e.getMessage() }));
                        throw new BaseRuntimeException("运单图片生成异常！");
                    }

                    mapForSend.put(mailNo, queryByMailno);
                }

                //订单发货
                //                Set<Entry<String, List<OrderDO>>> entrySet2 = mapForSend.entrySet();
                //                for (Entry<String, List<OrderDO>> entry : entrySet2) {
                //                    String mailNo = entry.getKey();
                //
                //                    List<OrderDO> orderList = entry.getValue();
                //                    for (OrderDO orderDO : orderList) {
                //                        UserDO userInfo = userDAO.finduserbyid(orderDO.getUserId());
                //
                //                        //修改打印状态
                //                        orderDAO.modifyPrintFlg(PrintFlgEnum.HSA_PRINT.getCode(),
                //                            orderDO.getChildOrderId());
                //
                //                        //修改发货状态
                //                        orderDAO.modifyOrderStatus(OrderStatusEnum.HAS_SEND.getCode(), user_id,
                //                            orderDO.getChildOrderId());
                //
                //                        if (null != userInfo) {
                //                            //发货通知
                //                            String msgContent = "您购买的商品" + orderDO.getProductName() + "已于"
                //                                                + DateUtil.getNowString() + "发货，运单号：" + mailNo;
                //                            msgService.newMsg(userInfo.getUserId(),
                //                                MsgTypeEnum.ORDER_MSG.getCode(), "订单发货通知", msgContent);
                //
                //                            String linkUrl = sysConfigCacheManager
                //                                .getConfigValue(SysConfigCacheKeyEnum.USER_ORDER_DETAILS_API)
                //                                             + "?id=" + orderDO.getChildOrderId();
                //
                //                            msgService.sendWechatCustomerMsg(userInfo.getOpenId(), linkUrl,
                //                                orderDO.getImgSrc(), "订单发货通知", msgContent);
                //                        }
                //                    }
                //                }

                //打印
                try {
                    PrintSfOrder.print(files);
                } catch (Exception e) {
                    logger.error(MessageFormat.format("----------运单图片打印异常！-------：，e:{0}",
                        new Object[] { e.getMessage() }));
                }

                result.setSuccess(true);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(user_id, "登录信息异常");
                //                AssertUtil.notBlank(savePath, "请输入本地一键创建的文件夹路径");
            }
        });
        return result;
    }

    @Override
    public QueryResult<List<String>> sfGetMainoAndPrint(final String[] chooseChildrenIdS,
                                                        final String user_id) {
        final QueryResult<List<String>> result = new QueryResult<List<String>>(false);
        serviceTemplate.execute(result, new ServiceCheckCallback() {

            @Override
            public void executeService() {

                if (null == chooseChildrenIdS) {
                    result.setMessage("未选中订单");
                    return;
                }

                List<String> childOrderIdList = new ArrayList<String>();
                for (String childrenOrderId : chooseChildrenIdS) {
                    if (StringUtils.isNotBlank(childrenOrderId))
                        childOrderIdList.add(childrenOrderId);
                }
                if (CollectionUtils.isEmpty(childOrderIdList)) {
                    result.setMessage("未选中订单");
                    return;
                }

                //                if(!ConfigrationFactory.getConfigration().isProd()){
                //                    result.setSuccess(true);
                //                    result.setResultObject(childOrderIdList);
                //                    return;
                //                }

                //订单收货地址整理
                Map<String, OrderMailnoResult> map = new HashMap<String, OrderMailnoResult>();
                for (String childOrderId : childOrderIdList) {
                    OrderDO orderDO = orderDAO.selectByChildOrderId(childOrderId);
                    if (null == orderDO
                        || !StringUtils.equals(orderDO.getTransferType(),
                            DeliveryTypeEnum.EXPRESS.getCode()))
                        continue;

                    if (StringUtils.isNotBlank(orderDO.getCarriNo()))
                        continue;

                    String province = orderDO.getProvince();
                    String city = orderDO.getCity();
                    String area = orderDO.getArea();
                    String reAddr = orderDO.getReAddr();

                    String reUserName = orderDO.getReUserName();
                    String cell = orderDO.getCell();

                    String thisKey = province + city + area + reAddr + reUserName + cell;

                    if (map.containsKey(thisKey)) {
                        OrderMailnoResult mailnoResult = map.get(thisKey);
                        List<OrderDO> childOrderS = mailnoResult.getChildOrderS();
                        childOrderS.add(orderDO);
                        mailnoResult.setChildOrderS(childOrderS);
                        map.put(thisKey, mailnoResult);
                    } else {
                        OrderMailnoResult mailnoResult = new OrderMailnoResult();
                        mailnoResult.setProvince(province);
                        mailnoResult.setCity(city);
                        mailnoResult.setArea(area);
                        mailnoResult.setReAddr(reAddr);
                        mailnoResult.setReUserName(reUserName);
                        mailnoResult.setCell(cell);
                        List<OrderDO> childOrderS = new ArrayList<OrderDO>();
                        childOrderS.add(orderDO);
                        mailnoResult.setChildOrderS(childOrderS);
                        map.put(thisKey, mailnoResult);
                    }
                }

                if (map.isEmpty()) {
                    return;
                }

                String carriCom = sysConfigCacheManager
                    .getConfigValue(SysConfigCacheKeyEnum.SHUNFENG_CODE);

                Map<String, List<OrderDO>> mapMailno = new HashMap<String, List<OrderDO>>();

                //下单
                Set<Entry<String, OrderMailnoResult>> entrySet = map.entrySet();
                for (Entry<String, OrderMailnoResult> entry : entrySet) {
                    OrderMailnoResult orderMailnoResult = entry.getValue();

                    Order order = new Order();
                    if (ConfigrationFactory.getConfigration().isProd())
                        order.setOrderid(codeGenerateComponent
                            .nextCodeByType(BizTypeEnum.SF_ORDER_ID));
                    else
                        order.setOrderid(codeGenerateComponent
                            .nextCodeByType(BizTypeEnum.SF_ORDER_ID_DEV));
                    order.setExpress_type("1");
                    //寄件人信息
                    order.setJ_province(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_PROVINCE));
                    order.setJ_city(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_CITY));
                    order.setJ_county(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_COUNTY));
                    order.setJ_company(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_COMPANY));
                    order.setJ_contact(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_CONTACT));
                    order.setJ_tel(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_TEL));
                    order.setJ_address(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_ADDRESS));
                    //收件人信息
                    order.setD_province(orderMailnoResult.getProvince());
                    order.setD_city(orderMailnoResult.getCity());
                    order.setD_county(orderMailnoResult.getArea());
                    //                    order.setD_company("神罗科技");
                    order.setD_contact(orderMailnoResult.getReUserName());
                    order.setD_tel(orderMailnoResult.getCell());
                    order.setD_address(orderMailnoResult.getReAddr());

                    order.setParcel_quantity(1);//包裹数
                    order.setPay_method("1");
                    order.setCustid(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.SF_CUSTID));

                    List<OrderDO> childOrderS = orderMailnoResult.getChildOrderS();
                    StringBuffer buffer = new StringBuffer();
                    for (OrderDO orderDO2 : childOrderS) {
                        buffer.append(orderDO2.getProductName()).append("*");
                    }
                    Cargo cargo = new Cargo();
                    cargo.setName(buffer.toString());

                    JsonQueryResult<ShunFengResult> callExpressService = callExpressService(order,
                        cargo, null);
                    if (callExpressService.isSuccess()) {
                        ShunFengResult shunFengResult = callExpressService.getObj();
                        String mailno = shunFengResult.getMailno();
                        for (OrderDO orderDO : childOrderS) {
                            orderDAO.modifyMailno(carriCom, mailno, user_id,
                                orderDO.getChildOrderId());
                        }
                        mapMailno.put(mailno, childOrderS);
                    } else {
                        throw new BaseRuntimeException(callExpressService.getMessage());
                    }
                }

                String clientCode = sysConfigCacheManager
                    .getConfigValue(SysConfigCacheKeyEnum.SF_CLIENT_CODE);
                String checkWord = sysConfigCacheManager
                    .getConfigValue(SysConfigCacheKeyEnum.SF_CHECK_CODE);

                String savePath = sysConfigCacheManager
                    .getConfigValue(SysConfigCacheKeyEnum.SAVE_PATH);

                Map<String, List<OrderDO>> mapForSend = new HashMap<String, List<OrderDO>>();
                Set<Entry<String, List<OrderDO>>> entrySetMailno = mapMailno.entrySet();
                List<String> files = new ArrayList<String>();
                for (Entry<String, List<OrderDO>> entry : entrySetMailno) {
                    String mailNo = entry.getKey();

                    //查出所有和这个运单号相关的订单
                    List<OrderDO> queryByMailno = entry.getValue();
                    if (CollectionUtils.isEmpty(queryByMailno))
                        continue;

                    List<CargoInfoDto> cargoInfoList = new ArrayList<CargoInfoDto>();
                    for (OrderDO orderDO : queryByMailno) {
                        CargoInfoDto cargo = new CargoInfoDto();
                        cargo.setCargo(orderDO.getProductName());
                        cargo.setCargoCount((int) orderDO.getOrderCount());
                        cargo.setCargoUnit("件");
                        //                        cargo.setSku("00015645");
                        cargo.setRemark(" ");
                        cargoInfoList.add(cargo);
                    }

                    ConsignerMsg consignerMsg = new ConsignerMsg();
                    consignerMsg.setConsignerProvince(queryByMailno.get(0).getProvince());
                    consignerMsg.setConsignerCity(queryByMailno.get(0).getCity());
                    consignerMsg.setConsignerCounty(queryByMailno.get(0).getArea());
                    consignerMsg.setConsignerAddress(queryByMailno.get(0).getReAddr());
                    consignerMsg.setConsignerMobile(queryByMailno.get(0).getCell());
                    consignerMsg.setConsignerName(queryByMailno.get(0).getReUserName());

                    DeliverMsg deliverMsg = new DeliverMsg();
                    deliverMsg.setDeliverProvince(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_PROVINCE));
                    deliverMsg.setDeliverCity(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_CITY));
                    deliverMsg.setDeliverCounty(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_COUNTY));
                    deliverMsg.setDeliverCompany(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_COMPANY));
                    deliverMsg.setDeliverAddress(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_ADDRESS));//详细地址建议最多30个字  字段过长影响打印效果
                    deliverMsg.setDeliverName(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_CONTACT));
                    deliverMsg.setDeliverMobile(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_MOBILE));
                    deliverMsg.setDeliverTel(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.J_TEL));
                    deliverMsg.setMonthAccount(sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.SF_CUSTID));

                    List<WaybillDto> waybillDtoList = PrintConver.buildWaybillDtoList(clientCode,
                        checkWord, mailNo, cargoInfoList, consignerMsg, deliverMsg);

                    try {
                        PrintSfOrder.print(waybillDtoList, savePath, savePath, files);
                    } catch (Exception e) {
                        logger.error(MessageFormat.format("----------运单图片生成异常！-------：，e:{0}",
                            new Object[] { e.getMessage() }));
                        throw new BaseRuntimeException("运单图片生成异常！");
                    }
                    mapForSend.put(mailNo, queryByMailno);
                }

                List<String> childOrderIdSucc = new ArrayList<String>();
                //订单发货
                Set<Entry<String, List<OrderDO>>> entrySet2 = mapForSend.entrySet();
                for (Entry<String, List<OrderDO>> entry : entrySet2) {
                    String mailNo = entry.getKey();

                    List<OrderDO> orderList = entry.getValue();
                    for (OrderDO orderDO : orderList) {
                        UserDO userInfo = userDAO.finduserbyid(orderDO.getUserId());

                        //修改打印状态
                        orderDAO.modifyPrintFlg(PrintFlgEnum.HSA_PRINT.getCode(),
                            orderDO.getChildOrderId());

                        //修改发货状态
                        orderDAO.modifyOrderStatus(OrderStatusEnum.HAS_SEND.getCode(), user_id,
                            orderDO.getChildOrderId());

                        if (null != userInfo) {
                            //发货通知
                            String msgContent = "您购买的商品" + orderDO.getProductName() + "已于"
                                                + DateUtil.getNowString() + "发货，运单号：" + mailNo;
                            msgService.newMsg(userInfo.getUserId(),
                                MsgTypeEnum.ORDER_MSG.getCode(), "订单发货通知", msgContent);

                            String linkUrl = sysConfigCacheManager
                                .getConfigValue(SysConfigCacheKeyEnum.USER_ORDER_DETAILS_API)
                                             + "?id=" + orderDO.getChildOrderId();

                            msgService.sendWechatCustomerMsg(userInfo.getOpenId(), linkUrl,
                                orderDO.getImgSrc(), "订单发货通知", msgContent);
                        }
                        childOrderIdSucc.add(orderDO.getChildOrderId());
                    }
                }

                //打印
                try {
                    PrintSfOrder.print(files);
                } catch (RuntimeException e) {
                    logger.error(MessageFormat.format("----------运单图片打印异常！-------：，e:{0}",
                        new Object[] { e.getMessage() }));
                } catch (Exception e) {
                    logger.error(MessageFormat.format("----------运单图片打印异常！-------：，e:{0}",
                        new Object[] { e.getMessage() }));
                }

                result.setSuccess(true);
                result.setResultObject(childOrderIdSucc);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(user_id, "登录信息异常");
            }
        });
        return result;
    }

    @SuppressWarnings("static-access")
    @Override
    public JsonQueryResult<ExpressQueryPojo> routeRequest(String orderId) {

        JsonQueryResult<ExpressQueryPojo> queryResult = new JsonQueryResult<ExpressQueryPojo>(false);

        OrderDO orderDO = orderDAO.selectByChildOrderId(orderId);
        if (null == orderDO || StringUtils.isBlank(orderDO.getCarriNo())) {
            return queryResult;
        }

        SysExpressDO expressDO = sysExpressDAO.selectByExpressNo(orderDO.getCarriCom());
        if (null == expressDO) {
            throw new RuntimeException("快递公司记录不存在");
        }

        String mailno = orderDO.getCarriNo();

        //丰桥平台公共测试账号  
        //https://bsp-oisp.sf-express.com/bsp-oisp/sfexpressService
        //SLKJ2019
        //FBIqMkZjzxbsZgo7jTpeq7PD8CVzLT4Q
        String reqURL = sysConfigCacheManager.getConfigValue(SysConfigCacheKeyEnum.SF_REQ_URL);
        String clientCode = sysConfigCacheManager
            .getConfigValue(SysConfigCacheKeyEnum.SF_CLIENT_CODE);
        String checkword = sysConfigCacheManager
            .getConfigValue(SysConfigCacheKeyEnum.SF_CHECK_CODE);

        RouteRequest request = new RouteRequest(1, mailno, 1);
        String requestXml = ComplateRequest.format_routeRequest_xml(clientCode, request);
        logger.info(MessageFormat.format("路由请求报文，message:{0}", new Object[] { requestXml }));

        CallExpressServiceTools client = CallExpressServiceTools.getInstance();

        String respXml = client.callSfExpressServiceByCSIM(reqURL, requestXml, clientCode,
            checkword);
        if (StringUtils.isNotBlank(respXml)) {
            logger.info(MessageFormat.format("路由请求返回报文，message:{0}", new Object[] { respXml }));
        } else {
            queryResult.setMessage("路由请求数据返回异常!");
            return queryResult;
        }

        ExpressQueryPojo pojo = new ExpressQueryPojo();
        try {
            Document document = DocumentHelper.parseText(respXml);
            Element rootElement = document.getRootElement();

            Element element2 = rootElement.element("Head");
            if (null != element2 && StringUtils.equals(element2.getText(), "OK")) {
                Element element3 = rootElement.element("Body");
                if (null != element3) {
                    Element element31 = element3.element("RouteResponse");
                    if (null != element31) {
                        @SuppressWarnings("unchecked")
                        List<Element> elements = element31.elements("Route");
                        List<ExpressDataPojo> data = new ArrayList<ExpressDataPojo>();
                        for (Element element : elements) {
                            ExpressDataPojo expressDataPojo = new ExpressDataPojo();
                            String time = element.attributeValue("accept_time");
                            Date dateTime = DateUtil.stringToDate(time, DateUtils.newFormat);
                            String date = DateUtil.dateToString(dateTime, DateUtils.webFormat);
                            String expressTime = DateUtils.format(dateTime, DateUtils.HH_MM);
                            expressDataPojo.setDate(date);
                            expressDataPojo.setExpressTime(expressTime);
                            expressDataPojo.setContext(element.attributeValue("remark"));
                            expressDataPojo.setLocation(element.attributeValue("accept_address"));
                            data.add(expressDataPojo);
                        }
                        pojo.setData(data);
                        pojo.setCompanyName(expressDO.getExpressName());
                        pojo.setOrderId(orderId);
                        pojo.setProImg(orderDO.getImgSrc());
                    } else {
                        queryResult.setMessage("路由请求数据返回异常!");
                        return queryResult;
                    }
                } else {
                    queryResult.setMessage("路由请求数据返回异常!");
                    return queryResult;
                }
            } else {
                queryResult.setMessage("路由请求数据返回异常!");
                return queryResult;
            }
        } catch (DocumentException e) {
            logger.error(MessageFormat.format("----------路由请求失败-------：，ERROR:{0}",
                new Object[] { e.getMessage() }));
            queryResult.setMessage("路由请求数据返回异常!");
            return queryResult;
        }

        queryResult.setSuccess(true);
        queryResult.setObj(pojo);
        return queryResult;
    }

    @SuppressWarnings("static-access")
    @Override
    public JsonQueryResult<ExpressQueryPojo> routeRequest(String comName, String mun) {

        JsonQueryResult<ExpressQueryPojo> queryResult = new JsonQueryResult<ExpressQueryPojo>(false);

        String mailno = mun;

        //丰桥平台公共测试账号  
        //https://bsp-oisp.sf-express.com/bsp-oisp/sfexpressService
        //SLKJ2019
        //FBIqMkZjzxbsZgo7jTpeq7PD8CVzLT4Q
        String reqURL = sysConfigCacheManager.getConfigValue(SysConfigCacheKeyEnum.SF_REQ_URL);
        String clientCode = sysConfigCacheManager
            .getConfigValue(SysConfigCacheKeyEnum.SF_CLIENT_CODE);
        String checkword = sysConfigCacheManager
            .getConfigValue(SysConfigCacheKeyEnum.SF_CHECK_CODE);

        RouteRequest request = new RouteRequest(1, mailno, 1);
        String requestXml = ComplateRequest.format_routeRequest_xml(clientCode, request);
        logger.info(MessageFormat.format("路由请求报文，message:{0}", new Object[] { requestXml }));

        CallExpressServiceTools client = CallExpressServiceTools.getInstance();

        String respXml = client.callSfExpressServiceByCSIM(reqURL, requestXml, clientCode,
            checkword);
        if (StringUtils.isNotBlank(respXml)) {
            logger.info(MessageFormat.format("路由请求返回报文，message:{0}", new Object[] { respXml }));
        } else {
            queryResult.setMessage("路由请求数据返回异常!");
            return queryResult;
        }

        ExpressQueryPojo pojo = new ExpressQueryPojo();
        try {
            Document document = DocumentHelper.parseText(respXml);
            Element rootElement = document.getRootElement();

            Element element2 = rootElement.element("Head");
            if (null != element2 && StringUtils.equals(element2.getText(), "OK")) {
                Element element3 = rootElement.element("Body");
                if (null != element3) {
                    Element element31 = element3.element("RouteResponse");
                    if (null != element31) {
                        @SuppressWarnings("unchecked")
                        List<Element> elements = element31.elements("Route");
                        List<ExpressDataPojo> data = new ArrayList<ExpressDataPojo>();
                        for (Element element : elements) {
                            ExpressDataPojo expressDataPojo = new ExpressDataPojo();
                            String time = element.attributeValue("accept_time");
                            Date dateTime = DateUtil.stringToDate(time, DateUtils.newFormat);
                            String date = DateUtil.dateToString(dateTime, DateUtils.webFormat);
                            String expressTime = DateUtils.format(dateTime, DateUtils.HH_MM);
                            expressDataPojo.setTime(time);
                            expressDataPojo.setDate(date);
                            expressDataPojo.setExpressTime(expressTime);
                            expressDataPojo.setContext(element.attributeValue("remark"));
                            expressDataPojo.setLocation(element.attributeValue("accept_address"));
                            data.add(expressDataPojo);
                        }
                        Collections.reverse(data);
                        pojo.setData(data);
                        pojo.setCompanyName(comName);
                    } else {
                        queryResult.setMessage("路由请求数据返回异常!");
                        return queryResult;
                    }
                } else {
                    queryResult.setMessage("路由请求数据返回异常!");
                    return queryResult;
                }
            } else {
                queryResult.setMessage("路由请求数据返回异常!");
                return queryResult;
            }
        } catch (DocumentException e) {
            logger.error(MessageFormat.format("----------路由请求失败-------：，ERROR:{0}",
                new Object[] { e.getMessage() }));
            queryResult.setMessage("路由请求数据返回异常!");
            return queryResult;
        }

        queryResult.setSuccess(true);
        queryResult.setObj(pojo);
        return queryResult;
    }
}
