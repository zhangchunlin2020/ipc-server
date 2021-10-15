package com.ipc.server.model;

import com.ipc.server.entity.IPCDeviceEntity;
import lombok.Data;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
@Data
public class IPCDevice {

    /** 设备供应商代码 */
    private String supplierCode;

    /** 设备RTSP流访问地址 */
    private String rtspPath;

    /** 设备ID */
    private String id;

    /** 设备ip */
    private String ip;

    /** 设备端口 */
    private Integer port;

    /** 设备登录名 */
    private String username;

    /** 设备密码 */
    private String password;

    public IPCDevice() {
    }

    public IPCDevice(String id) {
        this.id = id;
    }

    public static IPCDevice fromEntity(IPCDeviceEntity entity) {
        IPCDevice ipcDevice = new IPCDevice();
        ipcDevice.setId(entity.getDeviceId());
        ipcDevice.setIp(entity.getDeviceIp());
        ipcDevice.setPort(entity.getDevicePort());
        ipcDevice.setUsername(entity.getDeviceUsername());
        ipcDevice.setPassword(entity.getDevicePassword());
        ipcDevice.setSupplierCode(entity.getSupplier().getCode());
        ipcDevice.setRtspPath(entity.getSupplier().getRtspPath());
        return ipcDevice;
    }

}
