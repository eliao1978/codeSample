package com.yp.pm.kp.ui.regression.campaignAdSchedule;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.model.domain.CampaignAdSchedule;
import com.yp.pm.kp.ui.MyAgencyPage;
import com.yp.pm.kp.ui.SettingsPage;
import com.yp.pm.kp.ui.UIMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class CreateAdScheduleTest extends BaseUITest {

    @Test
    public void testCreateNewAdSchedule() throws Exception {
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
        assertConditionTrue(settingsPage.getTableText(2, 2).contains("FRIDAY | 7:00am - 7:00pm"));

        List<CampaignAdSchedule> newAdSchedule = getCampaignAdScheduleByCampaignId(account.getAccountId(), campaign.getCampaignId());
        for (CampaignAdSchedule schedule : newAdSchedule) {
            assertObjectEqual(schedule.getDayOfWeek().toString(), time[0]);
            assertObjectEqual(schedule.getStartTime(), "0700");
            assertObjectEqual(schedule.getEndTime(), "1900");
        }
    }

    @Test
    public void testCancelCreateNewAdSchedule() throws Exception {
        String day = "Monday";
        String startTime = "1 PM";
        String endTime = "2 PM";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.clickAdScheduleTab();
        settingsPage.createAdSchedule(day, startTime, endTime);
        settingsPage.clickCancelAdSchedule();
        assertConditionTrue(!settingsPage.getTableText(2, 2).contains("Monday | 1:00am - 2:00pm"));


        List<CampaignAdSchedule> newAdSchedule = getCampaignAdScheduleByCampaignId(account.getAccountId(), campaign.getCampaignId());
        if (newAdSchedule != null) {
            for (CampaignAdSchedule schedule : newAdSchedule) {
                assertConditionTrue(!schedule.getDayOfWeek().toString().equalsIgnoreCase("Monday"));
                assertConditionTrue(!schedule.getStartTime().equals("1100"));
                assertConditionTrue(!schedule.getEndTime().equals("1200"));
            }
        }
    }

    @Test
    public void testDuplicateAdScheduleError() throws Exception {
        String time[] = {"Friday", "7 AM", "7 PM"};
        String timePeriodOverLapError = "Time periods overlap.";
        String duplicateAdScheduleError = "Duplicate ad schedule.";

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
        settingsPage.clickAdScheduleTab();
        settingsPage.createAdSchedule(time[0], time[1], time[2]);
        settingsPage.clickSaveScheduleButton();
        settingsPage.checkDuplicateError();
        assertConditionTrue(settingsPage.getTimeOverLapErrorText().contains(timePeriodOverLapError));
        assertConditionTrue(settingsPage.getDuplicateAdScheduleErrorText().contains(duplicateAdScheduleError));

    }


    @Test
    public void testTimePeriodOverlapError() throws Exception {
        String time[] = {"Friday", "7 AM", "7 PM"};
        String time2[] = {"Friday", "7 AM", "8 AM"};
        String timePeriodOverLapError = "Time periods overlap.";

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
        settingsPage.clickAdScheduleTab();
        settingsPage.createAdSchedule(time2[0], time2[1], time2[2]);
        settingsPage.clickSaveScheduleButton();
        assertConditionTrue(settingsPage.getTimeOverLapErrorText().contains(timePeriodOverLapError));
    }

    @Test
    public void testInvalidEndTimeError() throws Exception {
        String time[] = {"Friday", "7 AM", "6 AM"};
        String inValidEndTime = "End time is earlier then start time.";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.clickAdScheduleTab();
        settingsPage.createAdSchedule(time[0], time[1], time[2]);
        settingsPage.clickSaveScheduleButton();
        assertConditionTrue(settingsPage.getInvalidEndTimeError().contains(inValidEndTime));

    }
}