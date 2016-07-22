package com.yp.pm.kp.ui.regression.ad;

import com.yp.enums.selenium.LocatorTypeEnum;
import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.enums.model.AdStatusEnum;
import com.yp.pm.kp.model.domain.*;
import com.yp.pm.kp.ui.AdPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import com.yp.pm.kp.ui.UIMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class EditAdTest extends BaseUITest {

    @Test
    public void testEdit() throws Exception {
        String headline = "test headline";
        String description1 = "test description 1";
        String description2 = "test description 2";
        String url = "www.yp.com";
        String editedAd = "LEGO";


        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());

        MyAgencyPage agencyPage = login();
        AdPage adPage = agencyPage.navigateToAdTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        if (!adPage.verifyTextPresent(headline)) {
            adPage.clickCreateAdButton();
            adPage.selectAdGroupX(adGroup.getAdGroupName());
            adPage.enterHeadline(headline);
            adPage.enterDescription1(description1);
            adPage.enterDescription2(description2);
            adPage.enterDisplayUrl(url);
            adPage.enterDestinationUrl(url);
            adPage.submit();
            adPage.checkButtonNotVisible(UIMapper.SUBMIT_AD, LocatorTypeEnum.CSS);
        }
        adPage.hoverAd(headline);
        adPage.clickEditIcon(headline);
        adPage.editHeadline(editedAd);
        adPage.clickSaveEdit();
        adPage.checkButtonNotVisible(UIMapper.SAVE_EDIT_AD_BUTTON, LocatorTypeEnum.CSS);
        assertConditionTrue(adPage.getAdTableText(2, 3).contains(headline));
        assertConditionTrue(adPage.getAdTableText(2, 3).contains(editedAd));
        assertConditionTrue(adPage.getAdTableText(2, 3).contains(description1));
        assertConditionTrue(adPage.getAdTableText(2, 3).contains(description2));
        assertConditionTrue(adPage.getAdTableText(2, 3).contains(url));


        List<Ad> adList = getAdByAdGroupId(account.getAccountId(), adGroup.getAdGroupId());
        assertConditionTrue(adList.size() >= 1);
        for (Ad ad : adList) {
            if (ad.getAdStatus().toString().equals(AdStatusEnum.ACTIVE.toString())) {
                assertObjectNotNull(ad.getAdId());
                assertObjectEqual(ad.getAccountId(), account.getAccountId());
                assertObjectEqual(ad.getAdGroupId(), adGroup.getAdGroupId());
                assertObjectEqual(ad.getHeadline(), headline + "LEGO");
                assertObjectEqual(ad.getDescription1(), description1);
                assertObjectEqual(ad.getDescription2(), description2);
                assertObjectEqual(ad.getDisplayUrl(), url);
                assertObjectEqual(ad.getDestinationUrl(), "http://" + url);
            }
        }
    }


    @Test
    public void testCancelEdit() throws Exception {
        String headline = "test headline";
        String description1 = "test description 1";
        String description2 = "test description 2";
        String url = "www.yp.com";
        String cancelEditedAd = "Help";

        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        AdGroup adGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());

        MyAgencyPage agencyPage = login();
        AdPage adPage = agencyPage.navigateToAdTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        if (!adPage.verifyTextPresent(headline)) {
            adPage.clickCreateAdButton();
            adPage.selectAdGroupX(adGroup.getAdGroupName());
            adPage.enterHeadline(headline);
            adPage.enterDescription1(description1);
            adPage.enterDescription2(description2);
            adPage.enterDisplayUrl(url);
            adPage.enterDestinationUrl(url);
            adPage.submit();
            adPage.checkButtonNotVisible(UIMapper.SUBMIT_AD, LocatorTypeEnum.CSS);
        }
        adPage.hoverAd(headline);
        adPage.clickEditIcon(headline);
        adPage.editHeadline(cancelEditedAd);
        adPage.clickCancelEdit();
        adPage.checkButtonNotVisible(UIMapper.SAVE_EDIT_AD_BUTTON, LocatorTypeEnum.CSS);
        assertConditionTrue(!adPage.getAdTableText(2, 3).contains(cancelEditedAd));


        List<Ad> adList = getAdByAdGroupId(account.getAccountId(), adGroup.getAdGroupId());
        assertConditionTrue(adList.size() >= 1);
        for (Ad ad : adList) {
            if (ad.getAdStatus().toString().equals(AdStatusEnum.ACTIVE.toString())) {
                assertObjectNotNull(ad.getAdId());
                assertObjectEqual(ad.getAccountId(), account.getAccountId());
                assertObjectEqual(ad.getAdGroupId(), adGroup.getAdGroupId());
                assertObjectEqual(ad.getHeadline(), headline);
                assertObjectEqual(ad.getDescription1(), description1);
                assertObjectEqual(ad.getDescription2(), description2);
                assertObjectEqual(ad.getDisplayUrl(), url);
                assertObjectEqual(ad.getDestinationUrl(), "http://" + url);
            }
        }
    }

}