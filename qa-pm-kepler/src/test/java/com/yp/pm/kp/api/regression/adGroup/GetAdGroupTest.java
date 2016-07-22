package com.yp.pm.kp.api.regression.adGroup;

import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.AdGroupDTO;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.AdGroup;
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
public class GetAdGroupTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testGetAdGroupById() throws Exception {
        String payload = "{\"adGroupIds\" : '[" + ((AdGroup) getTestData().get("adGroup")).getAdGroupId() + "]', \"allowPartialFailure\" : 'false'}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD_GROUP, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/get", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            AdGroupDTO adGroupDTO = (AdGroupDTO) result.getObject();
            assertObjectEqual(adGroupDTO.getAccountId(), ((Account) getTestData().get("account")).getAccountId());
            assertObjectEqual(adGroupDTO.getCampaignId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
            assertObjectEqual(adGroupDTO.getId(), ((AdGroup) getTestData().get("adGroup")).getAdGroupId());
            assertObjectEqual(adGroupDTO.getName(), ((AdGroup) getTestData().get("adGroup")).getAdGroupName());
            assertObjectEqual(adGroupDTO.getStatus(), ((AdGroup) getTestData().get("adGroup")).getAdGroupStatus().toString());
            assertObjectEqual(adGroupDTO.getMaxCpc(), ((AdGroup) getTestData().get("adGroup")).getMaxCpc().getMicroAmount().toMicroDollar());
        }
    }

    @Test
    public void testAllowPartialFailure() throws Exception {
        String payload = "{\"adGroupIds\" : '[" + ((AdGroup) getTestData().get("adGroup")).getAdGroupId() + "]', \"allowPartialFailure\" : 'true'}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD_GROUP, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/get", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            AdGroupDTO adGroupDTO = (AdGroupDTO) result.getObject();
            assertObjectEqual(adGroupDTO.getAccountId(), ((Account) getTestData().get("account")).getAccountId());
            assertObjectEqual(adGroupDTO.getCampaignId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
            assertObjectEqual(adGroupDTO.getId(), ((AdGroup) getTestData().get("adGroup")).getAdGroupId());
            assertObjectEqual(adGroupDTO.getName(), ((AdGroup) getTestData().get("adGroup")).getAdGroupName());
            assertObjectEqual(adGroupDTO.getStatus(), ((AdGroup) getTestData().get("adGroup")).getAdGroupStatus().toString());
            assertObjectEqual(adGroupDTO.getMaxCpc(), ((AdGroup) getTestData().get("adGroup")).getMaxCpc().getMicroAmount().toMicroDollar());
        }
    }

    @Test
    public void testInvalidAdGroupId() throws Exception {
        String payload = "{\"adGroupIds\" : '[" + 12345 + "]', \"allowPartialFailure\" : 'false'}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD_GROUP, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/get", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 0);

        for (ResultsDTO result : responseDTO.getResults()) {
            assertObjectEqual(((AdGroupDTO) result.getObject()).getId(), -1);
        }
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = createBudget(account.getAccountId(), budgetAmount, deliveryMethod);
            Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
            AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());

            testData.put("account", account);
            testData.put("campaign", campaign);
            testData.put("adGroup", adGroup);
        }

        return testData;
    }
}