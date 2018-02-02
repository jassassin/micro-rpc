package org.micro.rpc.serialization.serializer.impl;

import org.micro.rpc.serialization.serializer.ISerializer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * XML序列化
 * @author jassassin
 * @date 2018年1月18日
 */
public class XmlSerializer implements ISerializer {

	private static final XStream xStream = new XStream(new DomDriver());
	
	@Override
	public <T> byte[] serialize(T obj) {
		return xStream.toXML(obj).getBytes();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(byte[] data, Class<T> cls) {
		String xml = new String(data);
		return (T) xStream.fromXML(xml);
	}

}
