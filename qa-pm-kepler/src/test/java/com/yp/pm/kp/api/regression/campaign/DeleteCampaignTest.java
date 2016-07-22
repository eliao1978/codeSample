package com.yp.pm.kp.api.regression.campaign;


import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.CampaignDTO;
import com.yp.pm.kp.enums.model.BudgetStatusEnum;
import com.yp.pm.kp.enums.model.CampaignStatusEnum;
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
public class DeleteCampaignTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testDeleteCampaign() throws Exception {
        Campaign campaign = createCampaignOnly(((Account) getTestData().get("account")).getAccountId(), ((Budget) getTestData().get("budget")).getBudgetId());

        String payload = "{\"campaignIds\" : '[" + campaign.getCampaignId() + "]', " +
                "\"allowPartialFailure\" : 'false'}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN, getAPIResponse(MethodEnum.DELETE, "account/campaign/delete", headers, payload));
        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        Campaign deletedCampaign = getCampaignById(((Account) getTestData().get("account")).getAccountId(), campaign.getCampaignId());
        assertObjectEqual(deletedCampaign.getCampaignStatus().toString(), BudgetStatusEnum.DELETED.toString());
    }

    @Test
    public void testAllowPartialFailure() throws Exception {
        Campaign campaign = createCampaignOnly(((Account) getTestData().get("account")).getAccountId(), ((Budget) getTestData().get("budget")).getBudgetId());

        String payload = "{\"campaignIds\" : '[" + campaign.getCampaignId() + "]', " +
                "\"allowPartialFailure\" : 'true'}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN, getAPIResponse(MethodEnum.DELETE, "account/campaign/delete", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        Campaign deletedCampaign = getCampaignById(((Account) getTestData().get("account")).getAccountId(), campaign.getCampaignId());
        assertObjectEqual(deletedCampaign.getCampaignStatus().toString(), BudgetStatusEnum.DELETED.toString());
    }

    @Test
    public void testInvalidCampaignId() throws Exception {
        Campaign campaign = createCampaignOnly(((Account) getTestData().get("account")).getAccountId(), ((Budget) getTestData().get("budget")).getBudgetId());

        String payload = "{\"campaignIds\" : '[" + 12345 + "]', " +
                "\"allowPartialFailure\" : 'false'}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN, getAPIResponse(MethodEnum.DELETE, "account/campaign/delete", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 0);

        for (ResultsDTO result : responseDTO.getResults()) {
            assertObjectEqual(((CampaignDTO) result.getObject()).getId(), -1);
        }

        Campaign deletedCampaign = getCampaignById(((Account) getTestData().get("account")).getAccountId(), campaign.getCampaignId());
        assertObjectEqual(deletedCampaign.getCampaignStatus().toString(), CampaignStatusEnum.ACTIVE.toString());
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = createBudget(account.getAccountId(), budgetAmount, deliveryMethod);
            testData.put("account", account);
            testData.put("budget", budget);
        }

        return testData;
    }
}