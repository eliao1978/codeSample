package com.yp.pm.kp.ui.regression.campaignGeocode;


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

public class DisplayDeviceInDeviceTabTest extends BaseUITest {
    String headerLabels[] = {"Device", "Bid adj.", "Clicks", "Impr.", "CTR", "Avg. CPC", "Cost", "Avg. Pos."};
    String deviceLabels[] = {"Mobile devices with full browsers", "Computers", "Unknown", "Tablets with full browsers"};


    @Test
    public void testDisplayDeviceInDeviceTab() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.clickDeviceTab();
        for (String deviceHeaderLabels : headerLabels) {
            if (!settingsPage.geoLocationInSettingsTabHeader(deviceHeaderLabels)) {
                throw new NullPointerException("Element does not exist [" + deviceHeaderLabels + "]");
            }
        }
        for (String deviceType : deviceLabels) {
            if (!settingsPage.deviceNames(deviceType)) {
                throw new NullPointerException("Element does not exist [" + deviceType + "]");
            }
        }

    }
}