package org.micro.rpc.serialization.serializer.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.micro.rpc.serialization.serializer.ISerializer;
import org.micro.rpc.util.ArrayUtil;
import org.micro.rpc.util.IOUtil;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * kryo序列化
 * @author jassassin
 * @date 2018年2月2日
 */
public class KryoSerializer implements ISerializer {

	@Override
	public <T> byte[] serialize(T obj) {
		byte[] data = null;
		if (obj != null){
			ByteArrayOutputStream outputStream = null;
			Output output = null;
			try {
				outputStream = new ByteArrayOutputStream();
				output = new Output(outputStream);
				Kryo kryo = new Kryo();
				kryo.writeObject(output, obj);
				output.flush();
				data = outputStream.toByteArray();
			} catch (Exception e){
				throw new RuntimeException(e);
			} finally {
				IOUtil.close(output);
				IOUtil.close(outputStream);
			}
		}
		return data;
	}

	@Override
	public <T> T deserialize(byte[] data, Class<T> cls) {
		T obj = null;
		if (ArrayUtil.isNotEmpty(data)){
			ByteArrayInputStream inputStream = null;
			Input input = null;
			try {
				inputStream = new ByteArrayInputStream(data);
				input = new Input(inputStream);
				Kryo kryo = new Kryo();
				obj = kryo.readObject(input, cls);
			} catch (Exception e){
				throw new RuntimeException(e);
			} finally {
				IOUtil.close(input);
				IOUtil.close(inputStream);
			}
		}
		return obj;
	}

}
