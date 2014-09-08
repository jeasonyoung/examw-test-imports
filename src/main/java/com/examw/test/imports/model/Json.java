package com.examw.test.imports.model;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * JSON数据模型。
 * @author young。
 * 
 * */
@JsonSerialize(include = Inclusion.NON_NULL)
public class Json implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean success = false;
	private Object data;
	private String msg;
	/**
	 * 获取是否成功。
	 * @return 是否成功。
	 * */
	public boolean isSuccess() {
		return success;
	}
	/**
	 * 设置是否成功。
	 * @param success
	 * 	是否成功。
	 * */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	/**
	 * 获取数据。
	 * @return 数据。
	 * */
	public Object getData() {
		return data;
	}
	/**
	 * 设置数据。
	 * @param data
	 * 	数据。
	 * */
	public void setData(Object data) {
		this.data = data;
	}
	/**
	 * 获取提示信息。
	 * @return 提示信息。
	 * */
	public String getMsg() {
		return msg;
	}
	/**
	 * 设置提示信息。
	 * @param msg
	 * 	提示信息。
	 * */
	public void setMsg(String msg) {
		this.msg = msg;
	}
}