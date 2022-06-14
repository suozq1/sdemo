package com.suo.sdemo.util;

public class StringUitls {
	/**
	 * 如果有一个为空，则返回true
	 * @param args
	 * @return
	 */
	public static boolean isAnyEmpty(Object ... args) {
		for(Object s : args) {
			if(s==null || s.toString().length() == 0) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isEmpty(Object obj) {
		if(obj==null) {
			return true;
		}
		if(obj.toString().length()==0) {
			return true;
		}
		return false;
	}
}
