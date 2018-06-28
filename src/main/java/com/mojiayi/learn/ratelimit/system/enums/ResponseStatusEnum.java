package com.mojiayi.learn.ratelimit.system.enums;

/**
 * 请求结果返回状态码枚举值
 *
 * @author mojiayi
 */
public enum ResponseStatusEnum {
    SUCCESS(200, "操作成功"),
    WRONG_ACCOUNT_PWD(302, "用户名或密码错误"),
    IP_FORBIDDEN(315, "IP限制"),
    ILLEGAL_OPERATION(403, "非法操作或没有权限"),
    OBJECT_NOT_EXIST(404, "对象不存在"),
    PARAMETER_TOO_LONG(405, "参数长度过长"),
    PARAMETER_ILLEGAL(414, "参数错误"),
    RATE_LIMIT(416, "频率控制"),
    DUPLICATE_OPERATION(417, "重复操作"),
    ACCOUNT_FORBIDDEN(422, "账号被禁用"),
    SERVER_ERROR(500, "服务器内部错误"),
    SERVER_BUSY(503, "服务器繁忙");

    private int status;

    private String msg;

    ResponseStatusEnum(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
