package com.examw.test.imports.view;

import javax.swing.JFrame;

import com.examw.test.imports.model.ClientUploadItem;
import com.examw.test.imports.service.UploadItemPreview;
import com.examw.test.imports.service.UploadPreview;
import com.examw.test.imports.service.UploadPreviewOPService;
import com.examw.test.imports.support.ViewUtils;

/**
 * 上传内容模态界面。
 * 
 * @author yangyong
 * @since 2014年9月12日
 */
public class UploadContentDialog extends ContentDialog implements UploadPreview,UploadPreviewOPService {
	private static final long serialVersionUID = 1L;
	private String paperId = null,structureId =  null;
	private UploadItemPreview uploadItemPreview;
	private ClientUploadItem[] clientUploadItems = null;
	private Integer index = -1;
	/**
	 * 构造函数。
	 * @param owner
	 * @param title
	 * @param width
	 * @param height
	 */
	public UploadContentDialog(JFrame owner, String title, int width, int height) {
		super(owner, title, width, height);
	}
	/**
	 * 设置上传预览文档。
	 * @param uploadItemPreview 
	 *	  上传预览文档。
	 */
	public void setUploadItemPreview(UploadItemPreview uploadItemPreview) {
		this.uploadItemPreview = uploadItemPreview;
	}
	/*
	 * (non-Javadoc)
	 * @see com.examw.test.imports.service.UploadPreview#showDialog(java.lang.String, java.lang.String, com.examw.test.imports.model.ClientUploadItem[])
	 */
	@Override
	public void showDialog(String paperId, String paperStructureId,ClientUploadItem[] clientUploadItem) {
		this.index = -1;
		this.paperId = paperId;
		this.structureId = paperStructureId;
		this.clientUploadItems = clientUploadItem;
		ViewUtils.positionCenterWindow(this);
		if(this.uploadItemPreview != null){
			this.uploadItemPreview.writeItemJSON(this.current());
		}
		this.setVisible(true);
	}
	/*
	 * 获取所属试卷ID。
	 * @see com.examw.test.imports.service.UploadPreview#getPaperId()
	 */
	@Override
	public String getPaperId() {
		return this.paperId;
	}
	/*
	 * 获取所属试卷结构ID。
	 * @see com.examw.test.imports.service.UploadPreview#getPaperStructureId()
	 */
	@Override
	public String getPaperStructureId() {
		return this.structureId;
	}
	/*
	 * 下一题。
	 * @see com.examw.test.imports.service.UploadPreviewOPService#next()
	 */
	@Override
	public ClientUploadItem next() {
		this.index++;
		return this.current();
	}
	/*
	 * 上一题。
	 * @see com.examw.test.imports.service.UploadPreviewOPService#prev()
	 */
	@Override
	public ClientUploadItem prev() {
		this.index--;
		return this.current();
	}
	/*
	 * 当前题。
	 * @see com.examw.test.imports.service.UploadPreviewOPService#current()
	 */
	@Override
	public ClientUploadItem current() {
		if(this.clientUploadItems == null || this.clientUploadItems.length == 0) return null;
		if(this.index <= -1){
			this.index = 0;
		}else if(this.index >= this.clientUploadItems.length){
			this.index = this.clientUploadItems.length - 1;
		} 
		return this.clientUploadItems[this.index];
	}
}