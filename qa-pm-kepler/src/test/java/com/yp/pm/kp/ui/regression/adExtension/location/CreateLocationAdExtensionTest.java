package com.yp.pm.kp.ui.regression.adExtension.location;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.model.domain.LocationAdExtension;
import com.yp.pm.kp.ui.AdExtensionLocationExtension;
import com.yp.pm.kp.ui.MyAgencyPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class CreateLocationAdExtensionTest extends BaseUITest {

    @Test
    public void testCreateNewLocation() throws Exception {
        String location[] = {"611 N Brand BLVD", "Glendale", "California", "91203", "8182222222"};
        String company = "YP";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();

        MyAgencyPage agencyPage = login();
        AdExtensionLocationExtension locationExtension = agencyPage.navigateToAdExtensionsLocationTab(agencyId, account.getAccountId(), campaignId);
        locationExtension.locationExtension();
        locationExtension.click_EXTENSION_BUTTON();
        locationExtension.click_New_Location_Extension_BUTTON();
        locationExtension.enterAddressLineOne(location[0]);
        locationExtension.enterCity(location[1]);
        locationExtension.enterState(location[2]);
        locationExtension.enterZip(location[3]);
        locationExtension.enterCompanyName(company);
        locationExtension.enterPhoneNumber(location[4]);
        locationExtension.saveButton();
        locationExtension.checkForAddressError();
        assertConditionTrue(locationExtension.getLocationTableText(2, 2).contains(company));
        for (String locationText : location) {
            assertConditionTrue(locationExtension.getLocationTableText(2, 3).contains(locationText));
        }

        List<LocationAdExtension> newLocationExtension = getLocationAdExtensionByAccountId(account.getAccountId());
        assertConditionTrue(newLocationExtension.size() >= 1);
        LocationAdExtension newLocation = newLocationExtension.get(0);
        assertObjectNotNull(newLocation.getAccountId());
        assertObjectEqual(newLocation.getAccountId(), account.getAccountId());
        assertObjectEqual(newLocation.getAddressLine1(), location[0]);
        assertObjectEqual(newLocation.getCity(), location[1]);
        assertObjectEqual(newLocation.getState(), location[2]);
        assertObjectEqual(newLocation.getZip(), location[3]);
        assertObjectEqual(newLocation.getPhoneNumber(), location[4]);
        assertObjectEqual(newLocation.getCompanyName(), company);
        assertObjectEqual(newLocation.getAdExtensionStatus().toString(), "ACTIVE");
    }


    @Test
    public void testCancelCreateLocation() throws Exception {
        String cancelLocation[] = {"611 N Cancel", "Brand BLVD Cancel", "Glendale Cancel", "California Cancel", "90000", "8088182222"};
        String company = "YP Cancel";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();

        MyAgencyPage agencyPage = login();
        AdExtensionLocationExtension locationExtension = agencyPage.navigateToAdExtensionsLocationTab(agencyId, account.getAccountId(), campaignId);
        locationExtension.locationExtension();
        locationExtension.click_EXTENSION_BUTTON();
        locationExtension.click_New_Location_Extension_BUTTON();
        locationExtension.enterAddressLineOne(cancelLocation[0]);
        locationExtension.enterAddressLineTwo(cancelLocation[1]);
        locationExtension.enterCity(cancelLocation[2]);
        locationExtension.enterState(cancelLocation[3]);
        locationExtension.enterZip(cancelLocation[4]);
        locationExtension.enterCompanyName(company);
        locationExtension.enterPhoneNumber(cancelLocation[5]);
        locationExtension.cancelButton();
        assertConditionTrue(!locationExtension.getLocationTableText(2, 2).contains(company));
        for (String locationText : cancelLocation) {
            assertConditionTrue(!locationExtension.getLocationTableText(2, 3).contains(locationText));
        }

        List<LocationAdExtension> newLocationExtension = getLocationAdExtensionByAccountId(account.getAccountId());
        for (LocationAdExtension location : newLocationExtension) {
            assertConditionTrue(!location.getAddressLine1().equalsIgnoreCase(cancelLocation[0]));
            assertConditionTrue(!location.getAddressLine2().equalsIgnoreCase(cancelLocation[1]));
            assertConditionTrue(!location.getCity().equalsIgnoreCase(cancelLocation[2]));
            assertConditionTrue(!location.getState().equalsIgnoreCase(cancelLocation[3]));
            assertConditionTrue(!location.getZip().equalsIgnoreCase(cancelLocation[4]));
            assertConditionTrue(!location.getPhoneNumber().equalsIgnoreCase(cancelLocation[5]));
            assertConditionTrue(!location.getCompanyName().equalsIgnoreCase(company));
        }
    }

    @Test
    public void testMissingAddressError() throws Exception {
        String errorMessages[] = {"Address 1 is required", "City is required.", "State is required.", "Zip code is required"};

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();

        MyAgencyPage agencyPage = login();
        AdExtensionLocationExtension locationExtension = agencyPage.navigateToAdExtensionsLocationTab(agencyId, account.getAccountId(), campaignId);
        locationExtension.locationExtension();
        locationExtension.click_EXTENSION_BUTTON();
        locationExtension.click_New_Location_Extension_BUTTON();
        locationExtension.saveButton();
        assertConditionTrue(locationExtension.getLocationExtensionAddressErrorText().contains(errorMessages[0]));
        assertConditionTrue(locationExtension.getLocationExtensionCityErrorText().contains(errorMessages[1]));
        assertConditionTrue(locationExtension.getLocationExtensionStateErrorText().contains(errorMessages[2]));
        assertConditionTrue(locationExtension.getLocationExtensionZipcodeErrorText().contains(errorMessages[3]));
    }


    @Test
    public void testBoundaryLevelError() throws Exception {
        String maxLength80 = "abcdefghijklmnopqrstuvwxyzzyxwvutsrqponmlkjihgfedcbaabcdefghijklmnopqrstuvwxyzzyxwv";
        String zipMaxLength10 = "1234567890";
        String phone = "81890090019999";
        String errorMessages[] = {"Address is too long", "City is too long", "State is too long", "Zip code is too long", "Phone number is too long"};


        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();

        MyAgencyPage agencyPage = login();
        AdExtensionLocationExtension locationExtension = agencyPage.navigateToAdExtensionsLocationTab(agencyId, account.getAccountId(), campaignId);
        locationExtension.locationExtension();
        locationExtension.click_EXTENSION_BUTTON();
        locationExtension.click_New_Location_Extension_BUTTON();
        locationExtension.enterAddressLineOne(maxLength80);
        locationExtension.enterAddressLineTwo(maxLength80);
        locationExtension.enterCity(maxLength80);
        locationExtension.enterState(maxLength80);
        locationExtension.enterZip(zipMaxLength10);
        locationExtension.enterCompanyName(maxLength80);
        locationExtension.enterPhoneNumber(phone);
        locationExtension.saveButton();
        assertConditionTrue(locationExtension.getAddress1TooLongError().contains(errorMessages[0]));
        assertConditionTrue(locationExtension.getAddress2TooLongError().contains(errorMessages[0]));
        assertConditionTrue(locationExtension.getCityTooLongError().contains(errorMessages[1]));
        assertConditionTrue(locationExtension.getStateTooLongError().contains(errorMessages[2]));
        assertConditionTrue(locationExtension.getZipcodeTooLongError().contains(errorMessages[3]));
        assertConditionTrue(locationExtension.getPhoneTooLongError().contains(errorMessages[4]));

    }

    @Test
    public void testInvalidZipCodeError() throws Exception {
        String location[] = {"611 N Brand BLVD", "Glendale", "California", "abcde", "8182222222"};
        String company = "YP";
        String errorMessage = "Invalid zip code";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();

        MyAgencyPage agencyPage = login();
        AdExtensionLocationExtension locationExtension = agencyPage.navigateToAdExtensionsLocationTab(agencyId, account.getAccountId(), campaignId);
        locationExtension.locationExtension();
        locationExtension.click_EXTENSION_BUTTON();
        locationExtension.click_New_Location_Extension_BUTTON();
        locationExtension.enterAddressLineOne(location[0]);
        locationExtension.enterCity(location[1]);
        locationExtension.enterState(location[2]);
        locationExtension.enterZip(location[3]);
        locationExtension.enterCompanyName(company);
        locationExtension.enterPhoneNumber(location[4]);
        locationExtension.saveButton();
        assertConditionTrue(locationExtension.getInvalidZipCodeError().contains(errorMessage));
    }

    @Test
    public void testImportLocationAdExtension() throws Exception {
        if (System.getProperty("os.name").equalsIgnoreCase("mac os x") && System.getProperty("selenium.driver").equalsIgnoreCase("firefox")) {

            Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = createBudget(account.getAccountId(), 100, "standard");
            Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
            String imagePath = System.getProperty("user.dir") + "/src/test/resources/data/location.xlsx";

            MyAgencyPage agencyPage = login();
            AdExtensionLocationExtension locationExtension = agencyPage.navigateToAdExtensionsLocationTab(agencyId, account.getAccountId(), campaign.getCampaignId());
            locationExtension.locationExtension();
            locationExtension.importExtension(imagePath);
        }

    }
}