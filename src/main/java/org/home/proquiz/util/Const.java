package org.home.proquiz.util;

import java.time.format.DateTimeFormatter;

public class Const {
	private static final String SESSION_USER = new String("user");
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	public static DateTimeFormatter getFormatter() { return formatter; }
	public static String            getUserName()  { return SESSION_USER; }
}
