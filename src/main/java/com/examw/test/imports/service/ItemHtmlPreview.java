package com.examw.test.imports.service;

import com.examw.test.imports.model.ClientUploadItem;

/**
 * 题目Html预览。
 * 
 * @author yangyong
 * @since 2014年9月15日
 */
public interface ItemHtmlPreview {
	/**
	 * html预览。
	 * @param source
	 * @return
	 */
	String htmlPreview(ClientUploadItem source);
}