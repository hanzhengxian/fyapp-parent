package com.onway.task;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.onway.common.lang.CollectionUtils;
import com.onway.fyapp.common.dal.daointerface.ProductTeamDAO;
import com.onway.fyapp.common.dal.daointerface.StockPriceDAO;
import com.onway.fyapp.common.dal.daointerface.StockPriceTeamDAO;
import com.onway.fyapp.common.dal.daointerface.StockPriceTeamMiddleDAO;
import com.onway.fyapp.common.dal.daointerface.TeamDAO;
import com.onway.fyapp.common.dal.dataobject.StockPriceDO;
import com.onway.fyapp.common.dal.dataobject.StockPriceTeamDO;
import com.onway.fyapp.common.dal.dataobject.StockPriceTeamMiddleDO;
import com.onway.fyapp.common.dal.dataobject.TeamDO;
import com.onway.platform.common.utils.LogUtil;
import com.onway.utils.DateUtil;

/**
 * 每天 定时同步门店库存
 */
public class TeamStockTask extends AbstractTask {

    private static final Logger     log = Logger.getLogger(TeamStockTask.class);

    private TeamDAO                 teamDAO;
    private StockPriceDAO           stockPriceDAO;
    private ProductTeamDAO          productTeamDAO;
    private StockPriceTeamMiddleDAO stockPriceTeamMiddleDAO;
    private StockPriceTeamDAO       stockPriceTeamDAO;

    @Override
    protected boolean canProcess() {
        return true;
    }

    @Override
    protected void process() {
        try {
            Date start = new Date();
            logger.info("*****************定时从中间表获取库存信息开始****************");
            List<StockPriceTeamMiddleDO> listMiddle = stockPriceTeamMiddleDAO.queryAll();
            logger.info("*****************一共获取" + listMiddle.size() + "条库存信息****************");
            int successNum = 0;
            int failedNum = 0;
            
            if(CollectionUtils.isNotEmpty(listMiddle)){
                //清理旧数据   是否为总店   0否  1是
                stockPriceTeamDAO.deleteAllByIstop("1");
            }
            
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
            //将所有记录状态改为已操作
            stockPriceTeamMiddleDAO.updateStatusAll();
            
            logger.info("*****************一共获取" + listMiddle.size() + "条库存信息,其中" + successNum
                        + "条同步成功," + failedNum + "条同步失败****************");
            Date end = new Date();
            System.out.println("---------------------------------开始时间" + start);
            System.out.println("---------------------------------结束时间" + end);
        } catch (Exception e) {
            LogUtil.error(
                log,
                MessageFormat.format(
                    "从中间表获取库存信息失败，date{0},message:{1}",
                    new Object[] { DateUtil.dateToString(new Date(), DateUtil.newFormat),
                            e.getMessage() }));
        }
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

    protected boolean doExcute2(StockPriceTeamDO stockPriceTeamDo) {
        StockPriceDO selectById = stockPriceDAO.selectById(stockPriceTeamDo.getStockId());
        int count = productTeamDAO.queryCountByTeamIdAndProductId(stockPriceTeamDo.getTeamId(),
            selectById.getGoodsNo());
        if (count > 0) {
            stockPriceDAO.updateStockByErpNo(stockPriceTeamDo.getStock(),
                stockPriceTeamDo.getStockId());
        }
        return true;
    }

    public void setTeamDAO(TeamDAO teamDAO) {
        this.teamDAO = teamDAO;
    }

    public void setStockPriceDAO(StockPriceDAO stockPriceDAO) {
        this.stockPriceDAO = stockPriceDAO;
    }

    public void setProductTeamDAO(ProductTeamDAO productTeamDAO) {
        this.productTeamDAO = productTeamDAO;
    }

    public void setStockPriceTeamMiddleDAO(StockPriceTeamMiddleDAO stockPriceTeamMiddleDAO) {
        this.stockPriceTeamMiddleDAO = stockPriceTeamMiddleDAO;
    }

    public void setStockPriceTeamDAO(StockPriceTeamDAO stockPriceTeamDAO) {
        this.stockPriceTeamDAO = stockPriceTeamDAO;
    }

}
