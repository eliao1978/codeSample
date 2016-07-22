package com.yp.pm.kp.ui.regression.account;

import com.yp.pm.kp.BaseUITest;
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
public class SearchAccountTest extends BaseUITest {

    @Test
    public void assertSearchAccountWithExactNameMatch() throws Exception {
        String clientName = "auto_" + System.currentTimeMillis();

        MyAgencyPage agencyPage = login();
        AccountPage accountPage = agencyPage.navigateToAccountPage(agencyId);
        accountPage.clickCreateAccountButton();
        accountPage.enterClientName(clientName);
        accountPage.selectTimeZone();
        accountPage.submitForm();
        agencyPage.navigateToAccountPage(agencyId);
        accountPage.searchByClientName(clientName);

        // ui assertion
        assertObjectEqual(accountPage.getTextFromStatsTable(2, 2), clientName);
    }

    @Test
    public void assertSearchAccountWithPartialNameMatch() throws Exception {
        MyAgencyPage agencyPage = login();
        AccountPage accountPage = agencyPage.navigateToAccountPage(agencyId);
        accountPage.searchByClientName("auto");

        // ui assertion
        assertConditionTrue(accountPage.getTextFromStatsTable(2, 2).toLowerCase().contains("auto"));
    }


    @Test
    public void assertSearchAccountWithInvalidName() throws Exception {
        String invalidClientName = "Invalid Account 123";

        MyAgencyPage agencyPage = login();
        AccountPage accountPage = agencyPage.navigateToAccountPage(agencyId);
        accountPage.searchByClientName(invalidClientName);

        // ui assertion
        assertObjectNull(accountPage.getTextFromStatsTable(2, 2));
    }
}
