package com.ipc.server.service;

import com.ipc.server.enums.IPCCodeStreamTypeEnum;
import com.ipc.server.ffmpeg.FFmpegCommandBuilder;
import com.ipc.server.ffmpeg.FFmpegGatherer;
import com.ipc.server.ffmpeg.FFmpegProcessManager;
import com.ipc.server.helper.IPCHelper;
import com.ipc.server.model.IPCDevice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
@Component
@Slf4j
public class IPCGatherService {

    @Autowired
    private FFmpegGatherer fFmpegGatherer;
    @Autowired
    private FFmpegProcessManager fFmpegProcessManager;
    @Autowired
    private FFmpegCommandBuilder fFmpegCommandBuilder;
    @Autowired
    private IPCHelper ipcHelper;

    /**
     * 开始采集所有IPC
     *
     * @return
     */
    public boolean startGather(List<IPCDevice> deviceList, int channel, IPCCodeStreamTypeEnum subtype) {
        for (IPCDevice ipcDevice : deviceList) {
            startGather(ipcDevice, channel, subtype);
        }
        return true;
    }

    /**
     * 停止采集所有IPC
     *
     * @return
     */
    public boolean stopGather(List<IPCDevice> deviceList) {
        for (IPCDevice ipcDevice : deviceList) {
            stopGather(ipcDevice);
        }
        return true;
    }

    /**
     * 开始采集指定IPC
     *
     * @param device
     * @param channel
     *          通道号
     * @param streamType
     *          码流类型
     * @return
     */
    public boolean startGather(IPCDevice device, int channel, IPCCodeStreamTypeEnum streamType) {
        if (fFmpegProcessManager.get(device.getId()) != null) {
            log.warn("Gather had been started, deviceId: {}", device.getId());
            return false;
        }
        fFmpegGatherer.execute(device.getId(), fFmpegCommandBuilder.buildFlvCommand(ipcHelper.getRTSPURL(device, channel, streamType), ipcHelper.getPushURL(device)));
        log.info("Gather start successful, deviceId: {}", device.getId());
        return true;
    }

    /**
     * 停止采集指定IPC
     *
     * @param device
     * @return
     */
    public boolean stopGather(IPCDevice device) {
        if (fFmpegProcessManager.get(device.getId()) == null) {
            log.warn("Gather has been stopped, deviceId: {}", device.getId());
            return false;
        }

        fFmpegProcessManager.get(device.getId()).destroyForcibly();
        fFmpegProcessManager.remove(device.getId());
        log.info("Gather stop successful, deviceId: {}", device.getId());
        return true;
    }

}
