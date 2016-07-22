package com.yp.pm.kp.api.regression.adExtension.location;

import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.LocationAdExtensionDTO;
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
public class UpdateLocationAdExtensionTest extends BaseAPITest {
    private Map<String, String> headers = getRequestHeader();

    private static Map<String, Object> testData;
    private static Map<String, Object> testData2;

    @Test
    public void testUpdatePhoneAdExtension() throws Exception {
        String payload = "{\"locationAdExtensions\" : [{" +
                "\"adExtensionId\" : " + ((LocationAdExtension) getTestData().get("adExtension")).getAdExtensionId() + ", " +
                "\"label\" : \"testLabel\", " +
                "\"addressLine1\" : \"test1\", " +
                "\"addressLine1\" : \"test2\", " +
                "\"city\" : \"Irvine\", " +
                "\"state\" : \"CA\", " +
                "\"zip\" : \"92630\", " +
                "\"companyName\" : \"ATT\", " +
                "\"adExtensionStatus\" : \"ACTIVE\"}]}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.LOCATION_AD_EXTENSION, getAPIResponse(MethodEnum.PUT, "account/ext/location/update", headers, payload));
        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);
        for (ResultsDTO result : responseDTO.getResults()) {
            LocationAdExtensionDTO locationAdExtensionDTO = (LocationAdExtensionDTO) result.getObject();
            LocationAdExtension updatedLocationAdExtension = getLocationAdExtension(((Account) getTestData().get("account")).getAccountId(), ((LocationAdExtension) getTestData().get("adExtension")).getAdExtensionId());
            assertObjectEqual(locationAdExtensionDTO.getId(), updatedLocationAdExtension.getAdExtensionId());
        }
    }

    @Test
    public void testIgnoreUpdatePhoneAdExtension() throws Exception {
        String payload = "{\"locationAdExtensions\" : [{" +
                "\"adExtensionId\" : -1, " +
                "\"label\" : \"testLabel\", " +
                "\"addressLine1\" : \"test1\", " +
                "\"addressLine1\" : \"test2\", " +
                "\"city\" : \"Chatsworth\", " +
                "\"state\" : \"CA\", " +
                "\"zip\" : \"91311\", " +
                "\"companyName\" : \"ATT\", " +
                "\"adExtensionStatus\" : \"ACTIVE\"}]}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.LOCATION_AD_EXTENSION, getAPIResponse(MethodEnum.PUT, "account/ext/location/update", headers, payload));

        assertResponseError(responseDTO);

        for (ResultsDTO result : responseDTO.getResults()) {
            LocationAdExtensionDTO locationAdExtensionDTO = (LocationAdExtensionDTO) result.getObject();
            LocationAdExtension updatedLocationAdExtension = getLocationAdExtension(((Account) getTestData2().get("account")).getAccountId(), ((LocationAdExtension) getTestData2().get("adExtension")).getAdExtensionId());
            assertObjectEqual(locationAdExtensionDTO.getId(), -1);
            assertConditionTrue(updatedLocationAdExtension.getCity().contains("Glendale"));

        }
    }

    @Test
    public void testInvalidAdExtensionId() throws Exception {
        String payload = "{\"locationAdExtensions\" : [{" +
                "\"adExtensionId\" : \"12345\", " +
                "\"label\" : \"testLabel\", " +
                "\"addressLine1\" : \"test1\", " +
                "\"addressLine1\" : \"test2\", " +
                "\"city\" : \"Irvine\", " +
                "\"state\" : \"CA\", " +
                "\"zip\" : \"92630\", " +
                "\"companyName\" : \"ATT\", " +
                "\"adExtensionStatus\" : \"ACTIVE\"}]}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.LOCATION_AD_EXTENSION, getAPIResponse(MethodEnum.PUT, "account/ext/location/update", headers, payload));

        assertObjectEqual(responseDTO.getTotalCount(), 0);

        for (ResultsDTO result : responseDTO.getResults()) {
            LocationAdExtensionDTO locationAdExtensionDTO = (LocationAdExtensionDTO) result.getObject();
            assertObjectEqual(locationAdExtensionDTO.getId(), -1);
        }
    }

    @Test
    public void testMissingAdExtensionId() throws Exception {
        String payload = "{\"locationAdExtensions\" : [{" +
                "\"adExtensionId\" : \"\", " +
                "\"label\" : \"testLabel\", " +
                "\"addressLine1\" : \"test1\", " +
                "\"addressLine1\" : \"test2\", " +
                "\"city\" : \"Irvine\", " +
                "\"state\" : \"CA\", " +
                "\"zip\" : \"92630\", " +
                "\"companyName\" : \"ATT\", " +
                "\"adExtensionStatus\" : \"ACTIVE\"}]}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.LOCATION_AD_EXTENSION, getAPIResponse(MethodEnum.PUT, "account/ext/location/update", headers, payload));

        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Ad extension id can not be null");
    }

    private Map<String, Object> getTestData2() throws Exception {
        if (testData2 == null) {
            testData2 = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            LocationAdExtension locationAdExtension = createLocationAdExtension(account.getAccountId());

            testData2.put("account", account);
            testData2.put("adExtension", locationAdExtension);
        }

        return testData2;
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