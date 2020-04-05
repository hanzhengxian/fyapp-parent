package com.onway.web.controller.mng;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.result.JsonResult;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;

//张月
@Controller
public class PlanController extends AbstractController {

    @RequestMapping("planList.do")
    @ResponseBody
    public Object planList(HttpServletRequest request, Integer offset, Integer limit) {
        //        String planTeamId = request.getParameter("planTeamId");
        //        String planId = request.getParameter("planId");
        //        String planName = request.getParameter("planName");
        //        String planChildType = request.getParameter("planChildType");
        //        String mainType = request.getParameter("mainType");
        //        JSONObject data = new JSONObject();
        //        try {
        //            List<PlanCopyDO> pllist = planCopyDAO.selectPlan(planTeamId, planId, planName,
        //                planChildType, mainType, offset, limit);
        //            int size = planCopyDAO.selectPlanPage(planTeamId, planId, planName, planChildType,
        //                mainType);
        //            data.put("success", true);
        //            data.put("rows", pllist);
        //            data.put("total", size);
        //        } catch (Exception e) {
        //            // TODO: handle exception
        //            logger.error(MessageFormat.format("查询用户异常", new Object[] {}));
        //        }
        //        return data;
        return null;
    }

    @RequestMapping("findbyteamid.do")
    @ResponseBody
    public Object plan(HttpServletRequest request, Integer offset, Integer limit) {
        //        String planTeamId = request.getParameter("planteamid");
        //
        //        JSONObject data = new JSONObject();
        //        try {
        //            List<PlanCopyDO> planlist = planCopyDAO.findbyteamid(planTeamId, offset, limit);
        //            int size = planCopyDAO.findbyteamidpage(planTeamId);
        //            data.put("success", true);
        //            data.put("rows", planlist);
        //            data.put("total", size);
        //        } catch (Exception e) {
        //            // TODO: handle exception
        //            logger.error(MessageFormat.format("查询用户异常", new Object[] {}));
        //        }
        //        return data;
        return null;
    }

