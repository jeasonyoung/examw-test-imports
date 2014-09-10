package com.examw.test.imports.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JTextArea;
import javax.swing.border.AbstractBorder;
import javax.swing.text.JTextComponent;

/**
 * 添加行号边框。
 * 
 * @author yangyong
 * @since 2014年9月2日
 */
public class LineNumberBorder extends AbstractBorder {
	private static final long serialVersionUID = 1L;
	/*
	 * 此方法在实例化时自动调用，关系到边框是否占用组件的空间。
	 * @see javax.swing.border.AbstractBorder#getBorderInsets(java.awt.Component)
	 */
	@Override
	public Insets getBorderInsets(Component c) {
		return this.getBorderInsets(c, new Insets(0, 0, 0, 0));
	}
	/*
	 * insets对象是容器边界的表示形式，它指定容器必须在其各个边缘留出的空间。
	 * @see javax.swing.border.AbstractBorder#getBorderInsets(java.awt.Component, java.awt.Insets)
	 */
	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		if(c instanceof JTextComponent){
			int width = this.lineNumberWidth((JTextComponent)c);
			insets.left = width;
		}
		return insets;
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.border.AbstractBorder#isBorderOpaque()
	 */
	@Override
	public boolean isBorderOpaque() {
		return false;
	}
	/*
	 * 边框的绘制方法，必须实现。
	 * @see javax.swing.border.AbstractBorder#paintBorder(java.awt.Component, java.awt.Graphics, int, int, int, int)
	 */
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width,int height) {
		if(!(c instanceof JTextComponent)){
			super.paintBorder(c, g, x, y, width, height);
			return;
		}
		 Rectangle clip = g.getClipBounds();//获得当前剪贴区域的边界矩形。
		 FontMetrics fm = g.getFontMetrics();
		 int fontHeight = fm.getHeight();
		 //starting location at the "top" of the page...
		 //y is the starting baseline for the font...
		 int ybaseline = y + fm.getAscent();
		 //now determine if it is the "top"  of the page ... or somewhere else
		 int startingLineNumber = (clip.y/ fontHeight) + 1;
		 if(startingLineNumber != 1){
			 ybaseline = y + (startingLineNumber * fontHeight) - (fontHeight - fm.getAscent());
		 }
		 int yend = ybaseline + height;
		 if(yend > (y + height)){
			 yend = y + height;
		 }
		 JTextComponent textComponent = (JTextComponent)c;
		 int lineWidth = this.lineNumberWidth(textComponent),
			  lnxStart = x + lineWidth;
		 g.setColor(new Color(0xCC, 0xCC, 0xCC));
		 g.drawLine(lnxStart, y, lnxStart, y + height);
		 
		 //g.setColor(Color.blue);
		 //loop nutil out of the "visible" region...
		 int length = lineWidth;//(" " + Math.max(textArea.getRows(), textArea.getLineCount() + 1)).length();
		 //绘制行号
		 while(ybaseline < yend){
			 String label = padLabel(startingLineNumber, length, true);
			 g.drawString(label, lnxStart - fm.stringWidth(label), ybaseline);
			 ybaseline += fontHeight;
			 startingLineNumber++;
		 }
	}
	//适合的数字宽度。
	private int lineNumberWidth(JTextComponent textComponent){
		int lineCount = 0;
		FontMetrics fm = textComponent.getFontMetrics(textComponent.getFont());
		if(textComponent instanceof JTextArea){
			lineCount = Math.max(((JTextArea)textComponent).getRows(), ((JTextArea)textComponent).getLineCount() + 1);
		}else {
			int height = fm.getHeight();
			lineCount = (textComponent.getHeight() / height) + 1;
		}
		if(lineCount < 10) lineCount = 10;
		return fm.stringWidth(lineCount + " ");
	}
	//
	private static String padLabel(int lineNumber,int length,boolean addSpace){
		StringBuffer buffer = new StringBuffer();
		buffer.append(lineNumber);
		for(int count = (length - buffer.length()); count > 0; count--){
			buffer.insert(0, ' ');
		}
		if(addSpace) buffer.append(' ');
		return buffer.toString();
	}
}