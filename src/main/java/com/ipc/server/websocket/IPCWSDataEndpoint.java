package com.ipc.server.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
@Slf4j
@RestController
@RequestMapping("/data")
public class IPCWSDataEndpoint {

    @Autowired
    private IPCWSHandler ipcwsHandler;

    @RequestMapping("/receive/{deviceId}")
    public String receive(HttpServletRequest request, @PathVariable String deviceId) {
        log.debug("method: {}, deviceId: {}", request.getMethod(), deviceId);
        try {
            ServletInputStream inputStream = request.getInputStream();
            int len = -1;
            while ((len =inputStream.available()) !=-1) {
                byte[] data = new byte[len];
                inputStream.read(data);
                ipcwsHandler.sendData(data, deviceId);
            }
        } catch (Exception ex) {
            log.error("receive error, deviceId: {}, ex: {}", deviceId, ex);
        }

        log.debug("receive over, deviceId: {}", deviceId);
        return "OVER";
    }

}
