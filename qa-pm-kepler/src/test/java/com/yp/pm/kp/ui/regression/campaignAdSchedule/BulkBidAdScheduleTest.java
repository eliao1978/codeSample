package com.yp.pm.kp.ui.regression.campaignAdSchedule;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.ui.MyAgencyPage;
import com.yp.pm.kp.ui.SettingsPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.lang.String;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")

public class BulkBidAdScheduleTest extends BaseUITest {
    @Test
    public void BulkBidAdScheduleAdjustment() throws Exception {
        String increaseByPer = "50";
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long accountId = account.getAccountId();
        long campaignId = campaign.getCampaignId();
        createCampaignAdSchedule(accountId,campaignId);
        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.clickAdScheduleTab();
        settingsPage.clickAdScheduleCheckall();
        settingsPage.clickBidAdjustmentAdScheduleButton();
        settingsPage.enterIncreaseByTextField(increaseByPer);
        settingsPage.clickScheduleMakeChangesButton();
        assertConditionTrue(!settingsPage.getTableText(2, 2).contains(increaseByPer));
        assertConditionTrue(!settingsPage.getTableText(3, 2).contains(increaseByPer));
        assertConditionTrue(!settingsPage.getTableText(4, 2).contains(increaseByPer));
        Thread.sleep(1000);
    }
}
