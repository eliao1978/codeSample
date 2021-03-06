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
public class DeleteLogoAdExtensionTest extends BaseAPITest {

    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testDeleteLogoAdExtension() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        LogoAdExtension logoAdExtension = createLogoAdExtension(account.getAccountId());

        String payload = "{\"adExtensionIds\" : [" + logoAdExtension.getAdExtensionId() + "]}";
        headers.put("accountId", account.getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.LOGO_AD_EXTENSION, getAPIResponse(MethodEnum.DELETE, "account/ext/logo/delete", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            LogoAdExtensionDTO logoAdExtensionDTO = (LogoAdExtensionDTO) result.getObject();
            assertObjectNotNull(logoAdExtensionDTO.getId());
        }

        assertObjectEqual(getLogoAdExtensionById(account.getAccountId(), logoAdExtension.getAdExtensionId()).getStatus().toString(), LogoAdExtensionStatusEnum.DELETED.toString());
    }

    @Test
    public void testInvalidAdExtensionId() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        String payload = "{\"adExtensionIds\" : [\"12345\"]}";
        headers.put("accountId", account.getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.LOGO_AD_EXTENSION, getAPIResponse(MethodEnum.DELETE, "account/ext/logo/delete", headers, payload));
        assertObjectEqual(responseDTO.getTotalCount(), 0);

        for (ResultsDTO result : responseDTO.getResults()) {
            LogoAdExtensionDTO logoAdExtensionDTO = (LogoAdExtensionDTO) result.getObject();
            assertObjectEqual(logoAdExtensionDTO.getId(), -1);
        }
    }
}