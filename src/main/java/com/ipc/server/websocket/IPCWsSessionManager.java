package com.ipc.server.websocket;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
@Slf4j
public class IPCWsSessionManager {

    /** sessionId->WebSocketSession */
    private static Map<String, WebSocketSession> SESSION_MAP = new ConcurrentHashMap<>();
    /** sessionId->deviceId */
    private static Map<String, String> SESSION_DEVICE_MAP = new ConcurrentHashMap<>();
    /** deviceId->[sessionIds] */
    private static Map<String, List<WebSocketSession>> DEVICE_SESSIONS_MAP = new ConcurrentHashMap<>();

    public static void add(WebSocketSession session, String deviceId) {
        SESSION_MAP.put(session.getId(), session);
        SESSION_DEVICE_MAP.put(session.getId(), deviceId);
        DEVICE_SESSIONS_MAP.compute(deviceId, (k, v) -> {
           List<WebSocketSession> sessionIds = v;
           if (sessionIds == null)
               sessionIds = new CopyOnWriteArrayList<>();
           sessionIds.add(session);
           return sessionIds;
        });
    }

    public static WebSocketSession remove(String sessionId) {
        String deviceId = SESSION_DEVICE_MAP.get(sessionId);
        if (StringUtils.isNotEmpty(deviceId)) {
            DEVICE_SESSIONS_MAP.compute(deviceId, (k, v)->{
               List<WebSocketSession> vals = v;
               if (vals == null)
                   return null;
               vals.remove(SESSION_MAP.get(sessionId));
               return vals.isEmpty() ? null : vals;
            });
        }

        SESSION_DEVICE_MAP.remove(sessionId);
        return SESSION_MAP.remove(sessionId);
    }

    public static WebSocketSession get(String sessionId) {
        return SESSION_MAP.get(sessionId);
    }

    public static List<WebSocketSession> getSessions(String deviceId) {
        return DEVICE_SESSIONS_MAP.get(deviceId);
    }

}
