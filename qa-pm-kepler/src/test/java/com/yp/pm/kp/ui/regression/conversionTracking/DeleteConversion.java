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
 * Created by ar2130 on 3/2/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeleteConversion extends BaseUITest {

    @Test
    public void testDeleteConversion() throws Exception {
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
        campaignPage.click("tr>td>input[type='checkbox'][ng-model='checkModel[$index]']");
        campaignPage.click("span>button[data-toggle='dropdown'][has-permission='ACCOUNT_UPDATE']");
        String deleteButton="span>ul[class='dropdown-menu']>li[ng-click=\"bulkEdit({ value: 'DELETED', label: 'Deleted' })\"]>a";
        campaignPage.waitForElementVisible(deleteButton);
        campaignPage.click(deleteButton);
        campaignPage.waitForElementNotVisible(UIMapper.LOADING);
        List<Map<String, Object>> dbLookup = redirectorDBLookup(account.getAccountId());
        Integer redirectorFlag =  Integer.valueOf(dbLookup.get(0).get("ENABLE_REDIRECTOR").toString());
        assertConditionTrue("Redirector flag was enabled", redirectorFlag.equals(0));
    }
    private List<Map<String, Object>> redirectorDBLookup(Long id) {
        return DBUtil.queryForList("SELECT ENABLE_REDIRECTOR FROM PM_ACCOUNT WHERE ACCOUNT_ID = " + id);
    }
}
