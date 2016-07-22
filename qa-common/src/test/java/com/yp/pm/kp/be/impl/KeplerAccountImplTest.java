package com.yp.pm.kp.be.impl;

import com.yp.BaseTest;
import com.yp.pm.kp.model.domain.Account;
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
public class KeplerAccountImplTest extends BaseTest {

    @Inject
    KeplerAccountImpl accountDao;
    private static Map<String, Object> testData;

    @Test
    public void testCreateAccount() throws Exception {
        assertNotNull(((Account) getTestData().get("account")).getAccountId());
    }

    @Test
    public void testGetAccountByName() throws Exception {
        assertNotNull(accountDao.getAccountByName(((Account) getTestData().get("account")).getClientName()));
    }

    @Test
    public void testGetActiveAccountByName() throws Exception {
        assertNotNull(accountDao.getActiveAccountByName(((Account) getTestData().get("account")).getClientName()));
    }

    @Test
    public void testGetAccountById() throws Exception {
        assertNotNull(accountDao.getAccountById(((Account) getTestData().get("account")).getAccountId()));
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<String, Object>();
            Account account = accountDao.createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            testData.put("account", account);
        }
        return testData;
    }
}