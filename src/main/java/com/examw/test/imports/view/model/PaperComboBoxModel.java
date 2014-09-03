package com.examw.test.imports.view.model;

import java.util.List;

import javax.swing.DefaultComboBoxModel;

import com.examw.test.imports.model.KeyValue;
import com.examw.test.imports.service.PaperOPService;
import com.examw.test.imports.service.PaperRemoteDataService;

/**
 * 所属试卷下拉列表处理模型。
 * 
 * @author yangyong
 * @since 2014年9月3日
 */
public class PaperComboBoxModel extends DefaultComboBoxModel<KeyValue> implements PaperOPService {
	private static final long serialVersionUID = 1L;
	private PaperRemoteDataService paperRemoteDataService;
	/**
	 * 设置试卷远程数据服务接口。
	 * @param paperRemoteDataService 
	 *	  试卷远程数据服务接口。
	 */
	public void setPaperRemoteDataService(PaperRemoteDataService paperRemoteDataService) {
		this.paperRemoteDataService = paperRemoteDataService;
	}
	/*
	 * 加载远程数据。
	 * @see com.examw.test.imports.service.OPService#loadRemoteData(java.lang.String[])
	 */
	@Override
	public void loadRemoteData(String[] parameters) {
		if(this.getSize() > 0) this.removeAllElements();
		if(this.paperRemoteDataService != null){
			List<KeyValue> papers = this.paperRemoteDataService.loadPapers(parameters == null ? null : parameters[0]);
			if(papers != null && papers.size() > 0){
				for(KeyValue kv : papers){
					this.addElement(kv);
				}
			}
		}
	}
	/*
	 * 选中所属试卷的值。
	 * @see com.examw.test.imports.service.ExamOPService#getSelected()
	 */
	@Override
	public String getSelected() {
		KeyValue kv = (KeyValue)this.getSelectedItem();
		if(kv != null) return kv.getKey();
		return null;
	}
}