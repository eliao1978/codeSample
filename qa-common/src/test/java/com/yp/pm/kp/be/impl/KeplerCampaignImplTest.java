package com.yp.pm.kp.be.impl;

import com.yp.BaseTest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class KeplerCampaignImplTest extends BaseTest {

    @Inject
    KeplerAccountImpl accountDao;
    @Inject
    KeplerBudgetImpl budgetDao;
    @Inject
    KeplerCampaignImpl campaignDao;

    private static Map<String, Object> testData;

    @Test
    public void testCreateCampaign() throws Exception {
        Account account = accountDao.createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = budgetDao.createBudget(account.getAccountId(), budgetAmount, deliveryMethod, null);
        List<Campaign> campaign = campaignDao.createCampaign(account.getAccountId(), budget.getBudgetId(), null);
        assertTrue(campaign.size() == 1);
    }

    @Test
    public void testCreateCampaignOnly() throws Exception {
        assertNotNull(getTestData().get("campaign"));
    }

    @Test
    public void testGetCampaignById() throws Exception {
        assertNotNull(campaignDao.getCampaignById(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId()));
    }

    @Test
    public void testGetCampaignByAccountId() throws Exception {
        assertNotNull(campaignDao.getCampaignByAccountId(((Account) getTestData().get("account")).getAccountId()));
    }

    @Test
    public void testUpdateCampaign() throws Exception {

    }

    @Test
    public void testUpdateCampaigns() throws Exception {

    }

    @Test
    public void testGetCampaignGeoCodeByCampaignId() throws Exception {

    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<String, Object>();
            Account account = accountDao.createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = budgetDao.createBudget(account.getAccountId(), budgetAmount, deliveryMethod, null);
            Campaign campaign = campaignDao.createCampaignOnly(account.getAccountId(), budget.getBudgetId(), null);

            testData.put("account", account);
            testData.put("budget", budget);
            testData.put("campaign", campaign);
        }

        return testData;
    }
}