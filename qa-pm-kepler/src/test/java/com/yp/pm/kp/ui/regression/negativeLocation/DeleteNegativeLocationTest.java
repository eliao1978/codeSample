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
public class DeleteNegativeLocationTest extends BaseUITest {

    @Test
    public void testDeleteNegativeLocation() throws Exception {
        String targetLocation = "USA";
        String negativeLocation = "Bloomington";
        String ExpectedLocation = "PEORIA-" + negativeLocation + ", " + "IL " + "(DMA)";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        JSONObject newLocation = new JSONObject();
        newLocation.put("geoLocationType", "COUNTRY");
        newLocation.put("geoLocationName", "USA");
        Long newGeoLibId = getGeoLibId(newLocation);

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.clickLocationsTab();
        settingsPage.click(UIMapper.ADD_LOCATION_BUTTON);
        settingsPage.enterGeoLocation(targetLocation);
        settingsPage.selectAddTargetLocation(1);
        assertObjectEqual(settingsPage.getTextFromAllMatchedResults(1), settingsPage.getTextFromTargetLocation(1, 1));
        settingsPage.clickSaveLocationButton();
        settingsPage.click(UIMapper.ADD_LOCATION_BUTTON);
        settingsPage.enterGeoLocation(negativeLocation);
        settingsPage.selectExcludeTargetLocation(1);
        settingsPage.clickExcludedLocation();
        settingsPage.clickSaveLocationButton();
        settingsPage.clickExcludedLocation();
        assertConditionTrue(settingsPage.getExcludedText(2, 2).equalsIgnoreCase(ExpectedLocation));
        settingsPage.selectExcludedText(2, 1);
        settingsPage.clickExcludedDeleteButton();
        settingsPage.clickExcludedLocation();
        assertConditionTrue(!settingsPage.getExcludedText(2, 2).equalsIgnoreCase(ExpectedLocation));

        List<CampaignGeoCode> newGeoCodes = getCampaignGeoCodeByCampaignId(account.getAccountId(), campaign.getCampaignId());
        for (CampaignGeoCode geo : newGeoCodes) {
            if (geo.getGeoLibId().equals(newGeoLibId)) {
                assertConditionTrue(!getCampaignGeoCode(account.getAccountId(), campaign.getCampaignId(), geo.getGeocodeId()).isExcluded());
            }
        }
    }


    @Test
    public void testDeleteAllNegativeLocation() throws Exception {
        String targetLocation = "USA";
        String newGeoLocation = "Bloomington";
        String expectedLocation = newGeoLocation + ", " + "CA " + "(City)";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        JSONObject newLocation = new JSONObject();
        newLocation.put("geoLocationType", "COUNTRY");
        newLocation.put("geoLocationName", "USA");
        Long newGeoLibId = getGeoLibId(newLocation);

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
        settingsPage.clickExcludeAll();
        settingsPage.clickSaveLocationButton();
        settingsPage.clickExcludedLocation();
        settingsPage.selectAllExcludedText();
        settingsPage.clickExcludedDeleteButton();
        settingsPage.clickExcludedLocation();
        assertConditionTrue(!settingsPage.getExcludedText(2, 2).equalsIgnoreCase(expectedLocation));

        List<CampaignGeoCode> newGeoCodes = getCampaignGeoCodeByCampaignId(account.getAccountId(), campaign.getCampaignId());
        for (CampaignGeoCode geo : newGeoCodes) {
            if (geo.getGeoLibId().equals(newGeoLibId)) {
                assertConditionTrue(!getCampaignGeoCode(account.getAccountId(), campaign.getCampaignId(), geo.getGeocodeId()).isExcluded());
            }
        }
    }

}