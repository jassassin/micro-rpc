package org.micro.rpc.cluster.impl;

import java.util.List;

import org.micro.rpc.cluster.ClusterStrategy;
import org.micro.rpc.model.ProviderService;
import org.micro.rpc.util.RandomUtil;

/**
 * 负载均衡随机算法实现
 * @author jassassin
 * @date 2018年3月7日
 */
public class RandomClusterStrategyImpl implements ClusterStrategy {

	@Override
	public ProviderService select(List<ProviderService> services) {
		int size = services.size();
		int index = RandomUtil.nextInt(size);
		return services.get(index);
	}

}
