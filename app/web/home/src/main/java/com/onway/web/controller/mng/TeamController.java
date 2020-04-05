package com.onway.web.controller.mng;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.onway.common.lang.HttpUtils;
import com.onway.common.lang.StringUtils;
import com.onway.fyapp.common.dal.dataobject.AccountDO;
import com.onway.fyapp.common.dal.dataobject.ImgDO;
import com.onway.fyapp.common.dal.dataobject.TeamCheckDO;
import com.onway.fyapp.common.dal.dataobject.TeamDO;
import com.onway.fyapp.common.dal.dataobject.TeamUserDO;
import com.onway.fyapp.common.dal.dataobject.UserDO;
import com.onway.model.enums.AccountTypeEnum;
import com.onway.model.enums.BizTypeEnum;
import com.onway.model.enums.DelFlgEnum;
import com.onway.model.enums.TeamLevelEnum;
import com.onway.model.enums.UserRoleEnum;
import com.onway.platform.common.exception.BaseRuntimeException;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.result.JsonResult;
import com.onway.utils.BigdecimalUtil;
import com.onway.utils.FileUploadUtils;
import com.onway.utils.StringToListUtil;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;

/**
 * 团队相关
 * 
 * @author Administrator
 *
 */
@Controller
public class TeamController extends AbstractController {

