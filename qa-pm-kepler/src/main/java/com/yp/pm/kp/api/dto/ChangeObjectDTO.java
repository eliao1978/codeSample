package com.yp.pm.kp.api.dto;

public class ChangeObjectDTO {

    private long id;
    private long parentId;
    private long objectId;
    private String objectType;
    private String parentType;

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long id) {
        this.parentId = id;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long id) {
        this.objectId = id;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String type) {
        this.objectType = type;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String type) {
        this.parentType = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
