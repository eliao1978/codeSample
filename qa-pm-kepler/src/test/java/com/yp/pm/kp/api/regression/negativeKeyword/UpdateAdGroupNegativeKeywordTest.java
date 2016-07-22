package com.yp.pm.kp.api.regression.negativeKeyword;


import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
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
public class UpdateAdGroupNegativeKeywordTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private static Map<String, Object> testData2;
    private Map<String, String> headers = getRequestHeader();

    public String generateAdGrpNegKeywordString(long adGroupId, long negKeywordId, String stat) {
        return " {" +
                "\"adGroupId\" : " + adGroupId + ", " +
                "\"negKeywordId\" : " + negKeywordId + ", " +
                "\"status\" : \"" + stat + "\", " +
                "\"negativeText\" : \"updated text\" " + "}";
    }

    @Test
    public void testUpdateAdGroupNegativeKeyword() throws Exception {
        String payload = "{\"negativeKeywords\" : [{" +
                "\"adGroupId\" : " + ((AdGroup) getTestData().get("adGroup")).getAdGroupId() + ", " +
                "\"negKeywordId\" : " + ((NegativeKeyword) getTestData().get("negKeyword")).getNegKeywordId() + ", " +
                "\"negativeText\" : \"updated text\"}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.PUT, "account/negative/update", headers, payload));


        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            NegativeKeywordDTO negativeKeywordDTO = (NegativeKeywordDTO) result.getObject();
            NegativeKeyword updatedNegativeKeyword = getNegativeKeywordById(((Account) getTestData().get("account")).getAccountId(), ((NegativeKeyword) getTestData().get("negKeyword")).getNegKeywordId());
            assertObjectEqual(negativeKeywordDTO.getId(), updatedNegativeKeyword.getNegKeywordId());
        }
    }

    @Test
    public void testAllowPartialFailure() throws Exception {
        String payload = "{\"negativeKeywords\" : [{" +
                "\"adGroupId\" : " + ((AdGroup) getTestData().get("adGroup")).getAdGroupId() + ", " +
                "\"negKeywordId\" : " + ((NegativeKeyword) getTestData().get("negKeyword")).getNegKeywordId() + ", " +
                "\"negativeText\" : \"updated text\"}], " +
                "\"allowPartialFailure\" : true}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.PUT, "account/negative/update", headers, payload));


        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            NegativeKeywordDTO negativeKeywordDTO = (NegativeKeywordDTO) result.getObject();
            NegativeKeyword updatedNegativeKeyword = getNegativeKeywordById(((Account) getTestData().get("account")).getAccountId(), ((NegativeKeyword) getTestData().get("negKeyword")).getNegKeywordId());
            assertObjectEqual(negativeKeywordDTO.getId(), updatedNegativeKeyword.getNegKeywordId());
        }
    }

    @Test
    public void testIgnoreUpdateAdGroupNegativeKeyword() throws Exception {
        String newKeyword = "updated text";
        String payload = "{\"negativeKeywords\" : [{" +
                "\"adGroupId\" : " + ((AdGroup) getTestData2().get("adGroup")).getAdGroupId() + ", " +
                "\"negKeywordId\" : -1, " +
                "\"negativeText\" : \"" + newKeyword + "\"}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.PUT, "account/negative/update", headers, payload));


        assertResponseError(responseDTO);

        for (ResultsDTO result : responseDTO.getResults()) {
            NegativeKeywordDTO negativeKeywordDTO = (NegativeKeywordDTO) result.getObject();
            NegativeKeyword updatedNegativeKeyword = getNegativeKeywordById(((Account) getTestData2().get("account")).getAccountId(), ((NegativeKeyword) getTestData2().get("negKeyword")).getNegKeywordId());
            assertObjectEqual(negativeKeywordDTO.getId(), -1);
            assertConditionTrue(!updatedNegativeKeyword.getNegativeText().equals(newKeyword));
        }
    }

    @Test
    public void testObjectProblemType() throws Exception {
        long accountId = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles").getAccountId();
        Campaign campaign = createCampaignOnly(accountId, createBudget(accountId, budgetAmount, deliveryMethod).getBudgetId());
        AdGroup adGroup1 = createAdGroupOnly(accountId, campaign.getCampaignId());
        AdGroup adGroup2 = createAdGroupOnly(accountId, campaign.getCampaignId());
        AdGroup adGroup3 = createAdGroupOnly(accountId, campaign.getCampaignId());
        NegativeKeyword negKeyword1 = createNegativeKeyword(accountId, campaign.getCampaignId(), adGroup1.getAdGroupId());
        NegativeKeyword negKeyword2 = createNegativeKeyword(accountId, campaign.getCampaignId(), adGroup2.getAdGroupId());
        NegativeKeyword negKeyword3 = createNegativeKeyword(accountId, campaign.getCampaignId(), adGroup3.getAdGroupId());
        String negKeywordString1 = generateAdGrpNegKeywordString(adGroup1.getAdGroupId(), negKeyword1.getNegKeywordId(), "ACTIVE");
        String negKeywordString2 = generateAdGrpNegKeywordString(adGroup2.getAdGroupId(), negKeyword2.getNegKeywordId(), "DELETED");
        String negKeywordString3 = generateAdGrpNegKeywordString(adGroup3.getAdGroupId(), negKeyword3.getNegKeywordId(), "ACTIVE");

        String command = "curl -X PUT " +
                "-d '{\"negativeKeywords\" : [" + negKeywordString1 + "," + negKeywordString2 + "," + negKeywordString3 + "], " +
                "\"allowPartialFailure\" : false}' " +
                "-H '" + contentType + "' " +
                "-H 'username:'" + userName + "'' " +
                "-H 'requestid:'" + requestId + "'' " +
                "-H 'accountId:'" + accountId + "'' " +
                "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/account/negative/update";

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(command));

        for (ResultsDTO result : responseDTO.getResults()) {
            NegativeKeywordDTO negativeKeywordDTO = (NegativeKeywordDTO) result.getObject();
            if (result.getError().getMessage() != null) {
                assertObjectEqual(negativeKeywordDTO.getId(), -3);
            } else {
                assertObjectEqual(negativeKeywordDTO.getId(), -2);
            }
        }
        assertObjectEqual(getNegativeKeywordById(accountId, negKeyword1.getNegKeywordId()).getStatus().toString(), "ACTIVE");
        assertObjectEqual(getNegativeKeywordById(accountId, negKeyword2.getNegKeywordId()).getStatus().toString(), "ACTIVE");
        assertObjectEqual(getNegativeKeywordById(accountId, negKeyword3.getNegKeywordId()).getStatus().toString(), "ACTIVE");
    }

    @Test
    public void testInvalidAdGroupId() throws Exception {
        String payload = "{\"negativeKeywords\" : [{" +
                "\"adGroupId\" : " + 12345 + ", " +
                "\"negKeywordId\" : " + ((NegativeKeyword) getTestData().get("negKeyword")).getNegKeywordId() + ", " +
                "\"negativeText\" : \"updated text\"}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.PUT, "account/negative/update", headers, payload));


        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            NegativeKeywordDTO negativeKeywordDTO = (NegativeKeywordDTO) result.getObject();
            NegativeKeyword updatedNegativeKeyword = getNegativeKeywordById(((Account) getTestData().get("account")).getAccountId(), ((NegativeKeyword) getTestData().get("negKeyword")).getNegKeywordId());
            assertObjectEqual(negativeKeywordDTO.getId(), updatedNegativeKeyword.getNegKeywordId());
        }
    }

    @Test
    public void testMissingAdGroupId() throws Exception {
        String payload = "{\"negativeKeywords\" : [{" +
                "\"negKeywordId\" : " + ((NegativeKeyword) getTestData().get("negKeyword")).getNegKeywordId() + ", " +
                "\"negativeText\" : \"updated text\"}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.PUT, "account/negative/update", headers, payload));


        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            NegativeKeywordDTO negativeKeywordDTO = (NegativeKeywordDTO) result.getObject();
            NegativeKeyword updatedNegativeKeyword = getNegativeKeywordById(((Account) getTestData().get("account")).getAccountId(), ((NegativeKeyword) getTestData().get("negKeyword")).getNegKeywordId());
            assertObjectEqual(negativeKeywordDTO.getId(), updatedNegativeKeyword.getNegKeywordId());
        }
    }

    @Test
    public void testInvalidNegativeKeywordId() throws Exception {
        String payload = "{\"negativeKeywords\" : [{" +
                "\"adGroupId\" : " + ((AdGroup) getTestData().get("adGroup")).getAdGroupId() + ", " +
                "\"negKeywordId\" : \"12345\", " +
                "\"negativeText\" : \"updated text\"}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.PUT, "account/negative/update", headers, payload));


        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 0);

        for (ResultsDTO result : responseDTO.getResults()) {
            NegativeKeywordDTO negativeKeywordDTO = (NegativeKeywordDTO) result.getObject();
            assertObjectEqual(negativeKeywordDTO.getId(), -1);
        }
    }

    @Test
    public void testMissingNegativeKeywordId() throws Exception {
        String payload = "{\"negativeKeywords\" : [{" +
                "\"adGroupId\" : " + ((AdGroup) getTestData().get("adGroup")).getAdGroupId() + ", " +
                "\"negativeText\" : \"updated text\"}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.PUT, "account/negative/update", headers, payload));


        assertObjectEqual(responseDTO.getTotalCount(), 0);
        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Negative keyword id can not be null");
    }

    @Test
    public void testMissingNegativeKeywordIdAllowPartialFailure() throws Exception {
        String payload = "{\"negativeKeywords\" : [{" +
                "\"adGroupId\" : " + ((AdGroup) getTestData().get("adGroup")).getAdGroupId() + ", " +
                "\"negativeText\" : \"updated text\"}], " +
                "\"allowPartialFailure\" : true}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.PUT, "account/negative/update", headers, payload));


        assertObjectEqual(responseDTO.getTotalCount(), 0);
        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Negative keyword id can not be null");
    }

    private Map<String, Object> getTestData2() throws Exception {
        if (testData2 == null) {
            testData2 = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
            AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());
            NegativeKeyword negKeyword = createNegativeKeyword(account.getAccountId(), campaign.getCampaignId(), adGroup.getAdGroupId());

            testData2.put("account", account);
            testData2.put("campaign", campaign);
            testData2.put("adGroup", adGroup);
            testData2.put("negKeyword", negKeyword);
        }

        return testData2;
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