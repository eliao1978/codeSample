package com.yp.pm.kp.api.regression.adExtension.logo;

import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.LogoAdExtensionDTO;
import com.yp.pm.kp.model.domain.Account;
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
public class AddLogoAdExtensionTest extends BaseAPITest {

    private Map<String, String> headers = getRequestHeader();


    @Test
    public void testGetLogoAdExtensionById() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        String payload = "{\"logoAdExtensions\" : [{" +
                "\"label\": \"test logo\", " +
                "\"imageContent\": \"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\"" +
                "}," +
                "{\"label\": \"test logo2\", " +
                "\"imageContent\": \"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\"" +
                "}," +
                "{\"label\": \"test logo3\", " +
                "\"imageContent\": \"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\"" +
                "}]}";
        headers.put("accountId", account.getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.LOGO_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/logo/create", headers, payload));
        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 3);
        int i = 0;
        for (ResultsDTO result : responseDTO.getResults()) {
            assertConditionTrue(result.getIndex() == i);
            i += 1;
            LogoAdExtensionDTO logoAdExtensionDTO = (LogoAdExtensionDTO) result.getObject();
            assertObjectNotNull(logoAdExtensionDTO.getId());
        }
    }
}