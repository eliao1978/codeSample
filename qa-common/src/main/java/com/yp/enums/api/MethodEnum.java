package com.yp.enums.api;

import com.yp.enums.BaseEnum;

public enum MethodEnum implements BaseEnum {
    POST("post", "post method"),
    PUT("put", "put method"),
    GET("get", "get method"),
    DELETE("delete", "delete method");

    private String value;
    private String label;

    MethodEnum(String value, String label) {
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