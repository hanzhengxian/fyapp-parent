package com.onway.web.controller.mng;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.onway.common.lang.CollectionUtils;
import com.onway.common.lang.StringUtils;
import com.onway.fyapp.common.dal.dataobject.BannerDO;
import com.onway.model.enums.BannerPositionEnum;
import com.onway.model.enums.BannerTypeEnum;
import com.onway.platform.common.configration.ConfigrationFactory;
import com.onway.platform.common.exception.BaseRuntimeException;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.result.JsonResult;
import com.onway.utils.FileUploadUtils;
import com.onway.utils.JSONArrayUtils;
import com.onway.web.controller.AbstractController;
import com.onway.web.template.ControllerCallBack;

/**
 * banner管理
 * 
 * @author yuzanmei
 *
 */
@Controller
public class BannerController extends AbstractController {

    private static final String IMAGE_FILE = ConfigrationFactory.getConfigration()
                                               .getPropertyValue("user_img_upload_realpath");

    private static final String IMAGE_PATH = ConfigrationFactory.getConfigration()
                                               .getPropertyValue("user_img_path");

    /**
     * 分页查询banner管理列表
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/selectallbanner.do")
    @ResponseBody
    public JSONObject selectAllBanner(HttpServletRequest request, Integer offset, Integer limit) {
        String type = request.getParameter("selectBannerType");
        String position = request.getParameter("selectBannerPosition");
        JSONObject data = new JSONObject();
        try {
            List<Map<String, Object>> list = bannerDAO.selectBanner(type, position, offset, limit);
            for (Map<String, Object> map : list) {
                String bannerPosition = MapUtils.getString(map, "bannerPosition");
                BannerPositionEnum bannerPositionEnum = BannerPositionEnum.getByValue(Integer
                    .valueOf(bannerPosition));
                if (null != bannerPositionEnum)
                    map.put("bannerPositionStr", bannerPositionEnum.message());
            }
            data.put("rows", list);
            data.put("total", bannerDAO.queryLists(type, position));
        } catch (Exception e) {
            logger.error(MessageFormat.format("查询用户异常", new Object[] {}));
        }
        return data;
    }

    /**
     * 添加banner[]
     * 
     * @param request
     * @param bannerType
     * @param bannerContent
     * @param bannerImg
     * @param bannerPosition
     * @param rank
     * @param status
     * @return
     */
    @RequestMapping("/addbanner.do")
    @ResponseBody
    public Object addbanner(HttpServletRequest request,
                            String bannerType,
                            String bannerContent,
                            @RequestParam(value = "bannerImg", required = false) MultipartFile bannerImg,
                            String bannerPosition, int rank, String status, String bannerDesc) {

        final JsonResult data = new JsonResult(true);
        String user_id = request.getSession().getAttribute("user_id").toString();
        String imgUrl = null;
        try {
            imgUrl = FileUploadUtils.upload(request, bannerImg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //		if (bannerType.equals(0)) {
        //
        //		} else {
        //			bannerContent = Html2Text.getContent(bannerContent);
        //			bannerContent = bannerContent.replaceAll("\\s*", "");
        //			// bannerContent = StringUtils.deleteWhitespace(bannerContent);
        //		}
        BannerDO bd = new BannerDO();
        bd.setBannerType(bannerType);
        if (StringUtils.equals(bannerType, BannerTypeEnum.IMG_WORD.getCode())) {
            bd.setBannerContent(bannerContent);
        }
        if (StringUtils.equals(bannerType, BannerTypeEnum.TO_HTML.getCode())) {
            if(StringUtils.isNotBlank(bannerContent)){
                String replace_first = bannerContent.replace("<p>", "");
                String  replace_second= replace_first.replace("</p>", "");
                String  replace_third= replace_second.replace("<br>", "");
                bd.setBannerContent(replace_third);
            }
        }
        bd.setBannerPosition(bannerPosition);
        bd.setBannerImg(imgUrl);
        bd.setBannerDesc(bannerDesc);
        bd.setRank(rank);
        bd.setStatus("ENABLED");
        bd.setCreater(user_id);
        int result = bannerDAO.insertBanner(bd);
        if (result > 0) {
            data.setSuccess(true);
            data.setMessage("操作成功");
        } else {
            data.setSuccess(false);
            data.setMessage("操作失败");
        }

        return data;
    }

    /**
     * 修改banner
     * 
     * @param request
     * @param id
     * @param bannerType
     * @param bannerContent
     * @param bannerPosition
     * @param bannerImg
     * @param rank
     * @return
     */
    @RequestMapping("/updatebanner.do")
    @ResponseBody
    public Object updateBanner(final HttpServletRequest request) {
        final String eid = request.getParameter("eid");
        final String ebannerType = request.getParameter("ebannerType");
        final String ebannerContent = request.getParameter("ebannerContent");
        //        final String oldImg = request.getParameter("oldImg");
        final String ebannerPosition = request.getParameter("ebannerPosition");
        final String erank = request.getParameter("erank");
        final String ebannerDesc = request.getParameter("ebannerDesc");
        final String user_id = request.getSession().getAttribute("user_id").toString();
        final JsonResult data = new JsonResult(true);
        controllerTemplate.execute(data, new ControllerCallBack() {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile               file2            = multipartRequest.getFile("ebannerImg");

            @Override
            public void executeService() {
                BannerDO bannerDO = bannerDAO.bannerDetailsQuery(Integer.parseInt(eid));
                if (null == bannerDO) {
                    throw new BaseRuntimeException("banner信息查询异常");
                }
                String oldBannerType = bannerDO.getBannerType();

                String bannerContent = ebannerContent;
                //				if (ebannerType.equals(0)) {
                //					bannerContent = ebannerContent;
                //				} else {
                //					bannerContent = Html2Text.getContent(ebannerContent);
                //					bannerContent = bannerContent.replaceAll("\\s*", "");
                //					// bannerContent =
                //					// StringUtils.deleteWhitespace(bannerContent);
                //				}
                String imgUrl = null;
                if (file2 != null) {
                    try {
                        imgUrl = FileUploadUtils.upload(request, file2);
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
                //                else {
                //                    imgUrl = oldImg;
                //                }
                bannerDO.setBannerType(ebannerType);
                
                if (StringUtils.equals(ebannerType, BannerTypeEnum.IMG_WORD.getCode())) {
                    bannerDO.setBannerContent(bannerContent);
                }
                if (StringUtils.equals(ebannerType, BannerTypeEnum.TO_HTML.getCode())) {
                    if(StringUtils.isNotBlank(bannerContent)){
                        String replace_first = bannerContent.replace("<p>", "");
                        String  replace_second= replace_first.replace("</p>", "");
                        String  replace_third= replace_second.replace("<br>", "");
                        bannerDO.setBannerContent(replace_third);
                    }
                }
                //现在类型是 跳商品 或者 店铺
                if (StringUtils.equals(ebannerType, BannerTypeEnum.TO_PRODUCT.getCode())
                    || StringUtils.equals(ebannerType, BannerTypeEnum.TO_MERCHANT.getCode())) {
                    //原来的旧类型是  图文 链接  
                    if (StringUtils.equals(oldBannerType, BannerTypeEnum.IMG_WORD.getCode())
                        || StringUtils.equals(oldBannerType, BannerTypeEnum.TO_HTML.getCode())) {
                        bannerDO.setBannerContent(null);
                    }
                }

                bannerDO.setBannerPosition(ebannerPosition);
                if (StringUtils.isNotBlank(imgUrl))
                    bannerDO.setBannerImg(imgUrl);
                bannerDO.setBannerDesc(ebannerDesc);
                bannerDO.setRank(Integer.parseInt(erank));
                bannerDO.setModifier(user_id);

                int result = bannerDAO.updateBanner(bannerDO);
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
                AssertUtil.notBlank(eid, "id不能为空");
            }
        });
        return data;
    }

    /**
     * 删除banner
     * 
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping("/deletebanner.do")
    @ResponseBody
    public Object deleteNotice(HttpServletRequest request, final int id) {
        final JsonResult json = new JsonResult(true);
        final String user_id = request.getSession().getAttribute("user_id").toString();
        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                int result = bannerDAO.deleteBanner(user_id, id);
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

    /**
     * 图片上传接口
     * 
     * @param photo
     * @return
     * @throws Exception
     */
    @RequestMapping("/fileUploadForEditor.do")
    @ResponseBody
    public String fileUploadForEditor(HttpServletRequest request, @RequestParam MultipartFile photo)
                                                                                                    throws Exception {
        String ImgName = getRandomCode(3) + ".jpg";
        String path = IMAGE_FILE + ImgName;

        String url = "";
        if (photo != null) {
            url = IMAGE_PATH + ImgName;
            try {
                uploadFile(photo, path);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                logger.error(e, e);
            }
        }

        String data = "{\"errno\":0,\"data\":[\"" + url + "\"]}";
        return data;
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

    @RequestMapping("/selectGoodsForBanner.do")
    @ResponseBody
    public JSONObject selectGoodsForBanner(HttpServletRequest request, Integer offset, Integer limit) {
        String bannerId = request.getParameter("bannerId");

        String productId = request.getParameter("productId");
        String proType = request.getParameter("proType");
        String teamId = request.getParameter("teamId");
        String teamName = request.getParameter("teamName");
        String productName = request.getParameter("productName");
        String onSale = request.getParameter("onSale");
        String canBuy = request.getParameter("canBuy");
        String licenseNo = request.getParameter("licenseNo");
        String isDelete = request.getParameter("isDelete");
        String goodErpNo = request.getParameter("goodErpNo");
        String barcode = request.getParameter("goodbarCode");//条形码
        String hasStockPrice = request.getParameter("hasStockPrice");//是否有属性  0无 1有

        String hasChoose = request.getParameter("linkType");

        JSONObject data = new JSONObject();

        BannerDO bannerDO = null;
        List<String> linkPros = new ArrayList<String>();
        if (StringUtils.isNotBlank(bannerId)) {
            bannerDO = bannerDAO.bannerDetailsQuery(Integer.valueOf(bannerId));
            String linkProChild = bannerDO.getBannerContent();
            if (StringUtils.isBlank(linkProChild)) {
                linkPros = null;
            } else {
                linkPros = JSONArrayUtils.parseArray(linkProChild, String.class);
                if (CollectionUtils.isEmpty(linkPros))
                    linkPros = null;
            }
        }

        List<Map<String, Object>> goodsList = productDAO.queryGoods(productId, proType, teamId,
            teamName, productName, onSale, canBuy, licenseNo, isDelete, goodErpNo, hasChoose,
            linkPros, null, barcode, hasStockPrice, offset, limit);
        for (Map<String, Object> map : goodsList) {
            String thisProductId = (String) map.get("productId");
            if (null != bannerDO
                && StringUtils.contains(bannerDO.getBannerContent(), thisProductId))
                map.put("hasChoose", true);
        }

        int total = productDAO.selectProductPageCount(productId, proType, teamId, teamName,
            productName, onSale, canBuy, licenseNo, isDelete, goodErpNo, hasChoose, linkPros, null,
            barcode, hasStockPrice);

        data.put("rows", goodsList);
        data.put("total", total);
        return data;
    }

    @RequestMapping("/selectTeamForBanner.do")
    @ResponseBody
    public JSONObject selectTeamForBanner(HttpServletRequest request, Integer offset, Integer limit) {
        String bannerId = request.getParameter("bannerId");

        String nickName = request.getParameter("nickName");
        String userCell = request.getParameter("userCell");
        String teamErpNo = request.getParameter("teamErpNo");
        String teamName = request.getParameter("teamName");
        String teamType = request.getParameter("teamType");
        String isTop = request.getParameter("isTop");
        String delflg = request.getParameter("delflg");
        String teamLevel = request.getParameter("teamLevel");
        String realName = request.getParameter("realName");

        String hasChoose = request.getParameter("linkType");
        JSONObject data = new JSONObject();
        try {

            BannerDO bannerDO = null;
            List<String> linkTeamIds = new ArrayList<String>();
            if (StringUtils.isNotBlank(bannerId)) {
                bannerDO = bannerDAO.bannerDetailsQuery(Integer.valueOf(bannerId));
                String linkTeamChild = bannerDO.getBannerContent();
                if (StringUtils.isBlank(linkTeamChild)) {
                    linkTeamIds = null;
                } else {
                    linkTeamIds = JSONArrayUtils.parseArray(linkTeamChild, String.class);
                    if (CollectionUtils.isEmpty(linkTeamIds))
                        linkTeamIds = null;
                }
            }

            List<Map<String, Object>> queryList = teamDAO.selectTeam(userCell, nickName, teamErpNo,
                teamName, teamType, isTop, delflg, teamLevel, realName, null, hasChoose,
                linkTeamIds, null, null, offset, limit);

            for (Map<String, Object> map : queryList) {
                String thisTeamId = (String) map.get("teamId");
                if (null != bannerDO
                    && StringUtils.contains(bannerDO.getBannerContent(), thisTeamId))
                    map.put("hasChoose", true);

            }
            int count = teamDAO.selectTeamCount(userCell, nickName, teamErpNo, teamName, teamType,
                isTop, delflg, teamLevel, realName, null, hasChoose, linkTeamIds, null, null);
            data.put("rows", queryList);
            data.put("total", count);
        } catch (Exception e) {
            logger.error(MessageFormat.format("查询团队异常", new Object[] {}));
            // throw new BaseRuntimeException(ErrorCode.QUERY_EEOR,
            // ErrorCode.QUERY_EEOR.getDesc());
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
    @RequestMapping("/bannerChooseGood.do")
    @ResponseBody
    public Object bannerChooseGood(final HttpServletRequest request, final int bannerId,
                                   final String productId) {

        final JsonResult json = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();

        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                BannerDO bannerDO = bannerDAO.bannerDetailsQuery(bannerId);
                if (null == bannerDO)
                    return;

                if (StringUtils.equals(bannerDO.getBannerType(),
                    BannerTypeEnum.TO_PRODUCT.getCode())
                    && StringUtils.equals(bannerDO.getBannerType(),
                        BannerTypeEnum.TO_MERCHANT.getCode()))
                    return;

                String bannerContent = bannerDO.getBannerContent();

                if (StringUtils.contains(bannerContent, productId))
                    return;

                List<String> parseArray = JSONArrayUtils.parseArray(bannerContent, String.class);
                if (CollectionUtils.isEmpty(parseArray)) {
                    parseArray = new ArrayList<String>();
                }
                parseArray.add(productId);

                JSONArray array = JSONArray.parseArray(JSON.toJSONString(parseArray));

                int modifyLinkGoodsOrShop = bannerDAO.modifyLinkGoodsOrShop(array.toString(),
                    user_id, bannerId);
                if (modifyLinkGoodsOrShop > 0)
                    json.setSuccess(true);
            }

            @Override
            public void check() {
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
    @RequestMapping("/bannerUnChooseGood.do")
    @ResponseBody
    public Object bannerUnChooseGood(final HttpServletRequest request, final int bannerId,
                                     final String productId) {

        final JsonResult json = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();

        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                BannerDO bannerDO = bannerDAO.bannerDetailsQuery(bannerId);
                if (null == bannerDO)
                    return;

                if (StringUtils.equals(bannerDO.getBannerType(),
                    BannerTypeEnum.TO_PRODUCT.getCode())
                    && StringUtils.equals(bannerDO.getBannerType(),
                        BannerTypeEnum.TO_MERCHANT.getCode()))
                    return;

                String bannerContent = bannerDO.getBannerContent();

                if (!StringUtils.contains(bannerContent, productId))
                    return;

                List<String> parseArray = JSONArrayUtils.parseArray(bannerContent, String.class);
                parseArray.remove(productId);

                JSONArray array = JSONArray.parseArray(JSON.toJSONString(parseArray));

                int modifyLinkGoodsOrShop = bannerDAO.modifyLinkGoodsOrShop(array.toString(),
                    user_id, bannerId);
                if (modifyLinkGoodsOrShop > 0)
                    json.setSuccess(true);
            }

            @Override
            public void check() {
            }

        });
        return json;
    }

    @RequestMapping("/bannerChooseGoodAll.do")
    @ResponseBody
    public Object bannerChooseGood(final HttpServletRequest request, final int bannerId,
                                   final String[] productIdS) {

        final JsonResult json = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();

        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                BannerDO bannerDO = bannerDAO.bannerDetailsQuery(bannerId);
                if (null == bannerDO)
                    return;

                if (StringUtils.equals(bannerDO.getBannerType(),
                    BannerTypeEnum.TO_PRODUCT.getCode())
                    && StringUtils.equals(bannerDO.getBannerType(),
                        BannerTypeEnum.TO_MERCHANT.getCode()))
                    return;

                String bannerContent = bannerDO.getBannerContent();
                List<String> parseArray = JSONArrayUtils.parseArray(bannerContent, String.class);
                if (CollectionUtils.isEmpty(parseArray)) {
                    parseArray = new ArrayList<String>();
                }

                for (String productId : productIdS) {
                    if (StringUtils.contains(bannerContent, productId))
                        continue;
                    parseArray.add(productId);
                }

                JSONArray array = JSONArray.parseArray(JSON.toJSONString(parseArray));

                int modifyLinkGoodsOrShop = bannerDAO.modifyLinkGoodsOrShop(array.toString(),
                    user_id, bannerId);
                if (modifyLinkGoodsOrShop > 0)
                    json.setSuccess(true);
            }

            @Override
            public void check() {
            }

        });
        return json;
    }

    @RequestMapping("/bannerUnChooseGoodAll.do")
    @ResponseBody
    public Object bannerUnChooseGoodAll(final HttpServletRequest request, final int bannerId,
                                        final String[] productIdS) {

        final JsonResult json = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();

        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                BannerDO bannerDO = bannerDAO.bannerDetailsQuery(bannerId);
                if (null == bannerDO)
                    return;

                if (StringUtils.equals(bannerDO.getBannerType(),
                    BannerTypeEnum.TO_PRODUCT.getCode())
                    && StringUtils.equals(bannerDO.getBannerType(),
                        BannerTypeEnum.TO_MERCHANT.getCode()))
                    return;

                String bannerContent = bannerDO.getBannerContent();
                List<String> parseArray = JSONArrayUtils.parseArray(bannerContent, String.class);
                if (CollectionUtils.isEmpty(parseArray)) {
                    parseArray = new ArrayList<String>();
                }

                for (String productId : productIdS) {
                    if (!StringUtils.contains(bannerContent, productId))
                        continue;
                    parseArray.remove(productId);
                }

                JSONArray array = JSONArray.parseArray(JSON.toJSONString(parseArray));

                int modifyLinkGoodsOrShop = bannerDAO.modifyLinkGoodsOrShop(array.toString(),
                    user_id, bannerId);
                if (modifyLinkGoodsOrShop > 0)
                    json.setSuccess(true);
            }

            @Override
            public void check() {
            }

        });
        return json;
    }

    /**
     * 选择关联门店
     * 
     * @param request
     * @param bannerId
     * @param productId
     * @return
     */
    @RequestMapping("/bannerChooseShop.do")
    @ResponseBody
    public Object bannerChooseShop(final HttpServletRequest request, final int bannerId,
                                   final String teamId) {

        final JsonResult json = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();

        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                BannerDO bannerDO = bannerDAO.bannerDetailsQuery(bannerId);
                if (null == bannerDO)
                    return;

                if (StringUtils.equals(bannerDO.getBannerType(),
                    BannerTypeEnum.TO_PRODUCT.getCode())
                    && StringUtils.equals(bannerDO.getBannerType(),
                        BannerTypeEnum.TO_MERCHANT.getCode()))
                    return;

                String bannerContent = bannerDO.getBannerContent();

                if (StringUtils.contains(bannerContent, teamId))
                    return;

                List<String> parseArray = JSONArrayUtils.parseArray(bannerContent, String.class);
                if (CollectionUtils.isEmpty(parseArray)) {
                    parseArray = new ArrayList<String>();
                }
                parseArray.add(teamId);

                JSONArray array = JSONArray.parseArray(JSON.toJSONString(parseArray));

                int modifyLinkGoodsOrShop = bannerDAO.modifyLinkGoodsOrShop(array.toString(),
                    user_id, bannerId);
                if (modifyLinkGoodsOrShop > 0)
                    json.setSuccess(true);
            }

            @Override
            public void check() {
            }

        });
        return json;
    }

    @RequestMapping("/bannerUnChooseShop.do")
    @ResponseBody
    public Object bannerUnChooseShop(final HttpServletRequest request, final int bannerId,
                                     final String teamId) {

        final JsonResult json = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();

        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                BannerDO bannerDO = bannerDAO.bannerDetailsQuery(bannerId);
                if (null == bannerDO)
                    return;

                if (StringUtils.equals(bannerDO.getBannerType(),
                    BannerTypeEnum.TO_PRODUCT.getCode())
                    && StringUtils.equals(bannerDO.getBannerType(),
                        BannerTypeEnum.TO_MERCHANT.getCode()))
                    return;

                String bannerContent = bannerDO.getBannerContent();

                if (!StringUtils.contains(bannerContent, teamId))
                    return;

                List<String> parseArray = JSONArrayUtils.parseArray(bannerContent, String.class);
                parseArray.remove(teamId);

                JSONArray array = JSONArray.parseArray(JSON.toJSONString(parseArray));

                int modifyLinkGoodsOrShop = bannerDAO.modifyLinkGoodsOrShop(array.toString(),
                    user_id, bannerId);
                if (modifyLinkGoodsOrShop > 0)
                    json.setSuccess(true);
            }

            @Override
            public void check() {
            }

        });
        return json;
    }

    @RequestMapping("/bannerChooseShopAll.do")
    @ResponseBody
    public Object bannerChooseShopAll(final HttpServletRequest request, final int bannerId,
                                      final String[] teamIdS) {

        final JsonResult json = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();

        //        final String[] teamIdS = request.getParameterValues("teamIdS");

        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                BannerDO bannerDO = bannerDAO.bannerDetailsQuery(bannerId);
                if (null == bannerDO)
                    return;

                if (StringUtils.equals(bannerDO.getBannerType(),
                    BannerTypeEnum.TO_PRODUCT.getCode())
                    && StringUtils.equals(bannerDO.getBannerType(),
                        BannerTypeEnum.TO_MERCHANT.getCode()))
                    return;

                String bannerContent = bannerDO.getBannerContent();

                List<String> parseArray = JSONArrayUtils.parseArray(bannerContent, String.class);
                if (CollectionUtils.isEmpty(parseArray)) {
                    parseArray = new ArrayList<String>();
                }

                for (String teamId : teamIdS) {
                    if (StringUtils.contains(bannerContent, teamId))
                        continue;

                    parseArray.add(teamId);
                }
                JSONArray array = JSONArray.parseArray(JSON.toJSONString(parseArray));

                int modifyLinkGoodsOrShop = bannerDAO.modifyLinkGoodsOrShop(array.toString(),
                    user_id, bannerId);

                if (modifyLinkGoodsOrShop > 0)
                    json.setSuccess(true);
            }

            @Override
            public void check() {
            }

        });
        return json;
    }

    @RequestMapping("/bannerChooseUnShopAll.do")
    @ResponseBody
    public Object bannerChooseUnShopAll(final HttpServletRequest request, final int bannerId,
                                        final String[] teamIdS) {

        final JsonResult json = new JsonResult(false);
        final String user_id = request.getSession().getAttribute("user_id").toString();

        //        final String[] teamIdS = request.getParameterValues("teamIdS");

        controllerTemplate.execute(json, new ControllerCallBack() {

            @Override
            public void executeService() {
                BannerDO bannerDO = bannerDAO.bannerDetailsQuery(bannerId);
                if (null == bannerDO)
                    return;

                if (StringUtils.equals(bannerDO.getBannerType(),
                    BannerTypeEnum.TO_PRODUCT.getCode())
                    && StringUtils.equals(bannerDO.getBannerType(),
                        BannerTypeEnum.TO_MERCHANT.getCode()))
                    return;

                String bannerContent = bannerDO.getBannerContent();

                List<String> parseArray = JSONArrayUtils.parseArray(bannerContent, String.class);
                if (CollectionUtils.isEmpty(parseArray)) {
                    parseArray = new ArrayList<String>();
                }

                for (String teamId : teamIdS) {
                    if (!StringUtils.contains(bannerContent, teamId))
                        continue;

                    parseArray.remove(teamId);
                }
                JSONArray array = JSONArray.parseArray(JSON.toJSONString(parseArray));

                int modifyLinkGoodsOrShop = bannerDAO.modifyLinkGoodsOrShop(array.toString(),
                    user_id, bannerId);

                if (modifyLinkGoodsOrShop > 0)
                    json.setSuccess(true);
            }

            @Override
            public void check() {
            }

        });
        return json;
    }

}
