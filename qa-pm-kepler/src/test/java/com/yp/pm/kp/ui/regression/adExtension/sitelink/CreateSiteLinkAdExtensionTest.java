package com.yp.pm.kp.ui.regression.adExtension.sitelink;

import com.yp.enums.selenium.LocatorTypeEnum;
import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.model.domain.SiteLinkAdExtension;
import com.yp.pm.kp.ui.AdExtensionSiteLinkExtension;
import com.yp.pm.kp.ui.MyAgencyPage;
import com.yp.pm.kp.ui.UIMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class CreateSiteLinkAdExtensionTest extends BaseUITest {

    @Test
    public void testCreateSiteLink() throws Exception {
        String siteLinkText = "auto discounts:";
        String url = "www.yp1.com";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();

        MyAgencyPage agencyPage = login();
        AdExtensionSiteLinkExtension siteLinkExtension = agencyPage.navigateToAdExtensionsTab(agencyId, account.getAccountId(), campaignId);
        siteLinkExtension.clickExtensionButton();
        siteLinkExtension.clickNewSiteLinkExtensionButton();
        siteLinkExtension.enterLinkText(siteLinkText);
        siteLinkExtension.enterLinkurl(url);
        siteLinkExtension.clickSaveButton();
        siteLinkExtension.waitForElementNotVisible(UIMapper.NEW_SITELINK_TEXT, LocatorTypeEnum.XPATH);
        assertConditionTrue(siteLinkExtension.getSiteLinkTableText(2, 2).contains(siteLinkText));
        assertConditionTrue(siteLinkExtension.validateUrl(2, 2).contains(url));

        List<SiteLinkAdExtension> newSiteLink = getSiteLinkAdExtensionByAccountId(account.getAccountId());
        assertConditionTrue(newSiteLink.size() == 1);
        SiteLinkAdExtension newSite = newSiteLink.get(0);
        assertObjectNotNull(newSite.getAccountId());
        assertObjectEqual(newSite.getAccountId(), account.getAccountId());
        assertObjectEqual(newSite.getDisplayText(), siteLinkText);
        assertObjectEqual(newSite.getDestinationUrl(), "http://" + url);
        assertObjectEqual(newSite.getAdExtensionStatus().toString(), "ACTIVE");
    }

    @Test
    public void testMissingSiteLinkNameSiteLinkText() throws Exception {
        String siteLinkTextError = "Link text is required";
        String siteLinkUrlError = "Link URL is required";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();

        MyAgencyPage agencyPage = login();
        AdExtensionSiteLinkExtension siteLinkExtension = agencyPage.navigateToAdExtensionsTab(agencyId, account.getAccountId(), campaignId);
        siteLinkExtension.clickExtensionButton();
        siteLinkExtension.clickNewSiteLinkExtensionButton();
        siteLinkExtension.clickSaveButton();
        assertConditionTrue(siteLinkExtension.getSiteLinkExtensionLinkTextErrorText().contains(siteLinkTextError));
        assertConditionTrue(siteLinkExtension.getSiteLinkExtensionLinkUrlErrorText().contains(siteLinkUrlError));
    }


    @Test
    public void testUrlTooLongError() throws Exception {
        String siteLinkText = "Maximum length for Link Text is Twenty Five Characters";
        String url = "Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters Maximum Length for display url is Two Thousand Forty Eight Characters.com";
        String linkTextError = "Link text is too long";
        String linkUrlError = "Link URL is too long";
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();

        MyAgencyPage agencyPage = login();
        AdExtensionSiteLinkExtension siteLinkExtension = agencyPage.navigateToAdExtensionsTab(agencyId, account.getAccountId(), campaignId);
        siteLinkExtension.clickExtensionButton();
        siteLinkExtension.clickNewSiteLinkExtensionButton();
        siteLinkExtension.enterLinkText(siteLinkText);
        siteLinkExtension.enterLinkurl("test.com");
        siteLinkExtension.clickSaveButton();
        assertConditionTrue(siteLinkExtension.getLinkTextTooLongError().contains(linkTextError));
        //assertConditionTrue(siteLinkExtension.getLinkUrlTooLongError().contains(linkUrlError));
    }

    @Test
    public void testInvalidCharactersInUrlError() throws Exception {
        String siteLinkText = "auto discounts:";
        String url = "afsdlkjdsalkfjdsal;kfjl;dskajf";
        String urlError = "Invalid characters in Link URL.";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();

        MyAgencyPage agencyPage = login();
        AdExtensionSiteLinkExtension siteLinkExtension = agencyPage.navigateToAdExtensionsTab(agencyId, account.getAccountId(), campaignId);
        siteLinkExtension.clickExtensionButton();
        siteLinkExtension.clickNewSiteLinkExtensionButton();
        siteLinkExtension.enterLinkText(siteLinkText);
        siteLinkExtension.enterLinkurl(url);
        siteLinkExtension.clickSaveButton();
        assertConditionTrue(siteLinkExtension.getInvalidLinkUrlError().contains(urlError));

    }

    @Test
    public void testCancelCreateNewSiteLink() throws Exception {
        String siteLinkText = "auto" + System.currentTimeMillis();
        String url = "www.ypcancel.com";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();

        MyAgencyPage agencyPage = login();
        AdExtensionSiteLinkExtension siteLinkExtension = agencyPage.navigateToAdExtensionsTab(agencyId, account.getAccountId(), campaignId);
        siteLinkExtension.clickExtensionButton();
        siteLinkExtension.clickNewSiteLinkExtensionButton();
        siteLinkExtension.enterLinkText(siteLinkText);
        siteLinkExtension.enterLinkurl(url);
        siteLinkExtension.clickCancelButton();

        List<SiteLinkAdExtension> newSiteLink = getSiteLinkAdExtensionByAccountId(account.getAccountId());
        for (SiteLinkAdExtension extensionSiteLink : newSiteLink) {
            assertConditionTrue(!extensionSiteLink.getDisplayText().equalsIgnoreCase(siteLinkText));
            assertConditionTrue(!extensionSiteLink.getDestinationUrl().equalsIgnoreCase("http://" + url));
        }
    }


    @Test
    public void testDifferentDomains() throws Exception {
        String domain[] = {"www.test.co", "www.test.biz", "www.test.jobs", "www.test.mobi", "www.test.travel", "www.test.net", "www.test.org", "www.test.pro", "www.test.tel", "www.test.info", "www.test.xxx",
                "www.test.name", "www.test.us"};

        String siteLinkText = "auto discounts:";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();

        MyAgencyPage agencyPage = login();
        AdExtensionSiteLinkExtension siteLinkExtension = agencyPage.navigateToAdExtensionsTab(agencyId, account.getAccountId(), campaignId);
        siteLinkExtension.clickExtensionButton();

        for (String domainUrl : domain) {
            siteLinkExtension.clickNewSiteLinkExtensionButton();
            siteLinkExtension.enterLinkText(siteLinkText);
            siteLinkExtension.enterLinkurl(domainUrl);
            siteLinkExtension.clickSaveButton();
            siteLinkExtension.waitForElementNotVisible(UIMapper.NEW_SITELINK_TEXT, LocatorTypeEnum.XPATH);
        }
        assertObjectEqual(siteLinkExtension.getTableSize(), 15.0);

        List<SiteLinkAdExtension> newSiteLink = getSiteLinkAdExtensionByAccountId(account.getAccountId());
        assertConditionTrue(newSiteLink.size() == 13);

        for (int i = 0; i <= 12; i++) {
            SiteLinkAdExtension newSite = newSiteLink.get(i);
            assertObjectNotNull(newSite.getAccountId());
            assertObjectEqual(newSite.getAccountId(), account.getAccountId());
            assertObjectEqual(newSite.getDisplayText(), siteLinkText);
            assertObjectEqual(newSite.getDestinationUrl(), "http://" + domain[i]);
            assertObjectEqual(newSite.getAdExtensionStatus().toString(), "ACTIVE");

        }
    }
}