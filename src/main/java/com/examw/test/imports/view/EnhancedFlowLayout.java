package com.examw.test.imports.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;

/**
 * 增强的流布局。
 * 
 * @author yangyong
 * @since 2014年9月2日
 */
public class EnhancedFlowLayout extends FlowLayout {
	private static final long serialVersionUID = 1L;
	/**
	 * 构造函数。
	 */
	public EnhancedFlowLayout(){
		super();
	}
	/**
	 * 构造函数。
	 * @param align
	 * 对齐方式。
	 */
	public EnhancedFlowLayout(int align){
		super(align);
	}
	/**
	 * 构造函数。
	 * @param align
	 * @param hgap
	 * @param vgap
	 */
	public EnhancedFlowLayout(int align,int hgap, int vgap){
		super(align, hgap, vgap);
	}
	/*
	 * (non-Javadoc)
	 * @see java.awt.FlowLayout#minimumLayoutSize(java.awt.Container)
	 */
	@Override
	public Dimension minimumLayoutSize(Container target) {    
        return computeSize(target, true);    
    }    
	/*
	 * (non-Javadoc)
	 * @see java.awt.FlowLayout#preferredLayoutSize(java.awt.Container)
	 */
    @Override
    public Dimension preferredLayoutSize(Container target) {    
        return computeSize(target, false);    
    } 
    //计算尺寸。
	private Dimension computeSize(Container target, boolean minimum) {    
        synchronized (target.getTreeLock()) {    
            int hgap = getHgap(), vgap = getVgap(), w = target.getWidth();    
            if (w == 0) w = Integer.MAX_VALUE;
    
            Insets insets = target.getInsets();    
            if (insets == null)  insets = new Insets(0, 0, 0, 0);
            int reqdWidth = 0;    
    
            int maxwidth = w - (insets.left + insets.right + hgap * 2);    
            int n = target.getComponentCount();    
            int x = 0;    
            int y = insets.top;    
            int rowHeight = 0;    
    
            for (int i = 0; i < n; i++) {    
                Component c = target.getComponent(i);    
                if (c.isVisible()) {    
                    Dimension d = minimum ? c.getMinimumSize() : c.getPreferredSize();    
                    if ((x == 0) || ((x + d.width) <= maxwidth)) {    
                        if (x > 0)  x += hgap;
                        x += d.width;    
                        rowHeight = Math.max(rowHeight, d.height);    
                    } else {    
                        x = d.width;    
                        y += vgap + rowHeight;    
                        rowHeight = Math.max(rowHeight, d.height);    
                    }    
                    reqdWidth = Math.max(reqdWidth, x);    
                }    
            }    
            y += rowHeight;  
            return new Dimension(reqdWidth + insets.left + insets.right, y + insets.bottom + insets.top);    
        }    
	}
}