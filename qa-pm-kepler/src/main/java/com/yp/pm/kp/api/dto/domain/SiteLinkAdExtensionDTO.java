package com.yp.pm.kp.api.dto.domain;

import com.yp.pm.kp.api.dto.BaseDTO;

public class SiteLinkAdExtensionDTO extends BaseDTO {

    private long id;
    private String displayText;
    private String status;
    private String destinationUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String text) {
        this.displayText = text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDestinationUrl() {
        return destinationUrl;
    }

    public void setDestinationUrl(String url) {
        this.destinationUrl = url;
    }
}
