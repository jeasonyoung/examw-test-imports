package com.examw.test.imports.service.impl;
 
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map; 
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.util.StringUtils;

import com.examw.test.imports.model.ClientUploadItem;
/**
 * 单选题格式化。
 * 
 * @author yangyong
 * @since 2014年9月4日
 */
public class SingleChoiceFormat extends BaseItemTypeFormat {
	/**
	 * 判断选项存在。
	 */
	protected static final String regex_opts_exists = "^([A-Z|a-z][\\.|．])";
	/**
	 * 按选项进行分组。
	 */
	protected static final String regex_opts_split = "[A-Z|a-z][\\.|．]";
	/**
	 * 查找选项头字母。
	 */
	protected static final String regex_find_opt_head = "([A-Z|a-z][\\.|．])$";
	/**
	 * 判断答案存在。
	 */
	protected static final String regex_answers_exists = "\\[答案\\]";
	/**
	 * 替换题序。
	 */
	protected static final String regex_item_order_replace = "^(\\d+)[\\.|．]";
	/**
	 * 判断解析存在。
	 */
	protected static final String regex_analysis_exists = "\\[解析\\]";
	/**
	 * 插入html换行符。
	 */
	protected static final String insert_html_newline = "<br/>";
	//private static final String regex_replace_html_newline = "(<br/>)$";//替换html尾部换行符。
	/**
	 * 题目内转换处理。
	 */
	protected static final String regex_item_convert_handler = "^(\\d+|[A-Z]|\\[(答案|解析)\\])(\\.?)";
	/*
	 * (non-Javadoc)
	 * @see com.examw.test.imports.service.impl.BaseItemTypeFormat#itemFormatHandler(java.lang.String)
	 */
	@Override
	protected String itemFormatHandler(String item) {
		if(StringUtils.isEmpty(item)) return item;
		StringBuilder builder = new StringBuilder();
		String[] rows = item.split(regex_line_separator);
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
						builder.append(insert_html_newline).append(regex_line_separator).append(opt);
					}
				}else if (this.isExists(regex_answers_exists, row)) {//答案
					row =  row.replaceFirst(regex_item_order_replace, "");
					row = this.trimSymbol(row);
					builder.append(insert_html_newline).append(regex_line_separator).append(row);
				}else if(this.isExists(regex_analysis_exists,row)){//解析
					builder.append(insert_html_newline).append(regex_line_separator).append(row);
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
		return this.convertItem(content);
	}
	/**
	 * 转换为题目。
	 * @param source
	 * @return
	 */
	protected ClientUploadItem convertItem(String source){
		String [] lines = source.split(regex_line_separator);
		if(lines != null && lines.length > 0){
			Map<String, String> map = new TreeMap<String,String>(new Comparator<String>(){ @Override public int compare(String o1, String o2) { return o1.compareTo(o2); } });
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
			return this.convertItem(map);
		}
		return null;
	}
	/**
	 * 转换处理。
	 * @param map
	 * @return
	 */
	protected ClientUploadItem convertItem(Map<String, String> map){
		if(map == null || map.size() == 0) return null;
		Set<ClientUploadItem> children = new TreeSet<ClientUploadItem>();
		Integer children_orderNo = 1;
		ClientUploadItem item = new ClientUploadItem();
		for(Map.Entry<String, String> entry : map.entrySet()){
			String order = entry.getKey(), content = entry.getValue();
			if(order.matches("^\\d+")){//内容
				item.setOrderNo(Integer.parseInt(order));
				item.setContent(content.replaceFirst(regex_item_order_replace, "").trim());
			}else if(order.matches(regex_answers_exists)){//答案
				item.setAnswer(content.replaceFirst(regex_answers_exists, "").trim());
			}else if(order.matches(regex_analysis_exists)){//解析
				item.setAnalysis(content.replaceFirst(regex_analysis_exists, "").trim());
			}else {
				children.add(new ClientUploadItem(content, children_orderNo++));
			}
		}
		if(children.size() > 0) item.setChildren(children);
		return this.convertAnswersHander(item);
	}
	/**
	 * 转换整理。
	 * @param source
	 * @return
	 */
	protected ClientUploadItem convertAnswersHander(ClientUploadItem source){
		if(source != null && !StringUtils.isEmpty(source.getAnswer())){
			 if(!StringUtils.isEmpty(source.getAnswer()) && source.getChildren() != null){
				 for(ClientUploadItem opt : source.getChildren()){
					if(opt == null || StringUtils.isEmpty(opt.getContent())) continue;
					if(opt.getContent().indexOf(source.getAnswer()) > -1){
						source.setAnswer(opt.getId());
						break;
					}
				 }
			 }
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
				String optOrder = this.find(regex_find_opt_head, sub, 1);
				if(!StringUtils.isEmpty(optOrder)){
					return optOrder.charAt(0) + ".";
				}
			}
		}
		return null;
	}
	/*
	 *  试题Html预览。
	 * @see com.examw.test.imports.service.ItemHtmlPreview#htmlPreview(com.examw.test.imports.model.ClientUploadItem)
	 */
	@Override
	public String htmlPreview(ClientUploadItem item) {
		if(item == null) return "";
		StringBuilder html = new StringBuilder();
		html.append(regex_line_separator).append("<br/>");
		if(!StringUtils.isEmpty(item.getOrderNo())) html.append("<span>").append(item.getOrderNo()).append(".").append("</span>");
		html.append(item.getContent()).append("<br/>").append(regex_line_separator);
		if(item.getChildren() != null && item.getChildren().size() > 0){
			ClientUploadItem[] opts = item.getChildren().toArray(new ClientUploadItem[0]);
			Arrays.sort(opts);
			for(ClientUploadItem opt : opts){
				html.append(this.renderOptionsHtml(item.getId(), opt, item.getAnswer())).append("<br/>").append(regex_line_separator);
			}
		}
		if(!StringUtils.isEmpty(item.getAnalysis())){
			html.append(regex_line_separator).append("<br/>").append(regex_line_separator);
			html.append("[答案解析]");
			html.append(regex_line_separator).append("<br/>").append(regex_line_separator);
			html.append(item.getAnalysis());
		}
		return html.toString();
	}
	/**
	 * 绘制选项。
	 * @param opt
	 * @param answers
	 * @return
	 */
	protected String renderOptionsHtml(String itemId, ClientUploadItem opt, String answers){
		if(opt == null) return null;
		StringBuilder optBuilder = new StringBuilder("<label>");
		optBuilder.append("<input type='radio' name='").append(itemId).append("' ");
		if(!StringUtils.isEmpty(answers) && answers.indexOf(opt.getId()) > -1){
			optBuilder.append(" checked='checked' ");
		}
		optBuilder.append(" />");
		optBuilder.append(opt.getContent());
		optBuilder.append("</label>");
		return optBuilder.toString();
	}
}