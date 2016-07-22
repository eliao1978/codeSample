package com.yp.pm.kp.api.dto.domain;

import com.yp.pm.kp.api.dto.BaseDTO;

public class CampaignAdScheduleDTO extends BaseDTO {

    private long id;
    private long campaignId;
    private Double bidModifier;
    private String status;
    private String dayOfWeek;
    private String startTime;
    private String endTime;
    private String adScheduleStatus;

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

    public Double getBidModifier() {
        return bidModifier;
    }

    public void setBidModifier(Double amount) {
        this.bidModifier = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String day) {
        this.dayOfWeek = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String time) {
        this.startTime = time;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String time) {
        this.endTime = time;
    }

    public String getAdScheduleStatus() {
        return adScheduleStatus;
    }

    public void setAdScheduleStatus(String status) {
        this.adScheduleStatus = status;
    }
}