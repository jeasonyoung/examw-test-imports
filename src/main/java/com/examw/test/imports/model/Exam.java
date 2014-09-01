package com.examw.test.imports.model;

import java.io.Serializable;

/**
 * 所属考试。
 * 
 * @author yangyong
 * @since 2014年8月30日
 */
public class Exam  implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id,name;
	/**
	 * 获取所属考试ID。
	 * @return 所属考试ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置所属考试ID。
	 * @param id 
	 *	  所属考试ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取所属考试名称。
	 * @return 所属考试名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置所属考试名称。
	 * @param name 
	 *	  所属考试名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
}