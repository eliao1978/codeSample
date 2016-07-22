package com.yp.pm.kp.be.query;

public interface KeplerCampaignQuery {

    String GET_CAMPAIGN_ID_BY_NAME = "SELECT CAMPAIGN_ID FROM PM_CAMPAIGN WHERE ACCOUNT_ID = ? AND CAMPAIGN_NAME = ?";
}