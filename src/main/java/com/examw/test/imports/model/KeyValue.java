package com.examw.test.imports.model;

import java.io.Serializable;
/**
 * 数据键值对。
 * 
 * @author yangyong
 * @since 2014年9月3日
 */
public class KeyValue implements Serializable,Comparable<KeyValue> {
	private static final long serialVersionUID = 1L;
	private String key,value;
	/**
	 *  构造函数。
	 * @param key
	 * 数据键名。
	 * @param value
	 * 数据键值。
	 */
	public KeyValue(String key,String value){
		this.key = key;
		this.value = value;
	}
	/**
	 * 获取数据键名。
	 * @return 数据键名。
	 */
	public String getKey() {
		return key;
	}
	/**
	 * 设置数据键名。
	 * @param key 
	 *	  数据键名。
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * 获取数据值。
	 * @return 数据值。
	 */
	public String getValue() {
		return value;
	}
	/**
	 * 设置数据值。
	 * @param value 
	 *	  数据值。
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(KeyValue o) {
		if(o != null){
			if(this == o) return 0;
			if(this.value != null) return this.value.compareTo(o.getValue());
			if(this.key != null) return this.key.compareTo(o.getKey());
		}
		return 0;
	}
	/*
	 * 输出字符串。
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		 return String.format("[%1$s=>%2$s]", this.key, this.value);
	}
}