package org.micro.rpc.spring;

import org.micro.rpc.invoker.InvokerFactoryBean;
import org.micro.rpc.util.StringUtil;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

/**
 * 服务调用者配置解析
 * @author jassassin
 * @date 2018年2月2日
 */
public class InvokerFactoryBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

	@Override
	protected Class<?> getBeanClass(Element element) {
		return InvokerFactoryBean.class;
	}

	@Override
	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		try {
			String targetInterface = element.getAttribute("interface");
			String timeout = element.getAttribute("timeout");
			String clusterStrategy = element.getAttribute("clusterStrategy");
			String remoteAppKey = element.getAttribute("remoteAppKey");
			String groupName = element.getAttribute("groupName");
			
			builder.addPropertyValue("targetInterface", Class.forName(targetInterface));
			builder.addPropertyValue("timeout", Integer.parseInt(timeout));
			builder.addPropertyValue("remoteAppKey", remoteAppKey);
			
			if (StringUtil.isNotEmpty(clusterStrategy)){
				builder.addPropertyValue("clusterStrategy", clusterStrategy);
			}
			
			if (StringUtil.isNotEmpty(groupName)){
				builder.addPropertyValue("groupName", groupName);
			}
		} catch (Exception e){
			throw new RuntimeException(e);
		}
	}
}
