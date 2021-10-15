package com.ipc.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
@Data
@TableName("tb_ipc_log")
public class IPCLogEntity extends BaseEntity {

    /** 设备ID */
    private String deviceId;
    /** 事件类型 */
    private String eventType;
    /** 事件状态 */
    private Integer eventStatus;
    /** 事件备注 */
    private String eventRemark;

}
