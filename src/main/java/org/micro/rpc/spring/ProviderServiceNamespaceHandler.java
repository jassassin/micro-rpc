package org.micro.rpc.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Provider名称空间handler
 * @author jassassin
 * @date 2018年2月5日
 */
public class ProviderServiceNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("service", new ProviderFactoryBeanDefinitionParser());
	}

}
