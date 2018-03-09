package org.micro.rpc.zookeeper;

import java.util.List;
import java.util.Map;

import org.micro.rpc.model.InvokerService;
import org.micro.rpc.model.ProviderService;

/**
 * 服务端接口
 * @author jassassin
 * @date 2018年3月8日
 */
public interface IRegisterCenter4Invoker {

	/**
	 * 消费端注册 
	 */
	void registerInvoker(InvokerService service);
	
	/**
	 * 缓存服务提供者信息
	 * @param remoteAppKey
	 * @param groupName
	 */
	void loadProviderMap(String remoteAppKey, String groupName);

	/**
	 * 获取服务提供端信息
	 */
	Map<String, List<ProviderService>> getServiceMetadataMap4Invoker();
}
