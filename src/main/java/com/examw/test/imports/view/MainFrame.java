package com.examw.test.imports.view;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;

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
}