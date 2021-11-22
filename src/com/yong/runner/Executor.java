package com.yong.runner;

import com.yong.common.Configuration;
import com.yong.ssh.OpenSshTunneling;

public class Executor {

	public static void main(String[] args) {
		try {
			Configuration config = new Configuration();
			config.initialize();
			
			for(int index = 0; index < config.getEnvListSize(); index++) {
				OpenSshTunneling ost = new OpenSshTunneling(null);
				Runnable runnable = new ExecuteTimer(ost);
				runnable.run();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

