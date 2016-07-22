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
public class DisplayAdScheduleInSettingsTabTest extends BaseUITest {
    String headerLabels[] = {"Day and time", "Bid adj.", "Clicks", "Impr.", "CTR", "Avg. CPC", "Cost", "Avg. Pos."};
    String row2column2 = "FRIDAY | 5:00am - 5:00pm";

    @Test
    public void testCreateAdSchedule() throws Exception {
        String day = "Friday";
        String startTime = "5 AM";
        String endTime = "5 PM";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.clickAdScheduleTab();
        settingsPage.createAdSchedule(day, startTime, endTime);
        settingsPage.clickSaveScheduleButton();
        settingsPage.waitForElementNotVisible(UIMapper.SAVE_SCHEDULE_BUTTON);
        settingsPage.waitForElementNotVisible(UIMapper.NO_RECORDS_TO_LOAD);
        settingsPage.waitForElementNotVisible(UIMapper.LOADING);
        assertConditionTrue(settingsPage.getTableText(2, 2).contains(row2column2));

        for (String adScheduleHeaderLabel : headerLabels) {
            if (!settingsPage.geoLocationInSettingsTabHeader(adScheduleHeaderLabel)) {
                throw new NullPointerException("Element does not exist [" + adScheduleHeaderLabel + "]");
            }
            assertConditionTrue(settingsPage.getTableText(2, 2).equalsIgnoreCase(row2column2));
            assertObjectEqual(settingsPage.getTableSize(), 3.0);
        }
    }
}