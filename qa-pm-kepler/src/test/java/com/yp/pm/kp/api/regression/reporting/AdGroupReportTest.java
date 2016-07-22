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
public class AdGroupReportTest extends BaseAPITest {

    @Test
    public void testAdGroupReport() throws Exception {
        assertResponseError(getResponseDTO(KeplerDomainTypeEnum.REPORTING, getAPIResponse(getCommand("NONE"))));
    }

    @Test
    public void testDailyAdGroupReport() throws Exception {
        assertResponseError(getResponseDTO(KeplerDomainTypeEnum.REPORTING, getAPIResponse(getCommand("DAILY"))));
    }

    @Test
    public void testDayOfWeekAdGroupReport() throws Exception {
        assertResponseError(getResponseDTO(KeplerDomainTypeEnum.REPORTING, getAPIResponse(getCommand("DAY_OF_WEEK"))));
    }

    @Test
    public void testWeeklyAdGroupReport() throws Exception {
        assertResponseError(getResponseDTO(KeplerDomainTypeEnum.REPORTING, getAPIResponse(getCommand("WEEKLY"))));
    }

    @Test
    public void testMonthlyAdGroupReport() throws Exception {
        assertResponseError(getResponseDTO(KeplerDomainTypeEnum.REPORTING, getAPIResponse(getCommand("MONTHLY"))));
    }

    @Test
    public void testQuarterlyAdGroupReport() throws Exception {
        assertResponseError(getResponseDTO(KeplerDomainTypeEnum.REPORTING, getAPIResponse(getCommand("QUARTERLY"))));
    }

    @Test
    public void testYearlyAdGroupReport() throws Exception {
        assertResponseError(getResponseDTO(KeplerDomainTypeEnum.REPORTING, getAPIResponse(getCommand("YEARLY"))));
    }

    private String getCommand(String dateSegmentation) {
        return "curl -X POST " +
                "-d '{" +
                "\"reportName\":\"Adgroup_Performance_Report\"," +
                "\"reportFormat\" : \"CSV\"," +
                "\"reportType\":\"AD_GROUP_PERFORMANCE_REPORT\"," +
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
                "\"AD_GROUP_STATUS\"," +
                "\"AD_GROUP_STATS_DATE\"," +
                "\"AD_GROUP_CLICKS\"," +
                "\"AD_GROUP_IMPRESSIONS\"," +
                "\"AD_GROUP_CLICK_THRU_RATE\"," +
                "\"AD_GROUP_AVG_CPC\"," +
                "\"AD_GROUP_SPEND\"," +
                "\"AD_GROUP_AVG_POSITION\"]," +
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