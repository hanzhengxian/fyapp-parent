/**
 * 
 */
package com.onway.core.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.httpclient.HttpException;

import com.onway.common.lang.StringUtils;
import com.onway.core.service.MsgService;
import com.onway.core.service.localcache.manager.SysConfigCacheManager;
import com.onway.fyapp.common.dal.daointerface.MsgDAO;
import com.onway.fyapp.common.dal.dataobject.MsgDO;
import com.onway.model.enums.BooleanEnum;
import com.onway.model.enums.SysConfigCacheKeyEnum;
import com.onway.platform.common.base.BaseResult;
import com.onway.platform.common.service.template.AbstractServiceImpl;
import com.onway.platform.common.service.template.ServiceCheckCallback;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.utils.sendMessage.Article;
import com.onway.utils.sendMessage.SendCustomMessage;

public class MsgServiceImpl extends AbstractServiceImpl implements MsgService {

    protected final static String SEND_CUSTOM_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

    private MsgDAO                msgDAO;

    private SysConfigCacheManager sysConfigCacheManager;

    public void setMsgDAO(MsgDAO msgDAO) {
        this.msgDAO = msgDAO;
    }

    public void setSysConfigCacheManager(SysConfigCacheManager sysConfigCacheManager) {
        this.sysConfigCacheManager = sysConfigCacheManager;
    }

    /**
     * 新增消息
     */
    @Override
    public BaseResult newMsg(final String userId, final String msgType, final String msgTitle,
                             final String msgContent) {
        final BaseResult result = new BaseResult(true);
        serviceTemplate.execute(result, new ServiceCheckCallback() {

            @Override
            public void check() {
                AssertUtil.notBlank(userId);
                AssertUtil.notBlank(msgType);
                AssertUtil.notBlank(msgTitle);
                AssertUtil.notBlank(msgContent);
            }

            @Override
            public void executeService() {
                MsgDO msg = new MsgDO();
                msg.setUserId(userId);
                msg.setMsgType(msgType);
                msg.setMsgTitle(msgTitle);
                msg.setMsgContent(msgContent);
                msg.setIsRead(BooleanEnum.FALSE.getCode());
                msgDAO.insertMsg(msg);
            }
        });
        return result;
    }

    /**
     * 发送微信消息
     */
    @Override
    public BaseResult sendWechatMsg(final String openId, final String msgContent) {
        final BaseResult result = new BaseResult(true);
        serviceTemplate.execute(result, new ServiceCheckCallback() {

            @Override
            public void check() {

            }

            @Override
            public void executeService() {
                if (StringUtils.isNotBlank(openId) && StringUtils.isNotBlank(msgContent)) {
                    String accessToken = sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.ACCESS_TOKEN);
                    String makeTextCustomMessage = SendCustomMessage.makeTextCustomMessage(openId,
                        msgContent);
                    try {
                        SendCustomMessage.sendCustomMessage(accessToken, makeTextCustomMessage);
                    } catch (HttpException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return result;
    }

    @Override
    public BaseResult sendWechatCustomerMsg(final String openId, final String linkUrl,
                                            final String pickUrl, final String title,
                                            final String desc) {
        final BaseResult result = new BaseResult(true);
        serviceTemplate.execute(result, new ServiceCheckCallback() {

            @Override
            public void check() {

            }

            @Override
            public void executeService() {
                if (StringUtils.isNotBlank(openId) && StringUtils.isNotBlank(linkUrl)) {
                    String accessToken = sysConfigCacheManager
                        .getConfigValue(SysConfigCacheKeyEnum.ACCESS_TOKEN);
                    List<Article> makeArticleList = SendCustomMessage.makeArticleListForCheckOrder(
                        pickUrl, linkUrl, title, desc);
                    String makeTextCustomMessage = SendCustomMessage.makeNewsCustomMessage(openId,
                        makeArticleList);
                    try {
                        SendCustomMessage.sendCustomMessage(accessToken, makeTextCustomMessage);
                    } catch (HttpException e) {
                    } catch (IOException e) {
                    }
                }
            }
        });
        return result;
    }

}
