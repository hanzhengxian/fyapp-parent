package com.onway.web.controller.mng;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.onway.common.lang.CollectionUtils;
import com.onway.common.lang.DateUtils;
import com.onway.common.lang.Money;
import com.onway.common.lang.StringUtils;
import com.onway.fyapp.common.dal.dataobject.DiscountDO;
import com.onway.fyapp.common.dal.dataobject.SProvinceDO;
import com.onway.model.enums.BizTypeEnum;
import com.onway.model.enums.BooleanEnum;
import com.onway.model.enums.DiscountStatusEnum;
import com.onway.model.enums.PublishFlgEnum;
import com.onway.platform.common.exception.BaseRuntimeException;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.result.JsonResult;
import com.onway.utils.BigdecimalUtil;
import com.onway.utils.DateUtil;
import com.onway.utils.FileUploadUtils;
import com.onway.utils.JSONArrayUtils;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;

/**
 * 方案管理-优惠活动控制器
 * 
 * @author liaoshengzhe
 * @version $Id: DiscountController.java, v 0.1 2018年8月21日 下午6:20:09
 *          liaoshengzhe Exp $
 */
@Controller
public class DiscountController extends AbstractController {

    /**
     * 分页查询优惠券列表
     * 
     * @param request
     * @return
     */
    @RequestMapping("selectDiscount.do")
    @ResponseBody
    public Object selectDiscount(HttpServletRequest request) {
        String discountId = request.getParameter("discountId");
        String discountName = request.getParameter("discountName");
        String discountType = request.getParameter("discountType");

        String teamId = request.getParameter("teamId");
        String teamName = request.getParameter("teamName");
        String teamErpNo = request.getParameter("teamErpNo");

        String productId = request.getParameter("productId");
        String productName = request.getParameter("productName");
        String productErpNo = request.getParameter("productErpNo");

        //1正在优惠   2结束优惠
        String speType = request.getParameter("speType");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        String canOverUse = request.getParameter("canOverUse");
        String limitUserLevel = request.getParameter("limitUserLevel");
        //0未发布  1已发布
        String publishFlg = request.getParameter("publishFlg");

        String linkProvince = request.getParameter("linkProvince");
        String linkCity = request.getParameter("linkCity");

        String offset = request.getParameter("offset");
        String limit = request.getParameter("limit");
        JSONObject data = new JSONObject();
        try {

            String productIdArray = null;
            if (StringUtils.isNotBlank(productName) || StringUtils.isNotBlank(productErpNo)) {
                List<String> queryForProductIdArray = productDAO.queryForProductIdArray(
                    productName, productErpNo);
                if (CollectionUtils.isNotEmpty(queryForProductIdArray)) {
                    queryForProductIdArray.add("");
                    JSONArray array = JSONArray.parseArray(JSON
                        .toJSONString(queryForProductIdArray));
                    productIdArray = array.toString();
                }
            }

            String linkCityArray = null;
            if (StringUtils.isNotBlank(linkCity)) {
                SProvinceDO city = sProvinceDAO.queryCityById(linkCity);
                if (null != city) {
                    List<String> cityList = new ArrayList<String>();
                    cityList.add(city.getCityName());
                    JSONArray array = JSONArray.parseArray(JSON.toJSONString(cityList));
                    linkCityArray = array.toString();
                }
            } else if (StringUtils.isNotBlank(linkProvince)) {
                List<String> cityList = sProvinceDAO.queryAllCityNameByParentId(linkProvince, null,
                    null, null);
                if (CollectionUtils.isNotEmpty(cityList)) {
                    JSONArray array = JSONArray.parseArray(JSON.toJSONString(cityList));
                    linkCityArray = array.toString();
                }
            }

            Date thisDate = new Date();

            Date endTime = DateUtil.stringToDate(endDate, DateUtil.webFormat);
            if (null != endTime)
                endTime = DateUtil.addDays(endTime, 1);

            List<Map<String, Object>> queryList = discountDAO.queryList(discountId, discountName,
                discountType, teamId, teamName, teamErpNo, productId, productIdArray, speType,
                canOverUse, limitUserLevel, DateUtil.stringToDate(startDate, DateUtil.webFormat),
                endTime, thisDate, null, publishFlg, null, null, linkCityArray, offset, limit);

            for (Map<String, Object> map : queryList) {
                BigDecimal amount = (BigDecimal) map.get("amount");
                map.put("amount", BigdecimalUtil.toMoney(amount));
                BigDecimal limitAmount = (BigDecimal) map.get("limitAmount");
                map.put("limitAmount", BigdecimalUtil.toMoney(limitAmount));

                String linkTeam = MapUtils.getString(map, "linkTeam");
                String linkPro = MapUtils.getString(map, "linkPro");
                String linkTeamChild = MapUtils.getString(map, "linkTeamChild");

                List<String> linkTeamChildS = JSONArrayUtils
                    .parseArray(linkTeamChild, String.class);
                if (StringUtils.isBlank(linkTeam) && CollectionUtils.isEmpty(linkTeamChildS)) {
                    map.put("chooseTeamBoolean", false);
                } else {
                    map.put("chooseTeamBoolean", true);
                }

                List<String> linkProS = JSONArrayUtils.parseArray(linkPro, String.class);
                if (CollectionUtils.isEmpty(linkProS)) {
                    map.put("chooseProBoolean", false);
                } else {
                    map.put("chooseProBoolean", true);
                }
            }

            int count = discountDAO.queryListCount(discountId, discountName, discountType, teamId,
                teamName, teamErpNo, productId, productIdArray, speType, canOverUse,
                limitUserLevel, DateUtil.stringToDate(startDate, DateUtil.webFormat), endTime,
                thisDate, null, publishFlg, null, null, linkCityArray);

            data.put("success", true);
            data.put("rows", queryList);
            data.put("total", count);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error(MessageFormat.format("查询优惠券异常", e));
        }
        return data;
    }

    /**
     * 分页查询组内优惠券列表
     * 
     * @param request
     * @return
     */
    @RequestMapping("queryGroupList.do")
    @ResponseBody
    public Object queryGroupList(HttpServletRequest request) {
        String discountTeamId = request.getParameter("alertDiscountTeamId");
        String discountId = request.getParameter("alertDiscountId");
        String discountName = request.getParameter("alertDiscountName");
        String discountType = request.getParameter("discountType");
        String offset = request.getParameter("offset");
        String limit = request.getParameter("limit");
        JSONObject data = new JSONObject();
        try {
            List<DiscountDO> queryList = discountDAO.queryGroupList(discountTeamId, discountName,
                discountType, discountId, offset, limit);
            int count = discountDAO.queryGroupListCount(discountTeamId, discountName, discountType,
                discountId);
            data.put("success", true);
            data.put("rows", queryList);
            data.put("total", count);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error(MessageFormat.format("查询优惠券异常", new Object[] {}));
        }
        return data;
    }

