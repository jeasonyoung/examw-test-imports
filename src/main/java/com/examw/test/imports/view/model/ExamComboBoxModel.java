package com.examw.test.imports.view.model;

import java.util.List;

import javax.swing.DefaultComboBoxModel;

import org.apache.log4j.Logger;

import com.examw.test.imports.model.KeyValue;
import com.examw.test.imports.service.ExamOPService;
import com.examw.test.imports.service.ExamRemoteDataService;
import com.examw.test.imports.service.PaperOPService;
/**
 * 考试下拉列表处理模型。
 * 
 * @author yangyong
 * @since 2014年9月3日
 */
public class ExamComboBoxModel extends DefaultComboBoxModel<KeyValue> implements ExamOPService {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ExamComboBoxModel.class);
	private ExamRemoteDataService examRemoteDataService;
	private PaperOPService paperOPService;
	/**
	 * 设置考试远程数据服务接口。
	 * @param examRemoteDataService 
	 *	  考试远程数据服务接口。
	 */
	public void setExamRemoteDataService(ExamRemoteDataService examRemoteDataService) {
		this.examRemoteDataService = examRemoteDataService;
	}
	/**
	 * 设置试卷操作服务接口。
	 * @param paperOPService 
	 *	  试卷操作服务接口。
	 */
	public void setPaperOPService(PaperOPService paperOPService) {
		this.paperOPService = paperOPService;
	}
	/*
	 * 加载远程数据。
	 * @see com.examw.test.imports.service.OPService#loadRemoteData(java.lang.String[])
	 */
	@Override
	public void loadRemoteData(String[] parameters) {
		if(this.getSize() > 0) this.removeAllElements();
		if(this.examRemoteDataService != null){
			List<KeyValue> exams = null;
			try {
				exams = this.examRemoteDataService.loadExams();
			} catch (Exception e) {
				logger.error("加载远程考试数据异常：" + e.getMessage(), e);
				e.printStackTrace();
			}
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
	/*
	 * (non-Javadoc)
	 * @see javax.swing.AbstractListModel#fireContentsChanged(java.lang.Object, int, int)
	 */
	@Override
	protected void fireContentsChanged(Object source, int index0, int index1) {
		super.fireContentsChanged(source, index0, index1);
		ExamComboBoxModel model = (ExamComboBoxModel)source;
		if(model != null && this.paperOPService != null){ 
				this.paperOPService.loadRemoteData(new String[]{ model.getSelected() }); 
		}
	}
}