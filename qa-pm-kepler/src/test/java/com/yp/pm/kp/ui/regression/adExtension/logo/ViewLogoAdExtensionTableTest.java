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

import java.util.Arrays;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class ViewLogoAdExtensionTableTest extends BaseUITest {

    private String logoExtensionLabels[] = {"Image", "Label", "Status", "Campaign", "Clicks", "Impr.", "CTR", "Avg. CPC", "Cost", "Avg. Pos."};

    @Test
    public void testLogoTableHeader() throws Exception {
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
            for (String logoExtensionLabel : logoExtensionLabels) {
                if (!logoExtension.checkLogoHeader(logoExtensionLabel)) {
                    throw new NullPointerException("Element does not exist [" + logoExtensionLabel + "]");
                }
            }
        }
    }

    @Test
    public void testSortLogoTable() throws Exception {
        String label[] = {"yp3", "yp1", "yp2"};
        String imagePath[] = {System.getProperty("user.dir") + "/src/test/resources/data/YellowPages2.jpg", System.getProperty("user.dir") + "/src/test/resources/data/yellow_pages_walking_fingers_logo.gif", System.getProperty("user.dir") + "/src/test/resources/data/YellowPages2.jpg"};

        if (System.getProperty("os.name").equalsIgnoreCase("mac os x") && System.getProperty("selenium.driver").equalsIgnoreCase("firefox")) {
            Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
            Budget budget = createBudget(account.getAccountId(), 100, "standard");
            Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
            long campaignId = campaign.getCampaignId();

            MyAgencyPage agencyPage = login();
            AdExtensionLogoExtension logoExtension = agencyPage.navigateToLogoExtensionsLocationTab(agencyId, account.getAccountId(), campaignId);
            logoExtension.logoExtension();
            logoExtension.clickExtensionButton();
            for (int addLogo = 0; addLogo < label.length; addLogo++) {
                logoExtension.clickNewLogoExtensionButton();
                logoExtension.enterLabel(label[addLogo]);
                logoExtension.browseFile(imagePath[addLogo]);
                logoExtension.clickSave();
            }
            Arrays.sort(label);
            logoExtension.clickTableHeader(logoExtensionLabels[1]);
            //Get Text for Label
            assertConditionTrue(logoExtension.getLogoTableText(2, 3).equals(label[2]));
            assertConditionTrue(logoExtension.getLogoTableText(3, 3).equals(label[1]));
            assertConditionTrue(logoExtension.getLogoTableText(4, 3).equals(label[0]));
            logoExtension.clickTableHeader(logoExtensionLabels[1]);
            assertConditionTrue(logoExtension.getLogoTableText(2, 3).equals(label[0]));
            assertConditionTrue(logoExtension.getLogoTableText(3, 3).equals(label[1]));
            assertConditionTrue(logoExtension.getLogoTableText(4, 3).equals(label[2]));
        }
    }

    @Test
    public void testLogoStaticFilterTest() throws Exception {
        String label = "automation_image";
        String imagePath = System.getProperty("user.dir") + "/src/test/resources/data/yellow_pages_walking_fingers_logo.gif";
        String deleted = "DELETED";

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
            assertConditionTrue(!logoExtension.getLogoTableText(2, 3).contains(label));
            logoExtension.clickStaticFilterButton();
            logoExtension.staticFilterSelectAllAdExtension();
            assertConditionTrue(logoExtension.getLogoTableText(2, 3).contains(label));
            logoExtension.clickStaticFilterButton();
            logoExtension.staticFilterSelectAllButDeletedAdExtension();
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
}