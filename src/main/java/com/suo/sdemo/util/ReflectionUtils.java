package com.suo.sdemo.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionUtils {
	
	public static final String METHOD_GET_PREFIX = "get";
	static Logger log = LoggerFactory.getLogger(ReflectionUtils.class);
	/**
	 * 把 o 中字段名称相同的值，复制到clazz对应的class对象中，并返回该实例
	 * @param <T>
	 * @param clazz
	 * @param o
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <T> T newInstance(Class<T> clazz,Object o) throws InstantiationException, IllegalAccessException {
		@SuppressWarnings("deprecation")
		T t = clazz.newInstance();
		Class<?> oClass = o.getClass();
		for(Field field:clazz.getDeclaredFields()) {
			String methodName = METHOD_GET_PREFIX + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
			try {
				Method m = oClass.getMethod(methodName);
				Object fieldValue = m.invoke(o);
				field.setAccessible(true);
				field.set(t, fieldValue);
			}catch(Exception e) {
				log.trace(e.getMessage(),e);
			} 
		}
		return t;
	}
	
	@SuppressWarnings("unchecked")
    public static <T> T getFieldValue(String fieldName,Object obj) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
	   return (T)obj.getClass().getField(fieldName).get(obj);
	}
	
	@SuppressWarnings("unchecked")
    public static <T> T getStaticFieldValue(String fieldName,Class<?> cls) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
       return (T)cls.getField(fieldName).get(null);
    }
}