    /**
     * 分页查询团队信息列表
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/selectteam.do")
    @ResponseBody
    public JSONObject selectuser(HttpServletRequest request, Integer offset, Integer limit) {
        String nickName = request.getParameter("nickName");
        String userCell = request.getParameter("userCell");
        String teamErpNo = request.getParameter("teamErpNo");
        String teamName = request.getParameter("teamName");
        String teamType = request.getParameter("teamType");
        String isTop = request.getParameter("isTop");
        String delflg = request.getParameter("delflg");
        String teamLevel = request.getParameter("teamLevel");
        String realName = request.getParameter("realName");

        String teamCheckRole = request.getParameter("teamCheckRole");
        String teamCheckCell = request.getParameter("teamCheckCell");

        JSONObject data = new JSONObject();
        try {
            List<Map<String, Object>> queryList = teamDAO.selectTeam(userCell, nickName, teamErpNo,
                teamName, teamType, isTop, delflg, teamLevel, realName, null, null, null,
                teamCheckRole, teamCheckCell, offset, limit);
            for (Map<String, Object> map : queryList) {
                String img = MapUtils.getString(map, "imgUrl");
                if (StringUtils.isNotBlank(img))
                    map.put("imgUrl", JSONArray.parseArray(img, String.class).get(0));
                Integer team_Level = MapUtils.getInteger(map, "teamLevel");
                TeamLevelEnum teamLevelEnum = TeamLevelEnum.getByValue(team_Level);
                if (null != teamLevelEnum)
                    map.put("teamLevelStr", teamLevelEnum.message());

                BigDecimal devoteAmount = (BigDecimal) map.get("devoteAmount");
                map.put("devoteAmount", BigdecimalUtil.toMoney(devoteAmount).toSimpleString());

                BigDecimal point = (BigDecimal) map.get("point");
                map.put("point", BigdecimalUtil.toMoney(point).toSimpleString());

                BigDecimal repoint = (BigDecimal) map.get("repoint");
                map.put("repoint", BigdecimalUtil.toMoney(repoint).toSimpleString());
            }
            int count = teamDAO.selectTeamCount(userCell, nickName, teamErpNo, teamName, teamType,
                isTop, delflg, teamLevel, realName, null, null, null, teamCheckRole, teamCheckCell);
            data.put("rows", queryList);
            data.put("total", count);
        } catch (Exception e) {
            logger.error(MessageFormat.format("查询团队异常", new Object[] {}));
        }
        return data;
    }

    @RequestMapping("queryTeamList.do")
    @ResponseBody
    public Object queryTeamList(HttpServletRequest request) {
        List<TeamDO> teamList = teamDAO.selectTopTeam();
        return teamList;
    }

    @RequestMapping("/findteamuser.do")
    @ResponseBody
    public JSONObject select(HttpServletRequest request, Integer offset, Integer limit) {
        String fnickName = request.getParameter("fnickName");
        String fcell = request.getParameter("fcell");
        String frealName = request.getParameter("frealName");
        JSONObject data = new JSONObject();
        try {
            List<UserDO> queryList = userDAO.findteamuser(fnickName, fcell, frealName, offset,
                limit);
            int count = userDAO.findteamuserCount(fnickName, fcell, frealName);
            data.put("rows", queryList);
            data.put("total", count);
        } catch (Exception e) {
            logger.error(MessageFormat.format("查询用户异常", new Object[] {}));
            // throw new BaseRuntimeException(ErrorCode.QUERY_EEOR,
            // ErrorCode.QUERY_EEOR.getDesc());
        }
        return data;
    }

    @RequestMapping("/finduserbyteamid.do")
    @ResponseBody
    public JSONObject selecta(HttpServletRequest request, Integer offset, Integer limit) {
        String teamId = request.getParameter("tid");
        String fnickName = request.getParameter("tanickName");
        String fcell = request.getParameter("tacell");
        String froleType = request.getParameter("taroletype");
        String frealName = request.getParameter("tarealName");
        JSONObject data = new JSONObject();
        try {
            List<Map<String, Object>> queryList = userDAO.finduserbyteamid(teamId, fnickName,
                fcell, froleType, frealName, offset, limit);
            for (Map<String, Object> map : queryList) {
                String teamLeder = (String) map.get("teamLeder");
                UserRoleEnum userRoleEnum = UserRoleEnum.getByCode(teamLeder);
                if (null != userRoleEnum)
                    map.put("teamLederStr", userRoleEnum.message());
            }
            int count = userDAO.finduserbyteamidCount(teamId, fnickName, fcell, froleType,
                frealName);
            data.put("rows", queryList);
            data.put("total", count);
        } catch (Exception e) {
            logger.error(MessageFormat.format("查询用户异常", new Object[] {}));
            // throw new BaseRuntimeException(ErrorCode.QUERY_EEOR,
            // ErrorCode.QUERY_EEOR.getDesc());
        }
        return data;
    }

    // 添加
    @RequestMapping("/addteam.do")
    @ResponseBody
    public Object addmsg(final HttpServletRequest request, final String ateamname,
                         final String ateamtype, final String ateamtop, final String ajd,
                         final String awd, final String atel, final String address,
                         final String active, final String alev, final String aistop,
                         final String aerp, final String ateamAllName,
                         final String achargePersonName, final String achargePersonCell,
                         final String ataxNo, final String abankName, final String abankNo,
                         final String[] businessCategory) {
        final MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        final MultipartFile ateamImg = multipartRequest.getFile("ateamImg");
        final String user_id = request.getSession().getAttribute("user_id").toString();
        final JsonResult data = new JsonResult(true);
        controllerTemplate.execute(data, new ControllerCallBack() {

            @Override
            public void executeService() {

                TeamDO t = new TeamDO();
                if (StringUtils.isNotBlank(alev)) {
                    t.setTeamLevel(Integer.parseInt(alev));
                }
                try {
                    t.setCity(getCityMessageByL(ajd, awd));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                String teamId = codeGenerateComponent.nextCodeByType(BizTypeEnum.KNOW_ID);
                t.setAddress(address);
                t.setFavourableActive(active);
                t.setLatitude(Double.parseDouble(awd));
                t.setLongitude(Double.parseDouble(ajd));
                t.setTeamId(teamId);
                t.setTeamName(ateamname);
                t.setTeamType(ateamtype);
                if (aistop.equals("0")) {
                    t.setTopTeamId(ateamtop);
                }
                t.setErpNo(aerp);
                t.setIsTop(aistop);
                t.setDelFlg("0");
                t.setTeamAllName(ateamAllName);
                t.setChargePersonName(achargePersonName);
                t.setChargePersonCell(achargePersonCell);
                t.setTaxNo(ataxNo);
                t.setBankName(abankName);
                t.setBankNo(abankNo);

                if (null != businessCategory) {
                    List<String> asList = Arrays.asList(businessCategory);
                    JSONArray array = JSONArray.parseArray(JSON.toJSONString(asList));
                    t.setBusinessCategory(array.toString());
                } else {
                    t.setBusinessCategory(null);
                }

                // int tc = teamDAO.checktel(atel);
                // if (tc == 0) {
                t.setCell(atel);
                t.setCreater(user_id);
                teamDAO.insertteam(t);
                // 加图片
                if (null != ateamImg) {
                    try {
                        String teamImg = FileUploadUtils.upload(request, ateamImg);
                        ImgDO existImg = imgDAO
                            .queryImgByProductIdAndType(teamId, null, null, null);
                        List<String> list = StringToListUtil.toList(teamImg);
                        JSONArray array = JSONArray.parseArray(JSON.toJSONString(list));
                        ImgDO img = new ImgDO();
                        img.setImgId(teamId);
                        img.setImgUrl(array.toString());
                        img.setDelFlg("0");
                        if (null != existImg) {
                            img.setId(existImg.getId());
                            imgDAO.updateById(img);
                        } else {
                            imgDAO.insert(img);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                // AccountTempDO at = new AccountTempDO();
                // at.setDelFlg("0");
                // at.setLinkId(t.getTeamId());
                // at.setType("2");
                // accountTempDAO.creatTemp(at);
                AccountDO ao = new AccountDO();
                ao.setAccountNo(codeGenerateComponent.nextCodeByType(BizTypeEnum.TEAM_NO));
                ao.setLinkId(t.getTeamId());
                ao.setType(AccountTypeEnum.TEAM.getCode());
                ao.setDelFlg("0");
                int result = accountDAO.creat(ao);
                if (result > 0) {
                    data.setSuccess(true);
                    data.setMessage("操作成功");
                } else {
                    data.setSuccess(false);
                    data.setMessage("操作失败");
                }
                // } else {
                // data.setSuccess(false);
                // data.setMessage("已有该号码，请重新输入");
                // }
            }

            @Override
            public void check() {
            }
        });
        return data;
    }

    /**
     * 根据经纬度返回所属市区
     * 
     * @param lng
     * @param lat
     * @return
     * @throws IOException
     */
    public static String getCityMessageByL(String lng, String lat) throws IOException {
        String city = "";

        String key = "MsN1WtQmTst4LRPqICEcGF1y8Pz3iOoX";
        String url = "http://api.map.baidu.com/geocoder/v2/";

        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("location", lat + "," + lng);
        paramsMap.put("ak", key);
        paramsMap.put("output", "json");
        // 返回发送结果
        String returnStr = HttpUtils.executePostMethod(url, "UTF-8", paramsMap);

        JSONObject parseObject = JSON.parseObject(returnStr);
        int status = (Integer) parseObject.get("status");
        if (status == 0) {
            String result = parseObject.get("result").toString();
            JSONObject parseResult = JSON.parseObject(result);
            String addressComponent = parseResult.get("addressComponent").toString();
            JSONObject parseAddressComponent = JSON.parseObject(addressComponent);
            city = (String) parseAddressComponent.get("city");
        }
        return city;
    }

