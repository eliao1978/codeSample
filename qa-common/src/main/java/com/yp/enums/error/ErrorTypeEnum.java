package com.yp.enums.error;

import com.yp.enums.BaseEnum;

public enum ErrorTypeEnum implements BaseEnum {
    NO_RESPONSE("SYSTEM_ERROR", "no response"),
    SYSTEM_ERROR("SYSTEM_ERROR", "system error"),
    USER_ERROR("USER_ERROR", "user error");

    private String value;
    private String label;

    ErrorTypeEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}