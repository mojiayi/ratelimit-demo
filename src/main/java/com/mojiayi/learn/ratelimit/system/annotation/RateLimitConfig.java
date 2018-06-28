package com.mojiayi.learn.ratelimit.system.annotation;

import com.mojiayi.learn.ratelimit.system.enums.RateLimitTypeEnum;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimitConfig {
    // 访问限制时间段，单位为毫秒，默认为1秒
    long intervalInMills() default 1000;

    // 每个访问限制时间段内的可访问次数，默认为5次
    int times() default 5;

    // 访问限制方式，目前有按用户id、按ip地址和全局限制3种方式，默认为按用户id限制
    RateLimitTypeEnum type() default RateLimitTypeEnum.USER_ID;
}
