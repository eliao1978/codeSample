package com.yp.pm.kp.ui.smoke;

import com.yp.enums.selenium.LocatorTypeEnum;
import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.ui.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.NoSuchElementException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UISmokeTest extends BaseUITest {

    MyAgencyPage agencyPage;

    @Test
    public void assertAccountPage() throws Exception {
        String columns[] = {"Client", "Clicks", "Impr.", "CTR", "Avg. CPC", "Amount Spent"};

        agencyPage = getAgencyPageObject();
        AccountPage accountPage = agencyPage.navigateToAccountPage(Long.valueOf(System.getProperty("smoke.agencyid")));

        for (String label : columns) {
            if (!accountPage.isElementPresent("//tr/th[normalize-space(.)='" + label + "']", LocatorTypeEnum.XPATH)) {
                throw new NoSuchElementException("Column does not exist [" + label + "]");
            }
        }

        assertConditionTrue(accountPage.isElementPresent(UIMapper.CREATE_ACCOUNT_BUTTON));
        assertConditionTrue(accountPage.isElementPresent(UIMapper.SEARCH_CLIENT_NAME_TEXT_BOX));
        assertConditionTrue(accountPage.isElementPresent(UIMapper.CLIENT_SEARCH_BUTTON));
    }

    @Test
    public void assertBudgetPage() throws Exception {
        String[] labels = {"Status", "Name", "Budget", "Delivery Method", "# Campaigns", "Clicks", "Impr.", "CTR", "Avg. CPC", "Cost", "Avg. Pos."};

        agencyPage = getAgencyPageObject();
        BudgetPage budgetPage = agencyPage.navigateToBudgetPage(Long.valueOf(System.getProperty("smoke.agencyid")), Long.valueOf(System.getProperty("smoke.accountid")));

        // ui assertion
        int i = 2;
        for (String label : labels) {
            assertObjectEqual(budgetPage.getTextFromStatsTableHeader(i), label);
            i += 1;
        }

        assertConditionTrue(budgetPage.isElementPresent(UIMapper.CREATE_BUDGET_BUTTON));
    }

    @Test
    public void assertCampaignPage() throws Exception {
        String[] columns = {"Status", "Campaign", "Serving Status", "Clicks", "Impr.", "CTR", "Avg. CPC", "Cost", "Avg. Pos."};

        agencyPage = getAgencyPageObject();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(Long.valueOf(System.getProperty("smoke.agencyid")), Long.valueOf(System.getProperty("smoke.accountid")));

        for (String label : columns) {
            if (!campaignPage.checkTableHeader(label)) {
                throw new NoSuchElementException("Column does not exist [" + label + "]");
            }
        }

        assertConditionTrue(campaignPage.isElementPresent(UIMapper.CREATE_CAMPAIGN_BUTTON));
        assertConditionTrue(campaignPage.isElementPresent(UIMapper.EDIT_STATUS_BUTTON));
        assertConditionTrue(campaignPage.isElementEnabled(UIMapper.CAMPAIGN_FILTER_BUTTON));
    }

    @Test
    public void assertAdGroupPage() throws Exception {
        String[] AdGroupsLabels = {"Status", "Ad Group", "Serving Status", "Default Max. CPC", "Clicks", "Impr.", "CTR", "Avg. CPC", "Cost", "Avg. Pos."};

        agencyPage = getAgencyPageObject();
        AdGroupPage adGroup = agencyPage.navigateToAdGroupTab(Long.valueOf(System.getProperty("smoke.agencyid")), Long.valueOf(System.getProperty("smoke.accountid")), Long.valueOf(System.getProperty("smoke.campaignid")));

        assertConditionTrue(adGroup.isElementPresent(UIMapper.CREATE_AD_GROUP_BUTTON));
        assertConditionTrue(adGroup.isElementPresent(UIMapper.EDIT_STATUS_BUTTON));
        assertConditionTrue(adGroup.isElementPresent(UIMapper.ADGROUP_FILTER_BUTTON));
        assertConditionTrue(adGroup.isElementEnabled(UIMapper.ADGROUP_FILTER_BUTTON));
        assertConditionTrue(adGroup.isElementPresent(UIMapper.SEARCH_AD_GROUP_TEXT_BOX));
        assertConditionTrue(adGroup.isElementPresent(UIMapper.AD_GROUP_SEARCH_BUTTON));
        assertConditionTrue(adGroup.isElementEnabled(UIMapper.AD_GROUP_SEARCH_BUTTON));
        assertConditionTrue(adGroup.isElementPresent(UIMapper.FILTER_BY_DATE_DROPDOWN));
        assertConditionTrue(adGroup.isElementEnabled(UIMapper.FILTER_BY_DATE_DROPDOWN));
        assertConditionTrue(adGroup.isElementPresent(UIMapper.DOWNLOAD_BUTTON));
        assertConditionTrue(adGroup.isElementEnabled(UIMapper.DOWNLOAD_BUTTON));
        assertConditionTrue(adGroup.isElementPresent(UIMapper.SHOW_CHART_BUTTON));
        assertConditionTrue(adGroup.isElementEnabled(UIMapper.SHOW_CHART_BUTTON));
        assertConditionTrue(adGroup.isElementPresent(UIMapper.ADGROUP_FILTER_DROPDOWN));
        assertConditionTrue(adGroup.isElementEnabled(UIMapper.ADGROUP_FILTER_DROPDOWN));

        for (String AdGroupLabels : AdGroupsLabels) {
            if (!adGroup.checkTableHeader(AdGroupLabels)) {
                throw new NullPointerException("Element not exist [" + AdGroupLabels + "]");
            }
        }
    }

    @Test
    public void assertAdPage() throws Exception {
        String[] AdLabels = {"Status", "Ad", "Ad Group", "Serving Status", "Clicks", "Impr.", "CTR", "Avg. CPC", "Cost", "Avg. Pos."};

        agencyPage = getAgencyPageObject();
        AdPage ad = agencyPage.navigateToAdTab(Long.valueOf(System.getProperty("smoke.agencyid")), Long.valueOf(System.getProperty("smoke.accountid")), Long.valueOf(System.getProperty("smoke.campaignid")));

        assertConditionTrue(ad.isElementPresent(UIMapper.CREATE_AD_BUTTON));
        assertConditionTrue(ad.isElementPresent(UIMapper.EDIT_STATUS_BUTTON));
        assertConditionTrue(ad.isElementPresent(UIMapper.AD_SEARCH_TEXTBOX));
        assertConditionTrue(ad.isElementPresent(UIMapper.AD_SEARCH_BUTTON));

        for (String AdTabLabels : AdLabels) {
            if (!ad.checkTableHeader(AdTabLabels)) {
                throw new NullPointerException("Element not exist [" + AdTabLabels + "]");
            }
        }
    }

    @Test
    public void assertKeywordPage() throws Exception {
        String[] KewWordLabels = {"Status", "Keyword", "Ad Group", "Serving Status", "Max. CPC", "Clicks", "Impr.", "CTR", "Avg. CPC", "Cost", "Avg. Pos.", "Dest. Url."};

        agencyPage = getAgencyPageObject();
        KeywordPage smokeTest = agencyPage.navigateToKeywordTab(Long.valueOf(System.getProperty("smoke.agencyid")), Long.valueOf(System.getProperty("smoke.accountid")), Long.valueOf(System.getProperty("smoke.campaignid")));

        assertConditionTrue(smokeTest.isElementPresent(UIMapper.CREATE_KEY_WORD_BUTTON));
        assertConditionTrue(smokeTest.isElementPresent(UIMapper.EDIT_STATUS_BUTTON));
        assertConditionTrue(smokeTest.isElementPresent(UIMapper.KEYWORD_ELEMENT_DROPDOWN));
        assertConditionTrue(smokeTest.isElementEnabled(UIMapper.KEYWORD_ELEMENT_DROPDOWN));

        for (String KeywordTabLabels : KewWordLabels) {
            if (!smokeTest.checkTableHeader(KeywordTabLabels)) {
                throw new NullPointerException("Element not exist [" + KeywordTabLabels + "]");
            }
        }
    }

    @Test
    public void assertSettingPage() throws Exception {
        agencyPage = getAgencyPageObject();
        SettingsPage settingsTab = agencyPage.navigateToSettingsTab(Long.valueOf(System.getProperty("smoke.agencyid")), Long.valueOf(System.getProperty("smoke.accountid")), Long.valueOf(System.getProperty("smoke.campaignid")));

        settingsTab.validateSettingsTab(2);
        settingsTab.validateSettingsTab(3);
        settingsTab.validateSettingsTab(4);
        assertConditionTrue(settingsTab.isElementPresent(UIMapper.FILTER_BY_DATE_DROPDOWN));
        assertConditionTrue(settingsTab.isElementEnabled(UIMapper.FILTER_BY_DATE_DROPDOWN));

    }

    @Test
    public void assertLocationPage() throws Exception {
        String[] settingsTabLocationLabel = {"Location", "Bid adj.", "Clicks", "Impr.", "CTR", "Avg. CPC", "Cost", "Avg. Pos."};

        agencyPage = getAgencyPageObject();
        SettingsPage settingsTab = agencyPage.navigateToSettingsTab(Long.valueOf(System.getProperty("smoke.agencyid")), Long.valueOf(System.getProperty("smoke.accountid")), Long.valueOf(System.getProperty("smoke.campaignid")));

        settingsTab.clickSettingsSubTabs(2);
        assertConditionTrue(settingsTab.isElementPresent(UIMapper.ADD_LOCATION_BUTTON));
        assertConditionTrue(settingsTab.isElementPresent(UIMapper.FILTER_BY_DATE_DROPDOWN));
        assertConditionTrue(settingsTab.isElementEnabled(UIMapper.FILTER_BY_DATE_DROPDOWN));
        assertConditionTrue(settingsTab.isElementPresent(UIMapper.EXCLUDED_LOCATION_LINK));

        for (String SettingSubTabLocations : settingsTabLocationLabel) {
            if (!settingsTab.checkTableHeader(SettingSubTabLocations)) {
                throw new NullPointerException("Element not exist [" + SettingSubTabLocations + "]");
            }
        }
    }

    @Test
    public void assertAdSchedulePage() throws Exception {
        String[] settingsTabAdScheduleLabel = {"Day and time", "Bid adj.", "Clicks", "Impr.", "CTR", "Avg. CPC", "Cost", "Avg. Pos."};

        agencyPage = getAgencyPageObject();
        SettingsPage settingsTab = agencyPage.navigateToSettingsTab(Long.valueOf(System.getProperty("smoke.agencyid")), Long.valueOf(System.getProperty("smoke.accountid")), Long.valueOf(System.getProperty("smoke.campaignid")));

        settingsTab.clickSettingsSubTabs(3);
        assertConditionTrue(settingsTab.isElementPresent(UIMapper.ADD_SCHEDULE_BUTTON));
        assertConditionTrue(settingsTab.isElementPresent(UIMapper.FILTER_BY_DATE_DROPDOWN));
        assertConditionTrue(settingsTab.isElementEnabled(UIMapper.FILTER_BY_DATE_DROPDOWN));

        for (String SettingsSubTabAdSchedule : settingsTabAdScheduleLabel) {
            if (!settingsTab.checkTableHeader(SettingsSubTabAdSchedule)) {
                throw new NullPointerException("Element not exist [" + SettingsSubTabAdSchedule + "]");
            }
        }
    }

    @Test
    public void assertDevicePage() throws Exception {
        String[] settingsTabDeviceLabel = {"Device", "Bid adj.", "Clicks", "Impr.", "CTR", "Avg. CPC", "Cost", "Avg. Pos."};

        agencyPage = getAgencyPageObject();
        SettingsPage settingsTab = agencyPage.navigateToSettingsTab(Long.valueOf(System.getProperty("smoke.agencyid")), Long.valueOf(System.getProperty("smoke.accountid")), Long.valueOf(System.getProperty("smoke.campaignid")));

        settingsTab.clickSettingsSubTabs(4);
        assertConditionTrue(settingsTab.isElementPresent(UIMapper.FILTER_BY_DATE_DROPDOWN));
        assertConditionTrue(settingsTab.isElementEnabled(UIMapper.FILTER_BY_DATE_DROPDOWN));

        for (String SettingsSubTabDevice : settingsTabDeviceLabel) {
            if (!settingsTab.checkTableHeader(SettingsSubTabDevice)) {
                throw new NullPointerException("Element not exist [" + SettingsSubTabDevice + "]");
            }
        }
    }

    @Test
    public void assertAdExtensionSiteLinkPage() throws Exception {
        String columns[] = {"Sitelink", "Status", "Campaign", "Clicks", "Impr.", "CTR", "Avg. CPC", "Cost", "Avg. Pos."};

        agencyPage = getAgencyPageObject();
        AdExtensionSiteLinkExtension siteLinkExtension = agencyPage.navigateToAdExtensionsTab(Long.valueOf(System.getProperty("smoke.agencyid")), Long.valueOf(System.getProperty("smoke.accountid")), Long.valueOf(System.getProperty("smoke.campaignid")));

        for (String label : columns) {
            if (!siteLinkExtension.checkTableHeader(label)) {
                throw new NoSuchElementException("Column does not exist [" + label + "]");
            }
        }
    }

    @Test
    public void assertAdExtensionLocationPage() throws Exception {
        String columns[] = {"Company Name", "Location", "Status", "Campaign", "Clicks", "Impr.", "CTR", "Avg. CPC", "Cost", "Avg. Pos."};

        agencyPage = getAgencyPageObject();
        AdExtensionLocationExtension locationExtension = agencyPage.navigateToAdExtensionsLocationTab(Long.valueOf(System.getProperty("smoke.agencyid")), Long.valueOf(System.getProperty("smoke.accountid")), Long.valueOf(System.getProperty("smoke.campaignid")));

        locationExtension.locationExtension();
        assertConditionTrue(locationExtension.isElementPresent(UIMapper.EXTENSION_BUTTON));
        assertConditionTrue(locationExtension.isElementPresent(UIMapper.IMPORT_EXTENSION));
        assertConditionTrue(locationExtension.isElementPresent(UIMapper.LOCATION_EDIT_BUTTON));
        assertConditionTrue(locationExtension.isElementPresent(UIMapper.LOCATION_REMOVE_BUTTON));
        assertConditionTrue(locationExtension.isElementPresent(UIMapper.EXTENSION_DROPDOWN));
        assertConditionTrue(locationExtension.isElementEnabled(UIMapper.EXTENSION_DROPDOWN));
        assertConditionTrue(locationExtension.isElementPresent(UIMapper.EXTENSION_BUTTON));
        assertConditionTrue(locationExtension.isElementPresent(UIMapper.FILTER_BY_DATE_DROPDOWN));
        assertConditionTrue(locationExtension.isElementEnabled(UIMapper.FILTER_BY_DATE_DROPDOWN));

        for (String label : columns) {
            if (!locationExtension.checkTableHeader(label)) {
                throw new NoSuchElementException("Column does not exist [" + label + "]");
            }
        }
    }

    @Test
    public void assertAdExtensionPhonePage() throws Exception {
        String callExtensionLabels[] = {"Phone Number", "Status", "Campaign", "Clicks", "Impr.", "CTR", "Avg. CPC", "Cost", "Avg. Pos."};

        agencyPage = getAgencyPageObject();
        AdExtensionCallExtension callExtension = agencyPage.navigateToCallExtensionsLocationTab(Long.valueOf(System.getProperty("smoke.agencyid")), Long.valueOf(System.getProperty("smoke.accountid")), Long.valueOf(System.getProperty("smoke.campaignid")));

        callExtension.callExtension();
        assertConditionTrue(callExtension.isElementPresent(UIMapper.EXTENSION_BUTTON));
        assertConditionTrue(callExtension.isElementPresent(UIMapper.EXTENSION_EDIT_BUTTON));
        assertConditionTrue(callExtension.isElementPresent(UIMapper.STATIC_FILTERS));
        assertConditionTrue(callExtension.isElementEnabled(UIMapper.STATIC_FILTERS));

        for (String callExtensionLabel : callExtensionLabels) {
            if (!callExtension.checkTableHeader(callExtensionLabel)) {
                throw new NullPointerException("Element does not exist [" + callExtensionLabel + "]");
            }
        }
    }

    @Test
    public void assertAdExtensionLogoPage() throws Exception {
        String logoExtensionLabels[] = {"Image", "Label", "Status", "Campaign", "Clicks", "Impr.", "CTR", "Avg. CPC", "Cost", "Avg. Pos."};

        agencyPage = getAgencyPageObject();
        AdExtensionLogoExtension logoExtension = agencyPage.navigateToLogoExtensionsLocationTab(Long.valueOf(System.getProperty("smoke.agencyid")), Long.valueOf(System.getProperty("smoke.accountid")), Long.valueOf(System.getProperty("smoke.campaignid")));

        logoExtension.logoExtension();
        assertConditionTrue(logoExtension.isElementPresent(UIMapper.EXTENSION_BUTTON));
        assertConditionTrue(logoExtension.isElementPresent(UIMapper.EXTENSION_EDIT_BUTTON));
        assertConditionTrue(logoExtension.isElementPresent(UIMapper.STATIC_FILTERS));
        assertConditionTrue(logoExtension.isElementEnabled(UIMapper.STATIC_FILTERS));

        for (String logoExtensionLabel : logoExtensionLabels) {
            if (!logoExtension.checkTableHeader(logoExtensionLabel)) {
                throw new NullPointerException("Element does not exist [" + logoExtensionLabel + "]");
            }
        }
    }

    @Test
    public void assertChangeHistoryPage() throws Exception {
        String[] changeHistoryLabels = {"Date & Time / User", "Campaign", "Ad Group", "Changes"};
        String changeHistoryText = "Change History";

        agencyPage = getAgencyPageObject();
        CampaignPage changeHistory = agencyPage.navigateToCampaignPage(Long.valueOf(System.getProperty("smoke.agencyid")), Long.valueOf(System.getProperty("smoke.accountid")));

        changeHistory.clickChangeHistory();
        assertConditionTrue(changeHistory.getChangeHistoryText().contains(changeHistoryText));
        assertConditionTrue(changeHistory.isElementPresent(UIMapper.CHANGE_HISTORY_FILTER_BUTTON));
        assertConditionTrue(changeHistory.isElementEnabled(UIMapper.CHANGE_HISTORY_FILTER_BUTTON));

        changeHistory.clickFilterByDateDropdown();
        assertConditionTrue(changeHistory.getChangeHistoryText().contains(changeHistoryText));

        for (String changeHistoryLabel : changeHistoryLabels) {
            if (!changeHistory.checkTableHeader(changeHistoryLabel)) {
                throw new NullPointerException("Element not exist [" + changeHistoryLabel + "]");
            }
        }
    }

    @Test
    public void assertPageHeader() throws Exception {
        String headerAgencyText = "My Agencies";

        agencyPage = getAgencyPageObject();
        assertConditionTrue(agencyPage.isElementPresent(UIMapper.HEADER_LOGO));
        assertConditionTrue(agencyPage.isElementPresent(UIMapper.HEADER_MY_AGENCY_TEXT));

        agencyPage.click(UIMapper.HEADER_MY_AGENCY_TEXT, LocatorTypeEnum.CSS);
        assertConditionTrue(agencyPage.isElementPresent(UIMapper.HEADER_LOGO));
        assertConditionTrue(agencyPage.getTextFromElement(UIMapper.HEADER_MY_AGENCY_TEXT, LocatorTypeEnum.CSS).contains(headerAgencyText));
        assertConditionTrue(agencyPage.isElementPresent(UIMapper.HEADER_AGECNY_DROPDOWN));
        assertConditionTrue(agencyPage.isElementPresent(UIMapper.HEADER_USER_NAME));
        assertConditionTrue(agencyPage.getTextFromElement(UIMapper.HEADER_USER_NAME, LocatorTypeEnum.CSS).contains(System.getProperty("ui.username")));

        agencyPage.click(UIMapper.HEADER_USER_NAME, LocatorTypeEnum.CSS);
        assertConditionTrue(agencyPage.isElementPresent(UIMapper.HEADER_LOGO));
        assertConditionTrue(agencyPage.isElementPresent(UIMapper.HEADER_SETTINGS_ICON));

        agencyPage.click(UIMapper.HEADER_SETTINGS_ICON, LocatorTypeEnum.CSS);
        assertConditionTrue(agencyPage.isElementPresent(UIMapper.HEADER_LOGO));
        assertConditionTrue(agencyPage.isElementPresent(UIMapper.HEADER_BELL_ICON));
    }

    @Test
    public void assertPageFooter() throws Exception {

        String browserVersionText = "This application is supported in the following browser versions:";
        String chromeText = "Chrome 30 and later";
        String firefoxText = "Firefox 24 and later";
        String safariText = "Safari 6 and later";
        String footerTextLine1 = "Amounts reported above are for informational, campaign planning purposes only and may vary from actual and/or billed totals as a result of time delays and/or agreed";
        String footerTextLine2 = "upon budget or campaign limitations. Unless otherwise specified by YP, only amounts shown on ypSearch Marketplace invoices will apply for billing purposes.";
        String footerTextLine3 = "Reporting is not real-time. Clicks and impressions received in the past few hours may not be included here. Time zone for all dates and times are based on the account’s time zone.";
        String footerTextLine4 = "YP, the YP logo and all other YP marks contained herein are trademarks of YP LLC and/or YP affiliated companies.";
        String footerTextLine5 = "© 2016 YP LLC. All rights reserved";
        String termsConditionText = "Terms & Conditions";

        agencyPage = getAgencyPageObject();
        assertConditionTrue(agencyPage.getTextFromElement(UIMapper.FOOTER_BROWSER_VERSION_TEXT, LocatorTypeEnum.CSS).contains(browserVersionText));
        assertConditionTrue(agencyPage.getTextFromElement("//*[@class='list-inline']/li[1]", LocatorTypeEnum.XPATH).contains(chromeText));
        assertConditionTrue(agencyPage.getTextFromElement("//*[@class='list-inline']/li[2]", LocatorTypeEnum.XPATH).contains(firefoxText));
        assertConditionTrue(agencyPage.getTextFromElement("//*[@class='list-inline']/li[3]", LocatorTypeEnum.XPATH).contains(safariText));
        assertConditionTrue(agencyPage.getTextFromElement(UIMapper.FOOTER_TEXT, LocatorTypeEnum.XPATH).contains(footerTextLine1));
        assertConditionTrue(agencyPage.getTextFromElement(UIMapper.FOOTER_TEXT, LocatorTypeEnum.XPATH).contains(footerTextLine2));
        assertConditionTrue(agencyPage.getTextFromElement(UIMapper.FOOTER_TEXT, LocatorTypeEnum.XPATH).contains(footerTextLine3));
        assertConditionTrue(agencyPage.getTextFromElement(UIMapper.FOOTER_TEXT, LocatorTypeEnum.XPATH).contains(footerTextLine4));
        assertConditionTrue(agencyPage.getTextFromElement(UIMapper.FOOTER_TEXT, LocatorTypeEnum.XPATH).contains(footerTextLine5));
        assertConditionTrue(agencyPage.getTextFromElement(UIMapper.FOOTER_TERMS_CONDITIONS_LINK, LocatorTypeEnum.CSS).contains(termsConditionText));
    }

    private MyAgencyPage getAgencyPageObject() throws Exception {
        if (agencyPage == null) {
            return login();
        }
        return agencyPage;
    }
}