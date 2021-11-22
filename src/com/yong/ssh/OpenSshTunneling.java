package com.yong.ssh;

import java.util.List;

import org.apache.log4j.Logger;

import com.yong.common.Configuration;
import com.yong.common.ExceptionHandler;
import com.yong.msg.MsgCodeConfiguration;
import com.yong.msg.MsgCodeException;

@SuppressWarnings({"unused", "rawtypes"})
public class OpenSshTunneling {

	Logger logger = Logger.getLogger(this.getClass());
	
	private int executeInterval = 60 * 1000; // default 60s
	private String executeType;
	
	private int localPort;
	
	private String remoteHost;
	private int remotePort;
	private String remoteKey;
	
	private String remoteMysqlHost;
	private int remoteMysqlPort;
	private String remoteMysqlId;
	private String remoteMysqlPwd;
	
	private List commandLine;
	
	public OpenSshTunneling(int index) throws Exception {
		
		this.executeInterval = Configuration.getInt(index, "execute.interval"); 
		this.executeType = Configuration.getString(index, "execute.type");
		
		this.localPort = Configuration.getInt(index, "local.port");
		
		this.remoteHost = Configuration.getString(index, "remote.host");
		this.remotePort = Configuration.getInt(index, "remote.port");
		this.remoteKey = Configuration.getString(index, "remote.key");
		
		if(executeType.equals(MsgCodeConfiguration.MSG_WORD_EXECUTE_TYPE_MYSQL)) {
			this.remoteMysqlHost = Configuration.getString(index, "remote.mysql.host");
			this.remoteMysqlPort = Configuration.getInt(index, "remote.mysql.port");
			this.remoteMysqlId = Configuration.getString(index, "remote.mysql.id");
			this.remoteMysqlPwd = Configuration.getString(index, "remote.mysql.pwd");
		}
		else if(executeType.equals(MsgCodeConfiguration.MSG_WORD_EXECUTE_TYPE_COMMAND)) {
			this.commandLine = Configuration.getList(index, "remote.command.line");
		}
		else {
			ExceptionHandler.exception(MsgCodeException.MSG_TYPE_CONFIGURATION, MsgCodeException.MSG_CODE_WRONG_EXECUTE_TYPE, Configuration.getEnvMap(index).toString());
		}
	}
	
//	public boolean checkSshPort() {
//		boolean result = false;
//		Socket socket = null;
//		try {
//			socket = new Socket("localhost", this.LOCAL_OPEN_PORT);
//			socket.setSoLinger(true, 0);	// Disallow "TIME_WAIT" status of TCP
//			logger.info(this.MAGENTO_RUN_ENV + " -- SSH PORT CHECKING : [" + this.LOCAL_OPEN_PORT + "] is not opened!");
//		}catch (Exception e) {
//			logger.info("SSH PORT CHECKING : [" + this.LOCAL_OPEN_PORT + "] is not opened!");
//			result = true;
//		}finally {
//			try {
//				if(socket != null)
//					socket.close();
//			} catch (Exception e) {
//				logger.error("Exception in closing TCP socket : " + e.toString());
//			
//			}
//		}
//		return result;
//	}
//	
//	public void openSshPort() {
//		try {
//			JSch jsch = new JSch();
//			
//			logger.info(this.MAGENTO_RUN_ENV + " -- TRY TO OPEN PORT : " + this.LOCAL_OPEN_PORT);
//			
//			Session session = jsch.getSession(this.MAGENTO_RUN_ENV_SSH_USER, this.MAGENTO_CLOUD_HOST, this.MAGENTO_CLOUD_PORT);
//			logger.info("\nSSH Connection Information : "
//					+ "\n- SSH ENV : " + this.MAGENTO_RUN_ENV
//					+ "\n- SSH USER : " + this.MAGENTO_RUN_ENV_SSH_USER
//					+ "\n- SSH HOST : " + this.MAGENTO_CLOUD_HOST
//					+ "\n- SSH PORT : " + this.MAGENTO_CLOUD_PORT
//					);
//			
//			Properties config = new Properties();
//			config.put("StrictHostKeyChecking", "no");
//			config.put("ConnectionAttempts", "3");
//			session.setConfig(config);
//			
//			session.connect();
//			logger.info("Success to create SSH Session !");
//			logger.info("TRY TO OPEN SSH TUNNELING !");
//			session.setPortForwardingL(
//					this.LOCAL_OPEN_PORT,
//					this.MAGENTO_MYSQL_HOST,
//					this.MAGENTO_MYSQL_PORT);
//			logger.info("SSH Tunneling - Port Forward (Local -> Destination) : "
//					+ "localhost/" + this.LOCAL_OPEN_PORT + " -> "
//					+ this.MAGENTO_MYSQL_HOST + "/" + this.MAGENTO_MYSQL_PORT);
//			logger.info("Success to open SSH Tunneling !");
//		}catch (Exception e) {
//			logger.error("Exception in opening SSH tunneling : " + e.toString());
//		}
//	}
}
