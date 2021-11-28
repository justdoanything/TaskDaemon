package com.yong.db;

import com.yong.config.Configuration;
import com.yong.connector.ConnectorSSH;
import com.yong.handler.LoggingHandler;

public class DatabaseService {

	private LoggingHandler logger = new LoggingHandler(this.getClass(), Configuration.loggerUse);
	private DatabaseDAO databaseDAO;
	
	public DatabaseService(ConnectorSSH connectorSSH) {
		this.databaseDAO = new DatabaseDAO(connectorSSH);
	}
	
	public void execute() throws Exception {
		// Add your logic
		
		// Call DAO
		databaseDAO.retrieveTestData();
	}
}
