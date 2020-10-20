package com.pcz.task.quartz.mapper;

import com.pcz.task.quartz.entity.domain.JobAndTrigger;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author picongzhi
 */
@Component
public interface JobMapper {
    /**
     * 查询定时作业和触发器列表
     *
     * @return
     */
    List<JobAndTrigger> list();
}
