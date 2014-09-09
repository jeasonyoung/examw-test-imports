package com.examw.test.imports.service;

import java.util.List;

import com.examw.test.imports.model.KeyValueType;

/**
 * 所属试卷结构远程数据服务接口。
 * 
 * @author yangyong
 * @since 2014年9月4日
 */
public interface PaperStructureRemoteDataService {
	/**
	 * 加载试卷结构数据。
	 * @param paperId
	 * 所属试卷Id。
	 * @return
	 */
	List<KeyValueType> loadPaperStructures(String paperId) throws Exception;
}