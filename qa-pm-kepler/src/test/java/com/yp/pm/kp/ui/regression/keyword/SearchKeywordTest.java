package com.yp.pm.kp.ui.regression.keyword;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.AdGroup;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.ui.KeywordPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import com.yp.pm.kp.ui.UIMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.FixMethodOrder;
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
public class SearchKeywordTest extends BaseUITest {

    private static Map<String, Object> testData;
    String[] keyword = {"YP Anaheim", "Auto", "Zi", "Laundry", "YP Northridge", "YP Burbank", "YP Glendale", "Zi123"};

    @Test
    public void testIncompleteKeywordSearch() throws Exception {
        MyAgencyPage agencyPage = login();
        KeywordPage keywordPage = agencyPage.navigateToKeywordTab(agencyId, ((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
        keywordPage.waitForElementNotVisible(UIMapper.LOADING);
        keywordPage.enterSearchText("YP");
        keywordPage.submitFilterText();
        keywordPage.clickKeywordHeader("Keyword");
        int tableColumnSize = keywordPage.getTableColumn().size();
        assertObjectEqual(tableColumnSize, 4);//4 rows for Filter Results and one row each for Table header & table end
        Arrays.sort(keyword);
        // Brackets around [keyword] are needed since it was setup as Exact keyword type
        assertConditionTrue(keywordPage.getTableText(2, 3).equals("[" + keyword[2] + "]"));
        assertConditionTrue(keywordPage.getTableText(3, 3).equals("[" + keyword[3] + "]"));
        assertConditionTrue(keywordPage.getTableText(4, 3).equals("[" + keyword[4] + "]"));
        assertConditionTrue(keywordPage.getTableText(5, 3).equals("[" + keyword[5] + "]"));
    }

    @Test
    public void testCompleteKeywordSearch() throws Exception {
        MyAgencyPage agencyPage = login();
        KeywordPage keywordPage = agencyPage.navigateToKeywordTab(agencyId, ((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
        keywordPage.enterSearchText("YP Glendale");
        keywordPage.submitFilterText();
        keywordPage.clickKeywordHeader("Keyword");
        int tableColumnSize = keywordPage.getTableColumn().size();
        assertObjectEqual(tableColumnSize, 1);//1 row for Filter Results and one row each for Table header & table end
        Arrays.sort(keyword);
        // Brackets around [keyword] are needed since it was setup as Exact keyword type
        assertConditionTrue(keywordPage.getTableText(2, 3).equals("[" + keyword[4] + "]"));
    }

    @Test
    public void testInvalidKeywordSearch() throws Exception {
        MyAgencyPage agencyPage = login();
        KeywordPage keywordPage = agencyPage.navigateToKeywordTab(agencyId, ((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
        keywordPage.enterSearchText("San Diego");
        keywordPage.submitFilterText();
        keywordPage.clickKeywordHeader("Keyword");
        int tableColumnSize = keywordPage.getTableColumn().size();
        assertObjectEqual(tableColumnSize, 0);//1 row for Filter Results and one row each for Table header & table end
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();

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
        }
        return testData;
    }

}
