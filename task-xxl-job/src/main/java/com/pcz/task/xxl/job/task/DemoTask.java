package com.pcz.task.xxl.job.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author picongzhi
 */
@Slf4j
@Component
@JobHandler("demoTask")
public class DemoTask extends IJobHandler {
    @Override
    public ReturnT<String> execute(String param) throws Exception {
        log.info("[param] = {}", param);
        XxlJobLogger.log("demo task run at {}", DateUtil.now());
        return RandomUtil.randomInt(1, 11) % 2 == 0 ? SUCCESS : FAIL;
    }
}
