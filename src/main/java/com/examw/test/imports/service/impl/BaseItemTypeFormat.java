package com.examw.test.imports.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.JTextComponent;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.StringUtils;

import com.examw.test.imports.model.ClientUploadItem;
import com.examw.test.imports.service.ItemTypeFormat;

/**
 * 题型格式化抽象类。
 * 
 * @author yangyong
 * @since 2014年9月11日
 */
public abstract class BaseItemTypeFormat implements ItemTypeFormat {
	/*
	 * 格式化处理入口。
	 * @see com.examw.test.imports.service.ItemTypeFormat#format(javax.swing.text.JTextComponent)
	 */
	@Override
	public void format(JTextComponent textComponent) throws Exception {
		 String[] rows = textComponent.getText().split(System.lineSeparator());
		 if(rows != null && rows.length > 0){
			 Map<Integer, String> map = new TreeMap<Integer,String>(new Comparator<Integer>() {@Override public int compare(Integer o1, Integer o2) {return o1 - o2;}});
			 StringBuilder singleBuilder = new StringBuilder();
			 Integer current_order = null, order = null;
			 for(String row : rows){
				 row = this.trimSymbol(row);
				 if(StringUtils.isEmpty(row)) continue;
				 String orderValue =  this.find("^(\\d+)\\.", row, 1);
				 order = StringUtils.isEmpty(orderValue) ? null : new Integer(orderValue);
				 if(order ==  null){
					 if(singleBuilder.length() > 0) singleBuilder.append(System.lineSeparator());
					 singleBuilder.append(row);
				 }else {
					 if(current_order != null){
						 if(map.containsKey(current_order)){
							 String content = map.get(current_order);
							 if(!StringUtils.isEmpty(content))singleBuilder.insert(0, System.lineSeparator()).insert(0, content);
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
					 if(!StringUtils.isEmpty(content))singleBuilder.insert(0, System.lineSeparator()).insert(0, content);
				 }
				 map.put(current_order, singleBuilder.toString());
			 }
			 //按题进行格式化处理
			 StringBuilder builder = new StringBuilder();
			 for(String content : map.values()){
				 if(StringUtils.isEmpty(content)) continue;
				 builder.append(this.itemFormatHandler(content));
				 builder.append(System.lineSeparator()).append("<br/><br/>").append(System.lineSeparator());
			 }
			 textComponent.setText(builder.toString());
		 }
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
		 String[] rows = format.split("(<br/><br/>)");
		 if(rows != null && rows.length > 0){
			for(String row : rows){
				row = this.trimSymbol(row);
				if(StringUtils.isEmpty(row)) continue;
				ClientUploadItem clientUploadItem = this.convertHander(row);
				if(!StringUtils.isEmpty(type) && clientUploadItem != null && clientUploadItem.getItem() != null){
					clientUploadItem.getItem().setType(new Integer(type));
				}
				list.add(clientUploadItem);
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
			result = source.replaceAll("^[　*| *| *|//s*]*", "").replaceAll("[　*| *| *|//s*]*$", "");
			result = result.replaceAll("^(<br/>)+$", "");
			String find = this.find("^(\\d+。)", result, 1);
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