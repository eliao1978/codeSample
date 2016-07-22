package com.yp.pm.kp.api.dto;

import com.yp.enums.error.ErrorTypeEnum;

public class ErrorDTO {

    private String message;
    private ErrorTypeEnum type;
    private long code;

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public ErrorTypeEnum getType(){
        return type;
    }

    public void setType(ErrorTypeEnum type){
        this.type = type;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }
}