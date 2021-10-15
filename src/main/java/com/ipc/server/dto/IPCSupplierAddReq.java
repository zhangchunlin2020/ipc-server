package com.ipc.server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
@Data
public class IPCSupplierAddReq {

    @NotEmpty(message = "供应商代码不允许为空")
    private String code;
    @NotEmpty(message = "供应商名称不允许为空")
    private String name;
    @NotEmpty(message = "RTSP协议取流路径不允许为空")
    private String rtspPath;

}
