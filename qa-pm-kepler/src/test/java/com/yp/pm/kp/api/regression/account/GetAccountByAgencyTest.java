package com.yp.pm.kp.api.regression.account;

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
public class GetAccountByAgencyTest extends BaseAPITest {

    private Map<String, String> headers = getRequestHeader();

    @Test
    public void assertGetAccountByAgencyId() throws Exception {
        String payload = "{\"paging\" : { \"startIndex\" : 0, \"numberResults\" : 10 }, \"allowPartialFailure\" : 'false'}";
        headers.put("agencyId", "142");

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.ACCOUNT, getAPIResponse(MethodEnum.POST, "agency/accounts/get", headers, payload));

        assertResponseError(responseDTO);
        assertConditionTrue(responseDTO.getTotalCount() > 0);
        assertObjectEqual(responseDTO.getResults().size(), 10);
    }

    @Test
    public void assertAllowPartialFailure() throws Exception {
        String payload = "{\"paging\" : { \"startIndex\" : 0, \"numberResults\" : 10 }, \"allowPartialFailure\" : 'true'}";
        headers.put("agencyId", "142");

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.ACCOUNT, getAPIResponse(MethodEnum.POST, "agency/accounts/get", headers, payload));

        assertResponseError(responseDTO);
        assertConditionTrue(responseDTO.getTotalCount() > 0);
        assertObjectEqual(responseDTO.getResults().size(), 10);
    }

    @Test
    public void assertInvalidAgencyId() throws Exception {
        String payload = "{\"paging\" : { \"startIndex\" : 0, \"numberResults\" : 10 }, \"allowPartialFailure\" : 'false'}";
        headers.put("agencyId", "12345");

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.ACCOUNT, getAPIResponse(MethodEnum.POST, "agency/accounts/get", headers, payload));

        assertResponseError(responseDTO);
        assertConditionTrue(responseDTO.getTotalCount() == 0);
        assertObjectEqual(responseDTO.getResults().size(), 0);
    }

    @Test
    public void assertAgencyIdIsNull() throws Exception {
        String payload = "{\"paging\" : { \"startIndex\" : 0, \"numberResults\" : 10 }, \"allowPartialFailure\" : 'false'}";
        headers.put("agencyId", "");

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.ACCOUNT, getAPIResponse(MethodEnum.POST, "agency/accounts/get", headers, payload));

        assertObjectEqual(responseDTO.getResultStatus().getStatus(), "FAILURE");
        assertObjectEqual(responseDTO.getResultStatus().getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Agency id can not be null");
    }

    @Test
    public void assertMissingAgencyId() throws Exception {
        String payload = "{\"paging\" : { \"startIndex\" : 0, \"numberResults\" : 10 }, \"allowPartialFailure\" : 'false'}";
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.ACCOUNT, getAPIResponse(MethodEnum.POST, "agency/accounts/get", headers, payload));

        assertObjectEqual(responseDTO.getResultStatus().getStatus(), "FAILURE");
        assertObjectEqual(responseDTO.getResultStatus().getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Agency id can not be null");
    }
}