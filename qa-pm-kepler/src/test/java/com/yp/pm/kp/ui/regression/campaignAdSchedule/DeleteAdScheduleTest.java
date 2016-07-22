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
public class DeleteAdScheduleTest extends BaseUITest {

    @Test
    public void testDeleteAdSchedule() throws Exception {
        String day = "Friday";
        String startTime = "7 AM";
        String endTime = "7 PM";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.clickAdScheduleTab();
        settingsPage.createAdSchedule(day, startTime, endTime);
        settingsPage.clickSaveScheduleButton();
        settingsPage.waitForElementNotVisible(UIMapper.SAVE_SCHEDULE_BUTTON);
        settingsPage.waitForElementNotVisible(UIMapper.LOADING);
        settingsPage.selectSchedule("FRIDAY");
        settingsPage.clickDeleteAdScheduleButton();
        settingsPage.waitForElementNotVisible(UIMapper.LOADING);
        assertConditionTrue(!settingsPage.getTableText(2, 2).contains("FRIDAY | 7:00am - 7:00pm"));

        List<CampaignAdSchedule> newAdSchedule = getCampaignAdScheduleByCampaignId(account.getAccountId(), campaign.getCampaignId());
        for (CampaignAdSchedule schedule : newAdSchedule) {
            assertConditionTrue(schedule.getAdScheduleStatus().toString().equalsIgnoreCase("DELETED"));
        }
    }
}