package com.yp.pm.kp.ui.regression.user;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.ui.AccountAccessPage;
import com.yp.pm.kp.ui.AccountPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import com.yp.util.DBUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ViewUserTest extends BaseUITest {

    @Test
    public void assertTableHeader() throws Exception {
        String usersInvitedTableColumns[] = {"Invited user", "Invited on", "Invited by", "Access level", "Actions"};
        String usersWithAccountAccess[] = {"ypSearch Marketplace℠ user", "Approved on", "Approved by", "Last logged in", "Access level", "Actions"};

        MyAgencyPage agencyPage = login();
        AccountPage accountPage = agencyPage.navigateToAccountPage(agencyId);
        AccountAccessPage accountAccessPage = accountPage.clickAccountAccess();

        int i = 1;
        for (String label : usersInvitedTableColumns) {
            assertObjectEqual(accountAccessPage.getTextFromUserInvitedTableHeader(i), label);
            i += 1;
        }

        int j = 1;
        for (String label : usersWithAccountAccess) {
            assertObjectEqual(accountAccessPage.getTextFromUserAccountAccessHeader(j), label);
            j += 1;
        }
    }

    @Test
    public void assertInvitedUserDetails() throws Exception {
        String email = "auto_" + System.currentTimeMillis() + "@yp.com";
        String name = "auto_" + System.currentTimeMillis();

        MyAgencyPage agencyPage = login();
        try {
            AccountPage accountPage = agencyPage.navigateToAccountPage(agencyId);
            AccountAccessPage accountAccessPage = accountPage.clickAccountAccess();
            accountAccessPage.clickAddUserButton();
            accountAccessPage.enterUserEmail(email);
            accountAccessPage.enterUserName(name);
            accountAccessPage.submitForm();

            accountAccessPage.searchInvitedUserByName(name);
            assertConditionTrue(accountAccessPage.getTextFromUserInvitedTable(1, 1).contains(name));
            assertConditionTrue(accountAccessPage.getTextFromUserInvitedTable(1, 2).contains(accountAccessPage.getCurrentDate()));
            assertConditionTrue(accountAccessPage.getTextFromUserInvitedTable(1, 3).contains(System.getProperty("ui.username")));
            assertConditionTrue(accountAccessPage.getTextFromUserInvitedTable(1, 4).contains("Read Only"));
            assertObjectEqual(accountAccessPage.getInvitedUserTableRowCount(), 1);

            accountAccessPage.searchUserWithAccountAccessByName(name);
            assertObjectEqual(accountAccessPage.getUserWithAccountAccessTableRowCount(), 0);
            assertObjectNull(accountAccessPage.getTextFromUserAccountAccessTable(1, 5));
        } finally {
            DBUtil.update("DELETE FROM PM_USER_DETAIL WHERE EMAIL = '" + email + "\'");
        }
    }
}