package com.mojiayi.learn.ratelimit.system.enums;

/**
 * 访问限制方式枚举值
 *
 * @author mojiayi
 */
public enum RateLimitTypeEnum {
    /**
     * 按照IP地址限制访问
     */
    IP("ip"),
    /**
     * 按照用户id限制访问
     */
    USER_ID("userId"),
    /**
     * 全局限制访问
     */
    GLOBAL("global");

    private String code;

    RateLimitTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return this.code;
    }
}
