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
import com.yp.util.DBUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddAdScheduleTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testAddAdSchedule() throws Exception {
        long campaignId = ((Campaign) getTestData().get("campaign")).getCampaignId();

        String payload = "{\"adSchedules\" : [{" +
                "\"campaignId\" : " + campaignId + ", " +
                "\"bidModifier\" : 1, " +
                "\"dayOfWeek\" : \"SUNDAY\", " +
                "\"startTime\" : \"0000\", " +
                "\"endTime\" : \"2359\"}," +
                "{\"campaignId\" : " + campaignId + ", " +
                "\"bidModifier\" : 2, " +
                "\"dayOfWeek\" : \"MONDAY\", " +
                "\"startTime\" : \"0900\", " +
                "\"endTime\" : \"2200\"}," +
                "{\"campaignId\" : " + campaignId + ", " +
                "\"bidModifier\" : 3, " +
                "\"dayOfWeek\" : \"TUESDAY\", " +
                "\"startTime\" : \"1000\", " +
                "\"endTime\" : \"2300\"}," +
                "{\"campaignId\" : " + campaignId + ", " +
                "\"bidModifier\" : 3, " +
                "\"dayOfWeek\" : \"WEDNESDAY\", " +
                "\"startTime\" : \"1000\", " +
                "\"endTime\" : \"2300\"}," +
                "{\"campaignId\" : " + campaignId + ", " +
                "\"bidModifier\" : 3, " +
                "\"dayOfWeek\" : \"THURSDAY\", " +
                "\"startTime\" : \"1000\", " +
                "\"endTime\" : \"2300\"}," +
                "{\"campaignId\" : " + campaignId + ", " +
                "\"bidModifier\" : 3, " +
                "\"dayOfWeek\" : \"FRIDAY\", " +
                "\"startTime\" : \"1000\", " +
                "\"endTime\" : \"2300\"}," +
                "{\"campaignId\" : " + campaignId + ", " +
                "\"bidModifier\" : 3, " +
                "\"dayOfWeek\" : \"SATURDAY\", " +
                "\"startTime\" : \"1000\", " +
                "\"endTime\" : \"2300\"}" +
                "]," +
                "\"allowPartialFailure\" : \"true\"}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_AD_SCHEDULE, getAPIResponse(MethodEnum.POST, "account/campaign/adschedule/create", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 7);

        int i = 0;
        for (ResultsDTO result : responseDTO.getResults()) {
            assertConditionTrue(result.getIndex() == i);
            i += 1;

            CampaignAdScheduleDTO campaignAdScheduleDTO = (CampaignAdScheduleDTO) result.getObject();
            assertObjectEqual(campaignAdScheduleDTO.getId(), getCampaignAdSchedule(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId(), campaignAdScheduleDTO.getId()).getAdScheduleId());
        }
    }

    @Test
    public void testAllowPartialFailure() throws Exception {
        Campaign campaign = createCampaignOnly(((Account) getTestData().get("account")).getAccountId(), createBudget(((Account) getTestData().get("account")).getAccountId(), budgetAmount, deliveryMethod).getBudgetId());

        String payload = "{\"adSchedules\" : [{" +
                "\"campaignId\" : " + campaign.getCampaignId() + ", " +
                "\"bidModifier\" : 0.1, " +
                "\"dayOfWeek\" : \"MONDAY\", " +
                "\"startTime\" : \"0900\", " +
                "\"endTime\" : \"2100\"}], " +
                "\"allowPartialFailure\" : \"true\"}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_AD_SCHEDULE, getAPIResponse(MethodEnum.POST, "account/campaign/adschedule/create", headers, payload));
        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            CampaignAdScheduleDTO campaignAdScheduleDTO = (CampaignAdScheduleDTO) result.getObject();
            assertObjectEqual(campaignAdScheduleDTO.getId(), getCampaignAdSchedule(((Account) getTestData().get("account")).getAccountId(), campaign.getCampaignId(), campaignAdScheduleDTO.getId()).getAdScheduleId());
        }
    }

    @Test
    public void testInvalidBidModifier() throws Exception {
        String payload = "{\"adSchedules\" : [{" +
                "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"bidModifier\" : 11, " +
                "\"dayOfWeek\" : \"MONDAY\", " +
                "\"startTime\" : \"0900\", " +
                "\"endTime\" : \"2100\"}], " +
                "\"allowPartialFailure\" : \"false\"}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_AD_SCHEDULE, getAPIResponse(MethodEnum.POST, "account/campaign/adschedule/create", headers, payload));
        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Ad schedule bid adjustment should be between -90% (0.1) and 900% (10.0)");
    }


    @Test
    public void testAddAdScheduleCustomLimit() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        try {
            DBUtil.update("INSERT INTO PM_CUSTOM_LIMIT_VALIDATION VALUES('ad_schedule', 6, ".concat(account.getAccountId().toString()).concat(", 'account', '").concat(userName) + "',SYSDATE,'" + userName + "',SYSDATE)");
            Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
            String[] days = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
            JSONObject newDay = new JSONObject();
            JSONArray newDays = new JSONArray();
            for (String y : days) {
                JSONObject tempDay = new JSONObject();
                tempDay.put("day", y);
                tempDay.put("startTime", "0900");
                tempDay.put("endTime", "2100");
                newDays.add(tempDay);
            }
            newDay.put("days", newDays);

            createCampaignAdSchedule(account.getAccountId(), campaign.getCampaignId(), newDay);

            String command = "curl -X POST " +
                    "-d '{\"adSchedules\" : [{" +
                    "\"campaignId\" : " + campaign.getCampaignId() + ", " +
                    "\"bidModifier\" : 1, " +
                    "\"dayOfWeek\" : \"SUNDAY\", " +
                    "\"startTime\" : \"0900\", " +
                    "\"endTime\" : \"2100\"}], " +
                    "\"allowPartialFailure\" : \"false\"}' " +
                    "-H '" + contentType + "' " +
                    "-H 'username:'" + userName + "'' " +
                    "-H 'requestid:'" + requestId + "'' " +
                    "-H 'accountId:'" + account.getAccountId() + "'' " +
                    "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/account/campaign/adschedule/create";

            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_AD_SCHEDULE, getAPIResponse(command));

            assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Exceeds maximum number of ad schedules in campaign");
        } finally {
            DBUtil.update("DELETE FROM PM_CUSTOM_LIMIT_VALIDATION WHERE PARENT_ID = ".concat(account.getAccountId().toString()));
        }
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, deliveryMethod).getBudgetId());

            testData.put("account", account);
            testData.put("campaign", campaign);
        }

        return testData;
    }
}