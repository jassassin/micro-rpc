package org.micro.rpc.zookeeper;

import java.util.List;
import java.util.Map;

import org.micro.rpc.model.InvokerService;
import org.micro.rpc.model.ProviderService;

/**
 * 服务治理接口
 * @author jassassin
 * @date 2018年3月8日
 */
public interface IRegisterCenter4Governance {
	
	/**
	 * 获取	服务提供者列表=>服务消费端列表 
	 */
	Map<List<ProviderService>, List<InvokerService>> queryProvidersAndInvokers(String serviceName, String appKey);
}
