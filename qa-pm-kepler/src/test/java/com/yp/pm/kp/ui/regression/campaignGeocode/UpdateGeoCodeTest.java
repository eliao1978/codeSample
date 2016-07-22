package com.yp.pm.kp.ui.regression.campaignGeocode;

import com.yp.enums.selenium.LocatorTypeEnum;
import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.ui.MyAgencyPage;
import com.yp.pm.kp.ui.SettingsPage;
import com.yp.pm.kp.ui.UIMapper;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")

public class UpdateGeoCodeTest extends BaseUITest {

    @Test
    public void testIncreaseBid() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        String newGeoLocation = "91101";
        JSONObject newLocation = new JSONObject();
        newLocation.put("geoLocationType", "ZIP");
        newLocation.put("geoLocationName", "91101");

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.loadingStatus();
        settingsPage.clickLocationsTab();
        settingsPage.clickLocationButton();
        settingsPage.enterGeoLocation(newGeoLocation);
        settingsPage.clickAddTargetLocation();
        settingsPage.clickSaveLocationButton();
        settingsPage.waitForElementNotVisible(UIMapper.NO_RECORDS_TO_LOAD, LocatorTypeEnum.XPATH);
        settingsPage.loadingStatus();
        settingsPage.clickBidModifier(2, 3);
        settingsPage.enterBid(2, 3, "50");
        settingsPage.clickBidSaveButton();
        settingsPage.waitForElementNotVisible("button[class='btn btn-xs btn-primary'][ng-click='save()']");
        settingsPage.waitForElementNotVisible(UIMapper.LOADING);
        assertConditionTrue(settingsPage.getTableText(2, 3).contains("50%"));
    }

    @Test
    public void testCancelIncreaseBid() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        String newGeoLocation = "91101";
        JSONObject newLocation = new JSONObject();
        newLocation.put("geoLocationType", "ZIP");
        newLocation.put("geoLocationName", "91101");

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.loadingStatus();
        settingsPage.clickLocationsTab();
        settingsPage.clickLocationButton();
        settingsPage.enterGeoLocation(newGeoLocation);
        settingsPage.clickAddTargetLocation();
        settingsPage.clickSaveLocationButton();
        settingsPage.waitForElementNotVisible(UIMapper.NO_RECORDS_TO_LOAD, LocatorTypeEnum.XPATH);
        settingsPage.loadingStatus();
        settingsPage.clickBidModifier(2, 3);
        settingsPage.enterBid(2, 3, "50");
        settingsPage.clickBidCancelButton();
        assertConditionTrue(settingsPage.getTableText(2, 3).contains("0%"));
    }


    @Test
    public void testDecreaseBid() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        String newGeoLocation = "91101";
        JSONObject newLocation = new JSONObject();
        newLocation.put("geoLocationType", "ZIP");
        newLocation.put("geoLocationName", "91101");

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.loadingStatus();
        settingsPage.clickLocationsTab();
        settingsPage.clickLocationButton();
        settingsPage.enterGeoLocation(newGeoLocation);
        settingsPage.clickAddTargetLocation();
        settingsPage.clickSaveLocationButton();
        settingsPage.clickBidModifier(2, 3);
        settingsPage.clickDecreaseBid();
        settingsPage.enterBid(2, 3, "50");
        settingsPage.clickBidSaveButton();
        assertConditionTrue(settingsPage.getTableText(2, 3).contains("-50%"));
    }


    @Test
    public void testDecreasedBidLimitAdjustmentError() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        String newGeoLocation = "91101";
        JSONObject newLocation = new JSONObject();
        newLocation.put("geoLocationType", "ZIP");
        newLocation.put("geoLocationName", "91101");

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.loadingStatus();
        settingsPage.clickLocationsTab();
        settingsPage.clickLocationButton();
        settingsPage.enterGeoLocation(newGeoLocation);
        settingsPage.clickAddTargetLocation();
        settingsPage.clickSaveLocationButton();
        settingsPage.clickBidModifier(2, 3);
        settingsPage.clickDecreaseBid();
        settingsPage.enterBid(2, 3, "91");
        assertConditionTrue(settingsPage.getDecreaseBidAdjustmentErrorText().contains("Please enter a smaller number."));
    }


    @Test
    public void testIncreaseBidLimitAdjustmentError() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        String newGeoLocation = "91101";
        JSONObject newLocation = new JSONObject();
        newLocation.put("geoLocationType", "ZIP");
        newLocation.put("geoLocationName", "91101");

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.loadingStatus();
        settingsPage.clickLocationsTab();
        settingsPage.clickLocationButton();
        settingsPage.enterGeoLocation(newGeoLocation);
        settingsPage.clickAddTargetLocation();
        settingsPage.clickSaveLocationButton();
        settingsPage.clickBidModifier(2, 3);
        settingsPage.enterBid(2, 3, "901");
        assertConditionTrue(settingsPage.getIncreaseBidAdjustmentErrorText().contains("Please enter a smaller number."));

    }

    @Test
    public void testCancelDecreaseBid() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        String newGeoLocation = "91101";
        JSONObject newLocation = new JSONObject();
        newLocation.put("geoLocationType", "ZIP");
        newLocation.put("geoLocationName", "91101");

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.loadingStatus();
        settingsPage.clickLocationsTab();
        settingsPage.clickLocationButton();
        settingsPage.enterGeoLocation(newGeoLocation);
        settingsPage.clickAddTargetLocation();
        settingsPage.clickSaveLocationButton();
        settingsPage.clickBidModifier(2, 3);
        settingsPage.clickDecreaseBid();
        settingsPage.enterBid(2, 3, "50");
        settingsPage.clickBidCancelButton();
        settingsPage.waitForElementNotVisible("button[class='btn btn-xs btn-primary'][ng-click='save()']");
        settingsPage.waitForElementNotVisible(UIMapper.LOADING);
        assertConditionTrue(settingsPage.getTableText(2, 3).contains("0%"));
    }

    @Test
    public void testEnterWholeNumberInBidError() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        String newGeoLocation = "91101";
        JSONObject newLocation = new JSONObject();
        newLocation.put("geoLocationType", "ZIP");
        newLocation.put("geoLocationName", "91101");

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.loadingStatus();
        settingsPage.clickLocationsTab();
        settingsPage.clickLocationButton();
        settingsPage.enterGeoLocation(newGeoLocation);
        settingsPage.clickAddTargetLocation();
        settingsPage.clickSaveLocationButton();
        settingsPage.clickBidModifier(2, 3);
        settingsPage.enterBid(2, 3, "0.09");
        assertConditionTrue(settingsPage.getEnterWholeNumberError().contains("Please enter a whole number."));

    }

    @Test
    public void testInvalidBidNumberError() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        String newGeoLocation = "91101";
        JSONObject newLocation = new JSONObject();
        newLocation.put("geoLocationType", "ZIP");
        newLocation.put("geoLocationName", "91101");

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        settingsPage.loadingStatus();
        settingsPage.clickLocationsTab();
        settingsPage.clickLocationButton();
        settingsPage.enterGeoLocation(newGeoLocation);
        settingsPage.clickAddTargetLocation();
        settingsPage.clickSaveLocationButton();
        settingsPage.clickBidModifier(2, 3);
        settingsPage.enterBid(2, 3, "*&(OU");
        settingsPage.waitForErrorMessage();
        assertConditionTrue(settingsPage.getInvalidBidError().contains("Invalid Number."));

    }

}
