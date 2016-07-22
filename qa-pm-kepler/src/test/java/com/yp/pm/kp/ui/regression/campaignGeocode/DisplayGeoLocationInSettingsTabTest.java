package com.yp.pm.kp.ui.regression.campaignGeocode;

import com.yp.enums.selenium.LocatorTypeEnum;
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
public class DisplayGeoLocationInSettingsTabTest extends BaseUITest {
    String headerLabels[] = {"Location", "Bid adj.", "Clicks", "Impr.", "CTR", "Avg. CPC", "Cost", "Avg. Pos."};

    @Test
    public void testDisplayGeoLocationInSettingsTab() throws Exception {
        String newGeoLocation = "91101";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.clickLocationsTab();
        settingsPage.click(UIMapper.ADD_LOCATION_BUTTON);
        settingsPage.enterGeoLocation(newGeoLocation);
        settingsPage.selectAddTargetLocation(1);
        settingsPage.clickSaveLocationButton();
        settingsPage.waitForElementNotVisible(UIMapper.NO_RECORDS_TO_LOAD, LocatorTypeEnum.XPATH);
        settingsPage.loadingStatus();
        for (String geoLocationHeaderLabel : headerLabels) {
            if (!settingsPage.geoLocationInSettingsTabHeader(geoLocationHeaderLabel)) {
                throw new NullPointerException("Element does not exist [" + geoLocationHeaderLabel + "]");
            }
            assertConditionTrue(settingsPage.getTableText(2, 2).equalsIgnoreCase(newGeoLocation + ", " + "CA " + "(ZIP)"));
            assertObjectEqual(settingsPage.getTableSize(), 3.0);
        }
    }
}
