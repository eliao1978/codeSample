package com.yp.pm.kp.ui.regression.adExtension.phone;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.enums.model.CampaignAdExtensionApprovalStatusEnum;
import com.yp.pm.kp.enums.model.CampaignAdExtensionStatusEnum;
import com.yp.pm.kp.enums.model.CampaignAdExtensionTypeEnum;
import com.yp.pm.kp.enums.model.PhoneAdExtensionStatusEnum;
import com.yp.pm.kp.model.domain.*;
import com.yp.pm.kp.ui.AdExtensionCallExtension;
import com.yp.pm.kp.ui.MyAgencyPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class EditPhoneAdExtensionTest extends BaseUITest {

    @Test
    public void testEditPhone() throws Exception {
        String phone = "9102191300";
        String editedPhone = "9219391293";
        String active = "ACTIVE";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        long campaignId = campaign.getCampaignId();
        PhoneAdExtension newPhone = new PhoneAdExtension();
        newPhone.setAccountId(account.getAccountId());
        newPhone.setPhoneNumber(phone);
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
        callExtension.selectCallExtension(phone);
        callExtension.click_Ad_Extension_Edit();
        callExtension.click_Edit_Extension();
        callExtension.enterPhone(editedPhone);
        callExtension.clickSaveButton();
        assertConditionTrue(!callExtension.getCallTableText(2, 2).contains(phone));
        assertConditionTrue(callExtension.getCallTableText(2, 2).contains(editedPhone));

        List<PhoneAdExtension> newPhoneAdExtension = getPhoneAdExtensionByAccountId(account.getAccountId());
        assertConditionTrue(newPhoneAdExtension.size() >= 1);
        for (PhoneAdExtension checkPhone : newPhoneAdExtension) {
            if (checkPhone.getAdExtensionStatus().toString().equalsIgnoreCase(active)) {
                assertObjectEqual(checkPhone.getAccountId(), account.getAccountId());
                assertObjectEqual(checkPhone.getPhoneNumber(), editedPhone);
            }
        }
    }
}