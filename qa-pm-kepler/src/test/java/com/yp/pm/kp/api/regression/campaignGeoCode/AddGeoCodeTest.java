package com.yp.pm.kp.api.regression.campaignGeoCode;


import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.CampaignGeoCodeDTO;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.util.DBUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
public class AddGeoCodeTest extends BaseAPITest {
    private Map<String, String> headers = getRequestHeader();

    private static Map<String, Object> testData;

    @Test
    public void testAddGeoCode() throws Exception {
        String payload = "{\"campaignGeoCodes\" : [{" +
                "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"geoCodeStatus\" : \"ACTIVE\", " +
                "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                "\"geoLibId\" : " + "37857" + ", " +
                "\"bidModifier\" : 1, " +
                "\"excluded\" : false}, " +
                "{\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"geoCodeStatus\" : \"ACTIVE\", " +
                "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                "\"geoLibId\" : " + "37873" + ", " +
                "\"bidModifier\" : 1, " +
                "\"excluded\" : false}, " +
                "{\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"geoCodeStatus\" : \"ACTIVE\", " +
                "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                "\"geoLibId\" : " + "37872" + ", " +
                "\"bidModifier\" : 1, " +
                "\"excluded\" : false}, " +
                "{\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"geoCodeStatus\" : \"ACTIVE\", " +
                "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                "\"geoLibId\" : " + "37889" + ", " +
                "\"bidModifier\" : 1, " +
                "\"excluded\" : false}, " +
                "{\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"geoCodeStatus\" : \"ACTIVE\", " +
                "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                "\"geoLibId\" : " + "37842" + ", " +
                "\"bidModifier\" : 1, " +
                "\"excluded\" : false}], " +
                "\"allowPartialFailure\" : false}";


        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.POST, "account/campaign/geo/create", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 5);
        int i = 0;
        for (ResultsDTO result : responseDTO.getResults()) {
            assertConditionTrue(result.getIndex() == i);
            i += 1;
            CampaignGeoCodeDTO campaignGeoCodeDTO = (CampaignGeoCodeDTO) result.getObject();
            assertObjectEqual(campaignGeoCodeDTO.getId(), getCampaignGeoCode(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId(), campaignGeoCodeDTO.getId()).getGeocodeId());
        }
    }

    @Test
    public void testAllowPartialFailure() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
        String payload = "{\"campaignGeoCodes\" : [{" +
                "\"campaignId\" : " + campaign.getCampaignId() + ", " +
                "\"geoCodeStatus\" : \"ACTIVE\", " +
                "\"accountId\" : " + account.getAccountId() + ", " +
                "\"geoLibId\" : " + getGeoLibId() + ", " +
                "\"bidModifier\" : 1, " +
                "\"excluded\" : false}], " +
                "\"allowPartialFailure\" : true}";
        headers.put("accountId", account.getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.POST, "account/campaign/geo/create", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            CampaignGeoCodeDTO campaignGeoCodeDTO = (CampaignGeoCodeDTO) result.getObject();
            assertObjectEqual(campaignGeoCodeDTO.getId(), getCampaignGeoCode(account.getAccountId(), campaign.getCampaignId(), campaignGeoCodeDTO.getId()).getGeocodeId());
        }
    }

    @Test
    public void testInvalidAccountId() throws Exception {
        String payload = "{\"campaignGeoCodes\" : [{" +
                "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"geoCodeStatus\" : \"ACTIVE\", " +
                "\"accountId\" : " + "12345" + ", " +
                "\"geoLibId\" : " + getGeoLibId() + ", " +
                "\"bidModifier\" : 1, " +
                "\"excluded\" : false}], " +
                "\"allowPartialFailure\" : true}";
        headers.put("accountId", "12345");
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.POST, "account/campaign/geo/create", headers, payload));

        assertObjectEqual(responseDTO.getTotalCount(), 0);
        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Campaign with the specified ID does not exist");

    }

    @Test
    public void testHeaderInvalidAccountId() throws Exception {
        String payload = "{\"campaignGeoCodes\" : [{" +
                "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"geoCodeStatus\" : \"ACTIVE\", " +
                "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                "\"geoLibId\" : " + getGeoLibId() + ", " +
                "\"bidModifier\" : 1, " +
                "\"excluded\" : false}], " +
                "\"allowPartialFailure\" : true}";
        headers.put("accountId", "12345");
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.POST, "account/campaign/geo/create", headers, payload));

        assertObjectEqual(responseDTO.getTotalCount(), 0);
        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Campaign with the specified ID does not exist");

    }

    @Test
    public void testAddGeoCodeWithPointRadius() throws Exception {
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
                "\"excluded\" : false}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.POST, "account/campaign/geo/create", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            CampaignGeoCodeDTO campaignGeoCodeDTO = (CampaignGeoCodeDTO) result.getObject();
            assertObjectEqual(campaignGeoCodeDTO.getId(), getCampaignGeoCode(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId(), campaignGeoCodeDTO.getId()).getGeocodeId());
        }
    }

    @Test
    public void testInvalidBidModifier() throws Exception {
        String payload = "{\"campaignGeoCodes\" : [{" +
                "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"geoCodeStatus\" : \"ACTIVE\", " +
                "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                "\"geoLibId\" : " + getGeoLibId() + ", " +
                "\"bidModifier\" : 11, " +
                "\"excluded\" : false}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.POST, "account/campaign/geo/create", headers, payload));

        assertObjectEqual(responseDTO.getTotalCount(), 0);
        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Location bid adjustment should be between -90% (0.1) and 900% (10.0)");
    }

    @Test
    public void testInvalidBidModifierAllowPartialFailure() throws Exception {
        String payload = "{\"campaignGeoCodes\" : [{" +
                "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                "\"geoCodeStatus\" : \"ACTIVE\", " +
                "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                "\"geoLibId\" : " + getGeoLibId() + ", " +
                "\"bidModifier\" : 11, " +
                "\"excluded\" : false}], " +
                "\"allowPartialFailure\" : true}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.POST, "account/campaign/geo/create", headers, payload));

        assertObjectEqual(responseDTO.getTotalCount(), 0);
        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Location bid adjustment should be between -90% (0.1) and 900% (10.0)");
    }

    @Test
    public void testAddGeoCodeCustomLimit() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        try {
            DBUtil.update("INSERT INTO PM_CUSTOM_LIMIT_VALIDATION VALUES('geocode', 5, ".concat(account.getAccountId().toString()).concat(", 'account', '").concat(userName) + "',SYSDATE,'" + userName + "',SYSDATE)");
            Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
            int[] geoArray = {37854, 6203, 5981, 37827, 26498};
            JSONArray newGeos = new JSONArray();
            for (int y : geoArray) {
                JSONObject newGeo = new JSONObject();
                newGeo.put("geoLibId", y);
                newGeos.add(newGeo);
            }

            createCampaignGeoCode(account.getAccountId(), campaign.getCampaignId(), newGeos);

            String payload = "{\"campaignGeoCodes\" : [{" +
                    "\"campaignId\" : " + campaign.getCampaignId() + ", " +
                    "\"geoCodeStatus\" : \"ACTIVE\", " +
                    "\"accountId\" : " + account.getAccountId() + ", " +
                    "\"geoLibId\" : " + getGeoLibId() + ", " +
                    "\"bidModifier\" : 1, " +
                    "\"excluded\" : false}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", account.getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.POST, "account/campaign/geo/create", headers, payload));


            for (ResultsDTO result : responseDTO.getResults()) {
                assertObjectEqual(result.getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
                assertObjectEqual(result.getError().getMessage(), "Exceeds maximum number of location targets in campaign");
            }
        } finally {
            DBUtil.update("DELETE FROM PM_CUSTOM_LIMIT_VALIDATION WHERE PARENT_ID = ".concat(account.getAccountId().toString()));
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