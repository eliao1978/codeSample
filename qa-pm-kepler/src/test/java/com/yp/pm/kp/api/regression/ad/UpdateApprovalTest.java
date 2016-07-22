package com.yp.pm.kp.api.regression.ad;

import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.enums.model.AdApprovalStatusEnum;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Ad;
import com.yp.pm.kp.model.domain.Campaign;
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
public class UpdateApprovalTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void assertDisapproveAd() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long adGroupId = createAdGroupOnly(accountId, ((Campaign) getTestData().get("campaign")).getCampaignId()).getAdGroupId();
        List<Ad> adList = createAd(accountId, adGroupId);

        for (Ad ad : adList) {
            String payload = "{\"apiVersion\":\"1.0\"," +
                    "\"keywords\":{}," +
                    "\"ads\":{\"" + ad.getAdId() + "\":{" +
                    "\"approvalStatus\":\"DISAPPROVED\"," +
                    "\"annotations\":[{" +
                    "\"annotationSource\":\"SYSTEM\"," +
                    "\"annotationType\":\"ADULT\"," +
                    "\"annotatedContentEntries\":[" +
                    "{\"cleared\":false,\"startIndex\":0,\"annotatedContent\":\"3some\",\"annotatedField\":\"AD_HEADLINE\"}," +
                    "{\"cleared\":false,\"startIndex\":0,\"annotatedContent\":\"3some\",\"annotatedField\":\"AD_DESCRIPTION_1\"}," +
                    "{\"cleared\":false,\"startIndex\":0,\"annotatedContent\":\"3some\",\"annotatedField\":\"AD_DESCRIPTION_2\"}," +
                    "{\"cleared\":false,\"startIndex\":0,\"annotatedContent\":\"3some\",\"annotatedField\":\"AD_DESTINATION_URL\"}" +
                    "]}]}}}";
            headers.put("accountId", String.valueOf(accountId));
            ResponseDTO responseDTO = getResponseDTO(getAPIResponse(MethodEnum.PUT, "v2/serenity/updateApproval", headers, payload));

            assertResponseError(responseDTO);

            Ad updatedAd = getAdById(accountId, ad.getAdId());
            assertConditionTrue(updatedAd.getApprovalStatus().equals(AdApprovalStatusEnum.DISAPPROVED));
            assertConditionTrue(updatedAd.getDisapprovalReasons().size() == 4);
            assertObjectNotNull(updatedAd.getLastApprovedBy());
            assertObjectNotNull(updatedAd.getLastApproveTimestamp());
        }
    }

    @Test
    public void assertApproveAd() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long adGroupId = createAdGroupOnly(accountId, ((Campaign) getTestData().get("campaign")).getCampaignId()).getAdGroupId();
        List<Ad> adList = createAd(accountId, adGroupId);

        for (Ad ad : adList) {
            String payload = "{\"apiVersion\":\"1.0\"," +
                    "\"keywords\":{}," +
                    "\"ads\":{\"" + ad.getAdId() + "\":{" +
                    "\"approvalStatus\":\"APPROVED\"," +
                    "\"annotations\":[{" +
                    "\"annotationSource\":\"SYSTEM\"," +
                    "\"annotationType\":\"ADULT\"," +
                    "\"annotatedContentEntries\":[" +
                    "{\"cleared\":false,\"startIndex\":0,\"annotatedContent\":\"3some\",\"annotatedField\":\"AD_HEADLINE\"}," +
                    "{\"cleared\":false,\"startIndex\":0,\"annotatedContent\":\"3some\",\"annotatedField\":\"AD_DESCRIPTION_1\"}," +
                    "{\"cleared\":false,\"startIndex\":0,\"annotatedContent\":\"3some\",\"annotatedField\":\"AD_DESCRIPTION_2\"}," +
                    "{\"cleared\":false,\"startIndex\":0,\"annotatedContent\":\"3some\",\"annotatedField\":\"AD_DESTINATION_URL\"}" +
                    "]}]}}}";
            headers.put("accountId", String.valueOf(accountId));
            ResponseDTO responseDTO = getResponseDTO(getAPIResponse(MethodEnum.PUT, "v2/serenity/updateApproval", headers, payload));

            assertResponseError(responseDTO);

            Ad updatedAd = getAdById(accountId, ad.getAdId());
            assertConditionTrue(updatedAd.getApprovalStatus().equals(AdApprovalStatusEnum.APPROVED));
            assertObjectNull(updatedAd.getDisapprovalReasons());
            assertObjectNotNull(updatedAd.getLastApprovedBy());
            assertObjectNotNull(updatedAd.getLastApproveTimestamp());
        }
    }


    @Test
    public void assertInvalidAdId() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long adGroupId = createAdGroupOnly(accountId, ((Campaign) getTestData().get("campaign")).getCampaignId()).getAdGroupId();
        List<Ad> adList = createAd(accountId, adGroupId);

        for (Ad ad : adList) {
            String payload = "{\"apiVersion\":\"1.0\"," +
                    "\"keywords\":{}," +
                    "\"ads\":{\"12345\":{" +
                    "\"approvalStatus\":\"DISAPPROVED\"," +
                    "\"annotations\":[{" +
                    "\"annotationSource\":\"SYSTEM\"," +
                    "\"annotationType\":\"ADULT\"," +
                    "\"annotatedContentEntries\":[" +
                    "{\"cleared\":false,\"startIndex\":0,\"annotatedContent\":\"3some\",\"annotatedField\":\"AD_HEADLINE\"}," +
                    "{\"cleared\":false,\"startIndex\":0,\"annotatedContent\":\"3some\",\"annotatedField\":\"AD_DESCRIPTION_1\"}," +
                    "{\"cleared\":false,\"startIndex\":0,\"annotatedContent\":\"3some\",\"annotatedField\":\"AD_DESCRIPTION_2\"}," +
                    "{\"cleared\":false,\"startIndex\":0,\"annotatedContent\":\"3some\",\"annotatedField\":\"AD_DESTINATION_URL\"}" +
                    "]}]}}}";
            headers.put("accountId", String.valueOf(accountId));
            ResponseDTO responseDTO = getResponseDTO(getAPIResponse(MethodEnum.PUT, "v2/serenity/updateApproval", headers, payload));

            assertResponseError(responseDTO);

            Ad updatedAd = getAdById(accountId, ad.getAdId());
            if(updatedAd.getApprovalStatus() != null) {
                assertConditionTrue(updatedAd.getApprovalStatus().equals(AdApprovalStatusEnum.APPROVED));
                assertObjectNull(updatedAd.getDisapprovalReasons());
                assertObjectNotNull(updatedAd.getLastApprovedBy());
                assertObjectNotNull(updatedAd.getLastApproveTimestamp());
            }
            else {
                logger.info("Approval Status is null");
            }
        }
    }

    @Test
    public void assertAccountIdIsNull() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long adGroupId = createAdGroupOnly(accountId, ((Campaign) getTestData().get("campaign")).getCampaignId()).getAdGroupId();
        List<Ad> adList = createAd(accountId, adGroupId);

        for (Ad ad : adList) {
            String payload = "{\"apiVersion\":\"1.0\"," +
                    "\"keywords\":{}," +
                    "\"ads\":{\"" + ad.getAdId() + "\":{" +
                    "\"approvalStatus\":\"DISAPPROVED\"," +
                    "\"annotations\":[{" +
                    "\"annotationSource\":\"SYSTEM\"," +
                    "\"annotationType\":\"ADULT\"," +
                    "\"annotatedContentEntries\":[" +
                    "{\"cleared\":false,\"startIndex\":0,\"annotatedContent\":\"3some\",\"annotatedField\":\"AD_HEADLINE\"}," +
                    "{\"cleared\":false,\"startIndex\":0,\"annotatedContent\":\"3some\",\"annotatedField\":\"AD_DESCRIPTION_1\"}," +
                    "{\"cleared\":false,\"startIndex\":0,\"annotatedContent\":\"3some\",\"annotatedField\":\"AD_DESCRIPTION_2\"}," +
                    "{\"cleared\":false,\"startIndex\":0,\"annotatedContent\":\"3some\",\"annotatedField\":\"AD_DESTINATION_URL\"}" +
                    "]}]}}}";
            headers.put("accountId", "");
            ResponseDTO responseDTO = getResponseDTO(getAPIResponse(MethodEnum.PUT, "v2/serenity/updateApproval", headers, payload));

            assertObjectEqual(responseDTO.getResultStatus().getStatus(), "FAILURE");
            assertObjectEqual(responseDTO.getResultStatus().getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Account id can not be null");
        }
    }

    @Test
    public void assertMissingAccountId() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long adGroupId = createAdGroupOnly(accountId, ((Campaign) getTestData().get("campaign")).getCampaignId()).getAdGroupId();
        List<Ad> adList = createAd(accountId, adGroupId);

        for (Ad ad : adList) {
            String payload = "{\"apiVersion\":\"1.0\"," +
                    "\"keywords\":{}," +
                    "\"ads\":{\"" + ad.getAdId() + "\":{" +
                    "\"approvalStatus\":\"DISAPPROVED\"," +
                    "\"annotations\":[{" +
                    "\"annotationSource\":\"SYSTEM\"," +
                    "\"annotationType\":\"ADULT\"," +
                    "\"annotatedContentEntries\":[" +
                    "{\"cleared\":false,\"startIndex\":0,\"annotatedContent\":\"3some\",\"annotatedField\":\"AD_HEADLINE\"}," +
                    "{\"cleared\":false,\"startIndex\":0,\"annotatedContent\":\"3some\",\"annotatedField\":\"AD_DESCRIPTION_1\"}," +
                    "{\"cleared\":false,\"startIndex\":0,\"annotatedContent\":\"3some\",\"annotatedField\":\"AD_DESCRIPTION_2\"}," +
                    "{\"cleared\":false,\"startIndex\":0,\"annotatedContent\":\"3some\",\"annotatedField\":\"AD_DESTINATION_URL\"}" +
                    "]}]}}}";
            ResponseDTO responseDTO = getResponseDTO(getAPIResponse(MethodEnum.PUT, "v2/serenity/updateApproval", headers, payload));

            assertObjectEqual(responseDTO.getResultStatus().getStatus(), "FAILURE");
            assertObjectEqual(responseDTO.getResultStatus().getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Account id can not be null");
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