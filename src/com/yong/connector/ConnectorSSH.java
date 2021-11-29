package com.yong.connector;

import java.net.Socket;
import java.util.List;
import java.util.Properties;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.yong.config.Configuration;
import com.yong.handler.ExceptionHandler;
import com.yong.handler.LoggingHandler;
import com.yong.msg.MsgCodeConfiguration;
import com.yong.msg.MsgCodeException;
import com.yong.msg.MsgCodeSSH;

import lombok.Getter;

@Getter
@SuppressWarnings("unchecked")
public class ConnectorSSH {

	private LoggingHandler logger = new LoggingHandler(this.getClass(), Configuration.loggerUse);
	
	private String env;
	private int executeInterval;
	private String executeType;
	
	private int localPort;
	
	private String remoteHost;
	private int remotePort;
	private String remoteKey;
	private String remoteName;
	
	// execute.type = mysql
	private String remoteTunnelHost;
	private int remoteTunnelPort;

	// execute.type = db
	private List<String> remoteCommandLine;
	
	// execute.type = db
	private String remoteDbMybatis;
	private String remoteDbDriver;
	private String remoteDbUrl;
	private String remoteDbId;
	private String remoteDbPwd;
	private String remoteDbQuery;

	/**
	 * @author yongwoo
	 * @throws Exception
	 * @category SSH
	 * @implNote Set this class values from Configuration class
	 */
	public ConnectorSSH(int index) throws Exception {
		
		logger.info("Trying to set the values for (" + Configuration.getString(index, "env") + ") : " + Configuration.getEnvMap(index).toString());
		
		this.env = Configuration.getString(index, "env");
		this.executeInterval = Configuration.getInt(index, "execute.interval");
		this.executeType = Configuration.getString(index, "execute.type");
		logger.debug("[" + env + "] Complete to set env : " + this.env);
		logger.debug("[" + env + "] Complete to set executeInterval : " + this.executeInterval);
		logger.debug("[" + env + "] Complete to set executeType : " + this.executeType);
		
		this.localPort = Configuration.getInt(index, "local.port");
		logger.debug("[" + env + "] Complete to set localPort : " + this.localPort);
		
		this.remoteHost = Configuration.getString(index, "remote.host");
		this.remotePort = Configuration.getInt(index, "remote.port");
		this.remoteKey = Configuration.getString(index, "remote.key");
		this.remoteName = Configuration.getString(index, "remote.name");
		logger.debug("[" + env + "] Complete to set remoteHost : " + this.remoteHost);
		logger.debug("[" + env + "] Complete to set remotePort : " + this.remotePort);
		logger.debug("[" + env + "] Complete to set remoteKey : " + this.remoteKey);
		logger.debug("[" + env + "] Complete to set remoteName : " + this.remoteName);
		
		// Set id/pwd for mysql if execute.type is mysql
		if(executeType.equals(MsgCodeConfiguration.MSG_WORD_EXECUTE_TYPE_TUNNEL)) {
			this.remoteTunnelHost = Configuration.getString(index, "remote.tunnel.host");
			this.remoteTunnelPort = Configuration.getInt(index, "remote.tunnel.port");
			logger.debug("[" + env + "] Complete to set remoteTunnelHost : " + this.remoteTunnelHost);
			logger.debug("[" + env + "] Complete to set remoteTunnelPort : " + this.remoteTunnelPort);
		}
		// Set command list for command if execute.type is command
		else if(executeType.equals(MsgCodeConfiguration.MSG_WORD_EXECUTE_TYPE_COMMAND)) {
			if(Configuration.getList(index, "remote.command.line") != null) {
				this.remoteCommandLine = Configuration.getList(index, "remote.command.line");
				logger.debug("[" + env + "] Complete to set remoteCommandLine : " + this.remoteCommandLine);
			}
		}
		// Set properties if execute.type = db
		else if(executeType.equals(MsgCodeConfiguration.MSG_WORD_EXECUTE_TYPE_DB)) {
			this.remoteDbMybatis = Configuration.getString(index, "remote.db.mybatis");
			this.remoteDbDriver = Configuration.getString(index, "remote.db.driver");
			this.remoteDbUrl = Configuration.getString(index, "remote.db.url");
			this.remoteDbId = Configuration.getString(index, "remote.db.id");
			this.remoteDbPwd = Configuration.getString(index, "remote.db.pwd");
			this.remoteDbQuery = Configuration.getString(index, "remote.db.query");
			logger.debug("[" + env + "] Complete to set remoteDbMybatis : " + this.remoteDbMybatis);
			logger.debug("[" + env + "] Complete to set remoteDbDriver : " + this.remoteDbDriver);
			logger.debug("[" + env + "] Complete to set remoteDbUrl : " + this.remoteDbUrl);
			logger.debug("[" + env + "] Complete to set remoteDbId : " + this.remoteDbId);
			logger.debug("[" + env + "] Complete to set remoteDbPwd : " + this.remoteDbPwd);
			logger.debug("[" + env + "] Complete to set remoteDbQuery : " + this.remoteDbQuery);
		}
		else {
			logger.error(MsgCodeException.MSG_CODE_WRONG_EXECUTE_TYPE_MSG + " [env : " + this.env + " / execute.type : " + this.executeType + ")\n" + Configuration.getEnvMap(index).toString());
			ExceptionHandler.exception(MsgCodeException.MSG_TYPE_CONFIGURATION, MsgCodeException.MSG_CODE_WRONG_EXECUTE_TYPE, Configuration.getEnvMap(index).toString());
		}
	}

