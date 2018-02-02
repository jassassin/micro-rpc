package org.micro.rpc.util;

/**
 * 对象操作工具类
 * @author jassassin
 * @date 2018年1月18日
 */
public final class ObjectUtil {

	/**
	 * 对象是否为Null 
	 */
	public static final boolean isNull(Object obj){
		return obj == null;
	}
	
	/**
	 * 对象是否非Null 
	 */
	public static final boolean isNotNull(Object obj){
		return !isNull(obj);
	}
	
	/**
	 * obj是否是interCls的子类 
	 */
	public static final boolean isSubCls(Class<?> interCls, Object obj){
		if (isNotNull(obj)){
			return interCls.isAssignableFrom(obj.getClass());
		}
		return false;
	}
}
