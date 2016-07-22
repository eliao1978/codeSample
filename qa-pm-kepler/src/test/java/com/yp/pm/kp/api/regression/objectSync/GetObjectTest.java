package com.yp.pm.kp.api.regression.objectSync;

import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ChangeObjectDTO;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.model.domain.*;
import com.yp.util.DBUtil;
import com.yp.util.DateUtil;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GetObjectTest extends BaseAPITest {

    private static Map<String, Object> testData;

    @Before
    public void updateParameter() throws Exception {
        // set TIME_TO_LIVE_IN_SECS = 1 for too_many_changes_limit parameter
        if (Long.valueOf(DBUtil.queryForList("SELECT TIME_TO_LIVE_IN_SECS FROM PM_PARAMETER WHERE PARAMETER_NAME = 'too_many_changes_limit'").get(0).get("TIME_TO_LIVE_IN_SECS").toString()) > 1) {
            DBUtil.update("UPDATE PM_PARAMETER SET TIME_TO_LIVE_IN_SECS = 1 WHERE PARAMETER_NAME = 'too_many_changes_limit'");
        }
    }

    @Test
    public void testGetSyncObject() throws Exception {
        String[] objectOrder = {"BUDGET", "CAMPAIGN", "SITE_LINK_AD_EXTENSION", "LOCATION_AD_EXTENSION", "PHONE_AD_EXTENSION",
                "AD_SCHEDULE", "AD_SCHEDULE", "AD_SCHEDULE", "AD_SCHEDULE", "AD_SCHEDULE", "AD_SCHEDULE", "AD_SCHEDULE",
                "GEOCODE", "AD_GROUP", "NEG_KEYWORD", "AD", "KEYWORD"};

        String command = "curl -X POST " +
                "-d '{ \"allowPartialFailure\" : 'true', \"modifiedSince\" : \"" + DateUtil.getCurrentDate(new Timestamp(System.currentTimeMillis())).concat("-000000") + "\"}' " +
                "-H '" + contentType + "' " +
                "-H 'username:'" + userName + "'' " +
                "-H 'requestid:'" + requestId + "'' " +
                "-H 'accountId:'" + ((Account) getTestData().get("account")).getAccountId() + "'' " +
                "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/account/syncbackobjects";

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.SYNC, getAPIResponse(command));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 17);

        for (ResultsDTO result : responseDTO.getResults()) {
            ArrayList objects = (ArrayList) result.getObject();
            assertObjectEqual(objects.size(), 17);

            int i = 0;
            for (Object item : objects) {
                ChangeObjectDTO changeObjectDTO = (ChangeObjectDTO) item;

                // validate the order of returned object
                assertConditionTrue(changeObjectDTO.getObjectType().equalsIgnoreCase(objectOrder[i]));
                i += 1;

                // validate the content of returned object
                if (changeObjectDTO.getObjectType().equalsIgnoreCase("BUDGET")) {
                    assertConditionTrue(changeObjectDTO.getParentType().equalsIgnoreCase("ACCOUNT"));
                    assertObjectEqual(changeObjectDTO.getParentId(), ((Account) getTestData().get("account")).getAccountId());
                    assertObjectEqual(changeObjectDTO.getObjectId(), ((Budget) getTestData().get("budget")).getBudgetId());
                } else if (changeObjectDTO.getObjectType().equalsIgnoreCase("CAMPAIGN")) {
                    assertConditionTrue(changeObjectDTO.getParentType().equalsIgnoreCase("ACCOUNT"));
                    assertObjectEqual(changeObjectDTO.getParentId(), ((Account) getTestData().get("account")).getAccountId());
                    assertObjectEqual(changeObjectDTO.getObjectId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
                } else if (changeObjectDTO.getObjectType().equalsIgnoreCase("AD_GROUP")) {
                    assertConditionTrue(changeObjectDTO.getParentType().equalsIgnoreCase("CAMPAIGN"));
                    assertObjectEqual(changeObjectDTO.getParentId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
                    assertObjectEqual(changeObjectDTO.getObjectId(), ((AdGroup) getTestData().get("adGroup")).getAdGroupId());
                } else if (changeObjectDTO.getObjectType().equalsIgnoreCase("GEOCODE")) {
                    assertConditionTrue(changeObjectDTO.getParentType().equalsIgnoreCase("CAMPAIGN"));
                    assertObjectEqual(changeObjectDTO.getParentId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
                    assertObjectEqual(changeObjectDTO.getObjectId(), ((CampaignGeoCode) getTestData().get("campaignGeoCode")).getGeocodeId());
                } else if (changeObjectDTO.getObjectType().equalsIgnoreCase("AD_SCHEDULE")) {
                    assertConditionTrue(changeObjectDTO.getParentType().equalsIgnoreCase("CAMPAIGN"));
                    assertObjectEqual(changeObjectDTO.getParentId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
                    assertConditionTrue(((getCampaignAdScheduleByCampaignId(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId())).size() >= 0));
                } else if (changeObjectDTO.getObjectType().equalsIgnoreCase("KEYWORD")) {
                    assertConditionTrue(changeObjectDTO.getParentType().equalsIgnoreCase("AD_GROUP"));
                    assertObjectEqual(changeObjectDTO.getParentId(), ((AdGroup) getTestData().get("adGroup")).getAdGroupId());
                    assertObjectEqual(changeObjectDTO.getObjectId(), ((Keyword) getTestData().get("keyword")).getKeywordId());
                } else if (changeObjectDTO.getObjectType().equalsIgnoreCase("AD")) {
                    assertConditionTrue(changeObjectDTO.getParentType().equalsIgnoreCase("AD_GROUP"));
                    assertObjectEqual(changeObjectDTO.getParentId(), ((AdGroup) getTestData().get("adGroup")).getAdGroupId());
                    assertObjectEqual(changeObjectDTO.getObjectId(), ((Ad) getTestData().get("ad")).getAdId());
                } else if (changeObjectDTO.getObjectType().equalsIgnoreCase("SITE_LINK_AD_EXTENSION")) {
                    assertConditionTrue(changeObjectDTO.getParentType().equalsIgnoreCase("ACCOUNT"));
                    assertObjectEqual(changeObjectDTO.getParentId(), ((SiteLinkAdExtension) getTestData().get("siteLinkAdExtension")).getAccountId());
                    assertObjectEqual(changeObjectDTO.getObjectId(), ((SiteLinkAdExtension) getTestData().get("siteLinkAdExtension")).getAdExtensionId());
                } else if (changeObjectDTO.getObjectType().equalsIgnoreCase("PHONE_AD_EXTENSION")) {
                    assertConditionTrue(changeObjectDTO.getParentType().equalsIgnoreCase("ACCOUNT"));
                    assertObjectEqual(changeObjectDTO.getParentId(), ((PhoneAdExtension) getTestData().get("phoneAdExtension")).getAccountId());
                    assertObjectEqual(changeObjectDTO.getObjectId(), ((PhoneAdExtension) getTestData().get("phoneAdExtension")).getAdExtensionId());
                } else if (changeObjectDTO.getObjectType().equalsIgnoreCase("LOCATION_AD_EXTENSION")) {
                    assertConditionTrue(changeObjectDTO.getParentType().equalsIgnoreCase("ACCOUNT"));
                    assertObjectEqual(changeObjectDTO.getParentId(), ((LocationAdExtension) getTestData().get("locationAdExtension")).getAccountId());
                    assertObjectEqual(changeObjectDTO.getObjectId(), ((LocationAdExtension) getTestData().get("locationAdExtension")).getAdExtensionId());
                } else if (changeObjectDTO.getObjectType().equalsIgnoreCase("NEG_KEYWORD")) {
                    assertConditionTrue(changeObjectDTO.getParentType().equalsIgnoreCase("AD_GROUP"));
                    assertObjectEqual(changeObjectDTO.getParentId(), ((AdGroup) getTestData().get("adGroup")).getAdGroupId());
                    assertObjectEqual(changeObjectDTO.getObjectId(), ((List<NegativeKeyword>) getTestData().get("negativeKeyword")).get(0).getNegKeywordId());
                }
            }
        }
    }

    @Test
    public void testGetSyncByObjectCampaignId() throws Exception {
        String[] objectOrder = {"BUDGET", "CAMPAIGN", "AD_SCHEDULE", "AD_SCHEDULE", "AD_SCHEDULE", "AD_SCHEDULE", "AD_SCHEDULE", "AD_SCHEDULE", "AD_SCHEDULE",
                "GEOCODE", "AD_GROUP", "NEG_KEYWORD", "AD", "KEYWORD"};

        String command = "curl -X POST " +
                "-d '{ \"allowPartialFailure\" : true, " +
                "\"campaignIds\" : [" + ((Campaign) getTestData().get("campaign")).getCampaignId() + "]," +
                "\"modifiedSince\" : \"" + DateUtil.getCurrentDate(new Timestamp(System.currentTimeMillis())).concat("-000000") + "\"}' " +
                "-H '" + contentType + "' " +
                "-H 'username:'" + userName + "'' " +
                "-H 'requestid:'" + requestId + "'' " +
                "-H 'accountId:'" + ((Account) getTestData().get("account")).getAccountId() + "'' " +
                "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/account/syncbackobjects";

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.SYNC, getAPIResponse(command));
        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 14);

        for (ResultsDTO result : responseDTO.getResults()) {
            ArrayList objects = (ArrayList) result.getObject();
            assertObjectEqual(objects.size(), 14);

            int i = 0;
            for (Object item : objects) {
                ChangeObjectDTO changeObjectDTO = (ChangeObjectDTO) item;

                // validate the order of returned object
                assertConditionTrue(changeObjectDTO.getObjectType().equalsIgnoreCase(objectOrder[i]));
                i += 1;

                // validate the content of returned object
                if (changeObjectDTO.getObjectType().equalsIgnoreCase("BUDGET")) {
                    assertConditionTrue(changeObjectDTO.getParentType().equalsIgnoreCase("ACCOUNT"));
                    assertObjectEqual(changeObjectDTO.getParentId(), ((Account) getTestData().get("account")).getAccountId());
                    assertObjectEqual(changeObjectDTO.getObjectId(), ((Budget) getTestData().get("budget")).getBudgetId());
                } else if (changeObjectDTO.getObjectType().equalsIgnoreCase("CAMPAIGN")) {
                    assertConditionTrue(changeObjectDTO.getParentType().equalsIgnoreCase("ACCOUNT"));
                    assertObjectEqual(changeObjectDTO.getParentId(), ((Account) getTestData().get("account")).getAccountId());
                    assertObjectEqual(changeObjectDTO.getObjectId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
                } else if (changeObjectDTO.getObjectType().equalsIgnoreCase("AD_GROUP")) {
                    assertConditionTrue(changeObjectDTO.getParentType().equalsIgnoreCase("CAMPAIGN"));
                    assertObjectEqual(changeObjectDTO.getParentId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
                    assertObjectEqual(changeObjectDTO.getObjectId(), ((AdGroup) getTestData().get("adGroup")).getAdGroupId());
                } else if (changeObjectDTO.getObjectType().equalsIgnoreCase("GEOCODE")) {
                    assertConditionTrue(changeObjectDTO.getParentType().equalsIgnoreCase("CAMPAIGN"));
                    assertObjectEqual(changeObjectDTO.getParentId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
                    assertObjectEqual(changeObjectDTO.getObjectId(), ((CampaignGeoCode) getTestData().get("campaignGeoCode")).getGeocodeId());
                } else if (changeObjectDTO.getObjectType().equalsIgnoreCase("AD_SCHEDULE")) {
                    assertConditionTrue(changeObjectDTO.getParentType().equalsIgnoreCase("CAMPAIGN"));
                    assertObjectEqual(changeObjectDTO.getParentId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
                    assertConditionTrue(((getCampaignAdScheduleByCampaignId(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId())).size() >= 0));
                } else if (changeObjectDTO.getObjectType().equalsIgnoreCase("KEYWORD")) {
                    assertConditionTrue(changeObjectDTO.getParentType().equalsIgnoreCase("AD_GROUP"));
                    assertObjectEqual(changeObjectDTO.getParentId(), ((AdGroup) getTestData().get("adGroup")).getAdGroupId());
                    assertObjectEqual(changeObjectDTO.getObjectId(), ((Keyword) getTestData().get("keyword")).getKeywordId());
                } else if (changeObjectDTO.getObjectType().equalsIgnoreCase("AD")) {
                    assertConditionTrue(changeObjectDTO.getParentType().equalsIgnoreCase("AD_GROUP"));
                    assertObjectEqual(changeObjectDTO.getParentId(), ((AdGroup) getTestData().get("adGroup")).getAdGroupId());
                    assertObjectEqual(changeObjectDTO.getObjectId(), ((Ad) getTestData().get("ad")).getAdId());
                } else if (changeObjectDTO.getObjectType().equalsIgnoreCase("NEG_KEYWORD")) {
                    assertConditionTrue(changeObjectDTO.getParentType().equalsIgnoreCase("AD_GROUP"));
                    assertObjectEqual(changeObjectDTO.getParentId(), ((AdGroup) getTestData().get("adGroup")).getAdGroupId());
                    assertObjectEqual(changeObjectDTO.getObjectId(), ((List<NegativeKeyword>) getTestData().get("negativeKeyword")).get(0).getNegKeywordId());
                }
            }
        }
    }

    @Test
    public void testInvalidModifyDateFormat() throws Exception {
        String command = "curl -X POST " +
                "-d '{ \"allowPartialFailure\" : 'true', \"modifiedSince\" : \"032815-192347\"}' " +
                "-H '" + contentType + "' " +
                "-H 'username:'" + userName + "'' " +
                "-H 'requestid:'" + requestId + "'' " +
                "-H 'accountId:'" + ((Account) getTestData().get("account")).getAccountId() + "'' " +
                "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/account/syncbackobjects";

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.SYNC, getAPIResponse(command));
        assertObjectEqual(responseDTO.getTotalCount(), 0);

        for (ResultsDTO result : responseDTO.getResults()) {
            assertObjectEqual(result.getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(result.getError().getMessage(), "Please format date as yyyyMMdd-HHmmss");
            assertObjectEqual(result.getError().getCode(), 400001);
        }
    }

    @Test
    public void testInvalidModifyDate() throws Exception {
        String command = "curl -X POST " +
                "-d '{ \"allowPartialFailure\" : 'true', \"modifiedSince\" : \"test123\"}' " +
                "-H '" + contentType + "' " +
                "-H 'username:'" + userName + "'' " +
                "-H 'requestid:'" + requestId + "'' " +
                "-H 'accountId:'" + ((Account) getTestData().get("account")).getAccountId() + "'' " +
                "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/account/syncbackobjects";

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.SYNC, getAPIResponse(command));
        assertObjectEqual(responseDTO.getTotalCount(), 0);

        for (ResultsDTO result : responseDTO.getResults()) {
            assertObjectEqual(result.getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(result.getError().getMessage(), "Please format date as yyyyMMdd-HHmmss");
            assertObjectEqual(result.getError().getCode(), 400001);
        }
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null || testData.get("campaign") == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = createBudget(account.getAccountId(), budgetAmount, deliveryMethod);
            Campaign campaign = createCampaign(account.getAccountId(), budget.getBudgetId()).get(0);
            AdGroup adGroup = getAdGroupByCampaignId(account.getAccountId(), campaign.getCampaignId()).get(0);
            CampaignGeoCode campaignGeoCode = getCampaignGeoCodeByCampaignId(account.getAccountId(), campaign.getCampaignId()).get(0);
            List<CampaignAdSchedule> campaignAdSchedule = getCampaignAdScheduleByCampaignId(account.getAccountId(), campaign.getCampaignId());
            Keyword keyword = getKeywordByAdGroupId(account.getAccountId(), adGroup.getAdGroupId()).get(0);
            Ad ad = getAdByAdGroupId(account.getAccountId(), adGroup.getAdGroupId()).get(0);
            List<NegativeKeyword> negativeKeyword = getNegativeKeywordByCampaignId(account.getAccountId(), campaign.getCampaignId());
            PhoneAdExtension phoneAdExtension = createPhoneAdExtension(account.getAccountId());
            SiteLinkAdExtension siteLinkAdExtension = createSiteLinkAdExtension(account.getAccountId());
            LocationAdExtension locationAdExtension = createLocationAdExtension(account.getAccountId());

            testData.put("account", account);
            testData.put("budget", budget);
            testData.put("campaign", campaign);
            testData.put("adGroup", adGroup);
            testData.put("campaignGeoCode", campaignGeoCode);
            testData.put("campaignAdSchedule", campaignAdSchedule);
            testData.put("keyword", keyword);
            testData.put("ad", ad);
            testData.put("negativeKeyword", negativeKeyword);
            testData.put("phoneAdExtension", phoneAdExtension);
            testData.put("siteLinkAdExtension", siteLinkAdExtension);
            testData.put("locationAdExtension", locationAdExtension);
        }

        return testData;
    }
}