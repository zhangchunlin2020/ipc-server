package com.ipc.server.api;

import com.ipc.server.dto.IPCDeviceMonitorItem;
import com.ipc.server.entity.IPCDeviceEntity;
import com.ipc.server.enums.IPCDeviceStatusEnum;
import com.ipc.server.ffmpeg.FFmpegProcessManager;
import com.ipc.server.model.ResponseModel;
import com.ipc.server.service.IPCDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/22 13:57
 */
@Slf4j
@RestController
@RequestMapping("/ipc/monitor")
@Validated
public class IPCMonitorController extends BaseController {

    @Value("${streaming.server.pullEndpoint}")
    private String streamingServePullEndpoint;

    @Autowired
    private IPCDeviceService ipcDeviceService;
    @Autowired
    private FFmpegProcessManager fFmpegProcessManager;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseModel<List<IPCDeviceMonitorItem>> list() {
        log.info("IPCMonitorController-list-------------------->");
        List<IPCDeviceEntity> ipcDeviceEntityList = ipcDeviceService.list();
        List<IPCDeviceMonitorItem> itemList = new ArrayList<>();
        for (IPCDeviceEntity deviceEntity : ipcDeviceEntityList) {
            IPCDeviceMonitorItem item = new IPCDeviceMonitorItem();
            item.setDeviceId(deviceEntity.getDeviceId());
            item.setDeviceIp(deviceEntity.getDeviceIp());
            if (fFmpegProcessManager.get(item.getDeviceId()) != null) {
                item.setStatus(IPCDeviceStatusEnum.GATHERING);
                item.setUrl(streamingServePullEndpoint + deviceEntity.getDeviceId());
                System.out.println("url前缀" + streamingServePullEndpoint + "，url后缀"+ deviceEntity.getDeviceId());
            }
            itemList.add(item);
        }
        return success(itemList);
    }

}
