package com.examw.test.imports.service;

import java.util.List;
import com.examw.test.imports.model.KeyValue;
/**
 * 客户端题型服务。
 * 
 * @author yangyong
 * @since 2014年9月2日
 */
public interface ItemTypeRemoteDataService {
	/**
	 * 下载题型数据。
	 * @return
	 */
	List<KeyValue> loadItemTypes();
}