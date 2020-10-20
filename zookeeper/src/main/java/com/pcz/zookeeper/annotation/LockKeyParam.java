package com.pcz.zookeeper.annotation;

import java.lang.annotation.*;

/**
 * 分布式锁动态key注解，配置之后key的值会动态获取参数内容
 *
 * @author picongzhi
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LockKeyParam {
    String[] fields() default {};
}
