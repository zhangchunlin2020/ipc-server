package com.ipc.server.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ipc.server.constants.CommonConstants;
import com.ipc.server.entity.IPCDeviceEntity;
import com.ipc.server.entity.IPCSupplierEntity;
import com.ipc.server.enums.IPCCodeStreamTypeEnum;
import com.ipc.server.ffmpeg.FFmpegProcessManager;
import com.ipc.server.helper.ResponseHelper;
import com.ipc.server.mapper.IPCDeviceMapper;
import com.ipc.server.model.IPCDevice;
import com.ipc.server.model.ResponseModel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
@Slf4j
@Service
public class IPCDeviceService extends ServiceImpl<IPCDeviceMapper, IPCDeviceEntity> {

    @Value("${streaming.server.pullEndpoint}")
    private String streamingServePullEndpoint;

    @Autowired
    private FFmpegProcessManager fFmpegProcessManager;
    @Autowired
    private IPCGatherService ipcGatherService;
    @Autowired
    private IPCSupplierService ipcSupplierService;
    @Autowired
    private ResponseHelper responseHelper;

    public synchronized ResponseModel<String> startGather(String deviceId, Integer channel, Integer streamType) {

        IPCCodeStreamTypeEnum streamTypeEnum = IPCCodeStreamTypeEnum.valueOf(streamType);
        if (streamTypeEnum == null) {
            return responseHelper.validateFail("码流类型不正确：0-主码流, 1-辅码流");
        }

        IPCDeviceEntity deviceEntity = this.getByDeviceId(deviceId);
        if (deviceEntity == null) {
            return responseHelper.validateFail("设备不存在");
        }

        if (fFmpegProcessManager.get(deviceEntity.getDeviceId()) != null) {
            return responseHelper.validateFail("设备流采集中");
        }

        LambdaQueryWrapper<IPCSupplierEntity> lambdaQueryWrapper = Wrappers.lambdaQuery(IPCSupplierEntity.class);
        lambdaQueryWrapper.eq(IPCSupplierEntity::getCode, deviceEntity.getDeviceSupplierCode());
        IPCSupplierEntity ipcSupplier = ipcSupplierService.getOne(lambdaQueryWrapper);
        deviceEntity.setSupplier(ipcSupplier);

        boolean result = ipcGatherService.startGather(IPCDevice.fromEntity(deviceEntity), channel, streamTypeEnum);
        System.out.println("url前缀" + streamingServePullEndpoint + "，url后缀"+ deviceId);
        return responseHelper.success(result ? streamingServePullEndpoint + deviceId : null);
    }

    public synchronized ResponseModel stopGather(String deviceId) {
        IPCDeviceEntity entity = this.getByDeviceId(deviceId);
        if (entity == null) {
            return responseHelper.validateFail("设备不存在");
        }
        if (fFmpegProcessManager.get(entity.getDeviceId()) == null) {
            return responseHelper.validateFail("设备未在流采集中");
        }

        boolean result = ipcGatherService.stopGather(new IPCDevice(entity.getDeviceId()));
        return responseHelper.success(result);
    }

    public synchronized ResponseModel batchStartGather(Set<String> deviceIdList, Integer channel, Integer streamType) {

        IPCCodeStreamTypeEnum streamTypeEnum = IPCCodeStreamTypeEnum.valueOf(streamType);
        if (streamTypeEnum == null) {
            return responseHelper.validateFail("码流类型不正确：0-主码流, 1-辅码流");
        }

        List<GatherMessageModel> messageModelList = new ArrayList<>();
        for (String deviceId : deviceIdList) {
            GatherMessageModel messageModel = new GatherMessageModel();
            messageModel.setDeviceId(deviceId);
            messageModel.setSuccess(false);

            messageModelList.add(messageModel);

            try {
                IPCDeviceEntity deviceEntity = this.getByDeviceId(deviceId);
                if (deviceEntity == null) {
                    messageModel.setTip("设备不存在");
                    continue;
                }

                if (fFmpegProcessManager.get(deviceEntity.getDeviceId()) != null) {
                    messageModel.setTip("设备流采集中");
                    continue;
                }

                LambdaQueryWrapper<IPCSupplierEntity> lambdaQueryWrapper = Wrappers.lambdaQuery(IPCSupplierEntity.class);
                lambdaQueryWrapper.eq(IPCSupplierEntity::getCode, deviceEntity.getDeviceSupplierCode());
                IPCSupplierEntity ipcSupplier = ipcSupplierService.getOne(lambdaQueryWrapper);
                deviceEntity.setSupplier(ipcSupplier);

                boolean result = ipcGatherService.startGather(IPCDevice.fromEntity(deviceEntity), channel, streamTypeEnum);
                messageModel.setSuccess(result);
            } catch (Exception ex) {
                log.error("batchStartGather error, deviceId: {}, ex: {}", deviceId, ex);
                messageModel.setTip("执行异常");
            }
        }

        return responseHelper.success(messageModelList);
    }

    public synchronized ResponseModel batchStopGather(Set<String> deviceIdList) {
        List<GatherMessageModel> messageModelList = new ArrayList<>();
        for (String deviceId : deviceIdList) {
            GatherMessageModel messageModel = new GatherMessageModel();
            messageModel.setDeviceId(deviceId);
            messageModel.setSuccess(false);

            messageModelList.add(messageModel);

            try {
                IPCDeviceEntity entity = this.getByDeviceId(deviceId);
                if (entity == null) {
                    messageModel.setTip("设备不存在");
                    continue;
                }
                if (fFmpegProcessManager.get(entity.getDeviceId()) == null) {
                    messageModel.setTip("设备未在流采集中");
                    continue;
                }
                boolean result = ipcGatherService.stopGather(new IPCDevice(entity.getDeviceId()));
                messageModel.setSuccess(result);
            } catch (Exception ex) {
                log.error("batchStopGather error, deviceId: {}, ex: {}", deviceId, ex);
                messageModel.setTip("执行异常");
            }
        }

        return responseHelper.success(messageModelList);
    }

    @Data
    private static class GatherMessageModel {
        private String deviceId;
        private Boolean success;
        private String tip;
    }

    public IPCDeviceEntity getByDeviceId(String deviceId) {
        LambdaQueryWrapper<IPCDeviceEntity> lambdaQueryWrapper = Wrappers.lambdaQuery(IPCDeviceEntity.class);
        lambdaQueryWrapper.eq(IPCDeviceEntity::getDeviceId, deviceId);
        lambdaQueryWrapper.eq(IPCDeviceEntity::getDeleted, CommonConstants.DELETED_NO);
        return this.getOne(lambdaQueryWrapper);
    }

}
