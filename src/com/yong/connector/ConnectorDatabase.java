package com.yong.connector;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.yong.config.Configuration;
import com.yong.handler.LoggingHandler;
import com.yong.msg.MsgCodeDatabase;

public class ConnectorDatabase {

	private LoggingHandler logger = new LoggingHandler(this.getClass(), Configuration.loggerUse);
	private static SqlSessionFactory ssf;
	
	public ConnectorDatabase(ConnectorSSH connectorSSH) {
		if(ssf == null) {
			File mybatisConfigFile = new File(connectorSSH.getRemoteDbMybatis());
			Properties props = new Properties();
			props.put(MsgCodeDatabase.MSG_KEY_CONFIG_DB_DRIVER, connectorSSH.getRemoteDbDriver());
			props.put(MsgCodeDatabase.MSG_KEY_CONFIG_DB_URL, connectorSSH.getRemoteDbUrl());
			props.put(MsgCodeDatabase.MSG_KEY_CONFIG_DB_ID, connectorSSH.getRemoteDbId());
			props.put(MsgCodeDatabase.MSG_KEY_CONFIG_DB_PWD, connectorSSH.getRemoteDbPwd());
			props.put(MsgCodeDatabase.MSG_KEY_CONFIG_DB_QUERY,connectorSSH.getRemoteDbQuery());
			
			try {
				// Set mybatis.xml and properties
				InputStream inputStream = new FileInputStream(mybatisConfigFile);
				ssf = new SqlSessionFactoryBuilder().build(inputStream, props); 
				logger.info("Success to create SqlSessionFactory → " 
								+ "\n  - driver : " + props.getProperty(MsgCodeDatabase.MSG_KEY_CONFIG_DB_DRIVER)
								+ "\n  - url : " + props.getProperty(MsgCodeDatabase.MSG_KEY_CONFIG_DB_URL)
								+ "\n  - id : " + props.getProperty(MsgCodeDatabase.MSG_KEY_CONFIG_DB_URL)
								+ "\n  - pwd : " + props.getProperty(MsgCodeDatabase.MSG_KEY_CONFIG_DB_PWD)
								+ "\n  - query : " + props.getProperty(MsgCodeDatabase.MSG_KEY_CONFIG_DB_QUERY));
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private SqlSession getSession() throws Exception {
		SqlSession session = null;
		try {
			logger.info("Getting Database Session ...");
			session = ssf.openSession();
			session.getConnection();
			logger.info("Success To Open Session!");
		}catch (Exception e) {
			logger.error("Fail To Open Session : " + e.getMessage());
			throw new Exception(e);
		}
		return session;
	}
	
	private SqlSession getSession(boolean autoCommit) throws Exception {
		SqlSession session = null;
		try {
			logger.info("Getting Database Session ...(autoCommit = " + autoCommit + ")");
			session = ssf.openSession(autoCommit);
			session.getConnection();
			logger.info("Success To Open Session ...(autoCommit = " + autoCommit + ")");
		}catch (Exception e) {
			logger.error("Fail To Open Session : " + e.getMessage());
			throw new Exception(e);
		}
		return session;
	}
	
	private void close(SqlSession session) {
		if(session != null) {
			session.close();
			logger.debug("Session Close");
		}
	}
	
	public void commit(SqlSession session) {
		if(session != null) {
			session.commit();
			logger.debug("Session Commit");
		}
	}
	
	public Object selectList(String id, Object param) throws Exception {
		SqlSession session = null;
		List<Object> reslut = null;
		try {
			session = this.getSession();
			logger.info("Starting SelectList ... " + id + " / " + param.toString());
			reslut = session.selectList(id, param);
			logger.info("Finished SelectList ... " + id + " / " + param.toString());
		}catch (Exception e) {
			logger.error("Fail SelectList : " + e.toString());
			throw new Exception(e);
		}finally {
			this.close(session);
		}
		return reslut;
	}
	
	public Object selectOne(String id, Object param) throws Exception {
		SqlSession session = null;
		Object result = null;

		try {
			session = this.getSession();
			logger.info("Starting SelectOne ... " + id + " / " + param.toString());
			result = session.selectOne(id, param);
			logger.info("Finished SelectOne ... " + id + " / " + param.toString());
		}
		catch(Exception e) {
			logger.error("Fail SelectOne : " + e.getMessage());
			throw new Exception(e);
		}
		finally {
			this.close(session);
		}

		return result;
	}
	
	public Object insert(String id, Object param) throws Exception {
		SqlSession session = null;
		int result = 0;
		
		try {
			session = this.getSession(false);
			logger.info("Starting Insert ... "  + id + " / " + param.toString());
			result = session.insert(id, param);
			logger.info("Finished Insert ... " + id + " / " + param.toString());
			
			if(result > 0) {
				session.commit();
			}
			else {
				session.rollback();
			}
		}catch (Exception e) {
			logger.error("Fail Insert : " + e.getMessage());
			throw new Exception(e);
		}finally {
			this.close(session);
		}
		return result;
	}
	
	public int update(String id, Object param) throws Exception {
		SqlSession session = null;
		int result = -1;		
		
		try {			
			session = this.getSession(false);
			logger.info("Starting Update ... "  + id + " / " + param.toString());
			result = session.update(id, param);
			logger.info("Finished Update ... "  + id + " / " + param.toString());
			
			if(result > 0) {
				session.commit();
			}
			else {
				session.rollback();
			}
		}
		catch(Exception e) {
			logger.error("Fail Update : " + e.getMessage());
			throw new Exception(e);
		}
		finally {
			this.close(session);
		}
		
		return result;
	}
}
