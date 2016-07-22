package com.yp.pm.kp.api.regression.negativeLocation;

import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.CampaignGeoCodeDTO;
import com.yp.pm.kp.model.domain.Account;
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
public class AddNegativeLocationTest extends BaseAPITest {
    private static Map<String, Object> testData;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testAddNegativeLocationGeoCode() throws Exception {
        String payload = "{\"campaignGeoCodes\" : [{" +
                "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"geoCodeStatus\" : \"ACTIVE\", " +
                "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                "\"geoLibId\" : " + "371" + ", " +
                "\"bidModifier\" : 1, " +
                "\"excluded\" : true}, " +
                "{\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"geoCodeStatus\" : \"ACTIVE\", " +
                "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                "\"geoLibId\" : " + "1374" + ", " +
                "\"bidModifier\" : 1, " +
                "\"excluded\" : true}, " +
                "{\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"geoCodeStatus\" : \"ACTIVE\", " +
                "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                "\"geoLibId\" : " + "1375" + ", " +
                "\"bidModifier\" : 1, " +
                "\"excluded\" : true}, " +
                "{\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"geoCodeStatus\" : \"ACTIVE\", " +
                "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                "\"geoLibId\" : " + "2237" + ", " +
                "\"bidModifier\" : 1, " +
                "\"excluded\" : true}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.POST, "account/campaign/geo/create", headers, payload));
        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 4);
        int i = 0;
        for (ResultsDTO result : responseDTO.getResults()) {
            assertConditionTrue(result.getIndex() == i);
            i += 1;
            CampaignGeoCodeDTO campaignGeoCodeDTO = (CampaignGeoCodeDTO) result.getObject();
            assertObjectEqual(true, getCampaignGeoCode(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId(), campaignGeoCodeDTO.getId()).isExcluded());
        }
    }

    @Test
    public void testAllowPartialFailureNegativeLocation() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, deliveryMethod).getBudgetId());

        String payload = "{\"campaignGeoCodes\" : [{" +
                "\"campaignId\" : " + campaign.getCampaignId() + ", " +
                "\"geoCodeStatus\" : \"ACTIVE\", " +
                "\"accountId\" : " + account.getAccountId() + ", " +
                "\"geoLibId\" : " + getGeoLibId() + ", " +
                "\"bidModifier\" : 1, " +
                "\"excluded\" : true}], " +
                "\"allowPartialFailure\" : true}";
        headers.put("accountId", account.getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.POST, "account/campaign/geo/create", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            CampaignGeoCodeDTO campaignGeoCodeDTO = (CampaignGeoCodeDTO) result.getObject();
            assertObjectEqual(true, getCampaignGeoCode(account.getAccountId(), campaign.getCampaignId(), campaignGeoCodeDTO.getId()).isExcluded());
        }
    }

    @Test
    public void testAddNegativeLocationGeoCodeWithPointRadius() throws Exception {
        String payload = "{\"campaignGeoCodes\" : [{" +
                "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"geoCodeStatus\" : \"ACTIVE\", " +
                "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                "\"geoLibId\" : null, " +
                "\"bidModifier\" : 1, " +
                "\"pointName\" : \"POINT_RADIUS\", " +
                "\"latitude\" : 34.155582, " +
                "\"longitude\" : -118.254943, " +
                "\"radiusInMiles\" : 10, " +
                "\"excluded\" : true}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.POST, "account/campaign/geo/create", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);


        for (ResultsDTO result : responseDTO.getResults()) {
            CampaignGeoCodeDTO campaignGeoCodeDTO = (CampaignGeoCodeDTO) result.getObject();
            assertObjectEqual(true, getCampaignGeoCode(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId(), campaignGeoCodeDTO.getId()).isExcluded());
        }
    }

    @Test
    public void testInvalidNegativeLocationBidModifier() throws Exception {
        String payload = "{\"campaignGeoCodes\" : [{" +
                "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"geoCodeStatus\" : \"ACTIVE\", " +
                "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                "\"geoLibId\" : " + getGeoLibId() + ", " +
                "\"bidModifier\" : 11, " +
                "\"excluded\" : true}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.POST, "account/campaign/geo/create", headers, payload));

        assertObjectEqual(responseDTO.getTotalCount(), 0);
        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Location bid adjustment should be between -90% (0.1) and 900% (10.0)");
    }

    @Test
    public void testInvalidNegativeLocationBidModifierAllowPartialFailure() throws Exception {
        String payload = "{\"campaignGeoCodes\" : [{" +
                "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"geoCodeStatus\" : \"ACTIVE\", " +
                "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                "\"geoLibId\" : " + getGeoLibId() + ", " +
                "\"bidModifier\" : 11, " +
                "\"excluded\" : true}], " +
                "\"allowPartialFailure\" : true}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.POST, "account/campaign/geo/create", headers, payload));

        assertObjectEqual(responseDTO.getTotalCount(), 0);
        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Location bid adjustment should be between -90% (0.1) and 900% (10.0)");
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