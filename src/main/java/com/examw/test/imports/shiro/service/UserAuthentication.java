package com.examw.test.imports.shiro.service;
/**
 * 用户鉴权接口。
 * 
 * @author yangyong
 * @since 2014年8月27日
 */
public interface UserAuthentication {
	/**
	 * 鉴权。
	 * @return
	 */
	boolean isAuthenticated();
	/**
	 * 用户认证。
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	Boolean authenticated(String username,String password) throws Exception;
}