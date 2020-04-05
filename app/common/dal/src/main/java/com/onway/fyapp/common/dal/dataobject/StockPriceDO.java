/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.dataobject;

import com.onway.fyapp.common.dal.dataobject.BaseDO;

import com.onway.common.lang.Money;
import java.util.Date;

/**
 * A data object class directly models database table <tt>hqyt_stock_price</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_stock_price.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: StockPriceDO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class StockPriceDO extends BaseDO {
    private static final long serialVersionUID = 741231858441822688L;

    //========== properties ==========

	/**
	 * This property corresponds to db column <tt>ID</tt>.
	 */
	private int id;

	/**
	 * This property corresponds to db column <tt>GOODS_NO</tt>.
	 */
	private String goodsNo;

	/**
	 * This property corresponds to db column <tt>ATTR_IDS</tt>.
	 */
	private String attrIds;

	/**
	 * This property corresponds to db column <tt>ATTR_NAMES</tt>.
	 */
	private String attrNames;

	/**
	 * This property corresponds to db column <tt>VALUE_IDS</tt>.
	 */
	private String valueIds;

	/**
	 * This property corresponds to db column <tt>VALUEES</tt>.
	 */
	private String valuees;

	/**
	 * This property corresponds to db column <tt>IMG_SRC</tt>.
	 */
	private String imgSrc;

	/**
	 * This property corresponds to db column <tt>PRICE</tt>.
	 */
	private Money price = new Money(0, 0);

	/**
	 * This property corresponds to db column <tt>GOOD_AMOUNT</tt>.
	 */
	private Money goodAmount = new Money(0, 0);

	/**
	 * This property corresponds to db column <tt>POINT</tt>.
	 */
	private Money point = new Money(0, 0);

	/**
	 * This property corresponds to db column <tt>POINT_RATE</tt>.
	 */
	private double pointRate;

	/**
	 * This property corresponds to db column <tt>STOCK</tt>.
	 */
	private double stock;

	/**
	 * This property corresponds to db column <tt>SELL_NUM</tt>.
	 */
	private double sellNum;

	/**
	 * This property corresponds to db column <tt>TYPE</tt>.
	 */
	private String type;

	/**
	 * This property corresponds to db column <tt>PICK_LIMIT</tt>.
	 */
	private double pickLimit;

	/**
	 * This property corresponds to db column <tt>BAR_CODE</tt>.
	 */
	private String barCode;

	/**
	 * This property corresponds to db column <tt>ERP_NO</tt>.
	 */
	private String erpNo;

	/**
	 * This property corresponds to db column <tt>PAY_TYPE</tt>.
	 */
	private String payType;

	/**
	 * This property corresponds to db column <tt>CONTROL_STOCK</tt>.
	 */
	private String controlStock;

	/**
	 * This property corresponds to db column <tt>MEMO</tt>.
	 */
	private String memo;

	/**
	 * This property corresponds to db column <tt>GMT_CREATE</tt>.
	 */
	private Date gmtCreate;

	/**
	 * This property corresponds to db column <tt>CREATER</tt>.
	 */
	private String creater;

	/**
	 * This property corresponds to db column <tt>GMT_MODIFIED</tt>.
	 */
	private Date gmtModified;

	/**
	 * This property corresponds to db column <tt>MODIFIER</tt>.
	 */
	private String modifier;

	/**
	 * This property corresponds to db column <tt>DEL_FLG</tt>.
	 */
	private String delFlg;

	/**
	 * This property corresponds to db column <tt>RETURN_TYPE</tt>.
	 */
	private String returnType;

	/**
	 * This property corresponds to db column <tt>RETURN_VALUE</tt>.
	 */
	private String returnValue;

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
     * Getter method for property <tt>goodsNo</tt>.
     *
     * @return property value of goodsNo
     */
	public String getGoodsNo() {
		return goodsNo;
	}
	
	/**
	 * Setter method for property <tt>goodsNo</tt>.
	 * 
	 * @param goodsNo value to be assigned to property goodsNo
     */
	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

    /**
     * Getter method for property <tt>attrIds</tt>.
     *
     * @return property value of attrIds
     */
	public String getAttrIds() {
		return attrIds;
	}
	
	/**
	 * Setter method for property <tt>attrIds</tt>.
	 * 
	 * @param attrIds value to be assigned to property attrIds
     */
	public void setAttrIds(String attrIds) {
		this.attrIds = attrIds;
	}

    /**
     * Getter method for property <tt>attrNames</tt>.
     *
     * @return property value of attrNames
     */
	public String getAttrNames() {
		return attrNames;
	}
	
	/**
	 * Setter method for property <tt>attrNames</tt>.
	 * 
	 * @param attrNames value to be assigned to property attrNames
     */
	public void setAttrNames(String attrNames) {
		this.attrNames = attrNames;
	}

    /**
     * Getter method for property <tt>valueIds</tt>.
     *
     * @return property value of valueIds
     */
	public String getValueIds() {
		return valueIds;
	}
	
	/**
	 * Setter method for property <tt>valueIds</tt>.
	 * 
	 * @param valueIds value to be assigned to property valueIds
     */
	public void setValueIds(String valueIds) {
		this.valueIds = valueIds;
	}

    /**
     * Getter method for property <tt>valuees</tt>.
     *
     * @return property value of valuees
     */
	public String getValuees() {
		return valuees;
	}
	
	/**
	 * Setter method for property <tt>valuees</tt>.
	 * 
	 * @param valuees value to be assigned to property valuees
     */
	public void setValuees(String valuees) {
		this.valuees = valuees;
	}

    /**
     * Getter method for property <tt>imgSrc</tt>.
     *
     * @return property value of imgSrc
     */
	public String getImgSrc() {
		return imgSrc;
	}
	
	/**
	 * Setter method for property <tt>imgSrc</tt>.
	 * 
	 * @param imgSrc value to be assigned to property imgSrc
     */
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

    /**
     * Getter method for property <tt>price</tt>.
     *
     * @return property value of price
     */
	public Money getPrice() {
		return price;
	}
	
	/**
	 * Setter method for property <tt>price</tt>.
	 * 
	 * @param price value to be assigned to property price
     */
	public void setPrice(Money price) {
		if (price == null) {
			this.price = new Money(0, 0);
		} else {
			this.price = price;
		}
	}

    /**
     * Getter method for property <tt>goodAmount</tt>.
     *
     * @return property value of goodAmount
     */
	public Money getGoodAmount() {
		return goodAmount;
	}
	
	/**
	 * Setter method for property <tt>goodAmount</tt>.
	 * 
	 * @param goodAmount value to be assigned to property goodAmount
     */
	public void setGoodAmount(Money goodAmount) {
		if (goodAmount == null) {
			this.goodAmount = new Money(0, 0);
		} else {
			this.goodAmount = goodAmount;
		}
	}

    /**
     * Getter method for property <tt>point</tt>.
     *
     * @return property value of point
     */
	public Money getPoint() {
		return point;
	}
	
	/**
	 * Setter method for property <tt>point</tt>.
	 * 
	 * @param point value to be assigned to property point
     */
	public void setPoint(Money point) {
		if (point == null) {
			this.point = new Money(0, 0);
		} else {
			this.point = point;
		}
	}

    /**
     * Getter method for property <tt>pointRate</tt>.
     *
     * @return property value of pointRate
     */
	public double getPointRate() {
		return pointRate;
	}
	
	/**
	 * Setter method for property <tt>pointRate</tt>.
	 * 
	 * @param pointRate value to be assigned to property pointRate
     */
	public void setPointRate(double pointRate) {
		this.pointRate = pointRate;
	}

    /**
     * Getter method for property <tt>stock</tt>.
     *
     * @return property value of stock
     */
	public double getStock() {
		return stock;
	}
	
	/**
	 * Setter method for property <tt>stock</tt>.
	 * 
	 * @param stock value to be assigned to property stock
     */
	public void setStock(double stock) {
		this.stock = stock;
	}

    /**
     * Getter method for property <tt>sellNum</tt>.
     *
     * @return property value of sellNum
     */
	public double getSellNum() {
		return sellNum;
	}
	
	/**
	 * Setter method for property <tt>sellNum</tt>.
	 * 
	 * @param sellNum value to be assigned to property sellNum
     */
	public void setSellNum(double sellNum) {
		this.sellNum = sellNum;
	}

    /**
     * Getter method for property <tt>type</tt>.
     *
     * @return property value of type
     */
	public String getType() {
		return type;
	}
	
	/**
	 * Setter method for property <tt>type</tt>.
	 * 
	 * @param type value to be assigned to property type
     */
	public void setType(String type) {
		this.type = type;
	}

    /**
     * Getter method for property <tt>pickLimit</tt>.
     *
     * @return property value of pickLimit
     */
	public double getPickLimit() {
		return pickLimit;
	}
	
	/**
	 * Setter method for property <tt>pickLimit</tt>.
	 * 
	 * @param pickLimit value to be assigned to property pickLimit
     */
	public void setPickLimit(double pickLimit) {
		this.pickLimit = pickLimit;
	}

    /**
     * Getter method for property <tt>barCode</tt>.
     *
     * @return property value of barCode
     */
	public String getBarCode() {
		return barCode;
	}
	
	/**
	 * Setter method for property <tt>barCode</tt>.
	 * 
	 * @param barCode value to be assigned to property barCode
     */
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

    /**
     * Getter method for property <tt>erpNo</tt>.
     *
     * @return property value of erpNo
     */
	public String getErpNo() {
		return erpNo;
	}
	
	/**
	 * Setter method for property <tt>erpNo</tt>.
	 * 
	 * @param erpNo value to be assigned to property erpNo
     */
	public void setErpNo(String erpNo) {
		this.erpNo = erpNo;
	}

    /**
     * Getter method for property <tt>payType</tt>.
     *
     * @return property value of payType
     */
	public String getPayType() {
		return payType;
	}
	
	/**
	 * Setter method for property <tt>payType</tt>.
	 * 
	 * @param payType value to be assigned to property payType
     */
	public void setPayType(String payType) {
		this.payType = payType;
	}

    /**
     * Getter method for property <tt>controlStock</tt>.
     *
     * @return property value of controlStock
     */
	public String getControlStock() {
		return controlStock;
	}
	
	/**
	 * Setter method for property <tt>controlStock</tt>.
	 * 
	 * @param controlStock value to be assigned to property controlStock
     */
	public void setControlStock(String controlStock) {
		this.controlStock = controlStock;
	}

    /**
     * Getter method for property <tt>memo</tt>.
     *
     * @return property value of memo
     */
	public String getMemo() {
		return memo;
	}
	
	/**
	 * Setter method for property <tt>memo</tt>.
	 * 
	 * @param memo value to be assigned to property memo
     */
	public void setMemo(String memo) {
		this.memo = memo;
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
     * Getter method for property <tt>creater</tt>.
     *
     * @return property value of creater
     */
	public String getCreater() {
		return creater;
	}
	
	/**
	 * Setter method for property <tt>creater</tt>.
	 * 
	 * @param creater value to be assigned to property creater
     */
	public void setCreater(String creater) {
		this.creater = creater;
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
     * Getter method for property <tt>modifier</tt>.
     *
     * @return property value of modifier
     */
	public String getModifier() {
		return modifier;
	}
	
	/**
	 * Setter method for property <tt>modifier</tt>.
	 * 
	 * @param modifier value to be assigned to property modifier
     */
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

    /**
     * Getter method for property <tt>delFlg</tt>.
     *
     * @return property value of delFlg
     */
	public String getDelFlg() {
		return delFlg;
	}
	
	/**
	 * Setter method for property <tt>delFlg</tt>.
	 * 
	 * @param delFlg value to be assigned to property delFlg
     */
	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

    /**
     * Getter method for property <tt>returnType</tt>.
     *
     * @return property value of returnType
     */
	public String getReturnType() {
		return returnType;
	}
	
	/**
	 * Setter method for property <tt>returnType</tt>.
	 * 
	 * @param returnType value to be assigned to property returnType
     */
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

    /**
     * Getter method for property <tt>returnValue</tt>.
     *
     * @return property value of returnValue
     */
	public String getReturnValue() {
		return returnValue;
	}
	
	/**
	 * Setter method for property <tt>returnValue</tt>.
	 * 
	 * @param returnValue value to be assigned to property returnValue
     */
	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}
}
