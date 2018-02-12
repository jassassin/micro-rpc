package org.micro.rpc.support;

import java.io.InputStream;
import java.util.Properties;

import org.micro.rpc.serialization.engine.SerializerType;
import org.micro.rpc.util.IOUtil;

/**
 * Property配置文件处理
 * @author jassassin
 * @date 2018年2月5日
 */
public class PropertyConfigHelper {

	// properties配置文件路径
	private static final String PROP_CLASSPATH = "/remote-rpc.properties";
	
	private static final Properties props = new Properties();
	
	// zookeeper服务地址
	private static String zkServers;
	
	// zk sesssion超时时长
	private static int zkSesssionTimeout;
	
	// zk 连接超时
	private static int zkConnectTimeout;
	
	// 序列化方式
	private static SerializerType serializerType;
	
	// 服务端Netty连接数
	private static int channelConnectSize;

	static {
		InputStream stream = null;
		try {
			stream = PropertyConfigHelper.class.getResourceAsStream(PROP_CLASSPATH);
			props.load(stream);
			
			zkServers = props.getProperty("remote.rpc.zookeepr.servers");
			zkSesssionTimeout = Integer.parseInt(props.getProperty("remote.rpc.zookeeper.session.timeout"));
			zkConnectTimeout = Integer.parseInt(props.getProperty("remote.rpc.zookeeper.connect.timeout"));
			String serType = props.getProperty("remote.rpc.serializer.type");
			serializerType = SerializerType.getType(serType);
			if (serializerType == null){
				throw new RuntimeException("failed load serializer type by remote.rpc.serializer.type=" + serializerType);
			}
			channelConnectSize = Integer.parseInt(props.getProperty("remote.rpc.netty.channel.connect.size"));
		} catch (Exception e){
			throw new RuntimeException("failed load remote-rpc.properties!", e);
		} finally {
			IOUtil.close(stream);
		}
	}
	
	public static String getZkServers() {
		return zkServers;
	}

	public static int getZkSesssionTimeout() {
		return zkSesssionTimeout;
	}

	public static int getZkConnectTimeout() {
		return zkConnectTimeout;
	}

	public static SerializerType getSerializerType() {
		return serializerType;
	}

	public static int getChannelConnectSize() {
		return channelConnectSize;
	}
	
}
