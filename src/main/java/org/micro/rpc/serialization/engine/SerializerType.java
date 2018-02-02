package org.micro.rpc.serialization.engine;

/**
 * 序列化方式
 * @author jassassin
 * @date 2018年1月18日
 */
public enum SerializerType {
	
	/**
	 * Serialization接口实现
	 */
	JAVA,
	
	/**
	 * Avro序列化
	 */
	AVRO,
	
	/**
	 * Json序列化
	 */
	JSON,
	
	/**
	 * XML 序列化
	 */
	XML,
	
	/**
	 * ProtoStuff序列化
	 */
	PROTO_STUFF,
	
	/**
	 * kryo序列化
	 */
	KRYO;
}
