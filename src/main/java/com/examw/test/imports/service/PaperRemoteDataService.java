package com.examw.test.imports.service;

import java.util.List;

import com.examw.test.imports.model.KeyValue;

/**
 * 客户端试卷数据服务接口。
 * 
 * @author yangyong
 * @since 2014年9月3日
 */
public interface PaperRemoteDataService {
	/**
	 * 加载试卷数据。
	 * @param examId
	 * 所属考试Id。
	 * @return
	 */
	List<KeyValue> loadPapers(String examId) throws Exception;
}