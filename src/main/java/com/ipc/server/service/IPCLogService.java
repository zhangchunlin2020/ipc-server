package com.ipc.server.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ipc.server.entity.IPCLogEntity;
import com.ipc.server.mapper.IPCLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
@Slf4j
@Service
public class IPCLogService extends ServiceImpl<IPCLogMapper, IPCLogEntity> {
}
