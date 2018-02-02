package org.micro.rpc.serialization.serializer.impl;

import org.junit.Assert;
import org.junit.Test;
import org.micro.rpc.serialization.engine.SerializerEngine;
import org.micro.rpc.serialization.engine.SerializerType;

/**
 * {@link XmlSerializer}
 * @author jassassin
 * @date 2018年1月18日
 */
public class XmlSerializerTest {

	@Test
	public void testSerialize(){
		Number number = new Byte((byte)10);
		byte[] data = SerializerEngine.serialize(number, SerializerType.XML);
		Assert.assertNotNull(data);
		Number num = SerializerEngine.deserialize(data, Number.class, SerializerType.XML);
		Assert.assertEquals(number, num);
	}
}