    // 添加
    @RequestMapping("/addplan.do")
    @ResponseBody
    public Object add(final HttpServletRequest request, final String aplanteamid,
                      final String aplanname, final String amaintype, final String acategory,
                      final String aroletype, final String aoperateobj, final String apointone,
                      final String atemppointone, final String ahubalanceone,
                      final String atemphubalanceone, final String apointtwo,
                      final String atemppointtwo, final String ahubalancetwo,
                      final String atemphubalancetwo, final String apointthree,
                      final String ahubalancethree, final String atemphubalancethree,
                      final String apointfour, final String atemppointfour,
                      final String ahubalancefour, final String atemphubalancefour,
                      final String apointfive, final String atemppointfive,
                      final String ahubalancefive, final String atemphubalancefive,
                      final String aplanchildtype, final String alimitamount,
                      final String astartdate, final String aenddate, final String ayouhuiflg,
                      final String adelflg, final String alimitcount, final String abuyflg,
                      final String atemppointthree, final String aplangrandtype,
                      final String areachoffone, final String areachofftwo,
                      final String areachofffour, final String areachoffthree,
                      final String areachofffive

    ) {

        final JsonResult data = new JsonResult(true);
        //        controllerTemplate.execute(data, new ControllerCallBack() {
        //
        //            @Override
        //            public void executeService() {
        //
        //                Date startTime = null;
        //                Date endTime = null;
        //
        //                if (StringUtils.hasLength(astartdate)) {
        //                    try {
        //                        startTime = DateUtils.parseDate(astartdate, DateUtils.webFormat);
        //                    } catch (ParseException e) {
        //                        // TODO Auto-generated catch block
        //                        e.printStackTrace();
        //                    }
        //                }
        //
        //                if (StringUtils.hasLength(aenddate)) {
        //                    try {
        //                        endTime = DateUtils.parseDate(aenddate, DateUtils.webFormat);
        //                    } catch (ParseException e) {
        //                        // TODO Auto-generated catch block
        //                        e.printStackTrace();
        //                    }
        //                }
        //                PlanCopyDO p = new PlanCopyDO();
        //                p.setPlanId((codeGenerateComponent.nextCodeByType(BizTypeEnum.PLAN_NO)));
        //                p.setBuyFlg(abuyflg);
        //                p.setCategory(acategory);
        //                p.setDelFlg("0");
        //                p.setEndDate(endTime);
        //                p.setPlanGrandType(aplangrandtype);
        //                if (StringUtils.isNotBlank(areachoffone)) {
        //                    p.setReachOffOne(Double.parseDouble(areachoffone));
        //                }
        //                if (StringUtils.isNotBlank(areachofftwo)) {
        //                    p.setReachOffTwo(Double.parseDouble(areachofftwo));
        //                }
        //                if (StringUtils.isNotBlank(areachoffthree)) {
        //                    p.setReachOffThree(Double.parseDouble(areachoffthree));
        //                }
        //                if (StringUtils.isNotBlank(areachofffour)) {
        //                    p.setReachOffFour(Double.parseDouble(areachofffour));
        //                }
        //                if (StringUtils.isNotBlank(areachofffive)) {
        //                    p.setReachOffFive(Double.parseDouble(areachofffive));
        //                }
        //                if (StringUtils.isNotBlank(ahubalancefive)) {
        //                    p.setHuBalanceFive(Double.parseDouble(ahubalancefive));
        //                }
        //                if (StringUtils.isNotBlank(ahubalancefour)) {
        //                    p.setHuBalanceFour(Double.parseDouble(ahubalancefour));
        //                }
        //                if (StringUtils.isNotBlank(ahubalanceone)) {
        //                    p.setHuBalanceOne(Double.parseDouble(ahubalanceone));
        //                }
        //                if (StringUtils.isNotBlank(ahubalancethree)) {
        //                    p.setHuBalanceThree(Double.parseDouble(ahubalancethree));
        //                }
        //                if (StringUtils.isNotBlank(ahubalancetwo)) {
        //                    p.setHuBalanceTwo(Double.parseDouble(ahubalancetwo));
        //                }
        //                if (StringUtils.isNotBlank(alimitamount)) {
        //                    p.setLimitAmount(new Money(alimitamount));
        //                }
        //                if (StringUtils.isNotBlank(alimitcount)) {
        //                    p.setLimitCount(Integer.parseInt(alimitcount));
        //                }
        //
        //                if (StringUtils.isNotBlank(apointfive)) {
        //                    p.setPointFive(Double.parseDouble(apointfive));
        //                }
        //                if (StringUtils.isNotBlank(apointfour)) {
        //                    p.setPointFour(Double.parseDouble(apointfour));
        //                }
        //                if (StringUtils.isNotBlank(apointone)) {
        //                    p.setPointOne(Double.parseDouble(apointone));
        //                }
        //                if (StringUtils.isNotBlank(apointthree)) {
        //                    p.setPointThree(Double.parseDouble(apointthree));
        //                }
        //                if (StringUtils.isNotBlank(apointtwo)) {
        //                    p.setPointTwo(Double.parseDouble(apointtwo));
        //                }
        //
        //                if (StringUtils.isNotBlank(atemphubalancefive)) {
        //                    p.setTempHuBalanceFive(Double.parseDouble(atemphubalancefive));
        //                }
        //                if (StringUtils.isNotBlank(atemphubalancefour)) {
        //                    p.setTempHuBalanceFour(Double.parseDouble(atemphubalancefour));
        //                }
        //                if (StringUtils.isNotBlank(atemphubalanceone)) {
        //                    p.setTempHuBalanceOne(Double.parseDouble(atemphubalanceone));
        //                }
        //                if (StringUtils.isNotBlank(atemphubalancethree)) {
        //                    p.setTempHuBalanceThree(Double.parseDouble(atemphubalancethree));
        //                }
        //                if (StringUtils.isNotBlank(atemphubalancetwo)) {
        //                    p.setTempHuBalanceTwo(Double.parseDouble(atemphubalancetwo));
        //                }
        //                if (StringUtils.isNotBlank(atemppointfive)) {
        //                    p.setTempPointFive(Double.parseDouble(atemppointfive));
        //                }
        //                if (StringUtils.isNotBlank(atemppointfour)) {
        //                    p.setTempPointFour(Double.parseDouble(atemppointfour));
        //                }
        //                if (StringUtils.isNotBlank(atemppointone)) {
        //                    p.setTempPointOne(Double.parseDouble(atemppointone));
        //                }
        //                if (StringUtils.isNotBlank(atemppointthree)) {
        //                    p.setTempPointThree(Double.parseDouble(atemppointthree));
        //                }
        //                if (StringUtils.isNotBlank(atemppointtwo)) {
        //                    p.setTempPointTwo(Double.parseDouble(atemppointtwo));
        //                }
        //                p.setMainType(amaintype);
        //                p.setOperateObj(aoperateobj);
        //                p.setPlanChildType(aplanchildtype);
        //
        //                p.setPlanName(aplanname);
        //                p.setPlanTeamId(aplanteamid);
        //                p.setRoleType(aroletype);
        //                p.setStartDate(startTime);
        //                p.setYouhuiFlg(ayouhuiflg);
        //
        //                int result = planCopyDAO.insertPlan(p);
        //                if (result > 0) {
        //                    data.setSuccess(true);
        //                    data.setMessage("操作成功");
        //                } else {
        //                    data.setSuccess(false);
        //                    data.setMessage("操作失败");
        //                }
        //            }
        //
        //            @Override
        //            public void check() {
        //                // TODO Auto-generated method stub
        //
        //            }
        //        });
        return data;
    }

