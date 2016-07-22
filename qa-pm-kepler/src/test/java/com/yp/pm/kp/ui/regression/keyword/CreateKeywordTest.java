package com.yp.pm.kp.ui.regression.keyword;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.*;
import com.yp.pm.kp.ui.KeywordPage;
import com.yp.pm.kp.ui.MyAgencyPage;
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
public class CreateKeywordTest extends BaseUITest {

    private static Map<String, Object> testData;

    @Test
    public void testCreateKeyword() throws Exception {
        String newKeyWord = "auto:" + System.currentTimeMillis();

        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long campaignId = ((Campaign) getTestData().get("campaign")).getCampaignId();
        AdGroup adGroup = (AdGroup) getTestData().get("adGroup");

        MyAgencyPage agencyPage = login();
        KeywordPage keywordPage = agencyPage.navigateToKeywordTab(agencyId, accountId, campaignId);
        keywordPage.clickCreateKeyword();
        keywordPage.selectAdGroup(adGroup.getAdGroupName());
        keywordPage.enterKeywordText(newKeyWord);
        keywordPage.submit();

        if (keywordPage.isSubmitSuccess()) {
            assertConditionTrue(keywordPage.getTableText(2, 3).contains(newKeyWord));
            assertConditionTrue(keywordPage.getTableText(2, 5).contains(adGroup.getAdGroupName()));

            List<Keyword> keyword = getKeywordByAdGroupId(accountId, adGroup.getAdGroupId());
            assertConditionTrue(keyword.size() == 1);
            Keyword AdNewKeyword = keyword.get(0);
            assertObjectNotNull(AdNewKeyword.getAccountId());
            assertObjectNotNull(AdNewKeyword.getAdGroupId());
            assertObjectEqual(AdNewKeyword.getAccountId(), accountId);
            assertObjectEqual(AdNewKeyword.getKeywordText(), newKeyWord);
            assertObjectEqual(AdNewKeyword.getKeywordStatus().toString(), "ACTIVE");
        }
    }

    @Test
    public void testCancelCreateKeyword() throws Exception {
        String newKeyWord = "auto1:" + System.currentTimeMillis();

        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long campaignId = ((Campaign) getTestData().get("campaign")).getCampaignId();
        AdGroup adGroup = (AdGroup) getTestData().get("adGroup");

        MyAgencyPage agencyPage = login();
        KeywordPage keywordPage = agencyPage.navigateToKeywordTab(agencyId, accountId, campaignId);
        keywordPage.clickCreateKeyword();
        keywordPage.selectAdGroup(adGroup.getAdGroupName());
        keywordPage.enterKeywordText(newKeyWord);
        keywordPage.cancel();

        assertConditionTrue(!keywordPage.getTableText(2, 3).contains(newKeyWord));
        assertConditionTrue(!keywordPage.getTableText(2, 5).contains(adGroup.getAdGroupName()));

        List<Keyword> keyword = getKeywordByAdGroupId(accountId, adGroup.getAdGroupId());
        for (Keyword adNewKeyWord : keyword) {
            assertConditionTrue(!adNewKeyWord.getKeywordText().equalsIgnoreCase(newKeyWord));
        }
    }

    @Test
    public void testMissingKeywordError() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long campaignId = ((Campaign) getTestData().get("campaign")).getCampaignId();
        AdGroup adGroup = (AdGroup) getTestData().get("adGroup");

        MyAgencyPage agencyPage = login();
        KeywordPage keywordPage = agencyPage.navigateToKeywordTab(agencyId, accountId, campaignId);
        keywordPage.clickCreateKeyword();
        keywordPage.selectAdGroup(adGroup.getAdGroupName());
        keywordPage.submit();

