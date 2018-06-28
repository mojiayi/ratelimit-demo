package com.mojiayi.learn.ratelimit.controller;

import com.mojiayi.learn.ratelimit.system.annotation.RateLimitConfig;
import com.mojiayi.learn.ratelimit.system.enums.RateLimitTypeEnum;
import com.mojiayi.learn.ratelimit.system.response.CommunicationFactory;
import com.mojiayi.learn.ratelimit.system.response.CommunicationResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 访问限制示例接口
 *
 * @author mojiayi
 */
@RestController
@RequestMapping("/api/rateLimitDemo")
public class RateLimitDemoController {
    @RateLimitConfig(intervalInMills = 5000, times = 3, type = RateLimitTypeEnum.USER_ID)
    @RequestMapping("/limitByUserId")
    public CommunicationResponse limitByUserId() {
        return CommunicationFactory.createCommunicationResponse();
    }
}
