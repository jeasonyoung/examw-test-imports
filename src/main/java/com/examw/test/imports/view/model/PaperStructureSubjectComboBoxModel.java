package com.examw.test.imports.view.model;

import java.util.List;

import javax.swing.DefaultComboBoxModel;

import org.apache.log4j.Logger;

import com.examw.test.imports.model.KeyValue;
import com.examw.test.imports.service.PaperStructureSubjectOPService;
import com.examw.test.imports.support.StructureSubjectCacheUtil;

/**
 * 试卷结构科目处理模型。
 * 
 * @author yangyong
 * @since 2014年12月8日
 */
public class PaperStructureSubjectComboBoxModel extends DefaultComboBoxModel<KeyValue> implements PaperStructureSubjectOPService {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PaperStructureSubjectComboBoxModel.class);
	/*
	 * 加载数据。
	 * @see com.examw.test.imports.service.OPService#loadRemoteData(java.lang.String[])
	 */
	@Override
	public void loadRemoteData(String[] parameters) {
		if(this.getSize() > 0) this.removeAllElements();
		if(parameters != null && parameters.length > 0){
			List<KeyValue> list = StructureSubjectCacheUtil.load(parameters[0]);
			if(list != null && list.size() > 0){
				for(KeyValue kv : list){
					this.addElement(kv);
				}
			}
		}
	}
	/*
	 * 选中的值。
	 * @see com.examw.test.imports.service.PaperStructureSubjectOPService#getSelected()
	 */
	@Override
	public KeyValue getSelected() {
		if(logger.isDebugEnabled()) logger.debug("选中的值...");
		return (KeyValue)this.getSelectedItem();
	}
}