    /**
     * 修改
     */
    @RequestMapping("/updateteam.do")
    @ResponseBody
    public Object update(final HttpServletRequest request) {
        final String eteamid = request.getParameter("eteamid");
        final String eteamname = request.getParameter("eteamname");
        final String eteamtype = request.getParameter("eteamtype");
        final String eteamtop = request.getParameter("eteamtop");
        final String ejd = request.getParameter("ejd");
        final String ewd = request.getParameter("ewd");
        final String etel = request.getParameter("etel");
        final String eaddress = request.getParameter("eaddress");
        final String eactive = request.getParameter("eactive");
//        final String elev = request.getParameter("elev");
        final String eistop = request.getParameter("eistop");
        final String erp = request.getParameter("erp");

        final String eteamAllName = request.getParameter("eteamAllName");
        final String echargePersonName = request.getParameter("echargePersonName");
        final String echargePersonCell = request.getParameter("echargePersonCell");
        final String etaxNo = request.getParameter("etaxNo");
        final String ebankName = request.getParameter("ebankName");
        final String ebankNo = request.getParameter("ebankNo");

        final String[] businessCategory = request.getParameterValues("businessCategory");

        final String user_id = request.getSession().getAttribute("user_id").toString();

        final JsonResult data = new JsonResult(true);
        controllerTemplate.execute(data, new ControllerCallBack() {

            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile               eteamImg         = multipartRequest.getFile("eteamImg");

            @Override
            public void executeService() {
                String topTeamIdTmp = "";
                if (eistop.equals("0")) {
                    topTeamIdTmp = eteamtop;
                }
                //                Integer lev = null;
                //                if (StringUtils.isNotBlank(elev)) {
                //                    lev = Integer.parseInt(elev);
                //                }
                String city = "";
                try {
                    city = getCityMessageByL(ejd, ewd);
                } catch (IOException e) {

                    e.printStackTrace();
                }

                TeamDO teamDO = teamDAO.selectByTeamId(eteamid);
                if (null == teamDO) {
                    data.setSuccess(false);
                    data.setMessage("团队信息查询异常");
                    return;
                }
                teamDO.setTeamName(eteamname);
                teamDO.setTeamType(eteamtype);
                teamDO.setCell(etel);
                teamDO.setAddress(eaddress);
                teamDO.setLongitude(Double.parseDouble(ejd));
                teamDO.setTopTeamId(topTeamIdTmp);
                teamDO.setIsTop(eistop);
                teamDO.setLatitude(Double.parseDouble(ewd));
                teamDO.setFavourableActive(eactive);
                teamDO.setTeamLevel(teamDO.getTeamLevel());
                teamDO.setErpNo(erp);
                teamDO.setCity(city);
                teamDO.setTeamAllName(eteamAllName);
                teamDO.setChargePersonName(echargePersonName);
                teamDO.setChargePersonCell(echargePersonCell);
                teamDO.setTaxNo(etaxNo);
                teamDO.setBankName(ebankName);
                teamDO.setBankNo(ebankNo);
                teamDO.setModifier(user_id);

                if (null != businessCategory) {
                    List<String> asList = Arrays.asList(businessCategory);
                    JSONArray array = JSONArray.parseArray(JSON.toJSONString(asList));
                    teamDO.setBusinessCategory(array.toString());
                } else {
                    teamDO.setBusinessCategory(null);
                }

                int result = teamDAO.updateteam(teamDO);

                if (null != eteamImg) {
                    try {
                        String teamImg = FileUploadUtils.upload(request, eteamImg);
                        ImgDO existImg = imgDAO.queryImgByProductIdAndType(eteamid, null, null,
                            null);
                        List<String> list = StringToListUtil.toList(teamImg);
                        JSONArray array = JSONArray.parseArray(JSON.toJSONString(list));
                        ImgDO img = new ImgDO();
                        img.setImgId(eteamid);
                        img.setImgUrl(array.toString());
                        img.setDelFlg("0");
                        if (null != existImg) {
                            img.setId(existImg.getId());
                            imgDAO.updateById(img);
                        } else {
                            imgDAO.insert(img);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                data.setSuccess(result > 0 ? true : false);
                data.setMessage(result > 0 ? "操作成功" : "操作失败");

            }

            @Override
            public void check() {

            }
        });
        return data;
    }

    /**
     * 删除
     */
    @RequestMapping("/deleteteam.do")
    @ResponseBody
    public Object delete(HttpServletRequest request, final String teamid) {
        final JsonResult json = new JsonResult(true);
        final String user_id = request.getSession().getAttribute("user_id").toString();
        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                TeamDO teamDO = teamDAO.selectByTeamId(teamid);
                if (null == teamDO)
                    throw new BaseRuntimeException("门店信息查询异常");

                String teamId = teamDO.getTeamId();
                if (StringUtils.equals(teamDO.getIsTop(), "1")) {
                    // 如果是总店下属有分店的话不能删除
                    int count = teamDAO.getNotDealChildTeam(teamId, DelFlgEnum.NOT_DEL.getCode());
                    //teamDAO.selectTeamByTopId(teamId);
                    int count1 = productTeamDAO.queryCountGoodsByTeamId(teamId);
                    if (count > 0) {
                        throw new BaseRuntimeException("删除失败，该总店机构下有子店存在");
                    }
                    if (count1 > 0) {
                        throw new BaseRuntimeException("删除失败，该总店机构下有关联商品存在");
                    }

                    int result = teamDAO.deleteteam(user_id, teamId);
                    if (result <= 0) {
                        throw new BaseRuntimeException("删除失败");
                    }

                    //清理关联门店用户
                    teamUserDAO.cleanALlTeamLink(teamId);
                    teamCheckDAO.cleanALlTeamLink(teamId);

                    json.setSuccess(true);
                    json.setMessage("操作成功");

                } else {
                    //子店不关联店铺
                    int result = teamDAO.deleteteam(user_id, teamId);
                    if (result <= 0) {
                        throw new BaseRuntimeException("删除失败");
                    }

                    //清理关联门店用户
                    teamUserDAO.cleanALlTeamLink(teamId);
                    teamCheckDAO.cleanALlTeamLink(teamId);

                    json.setSuccess(true);
                    json.setMessage("操作成功");
                }
            }

            @Override
            public void check() {
            }

        });
        return json;
    }

