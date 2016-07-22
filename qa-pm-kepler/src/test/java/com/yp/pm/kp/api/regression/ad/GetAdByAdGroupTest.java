package com.yp.pm.kp.api.regression.ad;

import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.AdDTO;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Ad;
import com.yp.pm.kp.model.domain.AdGroup;
import com.yp.pm.kp.model.domain.Campaign;
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
public class GetAdByAdGroupTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void assertGetAdByAdGroupId() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long adGroupId = ((AdGroup) getTestData().get("adGroup")).getAdGroupId();
        Ad ad = (Ad) getTestData().get("ad");
        String payload = "{\"adGroupIds\" : '[" + adGroupId + "]', \"paging\" : { \"startIndex\" : 0, \"numberResults\" : 10 }, \"allowPartialFailure\" : 'false'}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/ad/get", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            AdDTO adDTO = (AdDTO) result.getObject();
            assertObjectEqual(adDTO.getAccountId(), accountId);
            assertObjectEqual(adDTO.getAdGroupId(), adGroupId);
            assertObjectEqual(adDTO.getId(), ad.getAdId());
            assertObjectEqual(adDTO.getType(), ad.getAdType().toString());
            assertObjectEqual(adDTO.getStatus(), ad.getAdStatus().getValue());
            assertObjectEqual(adDTO.isMobilePreferred(), ad.isMobilePreferred());
            assertObjectEqual(adDTO.getHeadline(), ad.getHeadline());
            assertObjectEqual(adDTO.getDescription1(), ad.getDescription1());
            assertObjectEqual(adDTO.getDescription2(), ad.getDescription2());
            assertObjectEqual(adDTO.getDisplayURL(), ad.getDisplayUrl());
            assertObjectEqual(adDTO.getDestinationURL(), ad.getDestinationUrl());
        }
    }

    @Test
    public void assertAllowPartialFailure() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long adGroupId = ((AdGroup) getTestData().get("adGroup")).getAdGroupId();
        Ad ad = (Ad) getTestData().get("ad");
        String payload = "{\"adGroupIds\" : '[" + adGroupId + "]', \"paging\" : { \"startIndex\" : 0, \"numberResults\" : 10 }, \"allowPartialFailure\" : 'true'}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/ad/get", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            AdDTO adDTO = (AdDTO) result.getObject();
            assertObjectEqual(adDTO.getAccountId(), accountId);
            assertObjectEqual(adDTO.getAdGroupId(), adGroupId);
            assertObjectEqual(adDTO.getId(), ad.getAdId());
            assertObjectEqual(adDTO.getType(), ad.getAdType().getValue());
            assertObjectEqual(adDTO.getStatus(), ad.getAdStatus().getValue());
            assertObjectEqual(adDTO.isMobilePreferred(), ad.isMobilePreferred());
            assertObjectEqual(adDTO.getHeadline(), ad.getHeadline());
            assertObjectEqual(adDTO.getDescription1(), ad.getDescription1());
            assertObjectEqual(adDTO.getDescription2(), ad.getDescription2());
            assertObjectEqual(adDTO.getDisplayURL(), ad.getDisplayUrl());
            assertObjectEqual(adDTO.getDestinationURL(), ad.getDestinationUrl());
        }
    }

    @Test
    public void assertAccountIdIsNull() throws Exception {
        long adGroupId = ((AdGroup) getTestData().get("adGroup")).getAdGroupId();
        String payload = "{\"adGroupIds\" : '[" + adGroupId + "]', \"paging\" : { \"startIndex\" : 0, \"numberResults\" : 10 }, \"allowPartialFailure\" : 'false'}";
        headers.put("accountId", "");
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/ad/get", headers, payload));

        assertConditionTrue(responseDTO.getResultStatus().getError().getType().equals(ErrorTypeEnum.USER_ERROR));
        assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Account id can not be null");
    }

    @Test
    public void assertMissingAccountId() throws Exception {
        long adGroupId = ((AdGroup) getTestData().get("adGroup")).getAdGroupId();
        String payload = "{\"adGroupIds\" : '[" + adGroupId + "]', \"paging\" : { \"startIndex\" : 0, \"numberResults\" : 10 }, \"allowPartialFailure\" : 'false'}";
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/ad/get", headers, payload));

        assertConditionTrue(responseDTO.getResultStatus().getError().getType().equals(ErrorTypeEnum.USER_ERROR));
        assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Account id can not be null");
    }

    @Test
    public void assertInvalidAdGroupId() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        String payload = "{\"adGroupIds\" : '[" + 12345 + "]', \"paging\" : { \"startIndex\" : 0, \"numberResults\" : 10 }, \"allowPartialFailure\" : 'false'}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/ad/get", headers, payload));
        assertResponseError(responseDTO);
        assertConditionTrue(responseDTO.getTotalCount() == 0);
        assertObjectEqual(responseDTO.getResults().size(), 0);
    }

    @Test
    public void assertAdGroupIdIsNull() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long adGroupId = ((AdGroup) getTestData().get("adGroup")).getAdGroupId();
        Ad ad = (Ad) getTestData().get("ad");
        String payload = "{\"adGroupIds\" : '[]', \"paging\" : { \"startIndex\" : 0, \"numberResults\" : 10 }, \"allowPartialFailure\" : 'false'}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/ad/get", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            AdDTO adDTO = (AdDTO) result.getObject();
            assertObjectEqual(adDTO.getAccountId(), accountId);
            assertObjectEqual(adDTO.getAdGroupId(), adGroupId);
            assertObjectEqual(adDTO.getId(), ad.getAdId());
            assertObjectEqual(adDTO.getType(), ad.getAdType().getValue());
            assertObjectEqual(adDTO.getStatus(), ad.getAdStatus().getValue());
            assertObjectEqual(adDTO.isMobilePreferred(), ad.isMobilePreferred());
            assertObjectEqual(adDTO.getHeadline(), ad.getHeadline());
            assertObjectEqual(adDTO.getDescription1(), ad.getDescription1());
            assertObjectEqual(adDTO.getDescription2(), ad.getDescription2());
            assertObjectEqual(adDTO.getDisplayURL(), ad.getDisplayUrl());
            assertObjectEqual(adDTO.getDestinationURL(), ad.getDestinationUrl());
        }
    }

    @Test
    public void assertPagingOutOfRange() throws Exception {
        long adGroupId = ((AdGroup) getTestData().get("adGroup")).getAdGroupId();
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        String payload = "{\"adGroupIds\" : '[" + adGroupId + "]', \"paging\" : { \"startIndex\" : 1, \"numberResults\" : 10 }, \"allowPartialFailure\" : 'false'}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/ad/get", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);
        assertObjectEqual(responseDTO.getResults().size(), 0);
    }

    @Test
    public void assertNegativeNumberResults() throws Exception {
        long adGroupId = ((AdGroup) getTestData().get("adGroup")).getAdGroupId();
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        String payload = "{\"adGroupIds\" : '[" + adGroupId + "]', \"paging\" : { \"startIndex\" : 0, \"numberResults\" : -10 }, \"allowPartialFailure\" : 'false'}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/ad/get", headers, payload));

        assertConditionTrue(responseDTO.getResultStatus().getError().getType().equals(ErrorTypeEnum.USER_ERROR));
        assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Invalid paging number of results. It must be greater than or equal to zero");
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
            AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());
            Ad ad = createAd(account.getAccountId(), adGroup.getAdGroupId()).get(0);

            testData.put("account", account);
            testData.put("campaign", campaign);
            testData.put("adGroup", adGroup);
            testData.put("ad", ad);
        }

        return testData;
    }
}