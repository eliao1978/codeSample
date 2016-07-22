package com.yp.pm.kp.ui.regression.campaign;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.enums.model.CampaignStatusEnum;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class EditCampaignAtSettingTabTest extends BaseUITest {

    private static Map<String, Object> testData;

    @Test
    public void testEditSettingsCampaign() throws Exception {
        String newGeoLocation = "90031";
        String campaignName = "campaign_" + System.currentTimeMillis();
        String editCampaignName = "E" + campaignName;
        String adGroupName = "adgroup_" + System.currentTimeMillis();
        String headline = "headline test";
        String desc1 = "desc1";
        String desc2 = "desc2";
        String displayURL = "www.yp.com";
        String destURL = "www.yp.com";

        JSONObject country = new JSONObject();
        country.put("geoLocationType", "COUNTRY");
        country.put("geoLocationName", "USA");
        Long countryGeoLib = getGeoLibId(country);

        JSONObject newLocation = new JSONObject();
        newLocation.put("geoLocationType", "ZIP");
        newLocation.put("geoLocationName", newGeoLocation);
        Long newGeoLibId = getGeoLibId(newLocation);
        Long accountId = ((Account) getTestData().get("account")).getAccountId();
        Campaign newCampaign = createCampaign(accountId, ((Budget) getTestData().get("budget")).getBudgetId()).get(0);

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToAllSettingsTab(agencyId, accountId);
        settingsPage.loadingStatus();
        settingsPage.clickCampaign(newCampaign.getCampaignName());
        settingsPage.clickEditCampaign();
        settingsPage.enterEditSettings(editCampaignName);
        settingsPage.clickSaveCampaignName();
        settingsPage.isSettingPageDisplayed();
        settingsPage.clickEditCampaignLocation();
        settingsPage.enterGeoLocation(newGeoLocation);
        settingsPage.clickAddTargetLocation();
        settingsPage.clickSaveCampaignLocationButton();
        assertConditionTrue(settingsPage.getCampaignHeaderText().contains(editCampaignName));

        Campaign campaign = getCampaignByName(accountId, editCampaignName);
        assertObjectNotNull(campaign.getCampaignId());
        assertObjectEqual(campaign.getCampaignName(), editCampaignName);
        assertObjectEqual(campaign.getAccountId(), accountId);
        assertObjectEqual(campaign.getCampaignStatus().toString(), CampaignStatusEnum.ACTIVE.toString());
        List<CampaignGeoCode> newGeoCodes = getCampaignGeoCodeByCampaignId(accountId, campaign.getCampaignId());
        for (CampaignGeoCode geo : newGeoCodes) {
            assertConditionTrue(geo.getGeoLibId().equals(countryGeoLib) || geo.getGeoLibId().equals(newGeoLibId));
        }
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = createBudget(account.getAccountId(), 100, "standard");
            testData.put("account", account);
            testData.put("budget", budget);
        }

        return testData;
    }
}