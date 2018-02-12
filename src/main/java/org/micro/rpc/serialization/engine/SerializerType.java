package org.micro.rpc.serialization.engine;

import org.micro.rpc.util.StringUtil;

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
	
	public static SerializerType getType(String type){
		SerializerType serializerType = null;
		if (StringUtil.isNotEmpty(type)){
			String upperType = type.toUpperCase();
			switch (upperType) {
			case "JAVA":
				serializerType = JAVA;
				break;
			case "AVRO":
				serializerType = AVRO;
				break;
			case "JSON":
				serializerType = JSON;
				break;
			case "XML":
				serializerType = XML;
				break;
			case "PROTO_STUFF":
				serializerType = PROTO_STUFF;
				break;
			case "KRYO":
				serializerType = KRYO;
				break;
			default:
				break;
			}
		}
		return serializerType;
	}
}
