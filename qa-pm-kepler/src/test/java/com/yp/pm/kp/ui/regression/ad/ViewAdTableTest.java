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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class ViewAdTableTest extends BaseUITest {
    String[] AdLabels = {"Status", "Ad", "Ad Group", "Serving Status", "Clicks", "Impr.", "CTR", "Avg. CPC", "Cost", "Avg. Pos."};
    String[] headline = {"Ad Life", "Ad Health", "Ad YP"};
    String[] description1 = {"life", "health", "promotion"};
    String[] description2 = {"insurance", "insurance", "insurance"};
    String[] displayUrl = {"www.yp1.com", "www.yp.com", "www.yp2.com"};
    String newline = System.getProperty("line.separator");

    @Test
    public void testTableLabels() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());

        MyAgencyPage agencyPage = login();
        AdPage adPage = agencyPage.navigateToAdTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        adPage.loadingStatus();
        for (String adLevel : AdLabels) {
            if (!adPage.checkAdHeader(adLevel)) {
                throw new NullPointerException("Element does not exist [" + adLevel + "]");
            }
        }
    }

    @Test
    public void testSortTable() throws Exception {
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

        MyAgencyPage agencyPage = login();
        AdPage adPage = agencyPage.navigateToAdTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        adPage.loadingStatus();
        Arrays.sort(headline);
        Arrays.sort(description1);
        Arrays.sort(description2);
        Arrays.sort(displayUrl);
        adPage.clickTableHeader(3);
        assertObjectEqual(adPage.getAdTableText(2, 3), headline[0] + newline + description1[0] + newline + description2[0] + newline + displayUrl[0]);
        assertObjectEqual(adPage.getAdTableText(3, 3), headline[1] + newline + description1[1] + newline + description2[1] + newline + displayUrl[1]);
        assertObjectEqual(adPage.getAdTableText(4, 3), headline[2] + newline + description1[2] + newline + description2[2] + newline + displayUrl[2]);
        adPage.clickTableHeader(3);
        assertObjectEqual(adPage.getAdTableText(2, 3), headline[2] + newline + description1[2] + newline + description2[2] + newline + displayUrl[2]);
        assertObjectEqual(adPage.getAdTableText(3, 3), headline[1] + newline + description1[1] + newline + description2[1] + newline + displayUrl[1]);
        assertObjectEqual(adPage.getAdTableText(4, 3), headline[0] + newline + description1[0] + newline + description2[0] + newline + displayUrl[0]);
    }


    @Test
    public void testAdsShowRowsDisplay() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());

        JSONArray adArray = new JSONArray();
        for (int i = 0; i < 50; i++) {
            JSONObject ad1 = new JSONObject();
            ad1.put("adType", "TEXT");
            ad1.put("headLine", "Auto" + i);
            ad1.put("description1", "Insurance" + i);
            ad1.put("description2", "test" + i);
            ad1.put("displayUrl", "www.yp" + i + ".com");
            adArray.add(ad1);

        }
        createAd(account.getAccountId(), adGroup.getAdGroupId(), adArray);

        MyAgencyPage agencyPage = login();
        AdPage adPage = agencyPage.navigateToAdTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        adPage.loadingStatus();
        adPage.selectFromRowDropdown(0);
        assertObjectEqual(adPage.getTableSize(), 12.0);
        adPage.selectFromRowDropdown(1);
        assertObjectEqual(adPage.getTableSize(), 32.0);
        adPage.selectFromRowDropdown(2);
        assertObjectEqual(adPage.getTableSize(), 52.0);
    }

    //TODO: add new test scenario
    @Ignore
    public void testDisapproveReason() throws Exception {

    }

    @Test
    public void testPagination() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());

        JSONArray adArray = new JSONArray();
        for (int i = 0; i < 50; i++) {
            JSONObject ad1 = new JSONObject();
            ad1.put("adType", "TEXT");
            ad1.put("headLine", "Health" + i);
            ad1.put("description1", "Insurance" + i);
            ad1.put("description2", "test" + i);
            ad1.put("displayUrl", "www.yp" + i + ".com");
            adArray.add(ad1);
        }
        createAd(account.getAccountId(), adGroup.getAdGroupId(), adArray);

        MyAgencyPage agencyPage = login();
        AdPage adPage = agencyPage.navigateToAdTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        adPage.loadingStatus();
        adPage.selectFromRowDropdown(0);
        for (int i = 1; i < 5; i++) {
            adPage.selectFromPageDropdown(i);
            assertObjectEqual(adPage.getTableSize(), 12.0);
        }
        adPage.selectFromRowDropdown(1);
        assertObjectEqual(adPage.getTableSize(), 32.0);
        adPage.selectFromPageDropdown(1);
        assertObjectEqual(adPage.getTableSize(), 22.0);
    }
}


