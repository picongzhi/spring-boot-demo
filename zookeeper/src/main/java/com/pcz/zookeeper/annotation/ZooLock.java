package com.pcz.zookeeper.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 基于Zookeeper的分布式锁注解
 * 在需要加锁的方法上打上该注解后，AOP会帮助统一管理这个方法的锁
 *
 * @author picongzhi
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ZooLock {
    /**
     * 分布式锁的建
     */
    String key();

    /**
     * 锁释放时间，默认5秒
     */
    long timeout() default 5 * 1000;

    /**
     * 时间格式，默认毫秒
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
