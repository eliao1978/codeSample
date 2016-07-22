
package com.yp.pm.kp.ui.regression.campaign;

import com.yp.enums.selenium.LocatorTypeEnum;
import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.ui.CampaignPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import com.yp.pm.kp.ui.UIMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class CampaignFilterTest extends BaseUITest {
    String campaigns[] = {"Auto Insurance Campaign" + System.currentTimeMillis(), "Life Campaign" + System.currentTimeMillis(), "Finance Campaign" + System.currentTimeMillis()};

    @Test
    public void testAllButDeletedCampaignTest() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");

        JSONArray campaignArray = new JSONArray();
        for (String campaign : campaigns) {
            JSONObject campaign1 = new JSONObject();
            campaign1.put("campaignName", campaign);
            campaign1.put("mobileBitModifier", "1.00");
            campaign1.put("desktopBidModifier", "1.00");
            campaignArray.add(campaign1);
        }
        createCampaign(account.getAccountId(), budget.getBudgetId(), campaignArray);

        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, account.getAccountId());
        Arrays.sort(campaigns);
        campaignPage.clickFilterButton();
        campaignPage.clickTableHeader("Campaign");
        campaignPage.selectCheckBox(2, 1);
        campaignPage.updateCampaignDropdown(1);//Pause Campaign
        campaignPage.selectCheckBox(3, 1);
        campaignPage.updateCampaignDropdown(2);//Delete Campaign
        campaignPage.campaignDropdown(2);//Select all but deleted
        assertObjectEqual(campaignPage.getTableSize(), 4.0);
        assertObjectEqual(campaignPage.getCampaignTableText(2, 3), campaigns[0]);
        assertObjectEqual(campaignPage.getCampaignTableText(3, 3), campaigns[2]);
    }


    @Test
    public void testAllEnabledCampaigns() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");

        JSONArray campaignArray = new JSONArray();
        for (String campaign : campaigns) {
            JSONObject campaign1 = new JSONObject();
            campaign1.put("campaignName", campaign);
            campaign1.put("mobileBitModifier", "1.00");
            campaign1.put("desktopBidModifier", "1.00");
            campaignArray.add(campaign1);
        }
        createCampaign(account.getAccountId(), budget.getBudgetId(), campaignArray);

        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, account.getAccountId());
        Arrays.sort(campaigns);
        campaignPage.loadingStatus();
        campaignPage.clickFilterButton();
        campaignPage.clickTableHeader("Campaign");
        campaignPage.selectCheckBox(2, 1);
        campaignPage.updateCampaignDropdown(1);//Pause Campaign
        campaignPage.selectCheckBox(3, 1);
        campaignPage.updateCampaignDropdown(2);//Delete Campaign
        campaignPage.campaignDropdown(1);//Select all but Enabled
        if (campaignPage.isElementPresent("//td/span[normalize-space(.)='Paused']", LocatorTypeEnum.XPATH)) {
            campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        }
        assertObjectEqual(campaignPage.getTableSize(), 3.0);
        assertObjectEqual(campaignPage.getCampaignTableText(2, 3), campaigns[2]);
    }


    @Test
    public void testAllCampaigns() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");

        JSONArray campaignArray = new JSONArray();
        for (String campaign : campaigns) {
            JSONObject campaign1 = new JSONObject();
            campaign1.put("campaignName", campaign);
            campaign1.put("mobileBitModifier", "1.00");
            campaign1.put("desktopBidModifier", "1.00");
            campaignArray.add(campaign1);
        }
        createCampaign(account.getAccountId(), budget.getBudgetId(), campaignArray);

        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, account.getAccountId());
        campaignPage.loadingStatus();
        Arrays.sort(campaigns);
        campaignPage.clickFilterButton();
        campaignPage.clickTableHeader("Campaign");
        campaignPage.selectCheckBox(2, 1);
        campaignPage.updateCampaignDropdown(1);//Pause Campaign
        campaignPage.selectCheckBox(3, 1);
        campaignPage.updateCampaignDropdown(2);//Delete Campaign
        campaignPage.campaignDropdown(0);//Select all Campaigns from Dropdown
        assertObjectEqual(campaignPage.getTableSize(), 5.0);
        assertObjectEqual(campaignPage.getCampaignTableText(2, 3), campaigns[0]);
        assertObjectEqual(campaignPage.getCampaignTableText(3, 3), campaigns[1]);
        assertObjectEqual(campaignPage.getCampaignTableText(4, 3), campaigns[2]);
    }

}