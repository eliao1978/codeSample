package com.yp.pm.kp.api.regression.negativeKeyword;


import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.NegativeKeywordDTO;
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
public class GetNegativeKeywordTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testGetNegativeKeywordById() throws Exception {
        String payload = "{\"negativeKeywordIds\" : '[" + ((NegativeKeyword) getTestData().get("negKeyword")).getNegKeywordId() + "]', " +
                "\"allowPartialFailure\" : 'false'}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.POST, "account/negative/get", headers, payload));


        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            NegativeKeywordDTO negativeKeywordDTO = (NegativeKeywordDTO) result.getObject();
            assertObjectEqual(negativeKeywordDTO.getAccountId(), ((NegativeKeyword) getTestData().get("negKeyword")).getAccountId());
            assertObjectEqual(negativeKeywordDTO.getAdGroupId(), ((NegativeKeyword) getTestData().get("negKeyword")).getAdGroupId());
            assertObjectEqual(negativeKeywordDTO.getCampaignId(), ((NegativeKeyword) getTestData().get("negKeyword")).getCampaignId());
            assertObjectEqual(negativeKeywordDTO.getId(), ((NegativeKeyword) getTestData().get("negKeyword")).getNegKeywordId());
            assertObjectNotNull(negativeKeywordDTO.getText());
            assertObjectEqual(negativeKeywordDTO.getMatchType(), ((NegativeKeyword) getTestData().get("negKeyword")).getMatchType().toString());
        }
    }

    @Test
    public void testAllowPartialFailure() throws Exception {
        String payload = "{\"negativeKeywordIds\" : '[" + ((NegativeKeyword) getTestData().get("negKeyword")).getNegKeywordId() + "]', " +
                "\"allowPartialFailure\" : 'true'}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.POST, "account/negative/get", headers, payload));


        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            NegativeKeywordDTO negativeKeywordDTO = (NegativeKeywordDTO) result.getObject();
            assertObjectEqual(negativeKeywordDTO.getAccountId(), ((NegativeKeyword) getTestData().get("negKeyword")).getAccountId());
            assertObjectEqual(negativeKeywordDTO.getAdGroupId(), ((NegativeKeyword) getTestData().get("negKeyword")).getAdGroupId());
            assertObjectEqual(negativeKeywordDTO.getCampaignId(), ((NegativeKeyword) getTestData().get("negKeyword")).getCampaignId());
            assertObjectEqual(negativeKeywordDTO.getId(), ((NegativeKeyword) getTestData().get("negKeyword")).getNegKeywordId());
            assertObjectNotNull(negativeKeywordDTO.getText());
            assertObjectEqual(negativeKeywordDTO.getMatchType(), ((NegativeKeyword) getTestData().get("negKeyword")).getMatchType().toString());
        }
    }

    @Test
    public void testInvalidNegativeKeywordId() throws Exception {
        String payload = "{\"negativeKeywordIds\" : '[" + 12345 + "]', " +
                "\"allowPartialFailure\" : 'false'}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.POST, "account/negative/get", headers, payload));


        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 0);

        for (ResultsDTO result : responseDTO.getResults()) {
            assertObjectEqual(((NegativeKeywordDTO) result.getObject()).getId(), -1);
        }
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
            AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());
            NegativeKeyword negKeyword = createNegativeKeyword(account.getAccountId(), campaign.getCampaignId(), adGroup.getAdGroupId());

            testData.put("account", account);
            testData.put("campaign", campaign);
            testData.put("adGroup", adGroup);
            testData.put("negKeyword", negKeyword);
        }

        return testData;
    }
}