package com.yp.pm.kp.be.impl;

import com.yp.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class KeplerSiteLinkAdExtensionImplTest extends BaseTest {

    @Inject
    KeplerSiteLinkAdExtensionImpl siteLinkAdExtensionDao;

    @Test
    public void testCreateSiteLinkAdExtension() throws Exception {

    }

    @Test
    public void testGetSiteLinkAdExtensionById() throws Exception {

    }

    @Test
    public void testGetSiteLinkAdExtensionByAccountId() throws Exception {

    }
}