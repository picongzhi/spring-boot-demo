package com.pcz.task.quartz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pcz.task.quartz.entity.domain.JobAndTrigger;
import com.pcz.task.quartz.entity.form.JobForm;
import com.pcz.task.quartz.mapper.JobMapper;
import com.pcz.task.quartz.service.JobService;
import com.pcz.task.quartz.util.JobUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author picongzhi
 */
@Service
@Slf4j
public class JobServiceImpl implements JobService {
    private final Scheduler scheduler;
    private final JobMapper jobMapper;

    @Autowired
    public JobServiceImpl(Scheduler scheduler, JobMapper jobMapper) {
        this.scheduler = scheduler;
        this.jobMapper = jobMapper;
    }

    @Override
    public void addJob(JobForm jobForm) throws Exception {
        scheduler.start();
        JobDetail jobDetail = JobBuilder
                .newJob(JobUtil.getClass(jobForm.getJobClassName()).getClass())
                .withIdentity(jobForm.getJobClassName(), jobForm.getJobGroupName())
                .build();

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(jobForm.getCronExpression());
        CronTrigger cronTrigger = TriggerBuilder
                .newTrigger()
                .withIdentity(jobForm.getJobClassName(), jobForm.getJobGroupName())
                .withSchedule(cronScheduleBuilder)
                .build();

        try {
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException e) {
            log.error("[定时任务] 创建失败!", e);
            throw new Exception("[定时任务] 创建失败!");
        }
    }

    @Override
    public void deleteJob(JobForm jobForm) throws SchedulerException {
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobForm.getJobClassName(), jobForm.getJobGroupName()));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobForm.getJobClassName(), jobForm.getJobGroupName()));
        scheduler.deleteJob(JobKey.jobKey(jobForm.getJobClassName(), jobForm.getJobGroupName()));
    }

    @Override
    public void pauseJob(JobForm jobForm) throws SchedulerException {
        scheduler.pauseJob(JobKey.jobKey(jobForm.getJobClassName(), jobForm.getJobGroupName()));
    }

    @Override
    public void resumeJob(JobForm jobForm) throws SchedulerException {
        scheduler.resumeJob(JobKey.jobKey(jobForm.getJobClassName(), jobForm.getJobGroupName()));
    }

    @Override
    public void cronJob(JobForm jobForm) throws Exception {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobForm.getJobClassName(), jobForm.getJobGroupName());
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(jobForm.getCronExpression());
            CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            cronTrigger = cronTrigger.getTriggerBuilder()
                    .withIdentity(triggerKey)
                    .withSchedule(cronScheduleBuilder)
                    .build();
            scheduler.rescheduleJob(triggerKey, cronTrigger);
        } catch (SchedulerException e) {
            log.error("[定时任务] 更新失败!", e);
            throw new Exception("[定时任务] 更新失败!");
        }
    }

    @Override
    public PageInfo<JobAndTrigger> list(Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<JobAndTrigger> list = jobMapper.list();
        return new PageInfo<>(list);
    }
}
