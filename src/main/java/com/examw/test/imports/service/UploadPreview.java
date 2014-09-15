package com.examw.test.imports.service;

import com.examw.test.imports.model.ClientUploadItem;

/**
 * 上传预览接口。
 * 
 * @author yangyong
 * @since 2014年9月12日
 */
public interface UploadPreview {
	/**
	 * 获取所属试卷ID。
	 * @return 所属试卷ID。
	 */
	String getPaperId();
	/**
	 * 获取所属试卷结构ID。
	 * @return 所属试卷结构ID。
	 */
	String getPaperStructureId();
	/**
	 * 显示预览界面。
	 * @param paperId
	 * @param paperStructureId
	 * @param clientUploadItem
	 */
	void showDialog(String paperId, String paperStructureId, ClientUploadItem[] clientUploadItem);
}