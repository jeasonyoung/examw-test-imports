package com.examw.test.imports.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.StringUtils;

import com.examw.test.imports.model.Json;
import com.examw.test.imports.model.KeyValue;
import com.examw.test.imports.model.KeyValueType;
import com.examw.test.imports.service.ExamRemoteDataService;
import com.examw.test.imports.service.ItemTypeRemoteDataService;
import com.examw.test.imports.service.PaperRemoteDataService;
import com.examw.test.imports.service.PaperStructureRemoteDataService;
import com.examw.test.imports.shiro.service.UserAuthentication;
import com.examw.test.imports.support.HttpUtil;
import com.examw.test.imports.support.MD5Util;
/**
 *  客户端服务实现。
 * 
 * @author yangyong
 * @since 2014年9月3日
 */
public class DefaultClientServiceImpl implements UserAuthentication,ItemTypeRemoteDataService,ExamRemoteDataService,PaperRemoteDataService,PaperStructureRemoteDataService {
	private static final Logger logger = Logger.getLogger(DefaultClientServiceImpl.class);
	private String serverUrl;
	/**
	 * 构造函数。
	 * @param serverUrl
	 * 服务器地址.
	 */
	public DefaultClientServiceImpl(String serverUrl){
		if(logger.isDebugEnabled()) logger.debug(String.format("注入服务器地址：%s", serverUrl));
		this.serverUrl = serverUrl;
	}
	/*
	 * 用户是否登录。
	 * @see com.examw.test.imports.shiro.service.IUserAuthentication#isAuthenticated()
	 */
	@Override
	public boolean isAuthenticated() {
		if(logger.isDebugEnabled()) logger.debug("校验用户是否登录...");
		Subject subject = SecurityUtils.getSubject();
		return subject.isAuthenticated();
	}
	/*
	 * 用户登录验证。
	 * @see com.examw.test.imports.shiro.service.IUserAuthentication#authenticated(java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean authenticated(String username, String password) throws Exception {
		if(logger.isDebugEnabled()) logger.debug(String.format("用户登录验证[username=%1$s  password=%2$s]...", username,password));
		if(StringUtils.isEmpty(username)) throw new Exception("用户账号为null !");
		if(StringUtils.isEmpty(password)) throw new Exception("密码为null !");
		String url = String.format("%1$s/api/imports/authen/%2$s", this.serverUrl, username);
		if(logger.isDebugEnabled())logger.debug(String.format("url=>%s", url));
		String token = MD5Util.MD5(String.format("%1$s%2$s", username, MD5Util.MD5(String.format("%1$s%2$s", username,password))));
		if(logger.isDebugEnabled()) logger.debug(String.format("token=>%s", token));
		String data = HttpUtil.sendRequest(url, "POST", String.format("token=%s", token));
		ObjectMapper mapper = new ObjectMapper();
		Json json = mapper.readValue(data, Json.class);
		if(json == null){
			throw new Exception("反馈收据转换失败！callback=>" + data);
		}
		if(json.isSuccess()){
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
			usernamePasswordToken.setRememberMe(false);
			subject.login(usernamePasswordToken);
			return true;
		}
		throw new Exception(json.getMsg());
	}
	/*
	 * 加载题型数据。
	 * @see com.examw.test.imports.service.IItemTypeService#downloadData()
	 */
	@Override
	public List<KeyValue> loadItemTypes() {
		List<KeyValue> list = new ArrayList<>();
		list.add(new KeyValue("1", "单选"));
		list.add(new KeyValue("2", "多选"));
		list.add(new KeyValue("3", "不定向选"));
		return list;
	}
	/*
	 * 加载考试数据。
	 * @see com.examw.test.imports.service.ExamRemoteDataService#loadExams()
	 */
	@Override
	public List<KeyValue>  loadExams() {
		List<KeyValue> list = new ArrayList<>();
		list.add(new KeyValue("1234567890121", "考试测试1"));
		list.add(new KeyValue("1234567890122", "考试测试2"));
		list.add(new KeyValue("1234567890123", "考试测试3"));
		return list;
	}
	/*
	 * 加载所属试卷数据。
	 * @see com.examw.test.imports.service.PaperRemoteDataService#loadPapers(java.lang.String)
	 */
	@Override
	public List<KeyValue> loadPapers(String examId) {
		List<KeyValue> list = new ArrayList<>();
		list.add(new KeyValue("01234567890121-"+ examId, "试卷测试1"));
		list.add(new KeyValue("01234567890122-"+ examId, "试卷测试2"));
		list.add(new KeyValue("01234567890123-"+ examId, "试卷测试3"));
		return list;
	}
	/*
	 * 加载试卷结构数据。
	 * @see com.examw.test.imports.service.PaperStructureRemoteDataService#loadPaperStructures(java.lang.String)
	 */
	@Override
	public List<KeyValueType> loadPaperStructures(String paperId) {
		List<KeyValueType> list = new ArrayList<>();
		list.add(new KeyValueType("00", "结构测试1", 1));
		list.add(new KeyValueType("01", "结构测试2", 2));
		list.add(new KeyValueType("02", "结构测试3", 3));
		return list;
	}
}