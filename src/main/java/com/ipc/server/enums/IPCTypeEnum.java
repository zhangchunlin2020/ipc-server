package com.ipc.server.enums;

import lombok.Getter;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
@Getter
public enum IPCTypeEnum {

    DH("大华");

    private String name;

    IPCTypeEnum(String name) {
        this.name = name;
    }

}
