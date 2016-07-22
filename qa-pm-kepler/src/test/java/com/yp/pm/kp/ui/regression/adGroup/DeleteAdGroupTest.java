package com.yp.pm.kp.ui.regression.adGroup;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.enums.model.AdGroupStatusEnum;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.AdGroup;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.ui.AdGroupPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class DeleteAdGroupTest extends BaseUITest {

    @Test
    public void testDeleteAdGroup() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign campaign = createCampaignOnly(account.getAccountId(), budget.getBudgetId());
        AdGroup newAdGroup = createAdGroupOnly(account.getAccountId(), campaign.getCampaignId());
        String adGroupName = newAdGroup.getAdGroupName();
        MyAgencyPage agencyPage = login();
        AdGroupPage adGroupPage = agencyPage.navigateToAdGroupTab(agencyId, account.getAccountId(), campaign.getCampaignId());
        adGroupPage.loadingStatus();
        adGroupPage.selectAdGroup(adGroupName);
        adGroupPage.clickDeleteAdGroup();
        assertConditionTrue(!adGroupPage.getAdGroupTableText(2, 3).contains(adGroupName));

        List<AdGroup> adGroupList = getAdGroupByCampaignId(account.getAccountId(), campaign.getCampaignId());
        assertConditionTrue(adGroupList.size() == 1);

        AdGroup adGroup = adGroupList.get(0);
        assertObjectNotNull(adGroup.getAdGroupId());
        assertObjectEqual(adGroup.getAdGroupName(), adGroupName);
        assertObjectEqual(adGroup.getAccountId(), account.getAccountId());
        assertObjectEqual(adGroup.getCampaignId(), campaign.getCampaignId());
        assertObjectEqual(adGroup.getMaxCpc().toDollar(), 1.00);
        assertObjectEqual(adGroup.getAdGroupStatus().toString(), AdGroupStatusEnum.DELETED.toString());
    }
}