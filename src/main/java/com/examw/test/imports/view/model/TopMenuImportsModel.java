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
import javax.swing.JTextArea; 
import javax.swing.filechooser.FileSystemView;

/**
 * 顶部导入试题处理模式。
 * 
 * @author yangyong
 * @since 2014年9月4日
 */
public class TopMenuImportsModel extends DefaultButtonModel {
	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	private String dialogTitle;
	/**
	 * 设置文本编辑器。
	 * @param textArea 
	 *	  文本编辑器。
	 */
	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
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
		if(this.textArea == null){
			return;
		}
		JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView());
		fc.setDialogTitle(this.dialogTitle);
		fc.setMultiSelectionEnabled(false);
		if(fc.showOpenDialog(this.textArea) == JFileChooser.APPROVE_OPTION){
			try {
				BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream(fc.getSelectedFile()),"GBK"));
				this.textArea.setText("");
				String s =  null;
				while((s = buffer.readLine()) != null){
					this.textArea.append(s + "\n");
				}
				buffer.close();
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(this.textArea, String.format("文件[%s]不存在！", fc.getSelectedFile().getName()), "错误", JOptionPane.WARNING_MESSAGE);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}