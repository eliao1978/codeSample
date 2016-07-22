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
public class UpdateAdGroupTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private static Map<String, Object> testData2;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testUpdateAdGroup() throws Exception {
        String newAdGroupName = "AG_" + System.currentTimeMillis();
        String payload = "{\"adGroups\" : [{" +
                "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"adGroupId\" : " + ((AdGroup) getTestData().get("adGroup")).getAdGroupId() + ", " +
                "\"adGroupStatus\" : \"PAUSED\", " +
                "\"adGroupName\" : \"" + newAdGroupName + "\", " +
                "\"maxCpc\" : { \"microAmount\" : 20000000}," +
                "\"mobileBidModifier\" : 2}], " +
                "\"allowPartialFailure\" : \"false\"}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD_GROUP, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/update", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            AdGroupDTO adGroupDTO = (AdGroupDTO) result.getObject();
            AdGroup updatedAdGroup = getAdGroupById(((Account) getTestData().get("account")).getAccountId(), adGroupDTO.getId());
            assertObjectEqual(adGroupDTO.getId(), updatedAdGroup.getAdGroupId());
            assertObjectEqual(updatedAdGroup.getAdGroupStatus().toString(), "PAUSED");
            assertObjectEqual(updatedAdGroup.getAdGroupName(), newAdGroupName);
            assertObjectEqual(updatedAdGroup.getMaxCpc().getMicroAmount().toMicroDollar(), 20000000);
            assertObjectEqual(updatedAdGroup.getMobileBidModifier(), 2.0);
        }
    }

    @Test
    public void testUpdateAdGroupAllowPartialFailure() throws Exception {
        String newAdGroupName = "AG_" + System.currentTimeMillis();
        String payload = "{\"adGroups\" : [{" +
                "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"adGroupId\" : " + ((AdGroup) getTestData().get("adGroup")).getAdGroupId() + ", " +
                "\"adGroupStatus\" : \"PAUSED\", " +
                "\"adGroupName\" : \"" + newAdGroupName + "\", " +
                "\"maxCpc\" : { \"microAmount\" : 20000000}," +
                "\"mobileBidModifier\" : 2}], " +
                "\"allowPartialFailure\" : \"true\"}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD_GROUP, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/update", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            AdGroupDTO adGroupDTO = (AdGroupDTO) result.getObject();
            AdGroup updatedAdGroup = getAdGroupById(((Account) getTestData().get("account")).getAccountId(), adGroupDTO.getId());
            assertObjectEqual(adGroupDTO.getId(), updatedAdGroup.getAdGroupId());
            assertObjectEqual(updatedAdGroup.getAdGroupStatus().toString(), "PAUSED");
            assertObjectEqual(updatedAdGroup.getAdGroupName(), newAdGroupName);
            assertObjectEqual(updatedAdGroup.getMaxCpc().getMicroAmount().toMicroDollar(), 20000000);
            assertObjectEqual(updatedAdGroup.getMobileBidModifier(), 2.0);
        }
    }

    @Test
    public void testDuplicateNameWithActiveAdGroup() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long campaignId = ((Campaign) getTestData().get("campaign")).getCampaignId();

        // create ad group 1
        AdGroup newAdGroup1 = createAdGroupOnly(accountId, campaignId);
        String adGroupName1 = newAdGroup1.getAdGroupName();

        // create ad group 2
        AdGroup newAdGroup2 = createAdGroupOnly(accountId, campaignId);

        // update ad group 2 with duplicate name as ad group 1
        String payload = "{\"adGroups\" : [{" +
                "\"campaignId\" : " + campaignId + ", " +
                "\"adGroupId\" : " + newAdGroup2.getAdGroupId() + ", " +
                "\"adGroupName\" : \"" + adGroupName1 + "\"}], " +
                "\"allowPartialFailure\" : \"false\"}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD_GROUP, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/update", headers, payload));

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
    public void testDuplicateNameWithActiveAdGroupAllowPartialFailure() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long campaignId = ((Campaign) getTestData().get("campaign")).getCampaignId();

        // create ad group 1
        AdGroup newAdGroup1 = createAdGroupOnly(accountId, campaignId);
        String adGroupName1 = newAdGroup1.getAdGroupName();

        // create ad group 2
        AdGroup newAdGroup2 = createAdGroupOnly(accountId, campaignId);

        // update ad group 2 with duplicate name as ad group 1
        String payload = "{\"adGroups\" : [{" +
                "\"campaignId\" : " + campaignId + ", " +
                "\"adGroupId\" : " + newAdGroup2.getAdGroupId() + ", " +
                "\"adGroupName\" : \"" + adGroupName1 + "\"}], " +
                "\"allowPartialFailure\" : \"true\"}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD_GROUP, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/update", headers, payload));

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
    public void testDuplicateNameWithPausedAdGroup() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long campaignId = ((Campaign) getTestData().get("campaign")).getCampaignId();

        // create ad group 1
        AdGroup adGroup1 = createAdGroupOnly(accountId, campaignId);
        String adGroupName1 = adGroup1.getAdGroupName();
        List<AdGroup> adGroupList = new ArrayList<>();
        adGroup1.setAdGroupStatus(AdGroupStatusEnum.PAUSED);
        adGroupList.add(adGroup1);
        updateAdGroups(adGroupList).get(0);

        // create ad group 2
        AdGroup newAdGroup2 = createAdGroupOnly(accountId, campaignId);

        // update ad group 2 with duplicate name as ad group 1
        String payload = "{\"adGroups\" : [{" +
                "\"campaignId\" : " + campaignId + ", " +
                "\"adGroupId\" : " + newAdGroup2.getAdGroupId() + ", " +
                "\"adGroupName\" : \"" + adGroupName1 + "\"}], " +
                "\"allowPartialFailure\" : \"false\"}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD_GROUP, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/update", headers, payload));

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

        // create ad group 1
        AdGroup adGroup1 = createAdGroupOnly(accountId, campaignId);
        String adGroupName1 = adGroup1.getAdGroupName();
        List<AdGroup> adGroupList = new ArrayList<>();
        adGroup1.setAdGroupStatus(AdGroupStatusEnum.PAUSED);
        adGroupList.add(adGroup1);
        updateAdGroups(adGroupList).get(0);

        // create ad group 2
        AdGroup newAdGroup2 = createAdGroupOnly(accountId, campaignId);

        // update ad group 2 with duplicate name as ad group 1
        String payload = "{\"adGroups\" : [{" +
                "\"campaignId\" : " + campaignId + ", " +
                "\"adGroupId\" : " + newAdGroup2.getAdGroupId() + ", " +
                "\"adGroupName\" : \"" + adGroupName1 + "\"}], " +
                "\"allowPartialFailure\" : \"true\"}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD_GROUP, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/update", headers, payload));

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
    public void testDuplicateNameWithDeletedAdGroup() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long campaignId = ((Campaign) getTestData().get("campaign")).getCampaignId();

        // create ad group 1
        AdGroup adGroup1 = createAdGroupOnly(accountId, campaignId);
        String adGroupName1 = adGroup1.getAdGroupName();
        List<Long> adGroupList = new ArrayList<>();
        adGroupList.add(adGroup1.getAdGroupId());
        deleteAdGroups(accountId, adGroupList);

        // create ad group 2
        AdGroup newAdGroup2 = createAdGroupOnly(accountId, campaignId);

        // update ad group 2 with duplicate name as ad group 1
        String payload = "{\"adGroups\" : [{" +
                "\"campaignId\" : " + campaignId + ", " +
                "\"adGroupId\" : " + newAdGroup2.getAdGroupId() + ", " +
                "\"adGroupName\" : \"" + adGroupName1 + "\"}], " +
                "\"allowPartialFailure\" : \"false\"}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD_GROUP, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/update", headers, payload));

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
    public void testDuplicateNameWithDeletedAdGroupAllowPartialFailure() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long campaignId = ((Campaign) getTestData().get("campaign")).getCampaignId();

        // create ad group 1
        AdGroup adGroup1 = createAdGroupOnly(accountId, campaignId);
        String adGroupName1 = adGroup1.getAdGroupName();
        List<Long> adGroupList = new ArrayList<>();
        adGroupList.add(adGroup1.getAdGroupId());
        deleteAdGroups(accountId, adGroupList);

        // create ad group 2
        AdGroup newAdGroup2 = createAdGroupOnly(accountId, campaignId);

        // update ad group 2 with duplicate name as ad group 1
        String payload = "{\"adGroups\" : [{" +
                "\"campaignId\" : " + campaignId + ", " +
                "\"adGroupId\" : " + newAdGroup2.getAdGroupId() + ", " +
                "\"adGroupName\" : \"" + adGroupName1 + "\"}], " +
                "\"allowPartialFailure\" : \"true\"}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD_GROUP, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/update", headers, payload));

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
    public void testIgnoreUpdateAdGroup() throws Exception {
        String newAdGroupName = "AG_" + System.currentTimeMillis();
        String payload = "{\"adGroups\" : [{" +
                "\"campaignId\" : " + ((Campaign) getTestData2().get("campaign")).getCampaignId() + ", " +
                "\"adGroupId\" : -1, " +
                "\"adGroupStatus\" : \"PAUSED\", " +
                "\"adGroupName\" : \"" + newAdGroupName + "\", " +
                "\"maxCpc\" : { \"microAmount\" : 20000000 }}], " +
                "\"allowPartialFailure\" : \"false\"}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD_GROUP, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/update", headers, payload));

        assertResponseError(responseDTO);

        for (ResultsDTO result : responseDTO.getResults()) {
            AdGroupDTO adGroupDTO = (AdGroupDTO) result.getObject();
            AdGroup updatedAdGroup = getAdGroupById(((Account) getTestData2().get("account")).getAccountId(), ((AdGroup) getTestData2().get("adGroup")).getAdGroupId());
            assertObjectEqual(adGroupDTO.getId(), -1);
            assertObjectEqual(updatedAdGroup.getAdGroupStatus().toString(), "ACTIVE");
            assertObjectEqual(updatedAdGroup.getMaxCpc().getMicroAmount().toMicroDollar(), 1000000);
        }
    }

    @Test
    public void testObjectProblemType() throws Exception {
        long accountId = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles").getAccountId();
        Campaign campaign = createCampaignOnly(accountId, createBudget(accountId, budgetAmount, deliveryMethod).getBudgetId());
        AdGroup adGroup1 = createAdGroupOnly(accountId, campaign.getCampaignId());
        AdGroup adGroup2 = createAdGroupOnly(accountId, campaign.getCampaignId());
        AdGroup adGroup3 = createAdGroupOnly(accountId, campaign.getCampaignId());
        long amount = adGroup1.getMaxCpc().getMicroAmount().toMicroDollar();
        String newAdGroupName = "AG_" + System.currentTimeMillis();
        String adGroupString1 = generateAdGroupString(campaign.getCampaignId(), adGroup1.getAdGroupId(), "PAUSED", newAdGroupName);
        String adGroupString2 = generateAdGroupString(campaign.getCampaignId(), adGroup2.getAdGroupId(), "DELETED", newAdGroupName);
        String adGroupString3 = generateAdGroupString(campaign.getCampaignId(), adGroup3.getAdGroupId(), "ACTIVE", newAdGroupName);

        String command = "curl -X PUT " +
                "-d '{\"adGroups\" : [" + adGroupString1 + "," + adGroupString2 + "," + adGroupString3 + "], " +
                "\"allowPartialFailure\" : \"false\"}' " +
                "-H '" + contentType + "' " +
                "-H 'username:'" + userName + "'' " +
                "-H 'requestid:'" + requestId + "'' " +
                "-H 'accountId:'" + accountId + "'' " +
                "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/account/campaign/adgroup/update";

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD_GROUP, getAPIResponse(command));

        for (ResultsDTO result : responseDTO.getResults()) {
            AdGroupDTO adGroupDTO = (AdGroupDTO) result.getObject();
            if (result.getError().getMessage() != null) {
                assertObjectEqual(adGroupDTO.getId(), -3);
            } else {
                assertObjectEqual(adGroupDTO.getId(), -2);
            }
        }

        assertObjectEqual(getAdGroupById(accountId, adGroup1.getAdGroupId()).getAdGroupStatus().toString(), "ACTIVE");
        assertObjectEqual(getAdGroupById(accountId, adGroup2.getAdGroupId()).getAdGroupStatus().toString(), "ACTIVE");
        assertObjectEqual(getAdGroupById(accountId, adGroup3.getAdGroupId()).getAdGroupStatus().toString(), "ACTIVE");
        assertObjectEqual(getAdGroupById(accountId, adGroup1.getAdGroupId()).getMaxCpc().getMicroAmount().toMicroDollar(), amount);
        assertObjectEqual(getAdGroupById(accountId, adGroup2.getAdGroupId()).getMaxCpc().getMicroAmount().toMicroDollar(), amount);
        assertObjectEqual(getAdGroupById(accountId, adGroup3.getAdGroupId()).getMaxCpc().getMicroAmount().toMicroDollar(), amount);
    }

    @Test
    public void testInvalidAdGroupId() throws Exception {
        String newAdGroupName = "AG_" + System.currentTimeMillis();
        String payload = "{\"adGroups\" : [{" +
                "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"adGroupId\" : \"12345\", " +
                "\"adGroupStatus\" : \"PAUSED\", " +
                "\"adGroupName\" : \"" + newAdGroupName + "\", " +
                "\"maxCpc\" : { \"microAmount\" : 20000000 }}], " +
                "\"allowPartialFailure\" : \"false\"}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD_GROUP, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/update", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 0);

        for (ResultsDTO result : responseDTO.getResults()) {
            assertObjectEqual(((AdGroupDTO) result.getObject()).getId(), -1);
        }
    }

    @Test
    public void testMissingAdGroupId() throws Exception {
        String newAdGroupName = "AG_" + System.currentTimeMillis();
        String payload = "{\"adGroups\" : [{" +
                "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"adGroupId\" : \"\", " +
                "\"adGroupStatus\" : \"PAUSED\", " +
                "\"adGroupName\" : \"" + newAdGroupName + "\", " +
                "\"maxCpc\" : { \"microAmount\" : 20000000 }}], " +
                "\"allowPartialFailure\" : \"false\"}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD_GROUP, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/update", headers, payload));

        assertObjectEqual(responseDTO.getTotalCount(), 0);
        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Ad group id can not be null");
    }

    @Test
    public void testInvalidCampaignId() throws Exception {
        String newAdGroupName = "AG_" + System.currentTimeMillis();
        String payload = "{\"adGroups\" : [{" +
                "\"campaignId\" : \"12345\", " +
                "\"adGroupId\" : " + ((AdGroup) getTestData().get("adGroup")).getAdGroupId() + ", " +
                "\"adGroupStatus\" : \"PAUSED\", " +
                "\"adGroupName\" : \"" + newAdGroupName + "\", " +
                "\"maxCpc\" : { \"microAmount\" : 20000000 }}], " +
                "\"allowPartialFailure\" : \"false\"}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD_GROUP, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/update", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            AdGroupDTO adGroupDTO = (AdGroupDTO) result.getObject();
            assertObjectEqual(adGroupDTO.getId(), ((AdGroup) getTestData().get("adGroup")).getAdGroupId());
        }
    }

    @Test
    public void testMissingCampaignId() throws Exception {
        String newAdGroupName = "AG_" + System.currentTimeMillis();
        String payload = "{\"adGroups\" : [{" +
                "\"campaignId\" : \"\", " +
                "\"adGroupId\" : " + ((AdGroup) getTestData().get("adGroup")).getAdGroupId() + ", " +
                "\"adGroupStatus\" : \"PAUSED\", " +
                "\"adGroupName\" : \"" + newAdGroupName + "\", " +
                "\"maxCpc\" : { \"microAmount\" : 20000000 }}], " +
                "\"allowPartialFailure\" : \"false\"}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD_GROUP, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/update", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            AdGroupDTO adGroupDTO = (AdGroupDTO) result.getObject();
            assertObjectEqual(adGroupDTO.getId(), ((AdGroup) getTestData().get("adGroup")).getAdGroupId());
        }
    }

    private Map<String, Object> getTestData2() throws Exception {
        if (testData2 == null) {
            testData2 = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = createBudget(account.getAccountId(), budgetAmount, deliveryMethod);
            Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
            AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());

            testData2.put("account", account);
            testData2.put("campaign", campaign);
            testData2.put("adGroup", adGroup);
        }

        return testData2;
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = createBudget(account.getAccountId(), budgetAmount, deliveryMethod);
            Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
            AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());

            testData.put("account", account);
            testData.put("campaign", campaign);
            testData.put("adGroup", adGroup);
        }

        return testData;
    }

    private String generateAdGroupString(long campaignID, long adGroupID, String stat, String name) {
        return " {" +
                "\"campaignId\" : " + campaignID + ", " +
                "\"adGroupId\" : " + adGroupID + ", " +
                "\"adGroupStatus\" : \"" + stat + "\", " +
                "\"adGroupName\" : \"" + name + "\", " +
                "\"maxCpc\" : { \"microAmount\" : 20000000 }} ";
    }
}