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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FilterKeywordTest extends BaseUITest {

    @Test
    public void testAllButDeleted() throws Exception {
        String[] keyword = {"YP", "Auto", "Zi", "Laundry", "YP Northridge", "YP Burbank", "YP Glendale", "Zi123"};

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();
        AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaignId);

        JSONArray keywords = new JSONArray();
        for (String deleteKeyword : keyword) {
            JSONObject newKeyword = new JSONObject();
            newKeyword.put("keywordText", deleteKeyword);
            newKeyword.put("matchType", "BROAD");
            newKeyword.put("maxCPC", "100");
            keywords.add(newKeyword);
        }
        createKeyword(account.getAccountId(), adGroup.getAdGroupId(), keywords);

        MyAgencyPage agencyPage = login();
        KeywordPage keywordPage = agencyPage.navigateToKeywordTab(agencyId, account.getAccountId(), campaignId);
        Arrays.sort(keyword);
        keywordPage.clickKeywordHeader("Keyword");
        keywordPage.selectKeywordCheckBox(2, 1);
        keywordPage.selectKeywordCheckBox(3, 1);
        keywordPage.pauseKeywordSelect();
        keywordPage.selectKeywordCheckBox(4, 1);
        keywordPage.selectKeywordCheckBox(5, 1);
        keywordPage.deleteKeywordSelect();
        keywordPage.clickFilterButton();
        keywordPage.allButDeletedKeywordsSelectFromDropdown();
        int tableColumnSize = keywordPage.getTableColumn().size();
        assertObjectEqual(tableColumnSize, 6);
        //5 rows for keyword and one row each for Table header & table end
        assertConditionTrue(keywordPage.getTableText(2, 2).equals("Paused"));
        assertConditionTrue(keywordPage.getTableText(3, 2).equals("Paused"));
        for (int allButDeletedActiveRow = 4; allButDeletedActiveRow >= 8; allButDeletedActiveRow++) {
            assertConditionTrue(keywordPage.getTableText(allButDeletedActiveRow, 2).equals("Active"));
        }
    }

    @Test
    public void testAllEnable() throws Exception {
        String[] keyword = {"YP", "Auto", "Zi", "Laundry", "YP Northridge", "YP Burbank", "YP Glendale", "Zi123"};

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();
        AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaignId);
        JSONArray keywords = new JSONArray();
        for (String enableKeyword : keyword) {
            JSONObject newKeyword = new JSONObject();
            newKeyword.put("keywordText", enableKeyword);
            newKeyword.put("matchType", "BROAD");
            newKeyword.put("maxCPC", "100");
            keywords.add(newKeyword);
        }
        createKeyword(account.getAccountId(), adGroup.getAdGroupId(), keywords);

        MyAgencyPage agencyPage = login();
        KeywordPage keywordPage = agencyPage.navigateToKeywordTab(agencyId, account.getAccountId(), campaignId);
        Arrays.sort(keyword);
        keywordPage.clickKeywordHeader("Keyword");
        keywordPage.selectKeywordCheckBox(2, 1);
        keywordPage.selectKeywordCheckBox(3, 1);
        keywordPage.pauseKeywordSelect();
        keywordPage.selectKeywordCheckBox(4, 1);
        keywordPage.deleteKeywordSelect();
        keywordPage.clickFilterButton();
        keywordPage.allEnableKeyWordsSelectFromDropdown();
        int tableColumnSize = keywordPage.getTableColumn().size();
        assertObjectEqual(tableColumnSize, 5);
        for (int allEnableKeywordRow = 2; allEnableKeywordRow >= 5; allEnableKeywordRow++) {
            assertConditionTrue(keywordPage.getTableText(allEnableKeywordRow, 2).equals("Active"));
        }
    }

    @Test
    public void testAllKeywords() throws Exception {
        String[] keyword = {"YP", "Auto", "Zi", "Laundry", "YP Northridge", "YP Burbank", "YP Glendale", "Zi123"};

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();
        AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaignId);
        JSONArray keywords = new JSONArray();
        for (String allKeywords : keyword) {
            JSONObject newKeyword = new JSONObject();
            newKeyword.put("keywordText", allKeywords);
            newKeyword.put("matchType", "BROAD");
            newKeyword.put("maxCPC", "100");
            keywords.add(newKeyword);
        }
        createKeyword(account.getAccountId(), adGroup.getAdGroupId(), keywords);

        MyAgencyPage agencyPage = login();
        KeywordPage keywordPage = agencyPage.navigateToKeywordTab(agencyId, account.getAccountId(), campaignId);
        Arrays.sort(keyword);
        keywordPage.clickKeywordHeader("Keyword");
        keywordPage.selectKeywordCheckBox(2, 1);
        keywordPage.selectKeywordCheckBox(3, 1);
        keywordPage.pauseKeywordSelect();
        keywordPage.selectKeywordCheckBox(4, 1);
        keywordPage.deleteKeywordSelect();
        keywordPage.clickFilterButton();
        keywordPage.allKeyWordsSelectFromDropdown();
        int tableColumnSize = keywordPage.getTableColumn().size();
        assertObjectEqual(tableColumnSize, 8);
        assertConditionTrue(keywordPage.getTableText(2, 2).equals("Paused"));
        assertConditionTrue(keywordPage.getTableText(3, 2).equals("Paused"));
        assertConditionTrue(keywordPage.getTableText(4, 2).equals("Deleted"));

        for (int allKeyWordsActiveRow = 6; allKeyWordsActiveRow >= 10; allKeyWordsActiveRow++) {
            assertConditionTrue(keywordPage.getTableText(allKeyWordsActiveRow, 2).equals("Active"));
        }
    }
}