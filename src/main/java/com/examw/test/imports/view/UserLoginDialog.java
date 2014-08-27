package com.examw.test.imports.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
/**
 * 用户登录模态窗口。
 * 
 * @author yangyong
 * @since 2014年8月27日
 */
public class UserLoginDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	/**
	 * 构造函数。
	 * @param frame
	 * 所属主窗体。
	 * @param title
	 * 标题。
	 * @param width
	 * 窗体宽度。
	 * @param heigt
	 * 窗体高度。
	 */
	public UserLoginDialog(Frame owner, String title,int width,int height){
		super(owner, title, true);
		this.setSize(new Dimension(width, height));
	}
	/*
	 * 窗体事件。
	 * @see javax.swing.JDialog#processWindowEvent(java.awt.event.WindowEvent)
	 */
	@Override
	protected void processWindowEvent(WindowEvent e) {
		if(e.getID() == WindowEvent.WINDOW_CLOSING){
			if(JOptionPane.showConfirmDialog(this, " 确认关闭?","确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
				System.exit(0);
			}
		}
		super.processWindowEvent(e);
	}
}