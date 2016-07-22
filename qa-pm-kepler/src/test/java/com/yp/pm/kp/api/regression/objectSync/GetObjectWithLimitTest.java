package com.yp.pm.kp.api.regression.objectSync;

import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.enums.model.CampaignStatusEnum;
import com.yp.pm.kp.model.domain.*;
import com.yp.pm.kp.model.other.Bid;
import com.yp.util.DBUtil;
import com.yp.util.DateUtil;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GetObjectWithLimitTest extends BaseAPITest {

    @Before
    public void updateParameter() throws Exception {
        // set TIME_TO_LIVE_IN_SECS = 1 for too_many_changes_limit parameter
        if (Long.valueOf(DBUtil.queryForList("SELECT TIME_TO_LIVE_IN_SECS FROM PM_PARAMETER WHERE PARAMETER_NAME = 'too_many_changes_limit'").get(0).get("TIME_TO_LIVE_IN_SECS").toString()) > 1) {
            DBUtil.update("UPDATE PM_PARAMETER SET TIME_TO_LIVE_IN_SECS = 1 WHERE PARAMETER_NAME = 'too_many_changes_limit'");
        }
    }

    @Test
    public void testChangesLimit() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), budgetAmount, deliveryMethod);
        Campaign campaign = createCampaign(account.getAccountId(), budget.getBudgetId()).get(0);
        Map<String, Object> data = DBUtil.queryForList("SELECT PARAMETER_VALUE FROM PM_PARAMETER WHERE PARAMETER_NAME = 'too_many_changes_limit'").get(0);
        String currentLimit = data.get("PARAMETER_VALUE").toString();

        AdGroup adGroup = getAdGroupByCampaignId(account.getAccountId(), campaign.getCampaignId()).get(0);
        Keyword keyword = getKeywordByAdGroupId(account.getAccountId(), adGroup.getAdGroupId()).get(0);

        Campaign updateCampaign = new Campaign();
        updateCampaign.setAccountId(account.getAccountId());
        updateCampaign.setCampaignId(campaign.getCampaignId());
        updateCampaign.setCampaignStatus(CampaignStatusEnum.PAUSED);
        updateCampaign(updateCampaign);

        Keyword updateKeyword = new Keyword();
        updateKeyword.setAccountId(account.getAccountId());
        updateKeyword.setKeywordId(keyword.getKeywordId());
        updateKeyword.setAdGroupId(adGroup.getAdGroupId());
        updateKeyword.setMaxCpc(Bid.fromMicroDollar(3000000));
        updateKeyword(updateKeyword);

        try {
            DBUtil.update("UPDATE PM_PARAMETER SET PARAMETER_VALUE = 1 WHERE PARAMETER_NAME = 'too_many_changes_limit'");
            Thread.sleep(5000);
            String command = "curl -X POST " +
                    "-d '{ \"allowPartialFailure\" : 'true', \"modifiedSince\" : \"" + DateUtil.getCurrentDate(new Timestamp(System.currentTimeMillis())).concat("-000000") + "\"}' " +
                    "-H '" + contentType + "' " +
                    "-H 'username:'" + userName + "'' " +
                    "-H 'requestid:'" + requestId + "'' " +
                    "-H 'accountId:'" + account.getAccountId() + "'' " +
                    "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/account/syncbackobjects";

            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.SYNC, getAPIResponse(command));

            assertObjectEqual(responseDTO.getTotalCount(), 0);

            for (ResultsDTO result : responseDTO.getResults()) {
                assertObjectEqual(result.getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
                assertObjectEqual(result.getError().getMessage(), "Too many changes made since the requested date");
                assertObjectEqual(result.getError().getCode(), 400000);
            }
        } finally {
            DBUtil.update("UPDATE PM_PARAMETER SET PARAMETER_VALUE = " + currentLimit + " WHERE PARAMETER_NAME = 'too_many_changes_limit'");
            Thread.sleep(5000);
        }
    }
}