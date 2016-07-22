package com.yp.pm.kp.ui.regression.keyword;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.AdGroup;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.ui.KeywordPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import com.yp.pm.kp.ui.UIMapper;
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
    public void testKeywordDisapprovalReason() throws Exception {
        Account account = getTestData();
        Campaign campaign = getCampaignByAccountId(account.getAccountId()).get(0);

        MyAgencyPage agencyPage = login();
        KeywordPage keywordPage = agencyPage.navigateToKeywordTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        keywordPage.waitForElementNotVisible(UIMapper.LOADING);
        String disapprovalReason = keywordPage.getDisapprovalReason();
        logger.debug(disapprovalReason);
        //assertObjectNotNull(keywordPage.getDisapprovalReason());
        //assertConditionTrue(disapprovalReason.contains("keyword contains adult related word"));

    }

    private Account getTestData() throws Exception {
        List<Map<String, Object>> AdultKeywordList = DBUtil.queryForList("SELECT * FROM (SELECT DISTINCT(ENTRY_KEY) FROM NG_WORDSET WHERE COMPONENT_TYPE = 'AD_COPY' AND DICTIONARY_NAME = 'adult' AND MATCHING_LEVEL='TOKEN' AND STATUS != 'DELETED' ORDER BY ENTRY_KEY) WHERE ROWNUM <= 50");
        Account account = createAccount(agencyId, "SERENITY_" + System.currentTimeMillis(), "America/Los_Angeles");
        Campaign campaign = createCampaignOnly(account.getAccountId(), createBudget(account.getAccountId(), budgetAmount, "STANDARD").getBudgetId());
        AdGroup adGroup = createAdGroupOnly(campaign.getAccountId(), campaign.getCampaignId());

        JSONArray kwJSONList = new JSONArray();
        String entryKey = AdultKeywordList.get(1).get("ENTRY_KEY").toString();

        JSONObject json2 = new JSONObject();
        json2.put("keywordText", entryKey);
        json2.put("matchType", "EXACT");
        json2.put("maxCPC", "1.00");
        kwJSONList.add(json2);

        createKeyword(adGroup.getAccountId(), adGroup.getAdGroupId(), kwJSONList);
        return account;

    }
}
