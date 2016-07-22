package com.yp.pm.kp.api.regression.campaign;


import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.CampaignDTO;
import com.yp.pm.kp.enums.model.AdRotationEnum;
import com.yp.pm.kp.enums.model.CampaignStatusEnum;
import com.yp.pm.kp.enums.model.NetworkTypeEnum;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.util.DateUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UpdateCampaignTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private static Map<String, Object> testData2;
    private Map<String, String> headers = getRequestHeader();

    public String generateCampaignString(long campaignID, long budgetID, String stat, String name, String sDate, String eDate) {
        return " {" +
                "\"campaignId\" : " + campaignID + ", " +
                "\"budgetId\" : " + budgetID + ", " +
                "\"campaignName\" : \"" + name + "\", " +
                "\"campaignStatus\" : \"" + stat + "\", " +
                "\"networkType\" : \"SEARCH_AND_PARTNER_NETWORK\", " +
                "\"mobileBidModifier\" : 1, " +
                "\"desktopBidModifier\" : 1, " +
                "\"startDate\" : \"" + sDate + "\", " +
                "\"endDate\" : \"" + eDate + "\", " +
                "\"adRotation\" : \"ROTATE_INDEFINITELY\", " +
                "\"servingStatus\" : \"SUSPENDED\", " +
                "\"dynamicUrl\" : \"www.url.com\", " +
                "\"includeKeywordCloseVariant\": \"false\"" + "}";
    }

    @Test
    public void testUpdateCampaign() throws Exception {
        String newCampaignName = "BUDGET_" + System.currentTimeMillis();
        String newStartDate = DateUtil.getStartDate(new Timestamp(System.currentTimeMillis()));
        String newEndDate = DateUtil.getEndDate(10);
        String payload = "{\"campaigns\" : [{" +
                "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"budgetId\" : " + ((Budget) getTestData().get("budget")).getBudgetId() + ", " +
                "\"campaignName\" : \"" + newCampaignName + "\", " +
                "\"campaignStatus\" : \"PAUSED\", " +
                "\"networkType\" : \"SEARCH_AND_PARTNER_NETWORK\", " +
                "\"mobileBidModifier\" : 1, " +
                "\"desktopBidModifier\" : 1, " +
                "\"startDate\" : \"" + newStartDate + "\", " +
                "\"endDate\" : \"" + newEndDate + "\", " +
                "\"adRotation\" : \"ROTATE_INDEFINITELY\", " +
                "\"servingStatus\" : \"SUSPENDED\", " +
                "\"dynamicUrl\" : \"www.url.com\"}], " +
                "\"allowPartialFailure\" : \"false\"}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN, getAPIResponse(MethodEnum.PUT, "account/campaign/update", headers, payload));
        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            CampaignDTO campaignDTO = (CampaignDTO) result.getObject();
            Campaign updatedCampaign = getCampaignById(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
            assertObjectEqual(updatedCampaign.getAccountId(), ((Account) getTestData().get("account")).getAccountId());
            assertObjectEqual(updatedCampaign.getBudgetId(), ((Budget) getTestData().get("budget")).getBudgetId());
            assertObjectEqual(campaignDTO.getId(), updatedCampaign.getCampaignId());
            assertObjectEqual(updatedCampaign.getCampaignName(), newCampaignName);
            assertObjectEqual(updatedCampaign.getCampaignStatus().toString(), CampaignStatusEnum.PAUSED.toString());
            assertObjectEqual(updatedCampaign.getNetworkType().toString(), NetworkTypeEnum.SEARCH_AND_PARTNER_NETWORK.toString());
            assertObjectEqual(updatedCampaign.getMobileBidModifier(), 1.0);
            assertObjectEqual(updatedCampaign.getDesktopBidModifier(), 1.0);
            assertObjectEqual(updatedCampaign.getStartDate(), newStartDate);
            assertObjectEqual(updatedCampaign.getEndDate(), newEndDate);
            assertObjectEqual(updatedCampaign.getAdRotation().toString(), AdRotationEnum.ROTATE_INDEFINITELY.toString());
            assertObjectEqual(updatedCampaign.getDynamicUrl(), "www.url.com");
        }
    }

    @Test
    public void testAllowPartialFailure() throws Exception {
        String newCampaignName = "BUDGET_" + System.currentTimeMillis();
        String newStartDate = DateUtil.getStartDate(new Timestamp(System.currentTimeMillis()));
        String newEndDate = DateUtil.getEndDate(10);
        String payload = "{\"campaigns\" : [{" +
                "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"budgetId\" : " + ((Budget) getTestData().get("budget")).getBudgetId() + ", " +
                "\"campaignName\" : \"" + newCampaignName + "\", " +
                "\"campaignStatus\" : \"PAUSED\", " +
                "\"networkType\" : \"SEARCH_AND_PARTNER_NETWORK\", " +
                "\"mobileBidModifier\" : 1, " +
                "\"desktopBidModifier\" : 1, " +
                "\"startDate\" : \"" + newStartDate + "\", " +
                "\"endDate\" : \"" + newEndDate + "\", " +
                "\"adRotation\" : \"ROTATE_INDEFINITELY\", " +
                "\"servingStatus\" : \"SUSPENDED\", " +
                "\"dynamicUrl\" : \"www.url.com\"}], " +
                "\"allowPartialFailure\" : \"true\"}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN, getAPIResponse(MethodEnum.PUT, "account/campaign/update", headers, payload));
        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            CampaignDTO campaignDTO = (CampaignDTO) result.getObject();
            Campaign updatedCampaign = getCampaignById(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
            assertObjectEqual(updatedCampaign.getAccountId(), ((Account) getTestData().get("account")).getAccountId());
            assertObjectEqual(updatedCampaign.getBudgetId(), ((Budget) getTestData().get("budget")).getBudgetId());
            assertObjectEqual(campaignDTO.getId(), updatedCampaign.getCampaignId());
            assertObjectEqual(updatedCampaign.getCampaignName(), newCampaignName);
            assertObjectEqual(updatedCampaign.getCampaignStatus().toString(), CampaignStatusEnum.PAUSED.toString());
            assertObjectEqual(updatedCampaign.getNetworkType().toString(), NetworkTypeEnum.SEARCH_AND_PARTNER_NETWORK.toString());
            assertObjectEqual(updatedCampaign.getMobileBidModifier(), 1.0);
            assertObjectEqual(updatedCampaign.getDesktopBidModifier(), 1.0);
            assertObjectEqual(updatedCampaign.getStartDate(), newStartDate);
            assertObjectEqual(updatedCampaign.getEndDate(), newEndDate);
            assertObjectEqual(updatedCampaign.getAdRotation().toString(), AdRotationEnum.ROTATE_INDEFINITELY.toString());
            assertObjectEqual(updatedCampaign.getDynamicUrl(), "www.url.com");
        }
    }

    @Test
    public void testIgnoreUpdateCampaign() throws Exception {
        String newCampaignName = "BUDGET_" + System.currentTimeMillis();
        String newStartDate = DateUtil.getStartDate(new Timestamp(System.currentTimeMillis()));
        String newEndDate = DateUtil.getEndDate(10);
        String payload = "{\"campaigns\" : [{" +
                "\"campaignId\" :  -1, " +
                "\"budgetId\" : " + ((Budget) getTestData2().get("budget")).getBudgetId() + ", " +
                "\"campaignName\" : \"" + newCampaignName + "\", " +
                "\"campaignStatus\" : \"PAUSED\", " +
                "\"networkType\" : \"SEARCH_AND_PARTNER_NETWORK\", " +
                "\"mobileBidModifier\" : 1, " +
                "\"desktopBidModifier\" : 1, " +
                "\"startDate\" : \"" + newStartDate + "\", " +
                "\"endDate\" : \"" + newEndDate + "\", " +
                "\"adRotation\" : \"ROTATE_INDEFINITELY\", " +
                "\"servingStatus\" : \"SUSPENDED\", " +
                "\"dynamicUrl\" : \"www.url.com\", " +
                "\"includeKeywordCloseVariant\": \"false\"}], " +
                "\"allowPartialFailure\" : \"false\"}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN, getAPIResponse(MethodEnum.PUT, "account/campaign/update", headers, payload));
        assertResponseError(responseDTO);


        for (ResultsDTO result : responseDTO.getResults()) {
            CampaignDTO campaignDTO = (CampaignDTO) result.getObject();
            Campaign updatedCampaign = getCampaignById(((Account) getTestData2().get("account")).getAccountId(), ((Campaign) getTestData2().get("campaign")).getCampaignId());
            assertObjectEqual(updatedCampaign.getAccountId(), ((Account) getTestData2().get("account")).getAccountId());
            assertObjectEqual(updatedCampaign.getBudgetId(), ((Budget) getTestData2().get("budget")).getBudgetId());
            assertObjectEqual(campaignDTO.getId(), -1);
            assertConditionTrue(!updatedCampaign.getCampaignName().equals(newCampaignName));
            assertObjectEqual(updatedCampaign.getNetworkType().toString(), NetworkTypeEnum.SEARCH_NETWORK.toString());
            assertObjectEqual(updatedCampaign.getCampaignStatus().toString(), CampaignStatusEnum.ACTIVE.toString());
            assertObjectEqual(updatedCampaign.getMobileBidModifier(), 1.0);
            assertObjectEqual(updatedCampaign.getDesktopBidModifier(), 1.0);
            assertObjectEqual(updatedCampaign.getStartDate(), newStartDate);
            assertObjectEqual(updatedCampaign.getAdRotation().toString(), AdRotationEnum.ROTATE_INDEFINITELY.toString());
            assertObjectEqual(updatedCampaign.getIncludeKeywordCloseVariant(), true);
            assertObjectEqual(updatedCampaign.getDynamicUrl(), "www.test.com");
        }
    }

    @Test
    public void testObjectProblemType() throws Exception {
        long accountId = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles").getAccountId();
        Budget budget = createBudget(accountId, budgetAmount, deliveryMethod);
        Campaign campaign1 = createCampaignOnly(accountId, createBudget(accountId, budgetAmount, deliveryMethod).getBudgetId());
        Campaign campaign2 = createCampaignOnly(accountId, createBudget(accountId, budgetAmount, deliveryMethod).getBudgetId());
        Campaign campaign3 = createCampaignOnly(accountId, createBudget(accountId, budgetAmount, deliveryMethod).getBudgetId());

        String newCampaignName = "BUDGET_" + System.currentTimeMillis();
        String newStartDate = DateUtil.getStartDate(new Timestamp(System.currentTimeMillis()));
        String newEndDate = DateUtil.getEndDate(10);

        String campaign1String = generateCampaignString(campaign1.getCampaignId(), budget.getBudgetId(), "PAUSED", newCampaignName, newStartDate, newEndDate);
        String campaign2String = generateCampaignString(campaign2.getCampaignId(), budget.getBudgetId(), "DELETED", newCampaignName, newStartDate, newEndDate);
        String campaign3String = generateCampaignString(campaign3.getCampaignId(), budget.getBudgetId(), "ACTIVE", newCampaignName, newStartDate, newEndDate);
        String command = "curl -X PUT " +
                "-d '{\"campaigns\" : [" + campaign1String + "," + campaign2String + "," + campaign3String + "], " +
                "\"allowPartialFailure\" : \"false\"}' " +
                "-H '" + contentType + "' " +
                "-H 'username:'" + userName + "'' " +
                "-H 'requestid:'" + requestId + "'' " +
                "-H 'accountId:'" + accountId + "'' " +
                "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/account/campaign/update";

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN, getAPIResponse(command));

        for (ResultsDTO result : responseDTO.getResults()) {
            CampaignDTO campaignDTO = (CampaignDTO) result.getObject();
            if (result.getError().getMessage() != null) {
                assertObjectEqual(campaignDTO.getId(), -3);
            } else {
                assertObjectEqual(campaignDTO.getId(), -2);
            }
        }
        assertObjectEqual(getCampaignById(accountId, campaign1.getCampaignId()).getCampaignStatus().toString(), CampaignStatusEnum.ACTIVE.toString());
        assertObjectEqual(getCampaignById(accountId, campaign2.getCampaignId()).getCampaignStatus().toString(), CampaignStatusEnum.ACTIVE.toString());
        assertObjectEqual(getCampaignById(accountId, campaign3.getCampaignId()).getCampaignStatus().toString(), CampaignStatusEnum.ACTIVE.toString());
        assertConditionTrue(!getCampaignById(accountId, campaign1.getCampaignId()).getCampaignName().equals(CampaignStatusEnum.ACTIVE.toString()));
        assertConditionTrue(!getCampaignById(accountId, campaign2.getCampaignId()).getCampaignName().equals(CampaignStatusEnum.ACTIVE.toString()));
        assertConditionTrue(!getCampaignById(accountId, campaign3.getCampaignId()).getCampaignName().equals(CampaignStatusEnum.ACTIVE.toString()));

    }

    @Test
    public void testInvalidCampaignId() throws Exception {
        String payload = "{\"campaigns\" : [{" +
                "\"campaignId\" : \"12345\", " +
                "\"budgetId\" : " + ((Budget) getTestData().get("budget")).getBudgetId() + ", " +
                "\"campaignStatus\" : \"PAUSED\", " +
                "\"networkType\" : \"SEARCH_AND_PARTNER_NETWORK\", " +
                "\"mobileBidModifier\" : 1, " +
                "\"desktopBidModifier\" : 1, " +
                "\"adRotation\" : \"ROTATE_INDEFINITELY\", " +
                "\"servingStatus\" : \"SUSPENDED\", " +
                "\"dynamicUrl\" : \"www.url.com\", " +
                "\"includeKeywordCloseVariant\": \"false\"}], " +
                "\"allowPartialFailure\" : \"true\"}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN, getAPIResponse(MethodEnum.PUT, "account/campaign/update", headers, payload));
        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 0);

        for (ResultsDTO result : responseDTO.getResults()) {
            assertObjectEqual(((CampaignDTO) result.getObject()).getId(), -1);
        }
    }

    @Test
    public void testInvalidMobileBidModifier() throws Exception {
        String payload = "{\"campaigns\" : [{" +
                "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"budgetId\" : " + ((Budget) getTestData().get("budget")).getBudgetId() + ", " +
                "\"campaignStatus\" : \"ACTIVE\", " +
                "\"networkType\" : \"SEARCH_AND_PARTNER_NETWORK\", " +
                "\"mobileBidModifier\" : 5, " +
                "\"desktopBidModifier\" : 1, " +
                "\"adRotation\" : \"ROTATE_INDEFINITELY\", " +
                "\"servingStatus\" : \"SUSPENDED\", " +
                "\"dynamicUrl\" : \"www.url.com\", " +
                "\"includeKeywordCloseVariant\": \"false\"}], " +
                "\"allowPartialFailure\" : \"true\"}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN, getAPIResponse(MethodEnum.PUT, "account/campaign/update", headers, payload));
        assertObjectEqual(responseDTO.getTotalCount(), 0);
        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Mobile bid adjustment should be -100% (0.0) or between -90% (0.1) and 300% (4.0)");

    }

    @Test
    public void testInvalidDesktopBidModifier() throws Exception {
        String payload = "{\"campaigns\" : [{" +
                "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"budgetId\" : " + ((Budget) getTestData().get("budget")).getBudgetId() + ", " +
                "\"campaignStatus\" : \"ACTIVE\", " +
                "\"networkType\" : \"SEARCH_AND_PARTNER_NETWORK\", " +
                "\"mobileBidModifier\" : 1, " +
                "\"desktopBidModifier\" : 2, " +
                "\"adRotation\" : \"ROTATE_INDEFINITELY\", " +
                "\"servingStatus\" : \"SUSPENDED\", " +
                "\"dynamicUrl\" : \"www.url.com\", " +
                "\"includeKeywordCloseVariant\": \"false\"}], " +
                "\"allowPartialFailure\" : \"false\"}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN, getAPIResponse(MethodEnum.PUT, "account/campaign/update", headers, payload));
        assertObjectEqual(responseDTO.getTotalCount(), 0);
        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Desktop bid adjustment should be -100% (0.0) or 0% (1.0)");

    }

    private Map<String, Object> getTestData2() throws Exception {
        if (testData2 == null) {
            testData2 = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = createBudget(account.getAccountId(), budgetAmount, deliveryMethod);
            Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

            testData2.put("account", account);
            testData2.put("budget", budget);
            testData2.put("campaign", campaign);
        }

        return testData2;
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = createBudget(account.getAccountId(), budgetAmount, deliveryMethod);
            Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

            testData.put("account", account);
            testData.put("budget", budget);
            testData.put("campaign", campaign);
        }

        return testData;
    }
}