package com.yp.pm.kp.be.impl;

import com.yp.BaseTest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
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
public class KeplerBudgetImplTest extends BaseTest {

    @Inject
    KeplerAccountImpl accountDao;
    @Inject
    KeplerBudgetImpl budgetDao;

    private static Map<String, Object> testData;

    @Test
    public void testCreateBudget() throws Exception {
        assertNotNull(getTestData().get("budget"));
    }

    @Test
    public void testGetBudgetById() throws Exception {
        assertNotNull(budgetDao.getBudgetById(((Account) getTestData().get("account")).getAccountId(), ((Budget) getTestData().get("budget")).getBudgetId()));
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<String, Object>();
            Account account = accountDao.createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = budgetDao.createBudget(account.getAccountId(), budgetAmount, deliveryMethod, null);

            testData.put("account", account);
            testData.put("budget", budget);
        }

        return testData;
    }
}