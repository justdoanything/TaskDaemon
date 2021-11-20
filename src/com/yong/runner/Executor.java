package com.yong.runner;

import com.yong.common.Configuration;

public class Executor {

	public static void main(String[] args) {
		try {
			Configuration config = new Configuration();
			config.initialize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

