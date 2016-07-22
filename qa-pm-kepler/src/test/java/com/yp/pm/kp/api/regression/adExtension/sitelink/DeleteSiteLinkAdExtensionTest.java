package com.yp.pm.kp.api.regression.adExtension.sitelink;


import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.SiteLinkAdExtensionDTO;
import com.yp.pm.kp.enums.model.SiteLinkAdExtensionStatusEnum;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.SiteLinkAdExtension;
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
public class DeleteSiteLinkAdExtensionTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testDeleteSiteLinkAdExtension() throws Exception {
        String payload = "{\"adExtensionIds\" : [" + ((SiteLinkAdExtension) getTestData().get("adExtension")).getAdExtensionId() + "]}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.SITE_LINK_AD_EXTENSION, getAPIResponse(MethodEnum.DELETE, "account/ext/sitelink/delete", headers, payload));


        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        SiteLinkAdExtension deletedSiteLinkAdExtension = getSiteLinkAdExtension(((Account) getTestData().get("account")).getAccountId(), ((SiteLinkAdExtension) getTestData().get("adExtension")).getAdExtensionId());
        assertObjectEqual(deletedSiteLinkAdExtension.getAdExtensionStatus().toString(), SiteLinkAdExtensionStatusEnum.DELETED.toString());
    }

    @Test
    public void testInvalidAdExtensionId() throws Exception {
        String payload = "{\"adExtensionIds\" : [\"12345\"]}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.SITE_LINK_AD_EXTENSION, getAPIResponse(MethodEnum.DELETE, "account/ext/sitelink/delete", headers, payload));

        assertObjectEqual(responseDTO.getTotalCount(), 0);

        for (ResultsDTO result : responseDTO.getResults()) {
            SiteLinkAdExtensionDTO siteLinkAdExtensionDTO = (SiteLinkAdExtensionDTO) result.getObject();
            assertObjectEqual(siteLinkAdExtensionDTO.getId(), -1);
        }
    }

    private Map<String, Object> getTestData() throws Exception {
        if (testData == null) {
            testData = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            SiteLinkAdExtension siteLinkAdExtension = createSiteLinkAdExtension(account.getAccountId());

            testData.put("account", account);
            testData.put("adExtension", siteLinkAdExtension);
        }

        return testData;
    }
}