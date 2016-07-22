package com.yp.pm.kp.ui.smoke;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.ui.MyAgencyPage;
import com.yp.pm.kp.ui.UIMapper;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by ar2130 on 2/1/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UIVersionTest extends BaseUITest {
    MyAgencyPage agencyPage;

    @Test
    public void assertAppVersion() throws Exception {
        String servers[] = new String[0];
        if(System.getProperty("ui.nodes").contains(",")) {
           servers = System.getProperty("ui.nodes").split(",");
        }
        else {
            servers[0] = System.getProperty("ui.nodes");
        }
        for (String node : servers) {
            logger.debug("Server: " + node);
            agencyPage = getAgencyPageObject(node);
            logger.debug(agencyPage.getUrl());
            String uiVersion = agencyPage.getTextFromElement(UIMapper.APP_VERSION);
            logger.debug("APP VERSION: " + uiVersion);
            assertConditionTrue(uiVersion.contains(version));
        }
    }

    private MyAgencyPage getAgencyPageObject(String server) throws Exception {

        return loginNode(server);
    }
}
