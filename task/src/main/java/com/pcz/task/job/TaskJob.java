package com.pcz.task.job;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author picongzhi
 */
@Component
@Slf4j
public class TaskJob {
    /**
     * 每隔10s执行一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void job1() {
        log.info("[job1] 开始执行: {}", DateUtil.formatDateTime(new Date()));
    }

    /**
     * 从启动时间开始，每隔2s执行
     */
    @Scheduled(fixedRate = 2000)
    public void job2() {
        log.info("[job2] 开始执行: {}", DateUtil.formatDateTime(new Date()));
    }

    /**
     * 从启动时间开始，延迟5s后间隔4s执行
     */
    @Scheduled(fixedDelay = 4000, initialDelay = 5000)
    public void job3() {
        log.info("[job3] 开始执行: {}", DateUtil.formatDateTime(new Date()));
    }
}
