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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SearchAdGroupTest extends BaseUITest {

    private static Map<String, Object> testData;
    private static String adGroupName1 = "Robot adgroup";

    @Test
    public void testSearchAdGroupName() throws Exception {
        MyAgencyPage agencyPage = login();
        AdGroupPage adGroupPage = agencyPage.navigateToAdGroupTab(agencyId, ((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
        adGroupPage.loadingStatus();
        adGroupPage.searchAdGroup(adGroupName1);
        assertConditionTrue(adGroupPage.getAdGroupTableText(2, 3).contains(adGroupName1));

        ArrayList<String> searchResults = adGroupPage.getTableColumn();
        assertConditionTrue(1 == searchResults.size());
        assertConditionTrue(adGroupName1.equalsIgnoreCase(searchResults.get(0).trim()));
    }

    @Test
    public void testAdgroupNamePartialSearch() throws Exception {
        String partialText = "adgroup";

        MyAgencyPage agencyPage = login();
        AdGroupPage adGroupPage = agencyPage.navigateToAdGroupTab(agencyId, ((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
        adGroupPage.loadingStatus();
        adGroupPage.searchAdGroup(partialText);
        assertConditionTrue(adGroupPage.getAdGroupTableText(2, 3).contains(adGroupName1));

        ArrayList<String> searchResults = adGroupPage.getTableColumn();
        for (String clients : searchResults) {
            assertConditionTrue(clients.contains(partialText));
        }
    }

    @Test
    public void testAdGroupNameInvalidSearch() throws Exception {
        String inValidAdGroupName = "invalid";

        MyAgencyPage agencyPage = login();
        AdGroupPage adGroupPage = agencyPage.navigateToAdGroupTab(agencyId, ((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
        adGroupPage.loadingStatus();
        adGroupPage.searchAdGroup(inValidAdGroupName);
        assertConditionTrue(!adGroupPage.getAdGroupTableText(2, 3).contains(adGroupName1));


        ArrayList<String> searchResults = adGroupPage.getTableColumn();
        assertConditionTrue(0 == searchResults.size());
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();

            Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = createBudget(account.getAccountId(), 100, "standard");
            Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
            JSONObject adGroup1 = new JSONObject();
            adGroup1.put("adGroupName", adGroupName1);
            JSONObject adGroup2 = new JSONObject();
            String adGroupName2 = "Selenium adgroup";
            adGroup2.put("adGroupName", adGroupName2);
            createAdGroupOnly(account.getAccountId(), campaign.getCampaignId(), adGroup1);
            createAdGroupOnly(account.getAccountId(), campaign.getCampaignId(), adGroup2);

            testData.put("account", account);
            testData.put("campaign", campaign);
        }
        return testData;
    }

}
