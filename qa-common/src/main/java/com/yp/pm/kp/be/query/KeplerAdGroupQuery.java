package com.yp.pm.kp.be.query;

public interface KeplerAdGroupQuery {

    String GET_AD_GROUP_ID_BY_NAME = "SELECT ADGROUP_ID FROM PM_ADGROUP WHERE ACCOUNT_ID = ? AND ADGROUP_NAME = ? AND ADGROUP_STATUS = 'active'";
}