package com.examw.test.imports.service;

import javax.swing.text.Document;

/**
 * 题型格式化。
 * 
 * @author yangyong
 * @since 2014年9月4日
 */
public interface ItemTypeFormat {
	/**
	 * 格式化处理。
	 * @param document
	 */
	void format(Document document);
}