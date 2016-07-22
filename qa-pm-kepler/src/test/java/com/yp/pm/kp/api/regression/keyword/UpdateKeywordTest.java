package com.yp.pm.kp.api.regression.keyword;


import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
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
public class UpdateKeywordTest extends BaseAPITest {
    private Map<String, String> headers = getRequestHeader();
    private static Map<String, Object> testData;
    private static Map<String, Object> testData2;

    public String generateKeywordString(long adGroupId, long keywordId, String stat, String newText) {
        return " {" +
                "\"adGroupId\" : " + adGroupId + ", " +
                "\"keywordId\" : " + keywordId + ", " +
                "\"keywordStatus\" : \"" + stat + "\", " +
                "\"maxCpc\" : { \"microAmount\" : 20000000 }, " +
                "\"keywordText\" : \"" + newText + "\", " +
                "\"matchType\" : \"PHRASE\" " + "}";
    }

    @Test
    public void testUpdateKeyword() throws Exception {
        for (Keyword keyword : ((List<Keyword>) getTestData().get("keyword"))) {
            String newKeywordText = "KEYWORD_" + System.currentTimeMillis();
            String payload = "{\"keywords\" : [{" +
                    "\"adGroupId\" : " + ((AdGroup) getTestData().get("adGroup")).getAdGroupId() + ", " +
                    "\"keywordId\" : " + keyword.getKeywordId() + ", " +
                    "\"keywordStatus\" : \"PAUSED\", " +
                    "\"maxCpc\" : { \"microAmount\" : 20000000 }, " +
                    "\"keywordText\" : \"" + newKeywordText + "\", " +
                    "\"matchType\" : \"PHRASE\"}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.KEYWORD, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/keyword/update", headers, payload));

            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 1);

            for (ResultsDTO result : responseDTO.getResults()) {
                KeywordDTO keywordDTO = (KeywordDTO) result.getObject();
                Keyword updatedKeyword = getKeywordById(((Account) getTestData().get("account")).getAccountId(), keyword.getKeywordId());
                assertObjectEqual(keywordDTO.getId(), updatedKeyword.getKeywordId());
                assertObjectEqual(updatedKeyword.getKeywordStatus().toString(), KeywordStatusEnum.PAUSED.toString());
            }
        }
    }

    @Test
    public void testAllowPartialFailure() throws Exception {
        for (Keyword keyword : ((List<Keyword>) getTestData().get("keyword"))) {
            String newKeywordText = "KEYWORD_" + System.currentTimeMillis();
            String payload = "{\"keywords\" : [{" +
                    "\"adGroupId\" : " + ((AdGroup) getTestData().get("adGroup")).getAdGroupId() + ", " +
                    "\"keywordId\" : " + keyword.getKeywordId() + ", " +
                    "\"keywordStatus\" : \"PAUSED\", " +
                    "\"maxCpc\" : { \"microAmount\" : 20000000 }, " +
                    "\"keywordText\" : \"" + newKeywordText + "\", " +
                    "\"matchType\" : \"PHRASE\"}], " +
                    "\"allowPartialFailure\" : true}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.KEYWORD, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/keyword/update", headers, payload));

            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 1);

            for (ResultsDTO result : responseDTO.getResults()) {
                KeywordDTO keywordDTO = (KeywordDTO) result.getObject();
                Keyword updatedKeyword = getKeywordById(((Account) getTestData().get("account")).getAccountId(), keyword.getKeywordId());
                assertObjectEqual(keywordDTO.getId(), updatedKeyword.getKeywordId());
                assertObjectEqual(updatedKeyword.getKeywordStatus().toString(), KeywordStatusEnum.PAUSED.toString());
            }
        }
    }

    @Test
    public void testIgnoreUpdateKeyword() throws Exception {
        for (Keyword keyword : ((List<Keyword>) getTestData2().get("keyword"))) {
            String newKeywordText = "KEYWORD_" + System.currentTimeMillis();
            String payload = "{\"keywords\" : [{" +
                    "\"adGroupId\" : " + ((AdGroup) getTestData2().get("adGroup")).getAdGroupId() + ", " +
                    "\"keywordId\" : -1, " +
                    "\"keywordStatus\" : \"PAUSED\", " +
                    "\"maxCpc\" : { \"microAmount\" : 20000000 }, " +
                    "\"keywordText\" : \"" + newKeywordText + "\", " +
                    "\"matchType\" : \"PHRASE\"}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.KEYWORD, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/keyword/update", headers, payload));

            assertResponseError(responseDTO);

            for (ResultsDTO result : responseDTO.getResults()) {
                KeywordDTO keywordDTO = (KeywordDTO) result.getObject();
                Keyword updatedKeyword = getKeywordById(((Account) getTestData2().get("account")).getAccountId(), keyword.getKeywordId());
                assertObjectEqual(keywordDTO.getId(), -1);
                assertObjectEqual(updatedKeyword.getKeywordStatus().toString(), KeywordStatusEnum.ACTIVE.toString());
            }
        }
    }

    @Test
    public void testObjectProblemType() throws Exception {
        long accountId = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles").getAccountId();
        Campaign campaign = createCampaignOnly(accountId, createBudget(accountId, budgetAmount, deliveryMethod).getBudgetId());
        AdGroup adGroup1 = createAdGroupOnly(accountId, campaign.getCampaignId());
        AdGroup adGroup2 = createAdGroupOnly(accountId, campaign.getCampaignId());
        AdGroup adGroup3 = createAdGroupOnly(accountId, campaign.getCampaignId());
        List<Keyword> keyword1 = createKeyword(accountId, adGroup1.getAdGroupId());
        List<Keyword> keyword2 = createKeyword(accountId, adGroup2.getAdGroupId());
        List<Keyword> keyword3 = createKeyword(accountId, adGroup3.getAdGroupId());
        String newKeywordText = "KEYWORD_" + System.currentTimeMillis();
        String keywordString1 = generateKeywordString(adGroup1.getAdGroupId(), keyword1.get(0).getKeywordId(), "PAUSED", newKeywordText);
        String keywordString2 = generateKeywordString(adGroup2.getAdGroupId(), keyword2.get(0).getKeywordId(), "DELETED", newKeywordText);
        String keywordString3 = generateKeywordString(adGroup3.getAdGroupId(), keyword3.get(0).getKeywordId(), "ACTIVE", newKeywordText);


        String command = "curl -X PUT " +
                "-d '{\"keywords\" : [" + keywordString1 + "," + keywordString2 + "," + keywordString3 + "], " +
                "\"allowPartialFailure\" : false}' " +
                "-H '" + contentType + "' " +
                "-H 'username:'" + userName + "'' " +
                "-H 'requestid:'" + requestId + "'' " +
                "-H 'accountId:'" + accountId + "'' " +
                "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/account/campaign/adgroup/keyword/update";

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.KEYWORD, getAPIResponse(command));


        for (ResultsDTO result : responseDTO.getResults()) {
            KeywordDTO keywordDTO = (KeywordDTO) result.getObject();
            if (result.getError().getMessage() != null) {
                assertObjectEqual(keywordDTO.getId(), -3);
            } else {
                assertObjectEqual(keywordDTO.getId(), -2);
            }

        }
        assertObjectEqual(getKeywordById(accountId, keyword1.get(0).getKeywordId()).getKeywordStatus().toString(), KeywordStatusEnum.ACTIVE.toString());
        assertObjectEqual(getKeywordById(accountId, keyword2.get(0).getKeywordId()).getKeywordStatus().toString(), KeywordStatusEnum.ACTIVE.toString());
        assertObjectEqual(getKeywordById(accountId, keyword3.get(0).getKeywordId()).getKeywordStatus().toString(), KeywordStatusEnum.ACTIVE.toString());
    }


    @Test
    public void testMissingKeywordId() throws Exception {
        for (Keyword ignored : ((List<Keyword>) getTestData().get("keyword"))) {
            String newKeywordText = "KEYWORD_" + System.currentTimeMillis();
            String payload = "{\"keywords\" : [{" +
                    "\"adGroupId\" : " + ((AdGroup) getTestData().get("adGroup")).getAdGroupId() + ", " +
                    "\"keywordId\" : \"\", " +
                    "\"keywordStatus\" : \"PAUSED\", " +
                    "\"maxCpc\" : { \"microAmount\" : 20000000 }, " +
                    "\"keywordText\" : \"" + newKeywordText + "\", " +
                    "\"matchType\" : \"PHRASE\"}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.KEYWORD, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/keyword/update", headers, payload));

            assertObjectEqual(responseDTO.getTotalCount(), 0);
            assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Keyword id can not be null");
        }
    }

    @Test
    public void testMissingKeywordIdAllowPartialFailure() throws Exception {
        for (Keyword ignored : ((List<Keyword>) getTestData().get("keyword"))) {
            String newKeywordText = "KEYWORD_" + System.currentTimeMillis();
            String payload = "{\"keywords\" : [{" +
                    "\"adGroupId\" : " + ((AdGroup) getTestData().get("adGroup")).getAdGroupId() + ", " +
                    "\"keywordId\" : \"\", " +
                    "\"keywordStatus\" : \"PAUSED\", " +
                    "\"maxCpc\" : { \"microAmount\" : 20000000 }, " +
                    "\"keywordText\" : \"" + newKeywordText + "\", " +
                    "\"matchType\" : \"PHRASE\"}], " +
                    "\"allowPartialFailure\" : true}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.KEYWORD, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/keyword/update", headers, payload));

            assertObjectEqual(responseDTO.getTotalCount(), 0);
            assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Keyword id can not be null");
        }
    }

    @Test
    public void testInvalidKeywordId() throws Exception {
        for (Keyword ignored : ((List<Keyword>) getTestData().get("keyword"))) {
            String newKeywordText = "KEYWORD_" + System.currentTimeMillis();
            String payload = "{\"keywords\" : [{" +
                    "\"adGroupId\" : " + ((AdGroup) getTestData().get("adGroup")).getAdGroupId() + ", " +
                    "\"keywordId\" : \"12345\", " +
                    "\"keywordStatus\" : \"PAUSED\", " +
                    "\"maxCpc\" : { \"microAmount\" : 20000000 }, " +
                    "\"keywordText\" : \"" + newKeywordText + "\", " +
                    "\"matchType\" : \"PHRASE\"}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.KEYWORD, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/keyword/update", headers, payload));


            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 0);

            for (ResultsDTO result : responseDTO.getResults()) {
                KeywordDTO keywordDTO = (KeywordDTO) result.getObject();
                assertObjectEqual(keywordDTO.getId(), -1);
            }
        }
    }

    @Test
    public void testInvalidAdGroupId() throws Exception {
        for (Keyword keyword : ((List<Keyword>) getTestData().get("keyword"))) {
            String newKeywordText = "KEYWORD_" + System.currentTimeMillis();
            String payload = "{\"keywords\" : [{" +
                    "\"adGroupId\" : \"12345\", " +
                    "\"keywordId\" : " + keyword.getKeywordId() + ", " +
                    "\"keywordStatus\" : \"PAUSED\", " +
                    "\"maxCpc\" : { \"microAmount\" : 20000000 }, " +
                    "\"keywordText\" : \"" + newKeywordText + "\", " +
                    "\"matchType\" : \"PHRASE\"}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.KEYWORD, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/keyword/update", headers, payload));

            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 1);

            for (ResultsDTO result : responseDTO.getResults()) {
                KeywordDTO keywordDTO = (KeywordDTO) result.getObject();
                Keyword updatedKeyword = getKeywordById(((Account) getTestData().get("account")).getAccountId(), keyword.getKeywordId());
                assertObjectEqual(keywordDTO.getId(), updatedKeyword.getKeywordId());
                assertObjectEqual(updatedKeyword.getKeywordStatus().toString(), KeywordStatusEnum.PAUSED.toString());
            }
        }
    }

    @Test
    public void testMissingAdGroupId() throws Exception {
        for (Keyword keyword : ((List<Keyword>) getTestData().get("keyword"))) {
            String newKeywordText = "KEYWORD_" + System.currentTimeMillis();
            String payload = "{\"keywords\" : [{" +
                    "\"keywordId\" : " + keyword.getKeywordId() + ", " +
                    "\"keywordStatus\" : \"PAUSED\", " +
                    "\"maxCpc\" : { \"microAmount\" : 20000000 }, " +
                    "\"keywordText\" : \"" + newKeywordText + "\", " +
                    "\"matchType\" : \"PHRASE\"}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.KEYWORD, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/keyword/update", headers, payload));

            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 1);

            for (ResultsDTO result : responseDTO.getResults()) {
                KeywordDTO keywordDTO = (KeywordDTO) result.getObject();
                Keyword updatedKeyword = getKeywordById(((Account) getTestData().get("account")).getAccountId(), keyword.getKeywordId());
                assertObjectEqual(keywordDTO.getId(), updatedKeyword.getKeywordId());
                assertObjectEqual(updatedKeyword.getKeywordStatus().toString(), KeywordStatusEnum.PAUSED.toString());
            }
        }
    }

    private Map<String, Object> getTestData2() throws Exception {
        if (testData2 == null) {
            testData2 = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
            AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());
            List<Keyword> keyword = createKeyword(account.getAccountId(), adGroup.getAdGroupId());

            testData2.put("account", account);
            testData2.put("campaign", campaign);
            testData2.put("adGroup", adGroup);
            testData2.put("keyword", keyword);
        }

        return testData2;
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
            AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());
            List<Keyword> keyword = createKeyword(account.getAccountId(), adGroup.getAdGroupId());

            testData.put("account", account);
            testData.put("campaign", campaign);
            testData.put("adGroup", adGroup);
            testData.put("keyword", keyword);
        }

        return testData;
    }
}