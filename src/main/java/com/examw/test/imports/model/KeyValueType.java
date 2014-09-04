package com.examw.test.imports.model;
/**
 * 数据键值类型对。
 * 
 * @author yangyong
 * @since 2014年9月4日
 */
public class KeyValueType extends KeyValue {
	private static final long serialVersionUID = 1L;
	private Integer type;
	/**
	 * 构造函数。
	 * @param key
	 * @param value
	 */
	public KeyValueType(String key, String value) {
		super(key, value);
	}
	/**
	 * 构造函数。
	 * @param key
	 * @param value
	 * @param type
	 */
	public KeyValueType(String key, String value, Integer type){
		this(key, value);
		this.type = type;
	}
	/**
	 * 获取类型。
	 * @return 类型。
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置类型。
	 * @param type 
	 *	  类型。
	 */
	public void setType(Integer type) {
		this.type = type;
	}
}