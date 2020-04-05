package com.onway.web.controller.mng;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.onway.common.lang.StringUtils;
import com.onway.fyapp.common.dal.dataobject.ActivityDO;
import com.onway.platform.common.configration.ConfigrationFactory;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.result.JsonResult;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;

/**
 * 活动
 * 
 * @author weina.chen
 * @version $Id: ActivityController.java, v 0.1 2018年7月30日 下午4:07:19 ROG Exp $
 */
@Controller
public class ActivityController extends AbstractController {

    private static final String IMAGE_FILE = ConfigrationFactory.getConfigration()
                                               .getPropertyValue("user_img_upload_realpath");

    private static final String IMAGE_PATH = ConfigrationFactory.getConfigration()
                                               .getPropertyValue("user_img_path");

    /**
     * 分页查询活动列表
     * 
     * @param title
     * @param type
     * @param state
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("selectActivity.do")
    @ResponseBody
    public Object selectActivity(String title, String type, String state, Integer offset,
                                 Integer limit) {
        JSONObject jo = new JSONObject();
        jo.put("rows", activityDAO.selectByQuery(title, type, state, offset, limit));
        jo.put("total", activityDAO.selectCountByQuery(title, type, state));
        return jo;
    }

    /**
     * 活动编辑
     * 
     * @param request
     * @return
     */
    @RequestMapping("editActivity.do")
    @ResponseBody
    public Object editActivity(final HttpServletRequest request, final MultipartFile img) {
        final JsonResult result = new JsonResult(true);
        final String idStr = request.getParameter("id");
        final String title = request.getParameter("title");
        final String info = request.getParameter("info");
        final String state = request.getParameter("state");
        final String type = request.getParameter("type");
        final String numStr = request.getParameter("num");
        final String url = request.getParameter("url");

        final String user_id = request.getSession().getAttribute("user_id").toString();
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {

                ActivityDO activity = new ActivityDO();

                if (StringUtils.hasLength(idStr)) {
                    activity = activityDAO.selectById(Integer.valueOf(idStr));
                    if (null == activity) {
                        throw new RuntimeException("不存在的记录");
                    }
                }

                if (null != img) {
                    String imgName = getRandomCode(3) + ".jpg";
                    String path = IMAGE_FILE + imgName;
                    String url = IMAGE_PATH + imgName;
                    try {
                        uploadFile(img, path);
                    } catch (Exception e) {
                        throw new RuntimeException("图片上传异常");
                    }

                    activity.setBackImg(url);
                }

                activity.setTitle(title);
                activity.setInfo(info);
                activity.setState(state);
                activity.setType(type);
                activity.setNum(Integer.valueOf(numStr));
                activity.setUrl(url);

                if (StringUtils.hasLength(idStr)) {
                    activity.setId(Integer.valueOf(idStr));
                    activity.setModifier(user_id);
                    activityDAO.update(activity);
                } else {
                    activity.setCreater(user_id);
                    activityDAO.insert(activity);
                }
            }

            @Override
            public void check() {
                AssertUtil.notBlank(title, "标题不能为空");
                AssertUtil.notBlank(info, "描述不能为空");
                AssertUtil.notBlank(type, "类型不能为空");
                AssertUtil.notBlank(state, "状态不能为空");
                AssertUtil.notBlank(numStr, "排序不能为空");

            }
        });
        return result;

    }

    // 生成随机数的,防止图片重复
    public String getRandomCode(int length) {
        if (length < 1 || length > 10) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= length; i++) {
            int rand = new Random().nextInt(10);
            sb.append(rand);
        }
        return String.valueOf(System.currentTimeMillis()) + sb.toString();
    }
}
