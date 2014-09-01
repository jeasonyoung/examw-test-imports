package com.examw.test.imports.view.model;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
/**
 * 组合框模型基础抽象类。
 * 
 * @author yangyong
 * @since 2014年9月1日
 */
public abstract class BaseComboBoxModel<T> implements ComboBoxModel<Object>,ActionListener {
	private List<ListDataListener> listeners;
	protected Object selected;
	protected T data;
	/**
	 * 构造函数。
	 */
	public BaseComboBoxModel(){
		this.listeners = new ArrayList<ListDataListener>();
	}
	/**
	 * 构造函数。
	 */
	public BaseComboBoxModel(T data){
		this();
		this.setData(data);
	}
	/**
	 * 设置数据。
	 * @param data 
	 *	  数据。
	 */
	public abstract void setData(T data);
	/*
	 * (non-Javadoc)
	 * @see javax.swing.ComboBoxModel#setSelectedItem(java.lang.Object)
	 */
	@Override
	public void setSelectedItem(Object item) {
		this.selected = item;
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.ComboBoxModel#getSelectedItem()
	 */
	@Override
	public Object getSelectedItem() {
		return this.selected;
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.ListModel#addListDataListener(javax.swing.event.ListDataListener)
	 */
	@Override
	public void addListDataListener(ListDataListener l) {
		this.listeners.add(l);
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.ListModel#removeListDataListener(javax.swing.event.ListDataListener)
	 */
	@Override
	public void removeListDataListener(ListDataListener l) {
		this.listeners.remove(l);
	}
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		 if(e.getActionCommand().equals("update")){
			 this.fireUpdate();
		 }
	}
	//触发更新。
	protected void fireUpdate(){
		ListDataEvent e = new ListDataEvent(this,ListDataEvent.CONTENTS_CHANGED, 0, this.getSize());
		for(int i = 0; i < this.listeners.size();  i++){
			ListDataListener l = this.listeners.get(i);
			if(l != null) l.contentsChanged(e);
		}
	}
	/**
	 * 更新数据。
	 * @param component
	 */
	public void update(Component component){
		if(component == null) return;
		this.actionPerformed(new ActionEvent(component, 0, "update"));
	}
}