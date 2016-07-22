package com.yp.pm.kp.api.regression.negativeKeyword;


import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.NegativeKeywordDTO;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.util.DBUtil;
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
public class AddCampaignNegativeKeywordTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testAddCampaignNegativeKeyword() throws Exception {
        String payload = "{\"negativeKeywords\" : [{" +
                "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"negativeText\" : \"testing negative keyword1\", " +
                "\"matchType\" : \"PHRASE\", " +
                "\"status\" : \"ACTIVE\"}, " +
                "{\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"negativeText\" : \"testing negative keyword2\", " +
                "\"matchType\" : \"PHRASE\", " +
                "\"status\" : \"ACTIVE\"}, " +
                "{\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"negativeText\" : \"testing negative keyword3\", " +
                "\"matchType\" : \"PHRASE\", " +
                "\"status\" : \"ACTIVE\"}, " +
                "{\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"negativeText\" : \"testing negative keyword4\", " +
                "\"matchType\" : \"PHRASE\", " +
                "\"status\" : \"ACTIVE\"}, " +
                "{\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"negativeText\" : \"testing negative keyword5\", " +
                "\"matchType\" : \"PHRASE\", " +
                "\"status\" : \"ACTIVE\"}, " +
                "{\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"negativeText\" : \"testing negative keyword6\", " +
                "\"matchType\" : \"PHRASE\", " +
                "\"status\" : \"ACTIVE\"}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.POST, "account/negative/create", headers, payload));
        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 6);
        int i = 0;
        for (ResultsDTO result : responseDTO.getResults()) {
            assertConditionTrue(result.getIndex() == i);
            i += 1;
            NegativeKeywordDTO negativeKeywordDTO = (NegativeKeywordDTO) result.getObject();
            assertObjectEqual(negativeKeywordDTO.getId(), getNegativeKeywordById(((Account) getTestData().get("account")).getAccountId(), negativeKeywordDTO.getId()).getNegKeywordId());
        }
    }

    @Test
    public void testAllowPartialFailure() throws Exception {
        String payload = "{\"negativeKeywords\" : [{" +
                "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"negativeText\" : \"test negative keyword2\", " +
                "\"matchType\" : \"PHRASE\", " +
                "\"status\" : \"ACTIVE\"}], " +
                "\"allowPartialFailure\" : true}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.POST, "account/negative/create", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            NegativeKeywordDTO negativeKeywordDTO = (NegativeKeywordDTO) result.getObject();
            assertObjectEqual(negativeKeywordDTO.getId(), getNegativeKeywordById(((Account) getTestData().get("account")).getAccountId(), negativeKeywordDTO.getId()).getNegKeywordId());
        }
    }

    @Test
    public void testAddCampaignNegativeKeywordWithSpecialCharacter() throws Exception {
        String payload = "{\"negativeKeywords\" : [{" +
                "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"negativeText\" : \"test!@%^*()={};~`<>?|\", " +
                "\"matchType\" : \"PHRASE\", " +
                "\"status\" : \"ACTIVE\"}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.POST, "account/negative/create", headers, payload));

        assertObjectEqual(responseDTO.getTotalCount(), 0);
        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Negative keyword can only contain ASCII printable (except ,!@%^*()={};~`<>?\\|) or French/Spanish accented characters");
    }

    @Test
    public void testInvalidCampaignId() throws Exception {
        String payload = "{\"negativeKeywords\" : [{" +
                "\"campaignId\" : \"12345\", " +
                "\"negativeText\" : \"test negative keyword\", " +
                "\"matchType\" : \"PHRASE\", " +
                "\"status\" : \"ACTIVE\"}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.POST, "account/negative/create", headers, payload));


        assertObjectEqual(responseDTO.getTotalCount(), 0);
        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Campaign with the specified ID does not exist");
    }

    @Test
    public void testAddCampaignNegativeKeywordCustomLimit() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        try {
            DBUtil.update("INSERT INTO PM_CUSTOM_LIMIT_VALIDATION VALUES('neg_keyword', 5, ".concat(account.getAccountId().toString()).concat(", 'account', '").concat(userName) + "',SYSDATE,'" + userName + "',SYSDATE)");
            Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, deliveryMethod).getBudgetId());

            int count = 1;
            while (count < 6) {
                createCampaignNegativeKeyword(account.getAccountId(), campaign.getCampaignId(), "Negative keyword".concat(Integer.toString(count)));
                count++;
            }
            String payload = "{\"negativeKeywords\" : [{" +
                    "\"campaignId\" : " + campaign.getCampaignId() + ", " +
                    "\"negativeText\" : \"test negative keyword\", " +
                    "\"matchType\" : \"PHRASE\", " +
                    "\"status\" : \"ACTIVE\"}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", account.getAccountId().toString());

            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.POST, "account/negative/create", headers, payload));

            assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Exceeds maximum number of negative keywords in campaign");
        } finally {
            DBUtil.update("DELETE FROM PM_CUSTOM_LIMIT_VALIDATION WHERE PARENT_ID = ".concat(account.getAccountId().toString()));
        }
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, deliveryMethod).getBudgetId());

            testData.put("account", account);
            testData.put("campaign", campaign);
        }

        return testData;
    }
}