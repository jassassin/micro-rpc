package org.micro.rpc.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Map集合操作工具类
 * @author jassassin
 * @date 2018年1月18日
 */
public final class MapUtil {

	/**
	 * 新建HashMap,非线程安全 
	 */
	public static final <K, V> HashMap<K, V> newHashMap(){
		return new HashMap<K, V>();
	}
	
	/**
	 * 新建线程安全ConcurrentHashMap
	 */
	public static final <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap(){
		return new ConcurrentHashMap<K, V>();
	}
	
	public static final <K,V> boolean isEmpty(Map<K, V> map){
		return map == null || map.size() == 0;
	} 
	
	public static final <K,V> boolean isNotEmpty(Map<K, V> map){
		return !isEmpty(map);
	}
}
