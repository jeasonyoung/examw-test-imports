package com.examw.test.imports.service.impl;
 
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map; 
import java.util.Set;

import org.springframework.util.StringUtils;

import com.examw.test.imports.model.ClientUploadItem;
import com.examw.test.imports.model.ClientUploadItem.ItemScoreInfo;

/**
 * 单选题格式化。
 * 
 * @author yangyong
 * @since 2014年9月4日
 */
public class SingleChoiceFormat extends BaseItemTypeFormat {
	private static final String regex_opts_exists = "^([A-Z]\\.)";//判断选项存在。
	private static final String regex_opts_split = "[A-Z]\\.";//按选项进行分组。
	private static final String regex_answers_exists = "\\[答案\\]";//判断答案存在。
	private static final String regex_item_order_replace = "^(\\d+)\\.";//替换题序。
	private static final String regex_analysis_exists = "\\[解析\\]";//判断解析存在。
	private static final String insert_html_newline = "<br/>";//插入html换行符。
	private static final String regex_replace_html_newline = "(<br/>)$";//替换html尾部换行符。
	private static final String regex_find_opt_head = "([A-Z]\\.)$";//查找选项头字母。
	private static final String regex_item_convert_handler = "^(\\d+|[A-Z]|\\[(答案|解析)\\])(\\.?)";//题目内转换处理。
	/*
	 * (non-Javadoc)
	 * @see com.examw.test.imports.service.impl.BaseItemTypeFormat#itemFormatHandler(java.lang.String)
	 */
	@Override
	protected String itemFormatHandler(String item) {
		if(StringUtils.isEmpty(item)) return item;
		StringBuilder builder = new StringBuilder();
		String[] rows = item.split(System.lineSeparator());
		if(rows != null && rows.length > 0){
			for(String row : rows){
				row = this.trimSymbol(row);
				if(StringUtils.isEmpty(row)) continue;
				if(this.isExists(regex_opts_exists,row)){//选项
					String[] opts = row.split(regex_opts_split);
					for(String opt : opts){
						opt = this.trimSymbol(opt);
						if(StringUtils.isEmpty(opt)) continue;
						opt = this.findOptHead(opt, row) + opt;
						builder.append(insert_html_newline).append(System.lineSeparator()).append(opt);
					}
				}else if (this.isExists(regex_answers_exists, row)) {//答案
					row =  row.replaceFirst(regex_item_order_replace, "");
					row = this.trimSymbol(row);
					builder.append(insert_html_newline).append(System.lineSeparator()).append(row);
				}else if(this.isExists(regex_analysis_exists,row)){//解析
					builder.append(insert_html_newline).append(System.lineSeparator()).append(row);
				}else {//题目内容
					builder.append(row);
				}
			}
		}
		return builder.toString();
	}
	/*
	 * (non-Javadoc)
	 * @see com.examw.test.imports.service.impl.BaseItemTypeFormat#convert(java.lang.String)
	 */
	@Override
	protected ClientUploadItem convertHander(String content) {
		String [] lines = content.split(System.lineSeparator());
		if(lines != null && lines.length > 0){
			Map<String, String> map =  new HashMap<>();
			StringBuilder builder = new StringBuilder();
			String current_order = null, order = null;
			for(String line : lines){
				line = this.trimSymbol(line);
				if(StringUtils.isEmpty(line)) continue;
				order = this.find(regex_item_convert_handler, line, 1);
				if(StringUtils.isEmpty(order)){
					builder.append(line);
				}else {
					if(!StringUtils.isEmpty(current_order)){
						 if(map.containsKey(current_order)){
							 String data = map.get(current_order);
							 if(!StringUtils.isEmpty(data))builder.insert(0, data);
						 }
						 map.put(current_order, builder.toString());
					}
					builder = new StringBuilder(line);
					current_order = order;
				}
			}
			//最后数据处理
			if(!StringUtils.isEmpty(current_order)){
				 if(map.containsKey(current_order)){
					 String data = map.get(current_order);
					 if(!StringUtils.isEmpty(data))builder.insert(0, data);
				 }
				 map.put(current_order, builder.toString());
			}
			return this.convertHander(map);
		}
		return null;
	}
	/**
	 * 转换处理。
	 * @param map
	 * @return
	 */
	protected ClientUploadItem convertHander(Map<String, String> map){
		if(map == null || map.size() == 0) return null;
		ClientUploadItem clientUploadItem = new ClientUploadItem();
		Set<ItemScoreInfo> children = new HashSet<>();
		Integer children_orderNo = 1;
		for(Map.Entry<String, String> entry : map.entrySet()){
			String order = entry.getKey(), content = entry.getValue();
			if(order.matches("^\\d+")){//内容
				clientUploadItem.setOrderNo(new Integer(order));
				clientUploadItem.getItem().setOrderNo(0);
				clientUploadItem.getItem().setContent(content.replaceFirst(regex_item_order_replace, "").trim());
			}else if(order.matches(regex_answers_exists)){//答案
				clientUploadItem.getItem().setAnswer(content.replaceFirst(regex_answers_exists, "").trim());
			}else if(order.matches(regex_analysis_exists)){//解析
				clientUploadItem.getItem().setAnalysis(content.replaceFirst(regex_analysis_exists, "").trim());
			}else {
				children.add(new ItemScoreInfo(content, children_orderNo++));
			}
		}
		if(children.size() > 0) clientUploadItem.getItem().setChildren(children);
		return clientUploadItem;
	}
	/*
	 * (non-Javadoc)
	 * @see com.examw.test.imports.service.impl.BaseItemTypeFormat#trimSymbol(java.lang.String)
	 */
	@Override
	protected String trimSymbol(String source) {
		source = super.trimSymbol(source);
		if(!StringUtils.isEmpty(source)){
			source = source.replaceFirst(regex_replace_html_newline, "").trim();
		}
		return source;
	}
	/**
	 * 查找选项头
	 * @param opt
	 * @param line
	 * @return
	 */
	protected String findOptHead(String opt, String line){
		if(StringUtils.isEmpty(opt) || StringUtils.isEmpty(line)) return null;
		int index = line.indexOf(opt);
		if(index > - 1){
			String sub = line.substring(0, index);
			if(!StringUtils.isEmpty(sub)){
				return this.find(regex_find_opt_head, sub, 1);
			}
		}
		return null;
	}
}