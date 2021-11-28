package com.yong.db;

import java.util.List;

import com.yong.config.Configuration;
import com.yong.connector.ConnectorDatabase;
import com.yong.connector.ConnectorSSH;
import com.yong.handler.LoggingHandler;

@SuppressWarnings("unchecked")
public class DatabaseDAO {

	private LoggingHandler logger = new LoggingHandler(this.getClass(), Configuration.loggerUse);
	
	private ConnectorDatabase database;
	private String namespace = "com.yong.db.";
	
	public DatabaseDAO(ConnectorSSH connectorSSH) {
		database = new ConnectorDatabase(connectorSSH);
	}
	
	public List<DatabaseVO> retrieveTestConnect(DatabaseVO vo) throws Exception {
		logger.info("Trying to retrieve Test Connect : " + vo.toStringJson());
		return (List<DatabaseVO>) database.selectList(namespace + "retrieveTestConnect", vo);
	}
}
