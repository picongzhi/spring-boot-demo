package com.pcz.exception.handler.exception;

import com.pcz.exception.handler.constant.Status;
import lombok.Getter;

/**
 * JSON异常
 *
 * @author picongzhi
 */
@Getter
public class JsonException extends BaseException {
    public JsonException(Status status) {
        super(status);
    }

    public JsonException(Integer code, String messgae) {
        super(code, messgae);
    }
}
