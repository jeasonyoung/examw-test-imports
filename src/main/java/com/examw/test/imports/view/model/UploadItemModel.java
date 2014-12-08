package com.examw.test.imports.view.model;

import java.awt.event.ActionEvent;

import javax.swing.DefaultButtonModel;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.text.Document;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.StringUtils;

import com.examw.test.imports.model.ClientUploadItem;
import com.examw.test.imports.model.KeyValue;
import com.examw.test.imports.service.ItemUploadRemoteService;
import com.examw.test.imports.service.PaperStructureSubjectOPService;
import com.examw.test.imports.service.UploadPreview;

/**
 * 试题上传。
 * 
 * @author yangyong
 * @since 2014年9月15日
 */
public class UploadItemModel extends DefaultButtonModel {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(UploadItemModel.class);
	private UploadPreview uploadPreview;
	private Document document;
	private ItemUploadRemoteService itemUploadRemoteService;
	private ObjectMapper mapper = null;
	private PaperStructureSubjectOPService paperStructureSubjectOPService;
	/**
	 * 构造函数。
	 * @param uploadPreview
	 * @param document
	 * @param itemUploadRemoteService
	 */
	public UploadItemModel(UploadPreview uploadPreview,Document document, ItemUploadRemoteService itemUploadRemoteService){
		super();
		if(logger.isDebugEnabled()) logger.debug("构造函数...");
		this.mapper = new ObjectMapper();
		this.uploadPreview = uploadPreview;
		this.document = document;
		this.itemUploadRemoteService = itemUploadRemoteService;
	}
	/**
	 * 设置试卷结构科目操作服务接口。
	 * @param paperStructureSubjectOPService 
	 *	  试卷结构科目操作服务接口。
	 */
	public void setPaperStructureSubjectOPService(PaperStructureSubjectOPService paperStructureSubjectOPService) {
		if(logger.isDebugEnabled()) logger.debug("试卷结构科目操作服务接口...");
		this.paperStructureSubjectOPService = paperStructureSubjectOPService;
	}
	/*
	 * 事件处理。
	 * @see javax.swing.DefaultButtonModel#fireActionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	protected void fireActionPerformed(ActionEvent e) {
		super.fireActionPerformed(e);
		if(this.uploadPreview != null && this.document != null && this.itemUploadRemoteService != null){
			try {
				String content = this.document.getText(0, this.document.getLength());
				if(StringUtils.isEmpty(content)){
					this.showMessageDialog("上传试题源代码为空!");
					return;
				}
				ClientUploadItem clientUploadItem = this.mapper.readValue(content, ClientUploadItem.class);
				if(clientUploadItem == null){
					this.showMessageDialog("上传试题源代码格式不正确!");
					return;
				}
				if(this.paperStructureSubjectOPService != null){
					KeyValue keyValue = this.paperStructureSubjectOPService.getSelected();
					if(keyValue != null){
						clientUploadItem.setSubjectId(keyValue.getKey());
					}
				}
				//clientUploadItem.setStructureId(this.uploadPreview.getPaperStructureId());
				if(JOptionPane.showConfirmDialog(null, "确认是否上传？", "确认", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION){
					boolean result = this.itemUploadRemoteService.upload(this.uploadPreview.getPaperId(), this.uploadPreview.getPaperStructureId(), clientUploadItem);
					this.showMessageDialog("提示", "导入试题［" + (result ? "成功" : "失败") + "］！", result ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
				}
			} catch(Exception e1) {
				logger.error("发生异常：" + e1.getMessage(), e1);
				this.showMessageDialog(e1.getMessage());
				e1.printStackTrace();
			}
		}
	}
	//显示错误消息。
	private void showMessageDialog(String error){
		this.showMessageDialog("错误", error, JOptionPane.ERROR_MESSAGE);
	}
	//显示消息。
	private void showMessageDialog(String title, String message, int messageType){
		JComponent component = null;
		if(this.uploadPreview instanceof JComponent){
			component = (JComponent)this.uploadPreview;
		}
		JOptionPane.showMessageDialog(component, message, title, messageType);
	}
}