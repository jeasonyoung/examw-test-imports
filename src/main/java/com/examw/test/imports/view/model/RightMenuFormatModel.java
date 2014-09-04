package com.examw.test.imports.view.model;

import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import org.springframework.util.StringUtils;

import com.examw.test.imports.service.ItemTypeFormat;
import com.examw.test.imports.service.ItemTypeOPService;

/**
 * 右键菜单－格式化。
 * 
 * @author yangyong
 * @since 2014年9月3日
 */
public class RightMenuFormatModel extends RightMenuBaseModel {
	private static final long serialVersionUID = 1L;
	private ItemTypeOPService itemTypeOPService;
	private Map<String, ItemTypeFormat> formats;
	/**
	 * 构造函数。
	 * @param textArea
	 */
	public RightMenuFormatModel(JTextArea textArea) {
		super(textArea);
	}
	/**
	 * 设置题型操作服务接口。
	 * @param itemTypeOPService 
	 *	  题型操作服务接口。
	 */
	public void setItemTypeOPService(ItemTypeOPService itemTypeOPService) {
		this.itemTypeOPService = itemTypeOPService;
	}
	/**
	 * 设置格式化处理集合。
	 * @param formats 
	 *	  formats
	 */
	public void setFormats(Map<String, ItemTypeFormat> formats) {
		this.formats = formats;
	}
	/*
	 * (non-Javadoc)
	 * @see com.examw.test.imports.view.model.RightMenuBaseModel#handler()
	 */
	@Override
	protected void handler() {
		if(this.itemTypeOPService == null){
			JOptionPane.showMessageDialog(this.textArea, "未配置题型操作！", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String itemTypeValue = this.itemTypeOPService.getSelected();
		if(StringUtils.isEmpty(itemTypeValue)){
			JOptionPane.showMessageDialog(this.textArea, "请选择题型！", "警告", JOptionPane.WARNING_MESSAGE);
			return;
		}
		ItemTypeFormat format = this.formats.get(itemTypeValue);
		if(format == null){
			JOptionPane.showMessageDialog(this.textArea, String.format("未配置题型[type=%s]处理！", itemTypeValue), "警告", JOptionPane.WARNING_MESSAGE);
			return;
		}
		try {
			format.format(this.textArea);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this.textArea, String.format("处理题型［type=%1$s］异常：%2$s", itemTypeValue, e.getMessage()), "警告", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
	}
}