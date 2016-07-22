package com.yp.pm.kp.ui.regression.adExtension.sitelink;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.model.domain.SiteLinkAdExtension;
import com.yp.pm.kp.ui.AdExtensionSiteLinkExtension;
import com.yp.pm.kp.ui.MyAgencyPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class EditSiteLinkAdExtensionTest extends BaseUITest {

    @Test
    public void testEditSiteLink() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();

        String siteLinkText = "auto" + System.currentTimeMillis();
        String url = "www.yp11.com";
        String siteLinkEditedText = "auto1" + System.currentTimeMillis();
        String editedUrl = "www.yp.com";

        MyAgencyPage agencyPage = login();
        AdExtensionSiteLinkExtension siteLinkExtension = agencyPage.navigateToAdExtensionsTab(agencyId, account.getAccountId(), campaignId);
        siteLinkExtension.clickExtensionButton();
        siteLinkExtension.clickNewSiteLinkExtensionButton();
        siteLinkExtension.enterLinkText(siteLinkText);
        siteLinkExtension.enterLinkurl(url);
        siteLinkExtension.clickSaveButton();
        siteLinkExtension.clickPanelEditExtension();
        siteLinkExtension.clickSiteLinKEditExtension();
        siteLinkExtension.enterLinkText(siteLinkEditedText);
        siteLinkExtension.enterLinkurl(editedUrl);
        siteLinkExtension.clickSaveButton();
        assertConditionTrue(siteLinkExtension.getSiteLinkTableText(2, 2).contains(siteLinkEditedText));
        assertConditionTrue(siteLinkExtension.validateUrl(2, 2).contains(editedUrl));

        List<SiteLinkAdExtension> newSiteLink = getSiteLinkAdExtensionByAccountId(account.getAccountId());
        assertConditionTrue(newSiteLink.size() >= 1);
        for (SiteLinkAdExtension extensionSiteLink : newSiteLink) {
            if (extensionSiteLink.getAdExtensionStatus().toString().equalsIgnoreCase("ACTIVE")) {
                assertObjectEqual(extensionSiteLink.getAccountId(), account.getAccountId());
                assertObjectEqual(extensionSiteLink.getDestinationUrl(), "http://" + editedUrl);
                assertObjectEqual(extensionSiteLink.getDisplayText(), siteLinkEditedText);
            }
        }
    }

    @Test
    public void testEditSiteLinkHttpUrl() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();
        String error = "Invalid characters in Link URL";

        String siteLinkText = "auto" + System.currentTimeMillis();
        String url = "www.yp11.com";
        String siteLinkEditedText = "auto1" + System.currentTimeMillis();
        String editedUrl = "http://www.yp.com";

        MyAgencyPage agencyPage = login();
        AdExtensionSiteLinkExtension siteLinkExtension = agencyPage.navigateToAdExtensionsTab(agencyId, account.getAccountId(), campaignId);
        siteLinkExtension.clickExtensionButton();
        siteLinkExtension.clickNewSiteLinkExtensionButton();
        siteLinkExtension.enterLinkText(siteLinkText);
        siteLinkExtension.enterLinkurl(url);
        siteLinkExtension.clickSaveButton();
        siteLinkExtension.clickPanelEditExtension();
        siteLinkExtension.clickSiteLinKEditExtension();
        siteLinkExtension.enterLinkText(siteLinkEditedText);
        siteLinkExtension.enterLinkurl(editedUrl);
        siteLinkExtension.clickSaveButton();
        assertConditionTrue(siteLinkExtension.getError().contains(error));

    }

    @Test
    public void testEditSiteLinkHttpsUrl() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();
        String error = "Invalid characters in Link URL";

        String siteLinkText = "auto" + System.currentTimeMillis();
        String url = "www.yp11.com";
        String siteLinkEditedText = "auto1" + System.currentTimeMillis();
        String editedUrl = "https://www.yp.com";

        MyAgencyPage agencyPage = login();
        AdExtensionSiteLinkExtension siteLinkExtension = agencyPage.navigateToAdExtensionsTab(agencyId, account.getAccountId(), campaignId);
        siteLinkExtension.clickExtensionButton();
        siteLinkExtension.clickNewSiteLinkExtensionButton();
        siteLinkExtension.enterLinkText(siteLinkText);
        siteLinkExtension.enterLinkurl(url);
        siteLinkExtension.clickSaveButton();
        siteLinkExtension.clickPanelEditExtension();
        siteLinkExtension.clickSiteLinKEditExtension();
        siteLinkExtension.enterLinkText(siteLinkEditedText);
        siteLinkExtension.enterLinkurl(editedUrl);
        siteLinkExtension.clickSaveButton();
        assertConditionTrue(siteLinkExtension.getError().contains(error));

    }
}