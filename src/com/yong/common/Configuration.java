package com.yong.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.yaml.snakeyaml.Yaml;

import com.yong.msg.MsgCodeConfiguration;
import com.yong.util.CommonUtil;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Configuration {

	private final Logger logger =  Logger.getLogger(Configuration.class);
	private final static Map<String, Object> envListMap = new HashMap<>();
	
	/**
	 * @author yongwoo
	 * @throws Exception
	 * @category Configuration
	 * @implNote Initialize context file when this program started.
	 */
	public void initialize() throws Exception {
			PropertyConfigurator.configure(MsgCodeConfiguration.MSG_VALUE_PATH_LOG4J);
			logger.info("Complete to load log4j properties file from " + MsgCodeConfiguration.MSG_VALUE_PATH_LOG4J);
			logger.info("Trying to parse application.yml to start this program!");
			this.parseApplicationYml();
			logger.info("Complete to load application.yml!");
			logger.debug("application map : " + envListMap.toString());
	}
	
	/**
	 * @author yongwoo
	 * @throws FileNotFoundException, Exception
	 * @category Configuration
	 * @implNote Parseing application.yml to envListMap
	 *           (This is like @Value from SpringBoot)
	 */
	private void parseApplicationYml() {
		try {
			Map<String, Object> yml = new Yaml().load(new FileReader(MsgCodeConfiguration.MSG_VALUE_PATH_APPLICATION));
			List envList = (List) ((Map) yml.get("environment")).get("envList");
			logger.info("There is " + envList.size() + " environment in application.yml");
			
			int index = 0;
			
			for(Object env : envList) {
				logger.info("env" + index++ + " : " + env.toString());
				this.createYmlToMap(env, envListMap);
			}
			
		}catch (FileNotFoundException e) { 
			e.printStackTrace(); 
			logger.error("There is a fileNotFoundException in progress : " + e.toString());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("There is a exception in progress : " + e.toString());
		}
	}
	
	/**
	 * @author yongwoo
	 * @throws
	 * @category Configuration
	 * @implNote Create envListMap from application.yml
	 */
	private void createYmlToMap(Object env, Map<String, Object> envListMap) {
		this.doDepthFirstSearch(env, CommonUtil.ObjToString(((Map) env).get("env")), envListMap);
	}
	
	/**
	 * @author yongwoo
	 * @throws 
	 * @category Configuration
	 * @implNote Create envListMap from application.yml with DFS searching argorithm 
	 */
	private void doDepthFirstSearch(Object env, String parentKey, Map<String, Object> envListMap) {
		Map<String, Object> obj = CommonUtil.ObjToMap(env);
		
		for(String key : obj.keySet()) {
			if(CommonUtil.mapOrNot(obj.get(key))) {
				// Create parentKey
				parentKey += "." + key;
				
				// Search child node
				doDepthFirstSearch(obj.get(key), parentKey, envListMap);
				
				// End to search child node
				parentKey = parentKey.replace("." + key, "");
				
			} else {
				// End to search parent node
				String configKey = parentKey + "." + key;
				envListMap.put(configKey, obj.get(key));
				logger.debug(configKey + " : " + obj.get(key).toString());
			}
		}
	}
	
	/**
	 * @author yongwoo
	 * @throws 
	 * @category Configuration
	 * @implNote Get String Object from envListMap 
	 */
	public static String getString(String key) {
		return CommonUtil.ObjToString(envListMap.get(key));
	}
	
	/**
	 * @author yongwoo
	 * @throws 
	 * @category Configuration
	 * @implNote Get List Object from envListMap 
	 */
	public static List getList(String key) {
		return CommonUtil.ObjToList(envListMap.get(key));
	}
}
