package com.onway.web.controller.mng;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.result.JsonResult;
import com.onway.web.controller.AbstractController;

/**
 * 
 * 优惠方案
 * @author weina.chen
 * @version $Id: PlanMngController.java, v 0.1 2018年8月21日 上午10:43:10 ROG Exp $
 */
@Controller
public class PlanMngController extends AbstractController {

    public static final Logger logger = Logger.getLogger(PlanMngController.class);

    /**
     * 分页查询
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("getPlanList.do")
    @ResponseBody
    public Object getPlanList(HttpServletRequest request, Integer offset, Integer limit) {
//        String planTeamId = request.getParameter("planTeamId");
//        String planId = request.getParameter("planId");
//        String planName = request.getParameter("planName");
//        String planChildType = request.getParameter("planChildType");
//        String mainType = request.getParameter("mainType");
        JSONObject data = new JSONObject();
//        try {
//            List<PlanDO> pllist = planDAO.selectByQuery(planTeamId, planId, planName, mainType,
//                planChildType, offset, limit);
//            int size = planDAO.selectCountByQuery(planTeamId, planId, planName, mainType,
//                planChildType);
//            data.put("success", true);
//            data.put("rows", pllist);
//            data.put("total", size);
//        } catch (Exception e) {
//            logger.error(MessageFormat.format("查询方案列表异常", new Object[] {}));
//        }
        return data;
    }

    @RequestMapping("storePlan.do")
    @ResponseBody
    public Object storePlan(final HttpServletRequest request, final String aplanteamid,
                            final String aplanname, final String amaintype,
                            final String aoperateobj, final String apointone,
                            final String ahubalanceone, final String atemphubalanceone,
                            final String apointtwo, final String ahubalancetwo,
                            final String atemphubalancetwo, final String apointthree,
                            final String ahubalancethree, final String atemphubalancethree,
                            final String apointfour, final String ahubalancefour,
                            final String atemphubalancefour, final String apointfive,
                            final String ahubalancefive, final String atemphubalancefive,
                            final String aplanchildtype, final String alimitamount,
                            final String astartdate, final String aenddate,
                            final String ayouhuiflg, final String adelflg,
                            final String alimitcount, final String abuyflg,
                            final String aplangrandtype, final String areachoffone,
                            final String areachofftwo, final String areachofffour,
                            final String areachoffthree, final String areachofffive) {

        final JsonResult data = new JsonResult(true);
//        controllerTemplate.execute(data, new ServiceCheckCallback() {
//
//            @Override
//            public void executeService() {
//
//                Date startTime = null;
//                Date endTime = null;
//                try {
//                    if (StringUtils.hasLength(astartdate)) {
//                        startTime = DateUtils.parseDate(astartdate, DateUtils.webFormat);
//                    }
//                    if (StringUtils.hasLength(aenddate)) {
//                        endTime = DateUtils.parseDate(aenddate, DateUtils.webFormat);
//                    }
//                } catch (ParseException e) {
//                    logger.error("时间转换异常", e);
//                }
//
//                PlanDO p = new PlanDO();
//                p.setPlanId((codeGenerateComponent.nextCodeByType(BizTypeEnum.PLAN_NO)));
//                p.setDelFlg("0");
//                p.setEndDate(endTime);
//                p.setPlanGrandType(aplangrandtype);
//                p.setReachOffOne(areachoffone);
//                p.setReachOffTwo(areachofftwo);
//                p.setReachOffThree(areachoffthree);
//                p.setReachOffFour(areachofffour);
//                p.setReachOffFive(areachofffive);
//
//                p.setHuBalanceFive(ahubalancefive);
//                p.setHuBalanceFour(ahubalancefour);
//                p.setHuBalanceOne(ahubalanceone);
//                p.setHuBalanceThree(ahubalancethree);
//                p.setHuBalanceTwo(ahubalancetwo);
//                if (StringUtils.isNotBlank(alimitamount)) {
//                    p.setLimitAmount(new Money(alimitamount));
//                }
//                if (StringUtils.isNotBlank(alimitcount)) {
//                    p.setLimitCount(Integer.parseInt(alimitcount));
//                }
//
//                p.setPointFive(apointfive);
//                p.setPointFour(apointfour);
//                p.setPointOne(apointone);
//                p.setPointThree(apointthree);
//                p.setPointTwo(apointtwo);
//
//                p.setTempHuBalanceFive(atemphubalancefive);
//                p.setTempHuBalanceFour(atemphubalancefour);
//                p.setTempHuBalanceOne(atemphubalanceone);
//                p.setTempHuBalanceThree(atemphubalancethree);
//                p.setTempHuBalanceTwo(atemphubalancetwo);
//
//                p.setTempPointOne("0");
//                p.setTempPointTwo("0");
//                p.setTempPointThree("0");
//                p.setTempPointFour("0");
//                p.setTempPointFive("0");
//
//                p.setMainType(amaintype);
//                p.setOperateObj(aoperateobj);
//                p.setPlanChildType(aplanchildtype);
//
//                p.setPlanName(aplanname);
//                p.setStartDate(startTime);
//                p.setYouhuiFlg(ayouhuiflg);
//
//                int result = planDAO.insert(p);
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
//                AssertUtil.notBlank(amaintype, "方案主类型不能为空");
//                AssertUtil.notBlank(alimitamount, "支出金额不能为空");
//                AssertUtil.notBlank(aoperateobj, "操作对象不能为空");
//                AssertUtil.notBlank(ayouhuiflg, "是否应用不能为空");
//                AssertUtil.notBlank(aplanname, "方案名字不能为空");
//                AssertUtil.notBlank(aplanchildtype, "是否为百分比不能为空");
//
//            }
//        });
        return data;
    }

    @RequestMapping("/updatePlan.do")
    @ResponseBody
    public Object updatePlan(final HttpServletRequest request) {
//        final String aplanId = request.getParameter("aplanId");
//        final String aplanname = request.getParameter("aplanname");
//        final String amaintype = request.getParameter("amaintype");
//        final String aoperateobj = request.getParameter("aoperateobj");
//        final String apointone = request.getParameter("apointone");
//        final String ahubalanceone = request.getParameter("ahubalanceone");
//        final String atemphubalanceone = request.getParameter("atemphubalanceone");
//        final String apointtwo = request.getParameter("apointtwo");
//        final String ahubalancetwo = request.getParameter("ahubalancetwo");
//        final String atemphubalancetwo = request.getParameter("atemphubalancetwo");
//        final String apointthree = request.getParameter("apointthree");
//        final String ahubalancethree = request.getParameter("ahubalancethree");
//        final String atemphubalancethree = request.getParameter("atemphubalancethree");
//        final String apointfour = request.getParameter("apointfour");
//        final String ahubalancefour = request.getParameter("ahubalancefour");
//        final String atemphubalancefour = request.getParameter("atemphubalancefour");
//        final String apointfive = request.getParameter("apointfive");
//        final String ahubalancefive = request.getParameter("ahubalancefive");
//        final String atemphubalancefive = request.getParameter("atemphubalancefive");
//        final String aplanchildtype = request.getParameter("aplanchildtype");
//        final String alimitamount = request.getParameter("alimitamount");
//        final String astartdate = request.getParameter("astartdate");
//        final String aenddate = request.getParameter("aenddate");
//        final String ayouhuiflg = request.getParameter("ayouhuiflg");
//        final String alimitcount = request.getParameter("alimitcount");
//        final String aplangrandtype = request.getParameter("aplangrandtype");
//        final String areachoffone = request.getParameter("areachoffone");
//        final String areachofftwo = request.getParameter("areachofftwo");
//        final String areachofffour = request.getParameter("areachofffour");
//        final String areachoffthree = request.getParameter("areachoffthree");
//        final String areachofffive = request.getParameter("areachofffive");

        final JsonResult data = new JsonResult(true);
//        controllerTemplate.execute(data, new ServiceCheckCallback() {
//
//            @Override
//            public void executeService() {
//
//                Date startTime = null;
//                Date endTime = null;
//                try {
//                    if (StringUtils.hasLength(astartdate)) {
//                        startTime = DateUtils.parseDate(astartdate, DateUtils.webFormat);
//                    }
//                    if (StringUtils.hasLength(aenddate)) {
//                        endTime = DateUtils.parseDate(aenddate, DateUtils.webFormat);
//                    }
//                } catch (ParseException e) {
//                    logger.error("时间转换异常", e);
//                }
//
//                PlanDO p = new PlanDO();
//                p.setPlanId(aplanId);
//                p.setDelFlg("0");
//                p.setEndDate(endTime);
//                p.setPlanGrandType(aplangrandtype);
//                p.setReachOffOne(areachoffone);
//                p.setReachOffTwo(areachofftwo);
//                p.setReachOffThree(areachoffthree);
//                p.setReachOffFour(areachofffour);
//                p.setReachOffFive(areachofffive);
//
//                p.setHuBalanceFive(ahubalancefive);
//                p.setHuBalanceFour(ahubalancefour);
//                p.setHuBalanceOne(ahubalanceone);
//                p.setHuBalanceThree(ahubalancethree);
//                p.setHuBalanceTwo(ahubalancetwo);
//                if (StringUtils.isNotBlank(alimitamount)) {
//                    p.setLimitAmount(new Money(alimitamount));
//                }
//                if (StringUtils.isNotBlank(alimitcount)) {
//                    p.setLimitCount(Integer.parseInt(alimitcount));
//                }
//
//                p.setPointFive(apointfive);
//                p.setPointFour(apointfour);
//                p.setPointOne(apointone);
//                p.setPointThree(apointthree);
//                p.setPointTwo(apointtwo);
//
//                p.setTempHuBalanceFive(atemphubalancefive);
//                p.setTempHuBalanceFour(atemphubalancefour);
//                p.setTempHuBalanceOne(atemphubalanceone);
//                p.setTempHuBalanceThree(atemphubalancethree);
//                p.setTempHuBalanceTwo(atemphubalancetwo);
//
//                p.setTempPointOne("0");
//                p.setTempPointTwo("0");
//                p.setTempPointThree("0");
//                p.setTempPointFour("0");
//                p.setTempPointFive("0");
//
//                p.setMainType(amaintype);
//                p.setOperateObj(aoperateobj);
//                p.setPlanChildType(aplanchildtype);
//
//                p.setPlanName(aplanname);
//                p.setStartDate(startTime);
//                p.setYouhuiFlg(ayouhuiflg);
//
//                int result = planDAO.update(p);
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
//                AssertUtil.notBlank(aplanId, "方案编号不能为空");
//                AssertUtil.notBlank(amaintype, "方案主类型不能为空");
//                AssertUtil.notBlank(alimitamount, "支出金额不能为空");
//                AssertUtil.notBlank(aoperateobj, "操作对象不能为空");
//                AssertUtil.notBlank(ayouhuiflg, "是否应用不能为空");
//                AssertUtil.notBlank(aplanname, "方案名字不能为空");
//                AssertUtil.notBlank(aplanchildtype, "是否为百分比不能为空");
//
//            }
//        });
        return data;
    }

    /**
     * 删除
     */
    @RequestMapping("/delPlan.do")
    @ResponseBody
    public Object delPlan(HttpServletRequest request) {
        final JsonResult json = new JsonResult(true);
//        final String planId = request.getParameter("planId");
//        controllerTemplate.execute(json, new ServiceCheckCallback() {
//
//            @Override
//            public void executeService() {
//                int result = planDAO.delete(planId);
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
//                AssertUtil.notBlank(planId, "方案编号不能为空");
//            }
//
//        });
        return json;
    }

