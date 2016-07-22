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
import java.util.HashMap;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ViewAdGroupTableTest extends BaseUITest {

    String[] AdGroupsLabels = {"Status", "Ad Group", "Serving Status", "Default Max. CPC", "Clicks", "Impr.", "CTR", "Avg. CPC", "Cost", "Avg. Pos."};
    private static Map<String, Object> testData;

    @Test
    public void testTableLabels() throws Exception {
        MyAgencyPage agencyPage = login();
        AdGroupPage adGroupPage = agencyPage.navigateToAdGroupTab(agencyId, ((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
        for (String adGroupLabel : AdGroupsLabels) {
            if (!adGroupPage.checkAdGroupHeader(adGroupLabel)) {
                throw new NullPointerException("Element does not exist [" + adGroupLabel + "]");
            }
        }
    }

    @Test
    public void testSortTable() throws Exception {
        MyAgencyPage agencyPage = login();
        AdGroupPage adGroupPage = agencyPage.navigateToAdGroupTab(agencyId, ((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
        adGroupPage.loadingStatus();
        String[] adGroupName = (String[]) getTestData().get("adGroupName");
        Arrays.sort(adGroupName);
        adGroupPage.clickTableHeader(3);
        assertConditionTrue(adGroupPage.getAdGroupTableText(2, 3).equals(adGroupName[0]));
        assertConditionTrue(adGroupPage.getAdGroupTableText(3, 3).equals(adGroupName[1]));
        assertConditionTrue(adGroupPage.getAdGroupTableText(4, 3).equals(adGroupName[2]));
        adGroupPage.clickTableHeader(3);
        assertConditionTrue(adGroupPage.getAdGroupTableText(2, 3).equals(adGroupName[2]));
        assertConditionTrue(adGroupPage.getAdGroupTableText(3, 3).equals(adGroupName[1]));
        assertConditionTrue(adGroupPage.getAdGroupTableText(4, 3).equals(adGroupName[0]));
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            String adGroupName[] = {"AdGroup Life" + System.currentTimeMillis(), "AdGroup Health" + System.currentTimeMillis(), "AdGroup YP" + System.currentTimeMillis()};

            Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = createBudget(account.getAccountId(), 100, "standard");
            Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
            for (String adGroups : adGroupName) {
                JSONObject adGroup1 = new JSONObject();
                adGroup1.put("adGroupName", adGroups);
                createAdGroupOnly(account.getAccountId(), campaign.getCampaignId(), adGroup1);
            }
            testData.put("account", account);
            testData.put("campaign", campaign);
            testData.put("adGroupName", adGroupName);
        }

        return testData;
    }
}