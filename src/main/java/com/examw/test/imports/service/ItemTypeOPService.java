package com.examw.test.imports.service;
/**
 * 题型操作接口。
 * 
 * @author yangyong
 * @since 2014年9月3日
 */
public interface ItemTypeOPService extends OPService {
	/**
	 * 设置选中的题型值。
	 * @param value
	 * 题型值。
	 */
	void setSelected(String value);
	/**
	 * 获取选中的题型值。
	 * @return 题型值。
	 */
	String getSelected();
}