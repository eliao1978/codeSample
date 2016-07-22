package com.yp.pm.kp.ui.regression.login;

import com.yp.enums.selenium.LocatorTypeEnum;
import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.AdGroup;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.ui.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class CopyrightsTest extends BaseUITest {
    String[] headline = {"Ad Life", "Ad Health", "Ad YP"};
    String[] description1 = {"life", "health", "promotion"};
    String[] description2 = {"insurance", "insurance", "insurance"};
    String[] displayUrl = {"www.yp1.com", "www.yp.com", "www.yp2.com"};

    @Test
    public void verifyCopyrightsTests() throws Exception
    {
        String textCopyright = "© 2016 YP LLC. All rights reserved";
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());
        long campaignId = campaign.getCampaignId();
        long accountId = account.getAccountId();

        JSONArray adArray = new JSONArray();
        for (int i = 0; i < headline.length; i++) {
            JSONObject ad1 = new JSONObject();
            ad1.put("adType", "TEXT");
            ad1.put("headLine", headline[i]);
            ad1.put("description1", description1[i]);
            ad1.put("description2", description2[i]);
            ad1.put("displayUrl", displayUrl[i]);
            adArray.add(ad1);
        }
        createAd(account.getAccountId(), adGroup.getAdGroupId(), adArray);
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.openBaseUrl();
        assertConditionTrue(loginPage.getTextFromElement(UIMapper.COPYRIGHT_TEXT_MATCH,LocatorTypeEnum.XPATH).contains(textCopyright));
        MyAgencyPage agencyPage = login();
        assertConditionTrue(loginPage.getTextFromElement(UIMapper.COPYRIGHT_TEXT_MATCH,LocatorTypeEnum.XPATH).contains(textCopyright));
        AccountPage accountPage = agencyPage.navigateToAccountPage(agencyId);
        assertConditionTrue(accountPage.getTextFromElement(UIMapper.COPYRIGHT_TEXT_MATCH,LocatorTypeEnum.XPATH).contains(textCopyright));
        AdPage adPage = agencyPage.navigateToAdTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        assertConditionTrue(adPage.getTextFromElement(UIMapper.COPYRIGHT_TEXT_MATCH,LocatorTypeEnum.XPATH).contains(textCopyright));
        AdExtensionLocationExtension locationExtension = agencyPage.navigateToAdExtensionsLocationTab(agencyId, account.getAccountId(), campaignId);
        assertConditionTrue(locationExtension.getTextFromElement(UIMapper.COPYRIGHT_TEXT_MATCH,LocatorTypeEnum.XPATH).contains(textCopyright));
        AdExtensionLogoExtension logoExtension = agencyPage.navigateToLogoExtensionsLocationTab(agencyId, account.getAccountId(), campaignId);
        assertConditionTrue(logoExtension.getTextFromElement(UIMapper.COPYRIGHT_TEXT_MATCH,LocatorTypeEnum.XPATH).contains(textCopyright));
        AdExtensionCallExtension callExtension = agencyPage.navigateToCallExtensionsLocationTab(agencyId, account.getAccountId(), campaignId);
        assertConditionTrue(callExtension.getTextFromElement(UIMapper.COPYRIGHT_TEXT_MATCH,LocatorTypeEnum.XPATH).contains(textCopyright));
        AdExtensionSiteLinkExtension siteLinkExtension = agencyPage.navigateToAdExtensionsTab(agencyId, account.getAccountId(), campaignId);
        assertConditionTrue(siteLinkExtension.getTextFromElement(UIMapper.COPYRIGHT_TEXT_MATCH,LocatorTypeEnum.XPATH).contains(textCopyright));
        AdGroupPage adGroupPage = agencyPage.navigateToAdGroupTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        assertConditionTrue(adGroupPage.getTextFromElement(UIMapper.COPYRIGHT_TEXT_MATCH,LocatorTypeEnum.XPATH).contains(textCopyright));
        BudgetPage budgetPage = agencyPage.navigateToBudgetPage(agencyId, accountId);
        assertConditionTrue(budgetPage.getTextFromElement(UIMapper.COPYRIGHT_TEXT_MATCH,LocatorTypeEnum.XPATH).contains(textCopyright));
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, accountId);
        assertConditionTrue(campaignPage.getTextFromElement(UIMapper.COPYRIGHT_TEXT_MATCH,LocatorTypeEnum.XPATH).contains(textCopyright));
        SettingsPage settingsPage = agencyPage.navigateToSettingsTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        assertConditionTrue(settingsPage.getTextFromElement(UIMapper.COPYRIGHT_TEXT_MATCH,LocatorTypeEnum.XPATH).contains(textCopyright));
        KeywordPage keywordPage = agencyPage.navigateToKeywordTab(agencyId, accountId, campaignId);
        assertConditionTrue(keywordPage.getTextFromElement(UIMapper.COPYRIGHT_TEXT_MATCH,LocatorTypeEnum.XPATH).contains(textCopyright));
        }
}
