package com.examw.test.imports.view;

import java.awt.Dimension;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;

import org.apache.log4j.Logger;
/**
 * 模态弹出框。
 * 
 * @author yangyong
 * @since 2014年9月12日
 */
public class ContentDialog extends JDialog {
	private static final long serialVersionUID = 1L; 
	private static final Logger logger = Logger.getLogger(ContentDialog.class);
	/**
	 * 构造函数。
	 * @param frame
	 * 所属主窗体。
	 * @param title
	 * 标题。
	 * @param width
	 * 窗体宽度。
	 * @param heigt
	 * 窗体高度。
	 */
	public ContentDialog(JFrame owner, String title,int width,int height){
		super(owner, title, true);
		if(logger.isDebugEnabled()) logger.debug("构造模态界面UI..");
		this.setSize(new Dimension(width, height));
		if(logger.isDebugEnabled()) logger.debug(String.format("设置模态界面［w=%1$s,h=%2$s］", width, height));
	}
	/**
	 * 设置成员控件集合。
	 * @param members 
	 *	  成员控件集合。
	 */
	public void setMembers(Map<String, JComponent> members) {
		if(members != null && members.size() > 0){
			if(logger.isDebugEnabled()) logger.debug("加载成员控件集合...");
			for(Map.Entry<String, JComponent> entry : members.entrySet()){
				if(logger.isDebugEnabled()) logger.debug(String.format("开始加载[%s]...", entry.getKey()));
				this.add(entry.getValue(),entry.getKey());
			}
		}
	}
}