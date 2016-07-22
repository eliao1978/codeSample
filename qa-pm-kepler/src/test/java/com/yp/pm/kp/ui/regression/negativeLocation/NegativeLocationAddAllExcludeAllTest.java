package com.yp.pm.kp.ui.regression.negativeLocation;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.model.domain.CampaignGeoCode;
import com.yp.pm.kp.ui.MyAgencyPage;
import com.yp.pm.kp.ui.SettingsPage;
import com.yp.pm.kp.ui.UIMapper;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class NegativeLocationAddAllExcludeAllTest extends BaseUITest {
    @Test
    public void testExcludeAllNegativeLocation() throws Exception {
        String targetLocation = "USA";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        JSONObject newLocation = new JSONObject();
        newLocation.put("geoLocationType", "COUNTRY");
        newLocation.put("geoLocationName", "USA");

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.clickLocationsTab();
        settingsPage.click(UIMapper.ADD_LOCATION_BUTTON);
        settingsPage.enterGeoLocation(targetLocation);
        settingsPage.excludeAllTargetLocation();
        settingsPage.clickSaveLocationButton();
        assertConditionTrue(!settingsPage.getTableText(2, 2).contains(targetLocation));

        List<CampaignGeoCode> newGeoCodes = getCampaignGeoCodeByCampaignId(account.getAccountId(), campaign.getCampaignId());
        for (CampaignGeoCode geo : newGeoCodes) {
            assertConditionTrue(getCampaignGeoCode(account.getAccountId(), campaign.getCampaignId(), geo.getGeocodeId()).isExcluded());
        }
    }


    @Test
    public void testAddAllNegativeLocation() throws Exception {
        String targetLocation = "USA";
        String newGeoLocation = "Mission Hills";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        JSONObject newLocation = new JSONObject();
        newLocation.put("geoLocationType", "COUNTRY");
        newLocation.put("geoLocationName", "USA");

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.clickLocationsTab();
        settingsPage.click(UIMapper.ADD_LOCATION_BUTTON);
        settingsPage.enterGeoLocation(targetLocation);
        settingsPage.selectAddTargetLocation(1);
        assertObjectEqual(settingsPage.getTextFromAllMatchedResults(1), settingsPage.getTextFromTargetLocation(1, 1));
        settingsPage.clickSaveLocationButton();
        settingsPage.click(UIMapper.ADD_LOCATION_BUTTON);
        settingsPage.enterGeoLocation(newGeoLocation);
        settingsPage.addAllTargetLocation();
        settingsPage.clickSaveLocationButton();
        assertConditionTrue(settingsPage.getTableText(2, 2).contains(targetLocation));
//        assertConditionTrue(settingsPage.getTableText(2, 3).contains(newGeoLocation));

        List<CampaignGeoCode> newGeoCodes = getCampaignGeoCodeByCampaignId(account.getAccountId(), campaign.getCampaignId());
        for (CampaignGeoCode geo : newGeoCodes) {
            assertConditionTrue(!getCampaignGeoCode(account.getAccountId(), campaign.getCampaignId(), geo.getGeocodeId()).isExcluded());
        }
    }

}
