package com.yp.pm.kp.api.regression.negativeLocation;

import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.CampaignGeoCodeDTO;
import com.yp.pm.kp.enums.model.CampaignGeoCodeStatusEnum;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeleteNegativeLocationTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private static JSONArray newGeoCode = new JSONArray();
    private static JSONObject newLocation = new JSONObject();
    private Map<String, String> headers = getRequestHeader();


    @Test
    public void testDeleteNegativeLocationGeoCode() throws Exception {
        Campaign campaign = createCampaignOnly(((Account) getTestData().get("account")).getAccountId(), createBudget(((Account) getTestData().get("account")).getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
        newLocation.put("geoLibId", "37643");
        newLocation.put("excluded", "true");
        newGeoCode.add(newLocation);
        createCampaignGeoCode(((Account) getTestData().get("account")).getAccountId(), campaign.getCampaignId(), newGeoCode);
        List<CampaignGeoCode> campaignGeoCodeList = getCampaignGeoCodeByCampaignId(((Account) getTestData().get("account")).getAccountId(), campaign.getCampaignId());
        for (CampaignGeoCode code : campaignGeoCodeList) {
            String payload = "{\"campaignIds\" : '[" + campaign.getCampaignId() + "]', " +
                    "\"campaignGeoCodeIds\" : '[" + code.getGeocodeId() + "]', " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.DELETE, "account/campaign/geo/delete", headers, payload));

            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 1);
            CampaignGeoCode deletedCampaignGeoCode = getCampaignGeoCode((((Account) getTestData().get("account")).getAccountId()), campaign.getCampaignId(), code.getGeocodeId());
            assertObjectEqual(deletedCampaignGeoCode.getGeoCodeStatus().toString(), CampaignGeoCodeStatusEnum.DELETED.toString());
            for (ResultsDTO result : responseDTO.getResults()) {
                CampaignGeoCodeDTO campaignGeoCodeDTO = (CampaignGeoCodeDTO) result.getObject();
                assertObjectEqual(true, getCampaignGeoCode(((Account) getTestData().get("account")).getAccountId(), campaign.getCampaignId(), campaignGeoCodeDTO.getId()).isExcluded());
            }
        }
    }

    @Test
    public void testAllowNegativeLocationPartialFailure() throws Exception {
        Campaign campaign = createCampaignOnly(((Account) getTestData().get("account")).getAccountId(), createBudget(((Account) getTestData().get("account")).getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
        newLocation.put("geoLibId", "37643");
        newLocation.put("excluded", "true");
        newGeoCode.add(newLocation);
        createCampaignGeoCode(((Account) getTestData().get("account")).getAccountId(), campaign.getCampaignId(), newGeoCode);
        List<CampaignGeoCode> campaignGeoCodeList = getCampaignGeoCodeByCampaignId(((Account) getTestData().get("account")).getAccountId(), campaign.getCampaignId());

        for (CampaignGeoCode code : campaignGeoCodeList) {
            String payload = "{\"campaignIds\" : '[" + campaign.getCampaignId() + "]', " +
                    "\"campaignGeoCodeIds\" : '[" + code.getGeocodeId() + "]', " +
                    "\"allowPartialFailure\" : true}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.DELETE, "account/campaign/geo/delete", headers, payload));

            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 1);

            CampaignGeoCode deletedCampaignGeoCode = getCampaignGeoCode(((Account) getTestData().get("account")).getAccountId(), campaign.getCampaignId(), code.getGeocodeId());
            assertObjectEqual(deletedCampaignGeoCode.getGeoCodeStatus().toString(), CampaignGeoCodeStatusEnum.DELETED.toString());
            for (ResultsDTO result : responseDTO.getResults()) {
                CampaignGeoCodeDTO campaignGeoCodeDTO = (CampaignGeoCodeDTO) result.getObject();
                assertObjectEqual(true, getCampaignGeoCode(((Account) getTestData().get("account")).getAccountId(), campaign.getCampaignId(), campaignGeoCodeDTO.getId()).isExcluded());
            }
        }
    }

    @Test
    public void testInvalidNegativeLocationGeocodeId() throws Exception {
        Campaign campaign = createCampaignOnly(((Account) getTestData().get("account")).getAccountId(), createBudget(((Account) getTestData().get("account")).getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
        newLocation.put("geoLibId", "37643");
        newLocation.put("excluded", "true");
        newGeoCode.add(newLocation);
        createCampaignGeoCode(((Account) getTestData().get("account")).getAccountId(), campaign.getCampaignId(), newGeoCode);
        List<CampaignGeoCode> campaignGeoCodeList = getCampaignGeoCodeByCampaignId(((Account) getTestData().get("account")).getAccountId(), campaign.getCampaignId());
        for (CampaignGeoCode code : campaignGeoCodeList) {
            String payload = "{\"campaignIds\" : '[" + campaign.getCampaignId() + "]', " +
                    "\"campaignGeoCodeIds\" : '[" + 12345 + "]', " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.DELETE, "account/campaign/geo/delete", headers, payload));

            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 0);

            for (ResultsDTO result : responseDTO.getResults()) {
                assertObjectEqual(((CampaignGeoCodeDTO) result.getObject()).getId(), -1);
            }

            CampaignGeoCode deletedCampaignGeoCode = getCampaignGeoCode(((Account) getTestData().get("account")).getAccountId(), campaign.getCampaignId(), code.getGeocodeId());
            assertObjectEqual(deletedCampaignGeoCode.getGeoCodeStatus().toString(), CampaignGeoCodeStatusEnum.ACTIVE.toString());
        }
    }


    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            testData.put("account", account);
        }
        return testData;
    }
}