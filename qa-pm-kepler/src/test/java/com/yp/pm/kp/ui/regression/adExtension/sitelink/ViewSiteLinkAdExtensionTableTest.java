package com.yp.pm.kp.ui.regression.adExtension.sitelink;


import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.ui.AdExtensionSiteLinkExtension;
import com.yp.pm.kp.ui.MyAgencyPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class ViewSiteLinkAdExtensionTableTest extends BaseUITest {
    private String siteLinkLabels[] = {"Sitelink", "Status", "Campaign", "Clicks", "Impr.", "CTR", "Avg. CPC", "Cost", "Avg. Pos."};

    @Test
    public void testTableHeader() throws Exception {
        String siteLinkText[] = {"auto discounts", "finance discounts", "holiday discounts"};
        String siteLinkUrl[] = {"www.yp1.com", "www.yp2.com", "www.yp3.com"};
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
        for (String siteLinkLabel : siteLinkLabels) {
            if (!siteLinkExtension.checkSiteLinkHeader(siteLinkLabel)) {
                throw new NullPointerException("Element does not exist [" + siteLinkLabel + "]");
            }
        }
    }

    @Test
    public void testSortTable() throws Exception {
        String siteLinkText[] = {"finance discounts", "holiday discounts", "auto discounts",};
        String siteLinkUrl[] = {"www.yp1.com", "www.yp2.com", "www.yp3.com"};
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
        siteLinkExtension.clickTableHeader(siteLinkLabels[0]);
        assertConditionTrue(siteLinkExtension.getSiteLinkTableText(2, 2).equals(siteLinkText[0]));
        assertConditionTrue(siteLinkExtension.getSiteLinkTableText(3, 2).equals(siteLinkText[1]));
        assertConditionTrue(siteLinkExtension.getSiteLinkTableText(4, 2).equals(siteLinkText[2]));
        siteLinkExtension.clickTableHeader(siteLinkLabels[0]);
        assertConditionTrue(siteLinkExtension.getSiteLinkTableText(2, 2).equals(siteLinkText[2]));
        assertConditionTrue(siteLinkExtension.getSiteLinkTableText(3, 2).equals(siteLinkText[1]));
        assertConditionTrue(siteLinkExtension.getSiteLinkTableText(4, 2).equals(siteLinkText[0]));
    }

}