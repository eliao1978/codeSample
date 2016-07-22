package com.yp.pm.kp.api.regression.authorization;

import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.enums.other.UserEnum;
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
public class AgencyLevelTest extends BaseAPITest {

    @Test
    public void assertAgencyLevelAuthorization() throws Exception {
        Map<String, String> headers = getRequestHeader(UserEnum.KP_AGENCY_ADMIN.getValue());
        headers.put("agencyId", String.valueOf(agencyId));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AUTHORIZATION, getAPIResponse(MethodEnum.GET, "authorization/agency/get?isReadOnly=true", headers, null));
        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.isAuthorized(), true);
    }

    @Test
    public void assertAgencyIdIsNull() throws Exception {
        Map<String, String> headers = getRequestHeader(UserEnum.KP_AGENCY_ADMIN.getValue());
        headers.put("agencyId", "");
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AUTHORIZATION, getAPIResponse(MethodEnum.GET, "authorization/agency/get?isReadOnly=true", headers, null));
        assertObjectEqual(responseDTO.getResultStatus().getStatus(), "FAILURE");
        assertObjectEqual(responseDTO.getResultStatus().getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Agency id can not be null");
    }

    @Test
    public void assertMissingAgencyId() throws Exception {
        Map<String, String> headers = getRequestHeader(UserEnum.KP_AGENCY_ADMIN.getValue());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AUTHORIZATION, getAPIResponse(MethodEnum.GET, "authorization/agency/get?isReadOnly=true", headers, null));
        assertObjectEqual(responseDTO.getResultStatus().getStatus(), "FAILURE");
        assertObjectEqual(responseDTO.getResultStatus().getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Agency id can not be null");
    }

    @Test
    public void assertAccountLevelAuthorization() throws Exception {
        Map<String, String> headers = getRequestHeader(UserEnum.KP_ACCOUNT_ADMIN.getValue());
        headers.put("accountId", "175");
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AUTHORIZATION, getAPIResponse(MethodEnum.GET, "authorization/account/get?isReadOnly=false", headers, null));
        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.isAuthorized(), true);
    }

    @Test
    public void assertAccountIdIsNull() throws Exception {
        Map<String, String> headers = getRequestHeader(UserEnum.KP_ACCOUNT_ADMIN.getValue());
        headers.put("accountId", "");
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AUTHORIZATION, getAPIResponse(MethodEnum.GET, "authorization/account/get?isReadOnly=false", headers, null));
        assertObjectEqual(responseDTO.getResultStatus().getStatus(), "FAILURE");
        assertObjectEqual(responseDTO.getResultStatus().getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Account id can not be null");
    }

    @Test
    public void assertMissingAccountId() throws Exception {
        Map<String, String> headers = getRequestHeader(UserEnum.KP_ACCOUNT_ADMIN.getValue());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.AUTHORIZATION, getAPIResponse(MethodEnum.GET, "authorization/account/get?isReadOnly=false", headers, null));
        assertObjectEqual(responseDTO.getResultStatus().getStatus(), "FAILURE");
        assertObjectEqual(responseDTO.getResultStatus().getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Account id can not be null");
    }
}