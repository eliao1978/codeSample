package com.yp.pm.kp.be.query;

public interface KeplerAdQuery {

    String GET_AD_BY_HEADLINE = "SELECT AD_ID FROM PM_AD WHERE ACCOUNT_ID = ? AND ADGROUP_ID = ? AND HEADLINE = ?";
}