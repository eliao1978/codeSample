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
import com.yp.util.DBUtil;
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
public class AddLocationAdExtensionTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testAddLocationAdExtension() throws Exception {
        String payload = "{\"locationAdExtensions\" : [{" +
                "\"addressLine1\" : \"611 N Brand Blvd\", " +
                "\"addressLine2\" : \"5th Floor\", " +
                "\"city\" : \"Glendale\", " +
                "\"state\" : \"CA\", " +
                "\"zip\" : \"91203\", " +
                "\"latitude\" : 34.15532, " +
                "\"longitude\" : -118.25622, " +
                "\"companyName\" : \"YP.com\"}," +
                "{\"addressLine1\" : \"615 N Brand Blvd\", " +
                "\"addressLine2\" : \"15th Floor\", " +
                "\"city\" : \"Glendale\", " +
                "\"state\" : \"CA\", " +
                "\"zip\" : \"91203\", " +
                "\"latitude\" : 34.15532, " +
                "\"longitude\" : -118.25622, " +
                "\"companyName\" : \"YP.com\"}," +
                "{\"addressLine1\" : \"617 N Brand Blvd\", " +
                "\"addressLine2\" : \"51th Floor\", " +
                "\"city\" : \"Glendale\", " +
                "\"state\" : \"CA\", " +
                "\"zip\" : \"91203\", " +
                "\"latitude\" : 34.15532, " +
                "\"longitude\" : -118.25622, " +
                "\"companyName\" : \"YP.com\"}," +
                "{\"addressLine1\" : \"613 N Brand Blvd\", " +
                "\"addressLine2\" : \"13th Floor\", " +
                "\"city\" : \"Glendale\", " +
                "\"state\" : \"CA\", " +
                "\"zip\" : \"91203\", " +
                "\"latitude\" : 34.15532, " +
                "\"longitude\" : -118.25622, " +
                "\"companyName\" : \"YP.com\"}]}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.LOCATION_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/location/create", headers, payload));
        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 4);
        int i = 0;
        for (ResultsDTO result : responseDTO.getResults()) {
            assertConditionTrue(result.getIndex() == i);
            i += 1;
            LocationAdExtensionDTO locationAdExtensionDTO = (LocationAdExtensionDTO) result.getObject();
            assertObjectEqual(locationAdExtensionDTO.getId(), getLocationAdExtension(((Account) getTestData().get("account")).getAccountId(), locationAdExtensionDTO.getId()).getAdExtensionId());
        }
    }

    @Test
    public void testAddDuplicateLocationAdExtension() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        String payload = "{\"locationAdExtensions\" : [{" +
                "\"addressLine1\" : \"611 N Brand Blvd\", " +
                "\"addressLine2\" : \"5th Floor\", " +
                "\"city\" : \"Glendale\", " +
                "\"state\" : \"CA\", " +
                "\"zip\" : \"91203\", " +
                "\"latitude\" : 34.15532, " +
                "\"longitude\" : -118.25622, " +
                "\"companyName\" : \"YP.com\"}]}";
        headers.put("accountId", account.getAccountId().toString());

        getResponseDTO(KeplerDomainTypeEnum.LOCATION_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/location/create", headers, payload));
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.LOCATION_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/location/create", headers, payload));


        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Duplicate location ad extension");
    }

    @Test
    public void testMissingCompanyName() throws Exception {
        String payload = "{\"locationAdExtensions\" : [{" +
                "\"addressLine1\" : \"611 N Brand Blvd\", " +
                "\"city\" : \"Glendale\", " +
                "\"state\" : \"CA\", " +
                "\"zip\" : \"91203\", " +
                "\"latitude\" : 34.15532, " +
                "\"longitude\" : -118.25622}]}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.LOCATION_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/location/create", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            LocationAdExtensionDTO locationAdExtensionDTO = (LocationAdExtensionDTO) result.getObject();
            assertObjectEqual(locationAdExtensionDTO.getId(), getLocationAdExtension(((Account) getTestData().get("account")).getAccountId(), locationAdExtensionDTO.getId()).getAdExtensionId());
        }
    }

    @Test
    public void testInvalidCompanyName() throws Exception {
        String payload = "{\"locationAdExtensions\" : [{" +
                "\"addressLine1\" : \"611 N Brand Blvd\", " +
                "\"city\" : \"Glendale\", " +
                "\"state\" : \"CA\", " +
                "\"zip\" : \"91203\", " +
                "\"latitude\" : 34.15532, " +
                "\"longitude\" : -118.25622, " +
                "\"companyName\" : \"YP.commmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm\"}]}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        getResponseDTO(KeplerDomainTypeEnum.LOCATION_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/location/create", headers, payload));

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.LOCATION_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/location/create", headers, payload));
        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Location ad extension company name is too long");
    }

    @Test
    public void testMissingAddress2() throws Exception {
        String payload = "{\"locationAdExtensions\" : [{" +
                "\"addressLine1\" : \"611 N Brand Blvd\", " +
                "\"city\" : \"Glendale\", " +
                "\"state\" : \"CA\", " +
                "\"zip\" : \"91203\", " +
                "\"latitude\" : 34.15532, " +
                "\"longitude\" : -118.25622, " +
                "\"companyName\" : \"YP.com\"}]}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.LOCATION_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/location/create", headers, payload));
        assertResponseError(responseDTO);

        for (ResultsDTO result : responseDTO.getResults()) {
            LocationAdExtensionDTO locationAdExtensionDTO = (LocationAdExtensionDTO) result.getObject();
            assertObjectEqual(locationAdExtensionDTO.getId(), getLocationAdExtension(((Account) getTestData().get("account")).getAccountId(), locationAdExtensionDTO.getId()).getAdExtensionId());
        }
    }

    @Test
    public void testMissingCity() throws Exception {
        String payload = "{\"locationAdExtensions\" : [{" +
                "\"addressLine1\" : \"611 N Brand Blvd\", " +
                "\"addressLine2\" : \"5th Floor\", " +
                "\"state\" : \"CA\", " +
                "\"zip\" : \"91203\", " +
                "\"latitude\" : 34.15532, " +
                "\"longitude\" : -118.25622, " +
                "\"companyName\" : \"YP.com\"}]}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.LOCATION_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/location/create", headers, payload));
        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Location ad extension city can not be null");
    }

    @Test
    public void testMissingState() throws Exception {
        String payload = "{\"locationAdExtensions\" : [{" +
                "\"addressLine1\" : \"611 N Brand Blvd\", " +
                "\"addressLine2\" : \"5th Floor\", " +
                "\"city\" : \"Glendale\", " +
                "\"zip\" : \"91203\", " +
                "\"latitude\" : 34.15532, " +
                "\"longitude\" : -118.25622, " +
                "\"companyName\" : \"YP.com\"}]}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.LOCATION_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/location/create", headers, payload));
        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Location ad extension state can not be null");
    }

    @Test
    public void testMissingZip() throws Exception {
        String payload = "{\"locationAdExtensions\" : [{" +
                "\"addressLine1\" : \"611 N Brand Blvd\", " +
                "\"addressLine2\" : \"5th Floor\", " +
                "\"city\" : \"Glendale\", " +
                "\"state\" : \"CA\", " +
                "\"latitude\" : 34.15532, " +
                "\"longitude\" : -118.25622, " +
                "\"companyName\" : \"YP.com\"}]}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.LOCATION_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/location/create", headers, payload));
        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Location ad extension zip can not be null");
    }

    @Test
    public void testAddLocationAdExtensionCustomLimit() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        try {
            DBUtil.update("insert into pm_custom_limit_validation values('location_ad_extension', 1, ".concat(account.getAccountId().toString()).concat(", 'account', '").concat(userName) + "',SYSDATE,'" + userName + "',SYSDATE)");

            LocationAdExtension locationAdExtension = new LocationAdExtension();
            locationAdExtension.setAccountId(account.getAccountId());
            locationAdExtension.setAddressLine1("2904 N Broadway");
            locationAdExtension.setCity("Los Angeles");
            locationAdExtension.setState("CA");
            locationAdExtension.setZip("90031");
            locationAdExtension.setCompanyName("QA Los Angeles");
            locationAdExtension.setAdExtensionStatus(LocationAdExtensionStatusEnum.ACTIVE);
            createLocationAdExtension(locationAdExtension);

            String command = "curl -X POST " +
                    "-d '{\"locationAdExtensions\" : [{" +
                    "\"addressLine1\" : \"611 N Brand Blvd\", " +
                    "\"addressLine2\" : \"5th Floor\", " +
                    "\"city\" : \"Glendale\", " +
                    "\"state\" : \"CA\", " +
                    "\"zip\" : \"91203\", " +
                    "\"latitude\" : 34.15532, " +
                    "\"longitude\" : -118.25622, " +
                    "\"companyName\" : \"YP.com\"}]}' " +
                    "-H '" + contentType + "' " +
                    "-H 'username:'" + userName + "'' " +
                    "-H 'requestid:'" + requestId + "'' " +
                    "-H 'accountId:'" + account.getAccountId() + "'' " +
                    "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/account/ext/location/create";

            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.LOCATION_AD_EXTENSION, getAPIResponse(command));

            assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Exceeds maximum number of location ad extensions in account");

        } finally {
            DBUtil.update("delete from pm_custom_limit_validation WHERE PARENT_ID = ".concat(account.getAccountId().toString()));
        }
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            testData.put("account", account);
        }

        return testData;
    }
}