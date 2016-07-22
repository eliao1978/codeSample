package com.yp.enums.selenium;

import com.yp.enums.BaseEnum;

public enum LocatorTypeEnum implements BaseEnum {
    CSS("CSS", "css"),
    NAME("NAME", "name"),
    ID("ID", "id"),
    LINK_TEXT("LINK_TEXT", "linkText"),
    XPATH("XPATH", "xpath"),
    JS("JS","js");

    private final String value;
    private final String label;

    LocatorTypeEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public static LocatorTypeEnum fromValue(String value) {
        for (LocatorTypeEnum type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }
}