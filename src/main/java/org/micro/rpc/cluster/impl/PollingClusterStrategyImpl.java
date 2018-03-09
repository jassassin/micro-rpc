package org.micro.rpc.cluster.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.micro.rpc.cluster.ClusterStrategy;
import org.micro.rpc.model.ProviderService;

/**
 * 负载均衡轮询实现
 * @author jassassin
 * @date 2018年3月7日
 */
public class PollingClusterStrategyImpl implements ClusterStrategy {

	private int count = 0;
	private Lock lock = new ReentrantLock();
	
	@Override
	public ProviderService select(List<ProviderService> services) {
		ProviderService service = null;
		try {
			lock.tryLock(1000, TimeUnit.MILLISECONDS);
			if (count >= services.size()){
				count = 0;
			}
			service = services.get(count);
			count++;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		if (service == null){
			service = services.get(0);
		}
		return service;
	}

}
