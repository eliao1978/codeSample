package com.yp.pm.kp.api.regression.budget;


import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.BudgetDTO;
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
public class GetBudgetTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testGetBudgetByID() throws Exception {
        String payload = "{\"budgetIds\" : '[" + ((Budget) getTestData().get("budget")).getBudgetId() + "]', " +
                "\"allowPartialFailure\" : 'false'}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.BUDGET, getAPIResponse(MethodEnum.POST, "account/campaign/budget/get", headers, payload));
        assertResponseError(responseDTO);

        for (ResultsDTO result : responseDTO.getResults()) {
            BudgetDTO budgetDTO = (BudgetDTO) result.getObject();
            assertObjectEqual(budgetDTO.getAccountId(), ((Account) getTestData().get("account")).getAccountId());
            assertObjectEqual(budgetDTO.getId(), ((Budget) getTestData().get("budget")).getBudgetId());
            assertObjectNotNull(budgetDTO.getName());
            assertObjectNotNull(budgetDTO.getDeliveryMethod());
            assertObjectNotNull(budgetDTO.getPeriodType());
            assertObjectEqual(budgetDTO.getStatus(), ((Budget) getTestData().get("budget")).getStatus().toString());
            assertObjectNotNull(budgetDTO.getAmount());
        }
    }

    @Test
    public void testAllowPartialFailure() throws Exception {
        String payload = "{\"budgetIds\" : '[" + ((Budget) getTestData().get("budget")).getBudgetId() + "]', " +
                "\"allowPartialFailure\" : 'true'}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.BUDGET, getAPIResponse(MethodEnum.POST, "account/campaign/budget/get", headers, payload));
        assertResponseError(responseDTO);

        for (ResultsDTO result : responseDTO.getResults()) {
            BudgetDTO budgetDTO = (BudgetDTO) result.getObject();
            assertObjectEqual(budgetDTO.getAccountId(), ((Account) getTestData().get("account")).getAccountId());
            assertObjectEqual(budgetDTO.getId(), ((Budget) getTestData().get("budget")).getBudgetId());
            assertObjectNotNull(budgetDTO.getName());
            assertObjectNotNull(budgetDTO.getDeliveryMethod());
            assertObjectNotNull(budgetDTO.getPeriodType());
            assertObjectEqual(budgetDTO.getStatus(), ((Budget) getTestData().get("budget")).getStatus().toString());
            assertObjectNotNull(budgetDTO.getAmount());
        }
    }

    @Test
    public void testInvalidBudgetId() throws Exception {
        String payload = "{\"budgetIds\" : '[" + 12345 + "]', " +
                "\"allowPartialFailure\" : 'false'}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.BUDGET, getAPIResponse(MethodEnum.POST, "account/campaign/budget/get", headers, payload));
        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 0);

        for (ResultsDTO result : responseDTO.getResults()) {
            assertObjectEqual(((BudgetDTO) result.getObject()).getId(), -1);
        }
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = createBudget(account.getAccountId(), budgetAmount, deliveryMethod);

            testData.put("account", account);
            testData.put("budget", budget);
        }

        return testData;
    }
}