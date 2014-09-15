package com.examw.test.imports.service;
/**
 * Tab内容广播接口。
 * 
 * @author yangyong
 * @since 2014年9月12日
 */
public interface TabbedContentBroadcast {
	/**
	 *  广播内容转换
	 * @param index
	 * @param content
	 * @return
	 */
	String contentBroadcat(Integer index,String content);
}