package com.yp.pm.kp.be;

import com.yp.pm.kp.model.domain.CampaignGeoCode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.SQLException;
import java.util.List;

public interface KeplerCampaignGeoCode {

    List<CampaignGeoCode> createCampaignGeoCode(long accountId, long campaignId, JSONArray data) throws Exception;

    List<Long> deleteCampaignGeoCodes(long accountId, long campaignId, List<Long> geoCodeIdList) throws Exception;

    List<Long> updateCampaignGeoCodes(List<CampaignGeoCode> geoCodes) throws Exception;

    List<CampaignGeoCode> getCampaignGeoCodeByCampaignId(long accountId, long campaignId) throws Exception;

    CampaignGeoCode getCampaignGeoCodeById(long accountId, long campaignId, long geoCodeId) throws Exception;

    long getGeoLibId(JSONObject data) throws SQLException;
}