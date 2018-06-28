package com.mojiayi.learn.ratelimit.leakbucket;

import java.util.HashMap;
import java.util.Map;

/**
 * 漏桶元素对象
 *
 * @author mojiayi
 */
public class BucketItem {
    private long lastRefillTime;
    private long tokensRemaining;

    public BucketItem(long lastRefillTime, long tokensRemaining) {
        this.lastRefillTime = lastRefillTime;
        this.tokensRemaining = tokensRemaining;
    }

    public BucketItem(Map<String, String> hash) {
        this.lastRefillTime = Long.parseLong(hash.get("lastRefillTime"));
        this.tokensRemaining = Integer.parseInt(hash.get("tokensRemaining"));
    }

    public Map<String, String> toHash() {
        Map<String, String> hash = new HashMap<>(2);
        hash.put("lastRefillTime", String.valueOf(lastRefillTime));
        hash.put("tokensRemaining", String.valueOf(tokensRemaining));
        return hash;
    }

    public long getLastRefillTime() {
        return lastRefillTime;
    }

    public void setLastRefillTime(long lastRefillTime) {
        this.lastRefillTime = lastRefillTime;
    }

    public long getTokensRemaining() {
        return tokensRemaining;
    }

    public void setTokensRemaining(long tokensRemaining) {
        this.tokensRemaining = tokensRemaining;
    }
}