    /**
     * 门店恢复
     * 
     * @param request
     * @param teamid
     * @return
     */
    @RequestMapping("/resumTeam.do")
    @ResponseBody
    public Object resumTeam(HttpServletRequest request, final String teamid) {
        final JsonResult json = new JsonResult(true);
        final String user_id = request.getSession().getAttribute("user_id").toString();
        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                TeamDO teamDO = teamDAO.selectByTeamId(teamid);
                if (null == teamDO)
                    throw new BaseRuntimeException("门店信息查询异常");

                String teamId = teamDO.getTeamId();
                if (StringUtils.equals(teamDO.getIsTop(), "1")) {
                    //总店直接恢复
                    int result = teamDAO.resumTeam(user_id, teamId);
                    if (result <= 0) {
                        throw new BaseRuntimeException("恢复失败");
                    }

                    json.setSuccess(true);
                    json.setMessage("操作成功");

                } else {
                    //判断子店上的总店是否被删除
                    String topTeamId = teamDO.getTopTeamId();
                    TeamDO teamDOTop = teamDAO.selectByTeamId(topTeamId);
                    if (null == teamDOTop)
                        throw new BaseRuntimeException("原所属总店信息查询异常");

                    if (StringUtils.equals(teamDOTop.getDelFlg(), DelFlgEnum.HAS_DEL.getCode()))
                        throw new BaseRuntimeException("总店已被删除，请先恢复总店：" + teamDOTop.getTeamName());

                    int result = teamDAO.resumTeam(user_id, teamId);
                    if (result <= 0) {
                        throw new BaseRuntimeException("恢复失败");
                    }

                    json.setSuccess(true);
                    json.setMessage("操作成功");
                }
            }

