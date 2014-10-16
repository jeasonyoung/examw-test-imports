package com.examw.test.imports.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.StringUtils;

import com.examw.test.imports.model.ClientUploadItem;

/**
 * 共享提干格式化。
 * 
 * @author yangyong
 * @since 2014年9月4日
 */
public class ShareTitleFormat extends UncertainChoiceFormat {
	private static final String regex_top_find_order = "^[\\(|（]([一|二|三|四|五|六|七|八|九|十]+)[\\)|）]$";//查找题序。
	private static final String number_cn = "零一二三四五六七八九十";
	/*
	 * 格式化处理入口。
	 * @see com.examw.test.imports.service.impl.BaseItemTypeFormat#format(javax.swing.text.JTextComponent)
	 */
	@Override
	public String format(String sources) throws Exception {
		ShareTitleItems shareTitleItems = this.convertShareTitleItems(sources);
		return (shareTitleItems == null) ? null : shareTitleItems.toString();
	}
	//转换为对象。
	private ShareTitleItems convertShareTitleItems(String sources){
		String[] rows = sources.split(regex_line_separator);
		 if(rows != null && rows.length > 0){
			 ShareTitleItems shareTitleItems = new ShareTitleItems();
			 String current_title_order = null, title_order = null;
			 StringBuilder builder = new StringBuilder();
			 for(String row : rows){
				 row = this.trimSymbol(row);
				 if(StringUtils.isEmpty(row)) continue;
				 title_order = this.find(regex_top_find_order, row, 1);
				 if(StringUtils.isEmpty(title_order)){
					 if(builder.length() > 0) builder.append(regex_line_separator);
					 builder.append(row);
				 }else {
					if(!StringUtils.isEmpty(current_title_order)){
						shareTitleItems.parse(current_title_order, builder.toString());
					}
					builder = new StringBuilder(row);
					current_title_order = title_order;
				}
			 }
			//处理最后数据。
			 if(!StringUtils.isEmpty(current_title_order) && builder != null && builder.length() > 0){
					shareTitleItems.parse(current_title_order, builder.toString());
			 }
			//格式化输出
			return shareTitleItems;
		 }
		 return null;
	}
	/*
	 * Json格式。
	 * @see com.examw.test.imports.service.impl.BaseItemTypeFormat#uploadFormatJson(java.lang.String, java.lang.String)
	 */
	@Override
	public String uploadFormatJson(String format, String type) throws Exception {
		if(StringUtils.isEmpty(type)) return null;
		ShareTitleItems shareTitleItems = this.convertShareTitleItems(format);
		if(shareTitleItems != null){
			List<ClientUploadItem> list = shareTitleItems.toClientUploadItems();
			if(list.size() > 0){
				for(ClientUploadItem clientUploadItem : list){
					if(clientUploadItem == null) continue;
					clientUploadItem.setType(new Integer(type));
				}
				Collections.sort(list, new Comparator<ClientUploadItem>() { 
					@Override 
					public int compare(ClientUploadItem o1, ClientUploadItem o2) {
						return o1.getOrderNo() - o2.getOrderNo(); 
					} 
				});
				ObjectMapper mapper = new ObjectMapper();
				return mapper.writeValueAsString(list);
			}
		}
		return "";
	}
	/*
	 * html 预览。
	 * @see com.examw.test.imports.service.impl.SingleChoiceFormat#htmlPreview(com.examw.test.imports.model.ClientUploadItem)
	 */
	@Override
	public String htmlPreview(ClientUploadItem source) {
		if(source == null) return null;
		StringBuilder html = new StringBuilder();
		html.append(regex_line_separator).append("<br/>");
		if(source != null){
			html.append(source.getContent());
			if(source.getChildren() != null){
				ClientUploadItem[] items = source.getChildren().toArray(new ClientUploadItem[0]);
				Arrays.sort(items);
				for(ClientUploadItem item : items){
					 if(item == null) continue;
					 html.append(super.htmlPreview(item));
					 html.append(regex_line_separator).append("<br/>");
				}
			}
		}
		return html.toString();
	}
	/**
	 * 共享提干题集合。
	 * 
	 * @author yangyong
	 * @since 2014年9月18日
	 */
	class ShareTitleItems implements Serializable{
		private static final long serialVersionUID = 1L;
		private Map<String, ShareTitleItem> map;
		/**
		 * 构造函数。
		 */
		public ShareTitleItems(){
			this.map = new TreeMap<>(new Comparator<String>(){
				@Override
				public int compare(String o1, String o2) {
					Integer o1_index = number_cn.indexOf(o1), o2_index = number_cn.indexOf(o2);
					if(o1_index > -1 || o2_index > -1){
						return o1_index - o2_index;
					}
					return o1.compareTo(o2);
				}
			});
		}
		/**
		 * 解析行数据。
		 * @param titleOrder
		 * @param content
		 */
		public void parse(String titleOrder, String content){
			if(!StringUtils.isEmpty(content)){
				String[] rows = content.split(regex_line_separator);
				Integer current_order = null, order =  null;
				StringBuilder builder = new StringBuilder();
				for(String row : rows){
					row = ShareTitleFormat.this.trimSymbol(row);
					if(StringUtils.isEmpty(row)) continue;
					String orderValue = ShareTitleFormat.this.find(regex_item_find_order, row, 1);
					order = StringUtils.isEmpty(orderValue) ? null : new Integer(orderValue);
					if(order == null){
						if(builder.length() > 0) builder.append(regex_line_separator);
						builder.append(row);
					}else {
						if(current_order == null && !StringUtils.isEmpty(titleOrder)){
							this.addTitleContent(titleOrder, builder.toString());
						}else {
							this.addItemContent(titleOrder, current_order, builder.toString());
						}
						builder = new StringBuilder(row);
						current_order = order;
					}
				}
				//最后数据处理。
				if(current_order == null && !StringUtils.isEmpty(titleOrder)){
					this.addTitleContent(titleOrder, builder.toString());
				}else {
					this.addItemContent(titleOrder, current_order, builder.toString());
				}
			}
		}
		//添加题目内容。
		private void addTitleContent(String titleOrder,String content){
			if(!StringUtils.isEmpty(titleOrder)){
				ShareTitleItem shareTitleItem = this.map.get(titleOrder);
				if(shareTitleItem == null){
					shareTitleItem = new ShareTitleItem(titleOrder, content);
				}else {
					 StringBuilder builder = new StringBuilder();
					 String text  = null;
					 if(!StringUtils.isEmpty(text = shareTitleItem.getTitleContent())){
						 builder.append(text).append(regex_line_separator);
					 }
					 builder.append(content);
					 shareTitleItem.setTitleContent(builder.toString());
				}
				this.map.put(titleOrder, shareTitleItem);
			}
		}
		//添加子题目。
		private void addItemContent(String titleOrder, Integer currentOrder, String content){
			if(!StringUtils.isEmpty(titleOrder) && currentOrder != null){
				ShareTitleItem shareTitleItem = this.loadShareTitleItem(currentOrder);
				if(shareTitleItem == null){
					shareTitleItem = this.map.get(titleOrder);
				}
				if(shareTitleItem != null){
					StringBuilder builder = new StringBuilder();
					String text = null;
					if(!StringUtils.isEmpty(text = shareTitleItem.getItems().get(currentOrder))){
						builder.append(text).append(regex_line_separator);
					}
					builder.append(content);
					shareTitleItem.getItems().put(currentOrder, builder.toString());
				}
			}
		}
		//加载共享提干。
		private ShareTitleItem loadShareTitleItem(Integer order){
			if(order == null || this.map == null || this.map.size() == 0) return null;
			for(ShareTitleItem item : this.map.values()){
				if(item == null) continue;
				if(item.isExistsItem(order)){
					return item;
				}
			}
			return null;
		}
		/**
		 * 
		 * @return
		 */
		public List<ClientUploadItem> toClientUploadItems(){
			if(this.map == null || this.map.size() == 0) return null;
			List<ClientUploadItem> clientUploadItems = new ArrayList<>();
			for(ShareTitleItem item : this.map.values()){
				if(item == null) continue;
				ClientUploadItem clientUploadItem = item.buildClientUploadItem();
				if(clientUploadItem != null) clientUploadItems.add(clientUploadItem);
			}
			return clientUploadItems;
		}
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			if(this.map != null){
				for(ShareTitleItem item : this.map.values()){
					if(item == null) continue;
					builder.append(item);
					builder.append(regex_line_separator).append(insert_html_blanklines).append(insert_html_blanklines).append(regex_line_separator);
				}
			}
			return builder.toString();
		}
	}
	/**
	 * 共享提干题。
	 * 
	 * @author yangyong
	 * @since 2014年9月18日
	 */
	class ShareTitleItem implements Serializable {
		private static final long serialVersionUID = 1L;
		private String titleOrder,titleContent;
		private Map<Integer,String> items;
		private static final int default_item_type = 3;//不定向选择题型。
		/**
		 * 构造函数。
		 */
		public ShareTitleItem(){
			this.items = new TreeMap<>(new Comparator<Integer>(){
				@Override
				public int compare(Integer o1, Integer o2) {
					return o1 - o2;
				}});
		}
		/**
		 * 构造函数。
		 * @param titleOrder
		 * @param titleContent
		 */
		public ShareTitleItem(String titleOrder,String titleContent){
			this();
			this.setTitleOrder(titleOrder);
			this.setTitleContent(titleContent);
		}
		/**
		 * 添加题目。
		 * @param order
		 * @param row
		 */
		public void addItem(Integer order,String row){
			 if(order != null && !StringUtils.isEmpty(row)){
				 StringBuilder builder = new StringBuilder();
				 if(this.items.containsKey(order)){
					 String content = this.items.get(order);
					 if(!StringUtils.isEmpty(content)){
						 builder.append(content).append(regex_line_separator);
					 }
				 }
				 builder.append(row);
				 this.items.put(order, builder.toString());
			 }
		}
		/**
		 * 是否存在子题。
		 * @param order
		 * @return
		 */
		public boolean isExistsItem(Integer order){
			if(order == null || this.items == null || this.items.size() == 0) return false;
			return this.items.containsKey(order);
		}
		/**
		 * 获取提干序号。
		 * @return 提干序号。
		 */
		public String getTitleOrder() {
			return titleOrder;
		}
		/**
		 * 设置提干序号。
		 * @param titleOrder 
		 *	  提干序号。
		 */
		public void setTitleOrder(String titleOrder) {
			this.titleOrder = titleOrder;
		}
		/**
		 * 获取提干内容。
		 * @return titleContent
		 */
		public String getTitleContent() {
			return titleContent;
		}
		/**
		 * 设置提干内容。
		 * @param titleContent 
		 *	  提干内容。
		 */
		public void setTitleContent(String titleContent) {
			this.titleContent = titleContent;
		}
		/**
		 * 获取子题集合。
		 * @return 子题集合。
		 */
		public Map<Integer, String> getItems() {
			return items;
		}
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append(this.getTitleContent()).append(insert_html_newline).append(regex_line_separator);
			if(this.getItems() != null){
				for(String content : this.getItems().values()){
					String text = ShareTitleFormat.super.itemFormatHandler(content);
					if(!StringUtils.isEmpty(text)){
						builder.append(text);
						builder.append(regex_line_separator).append(insert_html_blanklines).append(regex_line_separator);
					}
				}
			}
			return builder.toString();
		}
		/**
		 * 构造成题目对象。
		 * @return
		 */
		public ClientUploadItem buildClientUploadItem(){
			ClientUploadItem clientUploadItem = new ClientUploadItem();
			int index = number_cn.indexOf(this.titleOrder);
			clientUploadItem.setOrderNo(index == -1 ? 0 : index);
			clientUploadItem.setContent(this.titleContent);
			if(this.getItems() != null && this.getItems().size() > 0){
				Set<ClientUploadItem> children = new TreeSet<ClientUploadItem>();
				for(String content : this.getItems().values()){
					if(StringUtils.isEmpty(content)) continue;
					ClientUploadItem item = ShareTitleFormat.super.convertItem(content);
					if(item == null) continue;
					item.setType(default_item_type); 
					children.add(item);
				}
				clientUploadItem.setChildren(children);
			}
			return clientUploadItem;
		}
	}
}