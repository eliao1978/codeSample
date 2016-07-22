package com.yp.pm.kp.ui.regression.account;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.ui.AccountPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreateAccountTest extends BaseUITest {

    @Test
    public void assertFormSubmission() throws Exception {
        String clientName = "auto_" + System.currentTimeMillis();

        MyAgencyPage agencyPage = login();
        AccountPage accountPage = agencyPage.navigateToAccountPage(agencyId);
        accountPage.clickCreateAccountButton();
        accountPage.enterClientName(clientName);
        accountPage.selectTimeZone();
        accountPage.submitForm();

        // ui assertion
        agencyPage.navigateToAccountPage(agencyId);
        accountPage.searchByClientName(clientName);
        assertConditionTrue(accountPage.getTextFromStatsTable(2, 2).contains(clientName)); // client name at row 1 column 1

        // db assertion
        Account newAccount = getAccountByName(clientName);
        assertObjectNotNull(newAccount.getAccountId());
        assertObjectEqual(newAccount.getClientName(), clientName);
    }

    @Test
    public void assertCancelFormSubmission() throws Exception {
        String clientName = "auto_" + System.currentTimeMillis();

        MyAgencyPage agencyPage = login();
        AccountPage accountPage = agencyPage.navigateToAccountPage(agencyId);
        accountPage.clickCreateAccountButton();
        accountPage.enterClientName(clientName);
        accountPage.selectTimeZone();
        accountPage.cancelFormSubmission();

        // ui assertion
        agencyPage.navigateToAccountPage(agencyId);
        accountPage.searchByClientName(clientName);
        assertObjectNull(accountPage.getTextFromStatsTable(2, 2)); // client name at row 1 column 1

        // db assertion
        assertConditionTrue(getAccountByName(clientName) == null);
    }
}