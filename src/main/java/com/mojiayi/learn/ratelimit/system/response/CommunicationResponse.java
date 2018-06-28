package com.mojiayi.learn.ratelimit.system.response;

import com.mojiayi.learn.ratelimit.system.enums.ResponseStatusEnum;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommunicationResponse {
    /**
     * 请求唯一id
     */
    private String traceId;
    /**
     * 状态码200代表成功，其它代表失败
     */
    private Integer status;
    /**
     * 错误或成功信息
     */
    private String msg;

    /**
     * 数据容器
     */
    protected Map<String, Object> data = new HashMap<>();

    /**
     * 快速的设置各种信息
     *
     * @param status 状态码
     * @param msg    描述信息
     */
    public void quickSet(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public void quickSet(ResponseStatusEnum statusEnum) {
        this.status = statusEnum.getStatus();
        this.msg = statusEnum.getMsg();
    }

    public void addSingleDataItem(String key, Object value) {
        keepDataNotNull();
        data.put(key, value);
    }

    public void addMultipleDataItems(Map<String, ? extends Object> items) {
        keepDataNotNull();
        data.putAll(items);
    }

    private void keepDataNotNull() {
        if (data == null) {
            data = new LinkedHashMap<>();
        }
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CommunicationResponse [tradeId=" + traceId + ", status=" + status + ", msg=" + msg + "]";
    }
}
