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