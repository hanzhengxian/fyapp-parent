package com.onway.task;


/**
 * 每天 定时清理临时金额
 */
public class CleanTempAmountTask extends AbstractTask {

//	private static final Logger log = Logger.getLogger(CleanTempAmountTask.class);

//	private AccountTempDAO accountTempDAO;
	
	@Override
	protected boolean canProcess() {
		return true;
	}

	@Override
	protected void process() {
//		try {
//			Date today = DateUtil.getToday();
//		    logger.info("*****************定时清理临时账户开始****************");
//		    int count = accountTempDAO.cleanTempAmount(today);
//		    logger.info("*****************定时清理临时账户结束，共清理了"+count+"个临时账户****************");
//		} catch (Exception e) {
//			LogUtil.error(log, MessageFormat.format("定时清理临时账户失败，date{0},message:{1}",new Object[] {DateUtil.dateToString(new Date(),DateUtil.newFormat), e.getMessage() }));
//		}
	}

//    public AccountTempDAO getAccountTempDAO() {
//        return accountTempDAO;
//    }
//
//    public void setAccountTempDAO(AccountTempDAO accountTempDAO) {
//        this.accountTempDAO = accountTempDAO;
//    }

}
