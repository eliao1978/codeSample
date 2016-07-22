package com.yp.enums.other;

import com.yp.enums.BaseEnum;

public enum UserEnum implements BaseEnum {
    KP_SUPER_ADMIN("ats@yp.com", "kepler super admin"),
    KP_AGENCY_ADMIN("ypsmkepler+agency+admin@gmail.com", "kepler agency admin"),
    KP_ACCOUNT_ADMIN("ypsmkepler+account+admin@gmail.com", "kepler account admin");

    private String value;
    private String label;

    UserEnum(String value, String label) {
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