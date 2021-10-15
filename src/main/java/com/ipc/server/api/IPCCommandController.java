package com.ipc.server.api;

import com.ipc.server.model.ResponseModel;
import com.ipc.server.service.IPCDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
@Slf4j
@RestController
@RequestMapping("/ipc/command")
public class IPCCommandController extends BaseController {

    @Autowired
    private IPCDeviceService ipcDeviceService;

    @RequestMapping(value = "/gather/start/{deviceId}/{channel}/{streamType}", method = RequestMethod.POST)
    public ResponseModel<String> startGather(@PathVariable String deviceId, @PathVariable Integer channel, @PathVariable Integer streamType) {
        log.info("startGather-------------------->");
        log.info("deviceId：" + deviceId);
        log.info("channel：" + channel);
        log.info("streamType：" + streamType);
        return ipcDeviceService.startGather(deviceId, channel, streamType);
    }

    @RequestMapping(value = "/gather/stop/{deviceId}", method = RequestMethod.POST)
    public ResponseModel stopGather(@PathVariable String deviceId) {
        log.info("stopGather-------------------->");
        return ipcDeviceService.stopGather(deviceId);
    }

    @RequestMapping(value = "/gather/batch/start/{channel}/{streamType}", method = RequestMethod.POST)
    public ResponseModel batchStartGather( @RequestBody Set<String> deviceIdList, @PathVariable Integer channel, @PathVariable Integer streamType) {
        log.info("batchStartGather-------------------->");
        return ipcDeviceService.batchStartGather(deviceIdList, channel, streamType);
    }

    @RequestMapping(value = "/gather/batch/stop", method = RequestMethod.POST)
    public ResponseModel batchStopGather(@RequestBody Set<String> deviceIdList) {
        log.info("batchStopGather-------------------->");
        return ipcDeviceService.batchStopGather(deviceIdList);
    }

}
