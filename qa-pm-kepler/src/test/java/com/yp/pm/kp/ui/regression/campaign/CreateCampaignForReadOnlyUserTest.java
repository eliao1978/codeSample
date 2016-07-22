package com.yp.pm.kp.ui.regression.campaign;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.ui.CampaignPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import com.yp.pm.kp.ui.UIMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class CreateCampaignForReadOnlyUserTest extends BaseUITest {

    private static Map<String, Object> testData;

    @Test
    public void testCreateCampaignForReadOnlyUser() throws Exception {

        long accountId = ((Account) getTestData().get("account")).getAccountId();

        MyAgencyPage agencyPage = loginRootReadOnly();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, accountId);
        campaignPage.clickCreateCampaignButton();
        assertConditionTrue("Button was not disabled", !campaignPage.isElementEnabled(UIMapper.CREATE_CAMPAIGN_BUTTON));
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = createBudget(account.getAccountId(), 100, "standard");
            testData.put("account", account);
            testData.put("budget", budget);
        }
        return testData;
    }
}