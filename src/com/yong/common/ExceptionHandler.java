package com.yong.common;

import java.io.FileNotFoundException;

import com.yong.msg.MsgCodeException;

public class ExceptionHandler {

	public static void exception(String type, String code, String message) throws Exception {
		
		if(type.equals(MsgCodeException.MSG_TYPE_CONFIGURATION)) {
			switch(code) {
			case MsgCodeException.MSG_CODE_WRONG_EXECUTE_TYPE :
				throw new Exception(MsgCodeException.MSG_CODE_WRONG_EXECUTE_TYPE_MSG + " : " + message);
			case MsgCodeException.MSG_CODE_FILE_NOT_FOUND :
				throw new FileNotFoundException(MsgCodeException.MSG_CODE_FILE_NOT_FOUND_MSG + " : " + message);
			case MsgCodeException.MSG_CODE_PARESE_APPLICATION_ERROR :
				throw new Exception(MsgCodeException.MSG_CODE_PARESE_APPLICATION_ERROR_MSG + " : " + message);
			case MsgCodeException.MSG_CODE_INITIAL_APPLICATION_ERROR :
				throw new Exception(MsgCodeException.MSG_CODE_INITIAL_APPLICATION_ERROR_MSG + " : " + message);
			default:
				throw new Exception(message);
			}
		} else if(type.equals(MsgCodeException.MSG_TYPE_SOCKET)){
			switch(code) {
			case MsgCodeException.MSG_CODE_SOCKET_NOT_CLOSE :
				throw new Exception(MsgCodeException.MSG_CODE_SOCKET_NOT_CLOSE_MSG + " : " + message);
			default:
				throw new Exception(message);
			}
			
		} else if(type.equals(MsgCodeException.MSG_TYPE_SSH)){
			switch(code) {
			case MsgCodeException.MSG_CODE_SSH_NOT_OPEN :
				throw new Exception(MsgCodeException.MSG_CODE_SSH_NOT_OPEN_MSG + " : " + message);
			case MsgCodeException.MSG_CODE_TUNNELING_NOT_OPEN :
				throw new Exception(MsgCodeException.MSG_CODE_TUNNELING_NOT_OPEN_MSG + " : " + message);
			default:
				throw new Exception(message);
			}
		}
	}
}
