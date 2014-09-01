package com.examw.test.imports.view;

import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
/**
 * 菜单。
 * 
 * @author yangyong
 * @since 2014年9月1日
 */
public class Menu extends JMenu {
	private static final long serialVersionUID = 1L;
	/**
	 * 构造函数。
	 * @param text
	 */
	public Menu(String text){
		super(text);
	}
	/**
	 * 设置菜单项集合。
	 * @param items
	 */
	public void setItems(List<JMenuItem> items){
		if(items != null && items.size() > 0){
			 for(JMenuItem item : items){
				 if(item == null) continue;
				 this.add(item);
			 }
		}
	}
}