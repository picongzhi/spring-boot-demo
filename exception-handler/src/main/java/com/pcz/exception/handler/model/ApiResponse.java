package com.pcz.exception.handler.model;

import com.pcz.exception.handler.constant.Status;
import com.pcz.exception.handler.exception.BaseException;
import lombok.Data;

/**
 * @author picongzhi
 */
@Data
public class ApiResponse {
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 数据
     */
    private Object data;

    /**
     * 无参构造函数
     */
    private ApiResponse() {
    }

    /**
     * 全参构造函数
     *
     * @param code    状态码
     * @param message 提示信息
     * @param data    数据
     */
    private ApiResponse(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 构造一个自定义的API返回
     *
     * @param code    状态码
     * @param message 提示信息
     * @param data    数据
     * @return ApiResponse
     */
    public static ApiResponse of(Integer code, String message, Object data) {
        return new ApiResponse(code, message, data);
    }

    /**
     * 构造一个成功且带有数据的API返回
     *
     * @param data 数据
     * @return ApiResponse
     */
    public static ApiResponse ofSuccess(Object data) {
        return ofStatus(Status.OK, data);
    }

    /**
     * 构造一个成功且自定义消息的API返回
     *
     * @param message
     * @return
     */
    public static ApiResponse ofMessage(String message) {
        return of(Status.OK.getCode(), message, null);
    }

    /**
     * 构造一个有状态的API返回
     *
     * @param status 状态
     * @return ApiResponse
     */
    public static ApiResponse ofStatus(Status status) {
        return ofStatus(status, null);
    }

    /**
     * 构造一个有状态且带数据的API返回
     *
     * @param status 状态
     * @param data   数据
     * @return ApiResponse
     */
    public static ApiResponse ofStatus(Status status, Object data) {
        return of(status.getCode(), status.getMessage(), data);
    }

    /**
     * 构造一个异常的API返回
     *
     * @param t   异常
     * @param <T> BaseException的子类
     * @return ApiResponse
     */
    public static <T extends BaseException> ApiResponse ofException(T t) {
        return ofException(t, null);
    }

    /**
     * 构造一个异常且带有数据的API返回
     *
     * @param t    异常
     * @param data 数据
     * @param <T>  BaseException的子类
     * @return ApiResponse
     */
    public static <T extends BaseException> ApiResponse ofException(T t, Object data) {
        return of(t.getCode(), t.getMessage(), data);
    }
}
