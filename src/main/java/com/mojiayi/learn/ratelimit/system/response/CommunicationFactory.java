package com.mojiayi.learn.ratelimit.system.response;

import com.mojiayi.learn.ratelimit.system.enums.ResponseStatusEnum;
import com.mojiayi.learn.ratelimit.system.util.IpUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.MDC;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

/**
 * 创建接口返回对象
 *
 * @author mojiayi
 */
public class CommunicationFactory {
    private static final String TRACE_ID_KEY = "traceId";

    private CommunicationFactory() {
    }

    public static CommunicationResponse createCommunicationResponse() {
        CommunicationResponse response = new CommunicationResponse();

        try {
            String traceId = getTraceId();
            response.setTraceId(traceId);
            response.quickSet(ResponseStatusEnum.SUCCESS);
        } catch (UnknownHostException e) {
            response.quickSet(ResponseStatusEnum.SERVER_ERROR);
        }
        return response;
    }

    private static String getTraceId() throws UnknownHostException {
        Optional<Object> traceId = Optional.ofNullable(MDC.get(TRACE_ID_KEY));
        if (!traceId.isPresent()) {
            String address = InetAddress.getLocalHost().getHostAddress();
            long addressInLong = IpUtil.ipToLong(address);
            traceId = Optional.of(RandomStringUtils.randomAlphanumeric(12).toLowerCase() + addressInLong);
            MDC.put(TRACE_ID_KEY, traceId.get());
        }
        return traceId.orElse("").toString();
    }
}
