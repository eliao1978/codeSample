package com.yp.pm.kp.ui.regression.campaign;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.enums.model.CampaignStatusEnum;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
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
public class CreateCampaignTest extends BaseUITest {

    private static Map<String, Object> testData;

    @Test
    public void testCreateCampaign() throws Exception {
        String campaignName = "campaign_" + System.currentTimeMillis();

        long accountId = ((Account) getTestData().get("account")).getAccountId();

        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, accountId);
        campaignPage.clickCreateCampaignButton();
        campaignPage.enterCampaignName(campaignName);
        campaignPage.selectBudget();
        campaignPage.submit();
        assertConditionTrue(campaignPage.getCampaignCreatedText().contains(campaignName));

        Campaign campaign = getCampaignByName(accountId, campaignName);
        assertObjectNotNull(campaign.getCampaignId());
        assertObjectEqual(campaign.getCampaignName(), campaignName);
        assertObjectEqual(campaign.getAccountId(), accountId);
        assertObjectEqual(campaign.getCampaignStatus().toString(), CampaignStatusEnum.ACTIVE.toString());
    }

    @Test
    public void testCancelCreateCampaign() throws Exception {
        String campaignName = "campaign_" + System.currentTimeMillis();

        long accountId = ((Account) getTestData().get("account")).getAccountId();

        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, accountId);
        campaignPage.clickCreateCampaignButton();
        campaignPage.enterCampaignName(campaignName);
        campaignPage.cancel();
        assertConditionTrue(getCampaignByName(accountId, campaignName) == null);
    }

    @Test
    public void testMissingCampaignNameError() throws Exception {
        String campaignErrorMessage = "Campaign name is required.";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        createBudget(account.getAccountId(),100, "standard");

        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, account.getAccountId());
        campaignPage.clickCreateCampaignButton();
        campaignPage.submit();
        assertConditionTrue(campaignPage.getCampaignNameErrorMessage().contains(campaignErrorMessage));
    }

    @Test
    //Ticket:KPL-1350
    public void testCampaignBoundaryValuesError() throws Exception {
        String campaignName = "Campaign Name Length should not be more than one hundred and twenty eight characters Campaign Name Length should not be more than one hundred and twenty eight characters" + System.currentTimeMillis();
        String errorMessage = "Campaign name is too long.";

        long accountId = ((Account) getTestData().get("account")).getAccountId();

        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, accountId);
        campaignPage.clickCreateCampaignButton();
        campaignPage.enterCampaignName(campaignName);
        campaignPage.selectBudget();
        campaignPage.submit();
        assertConditionTrue(campaignPage.getCampaignNameTooLongErrorMessage().contains(errorMessage));
    }

    @Test
    public void testDuplicateCampaignError() throws Exception {
        String campaignName = "campaign_" + System.currentTimeMillis();
        String errorMessage = "This is a duplicate campaign.";

        long accountId = ((Account) getTestData().get("account")).getAccountId();

        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, accountId);
        campaignPage.clickCreateCampaignButton();
        campaignPage.enterCampaignName(campaignName);
        campaignPage.selectBudget();
        campaignPage.submit();
        agencyPage.navigateToCampaignPage(agencyId, accountId);
        campaignPage.clickCreateCampaignButton();
        campaignPage.enterCampaignName(campaignName);
        campaignPage.selectBudget();
        campaignPage.submit();
        assertConditionTrue(campaignPage.getDuplicateCampaignErrorMessage().contains(errorMessage));
    }

    @Test
    public void testMissingLocationError() throws Exception {
        String campaignName = "campaign_" + System.currentTimeMillis();
        String errorMessage = "Location is required.";

        long accountId = ((Account) getTestData().get("account")).getAccountId();

        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, accountId);
        campaignPage.clickCreateCampaignButton();
        campaignPage.enterCampaignName(campaignName);
        campaignPage.selectBudget();
        campaignPage.clickRemoveLocation();
        campaignPage.submit();
        assertConditionTrue(campaignPage.getLocationRequiredError().contains(errorMessage));
    }

    @Test
    public void testMissingSearchLocationError() throws Exception {
        String campaignName = "campaign_" + System.currentTimeMillis();
        String errorMessage = "Please enter a location (place name or address).";

        long accountId = ((Account) getTestData().get("account")).getAccountId();

        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, accountId);
        campaignPage.clickCreateCampaignButton();
        campaignPage.enterCampaignName(campaignName);
        campaignPage.selectBudget();
        campaignPage.clickRemoveLocation();
        campaignPage.clickLocationSearchButton();
        campaignPage.submit();
        assertConditionTrue(campaignPage.getMissingSearchLocation().contains(errorMessage));
    }

    @Test
    public void testPastEndDateError() throws Exception {
        String campaignName = "campaign_" + System.currentTimeMillis();
        String errorMessageStartDate = "Start date and end date are not in chronological order.";
        String errorMessageEndDate = "End date cannot be set to the past.";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        createBudget(account.getAccountId(),100, "standard");

        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, account.getAccountId());
        campaignPage.clickCreateCampaignButton();
        campaignPage.enterCampaignName(campaignName);
        campaignPage.selectBudget();
        campaignPage.clickEndDate();
        campaignPage.clickCalendarBackButton();
        campaignPage.clickDate();
        campaignPage.submit();
        assertConditionTrue(campaignPage.getStartDateErrorMessage().contains(errorMessageStartDate));
        assertConditionTrue(campaignPage.getEndDateErrorMessage().contains(errorMessageEndDate));
    }

    @Test
    public void testValidateLocationAdded() throws Exception {
        String campaignName = "campaign_" + System.currentTimeMillis();

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, account.getAccountId());
        campaignPage.clickCreateCampaignButton();
        campaignPage.enterCampaignName(campaignName);
        campaignPage.selectBudget();
        campaignPage.removeLocation();
        campaignPage.enterCityText("Los Angeles");
        campaignPage.submit();
        campaignPage.enterAdGroupName("test");
        campaignPage.enterAdBroupBid(1.00);
        campaignPage.adGroupsubmit();
        agencyPage.navigateToAdSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        campaignPage.selectCampaign();
        campaignPage.enterCityText("Los Angeles");
        assertObjectEqual(campaignPage.ValidateAddButton(), "Add");
        assertObjectEqual(campaignPage.validateAddedText(), "Added");
        campaignPage.clickSaveButton();

        Campaign campaigntest1 = getCampaignByName(account.getAccountId(), campaignName);
        assertObjectNotNull(campaigntest1.getCampaignId());
        assertObjectEqual(campaigntest1.getCampaignName(), campaignName);
        assertObjectEqual(campaigntest1.getAccountId(), account.getAccountId());
        assertObjectEqual(campaigntest1.getCampaignStatus().toString(), CampaignStatusEnum.ACTIVE.toString());
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