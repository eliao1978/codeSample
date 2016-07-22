package com.yp.pm.kp.api.regression.ad;

import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.AdDTO;
import com.yp.pm.kp.enums.model.AdApprovalStatusEnum;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Ad;
import com.yp.pm.kp.model.domain.AdGroup;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.util.DBUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
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
public class AddAdTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void assertAddAd() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        AdGroup adGroup = createAdGroupOnly(accountId, ((Campaign) getTestData().get("campaign")).getCampaignId());
        String payload = "{\"ads\" : [{" +
                "\"adGroupId\" : " + adGroup.getAdGroupId() + ", " +
                "\"adType\" : \"TEXT\", " +
                "\"headline\" : \"test_headline1\", " +
                "\"description1\" : \"test_description1\", " +
                "\"description2\" : \"test_description2\", " +
                "\"displayUrl\" : \"www.yp.com\", " +
                "\"destinationUrl\" : \"www.yp1.com\", " +
                "\"mobilePreferred\" : true}, " +
                "{\"adGroupId\" : " + adGroup.getAdGroupId() + ", " +
                "\"adType\" : \"TEXT\", " +
                "\"headline\" : \"test_headline2\", " +
                "\"description1\" : \"test_description3\", " +
                "\"description2\" : \"test_description4\", " +
                "\"displayUrl\" : \"www.yp2.com\", " +
                "\"destinationUrl\" : \"www.yp3.com\", " +
                "\"mobilePreferred\" : true}, " +
                "{\"adGroupId\" : " + adGroup.getAdGroupId() + ", " +
                "\"adType\" : \"TEXT\", " +
                "\"headline\" : \"test_headline3\", " +
                "\"description1\" : \"test_description5\", " +
                "\"description2\" : \"test_description6\", " +
                "\"displayUrl\" : \"www.yp2.com\", " +
                "\"destinationUrl\" : \"www.yp3.com\", " +
                "\"mobilePreferred\" : false}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/ad/create", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 3);

        int i = 0;
        for (ResultsDTO result : responseDTO.getResults()) {
            assertConditionTrue(result.getIndex() == i);
            i += 1;

            AdDTO adDTO = (AdDTO) result.getObject();
            assertObjectEqual(adDTO.getId(), getAdById(accountId, adDTO.getId()).getAdId());
        }
    }

    @Test
    public void assertAllowPartialFailure() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        AdGroup adGroup = createAdGroupOnly(accountId, ((Campaign) getTestData().get("campaign")).getCampaignId());
        String payload = "{\"ads\" : [{" +
                "\"adGroupId\" : " + adGroup.getAdGroupId() + ", " +
                "\"adType\" : \"TEXT\", " +
                "\"headline\" : \"test_headline\", " +
                "\"description1\" : \"test_description1\", " +
                "\"description2\" : \"test_description2\", " +
                "\"displayUrl\" : \"www.yp.com\", " +
                "\"destinationUrl\" : \"www.yp1.com\", " +
                "\"mobilePreferred\" : true}, " +
                "{\"adGroupId\" : " + adGroup.getAdGroupId() + ", " +
                "\"adType\" : \"TEXT\", " +
                "\"headline\" : \"test_headline\", " +
                "\"description1\" : \"test_description3\", " +
                "\"description2\" : \"test_description4\", " +
                "\"displayUrl\" : \"www.yp2.com\", " +
                "\"destinationUrl\" : \"www.yp3.com\", " +
                "\"mobilePreferred\" : true}, " +
                "{\"adGroupId\" : " + adGroup.getAdGroupId() + ", " +
                "\"adType\" : \"TEXT\", " +
                "\"headline\" : \"test_headline3\", " +
                "\"description1\" : \"test_description3\", " +
                "\"description2\" : \"test_description4\", " +
                "\"displayUrl\" : \"www.yp2.com\", " +
                "\"destinationUrl\" : \"www.yp3.com\", " +
                "\"mobilePreferred\" : true}], " +
                "\"allowPartialFailure\" : true}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/ad/create", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 3);

        int i = 0;
        for (ResultsDTO result : responseDTO.getResults()) {
            assertConditionTrue(result.getIndex() == i);
            i += 1;

            AdDTO adDTO = (AdDTO) result.getObject();
            assertObjectEqual(adDTO.getId(), getAdById(accountId, adDTO.getId()).getAdId());
        }
    }

    @Test
    public void assertAccountIdIsNull() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        AdGroup adGroup = createAdGroupOnly(accountId, ((Campaign) getTestData().get("campaign")).getCampaignId());
        String payload = "{\"ads\" : [{" +
                "\"adGroupId\" : " + adGroup.getAdGroupId() + ", " +
                "\"adType\" : \"TEXT\", " +
                "\"headline\" : \"test_headline1\", " +
                "\"description1\" : \"test_description1\", " +
                "\"description2\" : \"test_description2\", " +
                "\"displayUrl\" : \"www.yp.com\", " +
                "\"destinationUrl\" : \"www.yp1.com\", " +
                "\"mobilePreferred\" : true}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", "");
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/ad/create", headers, payload));

        assertObjectEqual(responseDTO.getResultStatus().getStatus(), "FAILURE");
        assertObjectEqual(responseDTO.getResultStatus().getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Account id can not be null");
    }

    @Test
    public void assertMissingAccountId() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        AdGroup adGroup = createAdGroupOnly(accountId, ((Campaign) getTestData().get("campaign")).getCampaignId());
        String payload = "{\"ads\" : [{" +
                "\"adGroupId\" : " + adGroup.getAdGroupId() + ", " +
                "\"adType\" : \"TEXT\", " +
                "\"headline\" : \"test_headline1\", " +
                "\"description1\" : \"test_description1\", " +
                "\"description2\" : \"test_description2\", " +
                "\"displayUrl\" : \"www.yp.com\", " +
                "\"destinationUrl\" : \"www.yp1.com\", " +
                "\"mobilePreferred\" : true}], " +
                "\"allowPartialFailure\" : false}";
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/ad/create", headers, payload));

        assertObjectEqual(responseDTO.getResultStatus().getStatus(), "FAILURE");
        assertObjectEqual(responseDTO.getResultStatus().getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Account id can not be null");
    }

    @Test
    public void assertAdGroupIdIsNull() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        String payload = "{\"ads\" : [{" +
                "\"adGroupId\" : \"\", " +
                "\"adType\" : \"TEXT\", " +
                "\"headline\" : \"test_headline1\", " +
                "\"description1\" : \"test_description1\", " +
                "\"description2\" : \"test_description2\", " +
                "\"displayUrl\" : \"www.yp.com\", " +
                "\"destinationUrl\" : \"www.yp1.com\", " +
                "\"mobilePreferred\" : true}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/ad/create", headers, payload));

        assertObjectEqual(responseDTO.getResultStatus().getStatus(), "FAILURE");
        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Ad group id can not be null");
    }

    @Test
    public void assertMissingAdGroupId() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        String payload = "{\"ads\" : [{" +
                "\"adType\" : \"TEXT\", " +
                "\"headline\" : \"test_headline1\", " +
                "\"description1\" : \"test_description1\", " +
                "\"description2\" : \"test_description2\", " +
                "\"displayUrl\" : \"www.yp.com\", " +
                "\"destinationUrl\" : \"www.yp1.com\", " +
                "\"mobilePreferred\" : true}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/ad/create", headers, payload));

        assertObjectEqual(responseDTO.getResultStatus().getStatus(), "FAILURE");
        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Ad group id can not be null");
    }

    @Test
    public void assertCustomLimitation() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");

        try {
            Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
            AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());
            DBUtil.update("INSERT INTO PM_CUSTOM_LIMIT_VALIDATION VALUES('ad', 5, ".concat(account.getAccountId().toString()).concat(", 'account', '").concat(userName) + "',SYSDATE,'" + userName + "',SYSDATE)");
            int count = 1;
            while (count < 6) {
                JSONObject newAd = new JSONObject();
                newAd.put("adType", "TEXT");
                newAd.put("description1", "test description".concat(Integer.toString(count)));
                newAd.put("description2", "test description2");
                newAd.put("displayUrl", "www.displayURL".concat(Integer.toString(count)).concat(".com"));
                newAd.put("destinationUrl", "www.destinationUrl".concat(Integer.toString(count)).concat(".com"));
                newAd.put("headLine", "test_headline".concat(Integer.toString(count)));
                JSONArray newAds = new JSONArray();
                newAds.add(newAd);
                createAd(account.getAccountId(), adGroup.getAdGroupId(), newAds);
                count++;
            }

            String payload = "{\"ads\" : [{" +
                    "\"adGroupId\" : " + adGroup.getAdGroupId() + ", " +
                    "\"adType\" : \"TEXT\", " +
                    "\"headline\" : \"test_headline\", " +
                    "\"description1\" : \"test_description1\", " +
                    "\"description2\" : \"test_description2\", " +
                    "\"displayUrl\" : \"www.displayUrl.com\", " +
                    "\"destinationUrl\" : \"www.destinationUrl.com\", " +
                    "\"mobilePreferred\" : true}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", account.getAccountId().toString());

            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/ad/create", headers, payload));

            assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Exceeds maximum number of ads in ad group");
        } finally {
            DBUtil.update("DELETE FROM PM_CUSTOM_LIMIT_VALIDATION WHERE PARENT_ID = ".concat(account.getAccountId().toString()));
        }
    }

    @Test
    public void assertDescriptionOneStartWithPhoneNumber() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        AdGroup adGroup = createAdGroupOnly(accountId, ((Campaign) getTestData().get("campaign")).getCampaignId());
        String payload = "{\"ads\" : [{" +
                "\"adGroupId\" : " + adGroup.getAdGroupId() + ", " +
                "\"adType\" : \"TEXT\", " +
                "\"headline\" : \"test_headline\", " +
                "\"description1\" : \"8189004397 description1Test\", " +
                "\"description2\" : \"description2\", " +
                "\"displayUrl\" : \"www.displayUrl.com\", " +
                "\"destinationUrl\" : \"www.destinationUrl.com\", " +
                "\"mobilePreferred\" : true}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/ad/create", headers, payload));

        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Phone numbers are not allowed in description fields");
    }

    @Test
    public void assertDescriptionOneContainPhoneNumber() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        AdGroup adGroup = createAdGroupOnly(accountId, ((Campaign) getTestData().get("campaign")).getCampaignId());
        String payload = "{\"ads\" : [{" +
                "\"adGroupId\" : " + adGroup.getAdGroupId() + ", " +
                "\"adType\" : \"TEXT\", " +
                "\"headline\" : \"test_headline\", " +
                "\"description1\" : \"Test 8189004397 description1\", " +
                "\"description2\" : \"test_description2\", " +
                "\"displayUrl\" : \"www.displayUrl.com\", " +
                "\"destinationUrl\" : \"www.destinationUrl.com\", " +
                "\"mobilePreferred\" : true}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/ad/create", headers, payload));

        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Phone numbers are not allowed in description fields");
    }

    @Test
    public void assertDescriptionOneEndWithPhoneNumber() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        AdGroup adGroup = createAdGroupOnly(accountId, ((Campaign) getTestData().get("campaign")).getCampaignId());
        String payload = "{\"ads\" : [{" +
                "\"adGroupId\" : " + adGroup.getAdGroupId() + ", " +
                "\"adType\" : \"TEXT\", " +
                "\"headline\" : \"test_headline\", " +
                "\"description1\" : \"description1Test,8189004397\", " +
                "\"description2\" : \"test_description2\", " +
                "\"displayUrl\" : \"www.displayUrl.com\", " +
                "\"destinationUrl\" : \"www.destinationUrl.com\", " +
                "\"mobilePreferred\" : true}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/ad/create", headers, payload));

        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Phone numbers are not allowed in description fields");
    }

    @Test
    public void assertDescriptionTwoStartWithPhoneNumber() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        AdGroup adGroup = createAdGroupOnly(accountId, ((Campaign) getTestData().get("campaign")).getCampaignId());
        String payload = "{\"ads\" : [{" +
                "\"adGroupId\" : " + adGroup.getAdGroupId() + ", " +
                "\"adType\" : \"TEXT\", " +
                "\"headline\" : \"test_headline\", " +
                "\"description1\" : \"description1Test\", " +
                "\"description2\" : \"8189004397 description2\", " +
                "\"displayUrl\" : \"www.displayUrl.com\", " +
                "\"destinationUrl\" : \"www.destinationUrl.com\", " +
                "\"mobilePreferred\" : true}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/ad/create", headers, payload));

        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Phone numbers are not allowed in description fields");
    }

    @Test
    public void assertDescriptionTwoContainWithPhoneNumber() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        AdGroup adGroup = createAdGroupOnly(accountId, ((Campaign) getTestData().get("campaign")).getCampaignId());
        String payload = "{\"ads\" : [{" +
                "\"adGroupId\" : " + adGroup.getAdGroupId() + ", " +
                "\"adType\" : \"TEXT\", " +
                "\"headline\" : \"test_headline\", " +
                "\"description1\" : \"description1Test\", " +
                "\"description2\" : \"description,28189004397,Test\", " +
                "\"displayUrl\" : \"www.displayUrl.com\", " +
                "\"destinationUrl\" : \"www.destinationUrl.com\", " +
                "\"mobilePreferred\" : true}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/ad/create", headers, payload));

        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Phone numbers are not allowed in description fields");
    }

    @Test
    public void assertDescriptionTwoEndWithPhoneNumber() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        AdGroup adGroup = createAdGroupOnly(accountId, ((Campaign) getTestData().get("campaign")).getCampaignId());
        String payload = "{\"ads\" : [{" +
                "\"adGroupId\" : " + adGroup.getAdGroupId() + ", " +
                "\"adType\" : \"TEXT\", " +
                "\"headline\" : \"test_headline\", " +
                "\"description1\" : \"description1Test\", " +
                "\"description2\" : \"description2-28-18900-4397\", " +
                "\"displayUrl\" : \"www.displayUrl.com\", " +
                "\"destinationUrl\" : \"www.destinationUrl.com\", " +
                "\"mobilePreferred\" : true}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/ad/create", headers, payload));

        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Phone numbers are not allowed in description fields");
    }

    @Test
    public void assertDomainWithDotIn() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        AdGroup adGroup = createAdGroupOnly(accountId, ((Campaign) getTestData().get("campaign")).getCampaignId());
        String payload = "{\"ads\" : [{" +
                "\"adGroupId\" : " + adGroup.getAdGroupId() + ", " +
                "\"adType\" : \"TEXT\", " +
                "\"headline\" : \"test_headline\", " +
                "\"description1\" : \"test_description1\", " +
                "\"description2\" : \"test_description2\", " +
                "\"displayUrl\" : \"https://www.google.in?gws_rd=ssl\", " +
                "\"destinationUrl\" : \"www.foursquare.in?xrp=8.mMLOyc_IyM_ZnMLOz8jLyMbPz8nZnsLOycrPyMfZnJKPws7HzMvHyc_J2ZPCzsnOzM3N2ZLCyA._Uzg\", " +
                "\"mobilePreferred\" : true}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/ad/create", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            AdDTO adDTO = (AdDTO) result.getObject();
            assertObjectEqual(adDTO.getId(), getAdById(((Account) getTestData().get("account")).getAccountId(), adDTO.getId()).getAdId());
        }
    }

    @Test
    public void assertDomainWithDotCom() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        AdGroup adGroup = createAdGroupOnly(accountId, ((Campaign) getTestData().get("campaign")).getCampaignId());

        String payload = "{\"ads\" : [{" +
                "\"adGroupId\" : " + adGroup.getAdGroupId() + ", " +
                "\"adType\" : \"TEXT\", " +
                "\"headline\" : \"test_headline\", " +
                "\"description1\" : \"test_description1\", " +
                "\"description2\" : \"test_description2\", " +
                "\"displayUrl\" : \"https://www.google.com?gws_rd=ssl\", " +
                "\"destinationUrl\" : \"www.foursquare.com?xrp=8.mMLOyc_IyM_ZnMLOz8jLyMbPz8nZnsLOycrPyMfZnJKPws7HzMvHyc_J2ZPCzsnOzM3N2ZLCyA._Uzg\", " +
                "\"mobilePreferred\" : true}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/ad/create", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            AdDTO adDTO = (AdDTO) result.getObject();
            assertObjectEqual(adDTO.getId(), getAdById(accountId, adDTO.getId()).getAdId());
        }
    }

    @Test
    public void assertDomainWithDotNet() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        AdGroup adGroup = createAdGroupOnly(accountId, ((Campaign) getTestData().get("campaign")).getCampaignId());
        String payload = "{\"ads\" : [{" +
                "\"adGroupId\" : " + adGroup.getAdGroupId() + ", " +
                "\"adType\" : \"TEXT\", " +
                "\"headline\" : \"test_headline\", " +
                "\"description1\" : \"test_description1\", " +
                "\"description2\" : \"test_description2\", " +
                "\"displayUrl\" : \"https://www.action.net/action-figures.php\", " +
                "\"destinationUrl\" : \"www.action.net/action-figures.php\", " +
                "\"mobilePreferred\" : true}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/ad/create", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            AdDTO adDTO = (AdDTO) result.getObject();
            assertObjectEqual(adDTO.getId(), getAdById(accountId, adDTO.getId()).getAdId());

        }
    }

    @Test
    public void assertDomainWithDotBiz() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        AdGroup adGroup = createAdGroupOnly(accountId, ((Campaign) getTestData().get("campaign")).getCampaignId());
        String payload = "{\"ads\" : [{" +
                "\"adGroupId\" : " + adGroup.getAdGroupId() + ", " +
                "\"adType\" : \"TEXT\", " +
                "\"headline\" : \"test_headline\", " +
                "\"description1\" : \"test_description1\", " +
                "\"description2\" : \"test_description2\", " +
                "\"displayUrl\" : \"https://www.godaddy.biz/tlds/biz.aspx\", " +
                "\"destinationUrl\" : \"www.godaddy.biz/tlds/biz.aspx\", " +
                "\"mobilePreferred\" : true}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/ad/create", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            AdDTO adDTO = (AdDTO) result.getObject();
            assertObjectEqual(adDTO.getId(), getAdById(accountId, adDTO.getId()).getAdId());
        }
    }

    @Ignore
    public void assertDomainNotMatching() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        AdGroup adGroup = createAdGroupOnly(accountId, ((Campaign) getTestData().get("campaign")).getCampaignId());
        String payload = "{\"ads\" : [{" +
                "\"adGroupId\" : " + adGroup.getAdGroupId() + ", " +
                "\"adType\" : \"TEXT\", " +
                "\"headline\" : \"test_headline\", " +
                "\"description1\" : \"test_description1\", " +
                "\"description2\" : \"test_description2\", " +
                "\"displayUrl\" : \"http://www.yahoo.com\", " +
                "\"destinationUrl\" : \"mail.yahoo.com\", " +
                "\"mobilePreferred\" : true}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", String.valueOf(accountId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/ad/create", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);
        List<Long> adList = new ArrayList<>();
        for (ResultsDTO result : responseDTO.getResults()) {
            AdDTO adDTO = (AdDTO) result.getObject();
            adList.add(adDTO.getId());
            assertObjectEqual(adDTO.getId(), getAdById(accountId, adDTO.getId()).getAdId());
        }
        Thread.sleep(60000);
        logger.debug("Approval Status: " + getAdById(accountId,adList.get(0)).getApprovalStatus());
        assertConditionTrue("Approval Status was not under review", getAdById(accountId, adList.get(0)).getApprovalStatus().equals(AdApprovalStatusEnum.UNDER_REVIEW));
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