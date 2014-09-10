package com.examw.test.imports.view.model;

import javax.swing.text.JTextComponent;

/**
 * 右键全选菜单按钮处理模型。
 * 
 * @author yangyong
 * @since 2014年9月2日
 */
public class RightMenuSelectAllModel extends RightMenuBaseModel {
	private static final long serialVersionUID = 1L;
	/**
	 * 构造函数。
	 * @param textComponent
	 */
	public RightMenuSelectAllModel(JTextComponent textComponent) {
		super(textComponent);
	}
	/*
	 * (non-Javadoc)
	 * @see com.examw.test.imports.view.model.RightMenuBaseModel#handler(java.awt.event.ActionEvent)
	 */
	@Override
	protected void handler() {
		this.textComponent.selectAll();
	}
}