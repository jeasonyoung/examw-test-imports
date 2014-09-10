package com.examw.test.imports.view.model;

import javax.swing.text.JTextComponent;

/**
 * 剪切。
 * 
 * @author yangyong
 * @since 2014年9月2日
 */
public class RightMenuCutModel extends RightMenuBaseModel {
	private static final long serialVersionUID = 1L;
	/**
	 * 构造函数。
	 * @param textComponent
	 */
	public RightMenuCutModel(JTextComponent textComponent) {
		super(textComponent);
	}
	/*
	 * (non-Javadoc)
	 * @see com.examw.test.imports.view.model.RightMenuBaseModel#handler()
	 */
	@Override
	protected void handler() {
		if(this.textComponent.getSelectedText() == null){
			this.textComponent.selectAll();
		}
		this.textComponent.cut();
	}
}