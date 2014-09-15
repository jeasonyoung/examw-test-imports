package com.examw.test.imports.view;

import java.awt.event.WindowEvent;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
/**
 * 用户登录模态窗口。
 * 
 * @author yangyong
 * @since 2014年8月27日
 */
public class UserLoginDialog extends ContentDialog {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(UserLoginDialog.class);
	private String closeDialogTitle,closeDialogMessage;
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
	public UserLoginDialog(JFrame owner, String title,int width,int height){
		 super(owner, title, width, height);
	}
	/**
	 * 设置关闭对话框时显示的标题。
	 * @param closeDialogTitle 
	 *	  关闭对话框时显示的标题。
	 */
	public void setCloseDialogTitle(String closeDialogTitle) {
		this.closeDialogTitle = closeDialogTitle;
	}
	/**
	 * 设置关闭对话框时显示的消息。
	 * @param closeDialogMessage 
	 *	  关闭对话框时显示的消息。
	 */
	public void setCloseDialogMessage(String closeDialogMessage) {
		this.closeDialogMessage = closeDialogMessage;
	}
	/**
	 * 设置成员控件集合。
	 * @param members 
	 *	  成员控件集合。
	 */
	@Override
	public void setMembers(Map<String, JComponent> members) {
		super.setMembers(members);
		if(members != null && members.size() > 0){
			this.pack();
		}
	}
	/*
	 * 窗体事件。
	 * @see javax.swing.JDialog#processWindowEvent(java.awt.event.WindowEvent)
	 */
	@Override
	protected void processWindowEvent(WindowEvent e) {
		if(e.getID() == WindowEvent.WINDOW_CLOSING){
			if(logger.isDebugEnabled()) logger.debug("捕获登录界面窗体关闭事件！");
			if(JOptionPane.showConfirmDialog(this, this.closeDialogMessage,this.closeDialogTitle, JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION){
				if(logger.isDebugEnabled()) logger.debug("退出程序！");
				System.exit(0);
			}
		}
		super.processWindowEvent(e);
	}
}