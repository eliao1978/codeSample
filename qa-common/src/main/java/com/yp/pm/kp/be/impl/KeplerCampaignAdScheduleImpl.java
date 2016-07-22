package com.yp.pm.kp.be.impl;

import com.yp.pm.kp.be.KeplerCampaignAdSchedule;
import com.yp.pm.kp.enums.model.CampaignAdScheduleStatusEnum;
import com.yp.pm.kp.enums.model.DayOfWeekEnum;
import com.yp.pm.kp.exception.ApiException;
import com.yp.pm.kp.model.domain.CampaignAdSchedule;
import com.yp.pm.kp.service.domain.CampaignAdScheduleService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class KeplerCampaignAdScheduleImpl extends BaseKeplerImpl implements KeplerCampaignAdSchedule {

    @Inject
    private CampaignAdScheduleService createCampaignAdSchedules;

    /**
     * @param accountId      long
     * @param campaignId     long
     * @param adScheduleData JSONObject
     * @return list of campaign ad schedule domain object
     * @throws Exception
     */
    public List<CampaignAdSchedule> createCampaignAdSchedule(long accountId, long campaignId, JSONObject adScheduleData) throws Exception {
        List<CampaignAdSchedule> campaignAdScheduleList = new ArrayList<>();

        if (adScheduleData != null && adScheduleData.get("days") != null) {
            JSONArray days = (JSONArray) adScheduleData.get("days");

            for (Object item : days) {
                JSONObject dayAndTime = (JSONObject) item;
                CampaignAdSchedule adSchedule = new CampaignAdSchedule();
                adSchedule.setAccountId(accountId);
                adSchedule.setCampaignId(campaignId);
                adSchedule.setBidModifier((adScheduleData.get("bidModifier") != null) ? Double.valueOf(adScheduleData.get("bidModifier").toString()) : 1.0);
                adSchedule.setAdScheduleStatus(CampaignAdScheduleStatusEnum.ACTIVE);
                adSchedule.setDayOfWeek(DayOfWeekEnum.fromValue(dayAndTime.get("day").toString().toLowerCase()));
                adSchedule.setStartTime(dayAndTime.get("startTime").toString());
                adSchedule.setEndTime(dayAndTime.get("endTime").toString());
                adSchedule.setCreateTimestamp(now);
                adSchedule.setLastUpdateTimestamp(now);
                campaignAdScheduleList.add(adSchedule);
            }
        } else {
            for (DayOfWeekEnum day : DayOfWeekEnum.values()) {
                CampaignAdSchedule adSchedule = new CampaignAdSchedule();
                adSchedule.setAccountId(accountId);
                adSchedule.setCampaignId(campaignId);
                adSchedule.setBidModifier(1.0);
                adSchedule.setAdScheduleStatus(CampaignAdScheduleStatusEnum.ACTIVE);
                adSchedule.setDayOfWeek(day);
                adSchedule.setStartTime("0000");
                adSchedule.setEndTime("2400");
                adSchedule.setCreateTimestamp(now);
                adSchedule.setLastUpdateTimestamp(now);
                campaignAdScheduleList.add(adSchedule);
            }
        }
        List<Long> adScheduleIdList = createCampaignAdSchedules.createCampaignAdSchedules(campaignAdScheduleList);

        for (long id : adScheduleIdList) {
            logger.debug("Ad Schedule [" + id + "] created");
        }

        return getCampaignAdScheduleByCampaignId(accountId, campaignId);
    }

    /**
     * @param accountId    long
     * @param campaignId   long
     * @param adScheduleId long
     * @return campaign ad schedule domain object
     */
    public CampaignAdSchedule getCampaignAdScheduleById(long accountId, long campaignId, long adScheduleId) {
        return createCampaignAdSchedules.getCampaignAdSchedule(accountId, campaignId, adScheduleId);
    }

    /**
     * @param accountId  long
     * @param campaignId long
     * @return list of campaign ad schedule domain object
     */
    public List<CampaignAdSchedule> getCampaignAdScheduleByCampaignId(long accountId, long campaignId) {
        return createCampaignAdSchedules.getCampaignAdSchedules(accountId, campaignId);
    }

    public List<Long> updateCampaignAdSchedules(List<CampaignAdSchedule> adSchedules) throws Exception {
        return createCampaignAdSchedules.updateCampaignAdSchedules(adSchedules);
    }

    /**
     * @param accountId    long
     * @param campaignId   long
     * @param adScheduleId long
     * @return campaign ad schedule domain object
     * @throws ApiException
     */
    public CampaignAdSchedule deleteCampaignAdSchedule(long accountId, long campaignId, long adScheduleId) throws ApiException {
        List<Long> adScheduleIds = new ArrayList<>();
        adScheduleIds.add(adScheduleId);
        createCampaignAdSchedules.deleteCampaignAdSchedules(accountId, campaignId, adScheduleIds);
        return getCampaignAdScheduleById(accountId, campaignId, adScheduleId);
    }
}
