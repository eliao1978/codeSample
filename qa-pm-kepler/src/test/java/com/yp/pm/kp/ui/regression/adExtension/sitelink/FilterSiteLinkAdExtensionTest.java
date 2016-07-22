package com.yp.pm.kp.ui.regression.adExtension.sitelink;

import com.yp.enums.selenium.LocatorTypeEnum;
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

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class FilterSiteLinkAdExtensionTest extends BaseUITest {

    @Test
    public void testFilterSiteLink() throws Exception {
        String siteLinkText[] = {"auto discounts", "finance discounts", "holiday discounts", "food discounts", "health discounts"};
        String siteLinkUrl[] = {"www.yp1.com", "www.yp2.com", "www.yp3.com", "www.yp4.com", "www.yp5.com"};
        int LinkText = siteLinkText.length;

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();

        MyAgencyPage agencyPage = login();
        AdExtensionSiteLinkExtension siteLinkExtension = agencyPage.navigateToAdExtensionsTab(agencyId, account.getAccountId(), campaignId);
        siteLinkExtension.clickExtensionButton();
        for (int newSiteLink = 0; newSiteLink < LinkText; newSiteLink++) {
            siteLinkExtension.clickNewSiteLinkExtensionButton();
            siteLinkExtension.enterLinkText(siteLinkText[newSiteLink]);
            siteLinkExtension.enterLinkurl(siteLinkUrl[newSiteLink]);
            siteLinkExtension.clickSaveButton();
        }

        Arrays.sort(siteLinkText);
        siteLinkExtension.clickFilter();
        for (int dropdownValues = 0; dropdownValues <= 5; dropdownValues++) {
            siteLinkExtension.selectSiteLinkDropDownValues(dropdownValues);
            assertConditionTrue(siteLinkExtension.getSiteLinkTableText(2, 4).equalsIgnoreCase("Not used"));
            assertConditionTrue(siteLinkExtension.getSiteLinkTableText(2, 5).equalsIgnoreCase("0"));
            assertConditionTrue(siteLinkExtension.getSiteLinkTableText(2, 6).equalsIgnoreCase("0"));
            assertConditionTrue(siteLinkExtension.getSiteLinkTableText(2, 7).equalsIgnoreCase("0.00%"));
            assertConditionTrue(siteLinkExtension.getSiteLinkTableText(2, 8).equalsIgnoreCase("$0.00"));
            assertConditionTrue(siteLinkExtension.getSiteLinkTableText(2, 9).equalsIgnoreCase("$0.00"));
            assertConditionTrue(siteLinkExtension.getSiteLinkTableText(2, 10).equalsIgnoreCase("0.0"));
            assertConditionTrue(siteLinkExtension.getTableSize() == 7);
        }
    }

    @Test
    public void testSiteLinkStaticFilter() throws Exception {
        String deleted = "DELETED";
        String siteLinkText = "auto discounts";
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
        siteLinkExtension.deleteSiteLinkExtensionByRemoveButton(2, 1);
        siteLinkExtension.waitForElementNotVisible("//td/div/div[contains(normalize-space(.),'" + siteLinkText + "')]", LocatorTypeEnum.XPATH);
        assertConditionTrue(!siteLinkExtension.getSiteLinkTableText(2, 2).contains(siteLinkText));

        siteLinkExtension.clickStaticFilterButton();
        siteLinkExtension.selectStaticFilterAllAdExtension();
        assertConditionTrue(siteLinkExtension.getSiteLinkTableText(2, 2).contains(siteLinkText));
        siteLinkExtension.clickStaticFilterButton();
        siteLinkExtension.selectStaticFilterAllButDeletedAdExtension();
        assertConditionTrue(!siteLinkExtension.getSiteLinkTableText(2, 2).contains(siteLinkText));

        List<SiteLinkAdExtension> newSiteLink = getSiteLinkAdExtensionByAccountId(account.getAccountId());
        for (SiteLinkAdExtension extensionSiteLink : newSiteLink) {
            if (extensionSiteLink.getAdExtensionStatus().toString().equalsIgnoreCase(deleted)) {
                assertObjectEqual(extensionSiteLink.getDisplayText(), siteLinkText);

            }
        }
    }
}