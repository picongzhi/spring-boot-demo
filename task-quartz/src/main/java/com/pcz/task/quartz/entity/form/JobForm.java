package com.pcz.task.quartz.entity.form;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @author picongzhi
 */
@Data
@Accessors(chain = true)
public class JobForm {
    /**
     * 定时任务全名
     */
    @NotBlank(message = "类名不能为空")
    private String jobClassName;

    /**
     * 任务组名
     */
    @NotBlank(message = "任务组名不能为空")
    private String jobGroupName;

    /**
     * 定时任务cron表达式
     */
    @NotBlank(message = "cron表达式不能为空")
    private String cronExpression;
}