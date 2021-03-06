/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.dataobject;

import com.onway.fyapp.common.dal.dataobject.BaseDO;

import com.onway.common.lang.Money;
import java.util.Date;

/**
 * A data object class directly models database table <tt>hqyt_order_return</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_order_return.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: OrderReturnDO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class OrderReturnDO extends BaseDO {
    private static final long serialVersionUID = 741231858441822688L;

    //========== properties ==========

	/**
	 * This property corresponds to db column <tt>ID</tt>.
	 */
	private int id;

	/**
	 * This property corresponds to db column <tt>CHILD_ORDER_ID</tt>.
	 */
	private String childOrderId;

	/**
	 * This property corresponds to db column <tt>CARRI_COM</tt>.
	 */
	private String carriCom;

	/**
	 * This property corresponds to db column <tt>CARRI_NO</tt>.
	 */
	private String carriNo;

	/**
	 * This property corresponds to db column <tt>REASON</tt>.
	 */
	private String reason;

	/**
	 * This property corresponds to db column <tt>REASON_OTHER</tt>.
	 */
	private String reasonOther;

	/**
	 * This property corresponds to db column <tt>RETURN_IMG</tt>.
	 */
	private String returnImg;

	/**
	 * This property corresponds to db column <tt>MONEY_STATUS</tt>.
	 */
	private String moneyStatus;

	/**
	 * This property corresponds to db column <tt>MONEY_MEMO</tt>.
	 */
	private String moneyMemo;

	/**
	 * This property corresponds to db column <tt>FAIL_REASON</tt>.
	 */
	private String failReason;

	/**
	 * This property corresponds to db column <tt>RETURN_VOUCHER_IMG</tt>.
	 */
	private String returnVoucherImg;

	/**
	 * This property corresponds to db column <tt>RETURN_VOUCHER_MEMO</tt>.
	 */
	private String returnVoucherMemo;

	/**
	 * This property corresponds to db column <tt>ASS_USER</tt>.
	 */
	private String assUser;

	/**
	 * This property corresponds to db column <tt>LINK_USER</tt>.
	 */
	private String linkUser;

	/**
	 * This property corresponds to db column <tt>LINK_CELL</tt>.
	 */
	private String linkCell;

	/**
	 * This property corresponds to db column <tt>QUARTY_USER</tt>.
	 */
	private String quartyUser;

	/**
	 * This property corresponds to db column <tt>QUARTY_IMG</tt>.
	 */
	private String quartyImg;

	/**
	 * This property corresponds to db column <tt>QUARTY_DESC</tt>.
	 */
	private String quartyDesc;

	/**
	 * This property corresponds to db column <tt>SUCC_REASON</tt>.
	 */
	private String succReason;

	/**
	 * This property corresponds to db column <tt>DEAL_WAY</tt>.
	 */
	private String dealWay;

	/**
	 * This property corresponds to db column <tt>SOME_FLG</tt>.
	 */
	private String someFlg;

	/**
	 * This property corresponds to db column <tt>CWNQ_USER</tt>.
	 */
	private String cwnqUser;

	/**
	 * This property corresponds to db column <tt>SUCC_CWNQ</tt>.
	 */
	private String succCwnq;

	/**
	 * This property corresponds to db column <tt>STATUS</tt>.
	 */
	private String status;

	/**
	 * This property corresponds to db column <tt>CHOOSE_NUM</tt>.
	 */
	private double chooseNum;

	/**
	 * This property corresponds to db column <tt>SHOULD_RETURN_MONEY</tt>.
	 */
	private Money shouldReturnMoney = new Money(0, 0);

	/**
	 * This property corresponds to db column <tt>NEED_RETURN_FLG</tt>.
	 */
	private String needReturnFlg;

	/**
	 * This property corresponds to db column <tt>SHOULD_RETURN_POINT</tt>.
	 */
	private Money shouldReturnPoint = new Money(0, 0);

	/**
	 * This property corresponds to db column <tt>SHOULD_RETURN_VOUCHER</tt>.
	 */
	private Money shouldReturnVoucher = new Money(0, 0);

	/**
	 * This property corresponds to db column <tt>SHOULD_RETURN_DEVOTE</tt>.
	 */
	private Money shouldReturnDevote = new Money(0, 0);

	/**
	 * This property corresponds to db column <tt>SHOULD_RETURN_REBATE_USER</tt>.
	 */
	private Money shouldReturnRebateUser = new Money(0, 0);

	/**
	 * This property corresponds to db column <tt>SHOULD_RETURN_REBATE_RECO</tt>.
	 */
	private Money shouldReturnRebateReco = new Money(0, 0);

	/**
	 * This property corresponds to db column <tt>SHOULD_RETURN_REBATE_RECOUSER</tt>.
	 */
	private Money shouldReturnRebateRecouser = new Money(0, 0);

	/**
	 * This property corresponds to db column <tt>GMT_CREATE</tt>.
	 */
	private Date gmtCreate;

	/**
	 * This property corresponds to db column <tt>GMT_MODIFIED</tt>.
	 */
	private Date gmtModified;

	/**
	 * This property corresponds to db column <tt>IS_SC</tt>.
	 */
	private boolean isSc;

	/**
	 * This property corresponds to db column <tt>GMT_SC</tt>.
	 */
	private Date gmtSc;

    //========== getters and setters ==========

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
	public int getId() {
		return id;
	}
	
	/**
	 * Setter method for property <tt>id</tt>.
	 * 
	 * @param id value to be assigned to property id
     */
	public void setId(int id) {
		this.id = id;
	}

    /**
     * Getter method for property <tt>childOrderId</tt>.
     *
     * @return property value of childOrderId
     */
	public String getChildOrderId() {
		return childOrderId;
	}
	
	/**
	 * Setter method for property <tt>childOrderId</tt>.
	 * 
	 * @param childOrderId value to be assigned to property childOrderId
     */
	public void setChildOrderId(String childOrderId) {
		this.childOrderId = childOrderId;
	}

    /**
     * Getter method for property <tt>carriCom</tt>.
     *
     * @return property value of carriCom
     */
	public String getCarriCom() {
		return carriCom;
	}
	
	/**
	 * Setter method for property <tt>carriCom</tt>.
	 * 
	 * @param carriCom value to be assigned to property carriCom
     */
	public void setCarriCom(String carriCom) {
		this.carriCom = carriCom;
	}

    /**
     * Getter method for property <tt>carriNo</tt>.
     *
     * @return property value of carriNo
     */
	public String getCarriNo() {
		return carriNo;
	}
	
	/**
	 * Setter method for property <tt>carriNo</tt>.
	 * 
	 * @param carriNo value to be assigned to property carriNo
     */
	public void setCarriNo(String carriNo) {
		this.carriNo = carriNo;
	}

    /**
     * Getter method for property <tt>reason</tt>.
     *
     * @return property value of reason
     */
	public String getReason() {
		return reason;
	}
	
	/**
	 * Setter method for property <tt>reason</tt>.
	 * 
	 * @param reason value to be assigned to property reason
     */
	public void setReason(String reason) {
		this.reason = reason;
	}

    /**
     * Getter method for property <tt>reasonOther</tt>.
     *
     * @return property value of reasonOther
     */
	public String getReasonOther() {
		return reasonOther;
	}
	
	/**
	 * Setter method for property <tt>reasonOther</tt>.
	 * 
	 * @param reasonOther value to be assigned to property reasonOther
     */
	public void setReasonOther(String reasonOther) {
		this.reasonOther = reasonOther;
	}

    /**
     * Getter method for property <tt>returnImg</tt>.
     *
     * @return property value of returnImg
     */
	public String getReturnImg() {
		return returnImg;
	}
	
	/**
	 * Setter method for property <tt>returnImg</tt>.
	 * 
	 * @param returnImg value to be assigned to property returnImg
     */
	public void setReturnImg(String returnImg) {
		this.returnImg = returnImg;
	}

    /**
     * Getter method for property <tt>moneyStatus</tt>.
     *
     * @return property value of moneyStatus
     */
	public String getMoneyStatus() {
		return moneyStatus;
	}
	
	/**
	 * Setter method for property <tt>moneyStatus</tt>.
	 * 
	 * @param moneyStatus value to be assigned to property moneyStatus
     */
	public void setMoneyStatus(String moneyStatus) {
		this.moneyStatus = moneyStatus;
	}

    /**
     * Getter method for property <tt>moneyMemo</tt>.
     *
     * @return property value of moneyMemo
     */
	public String getMoneyMemo() {
		return moneyMemo;
	}
	
	/**
	 * Setter method for property <tt>moneyMemo</tt>.
	 * 
	 * @param moneyMemo value to be assigned to property moneyMemo
     */
	public void setMoneyMemo(String moneyMemo) {
		this.moneyMemo = moneyMemo;
	}

    /**
     * Getter method for property <tt>failReason</tt>.
     *
     * @return property value of failReason
     */
	public String getFailReason() {
		return failReason;
	}
	
	/**
	 * Setter method for property <tt>failReason</tt>.
	 * 
	 * @param failReason value to be assigned to property failReason
     */
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

    /**
     * Getter method for property <tt>returnVoucherImg</tt>.
     *
     * @return property value of returnVoucherImg
     */
	public String getReturnVoucherImg() {
		return returnVoucherImg;
	}
	
	/**
	 * Setter method for property <tt>returnVoucherImg</tt>.
	 * 
	 * @param returnVoucherImg value to be assigned to property returnVoucherImg
     */
	public void setReturnVoucherImg(String returnVoucherImg) {
		this.returnVoucherImg = returnVoucherImg;
	}

    /**
     * Getter method for property <tt>returnVoucherMemo</tt>.
     *
     * @return property value of returnVoucherMemo
     */
	public String getReturnVoucherMemo() {
		return returnVoucherMemo;
	}
	
	/**
	 * Setter method for property <tt>returnVoucherMemo</tt>.
	 * 
	 * @param returnVoucherMemo value to be assigned to property returnVoucherMemo
     */
	public void setReturnVoucherMemo(String returnVoucherMemo) {
		this.returnVoucherMemo = returnVoucherMemo;
	}

    /**
     * Getter method for property <tt>assUser</tt>.
     *
     * @return property value of assUser
     */
	public String getAssUser() {
		return assUser;
	}
	
	/**
	 * Setter method for property <tt>assUser</tt>.
	 * 
	 * @param assUser value to be assigned to property assUser
     */
	public void setAssUser(String assUser) {
		this.assUser = assUser;
	}

    /**
     * Getter method for property <tt>linkUser</tt>.
     *
     * @return property value of linkUser
     */
	public String getLinkUser() {
		return linkUser;
	}
	
	/**
	 * Setter method for property <tt>linkUser</tt>.
	 * 
	 * @param linkUser value to be assigned to property linkUser
     */
	public void setLinkUser(String linkUser) {
		this.linkUser = linkUser;
	}

    /**
     * Getter method for property <tt>linkCell</tt>.
     *
     * @return property value of linkCell
     */
	public String getLinkCell() {
		return linkCell;
	}
	
	/**
	 * Setter method for property <tt>linkCell</tt>.
	 * 
	 * @param linkCell value to be assigned to property linkCell
     */
	public void setLinkCell(String linkCell) {
		this.linkCell = linkCell;
	}

    /**
     * Getter method for property <tt>quartyUser</tt>.
     *
     * @return property value of quartyUser
     */
	public String getQuartyUser() {
		return quartyUser;
	}
	
	/**
	 * Setter method for property <tt>quartyUser</tt>.
	 * 
	 * @param quartyUser value to be assigned to property quartyUser
     */
	public void setQuartyUser(String quartyUser) {
		this.quartyUser = quartyUser;
	}

    /**
     * Getter method for property <tt>quartyImg</tt>.
     *
     * @return property value of quartyImg
     */
	public String getQuartyImg() {
		return quartyImg;
	}
	
	/**
	 * Setter method for property <tt>quartyImg</tt>.
	 * 
	 * @param quartyImg value to be assigned to property quartyImg
     */
	public void setQuartyImg(String quartyImg) {
		this.quartyImg = quartyImg;
	}

    /**
     * Getter method for property <tt>quartyDesc</tt>.
     *
     * @return property value of quartyDesc
     */
	public String getQuartyDesc() {
		return quartyDesc;
	}
	
	/**
	 * Setter method for property <tt>quartyDesc</tt>.
	 * 
	 * @param quartyDesc value to be assigned to property quartyDesc
     */
	public void setQuartyDesc(String quartyDesc) {
		this.quartyDesc = quartyDesc;
	}

    /**
     * Getter method for property <tt>succReason</tt>.
     *
     * @return property value of succReason
     */
	public String getSuccReason() {
		return succReason;
	}
	
	/**
	 * Setter method for property <tt>succReason</tt>.
	 * 
	 * @param succReason value to be assigned to property succReason
     */
	public void setSuccReason(String succReason) {
		this.succReason = succReason;
	}

    /**
     * Getter method for property <tt>dealWay</tt>.
     *
     * @return property value of dealWay
     */
	public String getDealWay() {
		return dealWay;
	}
	
	/**
	 * Setter method for property <tt>dealWay</tt>.
	 * 
	 * @param dealWay value to be assigned to property dealWay
     */
	public void setDealWay(String dealWay) {
		this.dealWay = dealWay;
	}

    /**
     * Getter method for property <tt>someFlg</tt>.
     *
     * @return property value of someFlg
     */
	public String getSomeFlg() {
		return someFlg;
	}
	
	/**
	 * Setter method for property <tt>someFlg</tt>.
	 * 
	 * @param someFlg value to be assigned to property someFlg
     */
	public void setSomeFlg(String someFlg) {
		this.someFlg = someFlg;
	}

    /**
     * Getter method for property <tt>cwnqUser</tt>.
     *
     * @return property value of cwnqUser
     */
	public String getCwnqUser() {
		return cwnqUser;
	}
	
	/**
	 * Setter method for property <tt>cwnqUser</tt>.
	 * 
	 * @param cwnqUser value to be assigned to property cwnqUser
     */
	public void setCwnqUser(String cwnqUser) {
		this.cwnqUser = cwnqUser;
	}

    /**
     * Getter method for property <tt>succCwnq</tt>.
     *
     * @return property value of succCwnq
     */
	public String getSuccCwnq() {
		return succCwnq;
	}
	
	/**
	 * Setter method for property <tt>succCwnq</tt>.
	 * 
	 * @param succCwnq value to be assigned to property succCwnq
     */
	public void setSuccCwnq(String succCwnq) {
		this.succCwnq = succCwnq;
	}

    /**
     * Getter method for property <tt>status</tt>.
     *
     * @return property value of status
     */
	public String getStatus() {
		return status;
	}
	
	/**
	 * Setter method for property <tt>status</tt>.
	 * 
	 * @param status value to be assigned to property status
     */
	public void setStatus(String status) {
		this.status = status;
	}

    /**
     * Getter method for property <tt>chooseNum</tt>.
     *
     * @return property value of chooseNum
     */
	public double getChooseNum() {
		return chooseNum;
	}
	
	/**
	 * Setter method for property <tt>chooseNum</tt>.
	 * 
	 * @param chooseNum value to be assigned to property chooseNum
     */
	public void setChooseNum(double chooseNum) {
		this.chooseNum = chooseNum;
	}

    /**
     * Getter method for property <tt>shouldReturnMoney</tt>.
     *
     * @return property value of shouldReturnMoney
     */
	public Money getShouldReturnMoney() {
		return shouldReturnMoney;
	}
	
	/**
	 * Setter method for property <tt>shouldReturnMoney</tt>.
	 * 
	 * @param shouldReturnMoney value to be assigned to property shouldReturnMoney
     */
	public void setShouldReturnMoney(Money shouldReturnMoney) {
		if (shouldReturnMoney == null) {
			this.shouldReturnMoney = new Money(0, 0);
		} else {
			this.shouldReturnMoney = shouldReturnMoney;
		}
	}

    /**
     * Getter method for property <tt>needReturnFlg</tt>.
     *
     * @return property value of needReturnFlg
     */
	public String getNeedReturnFlg() {
		return needReturnFlg;
	}
	
	/**
	 * Setter method for property <tt>needReturnFlg</tt>.
	 * 
	 * @param needReturnFlg value to be assigned to property needReturnFlg
     */
	public void setNeedReturnFlg(String needReturnFlg) {
		this.needReturnFlg = needReturnFlg;
	}

    /**
     * Getter method for property <tt>shouldReturnPoint</tt>.
     *
     * @return property value of shouldReturnPoint
     */
	public Money getShouldReturnPoint() {
		return shouldReturnPoint;
	}
	
	/**
	 * Setter method for property <tt>shouldReturnPoint</tt>.
	 * 
	 * @param shouldReturnPoint value to be assigned to property shouldReturnPoint
     */
	public void setShouldReturnPoint(Money shouldReturnPoint) {
		if (shouldReturnPoint == null) {
			this.shouldReturnPoint = new Money(0, 0);
		} else {
			this.shouldReturnPoint = shouldReturnPoint;
		}
	}

    /**
     * Getter method for property <tt>shouldReturnVoucher</tt>.
     *
     * @return property value of shouldReturnVoucher
     */
	public Money getShouldReturnVoucher() {
		return shouldReturnVoucher;
	}
	
	/**
	 * Setter method for property <tt>shouldReturnVoucher</tt>.
	 * 
	 * @param shouldReturnVoucher value to be assigned to property shouldReturnVoucher
     */
	public void setShouldReturnVoucher(Money shouldReturnVoucher) {
		if (shouldReturnVoucher == null) {
			this.shouldReturnVoucher = new Money(0, 0);
		} else {
			this.shouldReturnVoucher = shouldReturnVoucher;
		}
	}

    /**
     * Getter method for property <tt>shouldReturnDevote</tt>.
     *
     * @return property value of shouldReturnDevote
     */
	public Money getShouldReturnDevote() {
		return shouldReturnDevote;
	}
	
	/**
	 * Setter method for property <tt>shouldReturnDevote</tt>.
	 * 
	 * @param shouldReturnDevote value to be assigned to property shouldReturnDevote
     */
	public void setShouldReturnDevote(Money shouldReturnDevote) {
		if (shouldReturnDevote == null) {
			this.shouldReturnDevote = new Money(0, 0);
		} else {
			this.shouldReturnDevote = shouldReturnDevote;
		}
	}

    /**
     * Getter method for property <tt>shouldReturnRebateUser</tt>.
     *
     * @return property value of shouldReturnRebateUser
     */
	public Money getShouldReturnRebateUser() {
		return shouldReturnRebateUser;
	}
	
	/**
	 * Setter method for property <tt>shouldReturnRebateUser</tt>.
	 * 
	 * @param shouldReturnRebateUser value to be assigned to property shouldReturnRebateUser
     */
	public void setShouldReturnRebateUser(Money shouldReturnRebateUser) {
		if (shouldReturnRebateUser == null) {
			this.shouldReturnRebateUser = new Money(0, 0);
		} else {
			this.shouldReturnRebateUser = shouldReturnRebateUser;
		}
	}

    /**
     * Getter method for property <tt>shouldReturnRebateReco</tt>.
     *
     * @return property value of shouldReturnRebateReco
     */
	public Money getShouldReturnRebateReco() {
		return shouldReturnRebateReco;
	}
	
	/**
	 * Setter method for property <tt>shouldReturnRebateReco</tt>.
	 * 
	 * @param shouldReturnRebateReco value to be assigned to property shouldReturnRebateReco
     */
	public void setShouldReturnRebateReco(Money shouldReturnRebateReco) {
		if (shouldReturnRebateReco == null) {
			this.shouldReturnRebateReco = new Money(0, 0);
		} else {
			this.shouldReturnRebateReco = shouldReturnRebateReco;
		}
	}

    /**
     * Getter method for property <tt>shouldReturnRebateRecouser</tt>.
     *
     * @return property value of shouldReturnRebateRecouser
     */
	public Money getShouldReturnRebateRecouser() {
		return shouldReturnRebateRecouser;
	}
	
	/**
	 * Setter method for property <tt>shouldReturnRebateRecouser</tt>.
	 * 
	 * @param shouldReturnRebateRecouser value to be assigned to property shouldReturnRebateRecouser
     */
	public void setShouldReturnRebateRecouser(Money shouldReturnRebateRecouser) {
		if (shouldReturnRebateRecouser == null) {
			this.shouldReturnRebateRecouser = new Money(0, 0);
		} else {
			this.shouldReturnRebateRecouser = shouldReturnRebateRecouser;
		}
	}

    /**
     * Getter method for property <tt>gmtCreate</tt>.
     *
     * @return property value of gmtCreate
     */
	public Date getGmtCreate() {
		return gmtCreate;
	}
	
	/**
	 * Setter method for property <tt>gmtCreate</tt>.
	 * 
	 * @param gmtCreate value to be assigned to property gmtCreate
     */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

    /**
     * Getter method for property <tt>gmtModified</tt>.
     *
     * @return property value of gmtModified
     */
	public Date getGmtModified() {
		return gmtModified;
	}
	
	/**
	 * Setter method for property <tt>gmtModified</tt>.
	 * 
	 * @param gmtModified value to be assigned to property gmtModified
     */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

    /**
     * Getter method for property <tt>isSc</tt>.
     *
     * @return property value of isSc
     */
	public boolean isIsSc() {
		return isSc;
	}
	
	/**
	 * Setter method for property <tt>isSc</tt>.
	 * 
	 * @param isSc value to be assigned to property isSc
     */
	public void setIsSc(boolean isSc) {
		this.isSc = isSc;
	}

    /**
     * Getter method for property <tt>gmtSc</tt>.
     *
     * @return property value of gmtSc
     */
	public Date getGmtSc() {
		return gmtSc;
	}
	
	/**
	 * Setter method for property <tt>gmtSc</tt>.
	 * 
	 * @param gmtSc value to be assigned to property gmtSc
     */
	public void setGmtSc(Date gmtSc) {
		this.gmtSc = gmtSc;
	}
}
