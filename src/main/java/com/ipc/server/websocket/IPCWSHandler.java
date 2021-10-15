package com.ipc.server.websocket;

import com.ipc.server.constants.IPCConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.util.Iterator;
import java.util.List;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
@Slf4j
@Component
public class IPCWSHandler extends BinaryWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("[CONNECTION_ESTABLISHED: sessionId {}]", session.getId());
        String deviceId = String.valueOf(session.getAttributes().get(IPCConstants.DEVICE_ID));
        IPCWsSessionManager.add(session, deviceId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("[CONNECTION_CLOSED: sessionId {}, status {}]", session.getId(), status);
        IPCWsSessionManager.remove(session.getId());
    }

    public void sendData(byte[] data, String deviceId) {
        BinaryMessage binaryMessage = new BinaryMessage(data);
        List<WebSocketSession> sessions = IPCWsSessionManager.getSessions(deviceId);
        if (!CollectionUtils.isEmpty(sessions)) {
            Iterator<WebSocketSession> it = sessions.iterator();
            while (it.hasNext()) {
                WebSocketSession session = it.next();
                try {
                    if (session != null && session.isOpen()) {
                        session.sendMessage(binaryMessage);
                    } else {
                        it.remove();
                    }
                } catch (Exception e) {
                    log.error("send data error, sessionId: {}, deviceId: {}, ex: {}", session.getId(), deviceId, e);
                }
            }
        }
    }

}
