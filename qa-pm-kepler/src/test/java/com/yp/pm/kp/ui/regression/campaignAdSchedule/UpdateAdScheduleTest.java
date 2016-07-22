package com.yp.pm.kp.ui.regression.campaignAdSchedule;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.ui.MyAgencyPage;
import com.yp.pm.kp.ui.SettingsPage;
import com.yp.pm.kp.ui.UIMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")

public class UpdateAdScheduleTest extends BaseUITest {

    @Test
    public void testIncreaseBidAmount() throws Exception {
        String time[] = {"Friday", "7 AM", "7 PM"};

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.clickAdScheduleTab();
        settingsPage.createAdSchedule(time[0], time[1], time[2]);
        settingsPage.clickSaveScheduleButton();
        settingsPage.waitForElementNotVisible(UIMapper.SAVE_SCHEDULE_BUTTON);
        settingsPage.waitForElementNotVisible(UIMapper.LOADING);
        settingsPage.selectTableRow(2, 1);
        settingsPage.clickBidModifier(2, 3);
        settingsPage.enterBid(2, 3, "50");
        settingsPage.clickBidSaveButton();
        settingsPage.waitForElementNotVisible("button[class='btn btn-xs btn-primary'][ng-click='save()']");
        settingsPage.waitForElementNotVisible(UIMapper.LOADING);
        assertConditionTrue(settingsPage.getTableText(2, 3).contains("50%"));
    }


    @Test
    public void testIncreaseBidLimitError() throws Exception {
        String time[] = {"Friday", "7 AM", "7 PM"};

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.clickAdScheduleTab();
        settingsPage.createAdSchedule(time[0], time[1], time[2]);
        settingsPage.clickSaveScheduleButton();
        settingsPage.waitForElementNotVisible(UIMapper.SAVE_SCHEDULE_BUTTON);
        settingsPage.waitForElementNotVisible(UIMapper.LOADING);
        settingsPage.selectTableRow(2, 1);
        settingsPage.clickBidModifier(2, 3);
        settingsPage.enterBid(2, 3, "901");
        assertConditionTrue(settingsPage.getIncreaseBidAdjustmentErrorText().contains("Please enter a smaller number."));
    }

    @Test
    public void testCancelIncreaseBidAmount() throws Exception {
        String time[] = {"Friday", "7 AM", "7 PM"};

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.clickAdScheduleTab();
        settingsPage.createAdSchedule(time[0], time[1], time[2]);
        settingsPage.clickSaveScheduleButton();
        settingsPage.waitForElementNotVisible(UIMapper.SAVE_SCHEDULE_BUTTON);
        settingsPage.waitForElementNotVisible(UIMapper.LOADING);
        settingsPage.selectTableRow(2, 1);
        settingsPage.clickBidModifier(2, 3);
        settingsPage.enterBid(2, 3, "50");
        settingsPage.clickBidCancelButton();
        assertConditionTrue(settingsPage.getTableText(2, 3).contains("0%"));
    }


    @Test
    public void testDecreaseBidAmount() throws Exception {
        String time[] = {"Friday", "7 AM", "7 PM"};

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.clickAdScheduleTab();
        settingsPage.createAdSchedule(time[0], time[1], time[2]);
        settingsPage.clickSaveScheduleButton();
        settingsPage.waitForElementNotVisible(UIMapper.SAVE_SCHEDULE_BUTTON);
        settingsPage.waitForElementNotVisible(UIMapper.LOADING);
        settingsPage.selectTableRow(2, 1);
        settingsPage.clickBidModifier(2, 3);
        settingsPage.clickDecreaseBid();
        settingsPage.enterBid(2, 3, "50");
        settingsPage.clickBidSaveButton();
        settingsPage.waitForElementNotVisible("button[class='btn btn-xs btn-primary'][ng-click='save()']");
        settingsPage.waitForElementNotVisible(UIMapper.LOADING);
        assertConditionTrue(settingsPage.getTableText(2, 3).contains("-50%"));
    }

    @Test
    public void testDecreaseBidLimitError() throws Exception {
        String time[] = {"Friday", "7 AM", "7 PM"};

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.clickAdScheduleTab();
        settingsPage.createAdSchedule(time[0], time[1], time[2]);
        settingsPage.clickSaveScheduleButton();
        settingsPage.waitForElementNotVisible(UIMapper.SAVE_SCHEDULE_BUTTON);
        settingsPage.waitForElementNotVisible(UIMapper.LOADING);
        settingsPage.selectTableRow(2, 1);
        settingsPage.clickBidModifier(2, 3);
        settingsPage.clickDecreaseBid();
        settingsPage.enterBid(2, 3, "91");
        assertConditionTrue(settingsPage.getDecreaseBidAdjustmentErrorText().contains("Please enter a smaller number."));
    }

    @Test
    public void testInvalidBidNumberError() throws Exception {
        String time[] = {"Friday", "7 AM", "7 PM"};

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.clickAdScheduleTab();
        settingsPage.createAdSchedule(time[0], time[1], time[2]);
        settingsPage.clickSaveScheduleButton();
        settingsPage.waitForElementNotVisible(UIMapper.SAVE_SCHEDULE_BUTTON);
        settingsPage.waitForElementNotVisible(UIMapper.LOADING);
        settingsPage.selectTableRow(2, 1);
        settingsPage.clickBidModifier(2, 3);
        settingsPage.clickDecreaseBid();
        settingsPage.enterBid(2, 3, "*&(OU");
        assertConditionTrue(settingsPage.getInvalidBidError().contains("Invalid Number."));
    }

    @Test
    public void testEnterWholeNumberInBidError() throws Exception {
        String time[] = {"Friday", "7 AM", "7 PM"};

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.clickAdScheduleTab();
        settingsPage.createAdSchedule(time[0], time[1], time[2]);
        settingsPage.clickSaveScheduleButton();
        settingsPage.waitForElementNotVisible(UIMapper.SAVE_SCHEDULE_BUTTON);
        settingsPage.waitForElementNotVisible(UIMapper.LOADING);
        settingsPage.selectTableRow(2, 1);
        settingsPage.clickBidModifier(2, 3);
        settingsPage.clickDecreaseBid();
        settingsPage.enterBid(2, 3, "0.003");
        assertConditionTrue(settingsPage.getEnterWholeNumberError().contains("Please enter a whole number."));
    }


    @Test
    public void testCancelDecreaseBidAmount() throws Exception {
        String time[] = {"Friday", "7 AM", "7 PM"};

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.clickAdScheduleTab();
        settingsPage.createAdSchedule(time[0], time[1], time[2]);
        settingsPage.clickSaveScheduleButton();
        settingsPage.waitForElementNotVisible(UIMapper.SAVE_SCHEDULE_BUTTON);
        settingsPage.waitForElementNotVisible(UIMapper.LOADING);
        settingsPage.selectTableRow(2, 1);
        settingsPage.clickBidModifier(2, 3);
        settingsPage.clickDecreaseBid();
        settingsPage.enterBid(2, 3, "50");
        settingsPage.clickBidCancelButton();
        assertConditionTrue(!settingsPage.getTableText(2, 3).contains("-50%"));
    }

}
