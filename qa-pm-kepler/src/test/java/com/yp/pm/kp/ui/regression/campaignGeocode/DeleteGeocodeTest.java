package com.yp.pm.kp.ui.regression.campaignGeocode;

import com.yp.enums.selenium.LocatorTypeEnum;
import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.enums.model.CampaignGeoCodeStatusEnum;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.model.domain.CampaignGeoCode;
import com.yp.pm.kp.ui.MyAgencyPage;
import com.yp.pm.kp.ui.SettingsPage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class DeleteGeocodeTest extends BaseUITest {

    @Test
    public void testDeleteGeocode() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        String newGeoLocation = "91101";
        JSONObject newLocation = new JSONObject();
        newLocation.put("geoLocationType", "ZIP");
        newLocation.put("geoLocationName", "91101");
        JSONArray newGeoCode = new JSONArray();
        newGeoCode.add(newLocation);
        createCampaignGeoCode(account.getAccountId(), campaign.getCampaignId(), newGeoCode);

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.clickLocationsTab();
        settingsPage.clickGeoLocation(newGeoLocation);
        settingsPage.clickDeleteLocationButton();
        settingsPage.waitForElementVisible("//tr/td[normalize-space(.)='No records to load.']", LocatorTypeEnum.XPATH);
        assertConditionTrue(!settingsPage.getTableText(2, 2).contains(newGeoLocation));

        List<CampaignGeoCode> newGeoCodes = getCampaignGeoCodeByCampaignId(account.getAccountId(), campaign.getCampaignId());
        if (newGeoCodes != null) {
            for (CampaignGeoCode geo : newGeoCodes) {
                assertConditionTrue(geo.getGeoCodeStatus().toString().equalsIgnoreCase(CampaignGeoCodeStatusEnum.DELETED.toString()));
            }
        }
    }
}