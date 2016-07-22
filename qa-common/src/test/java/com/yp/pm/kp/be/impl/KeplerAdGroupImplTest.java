package com.yp.pm.kp.be.impl;

import com.yp.BaseTest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.AdGroup;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
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
public class KeplerAdGroupImplTest extends BaseTest {

    @Inject
    KeplerAccountImpl accountDao;
    @Inject
    KeplerBudgetImpl budgetDao;
    @Inject
    KeplerCampaignImpl campaignDao;
    @Inject
    KeplerAdGroupImpl adGroupDao;

    private static Map<String, Object> testData;

    @Test
    public void testCreateAdGroupOnly() throws Exception {
        assertNotNull(getTestData().get("adGroup"));
    }

    @Test
    public void testGetAdGroupById() throws Exception {
        assertNotNull(adGroupDao.getAdGroupById(((Account) getTestData().get("account")).getAccountId(), ((AdGroup) getTestData().get("adGroup")).getAdGroupId()));
    }

    @Test
    public void testGetAdGroupByCampaignId() throws Exception {
        assertNotNull(adGroupDao.getAdGroupByCampaignId(((Account) getTestData().get("account")).getAccountId(), ((Campaign) getTestData().get("campaign")).getCampaignId()));
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<String, Object>();
            Account account = accountDao.createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = budgetDao.createBudget(account.getAccountId(), budgetAmount, deliveryMethod, null);
            Campaign campaign = campaignDao.createCampaignOnly(account.getAccountId(), budget.getBudgetId(), null);
            AdGroup adGroup = adGroupDao.createAdGroupOnly(account.getAccountId(), campaign.getCampaignId(), null);

            testData.put("account", account);
            testData.put("budget", budget);
            testData.put("campaign", campaign);
            testData.put("adGroup", adGroup);
        }

        return testData;
    }
}