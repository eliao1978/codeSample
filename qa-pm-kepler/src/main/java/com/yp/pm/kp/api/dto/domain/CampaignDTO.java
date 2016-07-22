package com.yp.pm.kp.api.dto.domain;

import com.yp.pm.kp.api.dto.BaseDTO;

public class CampaignDTO extends BaseDTO {

    private long id;
    private long budgetId;
    private String name;
    private String type;
    private String status;
    private String networkType;
    private String deviceType;
    private Double mobileBidModifier;
    private Double desktopBidModifier;
    private String servingStatus;
    private String startDate;
    private String endDate;
    private String biddingStrategyType;
    private String adRotation;
    private String dynamicURL;
    private String includeKeywordCloseVariant;
    private String effectiveStatus;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(long id) {
        this.budgetId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String type) {
        this.networkType = type;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String type) {
        this.deviceType = type;
    }

    public Double getMobileBidModifier() {
        return mobileBidModifier;
    }

    public void setMobileBidModifier(Double modifier) {
        this.mobileBidModifier = modifier;
    }

    public Double getDesktopBidModifier() {
        return desktopBidModifier;
    }

    public void setDesktopBidModifier(Double modifier) {
        this.desktopBidModifier = modifier;
    }

    public String getServingStatus() {
        return servingStatus;
    }

    public void setServingStatus(String status) {
        this.servingStatus = status;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String date) {
        this.startDate = date;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String date) {
        this.endDate = date;
    }

    public String getBiddingStrategyType() {
        return biddingStrategyType;
    }

    public void setBiddingStrategyType(String type) {
        this.biddingStrategyType = type;
    }

    public String getAdRotation() {
        return adRotation;
    }

    public void setAdRotation(String rotation) {
        this.adRotation = rotation;
    }

    public String getDynamicURL() {
        return dynamicURL;
    }

    public void setDynamicURL(String url) {
        this.dynamicURL = url;
    }

    public String getIncludeKeywordCloseVariant() {
        return includeKeywordCloseVariant;
    }

    public void setIncludeKeywordCloseVariant(String variant) {
        this.includeKeywordCloseVariant = variant;
    }

    public String getEffectiveStatus() {
        return effectiveStatus;
    }

    public void setEffectiveStatus(String status) {
        this.effectiveStatus = status;
    }
}
