package com.yp.pm.kp.ui.regression.budget;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.enums.model.BudgetStatusEnum;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.ui.BudgetPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeleteBudgetTest extends BaseUITest {

    @Test
    public void assertDeleteBudget() throws Exception {
        String budgetName = "budget_" + System.currentTimeMillis();
        long accountId = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles").getAccountId();
        Budget budget = createBudget(accountId, 100, "standard", budgetName);

        MyAgencyPage agencyPage = login();
        BudgetPage budgetPage = agencyPage.navigateToBudgetPage(agencyId, accountId);
        budgetPage.clickBudgetStatsTableCheckbox(2, 1);
        budgetPage.clickDeleteBudgetButton();

        Budget updatedBudget = getBudgetById(budget.getAccountId(), budget.getBudgetId());
        assertObjectNull(budgetPage.getTextFromStatsTable(2, 4));
        assertConditionTrue(updatedBudget.getStatus().equals(BudgetStatusEnum.DELETED));
    }
}