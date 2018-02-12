package org.micro.rpc.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Invoker名称空间handler
 * @author jassassin
 * @date 2018年2月5日
 */
public class InvokerServiceNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("reference", new InvokerFactoryBeanDefinitionParser());
	}

}
