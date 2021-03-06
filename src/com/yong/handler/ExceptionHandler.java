package com.yong.handler;

import java.io.FileNotFoundException;

import com.yong.msg.MsgCodeException;

public class ExceptionHandler {

	/**
	 * @author yongwoo
	 * @throws Exception
	 * @category Exception
	 * @implNote Handler exception type, code, message
	 */
	public static void exception(String type, String code, String message) throws Exception {
		
		if(type.equals(MsgCodeException.MSG_TYPE_CONFIGURATION)) {
			// Configuration
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
			// Socket
			switch(code) {
			case MsgCodeException.MSG_CODE_SOCKET_NOT_CLOSE :
				throw new Exception(MsgCodeException.MSG_CODE_SOCKET_NOT_CLOSE_MSG + " : " + message);
			default:
				throw new Exception(message);
			}
			
		} else if(type.equals(MsgCodeException.MSG_TYPE_SSH)){
			// SSH
			switch(code) {
			case MsgCodeException.MSG_CODE_SSH_NOT_OPEN :
				throw new Exception(MsgCodeException.MSG_CODE_SSH_NOT_OPEN_MSG + " : " + message);
			case MsgCodeException.MSG_CODE_TUNNELING_NOT_OPEN :
				throw new Exception(MsgCodeException.MSG_CODE_TUNNELING_NOT_OPEN_MSG + " : " + message);
			default:
				throw new Exception(message);
			}
		} else if(type.equals(MsgCodeException.MSG_TYPE_RUNNABLE)){
			// Runnable
			switch(code) {
			case MsgCodeException.MSG_CODE_RUNNABLE_NOT_RUN :
				throw new Exception(MsgCodeException.MSG_CODE_RUNNABLE_NOT_RUN_MSG + " : " + message);
			default:
				throw new Exception(message);
			}
		} else if(type.equals(MsgCodeException.MSG_TYPE_COMMAND)) {
			// Command
			switch(code) {
			case MsgCodeException.MSG_CODE_COMMAND_NOT_EXECUTE :
				throw new Exception(MsgCodeException.MSG_CODE_COMMAND_NOT_EXECUTE_MSG + " : " + message);
			default:
				throw new Exception(message);
			}
		} else if(type.equals(MsgCodeException.MSG_TYPE_DATABASE)) {
			// Database
			switch(code) {
			case MsgCodeException.MSG_CODE_DATABASE_NOT_CONNECT :
				throw new Exception(MsgCodeException.MSG_CODE_DATABASE_NOT_CONNECT_MSG + " : " + message);
			case MsgCodeException.MSG_CODE_DATABASE_NOT_EXECUTE_UPDATE :
				throw new Exception(MsgCodeException.MSG_CODE_DATABASE_NOT_EXECUTE_UPDATE_MSG + " : " + message);
			case MsgCodeException.MSG_CODE_DATABASE_NOT_EXECUTE_INSERT :
				throw new Exception(MsgCodeException.MSG_CODE_DATABASE_NOT_EXECUTE_INSERT_MSG + " : " + message);
			case MsgCodeException.MSG_CODE_DATABASE_NOT_EXECUTE_DELETE :
				throw new Exception(MsgCodeException.MSG_CODE_DATABASE_NOT_EXECUTE_DELETE_MSG + " : " + message);
			case MsgCodeException.MSG_CODE_DATABASE_NOT_EXECUTE_SELECT_ONE :
				throw new Exception(MsgCodeException.MSG_CODE_DATABASE_NOT_EXECUTE_SELECT_ONE_MSG + " : " + message);
			case MsgCodeException.MSG_CODE_DATABASE_NOT_EXECUTE_SELECT_LIST :
				throw new Exception(MsgCodeException.MSG_CODE_DATABASE_NOT_EXECUTE_SELECT_LIST_MSG + " : " + message);
			case MsgCodeException.MSG_CODE_DATABASE_NOT_GET_SESSION :
				throw new Exception(MsgCodeException.MSG_CODE_DATABASE_NOT_GET_SESSION_MSG + " : " + message);
			default:
				throw new Exception(message);
			}
		} else {
			// type not defined 
			throw new Exception(message);
		}
	}
}
