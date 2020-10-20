package com.pcz.ratelimit.guava.aspect;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author picongzhi
 */
@Slf4j
@Aspect
@Component
public class RateLimiterAspect {
    private static final ConcurrentMap<String, RateLimiter> RATE_LIMITER_CACHE = new ConcurrentHashMap<>();

    @Pointcut("@annotation(com.pcz.ratelimit.guava.annotation.RateLimiter)")
    public void rateLimit() {
    }

    @Around("rateLimit()")
    public Object pointcut(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();

        com.pcz.ratelimit.guava.annotation.RateLimiter rateLimiter = AnnotationUtils.findAnnotation(method, com.pcz.ratelimit.guava.annotation.RateLimiter.class);
        if (rateLimiter != null && rateLimiter.qps() > com.pcz.ratelimit.guava.annotation.RateLimiter.NOT_LIMITED) {
            double qps = rateLimiter.qps();
            if (RATE_LIMITER_CACHE.get(method.getName()) == null) {
                RATE_LIMITER_CACHE.put(method.getName(), RateLimiter.create(qps));
            }

            log.info("[{}]的qps设置为: {}", method.getName(), RATE_LIMITER_CACHE.get(method.getName()));

            if (RATE_LIMITER_CACHE.get(method.getName()) != null &&
                    !RATE_LIMITER_CACHE.get(method.getName()).tryAcquire(rateLimiter.timeout(), rateLimiter.timeUnit())) {
                throw new RuntimeException("手速太快了，慢点儿吧");
            }
        }

        return proceedingJoinPoint.proceed();
    }
}
