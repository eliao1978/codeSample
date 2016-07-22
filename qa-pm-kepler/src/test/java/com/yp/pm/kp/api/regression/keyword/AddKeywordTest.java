package com.yp.pm.kp.api.regression.keyword;


import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.KeywordDTO;
import com.yp.pm.kp.enums.model.KeywordApprovalStatusEnum;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.AdGroup;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.model.domain.Keyword;
import com.yp.util.DBUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
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
public class AddKeywordTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void assertAddKeyword() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long adGroupId = ((AdGroup) getTestData().get("adGroup")).getAdGroupId();
        String payload = "{\"keywords\" : [{" +
                "\"adGroupId\" : " + adGroupId + ", " +
                "\"keywordText\" : \"testing keyword1\", " +
                "\"matchType\" : \"EXACT\", " +
                "\"maxCpc\" : { \"microAmount\" : 1000000 }}, " +
                "{\"adGroupId\" : " + adGroupId + ", " +
                "\"keywordText\" : \"testing keyword2\", " +
                "\"matchType\" : \"EXACT\", " +
                "\"maxCpc\" : { \"microAmount\" : 1000000 }}, " +
                "{\"adGroupId\" : " + adGroupId + ", " +
                "\"keywordText\" : \"testing keyword3\", " +
                "\"matchType\" : \"EXACT\", " +
                "\"maxCpc\" : { \"microAmount\" : 1000000 }}, " +
                "{\"adGroupId\" : " + adGroupId + ", " +
                "\"keywordText\" : \"testing keyword4\", " +
                "\"matchType\" : \"EXACT\", " +
                "\"maxCpc\" : { \"microAmount\" : 1000000 }}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.KEYWORD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/keyword/create", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 4);

        int i = 0;
        for (ResultsDTO result : responseDTO.getResults()) {
            assertConditionTrue(result.getIndex() == i);
            i += 1;
            KeywordDTO keywordDTO = (KeywordDTO) result.getObject();
            assertObjectEqual(keywordDTO.getId(), getKeywordById(accountId, keywordDTO.getId()).getKeywordId());
            Keyword newKeyword = getKeywordById(accountId, keywordDTO.getId());
            if(newKeyword.getApprovalStatus() != null) {
                assertConditionTrue("Approval status is not approved", newKeyword.getApprovalStatus().equals(KeywordApprovalStatusEnum.APPROVED));

            }
            else {
                logger.info("Approval status is NULL");
            }
        }
    }

    @Test
    public void assertAllowPartialFailure() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        String payload = "{\"keywords\" : [{" +
                "\"adGroupId\" : " + ((AdGroup) getTestData().get("adGroup")).getAdGroupId() + ", " +
                "\"keywordText\" : \"test keyword2\", " +
                "\"matchType\" : \"EXACT\", " +
                "\"maxCpc\" : { \"microAmount\" : 1000000 }}], " +
                "\"allowPartialFailure\" : true}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.KEYWORD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/keyword/create", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            KeywordDTO keywordDTO = (KeywordDTO) result.getObject();
            assertObjectEqual(keywordDTO.getId(), getKeywordById(accountId, keywordDTO.getId()).getKeywordId());
        }
    }

    @Test
    public void assertAddKeywordWithSpecialCharacter() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        String payload = "{\"keywords\" : [{" +
                "\"adGroupId\" : " + ((AdGroup) getTestData().get("adGroup")).getAdGroupId() + ", " +
                "\"keywordText\" : \"test!@%^*()={};~`<>?|\", " +
                "\"matchType\" : \"EXACT\", " +
                "\"maxCpc\" : { \"microAmount\" : 1000000 }}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.KEYWORD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/keyword/create", headers, payload));

        assertObjectEqual(responseDTO.getTotalCount(), 0);
        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Keyword can only contain ASCII printable (except ,!@%^*()={};~`<>?\\|) or French/Spanish accented characters");
    }

    @Test
    public void assertCustomLimitation() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        try {
            DBUtil.update("INSERT INTO PM_CUSTOM_LIMIT_VALIDATION VALUES('keyword', 5, ".concat(account.getAccountId().toString()).concat(", 'account', '").concat(userName) + "',SYSDATE,'" + userName + "',SYSDATE)");
            Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
            AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());

            for (int i = 0; i < 5; i++) {
                JSONObject newKeyword = new JSONObject();
                newKeyword.put("keywordText", "test keyword".concat(Integer.toString(i)));
                newKeyword.put("matchType", "EXACT");
                newKeyword.put("maxCPC", "5");
                JSONArray newKeywords = new JSONArray();
                newKeywords.add(newKeyword);
                createKeyword(account.getAccountId(), adGroup.getAdGroupId(), newKeywords);
            }

            String payload = "{\"keywords\" : [{" +
                    "\"adGroupId\" : " + adGroup.getAdGroupId() + ", " +
                    "\"keywordText\" : \"test keyword max\", " +
                    "\"matchType\" : \"EXACT\", " +
                    "\"maxCpc\" : { \"microAmount\" : 1000000 }}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", account.getAccountId().toString());

            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.KEYWORD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/keyword/create", headers, payload));

            assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Exceeds maximum number of keywords in ad group");
        } finally {
            DBUtil.update("DELETE FROM PM_CUSTOM_LIMIT_VALIDATION WHERE PARENT_ID = ".concat(account.getAccountId().toString()));
        }
    }

    @Ignore
    public void assertKeywordWithDestinationURLNotMatching() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        createAd(accountId, getAdGroupByCampaignId(accountId,getCampaignByAccountId(accountId).get(0).getCampaignId()).get(0).getAdGroupId());
        String payload = "{\"keywords\" : [{" +
                "\"adGroupId\" : " + ((AdGroup) getTestData().get("adGroup")).getAdGroupId() + ", " +
                "\"keywordText\" : \"testing keyword1\", " +
                "\"matchType\" : \"EXACT\", " +
                "\"destinationUrl\" : \"http://www.yahoo.com\", " +
                "\"maxCpc\" : { \"microAmount\" : 1000000 }}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.KEYWORD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/keyword/create", headers, payload));
        assertResponseError(responseDTO);
        Long keywordId = null;
        for (ResultsDTO result : responseDTO.getResults()) {
            KeywordDTO keywordDTO = (KeywordDTO) result.getObject();
            keywordId = keywordDTO.getId();
        }
        Thread.sleep(60000);
        logger.debug("Approval Status: " + getKeywordById(accountId,keywordId).getApprovalStatus());
        assertConditionTrue("Approval Status was not under review", getKeywordById(accountId, keywordId).getApprovalStatus().equals(KeywordApprovalStatusEnum.UNDER_REVIEW));

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