package com.yp.pm.kp.api.regression.ad;


import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.AdDTO;
import com.yp.pm.kp.enums.model.AdStatusEnum;
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
public class DeleteAdTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void assertDeleteAd() throws Exception {
        AdGroup adGroup = createAdGroupOnly(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
        Ad ad = createAd(((Account) getTestData().get("account")).getAccountId(), adGroup.getAdGroupId()).get(0);

        String payload = "{\"adIds\" : '[" + ad.getAdId() + "]', \"allowPartialFailure\" : false}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.DELETE, "account/campaign/adgroup/ad/delete", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        Ad deletedAd = getAdById(((Account) getTestData().get("account")).getAccountId(), ad.getAdId());
        assertObjectEqual(deletedAd.getAdStatus().toString(), AdStatusEnum.DELETED.toString());
    }

    @Test
    public void assertAllowPartialFailure() throws Exception {
        AdGroup adGroup = createAdGroupOnly(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
        Ad ad = createAd(((Account) getTestData().get("account")).getAccountId(), adGroup.getAdGroupId()).get(0);

        String payload = "{\"adIds\" : '[" + ad.getAdId() + "]', \"allowPartialFailure\" : true}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.DELETE, "account/campaign/adgroup/ad/delete", headers, payload));
        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);
        Ad deletedAd = getAdById(((Account) getTestData().get("account")).getAccountId(), ad.getAdId());
        assertObjectEqual(deletedAd.getAdStatus().toString(), AdStatusEnum.DELETED.toString());
    }

    @Test
    public void assertInvalidAdId() throws Exception {
        AdGroup adGroup = createAdGroupOnly(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
        Ad ad = createAd(((Account) getTestData().get("account")).getAccountId(), adGroup.getAdGroupId()).get(0);

        String payload = "{\"adIds\" : '[" + 12345 + "]', \"allowPartialFailure\" : false}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.DELETE, "account/campaign/adgroup/ad/delete", headers, payload));
        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 0);
        for (ResultsDTO result : responseDTO.getResults()) {
            assertObjectEqual(((AdDTO) result.getObject()).getId(), -1);
        }
        Ad deletedAd = getAdById(((Account) getTestData().get("account")).getAccountId(), ad.getAdId());
        assertObjectEqual(deletedAd.getAdStatus().toString(), AdStatusEnum.ACTIVE.toString());
    }

    @Test
    public void assertAccountIdIsNull() throws Exception {
        AdGroup adGroup = createAdGroupOnly(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
        Ad ad = createAd(((Account) getTestData().get("account")).getAccountId(), adGroup.getAdGroupId()).get(0);

        String payload = "{\"adIds\" : '[" + ad.getAdId() + "]', \"allowPartialFailure\" : false}";
        headers.put("accountId", "");
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.DELETE, "account/campaign/adgroup/ad/delete", headers, payload));

        assertObjectEqual(responseDTO.getResultStatus().getStatus(), "FAILURE");
        assertObjectEqual(responseDTO.getResultStatus().getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Account id can not be null");
    }

    @Test
    public void assertMissingAccountId() throws Exception {
        AdGroup adGroup = createAdGroupOnly(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
        Ad ad = createAd(((Account) getTestData().get("account")).getAccountId(), adGroup.getAdGroupId()).get(0);

        String payload = "{\"adIds\" : '[" + ad.getAdId() + "]', \"allowPartialFailure\" : false}";
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.DELETE, "account/campaign/adgroup/ad/delete", headers, payload));

        assertObjectEqual(responseDTO.getResultStatus().getStatus(), "FAILURE");
        assertObjectEqual(responseDTO.getResultStatus().getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Account id can not be null");
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