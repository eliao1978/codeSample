package com.yp.pm.kp.be.impl;

import com.yp.pm.kp.be.KeplerCampaign;
import com.yp.pm.kp.be.query.KeplerCampaignQuery;
import com.yp.pm.kp.enums.model.*;
import com.yp.pm.kp.model.domain.AdGroup;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.model.domain.CampaignGeoCode;
import com.yp.pm.kp.service.domain.CampaignService;
import com.yp.util.DBUtil;
import com.yp.util.DateUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class KeplerCampaignImpl extends BaseKeplerImpl implements KeplerCampaign, KeplerCampaignQuery {

    @Inject
    private CampaignService campaignService;
    @Inject
    private KeplerAdGroupImpl adGroupDao;
    @Inject
    private KeplerCampaignGeoCodeImpl geoCodeDao;
    @Inject
    private KeplerCampaignAdScheduleImpl adScheduleDao;

    /**
     * method to create single/multiple campaigns along with campaign geo code, ad schedule, ad group, ad, and keyword.
     *
     * @param accountId     long
     * @param budgetId      long
     * @param campaignsData JSONArray
     * @return list of Campaign domain object
     * @throws Exception
     */
    public List<Campaign> createCampaign(long accountId, long budgetId, JSONArray campaignsData) throws Exception {
        List<Campaign> campaignList = new ArrayList<>();
        if (campaignsData != null) {
            for (Object data : campaignsData) {
                JSONObject campaignData = (JSONObject) data;
                long campaignId = createCampaignOnly(accountId, budgetId, campaignData).getCampaignId();

                if (campaignData != null) {
                    createCampaignGeoCode(accountId, campaignId, (JSONArray) campaignData.get("campaignGeoCode"));
                    createCampaignAdSchedule(accountId, campaignId, (JSONObject) campaignData.get("campaignAdSchedule"));
                    createAdGroup(accountId, campaignId, (JSONArray) campaignData.get("adGroup"));
                } else {
                    createCampaignGeoCode(accountId, campaignId, null);
                    createCampaignAdSchedule(accountId, campaignId, null);
                    createAdGroup(accountId, campaignId, null);
                }
                Campaign newCampaign = getCampaignById(accountId, campaignId);
                campaignList.add(newCampaign);
            }
        } else {
            long campaignId = createCampaignOnly(accountId, budgetId, null).getCampaignId();
            createCampaignGeoCode(accountId, campaignId, null);
            createCampaignAdSchedule(accountId, campaignId, null);
            createAdGroup(accountId, campaignId, null);
            Campaign newCampaign = getCampaignById(accountId, campaignId);
            campaignList.add(newCampaign);
        }

        return campaignList;
    }

    /**
     * method to create only single campaign.
     *
     * @param accountId    long
     * @param budgetId     long
     * @param campaignData JSONObject
     * @return Campaign domain object
     * @throws Exception
     */
    public Campaign createCampaignOnly(long accountId, long budgetId, JSONObject campaignData) throws Exception {
        List<Campaign> campaignList = new ArrayList<>();

        Campaign campaign = new Campaign();
        campaign.setAccountId(accountId);
        campaign.setBudgetId(budgetId);
        campaign.setCampaignStatus(CampaignStatusEnum.ACTIVE);
        campaign.setServingStatus(CampaignServingStatusEnum.SERVING);
        campaign.setEffectiveStatus(CampaignEffectiveStatusEnum.ACTIVE);
        campaign.setStartDate(DateUtil.getStartDate(new Timestamp(System.currentTimeMillis())));
        campaign.setIncludeKeywordCloseVariant(true);
        campaign.setCreateTimestamp(now);
        campaign.setLastUpdateTimestamp(now);

        if (campaignData != null) {
            if (campaignData.get("startDate") != null) {
                campaign.setStartDate(campaignData.get("startDate").toString());
            }
            campaign.setCampaignName((campaignData.get("campaignName") != null) ? campaignData.get("campaignName").toString() : "CAMP_" + System.currentTimeMillis());
            campaign.setAdRotation((campaignData.get("adRotation") != null) ? AdRotationEnum.fromValue(campaignData.get("adRotation").toString().toLowerCase()) : AdRotationEnum.OPTIMIZE_FOR_CLICKS);
            campaign.setBiddingStrategyType((campaignData.get("biddingStrategyType") != null) ? BiddingStrategyTypeEnum.fromValue(campaignData.get("biddingStrategyType").toString().toLowerCase()) : BiddingStrategyTypeEnum.MANUAL_CPC);
            campaign.setCampaignType((campaignData.get("campaignType") != null) ? CampaignTypeEnum.fromValue(campaignData.get("campaignType").toString().toLowerCase()) : CampaignTypeEnum.SEARCH);
            campaign.setNetworkType((campaignData.get("networkType") != null) ? NetworkTypeEnum.fromValue(campaignData.get("networkType").toString().toLowerCase()) : NetworkTypeEnum.SEARCH_NETWORK);
            campaign.setDeviceType((campaignData.get("deviceType") != null) ? CampaignDeviceTypeEnum.fromValue(campaignData.get("deviceType").toString().toLowerCase()) : CampaignDeviceTypeEnum.DESKTOP);
            campaign.setDynamicUrl((campaignData.get("dynamicUrl") != null) ? campaignData.get("dynamicUrl").toString() : "www.test.com");
            campaign.setIncludeKeywordCloseVariant((campaignData.get("keywordCloseVariant") != null) ? Boolean.valueOf(campaignData.get("dynamicUrl").toString()) : true);

            if (campaignData.get("mobileBitModifier") != null && campaignData.get("desktopBidModifier") != null) {
                campaign.setMobileBidModifier(Double.valueOf(campaignData.get("mobileBitModifier").toString()));
                campaign.setDesktopBidModifier(Double.valueOf(campaignData.get("desktopBidModifier").toString()));
            } else if (campaignData.get("mobileBitModifier") != null) {
                campaign.setMobileBidModifier(Double.valueOf(campaignData.get("mobileBitModifier").toString()));
            } else if (campaignData.get("desktopBidModifier") != null) {
                campaign.setDesktopBidModifier(Double.valueOf(campaignData.get("desktopBidModifier").toString()));
            } else {
                campaign.setDesktopBidModifier(1.0);
                campaign.setMobileBidModifier(1.0);
            }
        } else {
            campaign.setCampaignName("CAMP_" + System.currentTimeMillis());

            campaign.setAdRotation(AdRotationEnum.ROTATE_INDEFINITELY);
            campaign.setBiddingStrategyType(BiddingStrategyTypeEnum.MANUAL_CPC);
            campaign.setCampaignType(CampaignTypeEnum.SEARCH);
            campaign.setNetworkType(NetworkTypeEnum.SEARCH_NETWORK);
            campaign.setDeviceType(CampaignDeviceTypeEnum.DESKTOP);
            campaign.setDesktopBidModifier(1.0);
            campaign.setMobileBidModifier(1.0);
            campaign.setDynamicUrl("www.test.com");
        }
        campaignList.add(campaign);
        List<Long> campaignIds = campaignService.createCampaigns(campaignList);

        logger.debug("Campaign [" + campaignIds.get(0) + "] created");
        return getCampaignById(accountId, campaignIds.get(0));
    }

    /**
     * @param campaign Campaign
     * @return Campaign object
     * @throws Exception
     */
    public Campaign updateCampaign(Campaign campaign) throws Exception {
        List<Campaign> campaignList = new ArrayList<>();
        campaignList.add(campaign);
        List<Long> campaigns = campaignService.updateCampaigns(campaignList);

        return getCampaignById(campaign.getAccountId(), campaigns.get(0));
    }

    /**
     * @param campaigns List<Campaign>
     * @return list of campaign objects
     * @throws Exception
     */
    public List<Campaign> updateCampaigns(List<Campaign> campaigns) throws Exception {
        List<Campaign> campaignList = new ArrayList<>();
        campaignService.updateCampaigns(campaigns);

        for (Campaign campaign : campaigns) {
            campaignList.add(getCampaignById(campaign.getAccountId(), campaign.getCampaignId()));
        }

        return campaignList;
    }

    public List<Long> deleteCampaignsById(Long accountId, List<Long> campaigns) throws Exception {
        return campaignService.deleteCampaigns(accountId, campaigns);
    }

    /**
     * method to return Campaign domain object
     *
     * @param accountId  long
     * @param campaignId long
     * @return Campaign domain object
     * @throws Exception
     */
    public Campaign getCampaignById(long accountId, long campaignId) throws Exception {
        return campaignService.getCampaign(accountId, campaignId);
    }

    /**
     * @param accountId long
     * @param name      String
     * @return Campaign
     * @throws Exception
     */
    public Campaign getCampaignByName(long accountId, String name) throws Exception {
        List<Map<String, Object>> queryList = DBUtil.queryForList(GET_CAMPAIGN_ID_BY_NAME, new Object[]{accountId, name}, new int[]{Types.NUMERIC, Types.VARCHAR});

        if (queryList.size() > 1) {
            throw new Exception("Multiple active campaigns were found");
        } else if (queryList.size() == 1) {
            return getCampaignById(accountId, Long.valueOf(queryList.get(0).get("CAMPAIGN_ID").toString()));
        } else {
            return null;
        }
    }

    /**
     * method to return Campaign domain object
     *
     * @param accountId long
     * @return list of Campaign domain object
     * @throws Exception
     */
    public List<Campaign> getCampaignByAccountId(long accountId) throws Exception {
        return campaignService.getCampaigns(accountId);
    }

    /**
     * @param accountId  long
     * @param campaignId long
     * @return CampaignGeoCode object
     * @throws Exception
     */
    public List<CampaignGeoCode> getCampaignGeoCodeByCampaignId(long accountId, long campaignId) throws Exception {
        return geoCodeDao.getCampaignGeoCodeByCampaignId(accountId, campaignId);
    }

    /**
     * method to create single/multiple campaign geo code
     *
     * @param accountId           long
     * @param campaignId          long
     * @param campaignGeoCodeData JSONArray
     * @throws Exception
     */
    private List<CampaignGeoCode> createCampaignGeoCode(long accountId, long campaignId, JSONArray campaignGeoCodeData) throws Exception {
        return geoCodeDao.createCampaignGeoCode(accountId, campaignId, campaignGeoCodeData);
    }

    /**
     * method to create campaign ad schedule
     *
     * @param accountId              long
     * @param campaignId             long
     * @param campaignAdScheduleData JSONObject
     * @throws Exception
     */
    public void createCampaignAdSchedule(long accountId, long campaignId, JSONObject campaignAdScheduleData) throws Exception {
        adScheduleDao.createCampaignAdSchedule(accountId, campaignId, campaignAdScheduleData);
    }

    /**
     * method to create ad group
     *
     * @param accountId   long
     * @param campaignId  long
     * @param adGroupData JSONArray
     * @return list of adGroup domain object
     * @throws Exception
     */
    private List<AdGroup> createAdGroup(long accountId, long campaignId, JSONArray adGroupData) throws Exception {
        return adGroupDao.createAdGroup(accountId, campaignId, adGroupData);
    }
}