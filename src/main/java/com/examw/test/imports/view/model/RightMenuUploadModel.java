package com.examw.test.imports.view.model;

import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.StringUtils;

import com.examw.test.imports.model.ClientUploadItem;
import com.examw.test.imports.model.KeyValueType;
import com.examw.test.imports.service.PaperOPService;
import com.examw.test.imports.service.PaperStructureOPService;
import com.examw.test.imports.service.UploadPreview;

/**
 * 右键上传菜单处理。
 * 
 * @author yangyong
 * @since 2014年9月12日
 */
public class RightMenuUploadModel extends RightMenuBaseModel {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(RightMenuUploadModel.class);
	private String warningTitle,noSelectPaperMessager,noSelectPaperStructureMessager;
	private PaperOPService paperOPService;
	private PaperStructureOPService paperStructureOPService;
	private UploadPreview preview;
	/**
	 * 构造函数。
	 * @param textComponent
	 */
	public RightMenuUploadModel(JTextComponent textComponent) {
		super(textComponent);
		if(logger.isDebugEnabled()) logger.debug("构造函数...");
	}
	/**
	 * 设置所属试卷。
	 * @param paperOPService 
	 *	  所属试卷。
	 */
	public void setPaperOPService(PaperOPService paperOPService) {
		if(logger.isDebugEnabled()) logger.debug("设置所属试卷...");
		this.paperOPService = paperOPService;
	}
	/**
	 * 设置所属试卷结构。
	 * @param paperStructureOPService 
	 *	  所属试卷结构。
	 */
	public void setPaperStructureOPService(PaperStructureOPService paperStructureOPService) {
		if(logger.isDebugEnabled()) logger.debug("设置所属试卷结构...");
		this.paperStructureOPService = paperStructureOPService;
	}
	/**
	 * 设置预览接口。
	 * @param preview 
	 *	  预览接口。
	 */
	public void setPreview(UploadPreview preview) {
		if(logger.isDebugEnabled()) logger.debug("设置预览接口...");
		this.preview = preview;
	}
	/**
	 * 设置警告信息标题。
	 * @param warningTitle 
	 *	  警告信息标题。
	 */
	public void setWarningTitle(String warningTitle) {
		if(logger.isDebugEnabled()) logger.debug("设置警告信息标题:" + warningTitle);
		this.warningTitle = warningTitle;
	}
	/**
	 * 设置未选择所属试卷消息。
	 * @param noSelectPaperMessager 
	 *	  未选择所属试卷消息。
	 */
	public void setNoSelectPaperMessager(String noSelectPaperMessager) {
		if(logger.isDebugEnabled()) logger.debug("设置未选择所属试卷消息:" + noSelectPaperMessager);
		this.noSelectPaperMessager = noSelectPaperMessager;
	}
	/**
	 * 设置未选择所属试卷结构消息。
	 * @param noSelectPaperStructureMessager 
	 *	  未选择所属试卷结构消息。
	 */
	public void setNoSelectPaperStructureMessager(String noSelectPaperStructureMessager) {
		this.noSelectPaperStructureMessager = noSelectPaperStructureMessager;
	}
	/*
	 * 事件处理。
	 * @see com.examw.test.imports.view.model.RightMenuBaseModel#handler()
	 */
	@Override
	protected void handler() {
		String paperId = this.paperOPService == null ? null : this.paperOPService.getSelected();
		if(StringUtils.isEmpty(paperId)){
			JOptionPane.showMessageDialog(this.textComponent, this.noSelectPaperMessager, this.warningTitle, JOptionPane.WARNING_MESSAGE);
			return;
		}
		KeyValueType keyValueType = this.paperStructureOPService == null ? null :  this.paperStructureOPService.getSelected();
		String structureId = keyValueType == null ? null : keyValueType.getKey();
		if(StringUtils.isEmpty(structureId)){
			JOptionPane.showMessageDialog(this.textComponent, this.noSelectPaperStructureMessager, this.warningTitle, JOptionPane.WARNING_MESSAGE);
			return;
		}
		String data = this.textComponent.getText();
		if(StringUtils.isEmpty(data)) return;
		ClientUploadItem[] clientUploadItems = null;
		try {
			  clientUploadItems =  new ObjectMapper().readValue(data, ClientUploadItem[].class);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this.textComponent, e.getMessage(), this.warningTitle, JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		if(this.preview != null && clientUploadItems != null && clientUploadItems.length > 0){
			 this.preview.showDialog(paperId, structureId, clientUploadItems);
		}
	}
}