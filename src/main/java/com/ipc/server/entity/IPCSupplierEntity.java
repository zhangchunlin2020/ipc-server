package com.ipc.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
@Data
@TableName("tb_ipc_supplier")
public class IPCSupplierEntity extends BaseEntity {

    /** 供应商代码 */
    private String code;
    /** 供应商名称 */
    private String name;
    /** RTSP协议取流路径 */
    private String rtspPath;

    public IPCSupplierEntity() {
    }

    public IPCSupplierEntity(String code, String name, String rtspPath) {
        this.setCode(code);
        this.setName(name);
        this.setRtspPath(rtspPath);
    }

    public IPCSupplierEntity(Integer id, String name, String rtspPath) {
        this.setId(id);
        this.setName(name);
        this.setRtspPath(rtspPath);
    }

    public IPCSupplierEntity(Integer id, String code, String name, String rtspPath) {
        this.setId(id);
        this.setCode(code);
        this.setName(name);
        this.setRtspPath(rtspPath);
    }

}
