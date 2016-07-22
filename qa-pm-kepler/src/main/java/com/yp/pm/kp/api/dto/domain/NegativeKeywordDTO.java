package com.yp.pm.kp.api.dto.domain;

import com.yp.pm.kp.api.dto.BaseDTO;

public class NegativeKeywordDTO extends BaseDTO {

    private long id;
    private long campaignId;
    private long adGroupId;
    private String status;
    private String text;
    private String matchType;

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

    public long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(long id) {
        this.campaignId = id;
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

    public void setMatchType(String type) {
        this.matchType = type;
    }
}