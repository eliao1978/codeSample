package com.yp.pm.kp.api.regression.error;

import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
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
public class UserErrorTest extends BaseAPITest {

    private Map<String, String> headers = getRequestHeader();

    @Test
    public void assertAgencyIdIsNull() throws Exception {
        String payload = "{\"account\" : {\"clientName\": \"\" + \"ACCT_\" + System.currentTimeMillis() + \"\", \"timezone\": \"PT\"}}";
        headers.put("agencyId", "");

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.ACCOUNT, getAPIResponse(MethodEnum.POST, "account/create", headers, payload));

        assertObjectEqual(responseDTO.getResultStatus().getStatus(), "FAILURE");
        assertConditionTrue(responseDTO.getResultStatus().getError().getType().equals(ErrorTypeEnum.USER_ERROR));
        assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Agency id can not be null");
        assertObjectEqual(responseDTO.getResultStatus().getError().getCode(), 100000);
    }

    @Test
    public void assertAccountIdIsNull() throws Exception {
        String payload = "{\"allowPartialFailure\" : 'false'}";
        headers.put("accountId", "");

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.ACCOUNT, getAPIResponse(MethodEnum.POST, "account/get", headers, payload));

        assertObjectEqual(responseDTO.getResultStatus().getStatus(), "FAILURE");
        assertConditionTrue(responseDTO.getResultStatus().getError().getType().equals(ErrorTypeEnum.USER_ERROR));
        assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Account id can not be null");
        assertObjectEqual(responseDTO.getResultStatus().getError().getCode(), 110000);
    }
}