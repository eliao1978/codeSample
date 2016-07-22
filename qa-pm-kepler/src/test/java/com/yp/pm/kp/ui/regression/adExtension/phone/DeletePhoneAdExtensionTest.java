package com.yp.pm.kp.ui.regression.adExtension.phone;

import com.yp.enums.selenium.LocatorTypeEnum;
import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.enums.model.CampaignAdExtensionApprovalStatusEnum;
import com.yp.pm.kp.enums.model.CampaignAdExtensionStatusEnum;
import com.yp.pm.kp.enums.model.CampaignAdExtensionTypeEnum;
import com.yp.pm.kp.enums.model.PhoneAdExtensionStatusEnum;
import com.yp.pm.kp.model.domain.*;
import com.yp.pm.kp.ui.AdExtensionCallExtension;
import com.yp.pm.kp.ui.MyAgencyPage;
import com.yp.pm.kp.ui.UIMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class DeletePhoneAdExtensionTest extends BaseUITest {
    private String deleted = "DELETED";
    private String deletePhone = "9992191300";

    @Test
    public void testDeletePhone() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();
        PhoneAdExtension newPhone = new PhoneAdExtension();
        newPhone.setAccountId(account.getAccountId());
        newPhone.setPhoneNumber(deletePhone);
        newPhone.setIsCallableOnly(true);
        newPhone.setAdExtensionStatus(PhoneAdExtensionStatusEnum.ACTIVE);
        PhoneAdExtension newPhoneAdExt = createPhoneAdExtension(newPhone);
        List<CampaignAdExtension> list = new ArrayList<>();
        CampaignAdExtension adExtension = new CampaignAdExtension();
        adExtension.setAdExtensionId(newPhoneAdExt.getAdExtensionId());
        adExtension.setAccountId(account.getAccountId());
        adExtension.setCampaignId(campaign.getCampaignId());
        adExtension.setAdExtensionStatus(CampaignAdExtensionStatusEnum.ACTIVE);
        adExtension.setApprovalStatus(CampaignAdExtensionApprovalStatusEnum.APPROVED);
        adExtension.setAdExtensionType(CampaignAdExtensionTypeEnum.PHONE);
        list.add(adExtension);
        createCampaignAdExtensions(list);

        MyAgencyPage agencyPage = login();
        AdExtensionCallExtension callExtension = agencyPage.navigateToCallExtensionsLocationTab(agencyId, account.getAccountId(), campaignId);
        callExtension.callExtension();
        callExtension.selectCallExtension(deletePhone);
        callExtension.clickRemoveButton();
        callExtension.confirmDelete();
        callExtension.waitForElementNotVisible(UIMapper.LOADING);
        callExtension.waitForElementVisible("//tr/td[normalize-space(.)='No records to load.']", LocatorTypeEnum.XPATH);
        assertConditionTrue(!callExtension.getCallTableText(2, 2).contains(deletePhone));

        List<PhoneAdExtension> newPhoneAdExtension = getPhoneAdExtensionByAccountId(account.getAccountId());
        for (PhoneAdExtension checkPhone : newPhoneAdExtension) {
            if (checkPhone.getAdExtensionStatus().toString().equalsIgnoreCase(deleted)) {
                assertObjectEqual(checkPhone.getPhoneNumber(), deletePhone);
            }
        }
    }

    @Test
    public void testDeletePhoneByClickingRemoveButton() throws Exception {
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
        callExtension.waitForElementNotVisible(UIMapper.LOADING);
        assertConditionTrue(!callExtension.getCallTableText(2, 2).contains(deletePhone));

        List<PhoneAdExtension> newPhoneAdExtension = getPhoneAdExtensionByAccountId(account.getAccountId());
        for (PhoneAdExtension newPhone : newPhoneAdExtension) {
            if (newPhone.getAdExtensionStatus().toString().equalsIgnoreCase(deleted)) {
                assertObjectEqual(newPhone.getPhoneNumber(), deletePhone);
            }
        }

    }
}
