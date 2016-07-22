package com.yp.pm.kp.ui;

import org.openqa.selenium.WebDriver;

public class MyAgencyPage extends SeleniumWrapper {

    public MyAgencyPage(WebDriver driver) throws Exception {
        super(driver);
        waitForElementVisible("div[ui-view='agenciesPane']>div>div[class='pm-body-title']");
        waitForElementNotVisible(UIMapper.LOADING);
        waitForElementNotVisible(UIMapper.LOADING_TABLE);

        if (!isAuthenticated()) {
            throw new Exception("Login failed.");
        }
    }

    private boolean isAuthenticated() {
        return isElementVisible(UIMapper.CUSTOMER_INFO) && isElementVisible("div[ui-view='agenciesPane']");
    }

    public AdGroupPage navigateToAdGroupTabCampaignErrorMessageTest(long agencyId, long accountId) throws Exception {
        open(System.getProperty("ui.server").concat("/cm/agency/").concat(String.valueOf(agencyId)).concat("/accounts/").concat(String.valueOf(accountId)).concat("/adgroups?/").concat(System.getProperty("ui.decryption")));
        return new AdGroupPage(this.driver);
    }

    public AccountPage navigateToAccountPage(long agencyId) throws Exception {
        open(System.getProperty("ui.server").concat("/cm/agency/").concat(String.valueOf(agencyId)).concat("/accounts?").concat(System.getProperty("ui.decryption")));
        return new AccountPage(this.driver);
    }

    public BudgetPage navigateToBudgetPage(long agencyId, long accountId) throws Exception {
        open(System.getProperty("ui.server").concat("/cm/agency/").concat(String.valueOf(agencyId)).concat("/accounts/").concat(String.valueOf(accountId)).concat("/budgets?").concat(System.getProperty("ui.decryption")));
        return new BudgetPage(this.driver);
    }

    public CampaignPage navigateToCampaignPage(long agencyId, long accountId) throws Exception {
        open(System.getProperty("ui.server").concat("/cm/agency/").concat(String.valueOf(agencyId)).concat("/accounts/").concat(String.valueOf(accountId)).concat("/campaigns?").concat(System.getProperty("ui.decryption")));
        return new CampaignPage(this.driver);
    }

    public AdGroupPage navigateToAdGroupTab(long agencyId, long accountId, long campaignId) throws Exception {
        open(System.getProperty("ui.server").concat("/cm/agency/").concat(String.valueOf(agencyId)).concat("/accounts/").concat(String.valueOf(accountId)).concat("/adgroups?campaignId=").concat(String.valueOf(campaignId)).concat("&").concat(System.getProperty("ui.decryption")));
        return new AdGroupPage(this.driver);
    }

    public AdPage navigateToAdTab(long agencyId, long accountId, long campaignId) throws Exception {
        open(System.getProperty("ui.server").concat("/cm/agency/").concat(String.valueOf(agencyId)).concat("/accounts/").concat(String.valueOf(accountId)).concat("/ads?campaignId=").concat(String.valueOf(campaignId)).concat("&").concat(System.getProperty("ui.decryption")));
        return new AdPage(this.driver);
    }

    public KeywordPage navigateToKeywordTab(long agencyId, long accountId, long campaignId) throws Exception {
        open(System.getProperty("ui.server").concat("/cm/agency/").concat(String.valueOf(agencyId)).concat("/accounts/").concat(String.valueOf(accountId)).concat("/keywords?campaignId=").concat(String.valueOf(campaignId)).concat("&").concat(System.getProperty("ui.decryption")));
        return new KeywordPage(this.driver);
    }

    public AdExtensionSiteLinkExtension navigateToAdExtensionsTab(long agencyId, long accountId, long campaignId) throws Exception {
        open(System.getProperty("ui.server").concat("/cm/agency/").concat(String.valueOf(agencyId)).concat("/accounts/").concat(String.valueOf(accountId)).concat("/extensions?").concat(String.valueOf(campaignId)).concat("&").concat(System.getProperty("ui.decryption")));
        return new AdExtensionSiteLinkExtension(this.driver);
    }

    public SettingsPage navigateToSettingsTab(long agencyId, long accountId, long campaignId) throws Exception {
        open(System.getProperty("ui.server").concat("/cm/agency/").concat(String.valueOf(agencyId)).concat("/accounts/").concat(String.valueOf(accountId)).concat("/settings?campaignId=").concat(String.valueOf(campaignId)).concat("&").concat(System.getProperty("ui.decryption")));
        return new SettingsPage(this.driver);
    }

    public SettingsPage navigateToAllSettingsTab(long agencyId, long accountId) throws Exception {
        open(System.getProperty("ui.server").concat("/cm/agency/").concat(String.valueOf(agencyId)).concat("/accounts/").concat(String.valueOf(accountId)).concat("/settings?").concat(System.getProperty("ui.decryption")));
        return new SettingsPage(this.driver);
    }

    public AdExtensionLocationExtension navigateToAdExtensionsLocationTab(long agencyId, long accountId, long campaignId) throws Exception {
        open(System.getProperty("ui.server").concat("/cm/agency/").concat(String.valueOf(agencyId)).concat("/accounts/").concat(String.valueOf(accountId)).concat("/extensions?").concat(String.valueOf(campaignId)).concat("&").concat(System.getProperty("ui.decryption")));
        return new AdExtensionLocationExtension(this.driver);
    }

    public AdExtensionCallExtension navigateToCallExtensionsLocationTab(long agencyId, long accountId, long campaignId) throws Exception {
        open(System.getProperty("ui.server").concat("/cm/agency/").concat(String.valueOf(agencyId)).concat("/accounts/").concat(String.valueOf(accountId)).concat("/extensions?").concat(String.valueOf(campaignId)).concat("&").concat(System.getProperty("ui.decryption")));
        return new AdExtensionCallExtension(this.driver);
    }

    public AdExtensionLogoExtension navigateToLogoExtensionsLocationTab(long agencyId, long accountId, long campaignId) throws Exception {
        open(System.getProperty("ui.server").concat("/cm/agency/").concat(String.valueOf(agencyId)).concat("/accounts/").concat(String.valueOf(accountId)).concat("/extensions?").concat(String.valueOf(campaignId)).concat("&").concat(System.getProperty("ui.decryption")));
        return new AdExtensionLogoExtension(this.driver);
    }

    public CampaignPage navigateToAdSettingsTab(long agencyId, long accountId, long campaignId) throws Exception {
        open(System.getProperty("ui.server").concat("/cm/agency/").concat(String.valueOf(agencyId)).concat("/accounts/").concat(String.valueOf(accountId)).concat("/settings?").concat(String.valueOf(campaignId)).concat("&").concat(System.getProperty("ui.decryption")));
        return new CampaignPage(this.driver);
    }
}