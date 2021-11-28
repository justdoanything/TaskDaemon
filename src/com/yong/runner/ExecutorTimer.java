package com.yong.runner;

import java.util.Timer;
import java.util.TimerTask;

import com.jcraft.jsch.Session;
import com.yong.config.Configuration;
import com.yong.connector.ConnectorChannel;
import com.yong.connector.ConnectorSSH;
import com.yong.handler.LoggingHandler;
import com.yong.msg.MsgCodeConfiguration;
import com.yong.msg.MsgCodeException;

public class ExecutorTimer implements Runnable {

	private LoggingHandler logger = new LoggingHandler(this.getClass(), Configuration.loggerUse);
	private int fileDelay = 60 * 1000; // default : 60s
	
	ConnectorSSH connectorSSH = null;
	
	/**
	 * @author yongwoo
	 * @throws Exception
	 * @category Execute
	 * @implNote Set ssh class you want to execute
	 */
	public ExecutorTimer(ConnectorSSH connectorSSH) {
		this.connectorSSH = connectorSSH;
	}
	
	/**
	 * @author yongwoo
	 * @throws Exception
	 * @category Execute
	 * @implNote Execute ssh class at intervals
	 */
	@Override
	public void run() {
		try {
			// Set time interval to run this program
			fileDelay = connectorSSH.getExecuteInterval();
			
			logger.info(String.format("[" + connectorSSH.getEnv() + "] Time Interval Delay : %d (ms)", fileDelay));
			
			// Running the program every "fileDelay (ms)"
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					try {
						if(connectorSSH != null && connectorSSH.checkSshPort()) {
							Session session = connectorSSH.openSshPort();
							
							// Checking execute.type = command && there is command.line
							if(connectorSSH.getExecuteType().equals(MsgCodeConfiguration.MSG_WORD_EXECUTE_TYPE_COMMAND) && connectorSSH.getRemoteCommandLine() != null) {
								String resultCommMsg = "";
								
								// Execute commands as remote.command.list
								for(String command : connectorSSH.getRemoteCommandLine()) {
									resultCommMsg = ConnectorChannel.runCommand(session, command);
									if(resultCommMsg.length() == 0) {
										logger.info("[" + connectorSSH.getEnv() + "] Result of Command : Empty ");
									}else {
										logger.info("[" + connectorSSH.getEnv() + "] Result of Command : \n" + resultCommMsg);
									}
								}
							}
							
							// Checking execute.type = mysql && there is remote.db.mybatis
							else if(connectorSSH.getExecuteType().equals(MsgCodeConfiguration.MSG_WORD_EXECUTE_TYPE_MYSQL) 
									&& connectorSSH.getRemoteDbMybatis() != null) {
								// Set Mybatis and connect sql factory
								
								
								// Run mysql query to tunnel
							}
						}
					}catch (Exception e) {
						e.printStackTrace();
						logger.error("[" + connectorSSH.getEnv() + "]" + MsgCodeException.MSG_CODE_SSH_NOT_OPEN_MSG + " : " + e.toString());
					}
				}
			}, 1000, fileDelay);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("[" + connectorSSH.getEnv() + MsgCodeException.MSG_CODE_RUNNABLE_NOT_RUN_MSG + " : " + e.toString());
		}
	}
}
