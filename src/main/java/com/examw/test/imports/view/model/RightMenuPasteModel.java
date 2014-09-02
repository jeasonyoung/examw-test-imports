package com.examw.test.imports.view.model;

import javax.swing.JTextArea;

/**
 * 粘贴。
 * 
 * @author yangyong
 * @since 2014年9月2日
 */
public class RightMenuPasteModel extends RightMenuBaseModel {
	private static final long serialVersionUID = 1L;
	/**
	 * 构造函数。
	 * @param textArea
	 */
	public RightMenuPasteModel(JTextArea textArea) {
		super(textArea);
	}
	/*
	 * (non-Javadoc)
	 * @see com.examw.test.imports.view.model.RightMenuBaseModel#handler()
	 */
	@Override
	protected void handler() {
		 this.textArea.paste();
	}
}