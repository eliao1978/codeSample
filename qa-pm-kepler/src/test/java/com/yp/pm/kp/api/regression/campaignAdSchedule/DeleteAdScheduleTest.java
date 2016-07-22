package com.yp.pm.kp.api.regression.campaignAdSchedule;


import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.CampaignAdScheduleDTO;
import com.yp.pm.kp.enums.model.AdGroupStatusEnum;
import com.yp.pm.kp.enums.model.CampaignAdScheduleStatusEnum;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.model.domain.CampaignAdSchedule;
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
public class DeleteAdScheduleTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testDeleteAdSchedule() throws Exception {
        Campaign campaign = createCampaignOnly(((Account) getTestData().get("account")).getAccountId(), createBudget(((Account) getTestData().get("account")).getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
        List<CampaignAdSchedule> campaignAdScheduleList = createCampaignAdSchedule(((Account) getTestData().get("account")).getAccountId(), campaign.getCampaignId());

        for (CampaignAdSchedule schedule : campaignAdScheduleList) {
            String payload = "{\"campaignIds\" : '[" + campaign.getCampaignId() + "]', " +
                    "\"adScheduleIds\" : '[" + schedule.getAdScheduleId() + "]', " +
                    "\"allowPartialFailure\" : 'false'}";

            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_AD_SCHEDULE, getAPIResponse(MethodEnum.DELETE, "account/campaign/adschedule/delete", headers, payload));

            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 1);

            CampaignAdSchedule deletedCampaignAdSchedule = getCampaignAdSchedule(((Account) getTestData().get("account")).getAccountId(), campaign.getCampaignId(), schedule.getAdScheduleId());
            assertObjectEqual(deletedCampaignAdSchedule.getAdScheduleStatus().toString(), AdGroupStatusEnum.DELETED.toString());
        }
    }

    @Test
    public void testAllowPartialFailure() throws Exception {
        Campaign campaign = createCampaignOnly(((Account) getTestData().get("account")).getAccountId(), createBudget(((Account) getTestData().get("account")).getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
        List<CampaignAdSchedule> campaignAdScheduleList = createCampaignAdSchedule(((Account) getTestData().get("account")).getAccountId(), campaign.getCampaignId());

        for (CampaignAdSchedule schedule : campaignAdScheduleList) {
            String payload = "{\"campaignIds\" : '[" + campaign.getCampaignId() + "]', " +
                    "\"adScheduleIds\" : '[" + schedule.getAdScheduleId() + "]', " +
                    "\"allowPartialFailure\" : 'true'}";

            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_AD_SCHEDULE, getAPIResponse(MethodEnum.DELETE, "account/campaign/adschedule/delete", headers, payload));

            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 1);

            CampaignAdSchedule deletedCampaignAdSchedule = getCampaignAdSchedule(((Account) getTestData().get("account")).getAccountId(), campaign.getCampaignId(), schedule.getAdScheduleId());
            assertObjectEqual(deletedCampaignAdSchedule.getAdScheduleStatus().toString(), AdGroupStatusEnum.DELETED.toString());
        }
    }

    @Test
    public void testInvalidAdScheduleId() throws Exception {
        Campaign campaign = createCampaignOnly(((Account) getTestData().get("account")).getAccountId(), createBudget(((Account) getTestData().get("account")).getAccountId(), budgetAmount, deliveryMethod).getBudgetId());
        List<CampaignAdSchedule> campaignAdScheduleList = createCampaignAdSchedule(((Account) getTestData().get("account")).getAccountId(), campaign.getCampaignId());

        for (CampaignAdSchedule schedule : campaignAdScheduleList) {
            String payload = "{\"campaignIds\" : '[" + campaign.getCampaignId() + "]', " +
                    "\"adScheduleIds\" : '[" + 12345 + "]', " +
                    "\"allowPartialFailure\" : 'false'}";

            headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.CAMPAIGN_AD_SCHEDULE, getAPIResponse(MethodEnum.DELETE, "account/campaign/adschedule/delete", headers, payload));

            assertResponseError(responseDTO);
            assertObjectEqual(responseDTO.getTotalCount(), 0);

            for (ResultsDTO result : responseDTO.getResults()) {
                assertObjectEqual(((CampaignAdScheduleDTO) result.getObject()).getId(), -1);
            }

            CampaignAdSchedule deletedCampaignAdSchedule = getCampaignAdSchedule(((Account) getTestData().get("account")).getAccountId(), campaign.getCampaignId(), schedule.getAdScheduleId());
            assertObjectEqual(deletedCampaignAdSchedule.getAdScheduleStatus().toString(), CampaignAdScheduleStatusEnum.ACTIVE.toString());
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