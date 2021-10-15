package com.ipc.server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
@Data
public class IPCDeviceAddReq {

    @NotEmpty(message = "设备id不允许为空")
    private String deviceId;
    @NotEmpty(message = "设备ip不允许为空")
    private String deviceIp;
    @NotNull(message = "设备端口号不允许为空")
    private Integer devicePort;
    @NotEmpty(message = "设备登录用户名不允许为空")
    private String deviceUsername;
    @NotEmpty(message = "设备登录密码不允许为空")
    private String devicePassword;
    @NotEmpty(message = "设备供应商代码不允许为空")
    private String deviceSupplierCode;

    /** 启用登录校验 */
    private Boolean loginCheckEnabled;

}
