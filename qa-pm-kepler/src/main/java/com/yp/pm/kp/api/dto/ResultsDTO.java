package com.yp.pm.kp.api.dto;

public class ResultsDTO {

    private long index;
    private ErrorDTO error;
    private Object object;

    public long getIndex(){
        return index;
    }

    public void setIndex(long index){
        this.index = index;
    }

    public ErrorDTO getError() {
        return error;
    }

    public void setErrors(ErrorDTO error) {
        this.error = error;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}