package com.yong.common;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.yong.util.MsgCode;

public class Configuration {

	private static Logger logger = null;
	private static Properties properties = new Properties();
	
	public static void initialize(String path) throws Exception {
		try {
			// set application properties(context.properties)
			FileInputStream fis = new FileInputStream(path);
			properties.load(fis);
			
			// set Log4j properties
			String LOG4J_CONFIG_FILE_PATH = Configuration.getString(MsgCode.CONF_KEY_PATH_LOG4J);
			PropertyConfigurator.configure(LOG4J_CONFIG_FILE_PATH);
			logger = Logger.getLogger(Configuration.class);
			logger.info("[SUCCESS] Load App Properties File : " + path);
			logger.info("[SUCCESS] Load Log4j Properties File : " + LOG4J_CONFIG_FILE_PATH);
		} catch (Exception e) {
			logger.info("[Exception] Fail to App Initialize : " + e.toString());
			throw new Exception(e);
		}
	}
	
	public static String getString(String key) throws Exception {
		return properties.getProperty(key).trim();
	}
	
	public static boolean getBoolean(String key) throws Exception {
		if(properties.getProperty(key).trim().equals("1"))
			return true;
		else
			return false;
	}
	
	public static int getInt(String key) throws Exception {
		try {
			return Integer.parseInt(properties.getProperty(key).trim());
		} catch (Exception e) {
			logger.info("Exception in getting properties - Integer Parsing Exception" + e.toString());
			throw new Exception(e);
		}
	}
}
