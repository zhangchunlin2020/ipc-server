package com.ipc.server.helper;

import com.ipc.server.constants.IPCConstants;
import com.ipc.server.enums.IPCCodeStreamTypeEnum;
import com.ipc.server.model.IPCDevice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
@Component
public class IPCHelper {

    @Value("${streaming.server.pushEndpoint}")
    private String streamingServePushEndpoint;

    public String getRTSPURL(IPCDevice device, int channel, IPCCodeStreamTypeEnum streamType) {
        StringBuilder sb = new StringBuilder();
        sb.append(IPCConstants.RTSP_PROTOCOL_PREFIX);
        sb.append(device.getUsername() + ":" + device.getPassword());
        sb.append("@");
        sb.append(device.getIp());
        sb.append(":");
        sb.append(device.getPort());
        sb.append(device.getRtspPath());
        sb.append("?");
        sb.append("channel=" + channel);
        sb.append("&subtype=" + streamType.getCode());
        return sb.toString();
    }

    public String getPushURL(IPCDevice device) {
        StringBuilder sb = new StringBuilder();
        sb.append(streamingServePushEndpoint);
        sb.append(device.getId());
        return sb.toString();
    }

}
