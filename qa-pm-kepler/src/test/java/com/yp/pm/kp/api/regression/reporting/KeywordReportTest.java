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
public class KeywordReportTest extends BaseAPITest {

    @Test
    public void testKeywordReport() throws Exception {
        assertResponseError(getResponseDTO(KeplerDomainTypeEnum.REPORTING, getAPIResponse(getCommand("NONE"))));
    }

    @Test
    public void testDailyKeywordReport() throws Exception {
        assertResponseError(getResponseDTO(KeplerDomainTypeEnum.REPORTING, getAPIResponse(getCommand("DAILY"))));
    }

    @Test
    public void testDayOfWeekKeywordReport() throws Exception {
        assertResponseError(getResponseDTO(KeplerDomainTypeEnum.REPORTING, getAPIResponse(getCommand("DAY_OF_WEEK"))));
    }

    @Test
    public void testWeeklyKeywordReport() throws Exception {
        assertResponseError(getResponseDTO(KeplerDomainTypeEnum.REPORTING, getAPIResponse(getCommand("WEEKLY"))));
    }

    @Test
    public void testMonthlyKeywordReport() throws Exception {
        assertResponseError(getResponseDTO(KeplerDomainTypeEnum.REPORTING, getAPIResponse(getCommand("MONTHLY"))));
    }

    @Test
    public void testQuarterlyKeywordReport() throws Exception {
        assertResponseError(getResponseDTO(KeplerDomainTypeEnum.REPORTING, getAPIResponse(getCommand("QUARTERLY"))));
    }

    @Test
    public void testYearlyKeywordReport() throws Exception {
        assertResponseError(getResponseDTO(KeplerDomainTypeEnum.REPORTING, getAPIResponse(getCommand("YEARLY"))));
    }

    private String getCommand(String dateSegmentation) {
        return "curl -X POST " +
                "-d '{" +
                "\"reportName\":\"Keyword_Performance_Report\"," +
                "\"reportFormat\" : \"CSV\"," +
                "\"reportType\":\"KEYWORD_PERFORMANCE_REPORT\"," +
                "\"fromDate\" : \"20150101\"," +
                "\"toDate\" : \"20150331\", " +
                "\"dateSegmentation\" : \"" + dateSegmentation + "\"," +
                "\"fields\":[" +
                "\"ACCOUNT_CLIENT_NAME\"," +
                "\"ACCOUNT_ID\"," +
                "\"CAMPAIGN_NAME\"," +
                "\"CAMPAIGN_ID\"," +
                "\"AD_GROUP_NAME\"," +
                "\"AD_GROUP_ID\"," +
                "\"KEYWORD_ID\"," +
                "\"KEYWORD_TEXT\"," +
                "\"KEYWORD_STATUS\"," +
                "\"KEYWORD_STATS_DATE\"," +
                "\"KEYWORD_MATCH_TYPE\"," +
                "\"KEYWORD_PROJECTED_MAX_CPC\"," +
                "\"KEYWORD_CLICKS\"," +
                "\"KEYWORD_IMPRESSIONS\"," +
                "\"KEYWORD_CLICK_THRU_RATE\"," +
                "\"KEYWORD_AVG_CPC\"," +
                "\"KEYWORD_SPEND\"," +
                "\"KEYWORD_AVG_POSITION\"]," +
                "\"accountIds\" : [108]," +
                "\"campaignIds\" : [142]" +
                "}' " +
                "-H '" + contentType + "' " +
                "-H 'username:'" + userName + "'' " +
                "-H 'requestid:'" + requestId + "'' " +
                "-H 'accountId:'" + 108 + "'' " +
                "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/reporting/generateReport";
    }
}