package com.yp.pm.kp.be.impl;

import com.yp.BaseTest;
import com.yp.pm.kp.model.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class KeplerAdImplTest extends BaseTest {

    @Inject
    KeplerAccountImpl accountDao;
    @Inject
    KeplerBudgetImpl budgetDao;
    @Inject
    KeplerCampaignImpl campaignDao;
    @Inject
    KeplerAdGroupImpl adGroupDao;
    @Inject
    KeplerAdImpl adDao;

    private static Map<String, Object> testData;

    @Test
    public void testCreateAd() throws Exception {
        assertNotNull(getTestData().get("ad"));
    }

    @Test
    public void testGetAdById() throws Exception {
        assertNotNull(adDao.getAdById(((Account) getTestData().get("account")).getAccountId(), ((Ad) getTestData().get("ad")).getAdId()));
    }

    @Test
    public void testGetAdByAdGroupId() throws Exception {
        assertNotNull(adDao.getAdByAdGroupId(((Account) getTestData().get("account")).getAccountId(), ((AdGroup) getTestData().get("adGroup")).getAdGroupId()));
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<String, Object>();
            Account account = accountDao.createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = budgetDao.createBudget(account.getAccountId(), budgetAmount, deliveryMethod, null);
            Campaign campaign = campaignDao.createCampaignOnly(account.getAccountId(), budget.getBudgetId(), null);
            AdGroup adGroup = adGroupDao.createAdGroupOnly(account.getAccountId(), campaign.getCampaignId(), null);
            Ad ad = adDao.createAd(account.getAccountId(), adGroup.getAdGroupId(), null).get(0);

            testData.put("account", account);
            testData.put("budget", budget);
            testData.put("campaign", campaign);
            testData.put("adGroup", adGroup);
            testData.put("ad", ad);
        }

        return testData;
    }
}