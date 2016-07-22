package com.yp.util;

import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.assertNotNull;

public class DateUtilTest {

    @Test
    public void testGetTime() throws Exception {
        assertNotNull(DateUtil.getTime());
    }

    @Test
    public void testGetStartDate() throws Exception {
        assertNotNull(DateUtil.getStartDate(new Timestamp(System.currentTimeMillis())));
    }

    @Test
    public void testGetEndDate() throws Exception {
        assertNotNull(DateUtil.getEndDate(3));
    }

    @Test
    public void testGetFutureDate() throws Exception {
        assertNotNull(DateUtil.getFutureDate(3));
    }

    @Test
    public void testGetPastDate() throws Exception {
        assertNotNull(DateUtil.getPastDate(-3));
    }
}