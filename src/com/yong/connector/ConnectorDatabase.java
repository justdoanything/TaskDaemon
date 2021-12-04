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
import com.yong.handler.ExceptionHandler;
import com.yong.handler.LoggingHandler;
import com.yong.msg.MsgCodeDatabase;
import com.yong.msg.MsgCodeException;

public class ConnectorDatabase {

	private LoggingHandler logger = new LoggingHandler(this.getClass(), Configuration.loggerUse);
	
	// Use private type if you want to execute several sqlSessionFactory
	// private SqlSessionFactory ssf;
	private static SqlSessionFactory ssf;
	
	/**
	 * @author yongwoo
	 * @throws Exception
	 * @category Database
	 * @implNote Connect database as properties
	 */
	public ConnectorDatabase(ConnectorSSH connectorSSH) throws Exception {
		if(ssf == null) {
			// Set mybatis properties
			File mybatisConfigFile = new File(connectorSSH.getRemoteDbMybatis());
			Properties props = new Properties();
			props.put(MsgCodeDatabase.MSG_KEY_CONFIG_DB_DRIVER, connectorSSH.getRemoteDbDriver());
			props.put(MsgCodeDatabase.MSG_KEY_CONFIG_DB_URL, connectorSSH.getRemoteDbUrl());
			props.put(MsgCodeDatabase.MSG_KEY_CONFIG_DB_ID, connectorSSH.getRemoteDbId());
			props.put(MsgCodeDatabase.MSG_KEY_CONFIG_DB_PWD, connectorSSH.getRemoteDbPwd());
			props.put(MsgCodeDatabase.MSG_KEY_CONFIG_DB_QUERY,connectorSSH.getRemoteDbQuery());
			
			try {
				InputStream inputStream = new FileInputStream(mybatisConfigFile);
				
				// Create sqlSessionFactory
				ssf = new SqlSessionFactoryBuilder().build(inputStream, props); 
				logger.info("Success to create SqlSessionFactory → " 
								+ "\n  - driver : " + props.getProperty(MsgCodeDatabase.MSG_KEY_CONFIG_DB_DRIVER)
								+ "\n  - url : " + props.getProperty(MsgCodeDatabase.MSG_KEY_CONFIG_DB_URL)
								+ "\n  - id : " + props.getProperty(MsgCodeDatabase.MSG_KEY_CONFIG_DB_URL)
								+ "\n  - pwd : " + props.getProperty(MsgCodeDatabase.MSG_KEY_CONFIG_DB_PWD)
								+ "\n  - query : " + props.getProperty(MsgCodeDatabase.MSG_KEY_CONFIG_DB_QUERY));
			}catch (Exception e) {
				e.printStackTrace();
				logger.error(MsgCodeException.MSG_CODE_DATABASE_NOT_CONNECT_MSG + " : " + e.toString());
				ExceptionHandler.exception(MsgCodeException.MSG_TYPE_DATABASE, MsgCodeException.MSG_CODE_DATABASE_NOT_CONNECT, e.toString());
			}
		}
	}
	
	/**
	 * @author yongwoo
	 * @throws Exception
	 * @category Database
	 * @implNote Getting sql session
	 */
	private SqlSession getSession() throws Exception {
		SqlSession session = null;
		try {
			logger.info("[SQL] Getting Database Session ...");
			session = ssf.openSession();
			session.getConnection();
			logger.info("[SQL] Success To Open Session!");
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(MsgCodeException.MSG_CODE_DATABASE_NOT_GET_SESSION_MSG + " : " + e.toString());
			ExceptionHandler.exception(MsgCodeException.MSG_TYPE_DATABASE, MsgCodeException.MSG_CODE_DATABASE_NOT_GET_SESSION, e.toString());
		}
		return session;
	}
	
	/**
	 * @author yongwoo
	 * @throws Exception
	 * @category Database
	 * @implNote Getting sql session with commit flag
	 */
	private SqlSession getSession(boolean autoCommit) throws Exception {
		SqlSession session = null;
		try {
			logger.info("[SQL] Getting Database Session ...(autoCommit = " + autoCommit + ")");
			session = ssf.openSession(autoCommit);
			session.getConnection();
			logger.info("[SQL] Success To Open Session ...(autoCommit = " + autoCommit + ")");
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(MsgCodeException.MSG_CODE_DATABASE_NOT_GET_SESSION_MSG + " : " + e.toString());
			ExceptionHandler.exception(MsgCodeException.MSG_TYPE_DATABASE, MsgCodeException.MSG_CODE_DATABASE_NOT_GET_SESSION, e.toString());
		}
		return session;
	}
	
	/**
	 * @author yongwoo
	 * @throws Exception
	 * @category Database
	 * @implNote Close sql session
	 */
	private void close(SqlSession session) throws Exception {
		if(session != null) {
			session.close();
			logger.debug("[SQL] Session Close");
		}
	}
	