    /**
     * 修改
     */

    @RequestMapping("/editplan.do")
    @ResponseBody
    public Object update(final HttpServletRequest request) {
        //        final String eid = request.getParameter("eid");
        //        final String aplanteamid = request.getParameter("aplanteamid");
        //        final String aplanname = request.getParameter("aplanname");
        //        final String amaintype = request.getParameter("amaintype");
        //        final String acategory = request.getParameter("acategory");
        //        final String aroletype = request.getParameter("aroletype");
        //        final String aoperateobj = request.getParameter("aoperateobj");
        //        final String apointone = request.getParameter("apointone");
        //        final String atemppointone = request.getParameter("atemppointone");
        //        final String ahubalanceone = request.getParameter("ahubalanceone");
        //        final String atemphubalanceone = request.getParameter("atemphubalanceone");
        //        final String apointtwo = request.getParameter("apointtwo");
        //        final String atemppointtwo = request.getParameter("atemppointtwo");
        //        final String ahubalancetwo = request.getParameter("ahubalancetwo");
        //        final String atemphubalancetwo = request.getParameter("atemphubalancetwo");
        //        final String apointthree = request.getParameter("apointthree");
        //        final String ahubalancethree = request.getParameter("ahubalancethree");
        //        final String atemphubalancethree = request.getParameter("atemphubalancethree");
        //        final String apointfour = request.getParameter("apointfour");
        //        final String atemppointfour = request.getParameter("atemppointfour");
        //        final String ahubalancefour = request.getParameter("ahubalancefour");
        //        final String atemphubalancefour = request.getParameter("atemphubalancefour");
        //        final String apointfive = request.getParameter("apointfive");
        //        final String atemppointfive = request.getParameter("atemppointfive");
        //        final String ahubalancefive = request.getParameter("ahubalancefive");
        //        final String atemphubalancefive = request.getParameter("atemphubalancefive");
        //        final String aplanchildtype = request.getParameter("aplanchildtype");
        //        final String alimitamount = request.getParameter("alimitamount");
        //        final String astartdate = request.getParameter("astartdate");
        //        final String aenddate = request.getParameter("aenddate");
        //        final String ayouhuiflg = request.getParameter("ayouhuiflg");
        //        final String alimitcount = request.getParameter("alimitcount");
        //        final String abuyflg = request.getParameter("abuyflg");
        //        final String atemppointthree = request.getParameter("atemppointthree");
        //        final String aplangrandtype = request.getParameter("aplangrandtype");
        //        final String areachoffone = request.getParameter("areachoffone");
        //        final String areachofftwo = request.getParameter("areachofftwo");
        //        final String areachofffour = request.getParameter("areachofffour");
        //        final String areachoffthree = request.getParameter("areachoffthree");
        //        final String areachofffive = request.getParameter("areachofffive");

        final JsonResult data = new JsonResult(true);
        controllerTemplate.execute(data, new ControllerCallBack() {

            @Override
            public void executeService() {
                //                Date startTime = null;
                //                Date endTime = null;
                //                if (StringUtils.hasLength(astartdate)) {
                //                    try {
                //                        startTime = DateUtils.parseDate(astartdate, DateUtils.webFormat);
                //                    } catch (ParseException e) {
                //                        // TODO Auto-generated catch block
                //                        e.printStackTrace();
                //                    }
                //                }
                //
                //                if (StringUtils.hasLength(aenddate)) {
                //                    try {
                //                        endTime = DateUtils.parseDate(aenddate, DateUtils.webFormat);
                //                    } catch (ParseException e) {
                //                        // TODO Auto-generated catch block
                //                        e.printStackTrace();
                //                    }
                //                }
                //                Double dehubalancefive = null;
                //                Double dehubalancefour = null;
                //                Double dehubalanceone = null;
                //                Double dehubalancethree = null;
                //                Double dehubalancetwo = null;
                //                Money delimitamount = new Money();
                //                Integer delimitcount = null;
                //                Double depointfive = null;
                //                Double depointfour = null;
                //                Double depointone = null;
                //                Double depointthree = null;
                //                Double depointtwo = null;
                //
                //                Double detemphubalancefive = null;
                //                Double detemphubalancefour = null;
                //
                //                Double detemphubalanceone = null;
                //                Double detemphubalancethree = null;
                //                Double detemphubalancetwo = null;
                //                Double detemppointfive = null;
                //                Double detemppointfour = null;
                //                Double detemppointone = null;
                //                Double detemppointthree = null;
                //                Double detemppointtwo = null;
                //                Double deareachoffone = null;
                //                Double deareachoffthree = null;
                //                Double deareachofftwo = null;
                //                Double deareachofffive = null;
                //                Double deareachofffour = null;
                //
                //                if (StringUtils.isNotBlank(ahubalancefive)) {
                //                    dehubalancefive = Double.parseDouble(ahubalancefive);
                //                }
                //                if (StringUtils.isNotBlank(ahubalancefour)) {
                //                    dehubalancefour = Double.parseDouble(ahubalancefour);
                //
                //                }
                //                if (StringUtils.isNotBlank(ahubalanceone)) {
                //                    dehubalanceone = Double.parseDouble(ahubalanceone);
                //
                //                }
                //                if (StringUtils.isNotBlank(ahubalancethree)) {
                //                    dehubalancethree = Double.parseDouble(ahubalancethree);
                //                }
                //                if (StringUtils.isNotBlank(ahubalancetwo)) {
                //                    dehubalancetwo = Double.parseDouble(ahubalancetwo);
                //                }
                //                if (StringUtils.isNotBlank(alimitamount)) {
                //                    delimitamount = new Money(alimitamount);
                //                }
                //                if (StringUtils.isNotBlank(alimitcount)) {
                //                    delimitcount = Integer.parseInt(alimitcount);
                //                }
                //
                //                if (StringUtils.isNotBlank(apointfive)) {
                //                    depointfive = Double.parseDouble(apointfive);
                //                }
                //                if (StringUtils.isNotBlank(apointfour)) {
                //                    depointfour = Double.parseDouble(apointfour);
                //                }
                //                if (StringUtils.isNotBlank(apointone)) {
                //                    depointone = Double.parseDouble(apointone);
                //                }
                //                if (StringUtils.isNotBlank(apointthree)) {
                //                    depointthree = Double.parseDouble(apointthree);
                //                }
                //                if (StringUtils.isNotBlank(apointtwo)) {
                //                    depointtwo = Double.parseDouble(apointtwo);
                //                }
                //
                //                if (StringUtils.isNotBlank(atemphubalancefive)) {
                //                    detemphubalancefive = Double.parseDouble(atemphubalancefive);
                //                }
                //                if (StringUtils.isNotBlank(atemphubalancefour)) {
                //                    detemphubalancefour = Double.parseDouble(atemphubalancefour);
                //                }
                //                if (StringUtils.isNotBlank(atemphubalanceone)) {
                //                    detemphubalanceone = Double.parseDouble(atemphubalanceone);
                //                }
                //
                //                if (StringUtils.isNotBlank(atemphubalancethree)) {
                //                    detemphubalancethree = Double.parseDouble(atemphubalancethree);
                //                }
                //                if (StringUtils.isNotBlank(atemphubalancetwo)) {
                //                    detemphubalancetwo = Double.parseDouble(atemphubalancetwo);
                //                }
                //                if (StringUtils.isNotBlank(atemppointfive)) {
                //                    detemppointfive = Double.parseDouble(atemppointfive);
                //                }
                //                if (StringUtils.isNotBlank(atemppointfour)) {
                //                    detemppointfour = Double.parseDouble(atemppointfour);
                //
                //                }
                //                if (StringUtils.isNotBlank(atemppointone)) {
                //                    detemppointone = Double.parseDouble(atemppointone);
                //                }
                //                if (StringUtils.isNotBlank(atemppointthree)) {
                //                    detemppointthree = Double.parseDouble(atemppointthree);
                //                }
                //                if (StringUtils.isNotBlank(atemppointtwo)) {
                //                    detemppointtwo = Double.parseDouble(atemppointtwo);
                //                }
                //                if (StringUtils.isNotBlank(areachofffive)) {
                //                    deareachofffive = Double.parseDouble(areachofffive);
                //                }
                //                if (StringUtils.isNotBlank(areachofffour)) {
                //                    deareachofffour = Double.parseDouble(areachofffour);
                //                }
                //                if (StringUtils.isNotBlank(areachoffone)) {
                //                    deareachoffone = Double.parseDouble(areachoffone);
                //                }
                //                if (StringUtils.isNotBlank(areachoffthree)) {
                //                    deareachoffthree = Double.parseDouble(areachoffthree);
                //                }
                //                if (StringUtils.isNotBlank(areachofftwo)) {
                //                    deareachofftwo = Double.parseDouble(areachofftwo);
                //                }
                int result = 0;
                //                		planCopyDAO.updatePlan(aplanteamid, aplanname, amaintype, acategory,
                //                    aroletype, aoperateobj, depointone, detemppointone, dehubalanceone,
                //                    detemphubalanceone, depointtwo, detemppointtwo, dehubalancetwo,
                //                    detemphubalancetwo, depointthree, detemppointthree, dehubalancethree,
                //                    detemphubalancethree, depointfour, detemppointfour, dehubalancefour,
                //                    detemphubalancefour, depointfive, detemppointfive, dehubalancefive,
                //                    detemphubalancefive, aplanchildtype, aplangrandtype,
                //
                //                    deareachoffone, deareachofftwo, deareachoffthree, deareachofffour,
                //                    deareachofffive, delimitamount, startTime, endTime, ayouhuiflg, "0",
                //                    delimitcount, abuyflg, Integer.parseInt(eid));

                if (result > 0) {
                    data.setSuccess(true);
                    data.setMessage("操作成功");
                } else {
                    data.setSuccess(false);
                    data.setMessage("操作失败");
                }
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
    @RequestMapping("/deleteplan.do")
    @ResponseBody
    public Object delete(HttpServletRequest request, final int id) {
        final JsonResult json = new JsonResult(true);
        //        controllerTemplate.execute(json, new ControllerCallBack() {
        //
        //            @Override
        //            public void executeService() {
        //                int result = planCopyDAO.deletePlan(id);
        //
        //                if (result > 0) {
        //                    json.setSuccess(true);
        //                    json.setMessage("操作成功");
        //                } else {
        //                    json.setSuccess(false);
        //                    json.setMessage("操作失败");
        //                }
        //            }
        //
        //            @Override
        //            public void check() {
        //            }
        //
        //        });
        return json;
    }

    //查证未选择方案
    @RequestMapping("/findplans.do")
    @ResponseBody
    public JSONObject select(HttpServletRequest request, Integer offset, Integer limit) {
        //        String planName = request.getParameter("planName");
        //        String mainType = request.getParameter("mainType");

        JSONObject data = new JSONObject();
        //        try {
        //            List<PlanCopyDO> queryList = planCopyDAO.findplans(planName, mainType, offset, limit);
        //            int count = planCopyDAO.findplans(planName, mainType, null, null).size();
        //            data.put("rows", queryList);
        //            data.put("total", count);
        //        } catch (Exception e) {
        //            logger.error(MessageFormat.format("查询用户异常", new Object[] {}));
        //            // throw new BaseRuntimeException(ErrorCode.QUERY_EEOR,
        //            // ErrorCode.QUERY_EEOR.getDesc());
        //        }
        return data;
    }

    // 添加fangan
    @RequestMapping("addplans.do")
    @ResponseBody
    public Object u(final HttpServletRequest request) {

        //        final String b = request.getParameter("b");
        //        final String tid = request.getParameter("tid");

        final JsonResult data = new JsonResult(true);
        //        controllerTemplate.execute(data, new ControllerCallBack() {
        //            @Override
        //            public void executeService() {
        //                String tmpb = b;
        //                tmpb = tmpb.replace("[", "");
        //                tmpb = tmpb.replace("]", "");
        //                tmpb = tmpb.replace("\"", "");
        //                String[] st = tmpb.split(",");
        //                for (String s : st) {
        //
        //                    planCopyDAO.zjplan(tid, s);
        //
        //                    data.setSuccess(true);
        //                    data.setMessage("操作成功");
        //
        //                }
        //
        //            }
        //
        //            @Override
        //            public void check() {
        //
        //            }
        //        });
        return data;
    }
}
