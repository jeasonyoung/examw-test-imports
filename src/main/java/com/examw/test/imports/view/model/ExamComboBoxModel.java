package com.examw.test.imports.view.model;

import java.util.List;

import javax.swing.DefaultComboBoxModel;

import com.examw.test.imports.model.KeyValue;
import com.examw.test.imports.service.ExamOPService;
import com.examw.test.imports.service.ExamRemoteDataService;
/**
 * 考试下拉列表处理模型。
 * 
 * @author yangyong
 * @since 2014年9月3日
 */
public class ExamComboBoxModel extends DefaultComboBoxModel<KeyValue> implements ExamOPService {
	private static final long serialVersionUID = 1L;
	private ExamRemoteDataService examRemoteDataService;
	/**
	 * 设置考试远程数据服务接口。
	 * @param examRemoteDataService 
	 *	  考试远程数据服务接口。
	 */
	public void setExamRemoteDataService(ExamRemoteDataService examRemoteDataService) {
		this.examRemoteDataService = examRemoteDataService;
	}
	/*
	 * 加载远程数据。
	 * @see com.examw.test.imports.service.OPService#loadRemoteData(java.lang.String[])
	 */
	@Override
	public void loadRemoteData(String[] parameters) {
		if(this.examRemoteDataService != null){
			if(this.getSize() > 0) this.removeAllElements();
			List<KeyValue> exams = this.examRemoteDataService.loadExams();
			if(exams != null && exams.size() > 0){
				for(KeyValue entry : exams){
					this.addElement(entry);
				}
			}
		}
	}
	/*
	 * 选中所属考试的值。
	 * @see com.examw.test.imports.service.ExamOPService#getSelected()
	 */
	@Override
	public String getSelected() {
		KeyValue kv = (KeyValue)this.getSelectedItem();
		if(kv != null) return kv.getKey();
		return null;
	}
}