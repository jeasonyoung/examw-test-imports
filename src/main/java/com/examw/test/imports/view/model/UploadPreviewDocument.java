package com.examw.test.imports.view.model;

import java.io.IOException;
import java.util.Map;

import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.StringUtils;

import com.examw.test.imports.model.ClientUploadItem;
import com.examw.test.imports.service.ItemHtmlPreview;
import com.examw.test.imports.service.TabbedContentBroadcast;
import com.examw.test.imports.service.TabbedContentBroadcastUpdate;
import com.examw.test.imports.service.UploadItemPreview;

/**
 * 上传预览显示模型处理。
 * 
 * @author yangyong
 * @since 2014年9月15日
 */
public class UploadPreviewDocument extends PlainDocument implements TabbedContentBroadcast,UploadItemPreview  {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(UploadPreviewDocument.class);
	private TabbedContentBroadcastUpdate broadcastUpdate;
	private Map<String, ItemHtmlPreview> itemHtmlPreviews;
	private ObjectMapper mapper;
	/**
	 * 构造函数。
	 */
	private UploadPreviewDocument(){
		super();
		this.mapper = new ObjectMapper();
	}
	/**
	 * 设置题目Html预览集合。
	 * @param itemHtmlPreviews 
	 *	  题目Html预览集合。
	 */
	public void setItemHtmlPreviews(Map<String, ItemHtmlPreview> itemHtmlPreviews) {
		this.itemHtmlPreviews = itemHtmlPreviews;
	}
	/**
	 * 设置Tabs广播更新。
	 * @param broadcastUpdate 
	 *	  Tabs广播更新。
	 */
	public void setBroadcastUpdate(TabbedContentBroadcastUpdate broadcastUpdate) {
		this.broadcastUpdate = broadcastUpdate;
	}
	/**
	 * 写入题目源数据。
	 * @param clientUploadItem
	 */
	@Override
	public void writeItemJSON(ClientUploadItem clientUploadItem) {
		if(clientUploadItem != null){
			 try {
				if(this.getLength() > 0) this.remove(0, this.getLength());
				String content = this.mapper.writeValueAsString(clientUploadItem);
				if(!StringUtils.isEmpty(content)){
					this.insertString(0, content, null);
					if(this.broadcastUpdate != null){
						this.broadcastUpdate.updateBroadcast();
					}
				}
			} catch (IOException | BadLocationException e) {
				logger.error("写入源数据异常:" + e.getMessage(), e);
				e.printStackTrace();
			}
		}
	}
	/*
	 * (non-Javadoc)
	 * @see com.examw.test.imports.service.TabbedContentBroadcast#contentBroadcat(java.lang.Integer, java.lang.String)
	 */
	@Override
	public String contentBroadcat(Integer index, String content) {
		if(logger.isDebugEnabled()) logger.debug("开始内容广播转换...");
		if(StringUtils.isEmpty(content) || this.itemHtmlPreviews == null || this.itemHtmlPreviews.size() == 0) return null;
		ClientUploadItem clientUploadItem = null;
		try {
			clientUploadItem = this.mapper.readValue(content, ClientUploadItem.class);
		} catch (JsonParseException e) { 
			e.printStackTrace();
		} catch (JsonMappingException e) { 
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
		}
		if(clientUploadItem == null) return null;
		ItemHtmlPreview preview = this.itemHtmlPreviews.get(clientUploadItem.getType().toString());
		if(preview == null) return null;
		return preview.htmlPreview(clientUploadItem);
	}
}