package com.examw.test.imports.service.impl;
 
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map; 
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

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
		ClientUploadItem clientUploadItem = new ClientUploadItem();
		ItemScoreInfo item = this.convertItem(content);
		if(item != null){
			clientUploadItem.setItem(item);
			String serial = item.getSerial();
			if(!StringUtils.isEmpty(serial)){
				clientUploadItem.setOrderNo(new Integer(serial));
			}
		}
		return clientUploadItem;
	}
	/**
	 * 转换为题目。
	 * @param source
	 * @return
	 */
	protected ItemScoreInfo convertItem(String source){
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
	protected ItemScoreInfo convertItem(Map<String, String> map){
		if(map == null || map.size() == 0) return null;
		Set<ItemScoreInfo> children = new TreeSet<ItemScoreInfo>(new Comparator<ItemScoreInfo>(){ @Override public int compare(ItemScoreInfo o1, ItemScoreInfo o2) { return o1.getOrderNo() - o2.getOrderNo(); } });
		Integer children_orderNo = 1;
		ItemScoreInfo item = new ItemScoreInfo();
		for(Map.Entry<String, String> entry : map.entrySet()){
			String order = entry.getKey(), content = entry.getValue();
			if(order.matches("^\\d+")){//内容
				//clientUploadItem.setOrderNo(new Integer(order));
				item.setSerial(order);
				item.setOrderNo(0);
				item.setContent(content.replaceFirst(regex_item_order_replace, "").trim());
			}else if(order.matches(regex_answers_exists)){//答案
				item.setAnswer(content.replaceFirst(regex_answers_exists, "").trim());
			}else if(order.matches(regex_analysis_exists)){//解析
				item.setAnalysis(content.replaceFirst(regex_analysis_exists, "").trim());
			}else {
				children.add(new ItemScoreInfo(content, children_orderNo++));
			}
		}
		if(children.size() > 0) item.setChildren(children);
		return this.convertHander(item);
	}
	/**
	 * 转换整理。
	 * @param source
	 * @return
	 */
	protected ItemScoreInfo convertHander(ItemScoreInfo source){
		if(source != null){
			 if(!StringUtils.isEmpty(source.getAnswer()) && source.getChildren() != null){
				 for(ItemScoreInfo opt : source.getChildren()){
					if(opt.getContent().indexOf(source.getAnswer()) > -1){
						source.setAnswer(opt.getId());
					}
					//opt.setContent(opt.getContent().replaceFirst(regex_opts_exists, "").trim());
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
	 * (non-Javadoc)
	 * @see com.examw.test.imports.service.ItemHtmlPreview#htmlPreview(com.examw.test.imports.model.ClientUploadItem)
	 */
	@Override
	public String htmlPreview(ClientUploadItem source) {
		ItemScoreInfo item = null;
		if(source != null && (item = source.getItem()) != null){
			return this.htmlPreview(item);
		}
		return "";
	}
	/**
	 * 题目html预览。
	 * @param item
	 * @return
	 */
	protected String htmlPreview(ItemScoreInfo item){
		if(item == null) return "";
		StringBuilder html = new StringBuilder();
		html.append(regex_line_separator).append("<br/>");
		if(!StringUtils.isEmpty(item.getSerial())) html.append("<span>").append(item.getSerial()).append(".").append("</span>");
		html.append(item.getContent()).append("<br/>").append(regex_line_separator);
		if(item.getChildren() != null && item.getChildren().size() > 0){
			ItemScoreInfo[] opts = item.getChildren().toArray(new ItemScoreInfo[0]);
			Arrays.sort(opts, new Comparator<ItemScoreInfo>() {@Override public int compare(ItemScoreInfo o1, ItemScoreInfo o2) { return o1.getOrderNo() - o2.getOrderNo(); } });
			for(ItemScoreInfo opt : opts){
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
	protected String renderOptionsHtml(String itemId, ItemScoreInfo opt, String answers){
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