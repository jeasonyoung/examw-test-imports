package com.examw.test.imports.support;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

/**
 * 界面视图工具类。
 * 
 * @author yangyong
 * @since 2014年8月27日
 */
public final class ViewUtils {
	/**
	 * 将窗体定位再屏幕中央。
	 * @param owner
	 * 窗体对象。
	 */
	public static void positionCenterWindow(Window owner){
		if(owner == null) return;
		Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();//获取显示器大小对象。
		int width = owner.getWidth(),height = owner.getHeight();
		//窗体大小不能大于显示器大小
		if(width > displaySize.width) width = displaySize.width;
		if(height > displaySize.height) height = displaySize.height;
		owner.setSize(new Dimension(width, height));
		//设置窗体居中显示器显示
		owner.setLocation((displaySize.width - width) /2, (displaySize.height - height)/2);
	}
}