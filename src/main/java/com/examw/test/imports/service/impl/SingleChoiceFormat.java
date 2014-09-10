package com.examw.test.imports.service.impl;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.JTextComponent;

import org.springframework.util.StringUtils;

import com.examw.test.imports.service.ItemTypeFormat;

/**
 * 单选题格式化。
 * 
 * @author yangyong
 * @since 2014年9月4日
 */
public class SingleChoiceFormat implements ItemTypeFormat {
	/*
	 * (non-Javadoc)
	 * @see com.examw.test.imports.service.ItemTypeFormat#format(javax.swing.text.JTextComponent)
	 */
	@Override
	public void format(JTextComponent textComponent) {
		 String[] rows = textComponent.getText().split(System.lineSeparator());
		 if(rows != null && rows.length > 0){
			 Map<Integer, String> map = new TreeMap<Integer,String>(new Comparator<Integer>() {
				@Override
				public int compare(Integer o1, Integer o2) {
					return o1 - o2;
				}
			});
			 StringBuilder singleBuilder = new StringBuilder();
			 Integer current_order = null;
			 for(String row : rows){
				 row = trimSymbol(row);
				 if(StringUtils.isEmpty(row)){
					 //singleBuilder.append(System.lineSeparator());
					 continue;
				 }
				 String orderValue =  find("^(\\d+)\\.", row);
				 Integer order = StringUtils.isEmpty(orderValue) ? null : new Integer(orderValue);
				 if(StringUtils.isEmpty(order)){
					 if(singleBuilder.length() > 0) singleBuilder.append(System.lineSeparator());
					 singleBuilder.append(row);
				 }else {
					 if(!StringUtils.isEmpty(current_order)){
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
			 if(!StringUtils.isEmpty(current_order)){
				 if(map.containsKey(current_order)){
					 String content = map.get(current_order);
					 if(!StringUtils.isEmpty(content))singleBuilder.insert(0, System.lineSeparator()).insert(0, content);
				 }
				 map.put(current_order, singleBuilder.toString());
			 }
			 //按题目格式化
			 StringBuilder builder = new StringBuilder();
			 for(Map.Entry<Integer, String> entry : map.entrySet()){
				 builder.append(entry.getValue()).append(System.lineSeparator()).append("<br/>").append(System.lineSeparator());
			 }
			 textComponent.setText(builder.toString());
		 }
	}
	/**
	 * 正则表达式查找数据。
	 * @param regex
	 * @param data
	 * @return
	 */
	protected String find(String regex, String data){
		Matcher matcher = Pattern.compile(regex).matcher(data);
		if(matcher.find()){
			return matcher.group(1);
		}
		return null;
	}
	/**
	 * 去除符号。
	 * @param source
	 * @return
	 */
	public static String trimSymbol(String source){
		String result = "";
		if(!StringUtils.isEmpty(source)){
			result = source.replaceAll("^[　*| *| *|//s*]*", "").replaceAll("[　*| *| *|//s*]*$", "");
			result = result.replaceAll("^<br/>$", "");
		}
		return result.trim();
	}
}