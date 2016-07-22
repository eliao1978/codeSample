package com.yp.pm.kp.api.regression.campaignGeoCode;


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
public class DeleteGeoCodeTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testDeleteGeocode() throws Exception {
        Campaign campaign = createCampaignOnly(((Account) getTestData().get("account")).getAccountId(), createBudget(((Account) getTestData().get("account")).getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
        List<CampaignGeoCode> campaignGeoCodeList = createCampaignGeoCode(((Account) getTestData().get("account")).getAccountId(), campaign.getCampaignId());

        for (CampaignGeoCode code : campaignGeoCodeList) {
            String payload = "{\"campaignIds\" : '[" + campaign.getCampaignId() + "]', " +
                    "\"campaignGeoCodeIds\" : '[" + code.getGeocodeId() + "]', " +
                    "\"allowPartialFailure\" : false}";
            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_GEO_CODE, getAPIResponse(MethodEnum.DELETE, "account/campaign/geo/delete", headers, payload));

            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 1);

            CampaignGeoCode deletedCampaignGeoCode = getCampaignGeoCode(((Account) getTestData().get("account")).getAccountId(), campaign.getCampaignId(), code.getGeocodeId());
            assertObjectEqual(deletedCampaignGeoCode.getGeoCodeStatus().toString(), CampaignGeoCodeStatusEnum.DELETED.toString());
        }
    }

    @Test
    public void testAllowPartialFailure() throws Exception {
        Campaign campaign = createCampaignOnly(((Account) getTestData().get("account")).getAccountId(), createBudget(((Account) getTestData().get("account")).getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
        List<CampaignGeoCode> campaignGeoCodeList = createCampaignGeoCode(((Account) getTestData().get("account")).getAccountId(), campaign.getCampaignId());

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
        }
    }

    @Test
    public void testInvalidGeocodeId() throws Exception {
        Campaign campaign = createCampaignOnly(((Account) getTestData().get("account")).getAccountId(), createBudget(((Account) getTestData().get("account")).getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
        List<CampaignGeoCode> campaignGeoCodeList = createCampaignGeoCode(((Account) getTestData().get("account")).getAccountId(), campaign.getCampaignId());

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