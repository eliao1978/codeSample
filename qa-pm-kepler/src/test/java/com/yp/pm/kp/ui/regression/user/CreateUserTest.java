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

import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreateUserTest extends BaseUITest {

    @Test
    public void assertFormSubmission() throws Exception {
        String name = "auto_" + System.currentTimeMillis();
        String email = name + "@yp.com";

        MyAgencyPage agencyPage = login();
        try {
            AccountPage accountPage = agencyPage.navigateToAccountPage(agencyId);
            AccountAccessPage accountAccessPage = accountPage.clickAccountAccess();
            accountAccessPage.clickAddUserButton();
            accountAccessPage.enterUserEmail(email);
            accountAccessPage.enterUserName(name);
            accountAccessPage.submitForm();

            List<Map<String, Object>> queryList = DBUtil.queryForList("SELECT * FROM PM_USER_DETAIL WHERE EMAIL =\'" + email + "\'");
            assertConditionTrue(queryList.size() == 1);
            assertConditionTrue(queryList.get(0).get("EMAIL").toString().equalsIgnoreCase(email));
        } finally {
            DBUtil.update("DELETE FROM PM_USER_DETAIL WHERE EMAIL = '" + email + "\'");
        }
    }

    @Test
    public void assertCancelFormSubmission() throws Exception {
        String name = "auto_" + System.currentTimeMillis();
        String email = name + "@yp.com";

        MyAgencyPage agencyPage = login();
        AccountPage accountPage = agencyPage.navigateToAccountPage(agencyId);
        AccountAccessPage accountAccessPage = accountPage.clickAccountAccess();
        accountAccessPage.clickAddUserButton();
        accountAccessPage.enterUserEmail(email);
        accountAccessPage.enterUserName(name);
        accountAccessPage.cancelFormSubmission();

        List<Map<String, Object>> queryList = DBUtil.queryForList("SELECT * FROM PM_USER_DETAIL WHERE EMAIL =\'" + email + "\'");
        assertConditionTrue(queryList.size() == 0);
    }
}