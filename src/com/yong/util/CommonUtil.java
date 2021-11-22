package com.yong.util;

import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class CommonUtil {

	public static Map ObjToMap(Object obj) {
		return (Map) obj;
	}
	
	public static List ObjToList(Object obj) {
		return (List) obj;
	}
	
	public static String ObjToString(Object obj) {
		return (String) obj;
	}
	
	public static boolean mapOrNot(Object obj) {
		boolean result = true;
		try {
			CommonUtil.ObjToMap(obj);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	
}
