package com.yp.pm.kp.api.dto;

public class ResultStatusDTO {

    private String status;
    private ErrorDTO error;

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public ErrorDTO getError(){
        return error;
    }

    public void setError(ErrorDTO error){
        this.error = error;
    }
}