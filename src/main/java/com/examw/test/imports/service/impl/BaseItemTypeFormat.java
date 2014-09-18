package com.examw.test.imports.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.StringUtils;

import com.examw.test.imports.model.ClientUploadItem;
import com.examw.test.imports.service.ItemHtmlPreview;
import com.examw.test.imports.service.ItemTypeFormat;

/**
 * 题型格式化抽象类。
 * 
 * @author yangyong
 * @since 2014年9月11日
 */
public abstract class BaseItemTypeFormat implements ItemTypeFormat,ItemHtmlPreview {
	/**
	 * 换行符。
	 */
	protected static final String regex_line_separator = "\n";
	/**
	 * 查找题序。
	 */
	protected static final String regex_item_find_order = "^(\\d+)[\\.|．]";
	/**
	 * 插入html空行符。
	 */
	protected static final String insert_html_blanklines ="<br/><br/>";
	/**
	 * 按html空行符进行拆分。
	 */
	protected static final String regex_item_html_blanklines_split = "(<br/><br/>)";
	/**
	 * 去掉右边的html换行。
	 */
	protected static final String regex_row_right_newline = "((<br/>)+)$";//
	
	private static final String regex_row_left_trim = "^[　*| *| *|//s*]*";//去掉左边空格（包括全角/半角空格 Tab 制表符等）。
	private static final String regex_row_right_trim = "[　*| *| *|//s*]*$";//去掉右边空格（包括全角/半角空格 Tab 制表符等）。
	/*
	 * 格式化处理入口。
	 * @see com.examw.test.imports.service.ItemTypeFormat#format(javax.swing.text.JTextComponent)
	 */
	@Override
	public String format(String sources) throws Exception {
		 String[] rows = sources.split(regex_line_separator);
		 if(rows != null && rows.length > 0){
			 Map<Integer, String> map = new TreeMap<Integer,String>(new Comparator<Integer>() {@Override public int compare(Integer o1, Integer o2) {return o1 - o2;}});
			 Integer current_order = null, order = null;
			 StringBuilder singleBuilder = new StringBuilder();
			 for(String row : rows){
				 row = this.trimSymbol(row);
				 if(StringUtils.isEmpty(row)) continue;
				 String orderValue =  this.find(regex_item_find_order, row, 1);
				 order = StringUtils.isEmpty(orderValue) ? null : new Integer(orderValue);
				 if(order ==  null){
					 if(singleBuilder.length() > 0) singleBuilder.append(regex_line_separator);
					 singleBuilder.append(row);
				 }else {
					 if(current_order != null){
						 if(map.containsKey(current_order)){
							 String content = map.get(current_order);
							 if(!StringUtils.isEmpty(content))singleBuilder.insert(0, regex_line_separator).insert(0, content);
						 }
						 map.put(current_order, singleBuilder.toString());
					 }
					singleBuilder = new StringBuilder(row);
					current_order = order;
				 }
			 }
			 //处理最后数据。
			 if(current_order != null){
				 if(map.containsKey(current_order)){
					 String content = map.get(current_order);
					 if(!StringUtils.isEmpty(content))singleBuilder.insert(0, regex_line_separator).insert(0, content);
				 }
				 map.put(current_order, singleBuilder.toString());
			 }
			 //按题进行格式化处理
			 StringBuilder builder = new StringBuilder();
			 for(String content : map.values()){
				 if(StringUtils.isEmpty(content)) continue;
				 builder.append(this.itemFormatHandler(content));
				 builder.append(regex_line_separator).append(insert_html_blanklines).append(regex_line_separator);
			 }
			 return builder.toString();
		 }
		 return null;
	}
	/**
	 * 按题格式化处理。
	 * @param item
	 * @return
	 */
	protected abstract String itemFormatHandler(String item);
	/**
	 * 上传json格式处理。
	 */
	@Override
	public String uploadFormatJson(String format,String type) throws Exception {
		if(StringUtils.isEmpty(format)) return format;
		 List<ClientUploadItem> list = new ArrayList<ClientUploadItem>();
		 String[] rows = format.split(regex_item_html_blanklines_split);
		 if(rows != null && rows.length > 0){
			for(String row : rows){
				row = this.trimSymbol(row);
				if(StringUtils.isEmpty(row)) continue;
				ClientUploadItem clientUploadItem = this.convertHander(row);
				if(!StringUtils.isEmpty(type) && clientUploadItem != null && clientUploadItem.getItem() != null){
					clientUploadItem.getItem().setType(new Integer(type));
					list.add(clientUploadItem);
				}
			}
		 }
		if(list.size() > 0){
			Collections.sort(list, new Comparator<ClientUploadItem>() { @Override public int compare(ClientUploadItem o1, ClientUploadItem o2) { return o1.getOrderNo() - o2.getOrderNo(); } });
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(list);
		}
		return "";
	}
	/**
	 * 题目转换。
	 * @param item
	 * @return
	 */
	protected abstract ClientUploadItem convertHander(String item);
	/**
	 * 正则表达式查找数据。
	 * @param regex
	 * @param data
	 * @return
	 */
	protected String find(String regex, String data,Integer index){
		Matcher matcher = Pattern.compile(regex).matcher(data);
		if(matcher.find()){
			return matcher.group(index == null ? 0 : index);
		}
		return null;
	}
	/**
	 * 去除符号。
	 * @param source
	 * @return
	 */
	protected String trimSymbol(String source){
		String result = "";
		if(!StringUtils.isEmpty(source)){
			result = source.replaceAll(regex_row_left_trim, "").replaceAll(regex_row_right_trim, "");
			result = result.replaceAll(regex_row_right_newline, "");
			String find = this.find("^(\\d+[。|．])", result, 1);
			if(!StringUtils.isEmpty(find)){
				result = result.replace(find, find.substring(0, find.length() - 1) + ".");
			}
			result = result.replaceAll("【", "[");
			result = result.replaceAll("】", "]");
		}
		return result.trim();
	}
	/**
	 * 是否存在。
	 * @param regex
	 * @param target
	 * @return
	 */
	protected boolean isExists(String regex,String target){
		if(StringUtils.isEmpty(regex) || StringUtils.isEmpty(target)) return false;
		return !StringUtils.isEmpty(this.find(regex, target, null));
	}
}