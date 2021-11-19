package com.yong.runner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.yong.dto.ConfigurationDTO;
import com.yong.msg.MsgCodeConfiguration;

public class Executor {

	public static void main(String[] args) {
		
		try { 
			Map<String, Object> propMap = new Yaml().load(new FileReader(MsgCodeConfiguration.MSG_VALUE_PATH_CONTEXT)); 
			System.out.println(propMap); 
			System.out.println(propMap.get("configuration")); 
			System.out.println(((Map<String, Object>)propMap.get("environment")).get("envList"));
			
			Yaml yaml = new Yaml(new Constructor(ConfigurationDTO.class));
			ConfigurationDTO propMap1 = yaml.load(new FileReader(MsgCodeConfiguration.MSG_VALUE_PATH_CONTEXT));
			System.out.println(propMap1);
			System.out.println(propMap1.getConfiguration().get("log4j").getPath());
		} catch (FileNotFoundException e) { 
			e.printStackTrace(); 
		}
	}
}
