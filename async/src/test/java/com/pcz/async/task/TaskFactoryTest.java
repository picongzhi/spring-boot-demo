package com.pcz.async.task;

import com.pcz.async.SpringBootAsyncApplicationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
public class TaskFactoryTest extends SpringBootAsyncApplicationTest {
    @Autowired
    private TaskFactory taskFactory;

    @Test
    public void asyncTaskTest() throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        Future<Boolean> asyncTask1 = taskFactory.asyncTask1();
        Future<Boolean> asyncTask2 = taskFactory.asyncTask2();
        Future<Boolean> asyncTask3 = taskFactory.asyncTask3();

        asyncTask1.get();
        asyncTask2.get();
        asyncTask3.get();
        long end = System.currentTimeMillis();

        log.info("异步任务全部执行结束，总耗时: {} 毫秒", (end - start));
    }

    @Test
    public void taskTest() throws InterruptedException {
        long start = System.currentTimeMillis();
        taskFactory.task1();
        taskFactory.task2();
        taskFactory.task3();
        long end = System.currentTimeMillis();

        log.info("同步任务全部执行结束，总耗时: {} 毫秒", (end - start));
    }
}
