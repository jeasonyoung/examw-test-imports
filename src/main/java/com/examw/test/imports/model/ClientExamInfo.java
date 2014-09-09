package com.examw.test.imports.model;

import java.io.Serializable;
/**
 * 客户端考试信息。
 * 
 * @author yangyong
 * @since 2014年9月9日
 */
public class ClientExamInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name;
	/**
	 * 获取考试ID。
	 * @return 考试ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置考试ID。
	 * @param id
	 * 考试ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取考试名称。
	 * @return 考试名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置考试名称。
	 * @param name
	 * 考试名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
}