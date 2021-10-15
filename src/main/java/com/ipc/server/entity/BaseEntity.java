package com.ipc.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ipc.server.constants.CommonConstants;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
@Data
public abstract class BaseEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted = CommonConstants.DELETED_NO;

}
