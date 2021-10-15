package com.ipc.server.enums;

import lombok.Getter;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
@Getter
public enum ResponseStatusEnum {

    SUCCESS("0000", "成功"),
    FAIL("9999", "异常"),
    BAD_REQUEST("1111", "无效请求"),
    VALIDATE_FAIL("2222", "业务校验失败");

    private String code;
    private String message;

    ResponseStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
