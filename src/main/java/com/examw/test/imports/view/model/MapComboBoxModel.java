package com.examw.test.imports.view.model;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * 可接受Map的组合框。
 * 
 * @author yangyong
 * @since 2014年8月30日
 */
public class MapComboBoxModel extends BaseComboBoxModel<Map<?,?>> {
	private List<?> index;
	/**
	 * 构造函数。
	 * @param data
	 */
	public MapComboBoxModel() {
		super();
		this.index = new ArrayList<>();
	}
	/*
	 * (non-Javadoc)
	 * @see com.examw.test.imports.view.model.BaseComboBoxModel#setData(java.lang.Object)
	 */
	@Override
	public void setData(Map<?, ?> data) {
		 if(data != null){
		 	this.buildIndex();
			if(this.index.size() > 0){
				this.selected = this.index.get(0);
			}
		 }
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		if(this.data != null){
			return this.data.size();
		}
		return -1;
	}
	//构建索引。
	private void buildIndex(){
		if(data != null){
			this.index = new ArrayList<>(this.data.keySet());
		}
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Object getElementAt(int index) {
		return this.index.get(index);
	}
	/*
	 * (non-Javadoc)
	 * @see com.examw.test.imports.view.model.BaseComboBoxModel#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		 if(e.getActionCommand().equals("update")){
			 this.buildIndex();
			 this.fireUpdate();
		 }
	}
	/**
	 * 获取选中的对象。
	 * @param selectedItem
	 * @return
	 */
	public Object getValue(Object selectedItem){
		return this.data.get(selectedItem);
	}
	/**
	 * 获取选中的对象。
	 * @param selectedItem
	 * @return
	 */
	public Object getValue(int selectedItem){
		return this.getValue(this.index.get(selectedItem));
	}
}