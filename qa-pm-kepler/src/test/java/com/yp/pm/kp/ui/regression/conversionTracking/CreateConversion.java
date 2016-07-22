package com.yp.pm.kp.ui.regression.conversionTracking;

import com.yp.enums.selenium.LocatorTypeEnum;
import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.ui.CampaignPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import com.yp.pm.kp.ui.UIMapper;
import com.yp.util.DBUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * Created by ar2130 on 12/11/15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class CreateConversion extends BaseUITest {

    @Test
    public void testCreateConversion() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        createCampaign(account.getAccountId(),budget.getBudgetId());
        String conversion_name = "Conversion Page";
        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, account.getAccountId());
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        campaignPage.click(UIMapper.CONVERSION_TAB, LocatorTypeEnum.XPATH);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        campaignPage.click(UIMapper.CREATE_CONVERSION);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        campaignPage.type(UIMapper.CONVERSION_NAME,conversion_name);
        campaignPage.type(UIMapper.CONVERSION_VALUE,"5");
        campaignPage.click(UIMapper.SAVE_AND_CONTINUE);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        assertConditionTrue(campaignPage.verifyTextPresent("Review Conversion"));
        String snippetCode = campaignPage.getAttributeFromElement("textarea[ng-model='conversion.snippet']", LocatorTypeEnum.CSS, "value");
        logger.debug("Snippet Code " + snippetCode);
        assertConditionTrue(snippetCode.contains("<div style=\"display:inline;\">"));
        assertConditionTrue(snippetCode.contains("<img height=\"1\" width=\"1\" style=\"border-style:none;\" alt=\"\" src=\"http://"));
        campaignPage.click(UIMapper.DONE_BUTTON);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        String name = campaignPage.getTextFromElement("table>tbody>tr:nth-of-type(2)>td:nth-of-type(3)>a");
        assertConditionTrue("Conversion name does not exist in table", name.equalsIgnoreCase(conversion_name));
        List<Map<String, Object>> dbLookup = redirectorDBLookup(account.getAccountId());
        Integer redirectorFlag =  Integer.valueOf(dbLookup.get(0).get("ENABLE_REDIRECTOR").toString());
        assertConditionTrue("Redirector flag was not enabled", redirectorFlag.equals(1));
    }

    @Test
    public void testConversionWithDuplicateName() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        createCampaign(account.getAccountId(),budget.getBudgetId());
        String conversion_name = "Conversion Page";
        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, account.getAccountId());
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        campaignPage.click(UIMapper.CONVERSION_TAB, LocatorTypeEnum.XPATH);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        campaignPage.click(UIMapper.CREATE_CONVERSION);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        campaignPage.type(UIMapper.CONVERSION_NAME,conversion_name);
        campaignPage.type(UIMapper.CONVERSION_VALUE,"5");
        campaignPage.click(UIMapper.SAVE_AND_CONTINUE);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        assertConditionTrue(campaignPage.verifyTextPresent("Review Conversion"));
        String snippetCode = campaignPage.getAttributeFromElement("textarea[ng-model='conversion.snippet']", LocatorTypeEnum.CSS, "value");
        logger.debug("Snippet Code " + snippetCode);
        assertConditionTrue(snippetCode.contains("<div style=\"display:inline;\">"));
        assertConditionTrue(snippetCode.contains("<img height=\"1\" width=\"1\" style=\"border-style:none;\" alt=\"\" src=\"http://"));
        campaignPage.click(UIMapper.DONE_BUTTON);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        String name = campaignPage.getTextFromElement("table>tbody>tr:nth-of-type(2)>td:nth-of-type(3)>a");
        assertConditionTrue("Conversion name does not exist in table", name.equalsIgnoreCase(conversion_name));
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        campaignPage.click(UIMapper.CREATE_CONVERSION);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        campaignPage.type(UIMapper.CONVERSION_NAME,conversion_name);
        campaignPage.type(UIMapper.CONVERSION_VALUE,"5");
        campaignPage.click(UIMapper.SAVE_AND_CONTINUE);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        String secondName = campaignPage.getTextFromElement(".error.ng-scope.ng-binding");
        String errorName = "Conversion name must be unique";
        assertConditionTrue("Duplicate name should not be accepted", secondName.equalsIgnoreCase(errorName));
    }

    @Test
    public void testConversionWithNoName() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        createCampaign(account.getAccountId(),budget.getBudgetId());
        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, account.getAccountId());
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        campaignPage.click(UIMapper.CONVERSION_TAB, LocatorTypeEnum.XPATH);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        campaignPage.click(UIMapper.CREATE_CONVERSION);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        campaignPage.type(UIMapper.CONVERSION_VALUE,"5");
        campaignPage.click(UIMapper.SAVE_AND_CONTINUE);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        String name = campaignPage.getTextFromElement(".//*[@id='editConversion']/section/div[1]/div/div[1]", LocatorTypeEnum.XPATH);
        String errorName = "Conversion name is required";
        assertConditionTrue("Should not accept with Null name", name.equalsIgnoreCase(errorName));
        List<Map<String, Object>> dbLookup = redirectorDBLookup(account.getAccountId());
        assertObjectNull(dbLookup.get(0).get("ENABLE_REDIRECTOR"));
    }

    @Test
    public void testConversionWithLargeValue() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        createCampaign(account.getAccountId(),budget.getBudgetId());
        String conversion_name = "Conversion Page";
        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, account.getAccountId());
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        campaignPage.click(UIMapper.CONVERSION_TAB, LocatorTypeEnum.XPATH);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        campaignPage.click(UIMapper.CREATE_CONVERSION);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        campaignPage.type(UIMapper.CONVERSION_NAME,conversion_name);
        campaignPage.type(UIMapper.CONVERSION_VALUE,"100987654");
        campaignPage.click(UIMapper.SAVE_AND_CONTINUE);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        String name = campaignPage.getTextFromElement(".//*[@id='editConversion']/section/div[2]/div/div/div[5]", LocatorTypeEnum.XPATH);
        String errorName= "Value is too large";
        assertConditionTrue("Should not accept too large value", name.equalsIgnoreCase(errorName));
    }

    @Test
    public void testConversionWithNoValue() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        createCampaign(account.getAccountId(),budget.getBudgetId());
        String conversion_name = "Conversion Page";
        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, account.getAccountId());
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        campaignPage.click(UIMapper.CONVERSION_TAB, LocatorTypeEnum.XPATH);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        campaignPage.click(UIMapper.CREATE_CONVERSION);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        campaignPage.type(UIMapper.CONVERSION_NAME,conversion_name);
        campaignPage.type(UIMapper.CONVERSION_VALUE,"");
        campaignPage.click(UIMapper.SAVE_AND_CONTINUE);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        String name = campaignPage.getTextFromElement(".//*[@id='editConversion']/section/div[2]/div/div/div[4]", LocatorTypeEnum.XPATH);
        String errorName = "Enter a number (0 or greater)";
        assertConditionTrue("Should not accept with no value", name.equalsIgnoreCase(errorName));
    }

    @Test
    public void testConversionWindowFor4Weeks() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        createCampaign(account.getAccountId(),budget.getBudgetId());
        String conversion_name = "Conversion Page";
        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, account.getAccountId());
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        campaignPage.click(UIMapper.CONVERSION_TAB, LocatorTypeEnum.XPATH);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        campaignPage.click(UIMapper.CREATE_CONVERSION);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        campaignPage.type(UIMapper.CONVERSION_NAME,conversion_name);
        campaignPage.type(UIMapper.CONVERSION_VALUE,"5");
        campaignPage.click(".//*[@id='editConversion']/div[2]/div/div/span/div/button", LocatorTypeEnum.XPATH);
        campaignPage.click(UIMapper.SELECT_OPTION, LocatorTypeEnum.XPATH);
        campaignPage.click(UIMapper.SAVE_AND_CONTINUE);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        assertConditionTrue(campaignPage.verifyTextPresent("Review Conversion"));
        String snippetCode = campaignPage.getAttributeFromElement("textarea[ng-model='conversion.snippet']", LocatorTypeEnum.CSS, "value");
        logger.debug("Snippet Code " + snippetCode);
        assertConditionTrue(snippetCode.contains("<div style=\"display:inline;\">"));
        assertConditionTrue(snippetCode.contains("<img height=\"1\" width=\"1\" style=\"border-style:none;\" alt=\"\" src=\"http://"));
        campaignPage.click(UIMapper.DONE_BUTTON);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        String name = campaignPage.getTextFromElement("table>tbody>tr:nth-of-type(2)>td:nth-of-type(3)>a");
        assertConditionTrue("Conversion name does not exist in table", name.equalsIgnoreCase(conversion_name));
    }

    @Test
    public void testConversionWindowReviewFeature() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        createCampaign(account.getAccountId(),budget.getBudgetId());
        String conversion_name = "Conversion Page";
        String conversion_name_2nd = "Conversion Page2";
        String conversion_name_3rd = "Conversion Page3";
        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, account.getAccountId());
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        campaignPage.click(UIMapper.CONVERSION_TAB, LocatorTypeEnum.XPATH);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        campaignPage.click(UIMapper.CREATE_CONVERSION);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        campaignPage.type(UIMapper.CONVERSION_NAME,conversion_name);
        campaignPage.type(UIMapper.CONVERSION_VALUE,"5");
        campaignPage.click(".//*[@id='editConversion']/div[2]/div/div/span/div/button", LocatorTypeEnum.XPATH);
        campaignPage.click(UIMapper.SELECT_OPTION, LocatorTypeEnum.XPATH);
        campaignPage.click(UIMapper.SAVE_AND_CONTINUE);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        assertConditionTrue(campaignPage.verifyTextPresent("Review Conversion"));
        String snippetCode = campaignPage.getAttributeFromElement("textarea[ng-model='conversion.snippet']", LocatorTypeEnum.CSS, "value");
        logger.debug("Snippet Code " + snippetCode);
        assertConditionTrue(snippetCode.contains("<div style=\"display:inline;\">"));
        assertConditionTrue(snippetCode.contains("<img height=\"1\" width=\"1\" style=\"border-style:none;\" alt=\"\" src=\"http://"));
        campaignPage.click(UIMapper.DONE_BUTTON);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        String name = campaignPage.getTextFromElement("table>tbody>tr:nth-of-type(2)>td:nth-of-type(3)>a");
        assertConditionTrue("Conversion name does not exist in table", name.equalsIgnoreCase(conversion_name));

        campaignPage.click(UIMapper.CREATE_CONVERSION);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        campaignPage.type(UIMapper.CONVERSION_NAME,conversion_name_2nd);
        campaignPage.type(UIMapper.CONVERSION_VALUE,"5");
        campaignPage.click(".//*[@id='editConversion']/div[2]/div/div/span/div/button", LocatorTypeEnum.XPATH);
        campaignPage.click(UIMapper.SELECT_OPTION, LocatorTypeEnum.XPATH);
        campaignPage.click(UIMapper.SAVE_AND_CONTINUE);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        assertConditionTrue(campaignPage.verifyTextPresent("Review Conversion"));
        String snippetCode2 = campaignPage.getAttributeFromElement("textarea[ng-model='conversion.snippet']", LocatorTypeEnum.CSS, "value");
        logger.debug("Snippet Code " + snippetCode);
        assertConditionTrue(snippetCode.contains("<div style=\"display:inline;\">"));
        assertConditionTrue(snippetCode.contains("<img height=\"1\" width=\"1\" style=\"border-style:none;\" alt=\"\" src=\"http://"));
        campaignPage.click(UIMapper.DONE_BUTTON);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        String name2 = campaignPage.getTextFromElement("//table/tbody/tr/td[normalize-space(.)='Conversion Page2']", LocatorTypeEnum.XPATH);
        assertConditionTrue("Conversion name does not exist in table", name2.equalsIgnoreCase(conversion_name_2nd));

        campaignPage.click(UIMapper.CREATE_CONVERSION);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        campaignPage.type(UIMapper.CONVERSION_NAME,conversion_name_3rd);
        campaignPage.type(UIMapper.CONVERSION_VALUE,"5");
        campaignPage.click(".//*[@id='editConversion']/div[2]/div/div/span/div/button", LocatorTypeEnum.XPATH);
        campaignPage.click(UIMapper.SELECT_OPTION, LocatorTypeEnum.XPATH);
        campaignPage.click(UIMapper.SAVE_AND_CONTINUE);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        assertConditionTrue(campaignPage.verifyTextPresent("Review Conversion"));
        String snippetCode3 = campaignPage.getAttributeFromElement("textarea[ng-model='conversion.snippet']", LocatorTypeEnum.CSS, "value");
        logger.debug("Snippet Code " + snippetCode);
        assertConditionTrue(snippetCode.contains("<div style=\"display:inline;\">"));
        assertConditionTrue(snippetCode.contains("<img height=\"1\" width=\"1\" style=\"border-style:none;\" alt=\"\" src=\"http://"));
        campaignPage.click(UIMapper.DONE_BUTTON);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        String name3 = campaignPage.getTextFromElement("//table/tbody/tr/td[normalize-space(.)='Conversion Page3']", LocatorTypeEnum.XPATH);
        assertConditionTrue("Conversion name does not exist in table", name3.equalsIgnoreCase(conversion_name_3rd));
        campaignPage.click(".//*[@id='indexPageId']/div[2]/div[2]/div[2]/div/div/div[2]/div/div[1]/table/tbody/tr[4]/td[1]/input", LocatorTypeEnum.XPATH);
        Thread.sleep(1000);
        campaignPage.click(".//*[@id='indexPageId']/div[2]/div[2]/div[2]/div/div/div[2]/div/div[1]/div[2]/span[2]/button", LocatorTypeEnum.XPATH);
        Thread.sleep(1000);
        campaignPage.click(".//*[@id='indexPageId']/div[2]/div[2]/div[2]/div/div/div[2]/div/div[1]/div[2]/span[2]/ul/li[2]/a", LocatorTypeEnum.XPATH);
        Thread.sleep(1000);
        campaignPage.click(".//*[@id='conversionsFilters']/span/div/button", LocatorTypeEnum.XPATH);
        Thread.sleep(1000);
        campaignPage.click(".//*[@id='conversionsFilters']/span/div/ul/li[1]/a", LocatorTypeEnum.XPATH);
        Thread.sleep(1000);
        String deleteText = "Deleted";
        String name4 = campaignPage.getTextFromElement(".//*[@id='indexPageId']/div[2]/div[2]/div[2]/div/div/div[2]/div/div[1]/table/tbody/tr[4]/td[2]/div/span/div[1]/span/span[1]", LocatorTypeEnum.XPATH);
        assertConditionTrue("Delete item not found", name4.equalsIgnoreCase(deleteText));
    }

    private List<Map<String, Object>> redirectorDBLookup(Long id) {
        return DBUtil.queryForList("SELECT ENABLE_REDIRECTOR FROM PM_ACCOUNT WHERE ACCOUNT_ID = " + id);
    }
}
