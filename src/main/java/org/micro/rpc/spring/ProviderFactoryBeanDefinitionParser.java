package org.micro.rpc.spring;

import org.micro.rpc.provider.ProviderFactoryBean;
import org.micro.rpc.util.StringUtil;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

/**
 * 服务提供者配置解析
 * @author jassassin
 * @date 2018年2月2日
 */
public class ProviderFactoryBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

	@Override
	protected Class<?> getBeanClass(Element element) {
		return ProviderFactoryBean.class;
	}
	
	@Override
	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		try {
			String serviceItf = element.getAttribute("interface");
			String timeout = element.getAttribute("timeout");
			String serverPort = element.getAttribute("serverPort");
			String ref = element.getAttribute("ref");
			String appKey = element.getAttribute("appKey");
			// 可选属性
			String groupName = element.getAttribute("groupName");
			String weight = element.getAttribute("weight");
			String workerThreads = element.getAttribute("workerThreads");
			
			builder.addPropertyValue("serviceItf", Class.forName(serviceItf));
			builder.addPropertyValue("timeout", Integer.parseInt(timeout));
			builder.addPropertyValue("serverPort", Integer.parseInt(serverPort));
			builder.addPropertyReference("serviceObj", ref);
			builder.addPropertyValue("appKey", appKey);
			
			if (StringUtil.isNotEmpty(groupName)){
				builder.addPropertyValue("groupName", groupName);
			}
			
			if (StringUtil.isNotEmpty(weight)){
				builder.addPropertyValue("weight", Integer.parseInt(weight));
			}
			
			if (StringUtil.isNotEmpty(workerThreads)){
				builder.addPropertyValue("workerThreads", Integer.parseInt(workerThreads));
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
