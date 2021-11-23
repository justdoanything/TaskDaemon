package com.yong.common;

import org.apache.log4j.Logger;

import com.yong.msg.MsgCode;

@SuppressWarnings("rawtypes")
public class LoggingHandler {

	private Logger logger = null;
	private String loggerUse = MsgCode.MSG_FLAG_NO;
	
	/**
	 * @author yongwoo
	 * @throws
	 * @category Log
	 * @implNote Create Logger and set useLogger flag
	 */
	public LoggingHandler(Class clazz, String useLogger) {
		this.logger = Logger.getLogger(clazz);
		this.loggerUse = useLogger;
	}
	
	/**
	 * @author yongwoo
	 * @throws
	 * @category Log
	 * @implNotes Write info log if flag is Y
	 */
	public void info(String message) {
		if(loggerUse.equals(MsgCode.MSG_FLAG_YES))
			logger.info(message);
	}
	
	/**
	 * @author yongwoo
	 * @throws
	 * @category Log
	 * @implNotes Write error log if flag is Y
	 */
	public void error(String message) {
		if(loggerUse.equals(MsgCode.MSG_FLAG_YES))
			logger.error(message);
	}
	
	/**
	 * @author yongwoo
	 * @throws
	 * @category Log
	 * @implNotes Write debug log if flag is Y
	 */
	public void debug(String message) {
		if(loggerUse.equals(MsgCode.MSG_FLAG_YES))
			logger.debug(message);
	}
}
