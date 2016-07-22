package com.yp.pm.kp.ui.regression.campaignGeocode;

import com.yp.enums.selenium.LocatorTypeEnum;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class CreateGeocodeTest extends BaseUITest {

    private static Map<String, Object> testData;

    @Test
    public void testCreateNewGeocode() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        Campaign newCampaign = (Campaign) testData.get("campaign");
        Long campaignId = newCampaign.getCampaignId();

        String newGeoLocation = "91101";
        JSONObject newLocation = new JSONObject();
        newLocation.put("geoLocationType", "ZIP");
        newLocation.put("geoLocationName", "91101");
        Long newGeoLibId = getGeoLibId(newLocation);

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, accountId, campaignId);
        settingsPage.clickLocationsTab();
        settingsPage.clickLocationButton();
        settingsPage.enterGeoLocation(newGeoLocation);
        settingsPage.clickAddTargetLocation();
        settingsPage.clickSaveLocationButton();
        settingsPage.waitForElementNotVisible(UIMapper.NO_RECORDS_TO_LOAD, LocatorTypeEnum.XPATH);
        assertConditionTrue(settingsPage.getTableText(2, 2).contains(newGeoLocation));

        List<CampaignGeoCode> newGeoCodes = getCampaignGeoCodeByCampaignId(accountId, campaignId);
        for (CampaignGeoCode geo : newGeoCodes) {
            assertObjectEqual(geo.getGeoLibId(), newGeoLibId);
        }
    }

    @Test
    public void testCancelCreateNewGeoCode() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        Campaign newCampaign = (Campaign) testData.get("campaign");
        Long campaignId = newCampaign.getCampaignId();

        String newGeoLocation = "90031";
        JSONObject newLocation = new JSONObject();
        newLocation.put("geoLocationType", "ZIP");
        newLocation.put("geoLocationName", newGeoLocation);
        Long newGeoLibId = getGeoLibId(newLocation);

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, accountId, campaignId);
        settingsPage.clickLocationsTab();
        settingsPage.clickLocationButton();
        settingsPage.enterGeoLocation(newGeoLocation);
        settingsPage.clickAddTargetLocation();
        settingsPage.clickCancelLocationButton();
        assertConditionTrue(!settingsPage.getTableText(2, 2).contains(newGeoLocation));

        List<CampaignGeoCode> newGeoCodes = getCampaignGeoCodeByCampaignId(accountId, campaignId);
        if (newGeoCodes != null) {
            for (CampaignGeoCode geo : newGeoCodes) {
                assertConditionTrue(!geo.getGeoLibId().equals(newGeoLibId));
            }
        }
    }

    @Test
    public void testGeoNoMatchFoundError() throws Exception {
        String newGeoLocation = "9999";

        long accountId = ((Account) getTestData().get("account")).getAccountId();
        Campaign newCampaign = (Campaign) testData.get("campaign");
        Long campaignId = newCampaign.getCampaignId();

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, accountId, campaignId);
        settingsPage.clickLocationsTab();
        settingsPage.clickLocationButton();
        settingsPage.enterGeoLocation(newGeoLocation);
        assertConditionTrue(settingsPage.getNoMatchFoundText().contains("Sorry, no matches found."));
    }


    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();

            Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = createBudget(account.getAccountId(), 100, "standard");
            Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
            testData.put("account", account);
            testData.put("campaign", campaign);
        }
        return testData;
    }
}