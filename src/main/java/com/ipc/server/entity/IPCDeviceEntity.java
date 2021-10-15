package com.ipc.server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
@Data
@TableName("tb_ipc_device")
public class IPCDeviceEntity extends BaseEntity {

    /** 设备id */
    private String deviceId;
    /** 设备ip */
    private String deviceIp;
    /** 设备端口 */
    private Integer devicePort;
    /** 设备登录名 */
    private String deviceUsername;
    /** 设备登录密码 */
    private String devicePassword;
    /** 设备供应商代码 */
    private String deviceSupplierCode;

    /** 设备供应商 */
    @TableField(exist = false)
    private IPCSupplierEntity supplier;

}
