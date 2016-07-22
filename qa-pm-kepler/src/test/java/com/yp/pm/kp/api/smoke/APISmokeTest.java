package com.yp.pm.kp.api.smoke;

import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class APISmokeTest extends BaseAPITest {

    private Map<String, String> headers = getRequestHeader();

    @Test
    public void assertGetAccountById() throws Exception {
        String payload = "{\"allowPartialFailure\" : 'true'}";
        headers.put("accountId", (System.getProperty("smoke.accountid")));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.ACCOUNT, getAPIResponse(MethodEnum.POST, "account/get", headers, payload));
        assertResponseError(responseDTO);
    }

    @Test
    public void assertGetBudgetByID() throws Exception {
        String payload = "{\"budgetIds\" : '[" + System.getProperty("smoke.budgetid") + "]', \"allowPartialFailure\" : 'true'}";
        headers.put("accountId", (System.getProperty("smoke.accountid")));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.BUDGET, getAPIResponse(MethodEnum.POST, "account/campaign/budget/get", headers, payload));
        assertResponseError(responseDTO);
    }

    @Test
    public void assertGetCampaignById() throws Exception {
        String payload = "{\"campaignIds\" : '[" + System.getProperty("smoke.campaignid") + "]', \"allowPartialFailure\" : 'true'}";
        headers.put("accountId", (System.getProperty("smoke.accountid")));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN, getAPIResponse(MethodEnum.POST, "account/campaign/get", headers, payload));
        assertResponseError(responseDTO);
    }

    @Test
    public void assertGetAdGroupById() throws Exception {
        String payload = "{\"adGroupIds\" : '[" + System.getProperty("smoke.adgroupid") + "]', \"allowPartialFailure\" : 'true'}";
        headers.put("accountId", (System.getProperty("smoke.accountid")));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD_GROUP, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/get", headers, payload));
        assertResponseError(responseDTO);
    }

    @Test
    public void assertGetAdById() throws Exception {
        String payload = "{\"adIds\" : '[" + System.getProperty("smoke.adid") + "]', \"allowPartialFailure\" : 'false'}";
        headers.put("accountId", (System.getProperty("smoke.accountid")));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/ad/get", headers, payload));
        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);
    }

    @Test
    public void assertGetKeywordById() throws Exception {
        String payload = "{\"keywordIds\" : '" + System.getProperty("smoke.keyword") + "', \"allowPartialFailure\" : 'true'}";
        headers.put("accountId", (System.getProperty("smoke.accountid")));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.KEYWORD, getAPIResponse(MethodEnum.POST, "account/campaign/adgroup/keyword/get", headers, payload));
        assertResponseError(responseDTO);
    }

    @Test
    public void assertGetNegativeLocationById() throws Exception {
        String payload = "{\"negativeKeywordIds\" : '[" + System.getProperty("smoke.negativeKeyword") + "]', \"allowPartialFailure\" : 'true'}";
        headers.put("accountId", (System.getProperty("smoke.accountid")));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.POST, "account/negative/get", headers, payload));
        assertResponseError(responseDTO);
    }

    @Test
    public void assertGetLocationAdExtensionById() throws Exception {
        String payload = "{\"adExtensionIds\" : '" + System.getProperty("smoke.locationAdExtension") + "', \"allowPartialFailure\" : 'true'}";
        headers.put("accountId", (System.getProperty("smoke.accountid")));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.LOCATION_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/location/get", headers, payload));
        assertResponseError(responseDTO);
    }

    @Test
    public void assertGetLogoAdExtensionById() throws Exception {
        String payload = "{\"adExtensionIds\" : '" + System.getProperty("smoke.logoAdExtension") + "', \"allowPartialFailure\" : 'true'}";
        headers.put("accountId", (System.getProperty("smoke.accountid")));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.LOGO_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/logo/get", headers, payload));
        assertResponseError(responseDTO);
    }

    @Test
    public void assertGetPhoneAdExtensionById() throws Exception {
        String payload = "{\"adExtensionIds\" : '" + System.getProperty("smoke.phoneAdExtension") + "', \"allowPartialFailure\" : 'true'}";
        headers.put("accountId", (System.getProperty("smoke.accountid")));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.PHONE_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/phone/get", headers, payload));
        assertResponseError(responseDTO);
    }

    @Test
    public void assertGetSiteLinkAdExtensionById() throws Exception {
        String payload = "{\"adExtensionIds\" : '" + System.getProperty("smoke.locationAdExtension") + "', \"allowPartialFailure\" : 'true'}";
        headers.put("accountId", (System.getProperty("smoke.accountid")));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.SITE_LINK_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/sitelink/get", headers, payload));
        assertResponseError(responseDTO);
    }
}