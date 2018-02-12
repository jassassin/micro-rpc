package org.micro.rpc.model;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 服务调用者消息实体
 * @author jassassin
 * @date 2018年2月5日
 */
public class InvokerService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4559500744367270128L;

	private Class<?> serviceItf;
	
	private Object serviceObj;
	
	private Method serviceMethod;
	
	private String invokerIp;
	
	private int invokerPort;
	
	private long timeout;
	
	private String remoteAppKey;
	
	private String groupName = "default";

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

	public String getInvokerIp() {
		return invokerIp;
	}

	public void setInvokerIp(String invokerIp) {
		this.invokerIp = invokerIp;
	}

	public int getInvokerPort() {
		return invokerPort;
	}

	public void setInvokerPort(int invokerPort) {
		this.invokerPort = invokerPort;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public String getRemoteAppKey() {
		return remoteAppKey;
	}

	public void setRemoteAppKey(String remoteAppKey) {
		this.remoteAppKey = remoteAppKey;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
}
