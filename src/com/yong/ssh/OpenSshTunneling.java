package com.yong.ssh;

import java.net.Socket;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.yong.common.Configuration;
import com.yong.common.ExceptionHandler;
import com.yong.msg.MsgCodeConfiguration;
import com.yong.msg.MsgCodeException;
import com.yong.msg.MsgCodeSocket;

@SuppressWarnings("rawtypes")
public class OpenSshTunneling {

	Logger logger = Logger.getLogger(this.getClass());
	
	private String env;
	private int executeInterval;
	private String executeType;
	
	private int localPort;
	
	private String remoteHost;
	private int remotePort;
	private String remoteKey;
	private String remoteName;
	
	private String remoteMysqlHost;
	private int remoteMysqlPort;
	
	private List remoteCommandLine;
	
	public String getEnv() {
		return this.env;
	}

	public int getExecuteInterval() {
		return this.executeInterval;
	}
	
	
	public OpenSshTunneling(int index) throws Exception {
		
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
		
		if(executeType.equals(MsgCodeConfiguration.MSG_WORD_EXECUTE_TYPE_MYSQL)) {
			this.remoteMysqlHost = Configuration.getString(index, "remote.mysql.host");
			this.remoteMysqlPort = Configuration.getInt(index, "remote.mysql.port");
			logger.debug("[" + env + "] Complete to set remoteMysqlHost : " + this.remoteMysqlHost);
			logger.debug("[" + env + "] Complete to set remoteMysqlPort : " + this.remoteMysqlPort);
		}
		else if(executeType.equals(MsgCodeConfiguration.MSG_WORD_EXECUTE_TYPE_COMMAND)) {
			this.remoteCommandLine = Configuration.getList(index, "remote.command.line");
			logger.debug("[" + env + "] Complete to set remoteCommandLine : " + this.remoteCommandLine);
		}
		else {
			logger.error(MsgCodeException.MSG_CODE_WRONG_EXECUTE_TYPE_MSG + " [env : " + this.env + " / execute.type : " + this.executeType + ")\n" + Configuration.getEnvMap(index).toString());
			ExceptionHandler.exception(MsgCodeException.MSG_TYPE_CONFIGURATION, MsgCodeException.MSG_CODE_WRONG_EXECUTE_TYPE, Configuration.getEnvMap(index).toString());
		}
	}
	
	public boolean checkSshPort() throws Exception {
		boolean result = false;
		Socket socket = null;
		try {
			socket = new Socket(MsgCodeSocket.MSG_WORD_OPEN_HOST, this.localPort);
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

	public void openSshPort() throws Exception {
		try {
			JSch jsch = new JSch();
			
			logger.info("[" + this.env + "] TRY TO OPEN SSH : " + this.localPort);
			
			Session session = jsch.getSession(this.remoteName, this.remoteHost, this.remotePort);
			jsch.addIdentity(this.remoteKey);
			logger.info("[" + this.env + "] SSH Connection Information : "
					+ "\n- SSH ENV : " + this.env
					+ "\n- SSH USER : " + this.remoteName
					+ "\n- SSH HOST : " + this.remoteHost
					+ "\n- SSH PORT : " + this.remotePort
					);
			
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			config.put("ConnectionAttempts", "3");
			session.setConfig(config);
			
			session.connect();
			logger.info("[" + this.env + "] Success to create SSH Session !");
			logger.info("[" + this.env + "] TRY TO OPEN SSH TUNNELING !");
			session.setPortForwardingL(
					this.localPort,
					this.remoteMysqlHost,
					this.remoteMysqlPort);
			logger.info("[" + this.env + "] SSH Tunneling - Port Forward (Local -> Destination) : "
					+ MsgCodeSocket.MSG_WORD_OPEN_HOST +"/" + this.localPort + " -> "
					+ this.remoteMysqlHost + "/" + this.remoteMysqlPort);
			logger.info("[" + this.env + "] Success to open SSH Tunneling !");
		}catch (Exception e) {
			logger.error(MsgCodeException.MSG_CODE_TUNNELING_NOT_OPEN_MSG + " : " + e.toString());
			ExceptionHandler.exception(MsgCodeException.MSG_TYPE_SSH, MsgCodeException.MSG_CODE_TUNNELING_NOT_OPEN, e.toString());
		}
	}
}
