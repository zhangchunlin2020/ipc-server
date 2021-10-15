package com.ipc.server.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
@Configuration
@EnableWebSocket
public class IPCWSConfiguration implements WebSocketConfigurer {

    @Autowired
    private IPCWSInterceptor ipcwsInterceptor;

    @Autowired
    private IPCWSHandler ipcwsHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(ipcwsHandler, "/ipcdata").addInterceptors(ipcwsInterceptor).setAllowedOrigins("*");
    }

}
