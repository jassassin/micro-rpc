package org.micro.rpc.helper;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

import org.micro.rpc.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IP工具类
 * @author jassassin
 * @date 2018年3月6日
 */
public final class IPHelper {

	private static final Logger logger = LoggerFactory.getLogger(IPHelperTest.class);
	
	private static String ip = "";
	
	static {
		String localIp = null;
		try {
			// 获取本机所有网络接口
			Enumeration<NetworkInterface> netInters = NetworkInterface.getNetworkInterfaces();
			while(netInters.hasMoreElements()){
				NetworkInterface netInter = netInters.nextElement();
				// 获取绑定到该网卡的所有IP地址
				List<InterfaceAddress> inetAddressList = netInter.getInterfaceAddresses();
				for (InterfaceAddress add : inetAddressList){
					InetAddress ip = add.getAddress();
					// 过滤IPV4地址
					if (ip != null && ip instanceof Inet4Address){
						if (!"127.0.0.1".equals(ip.getHostAddress())){
							localIp = ip.getHostAddress();
							break;
						}
					}
				}
			}
		} catch (SocketException e) {
			logger.error("获取本机IP失败:", e);
			throw new RuntimeException(e);
		}
		ip = localIp;
	}
	
	public static String getLocalIp(){
		return ip;
	}
	
	/**
	 * 获取外网IP
	 */
	public static String getRealIp(){
		String localIp = "";
		String realIp = "";
		try {
			Enumeration<NetworkInterface> allNetworks = NetworkInterface.getNetworkInterfaces();
			boolean find = false;
			InetAddress inetAddress = null;
			while(allNetworks.hasMoreElements() && !find){
				NetworkInterface networkInterface = allNetworks.nextElement();
				Enumeration<InetAddress> inetEnum = networkInterface.getInetAddresses();
				while (inetEnum.hasMoreElements()){
					inetAddress = inetEnum.nextElement();
					if (!inetAddress.isSiteLocalAddress() && !inetAddress.isLoopbackAddress() 
														  && !inetAddress.getHostAddress().contains(":")){
						realIp = inetAddress.getHostAddress();
						find = true;
						break;
					} else if (inetAddress.isSiteLocalAddress() && !inetAddress.isLoopbackAddress() 
																&& !inetAddress.getHostAddress().contains(":")){
						localIp = inetAddress.getHostAddress();
					}
				}
			}
			
			if (StringUtil.isNotEmpty(realIp)){
				return realIp;
			}
		} catch (SocketException e) {
			logger.error("获取本机外网IP失败:", e);
			throw new RuntimeException(e);
		}
		return localIp;
	}
}
