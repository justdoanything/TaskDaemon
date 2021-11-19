package com.yong.dto;

import java.util.Map;

public class ConfigurationDTO {
	
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
