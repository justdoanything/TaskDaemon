package com.yong.db;

import java.util.List;

import com.yong.config.Configuration;
import com.yong.connector.ConnectorDatabase;
import com.yong.connector.ConnectorSSH;
import com.yong.handler.LoggingHandler;
import com.yong.msg.MsgCodeDatabase;

@SuppressWarnings("unchecked")
public class DatabaseDAO {

	private LoggingHandler logger = new LoggingHandler(this.getClass(), Configuration.loggerUse);
	
	private ConnectorDatabase database;
	
	/**
	 * @author yongwoo
	 * @throws Exception
	 * @category Database
	 * @implNote Create database factory
	 */
	public DatabaseDAO(ConnectorSSH connectorSSH) throws Exception {
		database = new ConnectorDatabase(connectorSSH);
	}
	
	/**
	 * @author yongwoo
	 * @throws Exception
	 * @category Database
	 * @implNote Execute sql query
	 */
	public List<DatabaseVO> retrieveTestConnect(DatabaseVO vo) throws Exception {
		logger.info("Trying to retrieve Test Connect : " + vo.toStringJson());
		return (List<DatabaseVO>) database.selectList(MsgCodeDatabase.MSG_VALUE_MYBATIS_NAMESPACE + "retrieveTestConnect", vo);
	}
}
