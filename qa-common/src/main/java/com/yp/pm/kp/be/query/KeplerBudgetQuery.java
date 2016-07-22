package com.yp.pm.kp.be.query;

public interface KeplerBudgetQuery {

    String GET_ACTIVE_BUDGET_ID_BY_NAME = "SELECT ACCOUNT_ID, BUDGET_ID FROM PM_BUDGET WHERE BUDGET_STATUS = ? AND BUDGET_NAME = ?";
}