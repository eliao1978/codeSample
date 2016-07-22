package com.yp.pm.kp.api.regression.keyword;


import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.KeywordDTO;
import com.yp.pm.kp.enums.model.KeywordStatusEnum;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.AdGroup;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.model.domain.Keyword;
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
public class DeleteKeywordTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testDeleteKeyword() throws Exception {
        List<Keyword> keywordList = createKeyword(((Account) getTestData().get("account")).getAccountId(), ((AdGroup) getTestData().get("adGroup")).getAdGroupId());

        for (Keyword keyword : keywordList) {
            String payload = "{\"keywordIds\" : '[" + keyword.getKeywordId() + "]', " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.KEYWORD, getAPIResponse(MethodEnum.DELETE, "account/campaign/adgroup/keyword/delete", headers, payload));

            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 1);

            Keyword deletedKeyword = getKeywordById(((Account) getTestData().get("account")).getAccountId(), keyword.getKeywordId());
            assertObjectEqual(deletedKeyword.getKeywordStatus().toString(), KeywordStatusEnum.DELETED.toString());
        }
    }

    @Test
    public void testAllowPartialFailure() throws Exception {
        List<Keyword> keywordList = createKeyword(((Account) getTestData().get("account")).getAccountId(), ((AdGroup) getTestData().get("adGroup")).getAdGroupId());

        for (Keyword keyword : keywordList) {
            String payload = "{\"keywordIds\" : '[" + keyword.getKeywordId() + "]', " +
                    "\"allowPartialFailure\" : true}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.KEYWORD, getAPIResponse(MethodEnum.DELETE, "account/campaign/adgroup/keyword/delete", headers, payload));

            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 1);

            Keyword deletedKeyword = getKeywordById(((Account) getTestData().get("account")).getAccountId(), keyword.getKeywordId());
            assertObjectEqual(deletedKeyword.getKeywordStatus().toString(), KeywordStatusEnum.DELETED.toString());
        }
    }

    @Test
    public void testInvalidKeywordId() throws Exception {
        List<Keyword> keywordList = createKeyword(((Account) getTestData().get("account")).getAccountId(), ((AdGroup) getTestData().get("adGroup")).getAdGroupId());

        for (Keyword keyword : keywordList) {
            String payload = "{\"keywordIds\" : '[" + 12345 + "]', " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.KEYWORD, getAPIResponse(MethodEnum.DELETE, "account/campaign/adgroup/keyword/delete", headers, payload));

            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 0);

            for (ResultsDTO result : responseDTO.getResults()) {
                assertObjectEqual(((KeywordDTO) result.getObject()).getId(), -1);
            }

            Keyword deletedKeyword = getKeywordById(((Account) getTestData().get("account")).getAccountId(), keyword.getKeywordId());
            assertObjectEqual(deletedKeyword.getKeywordStatus().toString(), KeywordStatusEnum.ACTIVE.toString());
        }
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