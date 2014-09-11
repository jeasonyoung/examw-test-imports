package com.examw.test.imports.service;

import javax.swing.text.JTextComponent;

/**
 * 题型格式化。
 * 
 * @author yangyong
 * @since 2014年9月4日
 */
public interface ItemTypeFormat {
	/**
	 * 格式化处理。
	 * @param textComponent
	 */
	void format(JTextComponent textComponent) throws Exception;
	/**
	 * 格式化为上传的json格式。
	 * @param format
	 * @param type
	 * @return
	 */
	String uploadFormatJson(String format, String type) throws Exception;
}