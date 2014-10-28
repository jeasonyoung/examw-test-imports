package com.examw.test.imports.tests;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.examw.test.imports.service.impl.BaseItemTypeFormat;

public class TestCharTrans {
	private static Logger logger = Logger.getLogger(TestCharTrans.class);
	@Test
	public void testMain(){
		String source = "";
		logger.debug("格式化前："+ source);
	    String target = 	BaseItemTypeFormat.formatPunctuation(source);
	    logger.debug("格式化后："+ target);
	}
}