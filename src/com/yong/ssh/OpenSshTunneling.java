package com.yong.ssh;

import java.net.Socket;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class OpenSshTunneling {

	Logger logger = Logger.getLogger(this.getClass());
	
	private String MAGENTO_CLOUD_PRIVATE_KEY = "";
	private String MAGENTO_CLOUD_HOST = "";
	private int MAGENTO_CLOUD_PORT = 0;
	
	private String MAGENTO_MYSQL_HOST = "";
	private int MAGENTO_MYSQL_PORT = 0;
	
	private String MAGENTO_RUN_ENV = "";
	private String MAGENTO_RUN_ENV_SSH_USER = "";
	private int LOCAL_OPEN_PORT = 0;
	
	public OpenSshTunneling(String env) throws Exception {
		if(env.equals("PROD")) {
			this.MAGENTO_RUN_ENV = "PROD";
			this.MAGENTO_RUN_ENV_SSH_USER = "";
			this.LOCAL_OPEN_PORT = 0;
		}
		if(env.equals("STG")) {
			this.MAGENTO_RUN_ENV = "STG";
			this.MAGENTO_RUN_ENV_SSH_USER = "";
			this.LOCAL_OPEN_PORT = 0;
		}
	}
	
	public boolean checkSshPort() {
		boolean result = false;
		Socket socket = null;
		try {
			socket = new Socket("localhost", this.LOCAL_OPEN_PORT);
			socket.setSoLinger(true, 0);	// Disallow "TIME_WAIT" status of TCP
			logger.info(this.MAGENTO_RUN_ENV + " -- SSH PORT CHECKING : [" + this.LOCAL_OPEN_PORT + "] is not opened!");
		}catch (Exception e) {
			logger.info("SSH PORT CHECKING : [" + this.LOCAL_OPEN_PORT + "] is not opened!");
			result = true;
		}finally {
			try {
				if(socket != null)
					socket.close();
			} catch (Exception e) {
				logger.error("Exception in closing TCP socket : " + e.toString());
			
			}
		}
		return result;
	}
	
	public void openSshPort() {
		try {
			JSch jsch = new JSch();
			
			logger.info(this.MAGENTO_RUN_ENV + " -- TRY TO OPEN PORT : " + this.LOCAL_OPEN_PORT);
			
			Session session = jsch.getSession(this.MAGENTO_RUN_ENV_SSH_USER, this.MAGENTO_CLOUD_HOST, this.MAGENTO_CLOUD_PORT);
			logger.info("\nSSH Connection Information : "
					+ "\n- SSH ENV : " + this.MAGENTO_RUN_ENV
					+ "\n- SSH USER : " + this.MAGENTO_RUN_ENV_SSH_USER
					+ "\n- SSH HOST : " + this.MAGENTO_CLOUD_HOST
					+ "\n- SSH PORT : " + this.MAGENTO_CLOUD_PORT
					);
			
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			config.put("ConnectionAttempts", "3");
			session.setConfig(config);
			
			session.connect();
			logger.info("Success to create SSH Session !");
			logger.info("TRY TO OPEN SSH TUNNELING !");
			session.setPortForwardingL(
					this.LOCAL_OPEN_PORT,
					this.MAGENTO_MYSQL_HOST,
					this.MAGENTO_MYSQL_PORT);
			logger.info("SSH Tunneling - Port Forward (Local -> Destination) : "
					+ "localhost/" + this.LOCAL_OPEN_PORT + " -> "
					+ this.MAGENTO_MYSQL_HOST + "/" + this.MAGENTO_MYSQL_PORT);
			logger.info("Success to open SSH Tunneling !");
		}catch (Exception e) {
			logger.error("Exception in opening SSH tunneling : " + e.toString());
		}
	}
}
