package org.micro.rpc.serialization.serializer.impl;

import org.junit.Assert;
import org.junit.Test;
import org.micro.rpc.model.User;
import org.micro.rpc.serialization.engine.SerializerEngine;
import org.micro.rpc.serialization.engine.SerializerType;

/**
 * 测试{@link KryoSerializer}
 * @author jassassin
 * @date 2018年2月2日
 */
public class KryoSerializerTest {

	@Test
	public void testSerialize(){
		User user = new User();
		user.setName("jack");
		user.setEmail("jack@qq.com");
		user.setAge(100);
		
		byte[] data = SerializerEngine.serialize(user, SerializerType.KRYO);
		Assert.assertNotNull(data);
		User duser = SerializerEngine.deserialize(data, User.class, SerializerType.KRYO);
		Assert.assertEquals(user, duser);
		
		System.out.println(duser);
	}
}
