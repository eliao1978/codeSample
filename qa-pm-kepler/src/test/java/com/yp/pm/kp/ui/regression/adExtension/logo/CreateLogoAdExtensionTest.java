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
public class CreateLogoAdExtensionTest extends BaseUITest {

    @Test
    public void testCreateNewLogo() throws Exception {
        String label = "yp";
        String imagePath = System.getProperty("user.dir") + "/src/test/resources/data/YellowPages2.jpg";

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
            assertConditionTrue(logoExtension.getLogoTableText(2, 3).contains(label));

            List<LogoAdExtension> newLogo = getLogoAdExtensionByAccountId(account.getAccountId());
            assertConditionTrue(newLogo.size() == 1);
            LogoAdExtension logo = newLogo.get(0);
            assertObjectNotNull(logo.getAccountId());
            assertObjectEqual(logo.getAccountId(), account.getAccountId());
            assertObjectEqual(logo.getLabel(), label);
            assertObjectNotNull(logo.getFullPath());
        }
    }

    @Test
    public void testCancelCreateLogo() throws Exception {
        String label = "yp";
        String imagePath = System.getProperty("user.dir") + "/src/test/resources/data/yellow_pages_walking_fingers_logo.gif";

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
            logoExtension.clickCancel();

            List<LogoAdExtension> newLogo = getLogoAdExtensionByAccountId(account.getAccountId());
            for (LogoAdExtension logo : newLogo) {
                assertConditionTrue(!logo.getLabel().equalsIgnoreCase(label));
            }
        }
    }

    @Test
    public void testMissingLogoLabelLogoImageError() throws Exception {
        String labelError = "Label is required";
        String imageError = "Image is required";

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
            logoExtension.clickSave();
            assertConditionTrue(logoExtension.getLogoExtensionLabelRequiredErrorText().contains(labelError));
            assertConditionTrue(logoExtension.getLogoExtensionImageRequiredErrorText().contains(imageError));
        }
    }

    @Test
    public void testLabelBoundaryValuesError() throws Exception {
        String labelError = "Label is too long";
        String imageError = "Image is required";
        String label = "abcdefghijklmnopqrstuvwxyzabcdefghij";

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
            logoExtension.clickSave();

            assertConditionTrue(logoExtension.getLogoExtensionLabelTooLong().contains(labelError));
            assertConditionTrue(logoExtension.getLogoExtensionImageRequiredErrorText().contains(imageError));
        }
    }

    @Test
    public void testLogoMaxImageSizeError() throws Exception {
        String errorMessage = "Image size must be 50x50 pixels";
        String label = "yp";
        String imagePath = System.getProperty("user.dir") + "/src/test/resources/data/MoreThan50Pixcels.png";

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
            assertConditionTrue(logoExtension.getLogoExtensionMaxImageSizeError().contains(errorMessage));
        }
    }

    @Test
    public void testLogoFileFormatError() throws Exception {
        String errorMessage = ".gif, .png and .jpg are only supported";
        String label = "yp";
        String imagePath = System.getProperty("user.dir") + "/src/test/resources/data/invalidFileFormat.jpeg";

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
            assertConditionTrue(logoExtension.getLogoExtensionInvalidFileFormatError().contains(errorMessage));
        }
    }
}
