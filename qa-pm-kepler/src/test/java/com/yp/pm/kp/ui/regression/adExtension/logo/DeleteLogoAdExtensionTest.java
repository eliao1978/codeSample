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
public class DeleteLogoAdExtensionTest extends BaseUITest {
    private String label = "automation_image";
    private String deleted = "DELETED";
    private String imagePath = System.getProperty("user.dir") + "/src/test/resources/data/yellow_pages_walking_fingers_logo.gif";

    @Test
    public void testDeleteLogo() throws Exception {

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
            logoExtension.clickDeleteButton(label);
            assertConditionTrue(!logoExtension.getLogoTableText(2, 3).contains(label));

            List<LogoAdExtension> newLogo = getLogoAdExtensionByAccountId(account.getAccountId());
            for (LogoAdExtension logo : newLogo) {
                if (logo.getStatus().toString().equalsIgnoreCase(deleted)) {
                    assertObjectEqual(logo.getAccountId(), account.getAccountId());
                    assertObjectEqual(logo.getLabel(), label);
                }
            }
        }
    }


    @Test
    public void testDeleteLogoByClickingRemoveButton() throws Exception {

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
            logoExtension.deleteLogoExtensionByRemoveButton(2, 1);
            List<LogoAdExtension> newLogo = getLogoAdExtensionByAccountId(account.getAccountId());
            for (LogoAdExtension logo : newLogo) {
                if (logo.getStatus().toString().equalsIgnoreCase(deleted)) {
                    assertObjectEqual(logo.getAccountId(), account.getAccountId());
                    assertObjectEqual(logo.getLabel(), label);
                }
            }
        }

    }
}
