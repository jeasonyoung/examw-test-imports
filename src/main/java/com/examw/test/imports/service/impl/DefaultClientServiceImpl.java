package com.examw.test.imports.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.examw.test.imports.service.ItemTypeRemoteDataService;
import com.examw.test.imports.shiro.service.UserAuthentication;

/**
 *  客户端服务实现。
 * 
 * @author yangyong
 * @since 2014年9月3日
 */
public class DefaultClientServiceImpl implements UserAuthentication,ItemTypeRemoteDataService {
	/*
	 * 用户是否登录。
	 * @see com.examw.test.imports.shiro.service.IUserAuthentication#isAuthenticated()
	 */
	@Override
	public boolean isAuthenticated() {
		Subject subject = SecurityUtils.getSubject();
		return subject.isAuthenticated();
	}
	/*
	 * 用户登录验证。
	 * @see com.examw.test.imports.shiro.service.IUserAuthentication#authenticated(java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean authenticated(String username, String password) throws Exception {
		return true;
	}
	/*
	 * 下载题型数据。
	 * @see com.examw.test.imports.service.IItemTypeService#downloadData()
	 */
	@Override
	public Map<Integer, String> downloadData() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "单选");
		map.put(2, "多选");
		map.put(3, "不定向选");
		return map;
	}
}