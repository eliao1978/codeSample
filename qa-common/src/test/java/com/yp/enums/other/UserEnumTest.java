package com.yp.enums.other;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class UserEnumTest {

    @Test
    public void testGetValue() throws Exception {
        assertTrue(UserEnum.KP_SUPER_ADMIN.getValue().equalsIgnoreCase("ats@yp.com"));
    }

    @Test
    public void testGetLabel() throws Exception {
        assertTrue(UserEnum.KP_SUPER_ADMIN.getLabel().equalsIgnoreCase("kepler super admin"));
    }
}