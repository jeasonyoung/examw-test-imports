package com.examw.test.imports.service;

import com.examw.test.imports.model.KeyValueType;

/**
 * 所属试卷结构操作服务接口。
 * 
 * @author yangyong
 * @since 2014年9月4日
 */
public interface PaperStructureOPService extends OPService {
	/**
	 * 获取选中的所属考试值。
	 * @return 所属考试值。
	 */
	KeyValueType getSelected();
}