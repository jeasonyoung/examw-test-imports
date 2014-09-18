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
public class ClientUploadItem implements Serializable {
	private static final long serialVersionUID = 1L;
	private String structureId;
	private Integer orderNo;
	private ItemScoreInfo item;
	/**
	 * 构造函数。
	 */
	public ClientUploadItem(){
		this.setItem(new ItemScoreInfo());
	}
	/**
	 * 获取所属试卷结构ID。
	 * @return structureId
	 */
	public String getStructureId() {
		return structureId;
	}

	/**
	 * 设置所属试卷结构ID。
	 * @param structureId 
	 *	  所属试卷结构ID。
	 */
	public void setStructureId(String structureId) {
		this.structureId = structureId;
	}

	/**
	 * 获取试题排序号。
	 * @return orderNo
	 */
	public Integer getOrderNo() {
		return orderNo;
	}

	/**
	 * 设置试题排序号。
	 * @param orderNo 
	 *	  orderNo
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * 获取题目。
	 * @return item
	 */
	public ItemScoreInfo getItem() {
		return item;
	}

	/**
	 * 设置题目。
	 * @param item 
	 *	  题目。
	 */
	public void setItem(ItemScoreInfo item) {
		this.item = item;
	}

	/**
	 * 结构下题目信息。
	 * 
	 * @author yangyong
	 * @since 2014年9月11日
	 */
	@JsonSerialize(include = Inclusion.NON_NULL)
	public static class ItemScoreInfo implements Serializable,Comparable<ItemScoreInfo> {
		private static final long serialVersionUID = 1L;
		private String  pid,id,serial,examId,content,answer,analysis;
		private Integer type,orderNo;
		private Set<ItemScoreInfo> children;
		/**
		 * 构造函数。
		 */
		public ItemScoreInfo(){
			this.setId(UUID.randomUUID().toString());
		}
		/**
		 * 构造函数。
		 * @param content
		 * @param orderNo
		 */
		public ItemScoreInfo(String content,Integer orderNo){
			this();
			this.setContent(content);
			this.setOrderNo(orderNo);
		}
		/**
		 * 获取父ID。
		 * @return pid
		 */
		public String getPid() {
			return pid;
		}
		/**
		 * 设置父ID。
		 * @param pid 
		 *	  父ID。
		 */
		public void setPid(String pid) {
			this.pid = pid;
		}
		/**
		 * 获取ID。
		 * @return id
		 */
		public String getId() {
			return id;
		}
		/**
		 * 设置ID。
		 * @param id 
		 *	  ID。
		 */
		public void setId(String id) {
			this.id = id;
		}
		/**
		 * 获取题序。
		 * @return serial
		 */
		public String getSerial() {
			return serial;
		}
		/**
		 * 设置题序。
		 * @param serial 
		 *	  题序。
		 */
		public void setSerial(String serial) {
			this.serial = serial;
		}
		/**
		 * 获取所属考试ID。
		 * @return examId
		 */
		public String getExamId() {
			return examId;
		}
		/**
		 * 设置所属考试ID。
		 * @param examId 
		 *	  所属考试ID。
		 */
		public void setExamId(String examId) {
			this.examId = examId;
		}
		/**
		 * 获取题目内容。
		 * @return content
		 */
		public String getContent() {
			return content;
		}
		/**
		 * 设置题目内容。
		 * @param content 
		 *	  content
		 */
		public void setContent(String content) {
			this.content = content;
		}
		/**
		 * 获取题目答案。
		 * @return answer
		 */
		public String getAnswer() {
			return answer;
		}
		/**
		 * 设置题目答案。
		 * @param answer 
		 *	  题目答案。
		 */
		public void setAnswer(String answer) {
			this.answer = answer;
		}
		/**
		 * 获取题目解析。
		 * @return analysis
		 */
		public String getAnalysis() {
			return analysis;
		}
		/**
		 * 设置题目解析。
		 * @param analysis 
		 *	  题目解析。
		 */
		public void setAnalysis(String analysis) {
			this.analysis = analysis;
		}
		/**
		 * 获取题型。
		 * @return type
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
		 * @return orderNo
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
		 * 获取子集合。
		 * @return children
		 */
		public Set<ItemScoreInfo> getChildren() {
			return children;
		}
		/**
		 * 设置子集合。
		 * @param children 
		 *	  子集合。
		 */
		public void setChildren(Set<ItemScoreInfo> children) {
			this.children = children;
		}
		/*
		 * 比较。
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo(ItemScoreInfo o) {
			int index = this.getOrderNo() - o.getOrderNo();
			if(index == 0){
				index = this.getContent().compareTo(o.getContent());
			}
			return index;
		}
		
	}
}