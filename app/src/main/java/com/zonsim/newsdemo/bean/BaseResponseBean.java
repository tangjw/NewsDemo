package com.zonsim.newsdemo.bean;

/**
 * Created by tang-jw on 2016/5/27.
 */
public class BaseResponseBean {
/**
 * proLevel : 5
 * proName : 食品检验工
 * status : 1
 */

private int proLevel;
private String proName;
private int status;

public int getProLevel() {
	return proLevel;
}

public void setProLevel(int proLevel) {
	this.proLevel = proLevel;
}

public String getProName() {
	return proName;
}

public void setProName(String proName) {
	this.proName = proName;
}

public int getStatus() {
	return status;
}

public void setStatus(int status) {
	this.status = status;
}

private int ret;
private String msg;

public int getRet() {
	return ret;
}

public void setRet(int ret) {
	this.ret = ret;
}

public String getMsg() {
	return msg;
}

public void setMsg(String msg) {
	this.msg = msg;
}
	
	
}
