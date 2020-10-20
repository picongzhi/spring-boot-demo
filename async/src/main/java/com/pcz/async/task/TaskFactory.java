package com.pcz.async.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author picongzhi
 */
@Component
@Slf4j
public class TaskFactory {
    @Async
    public Future<Boolean> asyncTask1() throws InterruptedException {
        doTask("asyncTask1", 5);
        return new AsyncResult<>(Boolean.TRUE);
    }

    @Async
    public Future<Boolean> asyncTask2() throws InterruptedException {
        doTask("asyncTask2", 2);
        return new AsyncResult<>(Boolean.TRUE);
    }

    @Async
    public Future<Boolean> asyncTask3() throws InterruptedException {
        doTask("asyncTask3", 3);
        return new AsyncResult<>(Boolean.TRUE);
    }

    public void task1() throws InterruptedException {
        doTask("task1", 5);
    }

    public void task2() throws InterruptedException {
        doTask("task2", 2);
    }

    public void task3() throws InterruptedException {
        doTask("task3", 3);
    }

    public void doTask(String taskName, Integer time) throws InterruptedException {
        log.info("{}开始执行，当前线程名称[{}]", taskName, Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(time);
        log.info("{}开始成功，当前线程名称[{}]", taskName, Thread.currentThread().getName());
    }
}
