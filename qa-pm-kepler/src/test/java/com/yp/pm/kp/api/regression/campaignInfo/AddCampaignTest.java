package com.yp.pm.kp.api.regression.campaignInfo;

import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.CampaignInfoResultDTO;
import com.yp.pm.kp.api.dto.ResponseDTO;
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

/**
 * Created by ar2130 on 2/29/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddCampaignTest extends BaseAPITest {
    private static Map<String, Object> testData;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testAddCampaignNoYPID() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        String payload = " [{" +
                "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId().toString() + ", " +
                "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId().toString() + "}] ";
        headers.put("accountId", "0");
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_INFO, getAPIResponse(MethodEnum.POST, "account/campaignInfo/create", headers, payload));

        ResponseDTO responseDTO2 = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_INFO, getAPIResponse(MethodEnum.GET, "account/" + String.valueOf(accountId) + "/campaignInfo/get?campaignId=" + ((Campaign) getTestData().get("campaign")).getCampaignId().toString(), headers, null));

        assertResponseError(responseDTO2);
        CampaignInfoResultDTO campaignInfoResultDTO = responseDTO2.getCampaignInfoResult();
        assertObjectEqual(campaignInfoResultDTO.getAccountId(),((Account) getTestData().get("account")).getAccountId());
        assertObjectEqual(campaignInfoResultDTO.getCampaignId(),((Campaign) getTestData().get("campaign")).getCampaignId());

    }

    @Test
    public void testAddCampaignYPID() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long ypId = System.currentTimeMillis();
        String payload = " [{" +
                "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId().toString() + ", " +
                "\"ypId\" : " + ypId + ", " +
                "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId().toString() + "}] ";
        headers.put("accountId", "0");
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_INFO, getAPIResponse(MethodEnum.POST, "account/campaignInfo/create", headers, payload));

        ResponseDTO responseDTO2 = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_INFO, getAPIResponse(MethodEnum.GET, "account/" + String.valueOf(accountId) + "/campaignInfo/get?campaignId=" + ((Campaign) getTestData().get("campaign")).getCampaignId().toString(), headers, null));

        assertResponseError(responseDTO2);
        CampaignInfoResultDTO campaignInfoResultDTO = responseDTO2.getCampaignInfoResult();
        assertObjectEqual(campaignInfoResultDTO.getAccountId(),((Account) getTestData().get("account")).getAccountId());
        assertObjectEqual(campaignInfoResultDTO.getCampaignId(),((Campaign) getTestData().get("campaign")).getCampaignId());
        assertObjectEqual(campaignInfoResultDTO.getYpId(),ypId);

    }



    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = createBudget(account.getAccountId(), budgetAmount, deliveryMethod);
            Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, deliveryMethod).getBudgetId());

            testData.put("account", account);
            testData.put("budget", budget);
            testData.put("campaign", campaign);
        }

        return testData;
    }
}
