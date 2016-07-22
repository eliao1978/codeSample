package com.yp.pm.kp.ui.regression.ad;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.AdGroup;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.ui.AdPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class SearchAdTest extends BaseUITest {

    private static Map<String, Object> testData;

    String[] headline = {"Ad Life", "Ad Health", "Ad YP"};
    String[] description1 = {"life", "health", "promotion"};
    String[] description2 = {"insurance", "insurance", "insurance"};
    String[] displayUrl = {"www.yp1.com", "www.yp.com", "www.yp2.com"};


    @Test
    public void testAdNameSearch() throws Exception {
        String adNameFull = "Ad Life";
        MyAgencyPage agencyPage = login();
        AdPage adPage = agencyPage.navigateToAdTab(agencyId, ((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
        Arrays.sort(headline);
        Arrays.sort(description1);
        Arrays.sort(description2);
        Arrays.sort(displayUrl);
        adPage.loadingStatus();
        adPage.searchAd(adNameFull);

        ArrayList<String> searchResults = adPage.getTableColumn();
        assertConditionTrue(1 == searchResults.size());
        assertConditionTrue(searchResults.get(0).trim().contains(adNameFull));
    }

    @Test
    public void testPartialAdNameSearch() throws Exception {
        String adNamePartial = "Ad";
        MyAgencyPage agencyPage = login();
        AdPage adPage = agencyPage.navigateToAdTab(agencyId, ((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
        Arrays.sort(headline);
        Arrays.sort(description1);
        Arrays.sort(description2);
        Arrays.sort(displayUrl);
        adPage.loadingStatus();
        adPage.searchAd(adNamePartial);

        ArrayList<String> searchResults = adPage.getTableColumn();
        assertConditionTrue(3 == searchResults.size());
        for (String clients : searchResults) {
            assertConditionTrue(clients.contains(adNamePartial));
        }
    }


    @Test
    public void testAdNameInvalidSearch() throws Exception {
        String adNameInvalidText = "sports";
        MyAgencyPage agencyPage = login();
        AdPage adPage = agencyPage.navigateToAdTab(agencyId, ((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId());
        Arrays.sort(headline);
        Arrays.sort(description1);
        Arrays.sort(description2);
        Arrays.sort(displayUrl);
        adPage.loadingStatus();
        adPage.searchAd(adNameInvalidText);

        ArrayList<String> searchResults = adPage.getTableColumn();
        assertConditionTrue(0 == searchResults.size());
    }


    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();

            Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = createBudget(account.getAccountId(), 100, "standard");
            Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
            AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());
            JSONArray adArray = new JSONArray();
            for (int i = 0; i < headline.length; i++) {
                JSONObject ad1 = new JSONObject();
                ad1.put("adType", "TEXT");
                ad1.put("headLine", headline[i]);
                ad1.put("description1", description1[i]);
                ad1.put("description2", description2[i]);
                ad1.put("displayUrl", displayUrl[i]);
                adArray.add(ad1);
            }
            createAd(account.getAccountId(), adGroup.getAdGroupId(), adArray);

            testData.put("account", account);
            testData.put("campaign", campaign);
            testData.put("adGroup", adGroup);
        }

        return testData;
    }


}