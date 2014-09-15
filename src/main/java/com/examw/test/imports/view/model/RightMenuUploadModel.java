package com.examw.test.imports.view.model;

import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

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
	}
	/**
	 * 设置所属试卷。
	 * @param paperOPService 
	 *	  paperOPService
	 */
	public void setPaperOPService(PaperOPService paperOPService) {
		this.paperOPService = paperOPService;
	}
	/**
	 * 设置所属试卷结构。
	 * @param paperStructureOPService 
	 *	  所属试卷结构。
	 */
	public void setPaperStructureOPService(PaperStructureOPService paperStructureOPService) {
		this.paperStructureOPService = paperStructureOPService;
	}
	/**
	 * 设置预览接口。
	 * @param preview 
	 *	  预览接口。
	 */
	public void setPreview(UploadPreview preview) {
		this.preview = preview;
	}
	/**
	 * 设置警告信息标题。
	 * @param warningTitle 
	 *	  warningTitle
	 */
	public void setWarningTitle(String warningTitle) {
		this.warningTitle = warningTitle;
	}
	/**
	 * 设置未选择所属试卷消息。
	 * @param noSelectPaperMessager 
	 *	  未选择所属试卷消息。
	 */
	public void setNoSelectPaperMessager(String noSelectPaperMessager) {
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