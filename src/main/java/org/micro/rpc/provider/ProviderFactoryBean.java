package org.micro.rpc.provider;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 服务提供者发布Bean
 * @author jassassin
 * @date 2018年2月2日
 */
@SuppressWarnings("rawtypes")
public class ProviderFactoryBean implements FactoryBean, InitializingBean {
	
	// 默认权重
	private static final int DEFAULT_WEIGHT = 1;
	
	// 默认线程数
	private static final int DEFAULT_THREAD_WORKERS = 10;
	
	// 默认分组
	private static final String DEFAULT_GROUP_NAME = "default";
	
	// 服务提供接口
	private Class<?> serviceItf;
	
	// 服务实现者
	private Object serviceObj;
	
	// 服务实现者代理
	private Object proxyServiceObj;
	
	// 服务端口
	private int serverPort;
	
	// 服务调用超时时间
	private int timeout;
	
	// 服务唯一标识
	private String appKey;
	
	// 服务分组
	private String groupName = DEFAULT_GROUP_NAME;
	
	// 服务权重
	private int weight = DEFAULT_WEIGHT;
	
	// 服务线程数
	private int threadWorkers = DEFAULT_THREAD_WORKERS;
	
	@Override
	public Object getObject() throws Exception {
		return proxyServiceObj;
	}

	@Override
	public Class<?> getObjectType() {
		return serviceItf;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {

	}

	public Class<?> getServiceItf() {
		return serviceItf;
	}

	public void setServiceItf(Class<?> serviceItf) {
		this.serviceItf = serviceItf;
	}

	public Object getServiceObj() {
		return serviceObj;
	}

	public void setServiceObj(Object serviceObj) {
		this.serviceObj = serviceObj;
	}

	public Object getProxyServiceObj() {
		return proxyServiceObj;
	}

	public void setProxyServiceObj(Object proxyServiceObj) {
		this.proxyServiceObj = proxyServiceObj;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getThreadWorkers() {
		return threadWorkers;
	}

	public void setThreadWorkers(int threadWorkers) {
		this.threadWorkers = threadWorkers;
	}
	
}
