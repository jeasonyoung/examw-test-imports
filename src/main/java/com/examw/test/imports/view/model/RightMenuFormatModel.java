package com.examw.test.imports.view.model;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import com.examw.test.imports.service.ExamOPService;
import com.examw.test.imports.service.ItemTypeOPService;

/**
 * 右键菜单－格式化。
 * 
 * @author yangyong
 * @since 2014年9月3日
 */
public class RightMenuFormatModel extends RightMenuBaseModel {
	private static final long serialVersionUID = 1L;
	private ItemTypeOPService itemTypeOPService;
	private ExamOPService examOPService;
	/**
	 * 构造函数。
	 * @param textArea
	 */
	public RightMenuFormatModel(JTextArea textArea) {
		super(textArea);
	}
	/**
	 * 设置题型操作服务接口。
	 * @param itemTypeOPService 
	 *	  题型操作服务接口。
	 */
	public void setItemTypeOPService(ItemTypeOPService itemTypeOPService) {
		this.itemTypeOPService = itemTypeOPService;
	}
	/**
	 * 设置所属考试服务接口。
	 * @param examOPService 
	 *	  所属考试服务接口。
	 */
	public void setExamOPService(ExamOPService examOPService) {
		this.examOPService = examOPService;
	}
	/*
	 * (non-Javadoc)
	 * @see com.examw.test.imports.view.model.RightMenuBaseModel#handler()
	 */
	@Override
	protected void handler() {
		String itemTypeValue = null;
		if(this.itemTypeOPService != null){
			itemTypeValue = this.itemTypeOPService.getSelected();
		}
		if(this.examOPService != null){
			itemTypeValue += ",||," + this.examOPService.getSelected();
		}
		JOptionPane.showMessageDialog(this.textArea, String.format("题型值＝>%s", itemTypeValue));
		// TODO Auto-generated method stub
	}
}