package com.yp.pm.kp.api.regression.account;

import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.AccountDTO;
import com.yp.pm.kp.enums.model.TimeZoneEnum;
import com.yp.pm.kp.model.domain.Account;
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
public class GetAccountTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void assertGetAccountById() throws Exception {
        Account account = ((Account) getTestData().get("account"));
        String payload = "{\"allowPartialFailure\" : 'false'}";
        headers.put("accountId", String.valueOf(account.getAccountId()));

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.ACCOUNT, getAPIResponse(MethodEnum.POST, "account/get", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            AccountDTO accountDTO = (AccountDTO) result.getObject();
            assertObjectEqual(accountDTO.getAccountId(), account.getAccountId());
            assertObjectEqual(accountDTO.getAgencyId(), account.getAgencyId());
            assertObjectEqual(accountDTO.getTimeZone(), TimeZoneEnum.fromValue("America/Los_Angeles").toString());
            assertObjectEqual(accountDTO.getStatus(), account.getAccountStatus().toString());
            assertObjectNotNull(accountDTO.getClientName());
        }
    }

    @Test
    public void assertAllowPartialFailure() throws Exception {
        Account account = ((Account) getTestData().get("account"));
        String payload = "{\"allowPartialFailure\" : 'true'}";
        headers.put("accountId", String.valueOf(account.getAccountId()));

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.ACCOUNT, getAPIResponse(MethodEnum.POST, "account/get", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            AccountDTO accountDTO = (AccountDTO) result.getObject();
            assertObjectEqual(accountDTO.getAccountId(), account.getAccountId());
            assertObjectEqual(accountDTO.getAgencyId(), account.getAgencyId());
            assertObjectEqual(accountDTO.getTimeZone(), TimeZoneEnum.fromValue("America/Los_Angeles").toString());
            assertObjectEqual(accountDTO.getStatus(), account.getAccountStatus().toString());
            assertObjectNotNull(accountDTO.getClientName());
        }
    }

    @Test
    public void assertInvalidAccountId() throws Exception {
        String payload = "{\"allowPartialFailure\" : 'false'}";
        headers.put("accountId", "12345");

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.ACCOUNT, getAPIResponse(MethodEnum.POST, "account/get", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 0);

        for (ResultsDTO result : responseDTO.getResults()) {
            assertObjectEqual(((AccountDTO) result.getObject()).getAccountId(), -1);
        }
    }

    @Test
    public void assertMissingAccountId() throws Exception {
        String payload = "{\"allowPartialFailure\" : 'false'}";

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.ACCOUNT, getAPIResponse(MethodEnum.POST, "account/get", headers, payload));

        assertConditionTrue(responseDTO.getResultStatus().getError().getType().equals(ErrorTypeEnum.USER_ERROR));
        assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Account id can not be null");
    }

    @Test
    public void assertAccountIdIsNull() throws Exception {
        String payload = "{\"allowPartialFailure\" : 'false'}";
        headers.put("accountId", "");

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.ACCOUNT, getAPIResponse(MethodEnum.POST, "account/get", headers, payload));

        assertConditionTrue(responseDTO.getResultStatus().getError().getType().equals(ErrorTypeEnum.USER_ERROR));
        assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Account id can not be null");
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