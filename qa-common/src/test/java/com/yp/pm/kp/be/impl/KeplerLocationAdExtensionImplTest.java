package com.yp.pm.kp.be.impl;

import com.yp.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class KeplerLocationAdExtensionImplTest extends BaseTest {

    @Inject
    KeplerLocationAdExtensionImpl locationAdExtensionDao;

    @Test
    public void testCreateLocationAdExtension() throws Exception {

    }

    @Test
    public void testGetLocationAdExtensionById() throws Exception {

    }

    @Test
    public void testGetLocationAdExtensionByAccountId() throws Exception {

    }
}