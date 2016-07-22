package com.yp.pm.kp.api.regression.account;

import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.AccountDTO;
import com.yp.pm.kp.model.domain.Account;
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
public class AddAccountTest extends BaseAPITest {

    private Map<String, String> headers = getRequestHeader();

    @Test
    public void assertAddAccount() throws Exception {
        String clientName = "ACCT_" + System.currentTimeMillis();
        String businessName = "BIZ_" + System.currentTimeMillis();
        String payload = "{\"account\" : {" +
                "\"clientName\": \"" + clientName + "" +
                "\", \"timezone\": \"PT\", " +
                "\"businessName\": \"" + businessName + "\"}}";
        headers.put("agencyId", "142");

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.ACCOUNT, getAPIResponse(MethodEnum.POST, "account/create", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            AccountDTO accountDTO = (AccountDTO) result.getObject();
            Account account = getAccountById(accountDTO.getAccountId());
            assertObjectEqual(accountDTO.getAccountId(), account.getAccountId());
            assertObjectEqual(account.getBusinessName(), businessName);
            assertObjectEqual(account.getClientName(), clientName);
        }
    }

    @Test
    public void assertBusinessNameTooLong() throws Exception {
        String clientName = "ACCT_" + System.currentTimeMillis();
        String businessName = "BIZ_zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz" + System.currentTimeMillis();
        String payload = "{\"account\" : {" +
                "\"clientName\": \"" + clientName + "" +
                "\", \"timezone\": \"PT\", " +
                "\"businessName\": \"" + businessName + "\"}}";
        headers.put("agencyId", "142");

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.ACCOUNT, getAPIResponse(MethodEnum.POST, "account/create", headers, payload));

        assertObjectEqual(responseDTO.getResultStatus().getStatus(), "FAILURE");
        assertConditionTrue(responseDTO.getResults().get(0).getError().getType().equals(ErrorTypeEnum.USER_ERROR));
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Business name is too long");
    }

    @Test
    public void assertMissingAgentId() throws Exception {
        String payload = "{\"account\" : {\"clientName\": \"" + "ACCT_" + System.currentTimeMillis() + "\", \"timezone\": \"PT\"}}";
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.ACCOUNT, getAPIResponse(MethodEnum.POST, "account/create", headers, payload));

        assertObjectEqual(responseDTO.getResultStatus().getStatus(), "FAILURE");
        assertConditionTrue(responseDTO.getResultStatus().getError().getType().equals(ErrorTypeEnum.USER_ERROR));
        assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Agency id can not be null");
    }

    @Test
    public void assertMissingClientName() throws Exception {
        String payload = "{\"account\" : {\"clientName\": \"\", \"timezone\": \"PT\"}}";
        headers.put("agencyId", "142");

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.ACCOUNT, getAPIResponse(MethodEnum.POST, "account/create", headers, payload));

        assertObjectEqual(responseDTO.getResultStatus().getStatus(), "FAILURE");
        assertConditionTrue(responseDTO.getResults().get(0).getError().getType().equals(ErrorTypeEnum.USER_ERROR));
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Account name is required");
    }

    @Test
    public void assertMissingTimezone() throws Exception {
        String payload = "{\"account\" : {\"clientName\": \"" + "ACCT_" + System.currentTimeMillis() + "\", \"timezone\": \"\"}}";
        headers.put("agencyId", "142");

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.ACCOUNT, getAPIResponse(MethodEnum.POST, "account/create", headers, payload));

        assertObjectEqual(responseDTO.getResultStatus().getStatus(), "FAILURE");
        assertConditionTrue(responseDTO.getResultStatus().getError().getType().equals(ErrorTypeEnum.SYSTEM_ERROR));
    }
}