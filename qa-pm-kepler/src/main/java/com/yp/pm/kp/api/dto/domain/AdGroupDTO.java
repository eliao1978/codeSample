package com.yp.pm.kp.api.dto.domain;

import com.yp.pm.kp.api.dto.BaseDTO;

public class AdGroupDTO extends BaseDTO {

    private long id;
    private long campaignId;
    private String name;
    private String status;
    private String effectiveStatus;
    private long maxCpc;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(long id) {
        this.campaignId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String name) {
        this.status = name;
    }

    public String getEffectiveStatus() {
        return effectiveStatus;
    }

    public void setEffectiveStatus(String status) {
        this.effectiveStatus = status;
    }

    public long getMaxCpc() {
        return maxCpc;
    }

    public void setMaxCpc(long maxCpc) {
        this.maxCpc = maxCpc;
    }
}
