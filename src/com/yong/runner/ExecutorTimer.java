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
	
	ConnectorSSH ost = null;
	
	/**
	 * @author yongwoo
	 * @throws Exception
	 * @category Execute
	 * @implNote Set ssh class you want to execute
	 */
	public ExecutorTimer(ConnectorSSH ost) {
		this.ost = ost;
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
			fileDelay = ost.getExecuteInterval();
			
			logger.info(String.format("[" + ost.getEnv() + "] Time Interval Delay : %d (ms)", fileDelay));
			
			// Running the program every "fileDelay (ms)"
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					try {
						if(ost != null && ost.checkSshPort()) {
							Session session = ost.openSshPort();
							
							// Checking execute.type = command && there is command.line
							if(ost.getExecuteType().equals(MsgCodeConfiguration.MSG_WORD_EXECUTE_TYPE_COMMAND) && ost.getRemoteCommandLine() != null) {
								String resultCommMsg = "";
								
								// Execute commands as remote.command.list
								for(String command : ost.getRemoteCommandLine()) {
									resultCommMsg = ConnectorChannel.runCommand(session, command);
									if(resultCommMsg.length() == 0) {
										logger.info("[" + ost.getEnv() + "] Result of Command : Empty ");
									}else {
										logger.info("[" + ost.getEnv() + "] Result of Command : \n" + resultCommMsg);
									}
								}
							}
							
							// Checking execute.type = mysql && there is remove.mysql.mybatis
							if(ost.getExecuteType().equals(MsgCodeConfiguration.MSG_WORD_EXECUTE_TYPE_MYSQL) && ost.getRemoteMysqlMybatis() != null) {
								// Set Mybatis
								
								// Run mysql query to tunnel
								ost.getLocalPort();
								ost.getRemoteMysqlId();
								ost.getRemoteMysqlPwd();
								
							}
						}
					}catch (Exception e) {
						e.printStackTrace();
						logger.error("[" + ost.getEnv() + "]" + MsgCodeException.MSG_CODE_SSH_NOT_OPEN_MSG + " : " + e.toString());
					}
				}
			}, 1000, fileDelay);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("[" + ost.getEnv() + MsgCodeException.MSG_CODE_RUNNABLE_NOT_RUN_MSG + " : " + e.toString());
		}
	}
}
