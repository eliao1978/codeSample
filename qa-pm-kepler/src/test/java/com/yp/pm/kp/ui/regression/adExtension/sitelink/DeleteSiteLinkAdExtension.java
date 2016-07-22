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
public class DeleteSiteLinkAdExtension extends BaseUITest {
    private String deleted = "DELETED";
    private String siteLinkText = "auto discounts";
    private String url = "www.yp1.com";

    @Test
    public void testDeleteSiteLink() throws Exception {
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
        siteLinkExtension.clickDeleteButton(siteLinkText);
        siteLinkExtension.deleteSiteLinkExtension();
        assertConditionTrue(!siteLinkExtension.getSiteLinkTableText(2, 2).contains(siteLinkText));


        List<SiteLinkAdExtension> newSiteLink = getSiteLinkAdExtensionByAccountId(account.getAccountId());
        for (SiteLinkAdExtension extensionSiteLink : newSiteLink) {
            if (extensionSiteLink.getAdExtensionStatus().toString().equalsIgnoreCase(deleted)) {
                assertObjectEqual(extensionSiteLink.getDisplayText(), siteLinkText);
            }
        }
    }

    @Test
    public void testSiteLinkExtensionDeleteByClickingRemoveButton() throws Exception {
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
        siteLinkExtension.deleteSiteLinkExtensionByRemoveButton(2, 1);
        assertConditionTrue(!siteLinkExtension.getSiteLinkTableText(2, 2).contains(siteLinkText));

        List<SiteLinkAdExtension> newSiteLink = getSiteLinkAdExtensionByAccountId(account.getAccountId());
        for (SiteLinkAdExtension extensionSiteLink : newSiteLink) {
            if (extensionSiteLink.getAdExtensionStatus().toString().equalsIgnoreCase(deleted)) {
                assertObjectEqual(extensionSiteLink.getDisplayText(), siteLinkText);
            }
        }
    }
}