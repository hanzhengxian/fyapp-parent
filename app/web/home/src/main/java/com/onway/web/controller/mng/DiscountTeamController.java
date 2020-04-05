package com.onway.web.controller.mng;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.onway.fyapp.common.dal.dataobject.DiscountTeamDO;
import com.onway.model.enums.BizTypeEnum;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.result.JsonResult;
import com.onway.utils.FileUploadUtils;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;

/**
 * 方案管理-优惠券组控制器
 * 
 * @author liaoshengzhe
 * @version $Id: DiscountTeamController.java, v 0.1 2018年8月21日 下午6:20:09
 *          liaoshengzhe Exp $
 */
@Controller
public class DiscountTeamController extends AbstractController {

    @RequestMapping("queryDiscountTeamList.do")
    @ResponseBody
    public Object queryDiscountTeamList(HttpServletRequest request) {
        List<Map<String, Object>> teamList = discountTeamDAO.queryList(null, null, null, null);
        return teamList;
    }

    /**
     * 分页查询优惠券组列表
     * 
     * @param request
     * @return
     */
    @RequestMapping("selectDiscountTeam.do")
    @ResponseBody
    public Object selectDiscount(HttpServletRequest request) {
        String discountTeamId = request.getParameter("discountTeamId");
        String discountTitle = request.getParameter("discountTitle");
        String offset = request.getParameter("offset");
        String limit = request.getParameter("limit");
        JSONObject data = new JSONObject();
        try {
            List<Map<String, Object>> queryList = discountTeamDAO.queryList(discountTeamId,
                discountTitle, offset, limit);
            int count = discountTeamDAO.queryList(discountTeamId, discountTitle, null, null).size();
            data.put("success", true);
            data.put("rows", queryList);
            data.put("total", count);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error(MessageFormat.format("查询优惠券异常", new Object[] {}));
        }
        return data;
    }

    // 增加优惠券组
    @RequestMapping("addDiscountTeam.do")
    @ResponseBody
    public Object addDiscountTeam(HttpServletRequest request,
                                  @RequestParam(value = "addDiscountBackground", required = false) MultipartFile addDiscountBackground,
                                  @RequestParam(value = "addDiscountBackgroundPc", required = false) MultipartFile addDiscountBackgroundPc) {
        String addDiscountDesc = request.getParameter("addDiscountDesc");
        String discountTitle = request.getParameter("addDiscountTitle");
        String user_id = request.getSession().getAttribute("user_id").toString();
        String addTitleColor = request.getParameter("addTitleColor");
        String addDescColor = request.getParameter("addDescColor");

        DiscountTeamDO discountTeam = new DiscountTeamDO();
        discountTeam.setDiscountTeamId(codeGenerateComponent
            .nextCodeByType(BizTypeEnum.PLAN_TEAM_NO));
        discountTeam.setDiscountDesc(addDiscountDesc);
        discountTeam.setDiscountTitle(discountTitle);
        discountTeam.setTitleColor(addTitleColor);
        discountTeam.setDescColor(addDescColor);

        discountTeam.setDelFlg("0");
        String imgUrl = null;
        if (addDiscountBackground != null) {
            // 处理图片上传
            try {
                imgUrl = FileUploadUtils.upload(request, addDiscountBackground);
                discountTeam.setDiscountBackground(imgUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (addDiscountBackgroundPc != null) {
            // 处理图片上传
            try {
                imgUrl = FileUploadUtils.upload(request, addDiscountBackgroundPc);
                discountTeam.setDiscountBackgroundPc(imgUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        JSONObject data = new JSONObject();
        try {
            discountTeam.setCreater(user_id);
            int insert = discountTeamDAO.insert(discountTeam);
            data.put("success", insert > 0 ? true : false);
            data.put("message", insert > 0 ? "添加成功" : "添加失败");
        } catch (Exception e) {
            // TODO: handle exception
            logger.error(MessageFormat.format("增加优惠券组异常", new Object[] {}));
        }
        return data;
    }

    @RequestMapping("updateDiscountTeamInfo.do")
    @ResponseBody
    public Object updateDiscountTeamInfo(HttpServletRequest request,
                                         @RequestParam(value = "editDiscountBackground", required = false) MultipartFile editDiscountBackground,
                                         @RequestParam(value = "editDiscountBackgroundPc", required = false) MultipartFile editDiscountBackgroundPc) {
        String editDiscountTitle = request.getParameter("editDiscountTitle");
        String editDiscountDesc = request.getParameter("editDiscountDesc");
        String editDiscountTeamId = request.getParameter("editDiscountTeamId");

        String editTitleColor = request.getParameter("editTitleColor");
        String editDescColor = request.getParameter("editDescColor");

        //		String oldImg = request.getParameter("oldImg");
        String user_id = request.getSession().getAttribute("user_id").toString();

        JSONObject data = new JSONObject();

        DiscountTeamDO discountTeamDO = discountTeamDAO.queryByDiscountTeamId(editDiscountTeamId);
        if (null == discountTeamDO) {
            data.put("success", false);
            data.put("message", "数据异常");
            return data;
        }

        String finalBackImgUrl = "";
        if (editDiscountBackground != null) {
            try {
                finalBackImgUrl = FileUploadUtils.upload(request, editDiscountBackground);
                discountTeamDO.setDiscountBackground(finalBackImgUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (editDiscountBackgroundPc != null) {
            try {
                finalBackImgUrl = FileUploadUtils.upload(request, editDiscountBackgroundPc);
                discountTeamDO.setDiscountBackgroundPc(finalBackImgUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        discountTeamDO.setDiscountTitle(editDiscountTitle);
        discountTeamDO.setDiscountDesc(editDiscountDesc);
        discountTeamDO.setModifier(user_id);
        discountTeamDO.setTitleColor(editTitleColor);
        discountTeamDO.setDescColor(editDescColor);

        int result = discountTeamDAO.update(discountTeamDO);

        data.put("success", result > 0 ? true : false);
        data.put("message", result > 0 ? "操作成功" : "操作失败");
        return data;
    }

    /**
     * 
     * @param request
     * @param discountTeamId
     * @return
     */
    @RequestMapping("/deleteDiscountTeam.do")
    @ResponseBody
    public Object deleteDiscountTeam(HttpServletRequest request, final String discountTeamId) {
        final JsonResult data = new JsonResult(true);
        final String user_id = request.getSession().getAttribute("user_id").toString();
        controllerTemplate.execute(data, new ControllerCallBack() {

            @Override
            public void executeService() {
                int count = discountDAO.queryCountDiscount(discountTeamId);
                if (count > 0) {
                    data.setSuccess(false);
                    data.setMessage("该优惠券组下仍有已发布的优惠券。");
                } else {
                    int result = discountTeamDAO.delete(user_id, discountTeamId);
                    data.setSuccess(result > 0 ? true : false);
                    data.setMessage(result > 0 ? "删除成功" : "删除失败");
                }
            }

            @Override
            public void check() {
                AssertUtil.notBlank(discountTeamId, "id不能为空");
            }

        });
        return data;
    }

}