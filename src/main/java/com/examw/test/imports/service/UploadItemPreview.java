package com.examw.test.imports.service;

import com.examw.test.imports.model.ClientUploadItem;

/**
 * 上传题目预览。
 * 
 * @author yangyong
 * @since 2014年9月15日
 */
public interface UploadItemPreview {
	/**
	 * 写入试题JSON内容。
	 * @param clientUploadItem
	 */
	void writeItemJSON(ClientUploadItem clientUploadItem);
}