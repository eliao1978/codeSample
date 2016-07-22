package com.yp.pm.kp.be.impl;

import com.yp.pm.kp.be.KeplerBudget;
import com.yp.pm.kp.be.query.KeplerBudgetQuery;
import com.yp.pm.kp.enums.model.BudgetStatusEnum;
import com.yp.pm.kp.enums.model.DeliveryMethodEnum;
import com.yp.pm.kp.enums.model.PeriodTypeEnum;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.other.Money;
import com.yp.pm.kp.service.domain.BudgetService;
import com.yp.util.DBUtil;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class KeplerBudgetImpl extends BaseKeplerImpl implements KeplerBudget, KeplerBudgetQuery {

    @Inject
    private BudgetService service;

    /**
     * create budget
     *
     * @param accountId      long
     * @param budgetAmount   long
     * @param deliveryMethod String
     * @return Budget
     * @throws Exception
     */
    public Budget createBudget(long accountId, long budgetAmount, String deliveryMethod) throws Exception {
        return createBudget(accountId, budgetAmount, deliveryMethod, null);
    }

    /**
     * create budget
     *
     * @param accountId      long
     * @param budgetAmount   long
     * @param deliveryMethod String
     * @param budgetName     String
     * @return Budget
     * @throws Exception
     */
    public Budget createBudget(long accountId, long budgetAmount, String deliveryMethod, String budgetName) throws Exception {

        List<Budget> budgetList = new ArrayList<>();
        Budget budget = new Budget();
        budget.setAccountId(accountId);
        budget.setAmount(Money.fromDollar(budgetAmount));
        if (budgetName != null) {
            budget.setName(budgetName);
        } else {
            budget.setName("BUDGET_" + System.currentTimeMillis());
        }
        budget.setStatus(BudgetStatusEnum.ACTIVE);
        budget.setDeliveryMethod(DeliveryMethodEnum.fromValue(deliveryMethod.toLowerCase()));
        budget.setPeriodType(PeriodTypeEnum.DAILY);
        budget.setCreateTimestamp(now);
        budget.setLastUpdateTimestamp(now);
        budgetList.add(budget);

        long budgetId = service.createBudgets(budgetList).get(0);
        logger.debug("Budget [" + budgetId + "] created");
        return getBudgetById(accountId, budgetId);
    }

    /**
     * get budget by budget id
     *
     * @param accountId long
     * @param budgetId  long
     * @return Budget
     */
    public Budget getBudgetById(long accountId, long budgetId) {
        return service.getBudget(accountId, budgetId);
    }

    /**
     * get budget by account id
     *
     * @param accountId long
     * @return List<Budget>
     */
    public List<Budget> getBudgetByAccountId(long accountId) {
        return service.getBudgets(accountId);
    }

    /**
     * delete budgets
     *
     * @param accountId long
     * @param budgets   List<Long>
     * @return List<Long>
     * @throws Exception
     */
    public List<Long> deleteBudgets(long accountId, List<Long> budgets) throws Exception {
        return service.deleteBudgets(accountId, budgets);
    }

    /**
     * get active budget by budget name
     *
     * @param name String
     * @return Budget
     * @throws Exception
     */
    public Budget getActiveBudgetByName(String name) throws Exception {
        List<Map<String, Object>> queryList = DBUtil.queryForList(GET_ACTIVE_BUDGET_ID_BY_NAME, new Object[]{"active", name}, new int[]{Types.VARCHAR, Types.VARCHAR});

        if (queryList.size() > 1) {
            throw new Exception("Multiple active accounts were found");
        } else if (queryList.size() == 1) {
            return getBudgetById(Long.valueOf(queryList.get(0).get("ACCOUNT_ID").toString()), Long.valueOf(queryList.get(0).get("BUDGET_ID").toString()));
        } else {
            return null;
        }
    }
}
