package com.onway.web.controller.excel;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.onway.common.lang.Money;
import com.onway.common.lang.StringUtils;
import com.onway.core.service.StockPriceComponent;
import com.onway.fyapp.common.dal.dataobject.StockPriceDO;
import com.onway.model.enums.AccountLogFlgEnum;
import com.onway.model.enums.AccountLogTypeEnum;
import com.onway.model.enums.DelFlgEnum;
import com.onway.model.enums.TeamLevelEnum;
import com.onway.model.enums.TeamTypeEnum;
import com.onway.model.enums.UserLevelEnum;
import com.onway.model.excel.ImportOrderSend;
import com.onway.platform.common.base.BaseResult;
import com.onway.platform.common.exception.BaseRuntimeException;
import com.onway.utils.BigdecimalUtil;
import com.onway.utils.DateUtil;
import com.onway.utils.excel.ExcelUtil;
import com.onway.utils.excel.ExportExcel;
import com.onway.web.controller.AbstractController;

/**
 * excel  
 * 
 * @author yuhang.ni
 * @version $Id: ExcelController.java, v 0.1 2018年12月8日 下午4:01:49 Administrator Exp $
 */
@Controller
public class ExcelController extends AbstractController {

    @Resource
    private StockPriceComponent stockPriceComponent;

