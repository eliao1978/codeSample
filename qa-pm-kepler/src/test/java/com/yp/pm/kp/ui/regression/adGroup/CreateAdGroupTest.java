package com.yp.pm.kp.ui.regression.adGroup;

import com.yp.enums.selenium.LocatorTypeEnum;
import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.enums.model.AdGroupStatusEnum;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.AdGroup;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.ui.AdGroupPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import com.yp.pm.kp.ui.UIMapper;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreateAdGroupTest extends BaseUITest {

    @Test
    public void testFormSubmission() throws Exception {
        String adGroupName = "AG_" + System.currentTimeMillis();
        Map<String, Object> data = getTestData();
        long accountId = ((Account) data.get("account")).getAccountId();
        long campaignId = ((Campaign) data.get("campaign")).getCampaignId();

        MyAgencyPage agencyPage = login();
        AdGroupPage adGroupPage = agencyPage.navigateToAdGroupTab(agencyId, accountId, campaignId);
        adGroupPage.clickCreateAdGroupButton();
        adGroupPage.enterAdGroupName(adGroupName);
        adGroupPage.enterHeadline("test headline");
        adGroupPage.enterDescription1("test description 1");
        adGroupPage.enterDescription2("test description 2");
        adGroupPage.enterDisplayUrl("www.yp.com");
        adGroupPage.enterDestinationUrl("www.yp.com");
        adGroupPage.enterKeyword("test1\ntest2\ntest3");
        adGroupPage.enterAdGroupBid(1.00);
        adGroupPage.clickSubmitButton();

        // assert ad group name
        assertConditionTrue(adGroupPage.getAdGroupTableText(2, 3).contains(adGroupName));

        // assert other as group details
        AdGroup adGroup = getAdGroupByName(accountId, adGroupName);
        assertObjectNotNull(adGroup.getAdGroupId());
        assertObjectEqual(adGroup.getAdGroupName(), adGroupName);
        assertObjectEqual(adGroup.getAccountId(), accountId);
        assertObjectEqual(adGroup.getCampaignId(), campaignId);
        assertObjectEqual(adGroup.getMaxCpc().toDollar(), 1.00);
        assertConditionTrue(adGroup.getMobileBidModifier() == null);
        assertConditionTrue(adGroup.getAdGroupStatus().equals(AdGroupStatusEnum.ACTIVE));
    }

    @Test
    public void testCancelFormSubmission() throws Exception {
        String adGroupName = "AG_" + System.currentTimeMillis();
        Map<String, Object> data = getTestData();
        long accountId = ((Account) data.get("account")).getAccountId();
        long campaignId = ((Campaign) data.get("campaign")).getCampaignId();

        MyAgencyPage agencyPage = login();
        AdGroupPage adGroupPage = agencyPage.navigateToAdGroupTab(agencyId, accountId, campaignId);
        adGroupPage.clickCreateAdGroupButton();
        adGroupPage.enterAdGroupName(adGroupName);
        adGroupPage.enterHeadline("test headline");
        adGroupPage.enterDescription1("test description 1");
        adGroupPage.enterDescription2("test description 2");
        adGroupPage.enterDisplayUrl("www.yp.com");
        adGroupPage.enterDestinationUrl("www.yp.com");
        adGroupPage.enterKeyword("test1\ntest2\ntest3");
        adGroupPage.enterAdGroupBid(1.00);
        adGroupPage.clickCancelButton();

        // assert ad group doesn't exist
        assertConditionTrue(getAdGroupByName(accountId, adGroupName) == null);
    }

    @Test
    public void testAdGroupNameLimit() throws Exception {
        String adGroupName = "abcdefghijklmnopqrstuvwxyzzyxwvutsrqponmlkjihgfedcbaabcdefghijklmnopqrstuvwxyzzyxwvutsrqponmlkjihgfedcbaabcdefghijklmnopqrstuvwxyzzyxwvutsrqponmlkjihgfedcbaabcdefghijklmnopqrstuvwxyzzyxwvutsrqponmlkjihgfedcbaabcdefghijklmnopqrstuvwxyzzyxwvutsrqponmlkjihgfedcbaabcdefghijklmnopqrstuvwxyzzyxwvutsrqponmlkjihgfedcbaabcdefghijklmnopqrstuvwxyzzyxwvutsrqponmlkjihgfedcbaabcdefghijklmnopqrstuvwxyzzyxwvutsrqponmlkjihgfedcbaabcdefghijklmnopqrstuvwxyzzyxwvutsrqponmlkjihgfedcba";
        Map<String, Object> data = getTestData();
        long accountId = ((Account) data.get("account")).getAccountId();
        long campaignId = ((Campaign) data.get("campaign")).getCampaignId();

        MyAgencyPage agencyPage = login();
        AdGroupPage adGroupPage = agencyPage.navigateToAdGroupTab(agencyId, accountId, campaignId);
        adGroupPage.clickCreateAdGroupButton();
        adGroupPage.enterAdGroupName(adGroupName);
        adGroupPage.enterAdGroupBid(1.00);
        adGroupPage.clickSubmitButton();

        assertConditionTrue(adGroupPage.getAdGroupNameTooLongErrorMessage().contains("Ad group name is too long."));
    }

    @Test
    public void testDuplicateNameWithActiveStatus() throws Exception {
        Map<String, Object> data = getTestData();
        long accountId = ((Account) data.get("account")).getAccountId();
        long campaignId = ((Campaign) data.get("campaign")).getCampaignId();
        AdGroup adGroup1 = createAdGroupOnly(accountId, campaignId);
        String adGroupName1 = adGroup1.getAdGroupName();

        MyAgencyPage agencyPage = login();
        AdGroupPage adGroupPage = agencyPage.navigateToAdGroupTab(agencyId, accountId, campaignId);

        // add ad group 2 with duplicate name
        adGroupPage.clickCreateAdGroupButton();
        adGroupPage.enterAdGroupName(adGroupName1);
        adGroupPage.enterAdGroupBid(1.00);
        adGroupPage.clickSubmitButton();
        assertConditionTrue(adGroupPage.getAdGroupNameDuplicateErrorMessage().contains("An ad group with this name already exists in this campaign."));
    }

    @Test
    public void testDuplicateNameWithPausedStatus() throws Exception {
        Map<String, Object> data = getTestData();
        long accountId = ((Account) data.get("account")).getAccountId();
        long campaignId = ((Campaign) data.get("campaign")).getCampaignId();
        AdGroup adGroup1 = createAdGroupOnly(accountId, campaignId);
        String adGroupName1 = adGroup1.getAdGroupName();

        // set ad group 1 to be paused
        List<AdGroup> adGroupList = new ArrayList<>();
        adGroup1.setAdGroupStatus(AdGroupStatusEnum.PAUSED);
        adGroupList.add(adGroup1);
        updateAdGroups(adGroupList).get(0);

        MyAgencyPage agencyPage = login();
        AdGroupPage adGroupPage = agencyPage.navigateToAdGroupTab(agencyId, accountId, campaignId);

        // add ad group 2
        adGroupPage.clickCreateAdGroupButton();
        adGroupPage.enterAdGroupName(adGroupName1);
        adGroupPage.enterAdGroupBid(1.00);
        adGroupPage.clickSubmitButton();
        assertConditionTrue(adGroupPage.getAdGroupNameDuplicateErrorMessage().contains("An ad group with this name already exists in this campaign."));
    }

    @Test
    public void testDuplicateNameWithDeletedStatus() throws Exception {
        Map<String, Object> data = getTestData();
        long accountId = ((Account) data.get("account")).getAccountId();
        long campaignId = ((Campaign) data.get("campaign")).getCampaignId();
        AdGroup adGroup1 = createAdGroupOnly(accountId, campaignId);
        String adGroupName1 = adGroup1.getAdGroupName();

        // set ad group 1 to be deleted
        List<Long> adGroupList = new ArrayList<>();
        adGroupList.add(adGroup1.getAdGroupId());
        deleteAdGroups(accountId, adGroupList);

        MyAgencyPage agencyPage = login();
        AdGroupPage adGroupPage = agencyPage.navigateToAdGroupTab(agencyId, accountId, campaignId);

        // add ad group 2
        adGroupPage.clickCreateAdGroupButton();
        adGroupPage.enterAdGroupName(adGroupName1);
        adGroupPage.enterAdGroupBid(1.00);
        adGroupPage.clickSubmitButton();

        // assert ad group name
        assertConditionTrue(adGroupPage.getAdGroupTableText(2, 3).contains(adGroupName1));

        // assert other as group details
        AdGroup adGroup = getAdGroupByName(accountId, adGroupName1);
        assertObjectNotNull(adGroup.getAdGroupId());
        assertObjectEqual(adGroup.getAdGroupName(), adGroupName1);
        assertObjectEqual(adGroup.getAccountId(), accountId);
        assertObjectEqual(adGroup.getCampaignId(), campaignId);
        assertObjectEqual(adGroup.getMaxCpc().toDollar(), 1.00);
        assertConditionTrue(adGroup.getMobileBidModifier() == null);
        assertConditionTrue(adGroup.getAdGroupStatus().equals(AdGroupStatusEnum.ACTIVE));
    }

    @Test
    public void testMissingBidAmount() throws Exception {
        String adGroupName = "AG_" + System.currentTimeMillis();
        Map<String, Object> data = getTestData();
        long accountId = ((Account) data.get("account")).getAccountId();
        long campaignId = ((Campaign) data.get("campaign")).getCampaignId();

        MyAgencyPage agencyPage = login();
        AdGroupPage adGroupPage = agencyPage.navigateToAdGroupTab(agencyId, accountId, campaignId);
        adGroupPage.clickCreateAdGroupButton();
        adGroupPage.enterAdGroupName(adGroupName);
        adGroupPage.clickSubmitButton();

        assertConditionTrue(adGroupPage.getBidRequiredErrorMessage().contains("Bid is required or invalid."));
    }

    @Test
    public void testBidAmountOverMax() throws Exception {
        String adGroupName = "AG_" + System.currentTimeMillis();
        Map<String, Object> data = getTestData();
        long accountId = ((Account) data.get("account")).getAccountId();
        long campaignId = ((Campaign) data.get("campaign")).getCampaignId();

        MyAgencyPage agencyPage = login();
        AdGroupPage adGroupPage = agencyPage.navigateToAdGroupTab(agencyId, accountId, campaignId);
        adGroupPage.clickCreateAdGroupButton();
        adGroupPage.enterAdGroupName(adGroupName);
        adGroupPage.enterAdGroupBid(9999.00);
        adGroupPage.clickSubmitButton();

        assertConditionTrue(adGroupPage.getBidTooLargeErrorMessage().contains("Bid is too large."));
    }

    @Test
    public void testBidAmountBelowMin() throws Exception {
        String adGroupName = "AG_" + System.currentTimeMillis();
        Map<String, Object> data = getTestData();
        long accountId = ((Account) data.get("account")).getAccountId();
        long campaignId = ((Campaign) data.get("campaign")).getCampaignId();

        MyAgencyPage agencyPage = login();
        AdGroupPage adGroupPage = agencyPage.navigateToAdGroupTab(agencyId, accountId, campaignId);
        adGroupPage.clickCreateAdGroupButton();
        adGroupPage.enterAdGroupName(adGroupName);
        adGroupPage.enterAdGroupBid(0.001);
        adGroupPage.clickSubmitButton();
        assertConditionTrue(adGroupPage.getAdGroupBidTooLowError().contains("Bid is too low."));
    }

    @Test
    public void testAdTextLengthLimit() throws Exception {
        String adGroupText[] = {"Maximum Length is 25 Characters",
                "Maximum Length is Thirty Five Characters",
                "MaximumLengthisThirtyFiveCharacters.com"};
        String headlineError = "Headline is too long.";
        String descriptionOneError = "Description line 1 is too long.";
        String descriptionTwoError = "Description line 2 is too long.";
        String displayUrlError = "Display URL is too long.";
        String adGroupName = "AG_" + System.currentTimeMillis();
        Map<String, Object> data = getTestData();
        long accountId = ((Account) data.get("account")).getAccountId();
        long campaignId = ((Campaign) data.get("campaign")).getCampaignId();

        MyAgencyPage agencyPage = login();
        AdGroupPage adGroupPage = agencyPage.navigateToAdGroupTab(agencyId, accountId, campaignId);
        adGroupPage.clickCreateAdGroupButton();
        adGroupPage.enterAdGroupName(adGroupName);
        adGroupPage.enterHeadline(adGroupText[0]);
        adGroupPage.enterDescription1(adGroupText[1]);
        adGroupPage.enterDescription2(adGroupText[1]);
        adGroupPage.enterDisplayUrl(adGroupText[2]);
        adGroupPage.enterDestinationUrl("test.com");
        adGroupPage.enterKeyword("test1\ntest2\ntest3");
        adGroupPage.enterAdGroupBid(1.00);
        adGroupPage.sleep(1000);
        adGroupPage.clickSubmitButton();
        adGroupPage.sleep(2000);

        assertConditionTrue(adGroupPage.getTextFromElement(UIMapper.HEADLINE_TOO_LONG_AD_GROUP_ERROR_MESSAGE, LocatorTypeEnum.CSS).contains(headlineError));
        assertConditionTrue(adGroupPage.getTextFromElement(UIMapper.DESCRIPTION_ONE_TOO_LONG_AD_GROUP_ERROR_MESSAGE).contains(descriptionOneError));
        assertConditionTrue(adGroupPage.getTextFromElement(UIMapper.DESCRIPTION_TWO_TOO_LONGAD_GROUP_ERROR_MESSAGE).contains(descriptionTwoError));
        assertConditionTrue(adGroupPage.getTextFromElement(UIMapper.DISPLAY_URL_TOO_LONG_AD_GROUP_ERROR_MESSAGE).contains(displayUrlError));
    }

    @Test
    public void testMissingCampaignName() throws Exception {
        String adGroupName = "AG_" + System.currentTimeMillis();
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");

        MyAgencyPage agencyPage = login();
        AdGroupPage adGroupPage = agencyPage.navigateToAdGroupTabCampaignErrorMessageTest(agencyId, account.getAccountId());
        adGroupPage.clickCreateAdGroupButton();
        adGroupPage.enterAdGroupName(adGroupName);
        adGroupPage.enterHeadline("test headline");
        adGroupPage.enterDescription1("test description 1");
        adGroupPage.enterDescription2("test description 2");
        adGroupPage.enterDisplayUrl("www.yp.com");
        adGroupPage.enterDestinationUrl("www.yp.com");
        adGroupPage.enterKeyword("test1\ntest2\ntest3");
        adGroupPage.enterAdGroupBid(1.00);
        adGroupPage.clickSubmitButton();
        assertConditionTrue(adGroupPage.getAdGroupCampaignRequiredError().contains("Campaign must be selected."));
    }

    private Map<String, Object> getTestData() throws Exception {
        Map<String, Object> testData = new HashMap<>();
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        testData.put("account", account);
        testData.put("campaign", campaign);

        return testData;
    }
}