package com.yong.runner;



import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.yong.common.Configuration;
import com.yong.ssh.OpenSshTunneling;

public class Executor {

	public static void main(String[] args) {
		try {
			if(args == null || args.length == 0) {
				System.out.println();
			} else {
				// Set context.properties
				Configuration.initialize(args[0]);
				
				Logger logger = Logger.getLogger(Configuration.class);
				
				// Set Running Environment
				List<String> envList = Arrays.asList(Configuration.getString(""));
				logger.info("Running Environment : " + Configuration.getString(""));
				
				if(envList.contains("PROD")) {
					OpenSshTunneling ost = new OpenSshTunneling("PROD");
					Runnable run = new ExecuteTimer(ost);
					run.run();
				}
				if(envList.contains("STG")) {
					OpenSshTunneling ost2 = new OpenSshTunneling("STG");
					Runnable run2 = new ExecuteTimer(ost2);
					run2.run();
				}
			}
			
		}catch (Exception e) {
			
		}
	}

}
