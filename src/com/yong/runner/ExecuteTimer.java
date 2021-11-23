package com.yong.runner;

import java.util.Timer;
import java.util.TimerTask;

import com.yong.common.Configuration;
import com.yong.common.LoggingHandler;
import com.yong.msg.MsgCodeException;
import com.yong.ssh.OpenSshTunneling;

public class ExecuteTimer implements Runnable {

	LoggingHandler logger = new LoggingHandler(this.getClass(), Configuration.loggerUse);
	private int fileDelay = 60 * 1000; // default : 60s
	
	OpenSshTunneling ost = null;
	
	public ExecuteTimer(OpenSshTunneling ost) {
		this.ost = ost;
	}
	
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
						if(ost != null && ost.checkSshPort())
							ost.openSshPort();
					}catch (Exception e) {
						logger.error("[" + ost.getEnv() + "]" + MsgCodeException.MSG_CODE_SSH_NOT_OPEN_MSG + " : " + e.toString());
					}
				}
			}, 1000, fileDelay);
		}catch (Exception e) {
			logger.error("[" + ost.getEnv() + MsgCodeException.MSG_CODE_RUNNABLE_NOT_RUN_MSG + " : " + e.toString());
		}
	}
}