    /**
     * 分页查询能同时使用的优惠券列表
     * 
     * @param request
     * @return
     */
    @RequestMapping("findDiscountList.do")
    @ResponseBody
    public Object findDiscountList(HttpServletRequest request) {
        String discountId = request.getParameter("alertDiscountId");
        String discountName = request.getParameter("alertDiscountName");
        String discountType = request.getParameter("discountType");
        String offset = request.getParameter("offset");
        String limit = request.getParameter("limit");

        String thisDiscounId = request.getParameter("thisDiscounId");

        String productName = request.getParameter("alertProductName");
        String productErpNo = request.getParameter("alertProductErpNo");
        String productId = request.getParameter("alertProductId");

        String teamName = request.getParameter("alertTeamName");
        String teamErpNo = request.getParameter("alertTeamErpno");
        String teamChildId = request.getParameter("alertTeamId");

        String linkProvince = request.getParameter("alertLinkProvince");

        JSONObject data = new JSONObject();
        try {
            //1正在优惠   2结束优惠
            String speType = "1";
            Date thisDate = new Date();

            String productIdArray = null;
            if (StringUtils.isNotBlank(productName) || StringUtils.isNotBlank(productErpNo)) {
                List<String> queryForProductIdArray = productDAO.queryForProductIdArray(
                    productName, productErpNo);
                if (CollectionUtils.isNotEmpty(queryForProductIdArray)) {
                    queryForProductIdArray.add("");
                    JSONArray array = JSONArray.parseArray(JSON
                        .toJSONString(queryForProductIdArray));
                    productIdArray = array.toString();
                }
            }

            String teamChildIdArray = null;
            if (StringUtils.isNotBlank(teamName) || StringUtils.isNotBlank(teamErpNo)) {
                List<String> queryFoTeamIdArray = teamDAO.queryFoTeamIdArray(teamName, teamErpNo);
                if (CollectionUtils.isNotEmpty(queryFoTeamIdArray)) {
                    queryFoTeamIdArray.add("");
                    JSONArray array = JSONArray.parseArray(JSON.toJSONString(queryFoTeamIdArray));
                    teamChildIdArray = array.toString();
                }
            }

            List<Map<String, Object>> queryList = discountDAO.queryList(discountId, discountName,
                discountType, null, null, null, productId, productIdArray, speType, null, null,
                null, null, thisDate, thisDiscounId, null, teamChildId, teamChildIdArray,
                linkProvince, offset, limit);

            for (Map<String, Object> map : queryList) {
                BigDecimal amount = (BigDecimal) map.get("amount");
                map.put("amount", BigdecimalUtil.toMoney(amount));
                BigDecimal limitAmount = (BigDecimal) map.get("limitAmount");
                map.put("limitAmount", BigdecimalUtil.toMoney(limitAmount));

                if (StringUtils.isNotBlank(thisDiscounId)) {
                    DiscountDO queryByDiscountId = discountDAO.queryByDiscountId(thisDiscounId);
                    String discountIdThis = MapUtils.getString(map, "discountId");
                    if (StringUtils.contains(queryByDiscountId.getCanTogeDisid(), discountIdThis)) {
                        map.put("choose", "-1");
                    }
                }

                String linkTeam = MapUtils.getString(map, "linkTeam");
                String linkPro = MapUtils.getString(map, "linkPro");
                String linkTeamChild = MapUtils.getString(map, "linkTeamChild");

                List<String> linkTeamChildS = JSONArrayUtils
                    .parseArray(linkTeamChild, String.class);
                if (StringUtils.isBlank(linkTeam) && CollectionUtils.isEmpty(linkTeamChildS)) {
                    map.put("chooseTeamBoolean", false);
                } else {
                    map.put("chooseTeamBoolean", true);
                }

                List<String> linkProS = JSONArrayUtils.parseArray(linkPro, String.class);
                if (CollectionUtils.isEmpty(linkProS)) {
                    map.put("chooseProBoolean", false);
                } else {
                    map.put("chooseProBoolean", true);
                }
            }

            int count = discountDAO.queryListCount(discountId, discountName, discountType, null,
                null, null, null, null, speType, null, null, null, null, thisDate, thisDiscounId,
                null, teamChildId, teamChildIdArray, linkProvince);

            data.put("success", true);
            data.put("rows", queryList);
            data.put("total", count);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error(MessageFormat.format("查询优惠券异常", new Object[] {}));
        }
        return data;
    }

