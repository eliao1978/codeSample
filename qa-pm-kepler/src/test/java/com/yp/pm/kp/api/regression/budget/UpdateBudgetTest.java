package com.yp.pm.kp.api.regression.budget;

import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.BudgetDTO;
import com.yp.pm.kp.enums.model.BudgetStatusEnum;
import com.yp.pm.kp.enums.model.DeliveryMethodEnum;
import com.yp.pm.kp.enums.model.PeriodTypeEnum;
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
public class UpdateBudgetTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private static Map<String, Object> testData2;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testUpdateBudget() throws Exception {
        String newBudgetName = "BUDGET_" + System.currentTimeMillis();
        String payload = "{\"budgets\" : [{" +
                "\"budget\" : {" +
                "\"budgetId\" : " + ((Budget) getTestData().get("budget")).getBudgetId() + ", " +
                "\"status\" : \"ACTIVE\", " +
                "\"name\" : \"" + newBudgetName + "\", " +
                "\"deliveryMethod\" : \"ACCELERATED\", " +
                "\"periodType\" : \"MONTHLY\", " +
                "\"amount\" : 200000000, " +
                "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + "}}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.BUDGET, getAPIResponse(MethodEnum.PUT, "account/campaign/budget/update", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            BudgetDTO budgetDTO = (BudgetDTO) result.getObject();
            Budget updatedBudget = getBudgetById(((Account) getTestData().get("account")).getAccountId(), ((Budget) getTestData().get("budget")).getBudgetId());
            assertObjectEqual(budgetDTO.getId(), ((Budget) getTestData().get("budget")).getBudgetId());
            assertObjectEqual(updatedBudget.getAmount().toMicroDollar(), 200000000);
            assertObjectEqual(updatedBudget.getStatus().toString(), BudgetStatusEnum.ACTIVE.toString());
            assertObjectEqual(updatedBudget.getName(), newBudgetName);
            assertObjectEqual(updatedBudget.getDeliveryMethod().toString(), DeliveryMethodEnum.ACCELERATED.toString());
            assertObjectEqual(updatedBudget.getPeriodType().toString(), PeriodTypeEnum.MONTHLY.toString());
        }
    }

    @Test
    public void testAllowPartialFailure() throws Exception {
        String newBudgetName = "BUDGET_" + System.currentTimeMillis();
        String payload = "{\"budgets\" : [{" +
                "\"budget\" : {" +
                "\"budgetId\" : " + ((Budget) getTestData().get("budget")).getBudgetId() + ", " +
                "\"status\" : \"ACTIVE\", " +
                "\"name\" : \"" + newBudgetName + "\", " +
                "\"deliveryMethod\" : \"ACCELERATED\", " +
                "\"periodType\" : \"MONTHLY\", " +
                "\"amount\" : 200000000, " +
                "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + "}}], " +
                "\"allowPartialFailure\" : true}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.BUDGET, getAPIResponse(MethodEnum.PUT, "account/campaign/budget/update", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            BudgetDTO budgetDTO = (BudgetDTO) result.getObject();
            Budget updatedBudget = getBudgetById(((Account) getTestData().get("account")).getAccountId(), ((Budget) getTestData().get("budget")).getBudgetId());
            assertObjectEqual(budgetDTO.getId(), ((Budget) getTestData().get("budget")).getBudgetId());
            assertObjectEqual(updatedBudget.getAmount().toMicroDollar(), 200000000);
            assertObjectEqual(updatedBudget.getStatus().toString(), BudgetStatusEnum.ACTIVE.toString());
            assertObjectEqual(updatedBudget.getName(), newBudgetName);
            assertObjectEqual(updatedBudget.getDeliveryMethod().toString(), DeliveryMethodEnum.ACCELERATED.toString());
            assertObjectEqual(updatedBudget.getPeriodType().toString(), PeriodTypeEnum.MONTHLY.toString());
        }
    }

    @Test
    public void testIgnoreUpdateBudget() throws Exception {
        String newBudgetName = "BUDGET_" + System.currentTimeMillis();
        String payload = "{\"budgets\" : [{" +
                "\"budget\" : {" +
                "\"budgetId\" : -1, " +
                "\"status\" : \"ACTIVE\", " +
                "\"name\" : \"" + newBudgetName + "\", " +
                "\"deliveryMethod\" : \"ACCELERATED\", " +
                "\"periodType\" : \"MONTHLY\", " +
                "\"amount\" : 200000000, " +
                "\"accountId\" : " + ((Account) getTestData2().get("account")).getAccountId() + "}}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.BUDGET, getAPIResponse(MethodEnum.PUT, "account/campaign/budget/update", headers, payload));

        assertResponseError(responseDTO);

        for (ResultsDTO result : responseDTO.getResults()) {
            BudgetDTO budgetDTO = (BudgetDTO) result.getObject();
            Budget updatedBudget = getBudgetById(((Account) getTestData2().get("account")).getAccountId(), ((Budget) getTestData2().get("budget")).getBudgetId());
            assertObjectEqual(budgetDTO.getId(), -1);
            assertObjectEqual(updatedBudget.getAmount().toMicroDollar(), 100000000);
            assertObjectEqual(updatedBudget.getStatus().toString(), BudgetStatusEnum.ACTIVE.toString());
            assertConditionTrue(!updatedBudget.getName().equals(newBudgetName));
            assertObjectEqual(updatedBudget.getDeliveryMethod().toString(), DeliveryMethodEnum.STANDARD.toString());
            assertObjectEqual(updatedBudget.getPeriodType().toString(), PeriodTypeEnum.DAILY.toString());
        }
    }

    @Test
    public void testObjectProblemType() throws Exception {
        long accountId = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles").getAccountId();
        Budget budget1 = createBudget(accountId, budgetAmount, deliveryMethod);
        Budget budget2 = createBudget(accountId, budgetAmount, deliveryMethod);
        Budget budget3 = createBudget(accountId, budgetAmount, deliveryMethod);
        long amount = budget1.getAmount().toMicroDollar();


        String budget1String = "{\"budget\" : {" +
                "\"budgetId\" : " + budget1.getBudgetId() + ", " +
                "\"status\" : \"ACTIVE\", " +
                "\"amount\" : 300000000, " +
                "\"accountId\" : " + accountId + "}}";

        String budget2String = "{\"budget\" : {" +
                "\"budgetId\" : " + budget2.getBudgetId() + ", " +
                "\"status\" : \"DELETED\", " +
                "\"accountId\" : " + accountId + "}}";

        String budget3String = "{\"budget\" : {" +
                "\"budgetId\" : " + budget3.getBudgetId() + ", " +
                "\"status\" : \"ACTIVE\", " +
                "\"amount\" : 300000000, " +
                "\"accountId\" : " + accountId + "}}";


        String command = "curl -X PUT " +
                "-d '{\"budgets\" : [" + budget1String + "," + budget2String + "," + budget3String + "], " +
                "\"allowPartialFailure\" : false}' " +
                "-H '" + contentType + "' " +
                "-H 'username:'" + userName + "'' " +
                "-H 'requestid:'" + requestId + "'' " +
                "-H 'accountId:'" + accountId + "'' " +
                "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/account/campaign/budget/update";

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.BUDGET, getAPIResponse(command));


        for (ResultsDTO result : responseDTO.getResults()) {
            BudgetDTO budgetDTO = (BudgetDTO) result.getObject();
            if (result.getError().getMessage() != null) {
                assertObjectEqual(budgetDTO.getId(), -3);
            } else {
                assertObjectEqual(budgetDTO.getId(), -2);
            }
        }

        assertObjectEqual(getBudgetById(accountId, budget1.getBudgetId()).getStatus().toString(), BudgetStatusEnum.ACTIVE.toString());
        assertObjectEqual(getBudgetById(accountId, budget2.getBudgetId()).getStatus().toString(), BudgetStatusEnum.ACTIVE.toString());
        assertObjectEqual(getBudgetById(accountId, budget3.getBudgetId()).getStatus().toString(), BudgetStatusEnum.ACTIVE.toString());
        assertObjectEqual(getBudgetById(accountId, budget1.getBudgetId()).getAmount().toMicroDollar(), amount);
        assertObjectEqual(getBudgetById(accountId, budget2.getBudgetId()).getAmount().toMicroDollar(), amount);
        assertObjectEqual(getBudgetById(accountId, budget3.getBudgetId()).getAmount().toMicroDollar(), amount);
    }

    @Test
    public void testInvalidBudgetId() throws Exception {
        String newBudgetName = "BUDGET_" + System.currentTimeMillis();
        String payload = "{\"budgets\" : [{" +
                "\"budget\" : {" +
                "\"budgetId\" : \"12345\", " +
                "\"status\" : \"ACTIVE\", " +
                "\"name\" : \"" + newBudgetName + "\", " +
                "\"deliveryMethod\" : \"ACCELERATED\", " +
                "\"periodType\" : \"MONTHLY\", " +
                "\"amount\" : 200000000, " +
                "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + "}}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.BUDGET, getAPIResponse(MethodEnum.PUT, "account/campaign/budget/update", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 0);

        for (ResultsDTO result : responseDTO.getResults()) {
            assertObjectEqual(((BudgetDTO) result.getObject()).getId(), -1);
        }
    }

    @Test
    public void testMissingBudgetId() throws Exception {
        String newBudgetName = "BUDGET_" + System.currentTimeMillis();
        String payload = "{\"budgets\" : [{" +
                "\"budget\" : {" +
                "\"budgetId\" : \"\", " +
                "\"status\" : \"ACTIVE\", " +
                "\"name\" : \"" + newBudgetName + "\", " +
                "\"deliveryMethod\" : \"ACCELERATED\", " +
                "\"periodType\" : \"MONTHLY\", " +
                "\"amount\" : 200000000, " +
                "\"accountId\" : " + ((Account) getTestData().get("account")).getAccountId() + "}}], " +
                "\"allowPartialFailure\" : false}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.BUDGET, getAPIResponse(MethodEnum.PUT, "account/campaign/budget/update", headers, payload));
        assertObjectEqual(responseDTO.getTotalCount(), 0);
        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Budget id can not be null");
    }

    private Map<String, Object> getTestData2() throws Exception {
        if (testData2 == null) {
            testData2 = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = createBudget(account.getAccountId(), budgetAmount, deliveryMethod);

            testData2.put("account", account);
            testData2.put("budget", budget);
        }

        return testData2;
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