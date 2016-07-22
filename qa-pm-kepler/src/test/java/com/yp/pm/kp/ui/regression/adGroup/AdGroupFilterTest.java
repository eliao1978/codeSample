package com.yp.pm.kp.ui.regression.adGroup;


import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.ui.AdGroupPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import org.json.simple.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AdGroupFilterTest extends BaseUITest {
    String adGroupName[] = {"AdGroup Life", "AdGroup Health", "AdGroup YP"};

    @Test
    public void testAllButDeleted() throws Exception {

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        for (String adGroup : adGroupName) {
            JSONObject adGroup1 = new JSONObject();
            adGroup1.put("adGroupName", adGroup);
            createAdGroupOnly(account.getAccountId(), campaign.getCampaignId(), adGroup1);
        }

        MyAgencyPage agencyPage = login();
        AdGroupPage adGroupPage = agencyPage.navigateToAdGroupTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        adGroupPage.loadingStatus();
        Arrays.sort(adGroupName);
        adGroupPage.clickFilterButton();
        adGroupPage.clickTableHeader(3);
        adGroupPage.selectCheckBox(2, 1);
        adGroupPage.updateAdGroupDropdown(1);//Pause Campaign
        adGroupPage.selectCheckBox(3, 1);
        adGroupPage.updateAdGroupDropdown(2);//Delete Campaign
        adGroupPage.selectAdGroupDropdown(2);//Select all but deleted
        assertObjectEqual(adGroupPage.getTableSize(), 4.0);
        assertObjectEqual(adGroupPage.getAdGroupTableText(2, 3), adGroupName[0]);
        assertObjectEqual(adGroupPage.getAdGroupTableText(3, 3), adGroupName[2]);
    }

    @Test
    public void testAllEnabled() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        for (String adGroup : adGroupName) {
            JSONObject adGroup1 = new JSONObject();
            adGroup1.put("adGroupName", adGroup);
            createAdGroupOnly(account.getAccountId(), campaign.getCampaignId(), adGroup1);
        }

        MyAgencyPage agencyPage = login();
        AdGroupPage adGroupPage = agencyPage.navigateToAdGroupTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        adGroupPage.loadingStatus();
        Arrays.sort(adGroupName);
        adGroupPage.clickFilterButton();
        adGroupPage.clickTableHeader(3);
        adGroupPage.selectCheckBox(2, 1);
        adGroupPage.updateAdGroupDropdown(1);//Pause Campaign
        adGroupPage.selectCheckBox(3, 1);
        adGroupPage.updateAdGroupDropdown(2);//Delete Campaign
        adGroupPage.selectAdGroupDropdown(1);//Select all but deleted
        assertObjectEqual(adGroupPage.getTableSize(), 3.0);
        assertObjectEqual(adGroupPage.getAdGroupTableText(2, 3), adGroupName[2]);
    }

    @Test
    public void testAllAdGroups() throws Exception {

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        for (String adGroup : adGroupName) {
            JSONObject adGroup1 = new JSONObject();
            adGroup1.put("adGroupName", adGroup);
            createAdGroupOnly(account.getAccountId(), campaign.getCampaignId(), adGroup1);
        }

        MyAgencyPage agencyPage = login();
        AdGroupPage adGroupPage = agencyPage.navigateToAdGroupTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        adGroupPage.loadingStatus();
        Arrays.sort(adGroupName);
        adGroupPage.clickFilterButton();
        adGroupPage.clickTableHeader(3);
        adGroupPage.selectCheckBox(2, 1);
        adGroupPage.updateAdGroupDropdown(1);//Pause Campaign
        adGroupPage.selectCheckBox(3, 1);
        adGroupPage.updateAdGroupDropdown(2);//Delete Campaign
        adGroupPage.selectAdGroupDropdown(0);//Select all Ad groups
        assertObjectEqual(adGroupPage.getTableSize(), 5.0);
        assertObjectEqual(adGroupPage.getAdGroupTableText(2, 3), adGroupName[0]);
        assertObjectEqual(adGroupPage.getAdGroupTableText(3, 3), adGroupName[1]);
        assertObjectEqual(adGroupPage.getAdGroupTableText(4, 3), adGroupName[2]);
    }
}