    /**
     * 添加优惠方案
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("addDiscount.do")
    @ResponseBody
    public Object addDiscount(HttpServletRequest request, Integer offset, Integer limit,
                              @RequestParam(value = "addImg", required = false) MultipartFile addImg) {

        String addDiscountName = request.getParameter("addDiscountName");
        String addDiscountType = request.getParameter("addDiscountType");
        String addAmount = request.getParameter("addAmount");
        String addDiscountTeamId = request.getParameter("addDiscountTeamId");
        String addLimitAmount = request.getParameter("addLimitAmount");
        String addDiscount = request.getParameter("addDiscount");
        String addTimeType = request.getParameter("addTimeType");
        String addEndDate = request.getParameter("addEndDate");
        String addEndDay = request.getParameter("addEndDay");
        String addTimeEndReceive = request.getParameter("addTimeEndReceive");
        String addLinkTeam = request.getParameter("addLinkTeam");
        String addLinkPro = request.getParameter("addLinkPro");
        String addDiscountSubType = request.getParameter("addDiscountSubType");
        String addCanOverUse = request.getParameter("addCanOverUse");
        String addCanDiscountUse = request.getParameter("addCanDiscountUse");
        //		String addCanOverNums = request.getParameter("addCanOverNums");
        String addCanTogeUse = request.getParameter("addCanTogeUse");
        String addCanTogeDisid = request.getParameter("addCanTogeDisid");
        String addLimitUserLevel = request.getParameter("addLimitUserLevel");
        String addRank = request.getParameter("addRank");

        String addIsNew = request.getParameter("addIsNew");// 是否新人券 true fasle

        String user_id = request.getSession().getAttribute("user_id").toString();

        JSONObject data = new JSONObject();
        DiscountDO discountDo = new DiscountDO();
        String discounId = codeGenerateComponent.nextCodeByType(BizTypeEnum.DISCOUNT_NO);
        discountDo.setDiscountId(discounId);
        discountDo.setDiscountName(addDiscountName);
        discountDo.setDiscountType(addDiscountType);
        discountDo.setDiscountTeamId(addDiscountTeamId);
        String[] str1 = { "1", "2" };
        if (StringUtils.equalsAny(addDiscountType, str1)) {
            // 类型为满减和每满减时
            discountDo.setAmount(new Money(addAmount));
            discountDo.setLimitAmount(new Money(addLimitAmount));
            discountDo.setDiscount(0);
        } else {
            discountDo.setAmount(new Money(0));
            discountDo.setLimitAmount(new Money(addLimitAmount));
            // 类型为折扣时
            discountDo.setDiscount(Double.parseDouble(addDiscount) / 100);
        }

        // 处理图片上传
        String imgUrl = null;
        try {
            imgUrl = FileUploadUtils.upload(request, addImg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 存入图片访问链接
        discountDo.setDiscountBackground(imgUrl);

        discountDo.setTimeType(addTimeType);
        if (addTimeType.equals("1")) {
            // 如果有效期为截止日期则存入日期字符串
            discountDo.setTimeEnd(addEndDate);
        } else if (addTimeType.equals("2")) {
            // 有效期为天数则存入天数
            discountDo.setTimeEnd(addEndDay);
        }

        if (StringUtils.isNotBlank(addTimeEndReceive)) {
            try {
                discountDo.setTimeEndReceive(DateUtils.parseDate(addTimeEndReceive,
                    "yyyy-MM-dd HH:mm:ss"));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (StringUtils.isNotBlank(addLinkTeam)) {
            discountDo.setLinkTeam(addLinkTeam);
        }
        if (StringUtils.isNotBlank(addLinkPro)) {
            discountDo.setLinkPro(addLinkPro);
        }
        discountDo.setDiscountSubType(addDiscountSubType);
        discountDo.setCanOverUse(addCanOverUse);
        discountDo.setCanDiscountUse(addCanDiscountUse);
        if (StringUtils.isBlank(addCanDiscountUse)) {
            discountDo.setCanDiscountUse(BooleanEnum.FALSE.getCode());
        }
        if (StringUtils.equals(addCanOverUse, "true")) {
            // discountDo.setCanOverNums(Integer.parseInt(addCanOverNums));
            discountDo.setCanOverNums(0);
        }
        discountDo.setCanTogeUse(addCanTogeUse);
        if (StringUtils.equals(addCanTogeUse, "true")) {
            discountDo.setCanTogeDisid(addCanTogeDisid);
            List<String> discountIds = JSONArrayUtils.parseArray(addCanTogeDisid, String.class);
            for (String disId : discountIds) {
                DiscountDO queryByDiscountId = discountDAO.queryByDiscountId(disId);
                if (null != queryByDiscountId
                    && !StringUtils.contains(queryByDiscountId.getCanTogeDisid(), discounId)) {
                    List<String> parseArray = JSONArrayUtils.parseArray(
                        queryByDiscountId.getCanTogeDisid(), String.class);
                    parseArray.add(discounId);
                    JSONArray array = JSONArray.parseArray(JSON.toJSONString(parseArray));
                    discountDAO
                        .updateCanTogetherDiscounId("true", array.toString(), user_id, disId);
                }
            }
        }
        discountDo.setLimitUserLevel(Integer.parseInt(addLimitUserLevel));
        discountDo.setRank(Integer.parseInt(addRank));
        discountDo.setDelFlg("0");
        if (StringUtils.equals(addIsNew, "true")) {
            discountDo.setDelFlg("2");
        }
        discountDo.setCreater(user_id);
        int result = discountDAO.insert(discountDo);
        data.put("success", result > 0 ? true : false);
        data.put("message", result > 0 ? "添加成功" : "添加失败");
        return data;
    }

    /**
     * 修改优惠方案
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("updateDiscount.do")
    @ResponseBody
    public Object updateDiscount(HttpServletRequest request,
                                 @RequestParam(value = "editImg", required = false) MultipartFile editImg) {
        String editDiscountId = request.getParameter("editDiscountId");
        String editDiscountTeamId = request.getParameter("editDiscountTeamId");
        String editDiscountName = request.getParameter("editDiscountName");
        String editDiscountType = request.getParameter("editDiscountType");
        String editLimitAmount = request.getParameter("editLimitAmount");
        String editAmount = request.getParameter("editAmount");
        String editDiscount = request.getParameter("editDiscount");
        String editTimeType = request.getParameter("editTimeType");
        String editLinkPro = request.getParameter("editLinkPro");
        String editEndDate = request.getParameter("editEndDate");
        String editEndDay = request.getParameter("editEndDay");
        String editTimeEndReceive = request.getParameter("editTimeEndReceive");
        String editDiscountSubType = request.getParameter("editDiscountSubType");
        String editCanOverUse = request.getParameter("editCanOverUse");
        String editCanOverNums = request.getParameter("editCanOverNums");
        String editCanTogeUse = request.getParameter("editCanTogeUse");
        String editCanDiscountUse = request.getParameter("editCanDiscountUse");
        String editLimitUserLevel = request.getParameter("editLimitUserLevel");
        String editRank = request.getParameter("editRank");
        String editCanTogeDisid = request.getParameter("editCanTogeDisid");
        String editLinkTeam = request.getParameter("editLinkTeam");
        String oldImg = request.getParameter("oldImg");

        String editIsNew = request.getParameter("editIsNew");// 是否新人券 true fasle

        String user_id = request.getSession().getAttribute("user_id").toString();

        JSONObject data = new JSONObject();

        DiscountDO searchDiscount = discountDAO.queryByDiscountId(editDiscountId);
        if (null == searchDiscount) {
            data.put("success", false);
            data.put("message", "优惠券信息查询异常");
            return data;
        }

        if (StringUtils.equals(searchDiscount.getPublishFlg(), PublishFlgEnum.PUBLISH.getCode())) {
            data.put("success", false);
            data.put("message", "优惠券发布状态下，不能修改优惠券信息");
            return data;
        }

        String finalBackImgUrl = "";
        if (editImg == null) {
            finalBackImgUrl = oldImg;
        } else {
            try {
                finalBackImgUrl = FileUploadUtils.upload(request, editImg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Money finalAmount = new Money(0);
        Money finalLimitAmount = new Money(0);

        String[] str1 = { "1", "2" };
        Double finalDiscount = 0.00;
        if (StringUtils.equalsAny(editDiscountType, str1)) {
            // 类型为满减和每满减时
            if (StringUtils.isNotBlank(editAmount)) {
                finalAmount = new Money(editAmount);
            }
            if (StringUtils.isNotBlank(editLimitAmount)) {
                finalLimitAmount = new Money(editLimitAmount);
            }
        } else {
            if (StringUtils.isNotBlank(editDiscountSubType)) {
                finalDiscount = Double.parseDouble(editDiscount) / 100;
            }
            if (StringUtils.isNotBlank(editLimitAmount)) {
                finalLimitAmount = new Money(editLimitAmount);
            }
        }

        String finalTimeEnd = "";
        if (editTimeType.equals("1")) {
            finalTimeEnd = editEndDate;
        } else if (editTimeType.equals("2")) {
            finalTimeEnd = editEndDay;
        }
        Date timeEndReceive = null;
        if (StringUtils.isNotBlank(editTimeEndReceive)) {
            try {
                timeEndReceive = DateUtils.parseDate(editTimeEndReceive, "yyyy-MM-dd HH:mm:ss");
            } catch (ParseException e) {
                logger.error(MessageFormat.format("时间转换异常", new Object[] {}));
            }
        }
        int rank = 0;
        if (StringUtils.isNotBlank(editRank)) {
            rank = Integer.parseInt(editRank);
        }
        int canOverNums = 0;
        if (StringUtils.isNotBlank(editCanOverNums)) {
            canOverNums = Integer.parseInt(editCanOverNums);
        }
        int limitUserLevel = 0;
        if (StringUtils.isNotBlank(editLimitUserLevel)) {
            limitUserLevel = Integer.parseInt(editLimitUserLevel);
        }

        if (StringUtils.equals(editCanTogeUse, "true")) {
            if (StringUtils.isNotBlank(editCanTogeDisid)) {
                List<String> discountIds = JSONArrayUtils
                    .parseArray(editCanTogeDisid, String.class);
                for (String disId : discountIds) {
                    DiscountDO queryByDiscountId = discountDAO.queryByDiscountId(disId);
                    if (null != queryByDiscountId
                        && !StringUtils.contains(queryByDiscountId.getCanTogeDisid(),
                            editDiscountId)) {
                        List<String> parseArray = JSONArrayUtils.parseArray(
                            queryByDiscountId.getCanTogeDisid(), String.class);
                        parseArray.add(editDiscountId);
                        JSONArray array = JSONArray.parseArray(JSON.toJSONString(parseArray));
                        discountDAO.updateCanTogetherDiscounId("true", array.toString(), user_id,
                            disId);
                    }
                }
            }
        } else {
            List<DiscountDO> queryByCantogeId = discountDAO.queryByCantogeId(editDiscountId);
            for (DiscountDO discountDO : queryByCantogeId) {
                List<String> discountIds = JSONArrayUtils.parseArray(discountDO.getCanTogeDisid(),
                    String.class);
                discountIds.remove(editDiscountId);
                String canTige = "true";
                if (CollectionUtils.isEmpty(discountIds))
                    canTige = "false";
                JSONArray array = JSONArray.parseArray(JSON.toJSONString(discountIds));
                discountDAO.updateCanTogetherDiscounId(canTige, array.toString(), user_id,
                    discountDO.getDiscountId());
            }
        }
        String delFlg = searchDiscount.getDelFlg();
        if (StringUtils.equals(editIsNew, "true")) {
            delFlg = "2";
        } else {
            delFlg = "0";
        }
        if (StringUtils.isBlank(editCanDiscountUse))
            editCanDiscountUse = BooleanEnum.FALSE.getCode();

        int result = discountDAO.update(editDiscountTeamId, editDiscountName, editDiscountType,
            finalBackImgUrl, finalAmount, finalLimitAmount, finalDiscount, editTimeType,
            finalTimeEnd, timeEndReceive, editLinkTeam, editLinkPro, editDiscountSubType,
            editCanOverUse, canOverNums, editCanTogeUse, editCanTogeDisid, limitUserLevel, rank,
            delFlg, user_id, editCanDiscountUse, editDiscountId);
        data.put("success", result > 0 ? true : false);
        data.put("message", result > 0 ? "操作成功" : "操作失败");
        return data;
    }

    /**
     * 删除优惠方案
     * 
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/deleteDiscount.do")
    @ResponseBody
    public Object deleteDiscount(HttpServletRequest request, final String discountId) {
        final JsonResult data = new JsonResult(true);
        final String user_id = request.getSession().getAttribute("user_id").toString();
        controllerTemplate.execute(data, new ControllerCallBack() {

            @Override
            public void executeService() {
                int result = discountDAO.delete(user_id, discountId);
                data.setSuccess(result > 0 ? true : false);
                data.setMessage(result > 0 ? "删除成功" : "删除失败");
            }

            @Override
            public void check() {
                AssertUtil.notBlank(discountId, "id不能为空");
            }

        });
        return data;
    }

    /**
     * 从组中移除优惠券
     * 
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/removeDiscountFromTeam.do")
    @ResponseBody
    public Object removeDiscountFromTeam(HttpServletRequest request, final String discountId) {
        final JsonResult data = new JsonResult(true);
        final String user_id = request.getSession().getAttribute("user_id").toString();
        controllerTemplate.execute(data, new ControllerCallBack() {

            @Override
            public void executeService() {
                int result = discountDAO.removeDiscount(user_id, discountId);
                data.setSuccess(result > 0 ? true : false);
                data.setMessage(result > 0 ? "移除成功" : "移除失败");
            }

            @Override
            public void check() {
                AssertUtil.notBlank(discountId, "id不能为空");
            }

        });
        return data;
    }

    @RequestMapping("/selectGoodsForDiscount.do")
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

        String hasChoose = request.getParameter("hasChoose");//0已选  1未选

        JSONObject data = new JSONObject();

        String discountId = request.getParameter("discountId");

        String barCode = request.getParameter("barCode");

        DiscountDO discountDO = null;
        if (StringUtils.isNotBlank(discountId))
            discountDO = discountDAO.queryByDiscountId(discountId);

        String linkPro = discountDO.getLinkPro();
        List<String> linkProIds = new ArrayList<String>();
        if (StringUtils.isBlank(linkPro)) {
            linkProIds = null;
        } else {
            linkProIds = JSONArrayUtils.parseArray(linkPro, String.class);
            if (CollectionUtils.isEmpty(linkProIds))
                linkProIds = null;
        }

        List<Map<String, Object>> goodsList = productDAO.queryGoodsWithBarCode(productId, proType,
            teamId, teamName, productName, onSale, canBuy, licenseNo, isDelete, goodErpNo,
            hasChoose, linkProIds, barCode, offset, limit);
        for (Map<String, Object> map : goodsList) {
            String thisProductId = (String) map.get("productId");
            if (null != discountDO && StringUtils.contains(discountDO.getLinkPro(), thisProductId))
                map.put("hasChoose", true);
        }

        int total = productDAO.queryGoodsWithBarCodeCount(productId, proType, teamId, teamName,
            productName, onSale, canBuy, licenseNo, isDelete, goodErpNo, hasChoose, linkProIds,
            barCode);
        data.put("rows", goodsList);
        data.put("total", total);
        return data;
    }

    @RequestMapping("/selectTeamForDiscount.do")
    @ResponseBody
    public JSONObject selectTeamForDiscount(HttpServletRequest request, Integer offset,
                                            Integer limit) {

        String nickName = request.getParameter("nickName");
        String userCell = request.getParameter("userCell");
        String teamErpNo = request.getParameter("teamErpNo");
        String teamName = request.getParameter("teamName");
        String teamType = request.getParameter("teamType");
        String isTop = "1";
        String delflg = request.getParameter("delflg");
        String teamLevel = request.getParameter("teamLevel");
        String realName = request.getParameter("realName");

        JSONObject data = new JSONObject();
        try {

            String discountId = request.getParameter("discountId");

            DiscountDO discountDO = null;
            if (StringUtils.isNotBlank(discountId))
                discountDO = discountDAO.queryByDiscountId(discountId);

            List<Map<String, Object>> queryList = teamDAO.selectTeam(userCell, nickName, teamErpNo,
                teamName, teamType, isTop, delflg, teamLevel, realName, null, null, null, null,
                null, offset, limit);

            for (Map<String, Object> map : queryList) {
                String thisTeamId = (String) map.get("teamId");
                if (null != discountDO
                    && StringUtils.contains(discountDO.getLinkTeam(), thisTeamId))
                    map.put("hasChoose", true);

            }
            int count = teamDAO.selectTeamCount(userCell, nickName, teamErpNo, teamName, teamType,
                isTop, delflg, teamLevel, realName, null, null, null, null, null);

            data.put("rows", queryList);
            data.put("total", count);
        } catch (Exception e) {
            logger.error(MessageFormat.format("查询团队异常", new Object[] {}));
        }
        return data;
    }

    @RequestMapping("/selectTeamChildForDiscount.do")
    @ResponseBody
    public JSONObject selectTeamChildForDiscount(HttpServletRequest request, Integer offset,
                                                 Integer limit) {

        String nickName = request.getParameter("nickName");
        String userCell = request.getParameter("userCell");
        String teamErpNo = request.getParameter("teamErpNo");
        String teamName = request.getParameter("teamName");
        String teamType = request.getParameter("teamType");
        String isTop = "0";
        String delflg = request.getParameter("delflg");
        String teamLevel = request.getParameter("teamLevel");
        String realName = request.getParameter("realName");

        String teamIdFather = request.getParameter("teamIdFather");

        String hasChoose = request.getParameter("linkType");
        JSONObject data = new JSONObject();
        try {
            String discountId = request.getParameter("discountId");

            DiscountDO discountDO = null;
            List<String> linkTeamIds = new ArrayList<String>();
            if (StringUtils.isNotBlank(discountId)) {
                discountDO = discountDAO.queryByDiscountId(discountId);
                String linkTeamChild = discountDO.getLinkTeamChild();
                if (StringUtils.isBlank(linkTeamChild)) {
                    linkTeamIds = null;
                } else {
                    linkTeamIds = JSONArrayUtils.parseArray(linkTeamChild, String.class);
                    if (CollectionUtils.isEmpty(linkTeamIds))
                        linkTeamIds = null;
                }
            }

            List<Map<String, Object>> queryList = teamDAO.selectTeam(userCell, nickName, teamErpNo,
                teamName, teamType, isTop, delflg, teamLevel, realName, teamIdFather, hasChoose,
                linkTeamIds, null, null, offset, limit);

            for (Map<String, Object> map : queryList) {
                String thisTeamId = (String) map.get("teamId");
                if (null != discountDO
                    && StringUtils.contains(discountDO.getLinkTeamChild(), thisTeamId))
                    map.put("hasChoose", true);

            }
            int count = teamDAO.selectTeamCount(userCell, nickName, teamErpNo, teamName, teamType,
                isTop, delflg, teamLevel, realName, teamIdFather, hasChoose, linkTeamIds, null,
                null);
            data.put("rows", queryList);
            data.put("total", count);
        } catch (Exception e) {
            logger.error(MessageFormat.format("查询团队异常", new Object[] {}));
        }
        return data;
    }

    /**
     * 选择关联商品
     * 
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/discountChooseGood.do")
    @ResponseBody
    public Object discountChooseGood(final HttpServletRequest request, final String discountId,
                                     final String productId) {

        final JsonResult json = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();

        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                DiscountDO discountDO = discountDAO.queryByDiscountId(discountId);
                if (null == discountDO)
                    return;

                String linkPro = discountDO.getLinkPro();

                if (StringUtils.contains(linkPro, productId))
                    return;

                List<String> parseArray = JSONArrayUtils.parseArray(linkPro, String.class);
                if (CollectionUtils.isEmpty(parseArray)) {
                    parseArray = new ArrayList<String>();
                }
                parseArray.add(productId);

                JSONArray array = JSONArray.parseArray(JSON.toJSONString(parseArray));

                int modifyLinkPro = discountDAO.modifyLinkPro(array.toString(), user_id, discountId);
                if (modifyLinkPro > 0)
                    json.setSuccess(true);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(discountId, "优惠券信息为空");
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
    @RequestMapping("/discountUnChooseGood.do")
    @ResponseBody
    public Object discountUnChooseGood(final HttpServletRequest request, final String discountId,
                                       final String productId) {

        final JsonResult json = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();

        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                DiscountDO discountDO = discountDAO.queryByDiscountId(discountId);
                if (null == discountDO)
                    return;

                String linkPro = discountDO.getLinkPro();

                if (!StringUtils.contains(linkPro, productId))
                    return;

                List<String> parseArray = JSONArrayUtils.parseArray(linkPro, String.class);
                parseArray.remove(productId);

                JSONArray array = JSONArray.parseArray(JSON.toJSONString(parseArray));

                int modifyLinkPro = discountDAO.modifyLinkPro(array.toString(), user_id, discountId);
                if (modifyLinkPro > 0)
                    json.setSuccess(true);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(discountId, "优惠券信息为空");
            }

        });
        return json;
    }

    @RequestMapping("/discountChooseGoodAll.do")
    @ResponseBody
    public Object discountChooseGoodAll(final HttpServletRequest request, final String discountId,
                                        final String[] productIdS) {

        final JsonResult json = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();

        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                DiscountDO discountDO = discountDAO.queryByDiscountId(discountId);
                if (null == discountDO)
                    return;

                String linkPro = discountDO.getLinkPro();
                List<String> parseArray = JSONArrayUtils.parseArray(linkPro, String.class);
                if (CollectionUtils.isEmpty(parseArray)) {
                    parseArray = new ArrayList<String>();
                }

                for (String productId : productIdS) {
                    if (StringUtils.contains(linkPro, productId))
                        continue;
                    parseArray.add(productId);
                }

                JSONArray array = JSONArray.parseArray(JSON.toJSONString(parseArray));

                int modifyLinkPro = discountDAO.modifyLinkPro(array.toString(), user_id, discountId);
                if (modifyLinkPro > 0)
                    json.setSuccess(true);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(discountId, "优惠券信息为空");
            }

        });
        return json;
    }

    @RequestMapping("/discountUnChooseGoodAll.do")
    @ResponseBody
    public Object discountUnChooseGoodAll(final HttpServletRequest request,
                                          final String discountId, final String[] productIdS) {

        final JsonResult json = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();

        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                DiscountDO discountDO = discountDAO.queryByDiscountId(discountId);
                if (null == discountDO)
                    return;

                String linkPro = discountDO.getLinkPro();
                List<String> parseArray = JSONArrayUtils.parseArray(linkPro, String.class);
                if (CollectionUtils.isEmpty(parseArray)) {
                    parseArray = new ArrayList<String>();
                }

                for (String productId : productIdS) {
                    if (!StringUtils.contains(linkPro, productId))
                        continue;
                    parseArray.remove(productId);
                }

                JSONArray array = JSONArray.parseArray(JSON.toJSONString(parseArray));

                int modifyLinkPro = discountDAO.modifyLinkPro(array.toString(), user_id, discountId);
                if (modifyLinkPro > 0)
                    json.setSuccess(true);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(discountId, "优惠券信息为空");
            }

        });
        return json;
    }

    @RequestMapping("/discountChooseShop.do")
    @ResponseBody
    public Object discountChooseShop(final HttpServletRequest request, final String discountId,
                                     final String teamId) {

        final JsonResult json = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();

        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                DiscountDO discountDO = discountDAO.queryByDiscountId(discountId);
                if (null == discountDO)
                    return;

                String linkTeam = discountDO.getLinkTeam();
                if (StringUtils.equals(linkTeam, teamId))
                    return;

                discountDO.setLinkTeam(teamId);
                discountDO.setLinkTeamChild(null);
                discountDO.setModifier(user_id);
                int modifyLinkTeam = discountDAO.modifyLinkTeam(discountDO);
                if (modifyLinkTeam > 0)
                    json.setSuccess(true);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(discountId, "优惠券信息为空");
            }

        });
        return json;
    }

    @RequestMapping("/discountUnChooseShop.do")
    @ResponseBody
    public Object discountUnChooseShop(final HttpServletRequest request, final String discountId,
                                       final String teamId) {

        final JsonResult json = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();

        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                DiscountDO discountDO = discountDAO.queryByDiscountId(discountId);
                if (null == discountDO)
                    return;

                String thisTeamId = teamId;

                String linkTeam = discountDO.getLinkTeam();
                if (StringUtils.equals(linkTeam, teamId))
                    thisTeamId = null;

                discountDO.setLinkTeam(thisTeamId);
                discountDO.setLinkTeamChild(null);
                discountDO.setModifier(user_id);
                int modifyLinkTeam = discountDAO.modifyLinkTeam(discountDO);
                if (modifyLinkTeam > 0)
                    json.setSuccess(true);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(discountId, "优惠券信息为空");
            }

        });
        return json;
    }

    @RequestMapping("/discountChooseDis.do")
    @ResponseBody
    public Object discountChooseDis(final HttpServletRequest request, final String discountId,
                                    final String chooseDis) {

        final JsonResult json = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();

        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                DiscountDO discountDO = discountDAO.queryByDiscountId(discountId);
                if (null == discountDO)
                    return;

                String canTogeDisid = discountDO.getCanTogeDisid();

                if (StringUtils.contains(canTogeDisid, chooseDis))
                    return;

                List<String> parseArray = JSONArrayUtils.parseArray(canTogeDisid, String.class);
                if (CollectionUtils.isEmpty(parseArray)) {
                    parseArray = new ArrayList<String>();
                }
                parseArray.add(chooseDis);

                JSONArray array = JSONArray.parseArray(JSON.toJSONString(parseArray));

                int modifyLinkDis = discountDAO.modifyLinkDis(array.toString(), user_id, discountId);
                if (modifyLinkDis > 0)
                    json.setSuccess(true);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(discountId, "优惠券信息为空");
            }

        });
        return json;
    }

    @RequestMapping("/discountUnChooseDis.do")
    @ResponseBody
    public Object discountUnChooseDis(final HttpServletRequest request, final String discountId,
                                      final String chooseDis) {

        final JsonResult json = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();

        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                DiscountDO discountDO = discountDAO.queryByDiscountId(discountId);
                if (null == discountDO)
                    return;

                String canTogeDisid = discountDO.getCanTogeDisid();

                if (!StringUtils.contains(canTogeDisid, chooseDis))
                    return;

                List<String> parseArray = JSONArrayUtils.parseArray(canTogeDisid, String.class);
                parseArray.remove(chooseDis);

                JSONArray array = JSONArray.parseArray(JSON.toJSONString(parseArray));

                int modifyLinkDis = discountDAO.modifyLinkDis(array.toString(), user_id, discountId);
                if (modifyLinkDis > 0)
                    json.setSuccess(true);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(discountId, "优惠券信息为空");
            }

        });
        return json;
    }

    @RequestMapping("/discountChooseTeamChild.do")
    @ResponseBody
    public Object discountChooseTeamChild(final HttpServletRequest request,
                                          final String discountId, final String teamId) {

        final JsonResult json = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();

        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                DiscountDO discountDO = discountDAO.queryByDiscountId(discountId);
                if (null == discountDO)
                    return;

                String linkTeamChild = discountDO.getLinkTeamChild();

                if (StringUtils.contains(linkTeamChild, teamId))
                    return;

                List<String> parseArray = JSONArrayUtils.parseArray(linkTeamChild, String.class);
                if (CollectionUtils.isEmpty(parseArray)) {
                    parseArray = new ArrayList<String>();
                }
                parseArray.add(teamId);

                JSONArray array = JSONArray.parseArray(JSON.toJSONString(parseArray));

                int modifyLinkTeamChild = discountDAO.modifyLinkTeamChild(array.toString(),
                    user_id, discountId);
                if (modifyLinkTeamChild > 0)
                    json.setSuccess(true);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(discountId, "优惠券信息为空");
            }

        });
        return json;
    }

    @RequestMapping("/discountUnChooseTeamChild.do")
    @ResponseBody
    public Object discountUnChooseTeamChild(final HttpServletRequest request,
                                            final String discountId, final String teamId) {

        final JsonResult json = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();

        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                DiscountDO discountDO = discountDAO.queryByDiscountId(discountId);
                if (null == discountDO)
                    return;

                String linkTeamChild = discountDO.getLinkTeamChild();

                if (!StringUtils.contains(linkTeamChild, teamId))
                    return;

                List<String> parseArray = JSONArrayUtils.parseArray(linkTeamChild, String.class);
                parseArray.remove(teamId);

                JSONArray array = JSONArray.parseArray(JSON.toJSONString(parseArray));

                int modifyLinkTeamChild = discountDAO.modifyLinkTeamChild(array.toString(),
                    user_id, discountId);
                if (modifyLinkTeamChild > 0)
                    json.setSuccess(true);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(discountId, "优惠券信息为空");
            }

        });
        return json;
    }

    /**
     * 修改发布状态
     * 
     * @param request
     * @param discountId
     * @return
     */
    @RequestMapping("/modifyPublishFlg.do")
    @ResponseBody
    public Object modifyPublishFlg(HttpServletRequest request, final String discountId,
                                   final String publishFlg) {
        final JsonResult data = new JsonResult(true);
        final String user_id = request.getSession().getAttribute("user_id").toString();
        controllerTemplate.execute(data, new ControllerCallBack() {

            @Override
            public void executeService() {
                PublishFlgEnum publishFlgEnum = PublishFlgEnum.getByCode(publishFlg);
                if (null == publishFlgEnum)
                    throw new BaseRuntimeException("枚举信息错误");

                DiscountDO discountDO = discountDAO.queryByDiscountId(discountId);
                if (null == discountDO)
                    throw new BaseRuntimeException("优惠信息查询错误");

                String canTogeDisid = discountDO.getCanTogeDisid();
                List<String> parseArray = JSONArrayUtils.parseArray(canTogeDisid, String.class);
                if (StringUtils.equals(discountDO.getCanTogeUse(), BooleanEnum.TRUE.getCode())
                    && CollectionUtils.isEmpty(parseArray)
                    && publishFlgEnum == PublishFlgEnum.PUBLISH)
                    throw new BaseRuntimeException("请选择可同时使用优惠券，无法发布！");

                int result = discountDAO.modifyPublishFlg(publishFlg, user_id, discountId);
                data.setSuccess(result > 0 ? true : false);
                data.setMessage(result > 0 ? "操作成功" : "操作失败");
            }

            @Override
            public void check() {
                AssertUtil.notBlank(discountId, "id不能为空");
                AssertUtil.notBlank(publishFlg, "状态不能为空");
            }

        });
        return data;
    }

