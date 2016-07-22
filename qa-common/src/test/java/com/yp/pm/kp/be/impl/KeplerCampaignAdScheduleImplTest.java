package com.yp.pm.kp.be.impl;

import com.yp.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class KeplerCampaignAdScheduleImplTest extends BaseTest {

    @Inject
    KeplerCampaignAdScheduleImpl campaignAdScheduleDao;

    @Test
    public void testCreateCampaignAdSchedule() throws Exception {
    }

    @Test
    public void testGetCampaignAdScheduleById() throws Exception {

    }

    @Test
    public void testGetCampaignAdScheduleByCampaignId() throws Exception {

    }

    @Test
    public void testDeleteCampaignAdSchedule() throws Exception {

    }
}