    /**
     * 查询未加入方案组的方案列表
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("queryNoTeamPlan.do")
    @ResponseBody
    public JSONObject queryNoTeamPlan(HttpServletRequest request, Integer offset, Integer limit) {
//        String planName = request.getParameter("planName");
//        String mainType = request.getParameter("mainType");

        JSONObject data = new JSONObject();
//        try {
//            List<PlanDO> queryList = planDAO.selectNoTeamList(planName, mainType, offset, limit);
//            int count = queryList.size();
//            data.put("rows", queryList);
//            data.put("total", count);
//        } catch (Exception e) {
//            logger.error(MessageFormat.format("查询用户异常", new Object[] {}));
//        }
        return data;
    }

    /**
     * 加入方案组
     * 
     * @param request
     * @return
     */
    @RequestMapping("manyAddTeam.do")
    @ResponseBody
    public Object u(final HttpServletRequest request) {

//        final String b = request.getParameter("b");
//        final String tid = request.getParameter("tid");

        final JsonResult data = new JsonResult(true);
//        controllerTemplate.execute(data, new ServiceCheckCallback() {
//            @Override
//            public void executeService() {
//                JSONArray parseArray = JSONArray.parseArray(b);
//                for (Object object : parseArray) {
//                    data.setSuccess(0 < planDAO.joinTeam(tid, object.toString()));
//                    data.setMessage("操作成功");
//                }
//
//            }
//
//            @Override
//            public void check() {
//                AssertUtil.notBlank(b, "方案编号不能为空");
//                AssertUtil.notBlank(tid, "方案组不能为空");
//            }
//        });
        return data;
    }

}
