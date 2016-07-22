package com.yp.pm.kp.ui.regression.negativeKeyword;

import com.yp.enums.selenium.LocatorTypeEnum;
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

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EditAdGroupLevelNegativeKeywordTest extends BaseUITest {

    @Test
    public void testEditNegativeKeyword() throws Exception {
        String negativeKeywordText = "\"farms\"";
        String EditedNegativeKeywordText = "\"life\"";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();
        AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaignId);
        createNegativeKeyword(account.getAccountId(), campaignId, adGroup.getAdGroupId(), negativeKeywordText);

        MyAgencyPage agencyPage = login();
        KeywordPage negativeKeyWordPage = agencyPage.navigateToKeywordTab(agencyId, account.getAccountId(), campaignId);
        negativeKeyWordPage.showNegativeKeywords();
        negativeKeyWordPage.clickEditNegativeKeyword(negativeKeywordText);
        negativeKeyWordPage.editNegativeKeywordEditTextAdGroupLevel(EditedNegativeKeywordText);
        negativeKeyWordPage.saveNegativeKeywordEditTextAdGroupLevel();
        negativeKeyWordPage.waitForElementNotVisible("div[class='table-cell-editor-actions']>button[ng-click='save()'][ng-disabled='editNegativeKeywordTextMatchTypeForm.$invalid']",20, LocatorTypeEnum.CSS);
        assertConditionTrue("Updated NegKeyword not displayed through UI", negativeKeyWordPage.isElementPresent("//td/span[normalize-space(.)='" + EditedNegativeKeywordText + "']", LocatorTypeEnum.XPATH));
        List<NegativeKeyword> negativeKeyWord = getKeywordByCampaignId(account.getAccountId(), campaignId);
        logger.debug("Size: " + negativeKeyWord.size());
        assertConditionTrue(negativeKeyWord.size() >= 1);
        for (NegativeKeyword editedKeyword : negativeKeyWord) {
            if (editedKeyword.getStatus().toString().equalsIgnoreCase("ACTIVE")) {
                assertObjectNotNull(editedKeyword.getAccountId());
                assertObjectNotNull(editedKeyword.getAdGroupId());
                assertObjectEqual(editedKeyword.getAccountId(), account.getAccountId());
                assertConditionTrue("NegKeyword backend validation failed", editedKeyword.getNegativeText().equalsIgnoreCase(EditedNegativeKeywordText));
            }
        }
    }

    @Test
    public void testCancelEditNegativeKeyword() throws Exception {
        String negativeKeywordText = "\"hotel\"";
        String EditedNegativeKeywordText = "\"health\"";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();
        AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaignId);
        createNegativeKeyword(account.getAccountId(), campaignId, adGroup.getAdGroupId(), negativeKeywordText);

        MyAgencyPage agencyPage = login();
        KeywordPage negativeKeyWordPage = agencyPage.navigateToKeywordTab(agencyId, account.getAccountId(), campaignId);
        negativeKeyWordPage.showNegativeKeywords();
        negativeKeyWordPage.clickEditNegativeKeyword(negativeKeywordText);
        negativeKeyWordPage.editNegativeKeywordEditTextAdGroupLevel(EditedNegativeKeywordText);
        negativeKeyWordPage.negativeKeywordCancelTextAdGroupLevel();
        assertConditionTrue(!negativeKeyWordPage.getNegativeKeywordText().contains(EditedNegativeKeywordText));

        List<NegativeKeyword> negativeKeyWord = getKeywordByCampaignId(account.getAccountId(), campaignId);
        assertConditionTrue(negativeKeyWord.size() == 1);
        for (NegativeKeyword editedKeyword : negativeKeyWord) {
            if (editedKeyword.getStatus().toString().equalsIgnoreCase("ACTIVE")) {
                assertObjectNotNull(editedKeyword.getAccountId());
                assertObjectNotNull(editedKeyword.getAdGroupId());
                assertObjectEqual(editedKeyword.getAccountId(), account.getAccountId());
                assertObjectEqual(editedKeyword.getNegativeText(), negativeKeywordText);
            }
        }
    }
}
