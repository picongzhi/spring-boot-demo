package com.pcz.exception.handler.exception;

import com.pcz.exception.handler.constant.Status;
import lombok.Getter;

/**
 * 页面异常
 *
 * @author picongzhi
 */
@Getter
public class PageException extends BaseException {
    public PageException(Status status) {
        super(status);
    }

    public PageException(Integer code, String message) {
        super(code, message);
    }
}
