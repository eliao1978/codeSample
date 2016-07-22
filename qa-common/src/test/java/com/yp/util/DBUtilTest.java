package com.yp.util;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class DBUtilTest {

    @Test
    public void testRunSQL() throws Exception {
        System.setProperty("db.url", "jdbc:oracle:thin:@ldap://oraldap.ev1.yellowpages.com:389/IQ1LGEN,cn=OracleContext,dc=yellowpages,dc=com");
        System.setProperty("db.username", "KEPLER_QA1");
        System.setProperty("db.password", "keplerqa1");
        assertNotNull(DBUtil.runSQL("SELECT SYSDATE FROM dual").get(0).get("SYSDATE"));
    }
}