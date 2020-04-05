package com.onway.result;

import java.util.List;

public class ObjAndListEntity<T> {
	private T obj;
	private List<?> objList ;
	
	public T getObj() {
		return obj;
	}
	public void setObj(T obj) {
		this.obj = obj;
	}
	public List<?> getObjList() {
		return objList;
	}
	public void setObjList(List<?> objList) {
		this.objList = objList;
	}
	
}
