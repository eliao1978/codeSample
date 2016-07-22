package com.yp.pm.kp.ui.regression.campaign;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.ui.CampaignPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")

public class ChangeHistoryCampaignTest extends BaseUITest {

    String campaign = "Auto Insurance Campaign" + System.currentTimeMillis();
    String changeHistoryTableLabels[] = {"Date & Time / User", "Campaign", "Ad Group", "Changes"};

    @Test
    public void changeHistoryValidationTest() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");

        JSONArray campaignArray = new JSONArray();
        JSONObject autoCampaign = new JSONObject();
        autoCampaign.put("campaignName", campaign);
        autoCampaign.put("mobileBitModifier", "1.00");
        autoCampaign.put("desktopBidModifier", "1.00");
        campaignArray.add(autoCampaign);
        createCampaign(account.getAccountId(), budget.getBudgetId(), campaignArray);

        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, account.getAccountId());
        campaignPage.clickFilterButton();
        campaignPage.clickTableHeader("Campaign");
        campaignPage.selectCheckBox(2, 1);
        campaignPage.updateCampaignDropdown(1);//Pause Campaign
        campaignPage.clickChangeHistory();
        campaignPage.selectTodayChangeHistory();
        for (String CampaignLabel : changeHistoryTableLabels) {
            if (!campaignPage.checkCampaignHeader(CampaignLabel)) {
                throw new NullPointerException("Element does not exist [" + CampaignLabel + "]");
            }
        }
        assertConditionTrue(campaignPage.getHistoryTableText(2, 2).contains(System.getProperty("ui.username")));
        assertConditionTrue(campaignPage.getHistoryTableText(2, 3).equalsIgnoreCase(campaign));
        assertConditionTrue(campaignPage.getHistoryTableText(2, 5).equalsIgnoreCase("Campaign status changed from Eligible to Paused"));

    }

}