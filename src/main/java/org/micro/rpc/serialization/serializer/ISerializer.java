package org.micro.rpc.serialization.serializer;
/**
 * 序列化接口
 * @author jassassin
 * @date 2018年1月18日
 */

public interface ISerializer {

	/**
	 * 序列化
	 * @param obj
	 * @return
	 */
	<T> byte[] serialize(T obj);
	
	/**
	 * 反序列化
	 * @param data
	 * @param cls
	 * @return
	 */
	<T> T deserialize(byte[] data, Class<T> cls);
}
