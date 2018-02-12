package org.micro.rpc.model;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 服务提供者消息实体
 * @author jassassin
 * @date 2018年2月5日
 */
public class ProviderService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4600363443986440760L;

	private Class<?> serviceItf;
	
	private transient Object serviceObj;
	
	private transient Method serviceMethod;
	
	private String serverIp;
	
	private int serverPort;
	
	private long timeout;
	
	// 该服务提供者权重
	private int weight;
	
	// 服务端线程数
	private int workerThreads;
	
	// 服务提供者唯一标识
	private String appKey;
	
	// 服务分组名称
	private String groupName;

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

	public Method getServiceMethod() {
		return serviceMethod;
	}

	public void setServiceMethod(Method serviceMethod) {
		this.serviceMethod = serviceMethod;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getWorkerThreads() {
		return workerThreads;
	}

	public void setWorkerThreads(int workerThreads) {
		this.workerThreads = workerThreads;
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
	
}
