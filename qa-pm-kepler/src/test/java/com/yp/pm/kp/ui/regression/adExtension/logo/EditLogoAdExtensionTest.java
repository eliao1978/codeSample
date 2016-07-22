package com.yp.pm.kp.ui.regression.adExtension.logo;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.model.domain.LogoAdExtension;
import com.yp.pm.kp.ui.AdExtensionLogoExtension;
import com.yp.pm.kp.ui.MyAgencyPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class EditLogoAdExtensionTest extends BaseUITest {

    @Test
    public void testEditLogo() throws Exception {
        String label = "yp";
        String EditedLabel = "YP1";
        String active = "ACTIVE";
        String imagePath = System.getProperty("user.dir") + "/src/test/resources/data/yellow_pages_walking_fingers_logo.gif";
        String editedPath = System.getProperty("user.dir") + "/src/test/resources/data/YellowPages2.jpg";

        if (System.getProperty("os.name").equalsIgnoreCase("mac os x") && System.getProperty("selenium.driver").equalsIgnoreCase("firefox")) {
            Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = createBudget(account.getAccountId(), 100, "standard");
            Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
            long campaignId = campaign.getCampaignId();

            MyAgencyPage agencyPage = login();
            AdExtensionLogoExtension logoExtension = agencyPage.navigateToLogoExtensionsLocationTab(agencyId, account.getAccountId(), campaignId);
            logoExtension.logoExtension();
            logoExtension.clickExtensionButton();
            logoExtension.clickNewLogoExtensionButton();
            logoExtension.enterLabel(label);
            logoExtension.browseFile(imagePath);
            logoExtension.clickSave();
            logoExtension.click_Panel_Edit_Extension();
            logoExtension.click_Edit_Extension();
            logoExtension.enterLabel(EditedLabel);
            logoExtension.browseFile(editedPath);
            logoExtension.clickSave();

            List<LogoAdExtension> newLogo = getLogoAdExtensionByAccountId(account.getAccountId());
            assertConditionTrue(newLogo.size() >= 1);
            for (LogoAdExtension logo : newLogo) {
                if (logo.getStatus().toString().equalsIgnoreCase(active)) {
                    assertObjectEqual(logo.getAccountId(), account.getAccountId());
                    assertObjectEqual(logo.getLabel(), EditedLabel);
                    assertObjectNotNull(logo.getFullPath());
                }
            }
        }
    }
}