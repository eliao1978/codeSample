package com.yp.pm.kp.api.regression.adExtension.phone;


import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.PhoneAdExtensionDTO;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.PhoneAdExtension;
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
public class UpdatePhoneAdExtensionTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private static Map<String, Object> testData2;
    private Map<String, String> headers = getRequestHeader();


    @Test
    public void testUpdatePhoneAdExtension() throws Exception {
        String payload = "{\"phoneAdExtensions\" : [{" +
                "\"adExtensionId\" : " + ((PhoneAdExtension) getTestData().get("adExtension")).getAdExtensionId() + ", " +
                "\"isCallableOnly\" : true, " +
                "\"adExtensionStatus\" : \"ACTIVE\"}]}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.PHONE_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/phone/update", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            PhoneAdExtensionDTO phoneAdExtensionDTO = (PhoneAdExtensionDTO) result.getObject();
            PhoneAdExtension updatedPhoneAdExtension = getPhoneAdExtension(((Account) getTestData().get("account")).getAccountId(), ((PhoneAdExtension) getTestData().get("adExtension")).getAdExtensionId());
            assertObjectEqual(phoneAdExtensionDTO.getId(), updatedPhoneAdExtension.getAdExtensionId());
        }
    }

    @Test
    public void testIgnoreUpdatePhoneAdExtension() throws Exception {
        String payload = "{\"phoneAdExtensions\" : [{" +
                "\"adExtensionId\" : -1, " +
                "\"isCallableOnly\" : false, " +
                "\"adExtensionStatus\" : \"ACTIVE\"}]}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.PHONE_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/phone/update", headers, payload));
        assertResponseError(responseDTO);

        for (ResultsDTO result : responseDTO.getResults()) {
            PhoneAdExtensionDTO phoneAdExtensionDTO = (PhoneAdExtensionDTO) result.getObject();
            PhoneAdExtension updatedPhoneAdExtension = getPhoneAdExtension(((Account) getTestData2().get("account")).getAccountId(), ((PhoneAdExtension) getTestData2().get("adExtension")).getAdExtensionId());
            assertObjectEqual(phoneAdExtensionDTO.getId(), -1);
            assertConditionTrue(updatedPhoneAdExtension.getIsCallableOnly());

        }
    }

    @Test
    public void testInvalidAdExtensionId() throws Exception {
        String payload = "{\"phoneAdExtensions\" : [{" +
                "\"adExtensionId\" : \"12345\", " +
                "\"isCallableOnly\" : true, " +
                "\"adExtensionStatus\" : \"ACTIVE\"}]}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.PHONE_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/phone/update", headers, payload));
        assertObjectEqual(responseDTO.getTotalCount(), 0);

        for (ResultsDTO result : responseDTO.getResults()) {
            PhoneAdExtensionDTO phoneAdExtensionDTO = (PhoneAdExtensionDTO) result.getObject();
            assertObjectEqual(phoneAdExtensionDTO.getId(), -1);
        }
    }

    @Test
    public void testMissingAdExtensionId() throws Exception {
        String payload = "{\"phoneAdExtensions\" : [{" +
                "\"adExtensionId\" : \"\", " +
                "\"isCallableOnly\" : true, " +
                "\"adExtensionStatus\" : \"ACTIVE\"}]}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.PHONE_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/phone/update", headers, payload));
        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Ad extension id can not be null");
    }

    private Map<String, Object> getTestData2() throws Exception {
        if (testData2 == null) {
            testData2 = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            PhoneAdExtension phoneAdExtension = createPhoneAdExtension(account.getAccountId());

            testData2.put("account", account);
            testData2.put("adExtension", phoneAdExtension);
        }

        return testData2;
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            PhoneAdExtension phoneAdExtension = createPhoneAdExtension(account.getAccountId());

            testData.put("account", account);
            testData.put("adExtension", phoneAdExtension);
        }

        return testData;
    }
}