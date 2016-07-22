package com.yp.pm.kp.ui.regression.keyword;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.AdGroup;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.ui.KeywordPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ViewKeywordTableTest extends BaseUITest {
    private static Map<String, Object> testData;
    String labels[] = {"Status", "Keyword", "Campaign", "Ad Group", "Serving Status", "Max. CPC", "Clicks", "Impr.", "CTR", "Avg. CPC", "Cost", "Avg. Pos.", "Dest. Url."};

    @Test
    public void tesTableHeaderLabels() throws Exception {
        MyAgencyPage agencyPage = login();
        KeywordPage keywordPage = agencyPage.navigateToKeywordTab(agencyId, ((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
        for (String label : labels) {
            if (!keywordPage.checkKeywordHeader(label)) {
                throw new NullPointerException("Element does not exist [" + label + "]");
            }
        }
    }

    @Test
    public void testSortTable() throws Exception {
        String[] keyword = (String[]) getTestData().get("keywordText");

        MyAgencyPage agencyPage = login();
        KeywordPage keywordPage = agencyPage.navigateToKeywordTab(agencyId, ((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
        keywordPage.clickKeywordHeader(3);
        Arrays.sort(keyword);
        assertConditionTrue(keywordPage.getTableText(2, 3).equals("[" + keyword[0] + "]"));
        assertConditionTrue(keywordPage.getTableText(3, 3).equals("[" + keyword[1] + "]"));
        assertConditionTrue(keywordPage.getTableText(4, 3).equals("[" + keyword[2] + "]"));
        assertConditionTrue(keywordPage.getTableText(5, 3).equals("[" + keyword[3] + "]"));
        assertConditionTrue(keywordPage.getTableText(6, 3).equals("[" + keyword[4] + "]"));
        keywordPage.clickKeywordHeader(3);
        assertConditionTrue(keywordPage.getTableText(2, 3).equals("[" + keyword[4] + "]"));
        assertConditionTrue(keywordPage.getTableText(3, 3).equals("[" + keyword[3] + "]"));
        assertConditionTrue(keywordPage.getTableText(4, 3).equals("[" + keyword[2] + "]"));
        assertConditionTrue(keywordPage.getTableText(5, 3).equals("[" + keyword[1] + "]"));
        assertConditionTrue(keywordPage.getTableText(6, 3).equals("[" + keyword[0] + "]"));
    }

    //TODO: add new test scenario
    @Ignore
    public void testDisapproveReason() throws Exception {

    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            String[] keyword = {"YP" + System.currentTimeMillis(), "Auto" + System.currentTimeMillis(), "Zi" + System.currentTimeMillis(), "Laundry" + System.currentTimeMillis(), "Finance" + System.currentTimeMillis()};
            Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = createBudget(account.getAccountId(), 100, "standard");
            Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
            AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());

            JSONArray keywordList = new JSONArray();
            for (String key : keyword) {
                JSONObject obj = new JSONObject();
                obj.put("keywordText", key);
                obj.put("matchType", "EXACT");
                obj.put("maxCPC", "10");
                keywordList.add(obj);
            }

            createKeyword(account.getAccountId(), adGroup.getAdGroupId(), keywordList);
            testData.put("account", account);
            testData.put("campaign", campaign);
            testData.put("adGroup", adGroup);
            testData.put("keywordText", keyword);
        }

        return testData;
    }

}
