package com.yp.pm.kp.ui.regression.campaign;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.ui.CampaignPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")

public class CampaignAdvanceSearchTest extends BaseUITest {

    private static Map<String, Object> testData;
    String text1 = "Georgia";
    String text2 = "California";

    @Test
    public void testAdvanceSearchTargetLocation() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();

        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, accountId);
        campaignPage.loadingStatus();
        campaignPage.clickCreateCampaignButton();
        campaignPage.clickAdvanceSearch(text1);
        campaignPage.clickAdvanceSearchAddAll();
        campaignPage.clickAdvanceSearchTargetRemove(2);
        String text = campaignPage.getSearchResultTextStatus(1);
        assertConditionTrue(text.contains("Exclude"));
        assertConditionTrue(text.contains("Add"));
        campaignPage.clickAdvanceSearch(text2);
        campaignPage.clickAdvanceSearchExcludeAll();
        campaignPage.clickAdvanceSearchExcludedRemove();
        assertConditionTrue(campaignPage.getExcludedLocationText().isEmpty());
    }

    @Test
    public void testAdvanceSearchRadiusTargeting() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        String location = "Georgia";
        String location1 = "California";

        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, accountId);
        campaignPage.loadingStatus();
        campaignPage.clickCreateCampaignButton();
        campaignPage.clickAdvanceSearch(text1);
        campaignPage.clickRadiusTargetingTab();
        campaignPage.enterRadiusTargettingLocationText(location);
        campaignPage.clickRadiusTargetingSEARCH_BUTTON();
        campaignPage.clickAdvanceSearchAddAll();
        campaignPage.clickAdvanceSearchTargetRemove(2);
        String text = campaignPage.getSearchResultTextStatus(1);
        assertConditionTrue(text.contains("Exclude"));
        assertConditionTrue(text.contains("Add"));
        campaignPage.enterRadiusTargettingLocationText(location1);
        campaignPage.clickAdvanceSearchExcludeAll();
        campaignPage.clickAdvanceSearchExcludedRemove();
        assertConditionTrue(campaignPage.getExcludedLocationText().isEmpty());
    }

    @Test
    public void testAdvanceSearchBulkLocations() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        String location = "California";
        String location1 = "Texas";

        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, accountId);
        campaignPage.loadingStatus();
        campaignPage.clickCreateCampaignButton();
        campaignPage.clickAdvanceSearch(text1);
        campaignPage.clickBulkLocationTab();
        campaignPage.enterBulkLocations(location);
        campaignPage.clickBulkLocationSearchButton();
        campaignPage.clickAdvanceSearchAddAll();
        campaignPage.clickAdvanceSearchTargetRemove(2);
        String text = campaignPage.getSearchResultTextStatus(1);
        assertConditionTrue(text.contains("Exclude"));
        assertConditionTrue(text.contains("Add"));
        campaignPage.enterBulkLocations(location1);
        campaignPage.clickAdvanceSearchExcludeAll();
        campaignPage.clickAdvanceSearchExcludedRemove();
        assertConditionTrue(campaignPage.getExcludedLocationText().isEmpty());

    }

    @Test
    public void testRadiusTargetingMissingLocationError() throws Exception {
        String errorMessage = "Please enter a location (place name or address).";
        long accountId = ((Account) getTestData().get("account")).getAccountId();

        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, accountId);
        campaignPage.loadingStatus();
        campaignPage.clickCreateCampaignButton();
        campaignPage.clickAdvanceSearch(text1);
        campaignPage.clickRadiusTargetingTab();
        campaignPage.clickRadiusTargetingSEARCH_BUTTON();

        assertConditionTrue(campaignPage.getRadiusTargetingNoInputSearchError().contains(errorMessage));
    }


    @Test
    public void testRadiusTargetingInvalidSearchError() throws Exception {
        String errorMessage = "We weren't able to locate this address. Please try entering a different address";
        String location = "asdfjadslkfjasdfjadslkfj";
        long accountId = ((Account) getTestData().get("account")).getAccountId();

        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, accountId);
        campaignPage.loadingStatus();
        campaignPage.clickCreateCampaignButton();
        campaignPage.clickAdvanceSearch(text1);
        campaignPage.clickRadiusTargetingTab();
        campaignPage.enterRadiusTargettingLocationText(location);
        campaignPage.clickRadiusTargetingSEARCH_BUTTON();
        assertConditionTrue(campaignPage.getRadiusTargetingInvalidInputSearchError().contains(errorMessage));
    }

    @Test
    public void testRadiusTargetingInvalidAddressError() throws Exception {
        String errorMessage = "Please enter a valid radius.";
        String location = "Burbank";
        long accountId = ((Account) getTestData().get("account")).getAccountId();

        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, accountId);
        campaignPage.loadingStatus();
        campaignPage.clickCreateCampaignButton();
        campaignPage.clickAdvanceSearch(text1);
        campaignPage.clickRadiusTargetingTab();
        campaignPage.enterRadiusTargettingLocationText(location);
        campaignPage.clearMiles();
        campaignPage.clickRadiusTargetingSEARCH_BUTTON();
        assertConditionTrue(campaignPage.getRadiusTargetingInvalidInputMilesError().contains(errorMessage));
    }


    @Test
    public void testRadiusTargetingMilesMaxRangeError() throws Exception {
        String errorMessage = "Radius must be between 1 and 500 miles.";
        String location = "Burbank";
        String miles = "501";

        long accountId = ((Account) getTestData().get("account")).getAccountId();

        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, accountId);
        campaignPage.loadingStatus();
        campaignPage.clickCreateCampaignButton();
        campaignPage.clickAdvanceSearch(text1);
        campaignPage.clickRadiusTargetingTab();
        campaignPage.enterRadiusTargettingLocationText(location);
        campaignPage.clearMiles();
        campaignPage.enterRadiusTargetingMilesText(miles);
        campaignPage.clickRadiusTargetingSEARCH_BUTTON();
        assertConditionTrue(campaignPage.getRadiusTargetingMilesMaximumRangeError().contains(errorMessage));
    }

    @Test
    public void testRadiusTargetingMilesMinRangeError() throws Exception {
        String errorMessage = "Radius must be between 1 and 500 miles.";
        String location = "Burbank";
        String miles = "0.9";

        long accountId = ((Account) getTestData().get("account")).getAccountId();

        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, accountId);
        campaignPage.loadingStatus();
        campaignPage.clickCreateCampaignButton();
        campaignPage.clickAdvanceSearch(text1);
        campaignPage.clickRadiusTargetingTab();
        campaignPage.enterRadiusTargettingLocationText(location);
        campaignPage.clearMiles();
        campaignPage.enterRadiusTargetingMilesText(miles);
        campaignPage.clickRadiusTargetingSEARCH_BUTTON();
        assertConditionTrue(campaignPage.getRadiusTargetingMilesMinimumRangeError().contains(errorMessage));
    }


    @Test
    public void testBulkLocationMissingLocationError() throws Exception {
        String errorMessage = "Please enter a location (state, DMA, city, or postal code).";

        long accountId = ((Account) getTestData().get("account")).getAccountId();

        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, accountId);
        campaignPage.loadingStatus();
        campaignPage.clickCreateCampaignButton();
        campaignPage.clickAdvanceSearch(text1);
        campaignPage.clickBulkLocationTab();
        campaignPage.clickBulkLocationSearchButton();
        assertConditionTrue(campaignPage.getBulkLocationNoInputError().contains(errorMessage));
    }

    @Test
    public void testBulkLocationInvalidLocationError() throws Exception {
        String errorMessage = "1 location(s) weren't recognized and are listed above. You can update and click Search to re-submit these locations.";
        String invalidBulkLocation = "djsflkasdjfl;kdasjfl;kdjs";

        long accountId = ((Account) getTestData().get("account")).getAccountId();

        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, accountId);
        campaignPage.loadingStatus();
        campaignPage.clickCreateCampaignButton();
        campaignPage.clickAdvanceSearch(text1);
        campaignPage.clickBulkLocationTab();
        campaignPage.enterBulkLocations(invalidBulkLocation);
        campaignPage.clickBulkLocationSearchButton();
        assertConditionTrue(campaignPage.getBulkLocationInvalidInputError().contains(errorMessage));
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
