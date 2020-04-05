package com.onway.shunfeng.model.conver;

import java.util.ArrayList;
import java.util.List;
import com.onway.common.lang.CollectionUtils;
import com.onway.shunfeng.model.ConsignerMsg;
import com.onway.shunfeng.model.DeliverMsg;
import com.sf.dto.CargoInfoDto;
import com.sf.dto.RlsInfoDto;
import com.sf.dto.WaybillDto;

public class PrintConver {

    public static List<WaybillDto> buildWaybillDtoList(String clientCode, String checkWord,
                                                       String mailNo,
                                                       List<CargoInfoDto> cargoInfoList,
                                                       ConsignerMsg consignerMsg,
                                                       DeliverMsg deliverMsg) {
        if (CollectionUtils.isEmpty(cargoInfoList))
            return null;

        List<WaybillDto> waybillDtoList = new ArrayList<WaybillDto>();

        WaybillDto dto = new WaybillDto();
        //这个必填 
        dto.setAppId(clientCode);//对应clientCode
        dto.setAppKey(checkWord);//对应checkWord
        dto.setMailNo(mailNo);

        //收件人信息  
        dto.setConsignerProvince(consignerMsg.getConsignerProvince());
        dto.setConsignerCity(consignerMsg.getConsignerCity());
        dto.setConsignerCounty(consignerMsg.getConsignerCounty());
        dto.setConsignerAddress(consignerMsg.getConsignerAddress()); //详细地址建议最多30个字  字段过长影响打印效果
        //        dto.setConsignerCompany("神一样的科技");
        dto.setConsignerMobile(consignerMsg.getConsignerMobile());
        dto.setConsignerName(consignerMsg.getConsignerName());
        //        dto.setConsignerShipperCode("518052");
        //        dto.setConsignerTel("0755-33123456");

        //寄件人信息
        dto.setDeliverProvince(deliverMsg.getDeliverProvince());
        dto.setDeliverCity(deliverMsg.getDeliverCity());
        dto.setDeliverCounty(deliverMsg.getDeliverCounty());
        dto.setDeliverCompany(deliverMsg.getDeliverCompany());
        dto.setDeliverAddress(deliverMsg.getDeliverAddress());//详细地址建议最多30个字  字段过长影响打印效果
        dto.setDeliverName(deliverMsg.getDeliverName());
        dto.setDeliverMobile(deliverMsg.getDeliverMobile());
        dto.setDeliverShipperCode(deliverMsg.getDeliverShipperCode());
        dto.setDeliverTel(deliverMsg.getDeliverTel());

        //        dto.setDestCode("755");//目的地代码 参考顺丰地区编号
        //        dto.setZipCode("571");//原寄地代码 参考顺丰地区编号

        //陆运E标示
        //业务类型为“电商特惠、顺丰特惠、电商专配、陆运件”则必须打印E标识，用以提示中转场分拣为陆运    
        dto.setElectric("E");

        //快递类型  
        //1 ：标准快递   2.顺丰特惠   3： 电商特惠   5：顺丰次晨  6：顺丰即日  7.电商速配   15：生鲜速配     
        dto.setExpressType(2);

        //COD代收货款金额,只需填金额, 单位元- 此项和月结卡号绑定的增值服务相关
        //        dto.setCodValue("999.9");

        //        dto.setInsureValue("501");//声明货物价值的保价金额,只需填金额,单位元
        dto.setMonthAccount(deliverMsg.getMonthAccount());//月结卡号  
        dto.setPayMethod(1);//

        /**丰密运单相关-如非使用丰密运单模板 不需要设置以下值**/

        List<RlsInfoDto> rlsInfoDtoList = new ArrayList<RlsInfoDto>();
        RlsInfoDto rlsMain = new RlsInfoDto();
        //主运单号
        rlsMain.setWaybillNo(dto.getMailNo());
        rlsMain.setDestRouteLabel("755WE-571A3");
        rlsMain.setPrintIcon("11110000");
        rlsMain.setProCode("T4");
        rlsMain.setAbFlag("A");
        rlsMain.setXbFlag("XB");
        rlsMain.setCodingMapping("F33");
        rlsMain.setCodingMappingOut("1A");
        rlsMain.setDestTeamCode("012345678");
        rlsMain.setSourceTransferCode("021WTF");
        //对应下订单设置路由标签返回字段twoDimensionCode 该参
        rlsMain
            .setQrcode("MMM={'k1':'755WE','k2':'021WT','k3':'','k4':'T4','k5':'755123456789','k6':''}");
        rlsInfoDtoList.add(rlsMain);

        if (dto.getReturnTrackingNo() != null) {
            RlsInfoDto rlsBack = new RlsInfoDto();
            //签回运单号Z
            rlsBack.setWaybillNo(dto.getReturnTrackingNo());
            rlsBack.setDestRouteLabel("021WTF");
            rlsBack.setPrintIcon("11110000");
            rlsBack.setProCode("T4");
            rlsBack.setAbFlag("A");
            rlsBack.setXbFlag("XB");
            rlsBack.setCodingMapping("1A");
            rlsBack.setCodingMappingOut("F33");
            rlsBack.setDestTeamCode("87654321");
            rlsBack.setSourceTransferCode("755WE-571A3");
            //对应下订单设置路由标签返回字段twoDimensionCode 该参
            rlsBack
                .setQrcode("MMM={'k1':'21WT','k2':'755WE','k3':'','k4':'T4','k5':'443123456789','k6':''}");
            rlsInfoDtoList.add(rlsBack);
        }

        //设置丰密运单必要参数
        dto.setRlsInfoDtoList(rlsInfoDtoList);
        //客户个性化Logo 必须是个可以访问的图片本地路径地址或者互联网图片地址
        //dto.setCustLogo("D:\\ibm.jpg");

        //备注相关
        //        dto.setMainRemark("这是主运单的备注");
        //        dto.setChildRemark("子单号备注");
        //        dto.setReturnTrackingRemark("迁回单备注");

        //加密项
        dto.setEncryptCustName(true);//加密寄件人及收件人名称
        dto.setEncryptMobile(true);//加密寄件人及收件人联系手机   

        dto.setCargoInfoDtoList(cargoInfoList);

        waybillDtoList.add(dto);

        return waybillDtoList;
    }

}