            @Override
            public void check() {
            }

        });
        return json;
    }

    // 删除 团队成员数据
    @RequestMapping("/deleteteamuser.do")
    @ResponseBody
    public Object deleteu(HttpServletRequest request, final String userid) {
        final JsonResult json = new JsonResult(true);
        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                System.out.println("用戶編號刪除" + userid);
                int result = teamUserDAO.deleteteamuser(userid);
                if (result > 0) {
                    json.setSuccess(true);
                    json.setMessage("操作成功");
                } else {
                    json.setSuccess(false);
                    json.setMessage("操作失败");
                }
            }

            @Override
            public void check() {
            }

        });
        return json;
    }

    // 添加团队成员
    @RequestMapping("/addteamuser.do")
    @ResponseBody
    public Object add(final HttpServletRequest request, final String ateamuserid,
                      final String ateamleder, final String atdeamid) {

        final JsonResult data = new JsonResult(true);
        controllerTemplate.execute(data, new ControllerCallBack() {

            @Override
            public void executeService() {
                /**
                 * TeamUserDO b=teamUserDAO.findteamuserbyid(ateamuserid);
                 * if(b!=null){ System.out.println("成员编号"+b.getTeamUserId());
                 * data.setSuccess(false); data.setMessage("团队已有该成员");
                 * 
                 * }else{ UserDO bo=userDAO.finduserbyid(ateamuserid);
                 * if(bo!=null){ System.out.println("添加成员编号"+bo.getUserId());
                 * TeamUserDO tu=new TeamUserDO(); tu.setTeamId(atdeamid);
                 * tu.setTeamLeder(ateamleder); tu.setTeamUserId(ateamuserid);
                 * int result = teamUserDAO.insertteamuser(tu); if (result > 0)
                 * { data.setSuccess(true); data.setMessage("操作成功"); } else {
                 * data.setSuccess(false); data.setMessage("操作失败"); }
                 * 
                 * 
                 * }else{ data.setSuccess(false); data.setMessage("该用户不存在");
                 * 
                 * } }
                 **/

            }

            @Override
            public void check() {
            }
        });
        return data;
    }

    // 添加成员
    @RequestMapping("addcy.do")
    @ResponseBody
    public Object u(final HttpServletRequest request) {
        final String b = request.getParameter("b");
        final String tid = request.getParameter("tid");

        final JsonResult data = new JsonResult(true);
        controllerTemplate.execute(data, new ControllerCallBack() {
            @Override
            public void executeService() {
                String tmpb = b;
                tmpb = tmpb.replace("[", "");
                tmpb = tmpb.replace("]", "");
                tmpb = tmpb.replace("\"", "");
                String[] st = tmpb.split(",");
                for (String s : st) {
                    if (null == teamUserDAO.checkOnlyUserTeam(tid, s)) {
                        TeamUserDO tu = new TeamUserDO();
                        tu.setTeamId(tid);
                        tu.setTeamLeder(UserRoleEnum.ASS_ROLE.getCode());
                        tu.setTeamUserId(s);
                        int result = teamUserDAO.insertteamuser(tu);

                        if (result > 0) {
                            data.setSuccess(true);
                            data.setMessage("操作成功");
                        } else {
                            data.setSuccess(false);
                            data.setMessage("操作失败");
                        }
                    } else {
                        data.setSuccess(true);
                        data.setMessage("操作成功");
                    }
                }
            }

            @Override
            public void check() {

            }
        });
        return data;
    }

    // 添加领导
    @RequestMapping("addld.do")
    @ResponseBody
    public Object ul(final HttpServletRequest request) {
        final String lid = request.getParameter("lid");
        final String tid = request.getParameter("tid");

        final JsonResult data = new JsonResult(true);
        controllerTemplate.execute(data, new ControllerCallBack() {
            @Override
            public void executeService() {
                TeamUserDO ct = teamUserDAO.checkld(tid, UserRoleEnum.LEADER_ROLE.getCode());
                if (ct == null) {
                    TeamUserDO tu = new TeamUserDO();
                    tu.setTeamId(tid);
                    tu.setTeamLeder("1");
                    tu.setTeamUserId(lid);
                    int result = teamUserDAO.insertteamuser(tu);

                    if (result > 0) {
                        data.setSuccess(true);
                        data.setMessage("操作成功");
                    } else {
                        data.setSuccess(false);
                        data.setMessage("操作失败");
                    }
                } else {
                    data.setSuccess(false);
                    data.setMessage("已有领导");

                }

            }

            @Override
            public void check() {
                // TODO Auto-generated method stub

            }

        });
        return data;
    }

    // 添加
    @RequestMapping("/findtop.do")
    @ResponseBody
    public JSONObject select(HttpServletRequest request) {

        JSONObject data = new JSONObject();
        try {
            List<TeamDO> queryList = teamDAO.findtop();
            for (TeamDO teamDO : queryList) {
                System.out.println("团队名称");

                System.out.println(teamDO);
            }
            data.put("teams", queryList);

        } catch (Exception e) {
            logger.error(MessageFormat.format("查询用户异常", new Object[] {}));
            // throw new BaseRuntimeException(ErrorCode.QUERY_EEOR,
            // ErrorCode.QUERY_EEOR.getDesc());
        }
        return data;
    }

    /**
     * 修改成员角色
     * 
     * @param request
     * @param changeRoleUserId
     * @param changeRoleType
     * @return
     */
    @RequestMapping("/changeTeamRole.do")
    @ResponseBody
    public Object changeTeamRole(final HttpServletRequest request, final String changeRoleUserId,
                                 final String changeRoleType) {

        final JsonResult result = new JsonResult(false);
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {
                UserRoleEnum userRoleEnum = UserRoleEnum.getByCode(changeRoleType);
                if (null == userRoleEnum) {
                    result.setMessage("角色信息有误");
                    return;
                }
                //
                TeamUserDO userTeamInfo = teamUserDAO.getUserTeamInfo(changeRoleUserId);
                if (null == userTeamInfo) {
                    result.setMessage("团队信息异常");
                    return;
                }

                if (StringUtils.equals(changeRoleType, UserRoleEnum.ASS_ROLE.getCode())) {
                    //
                    teamUserDAO.changeTeamRole(changeRoleType, changeRoleUserId);
                } else {
                    TeamUserDO thisTeamUser = teamUserDAO.checkld(userTeamInfo.getTeamId(),
                        changeRoleType);
                    if (null != thisTeamUser)
                        teamUserDAO.changeTeamRole(UserRoleEnum.ASS_ROLE.getCode(),
                            thisTeamUser.getTeamUserId());

                    teamUserDAO.changeTeamRole(changeRoleType, changeRoleUserId);
                }
                result.setSuccess(true);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(changeRoleUserId, "成员信息不存在");
                AssertUtil.notBlank(changeRoleType, "未选择成员角色");
            }
        });
        return result;
    }

    // ////////////////////////////////////////////////////////////////////

    /**
     * 查询门店审核员
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/queryTeamCheckUser.do")
    @ResponseBody
    public Object queryTeamCheckUser(HttpServletRequest request, Integer offset, Integer limit) {

        String teamId = request.getParameter("teamId");
        String nickName = request.getParameter("checkNickName");
        String cell = request.getParameter("checkCell");
        String roleType = request.getParameter("checkRoleType");
        String realName = request.getParameter("checkRealName");
        JSONObject data = new JSONObject();
        try {

            List<Map<String, Object>> queryTeamCheckUser = teamCheckDAO.queryTeamCheckUser(teamId,
                nickName, cell, roleType, realName, offset, limit);

            for (Map<String, Object> map : queryTeamCheckUser) {
                String teamLeder = (String) map.get("checkRole");
                UserRoleEnum userRoleEnum = UserRoleEnum.getByCode(teamLeder);
                if (null != userRoleEnum)
                    map.put("checkRoleStr", userRoleEnum.message());
            }
            int count = teamCheckDAO.queryTeamCheckUserCount(teamId, nickName, cell, roleType,
                realName);

            data.put("rows", queryTeamCheckUser);
            data.put("total", count);

        } catch (Exception e) {

            logger.error(MessageFormat.format("查询用户异常", new Object[] {}));
        }
        return data;
    }

    /**
     * 修改审核角色
     * 
     * @param request
     * @param checkteamId
     * @param checkUserId
     * @param checkRoleType
     * @return
     */
    @RequestMapping("/changeTeamCheckRole.do")
    @ResponseBody
    public Object changeTeamCheckRole(final HttpServletRequest request, final String checkteamId,
                                      final String checkUserId, final String checkRoleType,
                                      final String id) {

        final JsonResult result = new JsonResult(false);
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {
                UserRoleEnum userRoleEnum = UserRoleEnum.getByCode(checkRoleType);
                if (null == userRoleEnum) {
                    result.setMessage("角色信息有误");
                    return;
                }
                if (StringUtils.equals(checkRoleType, UserRoleEnum.CUSTOMER.getCode())) {
                    teamCheckDAO.delTeamCheckRole(Integer.valueOf(id));
                } else {
                    // 新增
                    // 先判断原来是否有该角色
                    TeamCheckDO teamCheckDO = teamCheckDAO.queryTeamCheck(checkteamId, null,
                        checkRoleType);
                    if (null != teamCheckDO) {
                        teamCheckDO.setTeamUserId(checkUserId);
                        teamCheckDAO.updateTeamCheckRole(teamCheckDO);
                    } else {
                        TeamCheckDO checkDO = new TeamCheckDO();
                        checkDO.setTeamId(checkteamId);
                        checkDO.setTeamUserId(checkUserId);
                        checkDO.setCheckLeder(checkRoleType);
                        teamCheckDAO.newTeamCheck(checkDO);
                    }
                }

                result.setSuccess(true);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(checkUserId, "成员信息不存在");
                AssertUtil.notBlank(checkRoleType, "未选择成员角色");
            }
        });
        return result;
    }

    @RequestMapping("/delTeamCheckRole.do")
    @ResponseBody
    public Object delTeamCheckRole(final HttpServletRequest request, final Integer id) {

        final JsonResult result = new JsonResult(false);
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {
                teamCheckDAO.delTeamCheckRole(id);

                result.setSuccess(true);
            }

            @Override
            public void check() {

            }
        });
        return result;
    }

    @RequestMapping("/selectuserforteamcheck.do")
    @ResponseBody
    public JSONObject selectuserforteamcheck(HttpServletRequest request, Integer offset,
                                             Integer limit) {
        String nickName = request.getParameter("nickName");
        String realName = request.getParameter("realName");
        String cell = request.getParameter("selectcell");
        String erpNo = request.getParameter("erpNo");

        String teamId = request.getParameter("teamId");
        JSONObject data = new JSONObject();
        try {
            List<Map<String, Object>> queryList = userDAO.selectUserList(nickName, realName, cell,
                null, null, null, null, null, null, null, null, null, null, erpNo, offset, limit);
            for (Map<String, Object> map : queryList) {
                String userId = (String) map.get("userId");
                TeamCheckDO queryTeamCheckFirst = teamCheckDAO.queryTeamCheck(teamId, userId,
                    UserRoleEnum.SALES_ROLE.getCode());
                if (null == queryTeamCheckFirst) {
                    map.put("checkRoleFirst", UserRoleEnum.CUSTOMER.getCode());
                    map.put("checkRoleIdFirst", "0");
                } else {
                    map.put("checkRoleFirst", queryTeamCheckFirst.getCheckLeder());
                    map.put("checkRoleIdFirst", queryTeamCheckFirst.getId());
                }

                TeamCheckDO queryTeamCheckSecond = teamCheckDAO.queryTeamCheck(teamId, userId,
                    UserRoleEnum.ACC_ROLE.getCode());
                if (null == queryTeamCheckSecond) {
                    map.put("checkRoleSecond", UserRoleEnum.CUSTOMER.getCode());
                    map.put("checkRoleIdSecond", "0");
                } else {
                    map.put("checkRoleSecond", queryTeamCheckSecond.getCheckLeder());
                    map.put("checkRoleIdSecond", queryTeamCheckSecond.getId());
                }

                TeamCheckDO queryTeamCheckThird = teamCheckDAO.queryTeamCheck(teamId, userId,
                    UserRoleEnum.SAFE_ROLE.getCode());
                if (null == queryTeamCheckThird) {
                    map.put("checkRoleThird", UserRoleEnum.CUSTOMER.getCode());
                    map.put("checkRoleIdThird", "0");
                } else {
                    map.put("checkRoleThird", queryTeamCheckThird.getCheckLeder());
                    map.put("checkRoleIdThird", queryTeamCheckThird.getId());
                }

                TeamCheckDO queryTeamCheckFour = teamCheckDAO.queryTeamCheck(teamId, userId,
                    UserRoleEnum.BANSHI_MA.getCode());
                if (null == queryTeamCheckFour) {
                    map.put("checkRoleFour", UserRoleEnum.CUSTOMER.getCode());
                    map.put("checkRoleIdFour", "0");
                } else {
                    map.put("checkRoleFour", queryTeamCheckFour.getCheckLeder());
                    map.put("checkRoleIdFour", queryTeamCheckFour.getId());
                }

                TeamCheckDO queryTeamCheckFive = teamCheckDAO.queryTeamCheck(teamId, userId,
                    UserRoleEnum.DAQU_MA.getCode());
                if (null == queryTeamCheckFive) {
                    map.put("checkRoleFive", UserRoleEnum.CUSTOMER.getCode());
                    map.put("checkRoleIdFive", "0");
                } else {
                    map.put("checkRoleFive", queryTeamCheckFive.getCheckLeder());
                    map.put("checkRoleIdFive", queryTeamCheckFive.getId());
                }

                TeamCheckDO queryTeamCheckSix = teamCheckDAO.queryTeamCheck(teamId, userId,
                    UserRoleEnum.DU_DAO.getCode());
                if (null == queryTeamCheckSix) {
                    map.put("checkRoleSix", UserRoleEnum.CUSTOMER.getCode());
                    map.put("checkRoleIdSix", "0");
                } else {
                    map.put("checkRoleSix", queryTeamCheckSix.getCheckLeder());
                    map.put("checkRoleIdSix", queryTeamCheckSix.getId());
                }

                TeamCheckDO queryTeamCheckSeven = teamCheckDAO.queryTeamCheck(teamId, userId,
                    UserRoleEnum.CW_MA.getCode());
                if (null == queryTeamCheckSeven) {
                    map.put("checkRoleSeven", UserRoleEnum.CUSTOMER.getCode());
                    map.put("checkRoleIdSeven", "0");
                } else {
                    map.put("checkRoleSeven", queryTeamCheckSeven.getCheckLeder());
                    map.put("checkRoleIdSeven", queryTeamCheckSeven.getId());
                }

                TeamCheckDO queryTeamCheckEigth = teamCheckDAO.queryTeamCheck(teamId, userId,
                    UserRoleEnum.CW_NQ.getCode());
                if (null == queryTeamCheckEigth) {
                    map.put("checkRoleEigth", UserRoleEnum.CUSTOMER.getCode());
                    map.put("checkRoleIdEigth", "0");
                } else {
                    map.put("checkRoleEigth", queryTeamCheckEigth.getCheckLeder());
                    map.put("checkRoleIdEigth", queryTeamCheckEigth.getId());
                }
            }
            data.put("rows", queryList);
            data.put("total", userDAO.queryListCount(nickName, realName, cell, null, null, null,
                null, null, null, null, null, null, null, erpNo));
        } catch (Exception e) {
            logger.error(MessageFormat.format("查询用户异常", new Object[] {}));
            // throw new BaseRuntimeException(ErrorCode.QUERY_EEOR,
            // ErrorCode.QUERY_EEOR.getDesc());
        }
        return data;
    }

    /**
     * 所有门店
     * 
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/getAllTeamMsg.do")
    @ResponseBody
    public JSONObject getAllTeamMsg(Integer offset, Integer limit) {
        JSONObject jo = new JSONObject();
        List<Map<String, Object>> queryAllTeamMsg = teamDAO.queryAllTeamMsg(null, null);
        jo.put("rows", queryAllTeamMsg);
        return jo;
    }
}
