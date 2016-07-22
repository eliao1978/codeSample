package com.yp.pm.kp.api.dto.domain;

import com.yp.pm.kp.api.dto.BaseDTO;

public class KeywordDTO extends BaseDTO {

    private long id;
    private long adGroupId;
    private String type;
    private String status;
    private String text;
    private String matchType;
    private long maxCpc;
    private String servingStatus;
    private String approvalStatus;
    private String destinationURL;
    private String effectiveStatus;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAdGroupId() {
        return adGroupId;
    }

    public void setAdGroupId(long id) {
        this.adGroupId = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMatchType() {
        return matchType;
    }

    public long getMaxCpc() {
        return maxCpc;
    }

    public void setMaxCpc(long amount) {
        this.maxCpc = amount;
    }

    public void setMatchType(String type) {
        this.matchType = type;
    }

    public String getServingStatus() {
        return servingStatus;
    }

    public void setServingStatus(String status) {
        this.servingStatus = status;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String status) {
        this.approvalStatus = status;
    }

    public String getDestinationURL() {
        return destinationURL;
    }

    public void setDestinationURL(String url) {
        this.destinationURL = url;
    }

    public String getEffectiveStatus() {
        return effectiveStatus;
    }

    public void setEffectiveStatus(String status) {
        this.effectiveStatus = status;
    }
}