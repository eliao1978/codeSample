package com.yp.pm.kp.api.dto.domain;

import com.yp.pm.kp.api.dto.BaseDTO;

public class CampaignGeoCodeDTO extends BaseDTO {

    private long id;
    private String status;
    private long campaignId;
    private String pointName;
    private String latitude;
    private String longitude;
    private boolean excluded;
    private long geoLibId;
    private long radiusInMiles;
    private Double bidModifier;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String name) {
        this.status = name;
    }

    public long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(long id) {
        this.campaignId = id;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String name) {
        this.pointName = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public boolean isExcluded() {
        return excluded;
    }

    public void setExcluded(boolean status) {
        this.excluded = status;
    }

    public long getGeoLibId() {
        return geoLibId;
    }

    public void setGeoLibId(long id) {
        this.geoLibId = id;
    }

    public long getRadiusInMiles() {
        return radiusInMiles;
    }

    public void setRadiusInMiles(long miles) {
        this.radiusInMiles = miles;
    }

    public Double getBidModifier() {
        return bidModifier;
    }

    public void setBidModifier(Double modifier) {
        this.bidModifier = modifier;
    }
}