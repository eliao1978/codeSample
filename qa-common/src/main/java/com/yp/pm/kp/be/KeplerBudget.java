package com.yp.pm.kp.be;

import com.yp.pm.kp.model.domain.Budget;

import java.util.List;

public interface KeplerBudget {

    Budget createBudget(long accountId, long amount, String deliveryMethod) throws Exception;

    Budget createBudget(long accountId, long amount, String deliveryMethod, String budgetName) throws Exception;

    Budget getBudgetById(long accountId, long budgetId) throws Exception;

    Budget getActiveBudgetByName(String name) throws Exception;

    List<Budget> getBudgetByAccountId(long accountId) throws Exception;

    List<Long> deleteBudgets(long accountId, List<Long> budgets) throws Exception;
}
