package com.yp.pm.kp.ui.regression.ad;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.AdGroup;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.ui.AdPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import com.yp.util.DBUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * Created by ar2130 on 2/1/16.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ViewDisapprovalReason extends BaseUITest {
    @Ignore
    public void testAdDisapprovalReason() throws Exception {
        Account account = getTestData();
        Campaign campaign = getCampaignByAccountId(account.getAccountId()).get(0);

        MyAgencyPage agencyPage = login();
        AdPage adPage = agencyPage.navigateToAdTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        adPage.loadingStatus();
        String disapprovalReason = adPage.getDisapprovalReason();
        logger.debug(disapprovalReason);
        assertObjectNotNull(adPage.getDisapprovalReason());
        assertConditionTrue(disapprovalReason.contains("headline contains adult related word"));
        assertConditionTrue(disapprovalReason.contains("description1 contains adult related word"));
        assertConditionTrue(disapprovalReason.contains("description2 contains adult related word"));

    }

    private Account getTestData() throws Exception {
        List<Map<String, Object>> AdultKeywordList = DBUtil.queryForList("SELECT * FROM (SELECT DISTINCT(ENTRY_KEY) FROM NG_WORDSET WHERE COMPONENT_TYPE = 'AD_COPY' AND DICTIONARY_NAME = 'adult' AND MATCHING_LEVEL='TOKEN' AND STATUS != 'DELETED' ORDER BY ENTRY_KEY) WHERE ROWNUM <= 50");
        Account account = createAccount(agencyId, "SERENITY_" + System.currentTimeMillis(), "America/Los_Angeles");
        Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, "STANDARD").getBudgetId());
        AdGroup adGroup = createAdGroupOnly(campaign.getAccountId(), campaign.getCampaignId());

        JSONArray adJSONList = new JSONArray();
        String entryKey = AdultKeywordList.get(1).get("ENTRY_KEY").toString();


        JSONObject json = new JSONObject();
        if (entryKey.length() > 35) {
            json.put("description1", entryKey.substring(0, 34));
            json.put("description2", entryKey.substring(0, 34));
        } else {
            json.put("description1", entryKey);
            json.put("description2", entryKey);
        }
        if (entryKey.length() > 25) {
            json.put("headLine", entryKey.substring(0, 24));
        } else {
            json.put("headLine", entryKey);
        }
        json.put("displayUrl", "www.robot.com");
        json.put("destinationUrl", "http://www.robot.com");
        json.put("adType", "text");
        adJSONList.add(json);

        createAd(adGroup.getAccountId(), adGroup.getAdGroupId(), adJSONList);
        return account;


    }
}
