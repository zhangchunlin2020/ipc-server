package com.ipc.server.api;

import com.ipc.server.helper.ResponseHelper;
import com.ipc.server.model.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
public abstract class BaseController {

    @Autowired
    private ResponseHelper responseHelper;

    protected ResponseModel success() {
        return responseHelper.success();
    }

    protected <T> ResponseModel<T> success(T data) {
        return responseHelper.success(data);
    }

    protected <T> ResponseModel<T> fail(T data) {
        return responseHelper.fail(data);
    }

    protected <T> ResponseModel<T> validateFail(T data) {
        return responseHelper.validateFail(data);
    }

}
