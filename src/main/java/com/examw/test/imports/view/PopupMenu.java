package com.examw.test.imports.view;

import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *  弹出菜单。
 * 
 * @author yangyong
 * @since 2014年9月1日
 */
public class PopupMenu extends JPopupMenu {
	private static final long serialVersionUID = 1L;
	/**
	 *  设置菜单集合。
	 * @param items
	 */
	public void setItems(List<JMenuItem> items){
		if(items == null || items.size() == 0) return;
		for(JMenuItem item : items){
			if(item == null) continue;
			this.add(item);
		}
	}
}