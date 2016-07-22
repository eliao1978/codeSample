package com.yp.pm.kp.api.dto.domain;

import com.yp.pm.kp.api.dto.BaseDTO;

public class PhoneAdExtensionDTO extends BaseDTO {

    private long id;
    private String status;
    private String countryCode;
    private String phoneNumber;
    private boolean callableOnly;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String code) {
        this.countryCode = code;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String number) {
        this.phoneNumber = number;
    }

    public boolean isCallableOnly() {
        return callableOnly;
    }

    public void setCallableOnly(boolean status) {
        this.callableOnly = status;
    }
}
