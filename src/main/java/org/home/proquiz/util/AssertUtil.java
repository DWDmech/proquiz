package org.home.proquiz.util;

public final class AssertUtil {
	private AssertUtil() {}
	
	public static void isNull(Object obj) {
		isNull(obj, "[ Assertion failed ] - Object is null");
	}
	
	public static void isNull(Object obj, String msg) {
		if (obj == null) 
			throw new NullPointerException(msg);
	}
	
	public static void isPositiv(Long l, String msg) {
		if(0 > l) {
			throw new IllegalArgumentException(msg);
		}
	}
	
	public static void isPositiv(Long l) {
		isPositiv(l,"[ Assertion failed ] - Long value is less than zero");
	}

	public static void isPositiv(Integer num, String msg) {
		if(0 > num) {
			throw new IllegalArgumentException(msg);
		}
	}
	public static void isPositiv(Integer num) {
		isPositiv(num,"[ Assertion failed ] - Integer value is less than zero");
	}
}
