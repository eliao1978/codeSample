package com.yp.pm.kp.ui.regression.account;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.ui.AccountPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ViewStatsTableTest extends BaseUITest {

    @Test
    public void assertTableHeader() throws Exception {
        String columns[] = {"Client", "Clicks", "Impr.", "CTR", "Avg. CPC", "Amount Spent"};

        MyAgencyPage agencyPage = login();
        AccountPage accountPage = agencyPage.navigateToAccountPage(agencyId);

        // ui assertion
        int i = 2;
        for (String label : columns) {
            assertObjectEqual(accountPage.getTextFromStatsTableHeader(i), label);
            i += 1;
        }
    }

    @Ignore
    public void assertTableSortingByColumn() throws Exception {
        MyAgencyPage agencyPage = login();
        AccountPage accountPage = agencyPage.navigateToAccountPage(agencyId);

        /**
         * assert sorting by clicking each header column
         * i = 3 Clicks
         * i = 4 Impression
         * i = 5 CTR
         * i = 6 Average CPC
         * i = 7 Amount Spent
         */
        for (int i = 3; i <= 7; i++) {
            // descending
            accountPage.clickStatsTableHeaderByColumn(i);
            ArrayList<String> originalDescending = accountPage.getTextFromStatsTableByColumn(10, i);
            assertConditionTrue(accountPage.isStatsColumnSorted(originalDescending, AccountPage.OrderType.DESCENDING));

            // ascending
            accountPage.clickStatsTableHeaderByColumn(i);
            ArrayList<String> originalAscending = accountPage.getTextFromStatsTableByColumn(10, i);
            assertConditionTrue(accountPage.isStatsColumnSorted(originalAscending, AccountPage.OrderType.ASCENDING));
        }
    }

    @Test
    public void assertTableRowCount() throws Exception {
        MyAgencyPage agencyPage = login();
        AccountPage accountPage = agencyPage.navigateToAccountPage(agencyId);

        /**
         * 1 = 10 row per page
         * 2 = 15 row per page
         * 3 = 30 row per page
         * 4 = 50 row per page
         * 5 = 100 row per page
         */
        accountPage.showRowsPerPage(1);
        assertObjectEqual(accountPage.getStatsTableRowCount(), 10);

        accountPage.showRowsPerPage(2);
        assertObjectEqual(accountPage.getStatsTableRowCount(), 15);

        accountPage.showRowsPerPage(3);
        assertObjectEqual(accountPage.getStatsTableRowCount(), 30);

        accountPage.showRowsPerPage(4);
        assertObjectEqual(accountPage.getStatsTableRowCount(), 50);

        accountPage.showRowsPerPage(5);
        assertObjectEqual(accountPage.getStatsTableRowCount(), 100);
    }
}