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
import com.yp.pm.kp.model.domain.CampaignGeoCode;
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
public class UpdateGeoCodeTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private static Map<String, Object> testData2;
    private Map<String, String> headers = getRequestHeader();

    public String generateGeoCodeString(long campaignID, long geoCodeId, long accountId, String stat) {
        return " {" +
                "\"campaignId\" : " + campaignID + ", " +
                "\"geocodeId\" : " + geoCodeId + ", " +
                "\"accountId\" : " + accountId + ", " +
                "\"geoCodeStatus\" : \"" + stat + "\", " +
                "\"bidModifier\" : 2 " + "}";
    }

    @Test
    public void testUpdateGeoCode() throws Exception {
        List<CampaignGeoCode> geoCodeList = ((List<CampaignGeoCode>) getTestData().get("geoCode"));

        for (CampaignGeoCode code : geoCodeList) {
            String payload = "{\"campaignGeoCodes\" : [{" +
                    "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                    "\"geocodeId\" : " + code.getGeocodeId() + ", " +
                    "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                    "\"geoCodeStatus\" : \"ACTIVE\", " +
                    "\"bidModifier\" : 2}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.PUT, "account/campaign/geo/update", headers, payload));

            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 1);

            for (ResultsDTO result : responseDTO.getResults()) {
                CampaignGeoCodeDTO campaignGeoCodeDTO = (CampaignGeoCodeDTO) result.getObject();
                CampaignGeoCode updatedCampaignGeoCode = getCampaignGeoCode(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId(), code.getGeocodeId());
                assertObjectEqual(campaignGeoCodeDTO.getId(), updatedCampaignGeoCode.getGeocodeId());
                assertObjectEqual(updatedCampaignGeoCode.getGeoCodeStatus().toString(), "ACTIVE");
                assertObjectEqual(updatedCampaignGeoCode.getBidModifier(), 2.0);
            }
        }
    }

    @Test
    public void testAllowPartialFailure() throws Exception {
        List<CampaignGeoCode> geoCodeList = ((List<CampaignGeoCode>) getTestData().get("geoCode"));

        for (CampaignGeoCode code : geoCodeList) {
            String payload = "{\"campaignGeoCodes\" : [{" +
                    "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                    "\"geocodeId\" : " + code.getGeocodeId() + ", " +
                    "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                    "\"geoCodeStatus\" : \"ACTIVE\", " +
                    "\"bidModifier\" : 2}], " +
                    "\"allowPartialFailure\" : true}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.PUT, "account/campaign/geo/update", headers, payload));

            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 1);

            for (ResultsDTO result : responseDTO.getResults()) {
                CampaignGeoCodeDTO campaignGeoCodeDTO = (CampaignGeoCodeDTO) result.getObject();
                CampaignGeoCode updatedCampaignGeoCode = getCampaignGeoCode(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId(), code.getGeocodeId());
                assertObjectEqual(campaignGeoCodeDTO.getId(), updatedCampaignGeoCode.getGeocodeId());
                assertObjectEqual(updatedCampaignGeoCode.getGeoCodeStatus().toString(), "ACTIVE");
                assertObjectEqual(updatedCampaignGeoCode.getBidModifier(), 2.0);
            }
        }
    }

    @Test
    public void testIgnoreUpdateGeoCode() throws Exception {
        List<CampaignGeoCode> geoCodeList = ((List<CampaignGeoCode>) getTestData2().get("geoCode"));

        for (CampaignGeoCode code : geoCodeList) {
            String payload = "{\"campaignGeoCodes\" : [{" +
                    "\"campaignId\" : " + ((Campaign) getTestData2().get("campaign")).getCampaignId() + ", " +
                    "\"geocodeId\" : -1, " +
                    "\"accountId\" : " + ((Account) getTestData2().get("account")).getAccountId() + ", " +
                    "\"geoCodeStatus\" : \"ACTIVE\", " +
                    "\"bidModifier\" : 2}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.PUT, "account/campaign/geo/update", headers, payload));

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
    public void testObjectProblemType() throws Exception {
        long accountId = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles").getAccountId();
        Campaign campaign1 = createCampaignOnly(accountId, createBudget(accountId, budgetAmount, deliveryMethod).getBudgetId());
        List<CampaignGeoCode> geoCodeList1 = createCampaignGeoCode(accountId, campaign1.getCampaignId());
        Campaign campaign2 = createCampaignOnly(accountId, createBudget(accountId, budgetAmount, deliveryMethod).getBudgetId());
        List<CampaignGeoCode> geoCodeList2 = createCampaignGeoCode(accountId, campaign2.getCampaignId());
        Campaign campaign3 = createCampaignOnly(accountId, createBudget(accountId, budgetAmount, deliveryMethod).getBudgetId());
        List<CampaignGeoCode> geoCodeList3 = createCampaignGeoCode(accountId, campaign3.getCampaignId());
        String geoCodeString1 = generateGeoCodeString(campaign1.getCampaignId(), geoCodeList1.get(0).getGeocodeId(), accountId, "ACTIVE");
        String geoCodeString2 = generateGeoCodeString(campaign2.getCampaignId(), geoCodeList2.get(0).getGeocodeId(), accountId, "DELETED");
        String geoCodeString3 = generateGeoCodeString(campaign3.getCampaignId(), geoCodeList3.get(0).getGeocodeId(), accountId, "ACTIVE");


        String command = "curl -X PUT " +
                "-d '{\"campaignGeoCodes\" : [" + geoCodeString1 + "," + geoCodeString2 + "," + geoCodeString3 + "], " +
                "\"allowPartialFailure\" : false}' " +
                "-H '" + contentType + "' " +
                "-H 'username:'" + userName + "'' " +
                "-H 'requestid:'" + requestId + "'' " +
                "-H 'accountId:'" + accountId + "'' " +
                "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/account/campaign/geo/update";

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(command));

        for (ResultsDTO result : responseDTO.getResults()) {
            CampaignGeoCodeDTO campaignGeoCodeDTO = (CampaignGeoCodeDTO) result.getObject();
            if (result.getError().getMessage() != null) {
                assertObjectEqual(campaignGeoCodeDTO.getId(), -3);
            } else {
                assertObjectEqual(campaignGeoCodeDTO.getId(), -2);
            }
        }

        assertObjectEqual(getCampaignGeoCode(accountId, campaign1.getCampaignId(), geoCodeList1.get(0).getGeocodeId()).getGeoCodeStatus().toString(), "ACTIVE");
        assertObjectEqual(getCampaignGeoCode(accountId, campaign2.getCampaignId(), geoCodeList2.get(0).getGeocodeId()).getGeoCodeStatus().toString(), "ACTIVE");
        assertObjectEqual(getCampaignGeoCode(accountId, campaign3.getCampaignId(), geoCodeList3.get(0).getGeocodeId()).getGeoCodeStatus().toString(), "ACTIVE");
    }

    @Test
    public void testInvalidGeoCodeId() throws Exception {
        List<CampaignGeoCode> geoCodeList = ((List<CampaignGeoCode>) getTestData().get("geoCode"));

        for (CampaignGeoCode ignored : geoCodeList) {
            String payload = "{\"campaignGeoCodes\" : [{" +
                    "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                    "\"geocodeId\" : \"12345\", " +
                    "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                    "\"geoCodeStatus\" : \"ACTIVE\", " +
                    "\"bidModifier\" : 2}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.PUT, "account/campaign/geo/update", headers, payload));

            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 0);

            for (ResultsDTO result : responseDTO.getResults()) {
                assertObjectEqual(((CampaignGeoCodeDTO) result.getObject()).getId(), -1);
            }
        }
    }

    @Test
    public void testMissingGeoCodeId() throws Exception {
        List<CampaignGeoCode> geoCodeList = ((List<CampaignGeoCode>) getTestData().get("geoCode"));
        for (CampaignGeoCode ignored : geoCodeList) {
            String payload = "{\"campaignGeoCodes\" : [{" +
                    "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                    "\"geocodeId\" : \"\", " +
                    "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                    "\"geoCodeStatus\" : \"ACTIVE\", " +
                    "\"bidModifier\" : 2}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.PUT, "account/campaign/geo/update", headers, payload));

            assertObjectEqual(responseDTO.getTotalCount(), 0);
            assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Geocode id can not be null");
        }
    }

    @Test
    public void testMissingGeoCodeIdAllowPartialFailure() throws Exception {
        List<CampaignGeoCode> geoCodeList = ((List<CampaignGeoCode>) getTestData().get("geoCode"));

        for (CampaignGeoCode ignored : geoCodeList) {
            String payload = "{\"campaignGeoCodes\" : [{" +
                    "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                    "\"geocodeId\" : \"\", " +
                    "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                    "\"geoCodeStatus\" : \"ACTIVE\", " +
                    "\"bidModifier\" : 2}], " +
                    "\"allowPartialFailure\" : true}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.PUT, "account/campaign/geo/update", headers, payload));

            assertObjectEqual(responseDTO.getTotalCount(), 0);
            assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Geocode id can not be null");
        }
    }

    @Test
    public void testInvalidCampaignId() throws Exception {
        List<CampaignGeoCode> geoCodeList = ((List<CampaignGeoCode>) getTestData().get("geoCode"));

        for (CampaignGeoCode code : geoCodeList) {
            String payload = "{\"campaignGeoCodes\" : [{" +
                    "\"campaignId\" : \"12345\", " +
                    "\"geocodeId\" : " + code.getGeocodeId() + ", " +
                    "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                    "\"geoCodeStatus\" : \"ACTIVE\", " +
                    "\"bidModifier\" : 2}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.PUT, "account/campaign/geo/update", headers, payload));


            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 0);

            for (ResultsDTO result : responseDTO.getResults()) {
                assertObjectEqual(((CampaignGeoCodeDTO) result.getObject()).getId(), -1);
            }
        }
    }

    @Test
    public void testMissingCampaignId() throws Exception {
        List<CampaignGeoCode> geoCodeList = ((List<CampaignGeoCode>) getTestData().get("geoCode"));
        for (CampaignGeoCode code : geoCodeList) {
            String payload = "{\"campaignGeoCodes\" : [{" +
                    "\"campaignId\" : \"\", " +
                    "\"geocodeId\" : " + code.getGeocodeId() + ", " +
                    "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                    "\"geoCodeStatus\" : \"ACTIVE\", " +
                    "\"bidModifier\" : 2}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.PUT, "account/campaign/geo/update", headers, payload));

            assertObjectEqual(responseDTO.getTotalCount(), 0);
            assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Campaign id can not be null");
        }
    }

    @Test
    public void testMissingCampaignIdAllowPartialFailure() throws Exception {
        List<CampaignGeoCode> geoCodeList = ((List<CampaignGeoCode>) getTestData().get("geoCode"));
        for (CampaignGeoCode code : geoCodeList) {
            String payload = "{\"campaignGeoCodes\" : [{" +
                    "\"campaignId\" : \"\", " +
                    "\"geocodeId\" : " + code.getGeocodeId() + ", " +
                    "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                    "\"geoCodeStatus\" : \"ACTIVE\", " +
                    "\"bidModifier\" : 2}], " +
                    "\"allowPartialFailure\" : true}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.PUT, "account/campaign/geo/update", headers, payload));

            assertObjectEqual(responseDTO.getTotalCount(), 0);
            assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Campaign id can not be null");
        }
    }

    @Test
    public void testInvalidAccountId() throws Exception {
        List<CampaignGeoCode> geoCodeList = ((List<CampaignGeoCode>) getTestData().get("geoCode"));
        for (CampaignGeoCode code : geoCodeList) {
            String payload = "{\"campaignGeoCodes\" : [{" +
                    "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                    "\"geocodeId\" : " + code.getGeocodeId() + ", " +
                    "\"accountId\" : \"12345\", " +
                    "\"geoCodeStatus\" : \"ACTIVE\", " +
                    "\"bidModifier\" : 2}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", "12345");
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.PUT, "account/campaign/geo/update", headers, payload));


            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 0);

            for (ResultsDTO result : responseDTO.getResults()) {
                assertObjectEqual(((CampaignGeoCodeDTO) result.getObject()).getId(), -1);
            }
        }
    }

    @Test
    public void testHeaderInvalidAccountId() throws Exception {
        List<CampaignGeoCode> geoCodeList = ((List<CampaignGeoCode>) getTestData().get("geoCode"));
        for (CampaignGeoCode code : geoCodeList) {
            String payload = "{\"campaignGeoCodes\" : [{" +
                    "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                    "\"geocodeId\" : " + code.getGeocodeId() + ", " +
                    "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                    "\"geoCodeStatus\" : \"ACTIVE\", " +
                    "\"bidModifier\" : 2}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", "12345");
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.PUT, "account/campaign/geo/update", headers, payload));


            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 0);

            for (ResultsDTO result : responseDTO.getResults()) {
                assertObjectEqual(((CampaignGeoCodeDTO) result.getObject()).getId(), -1);
            }
        }
    }

    @Test
    public void testMissingAccountId() throws Exception {
        List<CampaignGeoCode> geoCodeList = ((List<CampaignGeoCode>) getTestData().get("geoCode"));
        for (CampaignGeoCode code : geoCodeList) {
            String payload = "{\"campaignGeoCodes\" : [{" +
                    "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                    "\"geocodeId\" : " + code.getGeocodeId() + ", " +
                    "\"accountId\" : \"\", " +
                    "\"geoCodeStatus\" : \"ACTIVE\", " +
                    "\"bidModifier\" : 2}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", "");
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.PUT, "account/campaign/geo/update", headers, payload));

            assertObjectEqual(responseDTO.getTotalCount(), 0);
            assertObjectEqual(responseDTO.getResultStatus().getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Account id can not be null");
        }
    }

    @Test
    public void testMissingAccountIdAllowPartialFailure() throws Exception {
        List<CampaignGeoCode> geoCodeList = ((List<CampaignGeoCode>) getTestData().get("geoCode"));
        for (CampaignGeoCode code : geoCodeList) {
            String payload = "{\"campaignGeoCodes\" : [{" +
                    "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                    "\"geocodeId\" : " + code.getGeocodeId() + ", " +
                    "\"accountId\" : \"\", " +
                    "\"geoCodeStatus\" : \"ACTIVE\", " +
                    "\"bidModifier\" : 2}], " +
                    "\"allowPartialFailure\" : true}";
            headers.put("accountId", "");
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.PUT, "account/campaign/geo/update", headers, payload));

            assertObjectEqual(responseDTO.getTotalCount(), 0);
            assertObjectEqual(responseDTO.getResultStatus().getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Account id can not be null");
        }
    }

    @Test
    public void testInvalidBidModifier() throws Exception {
        List<CampaignGeoCode> geoCodeList = ((List<CampaignGeoCode>) getTestData().get("geoCode"));
        for (CampaignGeoCode code : geoCodeList) {
            String payload = "{\"campaignGeoCodes\" : [{" +
                    "\"campaignId\" : " + ((Campaign) getTestData().get("campaign")).getCampaignId() + ", " +
                    "\"geocodeId\" : " + code.getGeocodeId() + ", " +
                    "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + ", " +
                    "\"geoCodeStatus\" : \"ACTIVE\", " +
                    "\"bidModifier\" : 11}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.PUT, "account/campaign/geo/update", headers, payload));

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
            List<CampaignGeoCode> campaignGeoCodeList = createCampaignGeoCode(account.getAccountId(), campaign.getCampaignId());

            testData.put("account", account);
            testData.put("geoCode", campaignGeoCodeList);
            testData.put("campaign", campaign);
        }

        return testData;
    }
}