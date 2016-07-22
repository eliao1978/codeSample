package com.yp.pm.kp.be.query;

public interface KeplerCampaignGeoCodeQuery {

    String GET_GEO_LIB_ID = "SELECT GEO_LIB_ID FROM PM_LOCATION WHERE STATUS = ? AND TYPE = ? AND NAME = ?";
}