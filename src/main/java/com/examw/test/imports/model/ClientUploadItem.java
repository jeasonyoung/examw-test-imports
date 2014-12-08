package com.examw.test.imports.model;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 客户端上传题目信息。
 * 
 * @author yangyong
 * @since 2014年9月11日
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ClientUploadItem implements Serializable,Comparable<ClientUploadItem> {
	private static final long serialVersionUID = 1L;
	private String  pid,id,content,answer,analysis,subjectId;
	private Integer type,orderNo;
	private Set<ClientUploadItem> children;
	/**
	 * 构造函数。
	 */
	public ClientUploadItem(){
		this.setId(UUID.randomUUID().toString());
	}
	/**
	 * 构造函数。
	 * @param content
	 * 试题内容。
	 * @param orderNo
	 * 排序号。
	 */
	public ClientUploadItem(String content, Integer orderNo){
		this();
		this.setContent(content);
		this.setOrderNo(orderNo);
	}
	/**
	 * 获取上级试题ID。
	 * @return 上级试题ID。
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * 设置上级试题ID。
	 * @param pid 
	 *	  上级试题ID。
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**
	 * 获取试题ID。
	 * @return 试题ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置试题ID。
	 * @param id 
	 *	  试题ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取试题内容。
	 * @return 试题内容。
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置试题内容。
	 * @param content 
	 *	  试题内容。
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取试题答案。
	 * @return 试题内容。
	 */
	public String getAnswer() {
		return answer;
	}
	/**
	 * 设置试题内容。
	 * @param answer 
	 *	  试题内容。
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	/**
	 * 获取答案解析。
	 * @return 答案解析。
	 */
	public String getAnalysis() {
		return analysis;
	}
	/**
	 * 设置答案解析。
	 * @param analysis 
	 *	  答案解析。
	 */
	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}
	/**
	 * 获取所属科目ID。
	 * @return 所属科目ID。
	 */
	public String getSubjectId() {
		return subjectId;
	}
	/**
	 * 设置所属科目ID。
	 * @param subjectId 
	 *	  所属科目ID。
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
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
	 *	  题型。
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
	 *	  排序号。
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * 获取子题集合。
	 * @return 子题集合。
	 */
	public Set<ClientUploadItem> getChildren() {
		return children;
	}
	/**
	 * 设置子题集合。
	 * @param children 
	 *	  子题集合。
	 */
	public void setChildren(Set<ClientUploadItem> children) {
		this.children = children;
	}
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ClientUploadItem o) {
		int index = 0;
		if(this == o) return index;
		index = this.getOrderNo() - o.getOrderNo();
		if(index == 0){
			index = this.getType() - o.getType();
			if(index == 0){
				index = this.getId().compareToIgnoreCase(o.getId());
			}
		}
		return index;
	}
}