package com.yp.pm.kp.ui.regression.campaign;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.ui.CampaignPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class ViewCampaignTableTest extends BaseUITest {

    private static Map<String, Object> testData;
    private String CampaignLabels[] = {"Status", "Campaign", "Budget Name", "Budget", "Serving Status", "Clicks", "Impr.", "CTR", "Avg. CPC", "Cost", "Avg. Pos."};


    @Test
    public void testCampaignTableHeader() throws Exception {
        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, ((Account) getTestData().get("account")).getAccountId());
        campaignPage.loadingStatus();

        for (String CampaignLabel : CampaignLabels) {
            if (!campaignPage.checkCampaignHeader(CampaignLabel)) {
                throw new NullPointerException("Element does not exist [" + CampaignLabel + "]");
            }
        }
    }

    @Test
    public void testSortCampaignTable() throws Exception {
        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, ((Account) getTestData().get("account")).getAccountId());
        campaignPage.loadingStatus();

        String[] campaignNames = (String[]) getTestData().get("campaignName");
        Arrays.sort(campaignNames);

        campaignPage.clickTableHeader("Campaign");
        assertConditionTrue(campaignPage.getCampaignTableText(2, 3).equalsIgnoreCase(campaignNames[0]));
        assertConditionTrue(campaignPage.getCampaignTableText(3, 3).equalsIgnoreCase(campaignNames[1]));
        assertConditionTrue(campaignPage.getCampaignTableText(4, 3).equalsIgnoreCase(campaignNames[2]));
        assertConditionTrue(campaignPage.getCampaignTableText(5, 3).equalsIgnoreCase(campaignNames[3]));
        campaignPage.clickTableHeader("Campaign");
        assertConditionTrue(campaignPage.getCampaignTableText(2, 3).equalsIgnoreCase(campaignNames[3]));
        assertConditionTrue(campaignPage.getCampaignTableText(3, 3).equalsIgnoreCase(campaignNames[2]));
        assertConditionTrue(campaignPage.getCampaignTableText(4, 3).equalsIgnoreCase(campaignNames[1]));
        assertConditionTrue(campaignPage.getCampaignTableText(5, 3).equalsIgnoreCase(campaignNames[0]));
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            String[] campaignNames = {"Campaign", "Auto_Campaign" + System.currentTimeMillis(), "Finance_Campaign" + System.currentTimeMillis(), "Insurance_Campaign" + System.currentTimeMillis()};
            Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = createBudget(account.getAccountId(), 100, "standard");

            JSONArray campaignArray = new JSONArray();
            for (String name : campaignNames) {
                JSONObject campaign1 = new JSONObject();
                campaign1.put("campaignName", name);
                campaign1.put("mobileBitModifier", "1.00");
                campaign1.put("desktopBidModifier", "1.00");
                campaignArray.add(campaign1);
            }
            createCampaign(account.getAccountId(), budget.getBudgetId(), campaignArray);

            testData.put("account", account);
            testData.put("campaignName", campaignNames);
        }
        return testData;
    }
}
