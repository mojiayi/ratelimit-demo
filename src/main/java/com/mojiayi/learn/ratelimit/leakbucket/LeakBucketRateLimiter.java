package com.mojiayi.learn.ratelimit.leakbucket;

import com.mojiayi.learn.ratelimit.system.enums.RateLimitTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * 使用漏桶算法实现的访问限制控制器
 *
 * @author mojiayi
 */
@Component
public class LeakBucketRateLimiter {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private HashOperations<String, String, String> hashOperations;

    private long intervalInMills;

    private int times;

    private double intervalPerPermit;

    private RateLimitTypeEnum type;

    @PostConstruct
    public void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    public void resetRequestConfig(long intervalInMills, int times, RateLimitTypeEnum type) {
        this.intervalInMills = intervalInMills;
        this.times = times;
        intervalPerPermit = (double) (intervalInMills / times);
        this.type = type;
    }

    public boolean check(Object userFeatureData) {
        String key = genKey(userFeatureData);
        Map<String, String> entries = hashOperations.entries(key);
        if (entries.isEmpty()) {
            BucketItem bucketItem = new BucketItem(System.currentTimeMillis(), times - 1);
            hashOperations.putAll(key, bucketItem.toHash());
            return true;
        } else {
            BucketItem bucketItem = new BucketItem(entries);

            long lastRefillTime = bucketItem.getLastRefillTime();
            long refillTime = System.currentTimeMillis();
            long intervalSinceLast = refillTime - lastRefillTime;

            long currentTokensRemaining;
            if (intervalSinceLast > intervalInMills) {
                currentTokensRemaining = times;
            } else {
                long grantedTokens = (long) (intervalSinceLast / intervalPerPermit);
                currentTokensRemaining = Math.min(grantedTokens + bucketItem.getTokensRemaining(), times);
            }
            bucketItem.setLastRefillTime(refillTime);
            boolean hasRemainingToken = currentTokensRemaining > 0;
            if (hasRemainingToken) {
                currentTokensRemaining -= 1;
            }
            bucketItem.setTokensRemaining(currentTokensRemaining);
            hashOperations.putAll(key, bucketItem.toHash());
            return hasRemainingToken;
        }
    }

    private String genKey(Object userFeatureData) {
        return "rate:limiter:" + type.getCode() + ":" + intervalInMills + ":" + times + ":" + userFeatureData;
    }
}
