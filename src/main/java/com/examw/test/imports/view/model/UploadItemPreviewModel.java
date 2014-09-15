package com.examw.test.imports.view.model;

import java.awt.event.ActionEvent;

import javax.swing.DefaultButtonModel;

import com.examw.test.imports.service.UploadItemPreview;
import com.examw.test.imports.service.UploadPreviewOPService;
/**
 * 按题目上传预览按钮模型基础类。
 * 
 * @author yangyong
 * @since 2014年9月15日
 */
public class UploadItemPreviewModel extends DefaultButtonModel {
	private static final long serialVersionUID = 1L;
	private UploadPreviewOPService previewOPService;
	private UploadItemPreview uploadItemPreview;
	private boolean isNext = false;
	/**
	 * 构造函数。
	 * @param previewOPService
	 */
	public UploadItemPreviewModel(UploadPreviewOPService previewOPService,UploadItemPreview uploadItemPreview, boolean isNext){
		super();
		this.previewOPService = previewOPService;
		this.uploadItemPreview = uploadItemPreview;
		this.isNext = isNext;
	}
	/**
	 * 构造函数。
	 * @param previewOPService
	 * @param uploadItemPreview
	 */
	public UploadItemPreviewModel(UploadPreviewOPService previewOPService,UploadItemPreview uploadItemPreview){
		this(previewOPService, uploadItemPreview, false);
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.DefaultButtonModel#fireActionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	protected void fireActionPerformed(ActionEvent e) {
		super.fireActionPerformed(e);
		if(this.uploadItemPreview != null && this.previewOPService != null){
			this.uploadItemPreview.writeItemJSON(this.isNext ? this.previewOPService.next() : this.previewOPService.prev());
		}
	}
	
}