package com.yong.connector;

import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;
import com.yong.config.Configuration;
import com.yong.handler.ExceptionHandler;
import com.yong.handler.LoggingHandler;
import com.yong.msg.MsgCodeException;

public class ConnectorChannel {
	
	private static LoggingHandler logger = new LoggingHandler(ConnectorChannel.class, Configuration.loggerUse);
	
	public static String runCommand(Session session, String command) throws Exception {
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
			logger.error(MsgCodeException.MSG_CODE_COMMAND_NOT_EXECUTE_MSG + " : " + e.toString());
			ExceptionHandler.exception(MsgCodeException.MSG_TYPE_CHANNEL, MsgCodeException.MSG_CODE_COMMAND_NOT_EXECUTE_MSG, e.toString());
		}
		return resultMsg;
	}
}
