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
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UpdateAdTest extends BaseAPITest {

    private Map<String, String> headers = getRequestHeader();
    private static Map<String, Object> testData;

    @Test
    public void assertUpdateAd() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long adGroupId = createAdGroupOnly(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId()).getAdGroupId();
        List<Ad> adList = createAd(accountId, adGroupId);

        for (Ad ad : adList) {
            String payload = "{\"ads\" : [{" +
                    "\"adGroupId\" : " + adGroupId + ", " +
                    "\"adId\" : " + ad.getAdId() + ", " +
                    "\"adStatus\" : \"ACTIVE\"}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", String.valueOf(accountId));
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/ad/update", headers, payload));

            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 1);

            for (ResultsDTO result : responseDTO.getResults()) {
                AdDTO adDTO = (AdDTO) result.getObject();
                Ad updatedAd = getAdById(accountId, ad.getAdId());
                assertObjectEqual(adDTO.getId(), ad.getAdId());
                assertConditionTrue(updatedAd.getAdStatus().equals(AdStatusEnum.ACTIVE));
            }
        }
    }

    @Test
    public void assertAllowPartialFailure() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long adGroupId = createAdGroupOnly(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId()).getAdGroupId();
        List<Ad> adList = createAd(accountId, adGroupId);

        for (Ad ad : adList) {
            String payload = "{\"ads\" : [{" +
                    "\"adGroupId\" : " + adGroupId + ", " +
                    "\"adId\" : " + ad.getAdId() + ", " +
                    "\"adStatus\" : \"PAUSED\"}], " +
                    "\"allowPartialFailure\" : true}";
            headers.put("accountId", String.valueOf(accountId));
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/ad/update", headers, payload));

            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 1);

            for (ResultsDTO result : responseDTO.getResults()) {
                AdDTO adDTO = (AdDTO) result.getObject();
                Ad updatedAd = getAdById(accountId, ad.getAdId());
                assertObjectEqual(adDTO.getId(), ad.getAdId());
                assertConditionTrue(updatedAd.getAdStatus().equals(AdStatusEnum.PAUSED));
            }
        }
    }

    @Test
    public void assertIgnoreUpdateFlag() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long adGroupId = createAdGroupOnly(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId()).getAdGroupId();
        List<Ad> adList = createAd(accountId, adGroupId);

        for (Ad ad : adList) {
            String payload = "{\"ads\" : [{" +
                    "\"adGroupId\" : " + adGroupId + ", " +
                    "\"adId\" :   -1  , " +
                    "\"adStatus\" : \"PAUSED\"}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", String.valueOf(accountId));
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/ad/update", headers, payload));

            assertResponseError(responseDTO);

            for (ResultsDTO result : responseDTO.getResults()) {
                AdDTO adDTO = (AdDTO) result.getObject();
                Ad updatedAd = getAdById(accountId, ad.getAdId());
                assertObjectEqual(adDTO.getId(), -1);
                assertObjectEqual(updatedAd.getAdStatus().toString(), "ACTIVE");
            }
        }
    }

    @Test
    public void assertReturnObjectProblemType() throws Exception {
        long accountId = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles").getAccountId();
        Campaign campaign = createCampaignOnly(accountId, createBudget(accountId, budgetAmount, deliveryMethod).getBudgetId());
        AdGroup adGroup1 = createAdGroupOnly(accountId, campaign.getCampaignId());
        AdGroup adGroup2 = createAdGroupOnly(accountId, campaign.getCampaignId());
        AdGroup adGroup3 = createAdGroupOnly(accountId, campaign.getCampaignId());
        Ad ad1 = createAd(accountId, adGroup1.getAdGroupId()).get(0);
        Ad ad2 = createAd(accountId, adGroup2.getAdGroupId()).get(0);
        Ad ad3 = createAd(accountId, adGroup3.getAdGroupId()).get(0);

        String ad1String = generateAdString(ad1.getAdGroupId(), ad1.getAdId(), "PAUSED");
        String ad2String = generateAdString(ad2.getAdGroupId(), ad2.getAdId(), "DELETED");
        String ad3String = generateAdString(ad3.getAdGroupId(), ad3.getAdId(), "ACTIVE");

        String command = "curl -X PUT " +
                "-d '{\"ads\" : [" + ad1String + "," + ad2String + "," + ad3String + "], " +
                "\"allowPartialFailure\" : false}' " +
                "-H '" + contentType + "' " +
                "-H 'username:'" + userName + "'' " +
                "-H 'requestid:'" + requestId + "'' " +
                "-H 'accountId:'" + accountId + "'' " +
                "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/account/campaign/adgroup/ad/update";

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(command));

        for (ResultsDTO result : responseDTO.getResults()) {
            AdDTO adDTO = (AdDTO) result.getObject();
            if (result.getError().getMessage() != null) {
                assertObjectEqual(adDTO.getId(), -3);
            } else {
                assertObjectEqual(adDTO.getId(), -2);
            }
        }

        assertObjectEqual(getAdById(accountId, ad1.getAdId()).getAdStatus().toString(), "ACTIVE");
        assertObjectEqual(getAdById(accountId, ad2.getAdId()).getAdStatus().toString(), "ACTIVE");
        assertObjectEqual(getAdById(accountId, ad3.getAdId()).getAdStatus().toString(), "ACTIVE");
    }

    @Test
    public void assertInvalidAdId() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long adGroupId = createAdGroupOnly(accountId, ((Campaign) getTestData().get("campaign")).getCampaignId()).getAdGroupId();

        String payload = "{\"ads\" : [{" +
                "\"adGroupId\" : " + adGroupId + ", " +
                "\"adId\" : \"12345\", " +
                "\"adStatus\" : \"PAUSED\"}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/ad/update", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 0);

        for (ResultsDTO result : responseDTO.getResults()) {
            assertObjectEqual(((AdDTO) result.getObject()).getId(), -1);
        }
    }

    @Test
    public void assertAdIdIsNull() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long adGroupId = createAdGroupOnly(accountId, ((Campaign) getTestData().get("campaign")).getCampaignId()).getAdGroupId();
        String payload = "{\"ads\" : [{" +
                "\"adGroupId\" : " + adGroupId + ", " +
                "\"adId\" : \"\", " +
                "\"adStatus\" : \"PAUSED\"}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/ad/update", headers, payload));

        assertObjectEqual(responseDTO.getTotalCount(), 0);
        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Ad id can not be null");
    }

    @Test
    public void assertInvalidAdGroupId() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long adGroupId = createAdGroupOnly(accountId, ((Campaign) getTestData().get("campaign")).getCampaignId()).getAdGroupId();
        List<Ad> adList = createAd(accountId, adGroupId);

        for (Ad ad : adList) {
            String payload = "{\"ads\" : [{" +
                    "\"adGroupId\" : \"12345\", " +
                    "\"adId\" : " + ad.getAdId() + ", " +
                    "\"adStatus\" : \"PAUSED\"}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", String.valueOf(accountId));
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/ad/update", headers, payload));
            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 1);

            for (ResultsDTO result : responseDTO.getResults()) {
                AdDTO adDTO = (AdDTO) result.getObject();
                Ad updatedAd = getAdById(accountId, ad.getAdId());
                assertObjectEqual(adDTO.getId(), updatedAd.getAdId());
                assertObjectEqual(updatedAd.getAdStatus().toString(), "PAUSED");
            }
        }
    }

    @Test
    public void assertMissingAdGroupId() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long adGroupId = createAdGroupOnly(accountId, ((Campaign) getTestData().get("campaign")).getCampaignId()).getAdGroupId();
        List<Ad> adList = createAd(accountId, adGroupId);

        for (Ad ad : adList) {
            String payload = "{\"ads\" : [{" +
                    "\"adId\" : " + ad.getAdId() + ", " +
                    "\"adStatus\" : \"PAUSED\"}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", String.valueOf(accountId));
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/ad/update", headers, payload));

            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 1);

            for (ResultsDTO result : responseDTO.getResults()) {
                AdDTO adDTO = (AdDTO) result.getObject();
                Ad updatedAd = getAdById(accountId, ad.getAdId());
                assertObjectEqual(adDTO.getId(), updatedAd.getAdId());
                assertObjectEqual(updatedAd.getAdStatus().toString(), "PAUSED");
            }
        }
    }

    @Test
    public void assertAccountIdIsNull() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long adGroupId = createAdGroupOnly(accountId, ((Campaign) getTestData().get("campaign")).getCampaignId()).getAdGroupId();
        List<Ad> adList = createAd(accountId, adGroupId);

        for (Ad ad : adList) {
            String payload = "{\"ads\" : [{" +
                    "\"adGroupId\" : " + adGroupId + ", " +
                    "\"adId\" : " + ad.getAdId() + ", " +
                    "\"adStatus\" : \"ACTIVE\"}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", "");
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/ad/update", headers, payload));

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
            String payload = "{\"ads\" : [{" +
                    "\"adGroupId\" : " + adGroupId + ", " +
                    "\"adId\" : " + ad.getAdId() + ", " +
                    "\"adStatus\" : \"ACTIVE\"}], " +
                    "\"allowPartialFailure\" : false}";
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.PUT, "account/campaign/adgroup/ad/update", headers, payload));

            assertObjectEqual(responseDTO.getResultStatus().getStatus(), "FAILURE");
            assertObjectEqual(responseDTO.getResultStatus().getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Account id can not be null");
        }
    }

    private String generateAdString(long adGroupID, long adID, String stat) {
        return " {" +
                "\"adGroupId\" : " + adGroupID + ", " +
                "\"adId\" : " + adID + ", " +
                "\"adStatus\" : \"" + stat + "\", " +
                "\"displayUrl\" : \"www.test.com\", " +
                "\"destinationUrl\" : \"www.test.com\", " +
                "\"mobilePreferred\" : false, " +
                "\"disapprovalReasons\" : [\"testing\"], " +
                "\"approvalStatus\" : \"DISAPPROVED\", " +
                "\"headline\" : \"updated headline\", " +
                "\"description2\" : \"updated test description 2\", " +
                "\"description1\" : \"updated test description 1\"" + "}";
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