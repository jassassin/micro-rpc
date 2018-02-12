package org.micro.rpc.zookeeper;

import java.util.List;
import java.util.Map;

import org.micro.rpc.model.ProviderService;

/**
 * 服务提供者注册接口
 * @author jassassin
 * @date 2018年2月6日
 */
public interface IRegisterCenter4Provider {
	
	/**
	 * 注册服务 
	 */
	void registerProvider(List<ProviderService> serviceMetaData);

	/**
	 * 获取服务注册<服务接口,接口方法列表> 
	 */
	Map<String, List<ProviderService>> getProviderServiceMap();
}
