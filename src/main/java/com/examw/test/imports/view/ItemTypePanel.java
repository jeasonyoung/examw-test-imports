package com.examw.test.imports.view;

import java.awt.FlowLayout;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;

import com.examw.test.imports.model.KeyValue;
import com.examw.test.imports.service.ItemTypeOPService;
import com.examw.test.imports.service.ItemTypeRemoteDataService;
/**
 * 题型面板。
 * 
 * @author yangyong
 * @since 2014年9月2日
 */
public class ItemTypePanel extends ContentPanel implements ItemTypeOPService {
	private static final long serialVersionUID = 1L;
	private ItemTypeRemoteDataService itemTypeService;
	private ButtonGroup group;
	private Map<String, ItemTypeRadioModel> itemTypeMap;
	/**
	 * 构造函数。
	 */
	public ItemTypePanel() {
		super(new FlowLayout(FlowLayout.LEFT));
		this.group = new ButtonGroup();
		this.itemTypeMap = new HashMap<>();
	}
	/**
	 * 设置 itemTypeService
	 * @param itemTypeService 
	 *	  itemTypeService
	 */
	public void setItemTypeService(ItemTypeRemoteDataService itemTypeService) {
		this.itemTypeService = itemTypeService;
	}
	/*
	 * 加载远程数据。
	 * @see com.examw.test.imports.service.OPService#loadRemoteData(java.lang.String[])
	 */
	@Override
	public void loadRemoteData(String[] parameters) {
		//移除原有数据。
		if(this.group.getButtonCount() > 0){
			Enumeration<AbstractButton> buttons =  this.group.getElements();
			while(buttons.hasMoreElements()){
				AbstractButton button = buttons.nextElement();
				if(button != null){
					this.group.remove(button);
					this.remove(button);
				}
			}
		}
		if(this.itemTypeService == null) return;
		List<KeyValue> itemTypes = this.itemTypeService.loadItemTypes();
		if(itemTypes != null){
			for(KeyValue entry : itemTypes){
				if(entry == null) continue;
				ItemTypeRadioModel model = new ItemTypeRadioModel(entry.getKey());
				this.itemTypeMap.put(entry.getKey(), model);
				JRadioButton radioButton = new JRadioButton(entry.getValue());
				radioButton.setModel(model);
				this.group.add(radioButton);
				this.add(radioButton); 
			}
			this.setVisible(true);
		}
	}
	/**
	 * 设置选中的题型。
	 * @param itemTypeValue
	 */
	@Override
	public void setSelected(String itemTypeValue){
		if(itemTypeValue == null || this.itemTypeMap.size() == 0) return;
		ItemTypeRadioModel model = this.itemTypeMap.get(itemTypeValue);
		if(model != null){
			this.group.setSelected(model, true);
		}
	}
	/**
	 * 获取选中的题型值。
	 * @return
	 */
	@Override
	public String getSelected(){
		if(this.itemTypeMap.size() == 0) return null;
		ItemTypeRadioModel model = (ItemTypeRadioModel)this.group.getSelection();
		if(model != null) return model.getValue();
		return null;
	}
	/**
	 * 题型Radio模型。
	 * 
	 * @author yangyong
	 * @since 2014年9月2日
	 */
	 class ItemTypeRadioModel extends JToggleButton.ToggleButtonModel{
		private static final long serialVersionUID = 1L;
		private String value;
		/**
		 * 构造函数。
		 * @param value
		 */
		public ItemTypeRadioModel(String value){
			super();
			this.value = value;
		}
		/**
		 * 获取题型值。
		 * @return 题型值。
		 */
		public String getValue() {
			return value;
		}
	}
}