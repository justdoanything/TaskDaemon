package com.yong.connector;

import java.util.Properties;

import org.apache.ibatis.session.SqlSessionFactory;

import com.yong.config.Configuration;
import com.yong.handler.LoggingHandler;

public class ConnectorMysql {

	private LoggingHandler logger = new LoggingHandler(this.getClass(), Configuration.loggerUse);
	private static SqlSessionFactory ssf;
	
	public static SqlSessionFactory getInstance(ConnectorSSH connectorSSH) {
		if(ssf == null) {
			String resource = "./conf/mybatis.xml";
			Properties props = new Properties();
			props.put("driver", "");
			props.put("type", "");
			props.put("host", "");
			props.put("port", "");
			props.put("schema", "");
			props.put("id", "");
			props.put("pwd", "");
			
		}
		return ssf;
	}
}
