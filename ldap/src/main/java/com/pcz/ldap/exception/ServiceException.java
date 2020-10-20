package com.pcz.ldap.exception;

import com.pcz.ldap.api.ResultCode;
import lombok.Getter;

/**
 * @author picongzhi
 */
public class ServiceException extends RuntimeException {
    @Getter
    private int errorCode;

    @Getter
    private String errorMsg;

    public ServiceException(ResultCode resultCode) {
        this(resultCode.getCode(), resultCode.getMsg());
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Integer errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public ServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ServiceException(Throwable throwable) {
        super(throwable);
    }

    public ServiceException(String message, Throwable throwable, boolean enableSuppression, boolean writableStackTrace) {
        super(message, throwable, enableSuppression, writableStackTrace);
    }
}
