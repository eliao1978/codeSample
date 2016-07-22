package com.yp.pm.kp.ui.regression.negativeKeyword;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.model.domain.NegativeKeyword;
import com.yp.pm.kp.ui.KeywordPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import com.yp.pm.kp.ui.UIMapper;
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
public class EditCampaignLevelNegativeKeywordTest extends BaseUITest {

    @Test
    public void testEditNegativeKeyword() throws Exception {
        String negativeKeywordTextCampaign = "\"Food\"";
        String editedNegativeKeywordTextCampaign = "\"Drinks\"";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();

        MyAgencyPage agencyPage = login();
        KeywordPage negativeKeyWordPage = agencyPage.navigateToKeywordTab(agencyId, account.getAccountId(), campaignId);
        negativeKeyWordPage.clickCreateNegativeKeyword();
        negativeKeyWordPage.campaignLevelAdButton();
        negativeKeyWordPage.NegativeKeyWordInputText2(negativeKeywordTextCampaign);
        negativeKeyWordPage.clickNegativeKeyWordCampaignSaveButton();
        negativeKeyWordPage.editNegativeKeywordCampaignLevel(negativeKeywordTextCampaign);
        negativeKeyWordPage.saveEditedNegativeKeywordTextCampaignLevel(editedNegativeKeywordTextCampaign);
        negativeKeyWordPage.checkButtonNotVisible(UIMapper.EDIT_KEYWORD_SAVE_BUTTON);
        assertConditionTrue(negativeKeyWordPage.getCampaignLevelText().contains(editedNegativeKeywordTextCampaign));
        assertConditionTrue(!negativeKeyWordPage.getCampaignLevelText().contains(negativeKeywordTextCampaign));

        List<NegativeKeyword> negativeKeyWord = getKeywordByCampaignId(account.getAccountId(), campaignId);
        assertConditionTrue(negativeKeyWord.size() >= 1);
        for (NegativeKeyword editedKeyword : negativeKeyWord) {
            if (editedKeyword.getStatus().toString().equalsIgnoreCase("ACTIVE")) {
                assertObjectNotNull(editedKeyword.getAccountId());
                assertObjectEqual(editedKeyword.getAccountId(), account.getAccountId());
                assertObjectEqual(editedKeyword.getNegativeText(), editedNegativeKeywordTextCampaign);
            }
        }
    }

    @Test
    public void testCancelEditNegativeKeyword() throws Exception {
        String negativeKeywordTextCampaign = "\"Auto\"";
        String editedNegativeKeywordTextCampaign = "\"Life\"";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();

        MyAgencyPage agencyPage = login();
        KeywordPage negativeKeyWordPage = agencyPage.navigateToKeywordTab(agencyId, account.getAccountId(), campaignId);
        negativeKeyWordPage.clickCreateNegativeKeyword();
        negativeKeyWordPage.campaignLevelAdButton();
        negativeKeyWordPage.NegativeKeyWordInputText2(negativeKeywordTextCampaign);
        negativeKeyWordPage.clickNegativeKeyWordCampaignSaveButton();
        negativeKeyWordPage.editNegativeKeywordCampaignLevel(negativeKeywordTextCampaign);
        negativeKeyWordPage.cancelEditedNegativeKeywordTextCampaignLevel(editedNegativeKeywordTextCampaign);
        assertConditionTrue(negativeKeyWordPage.getCampaignLevelText().contains(negativeKeywordTextCampaign));
        assertConditionTrue(!negativeKeyWordPage.getCampaignLevelText().contains(editedNegativeKeywordTextCampaign));

        List<NegativeKeyword> negativeKeyWord = getKeywordByCampaignId(account.getAccountId(), campaignId);
        assertConditionTrue(negativeKeyWord.size() == 1);
        for (NegativeKeyword editedKeyword : negativeKeyWord) {
            if (editedKeyword.getStatus().toString().equalsIgnoreCase("ACTIVE")) {
                assertObjectNotNull(editedKeyword.getAccountId());
                assertObjectEqual(editedKeyword.getAccountId(), account.getAccountId());
                assertObjectEqual(editedKeyword.getNegativeText(), negativeKeywordTextCampaign.replace("\"", ""));
            }
        }
    }
}
