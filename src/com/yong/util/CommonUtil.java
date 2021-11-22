package com.yong.util;

import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class CommonUtil {

	public static Map ObjToMap(Object obj) throws Exception {
		return (Map) obj;
	}
	
	public static List ObjToList(Object obj) throws Exception {
		return (List) obj;
	}
	
	public static String ObjToString(Object obj) throws Exception {
		return (String) obj;
	}
	
	public static int ObjToInt(Object obj) throws Exception {
		return (int) obj;
	}
	
	public static boolean ObjToBoolean(Object obj) throws Exception {
		if(ObjToString(obj).equals("1"))
			return true;
		else
			return false;
	}
	
	public static boolean mapOrNot(Object obj) throws Exception {
		boolean result = true;
		try {
			CommonUtil.ObjToMap(obj);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	
}