	/**
	 * @author yongwoo
	 * @throws Exception
	 * @category SSH
	 * @implNote Checking port you want to open in your local.
	 */
	public boolean checkSshPort() throws Exception {
		boolean result = false;
		Socket socket = null;
		try {
			socket = new Socket(MsgCodeSSH.MSG_WORD_OPEN_HOST, this.localPort);
			socket.setSoLinger(true, 0);	// Disallow "TIME_WAIT" status of TCP
			logger.info("[" + this.env + "] CHECKING LOCAL PORT : [" + this.localPort + "] is already opened!");
		}catch (Exception e) {
			logger.info("[" + this.env + "] CHECKING LOCAL PORT : [" + this.localPort + "] is not opened!");
			result = true;
		}finally {
			try {
				if(socket != null)
					socket.close();
			} catch (Exception e) {
				logger.error(MsgCodeException.MSG_CODE_SOCKET_NOT_CLOSE_MSG + " : " + e.toString());
				ExceptionHandler.exception(MsgCodeException.MSG_TYPE_SOCKET, MsgCodeException.MSG_CODE_SOCKET_NOT_CLOSE, e.toString());
			}
		}
		return result;
	}

	/**
	 * @author yongwoo
	 * @throws Exception
	 * @category SSH
	 * @implNote Open your local port and ssh tunneling
	 */
	public Session openSshPort() throws Exception {
		Session session = null;
		try {
			JSch jsch = new JSch();
			
			logger.info("[" + this.env + "] TRY TO OPEN SSH : " + this.localPort);
			
			session = jsch.getSession(this.remoteName, this.remoteHost, this.remotePort);
			jsch.addIdentity(this.remoteKey);
			logger.info("[" + this.env + "] SSH Connection Information : "
					+ "\n  - SSH ENV : " + this.env
					+ "\n  - SSH USER : " + this.remoteName
					+ "\n  - SSH HOST : " + this.remoteHost
					+ "\n  - SSH PORT : " + this.remotePort
					);
			
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			config.put("ConnectionAttempts", "3");
			session.setConfig(config);
			
			session.connect();
			logger.info("[" + this.env + "] Success to create SSH Session !");
			
			if(!this.executeType.equals(MsgCodeConfiguration.MSG_WORD_EXECUTE_TYPE_COMMAND)) {
				logger.info("[" + this.env + "] TRY TO OPEN SSH TUNNELING !");
				session.setPortForwardingL(
						this.localPort,
						this.remoteTunnelHost,
						this.remoteTunnelPort);
				logger.info("[" + this.env + "] SSH Tunneling - Port Forward (Local -> Destination) : "
						+ MsgCodeSSH.MSG_WORD_OPEN_HOST +"/" + this.localPort + " -> "
						+ this.remoteTunnelHost + "/" + this.remoteTunnelPort);
				logger.info("[" + this.env + "] Success to open SSH Tunneling !");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(MsgCodeException.MSG_CODE_TUNNELING_NOT_OPEN_MSG + " : " + e.toString());
			ExceptionHandler.exception(MsgCodeException.MSG_TYPE_SSH, MsgCodeException.MSG_CODE_TUNNELING_NOT_OPEN, e.toString());
		}
		return session;
	}
}
