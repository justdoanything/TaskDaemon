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
	
}
