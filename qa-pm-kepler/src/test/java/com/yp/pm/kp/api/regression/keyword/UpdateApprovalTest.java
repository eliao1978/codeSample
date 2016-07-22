package com.yp.pm.kp.api.regression.keyword;

import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.enums.model.KeywordApprovalStatusEnum;
import com.yp.pm.kp.model.domain.Account;
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
public class UpdateApprovalTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void assertDisapproveKeyword() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long adGroupId = createAdGroupOnly(accountId, ((Campaign) getTestData().get("campaign")).getCampaignId()).getAdGroupId();
        List<Keyword> keywordList = createKeyword(accountId, adGroupId);

        for (Keyword kw : keywordList) {
            String payload = "{\"apiVersion\":\"1.0\"," +
                    "\"ads\":{}," +
                    "\"keywords\":{\"" + kw.getKeywordId() + "\":{" +
                    "\"approvalStatus\":\"DISAPPROVED\"," +
                    "\"annotations\":[{" +
                    "\"annotationType\":\"ALCOHOL\"," +
                    "\"annotationSource\":\"SYSTEM\"," +
                    "\"annotatedContentEntries\":[{\"annotatedField\":\"KEYWORD_TEXT\",\"annotatedContent\":\"whisky\",\"startIndex\":0,\"cleared\":false}]}," +
                    "{\"annotationType\":\"TOBACCO\"," +
                    "\"annotationSource\":\"SYSTEM\"," +
                    "\"annotatedContentEntries\":[{\"annotatedField\":\"KEYWORD_DESTINATION_URL\",\"annotatedContent\":\"whisky\",\"startIndex\":0,\"cleared\":false}]}" +
                    "]}}}";
            headers.put("accountId", String.valueOf(accountId));
            ResponseDTO responseDTO = getResponseDTO(getAPIResponse(MethodEnum.PUT, "v2/serenity/updateApproval", headers, payload));

            assertResponseError(responseDTO);

            Keyword updatedKeyword = getKeywordById(accountId, kw.getKeywordId());
                assertConditionTrue(updatedKeyword.getApprovalStatus().equals(KeywordApprovalStatusEnum.DISAPPROVED));
                assertConditionTrue(updatedKeyword.getDisapprovalReasons().size() == 2);
                assertObjectNotNull(updatedKeyword.getLastApprovedBy());
                assertObjectNotNull(updatedKeyword.getLastApproveTimestamp());
        }
    }

    @Test
    public void assertApproveKeyword() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long adGroupId = createAdGroupOnly(accountId, ((Campaign) getTestData().get("campaign")).getCampaignId()).getAdGroupId();
        List<Keyword> keywordList = createKeyword(accountId, adGroupId);

        for (Keyword kw : keywordList) {
            String payload = "{\"apiVersion\":\"1.0\"," +
                    "\"ads\":{}," +
                    "\"keywords\":{\"" + kw.getKeywordId() + "\":{" +
                    "\"approvalStatus\":\"APPROVED\"," +
                    "\"annotations\":[{" +
                    "\"annotationType\":\"ALCOHOL\"," +
                    "\"annotationSource\":\"SYSTEM\"," +
                    "\"annotatedContentEntries\":[{\"annotatedField\":\"KEYWORD_TEXT\",\"annotatedContent\":\"whisky\",\"startIndex\":0,\"cleared\":false}]}," +
                    "{\"annotationType\":\"TOBACCO\"," +
                    "\"annotationSource\":\"SYSTEM\"," +
                    "\"annotatedContentEntries\":[{\"annotatedField\":\"KEYWORD_DESTINATION_URL\",\"annotatedContent\":\"whisky\",\"startIndex\":0,\"cleared\":false}]}" +
                    "]}}}";
            headers.put("accountId", String.valueOf(accountId));
            ResponseDTO responseDTO = getResponseDTO(getAPIResponse(MethodEnum.PUT, "v2/serenity/updateApproval", headers, payload));

            assertResponseError(responseDTO);

            Keyword updatedKeyword = getKeywordById(accountId, kw.getKeywordId());
            assertConditionTrue(updatedKeyword.getApprovalStatus().equals(KeywordApprovalStatusEnum.APPROVED));
            assertObjectNull(updatedKeyword.getDisapprovalReasons());
            assertObjectNotNull(updatedKeyword.getLastApprovedBy());
            assertObjectNotNull(updatedKeyword.getLastApproveTimestamp());
        }
    }

    @Test
    public void assertInvalidKeywordId() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long adGroupId = createAdGroupOnly(accountId, ((Campaign) getTestData().get("campaign")).getCampaignId()).getAdGroupId();
        List<Keyword> keywordList = createKeyword(accountId, adGroupId);

        for (Keyword kw : keywordList) {
            String payload = "{\"apiVersion\":\"1.0\"," +
                    "\"ads\":{}," +
                    "\"keywords\":{\"12345\":{" +
                    "\"approvalStatus\":\"DISAPPROVED\"," +
                    "\"annotations\":[{" +
                    "\"annotationType\":\"ALCOHOL\"," +
                    "\"annotationSource\":\"SYSTEM\"," +
                    "\"annotatedContentEntries\":[{\"annotatedField\":\"KEYWORD_TEXT\",\"annotatedContent\":\"whisky\",\"startIndex\":0,\"cleared\":false}]}," +
                    "{\"annotationType\":\"TOBACCO\"," +
                    "\"annotationSource\":\"SYSTEM\"," +
                    "\"annotatedContentEntries\":[{\"annotatedField\":\"KEYWORD_DESTINATION_URL\",\"annotatedContent\":\"whisky\",\"startIndex\":0,\"cleared\":false}]}" +
                    "]}}}";
            headers.put("accountId", String.valueOf(accountId));
            ResponseDTO responseDTO = getResponseDTO(getAPIResponse(MethodEnum.PUT, "v2/serenity/updateApproval", headers, payload));

            assertResponseError(responseDTO);

            Keyword updatedKeyword = getKeywordById(accountId, kw.getKeywordId());
            if(updatedKeyword.getApprovalStatus() != null) {
                assertConditionTrue(updatedKeyword.getApprovalStatus().equals(KeywordApprovalStatusEnum.APPROVED));
                assertObjectNull(updatedKeyword.getDisapprovalReasons());
                assertObjectNotNull(updatedKeyword.getLastApprovedBy());
                assertObjectNotNull(updatedKeyword.getLastApproveTimestamp());
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
        List<Keyword> keywordList = createKeyword(accountId, adGroupId);

        for (Keyword kw : keywordList) {
            String payload = "{\"apiVersion\":\"1.0\"," +
                    "\"ads\":{}," +
                    "\"keywords\":{\"" + kw.getKeywordId() + "\":{" +
                    "\"approvalStatus\":\"DISAPPROVED\"," +
                    "\"annotations\":[{" +
                    "\"annotationType\":\"ALCOHOL\"," +
                    "\"annotationSource\":\"SYSTEM\"," +
                    "\"annotatedContentEntries\":[{\"annotatedField\":\"KEYWORD_TEXT\",\"annotatedContent\":\"whisky\",\"startIndex\":0,\"cleared\":false}]}," +
                    "{\"annotationType\":\"TOBACCO\"," +
                    "\"annotationSource\":\"SYSTEM\"," +
                    "\"annotatedContentEntries\":[{\"annotatedField\":\"KEYWORD_DESTINATION_URL\",\"annotatedContent\":\"whisky\",\"startIndex\":0,\"cleared\":false}]}" +
                    "]}}}";
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
        List<Keyword> keywordList = createKeyword(accountId, adGroupId);

        for (Keyword kw : keywordList) {
            String payload = "{\"apiVersion\":\"1.0\"," +
                    "\"ads\":{}," +
                    "\"keywords\":{\"" + kw.getKeywordId() + "\":{" +
                    "\"approvalStatus\":\"DISAPPROVED\"," +
                    "\"annotations\":[{" +
                    "\"annotationType\":\"ALCOHOL\"," +
                    "\"annotationSource\":\"SYSTEM\"," +
                    "\"annotatedContentEntries\":[{\"annotatedField\":\"KEYWORD_TEXT\",\"annotatedContent\":\"whisky\",\"startIndex\":0,\"cleared\":false}]}," +
                    "{\"annotationType\":\"TOBACCO\"," +
                    "\"annotationSource\":\"SYSTEM\"," +
                    "\"annotatedContentEntries\":[{\"annotatedField\":\"KEYWORD_DESTINATION_URL\",\"annotatedContent\":\"whisky\",\"startIndex\":0,\"cleared\":false}]}" +
                    "]}}}";
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