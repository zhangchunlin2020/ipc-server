package com.ipc.server.exception;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
public class MaxLimitedException extends RuntimeException {

    public MaxLimitedException() {
        super();
    }

    public MaxLimitedException(String message) {
        super(message);
    }

    public MaxLimitedException(String message, Throwable cause) {
        super(message, cause);
    }

    public MaxLimitedException(Throwable cause) {
        super(cause);
    }

    protected MaxLimitedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
