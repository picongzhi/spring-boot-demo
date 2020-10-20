package com.pcz.swagger.beauty.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author picongzhi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户实体", description = "User Entity")
public class User implements Serializable {
    private static final long serialVersionUID = -5849447704614856866L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", required = true)
    private Integer id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", required = true)
    private String name;

    /**
     * 岗位
     */
    @ApiModelProperty(value = "岗位", required = true)
    private String job;
}
