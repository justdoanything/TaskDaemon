package com.yong.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.yaml.snakeyaml.Yaml;

import com.yong.msg.MsgCodeConfiguration;
import com.yong.util.CommonUtil;

@SuppressWarnings({ "unused", "rawtypes" })
public class Configuration {

	private final Logger logger =  Logger.getLogger(Configuration.class);
		
	public void initialize() throws Exception {
			PropertyConfigurator.configure(MsgCodeConfiguration.MSG_VALUE_PATH_LOG4J);
			logger.info("Complete to load log4j properties file from " + MsgCodeConfiguration.MSG_VALUE_PATH_LOG4J);
			logger.info("Trying to parse application.yml to start this program!");
			this.parseApplicationYml();
			logger.info("Complete to load application.yml!");
	}
	
	private void parseApplicationYml() {
		try {
			Map<String, Object> yml = new Yaml().load(new FileReader(MsgCodeConfiguration.MSG_VALUE_PATH_APPLICATION));
			List envList = (List) ((Map) yml.get("environment")).get("envList");
			logger.info("There is " + envList.size() + " environment in application.yml");
			
			int index = 1;
			for(Object env : envList) {
				logger.info("env" + index++ + " : " + env.toString());
				logger.info("  - local.port : " + this.getValue(env, new String[] {"local","port"}));
				logger.info("  - remote.host : " + this.getValue(env, new String[] {"remote","host"}));
				logger.info("  - remote.port : " + this.getValue(env, new String[] {"remote","port"}));
				logger.info("  - remote.key : " + this.getValue(env, new String[] {"remote","key"}));
				if(((Map) env).get("mysql") != null) {
					logger.info("  - mysql.host : " + this.getValue(env, new String[] {"mysql","host"}));
					logger.info("  - mysql.port : " + this.getValue(env, new String[] {"mysql","port"}));
					logger.info("  - mysql.id : " + this.getValue(env, new String[] {"mysql","id"}));
					logger.info("  - mysql.pwd : " + this.getValue(env, new String[] {"mysql","pwd"}));
					logger.info("  - mysql.test.test : " + this.getValue(env, new String[] {"mysql","test","test"}));
				} else if (((Map) env).get("command") != null) {
					logger.info("  - command.line : " + this.getValue(env, new String[] {"command","line"}));
				} else {
					logger.error("Wrong Type");
				}
			}
			
		}catch (FileNotFoundException e) { 
			e.printStackTrace(); 
			logger.error("There is a exception in progress : " + e.toString());
		}	
	}
	
	private Object getValue(Object obj, String[] keys) {
		Object tt = obj;
		for(String key : keys) {
			tt = CommonUtil.ObjToMap(tt).get(key);
		}
		return tt;
	}
	
}
