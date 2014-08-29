package com.examw.test.imports.view;

import java.awt.Component;
import java.awt.LayoutManager;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
/**
 * 内容面板类。
 * 
 * @author yangyong
 * @since 2014年8月29日
 */
public class ContentPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ContentPanel.class);
	/**
	 * 构造函数。
	 * @param layout
	 */
	public ContentPanel(LayoutManager layout){
		super(layout);
		if(logger.isDebugEnabled()) logger.debug("构造内容面板...");
	}
	/**
	 * 设置成员控件集合。
	 * @param members 
	 *	  成员控件集合。
	 */
	public void setMemberMap(Map<String,Component> members) {
		if(members != null && members.size() > 0){
			if(logger.isDebugEnabled()) logger.debug("加载成员组件...");
			for(Map.Entry<String, Component> entry : members.entrySet()){
				if(logger.isDebugEnabled()) logger.debug(String.format("开始加载[%s]...", entry.getKey()));
				this.add(entry.getValue(),entry.getKey());
			}			
		}
	}
	/**
	 * 设置成员控件集合。
	 * @param members
	 */
	public void setMemberList(List<Component> members){
		if(members != null && members.size() > 0){
			if(logger.isDebugEnabled()) logger.debug("加载成员组件...");
			for(Component component : members){
				if(component == null) continue;
				this.add(component);
			}
		}
	}
	/**
	 * 判断字符串是否为数字。
	 * @param value
	 * @return
	 */
	protected static boolean isNumeric(String value){
		if(StringUtils.isEmpty(value)) return false;
		Pattern pattern = Pattern.compile("^(-)?[0-9]+$");
		return pattern.matcher(value).matches();
	}
}