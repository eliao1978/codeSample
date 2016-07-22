package com.yp.pm.kp.api.regression.adExtension.sitelink;

import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.enums.error.ErrorTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.SiteLinkAdExtensionDTO;
import com.yp.pm.kp.model.domain.Account;
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
public class AddSiteLinkAdExtensionTest extends BaseAPITest {
    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testAddSiteLinkAdExtension() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        String payload = "{\"siteLinkAdExtensions\" : [{" +
                "\"destinationUrl\" : \"" + "http://yp_1" + System.currentTimeMillis() + ".com" + "\", " +
                "\"displayText\" : \"Contact Us1\"}," +
                "{\"destinationUrl\" : \"" + "http://yp_2" + System.currentTimeMillis() + ".com" + "\", " +
                "\"displayText\" : \"Contact Us2\"}," +
                "{\"destinationUrl\" : \"" + "http://yp_3" + System.currentTimeMillis() + ".com" + "\", " +
                "\"displayText\" : \"Contact Us3\"}," +
                "{\"destinationUrl\" : \"" + "http://yp_4" + System.currentTimeMillis() + ".com" + "\", " +
                "\"displayText\" : \"Contact Us4\"}]}";
        headers.put("accountId", account.getAccountId().toString());
        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.SITE_LINK_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/sitelink/create", headers, payload));
        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 4);
        int i = 0;
        for (ResultsDTO result : responseDTO.getResults()) {
            assertConditionTrue(result.getIndex() == i);
            i += 1;
            SiteLinkAdExtensionDTO siteLinkAdExtensionDTO = (SiteLinkAdExtensionDTO) result.getObject();
            assertObjectEqual(siteLinkAdExtensionDTO.getId(), getSiteLinkAdExtension(account.getAccountId(), siteLinkAdExtensionDTO.getId()).getAdExtensionId());
        }
    }

    @Test
    public void testAddSiteLinkAdExtensionCustomLimit() throws Exception {
        Account account = createAccount(agencyId, "ACCT_" + System.currentTimeMillis(), "America/Los_Angeles");
        try {
            DBUtil.update("insert into pm_custom_limit_validation values('site_link_ad_extension', 1, ".concat(account.getAccountId().toString()).concat(", 'account', '").concat(userName) + "',SYSDATE,'" + userName + "',SYSDATE)");
            createSiteLinkAdExtension(account.getAccountId());

            String payload = "{\"siteLinkAdExtensions\" : [{" +
                    "\"destinationUrl\" : \"" + "http://yp_" + System.currentTimeMillis() + ".com" + "\", " +
                    "\"displayText\" : \"Contact Us\"}]}";
            headers.put("accountId", account.getAccountId().toString());
            ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.SITE_LINK_AD_EXTENSION, getAPIResponse(MethodEnum.POST, "account/ext/sitelink/create", headers, payload));


            assertObjectEqual(responseDTO.getResults().get(0).getError().getType().toString(), ErrorTypeEnum.USER_ERROR.toString());
            assertObjectEqual(responseDTO.getResults().get(0).getError().getMessage(), "Exceeds maximum number of site link ad extensions in account");
        } finally {
            DBUtil.update("delete from pm_custom_limit_validation WHERE PARENT_ID = ".concat(account.getAccountId().toString()));
        }
    }
}