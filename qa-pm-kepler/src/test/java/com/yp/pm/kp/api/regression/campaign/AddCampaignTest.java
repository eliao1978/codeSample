package com.yp.pm.kp.api.regression.campaign;

import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.CampaignDTO;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.util.DBUtil;
import com.yp.util.DateUtil;
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
public class AddCampaignTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testAddCampaign() throws Exception {
        String payload = "{\"campaigns\" : [{" +
                "\"budgetId\" : " + ((Budget) getTestData().get("budget")).getBudgetId() + ", " +
                "\"campaignName\" : " + "\"CAMP_" + System.currentTimeMillis() + "\", " +
                "\"campaignType\" : \"SEARCH\", " +
                "\"networkType\" : \"SEARCH_NETWORK\", " +
                "\"mobileBidModifier\" : 1, " +
                "\"desktopBidModifier\" : 1, " +
                "\"campaignStatus\" : \"ACTIVE\", " +
                "\"biddingStrategyType\" : \"MANUAL_CPC\", " +
                "\"startDate\" : " + DateUtil.getStartDate(DateUtil.getTime()) + ", " +
                "\"endDate\" : " + DateUtil.getEndDate(5) + ", " +
                "\"adRotation\": \"ROTATE_INDEFINITELY\", " +
                "\"includeKeywordCloseVariant\": \"false\"}, " +
                "{\"budgetId\" : " + ((Budget) getTestData().get("budget")).getBudgetId() + ", " +
                "\"campaignName\" : " + "\"CAMP_1" + System.currentTimeMillis() + "\", " +
                "\"campaignType\" : \"SEARCH\", " +
                "\"networkType\" : \"SEARCH_NETWORK\", " +
                "\"mobileBidModifier\" :1, " +
                "\"desktopBidModifier\" : 1, " +
                "\"campaignStatus\" : \"ACTIVE\", " +
                "\"biddingStrategyType\" : \"MANUAL_CPC\", " +
                "\"startDate\" : " + DateUtil.getStartDate(DateUtil.getTime()) + ", " +
                "\"endDate\" : " + DateUtil.getEndDate(5) + ", " +
                "\"adRotation\": \"ROTATE_INDEFINITELY\", " +
                "\"includeKeywordCloseVariant\": \"true\"}, " +
                "{\"budgetId\" : " + ((Budget) getTestData().get("budget")).getBudgetId() + ", " +
                "\"campaignName\" : " + "\"CAMP_2" + System.currentTimeMillis() + "\", " +
                "\"campaignType\" : \"SEARCH\", " +
                "\"networkType\" : \"SEARCH_NETWORK\", " +
                "\"mobileBidModifier\" : 1, " +
                "\"desktopBidModifier\" : 1, " +
                "\"campaignStatus\" : \"ACTIVE\", " +
                "\"biddingStrategyType\" : \"MANUAL_CPC\", " +
                "\"startDate\" : " + DateUtil.getStartDate(DateUtil.getTime()) + ", " +
                "\"endDate\" : " + DateUtil.getEndDate(5) + ", " +
                "\"adRotation\": \"ROTATE_INDEFINITELY\", " +
                "\"includeKeywordCloseVariant\": \"false\"}], " +
                "\"allowPartialFailure\" : \"false\"}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN, getAPIResponse(MethodEnum.POST, "account/campaign/create", headers, payload));


        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 3);
        int i = 0;
        for (ResultsDTO result : responseDTO.getResults()) {
            assertConditionTrue(result.getIndex() == i);
            i += 1;

            CampaignDTO campaignDTO = (CampaignDTO) result.getObject();
            assertObjectEqual(campaignDTO.getId(), getCampaignById(((Account) getTestData().get("account")).getAccountId(), campaignDTO.getId()).getCampaignId());
            assertConditionTrue(getCampaignById(((Account) getTestData().get("account")).getAccountId(), campaignDTO.getId()).getIncludeKeywordCloseVariant());
        }
    }

    @Test
    public void testAllowPartialFailure() throws Exception {
        String payload = "{\"campaigns\" : [{" +
                "\"budgetId\" : " + ((Budget) getTestData().get("budget")).getBudgetId() + ", " +
                "\"campaignName\" : " + "\"CAMP_" + System.currentTimeMillis() + "\", " +
                "\"campaignType\" : \"SEARCH\", " +
                "\"networkType\" : \"SEARCH_NETWORK\", " +
                "\"mobileBidModifier\" : 1, " +
                "\"desktopBidModifier\" : 1, " +
                "\"campaignStatus\" : \"ACTIVE\", " +
                "\"biddingStrategyType\" : \"MANUAL_CPC\", " +
                "\"startDate\" : " + DateUtil.getStartDate(DateUtil.getTime()) + ", " +
                "\"endDate\" : " + DateUtil.getEndDate(5) + ", " +
                "\"adRotation\": \"ROTATE_INDEFINITELY\", " +
                "\"includeKeywordCloseVariant\": \"false\"}], " +
                "\"allowPartialFailure\" : \"true\"}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN, getAPIResponse(MethodEnum.POST, "account/campaign/create", headers, payload));


        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            CampaignDTO campaignDTO = (CampaignDTO) result.getObject();
            assertObjectEqual(campaignDTO.getId(), getCampaignById(((Account) getTestData().get("account")).getAccountId(), campaignDTO.getId()).getCampaignId());
        }
    }

    @Test
    public void testDeletedStatus() throws Exception {
        String payload = "{\"campaigns\" : [{" +
                "\"budgetId\" : " + ((Budget) getTestData().get("budget")).getBudgetId() + ", " +
                "\"campaignName\" : " + "\"CAMP_" + System.currentTimeMillis() + "\", " +
                "\"campaignType\" : \"SEARCH\", " +
                "\"networkType\" : \"SEARCH_NETWORK\", " +
                "\"mobileBidModifier\" : 1, " +
                "\"desktopBidModifier\" : 1, " +
                "\"campaignStatus\" : \"DELETED\", " +
                "\"biddingStrategyType\" : \"MANUAL_CPC\", " +
                "\"startDate\" : " + DateUtil.getStartDate(DateUtil.getTime()) + ", " +
                "\"adRotation\": \"ROTATE_INDEFINITELY\", " +
                "\"includeKeywordCloseVariant\": \"false\"}], " +
                "\"allowPartialFailure\" : \"false\"}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN, getAPIResponse(MethodEnum.POST, "account/campaign/create", headers, payload));


        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            CampaignDTO campaignDTO = (CampaignDTO) result.getObject();
            assertObjectEqual(campaignDTO.getId(), getCampaignById(((Account) getTestData().get("account")).getAccountId(), campaignDTO.getId()).getCampaignId());
        }
    }

    @Test
    public void testPausedStatus() throws Exception {
        String payload = "{\"campaigns\" : [{" +
                "\"budgetId\" : " + ((Budget) getTestData().get("budget")).getBudgetId() + ", " +
                "\"campaignName\" : " + "\"CAMP_" + System.currentTimeMillis() + "\", " +
                "\"campaignType\" : \"SEARCH\", " +
                "\"networkType\" : \"SEARCH_NETWORK\", " +
                "\"mobileBidModifier\" : 1, " +
                "\"desktopBidModifier\" : 1, " +
                "\"campaignStatus\" : \"PAUSED\", " +
                "\"biddingStrategyType\" : \"MANUAL_CPC\", " +
                "\"startDate\" : " + DateUtil.getStartDate(DateUtil.getTime()) + ", " +
                "\"adRotation\": \"ROTATE_INDEFINITELY\", " +
                "\"includeKeywordCloseVariant\": \"false\"}], " +
                "\"allowPartialFailure\" : \"false\"}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN, getAPIResponse(MethodEnum.POST, "account/campaign/create", headers, payload));


        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            CampaignDTO campaignDTO = (CampaignDTO) result.getObject();
            assertObjectEqual(campaignDTO.getId(), getCampaignById(((Account) getTestData().get("account")).getAccountId(), campaignDTO.getId()).getCampaignId());
        }
    }

    @Test
    public void testWithoutEndDate() throws Exception {
        String payload = "{\"campaigns\" : [{" +
                "\"budgetId\" : " + ((Budget) getTestData().get("budget")).getBudgetId() + ", " +
                "\"campaignName\" : " + "\"CAMP_" + System.currentTimeMillis() + "\", " +
                "\"campaignType\" : \"SEARCH\", " +
                "\"networkType\" : \"SEARCH_NETWORK\", " +
                "\"mobileBidModifier\" : 1, " +
                "\"desktopBidModifier\" : 1, " +
                "\"campaignStatus\" : \"ACTIVE\", " +
                "\"biddingStrategyType\" : \"MANUAL_CPC\", " +
                "\"startDate\" : " + DateUtil.getStartDate(DateUtil.getTime()) + ", " +
                "\"adRotation\": \"ROTATE_INDEFINITELY\", " +
                "\"includeKeywordCloseVariant\": \"false\"}], " +
                "\"allowPartialFailure\" : \"false\"}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN, getAPIResponse(MethodEnum.POST, "account/campaign/create", headers, payload));


        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            CampaignDTO campaignDTO = (CampaignDTO) result.getObject();
            assertObjectEqual(campaignDTO.getId(), getCampaignById(((Account) getTestData().get("account")).getAccountId(), campaignDTO.getId()).getCampaignId());
        }
    }

    @Test
    public void testInvalidMobileBidModifier() throws Exception {
        String payload = "{\"campaigns\" : [{" +
                "\"budgetId\" : " + ((Budget) getTestData().get("budget")).getBudgetId() + ", " +
                "\"campaignName\" : " + "\"CAMP_" + System.currentTimeMillis() + "\", " +
                "\"campaignType\" : \"SEARCH\", " +
                "\"networkType\" : \"SEARCH_NETWORK\", " +
                "\"mobileBidModifier\" : 5, " +
                "\"desktopBidModifier\" : 1, " +
                "\"campaignStatus\" : \"ACTIVE\", " +
                "\"biddingStrategyType\" : \"MANUAL_CPC\", " +
                "\"startDate\" : " + DateUtil.getStartDate(DateUtil.getTime()) + ", " +
                "\"endDate\" : " + DateUtil.getEndDate(5) + ", " +
                "\"adRotation\": \"ROTATE_INDEFINITELY\", " +
                "\"includeKeywordCloseVariant\": \"false\"}], " +
                "\"allowPartialFailure\" : \"false\"}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN, getAPIResponse(MethodEnum.POST, "account/campaign/create", headers, payload));


        assertObjectEqual(responseDTO.getTotalCount(), 0);
        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Mobile bid adjustment should be -100% (0.0) or between -90% (0.1) and 300% (4.0)");
    }

    @Test
    public void testInvalidDesktopBidModifier() throws Exception {
        String payload = "{\"campaigns\" : [{" +
                "\"budgetId\" : " + ((Budget) getTestData().get("budget")).getBudgetId() + ", " +
                "\"campaignName\" : " + "\"CAMP_" + System.currentTimeMillis() + "\", " +
                "\"campaignType\" : \"SEARCH\", " +
                "\"networkType\" : \"SEARCH_NETWORK\", " +
                "\"mobileBidModifier\" : 1, " +
                "\"desktopBidModifier\" : 5, " +
                "\"campaignStatus\" : \"ACTIVE\", " +
                "\"biddingStrategyType\" : \"MANUAL_CPC\", " +
                "\"startDate\" : " + DateUtil.getStartDate(DateUtil.getTime()) + ", " +
                "\"endDate\" : " + DateUtil.getEndDate(5) + ", " +
                "\"adRotation\": \"ROTATE_INDEFINITELY\", " +
                "\"includeKeywordCloseVariant\": \"false\"}], " +
                "\"allowPartialFailure\" : \"false\"}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN, getAPIResponse(MethodEnum.POST, "account/campaign/create", headers, payload));


        assertObjectEqual(responseDTO.getTotalCount(), 0);
        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Desktop bid adjustment should be -100% (0.0) or 0% (1.0)");
    }

    @Test
    public void testAddCampaignCustomLimit() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        try {
            DBUtil.update("INSERT INTO PM_CUSTOM_LIMIT_VALIDATION VALUES('campaign', 2, ".concat(account.getAccountId().toString()).concat(", 'account', '").concat(userName) + "',SYSDATE,'" + userName + "',SYSDATE)");
            Budget budget = createBudget(account.getAccountId(), budgetAmount, deliveryMethod);
            for (int i = 0; i < 2; i++) {
                createCampaignOnly(account.getAccountId(), budget.getBudgetId());
            }

            String payload = "{\"campaigns\" : [{" +
                    "\"budgetId\" : " + budget.getBudgetId() + ", " +
                    "\"campaignName\" : " + "\"CAMP_" + System.currentTimeMillis() + "\", " +
                    "\"campaignType\" : \"SEARCH\", " +
                    "\"networkType\" : \"SEARCH_NETWORK\", " +
                    "\"mobileBidModifier\" : 1, " +
                    "\"desktopBidModifier\" : 1, " +
                    "\"campaignStatus\" : \"ACTIVE\", " +
                    "\"biddingStrategyType\" : \"MANUAL_CPC\", " +
                    "\"startDate\" : " + DateUtil.getStartDate(DateUtil.getTime()) + ", " +
                    "\"endDate\" : " + DateUtil.getEndDate(5) + ", " +
                    "\"adRotation\": \"ROTATE_INDEFINITELY\", " +
                    "\"includeKeywordCloseVariant\": \"false\"}], " +
                    "\"allowPartialFailure\" : \"false\"}";
            headers.put("accountId", account.getAccountId().toString());

            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN, getAPIResponse(MethodEnum.POST, "account/campaign/create", headers, payload));

            assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Exceeds maximum number of campaigns in account");

        } finally {
            DBUtil.update("DELETE FROM PM_CUSTOM_LIMIT_VALIDATION WHERE PARENT_ID = ".concat(account.getAccountId().toString()));
        }
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = createBudget(account.getAccountId(), budgetAmount, deliveryMethod);

            testData.put("account", account);
            testData.put("budget", budget);
        }

        return testData;
    }

}