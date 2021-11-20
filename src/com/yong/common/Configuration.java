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
				logger.info("===============================START");
				getValue(env, "");
				logger.info("===============================END");
				logger.info("  - local.port : " + this.getValue(env, new String[] {"local","port"}));
				logger.info("  - remote.host : " + this.getValue(env, new String[] {"remote","host"}));
				logger.info("  - remote.port : " + this.getValue(env, new String[] {"remote","port"}));
				logger.info("  - remote.key : " + this.getValue(env, new String[] {"remote","key"}));
				if(this.getValue(env, new String[] {"mysql"}) != null) {
					logger.info("  - mysql.host : " + this.getValue(env, new String[] {"mysql","host"}));
					logger.info("  - mysql.port : " + this.getValue(env, new String[] {"mysql","port"}));
					logger.info("  - mysql.id : " + this.getValue(env, new String[] {"mysql","id"}));
					logger.info("  - mysql.pwd : " + this.getValue(env, new String[] {"mysql","pwd"}));
				} else if (this.getValue(env, new String[] {"command"}) != null) {
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
	
	private boolean mapOrNot(Object obj) {
		boolean result = true;
		try {
			CommonUtil.ObjToMap(obj);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	
	private Object getValue(Object env, String parentKey) {
		Map<String, Object> t = CommonUtil.ObjToMap(env);
		for(String key : t.keySet()) {
			System.out.print(parentKey.equals("") ? key : parentKey);
			if(mapOrNot(t.get(key))) {
//1				parentKey = parentKey + "." + key;
				parentKey = "." + key;
				getValue(t.get(key), parentKey);
				parentKey = "";
//				System.out.print(key + ".");
			} else {
//				System.out.println(key + ":" + t.get(key).toString());
				System.out.println(" : " + t.get(key).toString());
			}
			
		}
		return null;
	}
	
}
