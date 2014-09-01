package com.examw.test.imports.view.model;
  
import java.util.List;
/**
 * 可接受列表的组合框。
 * 
 * @author yangyong
 * @since 2014年8月30日
 */
public class ListComboBoxModel extends BaseComboBoxModel<List<?>>{
	/*
	 * (non-Javadoc)
	 * @see com.examw.test.imports.view.model.BaseComboBoxModel#setData(java.lang.Object)
	 */
	@Override
	public void setData(List<?> data) {
		 if(data != null){
			 this.data = data;
			 if(data.size() > 0){
				 this.selected = data.get(0);
			 }
		 }
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Object getElementAt(int index) {
		return this.data.get(index);
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return this.data.size();
	}
}