package com.examw.test.imports.view;

import java.util.List;

import javax.swing.JMenuBar;

/**
 * UI 菜单Bar。
 * 
 * @author yangyong
 * @since 2014年9月1日
 */
public class MenuBars extends JMenuBar {
	private static final long serialVersionUID = 1L;
	/**
	 * 设置菜单
	 * @param menus
	 */
	public void setMenus(List<Menu> menus){
		if(menus != null && menus.size() > 0){
			for(Menu menu : menus){
				if(menu == null) continue;
				this.add(menu);
			}
		}
	}
}