package com.pcz.swagger.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author picongzhi
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "通用API接口返回", description = "Common Api Response")
public class ApiResponse<T> implements Serializable {
    private static final long serialVersionUID = -5661770196775663862L;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", required = true)
    private Integer code;

    /**
     * 信息
     */
    @ApiModelProperty(value = "信息", required = true)
    private String message;

    /**
     * 数据
     */
    @ApiModelProperty(value = "数据", required = true)
    private T data;
}
