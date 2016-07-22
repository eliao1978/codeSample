package com.yp.pm.kp.api.dto;

import org.json.simple.JSONObject;

public class BaseDTO {

    private long accountId;
    private String request;
    private JSONObject response;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public JSONObject getResponse() {
        return response;
    }

    public void setResponse(JSONObject response) {
        this.response = response;
    }
}
