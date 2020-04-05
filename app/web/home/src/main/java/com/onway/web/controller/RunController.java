/**
 * onway.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.onway.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//import com.onway.common.lang.CollectionUtils;
import com.onway.fyapp.common.dal.dataobject.StockPriceDO;
import com.onway.fyapp.common.dal.dataobject.StockPriceTeamDO;
import com.onway.fyapp.common.dal.dataobject.StockPriceTeamMiddleDO;
import com.onway.fyapp.common.dal.dataobject.TeamDO;
import com.onway.result.JsonResult;
import com.onway.web.template.ControllerCallBack;

@Controller
public class RunController extends AbstractController {

    @RequestMapping("/runDemo")
    @ResponseBody
    public Object runDemo(HttpServletRequest request) {

        final JsonResult result = new JsonResult(false);
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void executeService() {
                Date start = new Date();
                logger.info("*****************定时从中间表获取库存信息开始****************");
                List<StockPriceTeamMiddleDO> listMiddle = stockPriceTeamMiddleDAO.queryAll();
                logger.info("*****************一共获取" + listMiddle.size() + "条库存信息****************");
                int successNum = 0;
                int failedNum = 0;
                for (StockPriceTeamMiddleDO stockPriceTeamMiddleDO : listMiddle) {
                    //执行添加数据
                    List<StockPriceTeamDO> dataList = doExcute1(stockPriceTeamMiddleDO);

                    if (CollectionUtils.isNotEmpty(dataList)) {
                        for (StockPriceTeamDO data : dataList) {
                            //先判断是否有数据
                            StockPriceTeamDO stockPriceTeamDO = stockPriceTeamDAO
                                .queryByIdAndTeamId(data.getStockId(), data.getTeamId());
                            int a = 0;
                            if (null == stockPriceTeamDO) {
                                a = stockPriceTeamDAO.insert(data);

                            } else {
                                stockPriceTeamDO.setStock(stockPriceTeamMiddleDO.getStock());
                                a = stockPriceTeamDAO.update(stockPriceTeamDO);
                            }
                            if (a > 0) {
                                stockPriceTeamMiddleDAO.updateStatus(stockPriceTeamMiddleDO.getId());
                                if (doExcute2(data)) {
                                    successNum++;
                                    logger.info("*****************同步"
                                                + stockPriceTeamMiddleDO.getErpNoAttr()
                                                + "库存信息成功****************");
                                } else {
                                    failedNum++;
                                    logger.error("*****************注意：同步"
                                                 + stockPriceTeamMiddleDO.getErpNoAttr()
                                                 + "库存信息失败****************");
                                }
                            } else {
                                failedNum++;
                                logger.error("*****************注意：同步"
                                             + stockPriceTeamMiddleDO.getErpNoAttr()
                                             + "库存信息失败****************");
                            }
                        }
                    }
                }
                logger.info("*****************一共获取" + listMiddle.size() + "条库存信息,其中" + successNum
                            + "条同步成功," + failedNum + "条同步失败****************");
                Date end = new Date();
                System.out.println("---------------------------------开始时间" + start);
                System.out.println("---------------------------------结束时间" + end);

                result.setSuccess(true);
            }

            @Override
            public void check() {
            }
        });
        return result;
    }

    protected List<StockPriceTeamDO> doExcute1(StockPriceTeamMiddleDO stockPriceTeamMiddleDO) {
        TeamDO teamDo = teamDAO.selectTeamByErpNo(stockPriceTeamMiddleDO.getErpNoTeam());
        //可能重复
        List<StockPriceDO> stockPriceList = stockPriceDAO.queryInfoByErpNo(stockPriceTeamMiddleDO
            .getErpNoAttr());
        if (teamDo != null && CollectionUtils.isNotEmpty(stockPriceList)) {
            List<StockPriceTeamDO> stockPriceTeamDOs = new ArrayList<StockPriceTeamDO>();
            for (StockPriceDO stockPriceDO : stockPriceList) {
                StockPriceTeamDO stockPriceTeam = new StockPriceTeamDO();
                stockPriceTeam.setTeamId(teamDo.getTeamId());
                stockPriceTeam.setStockId(stockPriceDO.getId());
                stockPriceTeam.setStock(stockPriceTeamMiddleDO.getStock());
                stockPriceTeamDOs.add(stockPriceTeam);
            }
            return stockPriceTeamDOs;
        } else {
            return null;
        }
    }

    private boolean doExcute2(StockPriceTeamDO stockPriceTeamDo) {
        StockPriceDO selectById = stockPriceDAO.selectById(stockPriceTeamDo.getStockId());
        int count = productTeamDAO.queryCountByTeamIdAndProductId(stockPriceTeamDo.getTeamId(),
            selectById.getGoodsNo());
        if (count > 0) {
            stockPriceDAO.updateStockByErpNo(stockPriceTeamDo.getStock(),
                stockPriceTeamDo.getStockId());
        }
        return true;
    }

}
