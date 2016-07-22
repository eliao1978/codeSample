package com.yp.pm.kp.ui.regression.budget;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.ui.BudgetPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ViewStatsTableTest extends BaseUITest {

    @Test
    public void assertTableHeader() throws Exception {
        String[] columns = {"Status", "Name", "Budget", "Delivery Method", "# Campaigns", "Clicks", "Impr.", "CTR", "Avg. CPC", "Cost", "Avg. Pos."};
        MyAgencyPage agencyPage = login();
        BudgetPage budgetPage = agencyPage.navigateToBudgetPage(agencyId, getAccountId());

        // ui assertion
        int i = 2;
        for (String label : columns) {
            assertObjectEqual(budgetPage.getTextFromStatsTableHeader(i), label);
            i += 1;
        }
    }

    @Test
    public void assertTableSortingByColumn() throws Exception {
        long accountId = getAccountId();
        for (int i = 0; i <= 4; i++) {
            createBudget(accountId, (100 + i), "standard", "budget_" + System.currentTimeMillis());
        }

        MyAgencyPage agencyPage = login();
        BudgetPage budgetPage = agencyPage.navigateToBudgetPage(agencyId, accountId);

        /**
         * assert sorting by clicking each header column
         * i = 4 Budgets
         * i = 7 Clicks
         * i = 8 Impr.
         * i = 9 CTR
         * i = 10 Avg. CPC
         * i = 11 Cost
         * i = 12 Avg Pos.
         */
        for (int i = 4; i <= 12; i++) {
            if (i != 5 && i != 6) {
                // descending
                budgetPage.clickStatsTableHeaderByColumn(i);
                ArrayList<String> originalDescending = budgetPage.getTextFromStatsTableByColumn(5, i);
                assertConditionTrue(budgetPage.isStatsColumnSorted(originalDescending, BudgetPage.OrderType.DESCENDING));

                // ascending
                budgetPage.clickStatsTableHeaderByColumn(i);
                ArrayList<String> originalAscending = budgetPage.getTextFromStatsTableByColumn(5, i);
                assertConditionTrue(budgetPage.isStatsColumnSorted(originalAscending, BudgetPage.OrderType.ASCENDING));
            }
        }
    }

    @Test
    public void assertFilterBudgetByStatus() throws Exception {
        long accountId = getAccountId();

        // create 5 budgets for testing
        for (int i = 1; i <= 3; i++) {
            createBudget(accountId, 100, "standard", "budget_" + System.currentTimeMillis());
        }

        // get all budgets by account Id
        List<Budget> budgetList = getBudgetByAccountId(accountId);

        // set one budget to be deleted
        List<Long> budgetToDelete = new ArrayList<>();
        budgetToDelete.add(budgetList.get(0).getBudgetId());
        long deletedBudgetId = deleteBudget(accountId, budgetToDelete).get(0);

        MyAgencyPage agencyPage = login();
        BudgetPage budgetPage = agencyPage.navigateToBudgetPage(agencyId, accountId);

        // assert only active budgets are listed
        budgetPage.clickBudgetFilterButton();
        budgetPage.selectAllButDeletedBudgets();

        for (int i = 2; i <= 3; i++) {
            assertObjectEqual("Enabled", budgetPage.getTextFromStatsTable(i, 2));
        }

        budgetPage.clickBudgetFilterButton();
        budgetPage.selectAllBudgets();

        // assert all budgets regardless status are listed
        for (int i = 2; i <= 4; i++) {
            if (budgetPage.getTextFromStatsTable(i, 3).equalsIgnoreCase(getBudgetById(accountId, deletedBudgetId).getName())) {
                assertObjectEqual("Deleted", budgetPage.getTextFromStatsTable(i, 2));
            } else {
                assertObjectEqual("Enabled", budgetPage.getTextFromStatsTable(i, 2));
            }
        }
    }

    private long getAccountId() throws Exception {
        return createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles").getAccountId();
    }
}