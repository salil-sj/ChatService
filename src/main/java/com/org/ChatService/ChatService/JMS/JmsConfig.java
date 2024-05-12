package com.org.ChatService.ChatService.JMS;

import ch.qos.logback.classic.pattern.MessageConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

import javax.jms.ConnectionFactory;
import java.util.Collections;

@Configuration
@EnableJms
public class JmsConfig {

    @Value("${spring.activemq.user}")
    private String activeMQBrokerUsername;
    @Value("${spring.activemq.broker-url}")
    private String activeMQBrokerUrl;
    @Value("${spring.activemq.password}")
    private String activeMQBrokerPassword;

    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(activeMQBrokerUsername,
                activeMQBrokerPassword,
                activeMQBrokerUrl);
       // connectionFactory.setTrustedPackages(Collections.singletonList("com.org.ChatService.ChatService"));

        return connectionFactory;
    }
//
//    @Bean
//    public JmsTemplate jmsTemplate()
//    {
//        JmsTemplate jmsTemplate = new JmsTemplate();
//        jmsTemplate.setConnectionFactory(connectionFactory());
//        return jmsTemplate;
//    }

    @Bean
    public JmsListenerContainerFactory<?> connectionFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrency("5-10");
        return factory;
    }







}
