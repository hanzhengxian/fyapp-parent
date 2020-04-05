package com.onway.task;

import java.text.MessageFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.onway.fyapp.common.dal.daointerface.ProductDAO;
import com.onway.platform.common.utils.LogUtil;
import com.onway.utils.DateUtil;

/**
 * 每月一号 定时重置所有商品月销量
 */
public class CleanProMounthSellTask extends AbstractTask {

	private static final Logger log = Logger
			.getLogger(CleanProMounthSellTask.class);

	private ProductDAO productDAO;

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	@Override
	protected boolean canProcess() {
		return true;
	}

	@Override
	protected void process() {
		try {

			productDAO.cleanProMounthSell();

		} catch (Exception e) {
			LogUtil.error(log, MessageFormat.format(
					"定时重置所有商品月销量失败，date{0},message:{1}",
					new Object[] {
							DateUtil.dateToString(new Date(),
									DateUtil.newFormat), e.getMessage() }));
		}
	}

}
