package org.micro.rpc.serialization.serializer.impl;

import java.util.Map;

import org.micro.rpc.serialization.serializer.ISerializer;
import org.micro.rpc.util.ArrayUtil;
import org.micro.rpc.util.MapUtil;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * ProtoStuff序列化
 * @author jassassin
 * @date 2018年2月2日
 */
public class ProtoStuffSerializer implements ISerializer {

	private static final Map<Class<?>, Schema<?>> cachedSchema = MapUtil.newConcurrentHashMap();
	private static final Objenesis objenesis = new ObjenesisStd(true);
	
	@SuppressWarnings("unchecked")
	public static <T> Schema<T> getSchema(Class<T> clazz){
		Schema<T> schema = (Schema<T>) cachedSchema.get(clazz);
		if (schema == null){
			schema = RuntimeSchema.createFrom(clazz);
			if (schema != null){
				cachedSchema.put(clazz, schema);
			}
		}
		return schema;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> byte[] serialize(T obj) {
		byte[] data = null;
		if (obj != null){
			Class<T> clazz = (Class<T>) obj.getClass();
			LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
			Schema<T> schema = getSchema(clazz);
			try {
				data = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
			} catch (Exception e){
				throw new RuntimeException(e);
			} finally {
				buffer.clear();
			}
		}
		return data;
	}

	@Override
	public <T> T deserialize(byte[] data, Class<T> cls) {
		T obj = null;
		if (ArrayUtil.isNotEmpty(data)){
			try {
				obj = objenesis.newInstance(cls);
				Schema<T> schema = getSchema(cls);
				ProtostuffIOUtil.mergeFrom(data, obj, schema);
			} catch (Exception e){
				throw new RuntimeException(e);
			}
		}
		return obj;
	}

}
