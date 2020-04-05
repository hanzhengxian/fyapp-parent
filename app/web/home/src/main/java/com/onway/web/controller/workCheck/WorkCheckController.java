package com.onway.web.controller.workCheck;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.onway.model.GpsDemo;
import com.onway.model.enums.WorkCheckTypeEnum;
import com.onway.utils.DateUtil;
import com.onway.web.controller.AbstractController;

/**
 * 考勤
 * 
 * @author yuhang.ni
 * @version $Id: WorkCheckController.java, v 0.1 2019年4月3日 下午1:43:43 Administrator Exp $
 */
@Controller
public class WorkCheckController extends AbstractController {

    /**
     * 考情记录
     * 
     * @param offset
     * @param limit
     * @param userMsg
     * @param teamMsg
     * @param enddate
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping("/queryAllWorkCheck.do")
    @ResponseBody
    public JSONObject queryAllWorkCheck(Integer offset, Integer limit, String userMsg,
                                        String teamMsg, String enddate, String startTime,
                                        String endTime, String type, String erpNo) {
        JSONObject jo = new JSONObject();

        Date startDate = DateUtil.stringToDate(startTime, DateUtil.webFormat);
        Date endDate = DateUtil.stringToDate(endTime, DateUtil.webFormat);
        if (null != endDate)
            endDate = DateUtil.addDays(endDate, 1);

        List<Map<String, Object>> queryAllWorkCheck = workCheckDAO.queryAllWorkCheck(userMsg,
            teamMsg, type, startDate, endDate, offset, limit);
        for (Map<String, Object> map : queryAllWorkCheck) {
            String teamGps = MapUtils.getString(map, "teamGps");
            GpsDemo gpsTeam = JSONObject.parseObject(teamGps, GpsDemo.class);
            if (null != gpsTeam) {
                map.put("longitudeTeam", gpsTeam.getLongitude());
                map.put("latitudeTeam", gpsTeam.getLatitude());
            }

            String userGps = MapUtils.getString(map, "userGps");
            GpsDemo gpsUser = JSONObject.parseObject(userGps, GpsDemo.class);
            if (null != gpsUser) {
                map.put("longitudeUser", gpsUser.getLongitude());
                map.put("latitudeUser", gpsUser.getLatitude());
            }

            WorkCheckTypeEnum workCheckTypeEnum = WorkCheckTypeEnum.getByCode(MapUtils.getString(
                map, "type"));
            if (null != workCheckTypeEnum)
                map.put("type", workCheckTypeEnum.message());
        }

        jo.put("rows", queryAllWorkCheck);
        jo.put("total",
            workCheckDAO.queryAllWorkCheckCount(userMsg, teamMsg, type, startDate, endDate));
        return jo;
    }
}
