package org.micro.rpc.invoker;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 服务调用者Bean
 * @author jassassin
 * @date 2018年2月2日
 */
@SuppressWarnings("rawtypes")
public class InvokerFactoryBean implements FactoryBean, InitializingBean {

	// 默认分组
	private static final String DEFAULT_GROUP_NAME = "default";
	
	// 服务目标接口
	private Class<?> targetInterface;
	
	// 服务接口对象
	private Object serviceObj;
	
	// 调用超时
	private int timeout;
	
	// 服务提供唯一标识
	private String remoteAppKey;
	
	// 负载均衡策略
	private String clusterStrategy;
	
	// 服务分组名称
	private String groupName = DEFAULT_GROUP_NAME;
	
	@Override
	public Object getObject() throws Exception {
		return serviceObj;
	}
	
	@Override
	public Class<?> getObjectType() {
		return targetInterface;
	}
	
	@Override
	public boolean isSingleton() {
		return true;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {

	}

	public Class<?> getTargetInterface() {
		return targetInterface;
	}

	public void setTargetInterface(Class<?> targetInterface) {
		this.targetInterface = targetInterface;
	}

	public Object getServiceObj() {
		return serviceObj;
	}

	public void setServiceObj(Object serviceObj) {
		this.serviceObj = serviceObj;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getClusterStrategy() {
		return clusterStrategy;
	}

	public void setClusterStrategy(String clusterStrategy) {
		this.clusterStrategy = clusterStrategy;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getRemoteAppKey() {
		return remoteAppKey;
	}

	public void setRemoteAppKey(String remoteAppKey) {
		this.remoteAppKey = remoteAppKey;
	}
}
