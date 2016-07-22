package com.yp.pm.kp.api.regression.budget;

import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.BudgetDTO;
import com.yp.pm.kp.enums.model.BudgetStatusEnum;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeleteBudgetTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testDeleteBudget() throws Exception {
        Budget budget = createBudget(((Account) getTestData().get("account")).getAccountId(), budgetAmount, deliveryMethod);

        String payload = "{\"budgetIds\" : '[" + budget.getBudgetId() + "]', " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.BUDGET, getAPIResponse(MethodEnum.DELETE, "account/campaign/budget/delete", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        Budget deletedBudget = getBudgetById(((Account) getTestData().get("account")).getAccountId(), budget.getBudgetId());
        assertObjectEqual(deletedBudget.getStatus().toString(), BudgetStatusEnum.DELETED.toString());
    }

    @Test
    public void testAllowPartialFailure() throws Exception {
        Budget budget = createBudget(((Account) getTestData().get("account")).getAccountId(), budgetAmount, deliveryMethod);

        String payload = "{\"budgetIds\" : '[" + budget.getBudgetId() + "]', " +
                "\"allowPartialFailure\" : true}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.BUDGET, getAPIResponse(MethodEnum.DELETE, "account/campaign/budget/delete", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        Budget deletedBudget = getBudgetById(((Account) getTestData().get("account")).getAccountId(), budget.getBudgetId());
        assertObjectEqual(deletedBudget.getStatus().toString(), BudgetStatusEnum.DELETED.toString());
    }

    @Test
    public void testInvalidBudgetId() throws Exception {
        Budget budget = createBudget(((Account) getTestData().get("account")).getAccountId(), budgetAmount, deliveryMethod);

        String payload = "{\"budgetIds\" : '[" + 12345 + "]', " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.BUDGET, getAPIResponse(MethodEnum.DELETE, "account/campaign/budget/delete", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 0);

        for (ResultsDTO result : responseDTO.getResults()) {
            assertObjectEqual(((BudgetDTO) result.getObject()).getId(), -1);
        }

        Budget deletedBudget = getBudgetById(((Account) getTestData().get("account")).getAccountId(), budget.getBudgetId());
        assertObjectEqual(deletedBudget.getStatus().toString(), BudgetStatusEnum.ACTIVE.toString());
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            testData.put("account", account);
        }

        return testData;
    }
}