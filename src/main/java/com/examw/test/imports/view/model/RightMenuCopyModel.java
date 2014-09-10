package com.examw.test.imports.view.model;

import javax.swing.text.JTextComponent;

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
	 * @param textComponent
	 */
	public RightMenuCopyModel(JTextComponent textComponent) {
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
		this.textComponent.copy();
	}

}