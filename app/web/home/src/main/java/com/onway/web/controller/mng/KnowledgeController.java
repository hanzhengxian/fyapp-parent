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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.onway.fyapp.common.dal.dataobject.KnowledgeDO;
import com.onway.model.enums.BizTypeEnum;
import com.onway.model.enums.DelFlgEnum;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.result.JsonResult;
import com.onway.utils.FileUploadUtils;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;

/**
 * 养生知识管理
 * @author yuzanmei
 *
 */
@Controller
public class KnowledgeController extends AbstractController {

    /**
     * 分页查询养生知识列表
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/selectnotice.do")
    @ResponseBody
    public JSONObject selectNotice(HttpServletRequest request, String offset, String limit) {
        String type = request.getParameter("selectType");
        String title = request.getParameter("selectTitle");
        String delFlg = request.getParameter("delFlg");
        JSONObject data = new JSONObject();
        try {
            List<Map<String, Object>> list = knowledgeDAO.selectKnowledge(type, title, delFlg,
                Integer.parseInt(offset), Integer.parseInt(limit));
            for (Map<String, Object> map : list) {
                //如果有性能上面的要求可以使用Gson将bean转换json确保数据的正确，使用FastJson将Json转换Bean
                List<String> tempList = JSONArray.parseArray((String) map.get("headImgUrl"),
                    String.class);
                map.put("headImgUrl", tempList);
            }
            data.put("rows", list);
            data.put("total", knowledgeDAO.selectKnowledgeCount(type, title, delFlg));
        } catch (Exception e) {
            logger.error(MessageFormat.format("查询用户异常", new Object[] {}));
        }
        return data;
    }

    /**
     * 添加养生知识
     * @param request
     * @return
     */
    @RequestMapping("/addnotice.do")
    @ResponseBody
    public Object addNotice(final HttpServletRequest request,
                            final String atype,
                            final String atitle,
                            final String asubTitle,
                            @RequestParam(value = "aheadImg", required = false) final MultipartFile[] aheadImg,
                            final String acontent) {
        final JsonResult data = new JsonResult(true);
        final String user_id = request.getSession().getAttribute("user_id").toString();
        controllerTemplate.execute(data, new ControllerCallBack() {

            @Override
            public void executeService() {
                JSONArray array = new JSONArray();
                String imgUrl = null;
                for (int i = 0; i < aheadImg.length; i++) {
                    try {
                        imgUrl = FileUploadUtils.upload(request, aheadImg[i]);
                        array.add(imgUrl);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                KnowledgeDO kd = new KnowledgeDO();
                kd.setKnowId(codeGenerateComponent.nextCodeByType(BizTypeEnum.KNOW_ID));
                kd.setType(atype);
                kd.setTitle(atitle);
                kd.setSubTitle(asubTitle);
                kd.setHeadImgUrl(array.toString());
                kd.setContent(acontent);
                kd.setPraiseCount(0);
                kd.setCommentCount(0);
                kd.setDelFlg("0");
                kd.setCreater(user_id);
                int result = knowledgeDAO.insertKnowledge(kd);
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
                AssertUtil.notBlank(atype, "类型不能为空");
                AssertUtil.notBlank(atitle, "标题不能为空");
            }
        });
        return data;
    }

    /**
     * 修改养生知识
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/updatenotice.do")
    @ResponseBody
    public Object updateNotice(final HttpServletRequest request, final String eknowId) {
        final String etype = request.getParameter("atype");
        final String etitle = request.getParameter("atitle");
        final String esubTitle = request.getParameter("asubTitle");
        final String econtent = request.getParameter("acontent");
        final String oldImg = request.getParameter("oldImg");

        final String user_id = request.getSession().getAttribute("user_id").toString();
        final JsonResult data = new JsonResult(true);
        controllerTemplate.execute(data, new ControllerCallBack() {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile>         eheadImg         = multipartRequest.getFiles("aheadImg");

            @Override
            public void executeService() {
                JSONArray array = new JSONArray();
                String imgUrl = null;
                if (eheadImg != null && eheadImg.size() > 0) {
                    for (int i = 0; i < eheadImg.size(); i++) {
                        try {
                            imgUrl = FileUploadUtils.upload(request, eheadImg.get(i));
                            array.add(imgUrl);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    String oldImgArr[] = oldImg.split(",");
                    array = (JSONArray) JSONArray.toJSON(oldImgArr);
                }

                int result = knowledgeDAO.updateKnowledge(etype, etitle, esubTitle, econtent,
                    array.toString(), user_id, eknowId);
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
                AssertUtil.notBlank(eknowId, "参数不能为空");
            }
        });
        return data;
    }

    /**
     * 删除养生知识
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/deletenotice.do")
    @ResponseBody
    public Object deleteNotice(HttpServletRequest request, final String knowId, final String delFlg) {
        final JsonResult json = new JsonResult(true);
        final String user_id = request.getSession().getAttribute("user_id").toString();
        controllerTemplate.execute(json, new ControllerCallBack() {
            @Override
            public void executeService() {
                if (null == DelFlgEnum.getByCode(delFlg)) {
                    json.setSuccess(false);
                    json.setMessage("参数异常");
                    return;
                }

                int result = knowledgeDAO.deleteKnowledge(delFlg, user_id, knowId);
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
                AssertUtil.notBlank(knowId, "参数不能为空");
            }
        });
        return json;
    }
}
