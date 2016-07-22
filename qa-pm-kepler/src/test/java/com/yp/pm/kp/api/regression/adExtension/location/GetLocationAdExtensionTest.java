package com.yp.pm.kp.api.regression.adExtension.location;

import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
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
public class GetLocationAdExtensionTest extends BaseAPITest {
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testGetAdExtensionById() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        LocationAdExtension locationAdExtension = createLocationAdExtension(account.getAccountId());

        String payload = "{\"adExtensionIds\" : [" + locationAdExtension.getAdExtensionId() + "]}";
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
    public void testGetDeletedAdExtension() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        LocationAdExtension locationAdExtension1 = createLocationAdExtension(account.getAccountId());
        deleteLocationAdExtension(locationAdExtension1);

        LocationAdExtension locationAdExtension = new LocationAdExtension();
        locationAdExtension.setAccountId(account.getAccountId());
        locationAdExtension.setAddressLine1("45 N Arroyo Pkwy");
        locationAdExtension.setCity("Pasadena");
        locationAdExtension.setState("CA");
        locationAdExtension.setZip("91103");
        locationAdExtension.setCompanyName("YP.com");
        locationAdExtension.setAdExtensionStatus(LocationAdExtensionStatusEnum.ACTIVE);
        LocationAdExtension locationAdExtension2 = createLocationAdExtension(locationAdExtension);

        String payload = "{\"adExtensionIds\" : [" + locationAdExtension1.getAdExtensionId() + ", " + locationAdExtension2.getAdExtensionId() + "]}";
        headers.put("accountId", account.getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.LOCATION_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/location/get", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 2);
        assertObjectEqual(responseDTO.getResults().size(), 2);
    }
}