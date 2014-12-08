package com.examw.test.imports.model;

import java.io.Serializable;
import java.util.List;
/**
 * 客户端试卷结构信息。
 * 
 * @author yangyong
 * @since 2014年9月9日
 */
public class ClientStructureInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,title;
	private Integer type,orderNo;
	private List<ClientStructureInfo> children;
	private String[] subjectId,subjectName;
	/**
	 * 获取结构ID。
	 * @return 结构ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置结构ID。
	 * @param id
	 * 结构ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取结构名称。
	 * @return 结构名称。
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置结构名称。
	 * @param title
	 * 结构名称。
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取题型。
	 * @return 题型。
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置题型。
	 * @param type
	 * 题型。
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取科目ID集合。
	 * @return 科目ID集合。
	 */
	public String[] getSubjectId() {
		return subjectId;
	}
	/**
	 * 设置科目ID集合。
	 * @param subjectId 
	 *	  科目ID集合。
	 */
	public void setSubjectId(String[] subjectId) {
		this.subjectId = subjectId;
	}
	/**
	 * 获取科目名称集合。
	 * @return 科目名称集合。
	 */
	public String[] getSubjectName() {
		return subjectName;
	}
	/**
	 * 设置科目名称集合。
	 * @param subjectName 
	 *	  科目名称集合。
	 */
	public void setSubjectName(String[] subjectName) {
		this.subjectName = subjectName;
	}
	/**
	 * 获取排序号。
	 * @return 排序号。
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置排序号。
	 * @param orderNo
	 * 排序号。
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * 获取子结构集合。
	 * @return 子结构集合。
	 */
	public List<ClientStructureInfo> getChildren() {
		return children;
	}
	/**
	 * 设置子结构集合。
	 * @param children 
	 *	  子结构集合。
	 */
	public void setChildren(List<ClientStructureInfo> children) {
		this.children = children;
	}
}