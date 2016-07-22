package com.yp.pm.kp.api.regression.error;

import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SystemErrorTest extends BaseAPITest {

    @Test
    public void testInvalidUserName() throws Exception {
        String command = "curl -X POST " +
                "-d '{\"account\" : {\"clientName\": \"" + "ACCT_" + System.currentTimeMillis() + "\", \"timezone\": \"PT\"}}' " +
                "-H '" + contentType + "' " +
                "-H 'username:'" + "test@yp.com" + "'' " +
                "-H 'requestid:'" + requestId + "'' " +
                "-H 'agencyId:'" + agencyId + "'' " +
                "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/account/create";

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.ACCOUNT, getAPIResponse(command));
        assertObjectEqual(responseDTO.getResultStatus().getStatus(), "FAILURE");
        assertConditionTrue(responseDTO.getResultStatus().getError().getType().equals(ErrorTypeEnum.SYSTEM_ERROR));
        assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Invalid User Name");
        assertObjectEqual(responseDTO.getResultStatus().getError().getCode(), 230003);
    }

    @Test
    public void testMissingUserName() throws Exception {
        String command = "curl -X POST " +
                "-d '{\"account\" : {\"clientName\": \"" + "ACCT_" + System.currentTimeMillis() + "\", \"timezone\": \"PT\"}}' " +
                "-H '" + contentType + "' " +
                "-H 'requestid:'" + requestId + "'' " +
                "-H 'agencyId:'" + agencyId + "'' " +
                "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/account/create";

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.ACCOUNT, getAPIResponse(command));
        assertObjectEqual(responseDTO.getResultStatus().getStatus(), "FAILURE");
        assertConditionTrue(responseDTO.getResultStatus().getError().getType().equals(ErrorTypeEnum.SYSTEM_ERROR));
        assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Username can not be null");
        assertObjectEqual(responseDTO.getResultStatus().getError().getCode(), 230001);
    }

    @Test
    public void testMissingRequestId() throws Exception {
        String command = "curl -X POST " +
                "-d '{\"account\" : {\"clientName\": \"" + "ACCT_" + System.currentTimeMillis() + "\", \"timezone\": \"PT\"}}' " +
                "-H '" + contentType + "' " +
                "-H 'username:'" + userName + "'' " +
                "-H 'agencyId:'" + agencyId + "'' " +
                "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/account/create";

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.ACCOUNT, getAPIResponse(command));
        assertObjectEqual(responseDTO.getResultStatus().getStatus(), "FAILURE");
        assertConditionTrue(responseDTO.getResultStatus().getError().getType().equals(ErrorTypeEnum.SYSTEM_ERROR));
        assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Request ID can not be null");
        assertObjectEqual(responseDTO.getResultStatus().getError().getCode(), 230002);
    }
}