        if (keywordPage.isSubmitSuccess()) {
            assertObjectEqual(keywordPage.getKeywordRequiredErrorMessage(), "Keyword is required");
        }
    }

    @Test
    public void testKeywordBoundaryValuesError() throws Exception {
        String Keyword = "abcdefghijklmnopqrstuvwxyzzyxwvutsrqponmlkjihgfedcbaabcdefghijklmnopqrstuvwxyzzyxwvutsrqponmlkjihgfedcbaabcdefghijklmnopqrstuvwxyzzyxwvutsrqponmlkjihgfedcba";
        String characterLimitError = "Keywords cannot be longer than 80 characters.";

        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long campaignId = ((Campaign) getTestData().get("campaign")).getCampaignId();
        AdGroup adGroup = (AdGroup) getTestData().get("adGroup");

        MyAgencyPage agencyPage = login();
        KeywordPage keywordPage = agencyPage.navigateToKeywordTab(agencyId, accountId, campaignId);
        keywordPage.clickCreateKeyword();
        keywordPage.selectAdGroup(adGroup.getAdGroupName());
        keywordPage.enterKeywordText(Keyword);
        keywordPage.submit();

        if (keywordPage.isSubmitSuccess()) {
            assertConditionTrue(keywordPage.getKeywordErrorMessage().contains(characterLimitError));
        }
    }

    @Test
    public void testKeywordMaxWordsError() throws Exception {
        String wordsLimitError = "Keywords cannot contain more than 10 words.";
        String Keyword = "a b c d e f g h i j k l ";

        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long campaignId = ((Campaign) getTestData().get("campaign")).getCampaignId();
        AdGroup adGroup = (AdGroup) getTestData().get("adGroup");

        MyAgencyPage agencyPage = login();
        KeywordPage keywordPage = agencyPage.navigateToKeywordTab(agencyId, accountId, campaignId);
        keywordPage.clickCreateKeyword();
        keywordPage.selectAdGroup(adGroup.getAdGroupName());
        keywordPage.enterKeywordText(Keyword);
        keywordPage.submit();

        if (keywordPage.isSubmitSuccess()) {
            assertConditionTrue(keywordPage.getKeywordErrorMessage().contains(wordsLimitError));
        }
    }

    @Test
    public void testKeywordSpecialCharactersError() throws Exception {
        String specialCharacterError = "Keyword cannot contain non-standard character(s)";
        String keyword = "(*&(*&(&(&*";

        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long campaignId = ((Campaign) getTestData().get("campaign")).getCampaignId();
        AdGroup adGroup = (AdGroup) getTestData().get("adGroup");

        MyAgencyPage agencyPage = login();
        KeywordPage keywordPage = agencyPage.navigateToKeywordTab(agencyId, accountId, campaignId);
        keywordPage.clickCreateKeyword();
        keywordPage.selectAdGroup(adGroup.getAdGroupName());
        keywordPage.enterKeywordText(keyword);
        keywordPage.submit();

        if (keywordPage.isSubmitSuccess()) {
            assertConditionTrue(keywordPage.getKeywordErrorMessage().contains(specialCharacterError));
        }
    }

    @Test
    public void testDuplicateKeywordError() throws Exception {
        String duplicateKeywordError = "Duplicate Keywords Found In The List";
        String keyword = "test" + System.currentTimeMillis();

        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long campaignId = ((Campaign) getTestData().get("campaign")).getCampaignId();
        AdGroup adGroup = (AdGroup) getTestData().get("adGroup");

        MyAgencyPage agencyPage = login();
        KeywordPage keywordPage = agencyPage.navigateToKeywordTab(agencyId, accountId, campaignId);
        keywordPage.clickCreateKeyword();
        keywordPage.selectAdGroup(adGroup.getAdGroupName());
        keywordPage.enterKeywordText(keyword);
        keywordPage.submit();

        if (keywordPage.isSubmitSuccess()) {
            keywordPage.clickCreateKeyword();
            keywordPage.selectAdGroup(adGroup.getAdGroupName());
            keywordPage.enterKeywordText(keyword);
            keywordPage.submit();

            if (keywordPage.isSubmitSuccess()) {
                keywordPage.checkDuplicateKeywordError();
                assertConditionTrue(keywordPage.getDuplicateKeywordErrorMessage().contains(duplicateKeywordError));
                keywordPage.clickDuplicateModalOkButton();
            }
        }
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = createBudget(account.getAccountId(), 100, "standard");
            Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
            AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());
            testData.put("account", account);
            testData.put("campaign", campaign);
            testData.put("adGroup", adGroup);
        }

        return testData;
    }
}
