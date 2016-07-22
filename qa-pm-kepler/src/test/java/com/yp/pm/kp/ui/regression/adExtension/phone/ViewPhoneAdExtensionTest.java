package com.yp.pm.kp.ui.regression.adExtension.phone;


import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.model.domain.PhoneAdExtension;
import com.yp.pm.kp.ui.AdExtensionCallExtension;
import com.yp.pm.kp.ui.MyAgencyPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class ViewPhoneAdExtensionTest extends BaseUITest {
    private String callExtensionLabels[] = {"Phone Number", "Status", "Campaign", "Clicks", "Impr.", "CTR", "Avg. CPC", "Cost", "Avg. Pos."};

    @Test
    public void testTableHeader() throws Exception {
        String CreatePhone = "9102191300";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();

        MyAgencyPage agencyPage = login();
        AdExtensionCallExtension callExtension = agencyPage.navigateToCallExtensionsLocationTab(agencyId, account.getAccountId(), campaignId);
        callExtension.callExtension();
        callExtension.click_EXTENSION_BUTTON();
        callExtension.click_New_Call_Extension_BUTTON();
        callExtension.enterPhone(CreatePhone);
        callExtension.clickSaveButton();
        for (String callExtensionLabel : callExtensionLabels) {
            if (!callExtension.checkCallHeader(callExtensionLabel)) {
                throw new NullPointerException("Element does not exist [" + callExtensionLabel + "]");
            }
        }
    }

    @Test
    public void testSortTable() throws Exception {
        String createPhone[] = {"9102191300", "8182222222", "7007721213"};

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();

        MyAgencyPage agencyPage = login();
        AdExtensionCallExtension callExtension = agencyPage.navigateToCallExtensionsLocationTab(agencyId, account.getAccountId(), campaignId);
        callExtension.callExtension();
        callExtension.click_EXTENSION_BUTTON();
        for (String aCreatePhone : createPhone) {
            callExtension.click_New_Call_Extension_BUTTON();
            callExtension.enterPhone(aCreatePhone);
            callExtension.clickSaveButton();
        }
        Arrays.sort(createPhone);
        //Validating Phone number
        callExtension.clickTableHeader(callExtensionLabels[0]);
        assertConditionTrue(callExtension.getCallTableText(2, 2).equals(createPhone[2]));
        assertConditionTrue(callExtension.getCallTableText(3, 2).equals(createPhone[1]));
        assertConditionTrue(callExtension.getCallTableText(4, 2).equals(createPhone[0]));
        callExtension.clickTableHeader(callExtensionLabels[0]);
        assertConditionTrue(callExtension.getCallTableText(2, 2).equals(createPhone[0]));
        assertConditionTrue(callExtension.getCallTableText(3, 2).equals(createPhone[1]));
        assertConditionTrue(callExtension.getCallTableText(4, 2).equals(createPhone[2]));
    }


    @Test
    public void testFilter() throws Exception {
        String deleted = "DELETED";
        String deletePhone = "9992191300";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();

        MyAgencyPage agencyPage = login();
        AdExtensionCallExtension callExtension = agencyPage.navigateToCallExtensionsLocationTab(agencyId, account.getAccountId(), campaignId);
        callExtension.callExtension();
        callExtension.click_EXTENSION_BUTTON();
        callExtension.click_New_Call_Extension_BUTTON();
        callExtension.enterPhone(deletePhone);
        callExtension.clickSaveButton();
        callExtension.deleteCallExtensionByRemoveButton(2, 1);
        callExtension.checkNoRecordsToLoad();
        assertConditionTrue(!callExtension.getCallTableText(2, 2).contains(deletePhone));
        callExtension.clickStaticFilterButton();
        callExtension.selectStaticFilterAllAdExtension();
        assertConditionTrue(callExtension.getCallTableText(2, 2).contains(deletePhone));
        callExtension.clickStaticFilterButton();
        callExtension.selectStaticFilterAllButDeletedAdExtension();
        assertConditionTrue(!callExtension.getCallTableText(2, 2).contains(deletePhone));

        List<PhoneAdExtension> newPhoneAdExtension = getPhoneAdExtensionByAccountId(account.getAccountId());
        for (PhoneAdExtension newPhone : newPhoneAdExtension) {
            if (newPhone.getAdExtensionStatus().toString().equalsIgnoreCase(deleted)) {
                assertObjectEqual(newPhone.getPhoneNumber(), deletePhone);
            }
        }

    }
}