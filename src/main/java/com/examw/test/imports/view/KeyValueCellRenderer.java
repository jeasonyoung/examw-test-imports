package com.examw.test.imports.view;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.examw.test.imports.model.KeyValue;

/**
 * JComboBox的绘制。
 * 
 * @author yangyong
 * @since 2014年9月3日
 */
public class KeyValueCellRenderer implements ListCellRenderer<KeyValue>{
	protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	/*
	 * (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(JList<? extends KeyValue> list, KeyValue value, int index, boolean isSelected, boolean cellHasFocus) {
		return defaultRenderer.getListCellRendererComponent(list, value == null ? value : value.getValue(), index, isSelected, cellHasFocus);
	}
}