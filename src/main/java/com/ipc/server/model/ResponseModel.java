package com.ipc.server.model;

import lombok.Data;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
@Data
public class ResponseModel<T> {

    private String code;
    private String message;
    private T data;

}
