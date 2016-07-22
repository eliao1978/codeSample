package com.yp.pm.kp.ui.regression.keyword;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.*;
import com.yp.pm.kp.ui.KeywordPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class HelpLinkTest extends BaseUITest {

    private static Map<String, Object> testData;
    @Test
    public void testLinkToDki() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long campaignId = ((Campaign) getTestData().get("campaign")).getCampaignId();
        MyAgencyPage agencyPage = login();
        KeywordPage keywordPage = agencyPage.navigateToKeywordTab(agencyId, accountId, campaignId);
        keywordPage.clickDkiHelpLink();
        Thread.sleep(1000);
    }
    @Test
    public void testLinkToDli() throws Exception {
        long accountId = ((Account) getTestData().get("account")).getAccountId();
        long campaignId = ((Campaign) getTestData().get("campaign")).getCampaignId();
        MyAgencyPage agencyPage = login();
        KeywordPage keywordPage = agencyPage.navigateToKeywordTab(agencyId, accountId, campaignId);
        keywordPage.clickDliHelpLink();
        Thread.sleep(1000);
    }
    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = createBudget(account.getAccountId(), 100, "standard");
            Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
            AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());
            testData.put("account", account);
            testData.put("campaign", campaign);
            testData.put("adGroup", adGroup);
        }
        return testData;
    }
}
