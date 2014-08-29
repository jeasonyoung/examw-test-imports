package com.examw.test.imports.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.examw.test.imports.shiro.service.IUserAuthentication;
import com.examw.test.imports.support.ViewUtils;
/**
 * 主窗体界面。
 * 
 * @author yangyong
 * @since 2014年8月26日
 */
public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(MainFrame.class);
	private IUserAuthentication userAuthentication;
	private JDialog loginDialog;
	private String closeDialogMessage,closeDialogTitle;
	/**
	 * 构造函数。
	 * @param title
	 * 窗体名称。
	 * @param width
	 * 窗体宽度。
	 * @param height
	 * 窗体高度。
	 */
	public MainFrame(String title,int width,int height){
		 super(title);
		 this.setSize(new Dimension(width, height));
	}
	/**
	 * 设置用户鉴权。
	 * @param userAuthentication 
	 *	  userAuthentication
	 */
	public void setUserAuthentication(IUserAuthentication userAuthentication) {
		this.userAuthentication = userAuthentication;
	}
	/**
	 * 设置用户登录窗口。
	 * @param loginDialog 
	 *	  用户登录窗口。
	 */
	public void setLoginDialog(JDialog loginDialog) {
		this.loginDialog = loginDialog;
	}
	/**
	 * 设置关闭窗体消息。
	 * @param closeDialogMessage 
	 *	  关闭窗体消息。
	 */
	public void setCloseDialogMessage(String closeDialogMessage) {
		this.closeDialogMessage = closeDialogMessage;
	}
	/**
	 * 设置关闭窗体消息标题。
	 * @param closeDialogTitle 
	 *	  关闭窗体消息标题。
	 */
	public void setCloseDialogTitle(String closeDialogTitle) {
		this.closeDialogTitle = closeDialogTitle;
	}
	/**
	 * 设置内容成员集合。
	 * @param members
	 * 内容成员集合。
	 */
	public void setMembers(Map<String, Component> members){
		if(members != null && members.size() > 0){
			if(logger.isDebugEnabled()) logger.debug("加载主界面成员组件...");
			for(Map.Entry<String, Component> entry : members.entrySet()){
				if(logger.isDebugEnabled()) logger.debug(String.format("开始加载[%s]...", entry.getKey()));
				this.add(entry.getValue(),entry.getKey());
			}
		}
	}
	/**
	 * 初始化函数。
	 */
	public void init(){
		if(logger.isDebugEnabled()) logger.debug("主窗体初始化...");
		ViewUtils.positionCenterWindow(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setState(JFrame.NORMAL);
		this.setVisible(true);
		this.authentication();
	}
	//鉴权。
	private void authentication(){
		if(logger.isDebugEnabled()) logger.debug("开始用户鉴权...");
		if(!this.userAuthentication.isAuthenticated()){
			if(logger.isDebugEnabled()) logger.debug("加载用户登录窗体...");
			this.loginDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			ViewUtils.positionCenterWindow(this.loginDialog);
			this.loginDialog.setVisible(true);
		}
	}
	/*
	 * 窗体事件。
	 * @see javax.swing.JDialog#processWindowEvent(java.awt.event.WindowEvent)
	 */
	@Override
	protected void processWindowEvent(WindowEvent e) {
		if(e.getID() == WindowEvent.WINDOW_CLOSING){
			if(logger.isDebugEnabled()) logger.debug("捕获主窗体关闭事件！");
			if(JOptionPane.showConfirmDialog(this, this.closeDialogMessage,this.closeDialogTitle, JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION){
				return;
			}
			if(logger.isDebugEnabled()) logger.debug("退出程序！");
			super.processWindowEvent(e);
		}
		
	}
}