package com.yp.pm.kp.ui.regression.campaign;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.enums.model.CampaignStatusEnum;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.ui.MyAgencyPage;
import com.yp.pm.kp.ui.SettingsPage;
import com.yp.pm.kp.ui.UIMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class CreateCampaignAtSettingTabTest extends BaseUITest {

    private static Map<String, Object> testData;

    @Test
    public void testCreateSettingsCampaign() throws Exception {
        String campaignName = "campaign_" + System.currentTimeMillis();
        String adGroupName = "adgroup_" + System.currentTimeMillis();
        String headline = "headline test";
        String desc1 = "desc1";
        String desc2 = "desc2";
        String displayURL = "www.yp.com";
        String destURL = "www.yp.com";

        long accountId = ((Account) getTestData().get("account")).getAccountId();

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToAllSettingsTab(agencyId, accountId);
        settingsPage.clickCreateCampaign();
        settingsPage.enterCampaignName(campaignName);
        settingsPage.selectBudget();
        settingsPage.clickSubmit();
        settingsPage.enterAdGroupName(adGroupName);
        settingsPage.enterHeadlineOne(headline);
        settingsPage.enterDescriptionOne(desc1);
        settingsPage.enterDescriptionTwo(desc2);
        settingsPage.enterDisplayUrl(displayURL);
        settingsPage.enterDestinationUrl(destURL);
        settingsPage.enterAdGroupBid("1");
        settingsPage.clickSaveAdgroup();
        settingsPage.waitForElementNotVisible(UIMapper.SUBMIT_AD_GROUP);
        settingsPage.waitForElementNotVisible(UIMapper.LOADING);
        assertConditionTrue(settingsPage.getTableText(2, 3).contains(adGroupName));

        Campaign campaign = getCampaignByName(accountId, campaignName);
        assertObjectNotNull(campaign.getCampaignId());
        assertObjectEqual(campaign.getCampaignName(), campaignName);
        assertObjectEqual(campaign.getAccountId(), accountId);
        assertObjectEqual(campaign.getCampaignStatus().toString(), CampaignStatusEnum.ACTIVE.toString());
    }

    @Test
    public void testCancelCreateSettingsCampaign() throws Exception {
        String campaignName = "campaign_" + System.currentTimeMillis();

        long accountId = ((Account) getTestData().get("account")).getAccountId();

        MyAgencyPage agencyPage = login();
        SettingsPage settingsPage = agencyPage.navigateToAllSettingsTab(agencyId, accountId);
        settingsPage.clickCreateCampaign();
        settingsPage.enterCampaignName(campaignName);
        settingsPage.clickCancelCampaign();
        assertConditionTrue(!settingsPage.getTableText(2, 3).contains(campaignName));

        assertConditionTrue(getCampaignByName(accountId, campaignName) == null);
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