package com.examw.test.imports.view;

import java.awt.Dimension;

import javax.swing.JLabel;

/**
 * 文本显示。
 * 
 * @author yangyong
 * @since 2014年8月29日
 */
public class Label extends JLabel {
	private static final long serialVersionUID = 1L;
	/**
	 * 构造函数。
	 * @param text
	 * @param width
	 * @param height
	 */
	public Label(String text, Integer width, Integer height){
		super(text, JLabel.RIGHT);
		if(width != null || height  != null){
		  Dimension dimension =	 this.getSize();
		  if(width == null) width = (int)dimension.getWidth();
		  if(height == null) height = (int)dimension.getHeight();
			this.setSize(new Dimension(width, height));
		}
	}
	/**
	 * 构造函数。
	 * @param text
	 * @param width
	 */
	public  Label(String text, int width){
		 this(text, width, null);
	}
	/**
	 * 构造函数。
	 * @param text
	 */
	public  Label(String text){
		this(text, 75);
	}
}