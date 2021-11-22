package com.yong.runner;

import com.yong.common.Configuration;
import com.yong.ssh.OpenSshTunneling;

public class Executor {

	public static void main(String[] args) {
		try {
			Configuration.initialize();
			
			for(int index = 0; index < Configuration.getEnvListSize(); index++) {
				OpenSshTunneling ost = new OpenSshTunneling(index);
//				Runnable runnable = new ExecuteTimer(ost);
//				runnable.run();
			}
			System.out.println("COMPLETE");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

