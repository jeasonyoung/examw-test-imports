package com.examw.test.imports.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.StringUtils;

import com.examw.test.imports.model.ClientExamInfo;
import com.examw.test.imports.model.ClientPaperInfo;
import com.examw.test.imports.model.ClientStructureInfo;
import com.examw.test.imports.model.ClientUploadItem;
import com.examw.test.imports.model.Json;
import com.examw.test.imports.model.KeyValue;
import com.examw.test.imports.model.KeyValueType;
import com.examw.test.imports.service.ExamRemoteDataService;
import com.examw.test.imports.service.ItemTypeRemoteDataService;
import com.examw.test.imports.service.ItemUploadRemoteService;
import com.examw.test.imports.service.PaperRemoteDataService;
import com.examw.test.imports.service.PaperStructureRemoteDataService;
import com.examw.test.imports.shiro.service.UserAuthentication;
import com.examw.test.imports.support.DigestClientUtil;
import com.examw.test.imports.support.MD5Util;
import com.examw.test.imports.support.StructureSubjectCacheUtil;
/**
 *  客户端服务实现。
 * 
 * @author yangyong
 * @since 2014年9月3日
 */
public class DefaultClientServiceImpl implements UserAuthentication,ItemTypeRemoteDataService,ExamRemoteDataService,PaperRemoteDataService,PaperStructureRemoteDataService,ItemUploadRemoteService {
	private static final Logger logger = Logger.getLogger(DefaultClientServiceImpl.class);
	private ObjectMapper mapper;
	private String username,password,serverUrl;
	/**
	 * 构造函数。
	 * @param serverUrl
	 * 服务器地址.
	 */
	public DefaultClientServiceImpl(String serverUrl){
		if(logger.isDebugEnabled()) logger.debug(String.format("注入服务器地址：%s", serverUrl));
		this.mapper = new ObjectMapper();
		this.mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
		this.mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
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
		String url = String.format("%1$s/authen/%2$s", this.serverUrl, username);
		if(logger.isDebugEnabled())logger.debug(String.format("url=>%s", url));
		String token = MD5Util.MD5(String.format("%1$s%2$s", username, MD5Util.MD5(String.format("%1$s%2$s", username,password))));
		if(logger.isDebugEnabled()) logger.debug(String.format("token=>%s", token));
		//String data = HttpUtil.sendRequest(url, "POST", String.format("token=%s", token));
		String data = DigestClientUtil.sendDigestRequest(username, password, "POST", url, String.format("token=%s", token));
		
		if(logger.isDebugEnabled()) logger.debug("反馈数据=>" + data);
		Json json = this.mapper.readValue(data, Json.class);
		if(json == null){
			throw new Exception("反馈收据转换失败！callback=>" + data);
		}
		if(json.isSuccess()){
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
			usernamePasswordToken.setRememberMe(false);
			subject.login(usernamePasswordToken);
			this.username = username;
			this.password = password;
			return true;
		}
		throw new Exception(json.getMsg());
	}
	/*
	 * 加载题型数据。
	 * @see com.examw.test.imports.service.IItemTypeService#downloadData()
	 */
	@Override
	public List<KeyValue> loadItemTypes() throws Exception {
		if(logger.isDebugEnabled()) logger.debug("加载题型数据...");
		List<KeyValue> list = new ArrayList<>();
		String uri = String.format("%s/itemtypes", this.serverUrl);
		//String data = HttpUtil.sendRequest(String.format("%s/itemtypes", this.serverUrl), "GET", null);
		String data = DigestClientUtil.sendDigestRequest(this.username, this.password, "GET", uri, null);
		if(logger.isDebugEnabled()) logger.debug("反馈数据=>" + data);
		String[][] array = this.mapper.readValue(data, String[][].class);
		if(array != null && array.length > 0){
			for(String[] values : array){
				if(values == null || values.length < 2) continue;
				list.add(new KeyValue(values[0], values[1]));
			}
		}
		return list;
	}
	/*
	 * 加载考试数据。
	 * @see com.examw.test.imports.service.ExamRemoteDataService#loadExams()
	 */
	@Override
	public List<KeyValue>  loadExams() throws Exception {
		if(logger.isDebugEnabled()) logger.debug("加载全部考试数据...");
		List<KeyValue> list = new ArrayList<>();
		String uri = String.format("%s/exams", this.serverUrl);
		//String data = HttpUtil.sendRequest(String.format("%s/exams", this.serverUrl), "GET", null);
		String data = DigestClientUtil.sendDigestRequest(this.username, this.password, "GET", uri, null);
		if(logger.isDebugEnabled()) logger.debug("反馈数据=>" + data);
		ClientExamInfo[] exams =  this.mapper.readValue(data, ClientExamInfo[].class);
		if(exams != null && exams.length > 0){
			for(ClientExamInfo examInfo : exams){
				if(examInfo == null) continue;
				list.add(new KeyValue(examInfo.getId(), examInfo.getName()));
			}
		}
		return list;
	}
	/*
	 * 加载所属试卷数据。
	 * @see com.examw.test.imports.service.PaperRemoteDataService#loadPapers(java.lang.String)
	 */
	@Override
	public List<KeyValue> loadPapers(String examId) throws Exception {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载考试[examId=%s]下的试卷数据...", examId));
		List<KeyValue> list = new ArrayList<>();
		String uri = String.format("%1$s/papers/%2$s", this.serverUrl, examId);
		//String data = HttpUtil.sendRequest(String.format("%1$s/papers/%2$s", this.serverUrl, examId), "GET", null);
		String data = DigestClientUtil.sendDigestRequest(this.username, this.password, "GET", uri, null);
		if(logger.isDebugEnabled()) logger.debug("反馈数据=>" + data);
		ClientPaperInfo[] papers = this.mapper.readValue(data, ClientPaperInfo[].class);
		if(papers != null && papers.length > 0){
			for(ClientPaperInfo paper: papers){
				if(paper == null) continue;
				list.add(new KeyValue(paper.getId(), paper.getName()));
			}
		}
		return list;
	}
	/*
	 * 加载试卷结构数据。
	 * @see com.examw.test.imports.service.PaperStructureRemoteDataService#loadPaperStructures(java.lang.String)
	 */
	@Override
	public List<KeyValueType> loadPaperStructures(String paperId) throws Exception {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试卷[paperId=%s] 的结构数据...", paperId));
		List<KeyValueType> list = new ArrayList<>();
		String uri = String.format("%1$s/structures/%2$s", this.serverUrl, paperId);
		//String data = HttpUtil.sendRequest(String.format("%1$s/structures/%2$s", this.serverUrl, paperId), "GET", null);
		String data = DigestClientUtil.sendDigestRequest(this.username, this.password, "GET", uri, null);
		if(logger.isDebugEnabled()) logger.debug("反馈数据=>" + data);
		ClientStructureInfo[] structureInfos = this.mapper.readValue(data, ClientStructureInfo[].class);
		if(structureInfos != null && structureInfos.length > 0){
			for(ClientStructureInfo structureInfo : structureInfos){
				if(structureInfo == null) continue;
				this.createPaperStructure(0,structureInfo, list);
			}
		}
		return list;
	}
	private void createPaperStructure(int level, ClientStructureInfo info, List<KeyValueType> list){
		if(info == null || list == null) return;
		String title = info.getTitle();
		if(level > 0){
			StringBuilder builder = new StringBuilder();
			for(int i = 0; i < level; i++){
				builder.append("-");
			}
			title = builder.append(">").append(title).toString();
		}
		StructureSubjectCacheUtil.put(info.getId(), info.getSubjectId(), info.getSubjectName());
		list.add(new KeyValueType(info.getId(), title, info.getType()));
		if(info.getChildren() != null && info.getChildren().size() > 0){
			for(ClientStructureInfo child : info.getChildren()){
				if(child == null) continue;
				this.createPaperStructure(level + 1,  child, list);
			}
		}
	}
	/*
	 * 上传试题数据。
	 * @see com.examw.test.imports.service.ItemUploadRemoteService#upload(java.lang.String, com.examw.test.imports.model.ClientUploadItem)
	 */
	@Override
	public boolean upload(String paperId, String structureId, ClientUploadItem data) throws Exception {
		if(logger.isDebugEnabled()) logger.debug(String.format("上传试题数据到试卷［paperId=%1$s］...", paperId));
		if(StringUtils.isEmpty(paperId)) throw new Exception("所属试卷ID为空！");
		if(data == null) throw new Exception("试题数据为NULL！");
		String post = this.mapper.writeValueAsString(data);
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-type","application/json;charset=UTF-8");
		String uri = String.format("%1$s/update/%2$s/%3$s", this.serverUrl, paperId, structureId);
		//String callback = HttpUtil.sendRequest(String.format("%1$s/update/%2$s/%3$s", this.serverUrl, paperId, structureId),headers, "POST", post);
		String callback = DigestClientUtil.sendDigestRequest(this.username, this.password, headers, "POST", uri, post);
		Json json = this.mapper.readValue(callback, Json.class);
		if(json == null){
			throw new Exception("反馈收据转换失败！callback=>" + callback);
		}
		if(json.isSuccess()) return true;
		if(StringUtils.isEmpty(json.getMsg())) return false;
		throw new Exception(json.getMsg());
	}
}