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
public class UpdateNegativeLocationTest extends BaseUITest {

    @Test
    public void testUpdateNegativeLocation() throws Exception {
        String targetLocation = "USA";
        String newGeoLocation = "Bloomington";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        JSONObject newLocation = new JSONObject();
        newLocation.put("geoLocationType", "COUNTRY");
        newLocation.put("geoLocationName", "USA");
        String expectedValues[] = {"PEORIA-" + newGeoLocation + ", " + "IL " + "(DMA)", newGeoLocation + ", " + "KS " + "(City)", newGeoLocation + ", " + "CA " + "(City)", newGeoLocation + ", " + "MD " + "(City)"};

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
        settingsPage.selectAddTargetLocation(1);
        settingsPage.selectAddTargetLocation(2);
        settingsPage.selectExcludeTargetLocation(3);
        assertObjectEqual(settingsPage.getTextFromAllMatchedResults(1), settingsPage.getTextFromTargetLocation(2, 1));
        assertObjectEqual(settingsPage.getTextFromAllMatchedResults(2), settingsPage.getTextFromTargetLocation(3, 1));
        assertObjectEqual(settingsPage.getTextFromAllMatchedResults(3), settingsPage.getTextFromExcludedLocation(1));
        settingsPage.clickSaveLocationButton();
        settingsPage.clickExcludedLocation();
        assertConditionTrue(settingsPage.getTableText(3, 2).equalsIgnoreCase(expectedValues[0]));
        assertConditionTrue(settingsPage.getTableText(4, 2).equalsIgnoreCase(expectedValues[1]));
        assertConditionTrue(settingsPage.getExcludedText(2, 2).equalsIgnoreCase(expectedValues[2]));
        settingsPage.selectExcludedText(2, 1);
        settingsPage.clickExcludedDeleteButton();
        settingsPage.clickExcludedLocation();
        assertConditionTrue(!settingsPage.getExcludedText(2, 2).equalsIgnoreCase(expectedValues[2]));
        settingsPage.click(UIMapper.ADD_LOCATION_BUTTON);
        settingsPage.enterGeoLocation(newGeoLocation);
        settingsPage.selectExcludeTargetLocation(4);
        settingsPage.clickSaveLocationButton();
        settingsPage.clickExcludedLocation();
        assertConditionTrue(settingsPage.getExcludedText(2, 2).equalsIgnoreCase(expectedValues[3]));

        List<CampaignGeoCode> newGeoCodeExcluded = getCampaignGeoCodeByCampaignId(account.getAccountId(), campaign.getCampaignId());
        for (CampaignGeoCode geo : newGeoCodeExcluded) {
            if (geo.getGeoLibId().equals(targetLocation)) {
                assertConditionTrue(getCampaignGeoCode(account.getAccountId(), campaign.getCampaignId(), geo.getGeocodeId()).isExcluded());
            }
        }
    }
}

