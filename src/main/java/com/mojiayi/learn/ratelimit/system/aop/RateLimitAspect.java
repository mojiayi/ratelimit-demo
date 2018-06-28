package com.mojiayi.learn.ratelimit.system.aop;

import com.mojiayi.learn.ratelimit.leakbucket.LeakBucketRateLimiter;
import com.mojiayi.learn.ratelimit.system.annotation.RateLimitConfig;
import com.mojiayi.learn.ratelimit.system.enums.RateLimitTypeEnum;
import com.mojiayi.learn.ratelimit.system.exception.RateLimitException;
import com.mojiayi.learn.ratelimit.system.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 访问限制接口请求拦截
 *
 * @author mojiayi
 */
@Slf4j
@Aspect
@Component
public class RateLimitAspect {
    @Autowired
    private LeakBucketRateLimiter leakBucketRateLimiter;

    @Pointcut("execution(public * com.mojiayi.learn.ratelimit.controller.*.*(..))")
    public void requestPoint() {

    }

    @Around("requestPoint() && @annotation(rateLimitConfig)")
    public Object rateLimitCheck(ProceedingJoinPoint joinPoint, RateLimitConfig rateLimitConfig) throws Throwable {
        long intervalInMills = rateLimitConfig.intervalInMills();
        int times = rateLimitConfig.times();
        RateLimitTypeEnum type = rateLimitConfig.type();
        leakBucketRateLimiter.resetRequestConfig(intervalInMills, times, type);
        Object userFeatureData;
        if (RateLimitTypeEnum.USER_ID.equals(type)) {
            // TODO 临时硬编码的模拟数据，实际项目中需要根据token获取真实用户id
            userFeatureData = 123L;
        } else if (RateLimitTypeEnum.IP.equals(type)) {
            userFeatureData = getIpInLong();
        } else {
            userFeatureData = RateLimitTypeEnum.GLOBAL.getCode();
        }
        boolean hasRemainingToken = leakBucketRateLimiter.check(userFeatureData);
        if (!hasRemainingToken) {
            log.warn("访问频率限制type={},userFeatureData={}", type, userFeatureData);
            throw new RateLimitException();
        }
        return joinPoint.proceed();
    }

    private long getIpInLong() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ipInStr = IpUtil.getIpAddr(request);
        long ipInLong = 0L;
        if (StringUtils.isNoneBlank(ipInStr)) {
            ipInLong = IpUtil.ipToLong(ipInStr);
        }
        return ipInLong;
    }
}
