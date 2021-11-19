package com.yong.dto;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

public class ConfigurationDTO {
	private final Logger logger = Logger.getLogger(this.getClass());
	private Map<String, Log4jDTO> configuration;
	private Map environment;
	public Map<String, Log4jDTO> getConfiguration() {
		return configuration;
	}
	public void setConfiguration(Map<String, Log4jDTO> configuration) {
		this.configuration = configuration;
	}
	public Map getEnvironment() {
		return environment;
	}
	public void setEnvironment(Map environment) {
		this.environment = environment;
	}

	
}
