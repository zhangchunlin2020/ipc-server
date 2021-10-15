package com.ipc.server.ffmpeg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author 胡学汪
 * @Description FFmpeg进程管理器
 * @Date 创建于 2021/9/11 13:57
 */
@Slf4j
@Component
public class FFmpegProcessManager {

    private Map<String, Process> processMap = new ConcurrentHashMap<>();

    public void put(String deviceId, Process process) {
        processMap.put(deviceId, process);
    }

    public Process get(String deviceId) {
        return processMap.get(deviceId);
    }

    public Process remove(String deviceId) {
        return processMap.remove(deviceId);
    }

    @PreDestroy
    public void close() {
        log.info("Clear data and shutdown FFmpeg process...");
        Set<Map.Entry<String, Process>> entrySet = processMap.entrySet();
        if (!CollectionUtils.isEmpty(entrySet)) {
            for (Map.Entry<String, Process> entry : entrySet) {
                Process process = entry.getValue();
                if (process != null && process.isAlive()) {
                    process.destroyForcibly();
                }
            }
        }
    }

}
