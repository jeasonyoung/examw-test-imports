package com.examw.test.imports;

import org.apache.log4j.Logger;

/**
 * 试卷批量导入运行主类
 * @author yangyong
 * @since 2014年8月25日
 */
public class ImportRunMain {
	private static final Logger logger = Logger.getLogger(ImportRunMain.class);
	/**
	 * 运行入口类。
	 * @param args
	 */
	public static void main(String[] args) {
		if(logger.isDebugEnabled()) logger.debug("主程序入口！");
		try{
			Launcher launcher = new Launcher();
			launcher.Start();
		}catch(Exception e){
			logger.error("发生异常：", e);
			e.printStackTrace();
		}
	}
}