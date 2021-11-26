package com.yong.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.PropertyConfigurator;
import org.yaml.snakeyaml.Yaml;

import com.yong.handler.ExceptionHandler;
import com.yong.handler.LoggingHandler;
import com.yong.msg.MsgCodeCommon;
import com.yong.msg.MsgCodeConfiguration;
import com.yong.msg.MsgCodeException;
import com.yong.util.CommonUtil;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Configuration {

	public static String loggerUse = MsgCodeCommon.MSG_FLAG_NO;
	private static LoggingHandler logger = null;
	private final static List<Map<String, Object>> envListMap = new ArrayList<>();
	
	/**
	 * @author yongwoo
	 * @throws Exception
	 * @category Configuration
	 * @implNote Initialize context file when this program started.
	 */
	public static void initialize() throws Exception {
		try {
			parseApplicationYml();
			logger.info("Complete to load application.yml from " + MsgCodeConfiguration.MSG_VALUE_PATH_APPLICATION);
			logger.debug("application.yml was parsed to List<Map> : " + envListMap.toString());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(MsgCodeException.MSG_CODE_INITIAL_APPLICATION_ERROR_MSG + " : " + e.getMessage());
			ExceptionHandler.exception(MsgCodeException.MSG_TYPE_CONFIGURATION, MsgCodeException.MSG_CODE_INITIAL_APPLICATION_ERROR, e.toString());
		}
	}
	
	/**
	 * @author yongwoo
	 * @throws FileNotFoundException, Exception
	 * @category Configuration
	 * @implNote Parseing application.yml to envListMap
	 *           (This is like @Value from SpringBoot)
	 */
	private static void parseApplicationYml() throws Exception {
		try {
			Map<String, Object> yml = new Yaml().load(new FileReader(MsgCodeConfiguration.MSG_VALUE_PATH_APPLICATION));
			
			// Read logger (You must set use → Y)
			Map<String, String> loggerMap = CommonUtil.ObjToMap(CommonUtil.ObjToMap(yml.get(MsgCodeConfiguration.MSG_WORD_KEY_ENVIRONMENT)).get(MsgCodeConfiguration.MSG_WORD_KEY_LOGGER));
			String loggerPath = loggerMap.get(MsgCodeConfiguration.MSG_WORD_KEY_LOGGER_PATH);
			loggerUse = loggerMap.get(MsgCodeConfiguration.MSG_WORD_KEY_LOGGER_USE);
			
			// Set Logger
			PropertyConfigurator.configure(loggerPath);
			logger =  new LoggingHandler(Configuration.class, loggerUse);
			logger.info("Complete to load log4j properties file from " + loggerPath);
			
			// Read envList
			List envListBean = (List) ((Map) yml.get(MsgCodeConfiguration.MSG_WORD_KEY_ENVIRONMENT)).get(MsgCodeConfiguration.MSG_WORD_KEY_ENVLIST);
			if(envListBean.size() == 1) {
				logger.info("There is a environment in application.yml");
			}else {
				logger.info("There are " + envListBean.size() + " environments in application.yml");
			}
			
			// Set envList values
			int index = 0;
			for(Object env : envListBean) {
				logger.info(CommonUtil.ObjToMap(env).get(MsgCodeConfiguration.MSG_WORD_KEY_ENV).toString() + " : " + env.toString());
				envListMap.add(new HashMap<>());
				createYmlToMap(env, envListMap.get(index));
				index++;
			}
		}catch (FileNotFoundException e) { 
			e.printStackTrace(); 
			logger.error(MsgCodeException.MSG_CODE_FILE_NOT_FOUND_MSG + " : " + e.toString());
			ExceptionHandler.exception(MsgCodeException.MSG_TYPE_CONFIGURATION, MsgCodeException.MSG_CODE_FILE_NOT_FOUND, e.toString());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(MsgCodeException.MSG_CODE_PARESE_APPLICATION_ERROR_MSG + " : " + e.toString());
			ExceptionHandler.exception(MsgCodeException.MSG_TYPE_CONFIGURATION, MsgCodeException.MSG_CODE_PARESE_APPLICATION_ERROR, e.toString());
		}
	}
	
	/**
	 * @author yongwoo
	 * @throws
	 * @category Configuration
	 * @implNote Create envListMap from application.yml
	 */
	private static void createYmlToMap(Object env, Map<String, Object> envListMap) throws Exception {
		doDepthFirstSearch(env, "", envListMap);
	}
	
	/**
	 * @author yongwoo
	 * @throws 
	 * @category Configuration
	 * @implNote Create envListMap from application.yml with DFS searching argorithm 
	 */
	private static void doDepthFirstSearch(Object env, String parentKey, Map<String, Object> envListMap) throws Exception {
		Map<String, Object> obj = CommonUtil.ObjToMap(env);
		
		for(String key : obj.keySet()) {
			if(CommonUtil.mapOrNot(obj.get(key))) {
				// Create parentKey
				parentKey += (parentKey.equals("") ? key : "." + key);
				
				// Search child node
				doDepthFirstSearch(obj.get(key), parentKey, envListMap);
				
				// End to search child node
				parentKey = parentKey.contains("." + key) ? parentKey.replace("." + key, "") : parentKey.replace(key, "");
				
			} else {
				// End to search parent node
				String configKey = (parentKey.equals("") ? key : parentKey + "." + key);
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
	public static String getString(int index, String key) throws Exception {
		return CommonUtil.ObjToString(envListMap.get(index).get(key));
	}
	
	/**
	 * @author yongwoo
	 * @throws 
	 * @category Configuration
	 * @implNote Get List Object from envListMap 
	 */
	public static List getList(int index, String key) throws Exception {
		return CommonUtil.ObjToList(envListMap.get(index).get(key));
	}
	
	/**
	 * @author yongwoo
	 * @throws 
	 * @category Configuration
	 * @implNote Get Int Object from envListMap 
	 */
	public static int getInt(int index, String key) throws Exception {
		return CommonUtil.ObjToInt(envListMap.get(index).get(key));
	}
	
	/**
	 * @author yongwoo
	 * @throws 
	 * @category Configuration
	 * @implNote Get size of envListMap 
	 */
	public static int getEnvListSize() throws Exception {
		return envListMap.size();
	}
	
	/**
	 * @author yongwoo
	 * @throws 
	 * @category Configuration
	 * @implNote Get envMap from envListMap with index 
	 */
	public static Map getEnvMap(int index) throws Exception {
		return envListMap.get(index);
	}

}
