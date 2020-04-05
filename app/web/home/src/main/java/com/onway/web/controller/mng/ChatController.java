package com.onway.web.controller.mng;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.onway.common.lang.StringUtils;
import com.onway.fyapp.common.dal.dataobject.ChatRecordDO;
import com.onway.platform.common.configration.ConfigrationFactory;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.result.JsonResult;
import com.onway.utils.EmojiUtil;
import com.onway.utils.FileUploadUtils;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;

/**
 * 客服相关
 * 
 * @author weina.chen
 * @version $Id: ChatController.java, v 0.1 2018年7月30日 上午9:32:03 ROG Exp $
 */
@Controller
public class ChatController extends AbstractController {

    private static final String IMAGE_FILE = ConfigrationFactory.getConfigration()
                                               .getPropertyValue("user_img_upload_realpath");

    private static final String IMAGE_PATH = ConfigrationFactory.getConfigration()
                                               .getPropertyValue("user_img_path");

    /**
     * 分页查询聊天室列表
     * 
     * @param userId
     * @param offset
     * @param limit
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("chatList.do")
    @ResponseBody
    public Object chatList(String userId, String userName, String userCell, String realName,
                           Integer offset, Integer limit) throws UnsupportedEncodingException {
        JSONObject jo = new JSONObject();
        List<Map<String, Object>> selectByQuery = chatListDAO.selectByQuery(userId, userName,
            userCell, realName, offset, limit);
        for (Map<String, Object> map : selectByQuery) {
            map.put(
                "lastChatComment",
                EmojiUtil.emojiRecovery2(map.get("lastChatComment") == null ? "" : map.get(
                    "lastChatComment").toString()));
            map.get("lastChatType");
        }
        jo.put("rows", selectByQuery);
        jo.put("total", chatListDAO.selectCountByQuery(userId, userName, userCell, realName));
        return jo;
    }

    /**
     * 分页查询聊天记录列表
     * 
     * @param chatId
     * @param offset
     * @param limit
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("chatRecordList.do")
    @ResponseBody
    public Object chatRecordList(String chatId, Integer offset, Integer limit)
                                                                              throws UnsupportedEncodingException {
        JSONObject jo = new JSONObject();
        chatRecordDAO.changeToRead("1", chatId);
        List<Map<String, Object>> selectByQuery = chatRecordDAO
            .selectByQuery(chatId, offset, limit);
        for (Map<String, Object> map : selectByQuery) {
            map.put(
                "CHAT_COMMNET",
                EmojiUtil.emojiRecovery2(map.get("CHAT_COMMNET") == null ? "" : map.get(
                    "CHAT_COMMNET").toString()));
            map.get("CHAT_TYPE");
        }
        jo.put("rows", selectByQuery);
        jo.put("total", chatRecordDAO.selectCountByQuery(chatId));
        return jo;
    }

    /**
     * 客服回复
     * 
     * @param request
     * @return
     */
    @RequestMapping("reply.do")
    @ResponseBody
    public Object reply(final HttpServletRequest request, final MultipartFile img) {
        final JsonResult result = new JsonResult(true);
        final String chatId = request.getParameter("chatId");
        final String chatCommnet = request.getParameter("chatCommnet");
        final String chatType = request.getParameter("chatType");
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {

                ChatRecordDO chatRecord = new ChatRecordDO();
                chatRecord.setChatId(chatId);
                if (StringUtils.equals(chatType, "1")) {
                    chatRecord.setChatCommnet(chatCommnet);
                } else if (StringUtils.equals(chatType, "2")) {
                    if (null != img) {
                        String imgName = getRandomCode(3) + ".jpg";
                        String path = IMAGE_FILE + imgName;
                        String url = IMAGE_PATH + imgName;
                        try {
                            uploadFile(img, path);
                        } catch (Exception e) {
                            throw new RuntimeException("图片上传异常");
                        }

                        chatRecord.setChatCommnet(url);
                    }
                }

                chatRecord.setChatObj("2");
                chatRecord.setChatType(chatType);
                chatRecordDAO.insert(chatRecord);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(chatId, "聊天室编号不能为空");
                AssertUtil.notBlank(chatType, "内容类型不能为空");
                if (StringUtils.equals(chatType, "1")) {
                    AssertUtil.notBlank(chatCommnet, "内容不能为空");
                } else if (StringUtils.equals(chatType, "2")) {
                    AssertUtil.notNull(img, "图片不能为空");
                } else {
                    throw new RuntimeException("无效的内容类型");
                }
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

    /**
     * 客服回复
     * 
     * @param request
     * @return
     */
    @RequestMapping("/repeatChat.do")
    @ResponseBody
    public Object repeatChat(final HttpServletRequest request, final MultipartFile repeatImg) {

        final String chatId = request.getParameter("rechatId");
        final String repeatComment = request.getParameter("repeatComment");

        final JsonResult result = new JsonResult(true);
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {
                // 文字回复
                if (StringUtils.isNotBlank(repeatComment)) {
                    ChatRecordDO chatRecord = new ChatRecordDO();
                    chatRecord.setChatId(chatId);
                    try {
                        chatRecord.setChatCommnet(EmojiUtil.emojiConvert1(repeatComment));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    chatRecord.setChatObj("2");
                    chatRecord.setChatType("1");
                    chatRecord.setChatStatus("1");
                    chatRecordDAO.insert(chatRecord);
                }
                // 图片回复
                if (null != repeatImg) {
                    String rterImg = null;
                    try {
                        rterImg = FileUploadUtils.upload(request, repeatImg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (StringUtils.isNotBlank(rterImg)) {
                        ChatRecordDO chatRecord = new ChatRecordDO();
                        chatRecord.setChatId(chatId);
                        chatRecord.setChatCommnet(rterImg);
                        chatRecord.setChatObj("2");
                        chatRecord.setChatType("2");
                        chatRecord.setChatStatus("1");
                        chatRecordDAO.insert(chatRecord);
                    }
                }

                result.setSuccess(true);
            }

            @Override
            public void check() {
                AssertUtil.notBlank(chatId, "数据异常");
            }
        });
        return result;
    }

}
