package com.nest_lot.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
@EnableJms
public class ActiveMqConfig {

	@Bean(name = "firstConnectionFactory")
	public ActiveMQConnectionFactory getFirstConnectionFactory(@Value("${spring.activemq.url}") String brokerUrl,
			@Value("${spring.activemq.user}") String userName, @Value("${spring.activemq.password}") String password) {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(brokerUrl);
		connectionFactory.setUserName(userName);
		connectionFactory.setPassword(password);
		return connectionFactory;
	}

	@Bean(name = "firstJmsTemplate")
	public JmsTemplate getFirstJmsTemplate(@Qualifier("firstConnectionFactory") ConnectionFactory connectionFactory) {
		JmsTemplate template = new JmsTemplate(connectionFactory);
		template.setDeliveryPersistent(true);
		template.setSessionTransacted(true);
		template.setPubSubDomain(true);
		return template;
	}

	@Bean(name = "JmsListenerContainerFactory")
	public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ActiveMQConnectionFactory connectionFactory) {
		DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
		// 设置为发布订阅方式, 默认情况下使用的生产消费者方式
		bean.setPubSubDomain(false);
		bean.setConnectionFactory(connectionFactory);
		return bean;
	}

}
