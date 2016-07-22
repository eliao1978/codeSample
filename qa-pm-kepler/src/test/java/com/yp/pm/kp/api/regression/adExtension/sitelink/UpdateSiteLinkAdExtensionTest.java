package com.yp.pm.kp.api.regression.adExtension.sitelink;


import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.SiteLinkAdExtensionDTO;
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
public class UpdateSiteLinkAdExtensionTest extends BaseAPITest {

    private static Map<String, Object> testData;
    private static Map<String, Object> testData2;
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testUpdateSiteLinkAdExtension() throws Exception {
        String payload = "{\"siteLinkAdExtensions\" : [{" +
                "\"adExtensionId\" : " + ((SiteLinkAdExtension) getTestData().get("adExtension")).getAdExtensionId() + ", " +
                "\"displayText\" : \"Overview\", " +
                "\"adExtensionStatus\" : \"ACTIVE\"}]}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.SITE_LINK_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/sitelink/update", headers, payload));
        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            SiteLinkAdExtensionDTO siteLinkAdExtensionDTO = (SiteLinkAdExtensionDTO) result.getObject();
            SiteLinkAdExtension updatedSiteLinkAdExtension = getSiteLinkAdExtension(((Account) getTestData().get("account")).getAccountId(), ((SiteLinkAdExtension) getTestData().get("adExtension")).getAdExtensionId());
            assertObjectEqual(siteLinkAdExtensionDTO.getId(), updatedSiteLinkAdExtension.getAdExtensionId());
        }
    }

    @Test
    public void testIgnoreUpdateSiteLinkAdExtension() throws Exception {
        String payload = "{\"siteLinkAdExtensions\" : [{" +
                "\"adExtensionId\" : -1, " +
                "\"displayText\" : \"ATEAM\", " +
                "\"adExtensionStatus\" : \"ACTIVE\"}]}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.SITE_LINK_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/sitelink/update", headers, payload));

        assertResponseError(responseDTO);

        for (ResultsDTO result : responseDTO.getResults()) {
            SiteLinkAdExtensionDTO siteLinkAdExtensionDTO = (SiteLinkAdExtensionDTO) result.getObject();
            SiteLinkAdExtension updatedSiteLinkAdExtension = getSiteLinkAdExtension(((Account) getTestData2().get("account")).getAccountId(), ((SiteLinkAdExtension) getTestData2().get("adExtension")).getAdExtensionId());
            assertObjectEqual(siteLinkAdExtensionDTO.getId(), -1);
            assertConditionTrue(updatedSiteLinkAdExtension.getDisplayText().contains("Contact Us"));
        }
    }

    @Test
    public void testInvalidSiteLinkAdExtensionId() throws Exception {
        String payload = "{\"siteLinkAdExtensions\" : [{" +
                "\"adExtensionId\" : \"12345\", " +
                "\"displayText\" : \"Overview\", " +
                "\"adExtensionStatus\" : \"ACTIVE\"}]}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.SITE_LINK_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/sitelink/update", headers, payload));
        assertObjectEqual(responseDTO.getTotalCount(), 0);

        for (ResultsDTO result : responseDTO.getResults()) {
            SiteLinkAdExtensionDTO siteLinkAdExtensionDTO = (SiteLinkAdExtensionDTO) result.getObject();
            assertObjectEqual(siteLinkAdExtensionDTO.getId(), -1);
        }
    }

    @Test
    public void testMissingSiteLinkAdExtensionId() throws Exception {
        String payload = "{\"siteLinkAdExtensions\" : [{" +
                "\"adExtensionId\" : \"\", " +
                "\"displayText\" : \"Overview\", " +
                "\"adExtensionStatus\" : \"ACTIVE\"}]}";
        headers.put("accountId", ((Account) getTestData().get("account")).getAccountId().toString());

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.SITE_LINK_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/sitelink/update", headers, payload));
        assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
        assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Ad extension id can not be null");
    }

    private Map<String, Object> getTestData2() throws Exception {
        if (testData2 == null) {
            testData2 = new HashMap<>();
            Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
            SiteLinkAdExtension siteLinkAdExtension = createSiteLinkAdExtension(account.getAccountId());

            testData2.put("account", account);
            testData2.put("adExtension", siteLinkAdExtension);
        }

        return testData2;
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