	/**
	 * @author yongwoo
	 * @throws Exception
	 * @category Database
	 * @implNote Commit to sql session
	 */
	public void commit(SqlSession session) throws Exception {
		if(session != null) {
			session.commit();
			logger.debug("[SQL] Session Commit");
		}
	}
	
	/**
	 * @author yongwoo
	 * @throws Exception
	 * @category Database
	 * @implNote Get session and execute select list query
	 */
	public Object selectList(String id, Object param) throws Exception {
		SqlSession session = null;
		List<Object> reslut = null;
		try {
			session = this.getSession();
			logger.info("[SQL] Starting SelectList ... " + id + " / " + param.toString());
			reslut = session.selectList(id, param);
			logger.info("[SQL] Finished SelectList ... " + id + " / " + param.toString());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(MsgCodeException.MSG_CODE_DATABASE_NOT_EXECUTE_SELECT_LIST_MSG + " : " + e.toString());
			ExceptionHandler.exception(MsgCodeException.MSG_TYPE_DATABASE, MsgCodeException.MSG_CODE_DATABASE_NOT_EXECUTE_SELECT_LIST, e.toString());
		}finally {
			this.close(session);
		}
		return reslut;
	}
	
	/**
	 * @author yongwoo
	 * @throws Exception
	 * @category Database
	 * @implNote Get session and execute select one query
	 */
	public Object selectOne(String id, Object param) throws Exception {
		SqlSession session = null;
		Object result = null;

		try {
			session = this.getSession();
			logger.info("[SQL] Starting SelectOne ... " + id + " / " + param.toString());
			result = session.selectOne(id, param);
			logger.info("[SQL] Finished SelectOne ... " + id + " / " + param.toString());
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error(MsgCodeException.MSG_CODE_DATABASE_NOT_EXECUTE_SELECT_ONE_MSG + " : " + e.toString());
			ExceptionHandler.exception(MsgCodeException.MSG_TYPE_DATABASE, MsgCodeException.MSG_CODE_DATABASE_NOT_EXECUTE_SELECT_ONE, e.toString());
		}
		finally {
			this.close(session);
		}
		return result;
	}
	
	/**
	 * @author yongwoo
	 * @throws Exception
	 * @category Database
	 * @implNote Get session and execute insert query
	 */
	public Object insert(String id, Object param) throws Exception {
		SqlSession session = null;
		int result = 0;
		
		try {
			session = this.getSession(false);
			logger.info("[SQL] Starting Insert ... "  + id + " / " + param.toString());
			result = session.insert(id, param);
			logger.info("[SQL] Finished Insert ... " + id + " / " + param.toString());
			
			if(result > 0) {
				session.commit();
			}
			else {
				session.rollback();
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(MsgCodeException.MSG_CODE_DATABASE_NOT_EXECUTE_INSERT_MSG + " : " + e.toString());
			ExceptionHandler.exception(MsgCodeException.MSG_TYPE_DATABASE, MsgCodeException.MSG_CODE_DATABASE_NOT_EXECUTE_INSERT, e.toString());
		}finally {
			this.close(session);
		}
		return result;
	}
	
	/**
	 * @author yongwoo
	 * @throws Exception
	 * @category Database
	 * @implNote Get session and execute update query
	 */
	public int update(String id, Object param) throws Exception {
		SqlSession session = null;
		int result = -1;		
		
		try {			
			session = this.getSession(false);
			logger.info("[SQL] Starting Update ... "  + id + " / " + param.toString());
			result = session.update(id, param);
			logger.info("[SQL] Finished Update ... "  + id + " / " + param.toString());
			
			if(result > 0) {
				session.commit();
			}
			else {
				session.rollback();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error(MsgCodeException.MSG_CODE_DATABASE_NOT_EXECUTE_UPDATE_MSG + " : " + e.toString());
			ExceptionHandler.exception(MsgCodeException.MSG_TYPE_DATABASE, MsgCodeException.MSG_CODE_DATABASE_NOT_EXECUTE_UPDATE, e.toString());
		}
		finally {
			this.close(session);
		}
		return result;
	}
	
	/**
	 * @author yongwoo
	 * @throws Exception
	 * @category Database
	 * @implNote Get session and execute delete query
	 */
	public int delete(String id, Object param) throws Exception {
		SqlSession session = null;
		int result = -1;		
		
		try {			
			session = this.getSession(false);
			logger.info("[SQL] Starting Update ... "  + id + " / " + param.toString());
			result = session.delete(id, param);
			logger.info("[SQL] Finished Update ... "  + id + " / " + param.toString());
			
			if(result > 0) {
				session.commit();
			}
			else {
				session.rollback();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error(MsgCodeException.MSG_CODE_DATABASE_NOT_EXECUTE_DELETE_MSG + " : " + e.toString());
			ExceptionHandler.exception(MsgCodeException.MSG_TYPE_DATABASE, MsgCodeException.MSG_CODE_DATABASE_NOT_EXECUTE_DELETE, e.toString());
		}
		finally {
			this.close(session);
		}
		return result;
	}
}
