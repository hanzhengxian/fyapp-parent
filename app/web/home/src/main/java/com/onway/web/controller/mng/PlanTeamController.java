package com.onway.web.controller.mng;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.result.JsonResult;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;

@Controller
public class PlanTeamController extends AbstractController {

    /**
     * 分页查询方案组
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("planTeamList.do")
    @ResponseBody
    public Object planTeamList(HttpServletRequest request, Integer offset, Integer limit) {
//        String planTeamName = request.getParameter("planTeamName");
//        String planTeamId = request.getParameter("planTeamId");
//        String type = request.getParameter("type");
//        String linkType = request.getParameter("linkType");
//        String linkId = request.getParameter("linkId");
        JSONObject data = new JSONObject();
//        try {
//            List<PlanTeamCopyDO> selectPlanTeamPage = planTeamCopyDAO.selectPlanTeamPage(
//                planTeamName, planTeamId, type, linkType, linkId, offset, limit);
//            int size = planTeamCopyDAO.selectPlanTeamPage(planTeamName, planTeamId, type, linkType,
//                linkId, null, null).size();
//            data.put("success", true);
//            data.put("rows", selectPlanTeamPage);
//            data.put("total", size);
//        } catch (Exception e) {
//            // TODO: handle exception
//            logger.error(MessageFormat.format("查询用户异常", new Object[] {}));
//        }
        return data;
    }

    // 添加
    @RequestMapping("/addplanteam.do")
    @ResponseBody
    public Object add(final HttpServletRequest request, final String addLinkId,
                      final String addLinkteamId, final String addType, final String addPlanTeamName) {

        final JsonResult data = new JsonResult(true);
//        controllerTemplate.execute(data, new ControllerCallBack() {
//
//            @Override
//            public void executeService() {
//                PlanTeamCopyDO pt = new PlanTeamCopyDO();
//
//                if (addType.equals("1")) {
//                    pt.setLinkType("1");
//                } else if (addType.equals("2")) {
//                    pt.setLinkType("2");
//                } else {
//                    pt.setLinkType("0");
//                }
//                pt.setPlanTeamId((codeGenerateComponent.nextCodeByType(BizTypeEnum.PLAN_TEAM_NO)));
//                pt.setDelFlg("0");
//                pt.setType(addType);
//
//                pt.setLinkId(addLinkId);
//                pt.setLinkTeamId(addLinkteamId);
//                pt.setPlanTeamName(addPlanTeamName);
//                int result = planTeamCopyDAO.insertPlanTeam(pt);
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
//            }
//        });
        return data;
    }

    /**
     * 修改
     */
    @RequestMapping("/editplanteam.do")
    @ResponseBody
    public Object update(final HttpServletRequest request) {
//        final String eid = request.getParameter("eid");
//        final String eLinkId = request.getParameter("eLinkId");
//        final String eLinkteamId = request.getParameter("eLinkteamId");
//        final String eType = request.getParameter("eType");
//        final String ePlanTeamName = request.getParameter("ePlanTeamName");
        final JsonResult data = new JsonResult(true);
        controllerTemplate.execute(data, new ControllerCallBack() {
            @Override
            public void executeService() {
//                String linktype = null;
//                if (eType.equals("1")) {
//                    linktype = "1";
//                } else if (eType.equals("2")) {
//                    linktype = "2";
//                } else {
//                    linktype = "0";
//                }
//                int result = 0;
//                		planTeamCopyDAO.updatePlanTeamById(ePlanTeamName, eType, linktype,
//                    eLinkId, eLinkteamId, Integer.parseInt(eid));
//                if (result > 0) {
//                    data.setSuccess(true);
//                    data.setMessage("操作成功");
//                } else {
//                    data.setSuccess(false);
//                    data.setMessage("操作失败");
//                }
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
    @RequestMapping("/deleteplanteam.do")
    @ResponseBody
    public Object delete(HttpServletRequest request, final int id) {
        final JsonResult json = new JsonResult(true);
//        controllerTemplate.execute(json, new ControllerCallBack() {
//
//            @Override
//            public void executeService() {
//
//                PlanTeamCopyDO exist = planTeamCopyDAO.selectById(id);
//                if (null == exist) {
//                    throw new RuntimeException("记录不存在");
//                }
//                int result = planTeamCopyDAO.deleteById(id);
//
//                if (result > 0) {
//
//                    List<PlanDO> planList = planDAO.selectByQuery(exist.getPlanTeamId(), null,
//                        null, null, null, null, null);
//                    for (PlanDO temp : planList) {
//                        //解除关联的方案关系
//                        planDAO.joinTeam(null, temp.getPlanId());
//                    }
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

}
