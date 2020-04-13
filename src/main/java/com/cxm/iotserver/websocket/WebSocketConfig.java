package com.cxm.iotserver.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * webSocket配置类，绑定前端连接端点url及其他信息
 *
 * create by
 * 三和智控: cxm on 2020/4/2
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    /**
     * 注册webSocket端点
     */
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 添加一个/WebSocketServer端点，客户端就可以通过这个端点来进行连接；withSockJS作用是添加SockJS支持
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
    }


}
