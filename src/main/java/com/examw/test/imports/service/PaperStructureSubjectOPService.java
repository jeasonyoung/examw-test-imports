package com.examw.test.imports.service;

import com.examw.test.imports.model.KeyValue;

/**
 * 试卷结构科目操作服务接口。
 * 
 * @author yangyong
 * @since 2014年12月8日
 */
public interface PaperStructureSubjectOPService extends OPService {
	/**
	 * 选择的值。
	 * @return
	 */
	KeyValue getSelected();
}