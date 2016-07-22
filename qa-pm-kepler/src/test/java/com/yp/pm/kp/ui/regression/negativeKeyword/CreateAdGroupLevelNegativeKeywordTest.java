package com.yp.pm.kp.ui.regression.negativeKeyword;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.*;
import com.yp.pm.kp.ui.KeywordPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreateAdGroupLevelNegativeKeywordTest extends BaseUITest {
    private static Map<String, Object> testData;

    @Test
    public void testCreateNegativeKeyword() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());

        String adGroupName = adGroup.getAdGroupName();
        String negativeKeywordText = "\"hotels\"";

        MyAgencyPage agencyPage = login();
        KeywordPage negativeKeyWordPage = agencyPage.navigateToKeywordTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        negativeKeyWordPage.clickCreateNegativeKeyword();
        negativeKeyWordPage.adGroupAdButton();
        negativeKeyWordPage.selectAdGroupNegativeKeyword(adGroupName);
        negativeKeyWordPage.NegativeKeyWordInputText(negativeKeywordText);
        negativeKeyWordPage.clickNegativeKeyWordAdGroupSaveButton();
        assertConditionTrue(negativeKeyWordPage.getAdGroupLevelText().contains(adGroupName));
        assertConditionTrue(negativeKeyWordPage.getNegativeKeywordText().contains(negativeKeywordText));

        List<NegativeKeyword> negativeKeyWord = getKeywordByCampaignId(account.getAccountId(), campaign.getCampaignId());
        assertConditionTrue(negativeKeyWord.size() == 1);
        NegativeKeyword newNegativeKeyword = negativeKeyWord.get(0);
        assertObjectNotNull(newNegativeKeyword.getAccountId());
        assertObjectNotNull(newNegativeKeyword.getAdGroupId());
        assertObjectEqual(newNegativeKeyword.getAccountId(), account.getAccountId());
        assertObjectEqual(newNegativeKeyword.getNegativeText(), negativeKeywordText.replace("\"", ""));
        assertObjectEqual(newNegativeKeyword.getStatus().toString(), "ACTIVE");
    }

    @Test
    public void testMinimumCharacterRequiredError() throws Exception {
        String minCharacterError = "Keywords cannot be longer than 80 characters.";
        String adGroupName = ((AdGroup) getTestData().get("adGroup")).getAdGroupName();
        String negativeKeywordText = "\"abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz\"";

        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long campaignId = ((Campaign) getTestData().get("campaign")).getCampaignId();

        MyAgencyPage agencyPage = login();
        KeywordPage negativeKeyWordPage = agencyPage.navigateToKeywordTab(agencyId, accountId, campaignId);
        negativeKeyWordPage.clickCreateNegativeKeyword();
        negativeKeyWordPage.adGroupAdButton();
        negativeKeyWordPage.selectAdGroupNegativeKeyword(adGroupName);
        negativeKeyWordPage.NegativeKeyWordInputText(negativeKeywordText);
        negativeKeyWordPage.clickNegativeKeyWordAdGroupSaveButton();
        assertConditionTrue(negativeKeyWordPage.getNegativeKeywordMaxCharacter().contains(minCharacterError));
    }

    @Test
    public void testMinimumWordsRequiredError() throws Exception {
        String maxWordError = "Negative keyword can not contain more than 10 words";
        String adGroupName = ((AdGroup) getTestData().get("adGroup")).getAdGroupName();
        String negativeKeywordText = "\"a b c d e f g h i j k \"";

        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long campaignId = ((Campaign) getTestData().get("campaign")).getCampaignId();

        MyAgencyPage agencyPage = login();
        KeywordPage negativeKeyWordPage = agencyPage.navigateToKeywordTab(agencyId, accountId, campaignId);
        negativeKeyWordPage.clickCreateNegativeKeyword();
        negativeKeyWordPage.adGroupAdButton();
        negativeKeyWordPage.selectAdGroupNegativeKeyword(adGroupName);
        negativeKeyWordPage.NegativeKeyWordInputText(negativeKeywordText);
        negativeKeyWordPage.clickNegativeKeyWordAdGroupSaveButton();
        assertConditionTrue(negativeKeyWordPage.getNegativeKeywordMaxWords().contains(maxWordError));
    }

    @Test
    public void testInvalidCharacterError() throws Exception {
        String invalidCharacters = "Keyword cannot contain non-standard character(s)";
        String adGroupName = ((AdGroup) getTestData().get("adGroup")).getAdGroupName();
        String negativeKeywordText = "\"&^%$#@!&*() \"";

        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long campaignId = ((Campaign) getTestData().get("campaign")).getCampaignId();

        MyAgencyPage agencyPage = login();
        KeywordPage negativeKeyWordPage = agencyPage.navigateToKeywordTab(agencyId, accountId, campaignId);
        negativeKeyWordPage.clickCreateNegativeKeyword();
        negativeKeyWordPage.adGroupAdButton();
        negativeKeyWordPage.selectAdGroupNegativeKeyword(adGroupName);
        negativeKeyWordPage.NegativeKeyWordInputText(negativeKeywordText);
        negativeKeyWordPage.clickNegativeKeyWordAdGroupSaveButton();
        assertConditionTrue(negativeKeyWordPage.getNegativeKeywordInvalidCharacter().contains(invalidCharacters));
    }

    @Test
    public void testCancelCreateNegativeKeyword() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());

        String adGroupName = adGroup.getAdGroupName();
        String negativeKeywordText = "\"motels\"";

        MyAgencyPage agencyPage = login();
        KeywordPage negativeKeyWordPage = agencyPage.navigateToKeywordTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        negativeKeyWordPage.clickCreateNegativeKeyword();
        negativeKeyWordPage.adGroupAdButton();
        negativeKeyWordPage.selectAdGroupNegativeKeyword(adGroupName);
        negativeKeyWordPage.NegativeKeyWordInputText(negativeKeywordText);
        negativeKeyWordPage.clickNegativeKeyWordAdGroupCancelButton();
        assertConditionTrue(negativeKeyWordPage.getEmptyAdGroupTableText().contains("No records to load."));

        List<NegativeKeyword> negativeKeyWord = getKeywordByCampaignId(account.getAccountId(), campaign.getCampaignId());
        for (NegativeKeyword adNewNegativeKeyWord : negativeKeyWord) {
            assertConditionTrue(!adNewNegativeKeyWord.getNegativeText().equalsIgnoreCase(negativeKeywordText.replace("\"", "")));
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


