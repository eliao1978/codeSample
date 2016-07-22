package com.yp.pm.kp.ui.regression.conversionTracking;

import com.yp.enums.selenium.LocatorTypeEnum;
import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.ui.CampaignPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import com.yp.pm.kp.ui.UIMapper;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.HashMap;
import java.util.Map;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.enums.model.CampaignStatusEnum;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.ui.CampaignPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

/**
 * Created by vr0262 on 1/20/16.
 */

public class CreateConversionForReadOnlyTest extends BaseUITest {

    private static Map<String, Object> testData;

    @Test
    public void testCreateConversionForReadOnly() throws Exception {
        String campaignName = "campaign_" + System.currentTimeMillis();

        long accountId = ((Account) getTestData().get("account")).getAccountId();
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        createCampaign(account.getAccountId(),budget.getBudgetId());

        String conversion_name = "Conversion Page";
        MyAgencyPage agencyPage = loginRootReadOnly();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, account.getAccountId());
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        campaignPage.click(UIMapper.CONVERSION_TAB, LocatorTypeEnum.XPATH);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        campaignPage.clickCreateConversionButton();
        assertConditionTrue(campaignPage.isCreateConversionButtonActive());

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
