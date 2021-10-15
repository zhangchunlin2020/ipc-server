package com.ipc.server.enums;

import lombok.Getter;

/**
 * @Author 胡学汪
 * @Description 码流类型
 * @Date 创建于 2021/9/11 13:57
 */
@Getter
public enum IPCCodeStreamTypeEnum {

    PRIMARY(0, "主码流"), AUXILIARY(1, "辅码流");

    private int code;
    private String desc;

    IPCCodeStreamTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static IPCCodeStreamTypeEnum valueOf(Integer code) {
        IPCCodeStreamTypeEnum[] values = IPCCodeStreamTypeEnum.values();
        for (IPCCodeStreamTypeEnum value : values) {
            if (value.getCode() == code) {
                return value;
            }
        }

        return null;
    }

}
