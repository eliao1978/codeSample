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
public class DeleteLocationAdExtensionTest extends BaseUITest {

    @Test
    public void testDeleteLocationAdExtension() throws Exception {
        String location[] = {"611 N Brand BLVD", "Glendale", "California", "91023", "YP", "8182222222"};
        String deleted = "DELETED";

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
        locationExtension.clickDeleteButton(location[0]);
        assertConditionTrue(!locationExtension.getLocationTableText(2, 2).contains(location[4]));

        List<LocationAdExtension> newLocationExtension = getLocationAdExtensionByAccountId(account.getAccountId());
        for (LocationAdExtension deleteLocation : newLocationExtension) {
            if (deleteLocation.getAdExtensionStatus().toString().equalsIgnoreCase(deleted)) {
                assertObjectEqual(deleteLocation.getAddressLine1(), location[0]);
                assertObjectEqual(deleteLocation.getCity(), location[1]);
                assertObjectEqual(deleteLocation.getState(), location[2]);
                assertObjectEqual(deleteLocation.getZip(), location[3]);
                assertObjectEqual(deleteLocation.getPhoneNumber(), location[5]);
            }
        }

    }

    @Test
    public void testLocationExtensionDeleteByClickingRemoveButton() throws Exception {
        String location[] = {"611 N Brand BLVD", "Glendale", "California", "91023", "YP", "8182222222"};
        String deleted = "DELETED";

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
        locationExtension.locationExtensionDeleteByRemoveButton(2, 1);
        assertConditionTrue(!locationExtension.getLocationTableText(2, 2).contains(location[4]));

        List<LocationAdExtension> newLocationExtension = getLocationAdExtensionByAccountId(account.getAccountId());
        for (LocationAdExtension deleteLocation : newLocationExtension) {
            if (deleteLocation.getAdExtensionStatus().toString().equalsIgnoreCase(deleted)) {
                assertObjectEqual(deleteLocation.getAddressLine1(), location[0]);
                assertObjectEqual(deleteLocation.getCity(), location[1]);
                assertObjectEqual(deleteLocation.getState(), location[2]);
                assertObjectEqual(deleteLocation.getZip(), location[3]);
                assertObjectEqual(deleteLocation.getPhoneNumber(), location[5]);
            }
        }
    }
}
