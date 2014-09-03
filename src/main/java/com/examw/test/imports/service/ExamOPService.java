package com.examw.test.imports.service;
/**
 * 所属考试操作服务。
 * 
 * @author yangyong
 * @since 2014年9月3日
 */
public interface ExamOPService extends OPService {
	/**
	 * 获取选中的所属考试值。
	 * @return 所属考试值。
	 */
	String getSelected();
}