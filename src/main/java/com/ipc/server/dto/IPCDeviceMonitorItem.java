package com.ipc.server.dto;

import com.ipc.server.enums.IPCDeviceStatusEnum;
import lombok.Data;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/22 16:56
 */
@Data
public class IPCDeviceMonitorItem {

    /** 设备ID */
    private String deviceId;
    /** 设备IP */
    private String deviceIp;
    /** 拉流地址 */
    private String url;
    /** 状态 */
    private IPCDeviceStatusEnum status;

}
