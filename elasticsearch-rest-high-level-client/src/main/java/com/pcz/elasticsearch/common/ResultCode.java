package com.pcz.elasticsearch.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author picongzhi
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    /**
     * 接口调用成功
     */
    SUCCESS(0, "Requrst Successful"),
    /**
     * 服务器暂不可用，建议稍后重试
     */
    FAILURE(-1, "System Busy");

    final int code;
    final String msg;
}
