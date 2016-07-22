package com.yp.pm.kp.be.impl;

import com.yp.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class KeplerPhoneAdExtensionImplTest extends BaseTest {

    @Inject
    KeplerPhoneAdExtensionImpl phoneAdExtensionDao;

    @Test
    public void testCreatePhoneAdExtension() throws Exception {

    }

    @Test
    public void testGetPhoneAdExtensionById() throws Exception {

    }

    @Test
    public void testGetPhoneAdExtensionByAccountId() throws Exception {

    }
}