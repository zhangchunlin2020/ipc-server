package com.ipc.server.enums;

import lombok.Getter;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
@Getter
public enum IPCDeviceStatusEnum {

    OFFLINE(0, "离线"), ONLINE(1, "在线"), GATHERING(2, "采集中");

    private Integer code;
    private String desc;

    IPCDeviceStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static IPCDeviceStatusEnum valueOf(Integer code) {
        IPCDeviceStatusEnum[] statuses = IPCDeviceStatusEnum.values();
        for (IPCDeviceStatusEnum status : statuses) {
            if (status.equals(code)) {
                return status;
            }
        }

        return null;
    }

}
