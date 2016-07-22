package com.yp.pm.kp.api.regression.campaignAdSchedule;


import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.CampaignAdScheduleDTO;
import com.yp.pm.kp.enums.model.CampaignAdScheduleStatusEnum;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Campaign;
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
public class GetAdScheduleByCampaignTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private Map<String, String> headers = getRequestHeader();


    @Test
    public void testGetAdScheduleByCampaignId() throws Exception {
        String payload = "{\"campaignIds\" : '[" + ((Campaign) getTestData().get("campaign")).getCampaignId() + "]', " +
                "\"allowPartialFailure\" : 'false'}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_AD_SCHEDULE, getAPIResponse(MethodEnum.POST, "account/campaign/adschedule/get", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 7);

        for (ResultsDTO result : responseDTO.getResults()) {
            CampaignAdScheduleDTO campaignAdScheduleDTO = (CampaignAdScheduleDTO) result.getObject();
            assertObjectEqual(campaignAdScheduleDTO.getAccountId(), ((Account) getTestData().get("account")).getAccountId());
            assertObjectEqual(campaignAdScheduleDTO.getCampaignId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
            assertObjectNotNull(campaignAdScheduleDTO.getId());
            assertObjectNotNull(campaignAdScheduleDTO.getBidModifier());
            assertObjectEqual(campaignAdScheduleDTO.getStatus(), CampaignAdScheduleStatusEnum.ACTIVE.toString());
            assertObjectNotNull(campaignAdScheduleDTO.getStartTime());
            assertObjectNotNull(campaignAdScheduleDTO.getEndTime());
            assertObjectNotNull(campaignAdScheduleDTO.getDayOfWeek());
        }
    }

    @Test
    public void testAllowPartialFailure() throws Exception {
        String payload = "{\"campaignIds\" : '[" + ((Campaign) getTestData().get("campaign")).getCampaignId() + "]', " +
                "\"allowPartialFailure\" : 'true'}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_AD_SCHEDULE, getAPIResponse(MethodEnum.POST, "account/campaign/adschedule/get", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 7);

        for (ResultsDTO result : responseDTO.getResults()) {
            CampaignAdScheduleDTO campaignAdScheduleDTO = (CampaignAdScheduleDTO) result.getObject();
            assertObjectEqual(campaignAdScheduleDTO.getAccountId(), ((Account) getTestData().get("account")).getAccountId());
            assertObjectEqual(campaignAdScheduleDTO.getCampaignId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
            assertObjectNotNull(campaignAdScheduleDTO.getId());
            assertObjectNotNull(campaignAdScheduleDTO.getBidModifier());
            assertObjectEqual(campaignAdScheduleDTO.getStatus(), CampaignAdScheduleStatusEnum.ACTIVE.toString());
            assertObjectNotNull(campaignAdScheduleDTO.getStartTime());
            assertObjectNotNull(campaignAdScheduleDTO.getEndTime());
            assertObjectNotNull(campaignAdScheduleDTO.getDayOfWeek());
        }
    }

    @Test
    public void testInvalidCampaignId() throws Exception {
        String payload = "{\"campaignIds\" : '[" + 12345 + "]', " +
                "\"allowPartialFailure\" : 'false'}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_AD_SCHEDULE, getAPIResponse(MethodEnum.POST, "account/campaign/adschedule/get", headers, payload));

        assertResponseError(responseDTO);
        assertConditionTrue(responseDTO.getTotalCount() == 0);
        assertObjectEqual(responseDTO.getResults().size(), 0);
    }

    @Test
    public void testMissingCampaignId() throws Exception {
        String payload = "{\"campaignIds\" : '[]', " +
                "\"allowPartialFailure\" : 'false'}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_AD_SCHEDULE, getAPIResponse(MethodEnum.POST, "account/campaign/adschedule/get", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 7);

        for (ResultsDTO result : responseDTO.getResults()) {
            CampaignAdScheduleDTO campaignAdScheduleDTO = (CampaignAdScheduleDTO) result.getObject();
            assertObjectEqual(campaignAdScheduleDTO.getAccountId(), ((Account) getTestData().get("account")).getAccountId());
            assertObjectEqual(campaignAdScheduleDTO.getCampaignId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
            assertObjectNotNull(campaignAdScheduleDTO.getId());
            assertObjectNotNull(campaignAdScheduleDTO.getBidModifier());
            assertObjectEqual(campaignAdScheduleDTO.getStatus(), CampaignAdScheduleStatusEnum.ACTIVE.toString());
            assertObjectNotNull(campaignAdScheduleDTO.getStartTime());
            assertObjectNotNull(campaignAdScheduleDTO.getEndTime());
            assertObjectNotNull(campaignAdScheduleDTO.getDayOfWeek());
        }
    }

    @Test
    public void testPagingOutOfRange() throws Exception {
        String payload = "{\"campaignIds\" : '[" + ((Campaign) getTestData().get("campaign")).getCampaignId() + "]', " +
                "\"paging\" : { \"startIndex\" : 7, \"numberResults\" : 10}, " +
                "\"allowPartialFailure\" : 'false'}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_AD_SCHEDULE, getAPIResponse(MethodEnum.POST, "account/campaign/adschedule/get", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 7);
        assertObjectEqual(responseDTO.getResults().size(), 0);
    }

    @Test
    public void testNegativeNumberResults() throws Exception {
        String payload = "{\"campaignIds\" : '[" + ((Campaign) getTestData().get("campaign")).getCampaignId() + "]', " +
                "\"paging\" : { \"startIndex\" : 0, \"numberResults\" : -10}, " +
                "\"allowPartialFailure\" : 'false'}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_AD_SCHEDULE, getAPIResponse(MethodEnum.POST, "account/campaign/adschedule/get", headers, payload));

        assertObjectEqual(responseDTO.getTotalCount(), 0);
        assertConditionTrue(responseDTO.getResultStatus().getError().getType().equals(ErrorTypeEnum.USER_ERROR));
        assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Invalid paging number of results. It must be greater than or equal to zero");
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
            createCampaignAdSchedule(account.getAccountId(), campaign.getCampaignId());

            testData.put("account", account);
            testData.put("campaign", campaign);
        }

        return testData;
    }
}