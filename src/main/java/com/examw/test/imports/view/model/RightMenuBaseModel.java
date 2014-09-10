package com.examw.test.imports.view.model;

import java.awt.event.ActionEvent;

import javax.swing.DefaultButtonModel;
import javax.swing.text.JTextComponent;

/**
 * 右键菜单基础抽象类。
 * 
 * @author yangyong
 * @since 2014年9月2日
 */
public abstract class RightMenuBaseModel extends DefaultButtonModel {
	private static final long serialVersionUID = 1L;
	protected JTextComponent textComponent;
	/**
	 *  构造函数。
	 * @param textComponent
	 */
	public RightMenuBaseModel(JTextComponent textComponent){
		super();
		this.textComponent = textComponent;
	}
	/**
	 * 事件处理。
	 * @param e
	 */
	protected abstract void handler();
	/*
	 * (non-Javadoc)
	 * @see javax.swing.DefaultButtonModel#fireActionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	protected void fireActionPerformed(ActionEvent e) {
		this.handler();
		super.fireActionPerformed(e);
	}
}