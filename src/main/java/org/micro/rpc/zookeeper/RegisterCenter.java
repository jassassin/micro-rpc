package org.micro.rpc.zookeeper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.micro.rpc.helper.IPHelper;
import org.micro.rpc.helper.PropertyConfigHelper;
import org.micro.rpc.model.InvokerService;
import org.micro.rpc.model.ProviderService;
import org.micro.rpc.util.ClassUtil;
import org.micro.rpc.util.CollectionUtil;
import org.micro.rpc.util.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * zookeeper注册中心实现
 * @author jassassin
 * @date 2018年3月8日
 */
public class RegisterCenter implements IRegisterCenter4Governance, IRegisterCenter4Invoker, IRegisterCenter4Provider {

	private Logger logger = LoggerFactory.getLogger(RegisterCenter.class);
	
	private static RegisterCenter registerCenter = new RegisterCenter();
	
	// 服务提供者列表, 服务提供者接口 => 服务提供者方法列表
	private static final Map<String, List<ProviderService>> providerServiceMap = MapUtil.newConcurrentHashMap();
	
	// 服务端元信息(第一次由ZK获取,后续由ZK监听主动刷新)
	private static final Map<String, List<ProviderService>> serviceMetadataMap4Invoker = MapUtil.newConcurrentHashMap();
	
	// ZK相关配置信息
	private static String ZK_SERVERS = PropertyConfigHelper.getZkServers();
	private static int ZK_SESSION_TIMEOUT = PropertyConfigHelper.getZkSesssionTimeout();
	private static int ZK_CONNECT_TIMEOUT = PropertyConfigHelper.getZkConnectTimeout();
	
	// ZK元数据存储目录信息
	private static final String ROOT_PATH = "/config_register";
	private static final String PROVIDER_TYPE = "provider";
	private static final String INVOKER_TYPE = "invoker";
	
	// ZK客户端
	private static volatile ZkClient zkClient = null;
	
	// 单例: 构造器私有
	private RegisterCenter(){}
	
	/**
	 * 实例获取接口 
	 */
	public static RegisterCenter instance(){
		return registerCenter;
	}
	
