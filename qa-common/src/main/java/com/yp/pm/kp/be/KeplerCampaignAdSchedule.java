package com.yp.pm.kp.be;

import com.yp.pm.kp.model.domain.CampaignAdSchedule;
import org.json.simple.JSONObject;

import java.util.List;

public interface KeplerCampaignAdSchedule {

    List<CampaignAdSchedule> createCampaignAdSchedule(long accountId, long campaignId, JSONObject data) throws Exception;

    CampaignAdSchedule getCampaignAdScheduleById(long accountId, long campaignId, long adScheduleId) throws Exception;

    List<CampaignAdSchedule> getCampaignAdScheduleByCampaignId(long accountId, long campaignId) throws Exception;

    CampaignAdSchedule deleteCampaignAdSchedule(long accountId, long campaignId, long adScheduleId) throws Exception;

    List<Long> updateCampaignAdSchedules(List<CampaignAdSchedule> adSchedules) throws Exception;
}
