package com.examw.test.imports.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.examw.test.imports.model.KeyValue;

/**
 * 试卷结构科目缓存工具。
 * 
 * @author yangyong
 * @since 2014年12月8日
 */
public final class StructureSubjectCacheUtil {
	private static final Map<String, List<KeyValue>> cache = new HashMap<>();
	/**
	 * 
	 * @param structureId
	 * @param subjectIds
	 * @param subjectNames
	 */
	public static final void put(String structureId,String[] subjectIds,String[] subjectNames){
		if(StringUtils.isEmpty(structureId)) throw new RuntimeException("试卷结构ID为空！");
		List<KeyValue> list = new ArrayList<>();
		int len_id = 0, len_name = 0;
		if(subjectIds != null && (len_id = subjectIds.length) > 0 && subjectNames != null && (len_name = subjectNames.length) > 0){
			int len = len_id;
			if(len > len_name) len = len_name;
			list.add(new KeyValue("", "   "));
			for(int i = 0; i < len; i++){
				list.add(new KeyValue(subjectIds[i], subjectNames[i]));
			}
		}
		cache.put(structureId, list);
	}
	/**
	 * 
	 * @param structureId
	 * @return
	 */
	public static final List<KeyValue> load(String structureId){
		if(StringUtils.isEmpty(structureId)) throw new RuntimeException("试卷结构ID为空！");
		return cache.get(structureId);
	}
}