package com.yp.pm.kp.api.regression.adExtension.logo;

import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.LogoAdExtensionDTO;
import com.yp.pm.kp.enums.model.LogoAdExtensionStatusEnum;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.LogoAdExtension;
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
public class GetLogoAdExtensionTest extends BaseAPITest {
    private Map<String, String> headers = getRequestHeader();


    @Test
    public void testGetLogoAdExtensionById() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        LogoAdExtension logoAdExtension = createLogoAdExtension(account.getAccountId());

        String payload = "{\"adExtensionIds\" : [" + logoAdExtension.getAdExtensionId() + "]}";
        headers.put("accountId", account.getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.LOGO_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/logo/get", headers, payload));


        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            LogoAdExtensionDTO logoAdExtensionDTO = (LogoAdExtensionDTO) result.getObject();
            assertObjectEqual(logoAdExtensionDTO.getAccountId(), logoAdExtension.getAccountId());
            assertObjectEqual(logoAdExtensionDTO.getId(), logoAdExtension.getAdExtensionId());
            assertObjectEqual(logoAdExtensionDTO.getStatus(), logoAdExtension.getStatus().toString());
            assertObjectNotNull(logoAdExtensionDTO.getLabel());
            assertObjectNotNull(logoAdExtensionDTO.getFullPath());
        }
    }

    @Test
    public void testGetDeletedLogoAdExtensionById() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        LogoAdExtension logoAdExtension1 = createLogoAdExtension(account.getAccountId());
        deleteLogoAdExtension(logoAdExtension1);

        LogoAdExtension logoAdExtension = new LogoAdExtension();
        logoAdExtension.setAccountId(account.getAccountId());
        logoAdExtension.setPath("d6ff13351e2d03594942a88e093fd91bf1aaaa77");
        logoAdExtension.setLabel("Test Logo2");
        logoAdExtension.setStatus(LogoAdExtensionStatusEnum.ACTIVE);
        LogoAdExtension logoAdExtension2 = createLogoAdExtension(logoAdExtension);


        String payload = "{\"adExtensionIds\" : [" + logoAdExtension1.getAdExtensionId() + ", " + logoAdExtension2.getAdExtensionId() + "]}";
        headers.put("accountId", account.getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.LOGO_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/logo/get", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 2);
        assertObjectEqual(responseDTO.getResults().size(), 2);


    }
}