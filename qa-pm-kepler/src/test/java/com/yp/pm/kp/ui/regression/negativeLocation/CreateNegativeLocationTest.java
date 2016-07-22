package com.yp.pm.kp.ui.regression.negativeLocation;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.model.domain.CampaignGeoCode;
import com.yp.pm.kp.ui.MyAgencyPage;
import com.yp.pm.kp.ui.SettingsPage;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class CreateNegativeLocationTest extends BaseUITest {

    @Test
    public void testCreateMultipleNegativeLocationCity() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        String newGeoLocation = "Mission Hills";
        String targetLocation = "USA";
        String expectedValues[] = {"USA (Country)", newGeoLocation + ", " + "KS " + "(City)", newGeoLocation + ", " + "CA " + "(City)", newGeoLocation + ", " + "MO " + "(City)"};

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.clickLocationsTab();
        settingsPage.clickLocationButton();
        settingsPage.enterGeoLocation(targetLocation);
        settingsPage.selectAddTargetLocation(1);
        assertObjectEqual(settingsPage.getTextFromAllMatchedResults(1), settingsPage.getTextFromTargetLocation(1, 1));
        settingsPage.clickSaveLocationButton();
        settingsPage.clickLocationButton();
        settingsPage.enterGeoLocation(newGeoLocation);
        settingsPage.selectAddTargetLocation(1);
        settingsPage.selectAddTargetLocation(2);
        settingsPage.selectExcludeTargetLocation(3);
        assertObjectEqual(settingsPage.getTextFromAllMatchedResults(1), settingsPage.getTextFromTargetLocation(2, 1));
        assertObjectEqual(settingsPage.getTextFromAllMatchedResults(2), settingsPage.getTextFromTargetLocation(3, 1));
        assertObjectEqual(settingsPage.getTextFromAllMatchedResults(3), settingsPage.getTextFromExcludedLocation(1));
        settingsPage.clickSaveLocationButton();
        settingsPage.clickExcludedLocation();
        assertConditionTrue(settingsPage.getTableText(2, 2).equalsIgnoreCase(expectedValues[0]));
        assertConditionTrue(settingsPage.getTableText(3, 2).equalsIgnoreCase(expectedValues[1]));
        assertConditionTrue(settingsPage.getTableText(4, 2).equalsIgnoreCase(expectedValues[2]));
        assertConditionTrue(settingsPage.getExcludedText(2, 2).equalsIgnoreCase(expectedValues[3]));

        List<CampaignGeoCode> newGeoCodes = getCampaignGeoCodeByCampaignId(account.getAccountId(), campaign.getCampaignId());
        boolean match = false;
        int isExcludedLocationSize=0;
        for (CampaignGeoCode geo : newGeoCodes) {
            if (geo.getGeoLibId().toString().equals("17794")) {
                assertConditionTrue(geo.isExcluded());
                match = true;
            }
            if (geo.isExcluded()) {
                isExcludedLocationSize++;
            }
        }
        assertConditionTrue(newGeoCodes.size() == 4);
        assertObjectEqual(isExcludedLocationSize++, 1);
        assertConditionTrue(match);
    }

    @Test
    public void testCreateSingleNegativeLocation() throws Exception {
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
        settingsPage.clickLocationButton();
        settingsPage.enterGeoLocation(targetLocation);
        settingsPage.selectAddTargetLocation(1);
        assertObjectEqual(settingsPage.getTextFromAllMatchedResults(1), settingsPage.getTextFromTargetLocation(1, 1));
        settingsPage.clickSaveLocationButton();
        settingsPage.clickLocationButton();
        settingsPage.enterGeoLocation(negativeLocation);
        settingsPage.selectExcludeTargetLocation(1);
        settingsPage.clickExcludedLocation();
        settingsPage.clickSaveLocationButton();
        settingsPage.clickExcludedLocation();
        assertConditionTrue(settingsPage.getExcludedText(2, 2).equalsIgnoreCase(ExpectedLocation));

        List<CampaignGeoCode> newGeoCodes = getCampaignGeoCodeByCampaignId(account.getAccountId(), campaign.getCampaignId());
        for (CampaignGeoCode geo : newGeoCodes) {
            if (geo.getGeoLibId().toString().equals(newGeoLibId)) {
                assertConditionTrue(getCampaignGeoCode(account.getAccountId(), campaign.getCampaignId(), geo.getGeocodeId()).isExcluded());

            }

        }
    }


    @Test
    public void testCreateNegativeLocationZip() throws Exception {
        String newGeoLocation = "91101";
        String geoLocation = "USA";
        String expectedLocation = newGeoLocation + ", " + "CA " + "(ZIP)";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        JSONObject newLocation = new JSONObject();
        newLocation.put("geoLocationType", "ZIP");
        newLocation.put("geoLocationName", "91101");
        Long newGeoLibId = getGeoLibId(newLocation);

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.clickLocationsTab();
        settingsPage.clickLocationButton();
        settingsPage.enterGeoLocation(geoLocation);
        settingsPage.selectAddTargetLocation(1);
        settingsPage.clickSaveLocationButton();
        settingsPage.clickLocationButton();
        settingsPage.enterGeoLocation(newGeoLocation);
        settingsPage.selectExcludeTargetLocation(1);
        assertObjectEqual(settingsPage.getTextFromAllMatchedResults(1), settingsPage.getTextFromExcludedLocation(1));
        settingsPage.clickSaveLocationButton();
        settingsPage.clickExcludedLocation();
        assertConditionTrue(settingsPage.getExcludedText(2, 2).equalsIgnoreCase(expectedLocation));

        List<CampaignGeoCode> newGeoCodes = getCampaignGeoCodeByCampaignId(account.getAccountId(), campaign.getCampaignId());
        boolean match = false;
        int isExcludedLocationSize = 0;
        for (CampaignGeoCode geo : newGeoCodes) {
            if (geo.getGeoLibId().equals(newGeoLibId)) {
                assertConditionTrue(geo.isExcluded());
                match = true;
            }
            if (geo.isExcluded()) {
                isExcludedLocationSize++;
            }
        }
        assertConditionTrue(newGeoCodes.size() == 2);
        assertObjectEqual(isExcludedLocationSize++, 1);
        assertConditionTrue(match);
    }


    @Test
    public void testCreateNegativeLocationState() throws Exception {
        String geoLocation = "USA";
        String newGeoLocation = "CALIFORNIA";
        String excludedValue = newGeoLocation + " (State)";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        JSONObject newLocation = new JSONObject();
        newLocation.put("geoLocationType", "STATE");
        newLocation.put("geoLocationName", "CALIFORNIA");
        Long newGeoLibId = getGeoLibId(newLocation);

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.clickLocationsTab();
        settingsPage.clickLocationButton();
        settingsPage.enterGeoLocation(geoLocation);
        settingsPage.selectAddTargetLocation(1);
        assertObjectEqual(settingsPage.getTextFromAllMatchedResults(1), settingsPage.getTextFromTargetLocation(1, 1));
        settingsPage.clickSaveLocationButton();
        settingsPage.clickLocationButton();
        settingsPage.enterGeoLocation(newGeoLocation);
        settingsPage.selectExcludeTargetLocation(1);
        settingsPage.selectExcludeTargetLocation(2);
        assertObjectEqual(settingsPage.getTextFromAllMatchedResults(1), settingsPage.getTextFromExcludedLocation(1));
        settingsPage.clickSaveLocationButton();
        settingsPage.clickExcludedLocation();
        assertConditionTrue(settingsPage.getExcludedText(2, 2).equalsIgnoreCase(excludedValue));

        List<CampaignGeoCode> newGeoCodes = getCampaignGeoCodeByCampaignId(account.getAccountId(), campaign.getCampaignId());


        boolean match = false;
        int isExcludedLocationSize = 0;
        for (CampaignGeoCode geo : newGeoCodes) {
            assertConditionTrue(newGeoCodes.get(1).isExcluded());
            assertConditionTrue(newGeoCodes.get(2).isExcluded());
            if (geo.getGeoLibId().equals(newGeoLibId)) {
                assertConditionTrue(geo.isExcluded());
                match = true;
            }
            if (geo.isExcluded()) {
                isExcludedLocationSize++;
            }
        }
        assertConditionTrue(newGeoCodes.size() == 3);
        assertObjectEqual(isExcludedLocationSize++, 2);
        assertConditionTrue(match);
    }
}

