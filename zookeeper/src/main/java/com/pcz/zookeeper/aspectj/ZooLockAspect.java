package com.pcz.zookeeper.aspectj;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.pcz.zookeeper.annotation.LockKeyParam;
import com.pcz.zookeeper.annotation.ZooLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 使用aop切面记录请求日志信息
 *
 * @author picongzhi
 */
@Aspect
@Component
@Slf4j
public class ZooLockAspect {
    private final CuratorFramework zkClient;

    private static final String KEY_PREFIX = "DISTRIBUTED_LOCK_";

    private static final String KEY_SEPARATOR = "/";

    @Autowired
    public ZooLockAspect(CuratorFramework zkClient) {
        this.zkClient = zkClient;
    }

    /**
     * 切入点
     */
    @Pointcut("@annotation(com.pcz.zookeeper.annotation.ZooLock)")
    public void doLock() {
    }

    /**
     * 环绕操作
     *
     * @param point 切入点
     * @return 原方法返回值
     * @throws Throwable 异常
     */
    @Around("doLock()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        Object[] args = point.getArgs();

        ZooLock zooLock = method.getAnnotation(ZooLock.class);
        if (StrUtil.isBlank(zooLock.key())) {
            throw new RuntimeException("分布式锁键不能为空");
        }

        String lockKey = buildLockKey(zooLock, method, args);
        InterProcessMutex mutex = new InterProcessMutex(zkClient, lockKey);
        try {
            if (mutex.acquire(zooLock.timeout(), zooLock.timeUnit())) {
                return point.proceed();
            } else {
                throw new RuntimeException("请勿重复提交");
            }
        } finally {
            mutex.release();
        }
    }

    private String buildLockKey(ZooLock lock, Method method, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        StringBuilder key = new StringBuilder(KEY_SEPARATOR + KEY_PREFIX + lock.key());

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                if (!annotation.annotationType().isInstance(LockKeyParam.class)) {
                    continue;
                }

                String[] fields = ((LockKeyParam) annotation).fields();
                if (ArrayUtil.isEmpty(fields)) {
                    if (ObjectUtil.isNull(args[i])) {
                        throw new RuntimeException("动态参数不能为null");
                    }

                    key.append(KEY_SEPARATOR).append(args[i]);
                } else {
                    for (String field : fields) {
                        Class<?> clazz = args[i].getClass();
                        Field declaredField = clazz.getDeclaredField(field);
                        declaredField.setAccessible(true);
                        Object value = declaredField.get(clazz);
                        key.append(KEY_SEPARATOR).append(value);
                    }
                }
            }
        }

        return key.toString();
    }
}
