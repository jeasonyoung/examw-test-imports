package com.examw.test.imports.service;

import com.examw.test.imports.model.ClientUploadItem;

/**
 * 试题上传到远程服务器。
 * 
 * @author yangyong
 * @since 2014年9月15日
 */
public interface ItemUploadRemoteService {
	/**
	 * 上传试题数据。
	 * @param paperId
	 * @param data
	 * @return
	 * @throws Exception
	 */
	boolean upload(String paperId,String structureId, ClientUploadItem data) throws Exception;
}