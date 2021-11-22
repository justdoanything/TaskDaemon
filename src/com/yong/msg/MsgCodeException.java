package com.yong.msg;

public class MsgCodeException {
	public static final String MSG_TYPE_CONFIGURATION = "configuration";
	public static final String MSG_TYPE_SOCKET = "socket";
	public static final String MSG_TYPE_SSH = "ssh";
	
	public static final String MSG_CODE_WRONG_EXECUTE_TYPE = "WET001";
	public static final String MSG_CODE_WRONG_EXECUTE_TYPE_MSG = "Wrong execute type in application.yml";
	public static final String MSG_CODE_FILE_NOT_FOUND = "FNF001";
	public static final String MSG_CODE_FILE_NOT_FOUND_MSG = "There is a fileNotFoundException in progress";
	public static final String MSG_CODE_INITIAL_APPLICATION_ERROR = "IAE001";
	public static final String MSG_CODE_INITIAL_APPLICATION_ERROR_MSG = "There is a exception in initializing application.yml";
	public static final String MSG_CODE_PARESE_APPLICATION_ERROR = "PAE001";
	public static final String MSG_CODE_PARESE_APPLICATION_ERROR_MSG = "There is a exception in parseing application.yml";
	
	public static final String MSG_CODE_SOCKET_NOT_CLOSE = "SNC001";
	public static final String MSG_CODE_SOCKET_NOT_CLOSE_MSG = "There is a exception in closing socket";
	
	public static final String MSG_CODE_SSH_NOT_OPEN = "SNO001";
	public static final String MSG_CODE_SSH_NOT_OPEN_MSG = "There is a exception in opening SSH";
	public static final String MSG_CODE_TUNNELING_NOT_OPEN = "TNO001";
	public static final String MSG_CODE_TUNNELING_NOT_OPEN_MSG = "There is a exception in opening SSH tunneling";
	
	public static final String MSG_CODE_RUNNABLE_NOT_RUN = "RNR001";
	public static final String MSG_CODE_RUNNABLE_NOT_RUN_MSG = "There is a exception in running thread";
	
}
