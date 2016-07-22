package com.yp.pm.kp.api.regression.campaignGeoCode;


import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
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
public class GetGeoCodeTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testGetGeoCodeById() throws Exception {
        List<CampaignGeoCode> campaignGeoCodeList = (List<CampaignGeoCode>) getTestData().get("campaignGeoCodeList");

        for (CampaignGeoCode code : campaignGeoCodeList) {
            String payload = "{\"campaignGeoCodeIds\" : '[" + code.getGeocodeId() + "]', " +
                    "\"allowPartialFailure\" : 'false'}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.POST, "account/campaign/geo/get", headers, payload));

            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 1);

            for (ResultsDTO result : responseDTO.getResults()) {
                CampaignGeoCodeDTO campaignGeoCodeDTO = (CampaignGeoCodeDTO) result.getObject();
                assertObjectEqual(campaignGeoCodeDTO.getAccountId(), code.getAccountId());
                assertObjectEqual(campaignGeoCodeDTO.getCampaignId(), code.getCampaignId());
                assertObjectEqual(campaignGeoCodeDTO.getId(), code.getGeocodeId());
                assertObjectEqual(campaignGeoCodeDTO.getStatus(), code.getGeoCodeStatus().toString());
                assertObjectNotNull(campaignGeoCodeDTO.getGeoLibId());
                assertObjectNotNull(campaignGeoCodeDTO.getBidModifier());
            }
        }
    }

    @Test
    public void testAllowPartialFailure() throws Exception {
        List<CampaignGeoCode> campaignGeoCodeList = (List<CampaignGeoCode>) getTestData().get("campaignGeoCodeList");

        for (CampaignGeoCode code : campaignGeoCodeList) {
            String payload = "{\"campaignGeoCodeIds\" : '[" + code.getGeocodeId() + "]', " +
                    "\"allowPartialFailure\" : 'true'}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.POST, "account/campaign/geo/get", headers, payload));

            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 1);

            for (ResultsDTO result : responseDTO.getResults()) {
                CampaignGeoCodeDTO campaignGeoCodeDTO = (CampaignGeoCodeDTO) result.getObject();
                assertObjectEqual(campaignGeoCodeDTO.getAccountId(), code.getAccountId());
                assertObjectEqual(campaignGeoCodeDTO.getCampaignId(), code.getCampaignId());
                assertObjectEqual(campaignGeoCodeDTO.getId(), code.getGeocodeId());
                assertObjectEqual(campaignGeoCodeDTO.getStatus(), code.getGeoCodeStatus().toString());
                assertObjectNotNull(campaignGeoCodeDTO.getGeoLibId());
                assertObjectNotNull(campaignGeoCodeDTO.getBidModifier());
            }
        }
    }

    @Test
    public void testInvalidGeoCodeId() throws Exception {
        String payload = "{\"campaignGeoCodeIds\" : '[" + 12345 + "]', " +
                "\"allowPartialFailure\" : 'false'}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.POST, "account/campaign/geo/get", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 0);

        for (ResultsDTO result : responseDTO.getResults()) {
            assertObjectEqual(((CampaignGeoCodeDTO) result.getObject()).getId(), -1);
        }
    }

    @Test
    public void testMissingGeoCodeId() throws Exception {
        List<CampaignGeoCode> campaignGeoCodeList = (List<CampaignGeoCode>) getTestData().get("campaignGeoCodeList");

        for (CampaignGeoCode code : campaignGeoCodeList) {
            String payload = "{\"campaignGeoCodeIds\" : '[]', " +
                    "\"allowPartialFailure\" : 'false'}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.POST, "account/campaign/geo/get", headers, payload));

            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 1);

            for (ResultsDTO result : responseDTO.getResults()) {
                CampaignGeoCodeDTO campaignGeoCodeDTO = (CampaignGeoCodeDTO) result.getObject();
                assertObjectEqual(campaignGeoCodeDTO.getAccountId(), code.getAccountId());
                assertObjectEqual(campaignGeoCodeDTO.getCampaignId(), code.getCampaignId());
                assertObjectEqual(campaignGeoCodeDTO.getId(), code.getGeocodeId());
                assertObjectEqual(campaignGeoCodeDTO.getStatus(), code.getGeoCodeStatus().toString());
                assertObjectNotNull(campaignGeoCodeDTO.getGeoLibId());
                assertObjectNotNull(campaignGeoCodeDTO.getBidModifier());
            }
        }
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
            List<CampaignGeoCode> campaignGeoCodeList = createCampaignGeoCode(account.getAccountId(), campaign.getCampaignId());

            testData.put("account", account);
            testData.put("campaign", campaign);
            testData.put("campaignGeoCodeList", campaignGeoCodeList);

        }

        return testData;
    }
}