package com.yong.db;

import java.util.List;

import com.yong.config.Configuration;
import com.yong.connector.ConnectorSSH;
import com.yong.handler.LoggingHandler;

public class DatabaseService {

	private LoggingHandler logger = new LoggingHandler(this.getClass(), Configuration.loggerUse);
	private DatabaseDAO databaseDAO;
	
	/**
	 * @author yongwoo
	 * @throws Exception
	 * @category Service
	 * @implNote Create DAO class (connect database)
	 */
	public DatabaseService(ConnectorSSH connectorSSH) throws Exception {
		this.databaseDAO = new DatabaseDAO(connectorSSH);
	}
	
	/**
	 * @author yongwoo
	 * @throws Exception
	 * @category Service
	 * @implNote Execute any process you want
	 */
	public void execute() throws Exception {
		/*
		 * Add your logic you want
		 */
		
		// Execute DAO
		DatabaseVO vo = new DatabaseVO();
		vo.setValue1("VO VALUE 1");
		vo.setValue2("VO VALUE 2");
		vo.setValue3("VO VALUE 3");
		List<DatabaseVO> result = databaseDAO.retrieveTestConnect(vo);
		logger.info("Param is VO : " + vo.toStringJson());
		logger.info("Query Result : " + result.toString());
	}
}
