package com.ipc.server.service.sdk;

import com.ipc.server.model.IPCDevice;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
public interface IIPCLoginService {

    public boolean login(IPCDevice device);

    public boolean logout(IPCDevice device);

}
