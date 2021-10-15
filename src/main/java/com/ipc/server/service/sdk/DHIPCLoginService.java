package com.ipc.server.service.sdk;

import com.ipc.server.model.IPCDevice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
@Slf4j
@Service
public class DHIPCLoginService implements IIPCLoginService {

    @Override
    public boolean login(IPCDevice device) {
        return false;
    }

    @Override
    public boolean logout(IPCDevice device) {
        return false;
    }

}
