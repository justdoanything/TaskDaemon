package com.yong.msg;

public class MsgCodeException {
	public static final String MSG_TYPE_CONFIGURATION = "configuration";
	public static final String MSG_TYPE_SOCKET = "socket";
	public static final String MSG_TYPE_SSH = "ssh";
	public static final String MSG_TYPE_RUNNABLE = "runnable";
	public static final String MSG_TYPE_COMMAND = "command";
	public static final String MSG_TYPE_DATABASE = "database";
	
	// Configuration
	public static final String MSG_CODE_WRONG_EXECUTE_TYPE = "WET001";
	public static final String MSG_CODE_WRONG_EXECUTE_TYPE_MSG = "Wrong execute type in application.yml";
	public static final String MSG_CODE_FILE_NOT_FOUND = "FNF001";
	public static final String MSG_CODE_FILE_NOT_FOUND_MSG = "There is a fileNotFoundException in progress";
	public static final String MSG_CODE_INITIAL_APPLICATION_ERROR = "IAE001";
	public static final String MSG_CODE_INITIAL_APPLICATION_ERROR_MSG = "There is a exception in initializing application.yml";
	public static final String MSG_CODE_PARESE_APPLICATION_ERROR = "PAE001";
	public static final String MSG_CODE_PARESE_APPLICATION_ERROR_MSG = "There is a exception in parseing application.yml";
	
	// Socket
	public static final String MSG_CODE_SOCKET_NOT_CLOSE = "SNC001";
	public static final String MSG_CODE_SOCKET_NOT_CLOSE_MSG = "There is a exception in closing socket";
	
	// SSH
	public static final String MSG_CODE_SSH_NOT_OPEN = "SNO001";
	public static final String MSG_CODE_SSH_NOT_OPEN_MSG = "There is a exception in opening SSH";
	public static final String MSG_CODE_TUNNELING_NOT_OPEN = "TNO001";
	public static final String MSG_CODE_TUNNELING_NOT_OPEN_MSG = "There is a exception in opening SSH tunneling";
	
	// Runnable
	public static final String MSG_CODE_RUNNABLE_NOT_RUN = "RNR001";
	public static final String MSG_CODE_RUNNABLE_NOT_RUN_MSG = "There is a exception in running thread";
	
	// Channel
	public static final String MSG_CODE_COMMAND_NOT_EXECUTE = "CNE001";
	public static final String MSG_CODE_COMMAND_NOT_EXECUTE_MSG = "There is a exception in executing commands";
	
	// Database
	public static final String MSG_CODE_DATABASE_NOT_CONNECT = "DNC001";
	public static final String MSG_CODE_DATABASE_NOT_CONNECT_MSG = "There is a exception in connecting database";
	public static final String MSG_CODE_DATABASE_NOT_EXECUTE_UPDATE = "DNU001";
	public static final String MSG_CODE_DATABASE_NOT_EXECUTE_UPDATE_MSG = "There is a exception in executing update query";
	public static final String MSG_CODE_DATABASE_NOT_EXECUTE_INSERT = "DNI001";
	public static final String MSG_CODE_DATABASE_NOT_EXECUTE_INSERT_MSG = "There is a exception in executing insert query";
	public static final String MSG_CODE_DATABASE_NOT_EXECUTE_DELETE = "DND001";
	public static final String MSG_CODE_DATABASE_NOT_EXECUTE_DELETE_MSG = "There is a exception in executing delete query";
	public static final String MSG_CODE_DATABASE_NOT_EXECUTE_SELECT_ONE = "DNSO001";
	public static final String MSG_CODE_DATABASE_NOT_EXECUTE_SELECT_ONE_MSG = "There is a exception in executing select one query";
	public static final String MSG_CODE_DATABASE_NOT_EXECUTE_SELECT_LIST = "DNSL001";
	public static final String MSG_CODE_DATABASE_NOT_EXECUTE_SELECT_LIST_MSG = "There is a exception in executing select list query";
	public static final String MSG_CODE_DATABASE_NOT_GET_SESSION = "DNGS001";
	public static final String MSG_CODE_DATABASE_NOT_GET_SESSION_MSG = "There is a exception in getting sql session";
}
