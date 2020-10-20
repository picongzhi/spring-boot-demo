package com.pcz.task.quartz.controller;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.pcz.task.quartz.common.ApiResponse;
import com.pcz.task.quartz.entity.domain.JobAndTrigger;
import com.pcz.task.quartz.entity.form.JobForm;
import com.pcz.task.quartz.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author picongzhi
 */
@RestController
@RequestMapping("/job")
@Slf4j
public class JobController {
    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addJob(@Valid JobForm jobForm) {
        try {
            jobService.addJob(jobForm);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.msg(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(ApiResponse.msg("操作成功"), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteJob(JobForm jobForm) throws SchedulerException {
        if (StrUtil.hasBlank(jobForm.getJobGroupName(), jobForm.getJobClassName())) {
            return new ResponseEntity<>(ApiResponse.msg("参数不能为空"), HttpStatus.BAD_REQUEST);
        }

        jobService.deleteJob(jobForm);
        return new ResponseEntity<>(ApiResponse.msg("删除成功"), HttpStatus.OK);
    }

    @PutMapping(params = "pause")
    public ResponseEntity<ApiResponse> pauseJob(JobForm jobForm) throws SchedulerException {
        if (StrUtil.hasBlank(jobForm.getJobGroupName(), jobForm.getJobClassName())) {
            return new ResponseEntity<>(ApiResponse.msg("参数不能为空"), HttpStatus.BAD_REQUEST);
        }

        jobService.pauseJob(jobForm);
        return new ResponseEntity<>(ApiResponse.msg("暂停成功"), HttpStatus.OK);
    }

    @PutMapping(params = "resume")
    public ResponseEntity<ApiResponse> resumeJob(JobForm jobForm) throws SchedulerException {
        if (StrUtil.hasBlank(jobForm.getJobGroupName(), jobForm.getJobClassName())) {
            return new ResponseEntity<>(ApiResponse.msg("参数不能为空"), HttpStatus.BAD_REQUEST);
        }

        jobService.resumeJob(jobForm);
        return new ResponseEntity<>(ApiResponse.msg("恢复成功"), HttpStatus.OK);
    }

    @PutMapping(params = "cron")
    public ResponseEntity<ApiResponse> cronJob(@Valid JobForm jobForm) {
        try {
            jobService.cronJob(jobForm);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.msg(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(ApiResponse.msg("修改成功"), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> jobList(Integer currentPage, Integer pageSize) {
        if (ObjectUtil.isNull(currentPage)) {
            currentPage = 1;
        }

        if (ObjectUtil.isNull(pageSize)) {
            pageSize = 10;
        }

        PageInfo<JobAndTrigger> all = jobService.list(currentPage, pageSize);

        return ResponseEntity.ok(ApiResponse.ok(Dict.create()
                .set("total", all.getTotal())
                .set("data", all.getList())));
    }
}
