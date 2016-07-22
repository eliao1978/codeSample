package com.yp.util;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class HTTPUtilTest {

    HTTPUtil service = new HTTPUtil();

    @Test
    public void testGetResponse() throws Exception {
        assertNotNull(service.getResponse("http://www.yellowpages.com/"));

    }

    @Test
    public void testGetResponseCode() throws Exception {
        assertNotNull(service.getResponseCode("http://www.yellowpages.com/"));
    }
}