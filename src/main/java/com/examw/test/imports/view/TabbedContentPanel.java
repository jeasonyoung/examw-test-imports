package com.examw.test.imports.view;

import java.awt.Component;
import java.util.List;
import java.util.Map;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import org.springframework.util.StringUtils;

import com.examw.test.imports.service.TabbedContentBroadcast;
import com.examw.test.imports.service.TabbedContentBroadcastUpdate;

/**
 * html 编辑器
 * 
 * @author yangyong
 * @since 2014年9月9日
 */
public class TabbedContentPanel extends JTabbedPane implements ChangeListener,TabbedContentBroadcastUpdate {
	private static final long serialVersionUID = 1L;
	private Document document;
	private TabbedContentBroadcast contentBroadcast;
	private List<JTextComponent> textComponents;
	/**
	 * 构造函数。
	 * @param tabPlacement
	 */
	public TabbedContentPanel(int tabPlacement){
		 super(tabPlacement, JTabbedPane.WRAP_TAB_LAYOUT);
		 this.addChangeListener(this);
	}
	/**
	 * 构造函数。
	 */
	public TabbedContentPanel(){
		this(JTabbedPane.TOP);
	}
	/**
	 * 设置选项集合。
	 * @param tabs
	 */
	public void setTabs(Map<String, Component> tabs){
		if(tabs != null && tabs.size() > 0){
			for(Map.Entry<String, Component> entry : tabs.entrySet()){
				if(entry == null || StringUtils.isEmpty(entry.getKey()) || entry.getValue() == null) continue;
				this.addTab(entry.getKey(), null, entry.getValue(), entry.getKey());
			}
		}
	}
	/**
	 * 设置触发源文档接口对象。
	 * @param document 
	 *	  文档接口对象。
	 */
	public void setDocument(Document document) {
		this.document = document;
	}
	/**
	 * 设置更新文档对象集合。
	 * @param textComponents 
	 *	  文档对象集合。
	 */
	public void setTextComponents(List<JTextComponent> textComponents) {
		this.textComponents = textComponents;
	}
	/**
	 * 设置内容广播转换。
	 * @param contentBroadcast 
	 *	  内容广播转换。
	 */
	public void setContentBroadcast(TabbedContentBroadcast contentBroadcast) {
		this.contentBroadcast = contentBroadcast;
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		TabbedContentPanel panel = (TabbedContentPanel)e.getSource();
		if(panel != null){
			 int index =	panel.getSelectedIndex();
			 if(index > -1){
				this.updateBroadcast();
			 }
		}
	}
	/**
	 * 更新广播。
	 */
	@Override
	public void updateBroadcast() {
		if(this.document != null && this.textComponents != null && this.textComponents.size() > 0){
			 try {
					String text = this.document.getText(0, this.document.getLength());
					if(!StringUtils.isEmpty(text)){
						JTextComponent textComponent = null;
						for(int i = 0; i < this.textComponents.size(); i++){
							if((textComponent = this.textComponents.get(i)) == null) continue;
							if(this.contentBroadcast != null){
								text = this.contentBroadcast.contentBroadcat(i, text);
							}
							textComponent.setText(text);
						}
					}
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
		}
	}
}