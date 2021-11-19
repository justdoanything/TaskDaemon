package com.yong.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.yaml.snakeyaml.Yaml;

import com.yong.msg.MsgCodeConfiguration;

public class Configuration {

	private final Logger logger =  Logger.getLogger(Configuration.class);
	
	public void initialize() throws Exception {
			PropertyConfigurator.configure(MsgCodeConfiguration.MSG_VALUE_PATH_LOG4J);
			logger.info("Complete to load log4j properties file from " + MsgCodeConfiguration.MSG_VALUE_PATH_LOG4J);
			
			logger.info("Trying to parse application.yml to start this program!");
	}
	
	private void parseApplicationYml() {
		try {
			Map<String, Object> propMap = new Yaml().load(new FileReader(MsgCodeConfiguration.MSG_VALUE_PATH_APPLICATION));
			
		}catch (FileNotFoundException e) { 
			e.printStackTrace(); 
			logger.error("There is a exception in progress : " + e.toString());
		}	
	}
	
}
