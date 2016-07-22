package com.yp.enums.selenium;

import com.yp.enums.BaseEnum;

public enum SeleniumDriverTypeEnum implements BaseEnum {
    HTMLUNIT("htmlunit", "headless"),
    PHANTOMJS("PHANTOMJS", "headless with JS support"),
    FIREFOX("FIREFOX", "firefox browser"),
    SAFARI("SAFARI", "safari browser"),
    CHROME("CHROME", "chrome browser");

    private String value;
    private String label;

    SeleniumDriverTypeEnum(String value, String label) {
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

    public static SeleniumDriverTypeEnum fromValue(String value) {
        for (SeleniumDriverTypeEnum type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid driver: [" + value + "]");
    }
}