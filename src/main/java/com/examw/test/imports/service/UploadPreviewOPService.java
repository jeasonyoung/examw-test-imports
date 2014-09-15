package com.examw.test.imports.service;

import com.examw.test.imports.model.ClientUploadItem;

/**
 * 上传预览操作。
 * 
 * @author yangyong
 * @since 2014年9月12日
 */
public interface UploadPreviewOPService {
	/**
	 * 下一题。
	 * @return
	 */
	ClientUploadItem next();
	/**
	 * 上一题。
	 * @return
	 */
	ClientUploadItem prev();
	/**
	 * 当前题目。
	 * @return
	 */
	ClientUploadItem current();
}
