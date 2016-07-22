package com.yp.pm.kp.api.regression.adExtension.phone;


import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.PhoneAdExtensionDTO;
import com.yp.pm.kp.enums.model.PhoneAdExtensionStatusEnum;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.PhoneAdExtension;
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
public class GetPhoneAdExtensionByAccountTest extends BaseAPITest {
    private Map<String, String> headers = getRequestHeader();


    @Test
    public void testGetAdExtensionByAccountId() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        PhoneAdExtension phoneAdExtension = createPhoneAdExtension(account.getAccountId());

        String payload = "{}";
        headers.put("accountId", account.getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.PHONE_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/phone/get", headers, payload));
        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            PhoneAdExtensionDTO phoneAdExtensionDTO = (PhoneAdExtensionDTO) result.getObject();
            assertObjectEqual(phoneAdExtensionDTO.getAccountId(), phoneAdExtension.getAccountId());
            assertObjectEqual(phoneAdExtensionDTO.getId(), phoneAdExtension.getAdExtensionId());
            assertObjectEqual(phoneAdExtensionDTO.getStatus(), phoneAdExtension.getAdExtensionStatus().toString());
            assertObjectNotNull(phoneAdExtensionDTO.getCountryCode());
            assertObjectNotNull(phoneAdExtensionDTO.getPhoneNumber());
        }
    }

    @Test
    public void testGetDeletedAdExtensionByAccountId() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        PhoneAdExtension phoneAdExtension1 = createPhoneAdExtension(account.getAccountId());
        deletePhoneAdExtension(phoneAdExtension1);

        PhoneAdExtension phoneAdExtension = new PhoneAdExtension();
        phoneAdExtension.setAccountId(account.getAccountId());
        phoneAdExtension.setCountryCode("1");
        phoneAdExtension.setPhoneNumber("8182758447");
        phoneAdExtension.setIsCallableOnly(true);
        phoneAdExtension.setAdExtensionStatus(PhoneAdExtensionStatusEnum.ACTIVE);
        createPhoneAdExtension(phoneAdExtension);

        String payload = "{}";
        headers.put("accountId", account.getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.PHONE_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/phone/get", headers, payload));
        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 2);
        assertObjectEqual(responseDTO.getResults().size(), 2);

    }

    @Test
    public void testInvalidAccountId() throws Exception {
        String command = "curl -X POST " +
                "-d '{}' " +
                "-H '" + contentType + "' " +
                "-H 'username:'" + userName + "'' " +
                "-H 'requestid:'" + requestId + "'' " +
                "-H 'accountId:'12345'' " +
                "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/account/ext/location/get";

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.PHONE_AD_EXTENSION, getAPIResponse(command));
        assertObjectEqual(responseDTO.getResults().size(), 0);
        assertObjectEqual(responseDTO.getTotalCount(), 0L);
    }

    @Test
    public void testMissingAccountIdHeader() throws Exception {
        String command = "curl -X POST " +
                "-d '{}' " +
                "-H '" + contentType + "' " +
                "-H 'username:'" + userName + "'' " +
                "-H 'requestid:'" + requestId + "'' " +
                "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/account/ext/phone/get";

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.PHONE_AD_EXTENSION, getAPIResponse(command));
        assertObjectEqual(responseDTO.getResultStatus().getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Account id can not be null");
    }

    @Test
    public void testMissingAccountId() throws Exception {
        String command = "curl -X POST " +
                "-d '{}' " +
                "-H '" + contentType + "' " +
                "-H 'username:'" + userName + "'' " +
                "-H 'requestid:'" + requestId + "'' " +
                "-H 'accountId:''' " +
                "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/account/ext/phone/get";

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.PHONE_AD_EXTENSION, getAPIResponse(command));
        assertObjectEqual(responseDTO.getResultStatus().getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Account id can not be null");
    }
}