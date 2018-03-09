package org.micro.rpc.cluster;

import java.util.List;

import org.micro.rpc.model.ProviderService;

/**
 * 负载均衡
 * @author jassassin
 * @date 2018年3月6日
 */
public interface ClusterStrategy {

	/**
	 * 选择一个服务节点
	 * @param services
	 * @return
	 */
	ProviderService select(List<ProviderService> services);
}
