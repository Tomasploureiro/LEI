package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import search.GatewayInterface;
import search.URLQueueInterface;

@Configuration
public class RmiConfig {

    private static final int GATEWAY_PORT = 1099;
    private static final String HOST = "172.20.10.3";

    @Bean
    public RmiProxyFactoryBean gatewayService() {
        RmiProxyFactoryBean factoryBean = new RmiProxyFactoryBean();
        factoryBean.setServiceUrl("rmi://" + HOST + ":" + GATEWAY_PORT + "/Gateway");
        factoryBean.setServiceInterface(GatewayInterface.class);
        return factoryBean;
    }

    @Bean
    public RmiProxyFactoryBean urlQueueService() {
        RmiProxyFactoryBean factoryBean = new RmiProxyFactoryBean();
        factoryBean.setServiceUrl("rmi://" + HOST + ":" + GATEWAY_PORT + "/URLQueue");
        factoryBean.setServiceInterface(URLQueueInterface.class);
        return factoryBean;
    }
}