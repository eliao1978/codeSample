package com.yp.enums.api;

import com.yp.enums.BaseEnum;

public enum KeplerDomainTypeEnum implements BaseEnum {
    ACCOUNT("ACCOUNT", "account"),
    AUTHORIZATION("AUTHORIZATION", "authorization"),
    BUDGET("BUDGET", "budget"),
    CAMPAIGN("CAMPAIGN", "campaign"),
    CAMPAIGN_INFO("CAMPAIGN_INFO", "campaign info"),
    AD("AD", "ad"),
    AD_GROUP("AD_GROUP", "adGroup"),
    KEYWORD("KEYWORD", "keyword"),
    CAMPAIGN_AD_SCHEDULE("CAMPAIGN_AD_SCHEDULE", "campaign ad schedule"),
    CAMPAIGN_GEO_CODE("CAMPAIGN_GEO_CODE", "campaign geo code"),
    SITE_LINK_AD_EXTENSION("SITE_LINK_AD_EXTENSION", "site link ad extension"),
    LOCATION_AD_EXTENSION("LOCATION_AD_EXTENSION", "location ad extension"),
    PHONE_AD_EXTENSION("PHONE_AD_EXTENSION", "phone ad extension"),
    PNI_AD_EXTENSION("PNI_AD_EXTENSION", "pni ad extension"),
    LOGO_AD_EXTENSION("LOGO_AD_EXTENSION", "logo ad extension"),
    NEGATIVE_KEYWORD("NEGATIVE_KEYWORD", "negative keyword"),
    REPORTING("REPORTING", "reporting"),
    UPLOAD("UPLOAD", "upload"),
    SYNC("SYNC", "object sync"),
    GEO_LOCATION("LOCATION", "geo location");

    private String value;
    private String label;

    KeplerDomainTypeEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}