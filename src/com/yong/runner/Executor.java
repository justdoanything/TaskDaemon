package com.yong.runner;

import com.yong.common.Configuration;
import com.yong.ssh.ConnectorSSH;

public class Executor {
	public static void main(String[] args) {
		try {
			//initializa this application
			Configuration.initialize();

			// Execute threads as many as number of "env" in application.yml
			for(int index = 0; index < Configuration.getEnvListSize(); index++) {
				// Set the environment value from application.yml
				ConnectorSSH ost = new ConnectorSSH(index);
				
				// Execute the program at intervals of "execute.interval" set in application.yml
				Runnable runnable = new ExecutorTimer(ost);
				runnable.run();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

