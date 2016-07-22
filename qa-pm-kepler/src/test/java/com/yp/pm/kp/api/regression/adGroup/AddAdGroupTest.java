package com.yp.pm.kp.api.regression.adGroup;

import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.AdGroupDTO;
import com.yp.pm.kp.enums.model.AdGroupStatusEnum;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.AdGroup;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.util.DBUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddAdGroupTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testAddAdGroup() throws Exception {
        String payload = "{\"adGroups\" : [{" +
                "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"adGroupName\" : " + "\"AG_" + System.currentTimeMillis() + "\", " +
                "\"maxCpc\" : { \"microAmount\" : 10000000 }}, " +
                "{\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"adGroupName\" : " + "\"AG_1" + System.currentTimeMillis() + "\", " +
                "\"maxCpc\" : { \"microAmount\" : 1000000 }}, " +
                "{\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"adGroupName\" : " + "\"AG_2" + System.currentTimeMillis() + "\", " +
                "\"maxCpc\" : { \"microAmount\" : 100000 }}, " +
                "{\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"adGroupName\" : " + "\"AG_3" + System.currentTimeMillis() + "\", " +
                "\"maxCpc\" : { \"microAmount\" : 10000 }}], " +
                "\"allowPartialFailure\" : \"false\"}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD_GROUP, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/create", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 4);

        int i = 0;
        for (ResultsDTO result : responseDTO.getResults()) {
            assertConditionTrue(result.getIndex() == i);
            i += 1;
            AdGroupDTO adGroupDTO = (AdGroupDTO) result.getObject();
            AdGroup adGroup = getAdGroupById(((Account) getTestData().get("account")).getAccountId(), adGroupDTO.getId());
            assertObjectEqual(adGroupDTO.getId(), adGroup.getAdGroupId());
        }
    }

    @Test
    public void testAddAdGroupAllowPartialFailure() throws Exception {
        String payload = "{\"adGroups\" : [{" +
                "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"adGroupName\" : " + "\"AG_" + System.currentTimeMillis() + "\", " +
                "\"maxCpc\" : { \"microAmount\" : 10000000 }," +
                "\"mobileBidModifier\" : 1}], " +
                "\"allowPartialFailure\" : \"true\"}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD_GROUP, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/create", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            AdGroupDTO adGroupDTO = (AdGroupDTO) result.getObject();
            AdGroup adGroup = getAdGroupById(((Account) getTestData().get("account")).getAccountId(), adGroupDTO.getId());
            assertObjectEqual(adGroupDTO.getId(), adGroup.getAdGroupId());
        }
    }

    @Test
    public void testDuplicateNameWithActiveAdGroup() throws Exception {
        String adGroupName = "AG_" + +System.currentTimeMillis();
        String payload = "{\"adGroups\" : [{" +
                "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"adGroupName\" : " + "\"" + adGroupName + "\", " +
                "\"maxCpc\" : { \"microAmount\" : 10000000 }}, " +
                "{\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"adGroupName\" : " + "\"" + adGroupName + "\", " +
                "\"maxCpc\" : { \"microAmount\" : 10000 }}], " +
                "\"allowPartialFailure\" : \"false\"}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD_GROUP, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/create", headers, payload));

        assertObjectEqual(responseDTO.getTotalCount(), 0);

        int i = 0;
        for (ResultsDTO result : responseDTO.getResults()) {
            assertConditionTrue(result.getIndex() == i);

            AdGroupDTO adGroupDTO = (AdGroupDTO) result.getObject();
            assertConditionTrue(adGroupDTO.getId() == -2 || adGroupDTO.getId() == -3);
            if (adGroupDTO.getId() == -3) {
                assertConditionTrue(result.getError().getType().equals(ErrorTypeEnum.USER_ERROR));
                assertConditionTrue(result.getError().getMessage().equalsIgnoreCase("Duplicate ad group"));
            }
            i += 1;
        }
    }

    @Test
    public void testDuplicateNameWithActiveAdGroupAllowPartialFailure() throws Exception {
        String adGroupName = "AG_" + +System.currentTimeMillis();
        String payload = "{\"adGroups\" : [{" +
                "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"adGroupName\" : " + "\"" + adGroupName + "\", " +
                "\"maxCpc\" : { \"microAmount\" : 10000000 }}, " +
                "{\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"adGroupName\" : " + "\"" + adGroupName + "\", " +
                "\"maxCpc\" : { \"microAmount\" : 10000 }}], " +
                "\"allowPartialFailure\" : \"true\"}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD_GROUP, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/create", headers, payload));

        assertObjectEqual(responseDTO.getTotalCount(), 1);

        int i = 0;
        for (ResultsDTO result : responseDTO.getResults()) {
            assertConditionTrue(result.getIndex() == i);
            AdGroupDTO adGroupDTO = (AdGroupDTO) result.getObject();

            if (result.getError().getCode() != 0) {
                assertConditionTrue(result.getError().getType().equals(ErrorTypeEnum.USER_ERROR));
                assertConditionTrue(result.getError().getMessage().equalsIgnoreCase("Duplicate ad group"));
                assertConditionTrue(adGroupDTO.getId() == -3);
            } else {
                assertConditionTrue(adGroupDTO.getId() != -1 && adGroupDTO.getId() != -2 && adGroupDTO.getId() != -3);
            }
            i += 1;
        }
    }

    @Test
    public void testDuplicateNameWithPausedAdGroup() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long campaignId = ((Campaign) getTestData().get("campaign")).getCampaignId();

        // new ad group with paused status
        AdGroup newAdGroup = createAdGroupOnly(accountId, campaignId);
        String adGroupName = newAdGroup.getAdGroupName();
        List<AdGroup> adGroupList = new ArrayList<>();
        newAdGroup.setAdGroupStatus(AdGroupStatusEnum.PAUSED);
        adGroupList.add(newAdGroup);
        updateAdGroups(adGroupList).get(0);

        // add new ad group with active status
        String payload = "{\"adGroups\" : [{" +
                "\"campaignId\" : " + campaignId + ", " +
                "\"adGroupName\" : " + "\"" + adGroupName + "\", " +
                "\"maxCpc\" : { \"microAmount\" : 10000000 }}], " +
                "\"allowPartialFailure\" : \"false\"}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD_GROUP, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/create", headers, payload));

        assertObjectEqual(responseDTO.getTotalCount(), 0);

        int i = 0;
        for (ResultsDTO result : responseDTO.getResults()) {
            assertConditionTrue(result.getIndex() == i);
            AdGroupDTO adGroupDTO = (AdGroupDTO) result.getObject();
            assertConditionTrue(adGroupDTO.getId() == -3);
            assertConditionTrue(result.getError().getType().equals(ErrorTypeEnum.USER_ERROR));
            assertConditionTrue(result.getError().getMessage().equalsIgnoreCase("Duplicate ad group"));
            i += 1;
        }
    }

    @Test
    public void testDuplicateNameWithPausedAdGroupAllowPartialFailure() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long campaignId = ((Campaign) getTestData().get("campaign")).getCampaignId();

        // new ad group with paused status
        AdGroup newAdGroup = createAdGroupOnly(accountId, campaignId);
        String adGroupName = newAdGroup.getAdGroupName();
        List<AdGroup> adGroupList = new ArrayList<>();
        newAdGroup.setAdGroupStatus(AdGroupStatusEnum.PAUSED);
        adGroupList.add(newAdGroup);
        updateAdGroups(adGroupList).get(0);

        // add new ad group with active status
        String payload = "{\"adGroups\" : [{" +
                "\"campaignId\" : " + campaignId + ", " +
                "\"adGroupName\" : " + "\"" + adGroupName + "\", " +
                "\"maxCpc\" : { \"microAmount\" : 10000000 }}], " +
                "\"allowPartialFailure\" : \"true\"}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD_GROUP, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/create", headers, payload));

        assertObjectEqual(responseDTO.getTotalCount(), 0);

        int i = 0;
        for (ResultsDTO result : responseDTO.getResults()) {
            assertConditionTrue(result.getIndex() == i);
            AdGroupDTO adGroupDTO = (AdGroupDTO) result.getObject();
            assertConditionTrue(adGroupDTO.getId() == -3);
            assertConditionTrue(result.getError().getType().equals(ErrorTypeEnum.USER_ERROR));
            assertConditionTrue(result.getError().getMessage().equalsIgnoreCase("Duplicate ad group"));
            i += 1;
        }
    }

    @Test
    public void testDuplicateNameWithDeleteAdGroup() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long campaignId = ((Campaign) getTestData().get("campaign")).getCampaignId();

        // new ad group with delete status
        AdGroup newAdGroup = createAdGroupOnly(accountId, campaignId);
        String adGroupName = newAdGroup.getAdGroupName();
        List<Long> adGroupList = new ArrayList<>();
        adGroupList.add(newAdGroup.getAdGroupId());
        deleteAdGroups(accountId, adGroupList);

        // add new ad group with active status
        String payload = "{\"adGroups\" : [{" +
                "\"campaignId\" : " + campaignId + ", " +
                "\"adGroupName\" : " + "\"" + adGroupName + "\", " +
                "\"maxCpc\" : { \"microAmount\" : 10000000 }}], " +
                "\"allowPartialFailure\" : \"false\"}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD_GROUP, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/create", headers, payload));

        assertObjectEqual(responseDTO.getTotalCount(), 1);

        int i = 0;
        for (ResultsDTO result : responseDTO.getResults()) {
            assertConditionTrue(result.getIndex() == i);
            AdGroupDTO adGroupDTO = (AdGroupDTO) result.getObject();
            AdGroup adGroup = getAdGroupById(accountId, adGroupDTO.getId());
            assertObjectEqual(adGroupDTO.getId(), adGroup.getAdGroupId());
            i += 1;
        }
    }

    @Test
    public void testDuplicateNameWithDeleteAdGroupAllowPartialFailure() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long campaignId = ((Campaign) getTestData().get("campaign")).getCampaignId();

        // new ad group with delete status
        AdGroup newAdGroup = createAdGroupOnly(accountId, campaignId);
        String adGroupName = newAdGroup.getAdGroupName();
        List<Long> adGroupList = new ArrayList<>();
        adGroupList.add(newAdGroup.getAdGroupId());
        deleteAdGroups(accountId, adGroupList);

        // add new ad group with active status
        String payload = "{\"adGroups\" : [{" +
                "\"campaignId\" : " + campaignId + ", " +
                "\"adGroupName\" : " + "\"" + adGroupName + "\", " +
                "\"maxCpc\" : { \"microAmount\" : 10000000 }}], " +
                "\"allowPartialFailure\" : \"true\"}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD_GROUP, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/create", headers, payload));

        assertObjectEqual(responseDTO.getTotalCount(), 1);

        int i = 0;
        for (ResultsDTO result : responseDTO.getResults()) {
            assertConditionTrue(result.getIndex() == i);
            AdGroupDTO adGroupDTO = (AdGroupDTO) result.getObject();
            AdGroup adGroup = getAdGroupById(accountId, adGroupDTO.getId());
            assertObjectEqual(adGroupDTO.getId(), adGroup.getAdGroupId());
            i += 1;
        }
    }

    @Test
    public void testAddAdGroupCustomLimit() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");

        try {
            DBUtil.update("INSERT INTO PM_CUSTOM_LIMIT_VALIDATION VALUES('ad_group', 5, ".concat(account.getAccountId().toString()).concat(", 'account', '").concat(userName) + "',SYSDATE,'" + userName + "',SYSDATE)");
            Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
            int count = 1;
            while (count < 6) {
                createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());
                count++;
            }

            String payload = "{\"adGroups\" : [{" +
                    "\"campaignId\" : " + campaign.getCampaignId() + ", " +
                    "\"adGroupName\" : " + "\"AG_" + System.currentTimeMillis() + "\", " +
                    "\"maxCpc\" : { \"microAmount\" : 10000000 }}], " +
                    "\"allowPartialFailure\" : \"false\"}";
            headers.put("accountId", account.getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD_GROUP, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/create", headers, payload));

            assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Exceeds maximum number of ad groups in campaign");
        } finally {
            DBUtil.update("DELETE FROM PM_CUSTOM_LIMIT_VALIDATION WHERE PARENT_ID = ".concat(account.getAccountId().toString()));
        }
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = createBudget(account.getAccountId(), budgetAmount, deliveryMethod);
            Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
            testData.put("account", account);
            testData.put("campaign", campaign);
        }

        return testData;
    }
}