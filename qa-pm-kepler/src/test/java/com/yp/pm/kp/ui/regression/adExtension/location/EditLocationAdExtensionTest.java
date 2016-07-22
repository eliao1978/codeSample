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
public class EditLocationAdExtensionTest extends BaseUITest {


    @Test
    public void testEditLocationAdExtension() throws Exception {
        String address[] = {"611 N Brand Ave", "Glendale", "California", "91203", "8008063906"};
        String editedAddress[] = {"45 N Arroyo Pkwy", "Pasadena", "California", "91103", "8008063900"};
        String company = "YP";
        String editedCompany = "self";
        String active = "ACTIVE";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();

        MyAgencyPage agencyPage = login();
        AdExtensionLocationExtension locationExtension = agencyPage.navigateToAdExtensionsLocationTab(agencyId, account.getAccountId(), campaignId);
        locationExtension.locationExtension();
        locationExtension.click_EXTENSION_BUTTON();
        locationExtension.click_New_Location_Extension_BUTTON();
        locationExtension.enterAddressLineOne(address[0]);
        locationExtension.enterCity(address[1]);
        locationExtension.enterState(address[2]);
        locationExtension.enterZip(address[3]);
        locationExtension.enterCompanyName(company);
        locationExtension.enterPhoneNumber(address[4]);
        locationExtension.saveButton();
        locationExtension.click_Panel_Edit_Extension();
        locationExtension.click_Edit_Extension();
        locationExtension.enterAddressLineOne(editedAddress[0]);
        locationExtension.enterCity(editedAddress[1]);
        locationExtension.enterState(editedAddress[2]);
        locationExtension.enterZip(editedAddress[3]);
        locationExtension.enterCompanyName(editedCompany);
        locationExtension.enterPhoneNumber(editedAddress[4]);
        locationExtension.saveButton();
        assertConditionTrue(locationExtension.getLocationTableText(2, 2).contains(editedCompany));
        for (String editedLocationText : editedAddress) {
            assertConditionTrue(locationExtension.getLocationTableText(2, 3).contains(editedLocationText));
        }

        List<LocationAdExtension> newLocationExtension = getLocationAdExtensionByAccountId(account.getAccountId());
        assertConditionTrue(newLocationExtension.size() >= 1);
        for (LocationAdExtension newLocation : newLocationExtension) {
            if (newLocation.getAdExtensionStatus().toString().equalsIgnoreCase(active)) {
                assertObjectEqual(newLocation.getAccountId(), account.getAccountId());
                assertObjectEqual(newLocation.getAddressLine1(), editedAddress[0]);
                assertObjectEqual(newLocation.getCity(), editedAddress[1]);
                assertObjectEqual(newLocation.getState(), editedAddress[2]);
                assertObjectEqual(newLocation.getZip(), editedAddress[3]);
                assertObjectEqual(newLocation.getCompanyName(), editedCompany);
                assertObjectEqual(newLocation.getPhoneNumber(), editedAddress[4]);
            }
        }
    }

    @Test
    public void testLocationExtensionEditButtonModalText() throws Exception {
        String location[] = {"611 N Brand BLVD", "Glendale", "California", "91023", "YP", "8182222222"};
        String alertTextLineOne = "Before you edit this ad extension...";
        String alertTextLineTwo = "This extension may be used by more than one campaign.";
        String alertTextLineThree = "Changing this ad extension will remove the existing extension and create a new one. The old extensions's statistics will be visible in the total line for removed extensions.";

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
        locationExtension.enterCompanyName(location[4]);
        locationExtension.enterPhoneNumber(location[5]);
        locationExtension.saveButton();
        locationExtension.locationExtensionClickEditAlertText(2, 1);
        assertConditionTrue(locationExtension.validateEditButtonModalTextLine(1).contains(alertTextLineOne));
        assertConditionTrue(locationExtension.validateEditButtonModalTextLine(2).contains(alertTextLineTwo));
        assertConditionTrue(locationExtension.validateEditButtonModalTextLine(3).contains(alertTextLineThree));

    }
}
