package com.yp.pm.kp.ui.regression.negativeKeyword;

import com.yp.enums.selenium.LocatorTypeEnum;
import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.*;
import com.yp.pm.kp.ui.KeywordPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import com.yp.pm.kp.ui.UIMapper;
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
public class CreateCampaignLevelNegativeKeywordTest extends BaseUITest {

    private static Map<String, Object> testData;


    @Test
    public void testCreateNegativeKeyword() throws Exception {
        String negativeKeywordTextCampaign = "\"motel\"";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        MyAgencyPage agencyPage = login();
        KeywordPage negativeKeyWordPage = agencyPage.navigateToKeywordTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        negativeKeyWordPage.clickCreateNegativeKeyword();
        negativeKeyWordPage.campaignLevelAdButton();
        negativeKeyWordPage.NegativeKeyWordInputText2(negativeKeywordTextCampaign);
        negativeKeyWordPage.clickNegativeKeyWordCampaignSaveButton();
        negativeKeyWordPage.checkButtonNotVisible("neg_campaign_save", LocatorTypeEnum.ID);
        assertConditionTrue(negativeKeyWordPage.getCampaignLevelText().contains(negativeKeywordTextCampaign));

        List<NegativeKeyword> negativeKeyWord = getKeywordByCampaignId(account.getAccountId(), campaign.getCampaignId());
        assertConditionTrue(negativeKeyWord.size() == 1);
        NegativeKeyword newNegativeKeyword = negativeKeyWord.get(0);
        assertObjectNotNull(newNegativeKeyword.getAccountId());
        assertObjectEqual(newNegativeKeyword.getAccountId(), account.getAccountId());
        assertObjectEqual(newNegativeKeyword.getNegativeText(), negativeKeywordTextCampaign.replace("\"", ""));
        assertObjectEqual(newNegativeKeyword.getStatus().toString(), "ACTIVE");
    }


    @Test
    public void testMinimumCharacter() throws Exception {
        String negativeKeywordTextCampaign = "\"abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopq\"";
        String errorMessage = "Keywords cannot be longer than 80 characters.";

        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long campaignId = ((Campaign) getTestData().get("campaign")).getCampaignId();

        MyAgencyPage agencyPage = login();
        KeywordPage negativeKeyWordPage = agencyPage.navigateToKeywordTab(agencyId, accountId, campaignId);
        negativeKeyWordPage.clickCreateNegativeKeyword();
        negativeKeyWordPage.campaignLevelAdButton();
        negativeKeyWordPage.NegativeKeyWordInputText2(negativeKeywordTextCampaign);
        negativeKeyWordPage.clickNegativeKeyWordCampaignSaveButton();
        assertConditionTrue(negativeKeyWordPage.getCampaignNegativeKeywordMaxCharacters().contains(errorMessage));
    }

    @Test
    public void testMaximumCharacter() throws Exception {
        String negativeKeywordTextCampaign = "\"a b c d e f g h i j k\"";
        String errorMessage = "Negative keyword can not contain more than 10 words";

        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long campaignId = ((Campaign) getTestData().get("campaign")).getCampaignId();

        MyAgencyPage agencyPage = login();
        KeywordPage negativeKeyWordPage = agencyPage.navigateToKeywordTab(agencyId, accountId, campaignId);
        negativeKeyWordPage.clickCreateNegativeKeyword();
        negativeKeyWordPage.campaignLevelAdButton();
        negativeKeyWordPage.NegativeKeyWordInputText2(negativeKeywordTextCampaign);
        negativeKeyWordPage.clickNegativeKeyWordCampaignSaveButton();
        assertConditionTrue(negativeKeyWordPage.getNegativeKeywordMaxWords().contains(errorMessage));
    }


    @Test
    public void testInvalidCharacter() throws Exception {
        String negativeKeywordTextCampaign = "\"&*(^%$#@!)\"";
        String errorMessage = "Keyword cannot contain non-standard character(s)";

        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long campaignId = ((Campaign) getTestData().get("campaign")).getCampaignId();

        MyAgencyPage agencyPage = login();
        KeywordPage negativeKeyWordPage = agencyPage.navigateToKeywordTab(agencyId, accountId, campaignId);
        negativeKeyWordPage.clickCreateNegativeKeyword();
        negativeKeyWordPage.campaignLevelAdButton();
        negativeKeyWordPage.NegativeKeyWordInputText2(negativeKeywordTextCampaign);
        negativeKeyWordPage.clickNegativeKeyWordCampaignSaveButton();
        assertConditionTrue(negativeKeyWordPage.getCampaignNegativeKeywordInvalidCharacters().contains(errorMessage));
    }

    @Test
    public void testDuplicateKeyword() throws Exception {
        String negativeKeywordTextCampaign = "\"motel\"";
        String errorMessage = "Below keyword(s) already exists in this campaign.";

        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long campaignId = ((Campaign) getTestData().get("campaign")).getCampaignId();

        MyAgencyPage agencyPage = login();
        KeywordPage negativeKeyWordPage = agencyPage.navigateToKeywordTab(agencyId, accountId, campaignId);
        negativeKeyWordPage.clickCreateNegativeKeyword();

        negativeKeyWordPage.campaignLevelAdButton();
        negativeKeyWordPage.NegativeKeyWordInputText2(negativeKeywordTextCampaign);
        negativeKeyWordPage.clickNegativeKeyWordCampaignSaveButton();
        negativeKeyWordPage.campaignLevelAdButton();
        negativeKeyWordPage.NegativeKeyWordInputText2(negativeKeywordTextCampaign);
        negativeKeyWordPage.clickNegativeKeyWordCampaignSaveButton();

        assertConditionTrue(negativeKeyWordPage.getCampaignDuplicateNegativeKeyword().contains(errorMessage));
    }

    @Test
    public void testCancelCreateNegativeKeyword() throws Exception {
        String negativeKeywordTextCampaign = " \"health\"  ";

        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long campaignId = ((Campaign) getTestData().get("campaign")).getCampaignId();

        MyAgencyPage agencyPage = login();
        KeywordPage negativeKeyWordPage = agencyPage.navigateToKeywordTab(agencyId, accountId, campaignId);
        negativeKeyWordPage.clickCreateNegativeKeyword();
        negativeKeyWordPage.campaignLevelAdButton();
        negativeKeyWordPage.NegativeKeyWordInputText2(negativeKeywordTextCampaign);
        negativeKeyWordPage.clickNegativeKeyWordCampaignCancelButton();
        assertConditionTrue("Text, No Records To Load, was not present", negativeKeyWordPage.isElementVisible(UIMapper.NO_RECORDS_TO_LOAD,LocatorTypeEnum.XPATH));

        List<NegativeKeyword> negativeKeyWord = getKeywordByCampaignId(accountId, campaignId);
        for (NegativeKeyword adNewNegativeKeyWord : negativeKeyWord) {
            assertConditionTrue(!adNewNegativeKeyWord.getNegativeText().equalsIgnoreCase(negativeKeywordTextCampaign));
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
