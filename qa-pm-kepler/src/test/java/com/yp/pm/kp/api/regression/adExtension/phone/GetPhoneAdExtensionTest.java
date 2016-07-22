package com.yp.pm.kp.api.regression.adExtension.phone;


import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
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
public class GetPhoneAdExtensionTest extends BaseAPITest {
    private Map<String, String> headers = getRequestHeader();


    @Test
    public void testGetPhoneAdExtensionById() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        PhoneAdExtension phoneAdExtension = createPhoneAdExtension(account.getAccountId());


        String payload = "{\"adExtensionIds\" : [" + phoneAdExtension.getAdExtensionId() + "]}";
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
    public void testGetDeletedPhoneAdExtensionById() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        PhoneAdExtension phoneAdExtension1 = createPhoneAdExtension(account.getAccountId());
        deletePhoneAdExtension(phoneAdExtension1);

        PhoneAdExtension phoneAdExtension = new PhoneAdExtension();
        phoneAdExtension.setAccountId(account.getAccountId());
        phoneAdExtension.setCountryCode("1");
        phoneAdExtension.setPhoneNumber("8182758447");
        phoneAdExtension.setIsCallableOnly(true);
        phoneAdExtension.setAdExtensionStatus(PhoneAdExtensionStatusEnum.ACTIVE);
        PhoneAdExtension phoneAdExtension2 = createPhoneAdExtension(phoneAdExtension);

        String payload = "{\"adExtensionIds\" : [" + phoneAdExtension1.getAdExtensionId() + ", " + phoneAdExtension2.getAdExtensionId() + "]}";

        headers.put("accountId", account.getAccountId().toString());

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.PHONE_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/phone/get", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 2);
        assertObjectEqual(responseDTO.getResults().size(), 2);

    }
}