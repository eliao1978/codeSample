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

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class CreatePhoneAdExtensionTest extends BaseUITest {

    @Test
    public void testCreateNewPhone() throws Exception {
        String createPhone = "9102191300";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();

        MyAgencyPage agencyPage = login();
        AdExtensionCallExtension callExtension = agencyPage.navigateToCallExtensionsLocationTab(agencyId, account.getAccountId(), campaignId);
        callExtension.callExtension();
        callExtension.click_EXTENSION_BUTTON();
        callExtension.click_New_Call_Extension_BUTTON();
        callExtension.enterPhone(createPhone);
        callExtension.clickSaveButton();
        assertConditionTrue(callExtension.getCallTableText(2, 2).contains(createPhone));

        List<PhoneAdExtension> newPhoneAdExtension = getPhoneAdExtensionByAccountId(account.getAccountId());
        assertConditionTrue(newPhoneAdExtension.size() == 1);
        PhoneAdExtension newPhone = newPhoneAdExtension.get(0);
        assertObjectEqual(newPhone.getAccountId(), account.getAccountId());
        assertObjectEqual(newPhone.getPhoneNumber(), createPhone);
        assertObjectEqual(newPhone.getAdExtensionStatus().toString(), "ACTIVE");
    }

    @Test
    public void testCancelCreateNewPhone() throws Exception {
        String cancelPhone = "9102191303";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();

        MyAgencyPage agencyPage = login();
        AdExtensionCallExtension callExtension = agencyPage.navigateToCallExtensionsLocationTab(agencyId, account.getAccountId(), campaignId);
        callExtension.callExtension();
        callExtension.click_EXTENSION_BUTTON();
        callExtension.click_New_Call_Extension_BUTTON();
        callExtension.enterPhone(cancelPhone);
        callExtension.clickCancelButton();

        List<PhoneAdExtension> newPhoneAdExtension = getPhoneAdExtensionByAccountId(account.getAccountId());
        for (PhoneAdExtension newPhone : newPhoneAdExtension) {
            assertConditionTrue(!newPhone.getPhoneNumber().equalsIgnoreCase(cancelPhone));
        }
    }

    @Test
    public void testPhoneNumberMissingError() throws Exception {
        String phoneError = "Phone number is required";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();

        MyAgencyPage agencyPage = login();
        AdExtensionCallExtension callExtension = agencyPage.navigateToCallExtensionsLocationTab(agencyId, account.getAccountId(), campaignId);
        callExtension.callExtension();
        callExtension.click_EXTENSION_BUTTON();
        callExtension.click_New_Call_Extension_BUTTON();
        callExtension.clickSaveButton();
        assertConditionTrue(callExtension.getCallExtensionPhoneRequiredErrorText().equalsIgnoreCase(phoneError));
    }

    @Test
    public void testPhoneBoundaryValuesError() throws Exception {
        String errorMessage = "Phone number is too long";
        String Phone = "91021999791300";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();

        MyAgencyPage agencyPage = login();
        AdExtensionCallExtension callExtension = agencyPage.navigateToCallExtensionsLocationTab(agencyId, account.getAccountId(), campaignId);
        callExtension.callExtension();
        callExtension.click_EXTENSION_BUTTON();
        callExtension.click_New_Call_Extension_BUTTON();
        callExtension.enterPhone(Phone);
        callExtension.clickSaveButton();
        assertConditionTrue(callExtension.getCallExtensionPhoneNumberTooLongErrorText().equalsIgnoreCase(errorMessage));
    }
}