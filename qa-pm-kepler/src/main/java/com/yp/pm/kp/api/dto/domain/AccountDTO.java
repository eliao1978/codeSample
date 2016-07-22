package com.yp.pm.kp.api.dto.domain;

import com.yp.pm.kp.api.dto.BaseDTO;

public class AccountDTO extends BaseDTO {

    private long agencyId;
    private String clientName;
    private String status;
    private String timeZone;

    public long getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(long id) {
        this.agencyId = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String name) {
        this.clientName = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String name) {
        this.status = name;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
