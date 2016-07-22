package com.yp.pm.kp.api.regression.negativeKeyword;


import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.NegativeKeywordDTO;
import com.yp.pm.kp.enums.model.NegativeKeywordStatusEnum;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.AdGroup;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.model.domain.NegativeKeyword;
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
public class DeleteNegativeKeywordTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testDeleteNegativeKeyword() throws Exception {
        NegativeKeyword negKeyword = createNegativeKeyword(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId(), ((AdGroup) getTestData().get("adGroup")).getAdGroupId());

        String payload = "{\"negativeKeywordIds\" : '[" + negKeyword.getNegKeywordId() + "]', " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.DELETE, "account/negative/delete", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        NegativeKeyword deletedKeyword = getNegativeKeywordById(((Account) getTestData().get("account")).getAccountId(), negKeyword.getNegKeywordId());
        assertObjectEqual(deletedKeyword.getStatus().toString(), NegativeKeywordStatusEnum.DELETED.toString());
    }

    @Test
    public void testAllowPartialFailure() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
        AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());
        NegativeKeyword negKeyword = createNegativeKeyword(account.getAccountId(), campaign.getCampaignId(), adGroup.getAdGroupId());

        String payload = "{\"negativeKeywordIds\" : '[" + negKeyword.getNegKeywordId() + "]', " +
                "\"allowPartialFailure\" : true}";
        headers.put("accountId", account.getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.DELETE, "account/negative/delete", headers, payload));


        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        NegativeKeyword deletedKeyword = getNegativeKeywordById(account.getAccountId(), negKeyword.getNegKeywordId());
        assertObjectEqual(deletedKeyword.getStatus().toString(), NegativeKeywordStatusEnum.DELETED.toString());
    }

    @Test
    public void testInvalidNegativeKeyword() throws Exception {
        NegativeKeyword negKeyword = createNegativeKeyword(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId(), ((AdGroup) getTestData().get("adGroup")).getAdGroupId());

        String payload = "{\"negativeKeywordIds\" : '[" + 12345 + "]', " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.DELETE, "account/negative/delete", headers, payload));


        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 0);

        for (ResultsDTO result : responseDTO.getResults()) {
            assertObjectEqual(((NegativeKeywordDTO) result.getObject()).getId(), -1);
        }

        NegativeKeyword deletedKeyword = getNegativeKeywordById(((Account) getTestData().get("account")).getAccountId(), negKeyword.getNegKeywordId());
        assertObjectEqual(deletedKeyword.getStatus().toString(), NegativeKeywordStatusEnum.ACTIVE.toString());
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
            AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());

            testData.put("account", account);
            testData.put("campaign", campaign);
            testData.put("adGroup", adGroup);
        }

        return testData;
    }
}