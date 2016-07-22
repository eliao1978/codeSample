package com.yp.pm.kp.api.dto.domain;

import com.yp.pm.kp.api.dto.BaseDTO;
import java.util.List;
public class AdDTO extends BaseDTO {

    private long id;
    private long adGroupId;
    private String status;
    private String type;
    private String approveStatus;
    private String headline;
    private String description1;
    private String description2;
    private String displayURL;
    private String destinationURL;
    private boolean mobilePreferred;
    private List<String> disapprovalReasons;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String name) {
        this.status = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String status) {
        this.approveStatus = status;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String text) {
        this.headline = text;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String text) {
        this.description1 = text;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String text) {
        this.description2 = text;
    }

    public String getDisplayURL() {
        return displayURL;
    }

    public void setDisplayURL(String url) {
        this.displayURL = url;
    }

    public String getDestinationURL() {
        return destinationURL;
    }

    public void setDestinationURL(String url) {
        this.destinationURL = url;
    }

    public List<String> getDisapprovalReasons() {
        return disapprovalReasons;
    }

    public void setDisapprovalReasons(List<String> reason) {
        this.disapprovalReasons = reason;
    }

    public boolean isMobilePreferred() {
        return mobilePreferred;
    }

    public void setMobilePreferred(boolean status) {
        this.mobilePreferred = status;
    }

    public String getEffectiveStatus() {
        return effectiveStatus;
    }

    public void setEffectiveStatus(String status) {
        this.effectiveStatus = status;
    }
}