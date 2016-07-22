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
public class AccountReportTest extends BaseAPITest {

    @Test
    public void testAccountReport() throws Exception {
        assertResponseError(getResponseDTO(KeplerDomainTypeEnum.REPORTING, getAPIResponse(getCommand("NONE"))));
    }

    @Test
    public void testDailyAccountReport() throws Exception {
        assertResponseError(getResponseDTO(KeplerDomainTypeEnum.REPORTING, getAPIResponse(getCommand("DAILY"))));
    }

    @Test
    public void testDayOfWeekAccountReport() throws Exception {
        assertResponseError(getResponseDTO(KeplerDomainTypeEnum.REPORTING, getAPIResponse(getCommand("DAY_OF_WEEK"))));
    }

    @Test
    public void testWeeklyAccountReport() throws Exception {
        assertResponseError(getResponseDTO(KeplerDomainTypeEnum.REPORTING, getAPIResponse(getCommand("WEEKLY"))));
    }

    @Test
    public void testMonthlyAccountReport() throws Exception {
        assertResponseError(getResponseDTO(KeplerDomainTypeEnum.REPORTING, getAPIResponse(getCommand("MONTHLY"))));
    }

    @Test
    public void testQuarterlyAccountReport() throws Exception {
        assertResponseError(getResponseDTO(KeplerDomainTypeEnum.REPORTING, getAPIResponse(getCommand("QUARTERLY"))));
    }

    @Test
    public void testYearlyAccountReport() throws Exception {
        assertResponseError(getResponseDTO(KeplerDomainTypeEnum.REPORTING, getAPIResponse(getCommand("YEARLY"))));
    }

    private String getCommand(String dateSegmentation) {
        return "curl -X POST " +
                "-d '{" +
                "\"reportName\":\"Account_Performance_Report\"," +
                "\"reportFormat\":\"CSV\"," +
                "\"reportType\":\"ACCOUNT_PERFORMANCE_REPORT\"," +
                "\"fromDate\":\"20150101\", " +
                "\"toDate\":\"20150331\"," +
                "\"dateSegmentation\":\"" + dateSegmentation + "\"," +
                "\"accountIds\":[" + 108 + "]," +
                "\"fields\":[" +
                "\"ACCOUNT_CLIENT_NAME\"," +
                "\"ACCOUNT_ID\"," +
                "\"ACCOUNT_STATS_DATE\"," +
                "\"ACCOUNT_CLICKS\"," +
                "\"ACCOUNT_IMPRESSIONS\"," +
                "\"ACCOUNT_CLICK_THRU_RATE\"," +
                "\"ACCOUNT_AVG_CPC\"," +
                "\"ACCOUNT_SPEND\"," +
                "\"ACCOUNT_AVG_POSITION\"]" +
                "}' " +
                "-H '" + contentType + "' " +
                "-H 'username:'" + userName + "'' " +
                "-H 'requestid:'" + requestId + "'' " +
                "-H 'accountId:'" + 108 + "'' " +
                "http://" + System.getProperty("api.server") + "/pm-kepler-api/api/reporting/generateReport";
    }
}