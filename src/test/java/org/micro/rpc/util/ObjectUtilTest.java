package org.micro.rpc.util;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link ObjectUtil}
 * @author jassassin
 * @date 2018年1月18日
 */
public class ObjectUtilTest {

	@Test
	public void testIsSubCls(){
		SeriCls seriObj = new SeriCls();
		SubCls subObj = new SubCls();
		Assert.assertTrue(ObjectUtil.isSubCls(Serializable.class, seriObj));
		Assert.assertTrue(ObjectUtil.isSubCls(Thread.class, subObj));
	}
}

/**
 * 可序列化类
 * @author jassassin
 * @date 2018年1月18日
 */
class SeriCls implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}

/**
 * 子类
 * @author jassassin
 * @date 2018年1月18日
 */
class SubCls extends Thread{
	
}
