package com.examw.test.imports.service.impl;

import org.springframework.util.StringUtils;

import com.examw.test.imports.model.ClientUploadItem;

/**
 * 多选题格式化。
 * 
 * @author yangyong
 * @since 2014年9月4日
 */
public class MultyChoiceFormat extends SingleChoiceFormat {
	private static final String regex_opts_start = "^([A-Z])(\\.)?";//判断选项存在。
	/*
	 * 转换答案处理。
	 * @see com.examw.test.imports.service.impl.SingleChoiceFormat#convertHander(com.examw.test.imports.model.ClientUploadItem)
	 */
	 @Override
	protected ClientUploadItem convertAnswersHander(ClientUploadItem source) {
		 if(source != null && !StringUtils.isEmpty(source.getAnswer())){
			 if(!StringUtils.isEmpty(source.getAnswer()) && source.getChildren() != null){
				 StringBuilder answers = new StringBuilder();
				 for(ClientUploadItem opt : source.getChildren()){
					 if(opt == null || StringUtils.isEmpty(opt.getContent())) continue;
					 String start = this.find(regex_opts_start, opt.getContent(), 1);
					 if(!StringUtils.isEmpty(start) && source.getAnswer().indexOf(start) > -1){
						 if(answers.length() > 0) answers.append(",");
						 answers.append(opt.getId());
					 }
				 }
				 source.setAnswer(answers.toString());
			 }
		 }
		 return source;
	}
	/*
	 * (non-Javadoc)
	 * @see com.examw.test.imports.service.impl.SingleChoiceFormat#renderOptionsHtml(java.lang.String, com.examw.test.imports.model.ClientUploadItem.ItemScoreInfo, java.lang.String)
	 */
	@Override
	protected String renderOptionsHtml(String itemId, ClientUploadItem opt, String answers) {
		if(opt == null) return null;
		StringBuilder optBuilder = new StringBuilder("<label>");
		optBuilder.append("<input type='checkbox' name='").append(itemId).append("' ");
		if(!StringUtils.isEmpty(answers) && answers.indexOf(opt.getId()) > -1){
			optBuilder.append(" checked='checked' ");
		}
		optBuilder.append(" />");
		optBuilder.append(opt.getContent());
		optBuilder.append("</label>");
		return optBuilder.toString();
	}
}