package com.onway.model;

import java.util.ArrayList;
import java.util.List;

import com.onway.fyapp.common.dal.dataobject.GoodsAttrValueDO;
import com.onway.platform.common.base.ToString;

public class GoodsAttrAndValues extends ToString {

    /**  */
    private static final long      serialVersionUID = -3559449619278257771L;

    private int                    attrId;

    private String                 attrName;

    private List<GoodsAttrValueDO> valueList        = new ArrayList<GoodsAttrValueDO>();

    public int getAttrId() {
        return attrId;
    }

    public void setAttrId(int attrId) {
        this.attrId = attrId;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public List<GoodsAttrValueDO> getValueList() {
        return valueList;
    }

    public void setValueList(List<GoodsAttrValueDO> valueList) {
        this.valueList = valueList;
    }

}
