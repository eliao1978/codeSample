package com.yp.pm.kp.api.regression.reporting;

import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.pm.kp.BaseAPITest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CampaignReportTest extends BaseAPITest {

    @Test
    public void testCampaignReport() throws Exception {
        assertResponseError(getResponseDTO(KeplerDomainTypeEnum.REPORTING, getAPIResponse(getCommand("NONE"))));
    }

    @Test
    public void testDailyCampaignReport() throws Exception {
        assertResponseError(getResponseDTO(KeplerDomainTypeEnum.REPORTING, getAPIResponse(getCommand("DAILY"))));
    }

    @Test
    public void testDayOfWeekCampaignReport() throws Exception {
        assertResponseError(getResponseDTO(KeplerDomainTypeEnum.REPORTING, getAPIResponse(getCommand("DAY_OF_WEEK"))));
    }

    @Test
    public void testWeeklyCampaignReport() throws Exception {
        assertResponseError(getResponseDTO(KeplerDomainTypeEnum.REPORTING, getAPIResponse(getCommand("WEEKLY"))));
    }

    @Test
    public void testMonthlyCampaignReport() throws Exception {
        assertResponseError(getResponseDTO(KeplerDomainTypeEnum.REPORTING, getAPIResponse(getCommand("MONTHLY"))));
    }

    @Test
    public void testQuarterlyCampaignReport() throws Exception {
        assertResponseError(getResponseDTO(KeplerDomainTypeEnum.REPORTING, getAPIResponse(getCommand("QUARTERLY"))));
    }

    @Test
    public void testYearlyCampaignReport() throws Exception {
        assertResponseError(getResponseDTO(KeplerDomainTypeEnum.REPORTING, getAPIResponse(getCommand("YEARLY"))));
    }

    private String getCommand(String dateSegmentation) {
        return "curl -X POST " +
                "-d '{" +
                "\"reportName\":\"Campaign_Performance_Report\"," +
                "\"reportFormat\" : \"CSV\"," +
                "\"reportType\":\"CAMPAIGN_PERFORMANCE_REPORT\"," +
                "\"fromDate\" : \"20150101\"," +
                "\"toDate\" : \"20150331\", " +
                "\"dateSegmentation\" : \"" + dateSegmentation + "\"," +
                "\"fields\":[" +
                "\"ACCOUNT_CLIENT_NAME\"," +
                "\"ACCOUNT_ID\"," +
                "\"CAMPAIGN_NAME\"," +
                "\"CAMPAIGN_ID\"," +
                "\"CAMPAIGN_STATUS\"," +
                "\"CAMPAIGN_STATS_DATE\"," +
                "\"CAMPAIGN_CLICKS\"," +
                "\"CAMPAIGN_IMPRESSIONS\"," +
                "\"CAMPAIGN_CLICK_THRU_RATE\"," +
                "\"CAMPAIGN_AVG_CPC\"," +
                "\"CAMPAIGN_SPEND\"," +
                "\"CAMPAIGN_AVG_POSITION\"]," +
                "\"agencyIds\" : [102]," +
                "\"accountIds\" : [108]" +
                "}' " +
                "-H '" + contentType + "' " +
                "-H 'username:'" + userName + "'' " +
                "-H 'requestid:'" + requestId + "'' " +
                "-H 'accountId:'" + 108 + "'' " +
                "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/reporting/generateReport";
    }
}