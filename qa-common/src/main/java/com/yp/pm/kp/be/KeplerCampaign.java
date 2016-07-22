package com.yp.pm.kp.be;

import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.model.domain.CampaignGeoCode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public interface KeplerCampaign {

    List<Campaign> createCampaign(long accountId, long budgetId, JSONArray data) throws Exception;

    Campaign createCampaignOnly(long accountId, long budgetId, JSONObject data) throws Exception;

    Campaign getCampaignById(long accountId, long campaignId) throws Exception;

    Campaign getCampaignByName(long accountId, String campaignName) throws Exception;

    List<Campaign> getCampaignByAccountId(long accountId) throws Exception;

    List<Long> deleteCampaignsById(Long accountId, List<Long> campaigns) throws Exception;

    Campaign updateCampaign(Campaign campaign) throws Exception;

    List<Campaign> updateCampaigns(List<Campaign> campaigns) throws Exception;

    List<CampaignGeoCode> getCampaignGeoCodeByCampaignId(long accountId, long campaignId) throws Exception;

    void createCampaignAdSchedule(long accountId, long campaignId, JSONObject campaignAdScheduleData) throws Exception;
}