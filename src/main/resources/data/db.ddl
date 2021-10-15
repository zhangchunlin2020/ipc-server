CREATE TABLE `tb_ipc_device` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `device_id` VARCHAR(50) NOT NULL COMMENT '设备id',
    `device_ip` VARCHAR(50) NOT NULL COMMENT '设备ip',
    `device_port` INT(11) NOT NULL COMMENT '设备端口',
    `device_username` VARCHAR(50) NOT NULL COMMENT '设备登录名',
    `device_password` VARCHAR(50) NOT NULL COMMENT '设备登录密码',
    `device_supplier_code` VARCHAR(20) NOT NULL COMMENT '设备供应商代码',
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `deleted` TINYINT(1) NOT NULL COMMENT '0:未删除, 1:删除',
     PRIMARY KEY (id)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8 COMMENT 'IPC设备信息表';

CREATE TABLE `tb_ipc_supplier` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `code` VARCHAR(20) NOT NULL COMMENT '供应商代码',
    `name` VARCHAR(20) NOT NULL COMMENT '供应商名称',
    `rtsp_path` VARCHAR(20) NOT NULL COMMENT 'RTSP协议取流路径',
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `deleted` TINYINT(1) NOT NULL COMMENT '0:未删除, 1:删除',
     PRIMARY KEY (id)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8 COMMENT 'IPC设备供应商表';

CREATE TABLE `tb_ipc_log` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `device_id` VARCHAR(50) NOT NULL COMMENT '设备id',
    `event_type` VARCHAR(50) NOT NULL COMMENT '事件类型',
    `event_status` TINYINT(1) NOT NULL COMMENT '事件状态, 1:执行成功, 0:执行失败',
    `event_remark` VARCHAR(1000) COMMENT '事件备注',
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `deleted` TINYINT(1) NOT NULL COMMENT '0:未删除, 1:删除',
     INDEX idx_device_id(`device_id`),
     PRIMARY KEY (id)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8 COMMENT 'IPC设备日志记录表';

INSERT INTO `ipc`.`tb_ipc_supplier` (`code`, `name`, `rtsp_path`, `create_time`, `update_time`, `deleted`) VALUES ('DH', '大华', '/cam/realmonitor', '2021-09-10 13:43:32', '2021-09-10 13:43:35', 0);
