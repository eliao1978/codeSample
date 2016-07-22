package com.yp.pm.kp.api.regression.campaignAdSchedule;

import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.CampaignAdScheduleDTO;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.model.domain.CampaignAdSchedule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UpdateAdScheduleTest extends BaseAPITest {
    private Map<String, String> headers = getRequestHeader();

    private static Map<String, Object> testData;
    private static Map<String, Object> testData2;

    public String generateAdScheduleString(long campaignID, long adScheduleID, String stat) {
        return " {" +
                "\"campaignId\" : " + campaignID + ", " +
                "\"adScheduleId\" : " + adScheduleID + ", " +
                "\"adScheduleStatus\" : \"" + stat + "\", " +
                "\"bidModifier\" : 2, " +
                "\"startTime\" : \"0900\", " +
                "\"endTime\" : \"2100\"} ";
    }

    @Test
    public void testUpdateAdSchedule() throws Exception {
        List<CampaignAdSchedule> scheduleList = ((List<CampaignAdSchedule>) getTestData().get("adSchedule"));
        for (CampaignAdSchedule schedule : scheduleList) {
            String payload = "{\"adSchedules\" : [{" +
                    "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                    "\"adScheduleId\" : " + schedule.getAdScheduleId() + ", " +
                    "\"adScheduleStatus\" : \"ACTIVE\", " +
                    "\"bidModifier\" : 2, " +
                    "\"startTime\" : \"0900\", " +
                    "\"endTime\" : \"2100\"}], " +
                    "\"allowPartialFailure\" : \"false\"}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_AD_SCHEDULE, getAPIResponse(MethodEnum.PUT, "account/campaign/adschedule/update", headers, payload));
            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 1);

            for (ResultsDTO result : responseDTO.getResults()) {
                CampaignAdScheduleDTO campaignAdScheduleDTO = (CampaignAdScheduleDTO) result.getObject();
                CampaignAdSchedule updatedCampaignAdSchedule = getCampaignAdSchedule(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId(), schedule.getAdScheduleId());
                assertObjectEqual(campaignAdScheduleDTO.getId(), updatedCampaignAdSchedule.getAdScheduleId());
                assertObjectEqual(updatedCampaignAdSchedule.getAdScheduleStatus().toString(), "ACTIVE");
                assertObjectEqual(updatedCampaignAdSchedule.getBidModifier(), 2.0);
            }
        }
    }

    @Test
    public void testAllowPartialFailure() throws Exception {
        List<CampaignAdSchedule> scheduleList = ((List<CampaignAdSchedule>) getTestData().get("adSchedule"));
        for (CampaignAdSchedule schedule : scheduleList) {
            String payload = "{\"adSchedules\" : [{" +
                    "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                    "\"adScheduleId\" : " + schedule.getAdScheduleId() + ", " +
                    "\"adScheduleStatus\" : \"ACTIVE\", " +
                    "\"bidModifier\" : 2, " +
                    "\"startTime\" : \"0900\", " +
                    "\"endTime\" : \"2100\"}], " +
                    "\"allowPartialFailure\" : \"true\"}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_AD_SCHEDULE, getAPIResponse(MethodEnum.PUT, "account/campaign/adschedule/update", headers, payload));

            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 1);

            for (ResultsDTO result : responseDTO.getResults()) {
                CampaignAdScheduleDTO campaignAdScheduleDTO = (CampaignAdScheduleDTO) result.getObject();
                CampaignAdSchedule updatedCampaignAdSchedule = getCampaignAdSchedule(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId(), schedule.getAdScheduleId());
                assertObjectEqual(campaignAdScheduleDTO.getId(), updatedCampaignAdSchedule.getAdScheduleId());
                assertObjectEqual(updatedCampaignAdSchedule.getAdScheduleStatus().toString(), "ACTIVE");
                assertObjectEqual(updatedCampaignAdSchedule.getBidModifier(), 2.0);
            }
        }
    }

    @Test
    public void testIgnoreUpdateAdSchedule() throws Exception {
        List<CampaignAdSchedule> scheduleList2 = ((List<CampaignAdSchedule>) getTestData2().get("adSchedule"));
        for (CampaignAdSchedule schedule : scheduleList2) {
            String payload = "{\"adSchedules\" : [{" +
                    "\"campaignId\" : " + ((Campaign) getTestData2().get("campaign")).getCampaignId() + ", " +
                    "\"adScheduleId\" :  -1, " +
                    "\"adScheduleStatus\" : \"ACTIVE\", " +
                    "\"bidModifier\" : 2, " +
                    "\"startTime\" : \"0900\", " +
                    "\"endTime\" : \"2100\"}], " +
                    "\"allowPartialFailure\" : \"false\"}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_AD_SCHEDULE, getAPIResponse(MethodEnum.PUT, "account/campaign/adschedule/update", headers, payload));

            assertResponseError(responseDTO);

            for (ResultsDTO result : responseDTO.getResults()) {
                CampaignAdScheduleDTO campaignAdScheduleDTO = (CampaignAdScheduleDTO) result.getObject();
                CampaignAdSchedule updatedCampaignAdSchedule2 = getCampaignAdSchedule(((Account) getTestData2().get("account")).getAccountId(), ((Campaign) getTestData2().get("campaign")).getCampaignId(), schedule.getAdScheduleId());
                assertObjectEqual(campaignAdScheduleDTO.getId(), -1);
                assertObjectEqual(updatedCampaignAdSchedule2.getAdScheduleStatus().toString(), "ACTIVE");
                assertObjectEqual(updatedCampaignAdSchedule2.getBidModifier(), 1.0);
            }
        }
    }


    @Test
    public void testObjectProblemType() throws Exception {
        long accountId = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles").getAccountId();
        Campaign campaign = createCampaignOnly(accountId, createBudget(accountId, budgetAmount, deliveryMethod).getBudgetId());
        List<CampaignAdSchedule> scheduleList = createCampaignAdSchedule(accountId, campaign.getCampaignId());

        String adScheduleString1 = generateAdScheduleString(campaign.getCampaignId(), scheduleList.get(0).getAdScheduleId(), "ACTIVE");
        String adScheduleString2 = generateAdScheduleString(campaign.getCampaignId(), scheduleList.get(1).getAdScheduleId(), "DELETED");
        String adScheduleString3 = generateAdScheduleString(campaign.getCampaignId(), scheduleList.get(2).getAdScheduleId(), "ACTIVE");

        String command = "curl -X PUT " +
                "-d '{\"adSchedules\" : [" + adScheduleString1 + "," + adScheduleString2 + "," + adScheduleString3 + "], " +
                "\"allowPartialFailure\" : \"false\"}' " +
                "-H '" + contentType + "' " +
                "-H 'username:'" + userName + "'' " +
                "-H 'requestid:'" + requestId + "'' " +
                "-H 'accountId:'" + accountId + "'' " +
                "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/account/campaign/adschedule/update";

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_AD_SCHEDULE, getAPIResponse(command));

        for (ResultsDTO result : responseDTO.getResults()) {
            CampaignAdScheduleDTO campaignAdScheduleDTO = (CampaignAdScheduleDTO) result.getObject();
            if (result.getError().getMessage() != null) {
                assertObjectEqual(campaignAdScheduleDTO.getId(), -3);
            } else {
                assertObjectEqual(campaignAdScheduleDTO.getId(), -2);
            }
        }

        assertObjectEqual(getCampaignAdSchedule(accountId, campaign.getCampaignId(), scheduleList.get(0).getAdScheduleId()).getAdScheduleStatus().toString(), "ACTIVE");
        assertObjectEqual(getCampaignAdSchedule(accountId, campaign.getCampaignId(), scheduleList.get(1).getAdScheduleId()).getAdScheduleStatus().toString(), "ACTIVE");
        assertObjectEqual(getCampaignAdSchedule(accountId, campaign.getCampaignId(), scheduleList.get(2).getAdScheduleId()).getAdScheduleStatus().toString(), "ACTIVE");
    }

    @Test
    public void testInvalidAdScheduleId() throws Exception {
        List<CampaignAdSchedule> scheduleList = ((List<CampaignAdSchedule>) getTestData().get("adSchedule"));
        for (CampaignAdSchedule ignored : scheduleList) {
            String payload = "{\"adSchedules\" : [{" +
                    "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                    "\"adScheduleId\" : \"12345\", " +
                    "\"adScheduleStatus\" : \"ACTIVE\", " +
                    "\"bidModifier\" : 2, " +
                    "\"startTime\" : \"0900\", " +
                    "\"endTime\" : \"2100\"}], " +
                    "\"allowPartialFailure\" : \"false\"}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_AD_SCHEDULE, getAPIResponse(MethodEnum.PUT, "account/campaign/adschedule/update", headers, payload));

            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 0);

            for (ResultsDTO result : responseDTO.getResults()) {
                assertObjectEqual(((CampaignAdScheduleDTO) result.getObject()).getId(), -1);
            }
        }
    }

    @Test
    public void testMissingAdScheduleId() throws Exception {
        List<CampaignAdSchedule> scheduleList = ((List<CampaignAdSchedule>) getTestData().get("adSchedule"));
        for (CampaignAdSchedule ignored : scheduleList) {
            String payload = "{\"adSchedules\" : [{" +
                    "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                    "\"adScheduleId\" : \"\", " +
                    "\"adScheduleStatus\" : \"ACTIVE\", " +
                    "\"bidModifier\" : 2, " +
                    "\"startTime\" : \"0900\", " +
                    "\"endTime\" : \"2100\"}], " +
                    "\"allowPartialFailure\" : \"false\"}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_AD_SCHEDULE, getAPIResponse(MethodEnum.PUT, "account/campaign/adschedule/update", headers, payload));
            assertObjectEqual(responseDTO.getTotalCount(), 0);
            assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Ad schedule id can not be null");
        }
    }

    @Test
    public void testMissingAdScheduleIdAllowPartialFailureFalse() throws Exception {
        List<CampaignAdSchedule> scheduleList = ((List<CampaignAdSchedule>) getTestData().get("adSchedule"));
        for (CampaignAdSchedule ignored : scheduleList) {
            String payload = "{\"adSchedules\" : [{" +
                    "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                    "\"adScheduleId\" : \"\", " +
                    "\"adScheduleStatus\" : \"ACTIVE\", " +
                    "\"bidModifier\" : 2, " +
                    "\"startTime\" : \"0900\", " +
                    "\"endTime\" : \"2100\"}], " +
                    "\"allowPartialFailure\" : \"false\"}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_AD_SCHEDULE, getAPIResponse(MethodEnum.PUT, "account/campaign/adschedule/update", headers, payload));
            assertObjectEqual(responseDTO.getTotalCount(), 0);
            assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Ad schedule id can not be null");
        }
    }

    @Test
    public void testInvalidCampaignId() throws Exception {
        List<CampaignAdSchedule> scheduleList = ((List<CampaignAdSchedule>) getTestData().get("adSchedule"));
        for (CampaignAdSchedule schedule : scheduleList) {
            String payload = "{\"adSchedules\" : [{" +
                    "\"campaignId\" : \"12345\", " +
                    "\"adScheduleId\" : " + schedule.getAdScheduleId() + ", " +
                    "\"adScheduleStatus\" : \"ACTIVE\", " +
                    "\"bidModifier\" : 2, " +
                    "\"startTime\" : \"0900\", " +
                    "\"endTime\" : \"2100\"}], " +
                    "\"allowPartialFailure\" : \"false\"}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_AD_SCHEDULE, getAPIResponse(MethodEnum.PUT, "account/campaign/adschedule/update", headers, payload));

            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 0);

            for (ResultsDTO result : responseDTO.getResults()) {
                assertObjectEqual(((CampaignAdScheduleDTO) result.getObject()).getId(), -1);
            }
        }
    }

    @Test
    public void testMissingCampaignId() throws Exception {
        List<CampaignAdSchedule> scheduleList = ((List<CampaignAdSchedule>) getTestData().get("adSchedule"));
        for (CampaignAdSchedule schedule : scheduleList) {
            String payload = "{\"adSchedules\" : [{" +
                    "\"campaignId\" : \"\", " +
                    "\"adScheduleId\" : " + schedule.getAdScheduleId() + ", " +
                    "\"adScheduleStatus\" : \"ACTIVE\", " +
                    "\"bidModifier\" : 2, " +
                    "\"startTime\" : \"0900\", " +
                    "\"endTime\" : \"2100\"}], " +
                    "\"allowPartialFailure\" : \"false\"}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_AD_SCHEDULE, getAPIResponse(MethodEnum.PUT, "account/campaign/adschedule/update", headers, payload));
            assertObjectEqual(responseDTO.getTotalCount(), 0);
            assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Campaign id can not be null");
        }
    }


    @Test
    public void testMissingCampaignIdAllowPartialFailure() throws Exception {
        List<CampaignAdSchedule> scheduleList = ((List<CampaignAdSchedule>) getTestData().get("adSchedule"));
        for (CampaignAdSchedule schedule : scheduleList) {
            String payload = "{\"adSchedules\" : [{" +
                    "\"campaignId\" : \"\", " +
                    "\"adScheduleId\" : " + schedule.getAdScheduleId() + ", " +
                    "\"adScheduleStatus\" : \"ACTIVE\", " +
                    "\"bidModifier\" : 2, " +
                    "\"startTime\" : \"0900\", " +
                    "\"endTime\" : \"2100\"}], " +
                    "\"allowPartialFailure\" : \"true\"}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_AD_SCHEDULE, getAPIResponse(MethodEnum.PUT, "account/campaign/adschedule/update", headers, payload));
            assertObjectEqual(responseDTO.getTotalCount(), 0);
            assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Campaign id can not be null");
        }
    }

    @Test
    public void testInvalidBidModifier() throws Exception {
        List<CampaignAdSchedule> scheduleList = ((List<CampaignAdSchedule>) getTestData().get("adSchedule"));
        for (CampaignAdSchedule schedule : scheduleList) {
            String payload = "{\"adSchedules\" : [{" +
                    "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                    "\"adScheduleId\" : " + schedule.getAdScheduleId() + ", " +
                    "\"adScheduleStatus\" : \"ACTIVE\", " +
                    "\"bidModifier\" : 11, " +
                    "\"startTime\" : \"0900\", " +
                    "\"endTime\" : \"2100\"}], " +
                    "\"allowPartialFailure\" : \"false\"}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_AD_SCHEDULE, getAPIResponse(MethodEnum.PUT, "account/campaign/adschedule/update", headers, payload));
            assertObjectEqual(responseDTO.getTotalCount(), 0);
            assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Ad schedule bid adjustment should be between -90% (0.1) and 900% (10.0)");
        }
    }

    private Map<String, Object> getTestData2() throws Exception {
        if (testData2 == null) {
            testData2 = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
            List<CampaignAdSchedule> campaignAdScheduleList = createCampaignAdSchedule(account.getAccountId(), campaign.getCampaignId());

            testData2.put("account", account);
            testData2.put("adSchedule", campaignAdScheduleList);
            testData2.put("campaign", campaign);
        }
        return testData2;
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
            List<CampaignAdSchedule> campaignAdScheduleList = createCampaignAdSchedule(account.getAccountId(), campaign.getCampaignId());

            testData.put("account", account);
            testData.put("adSchedule", campaignAdScheduleList);
            testData.put("campaign", campaign);
        }

        return testData;
    }
}
