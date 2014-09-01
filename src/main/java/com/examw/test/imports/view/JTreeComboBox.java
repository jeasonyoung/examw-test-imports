package com.examw.test.imports.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.MenuSelectionManager;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalComboBoxUI;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.sun.java.swing.plaf.motif.*;
import com.sun.java.swing.plaf.windows.*;
/**
 * 树形下拉列表框。
 * 
 * @author yangyong
 * @since 2014年9月1日
 */
public class JTreeComboBox<T> extends JComboBox<T> {
	private static final long serialVersionUID = 1L;
	private JTree tree;
	/**
	 * 构造函数。
	 * @param tree
	 */
	public JTreeComboBox(JTree tree){
		this.setTree(tree);
	}
	/**
	 * 构造函数。
	 */
	public JTreeComboBox(){
		this(new JTree());
	}
	/**
	 * 获取树。
	 * @return tree
	 */
	public JTree getTree() {
		return tree;
	}
	/**
	 * 设置树。
	 * @param tree 
	 *	  树。
	 */
	public void setTree(JTree tree) {
		this.tree = tree;
		if(tree != null){
			this.setSelectedItem(tree.getSelectionPath());
			this.setRenderer(new JTreeComboBoxRenderer());
		}
		this.updateUI();
	}
	/**
	 * 设置当前的树路径。
	 * @param o
	 */
	public void setSelectedItem(Object o){
		this.getModel().setSelectedItem(o);
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComboBox#updateUI()
	 */
	@Override
	public void updateUI() {
		 ComboBoxUI cui = (ComboBoxUI)UIManager.getUI(this);
		 if(cui instanceof MetalComboBoxUI){
			 cui = new MetalJTreeComboBoxUI();
		 }else if(cui instanceof MotifComboBoxUI){
			 cui = new MotifJTreeComboBoxUI();
		 }else {
			cui = new WindowsJTreeComboBoxUI();
		}
		this.setUI(cui);
	}
	/**
	 * UI Inner classes - one for each supported look and feel.
	 * 
	 * @author yangyong
	 * @since 2014年9月1日
	 */
	class MetalJTreeComboBoxUI extends MetalComboBoxUI{
		@Override
		protected ComboPopup createPopup() {
			return new TreePopup(comboBox);
		}
	}
	class WindowsJTreeComboBoxUI extends WindowsComboBoxUI{
		@Override
		protected ComboPopup createPopup() {
			return new TreePopup(comboBox);
		}
	}
	class MotifJTreeComboBoxUI extends MotifComboBoxUI{
		private static final long serialVersionUID = 1L;
		@Override
		protected ComboPopup createPopup() {
			return new TreePopup(comboBox);
		}
	}
	/**
	 * JTreeComboBox
	 * 
	 * @author yangyong
	 * @since 2014年9月1日
	 */
	class TreePopup extends JPopupMenu implements ComboPopup{
		private static final long serialVersionUID = 1L;
		private JTreeComboBox<?> comboBox;
		private JScrollPane scrollPane;
		private MouseMotionListener mouseMotionListener;
		private MouseListener mouseListener;
		private JList<?> list = new JList<>();
		private MouseListener treeSelectListener = new MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent e) {
				JTree tree = (JTree)e.getSource();
				TreePath tp = tree.getPathForLocation(e.getPoint().x, e.getPoint().y);
				if(tp == null) return;
				comboBox.setSelectedItem(tp);
				togglePopup();
				MenuSelectionManager.defaultManager().clearSelectedPath();
			};
		};
		/**
		 * 构造函数。
		 * @param comboBox
		 */
		public TreePopup(JComboBox<?> comboBox){
			this.comboBox = (JTreeComboBox<?>)comboBox;
			this.setBorder(BorderFactory.createLineBorder(Color.black));
			this.setLayout(new BorderLayout());
			this.setLightWeightPopupEnabled(comboBox.isLightWeightPopupEnabled());
			JTree tree = this.comboBox.getTree();
			if(tree != null){
				this.scrollPane = new JScrollPane(tree);
				this.scrollPane.setBorder(null);
				this.add(this.scrollPane, BorderLayout.CENTER);
				tree.addMouseListener(this.treeSelectListener);
			}
		}
		/*
		 * (non-Javadoc)
		 * @see java.awt.Component#show()
		 */
		@Override
		public void show() {
			 this.updatePopup();
			 this.show(this.comboBox,0, this.comboBox.getHeight());
			 this.comboBox.getTree().requestFocus();
		}
		/*
		 * (non-Javadoc)
		 * @see javax.swing.JComponent#hide()
		 */
		 @Override
		public void hide() {
			this.setVisible(false);
			this.comboBox.firePropertyChange("popupVisible", true, false);
		}
		 /*
		  * (non-Javadoc)
		  * @see javax.swing.plaf.basic.ComboPopup#getList()
		  */
		@Override
		public JList<?> getList() {
			return this.list;
		}
		/*
		 * (non-Javadoc)
		 * @see javax.swing.plaf.basic.ComboPopup#getMouseMotionListener()
		 */
		@Override
		public MouseMotionListener getMouseMotionListener() {
			 if(this.mouseMotionListener == null){
				 this.mouseMotionListener = new MouseMotionAdapter() {
				};
			 }
			 return this.mouseMotionListener;
		}
		/*
		 * (non-Javadoc)
		 * @see javax.swing.plaf.basic.ComboPopup#getKeyListener()
		 */
		@Override
		public KeyListener getKeyListener() {
			return null;
		}
		/*
		 * (non-Javadoc)
		 * @see javax.swing.plaf.basic.ComboPopup#uninstallingUI()
		 */
		@Override
		public void uninstallingUI() {
			
		}
		/*
		 * (non-Javadoc)
		 * @see javax.swing.plaf.basic.ComboPopup#getMouseListener()
		 */
		@Override
		public MouseListener getMouseListener() {
			if(this.mouseListener == null)
				this.mouseListener = new InvocationMouseHandler();
			return this.mouseListener;
		}
		/**
		 * 
		 */
		protected void togglePopup(){
			if(this.isVisible()){
				this.hide();
			}else {
				this.show();
			}
		}
		/**
		 * 
		 */
		protected void updatePopup(){
			this.setPreferredSize(new Dimension(this.comboBox.getSize().width,200));
			Object selectedObject = this.comboBox.getSelectedItem();
			if(selectedObject != null){
				TreePath tp = (TreePath)selectedObject;
				((JTreeComboBox<?>)this.comboBox).getTree().setSelectionPath(tp);
			}
		}
		/**
		 * 
		 * 
		 * @author yangyong
		 * @since 2014年9月1日
		 */
		protected class InvocationMouseHandler extends MouseAdapter{
			@Override
			public void mousePressed(MouseEvent e) {
				if(!SwingUtilities.isLeftMouseButton(e) || !comboBox.isEnabled()) return;
				if(comboBox.isEditable()){
					Component comp = comboBox.getEditor().getEditorComponent();
					if((!(comp instanceof JComponent)) || ((JComponent)comp).isRequestFocusEnabled()){
						comp.requestFocus();
					}
				} else if(comboBox.isRequestFocusEnabled()){
					comboBox.requestFocus();
				}
				togglePopup();
			}
		}
	}
	/**
	 * 树形结构而来的DefaultListCellRenderer。
	 * 
	 * @author yangyong
	 * @since 2014年9月1日
	 */
	class JTreeComboBoxRenderer extends DefaultListCellRenderer{
		private static final long serialVersionUID = 1L;
		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,boolean cellHasFocus) {
			if(value != null){
				TreePath path = (TreePath)value;
				TreeNode node = (TreeNode)path.getLastPathComponent();
				value = node;
				TreeCellRenderer renderer =  tree.getCellRenderer();
				JLabel lb = (JLabel)renderer.getTreeCellRendererComponent(tree, value, isSelected, false, node.isLeaf(), index, cellHasFocus);
				return lb;
			}
			return super.getListCellRendererComponent(list, value, index, isSelected,cellHasFocus);
		}
	}
}