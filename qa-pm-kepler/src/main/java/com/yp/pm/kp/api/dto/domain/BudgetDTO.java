package com.yp.pm.kp.api.dto.domain;

import com.yp.pm.kp.api.dto.BaseDTO;

public class BudgetDTO extends BaseDTO {

    private long id;
    private String name;
    private long amount;
    private String deliveryMethod;
    private String periodType;
    private String status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String name) {
        this.status = name;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String method) {
        this.deliveryMethod = method;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String type) {
        this.periodType = type;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