	@Override
	public void registerProvider(List<ProviderService> serviceMetaData) {
		if (CollectionUtil.isNotEmpty(serviceMetaData)){
			synchronized (RegisterCenter.class) {
				// 缓存服务提供者信息 服务名称 => 服务提供方列表
				for (ProviderService service : serviceMetaData){
					String serviceName = service.getServiceItf().getName();
					List<ProviderService> serviceList = providerServiceMap.get(serviceName);
					if (serviceList == null){
						serviceList = new ArrayList<ProviderService>();
						providerServiceMap.put(serviceName, serviceList);
					}
					serviceList.add(service);
				}
				
				// 初始化ZkClient
				if (zkClient == null){
					zkClient = new ZkClient(ZK_SERVERS, ZK_SESSION_TIMEOUT, ZK_CONNECT_TIMEOUT, new SerializableSerializer());
				}
				
				// 创建 ROOT_PATH/当前应用命名空间
				String appKey = serviceMetaData.get(0).getAppKey();
				String zkPath = ROOT_PATH + "/" + appKey;
				if (!zkClient.exists(zkPath)){
					zkClient.createPersistent(zkPath);
					logger.info("create provider path:{}", zkPath);
				}
				
				//  /config_register/app_key/groupName/serviceNode/provider/localIp|serverPort|weight|workerThreads|groupName
				for (Map.Entry<String, List<ProviderService>> entry : providerServiceMap.entrySet()){
					ProviderService service = entry.getValue().get(0);
					String serviceNode = entry.getKey();
					String groupName = service.getGroupName();
					String servicePath = zkPath + "/" + groupName + "/" + serviceNode + "/" + PROVIDER_TYPE;
					if (!zkClient.exists(servicePath)){
						zkClient.createPersistent(servicePath);
						logger.info("create servicePath:{}", servicePath);
					}
					
					// 处理'|' 分隔部分
					String localIp = IPHelper.getLocalIp();
					int serverPort = service.getServerPort();
					int weight = service.getWeight();
					int workerThreads = service.getWorkerThreads();
					String currentServiceNode = servicePath + "/" + localIp + "|" + serverPort + "|" + weight + "|" + workerThreads + "|" + groupName;
					if (!zkClient.exists(currentServiceNode)){
						zkClient.createEphemeral(currentServiceNode);
						logger.info("create currentServiceNode:{}", currentServiceNode);
					}
					
					// 监听节点变化,及时刷新本地缓存
					zkClient.subscribeChildChanges(servicePath, new IZkChildListener() {
						
						@Override
						public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
							logger.debug("service path: {}, child changes.", parentPath);
							if (currentChilds == null){
								currentChilds = new ArrayList<String>(); 
							}
							
							// 获取存活的service节点ip
							List<String> ipNodes = new ArrayList<String>();
							for (String serviceNode : currentChilds){
								ipNodes.add(serviceNode.split("|")[0]);
							}
							
							// 刷新本地缓存
							refreshActivityService(ipNodes);
						}
					});
				}
				
			}
		}
	}

	@Override
	public Map<String, List<ProviderService>> getProviderServiceMap() {
		return providerServiceMap;
	}

	@Override
	public void registerInvoker(InvokerService invoker) {
		if (invoker != null){
			// /config_register/remoteAppKey/groupName/serviceNode/consumer/localIp
			synchronized (RegisterCenter.class) {
				String remoteAppKey = invoker.getRemoteAppKey();
				String groupName = invoker.getGroupName();
				String serviceNode = invoker.getServiceItf().getName();
				String invokerPath = ROOT_PATH + "/" + remoteAppKey + "/" + groupName + "/" + serviceNode + "/" + INVOKER_TYPE;
				
				if (zkClient == null){
					zkClient = new ZkClient(ZK_SERVERS, ZK_SESSION_TIMEOUT, ZK_CONNECT_TIMEOUT, new SerializableSerializer());
				}
				
				if (!zkClient.exists(invokerPath)){
					zkClient.createPersistent(invokerPath);
				}
				
				String localIp = IPHelper.getLocalIp();
				String currentInvokerNode = invokerPath + "/" + localIp;
				if (!zkClient.exists(currentInvokerNode)){
					zkClient.createEphemeral(currentInvokerNode);
				}
			}
		}
	}

	@Override
	public void loadProviderMap(String remoteAppKey, String groupName) {
		if (MapUtil.isEmpty(serviceMetadataMap4Invoker)){
			serviceMetadataMap4Invoker.putAll(fetchOrUpdateServiceMetaData(remoteAppKey, groupName));
		}
	}

	@Override
	public Map<String, List<ProviderService>> getServiceMetadataMap4Invoker() {
		return null;
	}

	@Override
	public Map<List<ProviderService>, List<InvokerService>> queryProvidersAndInvokers(String serviceName, String appKey) {
		return null;
	}
	
	private void refreshActivityService(List<String> ipNodes) {
		if (ipNodes == null){
			ipNodes = new ArrayList<String>();
		}
		Map<String, List<ProviderService>> currentProviderMap = new HashMap<String, List<ProviderService>>();
		for (Map.Entry<String, List<ProviderService>> entry : providerServiceMap.entrySet()){
			String serviceNode = entry.getKey();
			List<ProviderService> serviceList = entry.getValue();
			
			List<ProviderService> currentServiceList = currentProviderMap.get(serviceNode);
			if (currentServiceList == null){
				currentServiceList = new ArrayList<ProviderService>();
				currentProviderMap.put(serviceNode, currentServiceList);
			}
			
			for (ProviderService service : serviceList){
				if (ipNodes.contains(service.getServerIp())){
					currentServiceList.add(service);
				}
			}
		}
		providerServiceMap.clear();
		providerServiceMap.putAll(currentProviderMap);
	}
	
	private Map<String, List<ProviderService>> fetchOrUpdateServiceMetaData(String remoteAppKey, String groupName) {
		Map<String, List<ProviderService>> providerServiceMap = MapUtil.newHashMap();
		synchronized(RegisterCenter.class){
			if (zkClient == null){
				zkClient = new ZkClient(ZK_SERVERS, ZK_SESSION_TIMEOUT, ZK_CONNECT_TIMEOUT, new SerializableSerializer());
			}
		}
		
		// /config_register/app_key/groupName/serviceNode/provider/localIp|serverPort|weight|workerThreads|groupName
		String providerPath = ROOT_PATH + "/" + remoteAppKey + "/" + groupName;
		List<String> providerServices = zkClient.getChildren(providerPath);
		
		for(String providerService : providerServices){
			String servicePath = providerPath + "/" + providerService + "/" + PROVIDER_TYPE;
			List<String> providerNodes = zkClient.getChildren(servicePath);
			for (String providerNode : providerNodes){
				String[] arr = providerNode.split("|");
				String serverIp = arr[0];
				int serverPort = Integer.parseInt(arr[1]);
				int weight = Integer.parseInt(arr[2]);
				int workerThreads = Integer.parseInt(arr[3]);
				String group = arr[4];
				
				List<ProviderService> serviceList = providerServiceMap.get(providerService);
				if (serviceList == null){
					serviceList = new ArrayList<ProviderService>();
					providerServiceMap.put(providerService, serviceList);
				}
				
				ProviderService service = new ProviderService();
				service.setGroupName(group);
				service.setServerIp(serverIp);
				service.setServerPort(serverPort);
				service.setServiceItf(ClassUtil.loadClass(providerService));
				service.setWeight(weight);
				service.setWorkerThreads(workerThreads);
				
				serviceList.add(service);
			}
			
			zkClient.subscribeChildChanges(servicePath, new IZkChildListener() {
				
				@Override
				public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
					if (currentChilds == null){
						currentChilds = new ArrayList<String>();
					}
					
					List<String> serviceIpList = new ArrayList<String>();
					for (String servicePath : currentChilds){
						serviceIpList.add(servicePath.split("|")[0]);
					}
					
					refreshServiceMetaData(serviceIpList);
				}
			});
		}
		return providerServiceMap;
	}

	private void refreshServiceMetaData(List<String> serviceIpList) {
		if (serviceIpList == null){
			serviceIpList = new ArrayList<String>();
		}
		
		Map<String, List<ProviderService>> currentServiceMetaDataMap = MapUtil.newHashMap();
		for (Map.Entry<String, List<ProviderService>> entry : serviceMetadataMap4Invoker.entrySet()){
			String providerName = entry.getKey();
			List<ProviderService> serviceList = currentServiceMetaDataMap.get(providerName);
			if (serviceList != null){
				serviceList = new ArrayList<ProviderService>();
				currentServiceMetaDataMap.put(providerName, serviceList);
			}
			for (ProviderService service : entry.getValue()){
				if (serviceIpList.contains(service.getServerIp())){
					serviceList.add(service);
				}
			}
		}
		serviceMetadataMap4Invoker.clear();
		serviceMetadataMap4Invoker.putAll(currentServiceMetaDataMap);
	}
}
