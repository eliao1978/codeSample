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
import com.yp.pm.kp.model.domain.CampaignGeoCode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
@FixMethodOrder(MethodSorters.DEFAULT)
public class UpdateNegativeLocationTest extends BaseAPITest {
    private static Map<String, Object> testData;
    private static Map<String, Object> testData2;
    private static JSONArray newGeoCode = new JSONArray();
    private JSONObject newLocation = new JSONObject();
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testUpdateNegativeLocationGeoCode() throws Exception {
        newLocation.put("geoLibId", "37643");
        newLocation.put("excluded", "true");
        newGeoCode.add(newLocation);
        List<CampaignGeoCode> geoCodeList = getCampaignGeoCodeByCampaignId(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
        for (CampaignGeoCode code : geoCodeList) {
            String payload = "{\"campaignGeoCodes\" : [{" +
                    "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                    "\"geocodeId\" : " + code.getGeocodeId() + ", " +
                    "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                    "\"geoCodeStatus\" : \"ACTIVE\", " +
                    "\"bidModifier\" : 2}], " +
                    "\"allowPartialFailure\" : false}'";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.PUT, "account/negative/update", headers, payload));


            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 1);

            for (ResultsDTO result : responseDTO.getResults()) {
                CampaignGeoCodeDTO campaignGeoCodeDTO = (CampaignGeoCodeDTO) result.getObject();
                CampaignGeoCode updatedCampaignGeoCode = getCampaignGeoCode(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId(), code.getGeocodeId());
                assertObjectEqual(campaignGeoCodeDTO.getId(), updatedCampaignGeoCode.getGeocodeId());
                assertObjectEqual(updatedCampaignGeoCode.getGeoCodeStatus().toString(), "ACTIVE");
                assertObjectEqual(updatedCampaignGeoCode.getBidModifier(), 2.0);
            }
            for (ResultsDTO result : responseDTO.getResults()) {
                CampaignGeoCodeDTO campaignGeoCodeDTO = (CampaignGeoCodeDTO) result.getObject();
                assertObjectEqual(true, getCampaignGeoCode(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId(), campaignGeoCodeDTO.getId()).isExcluded());
            }
        }
    }

    @Test
    public void testAllowNegativeLocationPartialFailure() throws Exception {
        newLocation.put("geoLibId", "37643");
        newLocation.put("excluded", "true");
        newGeoCode.add(newLocation);
        List<CampaignGeoCode> geoCodeList = getCampaignGeoCodeByCampaignId(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
        for (CampaignGeoCode code : geoCodeList) {
            String payload = "{\"campaignGeoCodes\" : [{" +
                    "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                    "\"geocodeId\" : " + code.getGeocodeId() + ", " +
                    "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                    "\"geoCodeStatus\" : \"ACTIVE\", " +
                    "\"bidModifier\" : 2}], " +
                    "\"allowPartialFailure\" : true}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.PUT, "account/negative/update", headers, payload));


            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 1);

            for (ResultsDTO result : responseDTO.getResults()) {
                CampaignGeoCodeDTO campaignGeoCodeDTO = (CampaignGeoCodeDTO) result.getObject();
                CampaignGeoCode updatedCampaignGeoCode = getCampaignGeoCode(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId(), code.getGeocodeId());
                assertObjectEqual(campaignGeoCodeDTO.getId(), updatedCampaignGeoCode.getGeocodeId());
                assertObjectEqual(updatedCampaignGeoCode.getGeoCodeStatus().toString(), "ACTIVE");
                assertObjectEqual(updatedCampaignGeoCode.getBidModifier(), 2.0);
            }
            for (ResultsDTO result : responseDTO.getResults()) {
                CampaignGeoCodeDTO campaignGeoCodeDTO = (CampaignGeoCodeDTO) result.getObject();
                assertObjectEqual(true, getCampaignGeoCode(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId(), campaignGeoCodeDTO.getId()).isExcluded());
            }
        }
    }

    @Test
    public void testIgnoreUpdateNegativeLocationGeoCode() throws Exception {
        newLocation.put("geoLibId", "37643");
        newLocation.put("excluded", "true");
        newGeoCode.add(newLocation);
        List<CampaignGeoCode> geoCodeList = getCampaignGeoCodeByCampaignId(((Account) getTestData2().get("account")).getAccountId(), ((Campaign) getTestData2().get("campaign")).getCampaignId());
        for (CampaignGeoCode code : geoCodeList) {
            String command = "curl -X PUT " +
                    "-d '{\"campaignGeoCodes\" : [{" +
                    "\"campaignId\" : " + ((Campaign) getTestData2().get("campaign")).getCampaignId() + ", " +
                    "\"geocodeId\" : -1, " +
                    "\"accountId\" : " + ((Account) getTestData2().get("account")).getAccountId() + ", " +
                    "\"geoCodeStatus\" : \"ACTIVE\", " +
                    "\"bidModifier\" : 2}], " +
                    "\"allowPartialFailure\" : false}' " +
                    "-H '" + contentType + "' " +
                    "-H 'username:'" + userName + "'' " +
                    "-H 'requestid:'" + requestId + "'' " +
                    "-H 'accountId:'" + ((Account) getTestData2().get("account")).getAccountId() + "'' " +
                    "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/account/campaign/geo/update";

            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(command));

            assertResponseError(responseDTO);

            for (ResultsDTO result : responseDTO.getResults()) {
                CampaignGeoCodeDTO campaignGeoCodeDTO = (CampaignGeoCodeDTO) result.getObject();
                CampaignGeoCode updatedCampaignGeoCode = getCampaignGeoCode(((Account) getTestData2().get("account")).getAccountId(), ((Campaign) getTestData2().get("campaign")).getCampaignId(), code.getGeocodeId());
                assertObjectEqual(campaignGeoCodeDTO.getId(), -1);
                assertObjectEqual(updatedCampaignGeoCode.getGeoCodeStatus().toString(), "ACTIVE");
                assertObjectEqual(updatedCampaignGeoCode.getBidModifier(), 5.0);
            }
        }
    }

    @Test
    public void testMissingNegativeLocationGeoCodeId() throws Exception {
        List<CampaignGeoCode> geoCodeList = ((List<CampaignGeoCode>) getTestData().get("geoCode"));
        for (CampaignGeoCode ignored : geoCodeList) {
            String payload = "{\"campaignGeoCodes\" : [{" +
                    "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                    "\"geoCodeStatus\" : \"ACTIVE\", " +
                    "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                    "\"geocodeId\" : \"\", " +
                    "\"bidModifier\" : 1, " +
                    "\"excluded\" : true}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.PUT, "account/negative/update", headers, payload));
            assertObjectEqual(responseDTO.getTotalCount(), 0);
            assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Geocode id can not be null");
        }
    }


    @Test
    public void testMissingNegativeLocationGeoCodeIdAllowPartialFailure() throws Exception {
        List<CampaignGeoCode> geoCodeList = ((List<CampaignGeoCode>) getTestData().get("geoCode"));
        for (CampaignGeoCode ignored : geoCodeList) {
            String payload = "{\"campaignGeoCodes\" : [{" +
                    "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                    "\"geoCodeStatus\" : \"ACTIVE\", " +
                    "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                    "\"geocodeId\" : \"\", " +
                    "\"bidModifier\" : 1, " +
                    "\"excluded\" : true}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.PUT, "account/negative/update", headers, payload));


            assertObjectEqual(responseDTO.getTotalCount(), 0);
            assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Geocode id can not be null");
        }
    }

    @Test
    public void testInvalidNegativeLocationCampaignId() throws Exception {
        List<CampaignGeoCode> geoCodeList = ((List<CampaignGeoCode>) getTestData().get("geoCode"));

        for (CampaignGeoCode code : geoCodeList) {
            String payload = "{\"campaignGeoCodes\" : [{" +
                    "\"campaignId\" : \"12345\", " +
                    "\"geoCodeStatus\" : \"ACTIVE\", " +
                    "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                    "\"geocodeId\" : " + code.getGeocodeId() + ", " +
                    "\"bidModifier\" : 1, " +
                    "\"excluded\" : true}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.PUT, "account/negative/update", headers, payload));


            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 0);

            for (ResultsDTO result : responseDTO.getResults()) {
                assertObjectEqual(((CampaignGeoCodeDTO) result.getObject()).getId(), -1);
            }
        }
    }


    @Test
    public void testMissingNegativeLocationCampaignId() throws Exception {
        List<CampaignGeoCode> geoCodeList = ((List<CampaignGeoCode>) getTestData().get("geoCode"));

        for (CampaignGeoCode code : geoCodeList) {
            String payload = "{\"campaignGeoCodes\" : [{" +
                    "\"campaignId\" : \"\", " +
                    "\"geoCodeStatus\" : \"ACTIVE\", " +
                    "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                    "\"geocodeId\" : " + code.getGeocodeId() + ", " + "\"bidModifier\" : 1, " +
                    "\"excluded\" : true}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.PUT, "account/negative/update", headers, payload));


            assertObjectEqual(responseDTO.getTotalCount(), 0);
            assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Campaign id can not be null");
        }
    }


    @Test
    public void testMissingNegativeLocationCampaignIdAllowPartialFailure() throws Exception {
        List<CampaignGeoCode> geoCodeList = ((List<CampaignGeoCode>) getTestData().get("geoCode"));

        for (CampaignGeoCode code : geoCodeList) {
            String payload = "'{\"campaignGeoCodes\" : [{" +
                    "\"campaignId\" : \"\", " +
                    "\"geoCodeStatus\" : \"ACTIVE\", " +
                    "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                    "\"geocodeId\" : " + code.getGeocodeId() + ", " +
                    "\"bidModifier\" : 1, " +
                    "\"excluded\" : true}], " +
                    "\"allowPartialFailure\" : true}'";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.PUT, "account/negative/update", headers, payload));

            assertObjectEqual(responseDTO.getTotalCount(), 0);
            assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Campaign id can not be null");
        }
    }


    @Test
    public void testInvalidNegativeLocationAccountId() throws Exception {
        List<CampaignGeoCode> geoCodeList = ((List<CampaignGeoCode>) getTestData().get("geoCode"));

        for (CampaignGeoCode code : geoCodeList) {
            String payload = "{\"campaignGeoCodes\" : [{" +
                    "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                    "\"geoCodeStatus\" : \"ACTIVE\", " +
                    "\"accountId\" : \"12345\", " +
                    "\"geocodeId\" : " + code.getGeocodeId() + ", " +
                    "\"bidModifier\" : 1, " +
                    "\"excluded\" : true}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.PUT, "account/negative/update", headers, payload));


            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 0);

            for (ResultsDTO result : responseDTO.getResults()) {
                assertObjectEqual(((CampaignGeoCodeDTO) result.getObject()).getId(), -1);
            }
        }
    }

    @Test
    public void testMissingNegativeLocationAccountId() throws Exception {
        List<CampaignGeoCode> geoCodeList = ((List<CampaignGeoCode>) getTestData().get("geoCode"));

        for (CampaignGeoCode code : geoCodeList) {
            String payload = "{\"campaignGeoCodes\" : [{" +
                    "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                    "\"geoCodeStatus\" : \"ACTIVE\", " +
                    "\"accountId\" : \"\", " +
                    "\"geocodeId\" : " + code.getGeocodeId() + ", " +
                    "\"bidModifier\" : 1, " +
                    "\"excluded\" : true}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.PUT, "account/negative/update", headers, payload));


            assertObjectEqual(responseDTO.getTotalCount(), 0);
            assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Account id can not be null");
        }
    }


    @Test
    public void testMissingNegativeLocationAccountIdPartialFailure() throws Exception {
        List<CampaignGeoCode> geoCodeList = ((List<CampaignGeoCode>) getTestData().get("geoCode"));

        for (CampaignGeoCode code : geoCodeList) {
            String payload = "{\"campaignGeoCodes\" : [{" +
                    "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                    "\"geoCodeStatus\" : \"ACTIVE\", " +
                    "\"accountId\" : \"\", " +
                    "\"geocodeId\" : " + code.getGeocodeId() + ", " +
                    "\"bidModifier\" : 1, " +
                    "\"excluded\" : true}], " +
                    "\"allowPartialFailure\" : true}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.PUT, "account/negative/update", headers, payload));


            assertObjectEqual(responseDTO.getTotalCount(), 0);
            assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Account id can not be null");
        }
    }


    @Test
    public void testInvalidNegativeLocationBidModifier() throws Exception {
        List<CampaignGeoCode> geoCodeList = ((List<CampaignGeoCode>) getTestData().get("geoCode"));

        for (CampaignGeoCode code : geoCodeList) {
            String payload = " '{\"campaignGeoCodes\" : [{" +
                    "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                    "\"geoCodeStatus\" : \"ACTIVE\", " +
                    "\"accountId\" : \"\", " +
                    "\"geocodeId\" : " + code.getGeocodeId() + ", " +
                    "\"bidModifier\" : 13}], " +
                    "\"excluded\" : true}], " +
                    "\"allowPartialFailure\" : true}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.NEGATIVE_KEYWORD, getAPIResponse(MethodEnum.PUT, "account/negative/update", headers, payload));


            assertObjectEqual(responseDTO.getTotalCount(), 0);
            assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Location bid adjustment should be between -90% (0.1) and 900% (10.0)");
        }
    }


    private Map<String, Object> getTestData2() throws Exception {
        if (testData2 == null) {
            testData2 = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
            List<CampaignGeoCode> campaignGeoCodeList = createCampaignGeoCode(account.getAccountId(), campaign.getCampaignId());
            testData2.put("account", account);
            testData2.put("geoCode", campaignGeoCodeList);
            testData2.put("campaign", campaign);
        }
        return testData2;
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
            List<CampaignGeoCode> campaignGeoCodeList = createCampaignGeoCode(account.getAccountId(), campaign.getCampaignId(), newGeoCode);
            testData.put("account", account);
            testData.put("campaign", campaign);
            testData.put("geoCode", campaignGeoCodeList);
        }
        return testData;
    }
}
