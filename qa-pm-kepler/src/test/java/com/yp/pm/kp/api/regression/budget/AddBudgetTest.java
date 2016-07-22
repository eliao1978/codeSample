package com.yp.pm.kp.api.regression.budget;


import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.BudgetDTO;
import com.yp.pm.kp.model.domain.Account;
import com.yp.util.DBUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddBudgetTest extends BaseAPITest {
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testAddBudget() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");

        String payload = "{\"budgets\" : [{" +
                "\"budget\" : {" +
                "\"name\" : " + "\"BUDGET_" + System.currentTimeMillis() + "\", " +
                "\"periodType\" : \"DAILY\", " +
                "\"amount\" : 100000000, " +
                "\"accountId\" : " + account.getAccountId() + ", " +
                "\"deliveryMethod\" : \"STANDARD\"}}, " +
                "{\"budget\" : {" +
                "\"name\" : " + "\"BUDGET_1" + System.currentTimeMillis() + "\", " +
                "\"periodType\" : \"DAILY\", " +
                "\"amount\" : 10000000, " +
                "\"accountId\" : " + account.getAccountId() + ", " +
                "\"deliveryMethod\" : \"STANDARD\"}}, " +
                "{\"budget\" : {" +
                "\"name\" : " + "\"BUDGET_2" + System.currentTimeMillis() + "\", " +
                "\"periodType\" : \"DAILY\", " +
                "\"amount\" : 20000000, " +
                "\"accountId\" : " + account.getAccountId() + ", " +
                "\"deliveryMethod\" : \"STANDARD\"}}, " +
                "{\"budget\" : {" +
                "\"name\" : " + "\"BUDGET_3" + System.currentTimeMillis() + "\", " +
                "\"periodType\" : \"DAILY\", " +
                "\"amount\" : 1000000, " +
                "\"accountId\" : " + account.getAccountId() + ", " +
                "\"deliveryMethod\" : \"STANDARD\"}}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("AccountId", account.getAccountId().toString());

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.BUDGET, getAPIResponse(MethodEnum.POST, "account/campaign/budget/create", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 4);
        int i = 0;
        for (ResultsDTO result : responseDTO.getResults()) {
            assertConditionTrue(result.getIndex() == i);
            i += 1;

            BudgetDTO budgetDTO = (BudgetDTO) result.getObject();
            assertObjectEqual(budgetDTO.getId(), getBudgetById(account.getAccountId(), budgetDTO.getId()).getBudgetId());
        }
    }

    @Test
    public void testAllowPartialFailure() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");

        String payload = "{\"budgets\" : [{" +
                "\"budget\" : {" +
                "\"name\" : " + "\"BUDGET_" + System.currentTimeMillis() + "\", " +
                "\"periodType\" : \"DAILY\", " +
                "\"amount\" : 100000000, " +
                "\"accountId\" : " + account.getAccountId() + ", " +
                "\"deliveryMethod\" : \"STANDARD\"}}], " +
                "\"allowPartialFailure\" : true}";
        headers.put("AccountId", account.getAccountId().toString());

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.BUDGET, getAPIResponse(MethodEnum.POST, "account/campaign/budget/create", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            BudgetDTO budgetDTO = (BudgetDTO) result.getObject();
            assertObjectEqual(budgetDTO.getId(), getBudgetById(account.getAccountId(), budgetDTO.getId()).getBudgetId());
        }
    }

    @Test
    public void testAddBudgetCustomLimit() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        try {
            DBUtil.update("INSERT INTO PM_CUSTOM_LIMIT_VALIDATION VALUES('budget', 2, ".concat(account.getAccountId().toString()).concat(", 'account', '").concat(userName) + "',SYSDATE,'" + userName + "',SYSDATE)");
            for (int i = 0; i < 2; i++) {
                createBudget(account.getAccountId(), 1000000, "STANDARD");
            }

            String payload = "{\"budgets\" : [{" +
                    "\"budget\" : {" +
                    "\"name\" : " + "\"BUDGET_" + System.currentTimeMillis() + "\", " +
                    "\"periodType\" : \"DAILY\", " +
                    "\"amount\" : 100000000, " +
                    "\"accountId\" : " + account.getAccountId() + ", " +
                    "\"deliveryMethod\" : \"STANDARD\"}}], " +
                    "\"allowPartialFailure\" : false}";
            headers.put("AccountId", account.getAccountId().toString());

            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.BUDGET, getAPIResponse(MethodEnum.POST, "account/campaign/budget/create", headers, payload));

            assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Exceeds maximum number of budgets in account");
        } finally {
            DBUtil.update("DELETE FROM PM_CUSTOM_LIMIT_VALIDATION WHERE PARENT_ID = ".concat(account.getAccountId().toString()));
        }
    }
}