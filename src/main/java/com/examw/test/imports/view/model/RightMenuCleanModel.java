package com.examw.test.imports.view.model;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

/**
 * 右键清空。
 * 
 * @author yangyong
 * @since 2014年9月18日
 */
public class RightMenuCleanModel extends RightMenuBaseModel {
	private static final long serialVersionUID = 1L;
	/**
	 * 构造函数。
	 * @param textComponent
	 */
	public RightMenuCleanModel(JTextComponent textComponent) {
		super(textComponent);
	}
	/*
	 * 清空.
	 * @see com.examw.test.imports.view.model.RightMenuBaseModel#handler()
	 */
	@Override
	protected void handler() {
		 Document document = this.textComponent.getDocument();
		 int len = 0;
		 if(document != null && (len = document.getLength()) > 0){
			 try {
				document.remove(0, len);
			} catch (BadLocationException e) {
			}
		 }
	}
}