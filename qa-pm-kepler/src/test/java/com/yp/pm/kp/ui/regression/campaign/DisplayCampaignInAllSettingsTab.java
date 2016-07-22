package com.yp.pm.kp.ui.regression.campaign;


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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class DisplayCampaignInAllSettingsTab extends BaseUITest {
    String campaignLabels[] = {"Campaign Settings", "Campaign Name", "Type", "Networks", "Device", "Locations", "Targeted Locations:", "Languages", "Budget", "Advanced Settings", "Start Date", "End Date", "Ad Scheduling", "Ad Rotation", "Exact and phrase match", "Edit", "Change Bid Adjustment »", "View Location Info »", "Schedule: Start Date, End Date, Ad Scheduling", "Ad Delivery: Ad Rotation", "Keyword Matching Options", "View Ad Schedule »"};

    @Test
    public void displayCampaign() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.loadingStatus();
        for (String campaignSettingLabel : campaignLabels) {
            assertConditionTrue(settingsPage.campaignSettingsLabel().contains(campaignSettingLabel));
        }
        assertConditionTrue(settingsPage.getCampaignText().contains(campaign.getCampaignName()));
        assertConditionTrue(settingsPage.getBudgetText().contains("100"));
        assertConditionTrue(settingsPage.getTypeText().contains("Search Network Only"));
        assertConditionTrue(settingsPage.getNetworkText().contains("YP Properties"));
        assertConditionTrue(settingsPage.getDeviceText().contains("Ads will show on all eligible devices by default."));
        assertConditionTrue(settingsPage.getDisplayDate().contains(settingsPage.currentDate()));
    }

}
