package com.ipc.server.api;

import com.ipc.server.model.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/22 13:57
 */
@Slf4j
@RestController
@RequestMapping("/ipc/stat")
@Validated
public class IPCStatController extends BaseController {

    @Value("${streaming.server.flvStatEndpoint}")
    private String streamingServerFlvStatEndpoint;

    @RequestMapping(value = "/http-flv", method = RequestMethod.GET)
    public ResponseModel<String> list() {
        log.info("ipc/stat-list-------------------->");
        return success(streamingServerFlvStatEndpoint);
    }

}
