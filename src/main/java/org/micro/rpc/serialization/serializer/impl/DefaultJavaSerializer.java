package org.micro.rpc.serialization.serializer.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.micro.rpc.serialization.serializer.ISerializer;
import org.micro.rpc.util.ArrayUtil;
import org.micro.rpc.util.IOUtil;
import org.micro.rpc.util.ObjectUtil;

/**
 * JDK序列化实现
 * @author jassassin
 * @date 2018年1月18日
 */
public class DefaultJavaSerializer implements ISerializer {

	@Override
	public <T> byte[] serialize(T obj) {
		byte[] data = null;
		if (ObjectUtil.isNotNull(obj)){
			ByteArrayOutputStream byteStream = null;
			ObjectOutputStream outStream = null;
			try {
				byteStream = new ByteArrayOutputStream();
				outStream = new ObjectOutputStream(byteStream);
				outStream.writeObject(obj);
				data = byteStream.toByteArray();
			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				IOUtil.close(outStream);
			}
		}
		return data;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(byte[] data, Class<T> cls) {
		T obj = null;
		if (ArrayUtil.isNotEmpty(data)){
			ObjectInputStream inStream = null;
			try {
				inStream = new ObjectInputStream(new ByteArrayInputStream(data));
				obj = (T) inStream.readObject();
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				IOUtil.close(inStream);
			}
		}
		return obj;
	}

}
