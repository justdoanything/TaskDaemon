package com.yong.runner;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.yong.common.Configuration;
import com.yong.ssh.OpenSshTunneling;

public class ExecuteTimer implements Runnable {

	Logger logger = Logger.getLogger(this.getClass());
	private int fileDelay = 60 * 1000; // default : 60s
	
	OpenSshTunneling ost = null;
	
	public ExecuteTimer(OpenSshTunneling ost) {
		this.ost = ost;
	}
	
	@Override
	public void run() {
		try {
			// Set time interval to run this program
//			fileDelay = Configuration.getInt(MsgCode.CONF_KEY_TIME_INTERVAL);
			
			logger.info(String.format("Time Interval Delay : %d (ms)", fileDelay));
			
			// Running the program every "fileDelay (ms)"
			new Timer().schedule(new TimerTask() {
				
				@Override
				public void run() {
					try {
						if(ost != null && ost.checkSshPort())
							ost.openSshPort();
					}catch (Exception e) {
						logger.error("Exception in opening SSH : " + e.toString());
					}
				}
			}, 1000, fileDelay);
		}catch (Exception e) {
			logger.error("Exception in Runnable : " + e.toString());
		}
	}
}
