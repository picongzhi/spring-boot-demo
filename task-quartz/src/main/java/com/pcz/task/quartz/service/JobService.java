package com.pcz.task.quartz.service;

import com.github.pagehelper.PageInfo;
import com.pcz.task.quartz.entity.domain.JobAndTrigger;
import com.pcz.task.quartz.entity.form.JobForm;
import org.quartz.SchedulerException;

/**
 * @author picongzhi
 */
public interface JobService {
    /**
     * 添加并启动定时任务
     *
     * @param jobForm 表单
     * @throws Exception 异常
     */
    void addJob(JobForm jobForm) throws Exception;

    /**
     * 删除定时任务
     *
     * @param jobForm 表单
     * @throws SchedulerException 异常
     */
    void deleteJob(JobForm jobForm) throws SchedulerException;

    /**
     * 暂停定时任务
     *
     * @param jobForm 表单
     * @throws SchedulerException 异常
     */
    void pauseJob(JobForm jobForm) throws SchedulerException;

    /**
     * 恢复定时任务
     *
     * @param jobForm 表单
     * @throws SchedulerException 异常
     */
    void resumeJob(JobForm jobForm) throws SchedulerException;

    /**
     * 重新配置定时任务
     *
     * @param jobForm 表单
     * @throws Exception 异常
     */
    void cronJob(JobForm jobForm) throws Exception;

    /**
     * 查询定时任务列表
     *
     * @param currentPage 当前页
     * @param pageSize    每页条数
     * @return 定时任务列表
     */
    PageInfo<JobAndTrigger> list(Integer currentPage, Integer pageSize);
}
