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
public class SearchCampaignTest extends BaseUITest {
    private static Map<String, Object> testData;
    String campaigns[] = {"Auto Insurance Campaign" + System.currentTimeMillis(), "Auto Campaign" + System.currentTimeMillis(), "Finance Campaign" + System.currentTimeMillis()};


    @Test
    public void testPartialSearchCampaign() throws Exception {
        String partialText = "Auto";

        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, ((Account) getTestData().get("account")).getAccountId());
        Arrays.sort(campaigns);
        campaignPage.enterSearchText(partialText);
        campaignPage.submitSearchText();
        campaignPage.clickTableHeader("Campaign");
        assertConditionTrue(campaignPage.getCampaignTableText(2, 3).contains(partialText));
        assertConditionTrue(campaignPage.getCampaignTableText(3, 3).contains(partialText));
        assertObjectEqual(campaignPage.getTableSize(), 4.0);
    }

    @Test
    public void testSearchCampaign() throws Exception {
        String searchText = "Auto Insurance Campaign";

        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, ((Account) getTestData().get("account")).getAccountId());
        Arrays.sort(campaigns);
        campaignPage.enterSearchText(searchText);
        campaignPage.submitSearchText();
        assertConditionTrue(campaignPage.getCampaignTableText(2, 3).contains(searchText));
        assertObjectEqual(campaignPage.getTableSize(), 3.0);
    }

    @Test
    public void testInvalidSearchCampaign() throws Exception {
        String searchText = "Hotels";

        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, ((Account) getTestData().get("account")).getAccountId());
        Arrays.sort(campaigns);
        campaignPage.enterSearchText(searchText);
        campaignPage.submitSearchText();
        assertConditionTrue(!campaignPage.getCampaignTableText(2, 3).contains(searchText));
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
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
            testData.put("account", account);
        }
        return testData;
    }
}
