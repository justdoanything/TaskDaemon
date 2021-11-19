package com.yong.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
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
			this.parseApplicationYml();
	}
	
	private void parseApplicationYml() {
		try {
			Map<String, Object> yml = new Yaml().load(new FileReader(MsgCodeConfiguration.MSG_VALUE_PATH_APPLICATION));
			List envList = (List) ((Map) yml.get("environment")).get("envList");
			logger.info("There is " + envList.size() + " environment in application.yml");
			for(Object env : envList) {
				logger.info(env.toString());
			}
			
		}catch (FileNotFoundException e) { 
			e.printStackTrace(); 
			logger.error("There is a exception in progress : " + e.toString());
		}	
	}
	
}
