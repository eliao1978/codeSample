package com.yp.pm.kp.api.dto;

public class CampaignInfoResultDTO {

    private Long accountId;
    private Long campaignId;
    private Long ypId;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long id) {
        this.accountId = id;
    }

    public long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(long id) {
        this.campaignId = id;
    }

    public long getYpId() {
        return ypId;
    }

    public void setYpId(long id) {
        this.ypId = id;
    }


}