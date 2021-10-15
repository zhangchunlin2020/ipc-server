package com.ipc.server.websocket;

import com.ipc.server.constants.IPCConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
@Slf4j
@Component
public class IPCWSInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
            String deviceId = serverHttpRequest.getServletRequest().getParameter(IPCConstants.DEVICE_ID);
            if (StringUtils.isEmpty(deviceId)) {
                log.error("Handshake failed, deviceId parameter is missing!");
                return false;
            }

            attributes.put(IPCConstants.DEVICE_ID, deviceId);
        }

        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        HttpServletRequest request = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest();
        HttpServletResponse response = ((ServletServerHttpResponse) serverHttpResponse).getServletResponse();
        String header = request.getHeader("sec-websocket-protocol");
        if (!StringUtils.isEmpty(header)) {
            response.addHeader("sec-websocket-protocol",header);
        }
        super.afterHandshake(serverHttpRequest,serverHttpResponse,webSocketHandler,e);
    }

}
