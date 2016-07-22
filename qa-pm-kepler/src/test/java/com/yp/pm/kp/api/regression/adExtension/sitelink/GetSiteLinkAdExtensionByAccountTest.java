package com.yp.pm.kp.api.regression.adExtension.sitelink;


import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
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

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GetSiteLinkAdExtensionByAccountTest extends BaseAPITest {
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testGetAdExtensionByAccountId() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        SiteLinkAdExtension siteLinkAdExtension = createSiteLinkAdExtension(account.getAccountId());

        String payload = "{}";
        headers.put("accountId", account.getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.SITE_LINK_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/sitelink/get", headers, payload));
        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            SiteLinkAdExtensionDTO siteLinkAdExtensionDTO = (SiteLinkAdExtensionDTO) result.getObject();
            assertObjectEqual(siteLinkAdExtensionDTO.getAccountId(), siteLinkAdExtension.getAccountId());
            assertObjectEqual(siteLinkAdExtensionDTO.getId(), siteLinkAdExtension.getAdExtensionId());
            assertObjectEqual(siteLinkAdExtensionDTO.getStatus(), siteLinkAdExtension.getAdExtensionStatus().toString());
            assertObjectNotNull(siteLinkAdExtensionDTO.getDestinationUrl());
            assertObjectNotNull(siteLinkAdExtensionDTO.getDisplayText());
        }
    }

    @Test
    public void testGetDeletedAdExtensionByAccountId() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        SiteLinkAdExtension siteLinkAdExtension1 = createSiteLinkAdExtension(account.getAccountId());
        deleteSiteLinkAdExtension(siteLinkAdExtension1);

        SiteLinkAdExtension siteLinkAdExtension = new SiteLinkAdExtension();
        siteLinkAdExtension.setAccountId(account.getAccountId());
        siteLinkAdExtension.setAdExtensionStatus(SiteLinkAdExtensionStatusEnum.ACTIVE);
        siteLinkAdExtension.setDisplayText("Contact Us Now");
        siteLinkAdExtension.setDestinationUrl("http://yp2_" + System.currentTimeMillis() + ".com");
        createSiteLinkAdExtension(siteLinkAdExtension);

        String payload = "{}";
        headers.put("accountId", account.getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.SITE_LINK_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/sitelink/get", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 2);
        assertObjectEqual(responseDTO.getResults().size(), 2);

    }

    @Test
    public void testInvalidAccountId() throws Exception {
        String command = "curl -X POST " +
                "-d '{}' " +
                "-H '" + contentType + "' " +
                "-H 'username:'" + userName + "'' " +
                "-H 'requestid:'" + requestId + "'' " +
                "-H 'accountId:'12345'' " +
                "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/account/ext/location/get";

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.SITE_LINK_AD_EXTENSION, getAPIResponse(command));
        assertObjectEqual(responseDTO.getResults().size(), 0);
        assertObjectEqual(responseDTO.getTotalCount(), 0L);
    }

    @Test
    public void testMissingAccountIdHeader() throws Exception {
        String command = "curl -X POST " +
                "-d '{}' " +
                "-H '" + contentType + "' " +
                "-H 'username:'" + userName + "'' " +
                "-H 'requestid:'" + requestId + "'' " +
                "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/account/ext/sitelink/get";

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.SITE_LINK_AD_EXTENSION, getAPIResponse(command));
        assertObjectEqual(responseDTO.getResultStatus().getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Account id can not be null");
    }

    @Test
    public void testMissingAccountId() throws Exception {
        String command = "curl -X POST " +
                "-d '{}' " +
                "-H '" + contentType + "' " +
                "-H 'username:'" + userName + "'' " +
                "-H 'requestid:'" + requestId + "'' " +
                "-H 'accountId:''' " +
                "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/account/ext/sitelink/get";

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.SITE_LINK_AD_EXTENSION, getAPIResponse(command));
        assertObjectEqual(responseDTO.getResultStatus().getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResultStatus().getError().getMessage(), "Account id can not be null");
    }
}