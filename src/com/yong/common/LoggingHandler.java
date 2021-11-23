package com.yong.common;

import org.apache.log4j.Logger;

import com.yong.msg.MsgCode;

@SuppressWarnings("rawtypes")
public class LoggingHandler {

	private Logger logger = null;
	private String loggerUse = MsgCode.MSG_FLAG_NO;
	
	public LoggingHandler(Class clazz, String useLogger) {
		this.logger = Logger.getLogger(clazz);
		this.loggerUse = useLogger;
	}
	
	public void info(String message) {
		if(loggerUse.equals(MsgCode.MSG_FLAG_YES))
			logger.info(message);
	}
	
	public void error(String message) {
		if(loggerUse.equals(MsgCode.MSG_FLAG_YES))
			logger.error(message);
	}
	
	public void debug(String message) {
		if(loggerUse.equals(MsgCode.MSG_FLAG_YES))
			logger.debug(message);
	}
}
