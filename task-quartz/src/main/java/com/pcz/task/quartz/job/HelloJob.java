package com.pcz.task.quartz.job;

import cn.hutool.core.date.DateUtil;
import com.pcz.task.quartz.job.base.BaseJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author picongzhi
 */
@Slf4j
public class HelloJob implements BaseJob {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.error("Hello Job 执行时间: {}", DateUtil.now());
    }
}
