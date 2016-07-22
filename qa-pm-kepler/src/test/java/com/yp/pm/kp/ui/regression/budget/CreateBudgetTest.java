package com.yp.pm.kp.ui.regression.budget;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.enums.model.BudgetStatusEnum;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.ui.BudgetPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import com.yp.pm.kp.ui.UIMapper;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreateBudgetTest extends BaseUITest {

    @Test
    public void assertFormSubmission() throws Exception {
        String budgetName = "budget_" + System.currentTimeMillis();
        long accountId = getAccountId();

        // ui assertion
        MyAgencyPage agencyPage = login();
        BudgetPage budgetPage = agencyPage.navigateToBudgetPage(agencyId, accountId);
        budgetPage.clickCreateBudgetButton();
        budgetPage.enterBudgetName(budgetName);
        budgetPage.enterBudgetAmount(100.00);
        budgetPage.clickDeliveryMethodLink();
        budgetPage.clickStandardDeliveryMethodRadioButton();
        budgetPage.submitForm();

        assertObjectEqual(budgetPage.getTextFromStatsTable(2, 2), "Enabled");
        assertObjectEqual(budgetPage.getTextFromStatsTable(2, 3), budgetName);
        assertObjectEqual(budgetPage.getTextFromStatsTable(2, 4), "$100.00");
        assertConditionTrue(budgetPage.getTextFromStatsTable(2, 5).contains("Show ads evenly over time"));

        // db assertion
        Budget budget = getBudgetByAccountId(accountId).get(0);
        assertObjectNotNull(budget.getBudgetId());
        assertObjectEqual(budget.getAmount().toDollar(), 100.00);
        assertObjectEqual(budget.getName(), budgetName);
        assertObjectEqual(budget.getAccountId(), accountId);
        assertConditionTrue(budget.getStatus().equals(BudgetStatusEnum.ACTIVE));
    }

    @Test
    public void assertCancelFormSubmission() throws Exception {
        String budgetName = "budget_" + System.currentTimeMillis();
        long accountId = getAccountId();

        MyAgencyPage agencyPage = login();
        BudgetPage budgetPage = agencyPage.navigateToBudgetPage(agencyId, accountId);
        budgetPage.clickCreateBudgetButton();
        budgetPage.enterBudgetName(budgetName);
        budgetPage.enterBudgetAmount(100.00);
        budgetPage.clickDeliveryMethodLink();
        budgetPage.clickAcceleratedDeliveryMethodRadioButton();
        budgetPage.cancelFormSubmission();

        assertObjectNull(budgetPage.getTextFromStatsTable(2, 2));
        assertConditionTrue(getActiveBudgetByName(budgetName) == null);
    }

    @Test
    public void assertMissingBudgetName() throws Exception {
        String error = "Please enter a budget name.";
        long accountId = getAccountId();

        MyAgencyPage agencyPage = login();
        BudgetPage budgetPage = agencyPage.navigateToBudgetPage(agencyId, accountId);
        budgetPage.clickCreateBudgetButton();
        budgetPage.enterBudgetAmount(100.00);
        budgetPage.submitForm();

        assertConditionTrue(budgetPage.getTextFromElement(UIMapper.MISSING_BUDGET_NAME_MSG).contains(error));
    }

    @Test
    public void assertBudgetNameTooLong() throws Exception {
        String budgetName = "Create Budget Name Too Long More Than 255 Characters.Create Budget Name Too Long More Than 255 CharactersCreate Budget Name Too Long More Than 255 Characters Create Budget Name Too Long More Than 255 CharactersCreate Budget Name Too Long More Than 255 CharactersCreate Budget Name Too Long More Than 255 CharactersCreate Budget Name Too Long More Than 255 Characters";
        String error = "Budget Name is too long.";
        long accountId = getAccountId();

        MyAgencyPage agencyPage = login();
        BudgetPage budgetPage = agencyPage.navigateToBudgetPage(agencyId, accountId);
        budgetPage.clickCreateBudgetButton();
        budgetPage.enterBudgetName(budgetName);
        budgetPage.enterBudgetAmount(100.00);
        budgetPage.submitForm();

        assertConditionTrue(budgetPage.getTextFromElement(UIMapper.BUDGET_NAME_TOO_LONG_MSG).contains(error));
    }


    @Test
    public void assertDuplicatedBudgetName() throws Exception {
        String error = "A budget with this name already exists.";
        String budgetName = "budget_Test" + System.currentTimeMillis();
        long accountId = getAccountId();

        MyAgencyPage agencyPage = login();
        BudgetPage budgetPage = agencyPage.navigateToBudgetPage(agencyId, accountId);

        for (int i = 0; i <= 1; i++) {
            budgetPage.clickCreateBudgetButton();
            budgetPage.enterBudgetName(budgetName);
            budgetPage.enterBudgetAmount(100.00);
            budgetPage.submitForm();
        }

        assertConditionTrue(budgetPage.getTextFromElement(UIMapper.DUPLICATED_BUDGET_MSG).contains(error));
    }

    @Test
    public void assertMissingBudgetAmount() throws Exception {
        String error = "Budget amount is required or invalid.";
        String budgetName = "budget_" + System.currentTimeMillis();
        long accountId = getAccountId();

        MyAgencyPage agencyPage = login();
        BudgetPage budgetPage = agencyPage.navigateToBudgetPage(agencyId, accountId);
        budgetPage.clickCreateBudgetButton();
        budgetPage.enterBudgetName(budgetName);
        budgetPage.submitForm();

        assertConditionTrue(budgetPage.getTextFromElement(UIMapper.MISSING_BUDGET_AMOUNT_MSG).contains(error));
    }


    @Test
    public void assertBudgetAmountTooHigh() throws Exception {
        String error = "Budget is too large.";
        String budgetName = "budget_" + System.currentTimeMillis();
        long accountId = getAccountId();

        MyAgencyPage agencyPage = login();
        BudgetPage budgetPage = agencyPage.navigateToBudgetPage(agencyId, accountId);
        budgetPage.clickCreateBudgetButton();
        budgetPage.enterBudgetName(budgetName);
        budgetPage.enterBudgetAmount(2500001.00);
        budgetPage.submitForm();

        assertConditionTrue(budgetPage.getTextFromElement(UIMapper.BUDGET_AMOUNT_TOO_HIGH_MSG).contains(error));
    }

    @Test
    public void assertBudgetAmountTooLow() throws Exception {
        String error = "Budget is too small.";
        String budgetName = "budget_" + System.currentTimeMillis();
        long accountId = getAccountId();

        MyAgencyPage agencyPage = login();
        BudgetPage budgetPage = agencyPage.navigateToBudgetPage(agencyId, accountId);
        budgetPage.clickCreateBudgetButton();
        budgetPage.enterBudgetName(budgetName);
        budgetPage.enterBudgetAmount(0.00);
        budgetPage.submitForm();

        assertConditionTrue(budgetPage.getTextFromElement(UIMapper.BUDGET_AMOUNT_TOO_LOW_MSG).contains(error));
    }

    private long getAccountId() throws Exception {
        return createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles").getAccountId();
    }
}