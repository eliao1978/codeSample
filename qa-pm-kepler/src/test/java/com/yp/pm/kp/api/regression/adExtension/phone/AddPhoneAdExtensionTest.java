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
import com.yp.util.DBUtil;
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
public class AddPhoneAdExtensionTest extends BaseAPITest {

    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testAddPhoneAdExtension() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");

        String payload = "{\"phoneAdExtensions\" : [{" +
                "\"isCallableOnly\" : true, " +
                "\"phoneNumber\" : \"8181231234\", " +
                "\"countryCode\" : \"1\"}]}";
        headers.put("accountId", account.getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.PHONE_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/phone/create", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            PhoneAdExtensionDTO phoneAdExtensionDTO = (PhoneAdExtensionDTO) result.getObject();
            assertObjectEqual(phoneAdExtensionDTO.getId(), getPhoneAdExtension(account.getAccountId(), phoneAdExtensionDTO.getId()).getAdExtensionId());
        }
    }

    @Test
    public void testInvalidCountryCode() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");

        String payload = "{\"phoneAdExtensions\" : [{" +
                "\"isCallableOnly\" : true, " +
                "\"phoneNumber\" : \"8181231234\", " +
                "\"countryCode\" : \"11111111111\"}]}";
        headers.put("accountId", account.getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.PHONE_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/phone/create", headers, payload));

        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Only US country code is allowed (1)");
    }

    @Test
    public void testAddPhoneAdExtensionWithCustomLimit() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        try {
            DBUtil.update("insert into pm_custom_limit_validation values('phone_ad_extension', 5, ".concat(account.getAccountId().toString()).concat(", 'account', '").concat(userName) + "',SYSDATE,'" + userName + "',SYSDATE)");
            String[] numbers = {
                    "3232220666", "7145551111", "7472221111", "2132223456", "9098881111"
            };
            for (String y : numbers) {
                PhoneAdExtension phoneAdExtension = new PhoneAdExtension();
                phoneAdExtension.setAccountId(account.getAccountId());
                phoneAdExtension.setPhoneNumber(y);
                phoneAdExtension.setAdExtensionStatus(PhoneAdExtensionStatusEnum.ACTIVE);
                phoneAdExtension.setIsCallableOnly(true);
                createPhoneAdExtension(phoneAdExtension);
            }

            String payload = "{\"phoneAdExtensions\" : [{" +
                    "\"isCallableOnly\" : true, " +
                    "\"phoneNumber\" : \"8181231234\", " +
                    "\"countryCode\" : \"1\"}]}";
            headers.put("accountId", account.getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.PHONE_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/phone/create", headers, payload));
            assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Exceeds maximum number of phone ad extensions in account");

        } finally {
            DBUtil.update("delete from pm_custom_limit_validation WHERE PARENT_ID = ".concat(account.getAccountId().toString()));
        }
    }
}