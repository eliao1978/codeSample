package com.yp.pm.kp.api.regression.adExtension.location;


import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.LocationAdExtensionDTO;
import com.yp.pm.kp.enums.model.LocationAdExtensionStatusEnum;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.LocationAdExtension;
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
public class GetLocationAdExtensionByAccountTest extends BaseAPITest {
    private Map<String, String> headers = getRequestHeader();


    @Test
    public void testGetAdExtensionByAccountId() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        LocationAdExtension locationAdExtension = createLocationAdExtension(account.getAccountId());

        String payload = "{}";
        headers.put("accountId", account.getAccountId().toString());

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.LOCATION_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/location/get", headers, payload));

        assertResponseError(responseDTO);

        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            LocationAdExtensionDTO locationAdExtensionDTO = (LocationAdExtensionDTO) result.getObject();
            assertObjectEqual(locationAdExtensionDTO.getAccountId(), locationAdExtension.getAccountId());
            assertObjectEqual(locationAdExtensionDTO.getId(), locationAdExtension.getAdExtensionId());
            assertObjectEqual(locationAdExtensionDTO.getStatus(), locationAdExtension.getAdExtensionStatus().toString());
            assertObjectNotNull(locationAdExtensionDTO.getLabel());
            assertObjectNotNull(locationAdExtensionDTO.getAddressLine1());
            assertObjectNotNull(locationAdExtensionDTO.getAddressLine2());
            assertObjectNotNull(locationAdExtensionDTO.getCity());
            assertObjectNotNull(locationAdExtensionDTO.getState());
            assertObjectNotNull(locationAdExtensionDTO.getZip());
            assertObjectNotNull(locationAdExtensionDTO.getLatitude());
            assertObjectNotNull(locationAdExtensionDTO.getLongitude());
            assertObjectNotNull(locationAdExtensionDTO.getCompanyName());
        }
    }

    @Test
    public void testGetDeletedAdExtensionByAccountId() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        LocationAdExtension locationAdExtension = createLocationAdExtension(account.getAccountId());
        deleteLocationAdExtension(locationAdExtension);

        LocationAdExtension newAdExtension = new LocationAdExtension();
        newAdExtension.setAccountId(account.getAccountId());
        newAdExtension.setAddressLine1("45 N Arroyo Pkwy");
        newAdExtension.setCity("Pasadena");
        newAdExtension.setState("CA");
        newAdExtension.setZip("91103");
        newAdExtension.setCompanyName("YP.com");
        newAdExtension.setAdExtensionStatus(LocationAdExtensionStatusEnum.ACTIVE);
        createLocationAdExtension(newAdExtension);
        String payload = "{}";
        headers.put("accountId", account.getAccountId().toString());

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.LOCATION_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/location/get", headers, payload));
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

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.LOCATION_AD_EXTENSION, getAPIResponse(command));
        assertObjectEqual(responseDTO.getResults().size(), 0);
        assertObjectEqual(responseDTO.getTotalCount(), 0);
    }

    @Test
    public void testMissingAccountIdHeader() throws Exception {
        String payload = "{}";
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.LOCATION_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/location/get", headers, payload));
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
                "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/account/ext/location/get";

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.LOCATION_AD_EXTENSION, getAPIResponse(command));
        assertObjectEqual(responseDTO.getResultStatus().getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Account id can not be null");
    }
}