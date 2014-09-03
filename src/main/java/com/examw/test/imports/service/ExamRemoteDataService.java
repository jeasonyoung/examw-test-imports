package com.examw.test.imports.service;

import java.util.List;

import com.examw.test.imports.model.KeyValue;

/**
 * 远程加载考试数据服务接口。
 * 
 * @author yangyong
 * @since 2014年9月3日
 */
public interface ExamRemoteDataService  {
	/**
	 * 加载考试数据。
	 * @return
	 * 考试数据。
	 */
	List<KeyValue> loadExams();
}