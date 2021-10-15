package com.ipc.server.api;

import com.ipc.server.entity.IPCSupplierEntity;
import com.ipc.server.model.ResponseModel;
import com.ipc.server.service.IPCSupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/ipc/supplier")
public class IPCSupplierController extends BaseController {

    @Autowired
    private IPCSupplierService ipcSupplierService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseModel<List<IPCSupplierEntity>> list() {
        log.info("ipc/supplier-list-------------------->");
        return success(ipcSupplierService.list());
    }

//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    public @ResponseBody ResponseModel<Integer> save(@Validated @RequestBody IPCSupplierAddReq req) {
//        IPCSupplierEntity entity = new IPCSupplierEntity(req.getCode(), req.getName(), req.getRtspPath());
//        ipcSupplierService.save(entity);
//        return success(entity.getId());
//    }
//
//    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
//    public @ResponseBody ResponseModel<IPCSupplierEntity> update(@Validated @RequestBody IPCSupplierUpdateReq req, @PathParam("id") Integer id) {
//        ipcSupplierService.saveOrUpdate(new IPCSupplierEntity(id, req.getName(), req.getRtspPath()));
//        return success(ipcSupplierService.getById(id));
//    }

}
