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

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeleteLocationAdExtensionTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private Map<String, String> headers = getRequestHeader();


    @Test
    public void testDeleteLocationAdExtension() throws Exception {
        String payload = "{\"adExtensionIds\" : [" + ((LocationAdExtension) getTestData().get("adExtension")).getAdExtensionId() + "]}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.LOCATION_AD_EXTENSION, getAPIResponse(MethodEnum.DELETE, "account/ext/location/delete", headers, payload));
        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);
        LocationAdExtension deletedLocationAdExtension = getLocationAdExtension(((Account) getTestData().get("account")).getAccountId(), ((LocationAdExtension) getTestData().get("adExtension")).getAdExtensionId());
        assertObjectEqual(deletedLocationAdExtension.getAdExtensionStatus().toString(), LocationAdExtensionStatusEnum.DELETED.toString());
    }

    @Test
    public void testInvalidAdExtensionId() throws Exception {
        String payload = "{\"adExtensionIds\" : [\"12345\"]}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.LOCATION_AD_EXTENSION, getAPIResponse(MethodEnum.DELETE, "account/ext/location/delete", headers, payload));

        assertObjectEqual(responseDTO.getTotalCount(), 0);

        for (ResultsDTO result : responseDTO.getResults()) {
            LocationAdExtensionDTO locationAdExtensionDTO = (LocationAdExtensionDTO) result.getObject();
            assertObjectEqual(locationAdExtensionDTO.getId(), -1);
        }
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            LocationAdExtension locationAdExtension = createLocationAdExtension(account.getAccountId());

            testData.put("account", account);
            testData.put("adExtension", locationAdExtension);
        }

        return testData;
    }
}