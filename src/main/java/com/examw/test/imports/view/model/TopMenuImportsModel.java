package com.examw.test.imports.view.model;

import java.awt.event.ActionEvent;  
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.DefaultButtonModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import org.apache.log4j.Logger;

/**
 * 顶部导入试题处理模式。
 * 
 * @author yangyong
 * @since 2014年9月4日
 */
public class TopMenuImportsModel extends DefaultButtonModel {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(TopMenuImportsModel.class);
	private JTextComponent textComponent;
	private String dialogTitle;
	/**
	 * 设置文本编辑器。
	 * @param textComponent 
	 *	  文本编辑器。
	 */
	public void setTextComponent(JTextComponent textComponent) {
		this.textComponent = textComponent;
	}
	/**
	 * 设置文件对话框标题。
	 * @param dialogTitle 
	 *	  文件对话框标题。
	 */
	public void setDialogTitle(String dialogTitle) {
		this.dialogTitle = dialogTitle;
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.DefaultButtonModel#fireActionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	protected void fireActionPerformed(ActionEvent e) { 
		super.fireActionPerformed(e);
		if(this.textComponent == null){
			return;
		}
		JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView());
		fc.setDialogTitle(this.dialogTitle);
		fc.setMultiSelectionEnabled(false);
		if(fc.showOpenDialog(this.textComponent) == JFileChooser.APPROVE_OPTION){
			try {
				Document document = this.textComponent.getDocument();
				BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream(fc.getSelectedFile()),"GBK"));
				if(document.getLength() > 0) document.remove(0, document.getLength());
				String s =  null;
				while((s = buffer.readLine()) != null){
					try {
						document.insertString(document.getLength(), s + "\n", null);
					} catch (BadLocationException e1){}
				}
				buffer.close();
			} catch (FileNotFoundException e1) {
				logger.error("导入文件异常：" + e1.getMessage(), e1);
				JOptionPane.showMessageDialog(this.textComponent, String.format("文件[%s]不存在！", fc.getSelectedFile().getName()), "错误", JOptionPane.WARNING_MESSAGE);
			}catch (BadLocationException e1) {
				logger.error("导入文件内容到Document发生异常：" + e1.getMessage(), e1);
				e1.printStackTrace();
			}catch (IOException e1) {
				logger.error("发生异常：" + e1.getMessage(), e1);
				e1.printStackTrace();
			}
		}
	}
}