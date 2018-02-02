package org.micro.rpc.serialization.serializer.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.micro.rpc.serialization.serializer.ISerializer;
import org.micro.rpc.util.ArrayUtil;
import org.micro.rpc.util.IOUtil;
import org.micro.rpc.util.ObjectUtil;

/**
 * Avro序列化    
 * @author jassassin
 * @date 2018年1月18日
 */
public class AvroSerializer implements ISerializer {

	@Override
	public <T> byte[] serialize(T obj) {
		byte[] data = null;
		if (ObjectUtil.isNotNull(obj)){
			ByteArrayOutputStream outStream = null;
			try {
				outStream = new ByteArrayOutputStream();
				DatumWriter<T> datumWriter = new SpecificDatumWriter<T>();
				BinaryEncoder binaryEncoder = EncoderFactory.get().directBinaryEncoder(outStream, null);
				datumWriter.write(obj, binaryEncoder);
				data = outStream.toByteArray();
			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				IOUtil.close(outStream);
			}
		}
		return data;
	}

	@Override
	public <T> T deserialize(byte[] data, Class<T> cls) {
		T obj = null;
		if (ArrayUtil.isNotEmpty(data)){
			ByteArrayInputStream inputStream = null;
			try {
				inputStream = new ByteArrayInputStream(data);
				DatumReader<T> reader = new SpecificDatumReader<T>();
				BinaryDecoder binaryDecoder = DecoderFactory.get().directBinaryDecoder(inputStream, null);
				obj = reader.read(cls.newInstance(), binaryDecoder);
			} catch (Exception e){
				throw new RuntimeException(e);
			} finally {
				IOUtil.close(inputStream);
			}
		}
		return obj;
	}

}
