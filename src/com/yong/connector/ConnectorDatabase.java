package com.yong.connector;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.yong.config.Configuration;
import com.yong.handler.LoggingHandler;

public class ConnectorDatabase {

	private LoggingHandler logger = new LoggingHandler(this.getClass(), Configuration.loggerUse);
	private static SqlSessionFactory ssf;
	
	public ConnectorDatabase(ConnectorSSH connectorSSH) {
		if(ssf == null) {
			String resource = connectorSSH.getRemoteDbMybatis();
			Properties props = new Properties();
			props.put("db.driver", connectorSSH.getRemoteDbDriver());
			props.put("db.url", connectorSSH.getRemoteDbUrl());
			props.put("db.id", connectorSSH.getRemoteDbId());
			props.put("db.pwd", connectorSSH.getRemoteDbPwd());
			props.put("db.query",connectorSSH.getRemoteDbQuery());
			
			InputStream inputStream = null;
			try {
				// Set mybatis.xml
				inputStream = Resources.getResourceAsStream(resource);
				ssf = new SqlSessionFactoryBuilder().build(inputStream, props); 
				
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
			logger.info("Session Open");
		}catch (Exception e) {
			logger.error("FAIL Session Open" + e.getMessage());
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
			logger.info("Session Open ...(autoCommit = " + autoCommit + ")");
		}catch (Exception e) {
			logger.error("FAIL Session Open" + e.getMessage());
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
			logger.info("Start SelectList");
			reslut = session.selectList(id, param);
			logger.info("End SelectList");
		}catch (Exception e) {
			logger.error("FAIL SelectList" + e.getMessage());
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
			logger.info("Start SelectOne");
			result = session.selectOne(id, param);
			logger.info("End SelectOne");
		}
		catch(Exception e) {
			logger.error("FAIL SelectOne" + e.getMessage());
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
			logger.info("Start Insert");
			result = session.insert(id, param);
			logger.info("End Insert");
			
			if(result > 0) {
				session.commit();
			}
			else {
				session.rollback();
			}
		}catch (Exception e) {
			logger.error("FAIL Insert" + e.getMessage());
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
			logger.info("Start Update");
			result = session.update(id, param);
			logger.info("End Update");
			
			if(result > 0) {
				session.commit();
			}
			else {
				session.rollback();
			}
		}
		catch(Exception e) {
			logger.error("FAIL Update" + e.getMessage());
			throw new Exception(e);
		}
		finally {
			this.close(session);
		}
		
		return result;
	}
}
