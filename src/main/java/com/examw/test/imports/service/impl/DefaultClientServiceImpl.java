package com.examw.test.imports.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.examw.test.imports.model.KeyValue;
import com.examw.test.imports.model.KeyValueType;
import com.examw.test.imports.service.ExamRemoteDataService;
import com.examw.test.imports.service.ItemTypeRemoteDataService;
import com.examw.test.imports.service.PaperRemoteDataService;
import com.examw.test.imports.service.PaperStructureRemoteDataService;
import com.examw.test.imports.shiro.service.UserAuthentication;
/**
 *  客户端服务实现。
 * 
 * @author yangyong
 * @since 2014年9月3日
 */
public class DefaultClientServiceImpl implements UserAuthentication,ItemTypeRemoteDataService,ExamRemoteDataService,PaperRemoteDataService,PaperStructureRemoteDataService {
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