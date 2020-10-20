package com.pcz.elasticsearch.exception;

import com.pcz.elasticsearch.common.ResultCode;
import lombok.Getter;

public class ElasticsearchException extends RuntimeException {
    @Getter
    private int errorCode;

    @Getter
    private String errorMsg;

    public ElasticsearchException(ResultCode resultCode) {
        this(resultCode.getCode(), resultCode.getMsg());
    }

    public ElasticsearchException(String message) {
        super(message);
    }

    public ElasticsearchException(Integer errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public ElasticsearchException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ElasticsearchException(Throwable throwable) {
        super(throwable);
    }

    public ElasticsearchException(String message, Throwable throwable,
                                  boolean enableSuppression, boolean writableStackTrace) {
        super(message, throwable, enableSuppression, writableStackTrace);
    }
}
