package com.examw.test.imports.view.model;

import javax.swing.JTextArea;

/**
 *  复制
 * 
 * @author yangyong
 * @since 2014年9月2日
 */
public class RightMenuCopyModel extends RightMenuBaseModel {
	private static final long serialVersionUID = 1L;
	/**
	 * 构造函数。
	 * @param textArea
	 */
	public RightMenuCopyModel(JTextArea textArea) {
		super(textArea);
	}
	/*
	 * (non-Javadoc)
	 * @see com.examw.test.imports.view.model.RightMenuBaseModel#handler()
	 */
	@Override
	protected void handler() {
		if(this.textArea.getSelectedText() == null){
			this.textArea.selectAll();
		}
		this.textArea.copy();
	}

}