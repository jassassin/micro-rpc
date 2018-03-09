package org.micro.rpc.cluster.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.micro.rpc.cluster.ClusterStrategy;
import org.micro.rpc.model.ProviderService;

/**
 * 软负载实现加权轮询均衡
 * @author jassassin
 * @date 2018年3月7日
 */
public class WeightPollingClusterStrategyImpl implements ClusterStrategy {

	private int index = 0;
	private Lock lock = new ReentrantLock();
	
	@Override
	public ProviderService select(List<ProviderService> services) {
		ProviderService value = null;
		try {
			lock.tryLock(10, TimeUnit.MILLISECONDS);
			List<ProviderService> weightServices = new ArrayList<ProviderService>();
			for (ProviderService service : services){
				int weight = service.getWeight();
				for (int i = 0; i < weight; i++){
					weightServices.add(service);
				}
			}
			if (index >= weightServices.size()){
				index = 0;
			}
			
			value = services.get(index);
			index++;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return value == null ? services.get(0) : value;
	}

}
