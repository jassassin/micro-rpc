package org.micro.rpc.serialization.engine;

import java.util.Map;

import org.micro.rpc.serialization.serializer.ISerializer;
import org.micro.rpc.serialization.serializer.impl.AvroSerializer;
import org.micro.rpc.serialization.serializer.impl.DefaultJavaSerializer;
import org.micro.rpc.serialization.serializer.impl.KryoSerializer;
import org.micro.rpc.serialization.serializer.impl.ProtoStuffSerializer;
import org.micro.rpc.serialization.serializer.impl.XmlSerializer;
import org.micro.rpc.util.ArrayUtil;
import org.micro.rpc.util.MapUtil;
import org.micro.rpc.util.ObjectUtil;

/**
 * 序列化引擎
 * @author jassassin
 * @date 2018年1月18日
 */
public class SerializerEngine {

	private static final Map<SerializerType, ISerializer> serializerMap = MapUtil.newConcurrentHashMap();
	
	static {
		serializerMap.put(SerializerType.JAVA, new DefaultJavaSerializer());
		serializerMap.put(SerializerType.XML, new XmlSerializer());
		serializerMap.put(SerializerType.PROTO_STUFF, new ProtoStuffSerializer());
		serializerMap.put(SerializerType.KRYO, new KryoSerializer());
		
		// 不能直接使用,需要先写schema
		serializerMap.put(SerializerType.AVRO, new AvroSerializer());
	}
	
	/**
	 * 序列化 
	 */
	public static <T> byte[] serialize(T obj, SerializerType type){
		byte[] data = null;
		try {
			if (ObjectUtil.isNotNull(obj)){
				ISerializer serializer = serializerMap.get(type);
				data = serializer.serialize(obj);
			}
		} catch (Exception e){
			throw new RuntimeException(e);
		}
		return data;
	}
	
	/**
	 * 反序列化
	 */
	public static <T> T deserialize(byte[] data, Class<T> clazz, SerializerType type){
		T obj = null;
		try {
			if (ArrayUtil.isNotEmpty(data)){
				ISerializer serializer = serializerMap.get(type);
				obj = serializer.deserialize(data, clazz);
			}
		} catch (Exception e){
			throw new RuntimeException(e);
		}
		return obj;
	}
}
