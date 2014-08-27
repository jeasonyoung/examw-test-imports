package com.examw.test.imports.shiro.service;
/**
 * 用户鉴权接口。
 * 
 * @author yangyong
 * @since 2014年8月27日
 */
public interface IUserAuthentication {
	/**
	 * 鉴权。
	 * @return
	 */
	boolean isAuthenticated();
}