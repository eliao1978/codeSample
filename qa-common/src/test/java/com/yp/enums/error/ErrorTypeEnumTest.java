package com.yp.enums.error;

import com.yp.enums.other.UserEnum;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ErrorTypeEnumTest {

    @Test
    public void testGetValue() throws Exception {
        assertTrue(ErrorTypeEnum.USER_ERROR.getValue().equalsIgnoreCase("USER_ERROR"));
    }

    @Test
    public void testGetLabel() throws Exception {
        assertTrue(ErrorTypeEnum.USER_ERROR.getLabel().equalsIgnoreCase("user error"));
    }
}