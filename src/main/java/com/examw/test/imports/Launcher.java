package com.examw.test.imports;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 装载器类。
 * 
 * @author yangyong
 * @since 2014年8月25日
 */
public class Launcher {
	private static final Logger logger = Logger.getLogger(Launcher.class);
	private ApplicationContext context;
	/**
	 * 获取应用上下文。
	 * @return 应用上下文。
	 */
	public ApplicationContext getContext(){
		return this.context;
	}
	/**
	 * 加载启动。
	 */
	public void Start(){
		String configLocations[] = new String[]{"spring-imports.xml"};
		if(logger.isDebugEnabled()) logger.debug(String.format("加载Spring配置文件：%s", Arrays.toString(configLocations)));
		this.context =  new ClassPathXmlApplicationContext(configLocations);
	}
}