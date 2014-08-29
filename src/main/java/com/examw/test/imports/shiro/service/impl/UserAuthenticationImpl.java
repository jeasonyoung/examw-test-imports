package com.examw.test.imports.shiro.service.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.examw.test.imports.shiro.service.IUserAuthentication;

/**
 * 用户鉴权接口实现类。
 * 
 * @author yangyong
 * @since 2014年8月27日
 */
public class UserAuthenticationImpl implements IUserAuthentication {
	/*
	 * 鉴权。
	 * @see com.examw.test.imports.shiro.service.IUserAuthentication#isAuthenticated()
	 */
	@Override
	public boolean isAuthenticated() {
		Subject subject = SecurityUtils.getSubject();
		return subject.isAuthenticated();
	}
	/*
	 * 用户认证。
	 * @see com.examw.test.imports.shiro.service.IUserAuthentication#authenticated(java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean authenticated(String username, String password) throws Exception {
		// TODO Auto-generated method stub
		return true;
	}
}