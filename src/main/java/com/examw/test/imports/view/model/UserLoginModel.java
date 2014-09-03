package com.examw.test.imports.view.model;
 
import java.awt.Color;
import java.awt.event.ActionEvent; 
import java.util.List;

import javax.swing.DefaultButtonModel; 
import javax.swing.JOptionPane;
import javax.swing.JTextField; 

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.imports.service.OPService;
import com.examw.test.imports.shiro.service.UserAuthentication;
import com.examw.test.imports.view.MainFrame;
import com.examw.test.imports.view.UserLoginDialog;

/**
 * 用户登录提交按钮。
 * 
 * @author yangyong
 * @since 2014年8月29日
 */
public class UserLoginModel extends DefaultButtonModel {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(UserLoginModel.class);
	private JTextField usernameField,passwordField;
	private UserLoginDialog loginDialog;
	private MainFrame mainFrame;
	private UserAuthentication userAuthentication;
	private Color oldColor;
	private List<OPService> opServices;
	/**
	 * 设置用户名文本输入框。
	 * @param usernameField 
	 *	  用户名文本输入框。
	 */
	public void setUsernameField(JTextField usernameField) {
		this.usernameField = usernameField;
	}
	/**
	 * 设置密码输入框。
	 * @param passwordField 
	 *	  密码输入框。
	 */
	public void setPasswordField(JTextField passwordField) {
		this.passwordField = passwordField;
	}
	/**
	 * 设置登录界面。
	 * @param loginDialog 
	 *	  登录界面。
	 */
	public void setLoginDialog(UserLoginDialog loginDialog) {
		this.loginDialog = loginDialog;
	}
	/**
	 * 设置主界面。
	 * @param mainFrame 
	 *	  主界面。
	 */
	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	/**
	 * 设置用户认证接口。
	 * @param userAuthentication 
	 *	  用户认证接口。
	 */
	public void setUserAuthentication(UserAuthentication userAuthentication) {
		this.userAuthentication = userAuthentication;
	}
	/**
	 * 设置题型操作服务。
	 * @param itemTypeOPService 
	 *	  题型操作服务。
	 */
	public void setOPServices(List<OPService> opServices) {
		this.opServices = opServices;
	}
	/*
	 * 事件重载。
	 * @see javax.swing.DefaultButtonModel#fireActionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	protected void fireActionPerformed(ActionEvent e){
		super.fireActionPerformed(e);
		if((e.getID() == ActionEvent.ACTION_PERFORMED)){
			if(logger.isDebugEnabled()) logger.debug("开始验证用户登录....");
			String username = this.validInput(this.usernameField), password = this.validInput(this.passwordField);
			if(!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)){
				try {
					if(logger.isDebugEnabled()) logger.debug(String.format("u=%1$s,p=%2$s", username, password));
					this.setEnabled(false);
				    boolean success =	this.userAuthentication.authenticated(username, password);
				    if(success){
				    	if(this.opServices != null && this.opServices.size() > 0){
				    		for(OPService opService : this.opServices){
				    			if(opService == null) continue;
				    			opService.loadRemoteData(null);
				    		}
				    	}
				    	this.loginDialog.setVisible(false);
				    	this.mainFrame.setVisible(true);
				    }
				} catch (Exception e1) {
					if(logger.isDebugEnabled()) logger.debug(String.format("登录失败！( %s )", e1.getMessage()));
					JOptionPane.showConfirmDialog(this.loginDialog, e1.getMessage());
				}finally{
					this.setEnabled(false);
				}
			}
		}
	}
	//验证输入框
	private String validInput(JTextField field){
		String text = field.getText();
		if(StringUtils.isEmpty(text)){
			if(field.getBackground() != Color.RED){
				this.oldColor = field.getBackground();
				field.setBackground(Color.RED);
			}
		}else{
			if(this.oldColor != null) field.setBackground(this.oldColor);
		}
		return StringUtils.isEmpty(text) ? null : text;
	}
   
}