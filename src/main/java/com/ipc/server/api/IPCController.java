package com.ipc.server.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ipc.server.dto.IPCDeviceAddReq;
import com.ipc.server.entity.IPCDeviceEntity;
import com.ipc.server.model.IPCDevice;
import com.ipc.server.model.ResponseModel;
import com.ipc.server.service.IPCDeviceService;
import com.ipc.server.dto.IPCDeviceEditReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
@Slf4j
@RestController
@RequestMapping("/ipc")
@Validated
public class IPCController extends BaseController {

    @Autowired
    private IPCDeviceService ipcDeviceService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseModel<List<IPCDeviceEntity>> list() {
        return success(ipcDeviceService.list());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseModel add(@Validated @RequestBody IPCDeviceAddReq req) {

        // 设备id重复性校验
        LambdaQueryWrapper<IPCDeviceEntity> lambdaQueryWrapper = Wrappers.lambdaQuery(IPCDeviceEntity.class);
        lambdaQueryWrapper.eq(IPCDeviceEntity::getDeviceId, req.getDeviceId());
        lambdaQueryWrapper.eq(IPCDeviceEntity::getDeviceSupplierCode, req.getDeviceSupplierCode());
        long count = ipcDeviceService.count(lambdaQueryWrapper);
        if (count != 0) {
            return validateFail("设备id重复");
        }

        IPCDeviceEntity entity = new IPCDeviceEntity();
        BeanUtils.copyProperties(req, entity);

        // 登录校验
//        if (req.getLoginCheckEnabled()) {
//            IPCDevice device = IPCDevice.fromEntity(entity);
//        }

        ipcDeviceService.save(entity);
        return success(entity.getId());
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ResponseModel<IPCDeviceEntity> view(@PathVariable("id") Integer id) {
        log.info("IPCController-view-------------------->");
        return success(ipcDeviceService.getById(id));
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public ResponseModel<IPCDeviceEntity> edit(@PathVariable("id") Integer id, @Validated @RequestBody IPCDeviceEditReq req) {
        IPCDeviceEntity entity = new IPCDeviceEntity();
        BeanUtils.copyProperties(req, entity);
        entity.setId(id);
        ipcDeviceService.saveOrUpdate(entity);
        return success(ipcDeviceService.getById(id));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseModel<Boolean> delete(@PathVariable("id") Integer id) {
        ipcDeviceService.removeById(id);
        return success(true);
    }

}
