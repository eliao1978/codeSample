package com.yp.enums.selenium;

import com.yp.enums.BaseEnum;

public enum SelectTypeEnum implements BaseEnum {
    TEXT("TEXT", "text"),
    INDEX("INDEX", "index"),
    VALUE("VALUE", "value");

    private String value;
    private String label;

    SelectTypeEnum(String value, String label) {
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
}