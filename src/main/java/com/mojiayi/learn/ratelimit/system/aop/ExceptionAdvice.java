package com.mojiayi.learn.ratelimit.system.aop;

import com.mojiayi.learn.ratelimit.system.enums.ResponseStatusEnum;
import com.mojiayi.learn.ratelimit.system.exception.CommonBizException;
import com.mojiayi.learn.ratelimit.system.exception.RateLimitException;
import com.mojiayi.learn.ratelimit.system.response.CommunicationFactory;
import com.mojiayi.learn.ratelimit.system.response.CommunicationResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统级异常拦截与封装
 *
 * @author mojiayi
 */
@ControllerAdvice(basePackages = "com.mojiayi.learn.ratelimit.controller")
public class ExceptionAdvice {
    @ExceptionHandler({Throwable.class})
    @ResponseBody
    public CommunicationResponse exceptionHandler(Exception e) {
        CommunicationResponse response = CommunicationFactory.createCommunicationResponse();
        if (e instanceof RateLimitException) {
            response.quickSet(ResponseStatusEnum.RATE_LIMIT);
        } else if (e instanceof CommonBizException) {
            response.quickSet(ResponseStatusEnum.SERVER_ERROR.getStatus(), e.getMessage());
        } else {
            response.quickSet(ResponseStatusEnum.SERVER_ERROR);
        }
        return response;
    }
}
