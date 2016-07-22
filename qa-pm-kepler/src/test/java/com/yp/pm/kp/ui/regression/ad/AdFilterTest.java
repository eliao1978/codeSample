
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

import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class AdFilterTest extends BaseUITest {
    String[] headline = {"Ad Life", "Ad Health", "Ad YP"};
    String[] description1 = {"life", "health", "promotion"};
    String[] description2 = {"insurance", "insurance", "insurance"};
    String[] displayUrl = {"www.yp1.com", "www.yp.com", "www.yp2.com"};
    String newline = System.getProperty("line.separator");

    @Test
    public void testAllButDeleted() throws Exception {
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
        adPage.clickFilterButton();
        adPage.clickTableHeader(3);
        adPage.selectCheckBox(2, 1);
        adPage.updateAdDropdown(1);//Pause Ad
        adPage.selectCheckBox(3, 1);
        adPage.updateAdDropdown(2);//Delete Ad
        adPage.selectAdDropdown(2);//Select all but deleted
        assertObjectEqual(adPage.getTableSize(), 4.0);
        assertObjectEqual(adPage.getAdTableText(2, 3), headline[0] + newline + description1[0] + newline + description2[0] + newline + displayUrl[0]);
        assertObjectEqual(adPage.getAdTableText(3, 3), headline[2] + newline + description1[2] + newline + description2[2] + newline + displayUrl[2]);
    }



    @Test
    public void testAllEnabled() throws Exception {
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
        adPage.clickFilterButton();
        adPage.clickTableHeader(3);
        adPage.selectCheckBox(2, 1);
        adPage.updateAdDropdown(1);//Pause Ad
        adPage.selectCheckBox(3, 1);
        adPage.updateAdDropdown(2);//Delete Ad
        adPage.selectAdDropdown(1);//Select all Enabled
        assertObjectEqual(adPage.getTableSize(), 3.0);
        assertObjectEqual(adPage.getAdTableText(2, 3), headline[2] + newline + description1[2] + newline + description2[2] + newline + displayUrl[2]);

    }



    @Test
    public void testAllAds() throws Exception {
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
        adPage.clickFilterButton();
        adPage.clickTableHeader(3);
        adPage.selectCheckBox(2, 1);
        adPage.updateAdDropdown(1);//Pause Ad
        adPage.selectCheckBox(3, 1);
        adPage.updateAdDropdown(2);//Delete Ad
        adPage.selectAdDropdown(0);
        assertObjectEqual(adPage.getTableSize(), 5.0);
        assertObjectEqual(adPage.getAdTableText(2, 3), headline[0] + newline + description1[0] + newline + description2[0] + newline + displayUrl[0]);
        assertObjectEqual(adPage.getAdTableText(3, 3), headline[1] + newline + description1[1] + newline + description2[1] + newline + displayUrl[1]);
        assertObjectEqual(adPage.getAdTableText(4, 3), headline[2] + newline + description1[2] + newline + description2[2] + newline + displayUrl[2]);

    }

}