    /**
     * 
     * 
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("/importExcelUtil.do")
    @ResponseBody
    public Object importExcelUtil(@RequestParam(required = false) final MultipartFile file,
                                  final HttpServletRequest request) {

        String user_id = request.getSession().getAttribute("user_id").toString();

        String goodNo = request.getParameter("goodNo");

        ExcelUtil poi = new ExcelUtil();
        List<List<String>> list = poi.read(file);

        List<StockPriceDO> importStockPriceDO = null;
        if (list != null) {
            //标题行
            List<String> title = list.get(0);
            if (!title.get(0).contains("属性名（英文逗号隔开）") || !title.get(1).contains("属性值名（英文逗号隔开）")) {
                throw new BaseRuntimeException("您的表格格式不正确，请按照模板上传！");
            }
            importStockPriceDO = new LinkedList<StockPriceDO>();
            for (int i = 1; i < list.size(); i++) {
                List<String> cellList = list.get(i);
                if (StringUtils.isBlank(cellList.get(0)))
                    continue;

                StockPriceDO stockPriceDO = new StockPriceDO();
                for (int j = 0; j < cellList.size(); j++) {
                    if (j == 0) {
                        String attrNames = cellList.get(j);
                        stockPriceDO.setAttrNames(attrNames);
                    } else if (j == 1) {
                        String valuees = cellList.get(j);
                        stockPriceDO.setValuees(valuees);
                    } else if (j == 2) {
                        String payType = cellList.get(j);
                        stockPriceDO.setPayType(payType);
                    } else if (j == 3) {
                        String price = cellList.get(j);
                        stockPriceDO.setPrice(new Money(price));
                    } else if (j == 4) {
                        String goodAmount = cellList.get(j);
                        stockPriceDO.setGoodAmount(new Money(goodAmount));
                    } else if (j == 5) {
                        String point = cellList.get(j);
                        stockPriceDO.setPoint(new Money(point));
                    } else if (j == 6) {
                        String pointRate = cellList.get(j);
                        stockPriceDO.setPointRate(Double.parseDouble(pointRate));
                    } else if (j == 7) {
                        String stock = cellList.get(j);
                        stockPriceDO.setStock(Double.parseDouble(stock));
                    } else if (j == 8) {
                        String pickLimit = cellList.get(j);
                        stockPriceDO.setPickLimit(Double.parseDouble(pickLimit));
                    } else if (j == 9) {
                        String barCode = cellList.get(j);
                        stockPriceDO.setBarCode(barCode);
                    } else if (j == 10) {
                        String erpNo = cellList.get(j);
                        stockPriceDO.setErpNo(erpNo);
                    } else if (j == 11) {
                        String controlStock = cellList.get(j);
                        stockPriceDO.setControlStock(controlStock);
                    } else if (j == 12) {
                        String img = cellList.get(j);
                        stockPriceDO.setImgSrc(img);
                    }
                }
                if (null != stockPriceDO)
                    importStockPriceDO.add(stockPriceDO);
            }
        }

        BaseResult baseResult = stockPriceComponent.importStockPriceForGoods(goodNo, user_id,
            importStockPriceDO);

        return baseResult;
    }

    @RequestMapping("/importExcelForOrderSend.do")
    @ResponseBody
    public Object importExcelForOrderSend(@RequestParam(required = false) final MultipartFile file,
                                          final HttpServletRequest request) {

        String user_id = request.getSession().getAttribute("user_id").toString();

        ExcelUtil poi = new ExcelUtil();
        List<List<String>> list = poi.read(file);

        List<ImportOrderSend> importOrderSendDO = null;
        if (list != null) {
            //标题行
            List<String> title = list.get(0);
            if (!title.get(0).contains("订单时间") || !title.get(1).contains("收货人")) {
                throw new BaseRuntimeException("您的表格格式不正确，请按照下载模板上传！");
            }
            importOrderSendDO = new LinkedList<ImportOrderSend>();
            for (int i = 1; i < list.size(); i++) {
                List<String> cellList = list.get(i);
                if (StringUtils.isBlank(cellList.get(0)))
                    continue;

                ImportOrderSend importOrderSend = new ImportOrderSend();
                for (int j = 0; j < cellList.size(); j++) {
                    if (j == 14) {
                        String childOrderId = cellList.get(j);
                        importOrderSend.setChildOrderId(childOrderId);
                    }
                    if (j == 17) {
                        String expressNo = cellList.get(j);
                        importOrderSend.setExpressNo(expressNo);
                    }
                    if (j == 18) {
                        String kuaidiNo = cellList.get(j);
                        importOrderSend.setKuaidiNo(kuaidiNo);
                    }
                }
                if (null != importOrderSend)
                    importOrderSendDO.add(importOrderSend);
            }
        }

        BaseResult baseResult = stockPriceComponent.excelForOrderSend(importOrderSendDO, user_id);

        return baseResult;
    }
    
    @RequestMapping("/importExcelForOrderSendSF.do")
    @ResponseBody
    public Object importExcelForOrderSendSF(@RequestParam(required = false) final MultipartFile file,
                                          final HttpServletRequest request) {

        String user_id = request.getSession().getAttribute("user_id").toString();

        ExcelUtil poi = new ExcelUtil();
        List<List<String>> list = poi.read(file);

        List<ImportOrderSend> importOrderSendDO = null;
        if (list != null) {
            //标题行
            List<String> title = list.get(1);
            if (!title.get(1).contains("订单号") || !title.get(2).contains("运单号")) {
                throw new BaseRuntimeException("您的表格格式不正确，请按照下载模板上传！");
            }
            importOrderSendDO = new LinkedList<ImportOrderSend>();
            for (int i = 2; i < list.size(); i++) {
                List<String> cellList = list.get(i);
                if (StringUtils.isBlank(cellList.get(1)) || StringUtils.isBlank(cellList.get(2)))
                    continue;

                ImportOrderSend importOrderSend = new ImportOrderSend();
                for (int j = 0; j < cellList.size(); j++) {
                    if (j == 1) {
                        String childOrderId = cellList.get(j);
                        importOrderSend.setChildOrderId(childOrderId);
                    }
                    if (j == 2) {
                        String kuaidiNo = cellList.get(j);
                        importOrderSend.setKuaidiNo(kuaidiNo);
                    }
                }
                if (null != importOrderSend)
                    importOrderSendDO.add(importOrderSend);
            }
        }

        BaseResult baseResult = stockPriceComponent.excelForOrderSend(importOrderSendDO, user_id);

        return baseResult;
    }

    /**
     * 用户导出
     */
    @RequestMapping("/exportAllPageForUser.do")
    public void exportAllPageForUser(String userId, String nickName, String realName,
                                     String selectcell, String selectsex, String userLevel,
                                     String delFlg, String startDate, String endDate,
                                     String recommendUserCell, String recommendUserId,
                                     String recommendNickName, String recommendRealName,
                                     String erpNo,
                                     int offset, int limit, String type, 
                                     HttpServletResponse response) {
        try {

            Date startTime = DateUtil.stringToDate(startDate, DateUtil.webFormat);
            Date endTime = DateUtil.stringToDate(endDate, DateUtil.webFormat);
            if (null != endTime)
                endTime = DateUtil.addDays(endTime, 1);

            List<Map<String, Object>> selectByQuery = new LinkedList<Map<String, Object>>();
            if (StringUtils.equals(type, "thisPage")) {
                selectByQuery = userDAO.selectUserList(nickName, realName, selectcell, selectsex,
                    userLevel, delFlg, startTime, endTime, recommendUserCell, userId,
                    recommendUserId, recommendNickName, recommendRealName, erpNo, offset, limit);
            }
            if (StringUtils.equals(type, "all")) {
                selectByQuery = userDAO.selectUserList(nickName, realName, selectcell, selectsex,
                    userLevel, delFlg, startTime, endTime, recommendUserCell, userId,
                    recommendUserId, recommendNickName, recommendRealName, erpNo, null, null);
            }
            if (selectByQuery.isEmpty()) {
                throw new RuntimeException("未找到相关数据");
            } else {
                for (Map<String, Object> map : selectByQuery) {
                    String sex = MapUtils.getString(map, "sex");
                    if (StringUtils.equals(sex, "1")) {
                        map.put("sex", "男");
                    } else if (StringUtils.equals(sex, "2")) {
                        map.put("sex", "女");
                    } else {
                        map.put("sex", "保密");
                    }

                    String delFlgThis = MapUtils.getString(map, "delFlg");
                    if (StringUtils.equals(delFlgThis, "0")) {
                        map.put("delFlg", "正常");
                    } else if (StringUtils.equals(delFlgThis, "1")) {
                        map.put("delFlg", "黑名单");
                    }

                    Date gmtCreate = (Date) map.get("gmtCreate");
                    map.put("gmtCreate", DateUtil.dateToString(gmtCreate, DateUtil.newFormat));

                    Date birthday = (Date) map.get("birthday");
                    map.put("birthday", DateUtil.dateToString(birthday, DateUtil.webFormat));

                    Integer user_Level = MapUtils.getInteger(map, "userLevel");
                    UserLevelEnum userLevelEnum = UserLevelEnum.getByValue(user_Level);
                    if (null != userLevelEnum)
                        map.put("userLevel", userLevelEnum.message());
                }
                // 表头
                String[] headers = { "手机号码", "用户昵称", "真实姓名", "性别", "身份证号", "生日", "年龄段", "职业",
                        "公司名称", "公司地址", "账户状态", "等级", "推荐人昵称", "推荐人手机号", "推荐人姓名", "创建时间", "用户编号",
                        "推荐人编号" };
                // 数据键名或者MODEL类字段名
                String[] Col = { "cell", "nickName", "realName", "sex", "certNo", "birthday",
                        "ageGroup", "profession", "comName", "comAddress", "delFlg", "userLevel",
                        "recommendNickName", "recommendUserCell", "recommendRealName", "gmtCreate",
                        "userId", "recommendUserId" };

                //                ExportExcel<Map<String, Object>> ex = new ExportExcel<Map<String, Object>>();
                //                List<Map<String, Object>> excelList = selectByQuery;
                //                // 生成Excel
                //                HSSFWorkbook workbook = ex.exportExcel("sheet1", headers, Col, excelList, null);
                //                // 下载
                //                response.setContentType("application/vnd.ms-excel");
                //                response.setHeader("Content-disposition", "attachment;filename=" + "table.xls");
                //                OutputStream ouputStream = response.getOutputStream();
                //                workbook.write(ouputStream);
                //                ouputStream.flush();
                //                ouputStream.close();

                String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())
                    .toString().concat(".xls");
                HSSFWorkbook book = ExportExcel.exportExcel(fileName, headers, Col, selectByQuery,
                    null);
                //写入数据
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + fileName);
                book.write(response.getOutputStream());

                response.getOutputStream().flush();
                response.getOutputStream().close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 团队导出
     */
    @RequestMapping("/exportAllPageForTeam.do")
    public void exportAllPageForTeam(String nickName, String userCell, String teamErpNo,
                                     String teamName, String teamType, String isTop, String delflg,
                                     String teamLevel, String realName, int offset, int limit,
                                     String type, String teamCheckRole, String teamCheckCell,
                                     HttpServletResponse response) {
        try {

            List<Map<String, Object>> selectByQuery = new LinkedList<Map<String, Object>>();
            if (StringUtils.equals(type, "thisPage")) {
                selectByQuery = teamDAO.selectTeam(userCell, nickName, teamErpNo, teamName,
                    teamType, isTop, delflg, teamLevel, realName, null, null, null, teamCheckRole,
                    teamCheckCell, offset, limit);
            }
            if (StringUtils.equals(type, "all")) {
                selectByQuery = teamDAO.selectTeam(userCell, nickName, teamErpNo, teamName,
                    teamType, isTop, delflg, teamLevel, realName, null, null, null, teamCheckRole,
                    teamCheckCell, null, null);
            }
            if (selectByQuery.isEmpty()) {
                throw new RuntimeException("未找到相关数据");
            } else {
                for (Map<String, Object> map : selectByQuery) {
                    String teamTypeThis = MapUtils.getString(map, "teamType");
                    TeamTypeEnum teamTypeEnum = TeamTypeEnum.getByCode(teamTypeThis);
                    if (null != teamTypeEnum)
                        map.put("teamType", teamTypeEnum.message());

                    Integer team_Level = MapUtils.getInteger(map, "teamLevel");
                    TeamLevelEnum teamLevelEnum = TeamLevelEnum.getByValue(team_Level);
                    if (null != teamLevelEnum)
                        map.put("teamLevelStr", teamLevelEnum.message());

                    String delFlg = MapUtils.getString(map, "delFlg");
                    DelFlgEnum delFlgEnum = DelFlgEnum.getByCode(delFlg);
                    if (null != delFlgEnum)
                        map.put("delFlg", delFlgEnum.message());

                    String isTopThis = MapUtils.getString(map, "isTop");
                    if (StringUtils.equals(isTopThis, "1")) {
                        map.put("isTop", "是");
                    } else if (StringUtils.equals(isTopThis, "0")) {
                        map.put("isTop", "否");
                    }

                    Date gmtCreate = (Date) map.get("gmtCreate");
                    map.put("gmtCreate", DateUtil.dateToString(gmtCreate, DateUtil.newFormat));

                    Date gmtModified = (Date) map.get("gmtModified");
                    map.put("gmtModified", DateUtil.dateToString(gmtModified, DateUtil.newFormat));

                    BigDecimal devoteAmount = (BigDecimal) map.get("devoteAmount");
                    map.put("devoteAmount", BigdecimalUtil.toMoney(devoteAmount).toSimpleString());

                    BigDecimal point = (BigDecimal) map.get("point");
                    map.put("point", BigdecimalUtil.toMoney(point).toSimpleString());

                    BigDecimal repoint = (BigDecimal) map.get("repoint");
                    map.put("repoint", BigdecimalUtil.toMoney(repoint).toSimpleString());
                }
                // 表头
                String[] headers = { "团队名称", "团队全称", "erp编号", "团队类型", "团队等级", "是否删除", "是否总店",
                        "所属总店", "团队电话", "负责人姓名", "负责人电话", "税号", "开户行", "卡账号", "地址", "经度", "纬度",
                        "健康值", "胡币", "积分", "创建人", "创建时间", "修改人", "修改时间", "团队编号" };
                // 数据键名或者MODEL类字段名
                String[] Col = { "teamName", "teamAllName", "erpNo", "teamType", "teamLevelStr",
                        "delFlg", "isTop", "topTeamName", "cell", "chargePersonName",
                        "chargePersonCell", "taxNo", "bankName", "bankNo", "address", "longitude",
                        "latitude", "devoteAmount", "repoint", "point", "creater", "gmtCreate",
                        "modifier", "gmtModified", "teamId" };

                //                ExportExcel<Map<String, Object>> ex = new ExportExcel<Map<String, Object>>();
                //                List<Map<String, Object>> excelList = selectByQuery;
                //                // 生成Excel
                //                HSSFWorkbook workbook = ex.exportExcel("sheet1", headers, Col, excelList, null);
                //                // 下载
                //                response.setContentType("application/vnd.ms-excel");
                //                response.setHeader("Content-disposition", "attachment;filename=" + "table.xls");
                //                OutputStream ouputStream = response.getOutputStream();
                //                workbook.write(ouputStream);
                //                ouputStream.flush();
                //                ouputStream.close();

                String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())
                    .toString().concat(".xls");
                HSSFWorkbook book = ExportExcel.exportExcel(fileName, headers, Col, selectByQuery,
                    null);
                //写入数据
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + fileName);
                book.write(response.getOutputStream());

                response.getOutputStream().flush();
                response.getOutputStream().close();

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 用户流水
     */
    @RequestMapping("/exportAllPageForUserAccountLog.do")
    public void exportAllPageForUserAccountLog(String nickName, String cell, String userId,
                                               String logType, String logFlg, String accountNo,
                                               String startDate, String endDate, int offset,
                                               int limit, String type, HttpServletResponse response) {
        try {

            Date startTime = DateUtil.stringToDate(startDate, DateUtil.webFormat);
            Date endTime = DateUtil.stringToDate(endDate, DateUtil.webFormat);
            if (null != endTime)
                endTime = DateUtil.addDays(endTime, 1);

            List<Map<String, Object>> selectByQuery = new LinkedList<Map<String, Object>>();
            if (StringUtils.equals(type, "thisPage")) {
                selectByQuery = accountLogDAO.selectUserAccountList(userId, cell, nickName,
                    logType, logFlg, accountNo, startTime, endTime, offset, limit);
            }
            if (StringUtils.equals(type, "all")) {
                selectByQuery = accountLogDAO.selectUserAccountList(userId, cell, nickName,
                    logType, logFlg, accountNo, startTime, endTime, null, null);
            }
            if (selectByQuery.isEmpty()) {
                throw new RuntimeException("未找到相关数据");
            } else {
                for (Map<String, Object> map : selectByQuery) {

                    BigDecimal amount = (BigDecimal) map.get("AMOUNT");
                    map.put("AMOUNT", new Money(amount).divide(100).toSimpleString());

                    BigDecimal beAmount = (BigDecimal) map.get("BEFORE_BALANCE");
                    map.put("BEFORE_BALANCE", new Money(beAmount).divide(100).toSimpleString());

                    BigDecimal afAmount = (BigDecimal) map.get("AFTER_BALANCE");
                    map.put("AFTER_BALANCE", new Money(afAmount).divide(100).toSimpleString());

                    String typeThis = (String) map.get("TYPE");
                    map.put("TYPE_SHOW", AccountLogTypeEnum.getByCode(typeThis).message());

                    String flg = (String) map.get("FLG");
                    map.put("FLG_SHOW", AccountLogFlgEnum.getByCode(flg).message());

                    Date gmtCreate = (Date) map.get("GMT_CREATE");
                    map.put("gmtCreate", DateUtil.dateToString(gmtCreate, DateUtil.newFormat));

                    Integer userLevel = MapUtils.getInteger(map, "USER_LEVEL");
                    UserLevelEnum userLevelEnum = UserLevelEnum.getByValue(userLevel);
                    if (null != userLevelEnum)
                        map.put("USER_LEVEL", userLevelEnum.message());

                    BigDecimal devote = (BigDecimal) map.get("DEVOTE_AMOUNT");
                    map.put("DEVOTE_AMOUNT", BigdecimalUtil.toMoney(devote).toSimpleString());

                    BigDecimal hu_point = (BigDecimal) map.get("HU_POINT");
                    BigDecimal re_point = (BigDecimal) map.get("RE_POINT");
                    map.put("POINT",
                        BigdecimalUtil.toMoney(hu_point).add(BigdecimalUtil.toMoney(re_point))
                            .toSimpleString());
                }
                // 表头
                String[] headers = { "会员手机号", "会员昵称", "真实姓名", "会员等级", "健康值", "积分", "流水类型", "流水标识",
                        "操作金额", "操作前金额", "操作后金额", "账户号", "会员编号", "创建时间" };
                // 数据键名或者MODEL类字段名
                String[] Col = { "CELL", "NICK_NAME", "REAL_NAME", "USER_LEVEL", "DEVOTE_AMOUNT",
                        "POINT", "TYPE_SHOW", "FLG_SHOW", "AMOUNT", "BEFORE_BALANCE",
                        "AFTER_BALANCE", "ACCOUNT_NO", "USER_ID", "gmtCreate" };

                //                ExportExcel<Map<String, Object>> ex = new ExportExcel<Map<String, Object>>();
                //                List<Map<String, Object>> excelList = selectByQuery;
                //                // 生成Excel
                //                HSSFWorkbook workbook = ex.exportExcel("sheet1", headers, Col, excelList, null);
                //                // 下载
                //                response.setContentType("application/vnd.ms-excel");
                //                response.setHeader("Content-disposition", "attachment;filename=" + "table.xls");
                //                OutputStream ouputStream = response.getOutputStream();
                //                workbook.write(ouputStream);
                //                ouputStream.flush();
                //                ouputStream.close();

                String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())
                    .toString().concat(".xls");
                HSSFWorkbook book = ExportExcel.exportExcel(fileName, headers, Col, selectByQuery,
                    null);
                //写入数据
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + fileName);
                book.write(response.getOutputStream());

                response.getOutputStream().flush();
                response.getOutputStream().close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 团队流水
     */
    @RequestMapping("/exportAllPageForTeamAccountLog.do")
    public void exportAllPageForTeamAccountLog(String teamName, String cell, String teamId,
                                               String logType, String logFlg, String startDate,
                                               String endDate, String erpNo, int offset, int limit,
                                               String type, HttpServletResponse response) {
        try {

            Date startTime = DateUtil.stringToDate(startDate, DateUtil.webFormat);
            Date endTime = DateUtil.stringToDate(endDate, DateUtil.webFormat);

            List<Map<String, Object>> selectByQuery = new LinkedList<Map<String, Object>>();
            if (StringUtils.equals(type, "thisPage")) {
                selectByQuery = accountLogDAO.selectTeamAccountList(teamId, cell, teamName,
                    logType, logFlg, erpNo, startTime, endTime, offset, limit);
            }
            if (StringUtils.equals(type, "all")) {
                selectByQuery = accountLogDAO.selectTeamAccountList(teamId, cell, teamName,
                    logType, logFlg, erpNo, startTime, endTime, null, null);
            }
            if (selectByQuery.isEmpty()) {
                throw new RuntimeException("未找到相关数据");
            } else {
                for (Map<String, Object> map : selectByQuery) {

                    BigDecimal amount = (BigDecimal) map.get("AMOUNT");
                    map.put("AMOUNT", new Money(amount).divide(100).toSimpleString());

                    BigDecimal beAmount = (BigDecimal) map.get("BEFORE_BALANCE");
                    map.put("BEFORE_BALANCE", new Money(beAmount).divide(100).toSimpleString());

                    BigDecimal afAmount = (BigDecimal) map.get("AFTER_BALANCE");
                    map.put("AFTER_BALANCE", new Money(afAmount).divide(100).toSimpleString());

                    String typeThis = (String) map.get("TYPE");
                    map.put("TYPE_SHOW", AccountLogTypeEnum.getByCode(typeThis).message());

                    String flg = (String) map.get("FLG");
                    map.put("FLG_SHOW", AccountLogFlgEnum.getByCode(flg).message());

                    Date gmtCreate = (Date) map.get("GMT_CREATE");
                    map.put("gmtCreate", DateUtil.dateToString(gmtCreate, DateUtil.newFormat));

                    Integer teamLevel = MapUtils.getInteger(map, "TEAM_LEVEL");
                    TeamLevelEnum teamLevelEnum = TeamLevelEnum.getByValue(teamLevel);
                    if (null != teamLevelEnum)
                        map.put("TEAM_LEVEL", teamLevelEnum.message());

                    BigDecimal devote = (BigDecimal) map.get("DEVOTE_AMOUNT");
                    map.put("DEVOTE_AMOUNT", BigdecimalUtil.toMoney(devote).toSimpleString());

                    BigDecimal hu_point = (BigDecimal) map.get("HU_POINT");
                    BigDecimal re_point = (BigDecimal) map.get("RE_POINT");
                    map.put("POINT",
                        BigdecimalUtil.toMoney(hu_point).add(BigdecimalUtil.toMoney(re_point))
                            .toSimpleString());
                }
                // 表头
                String[] headers = { "团队名称", "联系电话", "负责人", "负责人电话", "团队等级", "健康值", "积分", "流水类型",
                        "流水标识", "操作金额", "操作前金额", "操作后金额", "erp编号", "团队编号", "账户号", "创建时间" };
                // 数据键名或者MODEL类字段名
                String[] Col = { "TEAM_NAME", "CELL", "CHARGE_PERSON_NAME", "CHARGE_PERSON_CELL",
                        "TEAM_LEVEL", "DEVOTE_AMOUNT", "POINT", "TYPE_SHOW", "FLG_SHOW", "AMOUNT",
                        "BEFORE_BALANCE", "AFTER_BALANCE", "ERP_NO", "TEAM_ID", "ACCOUNT_NO",
                        "gmtCreate" };

                //                ExportExcel<Map<String, Object>> ex = new ExportExcel<Map<String, Object>>();
                //                List<Map<String, Object>> excelList = selectByQuery;
                //                // 生成Excel
                //                HSSFWorkbook workbook = ex.exportExcel("sheet1", headers, Col, excelList, null);
                //                // 下载
                //                response.setContentType("application/vnd.ms-excel");
                //                response.setHeader("Content-disposition", "attachment;filename=" + "table.xls");
                //                OutputStream ouputStream = response.getOutputStream();
                //                workbook.write(ouputStream);
                //                ouputStream.flush();
                //                ouputStream.close();

                String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())
                    .toString().concat(".xls");
                HSSFWorkbook book = ExportExcel.exportExcel(fileName, headers, Col, selectByQuery,
                    null);
                //写入数据
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + fileName);
                book.write(response.getOutputStream());

                response.getOutputStream().flush();
                response.getOutputStream().close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
