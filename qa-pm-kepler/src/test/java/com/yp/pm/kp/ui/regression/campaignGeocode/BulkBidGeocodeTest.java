package com.yp.pm.kp.ui.regression.campaignGeocode;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.ui.MyAgencyPage;
import com.yp.pm.kp.ui.SettingsPage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class BulkBidGeocodeTest extends BaseUITest {
    @Test
    public void BulkBidGeocodeAdjustment() throws Exception {
        String increaseByPer = "30";
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        JSONObject firstLocation = new JSONObject();
        firstLocation.put("geoLocationType", "ZIP");
        firstLocation.put("geoLocationName", "91101");
        JSONArray firstGeoCode = new JSONArray();
        firstGeoCode.add(firstLocation);
        createCampaignGeoCode(account.getAccountId(), campaign.getCampaignId(), firstGeoCode);

        JSONObject secondLocation = new JSONObject();
        secondLocation.put("geoLocationType", "ZIP");
        secondLocation.put("geoLocationName", "90503");
        JSONArray secondGeoCode = new JSONArray();
        secondGeoCode.add(secondLocation);
        createCampaignGeoCode(account.getAccountId(), campaign.getCampaignId(), secondGeoCode);

        JSONObject thirdLocation = new JSONObject();
        thirdLocation.put("geoLocationType", "ZIP");
        thirdLocation.put("geoLocationName", "91201");
        JSONArray thirdGeoCode = new JSONArray();
        thirdGeoCode.add(thirdLocation);
        createCampaignGeoCode(account.getAccountId(), campaign.getCampaignId(), thirdGeoCode);

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.clickLocationsTab();
        settingsPage.clickLocationsCheckall();
        settingsPage.clickBidAdjustmentButton();
        settingsPage.enterIncreaseByTextField(increaseByPer);
        settingsPage.clickMakeChangesButton();
        assertConditionTrue(!settingsPage.getTableText(2, 2).contains(increaseByPer));
        assertConditionTrue(!settingsPage.getTableText(3, 2).contains(increaseByPer));
        assertConditionTrue(!settingsPage.getTableText(4, 2).contains(increaseByPer));
        Thread.sleep(1000);
    }
}
