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

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class ViewLocationAdExtensionTableTest extends BaseUITest {

    @Test
    public void testLocationAdExtensionTableHeader() throws Exception {
        String locationExtensionLabels[] = {"Company Name", "Location", "Status", "Campaign", "Clicks", "Impr.", "CTR", "Avg. CPC", "Cost", "Avg. Pos."};
        String address[] = {"611 N Brand Ave", "Glendale", "California", "91203", "8008063906"};
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
        locationExtension.enterAddressLineOne(address[0]);
        locationExtension.enterCity(address[1]);
        locationExtension.enterState(address[2]);
        locationExtension.enterZip(address[3]);
        locationExtension.enterPhoneNumber(address[4]);
        locationExtension.enterCompanyName(company);
        locationExtension.saveButton();
        locationExtension.checkForAddressError();
        for (String locationExtensionLabel : locationExtensionLabels) {
            if (!locationExtension.checkLocationHeader(locationExtensionLabel)) {
                throw new NullPointerException("Element does not exist [" + locationExtensionLabel + "]");
            }
        }
    }


    @Test
    public void testSortLocationAdExtensionTable() throws Exception {
        String AddressLine1[] = {"1537 Alcazar St", "611 N Brand BLVD", "225 W Broadway#600"};
        String city[] = {"Los Angeles", "Glendale", "Glendale"};
        String state[] = {"California", "California", "California"};
        String zip[] = {"90033", "91203", "91204"};
        String company[] = {"LA County Public Work", "YP", "SSN Office"};
        String phone[] = {"6264581707", "8182222222", "8007721213"};
        String companyLabel = "Company Name";
        int size = AddressLine1.length;

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();

        MyAgencyPage agencyPage = login();
        AdExtensionLocationExtension locationExtension = agencyPage.navigateToAdExtensionsLocationTab(agencyId, account.getAccountId(), campaignId);
        locationExtension.locationExtension();
        locationExtension.click_EXTENSION_BUTTON();
        for (int locationAddress = 0; locationAddress < size; locationAddress++) {
            locationExtension.click_New_Location_Extension_BUTTON();
            locationExtension.enterAddressLineOne(AddressLine1[locationAddress]);
            locationExtension.enterCity(city[locationAddress]);
            locationExtension.enterState(state[locationAddress]);
            locationExtension.enterZip(zip[locationAddress]);
            locationExtension.enterCompanyName(company[locationAddress]);
            locationExtension.enterPhoneNumber(phone[locationAddress]);
            locationExtension.saveButton();
            locationExtension.checkForAddressError();
        }
        Arrays.sort(company);
        locationExtension.clickTableHeader(companyLabel);
        assertConditionTrue(locationExtension.getLocationTableText(2, 2).equals(company[2]));
        assertConditionTrue(locationExtension.getLocationTableText(3, 2).equals(company[1]));
        assertConditionTrue(locationExtension.getLocationTableText(4, 2).equals(company[0]));
        locationExtension.clickTableHeader(companyLabel);
        assertConditionTrue(locationExtension.getLocationTableText(2, 2).equals(company[0]));
        assertConditionTrue(locationExtension.getLocationTableText(3, 2).equals(company[1]));
        assertConditionTrue(locationExtension.getLocationTableText(4, 2).equals(company[2]));
    }


    @Test
    public void testLocationAdExtensionStaticFilterTest() throws Exception {
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
        locationExtension.clickStaticFilterButton();
        locationExtension.staticFilterSelectAllAdExtension();
        assertConditionTrue(locationExtension.getLocationTableText(2, 2).contains(location[4]));
        locationExtension.clickStaticFilterButton();
        locationExtension.staticFilterSelectAllButDeletedAdExtension();
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