    @RequestMapping("/discountChooseProvinceChild.do")
    @ResponseBody
    public Object discountChooseProvinceChild(final HttpServletRequest request,
                                              final String discountId, final String cityName) {

        final JsonResult json = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();

        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                DiscountDO discountDO = discountDAO.queryByDiscountId(discountId);
                if (null == discountDO)
                    return;

                String linkProvince = discountDO.getLinkProvince();

                if (StringUtils.contains(linkProvince, cityName))
                    return;

                List<String> parseArray = JSONArrayUtils.parseArray(linkProvince, String.class);
                if (CollectionUtils.isEmpty(parseArray)) {
                    parseArray = new ArrayList<String>();
                }
                parseArray.add(cityName);

                JSONArray array = JSONArray.parseArray(JSON.toJSONString(parseArray));

                int modifyLinkProvince = discountDAO.modifyLinkProvince(array.toString(), user_id,
                    discountId);
                if (modifyLinkProvince > 0)
                    json.setSuccess(true);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(discountId, "优惠券信息为空");
            }

        });
        return json;
    }

    @RequestMapping("/discountUnChooseProvinceChild.do")
    @ResponseBody
    public Object discountUnChooseProvinceChild(final HttpServletRequest request,
                                                final String discountId, final String cityName) {

        final JsonResult json = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();

        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                DiscountDO discountDO = discountDAO.queryByDiscountId(discountId);
                if (null == discountDO)
                    return;

                String linkProvince = discountDO.getLinkProvince();

                if (!StringUtils.contains(linkProvince, cityName))
                    return;

                List<String> parseArray = JSONArrayUtils.parseArray(linkProvince, String.class);
                parseArray.remove(cityName);

                JSONArray array = JSONArray.parseArray(JSON.toJSONString(parseArray));

                int modifyLinkProvince = discountDAO.modifyLinkProvince(array.toString(), user_id,
                    discountId);
                if (modifyLinkProvince > 0)
                    json.setSuccess(true);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(discountId, "优惠券信息为空");
            }

        });
        return json;
    }

    @RequestMapping("/selectAllProForDiscount.do")
    @ResponseBody
    public JSONObject selectAllProForDiscount(HttpServletRequest request, Integer offset,
                                              Integer limit) {

        String cityName = request.getParameter("cityName");
        String isUse = request.getParameter("isUse");
        String depth = request.getParameter("depth");

        JSONObject data = new JSONObject();
        try {
            String discountId = request.getParameter("discountId");
            String hasChoose = request.getParameter("linkType");

            DiscountDO discountDO = null;
            List<String> linkCitys = new ArrayList<String>();
            if (StringUtils.isNotBlank(discountId)) {
                discountDO = discountDAO.queryByDiscountId(discountId);
                String linkCity = discountDO.getLinkProvince();
                if (StringUtils.isBlank(linkCity)) {
                    linkCitys = null;
                } else {
                    linkCitys = JSONArrayUtils.parseArray(linkCity, String.class);
                    if (CollectionUtils.isEmpty(linkCitys))
                        linkCitys = null;
                }
            }
            List<String> linkProvinces = sProvinceDAO.queryAllProDisByCitys(isUse, linkCitys);

            List<Map<String, Object>> queryList = sProvinceDAO.queryAllProDiscount(cityName, isUse,
                depth, hasChoose, linkProvinces, offset, limit);
            for (Map<String, Object> map : queryList) {
                String cityNameThis = MapUtils.getString(map, "cityName");
                if (CollectionUtils.isNotEmpty(linkProvinces)
                    && linkProvinces.contains(cityNameThis)
                    && CollectionUtils.isNotEmpty(linkCitys))
                    map.put("hasChoose", true);
                map.put("thisId", MapUtils.getInteger(map, "id"));
            }

            int count = sProvinceDAO.queryAllProDiscountCount(cityName, isUse, depth, hasChoose,
                linkProvinces);
            data.put("rows", queryList);
            data.put("total", count);
        } catch (Exception e) {
            logger.error(MessageFormat.format("查询省异常", new Object[] {}));
        }
        return data;
    }

    @RequestMapping("/selectAllCityForDiscount.do")
    @ResponseBody
    public JSONObject selectAllCityForDiscount(HttpServletRequest request, Integer offset,
                                               Integer limit) {

        String parentId = request.getParameter("parentId");
        String isUse = request.getParameter("isUse");
        String depth = request.getParameter("depth");

        JSONObject data = new JSONObject();
        try {
            String discountId = request.getParameter("discountId");
            String hasChoose = request.getParameter("linkType");

            DiscountDO discountDO = null;
            List<String> linkProvinces = new ArrayList<String>();
            if (StringUtils.isNotBlank(discountId)) {
                discountDO = discountDAO.queryByDiscountId(discountId);
                String linkProvince = discountDO.getLinkProvince();
                if (StringUtils.isBlank(linkProvince)) {
                    linkProvinces = null;
                } else {
                    linkProvinces = JSONArrayUtils.parseArray(linkProvince, String.class);
                    if (CollectionUtils.isEmpty(linkProvinces))
                        linkProvinces = null;
                }
            }

            List<Map<String, Object>> queryList = sProvinceDAO.queryAllCityDiscount(parentId,
                isUse, depth, hasChoose, linkProvinces, offset, limit);
            for (Map<String, Object> map : queryList) {
                String cityNameThis = MapUtils.getString(map, "cityName");
                if (null != discountDO
                    && StringUtils.contains(discountDO.getLinkProvince(), cityNameThis))
                    map.put("hasChoose", true);
            }

            int count = sProvinceDAO.queryAllCityDiscountCount(parentId, isUse, depth, hasChoose,
                linkProvinces);
            data.put("rows", queryList);
            data.put("total", count);
        } catch (Exception e) {
            logger.error(MessageFormat.format("查询省异常", new Object[] {}));
        }
        return data;
    }

    @RequestMapping("/discountChooseAllCityChild.do")
    @ResponseBody
    public Object discountChooseAllCityChild(final HttpServletRequest request,
                                             final String discountId, final String[] cityNameS) {

        final JsonResult json = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();

        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                DiscountDO discountDO = discountDAO.queryByDiscountId(discountId);
                if (null == discountDO)
                    return;

                String linkCity = discountDO.getLinkProvince();
                List<String> parseArray = JSONArrayUtils.parseArray(linkCity, String.class);
                if (CollectionUtils.isEmpty(parseArray)) {
                    parseArray = new ArrayList<String>();
                }

                for (String cityName : cityNameS) {
                    if (StringUtils.contains(linkCity, cityName))
                        continue;
                    parseArray.add(cityName);
                }

                JSONArray array = JSONArray.parseArray(JSON.toJSONString(parseArray));

                int modifyLinkProvince = discountDAO.modifyLinkProvince(array.toString(), user_id,
                    discountId);
                if (modifyLinkProvince > 0)
                    json.setSuccess(true);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(discountId, "优惠券信息为空");
            }

        });
        return json;
    }

    @RequestMapping("/discountUnChooseAllCityChild.do")
    @ResponseBody
    public Object discountUnChooseAllCityChild(final HttpServletRequest request,
                                               final String discountId, final String[] cityNameS) {

        final JsonResult json = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();

        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                DiscountDO discountDO = discountDAO.queryByDiscountId(discountId);
                if (null == discountDO)
                    return;

                String linkCity = discountDO.getLinkProvince();
                List<String> parseArray = JSONArrayUtils.parseArray(linkCity, String.class);
                if (CollectionUtils.isEmpty(parseArray)) {
                    parseArray = new ArrayList<String>();
                }

                for (String cityName : cityNameS) {
                    if (!StringUtils.contains(linkCity, cityName))
                        continue;
                    parseArray.remove(cityName);
                }

                JSONArray array = JSONArray.parseArray(JSON.toJSONString(parseArray));

                int modifyLinkProvince = discountDAO.modifyLinkProvince(array.toString(), user_id,
                    discountId);
                if (modifyLinkProvince > 0)
                    json.setSuccess(true);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(discountId, "优惠券信息为空");
            }

        });
        return json;
    }

    /**
     * 优惠券领取详情
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("getDiscountForUser.do")
    @ResponseBody
    public Object getDiscountForUser(HttpServletRequest request, Integer offset, Integer limit) {

        String discountId = request.getParameter("discountId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        //是否使用    0是  1否
        String useType = request.getParameter("useType");

        JSONObject data = new JSONObject();
        try {
            Date endTime = DateUtil.stringToDate(endDate, DateUtil.webFormat);
            if (null != endTime)
                endTime = DateUtil.addDays(endTime, 1);

            List<String> useTypes = new ArrayList<String>();
            if (StringUtils.equals(useType, "0")) {
                useTypes.add(DiscountStatusEnum.HAS_USED.getCode());
            } else if (StringUtils.equals(useType, "1")) {
                useTypes.add(DiscountStatusEnum.WAIT_USE.getCode());
            }

            List<Map<String, Object>> queryList = discountUserDAO.queryList(discountId,
                DateUtil.stringToDate(startDate, DateUtil.webFormat), endTime, useTypes, offset,
                limit);

            for (Map<String, Object> map : queryList) {
                Date getTime = (Date) map.get("getTime");
                map.put("getTime", DateUtil.dateToString(getTime, DateUtil.newFormat));

                Date timeEnd = (Date) map.get("timeEnd");
                map.put("timeEnd", DateUtil.dateToString(timeEnd, DateUtil.webFormat));

                String status = MapUtils.getString(map, "status");
                DiscountStatusEnum discountStatusEnum = DiscountStatusEnum.getByCode(status);
                if (null != discountStatusEnum)
                    map.put("status_str", discountStatusEnum.message());
            }

            int count = discountUserDAO.queryListCount(discountId,
                DateUtil.stringToDate(startDate, DateUtil.webFormat), endTime, useTypes);

            data.put("rows", queryList);
            data.put("total", count);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error(MessageFormat.format("查询优惠券领取详情异常", e));
        }
        return data;
    }
}