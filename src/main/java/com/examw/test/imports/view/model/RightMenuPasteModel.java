package com.examw.test.imports.view.model;

import javax.swing.text.JTextComponent;

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
	 * @param textComponent
	 */
	public RightMenuPasteModel(JTextComponent textComponent) {
		super(textComponent);
	}
	/*
	 * (non-Javadoc)
	 * @see com.examw.test.imports.view.model.RightMenuBaseModel#handler()
	 */
	@Override
	protected void handler() {
		 this.textComponent.paste();
	}
}