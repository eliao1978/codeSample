package com.yp.util;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class LogUtilTest {

    LogUtil service = new LogUtil();

    @Test
    public void testGetLogger() throws Exception {
        assertNotNull(service.getLogger());
    }

    @Test
    public void testGetFileLogger() throws Exception {
        assertNotNull(service.getFileLogger());
    }
}