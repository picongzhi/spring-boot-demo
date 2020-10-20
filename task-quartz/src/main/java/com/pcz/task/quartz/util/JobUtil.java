package com.pcz.task.quartz.util;

import com.pcz.task.quartz.job.base.BaseJob;

/**
 * @author picongzhi
 */
public class JobUtil {
    /**
     * 根据全类名获取Job实例
     *
     * @param className 全类名
     * @return Job实例
     * @throws Exception 异常
     */
    public static BaseJob getClass(String className) throws Exception {
        Class<?> clazz = Class.forName(className);

        return (BaseJob) clazz.newInstance();
    }
}
