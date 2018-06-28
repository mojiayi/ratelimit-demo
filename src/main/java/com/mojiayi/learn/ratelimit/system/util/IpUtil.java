package com.mojiayi.learn.ratelimit.system.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IP地址读取和转换工具
 *
 * @author mojiayi
 */
public class IpUtil {
    public static final String LOCAL_ADDRESS = "127.0.0.1";
    public static final int IP_ADDRESS_MAX_LENGHT = 15;
    public static final String IP_ADDRESS_SPLIT = ",";

    public IpUtil() {
    }

    public static long ipToLong(String ipAddress) {
        String[] ipAddressInArray = ipAddress.split("\\.");
        long result = 0L;

        for (int i = 0; i < ipAddressInArray.length; ++i) {
            int power = 3 - i;
            int ip = Integer.parseInt(ipAddressInArray[i]);
            result = (long) ((double) result + (double) ip * Math.pow(256.0D, (double) power));
        }

        return result;
    }

    public static String longToIp(long i) {
        return (i >> 24 & 255L) + "." + (i >> 16 & 255L) + "." + (i >> 8 & 255L) + "." + (i & 255L);
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if ("127.0.0.1".equals(ipAddress)) {
                InetAddress inet = null;

                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException var4) {
                    throw new RuntimeException("获取ip异常", var4);
                }

                ipAddress = inet.getHostAddress();
            }
        }

        if (ipAddress != null && ipAddress.length() > 15 && ipAddress.indexOf(",") > 0) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }

        return ipAddress;
    }
}
