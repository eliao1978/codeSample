package com.yp.pm.kp.api.regression.campaignAdSchedule;

import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.CampaignAdScheduleDTO;
import com.yp.pm.kp.enums.model.CampaignAdScheduleStatusEnum;
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
public class GetAdScheduleTest extends BaseAPITest {
    private Map<String, String> headers = getRequestHeader();

    private static Map<String, Object> testData;

    @Test
    public void testGetAdScheduleById() throws Exception {
        String campaignAdScheduleIDs = "";
        List<CampaignAdSchedule> campaignAdScheduleList = (List<CampaignAdSchedule>) getTestData().get("campaignAdScheduleList");

        for (CampaignAdSchedule schedule : campaignAdScheduleList) {
            campaignAdScheduleIDs += schedule.getAdScheduleId().toString().concat(" ");
        }
        String formattedAdScheduleIDs = campaignAdScheduleIDs.trim().replace(" ", ",");

        String payload = "{\"adScheduleIds\" : '[" + formattedAdScheduleIDs + "]', " +
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
        List<CampaignAdSchedule> campaignAdScheduleList = (List<CampaignAdSchedule>) getTestData().get("campaignAdScheduleList");

        String campaignAdScheduleIDs = "";
        for (CampaignAdSchedule schedule : campaignAdScheduleList) {
            campaignAdScheduleIDs += schedule.getAdScheduleId().toString().concat(" ");
        }
        String formattedAdScheduleIDs = campaignAdScheduleIDs.trim().replace(" ", ",");

        String payload = "{\"adScheduleIds\" : '[" + formattedAdScheduleIDs + "]', " +
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
    public void testInvalidAdScheduleId() throws Exception {
        String payload = "{\"adScheduleIds\" : '[" + 12345 + "]', " +
                "\"allowPartialFailure\" : 'false'}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_AD_SCHEDULE, getAPIResponse(MethodEnum.POST, "account/campaign/adschedule/get", headers, payload));
        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 0);

        for (ResultsDTO result : responseDTO.getResults()) {
            assertObjectEqual(((CampaignAdScheduleDTO) result.getObject()).getId(), -1);
        }
    }

    @Test
    public void testMissingAdScheduleId() throws Exception {
        String payload = "{\"adScheduleIds\" : '[]', " +
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

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
            List<CampaignAdSchedule> campaignAdScheduleList = createCampaignAdSchedule(account.getAccountId(), campaign.getCampaignId());

            testData.put("account", account);
            testData.put("campaign", campaign);
            testData.put("campaignAdScheduleList", campaignAdScheduleList);

        }

        return testData;
    }
}
