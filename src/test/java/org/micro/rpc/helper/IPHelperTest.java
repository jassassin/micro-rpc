package org.micro.rpc.helper;

import org.junit.Test;

/**
 * IPHelper测试
 * @author jassassin
 * @date 2018年3月6日
 */
public class IPHelperTest {

	@Test
	public void testGetLocalIp(){
		System.out.println(IPHelper.getLocalIp());
	}
	
	@Test
	public void testGetRealIp(){
		System.out.println(IPHelper.getRealIp());
	}
}
