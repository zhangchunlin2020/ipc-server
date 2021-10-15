package com.ipc.server.helper;

import com.ipc.server.enums.ResponseStatusEnum;
import com.ipc.server.model.ResponseModel;
import org.springframework.stereotype.Component;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
@Component
public class ResponseHelper {

    public ResponseModel success() {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setCode(ResponseStatusEnum.SUCCESS.getCode());
        responseModel.setMessage(ResponseStatusEnum.SUCCESS.getMessage());
        return responseModel;
    }

    public <T> ResponseModel<T> success(T data) {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setCode(ResponseStatusEnum.SUCCESS.getCode());
        responseModel.setMessage(ResponseStatusEnum.SUCCESS.getMessage());
        responseModel.setData(data);
        return responseModel;
    }

    public <T> ResponseModel<T> fail(T data) {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setCode(ResponseStatusEnum.FAIL.getCode());
        responseModel.setMessage(ResponseStatusEnum.FAIL.getMessage());
        responseModel.setData(data);
        return responseModel;
    }

    public <T> ResponseModel<T> validateFail(T data) {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setCode(ResponseStatusEnum.VALIDATE_FAIL.getCode());
        responseModel.setMessage(ResponseStatusEnum.VALIDATE_FAIL.getMessage());
        responseModel.setData(data);
        return responseModel;
    }

}
