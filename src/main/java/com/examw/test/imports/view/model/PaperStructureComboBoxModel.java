package com.examw.test.imports.view.model;

import java.util.List;

import javax.swing.DefaultComboBoxModel;

import org.apache.log4j.Logger;

import com.examw.test.imports.model.KeyValueType;
import com.examw.test.imports.service.ItemTypeOPService;
import com.examw.test.imports.service.PaperStructureOPService;
import com.examw.test.imports.service.PaperStructureRemoteDataService;

/**
 * 试卷结构下来处理模型。
 * 
 * @author yangyong
 * @since 2014年9月4日
 */
public class PaperStructureComboBoxModel extends DefaultComboBoxModel<KeyValueType> implements PaperStructureOPService {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PaperStructureComboBoxModel.class);
	private PaperStructureRemoteDataService paperStructureRemoteDataService;
	private ItemTypeOPService itemTypeOPService;
	/**
	 * 设置试卷结构远程数据服务接口。
	 * @param paperStructureRemoteDataService 
	 *	  试卷结构远程数据服务接口。
	 */
	public void setPaperStructureRemoteDataService(PaperStructureRemoteDataService paperStructureRemoteDataService) {
		this.paperStructureRemoteDataService = paperStructureRemoteDataService;
	}
	/**
	 * 设置题型操作服务接口。
	 * @param itemTypeOPService 
	 *	  题型操作服务接口。
	 */
	public void setItemTypeOPService(ItemTypeOPService itemTypeOPService) {
		this.itemTypeOPService = itemTypeOPService;
	}
	/*
	 * 加载远程数据。
	 * @see com.examw.test.imports.service.OPService#loadRemoteData(java.lang.String[])
	 */
	@Override
	public void loadRemoteData(String[] parameters) {
		if(this.getSize() > 0) this.removeAllElements();
		if(this.paperStructureRemoteDataService != null){
			List<KeyValueType> structures = null;
			try {
				structures = this.paperStructureRemoteDataService.loadPaperStructures(parameters == null ? null : parameters[0]);
			} catch (Exception e) {
				logger.debug("加载试卷结构远程数据异常：" + e.getMessage(), e);
				e.printStackTrace();
			}
			if(structures != null && structures.size() > 0){
				for(KeyValueType kvt : structures){
					this.addElement(kvt);
				}
			}
		}
	}
	/*
	 * 选中的值。
	 * @see com.examw.test.imports.service.PaperStructureOPService#getSelected()
	 */
	@Override
	public KeyValueType getSelected() {
		return (KeyValueType)this.getSelectedItem();
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.AbstractListModel#fireContentsChanged(java.lang.Object, int, int)
	 */
	@Override
	protected void fireContentsChanged(Object source, int index0, int index1) {
		super.fireContentsChanged(source, index0, index1);
		PaperStructureComboBoxModel model = (PaperStructureComboBoxModel)source;
		if(model != null && this.itemTypeOPService != null){
			KeyValueType data = model.getSelected();
			if(data != null){
				this.itemTypeOPService.setSelected(data.getType() == null ? "" : data.getType().toString());
			}
		}
	}
}