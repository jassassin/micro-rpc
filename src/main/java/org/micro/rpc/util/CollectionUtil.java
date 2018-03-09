package org.micro.rpc.util;

import java.util.Collection;

/**
 * 集合工具类
 * @author jassassin
 * @date 2018年3月8日
 */
public final class CollectionUtil {

	public static final boolean isEmpty(Collection<?> coll){
		return coll == null || coll.size() == 0;
	}
	
	public static final boolean isNotEmpty(Collection<?> coll){
		return !isEmpty(coll);
	}
}
