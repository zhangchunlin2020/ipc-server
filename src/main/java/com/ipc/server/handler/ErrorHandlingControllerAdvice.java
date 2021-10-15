package com.ipc.server.handler;

import com.ipc.server.enums.ResponseStatusEnum;
import com.ipc.server.model.ResponseModel;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ResponseModel onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ResponseModel response = new ResponseModel();
        response.setCode(ResponseStatusEnum.BAD_REQUEST.getCode());
        response.setMessage(ResponseStatusEnum.BAD_REQUEST.getMessage());
        List<FieldErrorModel> errorList = new ArrayList<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            FieldErrorModel model = new FieldErrorModel();
            model.setField(fieldError.getField());
            model.setMessage(fieldError.getDefaultMessage());
            errorList.add(model);
        }

        response.setData(errorList);
        return response;
    }

    @Data
    private class FieldErrorModel {
        private String field;
        private String message;
    }

}
