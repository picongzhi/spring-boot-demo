package com.pcz.codegen.common;

/**
 * @author picongzhi
 */
public interface IResultCode {
    /**
     * 获取状态码
     *
     * @return 状态码
     */
    Integer getCode();

    /**
     * 获取返回消息
     *
     * @return 返回消息
     */
    String getMessage();
}
