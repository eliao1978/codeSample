package com.yp.pm.kp.ui.regression.ad;

import com.yp.enums.selenium.LocatorTypeEnum;
import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.enums.model.AdStatusEnum;
import com.yp.pm.kp.model.domain.*;
import com.yp.pm.kp.ui.AdPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import com.yp.pm.kp.ui.UIMapper;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreateAdTest extends BaseUITest {

    private String headline = "test headline";
    private String description1 = "test description 1";
    private String description2 = "test description 2";
    String[] description1WithPhone = {"8184582000life", "health8184582000", "insurance8184582000promotion", "818-458-2000", "8184582000,8189000000", "818*458*9090", "8184582000", "(81890909090)"};
    String[] description2WithPhone = {"8184582000life", "health8184582000", "insurance8184582000promotion", "818-458-2000", "8184582000,8189000000", "818*458*9090", "8184582000", "(81890909090)"};
    String descriptionErrorMessage = "Phone numbers are not allowed in description fields";
    String displayUrl = "www.yp.com";
    String destinationUrl = "yp1.com";

    @Test
    public void testCreateAd() throws Exception {
        String displayUrl = "www.yp.com";
        String destinationUrl = "www.yp.com";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());

        MyAgencyPage agencyPage = login();
        AdPage adPage = agencyPage.navigateToAdTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        adPage.clickCreateAdButton();
        adPage.selectAdGroup(adGroup.getAdGroupName());
        adPage.enterHeadline(headline);
        adPage.enterDescription1(description1);
        adPage.enterDescription2(description2);
        adPage.enterDisplayUrl(displayUrl);
        adPage.enterDestinationUrl(destinationUrl);
        adPage.submit();
        adPage.checkButtonNotVisible(UIMapper.SUBMIT_AD, LocatorTypeEnum.CSS);
        adPage.checkNoRecordsToLoad();

        // UI validation
        adPage.checkAdLoaded(headline);
        String adText = adPage.getAdTableText(2, 3);
        assertConditionTrue(adText.contains(headline));
        assertConditionTrue(adText.contains(description1));
        assertConditionTrue(adText.contains(description2));
        assertConditionTrue(adText.contains(displayUrl));
        assertConditionTrue(adText.contains(destinationUrl));

        adPage.hoverAd(headline);
        adPage.clickEditIcon(headline);

        // BE validation
        List<Ad> adList = getAdByAdGroupId(account.getAccountId(), adGroup.getAdGroupId());
        assertConditionTrue(adList.size() == 1);

        Ad ad = adList.get(0);
        assertObjectNotNull(ad.getAdId());
        assertObjectEqual(ad.getAccountId(), account.getAccountId());
        assertObjectEqual(ad.getAdGroupId(), adGroup.getAdGroupId());
        assertObjectEqual(ad.getHeadline(), headline);
        assertObjectEqual(ad.getDescription1(), description1);
        assertObjectEqual(ad.getDescription2(), description2);
        assertObjectEqual(ad.getDisplayUrl(), displayUrl);
        assertObjectEqual(ad.getDestinationUrl(), "http://" + destinationUrl);
        assertObjectEqual(ad.getAdStatus().toString(), AdStatusEnum.ACTIVE.toString());
    }

    @Test
    public void testUpdateDestinationDisplayUrlCom() throws Exception {
        String description1Url = "https://www.google.com?gws_rd=ssl";
        String description2Url = "www.foursquare.com?xrp=8.mMLOyc_IyM_ZnMLOz8jLyMbPz8nZnsLOycrPyMfZnJKPws7HzMvHyc_J2ZPCzsnOzM3N2ZLCyA._Uzg";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());

        MyAgencyPage agencyPage = login();
        AdPage adPage = agencyPage.navigateToAdTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        adPage.clickCreateAdButton();
        adPage.selectAdGroup(adGroup.getAdGroupName());
        adPage.enterHeadline(headline);
        adPage.enterDescription1(description1);
        adPage.enterDescription2(description2);
        adPage.enterDisplayUrl(description1Url);
        adPage.enterDestinationUrl(description2Url);
        adPage.submit();
        adPage.checkButtonNotVisible(UIMapper.SUBMIT_AD, LocatorTypeEnum.CSS);
        adPage.checkNoRecordsToLoad();
        List<Ad> adList = getAdByAdGroupId(account.getAccountId(), adGroup.getAdGroupId());
        assertConditionTrue(adList.size() == 1);
        Ad ad = adList.get(0);
        assertObjectNotNull(ad.getAdId());
        assertObjectEqual(ad.getAccountId(), account.getAccountId());
        assertObjectEqual(ad.getAdGroupId(), adGroup.getAdGroupId());
        assertObjectEqual(ad.getHeadline(), headline);
        assertObjectEqual(ad.getDescription1(), description1);
        assertObjectEqual(ad.getDescription2(), description2);
        assertObjectEqual(ad.getDisplayUrl(), description1Url);
        assertObjectEqual(ad.getDestinationUrl(), "http://" + description2Url);
        assertObjectEqual(ad.getAdStatus().toString(), AdStatusEnum.ACTIVE.toString());
    }

    @Test
    public void testUpdateDestinationDisplayUrlIn() throws Exception {
        String description1In = "https://www.google.in?gws_rd=ssl";
        String description2In = "www.foursquare.com?xrp=8.mMLOyc_IyM_ZnMLOz8jLyMbPz8nZnsLOycrPyMfZnJKPws7HzMvHyc_J2ZPCzsnOzM3N2ZLCyA._Uzg";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());

        MyAgencyPage agencyPage = login();
        AdPage adPage = agencyPage.navigateToAdTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        adPage.clickCreateAdButton();
        adPage.selectAdGroup(adGroup.getAdGroupName());
        adPage.enterHeadline(headline);
        adPage.enterDescription1(description1);
        adPage.enterDescription2(description2);
        adPage.enterDisplayUrl(description1In);
        adPage.enterDestinationUrl(description2In);
        adPage.submit();
        adPage.checkButtonNotVisible(UIMapper.SUBMIT_AD, LocatorTypeEnum.CSS);
        adPage.checkNoRecordsToLoad();

        List<Ad> adList = getAdByAdGroupId(account.getAccountId(), adGroup.getAdGroupId());
        assertConditionTrue(adList.size() == 1);
        Ad ad = adList.get(0);
        assertObjectNotNull(ad.getAdId());
        assertObjectEqual(ad.getAccountId(), account.getAccountId());
        assertObjectEqual(ad.getAdGroupId(), adGroup.getAdGroupId());
        assertObjectEqual(ad.getHeadline(), headline);
        assertObjectEqual(ad.getDescription1(), description1);
        assertObjectEqual(ad.getDescription2(), description2);
        assertObjectEqual(ad.getDisplayUrl(), description1In);
        assertObjectEqual(ad.getDestinationUrl(), "http://" + description2In);
        assertObjectEqual(ad.getAdStatus().toString(), AdStatusEnum.ACTIVE.toString());
    }

    @Test
    public void testUpdateDestinationDisplayUrlNet() throws Exception {
        String descriptionNet1 = "http://www.action.net/";
        String descriptionNet2 = "www.action.net/action-figures.php";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());

        MyAgencyPage agencyPage = login();
        AdPage adPage = agencyPage.navigateToAdTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        adPage.clickCreateAdButton();
        adPage.selectAdGroup(adGroup.getAdGroupName());
        adPage.enterHeadline(headline);
        adPage.enterDescription1(description1);
        adPage.enterDescription2(description2);
        adPage.enterDisplayUrl(descriptionNet1);
        adPage.enterDestinationUrl(descriptionNet2);
        adPage.submit();
        adPage.checkButtonNotVisible(UIMapper.SUBMIT_AD, LocatorTypeEnum.CSS);
        adPage.checkNoRecordsToLoad();

        List<Ad> adList = getAdByAdGroupId(account.getAccountId(), adGroup.getAdGroupId());
        assertConditionTrue(adList.size() == 1);
        Ad ad = adList.get(0);
        assertObjectNotNull(ad.getAdId());
        assertObjectEqual(ad.getAccountId(), account.getAccountId());
        assertObjectEqual(ad.getAdGroupId(), adGroup.getAdGroupId());
        assertObjectEqual(ad.getHeadline(), headline);
        assertObjectEqual(ad.getDescription1(), description1);
        assertObjectEqual(ad.getDescription2(), description2);
        assertObjectEqual(ad.getDisplayUrl(), descriptionNet1);
        assertObjectEqual(ad.getDestinationUrl(), "http://" + descriptionNet2);
        assertObjectEqual(ad.getAdStatus().toString(), AdStatusEnum.ACTIVE.toString());
    }

    @Test
    public void testUpdateDestinationDisplayUrlBiz() throws Exception {
        String descriptionBiz1 = "www.godaddy.com/tlds/biz.aspx";
        String descriptionBiz2 = "www.godaddy.com/tlds/biz.aspx";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());

        MyAgencyPage agencyPage = login();
        AdPage adPage = agencyPage.navigateToAdTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        adPage.clickCreateAdButton();
        adPage.selectAdGroup(adGroup.getAdGroupName());
        adPage.enterHeadline(headline);
        adPage.enterDescription1(description1);
        adPage.enterDescription2(description2);
        adPage.enterDisplayUrl(descriptionBiz1);
        adPage.enterDestinationUrl(descriptionBiz2);
        adPage.submit();
        adPage.checkButtonNotVisible(UIMapper.SUBMIT_AD, LocatorTypeEnum.CSS);
        adPage.checkNoRecordsToLoad();

        List<Ad> adList = getAdByAdGroupId(account.getAccountId(), adGroup.getAdGroupId());
        assertConditionTrue(adList.size() == 1);
        Ad ad = adList.get(0);
        assertObjectNotNull(ad.getAdId());
        assertObjectEqual(ad.getAccountId(), account.getAccountId());
        assertObjectEqual(ad.getAdGroupId(), adGroup.getAdGroupId());
        assertObjectEqual(ad.getHeadline(), headline);
        assertObjectEqual(ad.getDescription1(), description1);
        assertObjectEqual(ad.getDescription2(), description2);
        assertObjectEqual(ad.getDisplayUrl(), descriptionBiz1);
        assertObjectEqual(ad.getDestinationUrl(), "http://" + descriptionBiz2);
        assertObjectEqual(ad.getAdStatus().toString(), AdStatusEnum.ACTIVE.toString());
    }

    @Test
    public void testCancelCreateNewAd() throws Exception {
        String displayUrl = "www.yp.com";
        String destinationUrl = "www.yp21.com";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());

        MyAgencyPage agencyPage = login();
        AdPage adPage = agencyPage.navigateToAdTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        adPage.clickCreateAdButton();
        adPage.selectAdGroup(adGroup.getAdGroupName());
        adPage.enterHeadline(headline);
        adPage.enterDescription1(description1);
        adPage.enterDescription2(description2);
        adPage.enterDisplayUrl(displayUrl);
        adPage.enterDestinationUrl(destinationUrl);
        adPage.cancel();

        assertConditionTrue(getAdByHeadline(account.getAccountId(), adGroup.getAdGroupId(), "test headline for cancel") == null);
    }

    @Test
    public void testMissingDescriptionText() throws Exception {
        String headlineError = "Headline is required.";
        String descriptionLineOneError = "Description line 1 is required.";
        String descriptionLineTwoError = "Description line 2 is required.";
        String displayUrlError = "Display URL is required.";
        String destinationUrlError = "Destination URL is required.";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());

        MyAgencyPage agencyPage = login();
        AdPage adPage = agencyPage.navigateToAdTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        adPage.clickCreateAdButton();
        adPage.selectAdGroup(adGroup.getAdGroupName());
        adPage.submit();

        assertConditionTrue(adPage.getHeadlineRequiredErrorMessage().equalsIgnoreCase(headlineError));
        assertConditionTrue(adPage.getDescriptionLineOneRequiredErrorMessage().equalsIgnoreCase(descriptionLineOneError));
        assertConditionTrue(adPage.getDescriptionLineTwoRequiredErrorMessage().equalsIgnoreCase(descriptionLineTwoError));
        assertConditionTrue(adPage.getDisplayUrlRequiredErrorMessage().equalsIgnoreCase(displayUrlError));
        assertConditionTrue(adPage.getDestinationUrlRequiredErrorMessage().equalsIgnoreCase(destinationUrlError));
    }

    @Test
    public void testDynamicKeywordInsertion() throws Exception {
        String errorTextHeadline = "Incorrect Keyword Insertion in Headline.";
        String errorTextDescriptionOne = "Incorrect Keyword Insertion in Description line 1.";
        String errorTextDescriptionTwo = "Incorrect Keyword Insertion in Description line 2.";
        String errorTextDisplayUrl = "Incorrect Keyword Insertion in Display URL.";
        String errorTextDestinationUrl = "Incorrect Keyword Insertion in Destination URL.";
        String keywordInsertionText = "Get Price on {keyword: }";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());

        MyAgencyPage agencyPage = login();
        AdPage adPage = agencyPage.navigateToAdTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        adPage.clickCreateAdButton();
        adPage.selectAdGroup(adGroup.getAdGroupName());
        adPage.enterHeadline(keywordInsertionText);
        adPage.enterDescription1(keywordInsertionText);
        adPage.enterDescription2(keywordInsertionText);
        adPage.enterDisplayUrl(keywordInsertionText);
        adPage.enterDestinationUrl(keywordInsertionText);
        adPage.submitKeyWordInsertion();
        assertConditionTrue(adPage.dynamicKeywordInsertionHeadlineError().equalsIgnoreCase(errorTextHeadline));
        assertConditionTrue(adPage.dynamicKeywordInsertionDescriptionOneError().equalsIgnoreCase(errorTextDescriptionOne));
        assertConditionTrue(adPage.dynamicKeywordInsertionDescriptionTwoError().equalsIgnoreCase(errorTextDescriptionTwo));
        assertConditionTrue(adPage.dynamicKeywordInsertionHDisplayUrlError().equalsIgnoreCase(errorTextDisplayUrl));
        assertConditionTrue(adPage.dynamicKeywordInsertionDestinationUrlError().equalsIgnoreCase(errorTextDestinationUrl));
    }

    @Test
    public void testBoundaryValuesError() throws Exception {
        String adsText[] = {"Maximum Length is 25 Characters", "Maximum Length is Thirty Five Characters", "MaximumLengthisThirtyFiveCharacters.com",
                "Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters "};
        String headlineError = "Headline is too long.";
        String descriptionOneError = "Description line 1 is too long.";
        String descriptionTwoError = "Description line 2 is too long.";
        String displayUrlError = "Display URL is too long.";
        //String destinationUrlError = "Destination URL is too long.";
        String destinationUrlError = "Invalid characters in Destination URL.";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());

        MyAgencyPage agencyPage = login();
        AdPage adPage = agencyPage.navigateToAdTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        adPage.clickCreateAdButton();
        adPage.selectAdGroup(adGroup.getAdGroupName());
        adPage.enterHeadline(adsText[0]);
        adPage.enterDescription1(adsText[1]);
        adPage.enterDescription2(adsText[1]);
        adPage.enterDisplayUrl(adsText[2]);
        //Combination of Selenium and AngularJS appears to cause error when entering text over 2000 characters (A script on this page may be busy, or it may have stopped responding.)
        adPage.enterDestinationUrl("test.com");
        adPage.enterDestinationUrlJS(adsText[3]);
        adPage.submit();
        assertConditionTrue("Headline error not displayed", adPage.verifyErrorMessage(headlineError));
        assertConditionTrue("Description1 error not displayed", adPage.verifyErrorMessage(descriptionOneError));
        assertConditionTrue("Description2 error not displayed", adPage.verifyErrorMessage(descriptionTwoError));
        assertConditionTrue("DisplayURL error not displayed", adPage.verifyErrorMessage(displayUrlError));
        //assertConditionTrue("Destination URL error not displayed", adPage.verifyErrorMessage(destinationUrlError));
    }

    @Test
    public void testDisplayUrlInvalidCharacterError() throws Exception {

        String displayUrlError = "Invalid characters in Display URL.";
        String destinationUrlError = "Invalid characters in Destination URL.";
        String displayUrl = "w ww. a b c d .com";
        String destinationUrl = "a b cd.com";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());

        MyAgencyPage agencyPage = login();
        AdPage adPage = agencyPage.navigateToAdTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        adPage.clickCreateAdButton();
        adPage.selectAdGroup(adGroup.getAdGroupName());
        adPage.enterHeadline(headline);
        adPage.enterDescription1(description1);
        adPage.enterDescription2(description2);
        adPage.enterDisplayUrl(displayUrl);
        adPage.enterDestinationUrl(destinationUrl);

        adPage.submit();
        assertConditionTrue(adPage.getInvalidDisplayUrl().contains(displayUrlError));
        assertConditionTrue(adPage.getInvalidDestinationUrl().contains(destinationUrlError));
    }

    @Test
    public void testInputPhoneInDescriptionLineOne() throws Exception {
        String description2 = "Description 2 without Phone";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());

        MyAgencyPage agencyPage = login();
        AdPage adPage = agencyPage.navigateToAdTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        adPage.clickCreateAdButton();
        adPage.selectAdGroup(adGroup.getAdGroupName());
        adPage.enterHeadline(headline);
        adPage.enterDescription2(description2);
        adPage.enterDisplayUrl(displayUrl);
        adPage.enterDestinationUrl(destinationUrl);
        for (String description1 : description1WithPhone) {
            adPage.enterDescription1(description1);
            adPage.submit();
            adPage.getPhoneErrorMessage();
            assertConditionTrue(adPage.getPhoneErrorMessage().contains(descriptionErrorMessage));
            adPage.clearDescription1Line();
        }

        List<Ad> adList = getAdByAdGroupId(account.getAccountId(), adGroup.getAdGroupId());
        assertConditionTrue(adList.size() == 0);
    }

    @Test
    public void testInputPhoneInDescriptionLineTwo() throws Exception {
        String description1 = "Description 1 without Phone";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());

        MyAgencyPage agencyPage = login();
        AdPage adPage = agencyPage.navigateToAdTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        adPage.clickCreateAdButton();
        adPage.selectAdGroup(adGroup.getAdGroupName());
        adPage.enterHeadline(headline);
        adPage.enterDescription1(description1);
        adPage.enterDisplayUrl(displayUrl);
        adPage.enterDestinationUrl(destinationUrl);
        for (String description2 : description2WithPhone) {
            adPage.enterDescription2(description2);
            adPage.submit();
            adPage.getPhoneErrorMessage();
            assertConditionTrue(adPage.getPhoneErrorMessage().contains(descriptionErrorMessage));
            adPage.clearDescription2Line();
            List<Ad> adList = getAdByAdGroupId(account.getAccountId(), adGroup.getAdGroupId());
            assertConditionTrue(adList.size() == 0);
        }

        List<Ad> adList = getAdByAdGroupId(account.getAccountId(), adGroup.getAdGroupId());
        assertConditionTrue(adList.size() == 0);
    }

    @Test
    public void testDuplicateAdError() throws Exception {
        String displayUrl = "www.yp.com";
        String destinationUrl = "www.yp.com";
        String errorMessage = "This ad already exists in this ad group.";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());

        MyAgencyPage agencyPage = login();
        AdPage adPage = agencyPage.navigateToAdTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        adPage.clickCreateAdButton();
        adPage.selectAdGroup(adGroup.getAdGroupName());
        adPage.enterHeadline(headline);
        adPage.enterDescription1(description1);
        adPage.enterDescription2(description2);
        adPage.enterDisplayUrl(displayUrl);
        adPage.enterDestinationUrl(destinationUrl);
        adPage.submit();
        adPage.checkButtonNotVisible(UIMapper.SUBMIT_AD, LocatorTypeEnum.CSS);
        adPage.checkNoRecordsToLoad();
        adPage.clickCreateAdButton();
        adPage.selectAdGroup(adGroup.getAdGroupName());
        adPage.enterHeadline(headline);
        adPage.enterDescription1(description1);
        adPage.enterDescription2(description2);
        adPage.enterDisplayUrl(displayUrl);
        adPage.enterDestinationUrl(destinationUrl);
        adPage.submit();
        adPage.checkDuplicateErrorMessage();
        assertConditionTrue(adPage.getDuplicateAdErrorMessage().contains(errorMessage));

        List<Ad> adList = getAdByAdGroupId(account.getAccountId(), adGroup.getAdGroupId());
        assertConditionTrue(adList.size() == 1);
    }
}