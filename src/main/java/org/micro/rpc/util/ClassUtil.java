package org.micro.rpc.util;

/**
 * class工具类
 * @author jassassin
 * @date 2018年3月9日
 */
public final class ClassUtil {

	public static final Class<?> loadClass(String className, boolean initialize){
		Class<?> targetClass = null;
		try {
			targetClass = Class.forName(className, initialize, getClassLoader());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("failed loadClass:" + className, e);
		}
		return targetClass;
	}
	
	public static final Class<?> loadClass(String className){
		return loadClass(className, true);
	}
	
	public static final ClassLoader getClassLoader(){
		return Thread.currentThread().getContextClassLoader();
	}
}
