package com.yong.ssh;

import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;
import com.yong.common.Configuration;
import com.yong.common.LoggingHandler;

public class ExecutorCommand {
	
	private static LoggingHandler logger = new LoggingHandler(ExecutorCommand.class, Configuration.loggerUse);
	
	public static String runCommand(Session session, String command) {
		String resultMsg = "";
		try {
			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setErrStream(System.err);
			((ChannelExec) channel).setCommand(command);
			channel.connect();
				
			logger.info("Execute command >>> " + command);
			InputStream inputStream = channel.getInputStream();
				
			byte[] result = new byte[1024];
			while (true) {
				while (inputStream.available() > 0) {
					int i = inputStream.read(result, 0, 1024);
					if (i < 0) break;
					resultMsg += new String(result, 0, i);
				}
				if(channel.isClosed())
					break;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resultMsg;
	}
}
