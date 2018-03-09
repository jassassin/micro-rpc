package org.micro.rpc.cluster.impl;

import java.util.ArrayList;
import java.util.List;

import org.micro.rpc.cluster.ClusterStrategy;
import org.micro.rpc.model.ProviderService;
import org.micro.rpc.util.RandomUtil;

/**
 * 软负载实现加权随机均衡
 * @author jassassin
 * @date 2018年3月7日
 */
public class WeightRandomClusterStrategyImpl implements ClusterStrategy {

	@Override
	public ProviderService select(List<ProviderService> services) {
		List<ProviderService> serviceList = new ArrayList<ProviderService>();
		for (ProviderService service : services){
			int weight = service.getWeight();
			for (int i = 0; i < weight; i++){
				serviceList.add(service);
			}
		}
		int index = RandomUtil.nextInt(serviceList.size());
		return serviceList.get(index);
	}

}
