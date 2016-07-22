package com.yp.pm.kp;

import com.yp.pm.kp.be.*;
import com.yp.pm.kp.model.domain.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaseBeTest extends BaseTest {

    @Inject
    KeplerAccount accountDao;
    @Inject
    KeplerBudget budgetDao;
    @Inject
    KeplerCampaign campaignDao;
    @Inject
    KeplerAdGroup adGroupDao;
    @Inject
    KeplerAd adDao;
    @Inject
    KeplerKeyword keywordDao;
    @Inject
    KeplerCampaignAdSchedule adScheduleDao;
    @Inject
    KeplerCampaignGeoCode geoCodeDao;
    @Inject
    KeplerNegativeKeyword negativeKeywordDao;
    @Inject
    KeplerSiteLinkAdExtension siteLinkAdExtensionDao;
    @Inject
    KeplerLocationAdExtension locationAdExtensionDao;
    @Inject
    KeplerPhoneAdExtension phoneAdExtensionDao;
    @Inject
    KeplerLogoAdExtension logoAdExtensionDao;
    @Inject
    KeplerCampaignAdExtension campaignAdExtension;

    protected Account createAccount(long agentId, String clientName, String timeZone) throws Exception {
        return accountDao.createAccount(agentId, clientName, timeZone);
    }

    protected Account getAccountById(long accountId) throws Exception {
        return accountDao.getAccountById(accountId);
    }

    protected Account getAccountByName(String name) throws Exception {
        return accountDao.getAccountByName(name);
    }

    protected Budget createBudget(long accountId, long amount, String type) throws Exception {
        return budgetDao.createBudget(accountId, amount, type, null);
    }

    protected Budget createBudget(long accountId, long amount, String type, String budgetName) throws Exception {
        return budgetDao.createBudget(accountId, amount, type, budgetName);
    }

    protected List<Budget> getBudgetByAccountId(long accountId) throws Exception {
        return budgetDao.getBudgetByAccountId(accountId);
    }

    protected Budget getActiveBudgetByName(String name) throws Exception {
        return budgetDao.getActiveBudgetByName(name);
    }

    protected Budget getBudgetById(long accountId, long budgetId) throws Exception {
        return budgetDao.getBudgetById(accountId, budgetId);
    }

    protected List<Long> deleteBudget(long accountId, List<Long> budgetIds) throws Exception {
        return budgetDao.deleteBudgets(accountId, budgetIds);
    }

    protected List<Campaign> createCampaign(long accountId, long budgetId) throws Exception {
        return campaignDao.createCampaign(accountId, budgetId, null);
    }

    protected List<Campaign> createCampaign(long accountId, long budgetId, JSONArray campaigns) throws Exception {
        return campaignDao.createCampaign(accountId, budgetId, campaigns);
    }

    protected Campaign createCampaignOnly(long accountId, long budgetId) throws Exception {
        return campaignDao.createCampaignOnly(accountId, budgetId, null);
    }

    protected Campaign getCampaignById(long accountId, long campaignId) throws Exception {
        return campaignDao.getCampaignById(accountId, campaignId);
    }

    protected List<Campaign> getCampaignByAccountId(long accountId) throws Exception {
        return campaignDao.getCampaignByAccountId(accountId);
    }

    protected Campaign getCampaignByName(long accountId, String name) throws Exception {
        return campaignDao.getCampaignByName(accountId, name);
    }

    protected Campaign updateCampaign(Campaign campaign) throws Exception {
        return campaignDao.updateCampaign(campaign);
    }

    protected AdGroup createAdGroupOnly(long accountId, long campaignId) throws Exception {
        return adGroupDao.createAdGroupOnly(accountId, campaignId, null);
    }

    protected AdGroup createAdGroupOnly(long accountId, long campaignId, JSONObject adGroup) throws Exception {
        return adGroupDao.createAdGroupOnly(accountId, campaignId, adGroup);
    }

    protected AdGroup getAdGroupById(long accountId, long adGroupId) throws Exception {
        return adGroupDao.getAdGroupById(accountId, adGroupId);
    }

    protected List<AdGroup> getAdGroupByCampaignId(long accountId, long campaignId) throws Exception {
        return adGroupDao.getAdGroupByCampaignId(accountId, campaignId);
    }

    protected AdGroup getAdGroupByName(long accountId, String name) throws Exception {
        return adGroupDao.getAdGroupByName(accountId, name);
    }

    protected List<Long> updateAdGroups(List<AdGroup> AdGroups) throws Exception {
        return adGroupDao.updateAdGroups(AdGroups);
    }

    protected List<Long> deleteAdGroups(long accountId, List<Long> adGroupIds) throws Exception {
        return adGroupDao.deleteAdGroups(accountId, adGroupIds);
    }

    protected List<Ad> createAd(long accountId, long adGroupId) throws Exception {
        return adDao.createAd(accountId, adGroupId, null);
    }

    protected List<Ad> createAd(long accountId, long adGroupId, JSONArray adsData) throws Exception {
        return adDao.createAd(accountId, adGroupId, adsData);
    }

    protected Ad getAdById(long accountId, long adId) throws Exception {
        return adDao.getAdById(accountId, adId);
    }

    protected Ad updateAd(Ad ad) throws Exception {
        return adDao.updateAd(ad);
    }

    protected List<Ad> getAdByAdGroupId(long accountId, long adGroupId) throws Exception {
        return adDao.getAdByAdGroupId(accountId, adGroupId);
    }

    protected Ad getAdByHeadline(long accountId, long adGroupId, String headline) throws Exception {
        return adDao.getAdByHeadline(accountId, adGroupId, headline);
    }

    protected List<Keyword> createKeyword(long accountId, long adGroupId) throws Exception {
        return keywordDao.createKeyword(accountId, adGroupId, null);
    }

    protected List<Keyword> createKeyword(long accountId, long adGroupId, JSONArray keywordData) throws Exception {
        return keywordDao.createKeyword(accountId, adGroupId, keywordData);
    }

    protected List<Long> createKeyword(List<Keyword> keywords) throws Exception {
        return keywordDao.createKeyword(keywords);
    }

    protected Keyword getKeywordById(long accountId, long keywordId) throws Exception {
        return keywordDao.getKeywordById(accountId, keywordId);
    }

    protected List<Keyword> getKeywordByAdGroupId(long accountId, long adgroupId) throws Exception {
        return keywordDao.getKeywordByAdGroupId(accountId, adgroupId);
    }

    protected void updateKeyword(Keyword keyword) throws Exception {
        List<Keyword> keywordList = new ArrayList<>();
        keywordList.add(keyword);
        keywordDao.updateKeywords(keywordList);
    }

    protected List<Long> updateKeywords(List<Keyword> keywordList) throws Exception {
        return keywordDao.updateKeywords(keywordList);
    }

    protected List<CampaignAdSchedule> createCampaignAdSchedule(long accountId, long campaignId) throws Exception {
        return adScheduleDao.createCampaignAdSchedule(accountId, campaignId, null);
    }

    protected List<CampaignAdSchedule> createCampaignAdSchedule(long accountId, long campaignId, JSONObject days) throws Exception {
        return adScheduleDao.createCampaignAdSchedule(accountId, campaignId, days);
    }

    protected CampaignAdSchedule getCampaignAdSchedule(long accountId, long campaignId, long adScheduleId) throws Exception {
        return adScheduleDao.getCampaignAdScheduleById(accountId, campaignId, adScheduleId);
    }

    protected List<CampaignAdSchedule> getCampaignAdScheduleByCampaignId(long accountId, long campaignId) throws Exception {
        return adScheduleDao.getCampaignAdScheduleByCampaignId(accountId, campaignId);
    }

    protected List<CampaignGeoCode> createCampaignGeoCode(long accountId, long campaignId) throws Exception {
        return geoCodeDao.createCampaignGeoCode(accountId, campaignId, null);
    }

    protected List<CampaignGeoCode> createCampaignGeoCode(long accountId, long campaignId, JSONArray geoCodes) throws Exception {
        return geoCodeDao.createCampaignGeoCode(accountId, campaignId, geoCodes);
    }

    protected CampaignGeoCode getCampaignGeoCode(long accountId, long campaignId, long geoCodeId) throws Exception {
        return geoCodeDao.getCampaignGeoCodeById(accountId, campaignId, geoCodeId);
    }

    protected List<CampaignGeoCode> getCampaignGeoCodeByCampaignId(long accountId, long campaignId) throws Exception {
        return geoCodeDao.getCampaignGeoCodeByCampaignId(accountId, campaignId);
    }

    protected long getGeoLibId() throws SQLException {
        return getGeoLibId(null);
    }

    protected long getGeoLibId(JSONObject data) throws SQLException {
        return geoCodeDao.getGeoLibId(data);
    }

    protected NegativeKeyword createNegativeKeyword(long accountId, long campaignId, long adGroupId) throws Exception {
        return negativeKeywordDao.createNegativeKeyword(accountId, campaignId, adGroupId, null);
    }

    protected NegativeKeyword createNegativeKeyword(long accountId, long campaignId, long adGroupId, String keywordData) throws Exception {
        return negativeKeywordDao.createNegativeKeyword(accountId, campaignId, adGroupId, keywordData);
    }

    protected NegativeKeyword createCampaignNegativeKeyword(long accountId, long campaignId, String keywordData) throws Exception {
        return negativeKeywordDao.createCampaignNegativeKeyword(accountId, campaignId, keywordData);
    }

    protected NegativeKeyword getNegativeKeywordById(long accountId, long negKeywordId) throws Exception {
        return negativeKeywordDao.getNegativeKeywordById(accountId, negKeywordId);
    }

    protected List<NegativeKeyword> getNegativeKeywordByCampaignId(long accountId, long campaignId) throws Exception {
        return negativeKeywordDao.getNegativeKeywordByCampaignId(accountId, campaignId);
    }

    protected List<NegativeKeyword> getKeywordByCampaignId(long accountId, long campaignId) throws Exception {
        return negativeKeywordDao.getKeywordByCampaignId(accountId, campaignId);
    }

    protected SiteLinkAdExtension createSiteLinkAdExtension(long accountId) throws Exception {
        return siteLinkAdExtensionDao.createSiteLinkAdExtension(accountId);
    }

    protected SiteLinkAdExtension createSiteLinkAdExtension(SiteLinkAdExtension siteLinkAdExt) throws Exception {
        return siteLinkAdExtensionDao.createSiteLinkAdExtension(siteLinkAdExt);
    }

    protected SiteLinkAdExtension deleteSiteLinkAdExtension(SiteLinkAdExtension siteLinkAdExtension) throws Exception {
        return siteLinkAdExtensionDao.deleteSiteLinkAdExtension(siteLinkAdExtension);
    }

    protected SiteLinkAdExtension getSiteLinkAdExtension(long accountId, long sdExtensionId) throws Exception {
        return siteLinkAdExtensionDao.getSiteLinkAdExtensionById(accountId, sdExtensionId);
    }

    protected List<SiteLinkAdExtension> getSiteLinkAdExtensionByAccountId(long accountId) throws Exception {
        return siteLinkAdExtensionDao.getSiteLinkAdExtensionByAccountId(accountId);
    }

    protected LocationAdExtension createLocationAdExtension(long accountId) throws Exception {
        return locationAdExtensionDao.createLocationAdExtension(accountId);
    }

    protected LocationAdExtension createLocationAdExtension(LocationAdExtension extension) throws Exception {
        return locationAdExtensionDao.createLocationAdExtension(extension);
    }

    protected LocationAdExtension getLocationAdExtension(long accountId, long adExtensionId) throws Exception {
        return locationAdExtensionDao.getLocationAdExtensionById(accountId, adExtensionId);
    }

    protected List<LocationAdExtension> getLocationAdExtensionByAccountId(long accountId) throws Exception {
        return locationAdExtensionDao.getLocationAdExtensionByAccountId(accountId);
    }

    protected LocationAdExtension deleteLocationAdExtension(LocationAdExtension extension) throws Exception {
        return locationAdExtensionDao.deleteLocationAdExtension(extension);
    }

    protected PhoneAdExtension createPhoneAdExtension(long accountId) throws Exception {
        return phoneAdExtensionDao.createPhoneAdExtension(accountId);
    }

    protected PhoneAdExtension createPhoneAdExtension(PhoneAdExtension phoneAdExtension) throws Exception {
        return phoneAdExtensionDao.createPhoneAdExtension(phoneAdExtension);
    }

    protected PhoneAdExtension getPhoneAdExtension(long accountId, long adExtensionId) throws Exception {
        return phoneAdExtensionDao.getPhoneAdExtensionById(accountId, adExtensionId);
    }

    protected List<PhoneAdExtension> getPhoneAdExtensionByAccountId(long accountId) throws Exception {
        return phoneAdExtensionDao.getPhoneAdExtensionByAccountId(accountId);
    }

    protected PhoneAdExtension deletePhoneAdExtension(PhoneAdExtension phoneAdExtension) throws Exception {
        return phoneAdExtensionDao.deletePhoneAdExtension(phoneAdExtension);
    }

    protected LogoAdExtension createLogoAdExtension(long accountId) throws Exception {
        return logoAdExtensionDao.createLogoAdExtension(accountId);
    }

    protected LogoAdExtension createLogoAdExtension(LogoAdExtension logoExt) throws Exception {
        return logoAdExtensionDao.createLogoAdExtension(logoExt);
    }

    protected List<LogoAdExtension> getLogoAdExtensionByAccountId(long accountId) throws Exception {
        return logoAdExtensionDao.getLogoAdExtensionByAccountId(accountId);
    }

    protected LogoAdExtension getLogoAdExtensionById(long accountId, long adExtensionId) throws Exception {
        return logoAdExtensionDao.getLogoAdExtensionById(accountId, adExtensionId);
    }

    protected LogoAdExtension deleteLogoAdExtension(LogoAdExtension logoExt) throws Exception {
        return logoAdExtensionDao.deleteLogoAdExtension(logoExt);
    }

    protected List<Long> createCampaignAdExtensions(List<CampaignAdExtension> campaignAdExtensions) throws Exception {
        return campaignAdExtension.createCampaignAdExtensions(campaignAdExtensions);
    }
}