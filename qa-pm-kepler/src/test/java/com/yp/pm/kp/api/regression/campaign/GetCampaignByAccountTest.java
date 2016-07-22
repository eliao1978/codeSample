package com.yp.pm.kp.api.regression.campaign;


import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.CampaignDTO;
import com.yp.pm.kp.enums.model.*;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
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
public class GetCampaignByAccountTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testGetCampaignByAccountId() throws Exception {
        String payload = "{\"allowPartialFailure\" : 'false'}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN, getAPIResponse(MethodEnum.POST, "account/campaign/get", headers, payload));
        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            CampaignDTO campaignDTO = (CampaignDTO) result.getObject();
            assertObjectEqual(campaignDTO.getAccountId(), ((Campaign) getTestData().get("campaign")).getAccountId());
            assertObjectEqual(campaignDTO.getId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
            assertObjectEqual(campaignDTO.getBudgetId(), ((Campaign) getTestData().get("campaign")).getBudgetId());
            assertObjectNotNull(campaignDTO.getName());
            assertObjectEqual(campaignDTO.getType(), CampaignTypeEnum.SEARCH.toString());
            assertObjectEqual(campaignDTO.getStatus(), CampaignStatusEnum.ACTIVE.toString());
            assertObjectEqual(campaignDTO.getNetworkType(), NetworkTypeEnum.SEARCH_NETWORK.toString());
            assertObjectEqual(campaignDTO.getDeviceType(), CampaignDeviceTypeEnum.BOTH.toString());
            assertObjectNotNull(campaignDTO.getMobileBidModifier());
            assertObjectNotNull(campaignDTO.getStartDate());
            assertObjectEqual(campaignDTO.getBiddingStrategyType(), BiddingStrategyTypeEnum.MANUAL_CPC.toString());
            assertObjectEqual(campaignDTO.getAdRotation(), AdRotationEnum.ROTATE_INDEFINITELY.toString());
        }
    }

    @Test
    public void testAllowPartialFailure() throws Exception {
        String payload = "{\"allowPartialFailure\" : 'true'}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN, getAPIResponse(MethodEnum.POST, "account/campaign/get", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            CampaignDTO campaignDTO = (CampaignDTO) result.getObject();
            assertObjectEqual(campaignDTO.getAccountId(), ((Campaign) getTestData().get("campaign")).getAccountId());
            assertObjectEqual(campaignDTO.getId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
            assertObjectEqual(campaignDTO.getBudgetId(), ((Campaign) getTestData().get("campaign")).getBudgetId());
            assertObjectNotNull(campaignDTO.getName());
            assertObjectEqual(campaignDTO.getType(), CampaignTypeEnum.SEARCH.toString());
            assertObjectEqual(campaignDTO.getStatus(), CampaignStatusEnum.ACTIVE.toString());
            assertObjectEqual(campaignDTO.getNetworkType(), NetworkTypeEnum.SEARCH_NETWORK.toString());
            assertObjectEqual(campaignDTO.getDeviceType(), CampaignDeviceTypeEnum.BOTH.toString());
            assertObjectNotNull(campaignDTO.getMobileBidModifier());
            assertObjectNotNull(campaignDTO.getStartDate());
            assertObjectEqual(campaignDTO.getBiddingStrategyType(), BiddingStrategyTypeEnum.MANUAL_CPC.toString());
            assertObjectEqual(campaignDTO.getAdRotation(), AdRotationEnum.ROTATE_INDEFINITELY.toString());
        }
    }

    @Test
    public void testInvalidAccountId() throws Exception {
        String command = "curl -X POST " +
                "-d '{\"allowPartialFailure\" : 'false'}' " +
                "-H '" + contentType + "' " +
                "-H 'username:'" + userName + "'' " +
                "-H 'requestid:'" + requestId + "'' " +
                "-H 'accountId:'" + 12345 + "'' " +
                "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/account/campaign/get";

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN, getAPIResponse(command));
        assertResponseError(responseDTO);
        assertConditionTrue(responseDTO.getTotalCount() == 0);
        assertObjectEqual(responseDTO.getResults().size(), 0);
    }

    @Test
    public void testPagingOutOfRange() throws Exception {
        String payload = "{\"paging\" : { \"startIndex\" : 1, \"numberResults\" : 10 }, \"allowPartialFailure\" : 'false'}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN, getAPIResponse(MethodEnum.POST, "account/campaign/get", headers, payload));
        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);
        assertObjectEqual(responseDTO.getResults().size(), 0);
    }

    @Test
    public void testNegativeNumberResults() throws Exception {
        String payload = "{\"paging\" : { \"startIndex\" : 1, \"numberResults\" : -10 }, \"allowPartialFailure\" : 'false'}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN, getAPIResponse(MethodEnum.POST, "account/campaign/get", headers, payload));
        assertConditionTrue(responseDTO.getResultStatus().getError().getType().equals(ErrorTypeEnum.USER_ERROR));
        assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Invalid paging number of results. It must be greater than or equal to zero");
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = createBudget(account.getAccountId(), budgetAmount, deliveryMethod);
            Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

            testData.put("account", account);
            testData.put("budget", budget);
            testData.put("campaign", campaign);
        }

        return testData;
    }
}