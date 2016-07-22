package com.yp.pm.kp.api.regression.keyword;


import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.KeywordDTO;
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
public class GetKeywordByAdGroupTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testGetKeywordByAdGroupID() throws Exception {
        List<Keyword> keywordList = ((List<Keyword>) getTestData().get("keywordList"));

        for (Keyword keyword : keywordList) {
            String payload = "{\"adGroupIds\" : '[" + ((AdGroup) getTestData().get("adGroup")).getAdGroupId() + "]', " +
                    "\"allowPartialFailure\" : 'false'}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.KEYWORD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/keyword/get", headers, payload));

            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 1);

            for (ResultsDTO result : responseDTO.getResults()) {
                KeywordDTO keywordDTO = (KeywordDTO) result.getObject();
                assertObjectEqual(keywordDTO.getAccountId(), ((Account) getTestData().get("account")).getAccountId());
                assertObjectEqual(keywordDTO.getAdGroupId(), ((AdGroup) getTestData().get("adGroup")).getAdGroupId());
                assertObjectEqual(keywordDTO.getId(), keyword.getKeywordId());
                assertObjectEqual(keywordDTO.getStatus(), keyword.getKeywordStatus().toString());
                assertObjectEqual(keywordDTO.getText(), keyword.getKeywordText());
                assertObjectEqual(keywordDTO.getMatchType(), keyword.getMatchType().toString());
                assertObjectNotNull(keywordDTO.getMaxCpc());
            }
        }
    }

    @Test
    public void testAllowPartialFailure() throws Exception {
        List<Keyword> keywordList = ((List<Keyword>) getTestData().get("keywordList"));

        for (Keyword keyword : keywordList) {
            String payload = "{\"adGroupIds\" : '[" + ((AdGroup) getTestData().get("adGroup")).getAdGroupId() + "]', " +
                    "\"allowPartialFailure\" : 'true'}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.KEYWORD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/keyword/get", headers, payload));

            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 1);

            for (ResultsDTO result : responseDTO.getResults()) {
                KeywordDTO keywordDTO = (KeywordDTO) result.getObject();
                assertObjectEqual(keywordDTO.getAccountId(), ((Account) getTestData().get("account")).getAccountId());
                assertObjectEqual(keywordDTO.getAdGroupId(), ((AdGroup) getTestData().get("adGroup")).getAdGroupId());
                assertObjectEqual(keywordDTO.getId(), keyword.getKeywordId());
                assertObjectEqual(keywordDTO.getStatus(), keyword.getKeywordStatus().toString());
                assertObjectEqual(keywordDTO.getText(), keyword.getKeywordText());
                assertObjectEqual(keywordDTO.getMatchType(), keyword.getMatchType().toString());
                assertObjectNotNull(keywordDTO.getMaxCpc());
            }
        }
    }

    @Test
    public void testInvalidAdGroupId() throws Exception {
        String payload = "{\"adGroupIds\" : '[" + 12345 + "]', " +
                "\"allowPartialFailure\" : 'false'}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.KEYWORD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/keyword/get", headers, payload));

        assertResponseError(responseDTO);
        assertConditionTrue(responseDTO.getTotalCount() == 0);
        assertObjectEqual(responseDTO.getResults().size(), 0);
    }

    @Test
    public void testMissingAdGroupId() throws Exception {
        List<Keyword> keywordList = ((List<Keyword>) getTestData().get("keywordList"));

        for (Keyword keyword : keywordList) {
            String payload = "{\"adGroupIds\" : '[]', " +
                    "\"allowPartialFailure\" : 'false'}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.KEYWORD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/keyword/get", headers, payload));

            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 1);

            for (ResultsDTO result : responseDTO.getResults()) {
                KeywordDTO keywordDTO = (KeywordDTO) result.getObject();
                assertObjectEqual(keywordDTO.getAccountId(), ((Account) getTestData().get("account")).getAccountId());
                assertObjectEqual(keywordDTO.getAdGroupId(), ((AdGroup) getTestData().get("adGroup")).getAdGroupId());
                assertObjectEqual(keywordDTO.getId(), keyword.getKeywordId());
                assertObjectEqual(keywordDTO.getStatus(), keyword.getKeywordStatus().toString());
                assertObjectEqual(keywordDTO.getText(), keyword.getKeywordText());
                assertObjectEqual(keywordDTO.getMatchType(), keyword.getMatchType().toString());
                assertObjectNotNull(keywordDTO.getMaxCpc());
            }
        }
    }

    @Test
    public void testPagingOutOfRange() throws Exception {
        String payload = "{\"adGroupIds\" : '[" + ((AdGroup) getTestData().get("adGroup")).getAdGroupId() + "]', " +
                "\"paging\" : { \"startIndex\" : 1, \"numberResults\" : 10 }, " +
                "\"allowPartialFailure\" : 'false'}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.KEYWORD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/keyword/get", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);
        assertObjectEqual(responseDTO.getResults().size(), 0);
    }

    @Test
    public void testNegativeNumberResults() throws Exception {
        String payload = "{\"adGroupIds\" : '[" + ((AdGroup) getTestData().get("adGroup")).getAdGroupId() + "]', " +
                "\"paging\" : { \"startIndex\" : 0, \"numberResults\" : -10 }, " +
                "\"allowPartialFailure\" : 'false'}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.KEYWORD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/keyword/get", headers, payload));

        assertConditionTrue(responseDTO.getResultStatus().getError().getType().equals(ErrorTypeEnum.USER_ERROR));
        assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Invalid paging number of results. It must be greater than or equal to zero");
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
            AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());
            List<Keyword> keywordList = createKeyword(account.getAccountId(), adGroup.getAdGroupId());

            testData.put("account", account);
            testData.put("campaign", campaign);
            testData.put("adGroup", adGroup);
            testData.put("keywordList", keywordList);
        }

        return testData;
    }
}