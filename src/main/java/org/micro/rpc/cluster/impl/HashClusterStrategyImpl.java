package org.micro.rpc.cluster.impl;

import java.util.List;

import org.micro.rpc.cluster.ClusterStrategy;
import org.micro.rpc.helper.IPHelper;
import org.micro.rpc.model.ProviderService;

/**
 * 软负载均衡哈希算法
 * @author jassassin
 * @date 2018年3月6日
 */
public class HashClusterStrategyImpl implements ClusterStrategy {

	@Override
	public ProviderService select(List<ProviderService> services) {
		// 获取调用方ip地址
		String localIp = IPHelper.getLocalIp();
		// 计算调用方ip地址哈希值
		int hashcode = localIp.hashCode();
		// 计算服务列表大小
		int size = services.size();
		return services.get(hashcode % size);
	}

}
